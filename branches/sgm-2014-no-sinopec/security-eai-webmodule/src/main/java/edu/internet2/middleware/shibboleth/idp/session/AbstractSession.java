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

import java.security.Principal;
import java.util.Set;
import java.util.UUID;

import javax.security.auth.Subject;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;

/** Base class for Shibboleth sessions. */
public abstract class AbstractSession implements Session {

  /** Serial version UID. */
  private static final long serialVersionUID = 4726780089406295821L;

  /** The session ID. */
  private final String sessionId;

  /** Subject of this session. */
  private Subject subject;

  /** Session inactivity timeout in milliseconds. */
  private long inactivityTimeout;

  /** The last activity time of the user. */
  private long lastActivity;

  /**
   * Constructor.
   * 
   * @param id
   *          ID of the session
   * @param timeout
   *          inactivity timeout for the session in milliseconds
   */
  public AbstractSession(String id, long timeout) {
    sessionId = id;
    subject = new Subject();
    inactivityTimeout = timeout;
    lastActivity = new DateTime().toDateTime(ISOChronology.getInstanceUTC()).getMillis();
  }
  
  
  /**
   * Constructor.
   * 
   * @param id
   *          ID of the session
   * @param timeout
   *          inactivity timeout for the session in milliseconds
   */
  public AbstractSession() {
	sessionId = UUID.randomUUID().toString();
    subject = new Subject();
  }
  
  

  /** {@inheritDoc} */
  public synchronized String getSessionID() {
    return sessionId;
  }

  /** {@inheritDoc} */
  public synchronized Subject getSubject() {
    return subject;
  }

  /** {@inheritDoc} */
  public synchronized void setSubject(Subject newSubject) {
    subject = newSubject;
  }

  /** {@inheritDoc} */
  public synchronized String getPrincipalName() {
    Set<Principal> principals = subject.getPrincipals();
    if (principals != null && !principals.isEmpty()) {
      return principals.iterator().next().getName();
    } else {
      return null;
    }
  }

  /** {@inheritDoc} */
  public synchronized long getInactivityTimeout() {
    return inactivityTimeout;
  }

  /** {@inheritDoc} */
  public synchronized DateTime getLastActivityInstant() {
    return new DateTime(lastActivity, ISOChronology.getInstanceUTC());
  }

  /** {@inheritDoc} */
  public synchronized void setLastActivityInstant(DateTime activity) {
    lastActivity = activity.toDateTime(ISOChronology.getInstanceUTC()).getMillis();
  }

}