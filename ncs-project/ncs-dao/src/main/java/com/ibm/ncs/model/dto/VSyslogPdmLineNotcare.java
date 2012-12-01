package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class VSyslogPdmLineNotcare implements Serializable
{
	/** 
	 * This attribute maps to the column DEVIP in the V_SYSLOG_PDM_LINE_NOTCARE table.
	 */
	protected String devip;

	/** 
	 * This attribute maps to the column OIDNAME in the V_SYSLOG_PDM_LINE_NOTCARE table.
	 */
	protected String oidname;

	/**
	 * Method 'VSyslogPdmLineNotcare'
	 * 
	 */
	public VSyslogPdmLineNotcare()
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
	 * Method 'getOidname'
	 * 
	 * @return String
	 */
	public String getOidname()
	{
		return oidname;
	}

	/**
	 * Method 'setOidname'
	 * 
	 * @param oidname
	 */
	public void setOidname(String oidname)
	{
		this.oidname = oidname;
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
		
		if (!(_other instanceof VSyslogPdmLineNotcare)) {
			return false;
		}
		
		final VSyslogPdmLineNotcare _cast = (VSyslogPdmLineNotcare) _other;
		if (devip == null ? _cast.devip != devip : !devip.equals( _cast.devip )) {
			return false;
		}
		
		if (oidname == null ? _cast.oidname != oidname : !oidname.equals( _cast.oidname )) {
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
		
		if (oidname != null) {
			_hashCode = 29 * _hashCode + oidname.hashCode();
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
		ret.append( "com.ibm.ncs.model.dto.VSyslogPdmLineNotcare: " );
		ret.append( "devip=" + devip );
		ret.append( ", oidname=" + oidname );
		return ret.toString();
	}

}
