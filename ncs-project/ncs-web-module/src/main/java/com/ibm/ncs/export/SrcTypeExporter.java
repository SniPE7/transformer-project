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
public interface SrcTypeExporter {
  /**
   * 注入JDBC Connection
   * @param connection
   */
  public void setJdbcConnection(Connection connection);
  
  /**
   * @param output 输出XML文件
   * @param log 输出处理过程信息, 供Web界面显示运行过程
   * @throws Exception
   */
  public void export(Writer output, Writer log) throws Exception;
}
