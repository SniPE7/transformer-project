package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the T_MODULE_INFO_INIT table.
 */
public class TModuleInfoInitPk implements Serializable
{
	protected long modid;

	/** 
	 * This attribute represents whether the primitive attribute modid is null.
	 */
	protected boolean modidNull;

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
	 * Method 'TModuleInfoInitPk'
	 * 
	 */
	public TModuleInfoInitPk()
	{
	}

	/**
	 * Method 'TModuleInfoInitPk'
	 * 
	 * @param modid
	 */
	public TModuleInfoInitPk(final long modid)
	{
		this.modid = modid;
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
		
		if (!(_other instanceof TModuleInfoInitPk)) {
			return false;
		}
		
		final TModuleInfoInitPk _cast = (TModuleInfoInitPk) _other;
		if (modid != _cast.modid) {
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
		_hashCode = 29 * _hashCode + (int) (modid ^ (modid >>> 32));
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
		ret.append( "com.ibm.ncs.model.dto.TModuleInfoInitPk: " );
		ret.append( "modid=" + modid );
		return ret.toString();
	}

}
