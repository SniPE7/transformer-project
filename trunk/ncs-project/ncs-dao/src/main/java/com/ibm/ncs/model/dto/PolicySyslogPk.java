package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the POLICY_SYSLOG table.
 */
public class PolicySyslogPk implements Serializable
{
	protected long spid;

	/** 
	 * This attribute represents whether the primitive attribute spid is null.
	 */
	protected boolean spidNull;

	/** 
	 * Sets the value of spid
	 */
	public void setSpid(long spid)
	{
		this.spid = spid;
	}

	/** 
	 * Gets the value of spid
	 */
	public long getSpid()
	{
		return spid;
	}

	/**
	 * Method 'PolicySyslogPk'
	 * 
	 */
	public PolicySyslogPk()
	{
	}

	/**
	 * Method 'PolicySyslogPk'
	 * 
	 * @param spid
	 */
	public PolicySyslogPk(final long spid)
	{
		this.spid = spid;
	}

	/** 
	 * Sets the value of spidNull
	 */
	public void setSpidNull(boolean spidNull)
	{
		this.spidNull = spidNull;
	}

	/** 
	 * Gets the value of spidNull
	 */
	public boolean isSpidNull()
	{
		return spidNull;
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
		
		if (!(_other instanceof PolicySyslogPk)) {
			return false;
		}
		
		final PolicySyslogPk _cast = (PolicySyslogPk) _other;
		if (spid != _cast.spid) {
			return false;
		}
		
		if (spidNull != _cast.spidNull) {
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
		_hashCode = 29 * _hashCode + (int) (spid ^ (spid >>> 32));
		_hashCode = 29 * _hashCode + (spidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.PolicySyslogPk: " );
		ret.append( "spid=" + spid );
		return ret.toString();
	}

}
