package com.ibm.ncs.model.dto;

import java.io.Serializable;

public class TServerNodePk implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -8855298957387974824L;
  
	private long serverID;
  
	public TServerNodePk() {
	}

	public TServerNodePk(long serverID) {
	  super();
	  this.serverID = serverID;
  }

	public long getServerID() {
		return serverID;
	}

	public void setServerID(long serverID) {
		this.serverID = serverID;
	}

	@Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + (int) (serverID ^ (serverID >>> 32));
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
	  TServerNodePk other = (TServerNodePk) obj;
	  if (serverID != other.serverID)
		  return false;
	  return true;
  }

	@Override
  public String toString() {
	  return "TServerNodePk [serverID=" + serverID + "]";
  }

}
