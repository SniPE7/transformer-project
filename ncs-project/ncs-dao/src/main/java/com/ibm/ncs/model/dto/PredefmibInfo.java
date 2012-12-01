package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class PredefmibInfo implements Serializable
{
	/** 
	 * This attribute maps to the column PDMID in the PREDEFMIB_INFO table.
	 */
	protected long pdmid;

	/** 
	 * This attribute maps to the column MID in the PREDEFMIB_INFO table.
	 */
	protected long mid;

	/** 
	 * This attribute represents whether the primitive attribute mid is null.
	 */
	protected boolean midNull = true;

	/** 
	 * This attribute maps to the column DEVID in the PREDEFMIB_INFO table.
	 */
	protected long devid;

	/** 
	 * This attribute represents whether the primitive attribute devid is null.
	 */
	protected boolean devidNull = true;

	/** 
	 * This attribute maps to the column OIDINDEX in the PREDEFMIB_INFO table.
	 */
	protected String oidindex;

	/** 
	 * This attribute maps to the column OIDNAME in the PREDEFMIB_INFO table.
	 */
	protected String oidname;

	/**
	 * Method 'PredefmibInfo'
	 * 
	 */
	public PredefmibInfo()
	{
	}

	/**
	 * Method 'getPdmid'
	 * 
	 * @return long
	 */
	public long getPdmid()
	{
		return pdmid;
	}

	/**
	 * Method 'setPdmid'
	 * 
	 * @param pdmid
	 */
	public void setPdmid(long pdmid)
	{
		this.pdmid = pdmid;
	}

	/**
	 * Method 'getMid'
	 * 
	 * @return long
	 */
	public long getMid()
	{
		return mid;
	}

	/**
	 * Method 'setMid'
	 * 
	 * @param mid
	 */
	public void setMid(long mid)
	{
		this.mid = mid;
		this.midNull = false;
	}

	/**
	 * Method 'setMidNull'
	 * 
	 * @param value
	 */
	public void setMidNull(boolean value)
	{
		this.midNull = value;
	}

	/**
	 * Method 'isMidNull'
	 * 
	 * @return boolean
	 */
	public boolean isMidNull()
	{
		return midNull;
	}

	/**
	 * Method 'getDevid'
	 * 
	 * @return long
	 */
	public long getDevid()
	{
		return devid;
	}

	/**
	 * Method 'setDevid'
	 * 
	 * @param devid
	 */
	public void setDevid(long devid)
	{
		this.devid = devid;
		this.devidNull = false;
	}

	/**
	 * Method 'setDevidNull'
	 * 
	 * @param value
	 */
	public void setDevidNull(boolean value)
	{
		this.devidNull = value;
	}

	/**
	 * Method 'isDevidNull'
	 * 
	 * @return boolean
	 */
	public boolean isDevidNull()
	{
		return devidNull;
	}

	/**
	 * Method 'getOidindex'
	 * 
	 * @return String
	 */
	public String getOidindex()
	{
		return oidindex;
	}

	/**
	 * Method 'setOidindex'
	 * 
	 * @param oidindex
	 */
	public void setOidindex(String oidindex)
	{
		this.oidindex = oidindex;
	}

	/**
	 * Method 'getOidname'
	 * 
	 * @return String
	 */
	public String getOidname()
	{
		return oidname;
	}

	/**
	 * Method 'setOidname'
	 * 
	 * @param oidname
	 */
	public void setOidname(String oidname)
	{
		this.oidname = oidname;
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
		
		if (!(_other instanceof PredefmibInfo)) {
			return false;
		}
		
		final PredefmibInfo _cast = (PredefmibInfo) _other;
		if (pdmid != _cast.pdmid) {
			return false;
		}
		
		if (mid != _cast.mid) {
			return false;
		}
		
		if (midNull != _cast.midNull) {
			return false;
		}
		
		if (devid != _cast.devid) {
			return false;
		}
		
		if (devidNull != _cast.devidNull) {
			return false;
		}
		
		if (oidindex == null ? _cast.oidindex != oidindex : !oidindex.equals( _cast.oidindex )) {
			return false;
		}
		
		if (oidname == null ? _cast.oidname != oidname : !oidname.equals( _cast.oidname )) {
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
		_hashCode = 29 * _hashCode + (int) (pdmid ^ (pdmid >>> 32));
		_hashCode = 29 * _hashCode + (int) (mid ^ (mid >>> 32));
		_hashCode = 29 * _hashCode + (midNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (devid ^ (devid >>> 32));
		_hashCode = 29 * _hashCode + (devidNull ? 1 : 0);
		if (oidindex != null) {
			_hashCode = 29 * _hashCode + oidindex.hashCode();
		}
		
		if (oidname != null) {
			_hashCode = 29 * _hashCode + oidname.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return PredefmibInfoPk
	 */
	public PredefmibInfoPk createPk()
	{
		return new PredefmibInfoPk(pdmid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.PredefmibInfo: " );
		ret.append( "pdmid=" + pdmid );
		ret.append( ", mid=" + mid );
		ret.append( ", devid=" + devid );
		ret.append( ", oidindex=" + oidindex );
		ret.append( ", oidname=" + oidname );
		return ret.toString();
	}

}
