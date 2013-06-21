/**
 * 
 */
package group.tivoli.security.eai.web.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.internet2.middleware.shibboleth.common.profile.ProfileException;


/**
 * @author zhaodonglu
 *
 */
public interface PostAuthenticationCallback {
  
  /**
   * @param context
   * @param request
   * @param response
   * @throws Exception
   */
  public void handle(HttpServletRequest request, HttpServletResponse response) throws ProfileException;
  
}
