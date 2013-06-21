/**
 * 
 */
package group.tivoli.security.eai.web.sso;

import java.util.List;

/**
 * @author zhaodonglu
 *
 */
public interface AuthenLevelDirector {
  
  /**
   * 返回一个匹配AuthenLevel的认证方法
   * <p>如果没有对应认证方法返回null ；如果对应认证方法列表为空返回""；如果认证级别对应多个认证方法，则取第一个</p>
   * @param authenLevel 认证级别
   * @return
   */
  public String getMatachedAuthenMethod(String authenLevel);
  
  /**
   * 返回一个匹配AuthenLevel的认证方法列表
   * <p>如果没有对应认证方法列表返回null</p>
   * @param authenLevel 认证级别
   * @return
   */
  public List<String> getMatachedAuthenMethodList(String authenLevel);
  
  /**
   * 查询认证方法对应的认证级别
   * @param authenMethod
   * @return
   */
  public String getAuthenLevel(String authenMethod);

}
