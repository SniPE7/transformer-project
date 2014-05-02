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

package edu.internet2.middleware.shibboleth.idp.session;

import javax.security.auth.Subject;

/**
 * Session information for user logged into the IdP.
 */
public interface Session extends edu.internet2.middleware.shibboleth.common.session.Session {

    /** Name of the HTTP request attribute to which a users IdP session is bound. */
    public static final String HTTP_SESSION_BINDING_ATTRIBUTE = "ShibbolethIdPSession";

    /**
     * A secret associated with this session.
     * 
     * @return secret associated with this session
     */
    public byte[] getSessionSecret();

    /**
     * Gets the methods by which the user has authenticated to the IdP.
     * 
     * @return methods by which the user has authenticated to the IdP
     */
    //public Map<String, AuthenticationMethodInformation> getAuthenticationMethods();
    /**
     * Get the method by name which the user has authenticated to the IdP.
     * @param name
     * @return
     */
    public AuthenticationMethodInformation getAuthenticationMethodByName(String name);

    /**
     * Gets the methods by which the user has authenticated to the IdP.
     * @return
     */
    public AuthenticationMethodInformation[] getAuthenticationMethods();
    
    /**
     * Add an authen method
     * @param authenticationMethod
     */
    public void addAuthenticationMethod(AuthenticationMethodInformation authenticationMethod);
    
    /**
     * Gets the services the user has logged in to.
     * 
     * @return services the user has logged in to
     */
    //public Map<String, ServiceInformation> getServicesInformation();
    
    /**
     * Get the method by name which the user has authenticated to the IdP.
     * @param name
     * @return
     */
    public ServiceInformation getServiceInformationByName(String name);

    /**
     * Gets the services the user has logged in to.
     * @return services the user has logged in to
     */
    public ServiceInformation[] getServicesInformation();
    
    /**
     * Add an authen method
     * @param authenticationMethod
     */
    public void addServiceInformation(ServiceInformation servicesInformation);
		public Subject getSubject();
    
		public void setSubject(Subject subject);
		
		public String getPrincipalName();
    
}