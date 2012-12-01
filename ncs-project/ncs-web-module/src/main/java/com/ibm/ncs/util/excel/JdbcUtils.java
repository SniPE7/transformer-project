package com.ibm.ncs.util.excel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.sun.org.apache.bcel.internal.ExceptionConstants;

public final class JdbcUtils {
	private static String url = "jdbc:oracle:thin:@localhost:1521:ncc";
	private static String user = "ncc";
	private static String password = "ncc";
    private JdbcUtils(){}
    static{//ע����
    try {
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		throw new ExceptionInInitializerError(e);
	}
    }
    public static Connection getConnection() throws SQLException{
    	return DriverManager.getConnection(url,user,password);
    }
  
   public static void free(ResultSet rs,Statement st,Connection conn){
    	try{
    		if(rs != null){
    			rs.close();
    		    rs = null;
    		}
    		if(st != null){
    			st.close();
    			st = null;
    		}
    		if(conn != null){
    			conn.close();
    			conn = null;
    		}
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    }
}
