package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TOidgroupInfoInit implements Serializable
{
	/** 
	 * This attribute maps to the column OPID in the T_OIDGROUP_INFO_INIT table.
	 */
	protected long opid;

	/** 
	 * This attribute maps to the column OIDGROUPNAME in the T_OIDGROUP_INFO_INIT table.
	 */
	protected String oidgroupname;

	/** 
	 * This attribute maps to the column OTYPE in the T_OIDGROUP_INFO_INIT table.
	 */
	protected long otype;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_OIDGROUP_INFO_INIT table.
	 */
	protected String description;

	/**
	 * Method 'TOidgroupInfoInit'
	 * 
	 */
	public TOidgroupInfoInit()
	{
	}

	/**
	 * Method 'getOpid'
	 * 
	 * @return long
	 */
	public long getOpid()
	{
		return opid;
	}

	/**
	 * Method 'setOpid'
	 * 
	 * @param opid
	 */
	public void setOpid(long opid)
	{
		this.opid = opid;
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
		
		if (!(_other instanceof TOidgroupInfoInit)) {
			return false;
		}
		
		final TOidgroupInfoInit _cast = (TOidgroupInfoInit) _other;
		if (opid != _cast.opid) {
			return false;
		}
		
		if (oidgroupname == null ? _cast.oidgroupname != oidgroupname : !oidgroupname.equals( _cast.oidgroupname )) {
			return false;
		}
		
		if (otype != _cast.otype) {
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
		_hashCode = 29 * _hashCode + (int) (opid ^ (opid >>> 32));
		if (oidgroupname != null) {
			_hashCode = 29 * _hashCode + oidgroupname.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (otype ^ (otype >>> 32));
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TOidgroupInfoInitPk
	 */
	public TOidgroupInfoInitPk createPk()
	{
		return new TOidgroupInfoInitPk(opid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TOidgroupInfoInit: " );
		ret.append( "opid=" + opid );
		ret.append( ", oidgroupname=" + oidgroupname );
		ret.append( ", otype=" + otype );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
