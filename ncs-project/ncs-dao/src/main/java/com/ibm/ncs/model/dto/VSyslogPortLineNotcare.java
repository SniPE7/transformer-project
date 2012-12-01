package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class VSyslogPortLineNotcare implements Serializable
{
	/** 
	 * This attribute maps to the column DEVIP in the V_SYSLOG_PORT_LINE_NOTCARE table.
	 */
	protected String devip;

	/** 
	 * This attribute maps to the column IFDESCR in the V_SYSLOG_PORT_LINE_NOTCARE table.
	 */
	protected String ifdescr;

	/**
	 * Method 'VSyslogPortLineNotcare'
	 * 
	 */
	public VSyslogPortLineNotcare()
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
	 * Method 'getIfdescr'
	 * 
	 * @return String
	 */
	public String getIfdescr()
	{
		return ifdescr;
	}

	/**
	 * Method 'setIfdescr'
	 * 
	 * @param ifdescr
	 */
	public void setIfdescr(String ifdescr)
	{
		this.ifdescr = ifdescr;
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
		
		if (!(_other instanceof VSyslogPortLineNotcare)) {
			return false;
		}
		
		final VSyslogPortLineNotcare _cast = (VSyslogPortLineNotcare) _other;
		if (devip == null ? _cast.devip != devip : !devip.equals( _cast.devip )) {
			return false;
		}
		
		if (ifdescr == null ? _cast.ifdescr != ifdescr : !ifdescr.equals( _cast.ifdescr )) {
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
		
		if (ifdescr != null) {
			_hashCode = 29 * _hashCode + ifdescr.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.VSyslogPortLineNotcare: " );
		ret.append( "devip=" + devip );
		ret.append( ", ifdescr=" + ifdescr );
		return ret.toString();
	}

}
