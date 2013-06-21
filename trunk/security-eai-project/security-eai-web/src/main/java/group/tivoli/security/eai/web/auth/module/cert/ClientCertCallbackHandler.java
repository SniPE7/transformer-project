package group.tivoli.security.eai.web.auth.module.cert;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;

import com.sinopec.siam.am.idp.authn.provider.RequestCallback;

/**
 * @description 用来与最终用户通信的 CallbackHandler
 * @author zhangxianwen
 * @version 1.0
 * @Copyright (c) 2012
 * @create 2012-3-13 下午3:49:14
 */

public class ClientCertCallbackHandler implements CallbackHandler {

  /* 客户端提交证书 */
  X509Certificate clientCert;
  
  /** HttpServletRequest会话对象 */
  private HttpServletRequest request;
  
  public ClientCertCallbackHandler(X509Certificate clientCert){
    this.clientCert = clientCert;
  }
  
  public ClientCertCallbackHandler(X509Certificate clientCert, HttpServletRequest request){
    this.clientCert = clientCert;
    this.request = request;
  }
  
  /**
   * Handle a callback.
   * 
   * @param callbacks
   *          The list of callbacks to process.
   * 
   * @throws UnsupportedCallbackException
   *           If callbacks has a callback other than {@link NameCallback} or
   *           {@link PasswordCallback}.
   */
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    if (callbacks == null || callbacks.length == 0) {
      return;
    }

    for (Callback cb : callbacks) {
      if (cb instanceof ClientCertCallback) {
        ClientCertCallback certcb = (ClientCertCallback) cb;
        certcb.setClientCert(clientCert);
      } else if (cb instanceof RequestCallback) {
        RequestCallback rqcb = (RequestCallback) cb;
        rqcb.setRequest(request);
      }
    }
  }

}