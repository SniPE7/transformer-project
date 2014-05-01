/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.siam.am.idp.authn;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.security.auth.Subject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sinopec.siam.am.idp.authn.LoginHandlerManager;
import com.sinopec.siam.am.idp.authn.module.AbstractSpringLoginModule;
import com.sinopec.siam.am.idp.authn.util.AuthencationUtil;

import edu.internet2.middleware.shibboleth.common.profile.AbstractErrorHandler;
import edu.internet2.middleware.shibboleth.common.profile.NoProfileHandlerException;
import edu.internet2.middleware.shibboleth.common.profile.ProfileException;
import edu.internet2.middleware.shibboleth.common.util.HttpHelper;
import edu.internet2.middleware.shibboleth.idp.authn.AuthenticationException;
import edu.internet2.middleware.shibboleth.idp.authn.ForceAuthenticationException;
import edu.internet2.middleware.shibboleth.idp.authn.LoginContext;
import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import edu.internet2.middleware.shibboleth.idp.authn.UsernamePrincipal;
import edu.internet2.middleware.shibboleth.idp.session.AuthenticationMethodInformation;
import edu.internet2.middleware.shibboleth.idp.session.ServiceInformation;
import edu.internet2.middleware.shibboleth.idp.session.Session;
import edu.internet2.middleware.shibboleth.idp.session.impl.AuthenticationMethodInformationImpl;
import edu.internet2.middleware.shibboleth.idp.session.impl.ServiceInformationImpl;
import edu.internet2.middleware.shibboleth.idp.session.impl.SessionImpl;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;
import edu.vt.middleware.ldap.bean.LdapAttribute;
import edu.vt.middleware.ldap.jaas.LdapPrincipal;

/** Manager responsible for handling authentication requests. */
public class AuthenticationEngine extends HttpServlet {

	/** Class logger. */
	private final Logger log = LoggerFactory.getLogger(AuthenticationEngine.class);
	/**
	 * Name of the Servlet config init parameter that indicates whether the public
	 * credentials of a {@link Subject} are retained after authentication.
	 */
	public static final String RETAIN_PUBLIC_CREDENTIALS = "retainSubjectsPublicCredentials";

	/**
	 * Name of the Servlet config init parameter that indicates whether the
	 * private credentials of a {@link Subject} are retained after authentication.
	 */
	public static final String RETAIN_PRIVATE_CREDENTIALS = "retainSubjectsPrivateCredentials";

	/**
	 * Name of the Servlet config init parameter that holds the partition name for
	 * login contexts.
	 */
	public static final String LOGIN_CONTEXT_PARTITION_NAME_INIT_PARAM_NAME = "loginContextPartitionName";

	/**
	 * Name of the Servlet config init parameter that holds lifetime of a login
	 * context in the storage service.
	 */
	public static final String LOGIN_CONTEXT_LIFETIME_INIT_PARAM_NAME = "loginContextEntryLifetime";

	/** Name of the IdP Cookie containing the IdP session ID. */
	public static final String IDP_SESSION_COOKIE_NAME = "_idp_session";

	/**
	 * Name of the key under which to bind the storage service key for a login
	 * context.
	 */
	public static final String LOGIN_CONTEXT_KEY_NAME = "_idp_authn_lc_key";

	/** Name of SAML IdP Cookie contain this IdP entity ID. */
	public static final String SAML_IDP_COOKIE_NAME = "_saml_idp";

