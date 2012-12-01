package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the PREDEFMIB_INFO table.
 */
public class PredefmibInfoPk implements Serializable
{
	protected long pdmid;

	/** 
	 * This attribute represents whether the primitive attribute pdmid is null.
	 */
	protected boolean pdmidNull;

	/** 
	 * Sets the value of pdmid
	 */
	public void setPdmid(long pdmid)
	{
		this.pdmid = pdmid;
	}

	/** 
	 * Gets the value of pdmid
	 */
	public long getPdmid()
	{
		return pdmid;
	}

	/**
	 * Method 'PredefmibInfoPk'
	 * 
	 */
	public PredefmibInfoPk()
	{
	}

	/**
	 * Method 'PredefmibInfoPk'
	 * 
	 * @param pdmid
	 */
	public PredefmibInfoPk(final long pdmid)
	{
		this.pdmid = pdmid;
	}

	/** 
	 * Sets the value of pdmidNull
	 */
	public void setPdmidNull(boolean pdmidNull)
	{
		this.pdmidNull = pdmidNull;
	}

	/** 
	 * Gets the value of pdmidNull
	 */
	public boolean isPdmidNull()
	{
		return pdmidNull;
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
		
		if (!(_other instanceof PredefmibInfoPk)) {
			return false;
		}
		
		final PredefmibInfoPk _cast = (PredefmibInfoPk) _other;
		if (pdmid != _cast.pdmid) {
			return false;
		}
		
		if (pdmidNull != _cast.pdmidNull) {
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
		_hashCode = 29 * _hashCode + (int) (pdmid ^ (pdmid >>> 32));
		_hashCode = 29 * _hashCode + (pdmidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.PredefmibInfoPk: " );
		ret.append( "pdmid=" + pdmid );
		return ret.toString();
	}

}
