package com.ibm.ncs.export;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import junit.framework.TestCase;

public class SnmpPolicyExporterImplTest extends TestCase {

  private static final String DBUSER = null;
  private Connection connection;

  protected void setUp() throws Exception {
    super.setUp();
    Class.forName("oracle.jdbc.driver.OracleDriver");
    // 连接时必须填写用户名及密码
    connection = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:NETCOOL", "nccnew", "nccnew");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    this.connection.close();
  }

  public void testExport() throws Exception {
    SnmpPolicyExporter exp = new SnmpPolicyExporterImpl();
    exp.setServerID("BF");
    exp.setJdbcConnection(this.connection);
    exp.export(new FileWriter("c:/temp/snmp.xml"), null);
  }

}
