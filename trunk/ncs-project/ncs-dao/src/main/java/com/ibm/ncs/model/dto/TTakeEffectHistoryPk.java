package com.ibm.ncs.model.dto;

import java.io.Serializable;

public class TTakeEffectHistoryPk implements Serializable {
	
	/**
	 * 
	 */
  private static final long serialVersionUID = 5761940477435967057L;
	private long teId;

	public TTakeEffectHistoryPk() {
		super();
	}

	public long getTeId() {
		return teId;
	}

	public void setTeId(long teId) {
		this.teId = teId;
	}

	public TTakeEffectHistoryPk(long teId) {
	  super();
	  this.teId = teId;
  }

	@Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + (int) (teId ^ (teId >>> 32));
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
	  TTakeEffectHistoryPk other = (TTakeEffectHistoryPk) obj;
	  if (teId != other.teId)
		  return false;
	  return true;
  }

	@Override
  public String toString() {
	  return "TTakeEffectHistoryPk [teId=" + teId + "]";
  }

}
