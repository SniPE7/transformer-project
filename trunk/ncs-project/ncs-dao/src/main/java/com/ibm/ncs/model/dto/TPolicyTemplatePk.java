package com.ibm.ncs.model.dto;

import java.io.Serializable;

/**
 * This class represents the primary key of the T_POLICY_TEMPLATE table.
 */
public class TPolicyTemplatePk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 895218542831323719L;

	protected long ptid;

	/**
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean ptidNull;

	/**
	 * Sets the value of mpid
	 */
	public void setPtid(long mpid) {
		this.ptid = mpid;
	}

	/**
	 * Gets the value of mpid
	 */
	public long getPtid() {
		return ptid;
	}

	/**
	 * Method 'TPolicyBasePk'
	 * 
	 */
	public TPolicyTemplatePk() {
	}

	/**
	 * Method 'TPolicyBasePk'
	 * 
	 * @param mpid
	 */
	public TPolicyTemplatePk(final long mpid) {
		this.ptid = mpid;
	}

	/**
	 * Sets the value of mpidNull
	 */
	public void setPtidNull(boolean mpidNull) {
		this.ptidNull = mpidNull;
	}

	/**
	 * Gets the value of mpidNull
	 */
	public boolean isPtidNull() {
		return ptidNull;
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

		if (!(_other instanceof TPolicyTemplatePk)) {
			return false;
		}

		final TPolicyTemplatePk _cast = (TPolicyTemplatePk) _other;
		if (ptid != _cast.ptid) {
			return false;
		}

		if (ptidNull != _cast.ptidNull) {
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
		_hashCode = 29 * _hashCode + (int) (ptid ^ (ptid >>> 32));
		_hashCode = 29 * _hashCode + (ptidNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append(TPolicyTemplatePk.class.getName() + ": ");
		ret.append("ptid=" + ptid);
		return ret.toString();
	}

}
