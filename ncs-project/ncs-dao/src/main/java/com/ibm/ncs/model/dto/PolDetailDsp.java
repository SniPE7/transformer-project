package com.ibm.ncs.model.dto;

import java.io.Serializable;

public class PolDetailDsp implements Serializable {
	/**
	 * 
	 */
  private static final long serialVersionUID = -4409531464128121777L;
  
	protected long mpid;
	protected long ptvid;

	/**
	 * This attribute represents whether the primitive attribute mpid is null.
	 */
	protected boolean mpidNull = true;

	protected long modid;

	/**
	 * This attribute represents whether the primitive attribute modid is null.
	 */
	protected boolean modidNull = true;

	protected long eveid;

	/**
	 * This attribute represents whether the primitive attribute eveid is null.
	 */
	protected boolean eveidNull = true;

	protected long poll;

	/**
	 * This attribute represents whether the primitive attribute poll is null.
	 */
	protected boolean pollNull = true;

	protected java.lang.String value1;

	protected long severity1;

	/**
	 * This attribute represents whether the primitive attribute severity1 is
	 * null.
	 */
	protected boolean severity1Null = true;

	protected java.lang.String filterA;

	protected java.lang.String value2;

	protected long severity2;

	/**
	 * This attribute represents whether the primitive attribute severity2 is
	 * null.
	 */
	protected boolean severity2Null = true;

	protected java.lang.String filterB;

	protected long severityA;

	/**
	 * This attribute represents whether the primitive attribute severityA is
	 * null.
	 */
	protected boolean severityANull = true;

	protected long severityB;

	/**
	 * This attribute represents whether the primitive attribute severityB is
	 * null.
	 */
	protected boolean severityBNull = true;

	protected java.lang.String oidgroup;

	protected java.lang.String ogflag;

	protected java.lang.String value1low;

	protected java.lang.String value2low;

	protected long v1lseverity1;

	/**
	 * This attribute represents whether the primitive attribute v1lseverity1 is
	 * null.
	 */
	protected boolean v1lseverity1Null = true;

	protected long v1lseverityA;

	/**
	 * This attribute represents whether the primitive attribute v1lseverityA is
	 * null.
	 */
	protected boolean v1lseverityANull = true;

	protected long v2lseverity2;

	/**
	 * This attribute represents whether the primitive attribute v2lseverity2 is
	 * null.
	 */
	protected boolean v2lseverity2Null = true;

	protected long v2lseverityB;

	/**
	 * This attribute represents whether the primitive attribute v2lseverityB is
	 * null.
	 */
	protected boolean v2lseverityBNull = true;

	protected java.lang.String major;

	protected long ecode;

	/**
	 * This attribute represents whether the primitive attribute ecode is null.
	 */
	protected boolean ecodeNull = true;

	protected long general;

	/**
	 * This attribute represents whether the primitive attribute general is null.
	 */
	protected boolean generalNull = true;

	protected java.lang.String mname;

	protected long mcode;

	/**
	 * This attribute represents whether the primitive attribute mcode is null.
	 */
	protected boolean mcodeNull = true;

	protected java.lang.String compareType;

	private String value1Rule;
	private String value2Rule;
	private String value1LowRule;
	private String value2LowRule;

	private TPolicyDetailsWithRule policyDetailsWithRule;

	/**
	 * Method 'PolDetailDsp'
	 * 
	 */
	public PolDetailDsp() {
	}

	/**
	 * Method 'getMpid'
	 * 
	 * @return long
	 */
	public long getMpid() {
		return mpid;
	}

