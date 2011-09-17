/**
 * 
 */
package com.ibm.tivoli.pim.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.MessageContext;

import com.ibm.itim.apps.ApplicationException;
import com.ibm.itim.apps.AuthorizationException;
import com.ibm.itim.apps.InitialPlatformContext;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.Request;
import com.ibm.itim.apps.SchemaViolationException;
import com.ibm.itim.apps.identity.PersonMO;
import com.ibm.itim.apps.identity.PersonManager;
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
import com.ibm.tivoli.pim.callback.ChainSubjectCallbackHandler;
import com.ibm.tivoli.pim.callback.HttpSessionBasedSubjectCallbackHandler;
import com.ibm.tivoli.pim.callback.SubjectCallbackHandler;
import com.ibm.tivoli.pim.callback.UserBasedSubjectCallbackHandler;
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
@WebService(endpointInterface = "com.ibm.tivoli.pim.service.RequestManager")
public class RequestManagerImpl implements RequestManager {

  private static Log log = LogFactory.getLog(RequestManagerImpl.class);

  private static final String LOGIN_CONTEXT = "ITIM";

  @Resource
  private WebServiceContext webServiceContext;

  @Context
  private MessageContext messgeContext;

  /**
   * Subject callback handler
   */
  private SubjectCallbackHandler subjectCallbackHandler = new ChainSubjectCallbackHandler(new UserBasedSubjectCallbackHandler(), new HttpSessionBasedSubjectCallbackHandler());
  
  private Hashtable environment = new Hashtable();
  
  //private PlatformContext platformContext = null;

  /**
   * Name of the profile identifying the type of PIM account as listed in
   * Configuration > Entities within the IBM Tivoli Idenitity Manager UI.
   */
  private String pimAccountProfileName = "PIMProfileAccount";

  /**
   * 
   */
  public RequestManagerImpl() {
    super();
  }

  public void setWebServiceContext(WebServiceContext webServiceContext) {
    this.webServiceContext = webServiceContext;
  }

  public void setMessgeContext(MessageContext messgeContext) {
    this.messgeContext = messgeContext;
  }

  public String getPimAccountProfileName() {
    return pimAccountProfileName;
  }

  public void setPimAccountProfileName(String pimAccountProfileName) {
    this.pimAccountProfileName = pimAccountProfileName;
  }

  public SubjectCallbackHandler getSubjectCallbackHandler() {
    return subjectCallbackHandler;
  }

  public void setSubjectCallbackHandler(SubjectCallbackHandler subjectCallbackHandler) {
    this.subjectCallbackHandler = subjectCallbackHandler;
  }

  private HttpServletRequest getHttpRequest() {
    HttpServletRequest request = null;
    if (webServiceContext != null && webServiceContext.getMessageContext() != null) {
       // For SOAP mode
       request = (HttpServletRequest) webServiceContext.getMessageContext().get("HTTP.REQUEST");
    }
    
    if (this.messgeContext != null) {
       // For RESTful mode
       request = this.messgeContext.getHttpServletRequest();
    }
    return request;
  }

  private Subject getSubject(PlatformContext platformContext, User user) throws LoginException {
    return this.subjectCallbackHandler.getSubject(LOGIN_CONTEXT, platformContext, this.getHttpRequest(), user);
  }

  public Hashtable getEnvironment() {
    return environment;
  }

  public void setEnvironment(Hashtable environment) {
    this.environment = environment;
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
      PlatformContext pcontext = new InitialPlatformContext((Hashtable) this.getEnvironment().clone());
      Subject subject = this.getSubject(pcontext, request.getRequester());
      AccountManager accountManager = new AccountManager(pcontext, subject);
      ServiceManager serviceManager = new ServiceManager(pcontext, subject);
      PersonManager mgr = new PersonManager(pcontext, subject);
      String currentUser = getCurrentUser(request);
      if (currentUser == null) {
        log.error("Could not found current login uid");
        return new SubmitResponse("Failure", "Could not found current login uid");
      }
      Collection people = mgr.getPeople("uid", currentUser, null);
      if (people.isEmpty()) {
        log.error("Could not found person by uid: [" + currentUser + "]");
        return new SubmitResponse("Failure", "Could not found person by uid: [" + currentUser + "]");
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
      // account.addAttribute(new AttributeValue("pimAccountTargetServiceType",
      // ""));
      account.addAttribute(new AttributeValue("pimAccountTargetServiceName", request.getService().getProfileName()));

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

  private String getCurrentUser(AccountRequest request) {
    HttpServletRequest hreq = this.getHttpRequest();
    if (hreq == null) {
       return request.getRequester().getUsername();
    }
    HttpSession session = hreq.getSession(false);
    if (session != null) {
       return (String)session.getAttribute(LoginServiceImpl.SESSION_USERNAME);
    }
    return null;
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
      PlatformContext pcontext = new InitialPlatformContext((Hashtable) this.getEnvironment().clone());
      Subject subject = this.getSubject(pcontext, approver);
      WorkflowManager wfm = new WorkflowManager(pcontext, subject);
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
    String status = ActivityResult.REJECTED;
    try {
      PlatformContext pcontext = new InitialPlatformContext((Hashtable) this.getEnvironment().clone());
      Subject subject = this.getSubject(pcontext, rejector);
      WorkflowManager wfm = new WorkflowManager(pcontext, subject);
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
    return this.getPendingRequestsByApprover(requester);
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
    return this.getPendingRequestsByApprover(null);
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
    return this.getPendingRequestsByApprover(null);
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
      PlatformContext pcontext = new InitialPlatformContext((Hashtable) this.getEnvironment().clone());
      Subject subject = this.getSubject(pcontext, approver);

      WorkflowManager wfm = new WorkflowManager(pcontext, subject);
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
             * log.info("\t[WorkflowProcessEntity Info] ");
             * WorkflowProcessEntity entity = new WorkflowProcessEntity(wp); for
             * (RelevantDataItem data : (Collection<RelevantDataItem>)
             * entity.getProcessContext()) { log.info("\t\t[Relevent Data]: " +
             * data); }
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
    //return result.toArray(new AccountRequest[0]);
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
