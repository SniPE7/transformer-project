package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TModuleInfoInit implements Serializable
{
	/** 
	 * This attribute maps to the column MODID in the T_MODULE_INFO_INIT table.
	 */
	protected long modid;

	/** 
	 * This attribute maps to the column MNAME in the T_MODULE_INFO_INIT table.
	 */
	protected String mname;

	/** 
	 * This attribute maps to the column MCODE in the T_MODULE_INFO_INIT table.
	 */
	protected long mcode;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_MODULE_INFO_INIT table.
	 */
	protected String description;

	/**
	 * Method 'TModuleInfoInit'
	 * 
	 */
	public TModuleInfoInit()
	{
	}

	/**
	 * Method 'getModid'
	 * 
	 * @return long
	 */
	public long getModid()
	{
		return modid;
	}

	/**
	 * Method 'setModid'
	 * 
	 * @param modid
	 */
	public void setModid(long modid)
	{
		this.modid = modid;
	}

	/**
	 * Method 'getMname'
	 * 
	 * @return String
	 */
	public String getMname()
	{
		return mname;
	}

	/**
	 * Method 'setMname'
	 * 
	 * @param mname
	 */
	public void setMname(String mname)
	{
		this.mname = mname;
	}

	/**
	 * Method 'getMcode'
	 * 
	 * @return long
	 */
	public long getMcode()
	{
		return mcode;
	}

	/**
	 * Method 'setMcode'
	 * 
	 * @param mcode
	 */
	public void setMcode(long mcode)
	{
		this.mcode = mcode;
	}

	/**
	 * Method 'getDescription'
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Method 'setDescription'
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
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
		
		if (!(_other instanceof TModuleInfoInit)) {
			return false;
		}
		
		final TModuleInfoInit _cast = (TModuleInfoInit) _other;
		if (modid != _cast.modid) {
			return false;
		}
		
		if (mname == null ? _cast.mname != mname : !mname.equals( _cast.mname )) {
			return false;
		}
		
		if (mcode != _cast.mcode) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
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
		if (mname != null) {
			_hashCode = 29 * _hashCode + mname.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (mcode ^ (mcode >>> 32));
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TModuleInfoInitPk
	 */
	public TModuleInfoInitPk createPk()
	{
		return new TModuleInfoInitPk(modid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TModuleInfoInit: " );
		ret.append( "modid=" + modid );
		ret.append( ", mname=" + mname );
		ret.append( ", mcode=" + mcode );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
