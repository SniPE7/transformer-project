package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_EVENTSUBTYPE_INFO_INIT table.
 */
public class TEventsubtypeInfoInitPk implements Serializable
{
	protected long estid;

	/** 
	 * This attribute represents whether the primitive attribute estid is null.
	 */
	protected boolean estidNull;

	/** 
	 * Sets the value of estid
	 */
	public void setEstid(long estid)
	{
		this.estid = estid;
	}

	/** 
	 * Gets the value of estid
	 */
	public long getEstid()
	{
		return estid;
	}

	/**
	 * Method 'TEventsubtypeInfoInitPk'
	 * 
	 */
	public TEventsubtypeInfoInitPk()
	{
	}

	/**
	 * Method 'TEventsubtypeInfoInitPk'
	 * 
	 * @param estid
	 */
	public TEventsubtypeInfoInitPk(final long estid)
	{
		this.estid = estid;
	}

	/** 
	 * Sets the value of estidNull
	 */
	public void setEstidNull(boolean estidNull)
	{
		this.estidNull = estidNull;
	}

	/** 
	 * Gets the value of estidNull
	 */
	public boolean isEstidNull()
	{
		return estidNull;
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
		
		if (!(_other instanceof TEventsubtypeInfoInitPk)) {
			return false;
		}
		
		final TEventsubtypeInfoInitPk _cast = (TEventsubtypeInfoInitPk) _other;
		if (estid != _cast.estid) {
			return false;
		}
		
		if (estidNull != _cast.estidNull) {
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
		_hashCode = 29 * _hashCode + (int) (estid ^ (estid >>> 32));
		_hashCode = 29 * _hashCode + (estidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TEventsubtypeInfoInitPk: " );
		ret.append( "estid=" + estid );
		return ret.toString();
	}

}
