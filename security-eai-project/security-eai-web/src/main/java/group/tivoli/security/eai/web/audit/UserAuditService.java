package group.tivoli.security.eai.web.audit;

/**
 * 用户登录审计接口
 * @author xuhong
 * @since 2012-12-5 下午10:22:08
 */

public interface UserAuditService {
  public void loginFailure(AuditEntry auditObj);
  
  public void loginSuccess(AuditEntry auditObj);
  
}
