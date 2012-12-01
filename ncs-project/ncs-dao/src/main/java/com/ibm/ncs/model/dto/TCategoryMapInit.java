package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TCategoryMapInit implements Serializable
{
	/** 
	 * This attribute maps to the column ID in the T_CATEGORY_MAP_INIT table.
	 */
	protected long id;

	/** 
	 * This attribute maps to the column NAME in the T_CATEGORY_MAP_INIT table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column FLAG in the T_CATEGORY_MAP_INIT table.
	 */
	protected String flag;

	/**
	 * Method 'TCategoryMapInit'
	 * 
	 */
	public TCategoryMapInit()
	{
	}

	/**
	 * Method 'getId'
	 * 
	 * @return long
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(long id)
	{
		this.id = id;
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
		
		if (!(_other instanceof TCategoryMapInit)) {
			return false;
		}
		
		final TCategoryMapInit _cast = (TCategoryMapInit) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (flag == null ? _cast.flag != flag : !flag.equals( _cast.flag )) {
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
		_hashCode = 29 * _hashCode + (int) (id ^ (id >>> 32));
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		if (flag != null) {
			_hashCode = 29 * _hashCode + flag.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TCategoryMapInitPk
	 */
	public TCategoryMapInitPk createPk()
	{
		return new TCategoryMapInitPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TCategoryMapInit: " );
		ret.append( "id=" + id );
		ret.append( ", name=" + name );
		ret.append( ", flag=" + flag );
		return ret.toString();
	}

}
