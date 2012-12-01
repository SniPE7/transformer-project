package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TEventsubtypeInfoInit implements Serializable
{
	/** 
	 * This attribute maps to the column ESTID in the T_EVENTSUBTYPE_INFO_INIT table.
	 */
	protected long estid;

	/** 
	 * This attribute maps to the column EVESUBTYPE in the T_EVENTSUBTYPE_INFO_INIT table.
	 */
	protected String evesubtype;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_EVENTSUBTYPE_INFO_INIT table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column ESTCODE in the T_EVENTSUBTYPE_INFO_INIT table.
	 */
	protected String estcode;

	/**
	 * Method 'TEventsubtypeInfoInit'
	 * 
	 */
	public TEventsubtypeInfoInit()
	{
	}

	/**
	 * Method 'getEstid'
	 * 
	 * @return long
	 */
	public long getEstid()
	{
		return estid;
	}

	/**
	 * Method 'setEstid'
	 * 
	 * @param estid
	 */
	public void setEstid(long estid)
	{
		this.estid = estid;
	}

	/**
	 * Method 'getEvesubtype'
	 * 
	 * @return String
	 */
	public String getEvesubtype()
	{
		return evesubtype;
	}

	/**
	 * Method 'setEvesubtype'
	 * 
	 * @param evesubtype
	 */
	public void setEvesubtype(String evesubtype)
	{
		this.evesubtype = evesubtype;
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
	 * Method 'getEstcode'
	 * 
	 * @return String
	 */
	public String getEstcode()
	{
		return estcode;
	}

	/**
	 * Method 'setEstcode'
	 * 
	 * @param estcode
	 */
	public void setEstcode(String estcode)
	{
		this.estcode = estcode;
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
		
		if (!(_other instanceof TEventsubtypeInfoInit)) {
			return false;
		}
		
		final TEventsubtypeInfoInit _cast = (TEventsubtypeInfoInit) _other;
		if (estid != _cast.estid) {
			return false;
		}
		
		if (evesubtype == null ? _cast.evesubtype != evesubtype : !evesubtype.equals( _cast.evesubtype )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		if (estcode == null ? _cast.estcode != estcode : !estcode.equals( _cast.estcode )) {
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
		_hashCode = 29 * _hashCode + (int) (estid ^ (estid >>> 32));
		if (evesubtype != null) {
			_hashCode = 29 * _hashCode + evesubtype.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		if (estcode != null) {
			_hashCode = 29 * _hashCode + estcode.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TEventsubtypeInfoInitPk
	 */
	public TEventsubtypeInfoInitPk createPk()
	{
		return new TEventsubtypeInfoInitPk(estid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TEventsubtypeInfoInit: " );
		ret.append( "estid=" + estid );
		ret.append( ", evesubtype=" + evesubtype );
		ret.append( ", description=" + description );
		ret.append( ", estcode=" + estcode );
		return ret.toString();
	}

}
