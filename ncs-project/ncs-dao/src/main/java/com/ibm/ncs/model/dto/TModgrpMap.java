package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TModgrpMap implements Serializable
{
	/** 
	 * This attribute maps to the column GID in the T_MODGRP_MAP table.
	 */
	protected long gid;

	/** 
	 * This attribute maps to the column NMSID in the T_MODGRP_MAP table.
	 */
	protected long nmsid;

	/** 
	 * This attribute maps to the column MODID in the T_MODGRP_MAP table.
	 */
	protected long modid;

	/**
	 * Method 'TModgrpMap'
	 * 
	 */
	public TModgrpMap()
	{
	}

	/**
	 * Method 'getGid'
	 * 
	 * @return long
	 */
	public long getGid()
	{
		return gid;
	}

	/**
	 * Method 'setGid'
	 * 
	 * @param gid
	 */
	public void setGid(long gid)
	{
		this.gid = gid;
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
		
		if (!(_other instanceof TModgrpMap)) {
			return false;
		}
		
		final TModgrpMap _cast = (TModgrpMap) _other;
		if (gid != _cast.gid) {
			return false;
		}
		
		if (nmsid != _cast.nmsid) {
			return false;
		}
		
		if (modid != _cast.modid) {
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
		_hashCode = 29 * _hashCode + (int) (nmsid ^ (nmsid >>> 32));
		_hashCode = 29 * _hashCode + (int) (modid ^ (modid >>> 32));
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TModgrpMapPk
	 */
	public TModgrpMapPk createPk()
	{
		return new TModgrpMapPk(gid, nmsid, modid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TModgrpMap: " );
		ret.append( "gid=" + gid );
		ret.append( ", nmsid=" + nmsid );
		ret.append( ", modid=" + modid );
		return ret.toString();
	}

}
