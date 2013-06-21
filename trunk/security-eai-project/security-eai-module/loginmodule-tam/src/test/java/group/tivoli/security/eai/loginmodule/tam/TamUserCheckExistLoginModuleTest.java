package group.tivoli.security.eai.loginmodule.tam;

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
import javax.security.auth.login.LoginException;

import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoaderListener;

import com.sinopec.siam.am.idp.authn.module.CommonLdapLoginModule;

import edu.vt.middleware.ldap.jaas.LdapPrincipal;

import junit.framework.TestCase;

/**
 * 类（接口）继承、实现、功能等描述
 * @author xuhong
 * @since 2012-11-30 下午5:35:39
 */

public class TamUserCheckExistLoginModuleTest extends TestCase {

  private MockServletContext servletContext;
  private ContextLoaderListener contextLoaderListener;
  
  protected void setUp() throws Exception {
    super.setUp();
    servletContext = new MockServletContext();
    servletContext.addInitParameter("contextConfigLocation", 
        "classpath:conf/config-*.xml,classpath:spring/**/spring-beans-*.xml");
    
    contextLoaderListener = new ContextLoaderListener();
    contextLoaderListener.initWebApplicationContext(servletContext);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    this.contextLoaderListener.closeWebApplicationContext(servletContext);
  }

  public void testLogin() throws LoginException {
    TamUserCheckExistLoginModule lm = new TamUserCheckExistLoginModule();
    lm.setApplicationContext(this.contextLoaderListener.getCurrentWebApplicationContext());
    
    Subject subject = new Subject();
    
    CallbackHandler callbackHandler = new CallbackHandler() {

      public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback cb: callbacks) {
           if (cb instanceof NameCallback) {
              ((NameCallback)cb).setName("sec_master");
           } else if (cb instanceof PasswordCallback) {
             ((PasswordCallback)cb).setPassword("passw0rd".toCharArray());
           }
        }
        
      }
      
    };
    Map<String, String> sharedState = new HashMap<String, String>();
    //sharedState.put("javax.security.auth.login.name", "sec_master");
    sharedState.put("javax.security.auth.login.name", "JPMGR001");
    sharedState.put("javax.security.auth.login.password", "passw0rd");
   // sharedState.put("secDN", "uid=JPMGR001,cn=users,DC=TAM,DC=COM");
    sharedState.put("secDN", "cn=SecurityMaster,secAuthority=Default");



    Map<String, String> options = new HashMap<String, String>();
    options.put("storePass", "true");
    options.put("userFilter", "(&(uid={0})(objectclass=inetOrgPerson))");
    options.put("returnAttributeNames", "cn,sn,telephoneNumber,seeAlso,description,title,x121Address,registeredAddress,destinationIndicator,preferredDeliveryMethod,telexNumber,teletexTerminalIdentifier,internationalISDNNumber,facsimileTelephoneNumber,street,postalAddress,postalCode,postOfficeBox,physicalDeliveryOfficeName,ou,st,l,businessCategory,departmentNumber,employeeNumber,employeeType,givenName,initials,labeledURI,mail,manager,pager,preferredLanguage,roomNumber,secretary,uid,displayName,o,userPKCS12,c,spPinYinName,spPinYinShortName,spIdentityType,spGender,spCountry,spOrgShortName,spOrgNumber,spDeptManagerName,spDeptManagerNumber,spPositionName,spPositionNumber,spPositionStatus,spEmployeeLevel,spTitleNumber,spTitleLevel,spTitleCategory,spPluralityStatus,spPluralityParents,spTransferStatus,spTransferParents,spStartTime,spEndTime,spEntryStatus,spAppendStatus,spUpdateDate,spRoleList,spAllowShow,spParents,ibm-allgroups");
    options.put("baseDn", "cn=users,DC=TAM,DC=COM");


    lm.initialize(subject, callbackHandler, sharedState, options);
    boolean ok = lm.login();
    assertTrue(ok);
    ok = lm.commit();
    assertTrue(ok);
    
    assertEquals("JPMGR001", sharedState.get(CommonLdapLoginModule.LOGIN_NAME));
    
    //assertEquals("uid=JPMGR001,cn=users,DC=TAM,DC=COM", sharedState.get(CommonLdapLoginModule.LOGIN_DN));
    assertNotNull(sharedState.get(CommonLdapLoginModule.LOGIN_PASSWORD));
    
    List<Principal> principals = new ArrayList<Principal>(subject.getPrincipals());
    //assertEquals(1, principals.size());
   // assertTrue(principals.get(0) instanceof LdapPrincipal);
  }

}
