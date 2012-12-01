package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TPolicyDetails implements Serializable
{
	/** 
	 * This attribute maps to the column MPID in the T_POLICY_DETAILS table.
	 */
	protected long mpid;

	/** 
	 * This attribute maps to the column MODID in the T_POLICY_DETAILS table.
	 */
	protected long modid;

	/** 
	 * This attribute maps to the column EVEID in the T_POLICY_DETAILS table.
	 */
	protected long eveid;

	/** 
	 * This attribute maps to the column POLL in the T_POLICY_DETAILS table.
	 */
	protected long poll;

	/** 
	 * This attribute represents whether the primitive attribute poll is null.
	 */
	protected boolean pollNull = true;

	/** 
	 * This attribute maps to the column VALUE_1 in the T_POLICY_DETAILS table.
	 */
	protected String value1;

	/** 
	 * This attribute maps to the column SEVERITY_1 in the T_POLICY_DETAILS table.
	 */
	protected long severity1;

	/** 
	 * This attribute represents whether the primitive attribute severity1 is null.
	 */
	protected boolean severity1Null;

	/** 
	 * This attribute maps to the column FILTER_A in the T_POLICY_DETAILS table.
	 */
	protected String filterA;

	/** 
	 * This attribute maps to the column VALUE_2 in the T_POLICY_DETAILS table.
	 */
	protected String value2;

	/** 
	 * This attribute maps to the column SEVERITY_2 in the T_POLICY_DETAILS table.
	 */
	protected long severity2;

	/** 
	 * This attribute represents whether the primitive attribute severity2 is null.
	 */
	protected boolean severity2Null;

	/** 
	 * This attribute maps to the column FILTER_B in the T_POLICY_DETAILS table.
	 */
	protected String filterB;

	/** 
	 * This attribute maps to the column SEVERITY_A in the T_POLICY_DETAILS table.
	 */
	protected long severityA;

	/** 
	 * This attribute represents whether the primitive attribute severityA is null.
	 */
	protected boolean severityANull = true;

	/** 
	 * This attribute maps to the column SEVERITY_B in the T_POLICY_DETAILS table.
	 */
	protected long severityB;

	/** 
	 * This attribute represents whether the primitive attribute severityB is null.
	 */
	protected boolean severityBNull = true;

	/** 
	 * This attribute maps to the column OIDGROUP in the T_POLICY_DETAILS table.
	 */
	protected String oidgroup;

	/** 
	 * This attribute maps to the column OGFLAG in the T_POLICY_DETAILS table.
	 */
	protected String ogflag;

	/** 
	 * This attribute maps to the column VALUE_1_LOW in the T_POLICY_DETAILS table.
	 */
	protected String value1Low;

	/** 
	 * This attribute maps to the column VALUE_2_LOW in the T_POLICY_DETAILS table.
	 */
	protected String value2Low;

	/** 
	 * This attribute maps to the column V1L_SEVERITY_1 in the T_POLICY_DETAILS table.
	 */
	protected long v1lSeverity1;

	/** 
	 * This attribute represents whether the primitive attribute v1lSeverity1 is null.
	 */
	protected boolean v1lSeverity1Null = true;

	/** 
	 * This attribute maps to the column V1L_SEVERITY_A in the T_POLICY_DETAILS table.
	 */
	protected long v1lSeverityA;

	/** 
	 * This attribute represents whether the primitive attribute v1lSeverityA is null.
	 */
	protected boolean v1lSeverityANull = true;

	/** 
	 * This attribute maps to the column V2L_SEVERITY_2 in the T_POLICY_DETAILS table.
	 */
	protected long v2lSeverity2;

	/** 
	 * This attribute represents whether the primitive attribute v2lSeverity2 is null.
	 */
	protected boolean v2lSeverity2Null = true;

	/** 
	 * This attribute maps to the column V2L_SEVERITY_B in the T_POLICY_DETAILS table.
	 */
	protected long v2lSeverityB;

	/** 
	 * This attribute represents whether the primitive attribute v2lSeverityB is null.
	 */
	protected boolean v2lSeverityBNull = true;

	/** 
	 * This attribute maps to the column COMPARETYPE in the T_POLICY_DETAILS table.
	 */
	protected String comparetype;

	/**
	 * Method 'TPolicyDetails'
	 * 
	 */
	public TPolicyDetails()
	{
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
	}

	/**
	 * Method 'getModid'
	 * 
	 * @return long
	 */
	public long getModid()
	{
		return modid;
	}

	/**
	 * Method 'setModid'
	 * 
	 * @param modid
	 */
	public void setModid(long modid)
	{
		this.modid = modid;
	}

	/**
	 * Method 'getEveid'
	 * 
	 * @return long
	 */
	public long getEveid()
	{
		return eveid;
	}

	/**
	 * Method 'setEveid'
	 * 
	 * @param eveid
	 */
	public void setEveid(long eveid)
	{
		this.eveid = eveid;
	}

	/**
	 * Method 'getPoll'
	 * 
	 * @return long
	 */
	public long getPoll()
	{
		return poll;
	}

	/**
	 * Method 'setPoll'
	 * 
	 * @param poll
	 */
	public void setPoll(long poll)
	{
		this.poll = poll;
		this.pollNull = false;
	}

	/**
	 * Method 'setPollNull'
	 * 
	 * @param value
	 */
	public void setPollNull(boolean value)
	{
		this.pollNull = value;
	}

	/**
	 * Method 'isPollNull'
	 * 
	 * @return boolean
	 */
	public boolean isPollNull()
	{
		return pollNull;
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
	 * Method 'getOidgroup'
	 * 
	 * @return String
	 */
	public String getOidgroup()
	{
		return oidgroup;
	}

	/**
	 * Method 'setOidgroup'
	 * 
	 * @param oidgroup
	 */
	public void setOidgroup(String oidgroup)
	{
		this.oidgroup = oidgroup;
	}

	/**
	 * Method 'getOgflag'
	 * 
	 * @return String
	 */
	public String getOgflag()
	{
		return ogflag;
	}

	/**
	 * Method 'setOgflag'
	 * 
	 * @param ogflag
	 */
	public void setOgflag(String ogflag)
	{
		this.ogflag = ogflag;
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
	 * Method 'getV2lSeverityB'
	 * 
	 * @return long
	 */
	public long getV2lSeverityB()
	{
		return v2lSeverityB;
	}

	/**
	 * Method 'setV2lSeverityB'
	 * 
	 * @param v2lSeverityB
	 */
	public void setV2lSeverityB(long v2lSeverityB)
	{
		this.v2lSeverityB = v2lSeverityB;
		this.v2lSeverityBNull = false;
	}

	/**
	 * Method 'setV2lSeverityBNull'
	 * 
	 * @param value
	 */
	public void setV2lSeverityBNull(boolean value)
	{
		this.v2lSeverityBNull = value;
	}

	/**
	 * Method 'isV2lSeverityBNull'
	 * 
	 * @return boolean
	 */
	public boolean isV2lSeverityBNull()
	{
		return v2lSeverityBNull;
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
		
		if (!(_other instanceof TPolicyDetails)) {
			return false;
		}
		
		final TPolicyDetails _cast = (TPolicyDetails) _other;
		if (mpid != _cast.mpid) {
			return false;
		}
		
		if (modid != _cast.modid) {
			return false;
		}
		
		if (eveid != _cast.eveid) {
			return false;
		}
		
		if (poll != _cast.poll) {
			return false;
		}
		
		if (pollNull != _cast.pollNull) {
			return false;
		}
		
		if (value1 == null ? _cast.value1 != value1 : !value1.equals( _cast.value1 )) {
			return false;
		}
		
		if (severity1 != _cast.severity1) {
			return false;
		}
		
		if (severity1Null != _cast.severity1Null) {
			return false;
		}
		
		if (filterA == null ? _cast.filterA != filterA : !filterA.equals( _cast.filterA )) {
			return false;
		}
		
		if (value2 == null ? _cast.value2 != value2 : !value2.equals( _cast.value2 )) {
			return false;
		}
		
		if (severity2 != _cast.severity2) {
			return false;
		}
		
		if (severity2Null != _cast.severity2Null) {
			return false;
		}
		
		if (filterB == null ? _cast.filterB != filterB : !filterB.equals( _cast.filterB )) {
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
		
		if (oidgroup == null ? _cast.oidgroup != oidgroup : !oidgroup.equals( _cast.oidgroup )) {
			return false;
		}
		
		if (ogflag == null ? _cast.ogflag != ogflag : !ogflag.equals( _cast.ogflag )) {
			return false;
		}
		
		if (value1Low == null ? _cast.value1Low != value1Low : !value1Low.equals( _cast.value1Low )) {
			return false;
		}
		
		if (value2Low == null ? _cast.value2Low != value2Low : !value2Low.equals( _cast.value2Low )) {
			return false;
		}
		
		if (v1lSeverity1 != _cast.v1lSeverity1) {
			return false;
		}
		
		if (v1lSeverity1Null != _cast.v1lSeverity1Null) {
			return false;
		}
		
		if (v1lSeverityA != _cast.v1lSeverityA) {
			return false;
		}
		
		if (v1lSeverityANull != _cast.v1lSeverityANull) {
			return false;
		}
		
		if (v2lSeverity2 != _cast.v2lSeverity2) {
			return false;
		}
		
		if (v2lSeverity2Null != _cast.v2lSeverity2Null) {
			return false;
		}
		
		if (v2lSeverityB != _cast.v2lSeverityB) {
			return false;
		}
		
		if (v2lSeverityBNull != _cast.v2lSeverityBNull) {
			return false;
		}
		
		if (comparetype == null ? _cast.comparetype != comparetype : !comparetype.equals( _cast.comparetype )) {
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
		_hashCode = 29 * _hashCode + (int) (mpid ^ (mpid >>> 32));
		_hashCode = 29 * _hashCode + (int) (modid ^ (modid >>> 32));
		_hashCode = 29 * _hashCode + (int) (eveid ^ (eveid >>> 32));
		_hashCode = 29 * _hashCode + (int) (poll ^ (poll >>> 32));
		_hashCode = 29 * _hashCode + (pollNull ? 1 : 0);
		if (value1 != null) {
			_hashCode = 29 * _hashCode + value1.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (severity1 ^ (severity1 >>> 32));
		_hashCode = 29 * _hashCode + (severity1Null ? 1 : 0);
		if (filterA != null) {
			_hashCode = 29 * _hashCode + filterA.hashCode();
		}
		
		if (value2 != null) {
			_hashCode = 29 * _hashCode + value2.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (severity2 ^ (severity2 >>> 32));
		_hashCode = 29 * _hashCode + (severity2Null ? 1 : 0);
		if (filterB != null) {
			_hashCode = 29 * _hashCode + filterB.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (severityA ^ (severityA >>> 32));
		_hashCode = 29 * _hashCode + (severityANull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (severityB ^ (severityB >>> 32));
		_hashCode = 29 * _hashCode + (severityBNull ? 1 : 0);
		if (oidgroup != null) {
			_hashCode = 29 * _hashCode + oidgroup.hashCode();
		}
		
		if (ogflag != null) {
			_hashCode = 29 * _hashCode + ogflag.hashCode();
		}
		
		if (value1Low != null) {
			_hashCode = 29 * _hashCode + value1Low.hashCode();
		}
		
		if (value2Low != null) {
			_hashCode = 29 * _hashCode + value2Low.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (v1lSeverity1 ^ (v1lSeverity1 >>> 32));
		_hashCode = 29 * _hashCode + (v1lSeverity1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (v1lSeverityA ^ (v1lSeverityA >>> 32));
		_hashCode = 29 * _hashCode + (v1lSeverityANull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (v2lSeverity2 ^ (v2lSeverity2 >>> 32));
		_hashCode = 29 * _hashCode + (v2lSeverity2Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (v2lSeverityB ^ (v2lSeverityB >>> 32));
		_hashCode = 29 * _hashCode + (v2lSeverityBNull ? 1 : 0);
		if (comparetype != null) {
			_hashCode = 29 * _hashCode + comparetype.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TPolicyDetailsPk
	 */
	public TPolicyDetailsPk createPk()
	{
		return new TPolicyDetailsPk(mpid, modid, eveid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TPolicyDetails: " );
		ret.append( "mpid=" + mpid );
		ret.append( ", modid=" + modid );
		ret.append( ", eveid=" + eveid );
		ret.append( ", poll=" + poll );
		ret.append( ", value1=" + value1 );
		ret.append( ", severity1=" + severity1 );
		ret.append( ", filterA=" + filterA );
		ret.append( ", value2=" + value2 );
		ret.append( ", severity2=" + severity2 );
		ret.append( ", filterB=" + filterB );
		ret.append( ", severityA=" + severityA );
		ret.append( ", severityB=" + severityB );
		ret.append( ", oidgroup=" + oidgroup );
		ret.append( ", ogflag=" + ogflag );
		ret.append( ", value1Low=" + value1Low );
		ret.append( ", value2Low=" + value2Low );
		ret.append( ", v1lSeverity1=" + v1lSeverity1 );
		ret.append( ", v1lSeverityA=" + v1lSeverityA );
		ret.append( ", v2lSeverity2=" + v2lSeverity2 );
		ret.append( ", v2lSeverityB=" + v2lSeverityB );
		ret.append( ", comparetype=" + comparetype );
		return ret.toString();
	}

}
