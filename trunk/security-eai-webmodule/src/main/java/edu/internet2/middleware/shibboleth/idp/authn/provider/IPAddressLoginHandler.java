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

package edu.internet2.middleware.shibboleth.idp.authn.provider;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.siam.am.idp.authn.AuthenticationEngine;
import com.sinopec.siam.am.idp.authn.module.AbstractSpringLoginModule;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import edu.internet2.middleware.shibboleth.idp.util.IPRange;

/**
 * IP Address authentication handler.
 * 
 * This "authenticates" a user based on their IP address. It operates in either default deny or default allow mode, and
 * evaluates a given request against a list of blocked or permitted IPs. It supports both IPv4 and IPv6.
 */
public class IPAddressLoginHandler extends AbstractLoginHandler {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(IPAddressLoginHandler.class);

    /** The username to use for IP-address "authenticated" users. */
    private String authenticatedUser;

    /** List of configured IP ranged. */
    private List<IPRange> ipRanges;

    /** Whether a user is "authenticated" if their IP address is within a configured IP range. */
    private boolean ipInRangeIsAuthenticated;

    /**
     * Constructor.
     * 
     * @param user username to return upon successful "authentication"
     * @param ranges range of IP addresses specified
     * @param isIpInRangeAuthenticated whether the specified IP address range represent those that are authenticated or
     *            those that are not
     */
    public IPAddressLoginHandler(String user, List<IPRange> ranges, boolean isIpInRangeAuthenticated) {
        authenticatedUser = user;
        if (StringUtils.isEmpty(authenticatedUser)) {
            throw new IllegalArgumentException("The authenticated user ID may not be null or empty");
        }

        if (ranges == null || ranges.isEmpty()) {
            throw new IllegalArgumentException("The list of IP ranges may not be null or empty");
        }
        ipRanges = new ArrayList<IPRange>(ranges);

        this.ipInRangeIsAuthenticated = isIpInRangeAuthenticated;
    }

    /** {@inheritDoc} */
    public boolean supportsPassive() {
        return true;
    }

    /** {@inheritDoc} */
    public boolean supportsForceAuthentication() {
        return true;
    }

    /** {@inheritDoc} */
    public void login(ServletContext servletContext, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
      if(log.isDebugEnabled()){
        log.debug("Attempting to authenticated client '{}'", httpRequest.getRemoteAddr());
      }
        try {
            InetAddress clientAddress = InetAddress.getByName(httpRequest.getRemoteAddr());
            if (authenticate(clientAddress)) {
                log.debug("Authenticated user by IP address");
                httpRequest.setAttribute(AbstractSpringLoginModule.PRINCIPAL_NAME_KEY, authenticatedUser);
                httpRequest.setAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY, "IP_AUTHN_CTX");
            } else {
              if(log.isDebugEnabled()){
                log.debug("Client IP address {} failed authentication.", httpRequest.getRemoteAddr());
              }
                httpRequest.setAttribute(LoginHandler.AUTHENTICATION_ERROR_KEY,
                        "Client failed IP address authentication");
            }
        } catch (UnknownHostException e) {
            String msg = "Unable to resolve " + httpRequest.getRemoteAddr() + " in to an IP address";
            log.warn(msg);
            httpRequest.setAttribute(LoginHandler.AUTHENTICATION_ERROR_KEY, msg);
        }

        AuthenticationEngine.returnToAuthenticationEngine(servletContext, httpRequest, httpResponse);
    }

    /**
     * Authenticates the client address.
     * 
     * @param clientAddress the client address
     * 
     * @return true if the client address is authenticated, false it not
     */
    protected boolean authenticate(InetAddress clientAddress) {
        if (ipInRangeIsAuthenticated) {
            for (IPRange range : ipRanges) {
                if (range.contains(clientAddress)) {
                    return true;
                }
            }
        } else {
            for (IPRange range : ipRanges) {
                if (!range.contains(clientAddress)) {
                    return true;
                }
            }
        }

        return false;
    }
}