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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;

import edu.internet2.middleware.shibboleth.idp.session.AuthenticationMethodInformation;
import edu.internet2.middleware.shibboleth.idp.session.ServiceInformation;

/** Information about a service a user has logged in to. */
public class ServiceInformationImpl implements ServiceInformation {
    
    /** Serial version UID. */
    private static final long serialVersionUID = 1185342879825302743L;

    /** Entity ID of the service. */
    private String entityID;

    /** Instant the user was authenticated to the service. */
    private long authenticationInstant;

    /** Authentication method used to authenticate the user to the service. */
    private AuthenticationMethodInformation methodInfo;

    /**
     * Default constructor.
     * 
     * @param id unique identifier for the service.
     * @param loginInstant time the user logged in to the service.
     * @param method authentication method used to log into the service.
     */
    public ServiceInformationImpl(String id, DateTime loginInstant, AuthenticationMethodInformation method) {
        entityID = id;
        authenticationInstant = loginInstant.toDateTime(ISOChronology.getInstanceUTC()).getMillis();
        methodInfo = method;
    }

    /** {@inheritDoc} */
    public synchronized String getEntityID() {
        return entityID;
    }

    /** {@inheritDoc} */
    public synchronized DateTime getLoginInstant() {
        return new DateTime(authenticationInstant, ISOChronology.getInstanceUTC());
    }

    /** {@inheritDoc} */
    public synchronized AuthenticationMethodInformation getAuthenticationMethod() {
        return methodInfo;
    }

    /** {@inheritDoc} */
    public synchronized int hashCode() {
        return entityID.hashCode();
    }

    /** {@inheritDoc} */
    public synchronized boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ServiceInformation)) {
            return false;
        }

        ServiceInformation si = (ServiceInformation) obj;
        return entityID.equals(si.getEntityID());
    }

    @Override
    public String toString() {
      return String.format("ServiceInformationImpl [entityID=%s, authenticationInstant=%s, methodInfo=%s]", entityID, authenticationInstant, methodInfo);
    }
}