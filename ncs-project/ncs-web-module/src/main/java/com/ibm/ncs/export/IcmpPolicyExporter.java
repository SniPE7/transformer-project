/**
 * 
 */
package com.ibm.ncs.export;

import java.io.Writer;
import java.sql.Connection;

/**
 * @author ZhaoDongLu
 *
 */
public interface IcmpPolicyExporter {
  /**
 * 注入JDBC Connection
   * @param connection
   */
  public void setJdbcConnection(Connection connection);
  
  /**
   * 设置生成XML所需的IMPACE Server ID, ȱʡΪBF
   * @param serverID
   */
  public void setServerID(String serverID);
  
  /**
    * @param output 输出XML文件
   * @param log 输出处理过程信息, 供Web界面显示运行过程
   * @throws Exception
   */
  public void export(Writer output, Writer log) throws Exception;
}
