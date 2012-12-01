package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_EVENTTYPE_INFO_INIT table.
 */
public class TEventtypeInfoInitPk implements Serializable
{
	protected long etid;

	/** 
	 * This attribute represents whether the primitive attribute etid is null.
	 */
	protected boolean etidNull;

	/** 
	 * Sets the value of etid
	 */
	public void setEtid(long etid)
	{
		this.etid = etid;
	}

	/** 
	 * Gets the value of etid
	 */
	public long getEtid()
	{
		return etid;
	}

	/**
	 * Method 'TEventtypeInfoInitPk'
	 * 
	 */
	public TEventtypeInfoInitPk()
	{
	}

	/**
	 * Method 'TEventtypeInfoInitPk'
	 * 
	 * @param etid
	 */
	public TEventtypeInfoInitPk(final long etid)
	{
		this.etid = etid;
	}

	/** 
	 * Sets the value of etidNull
	 */
	public void setEtidNull(boolean etidNull)
	{
		this.etidNull = etidNull;
	}

	/** 
	 * Gets the value of etidNull
	 */
	public boolean isEtidNull()
	{
		return etidNull;
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
		
		if (!(_other instanceof TEventtypeInfoInitPk)) {
			return false;
		}
		
		final TEventtypeInfoInitPk _cast = (TEventtypeInfoInitPk) _other;
		if (etid != _cast.etid) {
			return false;
		}
		
		if (etidNull != _cast.etidNull) {
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
		_hashCode = 29 * _hashCode + (int) (etid ^ (etid >>> 32));
		_hashCode = 29 * _hashCode + (etidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TEventtypeInfoInitPk: " );
		ret.append( "etid=" + etid );
		return ret.toString();
	}

}
