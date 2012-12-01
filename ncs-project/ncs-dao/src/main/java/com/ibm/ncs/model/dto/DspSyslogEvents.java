package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DspSyslogEvents implements Serializable
{
	protected java.lang.String mark;

	protected java.lang.String varlist;

	protected java.lang.String btimelist;

	protected java.lang.String etimelist;

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

	protected java.lang.String port;

	protected long notcareflag;

	/** 
	 * This attribute represents whether the primitive attribute notcareflag is null.
	 */
	protected boolean notcareflagNull = true;

	protected long type;

	/** 
	 * This attribute represents whether the primitive attribute type is null.
	 */
	protected boolean typeNull = true;

	protected long eventtype;

	/** 
	 * This attribute represents whether the primitive attribute eventtype is null.
	 */
	protected boolean eventtypeNull = true;

	protected long subeventtype;

	/** 
	 * This attribute represents whether the primitive attribute subeventtype is null.
	 */
	protected boolean subeventtypeNull = true;

	protected java.lang.String alertgroup;

	protected java.lang.String alertkey;

	protected java.lang.String summarycn;

	protected java.lang.String processsuggest;

	protected java.lang.String status;

	protected long attentionflag;

	/** 
	 * This attribute represents whether the primitive attribute attentionflag is null.
	 */
	protected boolean attentionflagNull = true;

	protected java.lang.String events;

	protected java.lang.String manufacture;

	protected java.lang.String origevent;

	/**
	 * Method 'DspSyslogEvents'
	 * 
	 */
	public DspSyslogEvents()
	{
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
	 * Method 'getVarlist'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getVarlist()
	{
		return varlist;
	}

	/**
	 * Method 'setVarlist'
	 * 
	 * @param varlist
	 */
	public void setVarlist(java.lang.String varlist)
	{
		this.varlist = varlist;
	}

	/**
	 * Method 'getBtimelist'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getBtimelist()
	{
		return btimelist;
	}

	/**
	 * Method 'setBtimelist'
	 * 
	 * @param btimelist
	 */
	public void setBtimelist(java.lang.String btimelist)
	{
		this.btimelist = btimelist;
	}

	/**
	 * Method 'getEtimelist'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getEtimelist()
	{
		return etimelist;
	}

	/**
	 * Method 'setEtimelist'
	 * 
	 * @param etimelist
	 */
	public void setEtimelist(java.lang.String etimelist)
	{
		this.etimelist = etimelist;
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
	 * Method 'getPort'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPort()
	{
		return port;
	}

	/**
	 * Method 'setPort'
	 * 
	 * @param port
	 */
	public void setPort(java.lang.String port)
	{
		this.port = port;
	}

	/**
	 * Method 'getNotcareflag'
	 * 
	 * @return long
	 */
	public long getNotcareflag()
	{
		return notcareflag;
	}

	/**
	 * Method 'setNotcareflag'
	 * 
	 * @param notcareflag
	 */
	public void setNotcareflag(long notcareflag)
	{
		this.notcareflag = notcareflag;
	}

	/** 
	 * Sets the value of notcareflagNull
	 */
	public void setNotcareflagNull(boolean notcareflagNull)
	{
		this.notcareflagNull = notcareflagNull;
	}

	/** 
	 * Gets the value of notcareflagNull
	 */
	public boolean isNotcareflagNull()
	{
		return notcareflagNull;
	}

	/**
	 * Method 'getType'
	 * 
	 * @return long
	 */
	public long getType()
	{
		return type;
	}

	/**
	 * Method 'setType'
	 * 
	 * @param type
	 */
	public void setType(long type)
	{
		this.type = type;
	}

	/** 
	 * Sets the value of typeNull
	 */
	public void setTypeNull(boolean typeNull)
	{
		this.typeNull = typeNull;
	}

	/** 
	 * Gets the value of typeNull
	 */
	public boolean isTypeNull()
	{
		return typeNull;
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
	 * Method 'getSubeventtype'
	 * 
	 * @return long
	 */
	public long getSubeventtype()
	{
		return subeventtype;
	}

	/**
	 * Method 'setSubeventtype'
	 * 
	 * @param subeventtype
	 */
	public void setSubeventtype(long subeventtype)
	{
		this.subeventtype = subeventtype;
	}

	/** 
	 * Sets the value of subeventtypeNull
	 */
	public void setSubeventtypeNull(boolean subeventtypeNull)
	{
		this.subeventtypeNull = subeventtypeNull;
	}

	/** 
	 * Gets the value of subeventtypeNull
	 */
	public boolean isSubeventtypeNull()
	{
		return subeventtypeNull;
	}

	/**
	 * Method 'getAlertgroup'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAlertgroup()
	{
		return alertgroup;
	}

	/**
	 * Method 'setAlertgroup'
	 * 
	 * @param alertgroup
	 */
	public void setAlertgroup(java.lang.String alertgroup)
	{
		this.alertgroup = alertgroup;
	}

	/**
	 * Method 'getAlertkey'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAlertkey()
	{
		return alertkey;
	}

	/**
	 * Method 'setAlertkey'
	 * 
	 * @param alertkey
	 */
	public void setAlertkey(java.lang.String alertkey)
	{
		this.alertkey = alertkey;
	}

	/**
	 * Method 'getSummarycn'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getSummarycn()
	{
		return summarycn;
	}

	/**
	 * Method 'setSummarycn'
	 * 
	 * @param summarycn
	 */
	public void setSummarycn(java.lang.String summarycn)
	{
		this.summarycn = summarycn;
	}

	/**
	 * Method 'getProcesssuggest'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getProcesssuggest()
	{
		return processsuggest;
	}

	/**
	 * Method 'setProcesssuggest'
	 * 
	 * @param processsuggest
	 */
	public void setProcesssuggest(java.lang.String processsuggest)
	{
		this.processsuggest = processsuggest;
	}

	/**
	 * Method 'getStatus'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getStatus()
	{
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(java.lang.String status)
	{
		this.status = status;
	}

	/**
	 * Method 'getAttentionflag'
	 * 
	 * @return long
	 */
	public long getAttentionflag()
	{
		return attentionflag;
	}

	/**
	 * Method 'setAttentionflag'
	 * 
	 * @param attentionflag
	 */
	public void setAttentionflag(long attentionflag)
	{
		this.attentionflag = attentionflag;
	}

	/** 
	 * Sets the value of attentionflagNull
	 */
	public void setAttentionflagNull(boolean attentionflagNull)
	{
		this.attentionflagNull = attentionflagNull;
	}

	/** 
	 * Gets the value of attentionflagNull
	 */
	public boolean isAttentionflagNull()
	{
		return attentionflagNull;
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
	 * Method 'getOrigevent'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getOrigevent()
	{
		return origevent;
	}

	/**
	 * Method 'setOrigevent'
	 * 
	 * @param origevent
	 */
	public void setOrigevent(java.lang.String origevent)
	{
		this.origevent = origevent;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.DspSyslogEvents: " );
		ret.append( "mark=" + mark );
		ret.append( ", varlist=" + varlist );
		ret.append( ", btimelist=" + btimelist );
		ret.append( ", etimelist=" + etimelist );
		ret.append( ", filterflag1=" + filterflag1 );
		ret.append( ", filterflag2=" + filterflag2 );
		ret.append( ", severity1=" + severity1 );
		ret.append( ", severity2=" + severity2 );
		ret.append( ", port=" + port );
		ret.append( ", notcareflag=" + notcareflag );
		ret.append( ", type=" + type );
		ret.append( ", eventtype=" + eventtype );
		ret.append( ", subeventtype=" + subeventtype );
		ret.append( ", alertgroup=" + alertgroup );
		ret.append( ", alertkey=" + alertkey );
		ret.append( ", summarycn=" + summarycn );
		ret.append( ", processsuggest=" + processsuggest );
		ret.append( ", status=" + status );
		ret.append( ", attentionflag=" + attentionflag );
		ret.append( ", events=" + events );
		ret.append( ", manufacture=" + manufacture );
		ret.append( ", origevent=" + origevent );
		return ret.toString();
	}

}
