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

package edu.internet2.middleware.shibboleth.common.session;

import java.io.Serializable;

import javax.security.auth.Subject;

import org.joda.time.DateTime;

/** Session information for user currently logged in. */
public interface Session extends Serializable {

    /**
     * Gets the unique identifier of the session.
     * 
     * @return unique identifier of the session
     */
    public String getSessionID();

    /**
     * Gets the subject with which this session is associated.
     * 
     * @return subject with which this session is associated
     */
    public Subject getSubject();
    
    /**
     * Sets the subject with which this session is associated.<br/>
     * 
     * <b>Caution: After update Subject internal information, always be required to re-set subject into session using this method<b/> 
     * 
     * @param newSubject the subject with which this session is associated
     */
    public void setSubject(Subject newSubject);

    /**
     * A convenience method that gets the first principal retrieved from the {@link Subject}.
     * 
     * @return principal ID of the user, or null
     */
    public String getPrincipalName();

    /**
     * Gets the session inactivity timeout in milliseconds.
     * 
     * @return session inactivity timeout in milliseconds
     */
    public long getInactivityTimeout();

    /**
     * Gets the time of the last activity from the user.
     * 
     * @return time of the last activity from the user
     */
    public DateTime getLastActivityInstant();

    /**
     * Sets the time of the last activity from the user.
     * 
     * @param lastActivity time of the last activity from the user
     */
    public void setLastActivityInstant(DateTime lastActivity);
}