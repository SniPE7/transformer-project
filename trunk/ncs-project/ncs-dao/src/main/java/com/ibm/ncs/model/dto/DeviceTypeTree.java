package com.ibm.ncs.model.dto;

import java.io.Serializable;

public class DeviceTypeTree implements Serializable, Comparable<DeviceTypeTree>
{
	protected long mrid;

	/** 
	 * This attribute represents whether the primitive attribute mrid is null.
	 */
	protected boolean mridNull = true;

	protected long dtid;

	/** 
	 * This attribute represents whether the primitive attribute dtid is null.
	 */
	protected boolean dtidNull = true;

	protected long category;

	/** 
	 * This attribute represents whether the primitive attribute category is null.
	 */
	protected boolean categoryNull = true;

	protected java.lang.String mrName;

	protected java.lang.String cateName;

	protected java.lang.String subCategory;

	protected java.lang.String model;

	protected java.lang.String objectid;

	protected java.lang.String logo;

	protected java.lang.String description;

	/**
	 * Method 'DeviceTypeTree'
	 * 
	 */
	public DeviceTypeTree()
	{
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
	}

	/** 
	 * Sets the value of mridNull
	 */
	public void setMridNull(boolean mridNull)
	{
		this.mridNull = mridNull;
	}

	/** 
	 * Gets the value of mridNull
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
	}

	/** 
	 * Sets the value of dtidNull
	 */
	public void setDtidNull(boolean dtidNull)
	{
		this.dtidNull = dtidNull;
	}

	/** 
	 * Gets the value of dtidNull
	 */
	public boolean isDtidNull()
	{
		return dtidNull;
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
	}

	/** 
	 * Sets the value of categoryNull
	 */
	public void setCategoryNull(boolean categoryNull)
	{
		this.categoryNull = categoryNull;
	}

	/** 
	 * Gets the value of categoryNull
	 */
	public boolean isCategoryNull()
	{
		return categoryNull;
	}

	/**
	 * Method 'getMrName'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getMrName()
	{
		return mrName;
	}

	/**
	 * Method 'setMrName'
	 * 
	 * @param mrName
	 */
	public void setMrName(java.lang.String mrName)
	{
		this.mrName = mrName;
	}

	/**
	 * Method 'getCateName'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getCateName()
	{
		return cateName;
	}

	/**
	 * Method 'setCateName'
	 * 
	 * @param cateName
	 */
	public void setCateName(java.lang.String cateName)
	{
		this.cateName = cateName;
	}

	/**
	 * Method 'getSubCategory'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getSubCategory()
	{
		return subCategory;
	}

	/**
	 * Method 'setSubCategory'
	 * 
	 * @param subCategory
	 */
	public void setSubCategory(java.lang.String subCategory)
	{
		this.subCategory = subCategory;
	}

	/**
	 * Method 'getModel'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getModel()
	{
		return model;
	}

	/**
	 * Method 'setModel'
	 * 
	 * @param model
	 */
	public void setModel(java.lang.String model)
	{
		this.model = model;
	}

	/**
	 * Method 'getObjectid'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getObjectid()
	{
		return objectid;
	}

	/**
	 * Method 'setObjectid'
	 * 
	 * @param objectid
	 */
	public void setObjectid(java.lang.String objectid)
	{
		this.objectid = objectid;
	}

	/**
	 * Method 'getLogo'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getLogo()
	{
		return logo;
	}

	/**
	 * Method 'setLogo'
	 * 
	 * @param logo
	 */
	public void setLogo(java.lang.String logo)
	{
		this.logo = logo;
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

	@Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + (int) (dtid ^ (dtid >>> 32));
	  return result;
  }

	@Override
  public boolean equals(Object obj) {
	  if (this == obj)
		  return true;
	  if (obj == null)
		  return false;
	  if (getClass() != obj.getClass())
		  return false;
	  DeviceTypeTree other = (DeviceTypeTree) obj;
	  if (dtid != other.dtid)
		  return false;
	  return true;
  }

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.DeviceTypeTree: " );
		ret.append( "mrid=" + mrid );
		ret.append( ", dtid=" + dtid );
		ret.append( ", category=" + category );
		ret.append( ", mrName=" + mrName );
		ret.append( ", cateName=" + cateName );
		ret.append( ", subCategory=" + subCategory );
		ret.append( ", model=" + model );
		ret.append( ", objectid=" + objectid );
		ret.append( ", logo=" + logo );
		ret.append( ", description=" + description );
		return ret.toString();
	}

	public int compareTo(DeviceTypeTree o) {
		if (o == null) {
			 return 1;
		}
		if (this.mrName == null || o.getMrName() == null) {
			 return -1;
		}
		if (this.model == null) {
			if (o.getModel() != null) {
				 return -1;
			} else {
				return 0;
			}
		}
		if (o.getModel() == null) {
			 return -1;
		}
		
		if (this.mrName.equalsIgnoreCase(o.getMrName())) {
			return this.model.compareToIgnoreCase(o.getModel());
		} else {
			return this.mrName.compareToIgnoreCase(o.getMrName());
		}
  }

}
