package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the SNMP_THRESHOLDS table.
 */
public class SnmpThresholdsPk implements Serializable
{
	protected String performance;

	/** 
	 * Sets the value of performance
	 */
	public void setPerformance(String performance)
	{
		this.performance = performance;
	}

	/** 
	 * Gets the value of performance
	 */
	public String getPerformance()
	{
		return performance;
	}

	/**
	 * Method 'SnmpThresholdsPk'
	 * 
	 */
	public SnmpThresholdsPk()
	{
	}

	/**
	 * Method 'SnmpThresholdsPk'
	 * 
	 * @param performance
	 */
	public SnmpThresholdsPk(final String performance)
	{
		this.performance = performance;
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
		
		if (!(_other instanceof SnmpThresholdsPk)) {
			return false;
		}
		
		final SnmpThresholdsPk _cast = (SnmpThresholdsPk) _other;
		if (performance == null ? _cast.performance != performance : !performance.equals( _cast.performance )) {
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
		if (performance != null) {
			_hashCode = 29 * _hashCode + performance.hashCode();
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
		ret.append( "com.ibm.ncs.model.dto.SnmpThresholdsPk: " );
		ret.append( "performance=" + performance );
		return ret.toString();
	}

}
