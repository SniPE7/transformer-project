/**
 * 
 */
package com.sinopec.siam.am.idp.authn.module;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import junit.framework.TestCase;

import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoaderListener;

import edu.vt.middleware.ldap.jaas.LdapPrincipal;

/**
 * @author zhaodonglu
 *
 */
public class CommonLdapLoginModuleTest extends TestCase {

  private MockServletContext servletContext;
  private ContextLoaderListener contextLoaderListener;

  protected void setUp() throws Exception {
    super.setUp();
    servletContext = new MockServletContext();
    servletContext.addInitParameter("contextConfigLocation", "classpath*:idp/conf/internal.xml, classpath*:idp/conf/service.xml,classpath*:idp/conf/spring-beans-*.xml");
    contextLoaderListener = new ContextLoaderListener();
    contextLoaderListener.initWebApplicationContext(servletContext);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    this.contextLoaderListener.closeWebApplicationContext(servletContext);
  }

  /**
   * Test method for {@link edu.vt.middleware.ldap.jaas.LdapLoginModule#login()}.
   */
  public void testLoginCaseNoReturnAnyAttributes() throws Exception {
    CommonLdapAuthLoginModule lm = new CommonLdapAuthLoginModule();
    lm.setApplicationContext(this.contextLoaderListener.getCurrentWebApplicationContext());
    Subject subject = new Subject();
    CallbackHandler callbackHandler = new CallbackHandler() {

      public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback cb: callbacks) {
           if (cb instanceof NameCallback) {
              ((NameCallback)cb).setName("fangzy");
           } else if (cb instanceof PasswordCallback) {
             ((PasswordCallback)cb).setPassword("000000".toCharArray());
           }
        }
        
      }
      
    };
    Map<String, String> sharedState = new HashMap<String, String>();
    sharedState.put("javax.security.auth.login.name", "fangzy");
    sharedState.put("javax.security.auth.login.password", "000000");

    Map<String, String> options = new HashMap<String, String>();
    options.put("storePass", "true");
    options.put("userFilter", "(&(uid={0})(objectclass=inetOrgPerson))");

    lm.initialize(subject, callbackHandler, sharedState, options);
    boolean ok = lm.login();
    assertTrue(ok);
    ok = lm.commit();
    assertTrue(ok);
    
    assertEquals("fangzy", sharedState.get(CommonLdapAuthLoginModule.LOGIN_NAME));
    assertEquals("uid=fangzy,cn=users,dc=SINOPEC,dc=COM", sharedState.get(CommonLdapAuthLoginModule.LOGIN_DN));
    assertNotNull(sharedState.get(CommonLdapAuthLoginModule.LOGIN_PASSWORD));
    
    List<Principal> principals = new ArrayList<Principal>(subject.getPrincipals());
    assertEquals(1, principals.size());
    assertTrue(principals.get(0) instanceof LdapPrincipal);
  }

  /**
   * Test method for {@link edu.vt.middleware.ldap.jaas.LdapLoginModule#login()}.
   */
  public void testLoginCaseReturnAllAttributes() throws Exception {
    CommonLdapAuthLoginModule lm = new CommonLdapAuthLoginModule();
    lm.setApplicationContext(this.contextLoaderListener.getCurrentWebApplicationContext());
    Subject subject = new Subject();
    CallbackHandler callbackHandler = new CallbackHandler() {

      public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback cb: callbacks) {
           if (cb instanceof NameCallback) {
              ((NameCallback)cb).setName("fangzy");
           } else if (cb instanceof PasswordCallback) {
             ((PasswordCallback)cb).setPassword("000000".toCharArray());
           }
        }
        
      }
      
    };
    Map<String, String> sharedState = new HashMap<String, String>();
    sharedState.put("javax.security.auth.login.name", "fangzy");
    sharedState.put("javax.security.auth.login.password", "000000");

    Map<String, String> options = new HashMap<String, String>();
    options.put("storePass", "true");
    options.put("userFilter", "(&(uid={0})(objectclass=inetOrgPerson))");
    options.put("returnAttributeNames", "");

    lm.initialize(subject, callbackHandler, sharedState, options);
    boolean ok = lm.login();
    assertTrue(ok);
    ok = lm.commit();
    assertTrue(ok);
    
    assertEquals("fangzy", sharedState.get(CommonLdapAuthLoginModule.LOGIN_NAME));
    assertEquals("uid=fangzy,cn=users,dc=SINOPEC,dc=COM", sharedState.get(CommonLdapAuthLoginModule.LOGIN_DN));
    assertNotNull(sharedState.get(CommonLdapAuthLoginModule.LOGIN_PASSWORD));
    
    List<Principal> principals = new ArrayList<Principal>(subject.getPrincipals());
    assertEquals(1, principals.size());
    assertTrue(principals.get(0) instanceof LdapPrincipal);
  }
}
