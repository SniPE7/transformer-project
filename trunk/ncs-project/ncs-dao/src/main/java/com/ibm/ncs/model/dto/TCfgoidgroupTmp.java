package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TCfgoidgroupTmp implements Serializable
{
	/** 
	 * This attribute maps to the column S_NO in the T_CFGOIDGROUP_TMP table.
	 */
	protected Long sNo;

	/** 
	 * This attribute maps to the column OIDGROUPNAME in the T_CFGOIDGROUP_TMP table.
	 */
	protected String oidgroupname;

	/** 
	 * This attribute maps to the column OIDVALUE in the T_CFGOIDGROUP_TMP table.
	 */
	protected String oidvalue;

	/** 
	 * This attribute maps to the column OIDNAME in the T_CFGOIDGROUP_TMP table.
	 */
	protected String oidname;

	/** 
	 * This attribute maps to the column OIDINDEX in the T_CFGOIDGROUP_TMP table.
	 */
	protected Long oidindex;

	/** 
	 * This attribute maps to the column OIDUNIT in the T_CFGOIDGROUP_TMP table.
	 */
	protected String oidunit;

	/** 
	 * This attribute maps to the column NMSIP in the T_CFGOIDGROUP_TMP table.
	 */
	protected String nmsip;

	/** 
	 * This attribute maps to the column TARFILENAME in the T_CFGOIDGROUP_TMP table.
	 */
	protected String tarfilename;

	/**
	 * Method 'TCfgoidgroupTmp'
	 * 
	 */
	public TCfgoidgroupTmp()
	{
	}

	/**
	 * Method 'getSNo'
	 * 
	 * @return Long
	 */
	public Long getSNo()
	{
		return sNo;
	}

	/**
	 * Method 'setSNo'
	 * 
	 * @param sNo
	 */
	public void setSNo(Long sNo)
	{
		this.sNo = sNo;
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
	 * Method 'getOidvalue'
	 * 
	 * @return String
	 */
	public String getOidvalue()
	{
		return oidvalue;
	}

	/**
	 * Method 'setOidvalue'
	 * 
	 * @param oidvalue
	 */
	public void setOidvalue(String oidvalue)
	{
		this.oidvalue = oidvalue;
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
	 * Method 'getOidindex'
	 * 
	 * @return Long
	 */
	public Long getOidindex()
	{
		return oidindex;
	}

	/**
	 * Method 'setOidindex'
	 * 
	 * @param oidindex
	 */
	public void setOidindex(Long oidindex)
	{
		this.oidindex = oidindex;
	}

	/**
	 * Method 'getOidunit'
	 * 
	 * @return String
	 */
	public String getOidunit()
	{
		return oidunit;
	}

	/**
	 * Method 'setOidunit'
	 * 
	 * @param oidunit
	 */
	public void setOidunit(String oidunit)
	{
		this.oidunit = oidunit;
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
	 * Method 'getTarfilename'
	 * 
	 * @return String
	 */
	public String getTarfilename()
	{
		return tarfilename;
	}

	/**
	 * Method 'setTarfilename'
	 * 
	 * @param tarfilename
	 */
	public void setTarfilename(String tarfilename)
	{
		this.tarfilename = tarfilename;
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
		
		if (!(_other instanceof TCfgoidgroupTmp)) {
			return false;
		}
		
		final TCfgoidgroupTmp _cast = (TCfgoidgroupTmp) _other;
		if (sNo == null ? _cast.sNo != sNo : !sNo.equals( _cast.sNo )) {
			return false;
		}
		
		if (oidgroupname == null ? _cast.oidgroupname != oidgroupname : !oidgroupname.equals( _cast.oidgroupname )) {
			return false;
		}
		
		if (oidvalue == null ? _cast.oidvalue != oidvalue : !oidvalue.equals( _cast.oidvalue )) {
			return false;
		}
		
		if (oidname == null ? _cast.oidname != oidname : !oidname.equals( _cast.oidname )) {
			return false;
		}
		
		if (oidindex == null ? _cast.oidindex != oidindex : !oidindex.equals( _cast.oidindex )) {
			return false;
		}
		
		if (oidunit == null ? _cast.oidunit != oidunit : !oidunit.equals( _cast.oidunit )) {
			return false;
		}
		
		if (nmsip == null ? _cast.nmsip != nmsip : !nmsip.equals( _cast.nmsip )) {
			return false;
		}
		
		if (tarfilename == null ? _cast.tarfilename != tarfilename : !tarfilename.equals( _cast.tarfilename )) {
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
		if (sNo != null) {
			_hashCode = 29 * _hashCode + sNo.hashCode();
		}
		
		if (oidgroupname != null) {
			_hashCode = 29 * _hashCode + oidgroupname.hashCode();
		}
		
		if (oidvalue != null) {
			_hashCode = 29 * _hashCode + oidvalue.hashCode();
		}
		
		if (oidname != null) {
			_hashCode = 29 * _hashCode + oidname.hashCode();
		}
		
		if (oidindex != null) {
			_hashCode = 29 * _hashCode + oidindex.hashCode();
		}
		
		if (oidunit != null) {
			_hashCode = 29 * _hashCode + oidunit.hashCode();
		}
		
		if (nmsip != null) {
			_hashCode = 29 * _hashCode + nmsip.hashCode();
		}
		
		if (tarfilename != null) {
			_hashCode = 29 * _hashCode + tarfilename.hashCode();
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
		ret.append( "com.ibm.ncs.model.dto.TCfgoidgroupTmp: " );
		ret.append( "sNo=" + sNo );
		ret.append( ", oidgroupname=" + oidgroupname );
		ret.append( ", oidvalue=" + oidvalue );
		ret.append( ", oidname=" + oidname );
		ret.append( ", oidindex=" + oidindex );
		ret.append( ", oidunit=" + oidunit );
		ret.append( ", nmsip=" + nmsip );
		ret.append( ", tarfilename=" + tarfilename );
		return ret.toString();
	}

}
