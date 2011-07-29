/**
 * 
 */
package com.ibm.tivoli.pim.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.itim.apps.ApplicationException;
import com.ibm.itim.apps.AuthorizationException;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.Request;
import com.ibm.itim.apps.SchemaViolationException;
import com.ibm.itim.apps.identity.PersonMO;
import com.ibm.itim.apps.identity.PersonManager;
import com.ibm.itim.apps.jaas.callback.PlatformCallbackHandler;
import com.ibm.itim.apps.provisioning.AccountManager;
import com.ibm.itim.apps.provisioning.ServiceMO;
import com.ibm.itim.apps.provisioning.ServiceManager;
import com.ibm.itim.apps.workflow.WorkflowActivityMO;
import com.ibm.itim.apps.workflow.WorkflowManager;
import com.ibm.itim.apps.workflow.WorkflowProcessMO;
import com.ibm.itim.common.AttributeValue;
import com.ibm.itim.dataservices.model.domain.Account;
import com.ibm.itim.workflow.model.Activity;
import com.ibm.itim.workflow.model.ActivityResult;
import com.ibm.itim.workflow.model.WorkflowProcess;
import com.ibm.tivoli.pim.entity.AccountRequest;
import com.ibm.tivoli.pim.entity.ApprovalReponse;
import com.ibm.tivoli.pim.entity.CheckInAccount;
import com.ibm.tivoli.pim.entity.RejectReponse;
import com.ibm.tivoli.pim.entity.Service;
import com.ibm.tivoli.pim.entity.SubmitResponse;
import com.ibm.tivoli.pim.entity.TimeRange;
import com.ibm.tivoli.pim.entity.User;

/**
 * @author Administrator
 * 
 */
public class RequestManagerImpl implements RequestManager, PlatformContextAware {

  private static Log log = LogFactory.getLog(RequestManagerImpl.class);

  private static final String LOGIN_CONTEXT = "ITIM";

  private PlatformContext platformContext = null;
  // private Subject subject = null;

  /**
   * Name of the profile (NTAccount, Exchange Account, etc.) identifying the
   * type of this account as listed in Configuration > Entities within the IBM
   * Tivoli Idenitity Manager UI.
   */
  private String pimAccountProfileName = "PIMProfileAccount";

  /**
   * 
   */
  public RequestManagerImpl() {
    super();
  }

  public PlatformContext getPlatformContext() {
    return platformContext;
  }

  public void setPlatformContext(PlatformContext platformContext) {
    this.platformContext = platformContext;
  }

  public String getPimAccountProfileName() {
    return pimAccountProfileName;
  }

  public void setPimAccountProfileName(String pimAccountProfileName) {
    this.pimAccountProfileName = pimAccountProfileName;
  }

