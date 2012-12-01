package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TPpDev implements Serializable
{
	/** 
	 * This attribute maps to the column DEVIP in the T_PP_DEV table.
	 */
	protected String devip;

	/** 
	 * This attribute maps to the column BTIME in the T_PP_DEV table.
	 */
	protected String btime;

	/** 
	 * This attribute maps to the column ETIME in the T_PP_DEV table.
	 */
	protected String etime;

	/**
	 * Method 'TPpDev'
	 * 
	 */
	public TPpDev()
	{
	}

	/**
	 * Method 'getDevip'
	 * 
	 * @return String
	 */
	public String getDevip()
	{
		return devip;
	}

	/**
	 * Method 'setDevip'
	 * 
	 * @param devip
	 */
	public void setDevip(String devip)
	{
		this.devip = devip;
	}

	/**
	 * Method 'getBtime'
	 * 
	 * @return String
	 */
	public String getBtime()
	{
		return btime;
	}

	/**
	 * Method 'setBtime'
	 * 
	 * @param btime
	 */
	public void setBtime(String btime)
	{
		this.btime = btime;
	}

	/**
	 * Method 'getEtime'
	 * 
	 * @return String
	 */
	public String getEtime()
	{
		return etime;
	}

	/**
	 * Method 'setEtime'
	 * 
	 * @param etime
	 */
	public void setEtime(String etime)
	{
		this.etime = etime;
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
		
		if (!(_other instanceof TPpDev)) {
			return false;
		}
		
		final TPpDev _cast = (TPpDev) _other;
		if (devip == null ? _cast.devip != devip : !devip.equals( _cast.devip )) {
			return false;
		}
		
		if (btime == null ? _cast.btime != btime : !btime.equals( _cast.btime )) {
			return false;
		}
		
		if (etime == null ? _cast.etime != etime : !etime.equals( _cast.etime )) {
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
		if (devip != null) {
			_hashCode = 29 * _hashCode + devip.hashCode();
		}
		
		if (btime != null) {
			_hashCode = 29 * _hashCode + btime.hashCode();
		}
		
		if (etime != null) {
			_hashCode = 29 * _hashCode + etime.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TPpDevPk
	 */
	public TPpDevPk createPk()
	{
		return new TPpDevPk(devip);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TPpDev: " );
		ret.append( "devip=" + devip );
		ret.append( ", btime=" + btime );
		ret.append( ", etime=" + etime );
		return ret.toString();
	}

}
