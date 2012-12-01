package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PerformPara implements Serializable
{
	protected long mrid;

	/** 
	 * This attribute represents whether the primitive attribute mrid is null.
	 */
	protected boolean mridNull = true;

	protected long dtid;

	/** 
	 * This attribute represents whether the primitive attribute dtid is null.
	 */
	protected boolean dtidNull = true;

	protected long modid;

	/** 
	 * This attribute represents whether the primitive attribute modid is null.
	 */
	protected boolean modidNull = true;

	protected long eveid;

	/** 
	 * This attribute represents whether the primitive attribute eveid is null.
	 */
	protected boolean eveidNull = true;

	protected java.lang.String oidgroupname;

	protected java.lang.String oid;

	protected java.lang.String unit;

	protected java.lang.String description;

	protected java.lang.String major;

	/**
	 * Method 'PerformPara'
	 * 
	 */
	public PerformPara()
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
	 * Sets the value of mridNull
	 */
	public void setMridNull(boolean mridNull)
	{
		this.mridNull = mridNull;
	}

	/** 
	 * Gets the value of mridNull
	 */
	public boolean isMridNull()
	{
		return mridNull;
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
	 * Sets the value of dtidNull
	 */
	public void setDtidNull(boolean dtidNull)
	{
		this.dtidNull = dtidNull;
	}

	/** 
	 * Gets the value of dtidNull
	 */
	public boolean isDtidNull()
	{
		return dtidNull;
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
	 * Sets the value of modidNull
	 */
	public void setModidNull(boolean modidNull)
	{
		this.modidNull = modidNull;
	}

	/** 
	 * Gets the value of modidNull
	 */
	public boolean isModidNull()
	{
		return modidNull;
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
	 * Sets the value of eveidNull
	 */
	public void setEveidNull(boolean eveidNull)
	{
		this.eveidNull = eveidNull;
	}

	/** 
	 * Gets the value of eveidNull
	 */
	public boolean isEveidNull()
	{
		return eveidNull;
	}

	/**
	 * Method 'getOidgroupname'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getOidgroupname()
	{
		return oidgroupname;
	}

	/**
	 * Method 'setOidgroupname'
	 * 
	 * @param oidgroupname
	 */
	public void setOidgroupname(java.lang.String oidgroupname)
	{
		this.oidgroupname = oidgroupname;
	}

	/**
	 * Method 'getOid'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getOid()
	{
		return oid;
	}

	/**
	 * Method 'setOid'
	 * 
	 * @param oid
	 */
	public void setOid(java.lang.String oid)
	{
		this.oid = oid;
	}

	/**
	 * Method 'getUnit'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUnit()
	{
		return unit;
	}

	/**
	 * Method 'setUnit'
	 * 
	 * @param unit
	 */
	public void setUnit(java.lang.String unit)
	{
		this.unit = unit;
	}

	/**
	 * Method 'getDescription'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDescription()
	{
		return description;
	}

	/**
	 * Method 'setDescription'
	 * 
	 * @param description
	 */
	public void setDescription(java.lang.String description)
	{
		this.description = description;
	}

	/**
	 * Method 'getMajor'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getMajor()
	{
		return major;
	}

	/**
	 * Method 'setMajor'
	 * 
	 * @param major
	 */
	public void setMajor(java.lang.String major)
	{
		this.major = major;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.PerformPara: " );
		ret.append( "mrid=" + mrid );
		ret.append( ", dtid=" + dtid );
		ret.append( ", modid=" + modid );
		ret.append( ", eveid=" + eveid );
		ret.append( ", oidgroupname=" + oidgroupname );
		ret.append( ", oid=" + oid );
		ret.append( ", unit=" + unit );
		ret.append( ", description=" + description );
		ret.append( ", major=" + major );
		return ret.toString();
	}

}
