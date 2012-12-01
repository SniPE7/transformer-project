package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DspEventsFromPolicySyslog implements Serializable
{
	protected java.lang.String events;

	protected long spid;

	/** 
	 * This attribute represents whether the primitive attribute spid is null.
	 */
	protected boolean spidNull = true;

	protected long mpid;

	/** 
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean mpidNull = true;

	protected java.lang.String mark;

	protected java.lang.String manufacture;

	protected long eventtype;

	/** 
	 * This attribute represents whether the primitive attribute eventtype is null.
	 */
	protected boolean eventtypeNull = true;

	protected long severity1;

	/** 
	 * This attribute represents whether the primitive attribute severity1 is null.
	 */
	protected boolean severity1Null = true;

	protected long severity2;

	/** 
	 * This attribute represents whether the primitive attribute severity2 is null.
	 */
	protected boolean severity2Null = true;

	protected long filterflag1;

	/** 
	 * This attribute represents whether the primitive attribute filterflag1 is null.
	 */
	protected boolean filterflag1Null = true;

	protected long filterflag2;

	/** 
	 * This attribute represents whether the primitive attribute filterflag2 is null.
	 */
	protected boolean filterflag2Null = true;

	/**
	 * Method 'DspEventsFromPolicySyslog'
	 * 
	 */
	public DspEventsFromPolicySyslog()
	{
	}

	/**
	 * Method 'getEvents'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getEvents()
	{
		return events;
	}

	/**
	 * Method 'setEvents'
	 * 
	 * @param events
	 */
	public void setEvents(java.lang.String events)
	{
		this.events = events;
	}

	/**
	 * Method 'getSpid'
	 * 
	 * @return long
	 */
	public long getSpid()
	{
		return spid;
	}

	/**
	 * Method 'setSpid'
	 * 
	 * @param spid
	 */
	public void setSpid(long spid)
	{
		this.spid = spid;
	}

	/** 
	 * Sets the value of spidNull
	 */
	public void setSpidNull(boolean spidNull)
	{
		this.spidNull = spidNull;
	}

	/** 
	 * Gets the value of spidNull
	 */
	public boolean isSpidNull()
	{
		return spidNull;
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
		this.mpidNull =false;
	}

	/** 
	 * Sets the value of mpidNull
	 */
	public void setMpidNull(boolean mpidNull)
	{
		this.mpidNull = mpidNull;
	}

	/** 
	 * Gets the value of mpidNull
	 */
	public boolean isMpidNull()
	{
		return mpidNull;
	}

	/**
	 * Method 'getMark'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getMark()
	{
		return mark;
	}

	/**
	 * Method 'setMark'
	 * 
	 * @param mark
	 */
	public void setMark(java.lang.String mark)
	{
		this.mark = mark;
	}

	/**
	 * Method 'getManufacture'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getManufacture()
	{
		return manufacture;
	}

	/**
	 * Method 'setManufacture'
	 * 
	 * @param manufacture
	 */
	public void setManufacture(java.lang.String manufacture)
	{
		this.manufacture = manufacture;
	}

	/**
	 * Method 'getEventtype'
	 * 
	 * @return long
	 */
	public long getEventtype()
	{
		return eventtype;
	}

	/**
	 * Method 'setEventtype'
	 * 
	 * @param eventtype
	 */
	public void setEventtype(long eventtype)
	{
		this.eventtype = eventtype;
		this.eventtypeNull = false;
	}

	/** 
	 * Sets the value of eventtypeNull
	 */
	public void setEventtypeNull(boolean eventtypeNull)
	{
		this.eventtypeNull = eventtypeNull;
	}

	/** 
	 * Gets the value of eventtypeNull
	 */
	public boolean isEventtypeNull()
	{
		return eventtypeNull;
	}

	/**
	 * Method 'getSeverity1'
	 * 
	 * @return long
	 */
	public long getSeverity1()
	{
		return severity1;
	}

	/**
	 * Method 'setSeverity1'
	 * 
	 * @param severity1
	 */
	public void setSeverity1(long severity1)
	{
		this.severity1 = severity1;
		this.severity1Null=false;
	}

	/** 
	 * Sets the value of severity1Null
	 */
	public void setSeverity1Null(boolean severity1Null)
	{
		this.severity1Null = severity1Null;
	}

	/** 
	 * Gets the value of severity1Null
	 */
	public boolean isSeverity1Null()
	{
		return severity1Null;
	}

	/**
	 * Method 'getSeverity2'
	 * 
	 * @return long
	 */
	public long getSeverity2()
	{
		return severity2;
	}

	/**
	 * Method 'setSeverity2'
	 * 
	 * @param severity2
	 */
	public void setSeverity2(long severity2)
	{
		this.severity2 = severity2;
		this.severity2Null =false;
	}

	/** 
	 * Sets the value of severity2Null
	 */
	public void setSeverity2Null(boolean severity2Null)
	{
		this.severity2Null = severity2Null;
	}

	/** 
	 * Gets the value of severity2Null
	 */
	public boolean isSeverity2Null()
	{
		return severity2Null;
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
	 * Sets the value of filterflag1Null
	 */
	public void setFilterflag1Null(boolean filterflag1Null)
	{
		this.filterflag1Null = filterflag1Null;
	}

	/** 
	 * Gets the value of filterflag1Null
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
		this.filterflag2Null =false;
	}

	/** 
	 * Sets the value of filterflag2Null
	 */
	public void setFilterflag2Null(boolean filterflag2Null)
	{
		this.filterflag2Null = filterflag2Null;
	}

	/** 
	 * Gets the value of filterflag2Null
	 */
	public boolean isFilterflag2Null()
	{
		return filterflag2Null;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.DspEventsFromPolicySyslog: " );
		ret.append( "events=" + events );
		ret.append( ", spid=" + spid );
		ret.append( ", mpid=" + mpid );
		ret.append( ", mark=" + mark );
		ret.append( ", manufacture=" + manufacture );
		ret.append( ", eventtype=" + eventtype );
		ret.append( ", severity1=" + severity1 );
		ret.append( ", severity2=" + severity2 );
		ret.append( ", filterflag1=" + filterflag1 );
		ret.append( ", filterflag2=" + filterflag2 );
		return ret.toString();
	}

}
