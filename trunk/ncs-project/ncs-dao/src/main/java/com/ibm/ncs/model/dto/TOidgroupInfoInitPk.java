package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_OIDGROUP_INFO_INIT table.
 */
public class TOidgroupInfoInitPk implements Serializable
{
	protected long opid;

	/** 
	 * This attribute represents whether the primitive attribute opid is null.
	 */
	protected boolean opidNull;

	/** 
	 * Sets the value of opid
	 */
	public void setOpid(long opid)
	{
		this.opid = opid;
	}

	/** 
	 * Gets the value of opid
	 */
	public long getOpid()
	{
		return opid;
	}

	/**
	 * Method 'TOidgroupInfoInitPk'
	 * 
	 */
	public TOidgroupInfoInitPk()
	{
	}

	/**
	 * Method 'TOidgroupInfoInitPk'
	 * 
	 * @param opid
	 */
	public TOidgroupInfoInitPk(final long opid)
	{
		this.opid = opid;
	}

	/** 
	 * Sets the value of opidNull
	 */
	public void setOpidNull(boolean opidNull)
	{
		this.opidNull = opidNull;
	}

	/** 
	 * Gets the value of opidNull
	 */
	public boolean isOpidNull()
	{
		return opidNull;
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
		
		if (!(_other instanceof TOidgroupInfoInitPk)) {
			return false;
		}
		
		final TOidgroupInfoInitPk _cast = (TOidgroupInfoInitPk) _other;
		if (opid != _cast.opid) {
			return false;
		}
		
		if (opidNull != _cast.opidNull) {
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
		_hashCode = 29 * _hashCode + (int) (opid ^ (opid >>> 32));
		_hashCode = 29 * _hashCode + (opidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TOidgroupInfoInitPk: " );
		ret.append( "opid=" + opid );
		return ret.toString();
	}

}
