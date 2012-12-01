package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_DEVPOL_MAP table.
 */
public class TDevpolMapPk implements Serializable
{
	protected long devid;

	/** 
	 * This attribute represents whether the primitive attribute devid is null.
	 */
	protected boolean devidNull;

	/** 
	 * Sets the value of devid
	 */
	public void setDevid(long devid)
	{
		this.devid = devid;
	}

	/** 
	 * Gets the value of devid
	 */
	public long getDevid()
	{
		return devid;
	}

	/**
	 * Method 'TDevpolMapPk'
	 * 
	 */
	public TDevpolMapPk()
	{
	}

	/**
	 * Method 'TDevpolMapPk'
	 * 
	 * @param devid
	 */
	public TDevpolMapPk(final long devid)
	{
		this.devid = devid;
	}

	/** 
	 * Sets the value of devidNull
	 */
	public void setDevidNull(boolean devidNull)
	{
		this.devidNull = devidNull;
	}

	/** 
	 * Gets the value of devidNull
	 */
	public boolean isDevidNull()
	{
		return devidNull;
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
		
		if (!(_other instanceof TDevpolMapPk)) {
			return false;
		}
		
		final TDevpolMapPk _cast = (TDevpolMapPk) _other;
		if (devid != _cast.devid) {
			return false;
		}
		
		if (devidNull != _cast.devidNull) {
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
		_hashCode = 29 * _hashCode + (int) (devid ^ (devid >>> 32));
		_hashCode = 29 * _hashCode + (devidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TDevpolMapPk: " );
		ret.append( "devid=" + devid );
		return ret.toString();
	}

}
