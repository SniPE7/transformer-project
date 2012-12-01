package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_PP_DEV table.
 */
public class TPpDevPk implements Serializable
{
	protected String devip;

	/** 
	 * Sets the value of devip
	 */
	public void setDevip(String devip)
	{
		this.devip = devip;
	}

	/** 
	 * Gets the value of devip
	 */
	public String getDevip()
	{
		return devip;
	}

	/**
	 * Method 'TPpDevPk'
	 * 
	 */
	public TPpDevPk()
	{
	}

	/**
	 * Method 'TPpDevPk'
	 * 
	 * @param devip
	 */
	public TPpDevPk(final String devip)
	{
		this.devip = devip;
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
		
		if (!(_other instanceof TPpDevPk)) {
			return false;
		}
		
		final TPpDevPk _cast = (TPpDevPk) _other;
		if (devip == null ? _cast.devip != devip : !devip.equals( _cast.devip )) {
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
		if (devip != null) {
			_hashCode = 29 * _hashCode + devip.hashCode();
		}
		
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
		ret.append( "com.ibm.ncs.model.dto.TPpDevPk: " );
		ret.append( "devip=" + devip );
		return ret.toString();
	}

}
