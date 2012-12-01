package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_LINE_INFO table.
 */
public class TLineInfoPk implements Serializable
{
	protected long leid;

	/** 
	 * This attribute represents whether the primitive attribute leid is null.
	 */
	protected boolean leidNull;

	/** 
	 * Sets the value of leid
	 */
	public void setLeid(long leid)
	{
		this.leid = leid;
	}

	/** 
	 * Gets the value of leid
	 */
	public long getLeid()
	{
		return leid;
	}

	/**
	 * Method 'TLineInfoPk'
	 * 
	 */
	public TLineInfoPk()
	{
	}

	/**
	 * Method 'TLineInfoPk'
	 * 
	 * @param leid
	 */
	public TLineInfoPk(final long leid)
	{
		this.leid = leid;
	}

	/** 
	 * Sets the value of leidNull
	 */
	public void setLeidNull(boolean leidNull)
	{
		this.leidNull = leidNull;
	}

	/** 
	 * Gets the value of leidNull
	 */
	public boolean isLeidNull()
	{
		return leidNull;
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
		
		if (!(_other instanceof TLineInfoPk)) {
			return false;
		}
		
		final TLineInfoPk _cast = (TLineInfoPk) _other;
		if (leid != _cast.leid) {
			return false;
		}
		
		if (leidNull != _cast.leidNull) {
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
		_hashCode = 29 * _hashCode + (int) (leid ^ (leid >>> 32));
		_hashCode = 29 * _hashCode + (leidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TLineInfoPk: " );
		ret.append( "leid=" + leid );
		return ret.toString();
	}

}
