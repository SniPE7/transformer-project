/**
 * 
 */
package com.ibm.ncs.model.dto;

import java.io.Serializable;


/**
 * @author zhaodonglu
 *
 */
public class PolicyTemplate implements Serializable {
	
	private long ptid;
	private String mpname;
	private String status;
	private long category;
	private String description;

	/**
	 * 
	 */
  private static final long serialVersionUID = 7299238926313232863L;

	/**
	 * 
	 */
	public PolicyTemplate() {
		super();
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
	 * @return the mpname
	 */
	public String getMpname() {
		return mpname;
	}

	/**
	 * @param mpname the mpname to set
	 */
	public void setMpname(String mpname) {
		this.mpname = mpname;
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
	 * @return the category
	 */
	public long getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(long category) {
		this.category = category;
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

	/**
	 * Method 'createPk'
	 * 
	 * @return TPolicyTemplatePk
	 */
	public TPolicyTemplatePk createPk() {
		return new TPolicyTemplatePk(this.ptid);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
  @Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + (int) (ptid ^ (ptid >>> 32));
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
	  PolicyTemplate other = (PolicyTemplate) obj;
	  if (ptid != other.ptid)
		  return false;
	  return true;
  }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
  @Override
  public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("PolicyTemplate [ptid=");
	  builder.append(ptid);
	  builder.append(", mpname=");
	  builder.append(mpname);
	  builder.append(", status=");
	  builder.append(status);
	  builder.append(", category=");
	  builder.append(category);
	  builder.append(", description=");
	  builder.append(description);
	  builder.append("]");
	  return builder.toString();
  }
	
}
