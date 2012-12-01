package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TDeviceTypeInit implements Serializable
{
	/** 
	 * This attribute maps to the column MRID in the T_DEVICE_TYPE_INIT table.
	 */
	protected long mrid;

	/** 
	 * This attribute maps to the column DTID in the T_DEVICE_TYPE_INIT table.
	 */
	protected long dtid;

	/** 
	 * This attribute maps to the column CATEGORY in the T_DEVICE_TYPE_INIT table.
	 */
	protected long category;

	/** 
	 * This attribute represents whether the primitive attribute category is null.
	 */
	protected boolean categoryNull = true;

	/** 
	 * This attribute maps to the column SUBCATEGORY in the T_DEVICE_TYPE_INIT table.
	 */
	protected String subcategory;

	/** 
	 * This attribute maps to the column MODEL in the T_DEVICE_TYPE_INIT table.
	 */
	protected String model;

	/** 
	 * This attribute maps to the column OBJECTID in the T_DEVICE_TYPE_INIT table.
	 */
	protected String objectid;

	/** 
	 * This attribute maps to the column LOGO in the T_DEVICE_TYPE_INIT table.
	 */
	protected String logo;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_DEVICE_TYPE_INIT table.
	 */
	protected String description;

	/**
	 * Method 'TDeviceTypeInit'
	 * 
	 */
	public TDeviceTypeInit()
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
	 * Method 'getDtid'
	 * 
	 * @return long
	 */
	public long getDtid()
	{
		return dtid;
	}

	/**
	 * Method 'setDtid'
	 * 
	 * @param dtid
	 */
	public void setDtid(long dtid)
	{
		this.dtid = dtid;
	}

	/**
	 * Method 'getCategory'
	 * 
	 * @return long
	 */
	public long getCategory()
	{
		return category;
	}

	/**
	 * Method 'setCategory'
	 * 
	 * @param category
	 */
	public void setCategory(long category)
	{
		this.category = category;
		this.categoryNull = false;
	}

	/**
	 * Method 'setCategoryNull'
	 * 
	 * @param value
	 */
	public void setCategoryNull(boolean value)
	{
		this.categoryNull = value;
	}

	/**
	 * Method 'isCategoryNull'
	 * 
	 * @return boolean
	 */
	public boolean isCategoryNull()
	{
		return categoryNull;
	}

	/**
	 * Method 'getSubcategory'
	 * 
	 * @return String
	 */
	public String getSubcategory()
	{
		return subcategory;
	}

	/**
	 * Method 'setSubcategory'
	 * 
	 * @param subcategory
	 */
	public void setSubcategory(String subcategory)
	{
		this.subcategory = subcategory;
	}

	/**
	 * Method 'getModel'
	 * 
	 * @return String
	 */
	public String getModel()
	{
		return model;
	}

	/**
	 * Method 'setModel'
	 * 
	 * @param model
	 */
	public void setModel(String model)
	{
		this.model = model;
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
	 * Method 'getLogo'
	 * 
	 * @return String
	 */
	public String getLogo()
	{
		return logo;
	}

	/**
	 * Method 'setLogo'
	 * 
	 * @param logo
	 */
	public void setLogo(String logo)
	{
		this.logo = logo;
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
		
		if (!(_other instanceof TDeviceTypeInit)) {
			return false;
		}
		
		final TDeviceTypeInit _cast = (TDeviceTypeInit) _other;
		if (mrid != _cast.mrid) {
			return false;
		}
		
		if (dtid != _cast.dtid) {
			return false;
		}
		
		if (category != _cast.category) {
			return false;
		}
		
		if (categoryNull != _cast.categoryNull) {
			return false;
		}
		
		if (subcategory == null ? _cast.subcategory != subcategory : !subcategory.equals( _cast.subcategory )) {
			return false;
		}
		
		if (model == null ? _cast.model != model : !model.equals( _cast.model )) {
			return false;
		}
		
		if (objectid == null ? _cast.objectid != objectid : !objectid.equals( _cast.objectid )) {
			return false;
		}
		
		if (logo == null ? _cast.logo != logo : !logo.equals( _cast.logo )) {
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
		_hashCode = 29 * _hashCode + (int) (dtid ^ (dtid >>> 32));
		_hashCode = 29 * _hashCode + (int) (category ^ (category >>> 32));
		_hashCode = 29 * _hashCode + (categoryNull ? 1 : 0);
		if (subcategory != null) {
			_hashCode = 29 * _hashCode + subcategory.hashCode();
		}
		
		if (model != null) {
			_hashCode = 29 * _hashCode + model.hashCode();
		}
		
		if (objectid != null) {
			_hashCode = 29 * _hashCode + objectid.hashCode();
		}
		
		if (logo != null) {
			_hashCode = 29 * _hashCode + logo.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TDeviceTypeInitPk
	 */
	public TDeviceTypeInitPk createPk()
	{
		return new TDeviceTypeInitPk(dtid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TDeviceTypeInit: " );
		ret.append( "mrid=" + mrid );
		ret.append( ", dtid=" + dtid );
		ret.append( ", category=" + category );
		ret.append( ", subcategory=" + subcategory );
		ret.append( ", model=" + model );
		ret.append( ", objectid=" + objectid );
		ret.append( ", logo=" + logo );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
