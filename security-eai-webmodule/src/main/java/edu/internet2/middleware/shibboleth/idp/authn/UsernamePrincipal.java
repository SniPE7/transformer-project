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

import org.opensaml.xml.util.DatatypeHelper;

import edu.internet2.middleware.shibboleth.common.attribute.LdapAttributesPrincipal;
import edu.vt.middleware.ldap.bean.LdapAttributes;

/** A basic implementation of {@link Principal}. */
public class UsernamePrincipal implements Serializable, LdapAttributesPrincipal {

    /** Serial version UID. */
    private static final long serialVersionUID = 8708917521896240626L;
    
    /** Name of the principal. */
    private String name;
    
    /** User attributes. */
    private LdapAttributes attributes;

    /**
     * Constructor.
     * 
     * @param principalName name of the principal
     */
    public UsernamePrincipal(String principalName) {
        name = DatatypeHelper.safeTrimOrNullString(principalName);
        if(name == null){
            throw new IllegalArgumentException("principal name may not be null or empty");
        }
    }

    /** {@inheritDoc} */
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    public String toString() {
        return "{BasicPrincipal}" + getName();
    }

    /** {@inheritDoc} */
    public int hashCode() {
        return name.hashCode();
    }

    /** {@inheritDoc} */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof UsernamePrincipal) {
            return DatatypeHelper.safeEquals(getName(), ((UsernamePrincipal) obj).getName());
        }

        return false;
    }

    /* (non-Javadoc)
     * @see edu.internet2.middleware.shibboleth.common.attribute.AttributesPrincipal#getLdapAttributes()
     */
    public LdapAttributes getLdapAttributes() {
      return attributes;
    }
    
    public void setLdapAttributes(LdapAttributes attributes){
      this.attributes = attributes;
    }
}