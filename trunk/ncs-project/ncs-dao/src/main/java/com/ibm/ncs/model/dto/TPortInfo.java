package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TPortInfo implements Serializable
{
	/** 
	 * This attribute maps to the column PTID in the T_PORT_INFO table.
	 */
	protected long ptid;

	/** 
	 * This attribute maps to the column DEVID in the T_PORT_INFO table.
	 */
	protected long devid;

	/** 
	 * This attribute maps to the column IFINDEX in the T_PORT_INFO table.
	 */
	protected long ifindex;

	/** 
	 * This attribute represents whether the primitive attribute ifindex is null.
	 */
	protected boolean ifindexNull = true;

	/** 
	 * This attribute maps to the column IFIP in the T_PORT_INFO table.
	 */
	protected String ifip;

	/** 
	 * This attribute maps to the column IPDECODE_IFIP in the T_PORT_INFO table.
	 */
	protected long ipdecodeIfip;

	/** 
	 * This attribute represents whether the primitive attribute ipdecodeIfip is null.
	 */
	protected boolean ipdecodeIfipNull = true;

	/** 
	 * This attribute maps to the column IFMAC in the T_PORT_INFO table.
	 */
	protected String ifmac;

	/** 
	 * This attribute maps to the column IFOPERSTATUS in the T_PORT_INFO table.
	 */
	protected String ifoperstatus;

	/** 
	 * This attribute maps to the column IFDESCR in the T_PORT_INFO table.
	 */
	protected String ifdescr;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_PORT_INFO table.
	 */
	protected String description;

	/**
	 * Method 'TPortInfo'
	 * 
	 */
	public TPortInfo()
	{
	}

	/**
	 * Method 'getPtid'
	 * 
	 * @return long
	 */
	public long getPtid()
	{
		return ptid;
	}

	/**
	 * Method 'setPtid'
	 * 
	 * @param ptid
	 */
	public void setPtid(long ptid)
	{
		this.ptid = ptid;
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
	}

	/**
	 * Method 'getIfindex'
	 * 
	 * @return long
	 */
	public long getIfindex()
	{
		return ifindex;
	}

	/**
	 * Method 'setIfindex'
	 * 
	 * @param ifindex
	 */
	public void setIfindex(long ifindex)
	{
		this.ifindex = ifindex;
		this.ifindexNull = false;
	}

	/**
	 * Method 'setIfindexNull'
	 * 
	 * @param value
	 */
	public void setIfindexNull(boolean value)
	{
		this.ifindexNull = value;
	}

	/**
	 * Method 'isIfindexNull'
	 * 
	 * @return boolean
	 */
	public boolean isIfindexNull()
	{
		return ifindexNull;
	}

	/**
	 * Method 'getIfip'
	 * 
	 * @return String
	 */
	public String getIfip()
	{
		return ifip;
	}

	/**
	 * Method 'setIfip'
	 * 
	 * @param ifip
	 */
	public void setIfip(String ifip)
	{
		this.ifip = ifip;
	}

	/**
	 * Method 'getIpdecodeIfip'
	 * 
	 * @return long
	 */
	public long getIpdecodeIfip()
	{
		return ipdecodeIfip;
	}

	/**
	 * Method 'setIpdecodeIfip'
	 * 
	 * @param ipdecodeIfip
	 */
	public void setIpdecodeIfip(long ipdecodeIfip)
	{
		this.ipdecodeIfip = ipdecodeIfip;
		this.ipdecodeIfipNull = false;
	}

	/**
	 * Method 'setIpdecodeIfipNull'
	 * 
	 * @param value
	 */
	public void setIpdecodeIfipNull(boolean value)
	{
		this.ipdecodeIfipNull = value;
	}

	/**
	 * Method 'isIpdecodeIfipNull'
	 * 
	 * @return boolean
	 */
	public boolean isIpdecodeIfipNull()
	{
		return ipdecodeIfipNull;
	}

	/**
	 * Method 'getIfmac'
	 * 
	 * @return String
	 */
	public String getIfmac()
	{
		return ifmac;
	}

	/**
	 * Method 'setIfmac'
	 * 
	 * @param ifmac
	 */
	public void setIfmac(String ifmac)
	{
		this.ifmac = ifmac;
	}

	/**
	 * Method 'getIfoperstatus'
	 * 
	 * @return String
	 */
	public String getIfoperstatus()
	{
		return ifoperstatus;
	}

	/**
	 * Method 'setIfoperstatus'
	 * 
	 * @param ifoperstatus
	 */
	public void setIfoperstatus(String ifoperstatus)
	{
		this.ifoperstatus = ifoperstatus;
	}

	/**
	 * Method 'getIfdescr'
	 * 
	 * @return String
	 */
	public String getIfdescr()
	{
		return ifdescr;
	}

	/**
	 * Method 'setIfdescr'
	 * 
	 * @param ifdescr
	 */
	public void setIfdescr(String ifdescr)
	{
		this.ifdescr = ifdescr;
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
		
		if (!(_other instanceof TPortInfo)) {
			return false;
		}
		
		final TPortInfo _cast = (TPortInfo) _other;
		if (ptid != _cast.ptid) {
			return false;
		}
		
		if (devid != _cast.devid) {
			return false;
		}
		
		if (ifindex != _cast.ifindex) {
			return false;
		}
		
		if (ifindexNull != _cast.ifindexNull) {
			return false;
		}
		
		if (ifip == null ? _cast.ifip != ifip : !ifip.equals( _cast.ifip )) {
			return false;
		}
		
		if (ipdecodeIfip != _cast.ipdecodeIfip) {
			return false;
		}
		
		if (ipdecodeIfipNull != _cast.ipdecodeIfipNull) {
			return false;
		}
		
		if (ifmac == null ? _cast.ifmac != ifmac : !ifmac.equals( _cast.ifmac )) {
			return false;
		}
		
		if (ifoperstatus == null ? _cast.ifoperstatus != ifoperstatus : !ifoperstatus.equals( _cast.ifoperstatus )) {
			return false;
		}
		
		if (ifdescr == null ? _cast.ifdescr != ifdescr : !ifdescr.equals( _cast.ifdescr )) {
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
		_hashCode = 29 * _hashCode + (int) (ptid ^ (ptid >>> 32));
		_hashCode = 29 * _hashCode + (int) (devid ^ (devid >>> 32));
		_hashCode = 29 * _hashCode + (int) (ifindex ^ (ifindex >>> 32));
		_hashCode = 29 * _hashCode + (ifindexNull ? 1 : 0);
		if (ifip != null) {
			_hashCode = 29 * _hashCode + ifip.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (ipdecodeIfip ^ (ipdecodeIfip >>> 32));
		_hashCode = 29 * _hashCode + (ipdecodeIfipNull ? 1 : 0);
		if (ifmac != null) {
			_hashCode = 29 * _hashCode + ifmac.hashCode();
		}
		
		if (ifoperstatus != null) {
			_hashCode = 29 * _hashCode + ifoperstatus.hashCode();
		}
		
		if (ifdescr != null) {
			_hashCode = 29 * _hashCode + ifdescr.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TPortInfoPk
	 */
	public TPortInfoPk createPk()
	{
		return new TPortInfoPk(ptid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TPortInfo: " );
		ret.append( "ptid=" + ptid );
		ret.append( ", devid=" + devid );
		ret.append( ", ifindex=" + ifindex );
		ret.append( ", ifip=" + ifip );
		ret.append( ", ipdecodeIfip=" + ipdecodeIfip );
		ret.append( ", ifmac=" + ifmac );
		ret.append( ", ifoperstatus=" + ifoperstatus );
		ret.append( ", ifdescr=" + ifdescr );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
