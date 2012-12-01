package com.ibm.ncs.util.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VersionInfo {
	Date ver;
	String syslog;
	String syslogns;
	
	public VersionInfo(){
		
	}
	public VersionInfo(Date ver, String syslogCount, String syslognsCount){
		this.ver = ver;
		syslog = syslogCount;
		syslogns=syslognsCount;
	}
	public Date getVer() {
		return ver;
	}
	public void setVer(Date ver) {
		this.ver = ver;
	}
	public String getSyslog() {
		return syslog;
	}
	public void setSyslog(String syslog) {
		this.syslog = syslog;
	}
	public String getSyslogns() {
		return syslogns;
	}
	public void setSyslogns(String syslogns) {
		this.syslogns = syslogns;
	}

	public String toString(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.util.excel.VersionInfo: " );
		ret.append( "ver=" + df.format(ver) );
		ret.append( ", syslog=" + syslog );
		ret.append( ", syslogns=" + syslogns );
		return ret.toString();

	}
}
