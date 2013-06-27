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

import java.io.Serializable;
import java.security.Principal;

import javax.security.auth.Subject;

import org.joda.time.DateTime;

/** Information about an authentication method employed by a user. */
public interface AuthenticationMethodInformation extends Serializable {

    /**
     * Gets the Subject created by this authentication method.
     * 
     * @return subject created by this authentication method
     * 
     * @deprecated use {@link Session#getSubject()}
     */
    public Subject getAuthenticationSubject();

    /**
     * Gets the principal, for the {@link Subject} of the session, created by this authentication method.
     * 
     * @return principal created by this authentication method
     */
    public Principal getAuthenticationPrincipal();

    /**
     * Gets the unique identifier for the authentication method.
     * 
     * @return unique identifier for the authentication method
     */
    public String getAuthenticationMethod();

    /**
     * Gets the time the user authenticated with this member.
     * 
     * @return time the user authenticated with this member
     */
    public DateTime getAuthenticationInstant();

    /**
     * Gets the duration of this authentication method.
     * 
     * @return duration of this authentication method
     */
    public long getAuthenticationDuration();

    /**
     * Gets whether this authentication method has expired and is not longer valid for use in constructing new sessions.
     * 
     * @return whether this authentication method has expired
     */
    public boolean isExpired();
}