package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class VSnmpPortThresholds implements Serializable
{
	/** 
	 * This attribute maps to the column DEVIP in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected String devip;

	/** 
	 * This attribute maps to the column IFDESCR in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected String ifdescr;

	/** 
	 * This attribute maps to the column MAJOR in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected String major;

	/** 
	 * This attribute maps to the column VALUE_1 in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected String value1;

	/** 
	 * This attribute maps to the column VALUE_1_LOW in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected String value1Low;

	/** 
	 * This attribute maps to the column VALUE_2 in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected String value2;

	/** 
	 * This attribute maps to the column VALUE_2_LOW in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected String value2Low;

	/** 
	 * This attribute maps to the column COMPARETYPE in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected String comparetype;

	/** 
	 * This attribute maps to the column SEVERITY_1 in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected long severity1;

	/** 
	 * This attribute represents whether the primitive attribute severity1 is null.
	 */
	protected boolean severity1Null = true;

	/** 
	 * This attribute maps to the column V1L_SEVERITY_1 in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected long v1lSeverity1;

	/** 
	 * This attribute represents whether the primitive attribute v1lSeverity1 is null.
	 */
	protected boolean v1lSeverity1Null = true;

	/** 
	 * This attribute maps to the column SEVERITY_2 in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected long severity2;

	/** 
	 * This attribute represents whether the primitive attribute severity2 is null.
	 */
	protected boolean severity2Null = true;

	/** 
	 * This attribute maps to the column V2L_SEVERITY_2 in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected long v2lSeverity2;

	/** 
	 * This attribute represents whether the primitive attribute v2lSeverity2 is null.
	 */
	protected boolean v2lSeverity2Null = true;

	/** 
	 * This attribute maps to the column FILTER_A in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected String filterA;

	/** 
	 * This attribute maps to the column FILTER_B in the V_SNMP_PORT_THRESHOLDS table.
	 */
	protected String filterB;

	/**
	 * Method 'VSnmpPortThresholds'
	 * 
	 */
	public VSnmpPortThresholds()
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
	 * Method 'getIfdescr'
	 * 
	 * @return String
	 */
	public String getIfdescr()
	{
		return ifdescr;
	}

	/**
	 * Method 'setIfdescr'
	 * 
	 * @param ifdescr
	 */
	public void setIfdescr(String ifdescr)
	{
		this.ifdescr = ifdescr;
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
	 * Method 'getValue1Low'
	 * 
	 * @return String
	 */
	public String getValue1Low()
	{
		return value1Low;
	}

	/**
	 * Method 'setValue1Low'
	 * 
	 * @param value1Low
	 */
	public void setValue1Low(String value1Low)
	{
		this.value1Low = value1Low;
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
	 * Method 'getValue2Low'
	 * 
	 * @return String
	 */
	public String getValue2Low()
	{
		return value2Low;
	}

	/**
	 * Method 'setValue2Low'
	 * 
	 * @param value2Low
	 */
	public void setValue2Low(String value2Low)
	{
		this.value2Low = value2Low;
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
	 * Method 'getV1lSeverity1'
	 * 
	 * @return long
	 */
	public long getV1lSeverity1()
	{
		return v1lSeverity1;
	}

	/**
	 * Method 'setV1lSeverity1'
	 * 
	 * @param v1lSeverity1
	 */
	public void setV1lSeverity1(long v1lSeverity1)
	{
		this.v1lSeverity1 = v1lSeverity1;
		this.v1lSeverity1Null = false;
	}

	/**
	 * Method 'setV1lSeverity1Null'
	 * 
	 * @param value
	 */
	public void setV1lSeverity1Null(boolean value)
	{
		this.v1lSeverity1Null = value;
	}

	/**
	 * Method 'isV1lSeverity1Null'
	 * 
	 * @return boolean
	 */
	public boolean isV1lSeverity1Null()
	{
		return v1lSeverity1Null;
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
	 * Method 'getV2lSeverity2'
	 * 
	 * @return long
	 */
	public long getV2lSeverity2()
	{
		return v2lSeverity2;
	}

	/**
	 * Method 'setV2lSeverity2'
	 * 
	 * @param v2lSeverity2
	 */
	public void setV2lSeverity2(long v2lSeverity2)
	{
		this.v2lSeverity2 = v2lSeverity2;
		this.v2lSeverity2Null = false;
	}

	/**
	 * Method 'setV2lSeverity2Null'
	 * 
	 * @param value
	 */
	public void setV2lSeverity2Null(boolean value)
	{
		this.v2lSeverity2Null = value;
	}

	/**
	 * Method 'isV2lSeverity2Null'
	 * 
	 * @return boolean
	 */
	public boolean isV2lSeverity2Null()
	{
		return v2lSeverity2Null;
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
		
		if (!(_other instanceof VSnmpPortThresholds)) {
			return false;
		}
		
		final VSnmpPortThresholds _cast = (VSnmpPortThresholds) _other;
		if (devip == null ? _cast.devip != devip : !devip.equals( _cast.devip )) {
			return false;
		}
		
		if (ifdescr == null ? _cast.ifdescr != ifdescr : !ifdescr.equals( _cast.ifdescr )) {
			return false;
		}
		
		if (major == null ? _cast.major != major : !major.equals( _cast.major )) {
			return false;
		}
		
		if (value1 == null ? _cast.value1 != value1 : !value1.equals( _cast.value1 )) {
			return false;
		}
		
		if (value1Low == null ? _cast.value1Low != value1Low : !value1Low.equals( _cast.value1Low )) {
			return false;
		}
		
		if (value2 == null ? _cast.value2 != value2 : !value2.equals( _cast.value2 )) {
			return false;
		}
		
		if (value2Low == null ? _cast.value2Low != value2Low : !value2Low.equals( _cast.value2Low )) {
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
		
		if (v1lSeverity1 != _cast.v1lSeverity1) {
			return false;
		}
		
		if (v1lSeverity1Null != _cast.v1lSeverity1Null) {
			return false;
		}
		
		if (severity2 != _cast.severity2) {
			return false;
		}
		
		if (severity2Null != _cast.severity2Null) {
			return false;
		}
		
		if (v2lSeverity2 != _cast.v2lSeverity2) {
			return false;
		}
		
		if (v2lSeverity2Null != _cast.v2lSeverity2Null) {
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
		
		if (ifdescr != null) {
			_hashCode = 29 * _hashCode + ifdescr.hashCode();
		}
		
		if (major != null) {
			_hashCode = 29 * _hashCode + major.hashCode();
		}
		
		if (value1 != null) {
			_hashCode = 29 * _hashCode + value1.hashCode();
		}
		
		if (value1Low != null) {
			_hashCode = 29 * _hashCode + value1Low.hashCode();
		}
		
		if (value2 != null) {
			_hashCode = 29 * _hashCode + value2.hashCode();
		}
		
		if (value2Low != null) {
			_hashCode = 29 * _hashCode + value2Low.hashCode();
		}
		
		if (comparetype != null) {
			_hashCode = 29 * _hashCode + comparetype.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (severity1 ^ (severity1 >>> 32));
		_hashCode = 29 * _hashCode + (severity1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (v1lSeverity1 ^ (v1lSeverity1 >>> 32));
		_hashCode = 29 * _hashCode + (v1lSeverity1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severity2 ^ (severity2 >>> 32));
		_hashCode = 29 * _hashCode + (severity2Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (v2lSeverity2 ^ (v2lSeverity2 >>> 32));
		_hashCode = 29 * _hashCode + (v2lSeverity2Null ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.VSnmpPortThresholds: " );
		ret.append( "devip=" + devip );
		ret.append( ", ifdescr=" + ifdescr );
		ret.append( ", major=" + major );
		ret.append( ", value1=" + value1 );
		ret.append( ", value1Low=" + value1Low );
		ret.append( ", value2=" + value2 );
		ret.append( ", value2Low=" + value2Low );
		ret.append( ", comparetype=" + comparetype );
		ret.append( ", severity1=" + severity1 );
		ret.append( ", v1lSeverity1=" + v1lSeverity1 );
		ret.append( ", severity2=" + severity2 );
		ret.append( ", v2lSeverity2=" + v2lSeverity2 );
		ret.append( ", filterA=" + filterA );
		ret.append( ", filterB=" + filterB );
		return ret.toString();
	}

}
