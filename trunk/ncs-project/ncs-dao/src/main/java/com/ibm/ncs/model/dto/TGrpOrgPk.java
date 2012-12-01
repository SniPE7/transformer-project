package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_GRP_ORG table.
 */
public class TGrpOrgPk implements Serializable
{
	protected long gid;

	/** 
	 * This attribute represents whether the primitive attribute gid is null.
	 */
	protected boolean gidNull;

	/** 
	 * Sets the value of gid
	 */
	public void setGid(long gid)
	{
		this.gid = gid;
	}

	/** 
	 * Gets the value of gid
	 */
	public long getGid()
	{
		return gid;
	}

	/**
	 * Method 'TGrpOrgPk'
	 * 
	 */
	public TGrpOrgPk()
	{
	}

	/**
	 * Method 'TGrpOrgPk'
	 * 
	 * @param gid
	 */
	public TGrpOrgPk(final long gid)
	{
		this.gid = gid;
	}

	/** 
	 * Sets the value of gidNull
	 */
	public void setGidNull(boolean gidNull)
	{
		this.gidNull = gidNull;
	}

	/** 
	 * Gets the value of gidNull
	 */
	public boolean isGidNull()
	{
		return gidNull;
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
		
		if (!(_other instanceof TGrpOrgPk)) {
			return false;
		}
		
		final TGrpOrgPk _cast = (TGrpOrgPk) _other;
		if (gid != _cast.gid) {
			return false;
		}
		
		if (gidNull != _cast.gidNull) {
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
		_hashCode = 29 * _hashCode + (int) (gid ^ (gid >>> 32));
		_hashCode = 29 * _hashCode + (gidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TGrpOrgPk: " );
		ret.append( "gid=" + gid );
		return ret.toString();
	}

}
