package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TManufacturerInfoInit implements Serializable
{
	/** 
	 * This attribute maps to the column MRID in the T_MANUFACTURER_INFO_INIT table.
	 */
	protected long mrid;

	/** 
	 * This attribute maps to the column MRNAME in the T_MANUFACTURER_INFO_INIT table.
	 */
	protected String mrname;

	/** 
	 * This attribute maps to the column OBJECTID in the T_MANUFACTURER_INFO_INIT table.
	 */
	protected String objectid;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_MANUFACTURER_INFO_INIT table.
	 */
	protected String description;

	/**
	 * Method 'TManufacturerInfoInit'
	 * 
	 */
	public TManufacturerInfoInit()
	{
	}

	/**
	 * Method 'getMrid'
	 * 
	 * @return long
	 */
	public long getMrid()
	{
		return mrid;
	}

	/**
	 * Method 'setMrid'
	 * 
	 * @param mrid
	 */
	public void setMrid(long mrid)
	{
		this.mrid = mrid;
	}

	/**
	 * Method 'getMrname'
	 * 
	 * @return String
	 */
	public String getMrname()
	{
		return mrname;
	}

	/**
	 * Method 'setMrname'
	 * 
	 * @param mrname
	 */
	public void setMrname(String mrname)
	{
		this.mrname = mrname;
	}

	/**
	 * Method 'getObjectid'
	 * 
	 * @return String
	 */
	public String getObjectid()
	{
		return objectid;
	}

	/**
	 * Method 'setObjectid'
	 * 
	 * @param objectid
	 */
	public void setObjectid(String objectid)
	{
		this.objectid = objectid;
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
		
		if (!(_other instanceof TManufacturerInfoInit)) {
			return false;
		}
		
		final TManufacturerInfoInit _cast = (TManufacturerInfoInit) _other;
		if (mrid != _cast.mrid) {
			return false;
		}
		
		if (mrname == null ? _cast.mrname != mrname : !mrname.equals( _cast.mrname )) {
			return false;
		}
		
		if (objectid == null ? _cast.objectid != objectid : !objectid.equals( _cast.objectid )) {
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
		_hashCode = 29 * _hashCode + (int) (mrid ^ (mrid >>> 32));
		if (mrname != null) {
			_hashCode = 29 * _hashCode + mrname.hashCode();
		}
		
		if (objectid != null) {
			_hashCode = 29 * _hashCode + objectid.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TManufacturerInfoInitPk
	 */
	public TManufacturerInfoInitPk createPk()
	{
		return new TManufacturerInfoInitPk(mrid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TManufacturerInfoInit: " );
		ret.append( "mrid=" + mrid );
		ret.append( ", mrname=" + mrname );
		ret.append( ", objectid=" + objectid );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
