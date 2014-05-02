package com.ibm.siam.am.idp.authn;

import java.util.HashMap;
import java.util.Map;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;

public class LoginHandlerManager {

	private Map<String, LoginHandler> loginHandlers = new HashMap<String, LoginHandler>();
	
	public LoginHandlerManager() {
		super();
	}

	public LoginHandlerManager(Map<String, LoginHandler> loginHandlers) {
	  super();
	  this.loginHandlers = loginHandlers;
  }

	public Map<String, LoginHandler> getLoginHandlers() {
		return loginHandlers;
	}

	public void setLoginHandlers(Map<String, LoginHandler> loginHandlers) {
		this.loginHandlers = loginHandlers;
	}

}
