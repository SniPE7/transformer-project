package group.tivoli.security.eai.web.auth.module.login;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.code.kaptcha.Constants;
import com.sinopec.siam.am.idp.authn.provider.FormOperationCallback;
import com.sinopec.siam.am.idp.authn.provider.KaptachaCallback;
import com.sinopec.siam.am.idp.authn.provider.LastAuthenticatedPrincipalCallback;
import com.sinopec.siam.am.idp.authn.provider.PasswordHintQuestionAndAnswerCallback;
import com.sinopec.siam.am.idp.authn.provider.PasswordUpdateOperationStatusCallback;
import com.sinopec.siam.am.idp.authn.provider.RequestCallback;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;

/**
 * 用于Handle TAM用户的登录, 支持用户名、密码、验证码方式的登录。
 * 
 * @author zhangxianwen
 * @since 2012-6-14 下午4:23:16
 */

public class LoginCallbackHandler implements CallbackHandler {

  /** Form表单操作标识属性名称 */
  private String formOptFlagAttribute = "op";

  /** Form表单验证码属性名称 */
  private String formCheckCodeAttribute = "j_checkcode";

  /** Name of the user. */
  private String username;

  /** User's password. */
  private String password;

  /** HttpServletRequest会话对象 */
  private HttpServletRequest request;

  /**
   * Constructor
   * 
   * @param username
   *          用户名
   */
  public LoginCallbackHandler(String username) {
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
  public LoginCallbackHandler(String username, String password) {
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
  public LoginCallbackHandler(String username, String password, HttpServletRequest request) {
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

      } else if (cb instanceof KaptachaCallback) {
        KaptachaCallback kcp = (KaptachaCallback) cb;

        HttpSession session = request.getSession(false);
        if (session != null) {
          kcp.setKaptachaCode((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY));
        }
        kcp.setCodeFromInput(request.getParameter(formCheckCodeAttribute));

      } else if (cb instanceof FormOperationCallback) {
        FormOperationCallback fcb = (FormOperationCallback) cb;

        fcb.setOperation(request.getParameter(formOptFlagAttribute));
      } else if (cb instanceof PasswordUpdateOperationStatusCallback) {

        PasswordUpdateOperationStatusCallback fcb = (PasswordUpdateOperationStatusCallback) cb;
        HttpSession session = request.getSession(false);

        if (session != null) {
          String v = (String) session.getAttribute(LoginHandler.PRINCIPAL_UPDATE_PASSWORD_SUCCESS_KEY);
          if ("true".equalsIgnoreCase(v)) {
            fcb.setPasswordUpdated(true);
          }
        }
      } else if (cb instanceof PasswordHintQuestionAndAnswerCallback) {

        PasswordHintQuestionAndAnswerCallback pcb = (PasswordHintQuestionAndAnswerCallback) cb;
        pcb.setUsername(request.getParameter("j_username"));
        pcb.setQuestion(request.getParameter("hintQuestion"));
        pcb.setAnswer(request.getParameter("hintAnswer"));

      } else if (cb instanceof LastAuthenticatedPrincipalCallback) {

        /*LastAuthenticatedPrincipalCallback pcb = (LastAuthenticatedPrincipalCallback) cb;

        Session idpSession = (Session) request.getAttribute(Session.HTTP_SESSION_BINDING_ATTRIBUTE);
        if (idpSession != null) {
          pcb.setPrincipalName(idpSession.getPrincipalName());
        }*/
        
      }/* else if (cb instanceof HttpSessionPrincipalCallback) {

        HttpSessionPrincipalCallback pcb = (HttpSessionPrincipalCallback) cb;

        HttpSession session = request.getSession(false);
        if (session != null) {
          pcb.setPrincipalName((String) session.getAttribute(LoginHandler.PRINCIPAL_NAME_KEY));
          pcb.setPrincipalDn((String) session.getAttribute(LoginHandler.PRINCIPAL_DN_KEY));

          Object password = session.getAttribute(LoginHandler.PRINCIPAL_PASSWORD_KEY);
          if (password != null && password instanceof char[]) {
            pcb.setPrincipalPassword(new String((char[]) password));
          }
        }
      }*/
    }
  }

}
