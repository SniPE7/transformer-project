package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_SERVER_INFO table.
 */
public class TServerInfoPk implements Serializable
{
	protected long nmsid;

	/** 
	 * This attribute represents whether the primitive attribute nmsid is null.
	 */
	protected boolean nmsidNull;

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
	 * Method 'TServerInfoPk'
	 * 
	 */
	public TServerInfoPk()
	{
	}

	/**
	 * Method 'TServerInfoPk'
	 * 
	 * @param nmsid
	 */
	public TServerInfoPk(final long nmsid)
	{
		this.nmsid = nmsid;
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
		
		if (!(_other instanceof TServerInfoPk)) {
			return false;
		}
		
		final TServerInfoPk _cast = (TServerInfoPk) _other;
		if (nmsid != _cast.nmsid) {
			return false;
		}
		
		if (nmsidNull != _cast.nmsidNull) {
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
		_hashCode = 29 * _hashCode + (int) (nmsid ^ (nmsid >>> 32));
		_hashCode = 29 * _hashCode + (nmsidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TServerInfoPk: " );
		ret.append( "nmsid=" + nmsid );
		return ret.toString();
	}

}