  private Subject getSubject(PlatformContext platformContext, String itimUser, String itimPswd) throws LoginException {
    Subject subject = null;

    // create the ITIM JAAS CallbackHandler
    PlatformCallbackHandler handler = new PlatformCallbackHandler(itimUser, itimPswd);
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.pim.service.RequestManager#submit(com.ibm.tivoli.pim.entity
   * .AccountRequest)
   */
  public SubmitResponse submit(AccountRequest request) {
    try {
      Subject subject = this.getSubject(this.getPlatformContext(), request.getRequester().getUsername(), request.getRequester().getPassword());
      AccountManager accountManager = new AccountManager(this.getPlatformContext(), subject);
      ServiceManager serviceManager = new ServiceManager(this.getPlatformContext(), subject);
      PersonManager mgr = new PersonManager(this.getPlatformContext(), subject);
      Collection people = mgr.getPeople("uid", request.getRequester().getUsername(), null);
      if (people.isEmpty()) {
        log.error("Could not found person by uid: [" + request.getRequester().getUsername() + "]");
        return new SubmitResponse("Failure", "Could not found person by uid: [" + request.getRequester().getUsername() + "]");
      }

      PersonMO personMO = (PersonMO) people.iterator().next();
      Collection c = serviceManager.getServices(null, request.getPimServiceName());
      if (c.isEmpty()) {
        log.error("Could not found PIM Server by name: [" + request.getPimServiceName() + "]");
        return new SubmitResponse("Failure", "Could not found PIM Service by name: [" + request.getPimServiceName() + "]");
      }
      ServiceMO serviceMO = (ServiceMO) c.iterator().next();

      Account account = new Account(this.pimAccountProfileName);
      // Set target uid
      account.addAttribute(new AttributeValue("eruid", request.getAccount().getUsername()));
      // Set time range
      account.addAttribute(new AttributeValue("pimAccountBeginTime", request.getTimeRange().getBeginTime()));
      account.addAttribute(new AttributeValue("pimAccountEndTime", request.getTimeRange().getBeginTime()));
      // Set target service
      account.addAttribute(new AttributeValue("pimAccountTargetServiceProfileName", request.getService().getProfileName()));

      log.info("Submit PIM Request: [" + request + "]");
      Request timReq = accountManager.createAccount(personMO, serviceMO, account, new Date());

      SubmitResponse response = new SubmitResponse("" + timReq.getID());
      log.info("Success to submit PIM request with response: [" + response + "]");
      return response;
    } catch (AuthorizationException e) {
      log.error("Fail to submit pim request, cause: " + e.getMessage(), e);
      return new SubmitResponse("Failure", "Fail to submit pim request, cause: " + e.getMessage());
    } catch (SchemaViolationException e) {
      log.error("Fail to submit pim request, cause: " + e.getMessage(), e);
      return new SubmitResponse("Failure", "Fail to submit pim request, cause: " + e.getMessage());
    } catch (RemoteException e) {
      log.error("Fail to submit pim request, cause: " + e.getMessage(), e);
      return new SubmitResponse("Failure", "Fail to submit pim request, cause: " + e.getMessage());
    } catch (ApplicationException e) {
      log.error("Fail to submit pim request, cause: " + e.getMessage(), e);
      return new SubmitResponse("Failure", "Fail to submit pim request, cause: " + e.getMessage());
    } catch (LoginException e) {
      log.error("Fail to submit pim request, cause: " + e.getMessage(), e);
      return new SubmitResponse("Failure", "Fail to submit pim request, cause: " + e.getMessage());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.pim.service.RequestManager#approval(com.ibm.tivoli.pim.entity
   * .User, java.lang.String)
   */
  public ApprovalReponse approval(User approver, String requestId, String comment) {
    String status = ActivityResult.APPROVED;
    try {
      Subject subject = this.getSubject(this.getPlatformContext(), approver.getUsername(), approver.getPassword());
      WorkflowManager wfm = new WorkflowManager(this.platformContext, subject);
      WorkflowProcessMO processMO = wfm.getProcess(Long.parseLong(requestId));
      if (processMO == null) {
        // Not found process
        return new ApprovalReponse(ApprovalReponse.CODE_FAILURE, "Could not found WorkflowProcess by id: " + requestId);
      }
      for (WorkflowActivityMO waMO : (Collection<WorkflowActivityMO>) processMO.getActivities()) {
        log.info("\tActivity ID: " + waMO.getID());

        // ActivityResult ar = new ActivityResult(ActivityResult.REJECTED);
        // waMO.complete(ar);

        Activity a = waMO.getData();
        // RUNNING = "R";
        // NOTSTARTED = "I";
        // TERMINATED = "T";
        // ABORTED = "A";
        // SUSPENDED = "S";
        // COMPLETED = "C";
        // BYPASSED = "B";
        String state = a.getState();
        log.info("\t\tActivity: " + a);
        log.info("\t\tActivity State: " + a.getState());
        log.info("\t\tgetDesignId: " + a.getDesignId());
        if (state.equals(Activity.RUNNING)) {
          ActivityResult ar = new ActivityResult(status);
          ar.setDescription(comment);
          waMO.complete(ar);
          log.info("Approvaled Activity ID: " + waMO.getID());
          return new ApprovalReponse(ApprovalReponse.CODE_SUCCESS);
        }
      }
      return new ApprovalReponse(ApprovalReponse.CODE_FAILURE, "Could not found activity or running WorkflowProcess by id: " + requestId);
    } catch (NumberFormatException e) {
      log.error(e.getMessage(), e);
      return new ApprovalReponse(ApprovalReponse.CODE_FAILURE, e.getMessage());
    } catch (RemoteException e) {
      log.error(e.getMessage(), e);
      return new ApprovalReponse(ApprovalReponse.CODE_FAILURE, e.getMessage());
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      return new ApprovalReponse(ApprovalReponse.CODE_FAILURE, e.getMessage());
    } catch (LoginException e) {
      log.error(e.getMessage(), e);
      return new ApprovalReponse(ApprovalReponse.CODE_FAILURE, e.getMessage());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.pim.service.RequestManager#reject(com.ibm.tivoli.pim.entity
   * .User, java.lang.String)
   */
  public RejectReponse reject(User rejector, String requestId, String comment) {
    String status = ActivityResult.APPROVED;
    try {
      Subject subject = this.getSubject(this.getPlatformContext(), rejector.getUsername(), rejector.getPassword());
      WorkflowManager wfm = new WorkflowManager(this.platformContext, subject);
      WorkflowProcessMO processMO = wfm.getProcess(Long.parseLong(requestId));
      if (processMO == null) {
        // Not found process
        return new RejectReponse(ApprovalReponse.CODE_FAILURE, "Could not found WorkflowProcess by id: " + requestId);
      }
      for (WorkflowActivityMO waMO : (Collection<WorkflowActivityMO>) processMO.getActivities()) {
        log.info("\tActivity ID: " + waMO.getID());

        // ActivityResult ar = new ActivityResult(ActivityResult.REJECTED);
        // waMO.complete(ar);

        Activity a = waMO.getData();
        // RUNNING = "R";
        // NOTSTARTED = "I";
        // TERMINATED = "T";
        // ABORTED = "A";
        // SUSPENDED = "S";
        // COMPLETED = "C";
        // BYPASSED = "B";
        String state = a.getState();
        log.info("\t\tActivity: " + a);
        log.info("\t\tActivity State: " + a.getState());
        log.info("\t\tgetDesignId: " + a.getDesignId());
        if (state.equals(Activity.RUNNING)) {
          ActivityResult ar = new ActivityResult(status);
          ar.setDescription(comment);
          waMO.complete(ar);
          log.info("Rejected Activity ID: " + waMO.getID());
          return new RejectReponse(RejectReponse.CODE_SUCCESS);
        }
      }
      return new RejectReponse(RejectReponse.CODE_FAILURE, "Could not found activity or running WorkflowProcess by id: " + requestId);
    } catch (NumberFormatException e) {
      log.error(e.getMessage(), e);
      return new RejectReponse(RejectReponse.CODE_FAILURE, e.getMessage());
    } catch (RemoteException e) {
      log.error(e.getMessage(), e);
      return new RejectReponse(RejectReponse.CODE_FAILURE, e.getMessage());
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
      return new RejectReponse(RejectReponse.CODE_FAILURE, e.getMessage());
    } catch (LoginException e) {
      log.error(e.getMessage(), e);
      return new RejectReponse(RejectReponse.CODE_FAILURE, e.getMessage());
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.pim.service.RequestManager#getPendingRequestsByRequester
   * (com.ibm.tivoli.pim.entity.User)
   */
  public List<AccountRequest> getPendingRequestsByRequester(User requester) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.pim.service.RequestManager#getPendingRequestsByTargetService
   * (java.lang.String)
   */
  public List<AccountRequest> getPendingRequestsByTargetService(String serviceName) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.pim.service.RequestManager#getPendingRequestsByTargetAccount
   * (java.lang.String, java.lang.String)
   */
  public List<AccountRequest> getPendingRequestsByTargetAccount(String serviceName, String accountId) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.pim.service.RequestManager#getPendingRequestsByApprover(
   * com.ibm.tivoli.pim.entity.User)
   */
  public List<AccountRequest> getPendingRequestsByApprover(User approver) {
    List<AccountRequest> result = new ArrayList<AccountRequest>();
    try {
      Subject subject = this.getSubject(this.getPlatformContext(), approver.getUsername(), approver.getPassword());

      WorkflowManager wfm = new WorkflowManager(this.platformContext, subject);
      Collection<WorkflowProcessMO> wpMOCollection = wfm.getActiveProcesses();

      if (wpMOCollection.size() == 0) {
        log.info("Unable to find active processes!");
      } else {
        for (WorkflowProcessMO wpMO : wpMOCollection) {
          // Find all the top workflow processes
          if (wpMO.getParent() == null) {
            log.info("Top Workflow Process ID: " + wpMO.getID());
            // getChildWorkflowProcess(wpMO);
            WorkflowProcess wp = wpMO.getData();
            log.info("\t[WorkflowProcess Info] ");
            log.info("\tgetRequester: " + wp.getRequester());
            log.info("\tgetProcessType: " + wp.getProcessType());
            log.info("\tgetTimeSubmitted: " + wp.getTimeSubmitted());
            log.info("\tgetComment: " + wp.getComment());
            log.info("\tgetRequesteeDN: " + wp.getRequesteeDN());
            log.info("\tgetRequesteeName: " + wp.getRequesteeName());
            log.info("\tgetSubject: " + wp.getSubject());
            log.info("\tgetSubjectService: " + wp.getSubjectService());
            log.info("\tgetSubjectProfile: " + wp.getSubjectProfile());
            log.info("\tgetTimeScheduled: " + wp.getTimeScheduled());

            AccountRequest req = new AccountRequest();
            req.setId(Long.toString(wp.getId()));
            req.setTimeRange(new TimeRange());
            req.setService(new Service());
            
            result.add(req);
            /*
            log.info("\t[WorkflowProcessEntity Info] ");
            WorkflowProcessEntity entity = new WorkflowProcessEntity(wp);
            for (RelevantDataItem data : (Collection<RelevantDataItem>) entity.getProcessContext()) {
              log.info("\t\t[Relevent Data]: " + data);
            }
            */
          }
        }
      }
    } catch (NumberFormatException e) {
      log.error(e.getMessage(), e);
    } catch (LoginException e) {
      log.error(e.getMessage(), e);
    } catch (RemoteException e) {
      log.error(e.getMessage(), e);
    } catch (ApplicationException e) {
      log.error(e.getMessage(), e);
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.pim.service.RequestManager#getAllCheckInAccount(com.ibm.
   * tivoli.pim.entity.TimeRange)
   */
  public List<CheckInAccount> getAllCheckInAccount(TimeRange timeRange) {
    // TODO Auto-generated method stub
    return null;
  }

}
