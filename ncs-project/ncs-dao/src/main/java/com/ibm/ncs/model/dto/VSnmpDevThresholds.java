package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class VSnmpDevThresholds implements Serializable
{
	/** 
	 * This attribute maps to the column DEVIP in the V_SNMP_DEV_THRESHOLDS table.
	 */
	protected String devip;

	/** 
	 * This attribute maps to the column MAJOR in the V_SNMP_DEV_THRESHOLDS table.
	 */
	protected String major;

	/** 
	 * This attribute maps to the column VALUE_1 in the V_SNMP_DEV_THRESHOLDS table.
	 */
	protected String value1;

	/** 
	 * This attribute maps to the column VALUE_2 in the V_SNMP_DEV_THRESHOLDS table.
	 */
	protected String value2;

	/** 
	 * This attribute maps to the column COMPARETYPE in the V_SNMP_DEV_THRESHOLDS table.
	 */
	protected String comparetype;

	/** 
	 * This attribute maps to the column SEVERITY_1 in the V_SNMP_DEV_THRESHOLDS table.
	 */
	protected long severity1;

	/** 
	 * This attribute represents whether the primitive attribute severity1 is null.
	 */
	protected boolean severity1Null = true;

	/** 
	 * This attribute maps to the column SEVERITY_2 in the V_SNMP_DEV_THRESHOLDS table.
	 */
	protected long severity2;

	/** 
	 * This attribute represents whether the primitive attribute severity2 is null.
	 */
	protected boolean severity2Null = true;

	/** 
	 * This attribute maps to the column FILTER_A in the V_SNMP_DEV_THRESHOLDS table.
	 */
	protected String filterA;

	/** 
	 * This attribute maps to the column FILTER_B in the V_SNMP_DEV_THRESHOLDS table.
	 */
	protected String filterB;

	/**
	 * Method 'VSnmpDevThresholds'
	 * 
	 */
	public VSnmpDevThresholds()
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
	 * Method 'getMajor'
	 * 
	 * @return String
	 */
	public String getMajor()
	{
		return major;
	}

	/**
	 * Method 'setMajor'
	 * 
	 * @param major
	 */
	public void setMajor(String major)
	{
		this.major = major;
	}

	/**
	 * Method 'getValue1'
	 * 
	 * @return String
	 */
	public String getValue1()
	{
		return value1;
	}

	/**
	 * Method 'setValue1'
	 * 
	 * @param value1
	 */
	public void setValue1(String value1)
	{
		this.value1 = value1;
	}

	/**
	 * Method 'getValue2'
	 * 
	 * @return String
	 */
	public String getValue2()
	{
		return value2;
	}

	/**
	 * Method 'setValue2'
	 * 
	 * @param value2
	 */
	public void setValue2(String value2)
	{
		this.value2 = value2;
	}

	/**
	 * Method 'getComparetype'
	 * 
	 * @return String
	 */
	public String getComparetype()
	{
		return comparetype;
	}

	/**
	 * Method 'setComparetype'
	 * 
	 * @param comparetype
	 */
	public void setComparetype(String comparetype)
	{
		this.comparetype = comparetype;
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
	 * Method 'getFilterA'
	 * 
	 * @return String
	 */
	public String getFilterA()
	{
		return filterA;
	}

	/**
	 * Method 'setFilterA'
	 * 
	 * @param filterA
	 */
	public void setFilterA(String filterA)
	{
		this.filterA = filterA;
	}

	/**
	 * Method 'getFilterB'
	 * 
	 * @return String
	 */
	public String getFilterB()
	{
		return filterB;
	}

	/**
	 * Method 'setFilterB'
	 * 
	 * @param filterB
	 */
	public void setFilterB(String filterB)
	{
		this.filterB = filterB;
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
		
		if (!(_other instanceof VSnmpDevThresholds)) {
			return false;
		}
		
		final VSnmpDevThresholds _cast = (VSnmpDevThresholds) _other;
		if (devip == null ? _cast.devip != devip : !devip.equals( _cast.devip )) {
			return false;
		}
		
		if (major == null ? _cast.major != major : !major.equals( _cast.major )) {
			return false;
		}
		
		if (value1 == null ? _cast.value1 != value1 : !value1.equals( _cast.value1 )) {
			return false;
		}
		
		if (value2 == null ? _cast.value2 != value2 : !value2.equals( _cast.value2 )) {
			return false;
		}
		
		if (comparetype == null ? _cast.comparetype != comparetype : !comparetype.equals( _cast.comparetype )) {
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
		
		if (filterA == null ? _cast.filterA != filterA : !filterA.equals( _cast.filterA )) {
			return false;
		}
		
		if (filterB == null ? _cast.filterB != filterB : !filterB.equals( _cast.filterB )) {
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
		
		if (major != null) {
			_hashCode = 29 * _hashCode + major.hashCode();
		}
		
		if (value1 != null) {
			_hashCode = 29 * _hashCode + value1.hashCode();
		}
		
		if (value2 != null) {
			_hashCode = 29 * _hashCode + value2.hashCode();
		}
		
		if (comparetype != null) {
			_hashCode = 29 * _hashCode + comparetype.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (severity1 ^ (severity1 >>> 32));
		_hashCode = 29 * _hashCode + (severity1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severity2 ^ (severity2 >>> 32));
		_hashCode = 29 * _hashCode + (severity2Null ? 1 : 0);
		if (filterA != null) {
			_hashCode = 29 * _hashCode + filterA.hashCode();
		}
		
		if (filterB != null) {
			_hashCode = 29 * _hashCode + filterB.hashCode();
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
		ret.append( "com.ibm.ncs.model.dto.VSnmpDevThresholds: " );
		ret.append( "devip=" + devip );
		ret.append( ", major=" + major );
		ret.append( ", value1=" + value1 );
		ret.append( ", value2=" + value2 );
		ret.append( ", comparetype=" + comparetype );
		ret.append( ", severity1=" + severity1 );
		ret.append( ", severity2=" + severity2 );
		ret.append( ", filterA=" + filterA );
		ret.append( ", filterB=" + filterB );
		return ret.toString();
	}

}
