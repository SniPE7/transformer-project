package com.ibm.siam.am.idp.authn.module.tamldap;

import java.util.Date;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;

import com.ibm.siam.am.idp.authn.LoginContextManager;
import com.ibm.siam.am.idp.authn.provider.tamldap.TAMCallbackHandler;

/**
 * TAM Ldap User Pass LoginModule ≤‚ ‘”√¿˝
 * @author zhangxianwen
 * @since 2012-6-15 …œŒÁ11:40:43
 */

public class TamLdapUserPassLoginModuleTest extends TestCase {

  private ApplicationContext applicationContext;
  
  public TamLdapUserPassLoginModuleTest(String name) {
    super(name);
  }

  public TamLdapUserPassLoginModuleTest() {
    super();
  }

  public void setUp() throws Exception {
    super.setUp();
 // Load Spring
    String[] config = new String[]{"classpath*:idp/conf/internal.xml",
        "classpath*:idp/conf/service.xml",
        "classpath*:idp/conf/spring-beans*.xml"};
    applicationContext = new ClassPathXmlApplicationContext(config);
  }

  public void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for
   * {@link com.ibm.siam.am.idp.authn.module.AbstractLoginModule#commit()}.
   */
  public void testSuccess() throws Exception {

    MockHttpServletRequest httpRequest = new MockHttpServletRequest();
    
    Subject subject = new Subject();
    
    TAMCallbackHandler cbh = new TAMCallbackHandler("fangzy", "000000", httpRequest );
    // Get Spring Bean Factory
    LoginContextManager loginContextManager = applicationContext.getBean("loginContextManager", LoginContextManager.class);
    LoginContext jaasLoginCtx = loginContextManager.getLoginContext("TamLdapUserPassAuth", subject, cbh);
    try {
      jaasLoginCtx.login();
    } catch (LoginException e) {
      fail("testSuccess failed:"+e.getMessage());
    }
  }
  
  public void test100Success() throws Exception {
    int total = 1000;
    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < total; i++) {
        System.out.println(String.format("[%s]:[%s]", new Date(), i));
        this.testSuccess();
    }
    long endTime = System.currentTimeMillis();
    System.out.println(String.format("Total: %s, Avg: %s", (endTime - beginTime), ((endTime - beginTime)/total)));
  }

  public void testFailure() throws Exception {
    MockHttpServletRequest httpRequest = new MockHttpServletRequest();

    Subject subject = new Subject();
    TAMCallbackHandler cbh = new TAMCallbackHandler("fangzy", "000000", httpRequest);
    // Get Spring Bean Factory
    LoginContextManager loginContextManager = applicationContext.getBean("loginContextManager",
        LoginContextManager.class);
    LoginContext jaasLoginCtx = loginContextManager.getLoginContext("TamLdapUserPassAuth", subject, cbh);
    try {
      jaasLoginCtx.login();
      fail("testSuccess failed!");
    } catch (LoginException e) {
      assertEquals("checkCode check failed,checkCode:checkcode", e.getMessage());
    }
  }

}
