/**
 * 
 */
package com.ibm.tivoli.pim.task;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.itim.apps.ApplicationException;
import com.ibm.itim.apps.AuthorizationException;
import com.ibm.itim.apps.InitialPlatformContext;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.identity.InvalidPasswordException;
import com.ibm.itim.apps.identity.OrganizationalContainerMO;
import com.ibm.itim.apps.jaas.callback.PlatformCallbackHandler;
import com.ibm.itim.apps.provisioning.AccountMO;
import com.ibm.itim.apps.provisioning.AccountManager;
import com.ibm.itim.apps.provisioning.PasswordManager;
import com.ibm.itim.apps.provisioning.PasswordRuleException;
import com.ibm.itim.apps.provisioning.ServiceMO;
import com.ibm.itim.apps.provisioning.ServiceManager;
import com.ibm.itim.common.AttributeValue;
import com.ibm.itim.dataservices.model.DistinguishedName;
import com.ibm.itim.dataservices.model.domain.Account;
import com.ibm.tivoli.pim.entity.TimeRange;

/**
 * @author zhaodonglu
 * 
 */
public class ResetPasswordScheduleTask extends ScheduleTask {
  private static Log log = LogFactory.getLog(ResetPasswordScheduleTask.class);

  private static final String LOGIN_CONTEXT = "ITIM";

  private Hashtable environment = new Hashtable();

  /**
   * Name of the profile (NTAccount, Exchange Account, etc.) identifying the
   * type of this account as listed in Configuration > Entities within the IBM
   * Tivoli Idenitity Manager UI.
   */
  private String pimServiceType = "PIMProfileAccount";

  /**
   * PIM Service Name
   */
  private List<String> pimServiceNames;

  /**
   * Organization Container DN in TIM
   */
  private String orgDN;
  /**
   * Password generator
   */
  private PasswordGenerator passwordGenerator;

  /**
   * Notifier
   */
  private Notifier notifier;

  /**
   * 
   */
  public ResetPasswordScheduleTask() {
    super();
  }

  public Hashtable getEnvironment() {
    return environment;
  }

  public void setEnvironment(Hashtable environment) {
    this.environment = environment;
  }

  /**
   * @return the subject
   * @throws LoginException 
   */
  private Subject getSubject(PlatformContext platformContext) throws LoginException {
    Subject subject = null;

    // create the ITIM JAAS CallbackHandler
    PlatformCallbackHandler handler = new PlatformCallbackHandler((String)this.getEnvironment().get("tim.server.schedule.user"), (String)this.getEnvironment().get("tim.server.schedule.password"));
    handler.setPlatformContext(platformContext);

    // Associate the CallbackHandler with a LoginContext,
    // then try to authenticate the user with the platform
    log.info("Logging in...");
    LoginContext lc = new LoginContext(LOGIN_CONTEXT, handler);
    lc.login();
    log.info("Done");

    // Extract the authenticated JAAS Subject from the LoginContext
    log.info("Getting subject... ");
    subject = lc.getSubject();
    return subject;
  }

  public String getPimServiceType() {
    return pimServiceType;
  }

  public void setPimServiceType(String pimAccountProfileName) {
    this.pimServiceType = pimAccountProfileName;
  }

  public String getOrgDN() {
    return orgDN;
  }

  public void setOrgDN(String orgDN) {
    this.orgDN = orgDN;
  }

  public List<String> getPimServiceNames() {
    return pimServiceNames;
  }

  public void setPimServiceNames(List<String> nameOfServices) {
    this.pimServiceNames = nameOfServices;
  }

  public PasswordGenerator getPasswordGenerator() {
    return passwordGenerator;
  }