	/** Serial version UID. */
	private static final long serialVersionUID = -8479060989001890156L;

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationEngine.class);

	public static final String PREVIOUS_SESSION_AUTHN_CTX = "PREVIOUS_SESSION_AUTHN_CTX";

	// TODO remove once HttpServletHelper does redirects
	private ServletContext context;

	/**
	 * Whether the public credentials of a {@link Subject} are retained after
	 * authentication.
	 */
	private boolean retainSubjectsPublicCredentials;
	
	/**
	 * Whether the private credentials of a {@link Subject} are retained after
	 * authentication.
	 */
	private boolean retainSubjectsPrivateCredentials;

	private LoginHandlerManager loginHandlerManager;

	/** {@inheritDoc} */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		String retain = config.getInitParameter(RETAIN_PRIVATE_CREDENTIALS);
		if (retain != null) {
			retainSubjectsPrivateCredentials = Boolean.parseBoolean(retain);
		} else {
			retainSubjectsPrivateCredentials = false;
		}

		retain = config.getInitParameter(RETAIN_PUBLIC_CREDENTIALS);
		if (retain != null) {
			retainSubjectsPublicCredentials = Boolean.parseBoolean(retain);
		} else {
			retainSubjectsPublicCredentials = false;
		}
		context = config.getServletContext();
		
		// Get Spring Bean Factory
		ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
		this.loginHandlerManager = appContext.getBean("loginHandlerManager", LoginHandlerManager.class);
	}

	/** {@inheritDoc} */
	protected void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
		LOG.debug("Processing incoming request");

		if (httpResponse.isCommitted()) {
			LOG.error("HTTP Response already committed");
		}

		LoginContext loginContext = HttpServletHelper.getLoginContext(getServletContext(), httpRequest);
		if (loginContext == null) {
			String msg = "No login context available, unable to proceed with authentication!";
			LOG.error(msg);
			ProfileException profileException = new ProfileException(msg);
			httpRequest.setAttribute(AbstractErrorHandler.ERROR_KEY, profileException);

			//forwardRequest("/error.do", httpRequest, httpResponse);
			
			//forwardRequest("/SSOLogout", httpRequest, httpResponse);
	        HttpSession session = httpRequest.getSession(false);
			if(session!=null) {
		         String appUrl = (String)session.getAttribute(AccessEnforcer.SESSION_ATTR_NAME_EAI_RETURN_URL);
		         httpResponse.sendRedirect(appUrl);
			}
			
			return;
		}

		List<String> requestedMethods = new ArrayList<String>();
		String currentAuthentication = null;
		// SP选择认证方式后，用户在IDP的登录界面重新选择的认证方式
		if (httpRequest.getParameter("currentAuthentication") != null) {
			currentAuthentication = httpRequest.getParameter("currentAuthentication");
			currentAuthentication = currentAuthentication.replace("_", ":");
			loginContext.setAttemptedAuthnMethod(currentAuthentication);
			requestedMethods.add(currentAuthentication);
			loginContext.getRequestedAuthenticationMethods().clear();
			loginContext.getRequestedAuthenticationMethods().addAll(requestedMethods);
			startUserAuthentication(loginContext, httpRequest, httpResponse);
		} else {
			if (!loginContext.getAuthenticationAttempted()) {
				startUserAuthentication(loginContext, httpRequest, httpResponse);
			} else {
				completeAuthentication(loginContext, httpRequest, httpResponse);
			}
		}
	}

	/**
	 * From LoginHandler returns control back to the authentication engine.
	 * 
	 * @param httpRequest
	 *          current HTTP request
	 * @param httpResponse
	 *          current HTTP response
	 * @throws IOException 
	 */
	public static void returnToAuthenticationEngine(ServletContext context, HttpServletRequest httpRequest, HttpServletResponse httpResponse, String authenticationMethod) {
		LOG.debug("Returning control to authentication engine");
		LoginContext loginContext = HttpServletHelper.getLoginContext(context, httpRequest);
		if (loginContext == null) {
		    // 登录成功后，缺少由AccessEnforcer设置在Session中的LoginContext, 重建一个新的LoginContext
            LOG.warn(String.format("No login context available, re-create default LoginContext before return to authentication engine!"));
            // 获取当前处理对应的认证方法名称
//            String requiredAuthenticationMethod = null;
//            ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
//            LoginHandlerManager loginHandlerManager = applicationContext.getBean("loginHandlerManager", LoginHandlerManager.class);
//            String currentServletURI = httpRequest.getServletPath();
//            for (LoginHandler loginHandler: loginHandlerManager.getLoginHandlers().values()) {
//              if (loginHandler instanceof AbstractLoginHandler) {
//                 if (currentServletURI.equals(((AbstractLoginHandler)loginHandler).getAuthenticationServletPath())) {
//                   requiredAuthenticationMethod = loginHandler.getSupportedAuthenticationMethods().get(0);
//                 }
//              }
//            }
//            if (requiredAuthenticationMethod == null) {
//               // 不期望运行到此段处理逻辑, 前面应该总是能根据URL找到当前所做的认证方法
//               requiredAuthenticationMethod = "urn:oasis:names:tc:SAML:2.0:ac:classes:TAMUsernamePassword";
//            }
            String authnEngineUrl = HttpServletHelper.getContextRelativeUrl(httpRequest, "/AuthnEngine").buildURL();
		    boolean alwaysReauthen = false;
            loginContext = new LoginContext(alwaysReauthen , false);
            loginContext.setAuthenticationAttempted();
		    loginContext.setAuthenticationEngineURL(authnEngineUrl);
		    loginContext.setAccessEnforcerURL("/login/info.do");
            loginContext.setDefaultAuthenticationMethod(authenticationMethod);
            
            loginContext.setAttemptedAuthnMethod(authenticationMethod);
            List<String> requestedMethods = new ArrayList<String>();
            requestedMethods.add(authenticationMethod);
            loginContext.getRequestedAuthenticationMethods().clear();
            loginContext.getRequestedAuthenticationMethods().addAll(requestedMethods);
            
		    HttpServletHelper.bindLoginContext(loginContext, httpRequest.getSession().getServletContext(), httpRequest, httpResponse);
		}
		forwardRequest("/AuthnEngine", httpRequest, httpResponse);
	}

	/**
	 * Returns control back to the profile handler that invoked the authentication
	 * engine.
	 * 
	 * @param httpRequest
	 *          current HTTP request
	 * @param httpResponse
	 *          current HTTP response
	 * @throws IOException 
	 */
	private void returnToAccessEnforcer(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		LOG.debug("Returning control to profile handler");
		LoginContext loginContext = HttpServletHelper.getLoginContext(context, httpRequest);
		if (loginContext == null) {
			String msg = "No login context available, unable to return to profile handler!";
			LOG.error(msg);
			ProfileException profileException = new ProfileException(msg);
			httpRequest.setAttribute(AbstractErrorHandler.ERROR_KEY, profileException);
			//forwardRequest("/error.do", httpRequest, httpResponse);
			//forwardRequest("/SSOLogout", httpRequest, httpResponse);
			HttpSession session = httpRequest.getSession(false);
            if(session!=null) {
                 String appUrl = (String)session.getAttribute(AccessEnforcer.SESSION_ATTR_NAME_EAI_RETURN_URL);
                 httpResponse.sendRedirect(appUrl);
            }
			
			return;
		}

		if (loginContext.getAccessEnforcerURL() == null) {
			String msg = "Login context did not contain a profile handler path, unable to return to profile handler!";
			LOG.error(msg);
			NoProfileHandlerException noProfileException = new NoProfileHandlerException(msg);
			httpRequest.setAttribute(AbstractErrorHandler.ERROR_KEY, noProfileException);
			forwardRequest("/error.do", httpRequest, httpResponse);
			return;
		} else {
			httpResponse.sendRedirect(httpRequest.getContextPath() + loginContext.getAccessEnforcerURL());
			LOG.info("returnToAccessEnforcer:" + httpRequest.getContextPath() + loginContext.getAccessEnforcerURL());
			return;
		}

	}

	/**
	 * Forwards a request to the given path.
	 * 
	 * @param forwardPath
	 *          path to forward the request to
	 * @param httpRequest
	 *          current HTTP request
	 * @param httpResponse
	 *          current HTTP response
	 */
	protected static void forwardRequest(String forwardPath, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		try {
			RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(forwardPath);
			dispatcher.forward(httpRequest, httpResponse);
			return;
		} catch (IOException e) {
			LOG.error("Unable to return control back to authentication engine", e);
		} catch (ServletException e) {
			LOG.error("Unable to return control back to authentication engine", e);
		}
	}

	/**
	 * Begins the authentication process. Determines if forced re-authentication
	 * is required or if an existing, active, authentication method is sufficient.
	 * Also determines, when authentication is required, which handler to use
	 * depending on whether passive authentication is required.
	 * 
	 * @param loginContext
	 *          current login context
	 * @param httpRequest
	 *          current HTTP request
	 * @param httpResponse
	 *          current HTTP response
	 * @throws IOException 
	 */
	protected void startUserAuthentication(LoginContext loginContext, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		LOG.debug("Beginning user authentication process.");
		try {
			Session idpSession = (Session) httpRequest.getAttribute(Session.HTTP_SESSION_BINDING_ATTRIBUTE);
			if (idpSession != null) {
				LOG.debug("Existing IdP session available for principal {}", idpSession.getPrincipalName());
			}

			Map<String, LoginHandler> possibleLoginHandlers = determinePossibleLoginHandlers(idpSession, loginContext);

			// Filter out possible candidate login handlers by forced and passive
			// authentication requirements
			if (loginContext.isForceAuthRequired()) {
				filterByForceAuthentication(idpSession, loginContext, possibleLoginHandlers);
			}

			if (loginContext.isPassiveAuthRequired()) {
				throw new RuntimeException("Unsupport passive auth.");
			}

			LoginHandler loginHandler = selectLoginHandler(possibleLoginHandlers, loginContext, idpSession);
			loginContext.setAuthenticationAttempted();
			loginContext.setAuthenticationEngineURL(HttpHelper.getRequestUriWithoutContext(httpRequest));

			String spAuthentication = "";// SP选择的认证方式
			if (httpRequest.getParameter("spAuthentication") != null) {
				spAuthentication = httpRequest.getParameter("spAuthentication");
				spAuthentication = spAuthentication.replace("_", ":");
			} else {
				spAuthentication = loginContext.getAttemptedAuthnMethod();
			}

			String currentAuthen = "";// SP选择认证方式后，用户在IDP的登录界面重新选择的认证方式
			if (httpRequest.getParameter("currentAuthen") != null) {
				currentAuthen = httpRequest.getParameter("currentAuthen");
			} else {
				currentAuthen = loginContext.getAttemptedAuthnMethod();
				currentAuthen = currentAuthen.replace(":", "_");
			}
			httpRequest.setAttribute("currentAuthen", currentAuthen);

			// 构造认证方式列表
			String authenTypes = AuthencationUtil.getAuthenType(spAuthentication, this.loginHandlerManager.getLoginHandlers());
			httpRequest.setAttribute("authenTypes", authenTypes);

			spAuthentication = spAuthentication.replace(":", "_");
			httpRequest.setAttribute("spAuthentication", spAuthentication);

			// Send the request to the login handler
			HttpServletHelper.bindLoginContext(loginContext, getServletContext(), httpRequest, httpResponse);
			// Validate session state
			loginHandler.login(this.getServletContext(), httpRequest, httpResponse);
		} catch (AuthenticationException e) {
			loginContext.setAuthenticationFailure(e);
			returnToAccessEnforcer(httpRequest, httpResponse);
		}
	}

	/**
	 * Determines which configured login handlers will support the requested
	 * authentication methods.
	 * 
	 * @param loginContext
	 *          current login context
	 * @param idpSession
	 *          current user's session, or null if they don't have one
	 * 
	 * @return login methods that may be used to authenticate the user
	 * 
	 * @throws AuthenticationException
	 *           thrown if no login handler meets the given requirements
	 */
	protected Map<String, LoginHandler> determinePossibleLoginHandlers(Session idpSession, LoginContext loginContext) throws AuthenticationException {
		Map<String, LoginHandler> supportedLoginHandlers = new HashMap<String, LoginHandler>(this.loginHandlerManager.getLoginHandlers());
		LOG.debug("Filtering configured LoginHandlers: {}", supportedLoginHandlers);

		// First, if the service provider requested a particular authentication
		// method, filter out everything but
		List<String> requestedMethods = loginContext.getRequestedAuthenticationMethods();
		if (requestedMethods != null && !requestedMethods.isEmpty()) {
			LOG.debug("Filtering possible login handlers by requested authentication methods: {}", requestedMethods);
			Iterator<Entry<String, LoginHandler>> supportedLoginHandlerItr = supportedLoginHandlers.entrySet().iterator();
			Entry<String, LoginHandler> supportedLoginHandlerEntry;
			while (supportedLoginHandlerItr.hasNext()) {
				supportedLoginHandlerEntry = supportedLoginHandlerItr.next();
				if (!supportedLoginHandlerEntry.getKey().equals(PREVIOUS_SESSION_AUTHN_CTX) && !requestedMethods.contains(supportedLoginHandlerEntry.getKey())) {
					LOG.debug("Filtering out login handler for authentication {}, it does not provide a requested authentication method", supportedLoginHandlerEntry.getKey());
					supportedLoginHandlerItr.remove();
				}
			}
		}

		if (supportedLoginHandlers.isEmpty()) {
			LOG.warn("No authentication method, requested by the service provider, is supported");
			throw new AuthenticationException("No authentication method, requested by the service provider, is supported");
		}

		return supportedLoginHandlers;
	}

	/**
	 * Filters out any login handler based on the requirement for forced
	 * authentication.
	 * 
	 * During forced authentication any handler that has not previously been used
	 * to authenticate the user or any handlers that have been and support force
	 * re-authentication may be used. Filter out any of the other ones.
	 * 
	 * @param idpSession
	 *          user's current IdP session
	 * @param loginContext
	 *          current login context
	 * @param loginHandlers
	 *          login handlers to filter
	 * 
	 * @throws ForceAuthenticationException
	 *           thrown if no handlers remain after filtering
	 */
	protected void filterByForceAuthentication(Session idpSession, LoginContext loginContext, Map<String, LoginHandler> loginHandlers) throws ForceAuthenticationException {
		LOG.debug("Forced authentication is required, filtering possible login handlers accordingly");

		ArrayList<AuthenticationMethodInformation> activeMethods = new ArrayList<AuthenticationMethodInformation>();
		if (idpSession != null) {
			activeMethods.addAll(Arrays.asList(idpSession.getAuthenticationMethods()));
		}

		loginHandlers.remove(PREVIOUS_SESSION_AUTHN_CTX);

		LoginHandler loginHandler;
		for (AuthenticationMethodInformation activeMethod : activeMethods) {
			loginHandler = loginHandlers.get(activeMethod.getAuthenticationMethod());
			if (loginHandler != null && !loginHandler.supportsForceAuthentication()) {
				for (String handlerSupportedMethods : loginHandler.getSupportedAuthenticationMethods()) {
					LOG.debug("Removing LoginHandler {}, it does not support forced re-authentication", loginHandler.getClass().getName());
					loginHandlers.remove(handlerSupportedMethods);
				}
			}
		}

		LOG.debug("Authentication handlers remaining after forced authentication requirement filtering: {}", loginHandlers);

		if (loginHandlers.isEmpty()) {
			LOG.info("Force authentication requested but no login handlers available to support it");
			throw new ForceAuthenticationException();
		}
	}

	/**
	 * Selects a login handler from a list of possible login handlers that could
	 * be used for the request.
	 * 
	 * @param possibleLoginHandlers
	 *          list of possible login handlers that could be used for the request
	 * @param loginContext
	 *          current login context
	 * @param idpSession
	 *          current IdP session, if one exists
	 * 
	 * @return the login handler to use for this request
	 * 
	 * @throws AuthenticationException
	 *           thrown if no handler can be used for this request
	 */
	protected LoginHandler selectLoginHandler(Map<String, LoginHandler> possibleLoginHandlers, LoginContext loginContext, Session idpSession) throws AuthenticationException {
		LOG.debug("Selecting appropriate login handler from filtered set {}", possibleLoginHandlers);
		LoginHandler loginHandler;
		if (idpSession != null && possibleLoginHandlers.containsKey(PREVIOUS_SESSION_AUTHN_CTX)) {
			LOG.debug("Authenticating user with previous session LoginHandler");
			loginHandler = possibleLoginHandlers.get(PREVIOUS_SESSION_AUTHN_CTX);

			for (AuthenticationMethodInformation authnMethod : idpSession.getAuthenticationMethods()) {
				if (authnMethod.isExpired()) {
					continue;
				}

				if (loginContext.getRequestedAuthenticationMethods().isEmpty() || loginContext.getRequestedAuthenticationMethods().contains(authnMethod.getAuthenticationMethod())) {
					LOG.debug("Basing previous session authentication on active authentication method {}", authnMethod.getAuthenticationMethod());
					loginContext.setAttemptedAuthnMethod(authnMethod.getAuthenticationMethod());
					loginContext.setAuthenticationMethodInformation(authnMethod);
					return loginHandler;
				}
			}
		}

		if (loginContext.getDefaultAuthenticationMethod() != null && possibleLoginHandlers.containsKey(loginContext.getDefaultAuthenticationMethod())) {
			loginHandler = possibleLoginHandlers.get(loginContext.getDefaultAuthenticationMethod());
			loginContext.setAttemptedAuthnMethod(loginContext.getDefaultAuthenticationMethod());
		} else {
			Entry<String, LoginHandler> chosenLoginHandler = possibleLoginHandlers.entrySet().iterator().next();
			loginContext.setAttemptedAuthnMethod(chosenLoginHandler.getKey());
			loginHandler = chosenLoginHandler.getValue();
		}

		LOG.debug("Authenticating user with login handler of type {}", loginHandler.getClass().getName());
		return loginHandler;
	}

	/**
	 * Completes the authentication process.
	 * 
	 * The principal name set by the authentication handler is retrieved and
	 * pushed in to the login context, a Shibboleth session is created if needed,
	 * information indicating that the user has logged into the service is
	 * recorded and finally control is returned back to the profile handler.
	 * 
	 * @param loginContext
	 *          current login context
	 * @param httpRequest
	 *          current HTTP request
	 * @param httpResponse
	 *          current HTTP response
	 * @throws IOException 
	 */
	protected void completeAuthentication(LoginContext loginContext, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		LOG.debug("Completing user authentication process");

		Session idpSession = (Session) httpRequest.getAttribute(Session.HTTP_SESSION_BINDING_ATTRIBUTE);

		try {
			// We allow a login handler to override the authentication method in the
			// event that it supports multiple methods
			String actualAuthnMethod = (String) httpRequest.getAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY);
			if (actualAuthnMethod != null) {
				if (!loginContext.getRequestedAuthenticationMethods().isEmpty() && !loginContext.getRequestedAuthenticationMethods().contains(actualAuthnMethod)) {
					String msg = "Relying patry required an authentication method of " + loginContext.getRequestedAuthenticationMethods() + " but the login handler performed "
					    + actualAuthnMethod;
					LOG.error(msg);
					throw new AuthenticationException(msg);
				}
			} else {
				actualAuthnMethod = loginContext.getAttemptedAuthnMethod();
			}

			// Check to make sure the login handler did the right thing
			validateSuccessfulAuthentication(loginContext, httpRequest, actualAuthnMethod);
			if (loginContext.getAuthenticationFailure() != null) {
				returnToAccessEnforcer(httpRequest, httpResponse);
				return;
			}

			// Check for an overridden authn instant.
			DateTime actualAuthnInstant = (DateTime) httpRequest.getAttribute(LoginHandler.AUTHENTICATION_INSTANT_KEY);

			// Get the Subject from the request. If force authentication was required
			// then make sure the
			// Subject identifies the same user that authenticated before
			Subject subject = getLoginHandlerSubject(httpRequest);
			if (loginContext.isForceAuthRequired()) {
				validateForcedReauthentication(idpSession, actualAuthnMethod, subject);

				// Reset the authn instant.
				if (actualAuthnInstant == null) {
					actualAuthnInstant = new DateTime();
				}
			}

			updateUserSession(loginContext, subject, actualAuthnMethod, actualAuthnInstant, httpRequest, httpResponse);
			updateToPrincipal(httpRequest, subject, actualAuthnMethod);
			LOG.debug("User {} authenticated with method {}", loginContext.getPrincipalName(), loginContext.getAuthenticationMethod());
		} catch (AuthenticationException e) {
			LOG.error("Authentication failed with the error:", e);
			loginContext.setAuthenticationFailure(e);
		}

		returnToAccessEnforcer(httpRequest, httpResponse);
	}

	/**
	 * Validates that the authentication was successfully performed by the login
	 * handler. An authentication is considered successful if no error is bound to
	 * the request attribute {@link LoginHandler#AUTHENTICATION_ERROR_KEY} and
	 * there is a value for at least one of the following request attributes:
	 * {@link LoginHandler#SUBJECT_KEY}, {@link LoginHandler#PRINCIPAL_KEY}, or
	 * {@link AbstractSpringLoginModule#PRINCIPAL_NAME_KEY}.
	 * 
	 * @param loginContext
	 *          current login context
	 * @param httpRequest
	 *          current HTTP request
	 * @param authenticationMethod
	 *          the authentication method used to authenticate the user
	 * 
	 * @throws AuthenticationException
	 *           thrown if the authentication was not successful
	 */
	protected void validateSuccessfulAuthentication(LoginContext loginContext, HttpServletRequest httpRequest, String authenticationMethod) throws AuthenticationException {
		LOG.debug("Validating authentication was performed successfully");

		if (authenticationMethod == null) {
			LOG.error("No authentication method reported by login handler.");
			throw new AuthenticationException("No authentication method reported by login handler.");
		}

		String errorMessage = (String) httpRequest.getAttribute(LoginHandler.AUTHENTICATION_ERROR_KEY);
		if (errorMessage != null) {
			LOG.debug("Error returned from login handler for authentication method {}:\n{}", loginContext.getAttemptedAuthnMethod(), errorMessage);
			loginContext.setAuthenticationFailure(new AuthenticationException(errorMessage));
			return;
		}

		AuthenticationException authnException = (AuthenticationException) httpRequest.getAttribute(LoginHandler.AUTHENTICATION_EXCEPTION_KEY);
		if (authnException != null) {
			LOG.debug("Exception returned from login handler for authentication method {}:\n{}", loginContext.getAttemptedAuthnMethod(), authnException);
			loginContext.setAuthenticationFailure(authnException);
			return;
		}

		Subject subject = (Subject) httpRequest.getAttribute(LoginHandler.SUBJECT_KEY);
		Principal principal = (Principal) httpRequest.getAttribute(LoginHandler.PRINCIPAL_KEY);
		String principalName = (String) httpRequest.getAttribute(AbstractSpringLoginModule.PRINCIPAL_NAME_KEY);

		if (subject == null && principal == null && principalName == null) {
			LOG.error("No user identified by login handler.");
			throw new AuthenticationException("No user identified by login handler.");
		}
	}

	/**
	 * Gets the subject from the request coming back from the login handler.
	 * 
	 * @param httpRequest
	 *          request coming back from the login handler
	 * 
	 * @return the {@link Subject} created from the request
	 * 
	 * @throws AuthenticationException
	 *           thrown if no subject can be retrieved from the request
	 */
	protected Subject getLoginHandlerSubject(HttpServletRequest httpRequest) throws AuthenticationException {
		Subject subject = (Subject) httpRequest.getAttribute(LoginHandler.SUBJECT_KEY);
		Principal principal = (Principal) httpRequest.getAttribute(LoginHandler.PRINCIPAL_KEY);
		String principalName = (String) httpRequest.getAttribute(AbstractSpringLoginModule.PRINCIPAL_NAME_KEY);

		if (subject == null && (principal != null || principalName != null)) {
			subject = new Subject();
			if (principal == null) {
				principal = new UsernamePrincipal(principalName);
			}
			subject.getPrincipals().add(principal);
		}

		return subject;
	}

	/**
	 * If forced authentication was required this method checks to ensure that the
	 * re-authenticated subject contains a principal name that is equal to the
	 * principal name associated with the authentication method. If this is the
	 * first time the subject has authenticated with this method than this check
	 * always passes.
	 * 
	 * @param idpSession
	 *          user's IdP session
	 * @param authnMethod
	 *          method used to authenticate the user
	 * @param subject
	 *          subject that was authenticated
	 * 
	 * @throws AuthenticationException
	 *           thrown if this check fails
	 */
	protected void validateForcedReauthentication(Session idpSession, String authnMethod, Subject subject) throws AuthenticationException {
		if (idpSession != null) {
			AuthenticationMethodInformation authnMethodInfo = idpSession.getAuthenticationMethodByName(authnMethod);
			if (authnMethodInfo != null) {
				boolean princpalMatch = false;
				for (Principal princpal : subject.getPrincipals()) {
					if (authnMethodInfo.getAuthenticationPrincipal().equals(princpal)) {
						princpalMatch = true;
						break;
					}
				}

				if (!princpalMatch) {
					throw new ForceAuthenticationException("Authenticated principal does not match previously authenticated principal");
				}
			}
		}
	}

	/**
	 * Updates the user's Shibboleth session with authentication information. If
	 * no session exists a new one will be created.
	 * 
	 * @param loginContext
	 *          current login context
	 * @param authenticationSubject
	 *          subject created from the authentication method
	 * @param authenticationMethod
	 *          the method used to authenticate the subject
	 * @param authenticationInstant
	 *          the time of authentication
	 * @param httpRequest
	 *          current HTTP request
	 * @param httpResponse
	 *          current HTTP response
	 */
	protected void updateUserSession(LoginContext loginContext, Subject authenticationSubject, String authenticationMethod, DateTime authenticationInstant,
	    HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

		UsernamePrincipal authenticationPrincipal = (UsernamePrincipal) authenticationSubject.getPrincipals(UsernamePrincipal.class).iterator().next();
		LOG.debug("Updating session information for principal {}", authenticationPrincipal.getName());

		HttpSession session = httpRequest.getSession(true);
		Session userSession = (Session) session.getAttribute(Session.HTTP_SESSION_BINDING_ATTRIBUTE);
		if (userSession == null) {
			LOG.debug("Creating shibboleth session for principal {}", authenticationPrincipal.getName());
			
			userSession = new SessionImpl();
			
			//userSession = (Session) sessionManager.createSession();
			session.setAttribute(Session.HTTP_SESSION_BINDING_ATTRIBUTE, userSession);
			
		}

		// Merge the information in the current session subject with the information
		// from the
		// login handler subject
		userSession.setSubject(mergeSubjects(userSession.getSubject(), authenticationSubject));

		// Check if an existing authentication method with no updated timestamp was
		// used (i.e. SSO occurred);
		// if not record the new information
		AuthenticationMethodInformation authnMethodInfo = userSession.getAuthenticationMethodByName(authenticationMethod);
		if (authnMethodInfo == null || authenticationInstant != null) {
			LOG.debug("Recording authentication and service information in session for principal: {}", authenticationPrincipal.getName());
			LoginHandler loginHandler = this.loginHandlerManager.getLoginHandlers().get(loginContext.getAttemptedAuthnMethod());
			DateTime authnInstant = authenticationInstant;
			if (authnInstant == null) {
				authnInstant = new DateTime();
			}
			authnMethodInfo = new AuthenticationMethodInformationImpl(userSession.getSubject(), authenticationPrincipal, authenticationMethod, new DateTime(),
			    loginHandler.getAuthenticationDuration());
		}

		loginContext.setAuthenticationMethodInformation(authnMethodInfo);
		userSession.addAuthenticationMethod(authnMethodInfo);

		ServiceInformation serviceInfo = new ServiceInformationImpl(null, new DateTime(), authnMethodInfo);
		userSession.addServiceInformation(serviceInfo);

	}
	
  /**
   * Decode assertions into principal.
   * @param assertions
   * @param principal
   */
  protected void updateToPrincipal(HttpServletRequest httpRequest, Subject authenticationSubject, String authenticationMethod) {
    ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.context);
    AuthenLevelDirector authenLevelDirector = (AuthenLevelDirector)applicationContext.getBean("authenLevelDirector", AuthenLevelDirector.class);

    HttpSession session = httpRequest.getSession(true);
    SSOPrincipalImpl principal = null;
    if (session != null && session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) != null) {
      principal = (SSOPrincipalImpl) session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR);
    } else {
      principal = new SSOPrincipalImpl();
    }

    principal.addSuccessAuthenMethod(authenticationMethod);
    principal.addSuccessAuthenLevel(authenLevelDirector.getAuthenLevel(authenticationMethod));
    
    for (LdapPrincipal lp: authenticationSubject.getPrincipals(LdapPrincipal.class)) {
      principal.setUid(lp.getName());
      principal.setCn(lp.getName());
      
      for(LdapAttribute attr : lp.getLdapAttributes().getAttributes()) {
    	  String attrName = attr.getName();
    	  List<String> values = new ArrayList<String>();
    	  for (String attrValue : attr.getStringValues()) {
    		  values.add(attrValue);
            }
    	  
    	  principal.setAttribute(attrName, values);
      }
      
    }
    session.setAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR, principal);
  }
	

	/**
	 * Merges the two {@link Subject}s in to a new {@link Subject}. The new
	 * subjects contains all the {@link Principal}s from both subjects. If
	 * {@link #retainSubjectsPrivateCredentials} is true then the new subject will
	 * contain all the private credentials from both subjects, if not the new
	 * subject will not contain private credentials. If
	 * {@link #retainSubjectsPublicCredentials} is true then the new subject will
	 * contain all the public credentials from both subjects, if not the new
	 * subject will not contain public credentials.
	 * 
	 * @param subject1
	 *          first subject to merge, may be null
	 * @param subject2
	 *          second subject to merge, may be null
	 * 
	 * @return subject containing the merged information
	 */
	protected Subject mergeSubjects(Subject subject1, Subject subject2) {
		if (subject1 == null && subject2 == null) {
			return new Subject();
		}

		if (subject1 == null) {
			return subject2;
		}

		if (subject2 == null) {
			return subject1;
		}

		Set<Principal> principals = new HashSet<Principal>(3);
		principals.addAll(subject1.getPrincipals());
		principals.addAll(subject2.getPrincipals());

		Set<Object> publicCredentials = new HashSet<Object>(3);
		if (retainSubjectsPublicCredentials) {
			LOG.debug("Merging in subjects public credentials");
			publicCredentials.addAll(subject1.getPublicCredentials());
			publicCredentials.addAll(subject2.getPublicCredentials());
		}

		Set<Object> privateCredentials = new HashSet<Object>(3);
		if (retainSubjectsPrivateCredentials) {
			LOG.debug("Merging in subjects private credentials");
			privateCredentials.addAll(subject1.getPrivateCredentials());
			privateCredentials.addAll(subject2.getPrivateCredentials());
		}

		return new Subject(false, principals, publicCredentials, privateCredentials);
	}

}
