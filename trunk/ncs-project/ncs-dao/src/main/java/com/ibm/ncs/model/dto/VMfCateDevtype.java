package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class VMfCateDevtype implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the V_MF_CATE_DEVTYPE table.
	 */
	protected long id;

	/** 
	 * This attribute maps to the column NAME in the V_MF_CATE_DEVTYPE table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column FLAG in the V_MF_CATE_DEVTYPE table.
	 */
	protected String flag;

	/** 
	 * This attribute maps to the column MRID in the V_MF_CATE_DEVTYPE table.
	 */
	protected long mrid;

	/** 
	 * This attribute maps to the column MRNAME in the V_MF_CATE_DEVTYPE table.
	 */
	protected String mrname;

	/** 
	 * This attribute maps to the column MF_OBJECTID in the V_MF_CATE_DEVTYPE table.
	 */
	protected String mfObjectid;

	/** 
	 * This attribute maps to the column MF_DESCRIPTION in the V_MF_CATE_DEVTYPE table.
	 */
	protected String mfDescription;

	/** 
	 * This attribute maps to the column DEVICETYPE_MRID in the V_MF_CATE_DEVTYPE table.
	 */
	protected long devicetypeMrid;

	/** 
	 * This attribute maps to the column DTID in the V_MF_CATE_DEVTYPE table.
	 */
	protected long dtid;

	/** 
	 * This attribute maps to the column CATEGORY in the V_MF_CATE_DEVTYPE table.
	 */
	protected long category;

	/** 
	 * This attribute represents whether the primitive attribute category is null.
	 */
	protected boolean categoryNull = true;

	/** 
	 * This attribute maps to the column SUBCATEGORY in the V_MF_CATE_DEVTYPE table.
	 */
	protected String subcategory;

	/** 
	 * This attribute maps to the column MODEL in the V_MF_CATE_DEVTYPE table.
	 */
	protected String model;

	/** 
	 * This attribute maps to the column OBJECTID in the V_MF_CATE_DEVTYPE table.
	 */
	protected String objectid;

	/** 
	 * This attribute maps to the column LOGO in the V_MF_CATE_DEVTYPE table.
	 */
	protected String logo;

	/** 
	 * This attribute maps to the column DESCRIPTION in the V_MF_CATE_DEVTYPE table.
	 */
	protected String description;

	/**
	 * Method 'VMfCateDevtype'
	 * 
	 */
	public VMfCateDevtype()
	{
	}

	/**
	 * Method 'getId'
	 * 
	 * @return long
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method 'getFlag'
	 * 
	 * @return String
	 */
	public String getFlag()
	{
		return flag;
	}

	/**
	 * Method 'setFlag'
	 * 
	 * @param flag
	 */
	public void setFlag(String flag)
	{
		this.flag = flag;
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
	 * Method 'getMfObjectid'
	 * 
	 * @return String
	 */
	public String getMfObjectid()
	{
		return mfObjectid;
	}

	/**
	 * Method 'setMfObjectid'
	 * 
	 * @param mfObjectid
	 */
	public void setMfObjectid(String mfObjectid)
	{
		this.mfObjectid = mfObjectid;
	}

	/**
	 * Method 'getMfDescription'
	 * 
	 * @return String
	 */
	public String getMfDescription()
	{
		return mfDescription;
	}

	/**
	 * Method 'setMfDescription'
	 * 
	 * @param mfDescription
	 */
	public void setMfDescription(String mfDescription)
	{
		this.mfDescription = mfDescription;
	}

	/**
	 * Method 'getDevicetypeMrid'
	 * 
	 * @return long
	 */
	public long getDevicetypeMrid()
	{
		return devicetypeMrid;
	}

	/**
	 * Method 'setDevicetypeMrid'
	 * 
	 * @param devicetypeMrid
	 */
	public void setDevicetypeMrid(long devicetypeMrid)
	{
		this.devicetypeMrid = devicetypeMrid;
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
		
		if (!(_other instanceof VMfCateDevtype)) {
			return false;
		}
		
		final VMfCateDevtype _cast = (VMfCateDevtype) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (flag == null ? _cast.flag != flag : !flag.equals( _cast.flag )) {
			return false;
		}
		
		if (mrid != _cast.mrid) {
			return false;
		}
		
		if (mrname == null ? _cast.mrname != mrname : !mrname.equals( _cast.mrname )) {
			return false;
		}
		
		if (mfObjectid == null ? _cast.mfObjectid != mfObjectid : !mfObjectid.equals( _cast.mfObjectid )) {
			return false;
		}
		
		if (mfDescription == null ? _cast.mfDescription != mfDescription : !mfDescription.equals( _cast.mfDescription )) {
			return false;
		}
		
		if (devicetypeMrid != _cast.devicetypeMrid) {
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
		_hashCode = 29 * _hashCode + (int) (id ^ (id >>> 32));
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		if (flag != null) {
			_hashCode = 29 * _hashCode + flag.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (mrid ^ (mrid >>> 32));
		if (mrname != null) {
			_hashCode = 29 * _hashCode + mrname.hashCode();
		}
		
		if (mfObjectid != null) {
			_hashCode = 29 * _hashCode + mfObjectid.hashCode();
		}
		
		if (mfDescription != null) {
			_hashCode = 29 * _hashCode + mfDescription.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (devicetypeMrid ^ (devicetypeMrid >>> 32));
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
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.VMfCateDevtype: " );
		ret.append( "id=" + id );
		ret.append( ", name=" + name );
		ret.append( ", flag=" + flag );
		ret.append( ", mrid=" + mrid );
		ret.append( ", mrname=" + mrname );
		ret.append( ", mfObjectid=" + mfObjectid );
		ret.append( ", mfDescription=" + mfDescription );
		ret.append( ", devicetypeMrid=" + devicetypeMrid );
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
