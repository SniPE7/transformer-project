package com.ibm.ncs.model.dto;

import java.io.Serializable;

/**
 * This class represents the primary key of the T_POLICY_TEMPLATE_VER table.
 */
public class PolicyTemplateVerPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 895218542831323719L;

	protected long ptvid;

	/**
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean ptvidNull;

	/**
	 * Sets the value of mpid
	 */
	public void setPtvid(long mpid) {
		this.ptvid = mpid;
	}

	/**
	 * Gets the value of mpid
	 */
	public long getPtvid() {
		return ptvid;
	}

	/**
	 * Method 'TPolicyBasePk'
	 * 
	 */
	public PolicyTemplateVerPk() {
	}

	/**
	 * Method 'TPolicyBasePk'
	 * 
	 * @param mpid
	 */
	public PolicyTemplateVerPk(final long mpid) {
		this.ptvid = mpid;
	}

	/**
	 * Sets the value of mpidNull
	 */
	public void setPtvidNull(boolean mpidNull) {
		this.ptvidNull = mpidNull;
	}

	/**
	 * Gets the value of mpidNull
	 */
	public boolean isPtvidNull() {
		return ptvidNull;
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

		if (!(_other instanceof PolicyTemplateVerPk)) {
			return false;
		}

		final PolicyTemplateVerPk _cast = (PolicyTemplateVerPk) _other;
		if (ptvid != _cast.ptvid) {
			return false;
		}

		if (ptvidNull != _cast.ptvidNull) {
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
		_hashCode = 29 * _hashCode + (int) (ptvid ^ (ptvid >>> 32));
		_hashCode = 29 * _hashCode + (ptvidNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append(PolicyTemplateVerPk.class.getName() + ": ");
		ret.append("ptid=" + ptvid);
		return ret.toString();
	}

}
