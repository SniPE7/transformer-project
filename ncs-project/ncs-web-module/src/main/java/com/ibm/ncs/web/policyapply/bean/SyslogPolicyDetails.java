package com.ibm.ncs.web.policyapply.bean;

import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.TPolicyBase;
import java.io.Serializable;

public class SyslogPolicyDetails implements Serializable
{
	TPolicyBase policyBase;
	PolicySyslog policySyslog;
	
	/*
	 * message to indicate which object is null
	 * values:
	 * 		
	 */	
	String message;		
	
	
	
	public String getPOLICY_BASE_IS_NULL() {
		return "POLICY_BASE_IS_NULL";
	}

	public String getPOLICY_SYSLOG_IS_NULL() {
		return  "POLICY_SYSLOG_IS_NULL";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TPolicyBase getPolicyBase() {
		return policyBase;
	}

	public void setPolicyBase(TPolicyBase policyBase) {
		this.policyBase = policyBase;
	}

	public PolicySyslog getPolicySyslog() {
		return policySyslog;
	}

	public void setPolicySyslog(PolicySyslog policySyslog) {
		this.policySyslog = policySyslog;
	}

}
