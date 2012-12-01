package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_DEVICE_TYPE_INIT table.
 */
public class TDeviceTypeInitPk implements Serializable
{
	protected long dtid;

	/** 
	 * This attribute represents whether the primitive attribute dtid is null.
	 */
	protected boolean dtidNull;

	/** 
	 * Sets the value of dtid
	 */
	public void setDtid(long dtid)
	{
		this.dtid = dtid;
	}

	/** 
	 * Gets the value of dtid
	 */
	public long getDtid()
	{
		return dtid;
	}

	/**
	 * Method 'TDeviceTypeInitPk'
	 * 
	 */
	public TDeviceTypeInitPk()
	{
	}

	/**
	 * Method 'TDeviceTypeInitPk'
	 * 
	 * @param dtid
	 */
	public TDeviceTypeInitPk(final long dtid)
	{
		this.dtid = dtid;
	}

	/** 
	 * Sets the value of dtidNull
	 */
	public void setDtidNull(boolean dtidNull)
	{
		this.dtidNull = dtidNull;
	}

	/** 
	 * Gets the value of dtidNull
	 */
	public boolean isDtidNull()
	{
		return dtidNull;
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
		
		if (!(_other instanceof TDeviceTypeInitPk)) {
			return false;
		}
		
		final TDeviceTypeInitPk _cast = (TDeviceTypeInitPk) _other;
		if (dtid != _cast.dtid) {
			return false;
		}
		
		if (dtidNull != _cast.dtidNull) {
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
		_hashCode = 29 * _hashCode + (int) (dtid ^ (dtid >>> 32));
		_hashCode = 29 * _hashCode + (dtidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TDeviceTypeInitPk: " );
		ret.append( "dtid=" + dtid );
		return ret.toString();
	}

}
