package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class IcmpThresholds implements Serializable
{
	/** 
	 * This attribute maps to the column IPADDRESS in the ICMP_THRESHOLDS table.
	 */
	protected String ipaddress;

	/** 
	 * This attribute maps to the column BTIME in the ICMP_THRESHOLDS table.
	 */
	protected String btime;

	/** 
	 * This attribute maps to the column ETIME in the ICMP_THRESHOLDS table.
	 */
	protected String etime;

	/** 
	 * This attribute maps to the column THRESHOLD in the ICMP_THRESHOLDS table.
	 */
	protected String threshold;

	/** 
	 * This attribute maps to the column COMPARETYPE in the ICMP_THRESHOLDS table.
	 */
	protected String comparetype;

	/** 
	 * This attribute maps to the column SEVERITY1 in the ICMP_THRESHOLDS table.
	 */
	protected String severity1;

	/** 
	 * This attribute maps to the column SEVERITY2 in the ICMP_THRESHOLDS table.
	 */
	protected String severity2;

	/** 
	 * This attribute maps to the column FILTERFLAG1 in the ICMP_THRESHOLDS table.
	 */
	protected long filterflag1;

	/** 
	 * This attribute represents whether the primitive attribute filterflag1 is null.
	 */
	protected boolean filterflag1Null = true;

	/** 
	 * This attribute maps to the column FILTERFLAG2 in the ICMP_THRESHOLDS table.
	 */
	protected long filterflag2;

	/** 
	 * This attribute represents whether the primitive attribute filterflag2 is null.
	 */
	protected boolean filterflag2Null = true;

	/** 
	 * This attribute maps to the column VARLIST in the ICMP_THRESHOLDS table.
	 */
	protected String varlist;

	/** 
	 * This attribute maps to the column SUMMARYCN in the ICMP_THRESHOLDS table.
	 */
	protected String summarycn;

	/**
	 * Method 'IcmpThresholds'
	 * 
	 */
	public IcmpThresholds()
	{
	}

	/**
	 * Method 'getIpaddress'
	 * 
	 * @return String
	 */
	public String getIpaddress()
	{
		return ipaddress;
	}

	/**
	 * Method 'setIpaddress'
	 * 
	 * @param ipaddress
	 */
	public void setIpaddress(String ipaddress)
	{
		this.ipaddress = ipaddress;
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
	 * Method 'getThreshold'
	 * 
	 * @return String
	 */
	public String getThreshold()
	{
		return threshold;
	}

	/**
	 * Method 'setThreshold'
	 * 
	 * @param threshold
	 */
	public void setThreshold(String threshold)
	{
		this.threshold = threshold;
	}

	/**
	 * Method 'getComparetype'
	 * 
	 * @return String
	 */
	public String getComparetype()
	{
		return comparetype;
	}

	/**
	 * Method 'setComparetype'
	 * 
	 * @param comparetype
	 */
	public void setComparetype(String comparetype)
	{
		this.comparetype = comparetype;
	}

	/**
	 * Method 'getSeverity1'
	 * 
	 * @return String
	 */
	public String getSeverity1()
	{
		return severity1;
	}

	/**
	 * Method 'setSeverity1'
	 * 
	 * @param severity1
	 */
	public void setSeverity1(String severity1)
	{
		this.severity1 = severity1;
	}

	/**
	 * Method 'getSeverity2'
	 * 
	 * @return String
	 */
	public String getSeverity2()
	{
		return severity2;
	}

	/**
	 * Method 'setSeverity2'
	 * 
	 * @param severity2
	 */
	public void setSeverity2(String severity2)
	{
		this.severity2 = severity2;
	}

	/**
	 * Method 'getFilterflag1'
	 * 
	 * @return long
	 */
	public long getFilterflag1()
	{
		return filterflag1;
	}

	/**
	 * Method 'setFilterflag1'
	 * 
	 * @param filterflag1
	 */
	public void setFilterflag1(long filterflag1)
	{
		this.filterflag1 = filterflag1;
		this.filterflag1Null = false;
	}

	/**
	 * Method 'setFilterflag1Null'
	 * 
	 * @param value
	 */
	public void setFilterflag1Null(boolean value)
	{
		this.filterflag1Null = value;
	}

	/**
	 * Method 'isFilterflag1Null'
	 * 
	 * @return boolean
	 */
	public boolean isFilterflag1Null()
	{
		return filterflag1Null;
	}

	/**
	 * Method 'getFilterflag2'
	 * 
	 * @return long
	 */
	public long getFilterflag2()
	{
		return filterflag2;
	}

	/**
	 * Method 'setFilterflag2'
	 * 
	 * @param filterflag2
	 */
	public void setFilterflag2(long filterflag2)
	{
		this.filterflag2 = filterflag2;
		this.filterflag2Null = false;
	}

	/**
	 * Method 'setFilterflag2Null'
	 * 
	 * @param value
	 */
	public void setFilterflag2Null(boolean value)
	{
		this.filterflag2Null = value;
	}

	/**
	 * Method 'isFilterflag2Null'
	 * 
	 * @return boolean
	 */
	public boolean isFilterflag2Null()
	{
		return filterflag2Null;
	}

	/**
	 * Method 'getVarlist'
	 * 
	 * @return String
	 */
	public String getVarlist()
	{
		return varlist;
	}

	/**
	 * Method 'setVarlist'
	 * 
	 * @param varlist
	 */
	public void setVarlist(String varlist)
	{
		this.varlist = varlist;
	}

	/**
	 * Method 'getSummarycn'
	 * 
	 * @return String
	 */
	public String getSummarycn()
	{
		return summarycn;
	}

	/**
	 * Method 'setSummarycn'
	 * 
	 * @param summarycn
	 */
	public void setSummarycn(String summarycn)
	{
		this.summarycn = summarycn;
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
		
		if (!(_other instanceof IcmpThresholds)) {
			return false;
		}
		
		final IcmpThresholds _cast = (IcmpThresholds) _other;
		if (ipaddress == null ? _cast.ipaddress != ipaddress : !ipaddress.equals( _cast.ipaddress )) {
			return false;
		}
		
		if (btime == null ? _cast.btime != btime : !btime.equals( _cast.btime )) {
			return false;
		}
		
		if (etime == null ? _cast.etime != etime : !etime.equals( _cast.etime )) {
			return false;
		}
		
		if (threshold == null ? _cast.threshold != threshold : !threshold.equals( _cast.threshold )) {
			return false;
		}
		
		if (comparetype == null ? _cast.comparetype != comparetype : !comparetype.equals( _cast.comparetype )) {
			return false;
		}
		
		if (severity1 == null ? _cast.severity1 != severity1 : !severity1.equals( _cast.severity1 )) {
			return false;
		}
		
		if (severity2 == null ? _cast.severity2 != severity2 : !severity2.equals( _cast.severity2 )) {
			return false;
		}
		
		if (filterflag1 != _cast.filterflag1) {
			return false;
		}
		
		if (filterflag1Null != _cast.filterflag1Null) {
			return false;
		}
		
		if (filterflag2 != _cast.filterflag2) {
			return false;
		}
		
		if (filterflag2Null != _cast.filterflag2Null) {
			return false;
		}
		
		if (varlist == null ? _cast.varlist != varlist : !varlist.equals( _cast.varlist )) {
			return false;
		}
		
		if (summarycn == null ? _cast.summarycn != summarycn : !summarycn.equals( _cast.summarycn )) {
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
		if (ipaddress != null) {
			_hashCode = 29 * _hashCode + ipaddress.hashCode();
		}
		
		if (btime != null) {
			_hashCode = 29 * _hashCode + btime.hashCode();
		}
		
		if (etime != null) {
			_hashCode = 29 * _hashCode + etime.hashCode();
		}
		
		if (threshold != null) {
			_hashCode = 29 * _hashCode + threshold.hashCode();
		}
		
		if (comparetype != null) {
			_hashCode = 29 * _hashCode + comparetype.hashCode();
		}
		
		if (severity1 != null) {
			_hashCode = 29 * _hashCode + severity1.hashCode();
		}
		
		if (severity2 != null) {
			_hashCode = 29 * _hashCode + severity2.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (filterflag1 ^ (filterflag1 >>> 32));
		_hashCode = 29 * _hashCode + (filterflag1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (filterflag2 ^ (filterflag2 >>> 32));
		_hashCode = 29 * _hashCode + (filterflag2Null ? 1 : 0);
		if (varlist != null) {
			_hashCode = 29 * _hashCode + varlist.hashCode();
		}
		
		if (summarycn != null) {
			_hashCode = 29 * _hashCode + summarycn.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return IcmpThresholdsPk
	 */
	public IcmpThresholdsPk createPk()
	{
		return new IcmpThresholdsPk(ipaddress);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.IcmpThresholds: " );
		ret.append( "ipaddress=" + ipaddress );
		ret.append( ", btime=" + btime );
		ret.append( ", etime=" + etime );
		ret.append( ", threshold=" + threshold );
		ret.append( ", comparetype=" + comparetype );
		ret.append( ", severity1=" + severity1 );
		ret.append( ", severity2=" + severity2 );
		ret.append( ", filterflag1=" + filterflag1 );
		ret.append( ", filterflag2=" + filterflag2 );
		ret.append( ", varlist=" + varlist );
		ret.append( ", summarycn=" + summarycn );
		return ret.toString();
	}

}
