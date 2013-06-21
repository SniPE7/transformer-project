package group.tivoli.security.eai.loginmodule.tim;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Properties;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ibm.itim.apps.ApplicationException;
import com.ibm.itim.apps.InitialPlatformContext;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.jaas.callback.PlatformCallbackHandler;

/**
 * 类（接口）继承、实现、功能等描述
 * @author xuhong
 * @since 2012-11-22 上午10:20:59
 */

public class TIMLoginTest{

  private static final String LOGIN_CONTEXT = "ITIM";

  private Properties environment = null;

  @Before
  public void setUp() throws Exception {
    //System.setProperty("java.security.auth.login.config", "classpath:conf/jaas_login_was.conf");
    System.setProperty("java.security.auth.login.config", "c:/jaas_login_was.conf");
    
    System.setProperty("apps.context.factory", "com.ibm.itim.apps.impl.websphere.WebSpherePlatformContextFactory");

    // get OS properties
    String contextFactory = "com.ibm.itim.apps.impl.websphere.WebSpherePlatformContextFactory";
    String appServerUrl = "iiop://devtam1:2809/cell/clusters/ITIM_Cluster";
    String ejbUser = "wasadmin";
    String ejbPswd = "Pass1234";

    // setup environment table to create an InitialPlatformContext
    environment = new Properties();

    //env.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
    //env.put(Context.PROVIDER_URL, appServerUrl);

    environment.put(InitialPlatformContext.CONTEXT_FACTORY, contextFactory);
    environment.put(PlatformContext.PLATFORM_URL, appServerUrl);
    environment.put(PlatformContext.PLATFORM_PRINCIPAL, ejbUser);
    environment.put(PlatformContext.PLATFORM_CREDENTIALS, ejbPswd);
    
    environment.put("com.ibm.CORBA.securityServerHost", "devtam1");
    environment.put("com.ibm.CORBA.securityServerPort", "2809");
  }

  @After
  public void tearDown() throws Exception {
    System.out.println("tearDown");
  }

  
  @Test
  public void LoginTIMTest() throws RemoteException, ApplicationException, LoginException{
    
    String itimUser = "maqs";
    String itimPswd = "000000";
    
    TIMLoginTest loginTest = new TIMLoginTest();
    
/*    String contextFactory = "com.ibm.itim.apps.impl.websphere.WebSpherePlatformContextFactory";
    //String appServerUrl = "iiop://iiop://10.5.86.161:2809/cell/clusters/tim_cluster";
    String appServerUrl = "iiop://10.5.86.161:2809";
    String ejbUser = "itim manager";
    String ejbPswd = "Pass1234";
    
    Hashtable env = new Hashtable();
    env.put(InitialPlatformContext.CONTEXT_FACTORY, contextFactory);
    env.put(PlatformContext.PLATFORM_URL, appServerUrl);
    env.put(PlatformContext.PLATFORM_PRINCIPAL, ejbUser);
    env.put(PlatformContext.PLATFORM_CREDENTIALS, ejbPswd);

    */
    PlatformContext platformContext = new InitialPlatformContext((Hashtable) this.getEnvironment().clone());
    //PlatformContext platformContext = new InitialPlatformContext(env);
    
    Subject subject = loginTest.getSubject(platformContext, itimUser, itimPswd);
    
    System.out.println("subject:" + subject);
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
  
  
  
  public Properties getEnvironment() {
    return environment;
  }

  public void setEnvironment(Properties environment) {
    this.environment = environment;
  }
  

}
