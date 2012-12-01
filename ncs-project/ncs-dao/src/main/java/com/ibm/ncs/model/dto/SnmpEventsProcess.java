package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class SnmpEventsProcess implements Serializable
{
	/** 
	 * This attribute maps to the column MARK in the SNMP_EVENTS_PROCESS table.
	 */
	protected String mark;

	/** 
	 * This attribute maps to the column MANUFACTURE in the SNMP_EVENTS_PROCESS table.
	 */
	protected String manufacture;

	/** 
	 * This attribute maps to the column RESULTLIST in the SNMP_EVENTS_PROCESS table.
	 */
	protected String resultlist;

	/** 
	 * This attribute maps to the column WARNMESSAGE in the SNMP_EVENTS_PROCESS table.
	 */
	protected String warnmessage;

	/** 
	 * This attribute maps to the column SUMMARY in the SNMP_EVENTS_PROCESS table.
	 */
	protected String summary;

	/**
	 * Method 'SnmpEventsProcess'
	 * 
	 */
	public SnmpEventsProcess()
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
	 * Method 'getResultlist'
	 * 
	 * @return String
	 */
	public String getResultlist()
	{
		return resultlist;
	}

	/**
	 * Method 'setResultlist'
	 * 
	 * @param resultlist
	 */
	public void setResultlist(String resultlist)
	{
		this.resultlist = resultlist;
	}

	/**
	 * Method 'getWarnmessage'
	 * 
	 * @return String
	 */
	public String getWarnmessage()
	{
		return warnmessage;
	}

	/**
	 * Method 'setWarnmessage'
	 * 
	 * @param warnmessage
	 */
	public void setWarnmessage(String warnmessage)
	{
		this.warnmessage = warnmessage;
	}

	/**
	 * Method 'getSummary'
	 * 
	 * @return String
	 */
	public String getSummary()
	{
		return summary;
	}

	/**
	 * Method 'setSummary'
	 * 
	 * @param summary
	 */
	public void setSummary(String summary)
	{
		this.summary = summary;
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
		
		if (!(_other instanceof SnmpEventsProcess)) {
			return false;
		}
		
		final SnmpEventsProcess _cast = (SnmpEventsProcess) _other;
		if (mark == null ? _cast.mark != mark : !mark.equals( _cast.mark )) {
			return false;
		}
		
		if (manufacture == null ? _cast.manufacture != manufacture : !manufacture.equals( _cast.manufacture )) {
			return false;
		}
		
		if (resultlist == null ? _cast.resultlist != resultlist : !resultlist.equals( _cast.resultlist )) {
			return false;
		}
		
		if (warnmessage == null ? _cast.warnmessage != warnmessage : !warnmessage.equals( _cast.warnmessage )) {
			return false;
		}
		
		if (summary == null ? _cast.summary != summary : !summary.equals( _cast.summary )) {
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
		
		if (manufacture != null) {
			_hashCode = 29 * _hashCode + manufacture.hashCode();
		}
		
		if (resultlist != null) {
			_hashCode = 29 * _hashCode + resultlist.hashCode();
		}
		
		if (warnmessage != null) {
			_hashCode = 29 * _hashCode + warnmessage.hashCode();
		}
		
		if (summary != null) {
			_hashCode = 29 * _hashCode + summary.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return SnmpEventsProcessPk
	 */
	public SnmpEventsProcessPk createPk()
	{
		return new SnmpEventsProcessPk(mark);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.SnmpEventsProcess: " );
		ret.append( "mark=" + mark );
		ret.append( ", manufacture=" + manufacture );
		ret.append( ", resultlist=" + resultlist );
		ret.append( ", warnmessage=" + warnmessage );
		ret.append( ", summary=" + summary );
		return ret.toString();
	}

}
