package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the SYSLOG_EVENTS_PROCESS_NS table.
 */
public class SyslogEventsProcessNsPk implements Serializable
{
	protected String mark;

	protected String manufacture;

	/** 
	 * Sets the value of mark
	 */
	public void setMark(String mark)
	{
		this.mark = mark;
	}

	/** 
	 * Gets the value of mark
	 */
	public String getMark()
	{
		return mark;
	}

	/** 
	 * Sets the value of manufacture
	 */
	public void setManufacture(String manufacture)
	{
		this.manufacture = manufacture;
	}

	/** 
	 * Gets the value of manufacture
	 */
	public String getManufacture()
	{
		return manufacture;
	}

	/**
	 * Method 'SyslogEventsProcessNsPk'
	 * 
	 */
	public SyslogEventsProcessNsPk()
	{
	}

	/**
	 * Method 'SyslogEventsProcessNsPk'
	 * 
	 * @param mark
	 * @param manufacture
	 */
	public SyslogEventsProcessNsPk(final String mark, final String manufacture)
	{
		this.mark = mark;
		this.manufacture = manufacture;
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
		
		if (!(_other instanceof SyslogEventsProcessNsPk)) {
			return false;
		}
		
		final SyslogEventsProcessNsPk _cast = (SyslogEventsProcessNsPk) _other;
		if (mark == null ? _cast.mark != mark : !mark.equals( _cast.mark )) {
			return false;
		}
		
		if (manufacture == null ? _cast.manufacture != manufacture : !manufacture.equals( _cast.manufacture )) {
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
		ret.append( "com.ibm.ncs.model.dto.SyslogEventsProcessNsPk: " );
		ret.append( "mark=" + mark );
		ret.append( ", manufacture=" + manufacture );
		return ret.toString();
	}

}
