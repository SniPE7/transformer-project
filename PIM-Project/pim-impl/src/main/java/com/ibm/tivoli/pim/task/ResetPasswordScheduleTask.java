/**
 * 
 */
package com.ibm.tivoli.pim.task;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;

import javax.security.auth.Subject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.itim.apps.ApplicationException;
import com.ibm.itim.apps.AuthorizationException;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.identity.InvalidPasswordException;
import com.ibm.itim.apps.identity.OrganizationalContainerMO;
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

  private PlatformContext platformContext = null;
  private Subject subject = null;

  /**
   * Name of the profile (NTAccount, Exchange Account, etc.) identifying the
   * type of this account as listed in Configuration > Entities within the IBM
   * Tivoli Idenitity Manager UI.
   */
  private String pimAccountProfileName = "PIMProfileAccount";
  private String orgDN;
  private String nameOfService;
  private PasswordGenerator passwordGenerator;

  private Notifier notifier;

  /**
   * 
   */
  public ResetPasswordScheduleTask() {
    super();
  }

  /**
   * @return the platformContext
   */
  public PlatformContext getPlatformContext() {
    return platformContext;
  }

  /**
   * @param platformContext
   *          the platformContext to set
   */
  public void setPlatformContext(PlatformContext platformContext) {
    this.platformContext = platformContext;
  }

  /**
   * @return the subject
   */
  public Subject getSubject() {
    return subject;
  }

  /**
   * @param subject
   *          the subject to set
   */
  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public String getPimAccountProfileName() {
    return pimAccountProfileName;
  }

  public void setPimAccountProfileName(String pimAccountProfileName) {
    this.pimAccountProfileName = pimAccountProfileName;
  }

  public String getOrgDN() {
    return orgDN;
  }

  public void setOrgDN(String orgDN) {
    this.orgDN = orgDN;
  }

  public String getNameOfService() {
    return nameOfService;
  }

  public void setNameOfService(String nameOfService) {
    this.nameOfService = nameOfService;
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
      OrganizationalContainerMO org = new OrganizationalContainerMO(this.getPlatformContext(), this.getSubject(), new DistinguishedName(this.orgDN));
      ServiceManager sm = new ServiceManager(this.getPlatformContext(), this.getSubject());
      AccountManager acctMgr = new AccountManager(this.getPlatformContext(), this.getSubject());

      PasswordManager pwdMgr = new PasswordManager(this.getPlatformContext(), this.getSubject());
      Collection<ServiceMO> services = sm.getServices(org, this.pimAccountProfileName, this.nameOfService);
      if (services == null) {
        log.error(String.format("Cound not found PIM Service by service type :[%s], and service name: [%s]", this.pimAccountProfileName, this.nameOfService));
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

    } catch (RemoteException e) {
      log.error(e.getMessage(), e);
    } catch (ApplicationException e) {
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
      OrganizationalContainerMO org = new OrganizationalContainerMO(this.getPlatformContext(), this.getSubject(), new DistinguishedName(this.orgDN));
      ServiceManager sm = new ServiceManager(this.getPlatformContext(), this.getSubject());
      AccountManager acctMgr = new AccountManager(this.getPlatformContext(), this.getSubject());

      PasswordManager pwdMgr = new PasswordManager(this.getPlatformContext(), this.getSubject());
      Collection<ServiceMO> services = sm.getServices(org, this.pimAccountProfileName, this.nameOfService);
      if (services == null) {
        log.error(String.format("Cound not found PIM Service by service type :[%s], and service name: [%s]", this.pimAccountProfileName, this.nameOfService));
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

    } catch (RemoteException e) {
      log.error(e.getMessage(), e);
    } catch (ApplicationException e) {
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
