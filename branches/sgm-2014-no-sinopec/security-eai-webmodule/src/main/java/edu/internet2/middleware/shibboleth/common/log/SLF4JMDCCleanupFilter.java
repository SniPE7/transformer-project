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

package edu.internet2.middleware.shibboleth.common.log;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.MDC;

/**
 * A Servlet filter which clears all the MDC state that has been accumulated during the processing of a request. It
 * should be installed as near as possible to the beginning of the effective filter chain - and in particular prior to
 * any filters which make use of MDC in their own logging - so that this filter will be last when the request stack
 * unwinds.
 */
public class SLF4JMDCCleanupFilter implements Filter {

    /** {@inheritDoc} */
    public void destroy() {
    }

    /** {@inheritDoc} */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /** {@inheritDoc} */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }

    }

}