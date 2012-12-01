package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_PORT_INFO table.
 */
public class TPortInfoPk implements Serializable
{
	protected long ptid;

	/** 
	 * This attribute represents whether the primitive attribute ptid is null.
	 */
	protected boolean ptidNull;

	/** 
	 * Sets the value of ptid
	 */
	public void setPtid(long ptid)
	{
		this.ptid = ptid;
	}

	/** 
	 * Gets the value of ptid
	 */
	public long getPtid()
	{
		return ptid;
	}

	/**
	 * Method 'TPortInfoPk'
	 * 
	 */
	public TPortInfoPk()
	{
	}

	/**
	 * Method 'TPortInfoPk'
	 * 
	 * @param ptid
	 */
	public TPortInfoPk(final long ptid)
	{
		this.ptid = ptid;
	}

	/** 
	 * Sets the value of ptidNull
	 */
	public void setPtidNull(boolean ptidNull)
	{
		this.ptidNull = ptidNull;
	}

	/** 
	 * Gets the value of ptidNull
	 */
	public boolean isPtidNull()
	{
		return ptidNull;
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
		
		if (!(_other instanceof TPortInfoPk)) {
			return false;
		}
		
		final TPortInfoPk _cast = (TPortInfoPk) _other;
		if (ptid != _cast.ptid) {
			return false;
		}
		
		if (ptidNull != _cast.ptidNull) {
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
		_hashCode = 29 * _hashCode + (int) (ptid ^ (ptid >>> 32));
		_hashCode = 29 * _hashCode + (ptidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TPortInfoPk: " );
		ret.append( "ptid=" + ptid );
		return ret.toString();
	}

}
