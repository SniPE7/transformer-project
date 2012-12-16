package com.ibm.ncs.model.dto;

import java.io.Serializable;

public class TServerNode implements Serializable {
	/**
	 * 
	 */
  private static final long serialVersionUID = 9012577413888906827L;

  private long serverID;
  private String serverCode;
  private String nodeType;
  private String serverName;
  private String description;
  private String serviceEndpointInfo;
  
  
	public TServerNode() {
	}

	public long getServerID() {
		return serverID;
	}

	public void setServerID(long serverID) {
		this.serverID = serverID;
	}

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceEndpointInfo() {
		return serviceEndpointInfo;
	}

	public void setServiceEndpointInfo(String serviceEndpointInfo) {
		this.serviceEndpointInfo = serviceEndpointInfo;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TPolicyDetailsPk
	 */
	public TServerNodePk createPk() {
		return new TServerNodePk(serverID);
	}

	@Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + ((serverCode == null) ? 0 : serverCode.hashCode());
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
	  TServerNode other = (TServerNode) obj;
	  if (serverCode == null) {
		  if (other.serverCode != null)
			  return false;
	  } else if (!serverCode.equals(other.serverCode))
		  return false;
	  if (serverID != other.serverID)
		  return false;
	  return true;
  }

	@Override
  public String toString() {
	  return "TServerNode [serverID=" + serverID + ", serverCode=" + serverCode + ", nodeType=" + nodeType + ", serverName=" + serverName + ", description=" + description
	      + ", serviceEndpointInfo=" + serviceEndpointInfo + "]";
  }

}
