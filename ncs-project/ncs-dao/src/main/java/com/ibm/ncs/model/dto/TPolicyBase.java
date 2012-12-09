package com.ibm.ncs.model.dto;

import java.io.Serializable;

public class TPolicyBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1599816057468044244L;

	/**
	 * This attribute maps to the column MPID in the T_POLICY_BASE table.
	 */
	protected long mpid;

	/**
	 * This attribute maps to the column MPNAME in the T_POLICY_BASE table.
	 */
	protected String mpname;

	/**
	 * This attribute maps to the column CATEGORY in the T_POLICY_BASE table.
	 */
	protected long category;

	/**
	 * This attribute maps to the column DESCRIPTION in the T_POLICY_BASE table.
	 */
	protected String description;

	/**
	 * Method 'TPolicyBase'
	 * 
	 */
	public TPolicyBase() {
	}

	/**
	 * Method 'getMpid'
	 * 
	 * @return long
	 */
	public long getMpid() {
		return mpid;
	}

	/**
	 * Method 'setMpid'
	 * 
	 * @param mpid
	 */
	public void setMpid(long mpid) {
		this.mpid = mpid;
	}

	/**
	 * Method 'getMpname'
	 * 
	 * @return String
	 */
	public String getMpname() {
		return mpname;
	}

	/**
	 * Method 'setMpname'
	 * 
	 * @param mpname
	 */
	public void setMpname(String mpname) {
		this.mpname = mpname;
	}

	/**
	 * Method 'getCategory'
	 * 
	 * @return long
	 */
	public long getCategory() {
		return category;
	}

	/**
	 * Method 'setCategory'
	 * 
	 * @param category
	 */
	public void setCategory(long category) {
		this.category = category;
	}

	/**
	 * Method 'getDescription'
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method 'setDescription'
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null) {
			return false;
		}

		if (_other == this) {
			return true;
		}

		if (!(_other instanceof TPolicyBase)) {
			return false;
		}

		final TPolicyBase _cast = (TPolicyBase) _other;
		if (mpid != _cast.mpid) {
			return false;
		}

		if (mpname == null ? _cast.mpname != mpname : !mpname.equals(_cast.mpname)) {
			return false;
		}

		if (category != _cast.category) {
			return false;
		}

		if (description == null ? _cast.description != description : !description.equals(_cast.description)) {
			return false;
		}

		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode() {
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + (int) (mpid ^ (mpid >>> 32));
		if (mpname != null) {
			_hashCode = 29 * _hashCode + mpname.hashCode();
		}

		_hashCode = 29 * _hashCode + (int) (category ^ (category >>> 32));
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}

		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TPolicyBasePk
	 */
	public TPolicyBasePk createPk() {
		return new TPolicyBasePk(mpid);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.ibm.ncs.model.dto.TPolicyBase: ");
		ret.append("mpid=" + mpid);
		ret.append(", mpname=" + mpname);
		ret.append(", category=" + category);
		ret.append(", description=" + description);
		return ret.toString();
	}

}
