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

package edu.internet2.middleware.shibboleth.idp.session.impl;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.security.auth.Subject;

import edu.internet2.middleware.shibboleth.common.session.SessionEventType;
import edu.internet2.middleware.shibboleth.idp.authn.UsernamePrincipal;
import edu.internet2.middleware.shibboleth.idp.session.AbstractSession;
import edu.internet2.middleware.shibboleth.idp.session.AuthenticationMethodInformation;
import edu.internet2.middleware.shibboleth.idp.session.ServiceInformation;
import edu.internet2.middleware.shibboleth.idp.session.Session;

/** Session information for user logged into the IdP. */
public class SessionImpl extends AbstractSession implements Session {

    /** Serial version UID. */
    private static final long serialVersionUID = 2927868242208211623L;
    
    /** Secret key associated with the session. */
    private byte[] sessionSecret;

    /** The list of methods used to authenticate the user. */
    private Map<String, AuthenticationMethodInformation> authnMethods;

    /** The list of services to which the user has logged in. */
    private Map<String, ServiceInformation> servicesInformation;
    
    public SessionImpl() {
	    super();
	    
        authnMethods = new HashMap<String, AuthenticationMethodInformation>();
        servicesInformation = new HashMap<String, ServiceInformation>();
    }

    /**
     * Constructor.
     * 
     * @param sessionId ID of the session
     * @param secret a secret to associate with the session
     * @param timeout inactivity timeout for the session in milliseconds
     */
    public SessionImpl(String sessionId, byte[] secret, long timeout) {
        super(sessionId, timeout);

        sessionSecret = secret;
        authnMethods = new ConcurrentHashMap<String, AuthenticationMethodInformation>(2);
        servicesInformation = new ConcurrentHashMap<String, ServiceInformation>(2);
    }

    /** {@inheritDoc} */
    public synchronized byte[] getSessionSecret() {
        return sessionSecret;
    }

    /** {@inheritDoc} */
    public synchronized AuthenticationMethodInformation[] getAuthenticationMethods() {
        return authnMethods.values().toArray(new AuthenticationMethodInformation[0]);
    }

    public synchronized AuthenticationMethodInformation getAuthenticationMethodByName(String name) {
      return this.authnMethods.get(name);
    }

    public synchronized void addAuthenticationMethod(AuthenticationMethodInformation authenticationMethod) {
      this.authnMethods.put(authenticationMethod.getAuthenticationMethod(), authenticationMethod);
      // Notify session listner
      fireEvent(SessionEventType.CHANGED);
    }

    /** {@inheritDoc} */
    public synchronized ServiceInformation[] getServicesInformation() {
        return servicesInformation.values().toArray(new ServiceInformation[0]);
    }

    public ServiceInformation getServiceInformationByName(String name) {
      return servicesInformation.get(name);
    }

    public void addServiceInformation(ServiceInformation servicesInformation) {
      this.servicesInformation.put(servicesInformation.getEntityID(), servicesInformation);

      // Notify session listner
      fireEvent(SessionEventType.CHANGED);
    }
    /**
     * Gets the service information for the given entity ID.
     * 
     * @param entityId entity ID to retrieve the service information for
     * 
     * @return the service information or null
     */
    public synchronized ServiceInformation getServiceInformation(String entityId) {
        return servicesInformation.get(entityId);
    }

    /**
     * This method will return the first, in an unordered list of principal names registered with the {@link Subject} of
     * the session. If one or more {@link UsernamePrincipal} principals is registered with the subject the returned
     * value will be the string form of one of those.
     * 
     * {@inheritDoc}
     */
    public synchronized String getPrincipalName() {
        Subject subject = getSubject();

        Set<? extends Principal> principals = subject.getPrincipals(UsernamePrincipal.class);
        if (principals == null || principals.isEmpty()) {
            principals = subject.getPrincipals();
        }

        if (principals == null || principals.isEmpty()) {
            return null;
        }

        return principals.iterator().next().getName();
    }

    @Override
    public String toString() {
      return String.format("SessionImpl [sessionSecret=%s, authnMethods=%s, servicesInformation=%s]", Arrays.toString(sessionSecret), authnMethods, servicesInformation);
    }


 }