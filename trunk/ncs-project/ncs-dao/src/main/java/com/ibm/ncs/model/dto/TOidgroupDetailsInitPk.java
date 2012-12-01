package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_OIDGROUP_DETAILS_INIT table.
 */
public class TOidgroupDetailsInitPk implements Serializable
{
	protected long opid;

	protected String oidname;

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
	 * Sets the value of oidname
	 */
	public void setOidname(String oidname)
	{
		this.oidname = oidname;
	}

	/** 
	 * Gets the value of oidname
	 */
	public String getOidname()
	{
		return oidname;
	}

	/**
	 * Method 'TOidgroupDetailsInitPk'
	 * 
	 */
	public TOidgroupDetailsInitPk()
	{
	}

	/**
	 * Method 'TOidgroupDetailsInitPk'
	 * 
	 * @param opid
	 * @param oidname
	 */
	public TOidgroupDetailsInitPk(final long opid, final String oidname)
	{
		this.opid = opid;
		this.oidname = oidname;
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
		
		if (!(_other instanceof TOidgroupDetailsInitPk)) {
			return false;
		}
		
		final TOidgroupDetailsInitPk _cast = (TOidgroupDetailsInitPk) _other;
		if (opid != _cast.opid) {
			return false;
		}
		
		if (oidname == null ? _cast.oidname != oidname : !oidname.equals( _cast.oidname )) {
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
		if (oidname != null) {
			_hashCode = 29 * _hashCode + oidname.hashCode();
		}
		
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
		ret.append( "com.ibm.ncs.model.dto.TOidgroupDetailsInitPk: " );
		ret.append( "opid=" + opid );
		ret.append( ", oidname=" + oidname );
		return ret.toString();
	}

}
