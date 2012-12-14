package com.ibm.ncs.model.dto;

import java.io.Serializable;

public class TPolicyDetailsWithRule implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1588863545008452297L;

	/**
	 * This attribute maps to the column MPID in the T_POLICY_DETAILS table.
	 */
	protected long ptvid;

	/**
	 * This attribute maps to the column MODID in the T_POLICY_DETAILS table.
	 */
	protected long modid;

	/**
	 * This attribute maps to the column EVEID in the T_POLICY_DETAILS table.
	 */
	protected long eveid;

	/**
	 * This attribute maps to the column POLL in the T_POLICY_DETAILS table.
	 */
	protected long poll;

	/**
	 * This attribute represents whether the primitive attribute poll is null.
	 */
	protected boolean pollNull = true;

	/**
	 * This attribute maps to the column VALUE_1 in the T_POLICY_DETAILS table.
	 */
	protected String value1;

	/**
	 * This attribute maps to the column SEVERITY_1 in the T_POLICY_DETAILS table.
	 */
	protected long severity1;

	/**
	 * This attribute represents whether the primitive attribute severity1 is
	 * null.
	 */
	protected boolean severity1Null;

	/**
	 * This attribute maps to the column FILTER_A in the T_POLICY_DETAILS table.
	 */
	protected String filterA;

	/**
	 * This attribute maps to the column VALUE_2 in the T_POLICY_DETAILS table.
	 */
	protected String value2;

	/**
	 * This attribute maps to the column SEVERITY_2 in the T_POLICY_DETAILS table.
	 */
	protected long severity2;

	/**
	 * This attribute represents whether the primitive attribute severity2 is
	 * null.
	 */
	protected boolean severity2Null;

	/**
	 * This attribute maps to the column FILTER_B in the T_POLICY_DETAILS table.
	 */
	protected String filterB;

	/**
	 * This attribute maps to the column SEVERITY_A in the T_POLICY_DETAILS table.
	 */
	protected long severityA;

	/**
	 * This attribute represents whether the primitive attribute severityA is
	 * null.
	 */
	protected boolean severityANull = true;

	/**
	 * This attribute maps to the column SEVERITY_B in the T_POLICY_DETAILS table.
	 */
	protected long severityB;

	/**
	 * This attribute represents whether the primitive attribute severityB is
	 * null.
	 */
	protected boolean severityBNull = true;

	/**
	 * This attribute maps to the column OIDGROUP in the T_POLICY_DETAILS table.
	 */
	protected String oidgroup;

	/**
	 * This attribute maps to the column OGFLAG in the T_POLICY_DETAILS table.
	 */
	protected String ogflag;

	/**
	 * This attribute maps to the column VALUE_1_LOW in the T_POLICY_DETAILS
	 * table.
	 */
	protected String value1Low;

	/**
	 * This attribute maps to the column VALUE_2_LOW in the T_POLICY_DETAILS
	 * table.
	 */
	protected String value2Low;

	/**
	 * This attribute maps to the column V1L_SEVERITY_1 in the T_POLICY_DETAILS
	 * table.
	 */
	protected long v1lSeverity1;

	/**
	 * This attribute represents whether the primitive attribute v1lSeverity1 is
	 * null.
	 */
	protected boolean v1lSeverity1Null = true;

	/**
	 * This attribute maps to the column V1L_SEVERITY_A in the T_POLICY_DETAILS
	 * table.
	 */
	protected long v1lSeverityA;

	/**
	 * This attribute represents whether the primitive attribute v1lSeverityA is
	 * null.
	 */
	protected boolean v1lSeverityANull = true;

	/**
	 * This attribute maps to the column V2L_SEVERITY_2 in the T_POLICY_DETAILS
	 * table.
	 */
	protected long v2lSeverity2;

	/**
	 * This attribute represents whether the primitive attribute v2lSeverity2 is
	 * null.
	 */
	protected boolean v2lSeverity2Null = true;

	/**
	 * This attribute maps to the column V2L_SEVERITY_B in the T_POLICY_DETAILS
	 * table.
	 */
	protected long v2lSeverityB;

	/**
	 * This attribute represents whether the primitive attribute v2lSeverityB is
	 * null.
	 */
	protected boolean v2lSeverityBNull = true;

	/**
	 * This attribute maps to the column COMPARETYPE in the T_POLICY_DETAILS
	 * table.
	 */
	protected String comparetype;

	private String value1Rule;
	private String value2Rule;
	private String value1LowRule;
	private String value2LowRule;

	public TPolicyDetailsWithRule() {
		super();
	}

	public long getPtvid() {
		return ptvid;
	}

	public void setPtvid(long ptvid) {
		this.ptvid = ptvid;
	}

	/**
	 * Method 'getModid'
	 * 
	 * @return long
	 */
	public long getModid() {
		return modid;
	}

	/**
	 * Method 'setModid'
	 * 
	 * @param modid
	 */
	public void setModid(long modid) {
		this.modid = modid;
	}

	/**
	 * Method 'getEveid'
	 * 
	 * @return long
	 */
	public long getEveid() {
		return eveid;
	}

	/**
	 * Method 'setEveid'
	 * 
	 * @param eveid
	 */
	public void setEveid(long eveid) {
		this.eveid = eveid;
	}

	/**
	 * Method 'getPoll'
	 * 
	 * @return long
	 */
	public long getPoll() {
		return poll;
	}

	/**
	 * Method 'setPoll'
	 * 
	 * @param poll
	 */
	public void setPoll(long poll) {
		this.poll = poll;
		this.pollNull = false;
	}

	/**
	 * Method 'setPollNull'
	 * 
	 * @param value
	 */
	public void setPollNull(boolean value) {
		this.pollNull = value;
	}

	/**
	 * Method 'isPollNull'
	 * 
	 * @return boolean
	 */
	public boolean isPollNull() {
		return pollNull;
	}

	/**
	 * Method 'getValue1'
	 * 
	 * @return String
	 */
	public String getValue1() {
		return value1;
	}

	/**
	 * Method 'setValue1'
	 * 
	 * @param value1
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * Method 'getSeverity1'
	 * 
	 * @return long
	 */
	public long getSeverity1() {
		return severity1;
	}

	/**
	 * Method 'setSeverity1'
	 * 
	 * @param severity1
	 */
	public void setSeverity1(long severity1) {
		this.severity1 = severity1;
		this.severity1Null = false;
	}

	/**
	 * Method 'setSeverity1Null'
	 * 
	 * @param value
	 */
	public void setSeverity1Null(boolean value) {
		this.severity1Null = value;
	}

	/**
	 * Method 'isSeverity1Null'
	 * 
	 * @return boolean
	 */
	public boolean isSeverity1Null() {
		return severity1Null;
	}

	/**
	 * Method 'getFilterA'
	 * 
	 * @return String
	 */
	public String getFilterA() {
		return filterA;
	}

	/**
	 * Method 'setFilterA'
	 * 
	 * @param filterA
	 */
	public void setFilterA(String filterA) {
		this.filterA = filterA;
	}

	/**
	 * Method 'getValue2'
	 * 
	 * @return String
	 */
	public String getValue2() {
		return value2;
	}

	/**
	 * Method 'setValue2'
	 * 
	 * @param value2
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/**
	 * Method 'getSeverity2'
	 * 
	 * @return long
	 */
	public long getSeverity2() {
		return severity2;
	}

	/**
	 * Method 'setSeverity2'
	 * 
	 * @param severity2
	 */
	public void setSeverity2(long severity2) {
		this.severity2 = severity2;
		this.severity2Null = false;
	}

	/**
	 * Method 'setSeverity2Null'
	 * 
	 * @param value
	 */
	public void setSeverity2Null(boolean value) {
		this.severity2Null = value;
	}

	/**
	 * Method 'isSeverity2Null'
	 * 
	 * @return boolean
	 */
	public boolean isSeverity2Null() {
		return severity2Null;
	}

	/**
	 * Method 'getFilterB'
	 * 
	 * @return String
	 */
	public String getFilterB() {
		return filterB;
	}

	/**
	 * Method 'setFilterB'
	 * 
	 * @param filterB
	 */
	public void setFilterB(String filterB) {
		this.filterB = filterB;
	}

	/**
	 * Method 'getSeverityA'
	 * 
	 * @return long
	 */
	public long getSeverityA() {
		return severityA;
	}

	/**
	 * Method 'setSeverityA'
	 * 
	 * @param severityA
	 */
	public void setSeverityA(long severityA) {
		this.severityA = severityA;
		this.severityANull = false;
	}

	/**
	 * Method 'setSeverityANull'
	 * 
	 * @param value
	 */
	public void setSeverityANull(boolean value) {
		this.severityANull = value;
	}

	/**
	 * Method 'isSeverityANull'
	 * 
	 * @return boolean
	 */
	public boolean isSeverityANull() {
		return severityANull;
	}

	/**
	 * Method 'getSeverityB'
	 * 
	 * @return long
	 */
	public long getSeverityB() {
		return severityB;
	}

	/**
	 * Method 'setSeverityB'
	 * 
	 * @param severityB
	 */
	public void setSeverityB(long severityB) {
		this.severityB = severityB;
		this.severityBNull = false;
	}

	/**
	 * Method 'setSeverityBNull'
	 * 
	 * @param value
	 */
	public void setSeverityBNull(boolean value) {
		this.severityBNull = value;
	}

	/**
	 * Method 'isSeverityBNull'
	 * 
	 * @return boolean
	 */
	public boolean isSeverityBNull() {
		return severityBNull;
	}

	/**
	 * Method 'getOidgroup'
	 * 
	 * @return String
	 */
	public String getOidgroup() {
		return oidgroup;
	}

	/**
	 * Method 'setOidgroup'
	 * 
	 * @param oidgroup
	 */
	public void setOidgroup(String oidgroup) {
		this.oidgroup = oidgroup;
	}

	/**
	 * Method 'getOgflag'
	 * 
	 * @return String
	 */
	public String getOgflag() {
		return ogflag;
	}

	/**
	 * Method 'setOgflag'
	 * 
	 * @param ogflag
	 */
	public void setOgflag(String ogflag) {
		this.ogflag = ogflag;
	}

	/**
	 * Method 'getValue1Low'
	 * 
	 * @return String
	 */
	public String getValue1Low() {
		return value1Low;
	}

	/**
	 * Method 'setValue1Low'
	 * 
	 * @param value1Low
	 */
	public void setValue1Low(String value1Low) {
		this.value1Low = value1Low;
	}

	/**
	 * Method 'getValue2Low'
	 * 
	 * @return String
	 */
	public String getValue2Low() {
		return value2Low;
	}

	/**
	 * Method 'setValue2Low'
	 * 
	 * @param value2Low
	 */
	public void setValue2Low(String value2Low) {
		this.value2Low = value2Low;
	}

	/**
	 * Method 'getV1lSeverity1'
	 * 
	 * @return long
	 */
	public long getV1lSeverity1() {
		return v1lSeverity1;
	}

	/**
	 * Method 'setV1lSeverity1'
	 * 
	 * @param v1lSeverity1
	 */
	public void setV1lSeverity1(long v1lSeverity1) {
		this.v1lSeverity1 = v1lSeverity1;
		this.v1lSeverity1Null = false;
	}

	/**
	 * Method 'setV1lSeverity1Null'
	 * 
	 * @param value
	 */
	public void setV1lSeverity1Null(boolean value) {
		this.v1lSeverity1Null = value;
	}

	/**
	 * Method 'isV1lSeverity1Null'
	 * 
	 * @return boolean
	 */
	public boolean isV1lSeverity1Null() {
		return v1lSeverity1Null;
	}

	/**
	 * Method 'getV1lSeverityA'
	 * 
	 * @return long
	 */
	public long getV1lSeverityA() {
		return v1lSeverityA;
	}

	/**
	 * Method 'setV1lSeverityA'
	 * 
	 * @param v1lSeverityA
	 */
	public void setV1lSeverityA(long v1lSeverityA) {
		this.v1lSeverityA = v1lSeverityA;
		this.v1lSeverityANull = false;
	}

	/**
	 * Method 'setV1lSeverityANull'
	 * 
	 * @param value
	 */
	public void setV1lSeverityANull(boolean value) {
		this.v1lSeverityANull = value;
	}

	/**
	 * Method 'isV1lSeverityANull'
	 * 
	 * @return boolean
	 */
	public boolean isV1lSeverityANull() {
		return v1lSeverityANull;
	}

	/**
	 * Method 'getV2lSeverity2'
	 * 
	 * @return long
	 */
	public long getV2lSeverity2() {
		return v2lSeverity2;
	}

	/**
	 * Method 'setV2lSeverity2'
	 * 
	 * @param v2lSeverity2
	 */
	public void setV2lSeverity2(long v2lSeverity2) {
		this.v2lSeverity2 = v2lSeverity2;
		this.v2lSeverity2Null = false;
	}

	/**
	 * Method 'setV2lSeverity2Null'
	 * 
	 * @param value
	 */
	public void setV2lSeverity2Null(boolean value) {
		this.v2lSeverity2Null = value;
	}

	/**
	 * Method 'isV2lSeverity2Null'
	 * 
	 * @return boolean
	 */
	public boolean isV2lSeverity2Null() {
		return v2lSeverity2Null;
	}

	/**
	 * Method 'getV2lSeverityB'
	 * 
	 * @return long
	 */
	public long getV2lSeverityB() {
		return v2lSeverityB;
	}

	/**
	 * Method 'setV2lSeverityB'
	 * 
	 * @param v2lSeverityB
	 */
	public void setV2lSeverityB(long v2lSeverityB) {
		this.v2lSeverityB = v2lSeverityB;
		this.v2lSeverityBNull = false;
	}

	/**
	 * Method 'setV2lSeverityBNull'
	 * 
	 * @param value
	 */
	public void setV2lSeverityBNull(boolean value) {
		this.v2lSeverityBNull = value;
	}

	/**
	 * Method 'isV2lSeverityBNull'
	 * 
	 * @return boolean
	 */
	public boolean isV2lSeverityBNull() {
		return v2lSeverityBNull;
	}

	/**
	 * Method 'getComparetype'
	 * 
	 * @return String
	 */
	public String getComparetype() {
		return comparetype;
	}

	/**
	 * Method 'setComparetype'
	 * 
	 * @param comparetype
	 */
	public void setComparetype(String comparetype) {
		this.comparetype = comparetype;
	}

	public String getValue1Rule() {
		return value1Rule;
	}

	public void setValue1Rule(String value1Rule) {
		this.value1Rule = value1Rule;
	}

	public String getValue2Rule() {
		return value2Rule;
	}

	public void setValue2Rule(String value2Rule) {
		this.value2Rule = value2Rule;
	}

	public String getValue1LowRule() {
		return value1LowRule;
	}

	public void setValue1LowRule(String value1LowRule) {
		this.value1LowRule = value1LowRule;
	}

	public String getValue2LowRule() {
		return value2LowRule;
	}

	public void setValue2LowRule(String value2LowRule) {
		this.value2LowRule = value2LowRule;
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
		TPolicyDetailsWithRule other = (TPolicyDetailsWithRule) obj;
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
		return "TPolicyDetailsWithRule [ptvid=" + ptvid + ", modid=" + modid + ", eveid=" + eveid + ", poll=" + poll + ", pollNull=" + pollNull + ", value1=" + value1 + ", severity1="
		    + severity1 + ", severity1Null=" + severity1Null + ", filterA=" + filterA + ", value2=" + value2 + ", severity2=" + severity2 + ", severity2Null=" + severity2Null
		    + ", filterB=" + filterB + ", severityA=" + severityA + ", severityANull=" + severityANull + ", severityB=" + severityB + ", severityBNull=" + severityBNull
		    + ", oidgroup=" + oidgroup + ", ogflag=" + ogflag + ", value1Low=" + value1Low + ", value2Low=" + value2Low + ", v1lSeverity1=" + v1lSeverity1 + ", v1lSeverity1Null="
		    + v1lSeverity1Null + ", v1lSeverityA=" + v1lSeverityA + ", v1lSeverityANull=" + v1lSeverityANull + ", v2lSeverity2=" + v2lSeverity2 + ", v2lSeverity2Null="
		    + v2lSeverity2Null + ", v2lSeverityB=" + v2lSeverityB + ", v2lSeverityBNull=" + v2lSeverityBNull + ", comparetype=" + comparetype + ", value1Rule=" + value1Rule
		    + ", value2Rule=" + value2Rule + ", value1LowRule=" + value1LowRule + ", value2LowRule=" + value2LowRule + "]";
	}

	public TPolicyDetailsWithRulePk createPk() {
		return new TPolicyDetailsWithRulePk(ptvid, modid, eveid);
	}

}
