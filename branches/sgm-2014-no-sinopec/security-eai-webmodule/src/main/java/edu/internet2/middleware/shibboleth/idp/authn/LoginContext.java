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

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.ibm.siam.am.idp.authn.AuthenticationEngine;

import edu.internet2.middleware.shibboleth.idp.session.AuthenticationMethodInformation;

/**
 * Login context created by a profile handler and interpreted by the authentication package.
 * 
 * Two properties are tracked by default:
 * <ul>
 * <li><code>forceAuth</code> - Should user authentication be forced (default value: false).</li>
 * <li><code>passiveAuth</code> - Should user authentication not control the UI (default value: false).</li>
 * </ul>
 * 
 * A {@link Map}&lt;String, Object&gt; is provided to store other properties. Alternatively, a profile handler may
 * create a subclass of LoginContext with extra fields.
 * 
 * LoginContexts should be created by a profile handler when authentication is needed. Once control has returned to the
 * profile handler, it should remove the LoginContext from the HttpSession.
 * 
 * The {@link AuthenticationEngine} should set the {@link LoginContext#setAuthenticationAttempted()},
 * {@link LoginContext#setPrincipalAuthenticated(boolean)},
 * {@link LoginContext#setAuthenticationFailure(AuthenticationException)}, appropriately.
 */
public class LoginContext implements Serializable {

    /** the key in a HttpSession where login contexts are stored. */
    public static final String LOGIN_CONTEXT_KEY = "shib2.logincontext";

    /** Serial version UID. */
    private static final long serialVersionUID = -8764003758734956911L;

    /** Should user authentication be forced. */
    private boolean forceAuth;

    /** Must authentication not interact with the UI. */
    private boolean passiveAuth;

    /** The Access Enforcer URL. */
    private String accessEnforcerURL;
	
    /** The authentication engine's URL. */
    private String authnEngineURL;

    /** Whether authentication been attempted yet. */
    private boolean authnAttempted;

    /** Attempted user authentication method. */
    private String attemptedAuthnMethod;

    /** Exception that occurred during authentication. */
    private AuthenticationException authnException;

    /** Default authentication method to use if no other method is requested. */
    private String defaultAuthenticationMethod;

    /** List of request authentication methods. */
    private List<String> requestAuthenticationMethods;

    /** Information about the authentication method. */
    private AuthenticationMethodInformation authenticationMethodInformation;

    /** Creates a new instance of LoginContext. */
    protected LoginContext() {
        requestAuthenticationMethods = new ArrayList<String>();
    }

    /**
     * Creates a new instance of LoginContext.
     * 
     * @param force if the authentication manager must re-authenticate the user.
     * @param passive if the authentication manager must not interact with the users UI.
     */
    public LoginContext(boolean force, boolean passive) {
        forceAuth = force;
        passiveAuth = passive;
        requestAuthenticationMethods = new ArrayList<String>();
    }

    /**
     * Gets the authentication method that was used when attempting to authenticate the user. Note, this may be
     * different than the authentication method reported within {@link #getAuthenticationMethodInformation()}.
     * 
     * @return authentication method that was used when attempting to authenticate the user
     */
    public synchronized String getAttemptedAuthnMethod() {
        return attemptedAuthnMethod;
    }

    /**
     * Returns if authentication has been attempted for this user.
     * 
     * @return if authentication has been attempted for this user
     */
    public synchronized boolean getAuthenticationAttempted() {
        return authnAttempted;
    }

    /**
     * Gets the authentication engine's URL.
     * 
     * @return the URL of the authentication engine
     */
    public synchronized String getAuthenticationEngineURL() {
        return authnEngineURL;
    }

    /**
     * Gets the error that occurred during authentication.
     * 
     * @return error that occurred during authentication
     */
    public synchronized AuthenticationException getAuthenticationFailure() {
        return authnException;
    }

    /**
     * Gets the method used to authenticate the user.
     * 
     * @return The method used to authenticate the user.
     */
    public synchronized String getAuthenticationMethod() {
        if(authenticationMethodInformation == null){
            return null;
        }
        return authenticationMethodInformation.getAuthenticationMethod();
    }


