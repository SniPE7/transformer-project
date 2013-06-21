package group.tivoli.security.eai.web.audit;

/**
 * 审计提交的消息对象体
 * @author xuhong
 * @since 2012-12-5 下午10:30:22
 */

public class AuditEntry {
  private String uid;
  private String ip;
  
  public String getUid() {
    return uid;
  }
  public void setUid(String uid) {
    this.uid = uid;
  }
  public String getIp() {
    return ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
}
