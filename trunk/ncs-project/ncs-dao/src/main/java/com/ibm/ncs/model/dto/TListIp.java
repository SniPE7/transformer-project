package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TListIp implements Serializable
{
	/** 
	 * This attribute maps to the column GID in the T_LIST_IP table.
	 */
	protected long gid;

	/** 
	 * This attribute maps to the column CATEGORY in the T_LIST_IP table.
	 */
	protected long category;

	/** 
	 * This attribute maps to the column IP in the T_LIST_IP table.
	 */
	protected String ip;

	/** 
	 * This attribute maps to the column MASK in the T_LIST_IP table.
	 */
	protected String mask;

	/** 
	 * This attribute maps to the column IPDECODE_MIN in the T_LIST_IP table.
	 */
	protected long ipdecodeMin;

	/** 
	 * This attribute maps to the column IPDECODE_MAX in the T_LIST_IP table.
	 */
	protected long ipdecodeMax;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_LIST_IP table.
	 */
	protected String description;

	/**
	 * Method 'TListIp'
	 * 
	 */
	public TListIp()
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
	}

	/**
	 * Method 'getIp'
	 * 
	 * @return String
	 */
	public String getIp()
	{
		return ip;
	}

	/**
	 * Method 'setIp'
	 * 
	 * @param ip
	 */
	public void setIp(String ip)
	{
		this.ip = ip;
	}

	/**
	 * Method 'getMask'
	 * 
	 * @return String
	 */
	public String getMask()
	{
		return mask;
	}

	/**
	 * Method 'setMask'
	 * 
	 * @param mask
	 */
	public void setMask(String mask)
	{
		this.mask = mask;
	}

	/**
	 * Method 'getIpdecodeMin'
	 * 
	 * @return long
	 */
	public long getIpdecodeMin()
	{
		return ipdecodeMin;
	}

	/**
	 * Method 'setIpdecodeMin'
	 * 
	 * @param ipdecodeMin
	 */
	public void setIpdecodeMin(long ipdecodeMin)
	{
		this.ipdecodeMin = ipdecodeMin;
	}

	/**
	 * Method 'getIpdecodeMax'
	 * 
	 * @return long
	 */
	public long getIpdecodeMax()
	{
		return ipdecodeMax;
	}

	/**
	 * Method 'setIpdecodeMax'
	 * 
	 * @param ipdecodeMax
	 */
	public void setIpdecodeMax(long ipdecodeMax)
	{
		this.ipdecodeMax = ipdecodeMax;
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
		
		if (!(_other instanceof TListIp)) {
			return false;
		}
		
		final TListIp _cast = (TListIp) _other;
		if (gid != _cast.gid) {
			return false;
		}
		
		if (category != _cast.category) {
			return false;
		}
		
		if (ip == null ? _cast.ip != ip : !ip.equals( _cast.ip )) {
			return false;
		}
		
		if (mask == null ? _cast.mask != mask : !mask.equals( _cast.mask )) {
			return false;
		}
		
		if (ipdecodeMin != _cast.ipdecodeMin) {
			return false;
		}
		
		if (ipdecodeMax != _cast.ipdecodeMax) {
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
		_hashCode = 29 * _hashCode + (int) (gid ^ (gid >>> 32));
		_hashCode = 29 * _hashCode + (int) (category ^ (category >>> 32));
		if (ip != null) {
			_hashCode = 29 * _hashCode + ip.hashCode();
		}
		
		if (mask != null) {
			_hashCode = 29 * _hashCode + mask.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (ipdecodeMin ^ (ipdecodeMin >>> 32));
		_hashCode = 29 * _hashCode + (int) (ipdecodeMax ^ (ipdecodeMax >>> 32));
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
		ret.append( "com.ibm.ncs.model.dto.TListIp: " );
		ret.append( "gid=" + gid );
		ret.append( ", category=" + category );
		ret.append( ", ip=" + ip );
		ret.append( ", mask=" + mask );
		ret.append( ", ipdecodeMin=" + ipdecodeMin );
		ret.append( ", ipdecodeMax=" + ipdecodeMax );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
