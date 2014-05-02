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

package edu.internet2.middleware.shibboleth.idp.authn;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.siam.am.idp.authn.AuthenticationEngine;
import com.ibm.siam.am.idp.authn.module.AbstractSpringLoginModule;

/**
 * Authentication handlers authenticate a user in an implementation specific manner. Some examples of this might be by
 * collecting a user name and password and validating it against an LDAP directory, validating a client certificate, or
 * validating one-time password.
 * 
 * When a login handler is invoked the user's {@link edu.internet2.middleware.shibboleth.idp.session.Session} is bound
 * to the {@link javax.servlet.http.HttpSession} under the attribute with the name
 * {@link edu.internet2.middleware.shibboleth.idp.session.Session#HTTP_SESSION_BINDING_ATTRIBUTE}.
 * 
 * After a successful authentication has been completed the handler <strong>MUST</strong> either:
 * <ul>
 * <li>Bind a {@link javax.security.auth.Subject} to the attribute identified by {@link #SUBJECT_KEY} if one was created
 * during the authentication process. The principals, public, and private credentials from this subject will be merged
 * with those in the {@link javax.security.auth.Subject} within the
 * {@link edu.internet2.middleware.shibboleth.idp.session.Session}.</li>
 * <li>Bind a {@link java.security.Principal} for the user to the request attribute identified by {@link #PRINCIPAL_KEY}
 * . Such a {@link java.security.Principal} <strong>MUST</strong> implement {@link java.io.Serializable}. This principal
 * will be added to the {@link javax.security.auth.Subject} within the
 * {@link edu.internet2.middleware.shibboleth.idp.session.Session}.</li>
 * <li>Bind a principal name string to the request attribute identified by {@link AbstractSpringLoginModule#PRINCIPAL_NAME_KEY}. In this case the
 * {@link AuthenticationEngine} will create a {@link java.security.Principal} object of type
 * {@link edu.internet2.middleware.shibboleth.idp.authn.UsernamePrincipal} and add that to the
 * {@link javax.security.auth.Subject} within the {@link edu.internet2.middleware.shibboleth.idp.session.Session}.</li>
 * </ul>
 * 
 * The handler <strong>SHOULD</strong> also:
 * <ul>
 * <li>Bind a URI string, representing the authentication method actually used, to a request attribute identified by
 * {@link #AUTHENTICATION_METHOD_KEY}. Failure to do so may lead to a situation where one authentication method is 
 * started but a user switches to a weaker one in mid-process.  Without the login handler explicitly setting the 
 * method, the first method that is started is what will be reported to the relying party.</li>
 * </ul>
 * 
 * The handler <strong>MAY</strong> also:
 * <ul>
 * <li>Bind an error message, if an error occurred during authentication to the request attribute identified by
 * {@link LoginHandler#AUTHENTICATION_ERROR_KEY}.</li>
 * <li>Bind a {@link AuthenticationException}, if an exception occurred during authentication to the request attribute
 * identified by {@link LoginHandler#AUTHENTICATION_EXCEPTION_KEY}.</li>
 * </ul>
 * 
 * Finally, the handler must return control to the authentication engine by invoking
 * {@link AuthenticationEngine#returnToAuthenticationEngine(HttpServletRequest, HttpServletResponse)}. After which the
 * authentication handler must immediately return.
 * 
 * Handlers <strong>MUST NOT</strong> change or add any data to the user's {@link javax.servlet.http.HttpSession} that
 * persists past the process of authenticating the user, that is no additional session data may be added and no existing
 * session data may be changed when the handler returns control to the authentication engine.
 */
public interface LoginHandler {

    /** Request attribute to which user's principal should be bound. */
    public static final String PRINCIPAL_KEY = "principal";

    /** Request attribute to which user's principal update password success state should be bound. */
    public static final String PRINCIPAL_UPDATE_PASSWORD_SUCCESS_KEY = "principal_update_password_success";
    
    /** Request attribute to which user's principal update mobile success state should be bound. */
    public static final String PRINCIPAL_UPDATE_MOBILE_SUCCESS_KEY = "principal_update_mobile_success";

    /** Request attribute to which user's subject should be bound. */
    public static final String SUBJECT_KEY = "subject";

    /** Request attribute to which an authentication method URI may be bound. */
    public static final String AUTHENTICATION_METHOD_KEY = "authnMethod";

    /** Request attribute to which an authentication timestamp may be bound. */
    public static final String AUTHENTICATION_INSTANT_KEY = "authnInstant";
    
    /** Request attribute to which an info message may be bound. */
    public static final String AUTHENTICATION_INFO_KEY = "authnInfo";
    
    /** Request attribute to which an error message may be bound. */
    public static final String AUTHENTICATION_ERROR_KEY = "authnError";
    
    /** Request attribute to which an error prompt message may be bound. */
    public static final String AUTHENTICATION_ERROR_TIP_KEY = "authnErrorTip";
    
    /** Request attribute to which an arguments message may be bound. */
    public static final String AUTHENTICATION_ARGUMENTS_KEY = "authnArguments";

    /** Request attribute to which an {@link AuthenticationException} may be bound. */
    public static final String AUTHENTICATION_EXCEPTION_KEY = "authnException";

    /**
     * Gets the list of authentication methods this handler supports.
     * 
     * @return authentication methods this handler supports
     */
    public List<String> getSupportedAuthenticationMethods();

    /**
     * Gets the length of time, in milliseconds, after which a user authenticated by this handler should be
     * re-authenticated.
     * 
     * @return length of time, in milliseconds, after which a user should be re-authenticated
     */
    public long getAuthenticationDuration();
    
    /**
     * Get the authentication strength level, the lowest level is 1.
     * @return
     */
    public int getAuthenticationLevel();

    /**
     * Gets whether this handler supports passive authentication.
     * 
     * @return whether this handler supports passive authentication
     */
    public boolean supportsPassive();

    /**
     * Returns if this handler supports the ability to force a user to (re-)authenticate.
     * 
     * @return if this handler can force a user to (re-)authenticate.
     */
    public boolean supportsForceAuthentication();

    /**
     * Authenticate the user making the request.
     * 
     * @param httpRequest user request
     * @param httpResponse response to user
     */
    public void login(ServletContext servletContext, HttpServletRequest httpRequest, HttpServletResponse httpResponse);
}