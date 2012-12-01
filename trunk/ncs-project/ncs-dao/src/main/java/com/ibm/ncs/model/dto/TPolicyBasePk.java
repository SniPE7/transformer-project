package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_POLICY_BASE table.
 */
public class TPolicyBasePk implements Serializable
{
	protected long mpid;

	/** 
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean mpidNull;

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
	 * Method 'TPolicyBasePk'
	 * 
	 */
	public TPolicyBasePk()
	{
	}

	/**
	 * Method 'TPolicyBasePk'
	 * 
	 * @param mpid
	 */
	public TPolicyBasePk(final long mpid)
	{
		this.mpid = mpid;
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
		
		if (!(_other instanceof TPolicyBasePk)) {
			return false;
		}
		
		final TPolicyBasePk _cast = (TPolicyBasePk) _other;
		if (mpid != _cast.mpid) {
			return false;
		}
		
		if (mpidNull != _cast.mpidNull) {
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
		_hashCode = 29 * _hashCode + (mpidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TPolicyBasePk: " );
		ret.append( "mpid=" + mpid );
		return ret.toString();
	}

}