  public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
    this.passwordGenerator = passwordGenerator;
  }

  public Notifier getNotifier() {
    return notifier;
  }

  public void setNotifier(Notifier notifier) {
    this.notifier = notifier;
  }

  @Override
  protected void enableAccounts() {
    try {
      PlatformContext pcontext = new InitialPlatformContext((Hashtable) this.getEnvironment().clone());

      Subject subj = this.getSubject(pcontext);
      OrganizationalContainerMO org = new OrganizationalContainerMO(pcontext, subj, new DistinguishedName(this.orgDN));
      ServiceManager sm = new ServiceManager(pcontext, subj);
      AccountManager acctMgr = new AccountManager(pcontext, subj);

      PasswordManager pwdMgr = new PasswordManager(pcontext, subj);
      for (String pimServiceName : this.pimServiceNames) {
        if (pimServiceName.trim().length() == 0) {
           continue;
        }
        Collection<ServiceMO> services = sm.getServices(org, this.pimServiceType, pimServiceName);
        if (services == null) {
          log.error(String.format("Cound not found PIM Service by service type :[%s], and service name: [%s]", this.pimServiceType, pimServiceName));
          return;
        }
        for (ServiceMO serviceMO : services) {
          Collection<AccountMO> accountMOs = serviceMO.getAccounts();
          for (AccountMO accountMO : accountMOs) {
            try {
              process4Activation(org, sm, acctMgr, pwdMgr, accountMO);
            } catch (Exception e) {
              log.error(String.format("fail to process PIM account: [%s]", accountMO.getDistinguishedName().toString()), e);
            }
          }
        }
      }

    } catch (RemoteException e) {
      log.error(e.getMessage(), e);
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
    } catch (LoginException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void process4Activation(OrganizationalContainerMO org, ServiceManager sm, AccountManager acctMgr, PasswordManager pwdMgr, AccountMO accountMO) throws RemoteException,
      ApplicationException, PasswordRuleException, InvalidPasswordException, AuthorizationException {
    Account account = accountMO.getData();
    // Check status
    if (account.isSuspended()) {
      return;
    }
    // Check data
    if (account.getAttribute("pimAccountTargetServiceName") == null || account.getAttribute("pimAccountBeginTime") == null || account.getAttribute("pimAccountEndTime") == null) {
      return;
    }

    // Check activation time
    if (isTimeToNotify(accountMO)) {
      log.info(String.format("Prepare to active account[%s]", account.toString()));
      // Get target service and account
      String targetServiceName = account.getAttribute("pimAccountTargetServiceName").getString();
      Collection<ServiceMO> tsMOs = sm.getServices(org, targetServiceName);
      if (tsMOs == null || tsMOs.size() == 0) {
        log.warn(String.format("Could not found target service by name [%s]", targetServiceName));
      } else if (tsMOs.size() > 1) {
        log.warn(String.format("Found more than one target services with name [%s]", targetServiceName));
      } else {
        ServiceMO targetService = tsMOs.iterator().next();
        String targetUid = account.getAttribute("eruid").getString();
        Collection<AccountMO> targetAccts = acctMgr.getAccounts(targetService, targetUid);
        if (targetAccts == null || targetAccts.size() == 0) {
          log.warn(String.format("Could not found target account by [ %s @ [%s] ]", targetUid, targetServiceName));
        } else {
          // Generate Password
          // String password = pwdMgr.generatePassword(acts);
          String password = this.passwordGenerator.generate();
          // Send mail & reset password
          Date scheduledTime = new Date();
          pwdMgr.changePassword(targetAccts, password, scheduledTime, false);
          this.notifier.notifyActivation(accountMO, password);

          // Update last notify time
          account.setAttribute(new AttributeValue("pimAccountNotifiedTime", new Date()));
          accountMO.update(account, new Date());
        }
      }
    }
  }

  private boolean isTimeToNotify(AccountMO accountMO) throws RemoteException, ApplicationException {
    Account account = accountMO.getData();
    TimeRange tr = new TimeRange(account.getAttribute("pimAccountBeginTime").getDate(), account.getAttribute("pimAccountEndTime").getDate());

    Date lastNotifyTime = null;
    AttributeValue attribute = account.getAttribute("pimAccountNotifiedTime");
    if (attribute != null) {
      lastNotifyTime = attribute.getDate();
    }
    if (lastNotifyTime != null) {
      return false;
    }
    return true;
  }

  @Override
  protected void disableAccounts() {
    try {
      PlatformContext pcontext = new InitialPlatformContext((Hashtable) this.getEnvironment().clone());
      Subject subj = this.getSubject(pcontext);
      OrganizationalContainerMO org = new OrganizationalContainerMO(pcontext, subj, new DistinguishedName(this.orgDN));
      ServiceManager sm = new ServiceManager(pcontext, subj);
      AccountManager acctMgr = new AccountManager(pcontext, subj);

      PasswordManager pwdMgr = new PasswordManager(pcontext, subj);
      for (String pimServiceName : this.pimServiceNames) {
        if (pimServiceName.trim().length() == 0) {
          continue;
        }
        Collection<ServiceMO> services = sm.getServices(org, this.pimServiceType, pimServiceName);
        if (services == null) {
          log.error(String.format("Cound not found PIM Service by service type :[%s], and service name: [%s]", this.pimServiceType, pimServiceName));
          return;
        }
        for (ServiceMO serviceMO : services) {
          Collection<AccountMO> accountMOs = serviceMO.getAccounts();
          for (AccountMO accountMO : accountMOs) {
            try {
              process4Deactivation(org, sm, acctMgr, pwdMgr, accountMO);
            } catch (Exception e) {
              log.error(String.format("fail to process PIM account: [%s]", accountMO.getDistinguishedName().toString()), e);
            }
          }
        }
      }

    } catch (RemoteException e) {
      log.error(e.getMessage(), e);
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
    } catch (LoginException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void process4Deactivation(OrganizationalContainerMO org, ServiceManager sm, AccountManager acctMgr, PasswordManager pwdMgr, AccountMO accountMO) throws RemoteException,
      ApplicationException, PasswordRuleException, InvalidPasswordException, AuthorizationException {
    Account account = accountMO.getData();
    // Check status
    if (account.isSuspended()) {
      return;
    }
    // Check data
    if (account.getAttribute("pimAccountTargetServiceName") == null || account.getAttribute("pimAccountBeginTime") == null || account.getAttribute("pimAccountEndTime") == null) {
      return;
    }

    // Check activation time
    if (isTimeToDeactivation(accountMO)) {
      log.info(String.format("Prepare to deactive account[%s]", account.toString()));
      // Get target service and account
      String targetServiceName = account.getAttribute("pimAccountTargetServiceName").getString();
      Collection<ServiceMO> tsMOs = sm.getServices(org, targetServiceName);
      if (tsMOs == null || tsMOs.size() == 0) {
        log.warn(String.format("Could not found target service by name [%s]", targetServiceName));
      } else if (tsMOs.size() > 1) {
        log.warn(String.format("Found more than one target services with name [%s]", targetServiceName));
      } else {
        ServiceMO targetService = tsMOs.iterator().next();
        String targetUid = account.getAttribute("eruid").getString();
        Collection<AccountMO> targetAccts = acctMgr.getAccounts(targetService, targetUid);
        if (targetAccts == null || targetAccts.size() == 0) {
          log.warn(String.format("Could not found target account by [ %s @ [%s] ]", targetUid, targetServiceName));
        } else {
          // Generate Password
          // String password = pwdMgr.generatePassword(acts);
          String password = this.passwordGenerator.generate();
          // Send mail & reset password
          Date scheduledTime = new Date();
          pwdMgr.changePassword(targetAccts, password, scheduledTime, false);
          this.notifier.notifyDeactivation(accountMO, password);

          // Disable PIM account
          accountMO.suspend(new Date());
          // accountMO.changePassword(password);
        }
      }
    }
  }

  private boolean isTimeToDeactivation(AccountMO accountMO) throws RemoteException, ApplicationException {
    Account account = accountMO.getData();
    TimeRange tr = new TimeRange(account.getAttribute("pimAccountBeginTime").getDate(), account.getAttribute("pimAccountEndTime").getDate());

    Date lastNotifyTime = null;
    AttributeValue attribute = account.getAttribute("pimAccountNotifiedTime");
    if (attribute != null) {
      lastNotifyTime = attribute.getDate();
    }
    return true;
  }

}
