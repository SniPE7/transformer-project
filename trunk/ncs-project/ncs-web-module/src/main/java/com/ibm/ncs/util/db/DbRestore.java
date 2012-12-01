package com.ibm.ncs.util.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;



import com.ibm.ncs.util.Log4jInit;

public class DbRestore {

	static Logger logger = Logger.getLogger(DbRestore.class);
	
	 
	DataSource dataSource;
	String message;
	boolean done;
	static int rownum;
	File dbfile;
	String errMsg;
	
	
	Thread process;
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}	
	public void init() {
		if (dbfile==null) return ;
		process = new Thread(){
			public void run(){
				operations();
			}
		};
	}
	
	public void startProcess(){
		done = false;
		process.start();
	}
	
	public void stopProcess(){
		process.interrupt();
		//process.stop();
		//process.destroy();
	}
	public void operations(){
		restoreDb(getDbfile());
	}
	
	public int restoreDb(File file){
		int ret =  -1 ; //return status;
		int failcount = -1 ;
		if (file == null) return -1;
		FileInputStream fin = null;
		BufferedReader reader = null;
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		try{
			fin = new FileInputStream(file);
			InputStreamReader inr = new InputStreamReader(fin, "UTF-8");
			reader = new BufferedReader(inr);
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ret = 0;
			failcount = 0;
			rownum=0;
			errMsg="";
	          do
	            {
	                if((sql = reader.readLine()) == null)
	                    break;
	                sql = sql.trim();
	                rownum++;
	                if(!sql.equals(""))
						try {
							stmt.execute(sql);
							ret++;
							logger.info(sql +"\n command  success. @"+rownum);
						} catch (Exception e) {
							failcount++;
							errMsg += "error on sql @line: "+rownum+"\n"; 
							e.printStackTrace();
							logger.error(sql+"\n error on restore sql data...: @"+rownum+"; "+e );
						}
	            } while(true);
	         done = true;
	         message="完成数据库恢复.";
	         logger.info(message);
	         logger.error(errMsg);
	        reader.close();
	          inr.close();
	          fin.close();
	       
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			
				try {
					if (stmt!=null)stmt.close();
				} catch (SQLException e) {}
				
					try {
						if (conn!=null)conn.close();
					} catch (SQLException e) {}
		}
		
		return ret;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public File getDbfile() {
		return dbfile;
	}
	public void setDbfile(File dbfile) {
		this.dbfile = dbfile;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}


}
