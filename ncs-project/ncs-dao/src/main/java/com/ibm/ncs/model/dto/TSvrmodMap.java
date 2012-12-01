package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TSvrmodMap implements Serializable
{
	/** 
	 * This attribute maps to the column NMSID in the T_SVRMOD_MAP table.
	 */
	protected long nmsid;

	/** 
	 * This attribute maps to the column MODID in the T_SVRMOD_MAP table.
	 */
	protected long modid;

	/** 
	 * This attribute maps to the column PATH in the T_SVRMOD_MAP table.
	 */
	protected String path;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_SVRMOD_MAP table.
	 */
	protected String description;

	/**
	 * Method 'TSvrmodMap'
	 * 
	 */
	public TSvrmodMap()
	{
	}

	/**
	 * Method 'getNmsid'
	 * 
	 * @return long
	 */
	public long getNmsid()
	{
		return nmsid;
	}

	/**
	 * Method 'setNmsid'
	 * 
	 * @param nmsid
	 */
	public void setNmsid(long nmsid)
	{
		this.nmsid = nmsid;
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
	 * Method 'getPath'
	 * 
	 * @return String
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * Method 'setPath'
	 * 
	 * @param path
	 */
	public void setPath(String path)
	{
		this.path = path;
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
		
		if (!(_other instanceof TSvrmodMap)) {
			return false;
		}
		
		final TSvrmodMap _cast = (TSvrmodMap) _other;
		if (nmsid != _cast.nmsid) {
			return false;
		}
		
		if (modid != _cast.modid) {
			return false;
		}
		
		if (path == null ? _cast.path != path : !path.equals( _cast.path )) {
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
		_hashCode = 29 * _hashCode + (int) (nmsid ^ (nmsid >>> 32));
		_hashCode = 29 * _hashCode + (int) (modid ^ (modid >>> 32));
		if (path != null) {
			_hashCode = 29 * _hashCode + path.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TSvrmodMapPk
	 */
	public TSvrmodMapPk createPk()
	{
		return new TSvrmodMapPk(nmsid, modid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TSvrmodMap: " );
		ret.append( "nmsid=" + nmsid );
		ret.append( ", modid=" + modid );
		ret.append( ", path=" + path );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
