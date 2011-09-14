package com.ibm.tivoli.pim.service;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import junit.framework.TestCase;

import com.ibm.itim.apps.InitialPlatformContext;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.jaas.callback.PlatformCallbackHandler;
import com.ibm.tivoli.pim.entity.AccountRequest;
import com.ibm.tivoli.pim.entity.PIMAccount;
import com.ibm.tivoli.pim.entity.Service;
import com.ibm.tivoli.pim.entity.SubmitResponse;
import com.ibm.tivoli.pim.entity.TimeRange;
import com.ibm.tivoli.pim.entity.User;

public class RequestManagerTest extends TestCase {

  private static final String LOGIN_CONTEXT = "ITIM";

  private Properties environment = null;

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
    Properties environment = new Properties();

    //env.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
    //env.put(Context.PROVIDER_URL, appServerUrl);

    environment.put(InitialPlatformContext.CONTEXT_FACTORY, contextFactory);
    environment.put(PlatformContext.PLATFORM_URL, appServerUrl);
    environment.put(PlatformContext.PLATFORM_PRINCIPAL, ejbUser);
    environment.put(PlatformContext.PLATFORM_CREDENTIALS, ejbPswd);

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

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testSubmit() throws Exception {
    AccountRequest req = new AccountRequest();
    req.setAccount(new PIMAccount("jhill"));
    req.setPimServiceName("PIMService1");
    req.setRequester(new User("bbbbb", "passw0rd"));
    //req.setRequester(new User("aaaaaaa", "passw0rd"));
    req.setService(new Service("ADprofile", "JKE Active Directory"));
    req.setTimeRange(new TimeRange(new Date(), new Date()));

    RequestManagerImpl rm = new RequestManagerImpl();
    rm.setEnvironment(this.environment);
    rm.setPimAccountProfileName("PIMProfileAccount");
    
    SubmitResponse resp = rm.submit(req);
    assertEquals("Success", resp.getCode());
    assertNotNull(resp.getRequestId());
  }
  
  public void testApproval() throws Exception {
    User approver = new User("itim manager", "smartway");
    RequestManagerImpl rm = new RequestManagerImpl();
    rm.setEnvironment(this.environment);
    rm.setPimAccountProfileName("PIMProfileAccount");
    
    rm.approval(approver, "8358946509918800095", "approval comment");
  }
  
  public void testSubmitAndAproval() throws Exception {
    AccountRequest req = new AccountRequest();
    req.setAccount(new PIMAccount("root"));
    req.setPimServiceName("PIMServer1");
    req.setRequester(new User("bbbbb", "passw0rd"));
    //req.setRequester(new User("aaaaaaa", "passw0rd"));
    req.setService(new Service("", ""));
    req.setTimeRange(new TimeRange(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 3600 * 1000)));

    RequestManagerImpl rm = new RequestManagerImpl();
    rm.setEnvironment(this.environment);
    rm.setPimAccountProfileName("PIMProfileAccount");
    
    SubmitResponse resp = rm.submit(req);
    assertEquals("Success", resp.getCode());
    assertNotNull(resp.getRequestId());

    User approver = new User("itim manager", "smartway");
    rm.setEnvironment(this.environment);
    rm.setPimAccountProfileName("PIMProfileAccount");
    
    rm.approval(approver, "8358946509918800095", "approval comment");
  }
  
  public void testReject() throws Exception {
    User rejector = new User("itim manager", "smartway");
    RequestManagerImpl rm = new RequestManagerImpl();
    rm.setEnvironment(this.environment);
    rm.setPimAccountProfileName("PIMProfileAccount");
    
    rm.reject(rejector, "8358946509918800095", "approval comment");
  }
  
  public void testGetPendingRequestsByApprover() throws Exception {
    User approver = new User("itim manager", "smartway");
    RequestManagerImpl rm = new RequestManagerImpl();
    rm.setEnvironment(this.environment);
    rm.setPimAccountProfileName("PIMProfileAccount");
    
    List<AccountRequest> result = rm.getPendingRequestsByApprover(approver);
    assertTrue(result.size() > 0);
  }

}
