package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class VIcmpPortThresholds implements Serializable
{
	/** 
	 * This attribute maps to the column DEVIP in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String devip;

	/** 
	 * This attribute maps to the column IFIP in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String ifip;

	/** 
	 * This attribute maps to the column EXPIP in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String expip;

	/** 
	 * This attribute maps to the column BTIME in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String btime;

	/** 
	 * This attribute maps to the column ETIME in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String etime;

	/** 
	 * This attribute maps to the column VALUE0 in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String value0;

	/** 
	 * This attribute maps to the column VALUE_1 in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String value1;

	/** 
	 * This attribute maps to the column VALUE_2 in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String value2;

	/** 
	 * This attribute maps to the column VAR0 in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String var0;

	/** 
	 * This attribute maps to the column VALUE_1_LOW in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String value1Low;

	/** 
	 * This attribute maps to the column VALUE_2_LOW in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String value2Low;

	/** 
	 * This attribute maps to the column COMPARETYPE in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String comparetype;

	/** 
	 * This attribute maps to the column V1L_SEVERITY_1 in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected long v1lSeverity1;

	/** 
	 * This attribute represents whether the primitive attribute v1lSeverity1 is null.
	 */
	protected boolean v1lSeverity1Null = true;

	/** 
	 * This attribute maps to the column SEVERITY_1 in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected long severity1;

	/** 
	 * This attribute represents whether the primitive attribute severity1 is null.
	 */
	protected boolean severity1Null = true;

	/** 
	 * This attribute maps to the column SEVERITY_2 in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected long severity2;

	/** 
	 * This attribute represents whether the primitive attribute severity2 is null.
	 */
	protected boolean severity2Null = true;

	/** 
	 * This attribute maps to the column V1L_SEVERITY_A in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected long v1lSeverityA;

	/** 
	 * This attribute represents whether the primitive attribute v1lSeverityA is null.
	 */
	protected boolean v1lSeverityANull = true;

	/** 
	 * This attribute maps to the column SEVERITY_A in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected long severityA;

	/** 
	 * This attribute represents whether the primitive attribute severityA is null.
	 */
	protected boolean severityANull = true;

	/** 
	 * This attribute maps to the column SEVERITY_B in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected long severityB;

	/** 
	 * This attribute represents whether the primitive attribute severityB is null.
	 */
	protected boolean severityBNull = true;

	/** 
	 * This attribute maps to the column FILTER_A in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String filterA;

	/** 
	 * This attribute maps to the column FILTER_B in the V_ICMP_PORT_THRESHOLDS table.
	 */
	protected String filterB;

	/**
	 * Method 'VIcmpPortThresholds'
	 * 
	 */
	public VIcmpPortThresholds()
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
	 * Method 'getIfip'
	 * 
	 * @return String
	 */
	public String getIfip()
	{
		return ifip;
	}

	/**
	 * Method 'setIfip'
	 * 
	 * @param ifip
	 */
	public void setIfip(String ifip)
	{
		this.ifip = ifip;
	}

	/**
	 * Method 'getExpip'
	 * 
	 * @return String
	 */
	public String getExpip()
	{
		return expip;
	}

	/**
	 * Method 'setExpip'
	 * 
	 * @param expip
	 */
	public void setExpip(String expip)
	{
		this.expip = expip;
	}

	/**
	 * Method 'getBtime'
	 * 
	 * @return String
	 */
	public String getBtime()
	{
		return btime;
	}

	/**
	 * Method 'setBtime'
	 * 
	 * @param btime
	 */
	public void setBtime(String btime)
	{
		this.btime = btime;
	}

	/**
	 * Method 'getEtime'
	 * 
	 * @return String
	 */
	public String getEtime()
	{
		return etime;
	}

	/**
	 * Method 'setEtime'
	 * 
	 * @param etime
	 */
	public void setEtime(String etime)
	{
		this.etime = etime;
	}

	/**
	 * Method 'getValue0'
	 * 
	 * @return String
	 */
	public String getValue0()
	{
		return value0;
	}

	/**
	 * Method 'setValue0'
	 * 
	 * @param value0
	 */
	public void setValue0(String value0)
	{
		this.value0 = value0;
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
	 * Method 'getVar0'
	 * 
	 * @return String
	 */
	public String getVar0()
	{
		return var0;
	}

	/**
	 * Method 'setVar0'
	 * 
	 * @param var0
	 */
	public void setVar0(String var0)
	{
		this.var0 = var0;
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
	 * Method 'getV1lSeverityA'
	 * 
	 * @return long
	 */
	public long getV1lSeverityA()
	{
		return v1lSeverityA;
	}

	/**
	 * Method 'setV1lSeverityA'
	 * 
	 * @param v1lSeverityA
	 */
	public void setV1lSeverityA(long v1lSeverityA)
	{
		this.v1lSeverityA = v1lSeverityA;
		this.v1lSeverityANull = false;
	}

	/**
	 * Method 'setV1lSeverityANull'
	 * 
	 * @param value
	 */
	public void setV1lSeverityANull(boolean value)
	{
		this.v1lSeverityANull = value;
	}

	/**
	 * Method 'isV1lSeverityANull'
	 * 
	 * @return boolean
	 */
	public boolean isV1lSeverityANull()
	{
		return v1lSeverityANull;
	}

	/**
	 * Method 'getSeverityA'
	 * 
	 * @return long
	 */
	public long getSeverityA()
	{
		return severityA;
	}

	/**
	 * Method 'setSeverityA'
	 * 
	 * @param severityA
	 */
	public void setSeverityA(long severityA)
	{
		this.severityA = severityA;
		this.severityANull = false;
	}

	/**
	 * Method 'setSeverityANull'
	 * 
	 * @param value
	 */
	public void setSeverityANull(boolean value)
	{
		this.severityANull = value;
	}

	/**
	 * Method 'isSeverityANull'
	 * 
	 * @return boolean
	 */
	public boolean isSeverityANull()
	{
		return severityANull;
	}

	/**
	 * Method 'getSeverityB'
	 * 
	 * @return long
	 */
	public long getSeverityB()
	{
		return severityB;
	}

	/**
	 * Method 'setSeverityB'
	 * 
	 * @param severityB
	 */
	public void setSeverityB(long severityB)
	{
		this.severityB = severityB;
		this.severityBNull = false;
	}

	/**
	 * Method 'setSeverityBNull'
	 * 
	 * @param value
	 */
	public void setSeverityBNull(boolean value)
	{
		this.severityBNull = value;
	}

	/**
	 * Method 'isSeverityBNull'
	 * 
	 * @return boolean
	 */
	public boolean isSeverityBNull()
	{
		return severityBNull;
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
		
		if (!(_other instanceof VIcmpPortThresholds)) {
			return false;
		}
		
		final VIcmpPortThresholds _cast = (VIcmpPortThresholds) _other;
		if (devip == null ? _cast.devip != devip : !devip.equals( _cast.devip )) {
			return false;
		}
		
		if (ifip == null ? _cast.ifip != ifip : !ifip.equals( _cast.ifip )) {
			return false;
		}
		
		if (expip == null ? _cast.expip != expip : !expip.equals( _cast.expip )) {
			return false;
		}
		
		if (btime == null ? _cast.btime != btime : !btime.equals( _cast.btime )) {
			return false;
		}
		
		if (etime == null ? _cast.etime != etime : !etime.equals( _cast.etime )) {
			return false;
		}
		
		if (value0 == null ? _cast.value0 != value0 : !value0.equals( _cast.value0 )) {
			return false;
		}
		
		if (value1 == null ? _cast.value1 != value1 : !value1.equals( _cast.value1 )) {
			return false;
		}
		
		if (value2 == null ? _cast.value2 != value2 : !value2.equals( _cast.value2 )) {
			return false;
		}
		
		if (var0 == null ? _cast.var0 != var0 : !var0.equals( _cast.var0 )) {
			return false;
		}
		
		if (value1Low == null ? _cast.value1Low != value1Low : !value1Low.equals( _cast.value1Low )) {
			return false;
		}
		
		if (value2Low == null ? _cast.value2Low != value2Low : !value2Low.equals( _cast.value2Low )) {
			return false;
		}
		
		if (comparetype == null ? _cast.comparetype != comparetype : !comparetype.equals( _cast.comparetype )) {
			return false;
		}
		
		if (v1lSeverity1 != _cast.v1lSeverity1) {
			return false;
		}
		
		if (v1lSeverity1Null != _cast.v1lSeverity1Null) {
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
		
		if (v1lSeverityA != _cast.v1lSeverityA) {
			return false;
		}
		
		if (v1lSeverityANull != _cast.v1lSeverityANull) {
			return false;
		}
		
		if (severityA != _cast.severityA) {
			return false;
		}
		
		if (severityANull != _cast.severityANull) {
			return false;
		}
		
		if (severityB != _cast.severityB) {
			return false;
		}
		
		if (severityBNull != _cast.severityBNull) {
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
		
		if (ifip != null) {
			_hashCode = 29 * _hashCode + ifip.hashCode();
		}
		
		if (expip != null) {
			_hashCode = 29 * _hashCode + expip.hashCode();
		}
		
		if (btime != null) {
			_hashCode = 29 * _hashCode + btime.hashCode();
		}
		
		if (etime != null) {
			_hashCode = 29 * _hashCode + etime.hashCode();
		}
		
		if (value0 != null) {
			_hashCode = 29 * _hashCode + value0.hashCode();
		}
		
		if (value1 != null) {
			_hashCode = 29 * _hashCode + value1.hashCode();
		}
		
		if (value2 != null) {
			_hashCode = 29 * _hashCode + value2.hashCode();
		}
		
		if (var0 != null) {
			_hashCode = 29 * _hashCode + var0.hashCode();
		}
		
		if (value1Low != null) {
			_hashCode = 29 * _hashCode + value1Low.hashCode();
		}
		
		if (value2Low != null) {
			_hashCode = 29 * _hashCode + value2Low.hashCode();
		}
		
		if (comparetype != null) {
			_hashCode = 29 * _hashCode + comparetype.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (v1lSeverity1 ^ (v1lSeverity1 >>> 32));
		_hashCode = 29 * _hashCode + (v1lSeverity1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severity1 ^ (severity1 >>> 32));
		_hashCode = 29 * _hashCode + (severity1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severity2 ^ (severity2 >>> 32));
		_hashCode = 29 * _hashCode + (severity2Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (v1lSeverityA ^ (v1lSeverityA >>> 32));
		_hashCode = 29 * _hashCode + (v1lSeverityANull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severityA ^ (severityA >>> 32));
		_hashCode = 29 * _hashCode + (severityANull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severityB ^ (severityB >>> 32));
		_hashCode = 29 * _hashCode + (severityBNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.VIcmpPortThresholds: " );
		ret.append( "devip=" + devip );
		ret.append( ", ifip=" + ifip );
		ret.append( ", expip=" + expip );
		ret.append( ", btime=" + btime );
		ret.append( ", etime=" + etime );
		ret.append( ", value0=" + value0 );
		ret.append( ", value1=" + value1 );
		ret.append( ", value2=" + value2 );
		ret.append( ", var0=" + var0 );
		ret.append( ", value1Low=" + value1Low );
		ret.append( ", value2Low=" + value2Low );
		ret.append( ", comparetype=" + comparetype );
		ret.append( ", v1lSeverity1=" + v1lSeverity1 );
		ret.append( ", severity1=" + severity1 );
		ret.append( ", severity2=" + severity2 );
		ret.append( ", v1lSeverityA=" + v1lSeverityA );
		ret.append( ", severityA=" + severityA );
		ret.append( ", severityB=" + severityB );
		ret.append( ", filterA=" + filterA );
		ret.append( ", filterB=" + filterB );
		return ret.toString();
	}

}
