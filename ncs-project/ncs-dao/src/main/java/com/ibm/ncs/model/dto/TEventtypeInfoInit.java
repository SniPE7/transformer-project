package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TEventtypeInfoInit implements Serializable
{
	/** 
	 * This attribute maps to the column ETID in the T_EVENTTYPE_INFO_INIT table.
	 */
	protected long etid;

	/** 
	 * This attribute maps to the column EVETYPE in the T_EVENTTYPE_INFO_INIT table.
	 */
	protected String evetype;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_EVENTTYPE_INFO_INIT table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column ETCODE in the T_EVENTTYPE_INFO_INIT table.
	 */
	protected String etcode;

	/**
	 * Method 'TEventtypeInfoInit'
	 * 
	 */
	public TEventtypeInfoInit()
	{
	}

	/**
	 * Method 'getEtid'
	 * 
	 * @return long
	 */
	public long getEtid()
	{
		return etid;
	}

	/**
	 * Method 'setEtid'
	 * 
	 * @param etid
	 */
	public void setEtid(long etid)
	{
		this.etid = etid;
	}

	/**
	 * Method 'getEvetype'
	 * 
	 * @return String
	 */
	public String getEvetype()
	{
		return evetype;
	}

	/**
	 * Method 'setEvetype'
	 * 
	 * @param evetype
	 */
	public void setEvetype(String evetype)
	{
		this.evetype = evetype;
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
	 * Method 'getEtcode'
	 * 
	 * @return String
	 */
	public String getEtcode()
	{
		return etcode;
	}

	/**
	 * Method 'setEtcode'
	 * 
	 * @param etcode
	 */
	public void setEtcode(String etcode)
	{
		this.etcode = etcode;
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
		
		if (!(_other instanceof TEventtypeInfoInit)) {
			return false;
		}
		
		final TEventtypeInfoInit _cast = (TEventtypeInfoInit) _other;
		if (etid != _cast.etid) {
			return false;
		}
		
		if (evetype == null ? _cast.evetype != evetype : !evetype.equals( _cast.evetype )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		if (etcode == null ? _cast.etcode != etcode : !etcode.equals( _cast.etcode )) {
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
		_hashCode = 29 * _hashCode + (int) (etid ^ (etid >>> 32));
		if (evetype != null) {
			_hashCode = 29 * _hashCode + evetype.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		if (etcode != null) {
			_hashCode = 29 * _hashCode + etcode.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TEventtypeInfoInitPk
	 */
	public TEventtypeInfoInitPk createPk()
	{
		return new TEventtypeInfoInitPk(etid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TEventtypeInfoInit: " );
		ret.append( "etid=" + etid );
		ret.append( ", evetype=" + evetype );
		ret.append( ", description=" + description );
		ret.append( ", etcode=" + etcode );
		return ret.toString();
	}

}
