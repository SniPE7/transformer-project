package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_USER table.
 */
public class TUserPk implements Serializable
{
	protected long usid;

	/** 
	 * This attribute represents whether the primitive attribute usid is null.
	 */
	protected boolean usidNull;

	/** 
	 * Sets the value of usid
	 */
	public void setUsid(long usid)
	{
		this.usid = usid;
	}

	/** 
	 * Gets the value of usid
	 */
	public long getUsid()
	{
		return usid;
	}

	/**
	 * Method 'TUserPk'
	 * 
	 */
	public TUserPk()
	{
	}

	/**
	 * Method 'TUserPk'
	 * 
	 * @param usid
	 */
	public TUserPk(final long usid)
	{
		this.usid = usid;
	}

	/** 
	 * Sets the value of usidNull
	 */
	public void setUsidNull(boolean usidNull)
	{
		this.usidNull = usidNull;
	}

	/** 
	 * Gets the value of usidNull
	 */
	public boolean isUsidNull()
	{
		return usidNull;
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
		
		if (!(_other instanceof TUserPk)) {
			return false;
		}
		
		final TUserPk _cast = (TUserPk) _other;
		if (usid != _cast.usid) {
			return false;
		}
		
		if (usidNull != _cast.usidNull) {
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
		_hashCode = 29 * _hashCode + (int) (usid ^ (usid >>> 32));
		_hashCode = 29 * _hashCode + (usidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TUserPk: " );
		ret.append( "usid=" + usid );
		return ret.toString();
	}

}
