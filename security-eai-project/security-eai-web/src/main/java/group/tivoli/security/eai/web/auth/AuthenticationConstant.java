package group.tivoli.security.eai.web.auth;

/**
 * 认证过程中使用的常量
 * @author xuhong
 * @since 2012-11-18 上午9:07:19
 */

public class AuthenticationConstant {
  
  /**
   * 存放session中，是否通过认证，valeu： true or false
   */
  public static final String SESSION_AUTH_HAS_TAG = "_session_auth_has_";
  
  /**
   * 认证类型：证书方式
   */
  public static final String AUTH_TYPE_CERT = "urn:oasis:names:tc:SAML:2.0:ac:classes:TLSClient";
  
  /**
   * 认证类型：TAM方式
   */
  public static final String AUTH_TYPE_TAM = "urn:oasis:names:tc:SAML:2.0:ac:classes:TAMUsernamePassword";

}
