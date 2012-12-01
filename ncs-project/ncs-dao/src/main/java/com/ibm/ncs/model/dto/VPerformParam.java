package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class VPerformParam implements Serializable
{
	/** 
	 * This attribute maps to the column EVENTTYPE_MODID in the V_PERFORM_PARAM table.
	 */
	protected long eventtypeModid;

	/** 
	 * This attribute maps to the column EVENTTYPE_EVEID in the V_PERFORM_PARAM table.
	 */
	protected long eventtypeEveid;

	/** 
	 * This attribute maps to the column ETID in the V_PERFORM_PARAM table.
	 */
	protected long etid;

	/** 
	 * This attribute represents whether the primitive attribute etid is null.
	 */
	protected boolean etidNull = true;

	/** 
	 * This attribute maps to the column ESTID in the V_PERFORM_PARAM table.
	 */
	protected long estid;

	/** 
	 * This attribute represents whether the primitive attribute estid is null.
	 */
	protected boolean estidNull = true;

	/** 
	 * This attribute maps to the column EVEOTHERNAME in the V_PERFORM_PARAM table.
	 */
	protected String eveothername;

	/** 
	 * This attribute maps to the column ECODE in the V_PERFORM_PARAM table.
	 */
	protected long ecode;

	/** 
	 * This attribute maps to the column GENERAL in the V_PERFORM_PARAM table.
	 */
	protected long general;

	/** 
	 * This attribute represents whether the primitive attribute general is null.
	 */
	protected boolean generalNull = true;

	/** 
	 * This attribute maps to the column MAJOR in the V_PERFORM_PARAM table.
	 */
	protected String major;

	/** 
	 * This attribute maps to the column MINOR in the V_PERFORM_PARAM table.
	 */
	protected String minor;

	/** 
	 * This attribute maps to the column OTHER in the V_PERFORM_PARAM table.
	 */
	protected String other;

	/** 
	 * This attribute maps to the column EVENTTYPE_DESCRIPTION in the V_PERFORM_PARAM table.
	 */
	protected String eventtypeDescription;

	/** 
	 * This attribute maps to the column USEFLAG in the V_PERFORM_PARAM table.
	 */
	protected String useflag;

	/** 
	 * This attribute maps to the column OIDGROUP_OPID in the V_PERFORM_PARAM table.
	 */
	protected long oidgroupOpid;

	/** 
	 * This attribute maps to the column OIDGROUP_OIDGROUPNAME in the V_PERFORM_PARAM table.
	 */
	protected String oidgroupOidgroupname;

	/** 
	 * This attribute maps to the column OTYPE in the V_PERFORM_PARAM table.
	 */
	protected long otype;

	/** 
	 * This attribute maps to the column OIDGROUP_DESCRIPTION in the V_PERFORM_PARAM table.
	 */
	protected String oidgroupDescription;

	/** 
	 * This attribute maps to the column EVEID in the V_PERFORM_PARAM table.
	 */
	protected long eveid;

	/** 
	 * This attribute maps to the column MRID in the V_PERFORM_PARAM table.
	 */
	protected long mrid;

	/** 
	 * This attribute maps to the column DTID in the V_PERFORM_PARAM table.
	 */
	protected long dtid;

	/** 
	 * This attribute maps to the column OIDGROUPNAME in the V_PERFORM_PARAM table.
	 */
	protected String oidgroupname;

	/** 
	 * This attribute maps to the column MODID in the V_PERFORM_PARAM table.
	 */
	protected long modid;

	/** 
	 * This attribute maps to the column OID in the V_PERFORM_PARAM table.
	 */
	protected String oid;

	/** 
	 * This attribute maps to the column UNIT in the V_PERFORM_PARAM table.
	 */
	protected String unit;

	/** 
	 * This attribute maps to the column DESCRIPTION in the V_PERFORM_PARAM table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column DEVTYPE_MRID in the V_PERFORM_PARAM table.
	 */
	protected long devtypeMrid;

	/** 
	 * This attribute maps to the column DEVTYPE_DTID in the V_PERFORM_PARAM table.
	 */
	protected long devtypeDtid;

	/** 
	 * This attribute maps to the column CATEGORY in the V_PERFORM_PARAM table.
	 */
	protected long category;

	/** 
	 * This attribute represents whether the primitive attribute category is null.
	 */
	protected boolean categoryNull = true;

	/** 
	 * This attribute maps to the column SUBCATEGORY in the V_PERFORM_PARAM table.
	 */
	protected String subcategory;

	/** 
	 * This attribute maps to the column MODEL in the V_PERFORM_PARAM table.
	 */
	protected String model;

	/** 
	 * This attribute maps to the column OBJECTID in the V_PERFORM_PARAM table.
	 */
	protected String objectid;

	/** 
	 * This attribute maps to the column LOGO in the V_PERFORM_PARAM table.
	 */
	protected String logo;

	/** 
	 * This attribute maps to the column DEVTYPE_DESCRIPTION in the V_PERFORM_PARAM table.
	 */
	protected String devtypeDescription;

	/**
	 * Method 'VPerformParam'
	 * 
	 */
	public VPerformParam()
	{
	}

	/**
	 * Method 'getEventtypeModid'
	 * 
	 * @return long
	 */
	public long getEventtypeModid()
	{
		return eventtypeModid;
	}

	/**
	 * Method 'setEventtypeModid'
	 * 
	 * @param eventtypeModid
	 */
	public void setEventtypeModid(long eventtypeModid)
	{
		this.eventtypeModid = eventtypeModid;
	}

	/**
	 * Method 'getEventtypeEveid'
	 * 
	 * @return long
	 */
	public long getEventtypeEveid()
	{
		return eventtypeEveid;
	}

	/**
	 * Method 'setEventtypeEveid'
	 * 
	 * @param eventtypeEveid
	 */
	public void setEventtypeEveid(long eventtypeEveid)
	{
		this.eventtypeEveid = eventtypeEveid;
	}

	/**
	 * Method 'getEtid'
	 * 
	 * @return long
	 */
	public long getEtid()
	{
		return etid;
	}

	/**
	 * Method 'setEtid'
	 * 
	 * @param etid
	 */
	public void setEtid(long etid)
	{
		this.etid = etid;
		this.etidNull = false;
	}

	/**
	 * Method 'setEtidNull'
	 * 
	 * @param value
	 */
	public void setEtidNull(boolean value)
	{
		this.etidNull = value;
	}

	/**
	 * Method 'isEtidNull'
	 * 
	 * @return boolean
	 */
	public boolean isEtidNull()
	{
		return etidNull;
	}

	/**
	 * Method 'getEstid'
	 * 
	 * @return long
	 */
	public long getEstid()
	{
		return estid;
	}

	/**
	 * Method 'setEstid'
	 * 
	 * @param estid
	 */
	public void setEstid(long estid)
	{
		this.estid = estid;
		this.estidNull = false;
	}

	/**
	 * Method 'setEstidNull'
	 * 
	 * @param value
	 */
	public void setEstidNull(boolean value)
	{
		this.estidNull = value;
	}

	/**
	 * Method 'isEstidNull'
	 * 
	 * @return boolean
	 */
	public boolean isEstidNull()
	{
		return estidNull;
	}

	/**
	 * Method 'getEveothername'
	 * 
	 * @return String
	 */
	public String getEveothername()
	{
		return eveothername;
	}

	/**
	 * Method 'setEveothername'
	 * 
	 * @param eveothername
	 */
	public void setEveothername(String eveothername)
	{
		this.eveothername = eveothername;
	}

	/**
	 * Method 'getEcode'
	 * 
	 * @return long
	 */
	public long getEcode()
	{
		return ecode;
	}

	/**
	 * Method 'setEcode'
	 * 
	 * @param ecode
	 */
	public void setEcode(long ecode)
	{
		this.ecode = ecode;
	}

	/**
	 * Method 'getGeneral'
	 * 
	 * @return long
	 */
	public long getGeneral()
	{
		return general;
	}

	/**
	 * Method 'setGeneral'
	 * 
	 * @param general
	 */
	public void setGeneral(long general)
	{
		this.general = general;
		this.generalNull = false;
	}

	/**
	 * Method 'setGeneralNull'
	 * 
	 * @param value
	 */
	public void setGeneralNull(boolean value)
	{
		this.generalNull = value;
	}

	/**
	 * Method 'isGeneralNull'
	 * 
	 * @return boolean
	 */
	public boolean isGeneralNull()
	{
		return generalNull;
	}

	/**
	 * Method 'getMajor'
	 * 
	 * @return String
	 */
	public String getMajor()
	{
		return major;
	}

	/**
	 * Method 'setMajor'
	 * 
	 * @param major
	 */
	public void setMajor(String major)
	{
		this.major = major;
	}

	/**
	 * Method 'getMinor'
	 * 
	 * @return String
	 */
	public String getMinor()
	{
		return minor;
	}

	/**
	 * Method 'setMinor'
	 * 
	 * @param minor
	 */
	public void setMinor(String minor)
	{
		this.minor = minor;
	}

	/**
	 * Method 'getOther'
	 * 
	 * @return String
	 */
	public String getOther()
	{
		return other;
	}

	/**
	 * Method 'setOther'
	 * 
	 * @param other
	 */
	public void setOther(String other)
	{
		this.other = other;
	}

	/**
	 * Method 'getEventtypeDescription'
	 * 
	 * @return String
	 */
	public String getEventtypeDescription()
	{
		return eventtypeDescription;
	}

	/**
	 * Method 'setEventtypeDescription'
	 * 
	 * @param eventtypeDescription
	 */
	public void setEventtypeDescription(String eventtypeDescription)
	{
		this.eventtypeDescription = eventtypeDescription;
	}

	/**
	 * Method 'getUseflag'
	 * 
	 * @return String
	 */
	public String getUseflag()
	{
		return useflag;
	}

	/**
	 * Method 'setUseflag'
	 * 
	 * @param useflag
	 */
	public void setUseflag(String useflag)
	{
		this.useflag = useflag;
	}

	/**
	 * Method 'getOidgroupOpid'
	 * 
	 * @return long
	 */
	public long getOidgroupOpid()
	{
		return oidgroupOpid;
	}

	/**
	 * Method 'setOidgroupOpid'
	 * 
	 * @param oidgroupOpid
	 */
	public void setOidgroupOpid(long oidgroupOpid)
	{
		this.oidgroupOpid = oidgroupOpid;
	}

	/**
	 * Method 'getOidgroupOidgroupname'
	 * 
	 * @return String
	 */
	public String getOidgroupOidgroupname()
	{
		return oidgroupOidgroupname;
	}

	/**
	 * Method 'setOidgroupOidgroupname'
	 * 
	 * @param oidgroupOidgroupname
	 */
	public void setOidgroupOidgroupname(String oidgroupOidgroupname)
	{
		this.oidgroupOidgroupname = oidgroupOidgroupname;
	}

	/**
	 * Method 'getOtype'
	 * 
	 * @return long
	 */
	public long getOtype()
	{
		return otype;
	}

	/**
	 * Method 'setOtype'
	 * 
	 * @param otype
	 */
	public void setOtype(long otype)
	{
		this.otype = otype;
	}

	/**
	 * Method 'getOidgroupDescription'
	 * 
	 * @return String
	 */
	public String getOidgroupDescription()
	{
		return oidgroupDescription;
	}

	/**
	 * Method 'setOidgroupDescription'
	 * 
	 * @param oidgroupDescription
	 */
	public void setOidgroupDescription(String oidgroupDescription)
	{
		this.oidgroupDescription = oidgroupDescription;
	}

	/**
	 * Method 'getEveid'
	 * 
	 * @return long
	 */
	public long getEveid()
	{
		return eveid;
	}

	/**
	 * Method 'setEveid'
	 * 
	 * @param eveid
	 */
	public void setEveid(long eveid)
	{
		this.eveid = eveid;
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
	 * Method 'getOidgroupname'
	 * 
	 * @return String
	 */
	public String getOidgroupname()
	{
		return oidgroupname;
	}

	/**
	 * Method 'setOidgroupname'
	 * 
	 * @param oidgroupname
	 */
	public void setOidgroupname(String oidgroupname)
	{
		this.oidgroupname = oidgroupname;
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
	 * Method 'getOid'
	 * 
	 * @return String
	 */
	public String getOid()
	{
		return oid;
	}

	/**
	 * Method 'setOid'
	 * 
	 * @param oid
	 */
	public void setOid(String oid)
	{
		this.oid = oid;
	}

	/**
	 * Method 'getUnit'
	 * 
	 * @return String
	 */
	public String getUnit()
	{
		return unit;
	}

	/**
	 * Method 'setUnit'
	 * 
	 * @param unit
	 */
	public void setUnit(String unit)
	{
		this.unit = unit;
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
	 * Method 'getDevtypeMrid'
	 * 
	 * @return long
	 */
	public long getDevtypeMrid()
	{
		return devtypeMrid;
	}

	/**
	 * Method 'setDevtypeMrid'
	 * 
	 * @param devtypeMrid
	 */
	public void setDevtypeMrid(long devtypeMrid)
	{
		this.devtypeMrid = devtypeMrid;
	}

	/**
	 * Method 'getDevtypeDtid'
	 * 
	 * @return long
	 */
	public long getDevtypeDtid()
	{
		return devtypeDtid;
	}

	/**
	 * Method 'setDevtypeDtid'
	 * 
	 * @param devtypeDtid
	 */
	public void setDevtypeDtid(long devtypeDtid)
	{
		this.devtypeDtid = devtypeDtid;
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
	 * Method 'getDevtypeDescription'
	 * 
	 * @return String
	 */
	public String getDevtypeDescription()
	{
		return devtypeDescription;
	}

	/**
	 * Method 'setDevtypeDescription'
	 * 
	 * @param devtypeDescription
	 */
	public void setDevtypeDescription(String devtypeDescription)
	{
		this.devtypeDescription = devtypeDescription;
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
		
		if (!(_other instanceof VPerformParam)) {
			return false;
		}
		
		final VPerformParam _cast = (VPerformParam) _other;
		if (eventtypeModid != _cast.eventtypeModid) {
			return false;
		}
		
		if (eventtypeEveid != _cast.eventtypeEveid) {
			return false;
		}
		
		if (etid != _cast.etid) {
			return false;
		}
		
		if (etidNull != _cast.etidNull) {
			return false;
		}
		
		if (estid != _cast.estid) {
			return false;
		}
		
		if (estidNull != _cast.estidNull) {
			return false;
		}
		
		if (eveothername == null ? _cast.eveothername != eveothername : !eveothername.equals( _cast.eveothername )) {
			return false;
		}
		
		if (ecode != _cast.ecode) {
			return false;
		}
		
		if (general != _cast.general) {
			return false;
		}
		
		if (generalNull != _cast.generalNull) {
			return false;
		}
		
		if (major == null ? _cast.major != major : !major.equals( _cast.major )) {
			return false;
		}
		
		if (minor == null ? _cast.minor != minor : !minor.equals( _cast.minor )) {
			return false;
		}
		
		if (other == null ? _cast.other != other : !other.equals( _cast.other )) {
			return false;
		}
		
		if (eventtypeDescription == null ? _cast.eventtypeDescription != eventtypeDescription : !eventtypeDescription.equals( _cast.eventtypeDescription )) {
			return false;
		}
		
		if (useflag == null ? _cast.useflag != useflag : !useflag.equals( _cast.useflag )) {
			return false;
		}
		
		if (oidgroupOpid != _cast.oidgroupOpid) {
			return false;
		}
		
		if (oidgroupOidgroupname == null ? _cast.oidgroupOidgroupname != oidgroupOidgroupname : !oidgroupOidgroupname.equals( _cast.oidgroupOidgroupname )) {
			return false;
		}
		
		if (otype != _cast.otype) {
			return false;
		}
		
		if (oidgroupDescription == null ? _cast.oidgroupDescription != oidgroupDescription : !oidgroupDescription.equals( _cast.oidgroupDescription )) {
			return false;
		}
		
		if (eveid != _cast.eveid) {
			return false;
		}
		
		if (mrid != _cast.mrid) {
			return false;
		}
		
		if (dtid != _cast.dtid) {
			return false;
		}
		
		if (oidgroupname == null ? _cast.oidgroupname != oidgroupname : !oidgroupname.equals( _cast.oidgroupname )) {
			return false;
		}
		
		if (modid != _cast.modid) {
			return false;
		}
		
		if (oid == null ? _cast.oid != oid : !oid.equals( _cast.oid )) {
			return false;
		}
		
		if (unit == null ? _cast.unit != unit : !unit.equals( _cast.unit )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		if (devtypeMrid != _cast.devtypeMrid) {
			return false;
		}
		
		if (devtypeDtid != _cast.devtypeDtid) {
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
		
		if (devtypeDescription == null ? _cast.devtypeDescription != devtypeDescription : !devtypeDescription.equals( _cast.devtypeDescription )) {
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
		_hashCode = 29 * _hashCode + (int) (eventtypeModid ^ (eventtypeModid >>> 32));
		_hashCode = 29 * _hashCode + (int) (eventtypeEveid ^ (eventtypeEveid >>> 32));
		_hashCode = 29 * _hashCode + (int) (etid ^ (etid >>> 32));
		_hashCode = 29 * _hashCode + (etidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (estid ^ (estid >>> 32));
		_hashCode = 29 * _hashCode + (estidNull ? 1 : 0);
		if (eveothername != null) {
			_hashCode = 29 * _hashCode + eveothername.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (ecode ^ (ecode >>> 32));
		_hashCode = 29 * _hashCode + (int) (general ^ (general >>> 32));
		_hashCode = 29 * _hashCode + (generalNull ? 1 : 0);
		if (major != null) {
			_hashCode = 29 * _hashCode + major.hashCode();
		}
		
		if (minor != null) {
			_hashCode = 29 * _hashCode + minor.hashCode();
		}
		
		if (other != null) {
			_hashCode = 29 * _hashCode + other.hashCode();
		}
		
		if (eventtypeDescription != null) {
			_hashCode = 29 * _hashCode + eventtypeDescription.hashCode();
		}
		
		if (useflag != null) {
			_hashCode = 29 * _hashCode + useflag.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (oidgroupOpid ^ (oidgroupOpid >>> 32));
		if (oidgroupOidgroupname != null) {
			_hashCode = 29 * _hashCode + oidgroupOidgroupname.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (otype ^ (otype >>> 32));
		if (oidgroupDescription != null) {
			_hashCode = 29 * _hashCode + oidgroupDescription.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (eveid ^ (eveid >>> 32));
		_hashCode = 29 * _hashCode + (int) (mrid ^ (mrid >>> 32));
		_hashCode = 29 * _hashCode + (int) (dtid ^ (dtid >>> 32));
		if (oidgroupname != null) {
			_hashCode = 29 * _hashCode + oidgroupname.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (modid ^ (modid >>> 32));
		if (oid != null) {
			_hashCode = 29 * _hashCode + oid.hashCode();
		}
		
		if (unit != null) {
			_hashCode = 29 * _hashCode + unit.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (devtypeMrid ^ (devtypeMrid >>> 32));
		_hashCode = 29 * _hashCode + (int) (devtypeDtid ^ (devtypeDtid >>> 32));
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
		
		if (devtypeDescription != null) {
			_hashCode = 29 * _hashCode + devtypeDescription.hashCode();
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
		ret.append( "com.ibm.ncs.model.dto.VPerformParam: " );
		ret.append( "eventtypeModid=" + eventtypeModid );
		ret.append( ", eventtypeEveid=" + eventtypeEveid );
		ret.append( ", etid=" + etid );
		ret.append( ", estid=" + estid );
		ret.append( ", eveothername=" + eveothername );
		ret.append( ", ecode=" + ecode );
		ret.append( ", general=" + general );
		ret.append( ", major=" + major );
		ret.append( ", minor=" + minor );
		ret.append( ", other=" + other );
		ret.append( ", eventtypeDescription=" + eventtypeDescription );
		ret.append( ", useflag=" + useflag );
		ret.append( ", oidgroupOpid=" + oidgroupOpid );
		ret.append( ", oidgroupOidgroupname=" + oidgroupOidgroupname );
		ret.append( ", otype=" + otype );
		ret.append( ", oidgroupDescription=" + oidgroupDescription );
		ret.append( ", eveid=" + eveid );
		ret.append( ", mrid=" + mrid );
		ret.append( ", dtid=" + dtid );
		ret.append( ", oidgroupname=" + oidgroupname );
		ret.append( ", modid=" + modid );
		ret.append( ", oid=" + oid );
		ret.append( ", unit=" + unit );
		ret.append( ", description=" + description );
		ret.append( ", devtypeMrid=" + devtypeMrid );
		ret.append( ", devtypeDtid=" + devtypeDtid );
		ret.append( ", category=" + category );
		ret.append( ", subcategory=" + subcategory );
		ret.append( ", model=" + model );
		ret.append( ", objectid=" + objectid );
		ret.append( ", logo=" + logo );
		ret.append( ", devtypeDescription=" + devtypeDescription );
		return ret.toString();
	}

}
