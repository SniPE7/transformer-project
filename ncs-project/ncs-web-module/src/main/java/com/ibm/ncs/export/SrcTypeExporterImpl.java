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
public class SrcTypeExporterImpl implements SrcTypeExporter {

  private Connection connection = null;

  /**
   * 
   */
  public SrcTypeExporterImpl() {
    super();
  }

  public void export(Writer outputWriter, Writer log) throws Exception {
    if (log == null) {
       log = new OutputStreamWriter(System.out);
    }
    PrintWriter output = new PrintWriter(outputWriter);
    //output.println("#本端管理IP\t设备厂商类型");

    // 输出设备生效策略
    String sql4SrcType = "select * from v_src_type_export order by devip desc";
    List<SrcTypeRecord> records = this.search(SrcTypeRecord.class, sql4SrcType);
    for (SrcTypeRecord r: records) {
        output.println(r.getDevip() + "\t" + r.getMrname());
        output.flush();
    }
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
