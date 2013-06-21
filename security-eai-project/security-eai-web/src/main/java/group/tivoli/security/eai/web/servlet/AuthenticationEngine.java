package group.tivoli.security.eai.web.servlet;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import group.tivoli.security.eai.web.sso.PostAuthenticationCallback;
import group.tivoli.security.eai.web.sso.SSOPrincipal;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class AuthenticationEngine
 */
public class AuthenticationEngine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/** Class logger. */
  private final Logger log = LoggerFactory.getLogger(AuthenticationEngine.class);
  
  private static AuthenticationEngine instance = null;
  
  /** 默认 main page name. */
  private String mainPage = "/main.html";
  private final String mainPageInitParam = "mainPage";
  
  private String loginPage = "/auth/userpass";
  
  private String ssoEAIPage = "/sso/eai";
  
  /**
   * EAI认证.
   */
  private boolean eaiAuthen = false;

  
  /** {@inheritDoc} */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    if (getInitParameter(mainPageInitParam) != null) {
      mainPage = getInitParameter(mainPageInitParam);
    }
    
    if (getInitParameter("loginPage") != null) {
      loginPage = getInitParameter("loginPage");
    }
    
    if (getInitParameter("ssoEAIPage") != null) {
      ssoEAIPage = getInitParameter("ssoEAIPage");
    }
    
    String eaiAuthen = getInitParameter("eaiAuthen");
    this.eaiAuthen = (eaiAuthen == null) ? false : Boolean.parseBoolean(eaiAuthen);
    log.info(String.format("[%s]:EAI Authen {[%s]}", this.getServletName(), this.eaiAuthen));
    
    instance = this;
  }

  public static AuthenticationEngine getInstance() {
    return instance;
  }

  /**
   * @see HttpServlet#HttpServlet()
   */
  public AuthenticationEngine() {
      super();
      // TODO Auto-generated constructor stub
  }
  
  
  /** {@inheritDoc} */
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    //String url = (String)request.getAttribute(SSOPrincipal.URL_REQUEST_TARGET_TAG);
    //逻辑判断应该以何种方式认证
    forwardRequest(this.loginPage, request, response);
  }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  //httpResponse.sendRedirect(httpRequest.getContextPath() + this.AUTH_PAGE);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	 /**
   * Forwards a request to the given path.
   * 
   * @param forwardPath
   *          path to forward the request to
   * @param httpRequest
   *          current HTTP request
   * @param httpResponse
   *          current HTTP response
   */
  protected void forwardRequest(String forwardPath, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
    try {
      
      RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(forwardPath);
      dispatcher.forward(httpRequest, httpResponse);
      
    } catch (IOException e) {
      log.error("Unable to return control back to authentication engine", e);
    } catch (ServletException e) {
      log.error("Unable to return control back to authentication engine", e);
    }
  }
  
  /**
   * Returns control back to the authentication engine.
   * 
   * @param httpRequest
   *          current HTTP request
   * @param httpResponse
   *          current HTTP response
   */
  public void returnToAuthenticationEngine(ServletContext context, HttpServletRequest httpRequest,
      HttpServletResponse httpResponse) {
        
    log.debug("Returning control to authentication engine");
    
    httpRequest.getSession(true).setAttribute(LoginHandler.SUBJECT_KEY, httpRequest.getAttribute(LoginHandler.SUBJECT_KEY));
    httpRequest.getSession(true).setAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY, httpRequest.getAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY));
    
    if (this.eaiAuthen && checkSSOEAI(httpRequest, getRequestUrl(httpRequest))) {
      doSSOAction(httpRequest, httpResponse);
      //清理认证方式，确保下次webseal认证请求来时是登陆界面。否则需要制作注销
      httpRequest.getSession(true).removeAttribute(LoginHandler.SUBJECT_KEY);
      httpRequest.getSession(true).removeAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY);
    }
    
    //转向到请求页面
    forwardRequest(getRequestUrl(httpRequest), httpRequest, httpResponse);
  }
  
  private String getRequestUrl(HttpServletRequest httpRequest){
    return (String)httpRequest.getAttribute(SSOPrincipal.URL_REQUEST_TARGET_TAG);
  }
  
  private boolean checkSSOEAI(HttpServletRequest httpRequest, String url){
    boolean isSSO = false;
    String checkUrl = httpRequest.getContextPath() + this.ssoEAIPage;
    if(checkUrl.equals(url)){
      isSSO = true;
    }
    
    return isSSO;
  }
  
  private void doSSOAction(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
    // Already authentication, call post
    try {

      // Set return params for WebSEAL EAI
      PostAuthenticationCallback handler = (PostAuthenticationCallback) WebApplicationContextUtils
          .getWebApplicationContext(getServletContext()).getBean("siam.sp.webseal.eai.handler");
      handler.handle(httpRequest, httpResponse);
    } catch (BeansException e) {
      log.warn(String.format("[%s]:Could not found WebSEAL EAI decorator, EAI feature disabled", this.getServletName()));
    } catch (Exception e) {
      log.error(
          String.format("[%s]:Fail to execute Post-Authenticatoin handler, cause: [%s]", this.getServletName(),
              e.getMessage()), e);
    }
  }

}
