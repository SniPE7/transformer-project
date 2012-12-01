package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_MANUFACTURER_INFO_INIT table.
 */
public class TManufacturerInfoInitPk implements Serializable
{
	protected long mrid;

	/** 
	 * This attribute represents whether the primitive attribute mrid is null.
	 */
	protected boolean mridNull;

	/** 
	 * Sets the value of mrid
	 */
	public void setMrid(long mrid)
	{
		this.mrid = mrid;
	}

	/** 
	 * Gets the value of mrid
	 */
	public long getMrid()
	{
		return mrid;
	}

	/**
	 * Method 'TManufacturerInfoInitPk'
	 * 
	 */
	public TManufacturerInfoInitPk()
	{
	}

	/**
	 * Method 'TManufacturerInfoInitPk'
	 * 
	 * @param mrid
	 */
	public TManufacturerInfoInitPk(final long mrid)
	{
		this.mrid = mrid;
	}

	/** 
	 * Sets the value of mridNull
	 */
	public void setMridNull(boolean mridNull)
	{
		this.mridNull = mridNull;
	}

	/** 
	 * Gets the value of mridNull
	 */
	public boolean isMridNull()
	{
		return mridNull;
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
		
		if (!(_other instanceof TManufacturerInfoInitPk)) {
			return false;
		}
		
		final TManufacturerInfoInitPk _cast = (TManufacturerInfoInitPk) _other;
		if (mrid != _cast.mrid) {
			return false;
		}
		
		if (mridNull != _cast.mridNull) {
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
		_hashCode = 29 * _hashCode + (int) (mrid ^ (mrid >>> 32));
		_hashCode = 29 * _hashCode + (mridNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TManufacturerInfoInitPk: " );
		ret.append( "mrid=" + mrid );
		return ret.toString();
	}

}
