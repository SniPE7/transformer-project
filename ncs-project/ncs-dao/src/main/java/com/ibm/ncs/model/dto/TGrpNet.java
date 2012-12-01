package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TGrpNet implements Serializable
{
	/** 
	 * This attribute maps to the column GID in the T_GRP_NET table.
	 */
	protected long gid;

	/** 
	 * This attribute maps to the column GNAME in the T_GRP_NET table.
	 */
	protected String gname;

	/** 
	 * This attribute maps to the column SUPID in the T_GRP_NET table.
	 */
	protected long supid;

	/** 
	 * This attribute maps to the column LEVELS in the T_GRP_NET table.
	 */
	protected long levels;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_GRP_NET table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column UNMALLOCEDIPSETFLAG in the T_GRP_NET table.
	 */
	protected String unmallocedipsetflag;

	/**
	 * Method 'TGrpNet'
	 * 
	 */
	public TGrpNet()
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
	 * Method 'getGname'
	 * 
	 * @return String
	 */
	public String getGname()
	{
		return gname;
	}

	/**
	 * Method 'setGname'
	 * 
	 * @param gname
	 */
	public void setGname(String gname)
	{
		this.gname = gname;
	}

	/**
	 * Method 'getSupid'
	 * 
	 * @return long
	 */
	public long getSupid()
	{
		return supid;
	}

	/**
	 * Method 'setSupid'
	 * 
	 * @param supid
	 */
	public void setSupid(long supid)
	{
		this.supid = supid;
	}

	/**
	 * Method 'getLevels'
	 * 
	 * @return long
	 */
	public long getLevels()
	{
		return levels;
	}

	/**
	 * Method 'setLevels'
	 * 
	 * @param levels
	 */
	public void setLevels(long levels)
	{
		this.levels = levels;
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
	 * Method 'getUnmallocedipsetflag'
	 * 
	 * @return String
	 */
	public String getUnmallocedipsetflag()
	{
		return unmallocedipsetflag;
	}

	/**
	 * Method 'setUnmallocedipsetflag'
	 * 
	 * @param unmallocedipsetflag
	 */
	public void setUnmallocedipsetflag(String unmallocedipsetflag)
	{
		this.unmallocedipsetflag = unmallocedipsetflag;
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
		
		if (!(_other instanceof TGrpNet)) {
			return false;
		}
		
		final TGrpNet _cast = (TGrpNet) _other;
		if (gid != _cast.gid) {
			return false;
		}
		
		if (gname == null ? _cast.gname != gname : !gname.equals( _cast.gname )) {
			return false;
		}
		
		if (supid != _cast.supid) {
			return false;
		}
		
		if (levels != _cast.levels) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		if (unmallocedipsetflag == null ? _cast.unmallocedipsetflag != unmallocedipsetflag : !unmallocedipsetflag.equals( _cast.unmallocedipsetflag )) {
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
		if (gname != null) {
			_hashCode = 29 * _hashCode + gname.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (supid ^ (supid >>> 32));
		_hashCode = 29 * _hashCode + (int) (levels ^ (levels >>> 32));
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		if (unmallocedipsetflag != null) {
			_hashCode = 29 * _hashCode + unmallocedipsetflag.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TGrpNetPk
	 */
	public TGrpNetPk createPk()
	{
		return new TGrpNetPk(gid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TGrpNet: " );
		ret.append( "gid=" + gid );
		ret.append( ", gname=" + gname );
		ret.append( ", supid=" + supid );
		ret.append( ", levels=" + levels );
		ret.append( ", description=" + description );
		ret.append( ", unmallocedipsetflag=" + unmallocedipsetflag );
		return ret.toString();
	}

}
