package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class TPolicyPeriod implements Serializable
{
	/** 
	 * This attribute maps to the column PPID in the T_POLICY_PERIOD table.
	 */
	protected long ppid;

	/** 
	 * This attribute maps to the column PPNAME in the T_POLICY_PERIOD table.
	 */
	protected String ppname;

	/** 
	 * This attribute maps to the column START_TIME in the T_POLICY_PERIOD table.
	 */
	protected Date startTime;

	/** 
	 * This attribute maps to the column END_TIME in the T_POLICY_PERIOD table.
	 */
	protected Date endTime;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_POLICY_PERIOD table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column WORKDAY in the T_POLICY_PERIOD table.
	 */
	protected String workday;

	/** 
	 * This attribute maps to the column ENABLED in the T_POLICY_PERIOD table.
	 */
	protected String enabled;

	/** 
	 * This attribute maps to the column DEFAULTFLAG in the T_POLICY_PERIOD table.
	 */
	protected String defaultflag;

	/** 
	 * This attribute maps to the column S1 in the T_POLICY_PERIOD table.
	 */
	protected long s1;

	/** 
	 * This attribute represents whether the primitive attribute s1 is null.
	 */
	protected boolean s1Null = true;

	/** 
	 * This attribute maps to the column S2 in the T_POLICY_PERIOD table.
	 */
	protected long s2;

	/** 
	 * This attribute represents whether the primitive attribute s2 is null.
	 */
	protected boolean s2Null = true;

	/** 
	 * This attribute maps to the column S3 in the T_POLICY_PERIOD table.
	 */
	protected long s3;

	/** 
	 * This attribute represents whether the primitive attribute s3 is null.
	 */
	protected boolean s3Null = true;

	/** 
	 * This attribute maps to the column S4 in the T_POLICY_PERIOD table.
	 */
	protected long s4;

	/** 
	 * This attribute represents whether the primitive attribute s4 is null.
	 */
	protected boolean s4Null = true;

	/** 
	 * This attribute maps to the column S5 in the T_POLICY_PERIOD table.
	 */
	protected long s5;

	/** 
	 * This attribute represents whether the primitive attribute s5 is null.
	 */
	protected boolean s5Null = true;

	/** 
	 * This attribute maps to the column S6 in the T_POLICY_PERIOD table.
	 */
	protected long s6;

	/** 
	 * This attribute represents whether the primitive attribute s6 is null.
	 */
	protected boolean s6Null = true;

	/** 
	 * This attribute maps to the column S7 in the T_POLICY_PERIOD table.
	 */
	protected long s7;

	/** 
	 * This attribute represents whether the primitive attribute s7 is null.
	 */
	protected boolean s7Null = true;

	/** 
	 * This attribute maps to the column E1 in the T_POLICY_PERIOD table.
	 */
	protected long e1;

	/** 
	 * This attribute represents whether the primitive attribute e1 is null.
	 */
	protected boolean e1Null = true;

	/** 
	 * This attribute maps to the column E2 in the T_POLICY_PERIOD table.
	 */
	protected long e2;

	/** 
	 * This attribute represents whether the primitive attribute e2 is null.
	 */
	protected boolean e2Null = true;

	/** 
	 * This attribute maps to the column E3 in the T_POLICY_PERIOD table.
	 */
	protected long e3;

	/** 
	 * This attribute represents whether the primitive attribute e3 is null.
	 */
	protected boolean e3Null = true;

	/** 
	 * This attribute maps to the column E4 in the T_POLICY_PERIOD table.
	 */
	protected long e4;

	/** 
	 * This attribute represents whether the primitive attribute e4 is null.
	 */
	protected boolean e4Null = true;

	/** 
	 * This attribute maps to the column E5 in the T_POLICY_PERIOD table.
	 */
	protected long e5;

	/** 
	 * This attribute represents whether the primitive attribute e5 is null.
	 */
	protected boolean e5Null = true;

	/** 
	 * This attribute maps to the column E6 in the T_POLICY_PERIOD table.
	 */
	protected long e6;

	/** 
	 * This attribute represents whether the primitive attribute e6 is null.
	 */
	protected boolean e6Null = true;

	/** 
	 * This attribute maps to the column E7 in the T_POLICY_PERIOD table.
	 */
	protected long e7;

	/** 
	 * This attribute represents whether the primitive attribute e7 is null.
	 */
	protected boolean e7Null = true;

	/** 
	 * This attribute maps to the column BTIME in the T_POLICY_PERIOD table.
	 */
	protected String btime;

	/** 
	 * This attribute maps to the column ETIME in the T_POLICY_PERIOD table.
	 */
	protected String etime;

	/**
	 * Method 'TPolicyPeriod'
	 * 
	 */
	public TPolicyPeriod()
	{
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
	}

	/**
	 * Method 'getPpname'
	 * 
	 * @return String
	 */
	public String getPpname()
	{
		return ppname;
	}

	/**
	 * Method 'setPpname'
	 * 
	 * @param ppname
	 */
	public void setPpname(String ppname)
	{
		this.ppname = ppname;
	}

	/**
	 * Method 'getStartTime'
	 * 
	 * @return Date
	 */
	public Date getStartTime()
	{
		return startTime;
	}

	/**
	 * Method 'setStartTime'
	 * 
	 * @param startTime
	 */
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * Method 'getEndTime'
	 * 
	 * @return Date
	 */
	public Date getEndTime()
	{
		return endTime;
	}

	/**
	 * Method 'setEndTime'
	 * 
	 * @param endTime
	 */
	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
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
	 * Method 'getWorkday'
	 * 
	 * @return String
	 */
	public String getWorkday()
	{
		return workday;
	}

	/**
	 * Method 'setWorkday'
	 * 
	 * @param workday
	 */
	public void setWorkday(String workday)
	{
		this.workday = workday;
	}

	/**
	 * Method 'getEnabled'
	 * 
	 * @return String
	 */
	public String getEnabled()
	{
		return enabled;
	}

	/**
	 * Method 'setEnabled'
	 * 
	 * @param enabled
	 */
	public void setEnabled(String enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * Method 'getDefaultflag'
	 * 
	 * @return String
	 */
	public String getDefaultflag()
	{
		return defaultflag;
	}

	/**
	 * Method 'setDefaultflag'
	 * 
	 * @param defaultflag
	 */
	public void setDefaultflag(String defaultflag)
	{
		this.defaultflag = defaultflag;
	}

	/**
	 * Method 'getS1'
	 * 
	 * @return long
	 */
	public long getS1()
	{
		return s1;
	}

	/**
	 * Method 'setS1'
	 * 
	 * @param s1
	 */
	public void setS1(long s1)
	{
		this.s1 = s1;
		this.s1Null = false;
	}

	/**
	 * Method 'setS1Null'
	 * 
	 * @param value
	 */
	public void setS1Null(boolean value)
	{
		this.s1Null = value;
	}

	/**
	 * Method 'isS1Null'
	 * 
	 * @return boolean
	 */
	public boolean isS1Null()
	{
		return s1Null;
	}

	/**
	 * Method 'getS2'
	 * 
	 * @return long
	 */
	public long getS2()
	{
		return s2;
	}

	/**
	 * Method 'setS2'
	 * 
	 * @param s2
	 */
	public void setS2(long s2)
	{
		this.s2 = s2;
		this.s2Null = false;
	}

	/**
	 * Method 'setS2Null'
	 * 
	 * @param value
	 */
	public void setS2Null(boolean value)
	{
		this.s2Null = value;
	}

	/**
	 * Method 'isS2Null'
	 * 
	 * @return boolean
	 */
	public boolean isS2Null()
	{
		return s2Null;
	}

	/**
	 * Method 'getS3'
	 * 
	 * @return long
	 */
	public long getS3()
	{
		return s3;
	}

	/**
	 * Method 'setS3'
	 * 
	 * @param s3
	 */
	public void setS3(long s3)
	{
		this.s3 = s3;
		this.s3Null = false;
	}

	/**
	 * Method 'setS3Null'
	 * 
	 * @param value
	 */
	public void setS3Null(boolean value)
	{
		this.s3Null = value;
	}

	/**
	 * Method 'isS3Null'
	 * 
	 * @return boolean
	 */
	public boolean isS3Null()
	{
		return s3Null;
	}

	/**
	 * Method 'getS4'
	 * 
	 * @return long
	 */
	public long getS4()
	{
		return s4;
	}

	/**
	 * Method 'setS4'
	 * 
	 * @param s4
	 */
	public void setS4(long s4)
	{
		this.s4 = s4;
		this.s4Null = false;
	}

	/**
	 * Method 'setS4Null'
	 * 
	 * @param value
	 */
	public void setS4Null(boolean value)
	{
		this.s4Null = value;
	}

	/**
	 * Method 'isS4Null'
	 * 
	 * @return boolean
	 */
	public boolean isS4Null()
	{
		return s4Null;
	}

	/**
	 * Method 'getS5'
	 * 
	 * @return long
	 */
	public long getS5()
	{
		return s5;
	}

	/**
	 * Method 'setS5'
	 * 
	 * @param s5
	 */
	public void setS5(long s5)
	{
		this.s5 = s5;
		this.s5Null = false;
	}

	/**
	 * Method 'setS5Null'
	 * 
	 * @param value
	 */
	public void setS5Null(boolean value)
	{
		this.s5Null = value;
	}

	/**
	 * Method 'isS5Null'
	 * 
	 * @return boolean
	 */
	public boolean isS5Null()
	{
		return s5Null;
	}

	/**
	 * Method 'getS6'
	 * 
	 * @return long
	 */
	public long getS6()
	{
		return s6;
	}

	/**
	 * Method 'setS6'
	 * 
	 * @param s6
	 */
	public void setS6(long s6)
	{
		this.s6 = s6;
		this.s6Null = false;
	}

	/**
	 * Method 'setS6Null'
	 * 
	 * @param value
	 */
	public void setS6Null(boolean value)
	{
		this.s6Null = value;
	}

	/**
	 * Method 'isS6Null'
	 * 
	 * @return boolean
	 */
	public boolean isS6Null()
	{
		return s6Null;
	}

	/**
	 * Method 'getS7'
	 * 
	 * @return long
	 */
	public long getS7()
	{
		return s7;
	}

	/**
	 * Method 'setS7'
	 * 
	 * @param s7
	 */
	public void setS7(long s7)
	{
		this.s7 = s7;
		this.s7Null = false;
	}

	/**
	 * Method 'setS7Null'
	 * 
	 * @param value
	 */
	public void setS7Null(boolean value)
	{
		this.s7Null = value;
	}

	/**
	 * Method 'isS7Null'
	 * 
	 * @return boolean
	 */
	public boolean isS7Null()
	{
		return s7Null;
	}

	/**
	 * Method 'getE1'
	 * 
	 * @return long
	 */
	public long getE1()
	{
		return e1;
	}

	/**
	 * Method 'setE1'
	 * 
	 * @param e1
	 */
	public void setE1(long e1)
	{
		this.e1 = e1;
		this.e1Null = false;
	}

	/**
	 * Method 'setE1Null'
	 * 
	 * @param value
	 */
	public void setE1Null(boolean value)
	{
		this.e1Null = value;
	}

	/**
	 * Method 'isE1Null'
	 * 
	 * @return boolean
	 */
	public boolean isE1Null()
	{
		return e1Null;
	}

	/**
	 * Method 'getE2'
	 * 
	 * @return long
	 */
	public long getE2()
	{
		return e2;
	}

	/**
	 * Method 'setE2'
	 * 
	 * @param e2
	 */
	public void setE2(long e2)
	{
		this.e2 = e2;
		this.e2Null = false;
	}

	/**
	 * Method 'setE2Null'
	 * 
	 * @param value
	 */
	public void setE2Null(boolean value)
	{
		this.e2Null = value;
	}

	/**
	 * Method 'isE2Null'
	 * 
	 * @return boolean
	 */
	public boolean isE2Null()
	{
		return e2Null;
	}

	/**
	 * Method 'getE3'
	 * 
	 * @return long
	 */
	public long getE3()
	{
		return e3;
	}

	/**
	 * Method 'setE3'
	 * 
	 * @param e3
	 */
	public void setE3(long e3)
	{
		this.e3 = e3;
		this.e3Null = false;
	}

	/**
	 * Method 'setE3Null'
	 * 
	 * @param value
	 */
	public void setE3Null(boolean value)
	{
		this.e3Null = value;
	}

	/**
	 * Method 'isE3Null'
	 * 
	 * @return boolean
	 */
	public boolean isE3Null()
	{
		return e3Null;
	}

	/**
	 * Method 'getE4'
	 * 
	 * @return long
	 */
	public long getE4()
	{
		return e4;
	}

	/**
	 * Method 'setE4'
	 * 
	 * @param e4
	 */
	public void setE4(long e4)
	{
		this.e4 = e4;
		this.e4Null = false;
	}

	/**
	 * Method 'setE4Null'
	 * 
	 * @param value
	 */
	public void setE4Null(boolean value)
	{
		this.e4Null = value;
	}

	/**
	 * Method 'isE4Null'
	 * 
	 * @return boolean
	 */
	public boolean isE4Null()
	{
		return e4Null;
	}

	/**
	 * Method 'getE5'
	 * 
	 * @return long
	 */
	public long getE5()
	{
		return e5;
	}

	/**
	 * Method 'setE5'
	 * 
	 * @param e5
	 */
	public void setE5(long e5)
	{
		this.e5 = e5;
		this.e5Null = false;
	}

	/**
	 * Method 'setE5Null'
	 * 
	 * @param value
	 */
	public void setE5Null(boolean value)
	{
		this.e5Null = value;
	}

	/**
	 * Method 'isE5Null'
	 * 
	 * @return boolean
	 */
	public boolean isE5Null()
	{
		return e5Null;
	}

	/**
	 * Method 'getE6'
	 * 
	 * @return long
	 */
	public long getE6()
	{
		return e6;
	}

	/**
	 * Method 'setE6'
	 * 
	 * @param e6
	 */
	public void setE6(long e6)
	{
		this.e6 = e6;
		this.e6Null = false;
	}

	/**
	 * Method 'setE6Null'
	 * 
	 * @param value
	 */
	public void setE6Null(boolean value)
	{
		this.e6Null = value;
	}

	/**
	 * Method 'isE6Null'
	 * 
	 * @return boolean
	 */
	public boolean isE6Null()
	{
		return e6Null;
	}

	/**
	 * Method 'getE7'
	 * 
	 * @return long
	 */
	public long getE7()
	{
		return e7;
	}

	/**
	 * Method 'setE7'
	 * 
	 * @param e7
	 */
	public void setE7(long e7)
	{
		this.e7 = e7;
		this.e7Null = false;
	}

	/**
	 * Method 'setE7Null'
	 * 
	 * @param value
	 */
	public void setE7Null(boolean value)
	{
		this.e7Null = value;
	}

	/**
	 * Method 'isE7Null'
	 * 
	 * @return boolean
	 */
	public boolean isE7Null()
	{
		return e7Null;
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
		
		if (!(_other instanceof TPolicyPeriod)) {
			return false;
		}
		
		final TPolicyPeriod _cast = (TPolicyPeriod) _other;
		if (ppid != _cast.ppid) {
			return false;
		}
		
		if (ppname == null ? _cast.ppname != ppname : !ppname.equals( _cast.ppname )) {
			return false;
		}
		
		if (startTime == null ? _cast.startTime != startTime : !startTime.equals( _cast.startTime )) {
			return false;
		}
		
		if (endTime == null ? _cast.endTime != endTime : !endTime.equals( _cast.endTime )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		if (workday == null ? _cast.workday != workday : !workday.equals( _cast.workday )) {
			return false;
		}
		
		if (enabled == null ? _cast.enabled != enabled : !enabled.equals( _cast.enabled )) {
			return false;
		}
		
		if (defaultflag == null ? _cast.defaultflag != defaultflag : !defaultflag.equals( _cast.defaultflag )) {
			return false;
		}
		
		if (s1 != _cast.s1) {
			return false;
		}
		
		if (s1Null != _cast.s1Null) {
			return false;
		}
		
		if (s2 != _cast.s2) {
			return false;
		}
		
		if (s2Null != _cast.s2Null) {
			return false;
		}
		
		if (s3 != _cast.s3) {
			return false;
		}
		
		if (s3Null != _cast.s3Null) {
			return false;
		}
		
		if (s4 != _cast.s4) {
			return false;
		}
		
		if (s4Null != _cast.s4Null) {
			return false;
		}
		
		if (s5 != _cast.s5) {
			return false;
		}
		
		if (s5Null != _cast.s5Null) {
			return false;
		}
		
		if (s6 != _cast.s6) {
			return false;
		}
		
		if (s6Null != _cast.s6Null) {
			return false;
		}
		
		if (s7 != _cast.s7) {
			return false;
		}
		
		if (s7Null != _cast.s7Null) {
			return false;
		}
		
		if (e1 != _cast.e1) {
			return false;
		}
		
		if (e1Null != _cast.e1Null) {
			return false;
		}
		
		if (e2 != _cast.e2) {
			return false;
		}
		
		if (e2Null != _cast.e2Null) {
			return false;
		}
		
		if (e3 != _cast.e3) {
			return false;
		}
		
		if (e3Null != _cast.e3Null) {
			return false;
		}
		
		if (e4 != _cast.e4) {
			return false;
		}
		
		if (e4Null != _cast.e4Null) {
			return false;
		}
		
		if (e5 != _cast.e5) {
			return false;
		}
		
		if (e5Null != _cast.e5Null) {
			return false;
		}
		
		if (e6 != _cast.e6) {
			return false;
		}
		
		if (e6Null != _cast.e6Null) {
			return false;
		}
		
		if (e7 != _cast.e7) {
			return false;
		}
		
		if (e7Null != _cast.e7Null) {
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
		_hashCode = 29 * _hashCode + (int) (ppid ^ (ppid >>> 32));
		if (ppname != null) {
			_hashCode = 29 * _hashCode + ppname.hashCode();
		}
		
		if (startTime != null) {
			_hashCode = 29 * _hashCode + startTime.hashCode();
		}
		
		if (endTime != null) {
			_hashCode = 29 * _hashCode + endTime.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		if (workday != null) {
			_hashCode = 29 * _hashCode + workday.hashCode();
		}
		
		if (enabled != null) {
			_hashCode = 29 * _hashCode + enabled.hashCode();
		}
		
		if (defaultflag != null) {
			_hashCode = 29 * _hashCode + defaultflag.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (s1 ^ (s1 >>> 32));
		_hashCode = 29 * _hashCode + (s1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (s2 ^ (s2 >>> 32));
		_hashCode = 29 * _hashCode + (s2Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (s3 ^ (s3 >>> 32));
		_hashCode = 29 * _hashCode + (s3Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (s4 ^ (s4 >>> 32));
		_hashCode = 29 * _hashCode + (s4Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (s5 ^ (s5 >>> 32));
		_hashCode = 29 * _hashCode + (s5Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (s6 ^ (s6 >>> 32));
		_hashCode = 29 * _hashCode + (s6Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (s7 ^ (s7 >>> 32));
		_hashCode = 29 * _hashCode + (s7Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (e1 ^ (e1 >>> 32));
		_hashCode = 29 * _hashCode + (e1Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (e2 ^ (e2 >>> 32));
		_hashCode = 29 * _hashCode + (e2Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (e3 ^ (e3 >>> 32));
		_hashCode = 29 * _hashCode + (e3Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (e4 ^ (e4 >>> 32));
		_hashCode = 29 * _hashCode + (e4Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (e5 ^ (e5 >>> 32));
		_hashCode = 29 * _hashCode + (e5Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (e6 ^ (e6 >>> 32));
		_hashCode = 29 * _hashCode + (e6Null ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (e7 ^ (e7 >>> 32));
		_hashCode = 29 * _hashCode + (e7Null ? 1 : 0);
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
	 * @return TPolicyPeriodPk
	 */
	public TPolicyPeriodPk createPk()
	{
		return new TPolicyPeriodPk(ppid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TPolicyPeriod: " );
		ret.append( "ppid=" + ppid );
		ret.append( ", ppname=" + ppname );
		ret.append( ", startTime=" + startTime );
		ret.append( ", endTime=" + endTime );
		ret.append( ", description=" + description );
		ret.append( ", workday=" + workday );
		ret.append( ", enabled=" + enabled );
		ret.append( ", defaultflag=" + defaultflag );
		ret.append( ", s1=" + s1 );
		ret.append( ", s2=" + s2 );
		ret.append( ", s3=" + s3 );
		ret.append( ", s4=" + s4 );
		ret.append( ", s5=" + s5 );
		ret.append( ", s6=" + s6 );
		ret.append( ", s7=" + s7 );
		ret.append( ", e1=" + e1 );
		ret.append( ", e2=" + e2 );
		ret.append( ", e3=" + e3 );
		ret.append( ", e4=" + e4 );
		ret.append( ", e5=" + e5 );
		ret.append( ", e6=" + e6 );
		ret.append( ", e7=" + e7 );
		ret.append( ", btime=" + btime );
		ret.append( ", etime=" + etime );
		return ret.toString();
	}

}
