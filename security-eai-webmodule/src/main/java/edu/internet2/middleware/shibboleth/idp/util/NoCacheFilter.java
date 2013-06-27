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

package edu.internet2.middleware.shibboleth.idp.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * An HTTP filter that adds the following headers/values to the {@link HttpServletResponse} and thus, hopefully,
 * prevents caching of the response on all browser.
 * <ul>
 * <li>Expires: 0</li>
 * <li>Cache-Control: no-cache, no-store, must-revalidate, max-age=0
 * <li>
 * <li>Pragma: no-cache</li>
 * </ul>
 */
public class NoCacheFilter implements Filter {

    /** {@inheritDoc} */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Expires", "0");
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
        httpResponse.setHeader("Pragma", "no-cache");
        chain.doFilter(request, response);
    }

    /** {@inheritDoc} */
    public void init(FilterConfig filterConfig) throws ServletException {
        // nothing to do here
    }

    /** {@inheritDoc} */
    public void destroy() {
        // nothing to do here
    }
}