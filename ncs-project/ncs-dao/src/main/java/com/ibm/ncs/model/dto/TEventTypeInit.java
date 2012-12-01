package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TEventTypeInit implements Serializable
{
	/** 
	 * This attribute maps to the column ETID in the T_EVENT_TYPE_INIT table.
	 */
	protected long etid;

	/** 
	 * This attribute represents whether the primitive attribute etid is null.
	 */
	protected boolean etidNull = true;

	/** 
	 * This attribute maps to the column MODID in the T_EVENT_TYPE_INIT table.
	 */
	protected long modid;

	/** 
	 * This attribute maps to the column EVEID in the T_EVENT_TYPE_INIT table.
	 */
	protected long eveid;

	/** 
	 * This attribute maps to the column ESTID in the T_EVENT_TYPE_INIT table.
	 */
	protected long estid;

	/** 
	 * This attribute represents whether the primitive attribute estid is null.
	 */
	protected boolean estidNull = true;

	/** 
	 * This attribute maps to the column EVEOTHERNAME in the T_EVENT_TYPE_INIT table.
	 */
	protected String eveothername;

	/** 
	 * This attribute maps to the column ECODE in the T_EVENT_TYPE_INIT table.
	 */
	protected long ecode;

	/** 
	 * This attribute maps to the column GENERAL in the T_EVENT_TYPE_INIT table.
	 */
	protected long general;

	/** 
	 * This attribute represents whether the primitive attribute general is null.
	 */
	protected boolean generalNull = true;

	/** 
	 * This attribute maps to the column MAJOR in the T_EVENT_TYPE_INIT table.
	 */
	protected String major;

	/** 
	 * This attribute maps to the column MINOR in the T_EVENT_TYPE_INIT table.
	 */
	protected String minor;

	/** 
	 * This attribute maps to the column OTHER in the T_EVENT_TYPE_INIT table.
	 */
	protected String other;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_EVENT_TYPE_INIT table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column USEFLAG in the T_EVENT_TYPE_INIT table.
	 */
	protected String useflag;

	/**
	 * Method 'TEventTypeInit'
	 * 
	 */
	public TEventTypeInit()
	{
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
		
		if (!(_other instanceof TEventTypeInit)) {
			return false;
		}
		
		final TEventTypeInit _cast = (TEventTypeInit) _other;
		if (etid != _cast.etid) {
			return false;
		}
		
		if (etidNull != _cast.etidNull) {
			return false;
		}
		
		if (modid != _cast.modid) {
			return false;
		}
		
		if (eveid != _cast.eveid) {
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
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		if (useflag == null ? _cast.useflag != useflag : !useflag.equals( _cast.useflag )) {
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
		_hashCode = 29 * _hashCode + (int) (etid ^ (etid >>> 32));
		_hashCode = 29 * _hashCode + (etidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (modid ^ (modid >>> 32));
		_hashCode = 29 * _hashCode + (int) (eveid ^ (eveid >>> 32));
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
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		if (useflag != null) {
			_hashCode = 29 * _hashCode + useflag.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TEventTypeInitPk
	 */
	public TEventTypeInitPk createPk()
	{
		return new TEventTypeInitPk(modid, eveid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TEventTypeInit: " );
		ret.append( "etid=" + etid );
		ret.append( ", modid=" + modid );
		ret.append( ", eveid=" + eveid );
		ret.append( ", estid=" + estid );
		ret.append( ", eveothername=" + eveothername );
		ret.append( ", ecode=" + ecode );
		ret.append( ", general=" + general );
		ret.append( ", major=" + major );
		ret.append( ", minor=" + minor );
		ret.append( ", other=" + other );
		ret.append( ", description=" + description );
		ret.append( ", useflag=" + useflag );
		return ret.toString();
	}

}
