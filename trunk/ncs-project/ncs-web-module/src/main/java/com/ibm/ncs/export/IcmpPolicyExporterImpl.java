/**
 * 
 */
package com.ibm.ncs.export;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author ZhaoDongLu
 *
 */
public class IcmpPolicyExporterImpl implements IcmpPolicyExporter {

  private String serverID = "BF";
  private Connection connection = null;

  /**
   * 
   */
  public IcmpPolicyExporterImpl() {
    super();
  }

  public String getServerID() {
    return serverID;
  }

  public void setServerID(String serverID) {
    this.serverID = serverID;
  }

  public void export(Writer outputWriter, Writer log) throws Exception {
    if (log == null) {
       log = new OutputStreamWriter(System.out);
    }
    PrintWriter output = new PrintWriter(outputWriter);
    output.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    output.println("<profile>");
    
    output.println("  <icmp>");
    // 输出设备生效策略
    String sql4Device = "select * from v_device_icmp_policy_export where moduleName='icmp' order by devip asc, eventTypeMajor";
    List<DevicePolicyRecord> deviceRecords = this.search(DevicePolicyRecord.class, sql4Device);
    for (DevicePolicyRecord r: deviceRecords) {
      if (r.getDevip() != null) {
        r.toIcmpXML(output, serverID);
        output.flush();
      }
    }
    // 输出端口生效策略
    String sql4Port = "select * from v_port_icmp_policy_export where moduleName='icmp' order by devip asc, ifindex asc, eventTypeMajor";
    List<PortPolicyRecord> portRecords = this.search(PortPolicyRecord.class, sql4Port);
    for (PortPolicyRecord r: portRecords) {
      if (r.getIfip() != null) {
        r.toIcmpXML(output, serverID);
        output.flush();
      }
    }
    output.println("  </icmp>");
    
    output.println("</profile>");
    output.flush();
  }
  
  /**
   * Retrieve from database and assign table field into object.
   * @param <T>
   * @param clazz
   * @param sql
   * @return
   * @throws SQLException 
   * @throws InstantiationException 
   * @throws IllegalAccessException 
   * @throws NoSuchMethodException 
   * @throws InvocationTargetException 
   */
  private <T> List<T> search(Class<T> clazz, String sql) throws SQLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    List<T> result = new ArrayList<T>();
    Statement statement = this.connection.createStatement();
    ResultSet rs = statement.executeQuery(sql);
    while (rs.next()) {
          T record = clazz.newInstance();
          this.copyValues(rs, record);
          result.add(record);
    }
    rs.close();
    statement.close();
    return result;
  }
  
  private void copyValues(ResultSet rs, Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException {
    Map<String, Object> map = BeanUtils.describe(object);
    for (String fieldName: map.keySet()) {
        Object value;
        try {
          value = rs.getObject(fieldName);
          BeanUtils.setProperty(object, fieldName, value);
        } catch (SQLException e) {
          // Ignore error
        }
    }
  }

  public void setJdbcConnection(Connection connection) {
    this.connection  = connection;
  }

}
