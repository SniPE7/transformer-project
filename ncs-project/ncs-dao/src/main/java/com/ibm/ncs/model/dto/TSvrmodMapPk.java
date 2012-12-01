package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_SVRMOD_MAP table.
 */
public class TSvrmodMapPk implements Serializable
{
	protected long nmsid;

	protected long modid;

	/** 
	 * This attribute represents whether the primitive attribute nmsid is null.
	 */
	protected boolean nmsidNull;

	/** 
	 * This attribute represents whether the primitive attribute modid is null.
	 */
	protected boolean modidNull;

	/** 
	 * Sets the value of nmsid
	 */
	public void setNmsid(long nmsid)
	{
		this.nmsid = nmsid;
	}

	/** 
	 * Gets the value of nmsid
	 */
	public long getNmsid()
	{
		return nmsid;
	}

	/** 
	 * Sets the value of modid
	 */
	public void setModid(long modid)
	{
		this.modid = modid;
	}

	/** 
	 * Gets the value of modid
	 */
	public long getModid()
	{
		return modid;
	}

	/**
	 * Method 'TSvrmodMapPk'
	 * 
	 */
	public TSvrmodMapPk()
	{
	}

	/**
	 * Method 'TSvrmodMapPk'
	 * 
	 * @param nmsid
	 * @param modid
	 */
	public TSvrmodMapPk(final long nmsid, final long modid)
	{
		this.nmsid = nmsid;
		this.modid = modid;
	}

	/** 
	 * Sets the value of nmsidNull
	 */
	public void setNmsidNull(boolean nmsidNull)
	{
		this.nmsidNull = nmsidNull;
	}

	/** 
	 * Gets the value of nmsidNull
	 */
	public boolean isNmsidNull()
	{
		return nmsidNull;
	}

	/** 
	 * Sets the value of modidNull
	 */
	public void setModidNull(boolean modidNull)
	{
		this.modidNull = modidNull;
	}

	/** 
	 * Gets the value of modidNull
	 */
	public boolean isModidNull()
	{
		return modidNull;
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
		
		if (!(_other instanceof TSvrmodMapPk)) {
			return false;
		}
		
		final TSvrmodMapPk _cast = (TSvrmodMapPk) _other;
		if (nmsid != _cast.nmsid) {
			return false;
		}
		
		if (modid != _cast.modid) {
			return false;
		}
		
		if (nmsidNull != _cast.nmsidNull) {
			return false;
		}
		
		if (modidNull != _cast.modidNull) {
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
		_hashCode = 29 * _hashCode + (int) (nmsid ^ (nmsid >>> 32));
		_hashCode = 29 * _hashCode + (int) (modid ^ (modid >>> 32));
		_hashCode = 29 * _hashCode + (nmsidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (modidNull ? 1 : 0);
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
		ret.append( "com.ibm.ncs.model.dto.TSvrmodMapPk: " );
		ret.append( "nmsid=" + nmsid );
		ret.append( ", modid=" + modid );
		return ret.toString();
	}

}
