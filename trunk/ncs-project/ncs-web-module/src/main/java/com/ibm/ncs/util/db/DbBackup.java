package com.ibm.ncs.util.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DbBackup {

	Logger logger = Logger.getLogger(DbBackup.class);

	DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public File backupDb() {
		File file = null;
		BufferedWriter out = null;
		Statement sm = null;
		DatabaseMetaData dbmeta;
		File file1 = null;
		File file2 = null;
		Connection conn = null;

		try {
			String fileName = (new File(System.getProperty("java.io.tmpdir"), "data.sql")).getCanonicalPath();
			conn = dataSource.getConnection();
			dbmeta = conn.getMetaData();
			String user = dbmeta.getUserName();

			if (user == null && user.equals("")) {
				return null;
			}
			Vector tables = new Vector();
			String schema = user.toUpperCase();
			ResultSet rs = dbmeta.getTables("TABLE", schema, "%", null);

			for (; rs.next();) {
				ResultSetMetaData rsmeta = rs.getMetaData();
				int j = rsmeta.getColumnCount();
				if (rs.getString(4) != null && rs.getString(4).equalsIgnoreCase("SYNONYM")) {
					// 不备份同义词
					logger.info(String.format("Skip SYNONYM: %s", rs.getString(3)));
					 continue;
				}
				tables.add(rs.getString(3));
			}
			rs.close();
			// System.out.println("tables="+tables);

			// Vector vTableSql = new Vector();
			file = new File(fileName);
			if (file.exists())
				file.delete();
			file.createNewFile();
			FileOutputStream fOutStm = new FileOutputStream(file, true);
			out = new BufferedWriter(new OutputStreamWriter(fOutStm, "UTF-8"));

			StringBuffer seqSql = readSequences(conn);
			out.write(seqSql.toString());

			for (int i = 0; i < tables.size(); i++) {
				String tableName = (String) tables.get(i);
				tableName = tableName.toUpperCase();
				if (tableName.contains("_TMP"))
					continue;
				if (tableName.contains("CFG"))
					continue;
				if (tableName.startsWith("V_"))
					continue;
				if (tableName.startsWith("BIN$"))
					continue;
				// vTableSql.removeAllElements();
				// readTableData(conn, tableName, vTableSql);

				// System.out.println(tableName +";\t"+vTableSql.size());
				// for(int k = 0; k < vTableSql.size(); k++)
				// {
				// StringBuffer sqlBuf = (StringBuffer)vTableSql.get(k);
				// out.write(sqlBuf.toString());
				// sqlBuf.delete(0, sqlBuf.length());
				//
				// }
				writeTableData(conn, tableName, out);
			}
			out.flush();
			if (out != null)
				out.close();
			out = null;
			fOutStm.close();

			// return file;
		} catch (SQLException e) {
			logger.error("error on DbBackup ,method backupDb ...SQLException..." + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("error on DbBackup ,method backupDb ...IOException ..." + e.getMessage());
			e.printStackTrace();

		} catch (Exception e) {
			logger.error("error on DbBackup ,method backupDb ..." + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

		return file;
	}

	public StringBuffer readSequences(Connection conn) {
		StringBuffer sbuf = new StringBuffer();
		long maxSeq = 0L;

		try {

			String sql = "select LAST_NUMBER from user_sequences where sequence_name = 'NM_SEQ'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				maxSeq = rs.getLong("LAST_NUMBER");
			}
			rs.close();
			stmt.close();
			sbuf.append("drop sequence NM_SEQ").append("\n");
			sbuf.append("create sequence NM_SEQ minvalue 1 maxvalue 99999999999 start with ").append(maxSeq).append(" increment by 1 nocache cycle").append("\n");
			//
			maxSeq = 0L;
			sql = "select LAST_NUMBER from user_sequences where sequence_name = 'TASK_SEQ'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				maxSeq = rs.getLong("LAST_NUMBER");
			}
			rs.close();
			stmt.close();
			sbuf.append("drop sequence TASK_SEQ").append("\n");
			sbuf.append("create sequence TASK_SEQ minvalue 1 maxvalue 99999999999 start with ").append(maxSeq).append(" increment by 1 nocache cycle").append("\n");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error on db backup, method readSequences" + e.getMessage());

		}
		// finally{
		// try {
		// if (conn!=null)conn.close();
		// } catch (SQLException e) {
		// }
		// }

		return sbuf;
	}

	public Vector readTableData(Connection conn, String tableName, Vector vout) {
		// Vector vout = new Vector();
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("delete from " + tableName).append("\n");
		vout.add(sbuf);
		// StringBuffer sqlData = null;
		try {
			StringBuffer rowData = new StringBuffer();
			StringBuffer rowName = new StringBuffer();
			// StringBuffer colData = new StringBuffer();
			// StringBuffer colName = new StringBuffer();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName);
			for (; rs.next();) {
				sbuf = new StringBuffer();
				ResultSetMetaData rsmeta = rs.getMetaData();
				int col = rsmeta.getColumnCount();
				rowData.delete(0, rowData.length());
				rowName.delete(0, rowName.length());
				for (int i = 1; i <= col; i++) {
					// colData.delete(0, colData.length());;
					// colName.delete(0, colName.length());
					int type = rsmeta.getColumnType(i);
					String tmp = rs.getString(i);
					System.out.println("rowDate" + rowData + ", rowName=" + rowName + ", type=" + type + ", tmp=" + tmp);
					switch (type) {
					case java.sql.Types.TIMESTAMP: // 93: // '[' java.sql.Types.TIMESTAMP

						if (tmp != null) {
							// colData.append(tmp.indexOf(0,tmp.indexOf(".")));
							// tmp = tmp.replaceAll("\r", " ");
							// tmp = tmp.replaceAll("\n", " ");
							rowData.append("to_timestamp('").append(tmp).append("',").append("'YYYY-MM-DD HH24:MI:SS.FF')");// .append(",");
						} else {
							rowData.append("null");// .append(",");
						}
						// colName.append(rsmeta.getColumnName(i));
						rowName.append(rsmeta.getColumnName(i));// .append(",");
						if (i != col) {
							rowData.append(",");
							rowName.append(",");
						}
						break;

					case java.sql.Types.DATE: // 91: // '[' java.sql.Types.DATE

						if (tmp != null) {
							// colData.append(tmp.indexOf(0,tmp.indexOf(".")));
							// tmp = tmp.replaceAll("\r", " ");
							// tmp = tmp.replaceAll("\n", " ");
							rowData.append("to_date('").append(tmp.substring(0, tmp.indexOf("."))).append("',").append("'yyyy-mm-dd hh24:mi:ss')");// .append(",");
						} else {
							rowData.append("null");// .append(",");
						}
						// colName.append(rsmeta.getColumnName(i));
						rowName.append(rsmeta.getColumnName(i));// .append(",");
						if (i != col) {
							rowData.append(",");
							rowName.append(",");
						}
						break;

					default:

						if (tmp != null) {
							// tmp = tmp.replaceAll("\r", " ");
							// tmp = tmp.replaceAll("\n", " ");
							if (tmp.indexOf("'") >= 0)
								tmp = tmp.replaceAll("'", "''");
							rowData.append("'").append(tmp).append("'");// .append(",");
						} else {
							rowData.append("null");// .append(",");
						}
						// colName.append(rsmeta.getColumnName(i));
						rowName.append(rsmeta.getColumnName(i));// .append(",");
						if (i != col) {
							rowData.append(",");
							rowName.append(",");
						}
						break;
					}
				}
				// System.out.println(" sbuf , rowName="+rowName+" \n rowData="+rowData);
				// if(rowData.length() > 0)
				// {
				// rowData = rowData.substring(0, rowData.length() - 1);
				// rowName = rowName.substring(0, rowName.length() - 1);
				// }
				String rowDataStr = rowData.toString().replaceAll("\r", " ");
				rowDataStr = rowDataStr.replaceAll("\n", " ");
				String rowNameStr = rowName.toString().replaceAll("\r", " ");
				rowNameStr = rowNameStr.replaceAll("\n", " ");

				// sbuf.append("insert into ");
				// sbuf.append(tableName);
				// sbuf.append(" (");
				// sbuf.append(rowName);
				// sbuf.append(") values (");
				// sbuf.append(rowData);
				// sbuf.append(") \n");
				// vout.add(sbuf);
				// sbuf.delete(0, sbuf.length());
				// rowName.delete(0,rowName.length());
				// rowData.delete(0, rowData.length());
				vout.add(new StringBuffer("insert into ").append(tableName).append(" (").append(rowNameStr).append(") values (").append(rowDataStr).append(") \n"));
			}

			rs.close();
			stmt.close();

		} catch (Exception e) {

		}

		return vout;
	}

	public void writeTableData(Connection conn, String tableName, Writer out) {

		logger.info(String.format("Write table : %s", tableName));
		StringBuffer sbuf = new StringBuffer();

		// StringBuffer sqlData = null;
		try {
			sbuf.append("delete from " + tableName).append("\n");

			out.write(sbuf.toString());
			StringBuffer rowData = new StringBuffer();
			StringBuffer rowName = new StringBuffer();
			// StringBuffer colData = new StringBuffer();
			// StringBuffer colName = new StringBuffer();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName);
			for (; rs.next();) {
				try {
					sbuf = new StringBuffer();
					ResultSetMetaData rsmeta = rs.getMetaData();
					int col = rsmeta.getColumnCount();
					rowData.delete(0, rowData.length());
					rowName.delete(0, rowName.length());
					for (int i = 1; i <= col; i++) {
						// colData.delete(0, colData.length());;
						// colName.delete(0, colName.length());
						int type = rsmeta.getColumnType(i);
						String tmp = rs.getString(i);
						switch (type) {
						// 不备份CLOB和BLOB类型的数据
						case java.sql.Types.CLOB: break;
						case java.sql.Types.BLOB: break;
						case java.sql.Types.TIMESTAMP: // 93: // '['
																					 // java.sql.Types.TIMESTAMP

							if (tmp != null) {
								// colData.append(tmp.indexOf(0,tmp.indexOf(".")));
								// tmp = tmp.replaceAll("\r", " ");
								// tmp = tmp.replaceAll("\n", " ");
								rowData.append("to_timestamp('").append(tmp).append("',").append("'YYYY-MM-DD HH24:MI:SS.FF')");// .append(",");
							} else {
								rowData.append("null");// .append(",");
							}
							// colName.append(rsmeta.getColumnName(i));
							rowName.append(rsmeta.getColumnName(i));// .append(",");
							if (i != col) {
								rowData.append(",");
								rowName.append(",");
							}
							break;

						case 91: // '['

							if (tmp != null) {
								// colData.append(tmp.indexOf(0,tmp.indexOf(".")));
								// tmp = tmp.replaceAll("\r", " ");
								// tmp = tmp.replaceAll("\n", " ");
								rowData.append("to_date('").append(tmp.substring(0, tmp.indexOf("."))).append("',").append("'yyyy-mm-dd hh24:mi:ss')");// .append(",");
							} else {
								rowData.append("null");// .append(",");
							}
							// colName.append(rsmeta.getColumnName(i));
							rowName.append(rsmeta.getColumnName(i));// .append(",");
							if (i != col) {
								rowData.append(",");
								rowName.append(",");
							}
							break;

						default:

							if (tmp != null) {
								// tmp = tmp.replaceAll("\r", " ");
								// tmp = tmp.replaceAll("\n", " ");
								if (tmp.indexOf("'") >= 0)
									tmp = tmp.replaceAll("'", "''");
								rowData.append("'").append(tmp).append("'");// .append(",");
							} else {
								rowData.append("null");// .append(",");
							}
							// colName.append(rsmeta.getColumnName(i));
							rowName.append(rsmeta.getColumnName(i));// .append(",");
							if (i != col) {
								rowData.append(",");
								rowName.append(",");
							}
							break;
						}
					}
					String rowDataStr = rowData.toString().replaceAll("\r", " ");
					rowDataStr = rowDataStr.replaceAll("\n", " ");
					String rowNameStr = rowName.toString().replaceAll("\r", " ");
					rowNameStr = rowNameStr.replaceAll("\n", " ");
					sbuf = new StringBuffer();
					sbuf.append("insert into ").append(tableName).append(" (").append(rowNameStr).append(") values (").append(rowDataStr).append(") \n");
					out.write(sbuf.toString());
				} catch (SQLException e) {
					logger.error("error on db backup, method writeTableData...SQLException" + e);
					e.printStackTrace();
				} catch (IOException e) {
					logger.error("error on db backup, method writeTableData...IOException" + e);
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("error on db backup, method writeTableData...Exception" + e);
					e.printStackTrace();
				}
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			logger.error("error on db backup, method writeTableData...SQLException" + e);
		} catch (IOException e1) {
			logger.error("error on db backup, method writeTableData...IOExcption" + e1);
		} catch (Exception e) {
			logger.error("error on db backup, method writeTableData..." + e);
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
