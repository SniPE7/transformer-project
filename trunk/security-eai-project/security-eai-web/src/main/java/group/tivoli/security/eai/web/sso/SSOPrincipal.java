package group.tivoli.security.eai.web.sso;

import java.util.List;

/**
 * 登录用户Princial
 * @author zhangxianwen
 * @since 2012-7-16 下午4:59:15
 */

public interface SSOPrincipal {

  /** Session会话中存储登录用户信息KEY */
  public final static String NAME_OF_SESSION_ATTR = "_SSO_USER_PRINCIPAL_";
  
  /** Session会话中存储登录用户最后认证级别KEY */
  public final static String LAST_REQUIRED_AUTHEN_LEVEL_OF_SESSION_ATTR = "_SSO_USER_LAST_AUTHEN_LEVEL_";
  
  /** Request中存储本次访问链接 */
  public final static String URL_REQUEST_TARGET_TAG = "_SSO_URL_TARGET_";

  /**
   * 获取统一ID
   * @return String
   */
  public abstract String getUid();

  /**
   * 获取用户姓名
   * @return String
   */
  public abstract String getCn();

  /**
   * 获取最后一次认证方法
   * <p>如果无认证方法，则返回""</p>
   * @return String
   */
  public String getLastAuthenMethod();
  
  /**
   * 是否包含指定认证方法
   * <p>如果已经通过指定的认证方法认证，返回true</p>
   * @param authenMethod
   * @return
   */
  public boolean containsAuthenMethod(String authenMethod);
  
  /**
   * @return
   */
  public String getMaxSucceedAuthenLevel();
  
  /**
   * @param successAuthenLevel
   */
  public void addSuccessAuthenLevel(String successAuthenLevel);
  
  /**
   * 获取所有属性名列表集
   * <p>如果属性名集为空,刚list.size=0</p>
   * @return List<String>
   */
  public abstract List<String> getAttributeNames();
  
  /**
   * 根据属性名获取属性值
   * <p>属性值总是String类型, 日期类型将采用yyyy-MM-dd HH:mm:ss:s.SSSZ格式, 例如：2012-09-14 16:56:02:2.078+0800</p>
   * @param attrName 属性名称
   * @return String
   */
  public abstract String getSingleValue(String attrName);
  
  /**
   * 根据属性名获取属性值
   * <p>如果属性值为空,则list.size=0</p>
   * <p>属性值总是String类型, 日期类型将采用yyyy-MM-dd HH:mm:ss:s.SSSZ格式, 例如：2012-09-14 16:56:02:2.078+0800</p>
   * @param attrName 属性名称
   * @return List<String>
   */
  public abstract List<String> getValueAsList(String attrName);

  /**
   * @param authenMethod
   */
  public abstract void addSuccessAuthenMethod(String authenMethod);

}
