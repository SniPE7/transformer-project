package com.ibm.ncs.model.dto;

import java.io.Serializable;

public class PolicyTemplateScope implements Serializable {

	private long ptvid;
	private long mrid;
	private long dtid;
	
	/**
	 * 
	 */
  private static final long serialVersionUID = 3808761693011077306L;

	public PolicyTemplateScope() {
		super();
	}

	public long getPtvid() {
		return ptvid;
	}

	public void setPtvid(long ptvid) {
		this.ptvid = ptvid;
	}

	public long getMrid() {
		return mrid;
	}

	public void setMrid(long mrid) {
		this.mrid = mrid;
	}

	public long getDtid() {
		return dtid;
	}

	public void setDtid(long dtid) {
		this.dtid = dtid;
	}

	@Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + (int) (dtid ^ (dtid >>> 32));
	  result = prime * result + (int) (mrid ^ (mrid >>> 32));
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
	  PolicyTemplateScope other = (PolicyTemplateScope) obj;
	  if (dtid != other.dtid)
		  return false;
	  if (mrid != other.mrid)
		  return false;
	  if (ptvid != other.ptvid)
		  return false;
	  return true;
  }

	@Override
  public String toString() {
	  return "PolicyTemplateScope [ptvid=" + ptvid + ", mrid=" + mrid + ", dtid=" + dtid + "]";
  }

}
