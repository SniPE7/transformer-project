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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;

/** Base class for authentication handlers. */
public abstract class AbstractLoginHandler implements LoginHandler {

	/** Authentication methods this handler supports. */
	private List<String> supportedAuthenticationMethods;

	/**
	 * Length of time, in milliseconds, after which a user should be
	 * re-authenticated.
	 */
	private long authenticationDuration;

	/** Whether this handler supports forced re-authentication. */
	private boolean supportsForceAuthentication;

	/** Whether this handler supports passive authentication. */
	private boolean supportsPassive;

	/**
	 * Get the authentication strength level, the lowest level is 1.
	 */
	private int authenticationLevel = 1;

	/** The context-relative path of the servlet used to perform authentication. */
	private String authenticationServletPath;

	/** Constructor. */
	protected AbstractLoginHandler() {
		supportedAuthenticationMethods = new ArrayList<String>();
		supportsForceAuthentication = false;
		supportsPassive = false;
	}

    /**
   * Constructor.
   * 
   * @param authenticationServletPath
   */
    public AbstractLoginHandler(String authenticationServletPath) {
      this();
      this.authenticationServletPath = authenticationServletPath;
    }

    public String getAuthenticationServletPath() {
        return authenticationServletPath;
    }

    public void setAuthenticationServletPath(String authenticationServletPath) {
        this.authenticationServletPath = authenticationServletPath;
    }

	/** {@inheritDoc} */
	public List<String> getSupportedAuthenticationMethods() {
		return supportedAuthenticationMethods;
	}

	public boolean isSupportsForceAuthentication() {
		return supportsForceAuthentication;
	}

	public boolean isSupportsPassive() {
		return supportsPassive;
	}

	public void setSupportedAuthenticationMethods(List<String> supportedAuthenticationMethods) {
		this.supportedAuthenticationMethods = supportedAuthenticationMethods;
	}

	/** {@inheritDoc} */
	public long getAuthenticationDuration() {
		return authenticationDuration;
	}

	/**
	 * Sets the length of time, in milliseconds, after which a user should be
	 * re-authenticated.
	 * 
	 * @param duration
	 *          length of time, in milliseconds, after which a user should be
	 *          re-authenticated
	 */
	public void setAuthenticationDuration(long duration) {
		authenticationDuration = duration;
	}

	/**
	 * Sets the length of time, in milliseconds, after which a user should be
	 * re-authenticated.
	 * 
	 * @param duration
	 *          length of time, in milliseconds, after which a user should be
	 *          re-authenticated
	 * 
	 * @deprecated use {@link #setAuthenticationDuration(long)}
	 */
	public void setAuthenticationDurection(long duration) {
		authenticationDuration = duration;
	}

	/** {@inheritDoc} */
	public boolean supportsForceAuthentication() {
		return supportsForceAuthentication;
	}

	/**
	 * Sets whether this handler supports forced re-authentication.
	 * 
	 * @param supported
	 *          whether this handler supports forced re-authentication
	 */
	public void setSupportsForceAuthentication(boolean supported) {
		supportsForceAuthentication = supported;
	}

	/** {@inheritDoc} */
	public boolean supportsPassive() {
		return supportsPassive;
	}

	/**
	 * Sets whether this handler supports passive authentication.
	 * 
	 * @param supported
	 *          whether this handler supports passive authentication.
	 */
	public void setSupportsPassive(boolean supported) {
		supportsPassive = supported;
	}

	public int getAuthenticationLevel() {
		return authenticationLevel;
	}

	public void setAuthenticationLevel(int authenticationLevel) {
		this.authenticationLevel = authenticationLevel;
	}

	public String setAuthnServletUrl(String authnServletUrl, HttpServletRequest httpRequest) {
		StringBuffer buffer = new StringBuffer(authnServletUrl);
		String spAuthentication = null;
		String authenTypes = null;
		String currentAuthen = null;
		if (httpRequest.getAttribute("authenTypes") != null) {
			try {
				authenTypes = URLEncoder.encode(httpRequest.getAttribute("authenTypes").toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("fail to encode url: " + httpRequest.getAttribute("authenTypes").toString(), e);
			}
			if (authnServletUrl.indexOf("?") > 0) {
				buffer.append("&authenTypes=");
			} else {
				buffer.append("?authenTypes=");
			}
			buffer.append(authenTypes);
		}

		if (httpRequest.getAttribute("spAuthentication") != null) {
			spAuthentication = httpRequest.getAttribute("spAuthentication").toString();
			if (spAuthentication != "" || !spAuthentication.equals("")) {
				buffer.append("&spAuthentication=").append(spAuthentication);
			}
		}
		if (httpRequest.getAttribute("currentAuthen") != null) {
			currentAuthen = httpRequest.getAttribute("currentAuthen").toString();
			if (currentAuthen != "" || !currentAuthen.equals("")) {
				buffer.append("&currentAuthen=").append(currentAuthen);
			}
		}

		return buffer.toString();
	}
}
