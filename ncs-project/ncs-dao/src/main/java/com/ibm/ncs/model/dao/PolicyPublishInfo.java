package com.ibm.ncs.model.dao;

import java.io.Serializable;
import java.util.Date;

public class PolicyPublishInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7131162617010036528L;

	/** 
	 * This attribute maps to the column PPID in the T_POLICY_PUBLISH_INFO table.
	 */
	private long ppiid = 0L;
	/** 
	 * This attribute maps to the column VERSION in the T_POLICY_PUBLISH_INFO table.
	 */
	private String version = null;
	/** 
	 * This attribute maps to the column VERSION_TAG in the T_POLICY_PUBLISH_INFO table.
	 */
	private String versionTag = null;
	/** 
	 * This attribute maps to the column DESCRIPTION in the T_POLICY_PUBLISH_INFO table.
	 */
	private String description = null;
	/** 
	 * This attribute maps to the column PUBLISH_TIME in the T_POLICY_PUBLISH_INFO table.
	 */
	private Date publishTime = null;
	
	/** 
	 * This attribute maps to the column CREATE_TIME in the T_POLICY_PUBLISH_INFO table.
	 */
	private Date createTime = null;
	
	/** 
	 * This attribute maps to the column UPDATE_TIME in the T_POLICY_PUBLISH_INFO table.
	 */
	private Date updateTime = null;

	public PolicyPublishInfo() {
	  super();
  }

	public long getPpiid() {
		return ppiid;
	}

	public void setPpiid(long ppiid) {
		this.ppiid = ppiid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersionTag() {
		return versionTag;
	}

	public void setVersionTag(String versionTag) {
		this.versionTag = versionTag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
  @Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + (int) (ppiid ^ (ppiid >>> 32));
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
	  PolicyPublishInfo other = (PolicyPublishInfo) obj;
	  if (ppiid != other.ppiid)
		  return false;
	  return true;
  }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
  @Override
  public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("PolicyPublishInfo [ppiid=");
	  builder.append(ppiid);
	  builder.append(", version=");
	  builder.append(version);
	  builder.append(", versionTag=");
	  builder.append(versionTag);
	  builder.append(", description=");
	  builder.append(description);
	  builder.append(", publishTime=");
	  builder.append(publishTime);
	  builder.append(", createTime=");
	  builder.append(createTime);
	  builder.append(", updateTime=");
	  builder.append(updateTime);
	  builder.append("]");
	  return builder.toString();
  }


}
