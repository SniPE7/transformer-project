package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TUser implements Serializable
{
	/** 
	 * This attribute maps to the column USID in the T_USER table.
	 */
	protected long usid;

	/** 
	 * This attribute maps to the column UNAME in the T_USER table.
	 */
	protected String uname;

	/** 
	 * This attribute maps to the column PASSWORD in the T_USER table.
	 */
	protected String password;

	/** 
	 * This attribute maps to the column STATUS in the T_USER table.
	 */
	protected String status;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_USER table.
	 */
	protected String description;

	/**
	 * Method 'TUser'
	 * 
	 */
	public TUser()
	{
	}

	/**
	 * Method 'getUsid'
	 * 
	 * @return long
	 */
	public long getUsid()
	{
		return usid;
	}

	/**
	 * Method 'setUsid'
	 * 
	 * @param usid
	 */
	public void setUsid(long usid)
	{
		this.usid = usid;
	}

	/**
	 * Method 'getUname'
	 * 
	 * @return String
	 */
	public String getUname()
	{
		return uname;
	}

	/**
	 * Method 'setUname'
	 * 
	 * @param uname
	 */
	public void setUname(String uname)
	{
		this.uname = uname;
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
	 * Method 'getStatus'
	 * 
	 * @return String
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(String status)
	{
		this.status = status;
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
		
		if (!(_other instanceof TUser)) {
			return false;
		}
		
		final TUser _cast = (TUser) _other;
		if (usid != _cast.usid) {
			return false;
		}
		
		if (uname == null ? _cast.uname != uname : !uname.equals( _cast.uname )) {
			return false;
		}
		
		if (password == null ? _cast.password != password : !password.equals( _cast.password )) {
			return false;
		}
		
		if (status == null ? _cast.status != status : !status.equals( _cast.status )) {
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
		_hashCode = 29 * _hashCode + (int) (usid ^ (usid >>> 32));
		if (uname != null) {
			_hashCode = 29 * _hashCode + uname.hashCode();
		}
		
		if (password != null) {
			_hashCode = 29 * _hashCode + password.hashCode();
		}
		
		if (status != null) {
			_hashCode = 29 * _hashCode + status.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TUserPk
	 */
	public TUserPk createPk()
	{
		return new TUserPk(usid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TUser: " );
		ret.append( "usid=" + usid );
		ret.append( ", uname=" + uname );
		ret.append( ", password=" + password );
		ret.append( ", status=" + status );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
