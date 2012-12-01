package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the ICMP_THRESHOLDS table.
 */
public class IcmpThresholdsPk implements Serializable
{
	protected String ipaddress;

	/** 
	 * Sets the value of ipaddress
	 */
	public void setIpaddress(String ipaddress)
	{
		this.ipaddress = ipaddress;
	}

	/** 
	 * Gets the value of ipaddress
	 */
	public String getIpaddress()
	{
		return ipaddress;
	}

	/**
	 * Method 'IcmpThresholdsPk'
	 * 
	 */
	public IcmpThresholdsPk()
	{
	}

	/**
	 * Method 'IcmpThresholdsPk'
	 * 
	 * @param ipaddress
	 */
	public IcmpThresholdsPk(final String ipaddress)
	{
		this.ipaddress = ipaddress;
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
		
		if (!(_other instanceof IcmpThresholdsPk)) {
			return false;
		}
		
		final IcmpThresholdsPk _cast = (IcmpThresholdsPk) _other;
		if (ipaddress == null ? _cast.ipaddress != ipaddress : !ipaddress.equals( _cast.ipaddress )) {
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
		if (ipaddress != null) {
			_hashCode = 29 * _hashCode + ipaddress.hashCode();
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
		ret.append( "com.ibm.ncs.model.dto.IcmpThresholdsPk: " );
		ret.append( "ipaddress=" + ipaddress );
		return ret.toString();
	}

}
