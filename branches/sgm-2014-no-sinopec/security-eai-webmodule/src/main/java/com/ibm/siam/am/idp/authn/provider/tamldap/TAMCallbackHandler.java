package com.ibm.siam.am.idp.authn.provider.tamldap;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.kaptcha.Constants;
import com.ibm.siam.am.idp.authn.module.MobileUpdateOperationStatusCallback;
import com.ibm.siam.am.idp.authn.provider.FormOperationCallback;
import com.ibm.siam.am.idp.authn.provider.KaptachaCallback;
import com.ibm.siam.am.idp.authn.provider.LastAuthenticatedPrincipalCallback;
import com.ibm.siam.am.idp.authn.provider.PasswordHintQuestionAndAnswerCallback;
import com.ibm.siam.am.idp.authn.provider.PasswordUpdateOperationStatusCallback;
import com.ibm.siam.am.idp.authn.provider.RequestCallback;
import com.ibm.siam.am.idp.authn.provider.ResponseCallback;
import com.ibm.siam.am.idp.authn.provider.SMSCodeCallback;
import com.ibm.siam.am.idp.authn.util.dyncpwd.DyncUtil;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import edu.internet2.middleware.shibboleth.idp.session.Session;

/**
 * 用于Handle TAM用户的登录, 支持用户名、密码、验证码方式的登录。
 * 
 * @author zhangxianwen
 * @since 2012-6-14 下午4:23:16
 */

public class TAMCallbackHandler implements CallbackHandler {
  
  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(TAMCallbackHandler.class);

  /** Form表单操作标识属性名称 */
  private String formOptFlagAttribute = "op";
  
  /** Form表单验证码属性名称 */
  private String formCheckCodeAttribute = "j_checkcode";
  
  /** Form表单验证码属性名称 */
  private String formSMSCodeAttribute = "j_smscode";
  
  private String formMatchCodeAttribute = "j_matchcode";


  
  /** Name of the user. */
  private String username;

  /** User's password. */
  private String password;

  /** HttpServletRequest会话对象 */
  private HttpServletRequest request;

	private HttpServletResponse response;

  /**
   * Constructor
   * 
   * @param username
   *          用户名
   */
  protected TAMCallbackHandler(String username) {
    this.username = username;
  }

  /**
   * Constructor
   * 
   * @param username
   *          用户名
   * @param password
   *          用户密码
   */
  protected TAMCallbackHandler(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Constructor
   * 
   * @param username
   *          用户名
   * @param password
   *          用户密码
   * @param request
   * @param response
   */
  public TAMCallbackHandler(String username, String password, HttpServletRequest request, HttpServletResponse response) {
    super();
    this.username = username;
    this.password = password;
    this.request = request;
    this.response = response;
  }
  
  public TAMCallbackHandler(String username, String password, HttpServletRequest request) {
      super();
      this.username = username;
      this.password = password;
      this.request = request;
    }

  /** {@inheritDoc} */
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    if (callbacks == null || callbacks.length == 0) {
      return;
    }

    for (Callback cb : callbacks) {
      if (cb instanceof NameCallback) {
        NameCallback ncb = (NameCallback) cb;
        ncb.setName(this.username);
      } else if (cb instanceof PasswordCallback) {
        PasswordCallback pcb = (PasswordCallback) cb;
        pcb.setPassword(this.password.toCharArray());
      } else if (cb instanceof RequestCallback) {
        RequestCallback rqcb = (RequestCallback) cb;
        rqcb.setRequest(request);
      } else if (cb instanceof ResponseCallback) {
      	ResponseCallback rqcb = (ResponseCallback) cb;
        rqcb.setResponse(response);
      } else if (cb instanceof KaptachaCallback) {
        KaptachaCallback kcp = (KaptachaCallback)cb;
        HttpSession session = request.getSession(false);
        
        if(log.isDebugEnabled()){
          log.debug(String.format("Kaptacha Callback session is %s: ", session));
        }
        
        if (session != null) {
          if(log.isDebugEnabled()){
            log.debug(String.format("Kaptacha Callback code is : %s", session.getAttribute(Constants.KAPTCHA_SESSION_KEY)));
          }
           kcp.setKaptachaCode((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY));
        }
        kcp.setCodeFromInput(request.getParameter(formCheckCodeAttribute));
      } else if (cb instanceof SMSCodeCallback) {
    	  SMSCodeCallback smscb = (SMSCodeCallback)cb;
    	  
    	  smscb.setSMSCode(DyncUtil.getPassword(this.username, this.password));
          smscb.setCodeFromInput(request.getParameter(formSMSCodeAttribute));
          
          //matchcode
          smscb.setMatchCode(request.getParameter(formMatchCodeAttribute));
          
        } else if (cb instanceof FormOperationCallback) {
        FormOperationCallback fcb = (FormOperationCallback)cb;
        fcb.setOperation(request.getParameter(formOptFlagAttribute));
      } else if (cb instanceof PasswordUpdateOperationStatusCallback) {
        PasswordUpdateOperationStatusCallback fcb = (PasswordUpdateOperationStatusCallback)cb;
        HttpSession session = request.getSession(false);
        if (session != null) {
          String v = (String)session.getAttribute(LoginHandler.PRINCIPAL_UPDATE_PASSWORD_SUCCESS_KEY);
          if ("true".equalsIgnoreCase(v)) {
             fcb.setPasswordUpdated(true);
          }
        }
      } else if (cb instanceof MobileUpdateOperationStatusCallback) {
    	  MobileUpdateOperationStatusCallback fcb = (MobileUpdateOperationStatusCallback)cb;
          HttpSession session = request.getSession(false);
          if (session != null) {
            String v = (String)session.getAttribute(LoginHandler.PRINCIPAL_UPDATE_MOBILE_SUCCESS_KEY);
            if ("true".equalsIgnoreCase(v)) {
               fcb.setMobileUpdated(true);
            }
          }
        } else if  (cb instanceof PasswordHintQuestionAndAnswerCallback) {
        PasswordHintQuestionAndAnswerCallback pcb = (PasswordHintQuestionAndAnswerCallback)cb;
        pcb.setUsername(request.getParameter("j_username"));
        pcb.setQuestion(request.getParameter("hintQuestion"));
        pcb.setAnswer(request.getParameter("hintAnswer"));
      } else if  (cb instanceof LastAuthenticatedPrincipalCallback) {
        LastAuthenticatedPrincipalCallback pcb = (LastAuthenticatedPrincipalCallback)cb;
        Session idpSession = (Session) request.getAttribute(Session.HTTP_SESSION_BINDING_ATTRIBUTE);
        if (idpSession != null) {
          pcb.setPrincipalName(idpSession.getPrincipalName());
        }
      }
    }
  }

}
