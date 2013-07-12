package com.sinopec.siam.am.idp.authn.module;

import java.io.IOException;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;

import com.sinopec.siam.am.idp.authn.module.CommonLdapLoginModule.DnAndAttributes;
import com.sinopec.siam.am.idp.authn.principal.UserPrincipal;
import com.sinopec.siam.am.idp.authn.provider.FormOperationCallback;

/**
 * SMS Code ģʽ�������û���¼���Ӷ����Ի�ȡ ldap������Ϣ LoginModule
 * @author zhangxianwen
 * @since 2012-9-13 ����10:24:27
 */

public class LoginStateSetLoginModule extends AbstractSpringLoginModule {
	
	public final static  String LOGIN_AUTH_SMS_OK = "LOGIN_AUTH_SMS_OK";
	
	  private static Log logger = LogFactory.getLog(LoginStateSetLoginModule.class);

	
	  private String ldapTemplateBeanName = "tamLdapTemplate";
	  
	  /**
	   * Filter to find user
	   */
	  private String userFilter = "(&(uid={0})(objectclass=inetOrgPerson))";
	  
	  /**
	   * LDAP baseDN
	   */
	  private String baseDn = "";
	  
	  /**
	   * checkSMS
	   */
	  private boolean checkSMS = false;
	  
	  /** ��¼�����ı�ʶ */
	  private String loginOptFlag = "login";

	  

	  /** {@inheritDoc} */
	  @SuppressWarnings("unchecked")
	  public boolean login() throws LoginException {
		  //if smscode verify ok, do next,or return fail
		  
	    if (callbackHandler == null) {
	      log.error("No CallbackHandler");
	      return false;
	    }
	
	    NameCallback nameCallback = new NameCallback("User Name: ");
	    FormOperationCallback formOperationCallback = new FormOperationCallback(this.loginOptFlag);
	    
	    Callback[] callbacks = new Callback[]{nameCallback, formOperationCallback};
	
	    try {
	      callbackHandler.handle(callbacks);
	    } catch (IOException e) {
	      log.error(e.getMessage(), e);
	      return false;
	    } catch (UnsupportedCallbackException e) {
	      log.error(e.getMessage(), e);
	      return false;
	    }
	    
	    /*
	    String opt = formOperationCallback.getOperation();
	    if (opt != null && !loginOptFlag.equals(opt)) {
	    	return true;
	    }*/
	
	    String username = nameCallback.getName();
	    //String sessionUsername = lastPrincipalCallback.getPrincipalName();

		    if(this.checkSMS) {
			    String hasSMSAuth = (String)this.getSessionLevelState(LoginStateSetLoginModule.LOGIN_AUTH_SMS_OK);
			    if(hasSMSAuth == null || "false".equalsIgnoreCase(hasSMSAuth)){
			      log.warn(String.format("No authentication, session username is null or unequal usernmae. username:%s, sessionUsername:%s", username, username));
			      throw new LoginException(String.format("No authentication, session username is null or unequal usernmae. username:%s, sessionUsername:%s", username, username));
			    }
		    }
	    
	      DnAndAttributes dnAndAttrs = this.searchUserDNByAccount(username);
	      if (dnAndAttrs == null){
	    	  log.warn(String.format("No authentication, username not found. username:%s ", username));
		      throw new LoginException(String.format("No authentication, username not found. username:%s ", username));
	      } else {
		    	//���ù����û���Ϣ
	    	  String loginName = username;
			try {
				loginName = dnAndAttrs.getAttributes().get("uid").get(0).toString();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			sharedState.put(LOGIN_NAME, loginName);

			String loginDn = dnAndAttrs.getDn();
			this.sharedState.put(LOGIN_DN, loginDn);
	      }
	    
	    
	    // �û�У�飬ͨ��LADP��ѯ������Աuid��Ϣ���洢Subject
	    UserPrincipal principal = new UserPrincipal();
	    principal.setName(username);
	
	    principals.add(principal);
	
	    authenticated = true;
	    return true;
	  }
	  
	  /**
	   * @return
	   */
	  private LdapTemplate getLdapTemplate() {
	    return (LdapTemplate) this.applicationContext.getBean(ldapTemplateBeanName, LdapTemplate.class);
	  }
	
	  /**
	   * Get LDAP dn
	   * 
	   * @param userName
	   * @return
	   */
	  private DnAndAttributes searchUserDNByAccount(String userName) {
	    String filter = MessageFormat.format(this.userFilter, userName);
	
	    try {
	      if (logger.isDebugEnabled()) {
	        logger.debug(String.format("Search user DN by filter [%s], base [%s]", filter, baseDn));
	      }
	      SearchControls controls = new SearchControls();
	      controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	      
	      /*controls.setReturningAttributes(this.returnAttributeNames);
	      if (this.subTree) {
	         controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	      } else {
	        controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
	      }*/
	      
	      List<DnAndAttributes> result = (List<DnAndAttributes>)this.getLdapTemplate().search(baseDn, filter, controls, new AbstractContextMapper() {
	
	        @Override
	        protected Object doMapFromContext(DirContextOperations ctx) {
	          String dn = ctx.getNameInNamespace();
	          Attributes attrs = ctx.getAttributes();
	          return new DnAndAttributes(dn, attrs);
	        }
	      });
	      if (result.size() == 1) {
	         return result.get(0);
	      } else {
	        return null;
	      }
	    } catch (EmptyResultDataAccessException e) {
	      if (logger.isDebugEnabled()) {
	        logger.debug(String.format("Search user DN exception", e.getMessage()));
	      }
	      return null;
	    }
	  }
	  
	  /** {@inheritDoc} */
	  public void initialize(final Subject subject, final CallbackHandler callbackHandler, final Map<String, ?> sharedState, final Map<String, ?> options) {

	    super.initialize(subject, callbackHandler, sharedState, options);

	    if (logger.isTraceEnabled()) {
	      logger.trace("Begin initialize");
	    }
	    this.subject = subject;
	    this.callbackHandler = callbackHandler;
	    this.sharedState = sharedState;

	    final Iterator<String> i = options.keySet().iterator();
	    while (i.hasNext()) {
	      final String key = i.next();
	      final String value = (String) options.get(key);
	      if (key.equalsIgnoreCase("userFilter")) {
	        this.userFilter = value;
	      } else if (key.equalsIgnoreCase("ldapTemplateBeanName")) {
	        this.ldapTemplateBeanName = value;
	      } else if (key.equalsIgnoreCase("baseDn")) {
	        this.baseDn = value;
	      } else if (key.equalsIgnoreCase("checkSMS")) {
		        this.checkSMS = Boolean.parseBoolean(value);
		  } else if (key.equalsIgnoreCase("loginOptFlag")) {
			    this.loginOptFlag = value;
		  } 
	    }

	    this.principals = new TreeSet<Principal>();
	  }
  
}
