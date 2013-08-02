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

import org.joda.time.DateTime;
import org.opensaml.util.storage.AbstractExpiringObject;

/** Storage service entry for login contexts. */
public class LoginContextEntry extends AbstractExpiringObject {

    /** Serial version UID. */
    private static final long serialVersionUID = -1528197153404835381L;
    
    /** Stored login context. */
    private LoginContext loginCtx;

    /**
     * Constructor.
     * 
     * @param ctx context to store
     * @param lifetime lifetime of the entry
     */
    public LoginContextEntry(LoginContext ctx, long lifetime) {
        super(new DateTime().plus(lifetime));
        loginCtx = ctx;
    }

    /**
     * Gets the login context.
     * 
     * @return login context
     */
    public LoginContext getLoginContext() {
        return loginCtx;
    }
}