package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class TLineInfo implements Serializable
{
	/** 
	 * This attribute maps to the column LEID in the T_LINE_INFO table.
	 */
	protected long leid;

	/** 
	 * This attribute maps to the column LINENAME in the T_LINE_INFO table.
	 */
	protected String linename;

	/** 
	 * This attribute maps to the column LENO in the T_LINE_INFO table.
	 */
	protected String leno;

	/** 
	 * This attribute maps to the column CATEGORY in the T_LINE_INFO table.
	 */
	protected long category;

	/** 
	 * This attribute represents whether the primitive attribute category is null.
	 */
	protected boolean categoryNull = true;

	/** 
	 * This attribute maps to the column BANDWIDTH in the T_LINE_INFO table.
	 */
	protected long bandwidth;

	/** 
	 * This attribute represents whether the primitive attribute bandwidth is null.
	 */
	protected boolean bandwidthNull = true;

	/** 
	 * This attribute maps to the column BANDWIDTHUNIT in the T_LINE_INFO table.
	 */
	protected String bandwidthunit;

	/** 
	 * This attribute maps to the column APPLYTIME in the T_LINE_INFO table.
	 */
	protected Date applytime;

	/** 
	 * This attribute maps to the column OPENTIME in the T_LINE_INFO table.
	 */
	protected Date opentime;

	/** 
	 * This attribute maps to the column DESCRIPTION in the T_LINE_INFO table.
	 */
	protected String description;

	/**
	 * Method 'TLineInfo'
	 * 
	 */
	public TLineInfo()
	{
	}

	/**
	 * Method 'getLeid'
	 * 
	 * @return long
	 */
	public long getLeid()
	{
		return leid;
	}

	/**
	 * Method 'setLeid'
	 * 
	 * @param leid
	 */
	public void setLeid(long leid)
	{
		this.leid = leid;
	}

	/**
	 * Method 'getLinename'
	 * 
	 * @return String
	 */
	public String getLinename()
	{
		return linename;
	}

	/**
	 * Method 'setLinename'
	 * 
	 * @param linename
	 */
	public void setLinename(String linename)
	{
		this.linename = linename;
	}

	/**
	 * Method 'getLeno'
	 * 
	 * @return String
	 */
	public String getLeno()
	{
		return leno;
	}

	/**
	 * Method 'setLeno'
	 * 
	 * @param leno
	 */
	public void setLeno(String leno)
	{
		this.leno = leno;
	}

	/**
	 * Method 'getCategory'
	 * 
	 * @return long
	 */
	public long getCategory()
	{
		return category;
	}

	/**
	 * Method 'setCategory'
	 * 
	 * @param category
	 */
	public void setCategory(long category)
	{
		this.category = category;
		this.categoryNull = false;
	}

	/**
	 * Method 'setCategoryNull'
	 * 
	 * @param value
	 */
	public void setCategoryNull(boolean value)
	{
		this.categoryNull = value;
	}

	/**
	 * Method 'isCategoryNull'
	 * 
	 * @return boolean
	 */
	public boolean isCategoryNull()
	{
		return categoryNull;
	}

	/**
	 * Method 'getBandwidth'
	 * 
	 * @return long
	 */
	public long getBandwidth()
	{
		return bandwidth;
	}

	/**
	 * Method 'setBandwidth'
	 * 
	 * @param bandwidth
	 */
	public void setBandwidth(long bandwidth)
	{
		this.bandwidth = bandwidth;
		this.bandwidthNull = false;
	}

	/**
	 * Method 'setBandwidthNull'
	 * 
	 * @param value
	 */
	public void setBandwidthNull(boolean value)
	{
		this.bandwidthNull = value;
	}

	/**
	 * Method 'isBandwidthNull'
	 * 
	 * @return boolean
	 */
	public boolean isBandwidthNull()
	{
		return bandwidthNull;
	}

	/**
	 * Method 'getBandwidthunit'
	 * 
	 * @return String
	 */
	public String getBandwidthunit()
	{
		return bandwidthunit;
	}

	/**
	 * Method 'setBandwidthunit'
	 * 
	 * @param bandwidthunit
	 */
	public void setBandwidthunit(String bandwidthunit)
	{
		this.bandwidthunit = bandwidthunit;
	}

	/**
	 * Method 'getApplytime'
	 * 
	 * @return Date
	 */
	public Date getApplytime()
	{
		return applytime;
	}

	/**
	 * Method 'setApplytime'
	 * 
	 * @param applytime
	 */
	public void setApplytime(Date applytime)
	{
		this.applytime = applytime;
	}

	/**
	 * Method 'getOpentime'
	 * 
	 * @return Date
	 */
	public Date getOpentime()
	{
		return opentime;
	}

	/**
	 * Method 'setOpentime'
	 * 
	 * @param opentime
	 */
	public void setOpentime(Date opentime)
	{
		this.opentime = opentime;
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
		
		if (!(_other instanceof TLineInfo)) {
			return false;
		}
		
		final TLineInfo _cast = (TLineInfo) _other;
		if (leid != _cast.leid) {
			return false;
		}
		
		if (linename == null ? _cast.linename != linename : !linename.equals( _cast.linename )) {
			return false;
		}
		
		if (leno == null ? _cast.leno != leno : !leno.equals( _cast.leno )) {
			return false;
		}
		
		if (category != _cast.category) {
			return false;
		}
		
		if (categoryNull != _cast.categoryNull) {
			return false;
		}
		
		if (bandwidth != _cast.bandwidth) {
			return false;
		}
		
		if (bandwidthNull != _cast.bandwidthNull) {
			return false;
		}
		
		if (bandwidthunit == null ? _cast.bandwidthunit != bandwidthunit : !bandwidthunit.equals( _cast.bandwidthunit )) {
			return false;
		}
		
		if (applytime == null ? _cast.applytime != applytime : !applytime.equals( _cast.applytime )) {
			return false;
		}
		
		if (opentime == null ? _cast.opentime != opentime : !opentime.equals( _cast.opentime )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
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
		_hashCode = 29 * _hashCode + (int) (leid ^ (leid >>> 32));
		if (linename != null) {
			_hashCode = 29 * _hashCode + linename.hashCode();
		}
		
		if (leno != null) {
			_hashCode = 29 * _hashCode + leno.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (int) (category ^ (category >>> 32));
		_hashCode = 29 * _hashCode + (categoryNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (int) (bandwidth ^ (bandwidth >>> 32));
		_hashCode = 29 * _hashCode + (bandwidthNull ? 1 : 0);
		if (bandwidthunit != null) {
			_hashCode = 29 * _hashCode + bandwidthunit.hashCode();
		}
		
		if (applytime != null) {
			_hashCode = 29 * _hashCode + applytime.hashCode();
		}
		
		if (opentime != null) {
			_hashCode = 29 * _hashCode + opentime.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TLineInfoPk
	 */
	public TLineInfoPk createPk()
	{
		return new TLineInfoPk(leid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.TLineInfo: " );
		ret.append( "leid=" + leid );
		ret.append( ", linename=" + linename );
		ret.append( ", leno=" + leno );
		ret.append( ", category=" + category );
		ret.append( ", bandwidth=" + bandwidth );
		ret.append( ", bandwidthunit=" + bandwidthunit );
		ret.append( ", applytime=" + applytime );
		ret.append( ", opentime=" + opentime );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
