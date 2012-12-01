package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class VOidGroup implements Serializable
{
	/** 
	 * This attribute maps to the column T_OIDGROUP_INFO_INIT_OPID in the V_OID_GROUP table.
	 */
	protected long tOidgroupInfoInitOpid;

	/** 
	 * This attribute maps to the column OIDGROUPNAME in the V_OID_GROUP table.
	 */
	protected String oidgroupname;

	/** 
	 * This attribute maps to the column OTYPE in the V_OID_GROUP table.
	 */
	protected long otype;

	/** 
	 * This attribute maps to the column DESCRIPTION in the V_OID_GROUP table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column T_OIDGROUP_DETAILS_INIT_OPID in the V_OID_GROUP table.
	 */
	protected long tOidgroupDetailsInitOpid;

	/** 
	 * This attribute maps to the column OIDVALUE in the V_OID_GROUP table.
	 */
	protected String oidvalue;

	/** 
	 * This attribute maps to the column OIDNAME in the V_OID_GROUP table.
	 */
	protected String oidname;

	/** 
	 * This attribute maps to the column OIDUNIT in the V_OID_GROUP table.
	 */
	protected String oidunit;

	/** 
	 * This attribute maps to the column FLAG in the V_OID_GROUP table.
	 */
	protected String flag;

	/** 
	 * This attribute maps to the column OIDINDEX in the V_OID_GROUP table.
	 */
	protected long oidindex;

	/** 
	 * This attribute represents whether the primitive attribute oidindex is null.
	 */
	protected boolean oidindexNull = true;

	/**
	 * Method 'VOidGroup'
	 * 
	 */
	public VOidGroup()
	{
	}

	/**
	 * Method 'getTOidgroupInfoInitOpid'
	 * 
	 * @return long
	 */
	public long getTOidgroupInfoInitOpid()
	{
		return tOidgroupInfoInitOpid;
	}

	/**
	 * Method 'setTOidgroupInfoInitOpid'
	 * 
	 * @param tOidgroupInfoInitOpid
	 */
	public void setTOidgroupInfoInitOpid(long tOidgroupInfoInitOpid)
	{
		this.tOidgroupInfoInitOpid = tOidgroupInfoInitOpid;
	}

	/**
	 * Method 'getOidgroupname'
	 * 
	 * @return String
	 */
	public String getOidgroupname()
	{
		return oidgroupname;
	}

	/**
	 * Method 'setOidgroupname'
	 * 
	 * @param oidgroupname
	 */
	public void setOidgroupname(String oidgroupname)
	{
		this.oidgroupname = oidgroupname;
	}

	/**
	 * Method 'getOtype'
	 * 
	 * @return long
	 */
	public long getOtype()
	{
		return otype;
	}

	/**
	 * Method 'setOtype'
	 * 
	 * @param otype
	 */
	public void setOtype(long otype)
	{
		this.otype = otype;
	}

	/**
	 * Method 'getDescription'
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Method 'setDescription'
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Method 'getTOidgroupDetailsInitOpid'
	 * 
	 * @return long
	 */
	public long getTOidgroupDetailsInitOpid()
	{
		return tOidgroupDetailsInitOpid;
	}

	/**
	 * Method 'setTOidgroupDetailsInitOpid'
	 * 
	 * @param tOidgroupDetailsInitOpid
	 */
	public void setTOidgroupDetailsInitOpid(long tOidgroupDetailsInitOpid)
	{
		this.tOidgroupDetailsInitOpid = tOidgroupDetailsInitOpid;
	}

	/**
	 * Method 'getOidvalue'
	 * 
	 * @return String
	 */
	public String getOidvalue()
	{
		return oidvalue;
	}

	/**
	 * Method 'setOidvalue'
	 * 
	 * @param oidvalue
	 */
	public void setOidvalue(String oidvalue)
	{
		this.oidvalue = oidvalue;
	}

	/**
	 * Method 'getOidname'
	 * 
	 * @return String
	 */
	public String getOidname()
	{
		return oidname;
	}

	/**
	 * Method 'setOidname'
	 * 
	 * @param oidname
	 */
	public void setOidname(String oidname)
	{
		this.oidname = oidname;
	}

	/**
	 * Method 'getOidunit'
	 * 
	 * @return String
	 */
	public String getOidunit()
	{
		return oidunit;
	}

	/**
	 * Method 'setOidunit'
	 * 
	 * @param oidunit
	 */
	public void setOidunit(String oidunit)
	{
		this.oidunit = oidunit;
	}

	/**
	 * Method 'getFlag'
	 * 
	 * @return String
	 */
	public String getFlag()
	{
		return flag;
	}

	/**
	 * Method 'setFlag'
	 * 
	 * @param flag
	 */
	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	/**
	 * Method 'getOidindex'
	 * 
	 * @return long
	 */
	public long getOidindex()
	{
		return oidindex;
	}

	/**
	 * Method 'setOidindex'
	 * 
	 * @param oidindex
	 */
	public void setOidindex(long oidindex)
	{
		this.oidindex = oidindex;
		this.oidindexNull = false;
	}

	/**
	 * Method 'setOidindexNull'
	 * 
	 * @param value
	 */
	public void setOidindexNull(boolean value)
	{
		this.oidindexNull = value;
	}

	/**
	 * Method 'isOidindexNull'
	 * 
	 * @return boolean
	 */
	public boolean isOidindexNull()
	{
		return oidindexNull;
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
		
		if (!(_other instanceof VOidGroup)) {
			return false;
		}
		
		final VOidGroup _cast = (VOidGroup) _other;
		if (tOidgroupInfoInitOpid != _cast.tOidgroupInfoInitOpid) {
			return false;
		}
		
		if (oidgroupname == null ? _cast.oidgroupname != oidgroupname : !oidgroupname.equals( _cast.oidgroupname )) {
			return false;
		}
		
		if (otype != _cast.otype) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		if (tOidgroupDetailsInitOpid != _cast.tOidgroupDetailsInitOpid) {
			return false;
		}
		
		if (oidvalue == null ? _cast.oidvalue != oidvalue : !oidvalue.equals( _cast.oidvalue )) {
			return false;
		}
		
		if (oidname == null ? _cast.oidname != oidname : !oidname.equals( _cast.oidname )) {
			return false;
		}
		
		if (oidunit == null ? _cast.oidunit != oidunit : !oidunit.equals( _cast.oidunit )) {
			return false;
		}
		
		if (flag == null ? _cast.flag != flag : !flag.equals( _cast.flag )) {
			return false;
		}
		
		if (oidindex != _cast.oidindex) {
			return false;
		}
		
		if (oidindexNull != _cast.oidindexNull) {
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
		_hashCode = 29 * _hashCode + (int) (tOidgroupInfoInitOpid ^ (tOidgroupInfoInitOpid >>> 32));
		if (oidgroupname != null) {
			_hashCode = 29 * _hashCode + oidgroupname.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (otype ^ (otype >>> 32));
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (tOidgroupDetailsInitOpid ^ (tOidgroupDetailsInitOpid >>> 32));
		if (oidvalue != null) {
			_hashCode = 29 * _hashCode + oidvalue.hashCode();
		}
		
		if (oidname != null) {
			_hashCode = 29 * _hashCode + oidname.hashCode();
		}
		
		if (oidunit != null) {
			_hashCode = 29 * _hashCode + oidunit.hashCode();
		}
		
		if (flag != null) {
			_hashCode = 29 * _hashCode + flag.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (oidindex ^ (oidindex >>> 32));
		_hashCode = 29 * _hashCode + (oidindexNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.VOidGroup: " );
		ret.append( "tOidgroupInfoInitOpid=" + tOidgroupInfoInitOpid );
		ret.append( ", oidgroupname=" + oidgroupname );
		ret.append( ", otype=" + otype );
		ret.append( ", description=" + description );
		ret.append( ", tOidgroupDetailsInitOpid=" + tOidgroupDetailsInitOpid );
		ret.append( ", oidvalue=" + oidvalue );
		ret.append( ", oidname=" + oidname );
		ret.append( ", oidunit=" + oidunit );
		ret.append( ", flag=" + flag );
		ret.append( ", oidindex=" + oidindex );
		return ret.toString();
	}

}
