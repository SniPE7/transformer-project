package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TDevpolMap implements Serializable
{
	/** 
	 * This attribute maps to the column DEVID in the T_DEVPOL_MAP table.
	 */
	protected long devid;

	/** 
	 * This attribute maps to the column MPID in the T_DEVPOL_MAP table.
	 */
	protected long mpid;

	/** 
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean mpidNull = true;

	/** 
	 * This attribute maps to the column PPID in the T_DEVPOL_MAP table.
	 */
	protected long ppid;

	/** 
	 * This attribute represents whether the primitive attribute ppid is null.
	 */
	protected boolean ppidNull = true;

	/**
	 * Method 'TDevpolMap'
	 * 
	 */
	public TDevpolMap()
	{
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
	 * Method 'getMpid'
	 * 
	 * @return long
	 */
	public long getMpid()
	{
		return mpid;
	}

	/**
	 * Method 'setMpid'
	 * 
	 * @param mpid
	 */
	public void setMpid(long mpid)
	{
		this.mpid = mpid;
		this.mpidNull = false;
	}

	/**
	 * Method 'setMpidNull'
	 * 
	 * @param value
	 */
	public void setMpidNull(boolean value)
	{
		this.mpidNull = value;
	}

	/**
	 * Method 'isMpidNull'
	 * 
	 * @return boolean
	 */
	public boolean isMpidNull()
	{
		return mpidNull;
	}

	/**
	 * Method 'getPpid'
	 * 
	 * @return long
	 */
	public long getPpid()
	{
		return ppid;
	}

	/**
	 * Method 'setPpid'
	 * 
	 * @param ppid
	 */
	public void setPpid(long ppid)
	{
		this.ppid = ppid;
		this.ppidNull = false;
	}

	/**
	 * Method 'setPpidNull'
	 * 
	 * @param value
	 */
	public void setPpidNull(boolean value)
	{
		this.ppidNull = value;
	}

	/**
	 * Method 'isPpidNull'
	 * 
	 * @return boolean
	 */
	public boolean isPpidNull()
	{
		return ppidNull;
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
		
		if (!(_other instanceof TDevpolMap)) {
			return false;
		}
		
		final TDevpolMap _cast = (TDevpolMap) _other;
		if (devid != _cast.devid) {
			return false;
		}
		
		if (mpid != _cast.mpid) {
			return false;
		}
		
		if (mpidNull != _cast.mpidNull) {
			return false;
		}
		
		if (ppid != _cast.ppid) {
			return false;
		}
		
		if (ppidNull != _cast.ppidNull) {
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
		_hashCode = 29 * _hashCode + (int) (devid ^ (devid >>> 32));
		_hashCode = 29 * _hashCode + (int) (mpid ^ (mpid >>> 32));
		_hashCode = 29 * _hashCode + (mpidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (ppid ^ (ppid >>> 32));
		_hashCode = 29 * _hashCode + (ppidNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TDevpolMapPk
	 */
	public TDevpolMapPk createPk()
	{
		return new TDevpolMapPk(devid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TDevpolMap: " );
		ret.append( "devid=" + devid );
		ret.append( ", mpid=" + mpid );
		ret.append( ", ppid=" + ppid );
		return ret.toString();
	}

}
