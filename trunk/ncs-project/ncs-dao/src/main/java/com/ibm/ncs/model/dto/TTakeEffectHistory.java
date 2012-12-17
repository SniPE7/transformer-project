package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.util.Date;

public class TTakeEffectHistory implements Serializable {
	/**
	 * 
	 */
  private static final long serialVersionUID = 9012577413888906827L;

  private long teId;
  private long usid;
  private long ppiid;
  private long serverId;
  private Date generedTime = null;
  private String srcTypeFile;
  private String icmpXMLFile;
  private String snmpXMLFile;
  private String icmpThreshold;
  private String snmpThreshold;
  private Date effectTime;
  private String effectStatus;
  
	public TTakeEffectHistory() {
	}

	public long getTeId() {
		return teId;
	}

	public void setTeId(long teId) {
		this.teId = teId;
	}

	public long getUsid() {
		return usid;
	}

	public void setUsid(long usid) {
		this.usid = usid;
	}

	public long getPpiid() {
		return ppiid;
	}

	public void setPpiid(long ppiid) {
		this.ppiid = ppiid;
	}

	public long getServerId() {
		return serverId;
	}

	public void setServerId(long server_id) {
		this.serverId = server_id;
	}

	public Date getGeneredTime() {
		return generedTime;
	}

	public void setGeneredTime(Date generedTime) {
		this.generedTime = generedTime;
	}

	public String getSrcTypeFile() {
		return srcTypeFile;
	}

	public void setSrcTypeFile(String srcTypeFile) {
		this.srcTypeFile = srcTypeFile;
	}

	public String getIcmpXMLFile() {
		return icmpXMLFile;
	}

	public void setIcmpXMLFile(String icmpXMLFile) {
		this.icmpXMLFile = icmpXMLFile;
	}

	public String getSnmpXMLFile() {
		return snmpXMLFile;
	}

	public void setSnmpXMLFile(String snmpXMLFile) {
		this.snmpXMLFile = snmpXMLFile;
	}

	public String getIcmpThreshold() {
		return icmpThreshold;
	}

	public void setIcmpThreshold(String icmpThreshold) {
		this.icmpThreshold = icmpThreshold;
	}

	public String getSnmpThreshold() {
		return snmpThreshold;
	}

	public void setSnmpThreshold(String snmpThreshold) {
		this.snmpThreshold = snmpThreshold;
	}

	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public String getEffectStatus() {
		return effectStatus;
	}

	public void setEffectStatus(String effectStatus) {
		this.effectStatus = effectStatus;
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
	  TTakeEffectHistory other = (TTakeEffectHistory) obj;
	  if (teId != other.teId)
		  return false;
	  return true;
  }

	@Override
  public String toString() {
	  return "TTakeEffectHistory [teId=" + teId + ", usid=" + usid + ", ppiid=" + ppiid + ", server_id=" + serverId + ", generedTime=" + generedTime + ", srcTypeFile="
	      + srcTypeFile + ", icmpXMLFile=" + icmpXMLFile + ", snmpXMLFile=" + snmpXMLFile + ", icmpThreshold=" + icmpThreshold + ", snmpThreshold=" + snmpThreshold + ", effectTime="
	      + effectTime + ", effectStatus=" + effectStatus + "]";
  }

	/**
	 * Method 'createPk'
	 * 
	 * @return TTakeEffectHistoryPk
	 */
	public TTakeEffectHistoryPk createPk() {
		return new TTakeEffectHistoryPk(teId);
	}

}
