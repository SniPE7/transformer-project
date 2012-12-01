package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the BK_SNMP_EVENTS_PROCESS table.
 */
public class BkSnmpEventsProcessPk implements Serializable
{
	protected long bkId;

	/** 
	 * This attribute represents whether the primitive attribute bkId is null.
	 */
	protected boolean bkIdNull;

	/** 
	 * Sets the value of bkId
	 */
	public void setBkId(long bkId)
	{
		this.bkId = bkId;
	}

	/** 
	 * Gets the value of bkId
	 */
	public long getBkId()
	{
		return bkId;
	}

	/**
	 * Method 'BkSnmpEventsProcessPk'
	 * 
	 */
	public BkSnmpEventsProcessPk()
	{
	}

	/**
	 * Method 'BkSnmpEventsProcessPk'
	 * 
	 * @param bkId
	 */
	public BkSnmpEventsProcessPk(final long bkId)
	{
		this.bkId = bkId;
	}

	/** 
	 * Sets the value of bkIdNull
	 */
	public void setBkIdNull(boolean bkIdNull)
	{
		this.bkIdNull = bkIdNull;
	}

	/** 
	 * Gets the value of bkIdNull
	 */
	public boolean isBkIdNull()
	{
		return bkIdNull;
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
		
		if (!(_other instanceof BkSnmpEventsProcessPk)) {
			return false;
		}
		
		final BkSnmpEventsProcessPk _cast = (BkSnmpEventsProcessPk) _other;
		if (bkId != _cast.bkId) {
			return false;
		}
		
		if (bkIdNull != _cast.bkIdNull) {
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
		_hashCode = 29 * _hashCode + (int) (bkId ^ (bkId >>> 32));
		_hashCode = 29 * _hashCode + (bkIdNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.BkSnmpEventsProcessPk: " );
		ret.append( "bkId=" + bkId );
		return ret.toString();
	}

}
