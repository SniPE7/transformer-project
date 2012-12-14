package com.ibm.ncs.model.dto;

import java.io.Serializable;

public class TPolicyDetailsWithRulePk implements Serializable {
	/**
	 * 
	 */
  private static final long serialVersionUID = -9197880039981214317L;

	protected long ptvid;

	protected long modid;

	protected long eveid;

	/**
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean mpidNull;

	/**
	 * This attribute represents whether the primitive attribute modid is null.
	 */
	protected boolean modidNull;

	/**
	 * This attribute represents whether the primitive attribute eveid is null.
	 */
	protected boolean eveidNull;

	/**
	 * Sets the value of modid
	 */
	public void setModid(long modid) {
		this.modid = modid;
	}

	/**
	 * Gets the value of modid
	 */
	public long getModid() {
		return modid;
	}

	/**
	 * Sets the value of eveid
	 */
	public void setEveid(long eveid) {
		this.eveid = eveid;
	}

	/**
	 * Gets the value of eveid
	 */
	public long getEveid() {
		return eveid;
	}

	/**
	 * Method 'TPolicyDetailsPk'
	 * 
	 */
	public TPolicyDetailsWithRulePk() {
	}

	/**
	 * Method 'TPolicyDetailsPk'
	 * 
	 * @param mpid
	 * @param modid
	 * @param eveid
	 */
	public TPolicyDetailsWithRulePk(final long ptvid, final long modid, final long eveid) {
		this.ptvid = ptvid;
		this.modid = modid;
		this.eveid = eveid;
	}

	public long getPtvid() {
		return ptvid;
	}

	public void setPtvid(long ptvid) {
		this.ptvid = ptvid;
	}

	/**
	 * Sets the value of mpidNull
	 */
	public void setMpidNull(boolean mpidNull) {
		this.mpidNull = mpidNull;
	}

	/**
	 * Gets the value of mpidNull
	 */
	public boolean isMpidNull() {
		return mpidNull;
	}

	/**
	 * Sets the value of modidNull
	 */
	public void setModidNull(boolean modidNull) {
		this.modidNull = modidNull;
	}

	/**
	 * Gets the value of modidNull
	 */
	public boolean isModidNull() {
		return modidNull;
	}

	/**
	 * Sets the value of eveidNull
	 */
	public void setEveidNull(boolean eveidNull) {
		this.eveidNull = eveidNull;
	}

	/**
	 * Gets the value of eveidNull
	 */
	public boolean isEveidNull() {
		return eveidNull;
	}

	@Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + (int) (eveid ^ (eveid >>> 32));
	  result = prime * result + (int) (modid ^ (modid >>> 32));
	  result = prime * result + (int) (ptvid ^ (ptvid >>> 32));
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
	  TPolicyDetailsWithRulePk other = (TPolicyDetailsWithRulePk) obj;
	  if (eveid != other.eveid)
		  return false;
	  if (modid != other.modid)
		  return false;
	  if (ptvid != other.ptvid)
		  return false;
	  return true;
  }

	@Override
  public String toString() {
	  return "TPolicyDetailsWithRulePk [ptvid=" + ptvid + ", modid=" + modid + ", eveid=" + eveid + "]";
  }
}
