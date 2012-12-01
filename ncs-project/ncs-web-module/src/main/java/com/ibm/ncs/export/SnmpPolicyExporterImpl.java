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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author ZhaoDongLu
 * 
 */
public class SnmpPolicyExporterImpl implements SnmpPolicyExporter {

  private String snmpPort = "161";
  private String serverID = "BF";
  private Connection connection = null;

  /**
   * Name of table T_OID_GROUP_DETAILS_INIT
   */
  private String oidGroupDetailTableName = "t_oidgroup_details_init";

  /**
   * 
   */
  public SnmpPolicyExporterImpl() {
    super();
  }

  public String getSnmpPort() {
    return snmpPort;
  }

  public void setSnmpPort(String snmpPort) {
    this.snmpPort = snmpPort;
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
    
    // Clear unique set
    oidGroupNames.clear();
    
    PrintWriter output = new PrintWriter(outputWriter);
    output.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    output.println("<profile>");

    output.println("  <snmp>");
    // 输出设备生效策略
    output.println("    <!-- Devices -->");
    String sql4Device = "select * from v_device_snmp_policy_export where moduleName='snmp' order by devip asc, eventTypeMajor";
    List<DevicePolicyRecord> deviceRecords = this.search(DevicePolicyRecord.class, sql4Device);
    for (DevicePolicyRecord r : deviceRecords) {
      if (r.getDevip() != null) {
        r.toSnmpXML(output, snmpPort, serverID);
        output.flush();
      }
    }
  // 输出端口生效策略
    output.println("    <!-- Ports -->");
    String sql4Port = "select * from v_port_snmp_policy_export where moduleName='snmp' order by devip asc, ifindex asc, eventTypeMajor";
    List<PortPolicyRecord> portRecords = this.search(PortPolicyRecord.class, sql4Port);
    for (PortPolicyRecord r : portRecords) {
      if (r.getDevip() != null) {
        r.toSnmpXML(output, snmpPort, serverID);
        output.flush();
      }
    }

  // 输出端口生效策略
    output.println("    <!-- Predef MIB -->");
    String sql4PredefMIB = "select * from v_predefmib_snmp_policy_export where moduleName='snmp' order by devip asc, eventTypeMajor";
    List<PredefMIBPolicyRecord> mibRecords = this.search(PredefMIBPolicyRecord.class, sql4PredefMIB);
    for (PredefMIBPolicyRecord r : mibRecords) {
      r.toSnmpXML(output, snmpPort, serverID);
    }
    output.println("  </snmp>");

    output.println("  <oidgroups>");
    output.println("    <!-- Devices -->");
    // 输出设备策略相关的OIDGroup
    for (DevicePolicyRecord r : deviceRecords) {
      toOIDGroupXML(output, r);
    }
    // 输出端口策略相关的OIDGroup
    output.println("    <!-- Ports -->");
    for (PortPolicyRecord r : portRecords) {
      toOIDGroupXML(output, r);
    }
    // 输出私有MIB策略相关的OIDGroup
    output.println("    <!-- Predef MIB -->");
    for (PredefMIBPolicyRecord r : mibRecords) {
      toOIDGroupXML(output, r);
    }
    output.println("  </oidgroups>");
    output.println("</profile>");
    output.flush();
  }
  
  private Set<String> oidGroupNames = new HashSet<String>();
  private void toOIDGroupXML(PrintWriter output, PortPolicyRecord r) throws SQLException {
    String sql = "select * from " + oidGroupDetailTableName + " ogd where ogd.opid=" + r.getOpid() + " order by oidindex asc";
    Statement statement = this.connection.createStatement();
    ResultSet rs = statement.executeQuery(sql);
    String oidGroupName = r.getOidGroupName() + "_" + r.getIfindex();
    if (!oidGroupNames.contains(oidGroupName)) {
      oidGroupNames.add(oidGroupName);
      output.println("  <oidgroup oidgroupname=\"" + oidGroupName + "\">");
      while (rs.next()) {
        String oidValue = rs.getString("oidvalue");
        String oidName = rs.getString("oidname");
        String oidUnit = rs.getString("oidunit");
        int oidFlag = rs.getInt("flag");
        int oidIndex = rs.getInt("oidindex");
        output.println("      <oid>");
        output.println("        <oidvalue>" + oidValue + ((oidFlag == 1) ? "." + r.getIfindex() : "") + "</oidvalue>");
        output.println("        <oidname>" + oidName + "</oidname>");
        output.println("        <oidunit>" + oidUnit + "</oidunit>");
        output.println("        <oidselector />");
        output.println("      </oid>");
        output.flush();
      }
      output.println("  </oidgroup>");
    }
    rs.close();
    statement.close();
  }

  private void toOIDGroupXML(PrintWriter output, DevicePolicyRecord r) throws SQLException {
    String sql = "select * from " + oidGroupDetailTableName + " ogd where ogd.opid=" + r.getOpid() + " order by oidindex asc";
    Statement statement = this.connection.createStatement();
    ResultSet rs = statement.executeQuery(sql);
    String oidGroupName = r.getOidGroupName();
    if (!oidGroupNames.contains(oidGroupName )) {
      oidGroupNames.add(oidGroupName);
      output.println("  <oidgroup oidgroupname=\"" + oidGroupName + "\">");
      while (rs.next()) {
        String oidValue = rs.getString("oidvalue");
        String oidName = rs.getString("oidname");
        String oidUnit = rs.getString("oidunit");
        int oidFlag = rs.getInt("flag");
        int oidIndex = rs.getInt("oidindex");
        output.println("      <oid>");
        output.println("        <oidvalue>" + oidValue + "</oidvalue>");
        output.println("        <oidname>" + oidName + "</oidname>");
        output.println("        <oidunit>" + oidUnit + "</oidunit>");
        output.println("        <oidselector />");
        output.println("      </oid>");
        output.flush();
      }
      output.println("  </oidgroup>");
    }
    rs.close();
    statement.close();
  }

  private void toOIDGroupXML(PrintWriter output, PredefMIBPolicyRecord r) throws SQLException {
    String sql = "select * from " + oidGroupDetailTableName + " ogd where ogd.opid=" + r.getOpid() + " order by oidindex asc";
    Statement statement = this.connection.createStatement();
    ResultSet rs = statement.executeQuery(sql);
    String oidGroupName = r.getOidGroupName() + "_" + r.getOidindex();
    if (!oidGroupNames.contains(oidGroupName )) {
      oidGroupNames.add(oidGroupName);
      output.println("  <oidgroup oidgroupname=\"" + oidGroupName + "\">");
      while (rs.next()) {
        String oidValue = rs.getString("oidvalue");
        String oidName = rs.getString("oidname");
        String oidUnit = rs.getString("oidunit");
        int oidFlag = rs.getInt("flag");
        int oidIndex = rs.getInt("oidindex");
        output.println("      <oid>");
        output.println("        <oidvalue>" + oidValue + ((oidFlag == 1) ? "." + r.getOidindex() : "") + "</oidvalue>");
        output.println("        <oidname>" + oidName + "</oidname>");
        output.println("        <oidunit>" + oidUnit + "</oidunit>");
        output.println("        <oidselector />");
        output.println("      </oid>");
        output.flush();
      }
      output.println("  </oidgroup>");
    }
    rs.close();
    statement.close();
  }

  /**
   * Retrieve from database and assign table field into object.
   * 
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
    for (String fieldName : map.keySet()) {
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
    this.connection = connection;
  }

}
