package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_TCPPORTPOL_MAP table.
 */
public class TTcpportpolMapPk implements Serializable
{
	protected long hid;

	/** 
	 * This attribute represents whether the primitive attribute hid is null.
	 */
	protected boolean hidNull;

	/** 
	 * Sets the value of hid
	 */
	public void setHid(long hid)
	{
		this.hid = hid;
	}

	/** 
	 * Gets the value of hid
	 */
	public long getHid()
	{
		return hid;
	}

	/**
	 * Method 'TTcpportpolMapPk'
	 * 
	 */
	public TTcpportpolMapPk()
	{
	}

	/**
	 * Method 'TTcpportpolMapPk'
	 * 
	 * @param hid
	 */
	public TTcpportpolMapPk(final long hid)
	{
		this.hid = hid;
	}

	/** 
	 * Sets the value of hidNull
	 */
	public void setHidNull(boolean hidNull)
	{
		this.hidNull = hidNull;
	}

	/** 
	 * Gets the value of hidNull
	 */
	public boolean isHidNull()
	{
		return hidNull;
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
		
		if (!(_other instanceof TTcpportpolMapPk)) {
			return false;
		}
		
		final TTcpportpolMapPk _cast = (TTcpportpolMapPk) _other;
		if (hid != _cast.hid) {
			return false;
		}
		
		if (hidNull != _cast.hidNull) {
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
		_hashCode = 29 * _hashCode + (int) (hid ^ (hid >>> 32));
		_hashCode = 29 * _hashCode + (hidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TTcpportpolMapPk: " );
		ret.append( "hid=" + hid );
		return ret.toString();
	}

}
