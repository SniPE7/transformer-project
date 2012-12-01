package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_PP_PORT table.
 */
public class TPpPortPk implements Serializable
{
	protected String devip;

	protected String ifdescr;

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
	 * Sets the value of ifdescr
	 */
	public void setIfdescr(String ifdescr)
	{
		this.ifdescr = ifdescr;
	}

	/** 
	 * Gets the value of ifdescr
	 */
	public String getIfdescr()
	{
		return ifdescr;
	}

	/**
	 * Method 'TPpPortPk'
	 * 
	 */
	public TPpPortPk()
	{
	}

	/**
	 * Method 'TPpPortPk'
	 * 
	 * @param devip
	 * @param ifdescr
	 */
	public TPpPortPk(final String devip, final String ifdescr)
	{
		this.devip = devip;
		this.ifdescr = ifdescr;
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
		
		if (!(_other instanceof TPpPortPk)) {
			return false;
		}
		
		final TPpPortPk _cast = (TPpPortPk) _other;
		if (devip == null ? _cast.devip != devip : !devip.equals( _cast.devip )) {
			return false;
		}
		
		if (ifdescr == null ? _cast.ifdescr != ifdescr : !ifdescr.equals( _cast.ifdescr )) {
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
		
		if (ifdescr != null) {
			_hashCode = 29 * _hashCode + ifdescr.hashCode();
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
		ret.append( "com.ibm.ncs.model.dto.TPpPortPk: " );
		ret.append( "devip=" + devip );
		ret.append( ", ifdescr=" + ifdescr );
		return ret.toString();
	}

}
