package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ModuleEventType implements Serializable
{
	protected long modid;

	/** 
	 * This attribute represents whether the primitive attribute modid is null.
	 */
	protected boolean modidNull = true;

	protected java.lang.String mname;

	protected long mcode;

	/** 
	 * This attribute represents whether the primitive attribute mcode is null.
	 */
	protected boolean mcodeNull = true;

	protected long eveid;

	/** 
	 * This attribute represents whether the primitive attribute eveid is null.
	 */
	protected boolean eveidNull = true;

	protected long etid;

	/** 
	 * This attribute represents whether the primitive attribute etid is null.
	 */
	protected boolean etidNull = true;

	protected long estid;

	/** 
	 * This attribute represents whether the primitive attribute estid is null.
	 */
	protected boolean estidNull = true;

	protected java.lang.String eveothername;

	protected long ecode;

	/** 
	 * This attribute represents whether the primitive attribute ecode is null.
	 */
	protected boolean ecodeNull = true;

	protected long general;

	/** 
	 * This attribute represents whether the primitive attribute general is null.
	 */
	protected boolean generalNull = true;

	protected java.lang.String major;

	protected java.lang.String minor;

	protected java.lang.String other;

	protected java.lang.String description;

	protected java.lang.String useflag;

	/**
	 * Method 'ModuleEventType'
	 * 
	 */
	public ModuleEventType()
	{
	}

	/**
	 * Method 'getModid'
	 * 
	 * @return long
	 */
	public long getModid()
	{
		return modid;
	}

	/**
	 * Method 'setModid'
	 * 
	 * @param modid
	 */
	public void setModid(long modid)
	{
		this.modid = modid;
	}

	/** 
	 * Sets the value of modidNull
	 */
	public void setModidNull(boolean modidNull)
	{
		this.modidNull = modidNull;
	}

	/** 
	 * Gets the value of modidNull
	 */
	public boolean isModidNull()
	{
		return modidNull;
	}

	/**
	 * Method 'getMname'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getMname()
	{
		return mname;
	}

	/**
	 * Method 'setMname'
	 * 
	 * @param mname
	 */
	public void setMname(java.lang.String mname)
	{
		this.mname = mname;
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
	}

	/** 
	 * Sets the value of mcodeNull
	 */
	public void setMcodeNull(boolean mcodeNull)
	{
		this.mcodeNull = mcodeNull;
	}

	/** 
	 * Gets the value of mcodeNull
	 */
	public boolean isMcodeNull()
	{
		return mcodeNull;
	}

	/**
	 * Method 'getEveid'
	 * 
	 * @return long
	 */
	public long getEveid()
	{
		return eveid;
	}

	/**
	 * Method 'setEveid'
	 * 
	 * @param eveid
	 */
	public void setEveid(long eveid)
	{
		this.eveid = eveid;
	}

	/** 
	 * Sets the value of eveidNull
	 */
	public void setEveidNull(boolean eveidNull)
	{
		this.eveidNull = eveidNull;
	}

	/** 
	 * Gets the value of eveidNull
	 */
	public boolean isEveidNull()
	{
		return eveidNull;
	}

	/**
	 * Method 'getEtid'
	 * 
	 * @return long
	 */
	public long getEtid()
	{
		return etid;
	}

	/**
	 * Method 'setEtid'
	 * 
	 * @param etid
	 */
	public void setEtid(long etid)
	{
		this.etid = etid;
	}

	/** 
	 * Sets the value of etidNull
	 */
	public void setEtidNull(boolean etidNull)
	{
		this.etidNull = etidNull;
	}

	/** 
	 * Gets the value of etidNull
	 */
	public boolean isEtidNull()
	{
		return etidNull;
	}

	/**
	 * Method 'getEstid'
	 * 
	 * @return long
	 */
	public long getEstid()
	{
		return estid;
	}

	/**
	 * Method 'setEstid'
	 * 
	 * @param estid
	 */
	public void setEstid(long estid)
	{
		this.estid = estid;
	}

	/** 
	 * Sets the value of estidNull
	 */
	public void setEstidNull(boolean estidNull)
	{
		this.estidNull = estidNull;
	}

	/** 
	 * Gets the value of estidNull
	 */
	public boolean isEstidNull()
	{
		return estidNull;
	}

	/**
	 * Method 'getEveothername'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getEveothername()
	{
		return eveothername;
	}

	/**
	 * Method 'setEveothername'
	 * 
	 * @param eveothername
	 */
	public void setEveothername(java.lang.String eveothername)
	{
		this.eveothername = eveothername;
	}

	/**
	 * Method 'getEcode'
	 * 
	 * @return long
	 */
	public long getEcode()
	{
		return ecode;
	}

	/**
	 * Method 'setEcode'
	 * 
	 * @param ecode
	 */
	public void setEcode(long ecode)
	{
		this.ecode = ecode;
	}

	/** 
	 * Sets the value of ecodeNull
	 */
	public void setEcodeNull(boolean ecodeNull)
	{
		this.ecodeNull = ecodeNull;
	}

	/** 
	 * Gets the value of ecodeNull
	 */
	public boolean isEcodeNull()
	{
		return ecodeNull;
	}

	/**
	 * Method 'getGeneral'
	 * 
	 * @return long
	 */
	public long getGeneral()
	{
		return general;
	}

	/**
	 * Method 'setGeneral'
	 * 
	 * @param general
	 */
	public void setGeneral(long general)
	{
		this.general = general;
	}

	/** 
	 * Sets the value of generalNull
	 */
	public void setGeneralNull(boolean generalNull)
	{
		this.generalNull = generalNull;
	}

	/** 
	 * Gets the value of generalNull
	 */
	public boolean isGeneralNull()
	{
		return generalNull;
	}

	/**
	 * Method 'getMajor'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getMajor()
	{
		return major;
	}

	/**
	 * Method 'setMajor'
	 * 
	 * @param major
	 */
	public void setMajor(java.lang.String major)
	{
		this.major = major;
	}

	/**
	 * Method 'getMinor'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getMinor()
	{
		return minor;
	}

	/**
	 * Method 'setMinor'
	 * 
	 * @param minor
	 */
	public void setMinor(java.lang.String minor)
	{
		this.minor = minor;
	}

	/**
	 * Method 'getOther'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getOther()
	{
		return other;
	}

	/**
	 * Method 'setOther'
	 * 
	 * @param other
	 */
	public void setOther(java.lang.String other)
	{
		this.other = other;
	}

	/**
	 * Method 'getDescription'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDescription()
	{
		return description;
	}

	/**
	 * Method 'setDescription'
	 * 
	 * @param description
	 */
	public void setDescription(java.lang.String description)
	{
		this.description = description;
	}

	/**
	 * Method 'getUseflag'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getUseflag()
	{
		return useflag;
	}

	/**
	 * Method 'setUseflag'
	 * 
	 * @param useflag
	 */
	public void setUseflag(java.lang.String useflag)
	{
		this.useflag = useflag;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.ModuleEventType: " );
		ret.append( "modid=" + modid );
		ret.append( ", mname=" + mname );
		ret.append( ", mcode=" + mcode );
		ret.append( ", eveid=" + eveid );
		ret.append( ", etid=" + etid );
		ret.append( ", estid=" + estid );
		ret.append( ", eveothername=" + eveothername );
		ret.append( ", ecode=" + ecode );
		ret.append( ", general=" + general );
		ret.append( ", major=" + major );
		ret.append( ", minor=" + minor );
		ret.append( ", other=" + other );
		ret.append( ", description=" + description );
		ret.append( ", useflag=" + useflag );
		return ret.toString();
	}

}
