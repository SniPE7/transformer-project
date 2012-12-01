package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_POLICY_DETAILS table.
 */
public class TPolicyDetailsPk implements Serializable
{
	protected long mpid;

	protected long modid;

	protected long eveid;

	/** 
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean mpidNull;

	/** 
	 * This attribute represents whether the primitive attribute modid is null.
	 */
	protected boolean modidNull;

	/** 
	 * This attribute represents whether the primitive attribute eveid is null.
	 */
	protected boolean eveidNull;

	/** 
	 * Sets the value of mpid
	 */
	public void setMpid(long mpid)
	{
		this.mpid = mpid;
	}

	/** 
	 * Gets the value of mpid
	 */
	public long getMpid()
	{
		return mpid;
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
	 * Sets the value of eveid
	 */
	public void setEveid(long eveid)
	{
		this.eveid = eveid;
	}

	/** 
	 * Gets the value of eveid
	 */
	public long getEveid()
	{
		return eveid;
	}

	/**
	 * Method 'TPolicyDetailsPk'
	 * 
	 */
	public TPolicyDetailsPk()
	{
	}

	/**
	 * Method 'TPolicyDetailsPk'
	 * 
	 * @param mpid
	 * @param modid
	 * @param eveid
	 */
	public TPolicyDetailsPk(final long mpid, final long modid, final long eveid)
	{
		this.mpid = mpid;
		this.modid = modid;
		this.eveid = eveid;
	}

	/** 
	 * Sets the value of mpidNull
	 */
	public void setMpidNull(boolean mpidNull)
	{
		this.mpidNull = mpidNull;
	}

	/** 
	 * Gets the value of mpidNull
	 */
	public boolean isMpidNull()
	{
		return mpidNull;
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
		
		if (!(_other instanceof TPolicyDetailsPk)) {
			return false;
		}
		
		final TPolicyDetailsPk _cast = (TPolicyDetailsPk) _other;
		if (mpid != _cast.mpid) {
			return false;
		}
		
		if (modid != _cast.modid) {
			return false;
		}
		
		if (eveid != _cast.eveid) {
			return false;
		}
		
		if (mpidNull != _cast.mpidNull) {
			return false;
		}
		
		if (modidNull != _cast.modidNull) {
			return false;
		}
		
		if (eveidNull != _cast.eveidNull) {
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
		_hashCode = 29 * _hashCode + (mpidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (modidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (eveidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TPolicyDetailsPk: " );
		ret.append( "mpid=" + mpid );
		ret.append( ", modid=" + modid );
		ret.append( ", eveid=" + eveid );
		return ret.toString();
	}

}
