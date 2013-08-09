package com.sinopec.siam.am.idp.authn.module;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;

import com.ibm.siam.am.idp.authn.service.MatchCodeService;

/**
 * 针对SGM MatchCode定制的认证模块.处理机制如下： <br/>
 * 1. username中传递的是Card UID, 本认证模块通过MatchCodeService将其转换为MatchCode 2. 以此进行认证,
 * 认证成功后, 更新下相关的Card UID到目录中, 方便后续处理
 * 
 * 
 */
public class SGMMatchCodeAuthLoginModule extends CommonLdapAuthLoginModule {

	private static Log logger = LogFactory.getLog(SGMMatchCodeAuthLoginModule.class);

  /** Constant for login name stored in shared state. */
  public static final String LOGIN_MATCHCODE = "login.match.code";
  
	private String matchCodeServiceBeanName = null;

	public SGMMatchCodeAuthLoginModule() {
		super();
	}

	/**
	 * @return
	 */
	protected MatchCodeService getMatchCodeService() {
		return (MatchCodeService) this.applicationContext.getBean(matchCodeServiceBeanName, MatchCodeService.class);
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);

		if (logger.isTraceEnabled()) {
			logger.trace("Begin initialize");
		}
		final Iterator<String> i = options.keySet().iterator();
		while (i.hasNext()) {
			final String key = i.next();
			final String value = (String) options.get(key);
			if (key.equalsIgnoreCase("matchCodeServiceBeanName")) {
				this.matchCodeServiceBeanName = value;
			}
		}
	}

	@Override
  public boolean login() throws LoginException {
	  boolean success = super.login();
	  // Success, update CardUID into User entry
	  return success;
  }

	@Override
	protected DnAndAttributes searchUserDNByUsername(String userName) {
		String cardMatchCode = null;
		try {
			String cardUID = userName;
			MatchCodeService matchCodeService = this.getMatchCodeService();
			cardMatchCode = matchCodeService.getMatchCode(cardUID);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Search user DN exception", e.getMessage()));
			}
			return null;
		}
		try {
			String filter = MessageFormat.format(this.userFilter, cardMatchCode);

			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Search user DN by filter [%s], base [%s]", filter, baseDn));
			}
			SearchControls controls = new SearchControls();
			controls.setReturningAttributes(this.returnAttributeNames);
			if (this.subTree) {
				controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			} else {
				controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			}
			List<DnAndAttributes> result = (List<DnAndAttributes>) this.getLdapTemplate().search(baseDn, filter, controls, new AbstractContextMapper() {

				@Override
				protected Object doMapFromContext(DirContextOperations ctx) {
					String dn = ctx.getNameInNamespace();
					Attributes attrs = ctx.getAttributes();
					return new DnAndAttributes(dn, attrs);
				}
			});
			if (result.size() == 1) {
			  // Update shareState for LoginStateSetLoginModule
			  this.sharedState.put(LOGIN_MATCHCODE, cardMatchCode);
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

}
