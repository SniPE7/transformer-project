package group.tivoli.security.eai.web.audit;

import java.sql.Types;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 向ITIM 数据库 记录 审计日志 服务实现类
 * 
 * @author xuhong
 * @since 2012-6-21 上午9:47:27
 */

public class TimDBUserAuditServiceImpl implements UserAuditService {
  private Log log = LogFactory.getLog(this.getClass().getName());

  private JdbcTemplate jdbcTemplate;

  /**
   * @return the jdbcTemplate
   */
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  /**
   * @param jdbcTemplate
   *          the jdbcTemplate to set
   */
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public TimDBUserAuditServiceImpl() {
    super();
  }
  
  private static final String EVENT_TYPE = "EAI";
  private final static String INSERT_AUDIT_EVENT_LOGIN = 
      "INSERT INTO AUDIT_EVENT (ID, ITIM_EVENT_CATEGORY, ACTION, TIMESTAMP, ENTITY_TYPE, RESULT_SUMMARY, COMMENTS, ENTITY_NAME) VALUES " + 
      "(?,'" + EVENT_TYPE + "', 'Authenticate',  current timestamp  , 'Basic', ?, ?, ?)";

  @Override
  public void loginFailure(AuditEntry auditObj) {
    String RESULT_SUMMARY = "FAILURE";
    
    if (log.isDebugEnabled()) {
      log.debug(String.format("Audit user login action , loginFailure , username[%s]: ip:%s", auditObj.getUid(),
          auditObj.getIp()));
    }
    
    int res = jdbcTemplate.update(INSERT_AUDIT_EVENT_LOGIN, new Object[] { RandomStringUtils.randomNumeric(18), RESULT_SUMMARY, auditObj.getIp(), auditObj.getUid() }, 
        new int[] { Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });

    if (res == 1) {
      if (log.isDebugEnabled()) {
        log.debug(String.format("Audit user login action succeed, loginFailure , username[%s]: ip:%s",
            auditObj.getUid(), auditObj.getIp()));
      }
     //return true;
    }
   
    //return false;
  }

  @Override
  public void loginSuccess(AuditEntry auditObj) {
    String RESULT_SUMMARY = "SUCCESS";
    
    if (log.isDebugEnabled()) {
      log.debug(String.format("Audit user login action , loginSuccess , username[%s]: ip:%s", auditObj.getUid(),
          auditObj.getIp()));
    }
    
    int res = jdbcTemplate.update(INSERT_AUDIT_EVENT_LOGIN, new Object[] { RandomStringUtils.randomNumeric(18), RESULT_SUMMARY, auditObj.getIp(), auditObj.getUid() }, 
        new int[] { Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });

    if (res == 1) {
      if (log.isDebugEnabled()) {
        log.debug(String.format("Audit user login action succeed, loginSuccess , username[%s]: ip:%s",
            auditObj.getUid(), auditObj.getIp()));
      }
     //return true;
    }
  
    //return false;
  }
}

