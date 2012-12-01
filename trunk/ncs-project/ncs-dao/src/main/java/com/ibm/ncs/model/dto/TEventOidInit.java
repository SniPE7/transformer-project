package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TEventOidInit implements Serializable
{
	/** 
	 * This attribute maps to the column MRID in the T_EVENT_OID_INIT table.
	 */
	protected long mrid;

	/** 
	 * This attribute maps to the column DTID in the T_EVENT_OID_INIT table.
	 */
	protected long dtid;

	/** 
	 * This attribute maps to the column MODID in the T_EVENT_OID_INIT table.
	 */
	protected long modid;

	/** 
	 * This attribute maps to the column EVEID in the T_EVENT_OID_INIT table.
	 */
	protected long eveid;

	/** 
	 * This attribute maps to the column OIDGROUPNAME in the T_EVENT_OID_INIT table.
	 */
	protected String oidgroupname;

	/** 
	 * This attribute maps to the column OID in the T_EVENT_OID_INIT table.
	 */
	protected String oid;

	/** 
	 * This attribute maps to the column UNIT in the T_EVENT_OID_INIT table.
	 */
	protected String unit;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_EVENT_OID_INIT table.
	 */
	protected String description;

	/**
	 * Method 'TEventOidInit'
	 * 
	 */
	public TEventOidInit()
	{
	}

	/**
	 * Method 'getMrid'
	 * 
	 * @return long
	 */
	public long getMrid()
	{
		return mrid;
	}

	/**
	 * Method 'setMrid'
	 * 
	 * @param mrid
	 */
	public void setMrid(long mrid)
	{
		this.mrid = mrid;
	}

	/**
	 * Method 'getDtid'
	 * 
	 * @return long
	 */
	public long getDtid()
	{
		return dtid;
	}

	/**
	 * Method 'setDtid'
	 * 
	 * @param dtid
	 */
	public void setDtid(long dtid)
	{
		this.dtid = dtid;
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
	 * Method 'getOid'
	 * 
	 * @return String
	 */
	public String getOid()
	{
		return oid;
	}

	/**
	 * Method 'setOid'
	 * 
	 * @param oid
	 */
	public void setOid(String oid)
	{
		this.oid = oid;
	}

	/**
	 * Method 'getUnit'
	 * 
	 * @return String
	 */
	public String getUnit()
	{
		return unit;
	}

	/**
	 * Method 'setUnit'
	 * 
	 * @param unit
	 */
	public void setUnit(String unit)
	{
		this.unit = unit;
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
		
		if (!(_other instanceof TEventOidInit)) {
			return false;
		}
		
		final TEventOidInit _cast = (TEventOidInit) _other;
		if (mrid != _cast.mrid) {
			return false;
		}
		
		if (dtid != _cast.dtid) {
			return false;
		}
		
		if (modid != _cast.modid) {
			return false;
		}
		
		if (eveid != _cast.eveid) {
			return false;
		}
		
		if (oidgroupname == null ? _cast.oidgroupname != oidgroupname : !oidgroupname.equals( _cast.oidgroupname )) {
			return false;
		}
		
		if (oid == null ? _cast.oid != oid : !oid.equals( _cast.oid )) {
			return false;
		}
		
		if (unit == null ? _cast.unit != unit : !unit.equals( _cast.unit )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
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
		_hashCode = 29 * _hashCode + (int) (mrid ^ (mrid >>> 32));
		_hashCode = 29 * _hashCode + (int) (dtid ^ (dtid >>> 32));
		_hashCode = 29 * _hashCode + (int) (modid ^ (modid >>> 32));
		_hashCode = 29 * _hashCode + (int) (eveid ^ (eveid >>> 32));
		if (oidgroupname != null) {
			_hashCode = 29 * _hashCode + oidgroupname.hashCode();
		}
		
		if (oid != null) {
			_hashCode = 29 * _hashCode + oid.hashCode();
		}
		
		if (unit != null) {
			_hashCode = 29 * _hashCode + unit.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
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
		ret.append( "com.ibm.ncs.model.dto.TEventOidInit: " );
		ret.append( "mrid=" + mrid );
		ret.append( ", dtid=" + dtid );
		ret.append( ", modid=" + modid );
		ret.append( ", eveid=" + eveid );
		ret.append( ", oidgroupname=" + oidgroupname );
		ret.append( ", oid=" + oid );
		ret.append( ", unit=" + unit );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
