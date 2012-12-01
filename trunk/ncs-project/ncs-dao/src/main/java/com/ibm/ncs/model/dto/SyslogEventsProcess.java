package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class SyslogEventsProcess implements Serializable
{
	/** 
	 * This attribute maps to the column MARK in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String mark;

	/** 
	 * This attribute maps to the column VARLIST in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String varlist;

	/** 
	 * This attribute maps to the column BTIMELIST in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String btimelist;

	/** 
	 * This attribute maps to the column ETIMELIST in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String etimelist;

	/** 
	 * This attribute maps to the column FILTERFLAG1 in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected long filterflag1;

	/** 
	 * This attribute represents whether the primitive attribute filterflag1 is null.
	 */
	protected boolean filterflag1Null = true;

	/** 
	 * This attribute maps to the column FILTERFLAG2 in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected long filterflag2;

	/** 
	 * This attribute represents whether the primitive attribute filterflag2 is null.
	 */
	protected boolean filterflag2Null = true;

	/** 
	 * This attribute maps to the column SEVERITY1 in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected long severity1;

	/** 
	 * This attribute represents whether the primitive attribute severity1 is null.
	 */
	protected boolean severity1Null = true;

	/** 
	 * This attribute maps to the column SEVERITY2 in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected long severity2;

	/** 
	 * This attribute represents whether the primitive attribute severity2 is null.
	 */
	protected boolean severity2Null = true;

	/** 
	 * This attribute maps to the column PORT in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String port;

	/** 
	 * This attribute maps to the column NOTCAREFLAG in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected long notcareflag;

	/** 
	 * This attribute represents whether the primitive attribute notcareflag is null.
	 */
	protected boolean notcareflagNull = true;

	/** 
	 * This attribute maps to the column TYPE in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected long type;

	/** 
	 * This attribute represents whether the primitive attribute type is null.
	 */
	protected boolean typeNull = true;

	/** 
	 * This attribute maps to the column EVENTTYPE in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected long eventtype;

	/** 
	 * This attribute represents whether the primitive attribute eventtype is null.
	 */
	protected boolean eventtypeNull = true;

	/** 
	 * This attribute maps to the column SUBEVENTTYPE in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected long subeventtype;

	/** 
	 * This attribute represents whether the primitive attribute subeventtype is null.
	 */
	protected boolean subeventtypeNull = true;

	/** 
	 * This attribute maps to the column ALERTGROUP in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String alertgroup;

	/** 
	 * This attribute maps to the column ALERTKEY in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String alertkey;

	/** 
	 * This attribute maps to the column SUMMARYCN in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String summarycn;

	/** 
	 * This attribute maps to the column PROCESSSUGGEST in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String processsuggest;

	/** 
	 * This attribute maps to the column STATUS in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String status;

	/** 
	 * This attribute maps to the column ATTENTIONFLAG in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected long attentionflag;

	/** 
	 * This attribute represents whether the primitive attribute attentionflag is null.
	 */
	protected boolean attentionflagNull = true;

	/** 
	 * This attribute maps to the column EVENTS in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String events;

	/** 
	 * This attribute maps to the column MANUFACTURE in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String manufacture;

	/** 
	 * This attribute maps to the column ORIGEVENT in the SYSLOG_EVENTS_PROCESS table.
	 */
	protected String origevent;

	/**
	 * Method 'SyslogEventsProcess'
	 * 
	 */
	public SyslogEventsProcess()
	{
	}

	/**
	 * Method 'getMark'
	 * 
	 * @return String
	 */
	public String getMark()
	{
		return mark;
	}

	/**
	 * Method 'setMark'
	 * 
	 * @param mark
	 */
	public void setMark(String mark)
	{
		this.mark = mark;
	}

	/**
	 * Method 'getVarlist'
	 * 
	 * @return String
	 */
	public String getVarlist()
	{
		return varlist;
	}

	/**
	 * Method 'setVarlist'
	 * 
	 * @param varlist
	 */
	public void setVarlist(String varlist)
	{
		this.varlist = varlist;
	}

	/**
	 * Method 'getBtimelist'
	 * 
	 * @return String
	 */
	public String getBtimelist()
	{
		return btimelist;
	}

	/**
	 * Method 'setBtimelist'
	 * 
	 * @param btimelist
	 */
	public void setBtimelist(String btimelist)
	{
		this.btimelist = btimelist;
	}

	/**
	 * Method 'getEtimelist'
	 * 
	 * @return String
	 */
	public String getEtimelist()
	{
		return etimelist;
	}

	/**
	 * Method 'setEtimelist'
	 * 
	 * @param etimelist
	 */
	public void setEtimelist(String etimelist)
	{
		this.etimelist = etimelist;
	}

	/**
	 * Method 'getFilterflag1'
	 * 
	 * @return long
	 */
	public long getFilterflag1()
	{
		return filterflag1;
	}

	/**
	 * Method 'setFilterflag1'
	 * 
	 * @param filterflag1
	 */
	public void setFilterflag1(long filterflag1)
	{
		this.filterflag1 = filterflag1;
		this.filterflag1Null = false;
	}

	/**
	 * Method 'setFilterflag1Null'
	 * 
	 * @param value
	 */
	public void setFilterflag1Null(boolean value)
	{
		this.filterflag1Null = value;
	}

	/**
	 * Method 'isFilterflag1Null'
	 * 
	 * @return boolean
	 */
	public boolean isFilterflag1Null()
	{
		return filterflag1Null;
	}

	/**
	 * Method 'getFilterflag2'
	 * 
	 * @return long
	 */
	public long getFilterflag2()
	{
		return filterflag2;
	}

	/**
	 * Method 'setFilterflag2'
	 * 
	 * @param filterflag2
	 */
	public void setFilterflag2(long filterflag2)
	{
		this.filterflag2 = filterflag2;
		this.filterflag2Null = false;
	}

	/**
	 * Method 'setFilterflag2Null'
	 * 
	 * @param value
	 */
	public void setFilterflag2Null(boolean value)
	{
		this.filterflag2Null = value;
	}

	/**
	 * Method 'isFilterflag2Null'
	 * 
	 * @return boolean
	 */
	public boolean isFilterflag2Null()
	{
		return filterflag2Null;
	}

	/**
	 * Method 'getSeverity1'
	 * 
	 * @return long
	 */
	public long getSeverity1()
	{
		return severity1;
	}

	/**
	 * Method 'setSeverity1'
	 * 
	 * @param severity1
	 */
	public void setSeverity1(long severity1)
	{
		this.severity1 = severity1;
		this.severity1Null = false;
	}

	/**
	 * Method 'setSeverity1Null'
	 * 
	 * @param value
	 */
	public void setSeverity1Null(boolean value)
	{
		this.severity1Null = value;
	}

	/**
	 * Method 'isSeverity1Null'
	 * 
	 * @return boolean
	 */
	public boolean isSeverity1Null()
	{
		return severity1Null;
	}

	/**
	 * Method 'getSeverity2'
	 * 
	 * @return long
	 */
	public long getSeverity2()
	{
		return severity2;
	}

	/**
	 * Method 'setSeverity2'
	 * 
	 * @param severity2
	 */
	public void setSeverity2(long severity2)
	{
		this.severity2 = severity2;
		this.severity2Null = false;
	}

	/**
	 * Method 'setSeverity2Null'
	 * 
	 * @param value
	 */
	public void setSeverity2Null(boolean value)
	{
		this.severity2Null = value;
	}

	/**
	 * Method 'isSeverity2Null'
	 * 
	 * @return boolean
	 */
	public boolean isSeverity2Null()
	{
		return severity2Null;
	}

	/**
	 * Method 'getPort'
	 * 
	 * @return String
	 */
	public String getPort()
	{
		return port;
	}

	/**
	 * Method 'setPort'
	 * 
	 * @param port
	 */
	public void setPort(String port)
	{
		this.port = port;
	}

	/**
	 * Method 'getNotcareflag'
	 * 
	 * @return long
	 */
	public long getNotcareflag()
	{
		return notcareflag;
	}

	/**
	 * Method 'setNotcareflag'
	 * 
	 * @param notcareflag
	 */
	public void setNotcareflag(long notcareflag)
	{
		this.notcareflag = notcareflag;
		this.notcareflagNull = false;
	}

	/**
	 * Method 'setNotcareflagNull'
	 * 
	 * @param value
	 */
	public void setNotcareflagNull(boolean value)
	{
		this.notcareflagNull = value;
	}

	/**
	 * Method 'isNotcareflagNull'
	 * 
	 * @return boolean
	 */
	public boolean isNotcareflagNull()
	{
		return notcareflagNull;
	}

	/**
	 * Method 'getType'
	 * 
	 * @return long
	 */
	public long getType()
	{
		return type;
	}

	/**
	 * Method 'setType'
	 * 
	 * @param type
	 */
	public void setType(long type)
	{
		this.type = type;
		this.typeNull = false;
	}

	/**
	 * Method 'setTypeNull'
	 * 
	 * @param value
	 */
	public void setTypeNull(boolean value)
	{
		this.typeNull = value;
	}

	/**
	 * Method 'isTypeNull'
	 * 
	 * @return boolean
	 */
	public boolean isTypeNull()
	{
		return typeNull;
	}

	/**
	 * Method 'getEventtype'
	 * 
	 * @return long
	 */
	public long getEventtype()
	{
		return eventtype;
	}

	/**
	 * Method 'setEventtype'
	 * 
	 * @param eventtype
	 */
	public void setEventtype(long eventtype)
	{
		this.eventtype = eventtype;
		this.eventtypeNull = false;
	}

	/**
	 * Method 'setEventtypeNull'
	 * 
	 * @param value
	 */
	public void setEventtypeNull(boolean value)
	{
		this.eventtypeNull = value;
	}

	/**
	 * Method 'isEventtypeNull'
	 * 
	 * @return boolean
	 */
	public boolean isEventtypeNull()
	{
		return eventtypeNull;
	}

	/**
	 * Method 'getSubeventtype'
	 * 
	 * @return long
	 */
	public long getSubeventtype()
	{
		return subeventtype;
	}

	/**
	 * Method 'setSubeventtype'
	 * 
	 * @param subeventtype
	 */
	public void setSubeventtype(long subeventtype)
	{
		this.subeventtype = subeventtype;
		this.subeventtypeNull = false;
	}

	/**
	 * Method 'setSubeventtypeNull'
	 * 
	 * @param value
	 */
	public void setSubeventtypeNull(boolean value)
	{
		this.subeventtypeNull = value;
	}

	/**
	 * Method 'isSubeventtypeNull'
	 * 
	 * @return boolean
	 */
	public boolean isSubeventtypeNull()
	{
		return subeventtypeNull;
	}

	/**
	 * Method 'getAlertgroup'
	 * 
	 * @return String
	 */
	public String getAlertgroup()
	{
		return alertgroup;
	}

	/**
	 * Method 'setAlertgroup'
	 * 
	 * @param alertgroup
	 */
	public void setAlertgroup(String alertgroup)
	{
		this.alertgroup = alertgroup;
	}

	/**
	 * Method 'getAlertkey'
	 * 
	 * @return String
	 */
	public String getAlertkey()
	{
		return alertkey;
	}

	/**
	 * Method 'setAlertkey'
	 * 
	 * @param alertkey
	 */
	public void setAlertkey(String alertkey)
	{
		this.alertkey = alertkey;
	}

	/**
	 * Method 'getSummarycn'
	 * 
	 * @return String
	 */
	public String getSummarycn()
	{
		return summarycn;
	}

	/**
	 * Method 'setSummarycn'
	 * 
	 * @param summarycn
	 */
	public void setSummarycn(String summarycn)
	{
		this.summarycn = summarycn;
	}

	/**
	 * Method 'getProcesssuggest'
	 * 
	 * @return String
	 */
	public String getProcesssuggest()
	{
		return processsuggest;
	}

	/**
	 * Method 'setProcesssuggest'
	 * 
	 * @param processsuggest
	 */
	public void setProcesssuggest(String processsuggest)
	{
		this.processsuggest = processsuggest;
	}

	/**
	 * Method 'getStatus'
	 * 
	 * @return String
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * Method 'getAttentionflag'
	 * 
	 * @return long
	 */
	public long getAttentionflag()
	{
		return attentionflag;
	}

	/**
	 * Method 'setAttentionflag'
	 * 
	 * @param attentionflag
	 */
	public void setAttentionflag(long attentionflag)
	{
		this.attentionflag = attentionflag;
		this.attentionflagNull = false;
	}

	/**
	 * Method 'setAttentionflagNull'
	 * 
	 * @param value
	 */
	public void setAttentionflagNull(boolean value)
	{
		this.attentionflagNull = value;
	}

	/**
	 * Method 'isAttentionflagNull'
	 * 
	 * @return boolean
	 */
	public boolean isAttentionflagNull()
	{
		return attentionflagNull;
	}

	/**
	 * Method 'getEvents'
	 * 
	 * @return String
	 */
	public String getEvents()
	{
		return events;
	}

	/**
	 * Method 'setEvents'
	 * 
	 * @param events
	 */
	public void setEvents(String events)
	{
		this.events = events;
	}

	/**
	 * Method 'getManufacture'
	 * 
	 * @return String
	 */
	public String getManufacture()
	{
		return manufacture;
	}

	/**
	 * Method 'setManufacture'
	 * 
	 * @param manufacture
	 */
	public void setManufacture(String manufacture)
	{
		this.manufacture = manufacture;
	}

	/**
	 * Method 'getOrigevent'
	 * 
	 * @return String
	 */
	public String getOrigevent()
	{
		return origevent;
	}

	/**
	 * Method 'setOrigevent'
	 * 
	 * @param origevent
	 */
	public void setOrigevent(String origevent)
	{
		this.origevent = origevent;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof SyslogEventsProcess)) {
			return false;
		}
		
		final SyslogEventsProcess _cast = (SyslogEventsProcess) _other;
		if (mark == null ? _cast.mark != mark : !mark.equals( _cast.mark )) {
			return false;
		}
		
		if (varlist == null ? _cast.varlist != varlist : !varlist.equals( _cast.varlist )) {
			return false;
		}
		
		if (btimelist == null ? _cast.btimelist != btimelist : !btimelist.equals( _cast.btimelist )) {
			return false;
		}
		
		if (etimelist == null ? _cast.etimelist != etimelist : !etimelist.equals( _cast.etimelist )) {
			return false;
		}
		
		if (filterflag1 != _cast.filterflag1) {
			return false;
		}
		
		if (filterflag1Null != _cast.filterflag1Null) {
			return false;
		}
		
		if (filterflag2 != _cast.filterflag2) {
			return false;
		}
		
		if (filterflag2Null != _cast.filterflag2Null) {
			return false;
		}
		
		if (severity1 != _cast.severity1) {
			return false;
		}
		
		if (severity1Null != _cast.severity1Null) {
			return false;
		}
		
		if (severity2 != _cast.severity2) {
			return false;
		}
		
		if (severity2Null != _cast.severity2Null) {
			return false;
		}
		
		if (port == null ? _cast.port != port : !port.equals( _cast.port )) {
			return false;
		}
		
		if (notcareflag != _cast.notcareflag) {
			return false;
		}
		
		if (notcareflagNull != _cast.notcareflagNull) {
			return false;
		}
		
		if (type != _cast.type) {
			return false;
		}
		
		if (typeNull != _cast.typeNull) {
			return false;
		}
		
		if (eventtype != _cast.eventtype) {
			return false;
		}
		
		if (eventtypeNull != _cast.eventtypeNull) {
			return false;
		}
		
		if (subeventtype != _cast.subeventtype) {
			return false;
		}
		
		if (subeventtypeNull != _cast.subeventtypeNull) {
			return false;
		}
		
		if (alertgroup == null ? _cast.alertgroup != alertgroup : !alertgroup.equals( _cast.alertgroup )) {
			return false;
		}
		
		if (alertkey == null ? _cast.alertkey != alertkey : !alertkey.equals( _cast.alertkey )) {
			return false;
		}
		
		if (summarycn == null ? _cast.summarycn != summarycn : !summarycn.equals( _cast.summarycn )) {
			return false;
		}
		
		if (processsuggest == null ? _cast.processsuggest != processsuggest : !processsuggest.equals( _cast.processsuggest )) {
			return false;
		}
		
		if (status == null ? _cast.status != status : !status.equals( _cast.status )) {
			return false;
		}
		
		if (attentionflag != _cast.attentionflag) {
			return false;
		}
		
		if (attentionflagNull != _cast.attentionflagNull) {
			return false;
		}
		
		if (events == null ? _cast.events != events : !events.equals( _cast.events )) {
			return false;
		}
		
		if (manufacture == null ? _cast.manufacture != manufacture : !manufacture.equals( _cast.manufacture )) {
			return false;
		}
		
		if (origevent == null ? _cast.origevent != origevent : !origevent.equals( _cast.origevent )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		if (mark != null) {
			_hashCode = 29 * _hashCode + mark.hashCode();
		}
		
		if (varlist != null) {
			_hashCode = 29 * _hashCode + varlist.hashCode();
		}
		
		if (btimelist != null) {
			_hashCode = 29 * _hashCode + btimelist.hashCode();
		}
		
		if (etimelist != null) {
			_hashCode = 29 * _hashCode + etimelist.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (filterflag1 ^ (filterflag1 >>> 32));
		_hashCode = 29 * _hashCode + (filterflag1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (filterflag2 ^ (filterflag2 >>> 32));
		_hashCode = 29 * _hashCode + (filterflag2Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severity1 ^ (severity1 >>> 32));
		_hashCode = 29 * _hashCode + (severity1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severity2 ^ (severity2 >>> 32));
		_hashCode = 29 * _hashCode + (severity2Null ? 1 : 0);
		if (port != null) {
			_hashCode = 29 * _hashCode + port.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (notcareflag ^ (notcareflag >>> 32));
		_hashCode = 29 * _hashCode + (notcareflagNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (type ^ (type >>> 32));
		_hashCode = 29 * _hashCode + (typeNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (eventtype ^ (eventtype >>> 32));
		_hashCode = 29 * _hashCode + (eventtypeNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (subeventtype ^ (subeventtype >>> 32));
		_hashCode = 29 * _hashCode + (subeventtypeNull ? 1 : 0);
		if (alertgroup != null) {
			_hashCode = 29 * _hashCode + alertgroup.hashCode();
		}
		
		if (alertkey != null) {
			_hashCode = 29 * _hashCode + alertkey.hashCode();
		}
		
		if (summarycn != null) {
			_hashCode = 29 * _hashCode + summarycn.hashCode();
		}
		
		if (processsuggest != null) {
			_hashCode = 29 * _hashCode + processsuggest.hashCode();
		}
		
		if (status != null) {
			_hashCode = 29 * _hashCode + status.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (attentionflag ^ (attentionflag >>> 32));
		_hashCode = 29 * _hashCode + (attentionflagNull ? 1 : 0);
		if (events != null) {
			_hashCode = 29 * _hashCode + events.hashCode();
		}
		
		if (manufacture != null) {
			_hashCode = 29 * _hashCode + manufacture.hashCode();
		}
		
		if (origevent != null) {
			_hashCode = 29 * _hashCode + origevent.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return SyslogEventsProcessPk
	 */
	public SyslogEventsProcessPk createPk()
	{
		return new SyslogEventsProcessPk(mark, manufacture);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.SyslogEventsProcess: " );
		ret.append( "mark=" + mark );
		ret.append( ", varlist=" + varlist );
		ret.append( ", btimelist=" + btimelist );
		ret.append( ", etimelist=" + etimelist );
		ret.append( ", filterflag1=" + filterflag1 );
		ret.append( ", filterflag2=" + filterflag2 );
		ret.append( ", severity1=" + severity1 );
		ret.append( ", severity2=" + severity2 );
		ret.append( ", port=" + port );
		ret.append( ", notcareflag=" + notcareflag );
		ret.append( ", type=" + type );
		ret.append( ", eventtype=" + eventtype );
		ret.append( ", subeventtype=" + subeventtype );
		ret.append( ", alertgroup=" + alertgroup );
		ret.append( ", alertkey=" + alertkey );
		ret.append( ", summarycn=" + summarycn );
		ret.append( ", processsuggest=" + processsuggest );
		ret.append( ", status=" + status );
		ret.append( ", attentionflag=" + attentionflag );
		ret.append( ", events=" + events );
		ret.append( ", manufacture=" + manufacture );
		ret.append( ", origevent=" + origevent );
		return ret.toString();
	}

}
