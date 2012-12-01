package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_MODGRP_MAP table.
 */
public class TModgrpMapPk implements Serializable
{
	protected long gid;

	protected long nmsid;

	protected long modid;

	/** 
	 * This attribute represents whether the primitive attribute gid is null.
	 */
	protected boolean gidNull;

	/** 
	 * This attribute represents whether the primitive attribute nmsid is null.
	 */
	protected boolean nmsidNull;

	/** 
	 * This attribute represents whether the primitive attribute modid is null.
	 */
	protected boolean modidNull;

	/** 
	 * Sets the value of gid
	 */
	public void setGid(long gid)
	{
		this.gid = gid;
	}

	/** 
	 * Gets the value of gid
	 */
	public long getGid()
	{
		return gid;
	}

	/** 
	 * Sets the value of nmsid
	 */
	public void setNmsid(long nmsid)
	{
		this.nmsid = nmsid;
	}

	/** 
	 * Gets the value of nmsid
	 */
	public long getNmsid()
	{
		return nmsid;
	}

	/** 
	 * Sets the value of modid
	 */
	public void setModid(long modid)
	{
		this.modid = modid;
	}

	/** 
	 * Gets the value of modid
	 */
	public long getModid()
	{
		return modid;
	}

	/**
	 * Method 'TModgrpMapPk'
	 * 
	 */
	public TModgrpMapPk()
	{
	}

	/**
	 * Method 'TModgrpMapPk'
	 * 
	 * @param gid
	 * @param nmsid
	 * @param modid
	 */
	public TModgrpMapPk(final long gid, final long nmsid, final long modid)
	{
		this.gid = gid;
		this.nmsid = nmsid;
		this.modid = modid;
	}

	/** 
	 * Sets the value of gidNull
	 */
	public void setGidNull(boolean gidNull)
	{
		this.gidNull = gidNull;
	}

	/** 
	 * Gets the value of gidNull
	 */
	public boolean isGidNull()
	{
		return gidNull;
	}

	/** 
	 * Sets the value of nmsidNull
	 */
	public void setNmsidNull(boolean nmsidNull)
	{
		this.nmsidNull = nmsidNull;
	}

	/** 
	 * Gets the value of nmsidNull
	 */
	public boolean isNmsidNull()
	{
		return nmsidNull;
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
		
		if (!(_other instanceof TModgrpMapPk)) {
			return false;
		}
		
		final TModgrpMapPk _cast = (TModgrpMapPk) _other;
		if (gid != _cast.gid) {
			return false;
		}
		
		if (nmsid != _cast.nmsid) {
			return false;
		}
		
		if (modid != _cast.modid) {
			return false;
		}
		
		if (gidNull != _cast.gidNull) {
			return false;
		}
		
		if (nmsidNull != _cast.nmsidNull) {
			return false;
		}
		
		if (modidNull != _cast.modidNull) {
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
		_hashCode = 29 * _hashCode + (int) (gid ^ (gid >>> 32));
		_hashCode = 29 * _hashCode + (int) (nmsid ^ (nmsid >>> 32));
		_hashCode = 29 * _hashCode + (int) (modid ^ (modid >>> 32));
		_hashCode = 29 * _hashCode + (gidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (nmsidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (modidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TModgrpMapPk: " );
		ret.append( "gid=" + gid );
		ret.append( ", nmsid=" + nmsid );
		ret.append( ", modid=" + modid );
		return ret.toString();
	}

}
