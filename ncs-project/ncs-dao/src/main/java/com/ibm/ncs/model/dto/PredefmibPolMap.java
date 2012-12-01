package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class PredefmibPolMap implements Serializable
{
	/** 
	 * This attribute maps to the column PDMID in the PREDEFMIB_POL_MAP table.
	 */
	protected long pdmid;

	/** 
	 * This attribute maps to the column MPID in the PREDEFMIB_POL_MAP table.
	 */
	protected long mpid;

	/** 
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean mpidNull = true;

	/** 
	 * This attribute maps to the column PPID in the PREDEFMIB_POL_MAP table.
	 */
	protected long ppid;

	/** 
	 * This attribute represents whether the primitive attribute ppid is null.
	 */
	protected boolean ppidNull = true;

	/** 
	 * This attribute maps to the column MCODE in the PREDEFMIB_POL_MAP table.
	 */
	protected long mcode;

	/** 
	 * This attribute represents whether the primitive attribute mcode is null.
	 */
	protected boolean mcodeNull = true;

	/** 
	 * This attribute maps to the column FLAG in the PREDEFMIB_POL_MAP table.
	 */
	protected int flag;

	/** 
	 * This attribute represents whether the primitive attribute flag is null.
	 */
	protected boolean flagNull = true;

	/**
	 * Method 'PredefmibPolMap'
	 * 
	 */
	public PredefmibPolMap()
	{
	}

	/**
	 * Method 'getPdmid'
	 * 
	 * @return long
	 */
	public long getPdmid()
	{
		return pdmid;
	}

	/**
	 * Method 'setPdmid'
	 * 
	 * @param pdmid
	 */
	public void setPdmid(long pdmid)
	{
		this.pdmid = pdmid;
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
	 * @return int
	 */
	public int getFlag()
	{
		return flag;
	}

	/**
	 * Method 'setFlag'
	 * 
	 * @param flag
	 */
	public void setFlag(int flag)
	{
		this.flag = flag;
		this.flagNull = false;
	}

	/**
	 * Method 'setFlagNull'
	 * 
	 * @param value
	 */
	public void setFlagNull(boolean value)
	{
		this.flagNull = value;
	}

	/**
	 * Method 'isFlagNull'
	 * 
	 * @return boolean
	 */
	public boolean isFlagNull()
	{
		return flagNull;
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
		
		if (!(_other instanceof PredefmibPolMap)) {
			return false;
		}
		
		final PredefmibPolMap _cast = (PredefmibPolMap) _other;
		if (pdmid != _cast.pdmid) {
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
		
		if (mcode != _cast.mcode) {
			return false;
		}
		
		if (mcodeNull != _cast.mcodeNull) {
			return false;
		}
		
		if (flag != _cast.flag) {
			return false;
		}
		
		if (flagNull != _cast.flagNull) {
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
		_hashCode = 29 * _hashCode + (int) (pdmid ^ (pdmid >>> 32));
		_hashCode = 29 * _hashCode + (int) (mpid ^ (mpid >>> 32));
		_hashCode = 29 * _hashCode + (mpidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (ppid ^ (ppid >>> 32));
		_hashCode = 29 * _hashCode + (ppidNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (mcode ^ (mcode >>> 32));
		_hashCode = 29 * _hashCode + (mcodeNull ? 1 : 0);
		_hashCode = 29 * _hashCode + flag;
		_hashCode = 29 * _hashCode + (flagNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return PredefmibPolMapPk
	 */
	public PredefmibPolMapPk createPk()
	{
		return new PredefmibPolMapPk(pdmid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.PredefmibPolMap: " );
		ret.append( "pdmid=" + pdmid );
		ret.append( ", mpid=" + mpid );
		ret.append( ", ppid=" + ppid );
		ret.append( ", mcode=" + mcode );
		ret.append( ", flag=" + flag );
		return ret.toString();
	}

}
