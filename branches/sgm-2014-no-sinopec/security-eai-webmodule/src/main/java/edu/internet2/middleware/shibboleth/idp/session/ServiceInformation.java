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

import org.joda.time.DateTime;

/** Information about a service a user has logged in to. */
public interface ServiceInformation extends Serializable {

    /**
     * Gets the unique identifier for the service.
     * 
     * @return unique identifier for the service
     */
    public String getEntityID();

    /**
     * Gets the time the user logged in to the service.
     * 
     * @return time the user logged in to the service
     */
    public DateTime getLoginInstant();

    /**
     * Gets the authentication method used to log into the service.
     * 
     * @return authentication method used to log into the service
     */
    public AuthenticationMethodInformation getAuthenticationMethod();
}