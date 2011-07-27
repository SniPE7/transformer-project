package test;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

import junit.framework.TestCase;

import com.ibm.itim.apps.ApplicationException;
import com.ibm.itim.apps.AuthorizationException;
import com.ibm.itim.apps.InitialPlatformContext;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.SchemaViolationException;
import com.ibm.itim.apps.jaas.callback.PlatformCallbackHandler;
import com.ibm.itim.apps.workflow.WorkflowActivityMO;
import com.ibm.itim.apps.workflow.WorkflowManager;
import com.ibm.itim.apps.workflow.WorkflowProcessMO;
import com.ibm.itim.apps.workflow.WorkflowSearchMO;
import com.ibm.itim.apps.workflow.WorkflowSearchResultsMO;
import com.ibm.itim.dataservices.model.system.WorkflowQuery;
import com.ibm.itim.workflow.model.ActivityResult;
import com.ibm.itim.workflow.model.RelevantDataItem;
import com.ibm.itim.workflow.model.WorkflowException;
import com.ibm.itim.workflow.model.WorkflowProcess;
import com.ibm.itim.workflow.model.WorkflowProcessEntity;
import com.ibm.itim.workflow.model.type.ProcessType;
import com.ibm.itim.workflow.provisioning.ProvisioningQueryStatement;

public class WorkflowTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
    // -Djava.security.auth.login.config=.\jaas_login_was.conf -Dapps.context.factory=com.ibm.itim.apps.impl.websphere.WebSpherePlatformContextFactory
    System.setProperty("java.security.auth.login.config", "./jaas_login_was.conf");
    System.setProperty("apps.context.factory", "com.ibm.itim.apps.impl.websphere.WebSpherePlatformContextFactory");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  private static final String LOGIN_CONTEXT = "ITIM";

  public void testCase2() throws Exception {
    // get OS properties
    String contextFactory = "com.ibm.itim.apps.impl.websphere.WebSpherePlatformContextFactory";
    String appServerUrl = "iiop://iam:2809";
    String ejbUser = "administrator";
    String ejbPswd = "smartway";
    String itimUser = "itim manager";
    String itimPswd = "smartway";

    // setup environment table to create an InitialPlatformContext
    Hashtable env = new Hashtable();

    //env.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
    //env.put(Context.PROVIDER_URL, appServerUrl);

    env.put(InitialPlatformContext.CONTEXT_FACTORY, contextFactory);
    env.put(PlatformContext.PLATFORM_URL, appServerUrl);
    env.put(PlatformContext.PLATFORM_PRINCIPAL, ejbUser);
    env.put(PlatformContext.PLATFORM_CREDENTIALS, ejbPswd);

    System.out.print("Creating new PlatformContext...");
    PlatformContext platform = new InitialPlatformContext(env);
    Subject subject = null;
    System.out.println("Done");

    // create the ITIM JAAS CallbackHandler
    PlatformCallbackHandler handler = new PlatformCallbackHandler(itimUser, itimPswd);
    handler.setPlatformContext(platform);

    // Associate the CallbackHandler with a LoginContext,
    // then try to authenticate the user with the platform
    System.out.print("Logging in...");
    LoginContext lc = new LoginContext(LOGIN_CONTEXT, handler);
    lc.login();
    System.out.println("Done");

    // Extract the authenticated JAAS Subject from the LoginContext
    System.out.print("Getting subject... ");
    subject = lc.getSubject();
    System.out.println("Done");

    // Put the real code here

    WorkflowManager wfm = new WorkflowManager(platform, subject);
    Collection<WorkflowProcessMO> wpMOCollection = wfm.getActiveProcesses();

    if (wpMOCollection.size() == 0) {
      System.out.println("Unable to find active processes!");
    } else {
      for (WorkflowProcessMO wpMO : wpMOCollection) {
        // Find all the top workflow processes
        if (wpMO.getParent() == null) {
          System.out.println("Top Workflow Process ID: " + wpMO.getID());
          // getChildWorkflowProcess(wpMO);
          displayWorkflowProcess(wpMO);
          displayWorkflowActivities(wpMO);

        }
      }
    }
  }

  private static void displayWorkflowActivities(WorkflowProcessMO wpMO) throws RemoteException, ApplicationException {
    for (WorkflowActivityMO waMO : (Collection<WorkflowActivityMO>) wpMO.getActivities()) {
      System.out.println("\tActivity ID: " + waMO.getID());

      ActivityResult ar = new ActivityResult(ActivityResult.REJECTED);
      // waMO.complete(ar);

      // Activity a = waMO.getData();
      // System.out.println("\t\tgetDesignId: " + a.getDesignId());
    }
  }

  private static void displayWorkflowProcess(WorkflowProcessMO wpMO) throws Exception {
      WorkflowProcess wp = wpMO.getData();
      System.out.println("\t[WorkflowProcess Info] ");
      System.out.println("\tgetRequesterName: " + wp.getRequesterName());
      System.out.println("\tgetProcessType: " + wp.getProcessType());
      System.out.println("\tgetTimeSubmitted: " + wp.getTimeSubmitted());
      System.out.println("\tgetComment: " + wp.getComment());
      System.out.println("\tgetRequesteeDN: " + wp.getRequesteeDN());
      System.out.println("\tgetRequesteeName: " + wp.getRequesteeName());
      System.out.println("\tgetSubject: " + wp.getSubject());
      System.out.println("\tgetSubjectService: " + wp.getSubjectService());
      System.out.println("\tgetSubjectProfile: " + wp.getSubjectProfile());
      System.out.println("\tgetTimeScheduled: " + wp.getTimeScheduled());

      System.out.println();
      System.out.println("\t[WorkflowProcessEntity Info] ");
      WorkflowProcessEntity entity = new WorkflowProcessEntity(wp);
      for (RelevantDataItem data : (Collection<RelevantDataItem>) entity.getProcessContext()) {
        System.out.println("\t\t[Relevent Data]: " + data);
      }
  }

  private static void getChildWorkflowProcess(WorkflowProcessMO wpMO) {
    try {
      Collection cwpMOCollection = wpMO.getChildren();
      if (cwpMOCollection.size() == 0) {
        System.out.println("This top process has no child process!");
      } else {
        while (cwpMOCollection.iterator().hasNext()) {
          WorkflowProcessMO cwpMO = (WorkflowProcessMO) cwpMOCollection.iterator().next();
          System.out.println("\tChild Workflow Process ID: " + cwpMO.getID());

          cwpMOCollection.remove(cwpMO);
        }
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (SchemaViolationException e) {
      e.printStackTrace();
    } catch (AuthorizationException e) {
      e.printStackTrace();
    } catch (ApplicationException e) {
      e.printStackTrace();
    }
  }

}
