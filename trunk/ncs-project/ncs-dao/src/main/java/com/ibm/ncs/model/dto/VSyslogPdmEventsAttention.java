package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class VSyslogPdmEventsAttention implements Serializable
{
	/** 
	 * This attribute maps to the column DEVIP in the V_SYSLOG_PDM_EVENTS_ATTENTION table.
	 */
	protected String devip;

	/** 
	 * This attribute maps to the column MARK in the V_SYSLOG_PDM_EVENTS_ATTENTION table.
	 */
	protected String mark;

	/** 
	 * This attribute maps to the column SEVERITY1 in the V_SYSLOG_PDM_EVENTS_ATTENTION table.
	 */
	protected long severity1;

	/** 
	 * This attribute represents whether the primitive attribute severity1 is null.
	 */
	protected boolean severity1Null = true;

	/** 
	 * This attribute maps to the column PROCESSSUGGEST in the V_SYSLOG_PDM_EVENTS_ATTENTION table.
	 */
	protected String processsuggest;

	/**
	 * Method 'VSyslogPdmEventsAttention'
	 * 
	 */
	public VSyslogPdmEventsAttention()
	{
	}

	/**
	 * Method 'getDevip'
	 * 
	 * @return String
	 */
	public String getDevip()
	{
		return devip;
	}

	/**
	 * Method 'setDevip'
	 * 
	 * @param devip
	 */
	public void setDevip(String devip)
	{
		this.devip = devip;
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
		
		if (!(_other instanceof VSyslogPdmEventsAttention)) {
			return false;
		}
		
		final VSyslogPdmEventsAttention _cast = (VSyslogPdmEventsAttention) _other;
		if (devip == null ? _cast.devip != devip : !devip.equals( _cast.devip )) {
			return false;
		}
		
		if (mark == null ? _cast.mark != mark : !mark.equals( _cast.mark )) {
			return false;
		}
		
		if (severity1 != _cast.severity1) {
			return false;
		}
		
		if (severity1Null != _cast.severity1Null) {
			return false;
		}
		
		if (processsuggest == null ? _cast.processsuggest != processsuggest : !processsuggest.equals( _cast.processsuggest )) {
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
		if (devip != null) {
			_hashCode = 29 * _hashCode + devip.hashCode();
		}
		
		if (mark != null) {
			_hashCode = 29 * _hashCode + mark.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (severity1 ^ (severity1 >>> 32));
		_hashCode = 29 * _hashCode + (severity1Null ? 1 : 0);
		if (processsuggest != null) {
			_hashCode = 29 * _hashCode + processsuggest.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.VSyslogPdmEventsAttention: " );
		ret.append( "devip=" + devip );
		ret.append( ", mark=" + mark );
		ret.append( ", severity1=" + severity1 );
		ret.append( ", processsuggest=" + processsuggest );
		return ret.toString();
	}

}