	/**
	 * Method 'setMpid'
	 * 
	 * @param mpid
	 */
	public void setMpid(long mpid) {
		this.mpid = mpid;
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
	 * Sets the value of pollNull
	 */
	public void setPollNull(boolean pollNull) {
		this.pollNull = pollNull;
	}

	/**
	 * Gets the value of pollNull
	 */
	public boolean isPollNull() {
		return pollNull;
	}

	/**
	 * Method 'getValue1'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getValue1() {
		return value1;
	}

	/**
	 * Method 'setValue1'
	 * 
	 * @param value1
	 */
	public void setValue1(java.lang.String value1) {
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
	 * Sets the value of severity1Null
	 */
	public void setSeverity1Null(boolean severity1Null) {
		this.severity1Null = severity1Null;
	}

	/**
	 * Gets the value of severity1Null
	 */
	public boolean isSeverity1Null() {
		return severity1Null;
	}

	/**
	 * Method 'getFilterA'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFilterA() {
		return filterA;
	}

	/**
	 * Method 'setFilterA'
	 * 
	 * @param filterA
	 */
	public void setFilterA(java.lang.String filterA) {
		this.filterA = filterA;
	}

	/**
	 * Method 'getValue2'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getValue2() {
		return value2;
	}

	/**
	 * Method 'setValue2'
	 * 
	 * @param value2
	 */
	public void setValue2(java.lang.String value2) {
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
	 * Sets the value of severity2Null
	 */
	public void setSeverity2Null(boolean severity2Null) {
		this.severity2Null = severity2Null;
	}

	/**
	 * Gets the value of severity2Null
	 */
	public boolean isSeverity2Null() {
		return severity2Null;
	}

	/**
	 * Method 'getFilterB'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFilterB() {
		return filterB;
	}

	/**
	 * Method 'setFilterB'
	 * 
	 * @param filterB
	 */
	public void setFilterB(java.lang.String filterB) {
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
	 * Sets the value of severityANull
	 */
	public void setSeverityANull(boolean severityANull) {
		this.severityANull = severityANull;
	}

	/**
	 * Gets the value of severityANull
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
	 * Sets the value of severityBNull
	 */
	public void setSeverityBNull(boolean severityBNull) {
		this.severityBNull = severityBNull;
	}

	/**
	 * Gets the value of severityBNull
	 */
	public boolean isSeverityBNull() {
		return severityBNull;
	}

	/**
	 * Method 'getOidgroup'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getOidgroup() {
		return oidgroup;
	}

	/**
	 * Method 'setOidgroup'
	 * 
	 * @param oidgroup
	 */
	public void setOidgroup(java.lang.String oidgroup) {
		this.oidgroup = oidgroup;
	}

	/**
	 * Method 'getOgflag'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getOgflag() {
		return ogflag;
	}

	/**
	 * Method 'setOgflag'
	 * 
	 * @param ogflag
	 */
	public void setOgflag(java.lang.String ogflag) {
		this.ogflag = ogflag;
	}

	/**
	 * Method 'getValue1low'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getValue1low() {
		return value1low;
	}

	/**
	 * Method 'setValue1low'
	 * 
	 * @param value1low
	 */
	public void setValue1low(java.lang.String value1low) {
		this.value1low = value1low;
	}

	/**
	 * Method 'getValue2low'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getValue2low() {
		return value2low;
	}

	/**
	 * Method 'setValue2low'
	 * 
	 * @param value2low
	 */
	public void setValue2low(java.lang.String value2low) {
		this.value2low = value2low;
	}

	/**
	 * Method 'getV1lseverity1'
	 * 
	 * @return long
	 */
	public long getV1lseverity1() {
		return v1lseverity1;
	}

	/**
	 * Method 'setV1lseverity1'
	 * 
	 * @param v1lseverity1
	 */
	public void setV1lseverity1(long v1lseverity1) {
		this.v1lseverity1 = v1lseverity1;
		this.v1lseverity1Null = false;
	}

	/**
	 * Sets the value of v1lseverity1Null
	 */
	public void setV1lseverity1Null(boolean v1lseverity1Null) {
		this.v1lseverity1Null = v1lseverity1Null;
	}

	/**
	 * Gets the value of v1lseverity1Null
	 */
	public boolean isV1lseverity1Null() {
		return v1lseverity1Null;
	}

	/**
	 * Method 'getV1lseverityA'
	 * 
	 * @return long
	 */
	public long getV1lseverityA() {
		return v1lseverityA;
	}

	/**
	 * Method 'setV1lseverityA'
	 * 
	 * @param v1lseverityA
	 */
	public void setV1lseverityA(long v1lseverityA) {
		this.v1lseverityA = v1lseverityA;
		this.v1lseverityANull = false;
	}

	/**
	 * Sets the value of v1lseverityANull
	 */
	public void setV1lseverityANull(boolean v1lseverityANull) {
		this.v1lseverityANull = v1lseverityANull;
	}

	/**
	 * Gets the value of v1lseverityANull
	 */
	public boolean isV1lseverityANull() {
		return v1lseverityANull;
	}

	/**
	 * Method 'getV2lseverity2'
	 * 
	 * @return long
	 */
	public long getV2lseverity2() {
		return v2lseverity2;
	}

	/**
	 * Method 'setV2lseverity2'
	 * 
	 * @param v2lseverity2
	 */
	public void setV2lseverity2(long v2lseverity2) {
		this.v2lseverity2 = v2lseverity2;
		this.v2lseverity2Null = false;
	}

	/**
	 * Sets the value of v2lseverity2Null
	 */
	public void setV2lseverity2Null(boolean v2lseverity2Null) {
		this.v2lseverity2Null = v2lseverity2Null;
	}

	/**
	 * Gets the value of v2lseverity2Null
	 */
	public boolean isV2lseverity2Null() {
		return v2lseverity2Null;
	}

	/**
	 * Method 'getV2lseverityB'
	 * 
	 * @return long
	 */
	public long getV2lseverityB() {
		return v2lseverityB;
	}

	/**
	 * Method 'setV2lseverityB'
	 * 
	 * @param v2lseverityB
	 */
	public void setV2lseverityB(long v2lseverityB) {
		this.v2lseverityB = v2lseverityB;
		this.v2lseverityBNull = false;
	}

	/**
	 * Sets the value of v2lseverityBNull
	 */
	public void setV2lseverityBNull(boolean v2lseverityBNull) {
		this.v2lseverityBNull = v2lseverityBNull;
	}

	/**
	 * Gets the value of v2lseverityBNull
	 */
	public boolean isV2lseverityBNull() {
		return v2lseverityBNull;
	}

	/**
	 * Method 'getMajor'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getMajor() {
		return major;
	}

	/**
	 * Method 'setMajor'
	 * 
	 * @param major
	 */
	public void setMajor(java.lang.String major) {
		this.major = major;
	}

	/**
	 * Method 'getEcode'
	 * 
	 * @return long
	 */
	public long getEcode() {
		return ecode;
	}

	/**
	 * Method 'setEcode'
	 * 
	 * @param ecode
	 */
	public void setEcode(long ecode) {
		this.ecode = ecode;
	}

	/**
	 * Sets the value of ecodeNull
	 */
	public void setEcodeNull(boolean ecodeNull) {
		this.ecodeNull = ecodeNull;
	}

	/**
	 * Gets the value of ecodeNull
	 */
	public boolean isEcodeNull() {
		return ecodeNull;
	}

	/**
	 * Method 'getGeneral'
	 * 
	 * @return long
	 */
	public long getGeneral() {
		return general;
	}

	/**
	 * Method 'setGeneral'
	 * 
	 * @param general
	 */
	public void setGeneral(long general) {
		this.general = general;
	}

	/**
	 * Sets the value of generalNull
	 */
	public void setGeneralNull(boolean generalNull) {
		this.generalNull = generalNull;
	}

	/**
	 * Gets the value of generalNull
	 */
	public boolean isGeneralNull() {
		return generalNull;
	}

	/**
	 * Method 'getMname'
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getMname() {
		return mname;
	}

	/**
	 * Method 'setMname'
	 * 
	 * @param mname
	 */
	public void setMname(java.lang.String mname) {
		this.mname = mname;
	}

	/**
	 * Method 'getMcode'
	 * 
	 * @return long
	 */
	public long getMcode() {
		return mcode;
	}

	/**
	 * Method 'setMcode'
	 * 
	 * @param mcode
	 */
	public void setMcode(long mcode) {
		this.mcode = mcode;
	}

	/**
	 * Sets the value of mcodeNull
	 */
	public void setMcodeNull(boolean mcodeNull) {
		this.mcodeNull = mcodeNull;
	}

	/**
	 * Gets the value of mcodeNull
	 */
	public boolean isMcodeNull() {
		return mcodeNull;
	}

	public java.lang.String getCompareType() {
		return compareType;
	}

	public void setCompareType(java.lang.String compareType) {
		this.compareType = compareType;
	}

	public long getPtvid() {
		return ptvid;
	}

	public void setPtvid(long ptvid) {
		this.ptvid = ptvid;
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

	public String getValue1RuleExpression() {
		return TPolicyDetailsWithRule.getRuleExpression(value1Rule);
	}

	public String getValue1RuleDisplayInfo() {
		return TPolicyDetailsWithRule.getRuleDisplayInfo(value1Rule, value1);
	}

	public String getValue2RuleExpression() {
		return TPolicyDetailsWithRule.getRuleExpression(value2Rule);
	}

	public String getValue2RuleDisplayInfo() {
		return TPolicyDetailsWithRule.getRuleDisplayInfo(value2Rule, value2);
	}

	public String getValue1LowRuleExpression() {
		return TPolicyDetailsWithRule.getRuleExpression(value1LowRule);
	}

	public String getValue1LowRuleDisplayInfo() {
		return TPolicyDetailsWithRule.getRuleDisplayInfo(value1LowRule, value1low);
	}

	public String getValue2LowRuleExpression() {
		return TPolicyDetailsWithRule.getRuleExpression(value2LowRule);
	}

	public String getValue2LowRuleDisplayInfo() {
		return TPolicyDetailsWithRule.getRuleDisplayInfo(value2LowRule, value2low);
	}

	public TPolicyDetailsWithRule getPolicyDetailsWithRule() {
		return policyDetailsWithRule;
	}

	public void setPolicyDetailsWithRule(TPolicyDetailsWithRule policyDetailsWithRule) {
		this.policyDetailsWithRule = policyDetailsWithRule;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.ibm.ncs.model.dto.PolDetailDsp: ");
		ret.append("mpid=" + mpid);
		ret.append(", modid=" + modid);
		ret.append(", eveid=" + eveid);
		ret.append(", poll=" + poll);
		ret.append(", value1=" + value1);
		ret.append(", severity1=" + severity1);
		ret.append(", filterA=" + filterA);
		ret.append(", value2=" + value2);
		ret.append(", severity2=" + severity2);
		ret.append(", filterB=" + filterB);
		ret.append(", severityA=" + severityA);
		ret.append(", severityB=" + severityB);
		ret.append(", oidgroup=" + oidgroup);
		ret.append(", ogflag=" + ogflag);
		ret.append(", value1low=" + value1low);
		ret.append(", value2low=" + value2low);
		ret.append(", v1lseverity1=" + v1lseverity1);
		ret.append(", v1lseverityA=" + v1lseverityA);
		ret.append(", v2lseverity2=" + v2lseverity2);
		ret.append(", v2lseverityB=" + v2lseverityB);
		ret.append(", major=" + major);
		ret.append(", ecode=" + ecode);
		ret.append(", general=" + general);
		ret.append(", mname=" + mname);
		ret.append(", mcode=" + mcode);
		return ret.toString();
	}

}
