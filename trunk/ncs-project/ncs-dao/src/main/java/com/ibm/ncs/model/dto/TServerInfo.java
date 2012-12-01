package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TServerInfo implements Serializable
{
	/** 
	 * This attribute maps to the column NMSID in the T_SERVER_INFO table.
	 */
	protected long nmsid;

	/** 
	 * This attribute maps to the column NMSIP in the T_SERVER_INFO table.
	 */
	protected String nmsip;

	/** 
	 * This attribute maps to the column NMSNAME in the T_SERVER_INFO table.
	 */
	protected String nmsname;

	/** 
	 * This attribute maps to the column USERNAME in the T_SERVER_INFO table.
	 */
	protected String username;

	/** 
	 * This attribute maps to the column PASSWORD in the T_SERVER_INFO table.
	 */
	protected String password;

	/** 
	 * This attribute maps to the column OSTYPE in the T_SERVER_INFO table.
	 */
	protected String ostype;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_SERVER_INFO table.
	 */
	protected String description;

	/**
	 * Method 'TServerInfo'
	 * 
	 */
	public TServerInfo()
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
	 * Method 'getNmsip'
	 * 
	 * @return String
	 */
	public String getNmsip()
	{
		return nmsip;
	}

	/**
	 * Method 'setNmsip'
	 * 
	 * @param nmsip
	 */
	public void setNmsip(String nmsip)
	{
		this.nmsip = nmsip;
	}

	/**
	 * Method 'getNmsname'
	 * 
	 * @return String
	 */
	public String getNmsname()
	{
		return nmsname;
	}

	/**
	 * Method 'setNmsname'
	 * 
	 * @param nmsname
	 */
	public void setNmsname(String nmsname)
	{
		this.nmsname = nmsname;
	}

	/**
	 * Method 'getUsername'
	 * 
	 * @return String
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Method 'setUsername'
	 * 
	 * @param username
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * Method 'getPassword'
	 * 
	 * @return String
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Method 'setPassword'
	 * 
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Method 'getOstype'
	 * 
	 * @return String
	 */
	public String getOstype()
	{
		return ostype;
	}

	/**
	 * Method 'setOstype'
	 * 
	 * @param ostype
	 */
	public void setOstype(String ostype)
	{
		this.ostype = ostype;
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
		
		if (!(_other instanceof TServerInfo)) {
			return false;
		}
		
		final TServerInfo _cast = (TServerInfo) _other;
		if (nmsid != _cast.nmsid) {
			return false;
		}
		
		if (nmsip == null ? _cast.nmsip != nmsip : !nmsip.equals( _cast.nmsip )) {
			return false;
		}
		
		if (nmsname == null ? _cast.nmsname != nmsname : !nmsname.equals( _cast.nmsname )) {
			return false;
		}
		
		if (username == null ? _cast.username != username : !username.equals( _cast.username )) {
			return false;
		}
		
		if (password == null ? _cast.password != password : !password.equals( _cast.password )) {
			return false;
		}
		
		if (ostype == null ? _cast.ostype != ostype : !ostype.equals( _cast.ostype )) {
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
		if (nmsip != null) {
			_hashCode = 29 * _hashCode + nmsip.hashCode();
		}
		
		if (nmsname != null) {
			_hashCode = 29 * _hashCode + nmsname.hashCode();
		}
		
		if (username != null) {
			_hashCode = 29 * _hashCode + username.hashCode();
		}
		
		if (password != null) {
			_hashCode = 29 * _hashCode + password.hashCode();
		}
		
		if (ostype != null) {
			_hashCode = 29 * _hashCode + ostype.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TServerInfoPk
	 */
	public TServerInfoPk createPk()
	{
		return new TServerInfoPk(nmsid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TServerInfo: " );
		ret.append( "nmsid=" + nmsid );
		ret.append( ", nmsip=" + nmsip );
		ret.append( ", nmsname=" + nmsname );
		ret.append( ", username=" + username );
		ret.append( ", password=" + password );
		ret.append( ", ostype=" + ostype );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
