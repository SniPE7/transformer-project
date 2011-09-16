package com.ibm.tivoli.pim.callback;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import com.ibm.itim.apps.PlatformContext;
import com.ibm.tivoli.pim.entity.User;

public interface SubjectCallbackHandler {

  public abstract Subject getSubject(String LOGIN_CONTEXT, PlatformContext platformContext, HttpServletRequest request, User user) throws LoginException;

}