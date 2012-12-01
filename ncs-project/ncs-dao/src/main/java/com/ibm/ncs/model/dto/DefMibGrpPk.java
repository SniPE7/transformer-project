package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the DEF_MIB_GRP table.
 */
public class DefMibGrpPk implements Serializable
{
	protected long mid;

	/** 
	 * This attribute represents whether the primitive attribute mid is null.
	 */
	protected boolean midNull;

	/** 
	 * Sets the value of mid
	 */
	public void setMid(long mid)
	{
		this.mid = mid;
	}

	/** 
	 * Gets the value of mid
	 */
	public long getMid()
	{
		return mid;
	}

	/**
	 * Method 'DefMibGrpPk'
	 * 
	 */
	public DefMibGrpPk()
	{
	}

	/**
	 * Method 'DefMibGrpPk'
	 * 
	 * @param mid
	 */
	public DefMibGrpPk(final long mid)
	{
		this.mid = mid;
	}

	/** 
	 * Sets the value of midNull
	 */
	public void setMidNull(boolean midNull)
	{
		this.midNull = midNull;
	}

	/** 
	 * Gets the value of midNull
	 */
	public boolean isMidNull()
	{
		return midNull;
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
		
		if (!(_other instanceof DefMibGrpPk)) {
			return false;
		}
		
		final DefMibGrpPk _cast = (DefMibGrpPk) _other;
		if (mid != _cast.mid) {
			return false;
		}
		
		if (midNull != _cast.midNull) {
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
		_hashCode = 29 * _hashCode + (int) (mid ^ (mid >>> 32));
		_hashCode = 29 * _hashCode + (midNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.DefMibGrpPk: " );
		ret.append( "mid=" + mid );
		return ret.toString();
	}

}
