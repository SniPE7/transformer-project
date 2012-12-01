package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the SNMP_EVENTS_PROCESS table.
 */
public class SnmpEventsProcessPk implements Serializable
{
	protected String mark;

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
	 * Method 'SnmpEventsProcessPk'
	 * 
	 */
	public SnmpEventsProcessPk()
	{
	}

	/**
	 * Method 'SnmpEventsProcessPk'
	 * 
	 * @param mark
	 */
	public SnmpEventsProcessPk(final String mark)
	{
		this.mark = mark;
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
		
		if (!(_other instanceof SnmpEventsProcessPk)) {
			return false;
		}
		
		final SnmpEventsProcessPk _cast = (SnmpEventsProcessPk) _other;
		if (mark == null ? _cast.mark != mark : !mark.equals( _cast.mark )) {
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
		ret.append( "com.ibm.ncs.model.dto.SnmpEventsProcessPk: " );
		ret.append( "mark=" + mark );
		return ret.toString();
	}

}
