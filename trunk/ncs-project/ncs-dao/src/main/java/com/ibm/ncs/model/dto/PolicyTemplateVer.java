package com.ibm.ncs.model.dto;

import java.io.Serializable;

import com.ibm.ncs.model.dao.PolicyPublishInfo;


public class PolicyTemplateVer implements Serializable {
	private long ptvid;
	private String ptVersion;
	private long ptid;
	private long ppiid;
	private String status;
	private String description;

	private PolicyPublishInfo policyPublishInfo = null;
	
	/**
	 * 
	 */
  private static final long serialVersionUID = 1335444366364144512L;

	public PolicyTemplateVer() {
		super();
	}

	/**
	 * @return the ptvid
	 */
	public long getPtvid() {
		return ptvid;
	}

	/**
	 * @param ptvid the ptvid to set
	 */
	public void setPtvid(long ptvid) {
		this.ptvid = ptvid;
	}

	/**
	 * @return the ptVersion
	 */
	public String getPtVersion() {
		return ptVersion;
	}

	/**
	 * @param ptVersion the ptVersion to set
	 */
	public void setPtVersion(String ptVersion) {
		this.ptVersion = ptVersion;
	}

	/**
	 * @return the ptid
	 */
	public long getPtid() {
		return ptid;
	}

	/**
	 * @param ptid the ptid to set
	 */
	public void setPtid(long ptid) {
		this.ptid = ptid;
	}

	/**
	 * @return the ppiid
	 */
	public long getPpiid() {
		return ppiid;
	}

	/**
	 * @param ppiid the ppiid to set
	 */
	public void setPpiid(long ppiid) {
		this.ppiid = ppiid;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public PolicyPublishInfo getPolicyPublishInfo() {
		return policyPublishInfo;
	}

	public void setPolicyPublishInfo(PolicyPublishInfo policyPublishInfo) {
		this.policyPublishInfo = policyPublishInfo;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return TPolicyTemplatePk
	 */
	public PolicyTemplateVerPk createPk() {
		return new PolicyTemplateVerPk(this.ptvid);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
  @Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + (int) (ptvid ^ (ptvid >>> 32));
	  return result;
  }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
  @Override
  public boolean equals(Object obj) {
	  if (this == obj)
		  return true;
	  if (obj == null)
		  return false;
	  if (getClass() != obj.getClass())
		  return false;
	  PolicyTemplateVer other = (PolicyTemplateVer) obj;
	  if (ptvid != other.ptvid)
		  return false;
	  return true;
  }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
  @Override
  public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("PolicyTemplateVer [ptvid=");
	  builder.append(ptvid);
	  builder.append(", ptVersion=");
	  builder.append(ptVersion);
	  builder.append(", ptid=");
	  builder.append(ptid);
	  builder.append(", ppiid=");
	  builder.append(ppiid);
	  builder.append(", status=");
	  builder.append(status);
	  builder.append(", description=");
	  builder.append(description);
	  builder.append("]");
	  return builder.toString();
  }

}
