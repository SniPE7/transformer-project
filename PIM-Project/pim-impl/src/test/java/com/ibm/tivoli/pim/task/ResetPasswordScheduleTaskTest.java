/**
 * 
 */
package com.ibm.tivoli.pim.task;

import java.util.Properties;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import junit.framework.TestCase;

import com.ibm.itim.apps.InitialPlatformContext;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.jaas.callback.PlatformCallbackHandler;

/**
 * @author Administrator
 *
 */
public class ResetPasswordScheduleTaskTest extends TestCase {

  private static final String LOGIN_CONTEXT = "ITIM";

  private PlatformContext platformContext;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    System.setProperty("java.security.auth.login.config", "./jaas_login_was.conf");
    System.setProperty("apps.context.factory", "com.ibm.itim.apps.impl.websphere.WebSpherePlatformContextFactory");

    // get OS properties
    String contextFactory = "com.ibm.itim.apps.impl.websphere.WebSpherePlatformContextFactory";
    String appServerUrl = "iiop://iam:2809";
    String ejbUser = "administrator";
    String ejbPswd = "smartway";

    // setup environment table to create an InitialPlatformContext
    Properties env = new Properties();

    //env.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
    //env.put(Context.PROVIDER_URL, appServerUrl);

    env.put(InitialPlatformContext.CONTEXT_FACTORY, contextFactory);
    env.put(PlatformContext.PLATFORM_URL, appServerUrl);
    env.put(PlatformContext.PLATFORM_PRINCIPAL, ejbUser);
    env.put(PlatformContext.PLATFORM_CREDENTIALS, ejbPswd);

    System.out.print("Creating new PlatformContext...");
    platformContext = new InitialPlatformContext(env);
  }

  private Subject getSubject(PlatformContext platformContext, String itimUser, String itimPswd) throws LoginException {
    Subject subject = null;
    System.out.println("Done");

    // create the ITIM JAAS CallbackHandler
    PlatformCallbackHandler handler = new PlatformCallbackHandler(itimUser, itimPswd);
    handler.setPlatformContext(platformContext);

    // Associate the CallbackHandler with a LoginContext,
    // then try to authenticate the user with the platform
    System.out.print("Logging in...");
    LoginContext lc = new LoginContext(LOGIN_CONTEXT, handler);
    lc.login();
    System.out.println("Done");

    // Extract the authenticated JAAS Subject from the LoginContext
    System.out.print("Getting subject... ");
    subject = lc.getSubject();
    return subject;
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testEnable() throws Exception {
    String itimUser = "itim manager";
    String itimPswd = "smartway";

    ResetPasswordScheduleTask task = new ResetPasswordScheduleTask();
    task.setPasswordGenerator(new SimplePasswordGeneratorImpl());
    task.setNotifier(new ConsoleAndLogNotifer());
    task.setOrgDN("ou=jke, DC=ITIM");
    //task.setPimAccountProfileName("PIMAdapterProfile");
    //task.setNameOfService("PIMServer1");
    task.setPimAccountProfileName("PIMAdapterProfile");
    task.setNameOfService("PIMService1");
    task.setPlatformContext(this.platformContext);
    task.setSubject(this.getSubject(this.platformContext, itimUser, itimPswd));
    
    task.enableAccounts();
  }

}
