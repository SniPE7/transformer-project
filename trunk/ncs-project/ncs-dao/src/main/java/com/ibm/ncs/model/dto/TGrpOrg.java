package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TGrpOrg implements Serializable
{
	/** 
	 * This attribute maps to the column GID in the T_GRP_ORG table.
	 */
	protected long gid;

	/** 
	 * This attribute maps to the column GNAME in the T_GRP_ORG table.
	 */
	protected String gname;

	/** 
	 * This attribute maps to the column ORGABBR in the T_GRP_ORG table.
	 */
	protected String orgabbr;

	/** 
	 * This attribute maps to the column SUPID in the T_GRP_ORG table.
	 */
	protected long supid;

	/** 
	 * This attribute maps to the column LEVELS in the T_GRP_ORG table.
	 */
	protected long levels;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_GRP_ORG table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column UNMALLOCEDIPSETFLAG in the T_GRP_ORG table.
	 */
	protected String unmallocedipsetflag;

	/** 
	 * This attribute maps to the column ORGSPELL in the T_GRP_ORG table.
	 */
	protected String orgspell;

	/**
	 * Method 'TGrpOrg'
	 * 
	 */
	public TGrpOrg()
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
	 * Method 'getOrgabbr'
	 * 
	 * @return String
	 */
	public String getOrgabbr()
	{
		return orgabbr;
	}

	/**
	 * Method 'setOrgabbr'
	 * 
	 * @param orgabbr
	 */
	public void setOrgabbr(String orgabbr)
	{
		this.orgabbr = orgabbr;
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
	 * Method 'getOrgspell'
	 * 
	 * @return String
	 */
	public String getOrgspell()
	{
		return orgspell;
	}

	/**
	 * Method 'setOrgspell'
	 * 
	 * @param orgspell
	 */
	public void setOrgspell(String orgspell)
	{
		this.orgspell = orgspell;
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
		
		if (!(_other instanceof TGrpOrg)) {
			return false;
		}
		
		final TGrpOrg _cast = (TGrpOrg) _other;
		if (gid != _cast.gid) {
			return false;
		}
		
		if (gname == null ? _cast.gname != gname : !gname.equals( _cast.gname )) {
			return false;
		}
		
		if (orgabbr == null ? _cast.orgabbr != orgabbr : !orgabbr.equals( _cast.orgabbr )) {
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
		
		if (orgspell == null ? _cast.orgspell != orgspell : !orgspell.equals( _cast.orgspell )) {
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
		
		if (orgabbr != null) {
			_hashCode = 29 * _hashCode + orgabbr.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (supid ^ (supid >>> 32));
		_hashCode = 29 * _hashCode + (int) (levels ^ (levels >>> 32));
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		if (unmallocedipsetflag != null) {
			_hashCode = 29 * _hashCode + unmallocedipsetflag.hashCode();
		}
		
		if (orgspell != null) {
			_hashCode = 29 * _hashCode + orgspell.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TGrpOrgPk
	 */
	public TGrpOrgPk createPk()
	{
		return new TGrpOrgPk(gid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TGrpOrg: " );
		ret.append( "gid=" + gid );
		ret.append( ", gname=" + gname );
		ret.append( ", orgabbr=" + orgabbr );
		ret.append( ", supid=" + supid );
		ret.append( ", levels=" + levels );
		ret.append( ", description=" + description );
		ret.append( ", unmallocedipsetflag=" + unmallocedipsetflag );
		ret.append( ", orgspell=" + orgspell );
		return ret.toString();
	}

}
