package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class DefMibGrp implements Serializable
{
	/** 
	 * This attribute maps to the column MID in the DEF_MIB_GRP table.
	 */
	protected long mid;

	/** 
	 * This attribute maps to the column NAME in the DEF_MIB_GRP table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column INDEXOID in the DEF_MIB_GRP table.
	 */
	protected String indexoid;

	/** 
	 * This attribute maps to the column INDEXVAR in the DEF_MIB_GRP table.
	 */
	protected String indexvar;

	/** 
	 * This attribute maps to the column DESCROID in the DEF_MIB_GRP table.
	 */
	protected String descroid;

	/** 
	 * This attribute maps to the column DESCRVAR in the DEF_MIB_GRP table.
	 */
	protected String descrvar;

	/**
	 * Method 'DefMibGrp'
	 * 
	 */
	public DefMibGrp()
	{
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
	}

	/**
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method 'getIndexoid'
	 * 
	 * @return String
	 */
	public String getIndexoid()
	{
		return indexoid;
	}

	/**
	 * Method 'setIndexoid'
	 * 
	 * @param indexoid
	 */
	public void setIndexoid(String indexoid)
	{
		this.indexoid = indexoid;
	}

	/**
	 * Method 'getIndexvar'
	 * 
	 * @return String
	 */
	public String getIndexvar()
	{
		return indexvar;
	}

	/**
	 * Method 'setIndexvar'
	 * 
	 * @param indexvar
	 */
	public void setIndexvar(String indexvar)
	{
		this.indexvar = indexvar;
	}

	/**
	 * Method 'getDescroid'
	 * 
	 * @return String
	 */
	public String getDescroid()
	{
		return descroid;
	}

	/**
	 * Method 'setDescroid'
	 * 
	 * @param descroid
	 */
	public void setDescroid(String descroid)
	{
		this.descroid = descroid;
	}

	/**
	 * Method 'getDescrvar'
	 * 
	 * @return String
	 */
	public String getDescrvar()
	{
		return descrvar;
	}

	/**
	 * Method 'setDescrvar'
	 * 
	 * @param descrvar
	 */
	public void setDescrvar(String descrvar)
	{
		this.descrvar = descrvar;
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
		
		if (!(_other instanceof DefMibGrp)) {
			return false;
		}
		
		final DefMibGrp _cast = (DefMibGrp) _other;
		if (mid != _cast.mid) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (indexoid == null ? _cast.indexoid != indexoid : !indexoid.equals( _cast.indexoid )) {
			return false;
		}
		
		if (indexvar == null ? _cast.indexvar != indexvar : !indexvar.equals( _cast.indexvar )) {
			return false;
		}
		
		if (descroid == null ? _cast.descroid != descroid : !descroid.equals( _cast.descroid )) {
			return false;
		}
		
		if (descrvar == null ? _cast.descrvar != descrvar : !descrvar.equals( _cast.descrvar )) {
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
		_hashCode = 29 * _hashCode + (int) (mid ^ (mid >>> 32));
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		if (indexoid != null) {
			_hashCode = 29 * _hashCode + indexoid.hashCode();
		}
		
		if (indexvar != null) {
			_hashCode = 29 * _hashCode + indexvar.hashCode();
		}
		
		if (descroid != null) {
			_hashCode = 29 * _hashCode + descroid.hashCode();
		}
		
		if (descrvar != null) {
			_hashCode = 29 * _hashCode + descrvar.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DefMibGrpPk
	 */
	public DefMibGrpPk createPk()
	{
		return new DefMibGrpPk(mid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.DefMibGrp: " );
		ret.append( "mid=" + mid );
		ret.append( ", name=" + name );
		ret.append( ", indexoid=" + indexoid );
		ret.append( ", indexvar=" + indexvar );
		ret.append( ", descroid=" + descroid );
		ret.append( ", descrvar=" + descrvar );
		return ret.toString();
	}

}
