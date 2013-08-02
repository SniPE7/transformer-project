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
import java.util.concurrent.ConcurrentHashMap;

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

    /** Entity ID of the relying party. */
    private String relyingPartyId;
    /** Should user authentication be forced. */
    private boolean forceAuth;

    /** Must authentication not interact with the UI. */
    private boolean passiveAuth;

    /** A catch-all map for other properties. */
    private Map<String, Serializable> propsMap = new ConcurrentHashMap<String, Serializable>(0);

    /** The Access Enforcer URL. */
    private String accessEnforcerURL;
	
	/** The ProfileHandler URL. */
    private String profileHandlerURL;

    /** The authentication engine's URL. */
    private String authnEngineURL;

    /** Whether authentication been attempted yet. */
    private boolean authnAttempted;

    /** Attempted user authentication method. */
    private String attemptedAuthnMethod;

    /** Did authentication succeed? */
    private boolean principalAuthenticated;

    /** Exception that occurred during authentication. */
    private AuthenticationException authnException;

    /** The session id. */
    private String sessionID;

    /** Default authentication method to use if no other method is requested. */
    private String defaultAuthenticationMethod;

    /** List of request authentication methods. */
    private List<String> requestAuthenticationMethods;

    /** Information about the authentication method. */
    private AuthenticationMethodInformation authenticationMethodInformation;

    /** Creates a new instance of LoginContext. */
    public LoginContext() {
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
     * Gets the duration of authentication.
     * 
     * @return The duration of authentication, or zero if none was set.
     */
    public synchronized long getAuthenticationDuration() {
        if(authenticationMethodInformation == null){
            return 0;
        }

        return authenticationMethodInformation.getAuthenticationDuration();
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
     * Gets the authentication instant.
     * 
     * @return The instant of authentication, or <code>null</code> if none was set.
     */
    public synchronized DateTime getAuthenticationInstant() {
        if(authenticationMethodInformation == null){
            return null;
        }

        return authenticationMethodInformation.getAuthenticationInstant();
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
     * Gets information about the authentication event.
     * 
     * @return information about the authentication event.
     */
    public synchronized AuthenticationMethodInformation getAuthenticationMethodInformation() {
        return authenticationMethodInformation;
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
     * Gets the ProfileHandler URL.
     * 
     * @return the URL of the profile handler that is invoking the Authentication Manager.
     */
    public synchronized String getProfileHandlerURL() {
        return profileHandlerURL;
    }

    /**
     * Get an optional property object.
     * 
     * @param key The key in the properties Map.
     * 
     * @return The object, or <code>null</code> is no object exists for the key.
     */
    public synchronized Object getProperty(String key) {
        return propsMap.get(key);
    }

    /**
     * Gets the entity ID of the relying party.
     * 
     * @return entity ID of the relying party
     */
    public synchronized String getRelyingPartyId() {
        return relyingPartyId;
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
     * Gets the {@link edu.internet2.middleware.shibboleth.idp.session.Session} ID.
     * 
     * @return the Session id
     */
    public synchronized String getSessionID() {
        return sessionID;
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
     * Returns if authentication succeeded.
     * 
     * @return <code>true</code> is the user was successfully authenticated.
     */
    public synchronized boolean isPrincipalAuthenticated() {
        return principalAuthenticated;
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
     * Sets the duration of authentication.
     * 
     * @param duration The duration of authentication.
     * 
     * @deprecated this information is contained in the {@link AuthenticationMethodInformation}
     */
    public synchronized void setAuthenticationDuration(long duration) {
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
     * Sets the method used to authenticate the user.
     * 
     * @param method The method used to authenticate the user.
     * 
     * @deprecated this information is contained in the {@link AuthenticationMethodInformation}
     */
    public synchronized void setAuthenticationMethod(String method) {
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
     * Sets if authentication succeeded.
     * 
     * @param authnOK if authentication succeeded;
     */
    public synchronized void setPrincipalAuthenticated(boolean authnOK) {
        this.principalAuthenticated = authnOK;
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
	
	    /**
     * Sets the ProfileHandler URL.
     * 
     * @param url The URL of the profile handler that invoked the AuthenticationManager/
     */
    public synchronized void setProfileHandlerURL(String url) {
        profileHandlerURL = url;
    }

    /**
     * Sets an optional property object.
     * 
     * If an object is already associated with key, it will be overwritten.
     * 
     * @param key The key to set.
     * @param obj The object to associate with key.
     */
    public synchronized void setProperty(String key, final Serializable obj) {
        propsMap.put(key, obj);
    }

    /**
     * Gets the entity ID of the relying party.
     * 
     * @param id entity ID of the relying party
     */
    public synchronized void setRelyingParty(String id) {
        relyingPartyId = id;
    }

    /**
     * Sets the {@link edu.internet2.middleware.shibboleth.idp.session.Session} ID.
     * 
     * @param id the Session ID
     */
    public synchronized void setSessionID(String id) {
        sessionID = id;
    }
}