package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class PolicySyslog implements Serializable
{
	/** 
	 * This attribute maps to the column SPID in the POLICY_SYSLOG table.
	 */
	protected long spid;

	/** 
	 * This attribute maps to the column MPID in the POLICY_SYSLOG table.
	 */
	protected long mpid;

	/** 
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean mpidNull = true;

	/** 
	 * This attribute maps to the column MARK in the POLICY_SYSLOG table.
	 */
	protected String mark;

	/** 
	 * This attribute maps to the column MANUFACTURE in the POLICY_SYSLOG table.
	 */
	protected String manufacture;

	/** 
	 * This attribute maps to the column EVENTTYPE in the POLICY_SYSLOG table.
	 */
	protected long eventtype;

	/** 
	 * This attribute represents whether the primitive attribute eventtype is null.
	 */
	protected boolean eventtypeNull = true;

	/** 
	 * This attribute maps to the column SEVERITY1 in the POLICY_SYSLOG table.
	 */
	protected long severity1;

	/** 
	 * This attribute represents whether the primitive attribute severity1 is null.
	 */
	protected boolean severity1Null = true;

	/** 
	 * This attribute maps to the column SEVERITY2 in the POLICY_SYSLOG table.
	 */
	protected long severity2;

	/** 
	 * This attribute represents whether the primitive attribute severity2 is null.
	 */
	protected boolean severity2Null = true;

	/** 
	 * This attribute maps to the column FILTERFLAG1 in the POLICY_SYSLOG table.
	 */
	protected long filterflag1;

	/** 
	 * This attribute represents whether the primitive attribute filterflag1 is null.
	 */
	protected boolean filterflag1Null = true;

	/** 
	 * This attribute maps to the column FILTERFLAG2 in the POLICY_SYSLOG table.
	 */
	protected long filterflag2;

	/** 
	 * This attribute represents whether the primitive attribute filterflag2 is null.
	 */
	protected boolean filterflag2Null = true;

	/**
	 * Method 'PolicySyslog'
	 * 
	 */
	public PolicySyslog()
	{
	}

	/**
	 * Method 'getSpid'
	 * 
	 * @return long
	 */
	public long getSpid()
	{
		return spid;
	}

	/**
	 * Method 'setSpid'
	 * 
	 * @param spid
	 */
	public void setSpid(long spid)
	{
		this.spid = spid;
	}

	/**
	 * Method 'getMpid'
	 * 
	 * @return long
	 */
	public long getMpid()
	{
		return mpid;
	}

	/**
	 * Method 'setMpid'
	 * 
	 * @param mpid
	 */
	public void setMpid(long mpid)
	{
		this.mpid = mpid;
		this.mpidNull = false;
	}

	/**
	 * Method 'setMpidNull'
	 * 
	 * @param value
	 */
	public void setMpidNull(boolean value)
	{
		this.mpidNull = value;
	}

	/**
	 * Method 'isMpidNull'
	 * 
	 * @return boolean
	 */
	public boolean isMpidNull()
	{
		return mpidNull;
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
		
		if (!(_other instanceof PolicySyslog)) {
			return false;
		}
		
		final PolicySyslog _cast = (PolicySyslog) _other;
		if (spid != _cast.spid) {
			return false;
		}
		
		if (mpid != _cast.mpid) {
			return false;
		}
		
		if (mpidNull != _cast.mpidNull) {
			return false;
		}
		
		if (mark == null ? _cast.mark != mark : !mark.equals( _cast.mark )) {
			return false;
		}
		
		if (manufacture == null ? _cast.manufacture != manufacture : !manufacture.equals( _cast.manufacture )) {
			return false;
		}
		
		if (eventtype != _cast.eventtype) {
			return false;
		}
		
		if (eventtypeNull != _cast.eventtypeNull) {
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
		_hashCode = 29 * _hashCode + (int) (spid ^ (spid >>> 32));
		_hashCode = 29 * _hashCode + (int) (mpid ^ (mpid >>> 32));
		_hashCode = 29 * _hashCode + (mpidNull ? 1 : 0);
		if (mark != null) {
			_hashCode = 29 * _hashCode + mark.hashCode();
		}
		
		if (manufacture != null) {
			_hashCode = 29 * _hashCode + manufacture.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (eventtype ^ (eventtype >>> 32));
		_hashCode = 29 * _hashCode + (eventtypeNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severity1 ^ (severity1 >>> 32));
		_hashCode = 29 * _hashCode + (severity1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severity2 ^ (severity2 >>> 32));
		_hashCode = 29 * _hashCode + (severity2Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (filterflag1 ^ (filterflag1 >>> 32));
		_hashCode = 29 * _hashCode + (filterflag1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (filterflag2 ^ (filterflag2 >>> 32));
		_hashCode = 29 * _hashCode + (filterflag2Null ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return PolicySyslogPk
	 */
	public PolicySyslogPk createPk()
	{
		return new PolicySyslogPk(spid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.PolicySyslog: " );
		ret.append( "spid=" + spid );
		ret.append( ", mpid=" + mpid );
		ret.append( ", mark=" + mark );
		ret.append( ", manufacture=" + manufacture );
		ret.append( ", eventtype=" + eventtype );
		ret.append( ", severity1=" + severity1 );
		ret.append( ", severity2=" + severity2 );
		ret.append( ", filterflag1=" + filterflag1 );
		ret.append( ", filterflag2=" + filterflag2 );
		return ret.toString();
	}

}