    /**
     * Gets the authentication method to use if none is requested.
     * 
     * @return authentication method to use if none is requested, may be null which indicates any method may be used
     */
    public synchronized String getDefaultAuthenticationMethod() {
        return defaultAuthenticationMethod;
    }

    /**
     * Returns the ID of the authenticated user.
     * 
     * @return the ID of the user, or <code>null</code> if authentication failed.
     */
    public synchronized String getPrincipalName() {
        if(authenticationMethodInformation == null){
            return null;
        }

        Principal principal = authenticationMethodInformation.getAuthenticationPrincipal();
        if(principal == null){
            return null;
        }
        
        return principal.getName();
    }

    /**
     * Gets the ProfileHandler URL.
     * 
     * @return the URL of the profile handler that is invoking the Authentication Manager.
     */
    public synchronized String getAccessEnforcerURL() {
        return accessEnforcerURL;
    }
	
    /**
     * Return the acceptable authentication handler URIs, in preference order, for authenticating this user. If no
     * authentication methods are preferred the resultant list will be empty.
     * 
     * @return an list of authentication method identifiers
     */
    public synchronized List<String> getRequestedAuthenticationMethods() {
        return requestAuthenticationMethods;
    }

     /**
     * Returns if authentication must be forced.
     * 
     * @return <code>true</code> if the authentication manager must re-authenticate the user.
     */
    public synchronized boolean isForceAuthRequired() {
        return forceAuth;
    }

    /**
     * Returns if authentication must be passive.
     * 
     * @return <code>true</code> if the authentication manager must not interact with the users UI.
     */
    public synchronized boolean isPassiveAuthRequired() {
        return passiveAuth;
    }

    /**
     * Sets the authentication method that was used when attempting to authenticate the user.
     * 
     * @param method authentication method that was used when attempting to authenticate the user
     */
    public synchronized void setAttemptedAuthnMethod(String method) {
        attemptedAuthnMethod = method;
    }

    /**
     * Set if authentication has been attempted.
     * 
     * This method should be called by an {@link LoginHandler} while processing a request.
     */
    public synchronized void setAuthenticationAttempted() {
        authnAttempted = true;
    }

     /**
     * Sets the authentication engine's URL.
     * 
     * @param url the URL of the authentication engine
     */
    public synchronized void setAuthenticationEngineURL(String url) {
        authnEngineURL = url;
    }

    /**
     * Sets the error that occurred during authentication.
     * 
     * @param error error that occurred during authentication
     */
    public synchronized void setAuthenticationFailure(AuthenticationException error) {
        authnException = error;
    }

    /**
     * Sets the authentication instant.
     * 
     * @param instant The instant of authentication.
     * 
     * @deprecated this information is contained in the {@link AuthenticationMethodInformation}
     */
    public synchronized void setAuthenticationInstant(final DateTime instant) {
    }

    /**
     * Sets the information about the authentication event.
     * 
     * @param info information about the authentication event
     */
    public synchronized void setAuthenticationMethodInformation(AuthenticationMethodInformation info) {
        authenticationMethodInformation = info;
    }

    /**
     * Sets the authentication method to use if none is requested.
     * 
     * @param method authentication method to use if none is requested, may be null which indicates any method may be
     *            used
     */
    public synchronized void setDefaultAuthenticationMethod(String method) {
        defaultAuthenticationMethod = method;
    }

    /**
     * Sets if authentication must be forced.
     * 
     * @param force if the authentication manager must re-authenticate the user.
     */
    public synchronized void setForceAuthRequired(boolean force) {
        forceAuth = force;
    }

    /**
     * Sets if authentication must be passive.
     * 
     * @param passive if the authentication manager must not interact with the users UI.
     */
    public synchronized void setPassiveAuthRequired(boolean passive) {
        passiveAuth = passive;
    }

    /**
     * Sets the ID of the authenticated user.
     * 
     * @param id The userid.
     * 
     * @deprecated this information is contained in the {@link AuthenticationMethodInformation}
     */
    public synchronized void setPrincipalName(String id) {

    }

    /**
     * Sets the ProfileHandler URL.
     * 
     * @param url The URL of the profile handler that invoked the AuthenticationManager/
     */
    public synchronized void setAccessEnforcerURL(String url) {
        accessEnforcerURL = url;
    }

}