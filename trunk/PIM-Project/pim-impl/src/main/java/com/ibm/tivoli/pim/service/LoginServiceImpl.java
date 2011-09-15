/**
 * 
 */
package com.ibm.tivoli.pim.service;

import java.rmi.RemoteException;
import java.util.Hashtable;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.MessageContext;

import com.ibm.itim.apps.ApplicationException;
import com.ibm.itim.apps.InitialPlatformContext;
import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.jaas.callback.PlatformCallbackHandler;
import com.ibm.tivoli.pim.entity.LoginResponse;

/**
 * @author Administrator
 *
 */
@WebService(endpointInterface = "com.ibm.tivoli.pim.service.LoginService")
public class LoginServiceImpl implements LoginService {

  public static final String SESSION_PASSWORD = "password";

  public static final String SESSION_USERNAME = "username";

  private static final String SESSION_RESPONSE = "login.response";

  private static final String LOGIN_CONTEXT = "ITIM";

  private static Log log = LogFactory.getLog(LoginServiceImpl.class);

  private Hashtable environment = new Hashtable();

  @Resource
  private WebServiceContext webServiceContext;
  
  @Context
  private MessageContext messgeContext;

  /**
   * 
   */
  public LoginServiceImpl() {
    super();
  }

  public Hashtable getEnvironment() {
    return environment;
  }

  public void setEnvironment(Hashtable environment) {
    this.environment = environment;
  }

  public void setWebServiceContext(WebServiceContext webServiceContext) {
    this.webServiceContext = webServiceContext;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pim.service.LoginService#login(java.lang.String, java.lang.String)
   */
  public LoginResponse login(String username, String password) {
    try {
      PlatformContext pcontext = new InitialPlatformContext((Hashtable) this.getEnvironment().clone());
      Subject subject = null;

      // create the ITIM JAAS CallbackHandler
      PlatformCallbackHandler handler = new PlatformCallbackHandler(username, password);
      handler.setPlatformContext(pcontext);

      // Associate the CallbackHandler with a LoginContext,
      // then try to authenticate the user with the platform
      log.info("Logging in...");
      LoginContext lc = new LoginContext(LOGIN_CONTEXT, handler);
      lc.login();
      log.info("Done");

      // Extract the authenticated JAAS Subject from the LoginContext
      log.info("Getting subject... ");
      subject = lc.getSubject();
      
      HttpServletRequest request = getHttpRequest();
      if (request == null) {
         throw new LoginException("Could not found HttpRequest");
      }
      HttpSession session = request.getSession(true);
      if (session == null) {
         throw new LoginException("Could not found HttpSession");
      }
      LoginResponse response = new LoginResponse(LoginResponse.CODE_SUCCESS);
      response.setCn(username);
      response.setUsername(username);

      session.setAttribute(SESSION_USERNAME, username);
      session.setAttribute(SESSION_PASSWORD, password);
      session.setAttribute(SESSION_RESPONSE, response);
      
      return response;
    } catch (RemoteException e) {
      log.error("Fail to login, cause: " + e.getMessage(), e);
      return new LoginResponse("Failure", "Fail to login, cause: " + e.getMessage());
    } catch (ApplicationException e) {
      log.error("Fail to login, cause: " + e.getMessage(), e);
      return new LoginResponse("Failure", "Fail to login, cause: " + e.getMessage());
    } catch (LoginException e) {
      log.error("Fail to login, cause: " + e.getMessage(), e);
      return new LoginResponse("Failure", "Fail to login, cause: " + e.getMessage());
    }
  }

  private HttpServletRequest getHttpRequest() {
    HttpServletRequest request = null;
    if (webServiceContext.getMessageContext() != null) {
       // For SOAP mode
       request = (HttpServletRequest) webServiceContext.getMessageContext().get("HTTP.REQUEST");
    }
    
    if (this.messgeContext != null) {
       // For RESTful mode
       request = this.messgeContext.getHttpServletRequest();
    }
    return request;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pim.service.LoginService#getCurrentUser()
   */
  public LoginResponse getCurrentUser() {
    HttpServletRequest request = getHttpRequest();
    if (request == null) {
      return new LoginResponse("Failure", "Session timeout or no login");
    }
    HttpSession session = request.getSession(false);
    if (session == null) {
      return new LoginResponse("Failure", "Session timeout or no login");
    }
    LoginResponse response = (LoginResponse)session.getAttribute(SESSION_RESPONSE);
    return response;
  }

  public Response logout() {
    HttpServletRequest request = getHttpRequest();
    if (request == null) {
      return Response.ok().build();
    }
    HttpSession session = request.getSession(false);
    if (session == null) {
      return Response.ok().build();
    }
    // Destroy session
    session.removeAttribute(SESSION_USERNAME);
    session.removeAttribute(SESSION_PASSWORD);
    session.removeAttribute(SESSION_RESPONSE);
    session.invalidate();
    return Response.ok().build();
  }

}
