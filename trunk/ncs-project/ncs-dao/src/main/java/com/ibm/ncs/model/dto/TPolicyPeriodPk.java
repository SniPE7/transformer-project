package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_POLICY_PERIOD table.
 */
public class TPolicyPeriodPk implements Serializable
{
	protected long ppid;

	/** 
	 * This attribute represents whether the primitive attribute ppid is null.
	 */
	protected boolean ppidNull;

	/** 
	 * Sets the value of ppid
	 */
	public void setPpid(long ppid)
	{
		this.ppid = ppid;
	}

	/** 
	 * Gets the value of ppid
	 */
	public long getPpid()
	{
		return ppid;
	}

	/**
	 * Method 'TPolicyPeriodPk'
	 * 
	 */
	public TPolicyPeriodPk()
	{
	}

	/**
	 * Method 'TPolicyPeriodPk'
	 * 
	 * @param ppid
	 */
	public TPolicyPeriodPk(final long ppid)
	{
		this.ppid = ppid;
	}

	/** 
	 * Sets the value of ppidNull
	 */
	public void setPpidNull(boolean ppidNull)
	{
		this.ppidNull = ppidNull;
	}

	/** 
	 * Gets the value of ppidNull
	 */
	public boolean isPpidNull()
	{
		return ppidNull;
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
		
		if (!(_other instanceof TPolicyPeriodPk)) {
			return false;
		}
		
		final TPolicyPeriodPk _cast = (TPolicyPeriodPk) _other;
		if (ppid != _cast.ppid) {
			return false;
		}
		
		if (ppidNull != _cast.ppidNull) {
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
		_hashCode = 29 * _hashCode + (int) (ppid ^ (ppid >>> 32));
		_hashCode = 29 * _hashCode + (ppidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TPolicyPeriodPk: " );
		ret.append( "ppid=" + ppid );
		return ret.toString();
	}

}
