package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class TDeviceInfo implements Serializable
{
	/** 
	 * This attribute maps to the column DEVID in the T_DEVICE_INFO table.
	 */
	protected long devid;

	/** 
	 * This attribute maps to the column DEVIP in the T_DEVICE_INFO table.
	 */
	protected String devip;

	/** 
	 * This attribute maps to the column IPDECODE in the T_DEVICE_INFO table.
	 */
	protected long ipdecode;

	/** 
	 * This attribute maps to the column SYSNAME in the T_DEVICE_INFO table.
	 */
	protected String sysname;

	/** 
	 * This attribute maps to the column SYSNAMEALIAS in the T_DEVICE_INFO table.
	 */
	protected String sysnamealias;

	/** 
	 * This attribute maps to the column RSNO in the T_DEVICE_INFO table.
	 */
	protected String rsno;

	/** 
	 * This attribute maps to the column SRID in the T_DEVICE_INFO table.
	 */
	protected long srid;

	/** 
	 * This attribute represents whether the primitive attribute srid is null.
	 */
	protected boolean sridNull = true;

	/** 
	 * This attribute maps to the column ADMIN in the T_DEVICE_INFO table.
	 */
	protected String admin;

	/** 
	 * This attribute maps to the column PHONE in the T_DEVICE_INFO table.
	 */
	protected String phone;

	/** 
	 * This attribute maps to the column MRID in the T_DEVICE_INFO table.
	 */
	protected long mrid;

	/** 
	 * This attribute represents whether the primitive attribute mrid is null.
	 */
	protected boolean mridNull = true;

	/** 
	 * This attribute maps to the column DTID in the T_DEVICE_INFO table.
	 */
	protected long dtid;

	/** 
	 * This attribute represents whether the primitive attribute dtid is null.
	 */
	protected boolean dtidNull = true;

	/** 
	 * This attribute maps to the column SERIALID in the T_DEVICE_INFO table.
	 */
	protected String serialid;

	/** 
	 * This attribute maps to the column SWVERSION in the T_DEVICE_INFO table.
	 */
	protected String swversion;

	/** 
	 * This attribute maps to the column RAMSIZE in the T_DEVICE_INFO table.
	 */
	protected long ramsize;

	/** 
	 * This attribute represents whether the primitive attribute ramsize is null.
	 */
	protected boolean ramsizeNull = true;

	/** 
	 * This attribute maps to the column RAMUNIT in the T_DEVICE_INFO table.
	 */
	protected String ramunit;

	/** 
	 * This attribute maps to the column NVRAMSIZE in the T_DEVICE_INFO table.
	 */
	protected long nvramsize;

	/** 
	 * This attribute represents whether the primitive attribute nvramsize is null.
	 */
	protected boolean nvramsizeNull = true;

	/** 
	 * This attribute maps to the column NVRAMUNIT in the T_DEVICE_INFO table.
	 */
	protected String nvramunit;

	/** 
	 * This attribute maps to the column FLASHSIZE in the T_DEVICE_INFO table.
	 */
	protected long flashsize;

	/** 
	 * This attribute represents whether the primitive attribute flashsize is null.
	 */
	protected boolean flashsizeNull = true;

	/** 
	 * This attribute maps to the column FLASHUNIT in the T_DEVICE_INFO table.
	 */
	protected String flashunit;

	/** 
	 * This attribute maps to the column FLASHFILENAME in the T_DEVICE_INFO table.
	 */
	protected String flashfilename;

	/** 
	 * This attribute maps to the column FLASHFILESIZE in the T_DEVICE_INFO table.
	 */
	protected String flashfilesize;

	/** 
	 * This attribute maps to the column RCOMMUNITY in the T_DEVICE_INFO table.
	 */
	protected String rcommunity;

	/** 
	 * This attribute maps to the column WCOMMUNITY in the T_DEVICE_INFO table.
	 */
	protected String wcommunity;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_DEVICE_INFO table.
	 */
	protected String description;

	/** 
	 * This attribute maps to the column DOMAINID in the T_DEVICE_INFO table.
	 */
	protected long domainid;

	/** 
	 * This attribute represents whether the primitive attribute domainid is null.
	 */
	protected boolean domainidNull = true;

	/** 
	 * This attribute maps to the column SNMPVERSION in the T_DEVICE_INFO table.
	 */
	protected String snmpversion;

	/**
	 * Method 'TDeviceInfo'
	 * 
	 */
	public TDeviceInfo()
	{
	}

	/**
	 * Method 'getDevid'
	 * 
	 * @return long
	 */
	public long getDevid()
	{
		return devid;
	}

	/**
	 * Method 'setDevid'
	 * 
	 * @param devid
	 */
	public void setDevid(long devid)
	{
		this.devid = devid;
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
	 * Method 'getIpdecode'
	 * 
	 * @return long
	 */
	public long getIpdecode()
	{
		return ipdecode;
	}

	/**
	 * Method 'setIpdecode'
	 * 
	 * @param ipdecode
	 */
	public void setIpdecode(long ipdecode)
	{
		this.ipdecode = ipdecode;
	}

	/**
	 * Method 'getSysname'
	 * 
	 * @return String
	 */
	public String getSysname()
	{
		return sysname;
	}

	/**
	 * Method 'setSysname'
	 * 
	 * @param sysname
	 */
	public void setSysname(String sysname)
	{
		this.sysname = sysname;
	}

	/**
	 * Method 'getSysnamealias'
	 * 
	 * @return String
	 */
	public String getSysnamealias()
	{
		return sysnamealias;
	}

	/**
	 * Method 'setSysnamealias'
	 * 
	 * @param sysnamealias
	 */
	public void setSysnamealias(String sysnamealias)
	{
		this.sysnamealias = sysnamealias;
	}

	/**
	 * Method 'getRsno'
	 * 
	 * @return String
	 */
	public String getRsno()
	{
		return rsno;
	}

	/**
	 * Method 'setRsno'
	 * 
	 * @param rsno
	 */
	public void setRsno(String rsno)
	{
		this.rsno = rsno;
	}

	/**
	 * Method 'getSrid'
	 * 
	 * @return long
	 */
	public long getSrid()
	{
		return srid;
	}

	/**
	 * Method 'setSrid'
	 * 
	 * @param srid
	 */
	public void setSrid(long srid)
	{
		this.srid = srid;
		this.sridNull = false;
	}

	/**
	 * Method 'setSridNull'
	 * 
	 * @param value
	 */
	public void setSridNull(boolean value)
	{
		this.sridNull = value;
	}

	/**
	 * Method 'isSridNull'
	 * 
	 * @return boolean
	 */
	public boolean isSridNull()
	{
		return sridNull;
	}

	/**
	 * Method 'getAdmin'
	 * 
	 * @return String
	 */
	public String getAdmin()
	{
		return admin;
	}

	/**
	 * Method 'setAdmin'
	 * 
	 * @param admin
	 */
	public void setAdmin(String admin)
	{
		this.admin = admin;
	}

	/**
	 * Method 'getPhone'
	 * 
	 * @return String
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * Method 'setPhone'
	 * 
	 * @param phone
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * Method 'getMrid'
	 * 
	 * @return long
	 */
	public long getMrid()
	{
		return mrid;
	}

	/**
	 * Method 'setMrid'
	 * 
	 * @param mrid
	 */
	public void setMrid(long mrid)
	{
		this.mrid = mrid;
		this.mridNull = false;
	}

	/**
	 * Method 'setMridNull'
	 * 
	 * @param value
	 */
	public void setMridNull(boolean value)
	{
		this.mridNull = value;
	}

	/**
	 * Method 'isMridNull'
	 * 
	 * @return boolean
	 */
	public boolean isMridNull()
	{
		return mridNull;
	}

	/**
	 * Method 'getDtid'
	 * 
	 * @return long
	 */
	public long getDtid()
	{
		return dtid;
	}

	/**
	 * Method 'setDtid'
	 * 
	 * @param dtid
	 */
	public void setDtid(long dtid)
	{
		this.dtid = dtid;
		this.dtidNull = false;
	}

	/**
	 * Method 'setDtidNull'
	 * 
	 * @param value
	 */
	public void setDtidNull(boolean value)
	{
		this.dtidNull = value;
	}

	/**
	 * Method 'isDtidNull'
	 * 
	 * @return boolean
	 */
	public boolean isDtidNull()
	{
		return dtidNull;
	}

	/**
	 * Method 'getSerialid'
	 * 
	 * @return String
	 */
	public String getSerialid()
	{
		return serialid;
	}

	/**
	 * Method 'setSerialid'
	 * 
	 * @param serialid
	 */
	public void setSerialid(String serialid)
	{
		this.serialid = serialid;
	}

	/**
	 * Method 'getSwversion'
	 * 
	 * @return String
	 */
	public String getSwversion()
	{
		return swversion;
	}

	/**
	 * Method 'setSwversion'
	 * 
	 * @param swversion
	 */
	public void setSwversion(String swversion)
	{
		this.swversion = swversion;
	}

	/**
	 * Method 'getRamsize'
	 * 
	 * @return long
	 */
	public long getRamsize()
	{
		return ramsize;
	}

	/**
	 * Method 'setRamsize'
	 * 
	 * @param ramsize
	 */
	public void setRamsize(long ramsize)
	{
		this.ramsize = ramsize;
		this.ramsizeNull = false;
	}

	/**
	 * Method 'setRamsizeNull'
	 * 
	 * @param value
	 */
	public void setRamsizeNull(boolean value)
	{
		this.ramsizeNull = value;
	}

	/**
	 * Method 'isRamsizeNull'
	 * 
	 * @return boolean
	 */
	public boolean isRamsizeNull()
	{
		return ramsizeNull;
	}

	/**
	 * Method 'getRamunit'
	 * 
	 * @return String
	 */
	public String getRamunit()
	{
		return ramunit;
	}

	/**
	 * Method 'setRamunit'
	 * 
	 * @param ramunit
	 */
	public void setRamunit(String ramunit)
	{
		this.ramunit = ramunit;
	}

	/**
	 * Method 'getNvramsize'
	 * 
	 * @return long
	 */
	public long getNvramsize()
	{
		return nvramsize;
	}

	/**
	 * Method 'setNvramsize'
	 * 
	 * @param nvramsize
	 */
	public void setNvramsize(long nvramsize)
	{
		this.nvramsize = nvramsize;
		this.nvramsizeNull = false;
	}

	/**
	 * Method 'setNvramsizeNull'
	 * 
	 * @param value
	 */
	public void setNvramsizeNull(boolean value)
	{
		this.nvramsizeNull = value;
	}

	/**
	 * Method 'isNvramsizeNull'
	 * 
	 * @return boolean
	 */
	public boolean isNvramsizeNull()
	{
		return nvramsizeNull;
	}

	/**
	 * Method 'getNvramunit'
	 * 
	 * @return String
	 */
	public String getNvramunit()
	{
		return nvramunit;
	}

	/**
	 * Method 'setNvramunit'
	 * 
	 * @param nvramunit
	 */
	public void setNvramunit(String nvramunit)
	{
		this.nvramunit = nvramunit;
	}

	/**
	 * Method 'getFlashsize'
	 * 
	 * @return long
	 */
	public long getFlashsize()
	{
		return flashsize;
	}

	/**
	 * Method 'setFlashsize'
	 * 
	 * @param flashsize
	 */
	public void setFlashsize(long flashsize)
	{
		this.flashsize = flashsize;
		this.flashsizeNull = false;
	}

	/**
	 * Method 'setFlashsizeNull'
	 * 
	 * @param value
	 */
	public void setFlashsizeNull(boolean value)
	{
		this.flashsizeNull = value;
	}

	/**
	 * Method 'isFlashsizeNull'
	 * 
	 * @return boolean
	 */
	public boolean isFlashsizeNull()
	{
		return flashsizeNull;
	}

	/**
	 * Method 'getFlashunit'
	 * 
	 * @return String
	 */
	public String getFlashunit()
	{
		return flashunit;
	}

	/**
	 * Method 'setFlashunit'
	 * 
	 * @param flashunit
	 */
	public void setFlashunit(String flashunit)
	{
		this.flashunit = flashunit;
	}

	/**
	 * Method 'getFlashfilename'
	 * 
	 * @return String
	 */
	public String getFlashfilename()
	{
		return flashfilename;
	}

	/**
	 * Method 'setFlashfilename'
	 * 
	 * @param flashfilename
	 */
	public void setFlashfilename(String flashfilename)
	{
		this.flashfilename = flashfilename;
	}

	/**
	 * Method 'getFlashfilesize'
	 * 
	 * @return String
	 */
	public String getFlashfilesize()
	{
		return flashfilesize;
	}

	/**
	 * Method 'setFlashfilesize'
	 * 
	 * @param flashfilesize
	 */
	public void setFlashfilesize(String flashfilesize)
	{
		this.flashfilesize = flashfilesize;
	}

	/**
	 * Method 'getRcommunity'
	 * 
	 * @return String
	 */
	public String getRcommunity()
	{
		return rcommunity;
	}

	/**
	 * Method 'setRcommunity'
	 * 
	 * @param rcommunity
	 */
	public void setRcommunity(String rcommunity)
	{
		this.rcommunity = rcommunity;
	}

	/**
	 * Method 'getWcommunity'
	 * 
	 * @return String
	 */
	public String getWcommunity()
	{
		return wcommunity;
	}

	/**
	 * Method 'setWcommunity'
	 * 
	 * @param wcommunity
	 */
	public void setWcommunity(String wcommunity)
	{
		this.wcommunity = wcommunity;
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
	 * Method 'getDomainid'
	 * 
	 * @return long
	 */
	public long getDomainid()
	{
		return domainid;
	}

	/**
	 * Method 'setDomainid'
	 * 
	 * @param domainid
	 */
	public void setDomainid(long domainid)
	{
		this.domainid = domainid;
		this.domainidNull = false;
	}

	/**
	 * Method 'setDomainidNull'
	 * 
	 * @param value
	 */
	public void setDomainidNull(boolean value)
	{
		this.domainidNull = value;
	}

	/**
	 * Method 'isDomainidNull'
	 * 
	 * @return boolean
	 */
	public boolean isDomainidNull()
	{
		return domainidNull;
	}

	/**
	 * Method 'getSnmpversion'
	 * 
	 * @return String
	 */
	public String getSnmpversion()
	{
		return snmpversion;
	}

	/**
	 * Method 'setSnmpversion'
	 * 
	 * @param snmpversion
	 */
	public void setSnmpversion(String snmpversion)
	{
		this.snmpversion = snmpversion;
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
		
		if (!(_other instanceof TDeviceInfo)) {
			return false;
		}
		
		final TDeviceInfo _cast = (TDeviceInfo) _other;
		if (devid != _cast.devid) {
			return false;
		}
		
		if (devip == null ? _cast.devip != devip : !devip.equals( _cast.devip )) {
			return false;
		}
		
		if (ipdecode != _cast.ipdecode) {
			return false;
		}
		
		if (sysname == null ? _cast.sysname != sysname : !sysname.equals( _cast.sysname )) {
			return false;
		}
		
		if (sysnamealias == null ? _cast.sysnamealias != sysnamealias : !sysnamealias.equals( _cast.sysnamealias )) {
			return false;
		}
		
		if (rsno == null ? _cast.rsno != rsno : !rsno.equals( _cast.rsno )) {
			return false;
		}
		
		if (srid != _cast.srid) {
			return false;
		}
		
		if (sridNull != _cast.sridNull) {
			return false;
		}
		
		if (admin == null ? _cast.admin != admin : !admin.equals( _cast.admin )) {
			return false;
		}
		
		if (phone == null ? _cast.phone != phone : !phone.equals( _cast.phone )) {
			return false;
		}
		
		if (mrid != _cast.mrid) {
			return false;
		}
		
		if (mridNull != _cast.mridNull) {
			return false;
		}
		
		if (dtid != _cast.dtid) {
			return false;
		}
		
		if (dtidNull != _cast.dtidNull) {
			return false;
		}
		
		if (serialid == null ? _cast.serialid != serialid : !serialid.equals( _cast.serialid )) {
			return false;
		}
		
		if (swversion == null ? _cast.swversion != swversion : !swversion.equals( _cast.swversion )) {
			return false;
		}
		
		if (ramsize != _cast.ramsize) {
			return false;
		}
		
		if (ramsizeNull != _cast.ramsizeNull) {
			return false;
		}
		
		if (ramunit == null ? _cast.ramunit != ramunit : !ramunit.equals( _cast.ramunit )) {
			return false;
		}
		
		if (nvramsize != _cast.nvramsize) {
			return false;
		}
		
		if (nvramsizeNull != _cast.nvramsizeNull) {
			return false;
		}
		
		if (nvramunit == null ? _cast.nvramunit != nvramunit : !nvramunit.equals( _cast.nvramunit )) {
			return false;
		}
		
		if (flashsize != _cast.flashsize) {
			return false;
		}
		
		if (flashsizeNull != _cast.flashsizeNull) {
			return false;
		}
		
		if (flashunit == null ? _cast.flashunit != flashunit : !flashunit.equals( _cast.flashunit )) {
			return false;
		}
		
		if (flashfilename == null ? _cast.flashfilename != flashfilename : !flashfilename.equals( _cast.flashfilename )) {
			return false;
		}
		
		if (flashfilesize == null ? _cast.flashfilesize != flashfilesize : !flashfilesize.equals( _cast.flashfilesize )) {
			return false;
		}
		
		if (rcommunity == null ? _cast.rcommunity != rcommunity : !rcommunity.equals( _cast.rcommunity )) {
			return false;
		}
		
		if (wcommunity == null ? _cast.wcommunity != wcommunity : !wcommunity.equals( _cast.wcommunity )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		if (domainid != _cast.domainid) {
			return false;
		}
		
		if (domainidNull != _cast.domainidNull) {
			return false;
		}
		
		if (snmpversion == null ? _cast.snmpversion != snmpversion : !snmpversion.equals( _cast.snmpversion )) {
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
		_hashCode = 29 * _hashCode + (int) (devid ^ (devid >>> 32));
		if (devip != null) {
			_hashCode = 29 * _hashCode + devip.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (ipdecode ^ (ipdecode >>> 32));
		if (sysname != null) {
			_hashCode = 29 * _hashCode + sysname.hashCode();
		}
		
		if (sysnamealias != null) {
			_hashCode = 29 * _hashCode + sysnamealias.hashCode();
		}
		
		if (rsno != null) {
			_hashCode = 29 * _hashCode + rsno.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (srid ^ (srid >>> 32));
		_hashCode = 29 * _hashCode + (sridNull ? 1 : 0);
		if (admin != null) {
			_hashCode = 29 * _hashCode + admin.hashCode();
		}
		
		if (phone != null) {
			_hashCode = 29 * _hashCode + phone.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (mrid ^ (mrid >>> 32));
		_hashCode = 29 * _hashCode + (mridNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (dtid ^ (dtid >>> 32));
		_hashCode = 29 * _hashCode + (dtidNull ? 1 : 0);
		if (serialid != null) {
			_hashCode = 29 * _hashCode + serialid.hashCode();
		}
		
		if (swversion != null) {
			_hashCode = 29 * _hashCode + swversion.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (ramsize ^ (ramsize >>> 32));
		_hashCode = 29 * _hashCode + (ramsizeNull ? 1 : 0);
		if (ramunit != null) {
			_hashCode = 29 * _hashCode + ramunit.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (nvramsize ^ (nvramsize >>> 32));
		_hashCode = 29 * _hashCode + (nvramsizeNull ? 1 : 0);
		if (nvramunit != null) {
			_hashCode = 29 * _hashCode + nvramunit.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (flashsize ^ (flashsize >>> 32));
		_hashCode = 29 * _hashCode + (flashsizeNull ? 1 : 0);
		if (flashunit != null) {
			_hashCode = 29 * _hashCode + flashunit.hashCode();
		}
		
		if (flashfilename != null) {
			_hashCode = 29 * _hashCode + flashfilename.hashCode();
		}
		
		if (flashfilesize != null) {
			_hashCode = 29 * _hashCode + flashfilesize.hashCode();
		}
		
		if (rcommunity != null) {
			_hashCode = 29 * _hashCode + rcommunity.hashCode();
		}
		
		if (wcommunity != null) {
			_hashCode = 29 * _hashCode + wcommunity.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (domainid ^ (domainid >>> 32));
		_hashCode = 29 * _hashCode + (domainidNull ? 1 : 0);
		if (snmpversion != null) {
			_hashCode = 29 * _hashCode + snmpversion.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TDeviceInfoPk
	 */
	public TDeviceInfoPk createPk()
	{
		return new TDeviceInfoPk(devid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TDeviceInfo: " );
		ret.append( "devid=" + devid );
		ret.append( ", devip=" + devip );
		ret.append( ", ipdecode=" + ipdecode );
		ret.append( ", sysname=" + sysname );
		ret.append( ", sysnamealias=" + sysnamealias );
		ret.append( ", rsno=" + rsno );
		ret.append( ", srid=" + srid );
		ret.append( ", admin=" + admin );
		ret.append( ", phone=" + phone );
		ret.append( ", mrid=" + mrid );
		ret.append( ", dtid=" + dtid );
		ret.append( ", serialid=" + serialid );
		ret.append( ", swversion=" + swversion );
		ret.append( ", ramsize=" + ramsize );
		ret.append( ", ramunit=" + ramunit );
		ret.append( ", nvramsize=" + nvramsize );
		ret.append( ", nvramunit=" + nvramunit );
		ret.append( ", flashsize=" + flashsize );
		ret.append( ", flashunit=" + flashunit );
		ret.append( ", flashfilename=" + flashfilename );
		ret.append( ", flashfilesize=" + flashfilesize );
		ret.append( ", rcommunity=" + rcommunity );
		ret.append( ", wcommunity=" + wcommunity );
		ret.append( ", description=" + description );
		ret.append( ", domainid=" + domainid );
		ret.append( ", snmpversion=" + snmpversion );
		return ret.toString();
	}

}
