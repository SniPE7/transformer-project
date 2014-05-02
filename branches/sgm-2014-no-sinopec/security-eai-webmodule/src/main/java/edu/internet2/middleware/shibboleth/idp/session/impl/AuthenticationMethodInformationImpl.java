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

import javax.security.auth.Subject;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;

import edu.internet2.middleware.shibboleth.idp.session.AuthenticationMethodInformation;

/** Information about an authentication method employed by a user. */
public class AuthenticationMethodInformationImpl implements AuthenticationMethodInformation {

    /** Serial version UID. */
    private static final long serialVersionUID = -2108905664641155003L;

    /** Subject created by this authentication mechanism. */
    private Subject authenticationSubject;

    /** Principal created by the authentication method. */
    private Principal authenticationPrincipal;

    /** The authentication method (a URI). */
    private String authenticationMethod;

    /** The timestamp at which authentication occurred. */
    private long authenticationInstant;

    /** The lifetime of the authentication method. */
    private long authenticationDuration;

    /** Time when this method expires. */
    private long expirationInstant;

    /**
     * Default constructor.  This constructor does NOT add the given principal to the given subject.
     * 
     * @param subject subject associated with the user's session
     * @param principal principal created by the authentication method
     * @param method The unique identifier for the authentication method
     * @param instant The time the user authenticated with this member
     * @param duration The duration of this authentication method
     */
    public AuthenticationMethodInformationImpl(Subject subject, Principal principal, String method, DateTime instant,
            long duration) {

        if (method == null || instant == null || duration < 0) {
            throw new IllegalArgumentException("Authentication method, instant, and duration may not be null");
        }

        authenticationSubject = subject;
        authenticationPrincipal = principal;
        authenticationMethod = method;
        authenticationInstant = instant.toDateTime(ISOChronology.getInstanceUTC()).getMillis();
        authenticationDuration = duration;
        expirationInstant = authenticationInstant + duration;
    }

    /** {@inheritDoc} */
    public synchronized Subject getAuthenticationSubject() {
        return authenticationSubject;
    }

    /** {@inheritDoc} */
    public synchronized Principal getAuthenticationPrincipal() {
        return authenticationPrincipal;
    }

    /** {@inheritDoc} */
    public synchronized String getAuthenticationMethod() {
        return authenticationMethod;
    }

    /** {@inheritDoc} */
    public synchronized DateTime getAuthenticationInstant() {
        return new DateTime(authenticationInstant, ISOChronology.getInstanceUTC());
    }

    /** {@inheritDoc} */
    public synchronized long getAuthenticationDuration() {
        return authenticationDuration;
    }

    /** {@inheritDoc} */
    public synchronized boolean isExpired() {
        return new DateTime(expirationInstant, ISOChronology.getInstanceUTC()).isBeforeNow();
    }

    /** {@inheritDoc} */
    public synchronized int hashCode() {
        return authenticationMethod.hashCode();
    }

    /** {@inheritDoc} */
    public synchronized boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof AuthenticationMethodInformation)) {
            return false;
        }

        AuthenticationMethodInformation amInfo = (AuthenticationMethodInformation) obj;
        return authenticationMethod.equals(amInfo.getAuthenticationMethod());
    }

    @Override
    public String toString() {
      return String
          .format(
              "AuthenticationMethodInformationImpl [authenticationSubject=%s, authenticationPrincipal=%s, authenticationMethod=%s, authenticationInstant=%s, authenticationDuration=%s, expirationInstant=%s]",
              authenticationSubject, authenticationPrincipal, authenticationMethod, authenticationInstant, authenticationDuration, expirationInstant);
    }
}