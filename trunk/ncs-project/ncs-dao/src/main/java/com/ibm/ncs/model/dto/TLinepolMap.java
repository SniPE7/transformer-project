package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TLinepolMap implements Serializable
{
	/** 
	 * This attribute maps to the column PTID in the T_LINEPOL_MAP table.
	 */
	protected long ptid;

	/** 
	 * This attribute maps to the column PPID in the T_LINEPOL_MAP table.
	 */
	protected long ppid;

	/** 
	 * This attribute represents whether the primitive attribute ppid is null.
	 */
	protected boolean ppidNull = true;

	/** 
	 * This attribute maps to the column MCODE in the T_LINEPOL_MAP table.
	 */
	protected long mcode;

	/** 
	 * This attribute represents whether the primitive attribute mcode is null.
	 */
	protected boolean mcodeNull = true;

	/** 
	 * This attribute maps to the column FLAG in the T_LINEPOL_MAP table.
	 */
	protected Integer flag;

	/** 
	 * This attribute maps to the column MPID in the T_LINEPOL_MAP table.
	 */
	protected long mpid;

	/** 
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean mpidNull = true;

	/**
	 * Method 'TLinepolMap'
	 * 
	 */
	public TLinepolMap()
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
	 * Method 'getMcode'
	 * 
	 * @return long
	 */
	public long getMcode()
	{
		return mcode;
	}

	/**
	 * Method 'setMcode'
	 * 
	 * @param mcode
	 */
	public void setMcode(long mcode)
	{
		this.mcode = mcode;
		this.mcodeNull = false;
	}

	/**
	 * Method 'setMcodeNull'
	 * 
	 * @param value
	 */
	public void setMcodeNull(boolean value)
	{
		this.mcodeNull = value;
	}

	/**
	 * Method 'isMcodeNull'
	 * 
	 * @return boolean
	 */
	public boolean isMcodeNull()
	{
		return mcodeNull;
	}

	/**
	 * Method 'getFlag'
	 * 
	 * @return Integer
	 */
	public Integer getFlag()
	{
		return flag;
	}

	/**
	 * Method 'setFlag'
	 * 
	 * @param flag
	 */
	public void setFlag(Integer flag)
	{
		this.flag = flag;
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
		
		if (!(_other instanceof TLinepolMap)) {
			return false;
		}
		
		final TLinepolMap _cast = (TLinepolMap) _other;
		if (ptid != _cast.ptid) {
			return false;
		}
		
		if (ppid != _cast.ppid) {
			return false;
		}
		
		if (ppidNull != _cast.ppidNull) {
			return false;
		}
		
		if (mcode != _cast.mcode) {
			return false;
		}
		
		if (mcodeNull != _cast.mcodeNull) {
			return false;
		}
		
		if (flag == null ? _cast.flag != flag : !flag.equals( _cast.flag )) {
			return false;
		}
		
		if (mpid != _cast.mpid) {
			return false;
		}
		
		if (mpidNull != _cast.mpidNull) {
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
		_hashCode = 29 * _hashCode + (int) (ppid ^ (ppid >>> 32));
		_hashCode = 29 * _hashCode + (ppidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (mcode ^ (mcode >>> 32));
		_hashCode = 29 * _hashCode + (mcodeNull ? 1 : 0);
		if (flag != null) {
			_hashCode = 29 * _hashCode + flag.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (mpid ^ (mpid >>> 32));
		_hashCode = 29 * _hashCode + (mpidNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TLinepolMapPk
	 */
	public TLinepolMapPk createPk()
	{
		return new TLinepolMapPk(ptid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TLinepolMap: " );
		ret.append( "ptid=" + ptid );
		ret.append( ", ppid=" + ppid );
		ret.append( ", mcode=" + mcode );
		ret.append( ", flag=" + flag );
		ret.append( ", mpid=" + mpid );
		return ret.toString();
	}

}
