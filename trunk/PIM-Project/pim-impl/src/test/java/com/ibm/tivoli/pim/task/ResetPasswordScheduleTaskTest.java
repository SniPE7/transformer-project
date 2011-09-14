/**
 * 
 */
package com.ibm.tivoli.pim.task;

import java.util.Arrays;
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

  private Properties environment = null;

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
    String itimUser = "itim manager";
    String itimPswd = "smartway";

    // setup environment table to create an InitialPlatformContext
    environment = new Properties();

    //env.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
    //env.put(Context.PROVIDER_URL, appServerUrl);

    environment.put(InitialPlatformContext.CONTEXT_FACTORY, contextFactory);
    environment.put(PlatformContext.PLATFORM_URL, appServerUrl);
    environment.put(PlatformContext.PLATFORM_PRINCIPAL, ejbUser);
    environment.put(PlatformContext.PLATFORM_CREDENTIALS, ejbPswd);
    environment.put("tim.server.schedule.user", itimUser);
    environment.put("tim.server.schedule.password", itimPswd);

  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testEnable() throws Exception {

    ResetPasswordScheduleTask task = new ResetPasswordScheduleTask();
    task.setPasswordGenerator(new SimplePasswordGeneratorImpl());
    task.setNotifier(new ConsoleAndLogNotifer());
    task.setOrgDN("ou=jke, DC=ITIM");
    task.setPimServiceType("PIMAdapterProfile");
    task.setPimServiceNames(Arrays.asList(new String[]{"PIMService1"}));
    task.setEnvironment(this.environment);
    
    task.enableAccounts();
  }

}
