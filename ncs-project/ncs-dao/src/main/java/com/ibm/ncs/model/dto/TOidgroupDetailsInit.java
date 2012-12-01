package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TOidgroupDetailsInit implements Serializable
{
	/** 
	 * This attribute maps to the column OPID in the T_OIDGROUP_DETAILS_INIT table.
	 */
	protected long opid;

	/** 
	 * This attribute maps to the column OIDVALUE in the T_OIDGROUP_DETAILS_INIT table.
	 */
	protected String oidvalue;

	/** 
	 * This attribute maps to the column OIDNAME in the T_OIDGROUP_DETAILS_INIT table.
	 */
	protected String oidname;

	/** 
	 * This attribute maps to the column OIDUNIT in the T_OIDGROUP_DETAILS_INIT table.
	 */
	protected String oidunit;

	/** 
	 * This attribute maps to the column FLAG in the T_OIDGROUP_DETAILS_INIT table.
	 */
	protected String flag;

	/** 
	 * This attribute maps to the column OIDINDEX in the T_OIDGROUP_DETAILS_INIT table.
	 */
	protected long oidindex;

	/** 
	 * This attribute represents whether the primitive attribute oidindex is null.
	 */
	protected boolean oidindexNull = true;

	/**
	 * Method 'TOidgroupDetailsInit'
	 * 
	 */
	public TOidgroupDetailsInit()
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
	 * Method 'getFlag'
	 * 
	 * @return String
	 */
	public String getFlag()
	{
		return flag;
	}

	/**
	 * Method 'setFlag'
	 * 
	 * @param flag
	 */
	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	/**
	 * Method 'getOidindex'
	 * 
	 * @return long
	 */
	public long getOidindex()
	{
		return oidindex;
	}

	/**
	 * Method 'setOidindex'
	 * 
	 * @param oidindex
	 */
	public void setOidindex(long oidindex)
	{
		this.oidindex = oidindex;
		this.oidindexNull = false;
	}

	/**
	 * Method 'setOidindexNull'
	 * 
	 * @param value
	 */
	public void setOidindexNull(boolean value)
	{
		this.oidindexNull = value;
	}

	/**
	 * Method 'isOidindexNull'
	 * 
	 * @return boolean
	 */
	public boolean isOidindexNull()
	{
		return oidindexNull;
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
		
		if (!(_other instanceof TOidgroupDetailsInit)) {
			return false;
		}
		
		final TOidgroupDetailsInit _cast = (TOidgroupDetailsInit) _other;
		if (opid != _cast.opid) {
			return false;
		}
		
		if (oidvalue == null ? _cast.oidvalue != oidvalue : !oidvalue.equals( _cast.oidvalue )) {
			return false;
		}
		
		if (oidname == null ? _cast.oidname != oidname : !oidname.equals( _cast.oidname )) {
			return false;
		}
		
		if (oidunit == null ? _cast.oidunit != oidunit : !oidunit.equals( _cast.oidunit )) {
			return false;
		}
		
		if (flag == null ? _cast.flag != flag : !flag.equals( _cast.flag )) {
			return false;
		}
		
		if (oidindex != _cast.oidindex) {
			return false;
		}
		
		if (oidindexNull != _cast.oidindexNull) {
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
		if (oidvalue != null) {
			_hashCode = 29 * _hashCode + oidvalue.hashCode();
		}
		
		if (oidname != null) {
			_hashCode = 29 * _hashCode + oidname.hashCode();
		}
		
		if (oidunit != null) {
			_hashCode = 29 * _hashCode + oidunit.hashCode();
		}
		
		if (flag != null) {
			_hashCode = 29 * _hashCode + flag.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (oidindex ^ (oidindex >>> 32));
		_hashCode = 29 * _hashCode + (oidindexNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TOidgroupDetailsInitPk
	 */
	public TOidgroupDetailsInitPk createPk()
	{
		return new TOidgroupDetailsInitPk(opid, oidname);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TOidgroupDetailsInit: " );
		ret.append( "opid=" + opid );
		ret.append( ", oidvalue=" + oidvalue );
		ret.append( ", oidname=" + oidname );
		ret.append( ", oidunit=" + oidunit );
		ret.append( ", flag=" + flag );
		ret.append( ", oidindex=" + oidindex );
		return ret.toString();
	}

}
