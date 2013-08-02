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

package edu.internet2.middleware.shibboleth.idp.profile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import edu.internet2.middleware.shibboleth.common.config.BaseReloadableService;
import edu.internet2.middleware.shibboleth.common.profile.AbstractErrorHandler;
import edu.internet2.middleware.shibboleth.common.profile.ProfileHandler;
import edu.internet2.middleware.shibboleth.common.profile.ProfileHandlerManager;
import edu.internet2.middleware.shibboleth.common.profile.provider.AbstractRequestURIMappedProfileHandler;
import edu.internet2.middleware.shibboleth.common.service.ServiceException;
import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;

/**
 * Implementation of a {@link ProfileHandlerManager} that maps the request path, without the servlet context, to a
 * profile handler and adds support for authentication handlers.
 */
public class IdPProfileHandlerManager extends BaseReloadableService implements ProfileHandlerManager {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(IdPProfileHandlerManager.class);

    /** Handler used for errors. */
    private AbstractErrorHandler errorHandler;

    /** Map of request paths to profile handlers. */
    private Map<String, AbstractRequestURIMappedProfileHandler> profileHandlers;

    /** Map of authentication methods to login handlers. */
    private Map<String, LoginHandler> loginHandlers;

    /** Constructor. */
    public IdPProfileHandlerManager() {
        super();
        profileHandlers = new HashMap<String, AbstractRequestURIMappedProfileHandler>();
        loginHandlers = new HashMap<String, LoginHandler>();
    }

    /** {@inheritDoc} */
    public AbstractErrorHandler getErrorHandler() {
        return errorHandler;
    }

    /**
     * Sets the error handler.
     * 
     * @param handler error handler
     */
    public void setErrorHandler(AbstractErrorHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("Error handler may not be null");
        }
        errorHandler = handler;
    }

    /** {@inheritDoc} */
    public ProfileHandler getProfileHandler(ServletRequest request) {
        ProfileHandler handler;

        String requestPath = ((HttpServletRequest) request).getPathInfo();
        if(log.isDebugEnabled()){
          log.debug("{}: Looking up profile handler for request path: {}", getId(), requestPath);
        }

        Lock readLock = getReadWriteLock().readLock();
        readLock.lock();
        try{
            handler = profileHandlers.get(requestPath);
        }finally{
            readLock.unlock();
        }

        if(log.isDebugEnabled()){
          if (handler != null) {
            log.debug("{}: Located profile handler of the following type for the request path: {}", getId(), handler
                    .getClass().getName());
          } else {
            log.debug("{}: No profile handler registered for request path {}", getId(), requestPath);
          }
        }
        return handler;
    }

    /**
     * Gets the registered profile handlers.
     * 
     * @return registered profile handlers
     */
    public Map<String, AbstractRequestURIMappedProfileHandler> getProfileHandlers() {
        return profileHandlers;
    }

    /**
     * Gets the registered authentication handlers.
     * 
     * @return registered authentication handlers
     */
    public Map<String, LoginHandler> getLoginHandlers() {
        return loginHandlers;
    }

    /** {@inheritDoc} */
    protected void onNewContextCreated(ApplicationContext newServiceContext) throws ServiceException {
      if(log.isDebugEnabled()){
        log.debug("{}: Loading new configuration into service", getId());
      }
        AbstractErrorHandler oldErrorHandler = errorHandler;
        Map<String, AbstractRequestURIMappedProfileHandler> oldProfileHandlers = profileHandlers;
        Map<String, LoginHandler> oldLoginHandlers = loginHandlers;

        try {
            loadNewErrorHandler(newServiceContext);
            loadNewProfileHandlers(newServiceContext);
            loadNewLoginHandlers(newServiceContext);
        } catch (Exception e) {
            errorHandler = oldErrorHandler;
            profileHandlers = oldProfileHandlers;
            loginHandlers = oldLoginHandlers;
            throw new ServiceException(getId() + " configuration is not valid, retaining old configuration", e);
        }
    }

    /**
     * Reads the new error handler from the newly created application context and loads it into this manager.
     * 
     * @param newServiceContext newly created application context
     */
    protected void loadNewErrorHandler(ApplicationContext newServiceContext) {
        String[] errorBeanNames = newServiceContext.getBeanNamesForType(AbstractErrorHandler.class);
        if(log.isDebugEnabled()){
          log.debug("{}: Loading {} new error handler.", getId(), errorBeanNames.length);
        }

        errorHandler = (AbstractErrorHandler) newServiceContext.getBean(errorBeanNames[0]);
        if(log.isDebugEnabled()){
          log.debug("{}: Loaded new error handler of type: {}", getId(), errorHandler.getClass().getName());
        }
    }

    /**
     * Reads the new profile handlers from the newly created application context and loads it into this manager.
     * 
     * @param newServiceContext newly created application context
     */
    protected void loadNewProfileHandlers(ApplicationContext newServiceContext) {
        String[] profileBeanNames = newServiceContext.getBeanNamesForType(AbstractRequestURIMappedProfileHandler.class);
        if(log.isDebugEnabled()){
          log.debug("{}: Loading {} new profile handlers.", getId(), profileBeanNames.length);
        }

        Map<String, AbstractRequestURIMappedProfileHandler> newProfileHandlers = new HashMap<String, AbstractRequestURIMappedProfileHandler>();
        AbstractRequestURIMappedProfileHandler<?, ?> profileHandler;
        for (String profileBeanName : profileBeanNames) {
            profileHandler = (AbstractRequestURIMappedProfileHandler) newServiceContext.getBean(profileBeanName);
            for (String requestPath : profileHandler.getRequestPaths()) {
                newProfileHandlers.put(requestPath, profileHandler);
                if(log.isDebugEnabled()){
                  log.debug("{}: Loaded profile handler for handling requests to request path {}", getId(), requestPath);
                }
            }
        }
        profileHandlers = newProfileHandlers;
    }

    /**
     * Reads the new authentication handlers from the newly created application context and loads it into this manager.
     * 
     * @param newServiceContext newly created application context
     */
    protected void loadNewLoginHandlers(ApplicationContext newServiceContext) {
        String[] authnBeanNames = newServiceContext.getBeanNamesForType(LoginHandler.class);
        if(log.isDebugEnabled()){
          log.debug("{}: Loading {} new authentication handlers.", getId(), authnBeanNames.length);
        }

        Map<String, LoginHandler> newLoginHandlers = new HashMap<String, LoginHandler>();
        LoginHandler authnHandler;
        for (String authnBeanName : authnBeanNames) {
            authnHandler = (LoginHandler) newServiceContext.getBean(authnBeanName);
            if(log.isDebugEnabled()){
              log.debug("{}: Loading authentication handler of type supporting authentication methods: {}", getId(),
                    authnHandler.getSupportedAuthenticationMethods());
            }
            for (String authnMethod : authnHandler.getSupportedAuthenticationMethods()) {
                newLoginHandlers.put(authnMethod, authnHandler);
            }
        }
        loginHandlers = newLoginHandlers;
    }
}