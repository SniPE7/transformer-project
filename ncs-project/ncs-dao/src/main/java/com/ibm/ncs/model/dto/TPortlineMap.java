package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TPortlineMap implements Serializable
{
	/** 
	 * This attribute maps to the column PTID in the T_PORTLINE_MAP table.
	 */
	protected long ptid;

	/** 
	 * This attribute maps to the column LEID in the T_PORTLINE_MAP table.
	 */
	protected long leid;

	/** 
	 * This attribute represents whether the primitive attribute leid is null.
	 */
	protected boolean leidNull = true;

	/** 
	 * This attribute maps to the column SRID in the T_PORTLINE_MAP table.
	 */
	protected long srid;

	/** 
	 * This attribute represents whether the primitive attribute srid is null.
	 */
	protected boolean sridNull = true;

	/** 
	 * This attribute maps to the column ADMIN in the T_PORTLINE_MAP table.
	 */
	protected String admin;

	/** 
	 * This attribute maps to the column PHONE in the T_PORTLINE_MAP table.
	 */
	protected String phone;

	/** 
	 * This attribute maps to the column SIDE in the T_PORTLINE_MAP table.
	 */
	protected String side;

	/**
	 * Method 'TPortlineMap'
	 * 
	 */
	public TPortlineMap()
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
	 * Method 'getLeid'
	 * 
	 * @return long
	 */
	public long getLeid()
	{
		return leid;
	}

	/**
	 * Method 'setLeid'
	 * 
	 * @param leid
	 */
	public void setLeid(long leid)
	{
		this.leid = leid;
		this.leidNull = false;
	}

	/**
	 * Method 'setLeidNull'
	 * 
	 * @param value
	 */
	public void setLeidNull(boolean value)
	{
		this.leidNull = value;
	}

	/**
	 * Method 'isLeidNull'
	 * 
	 * @return boolean
	 */
	public boolean isLeidNull()
	{
		return leidNull;
	}

	/**
	 * Method 'getSrid'
	 * 
	 * @return long
	 */
	public long getSrid()
	{
		return srid;
	}

	/**
	 * Method 'setSrid'
	 * 
	 * @param srid
	 */
	public void setSrid(long srid)
	{
		this.srid = srid;
		this.sridNull = false;
	}

	/**
	 * Method 'setSridNull'
	 * 
	 * @param value
	 */
	public void setSridNull(boolean value)
	{
		this.sridNull = value;
	}

	/**
	 * Method 'isSridNull'
	 * 
	 * @return boolean
	 */
	public boolean isSridNull()
	{
		return sridNull;
	}

	/**
	 * Method 'getAdmin'
	 * 
	 * @return String
	 */
	public String getAdmin()
	{
		return admin;
	}

	/**
	 * Method 'setAdmin'
	 * 
	 * @param admin
	 */
	public void setAdmin(String admin)
	{
		this.admin = admin;
	}

	/**
	 * Method 'getPhone'
	 * 
	 * @return String
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * Method 'setPhone'
	 * 
	 * @param phone
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * Method 'getSide'
	 * 
	 * @return String
	 */
	public String getSide()
	{
		return side;
	}

	/**
	 * Method 'setSide'
	 * 
	 * @param side
	 */
	public void setSide(String side)
	{
		this.side = side;
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
		
		if (!(_other instanceof TPortlineMap)) {
			return false;
		}
		
		final TPortlineMap _cast = (TPortlineMap) _other;
		if (ptid != _cast.ptid) {
			return false;
		}
		
		if (leid != _cast.leid) {
			return false;
		}
		
		if (leidNull != _cast.leidNull) {
			return false;
		}
		
		if (srid != _cast.srid) {
			return false;
		}
		
		if (sridNull != _cast.sridNull) {
			return false;
		}
		
		if (admin == null ? _cast.admin != admin : !admin.equals( _cast.admin )) {
			return false;
		}
		
		if (phone == null ? _cast.phone != phone : !phone.equals( _cast.phone )) {
			return false;
		}
		
		if (side == null ? _cast.side != side : !side.equals( _cast.side )) {
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
		_hashCode = 29 * _hashCode + (int) (leid ^ (leid >>> 32));
		_hashCode = 29 * _hashCode + (leidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (srid ^ (srid >>> 32));
		_hashCode = 29 * _hashCode + (sridNull ? 1 : 0);
		if (admin != null) {
			_hashCode = 29 * _hashCode + admin.hashCode();
		}
		
		if (phone != null) {
			_hashCode = 29 * _hashCode + phone.hashCode();
		}
		
		if (side != null) {
			_hashCode = 29 * _hashCode + side.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TPortlineMapPk
	 */
	public TPortlineMapPk createPk()
	{
		return new TPortlineMapPk(ptid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TPortlineMap: " );
		ret.append( "ptid=" + ptid );
		ret.append( ", leid=" + leid );
		ret.append( ", srid=" + srid );
		ret.append( ", admin=" + admin );
		ret.append( ", phone=" + phone );
		ret.append( ", side=" + side );
		return ret.toString();
	}

}
