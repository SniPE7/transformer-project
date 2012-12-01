package com.ibm.ncs.web.policyapply.bean;

import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyDetails;

import java.io.Serializable;

public class NonTimeFramePolicyDetails implements Serializable
{
	TPolicyBase policyBase;
	TEventTypeInit eventTypeInit;
	TModuleInfoInit moduleInfoInit;
	TPolicyDetails policyDetails;
	
	/*
	 * message to indicate which object is null
	 * values:
	 * 		
	 */	
	String message;		
	
	
	
	public String getPOLICY_BASE_IS_NULL() {
		return "POLICY_BASE_IS_NULL";
	}

	public String getEVENT_TYPE_INIT_IS_NULL() {
		return  "EVENT_TYPE_INIT_IS_NULL";
	}

	public String getMODULE_INFO_INIT_IS_NULL() {
		return "MODULE_INFO_INIT_IS_NULL";
	}

	public String getPOLICY_DETAILS_IS_NULL() {
		return "POLICY_DETAILS_IS_NULL";
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TPolicyDetails getPolicyDetails() {
		return policyDetails;
	}

	public void setPolicyDetails(TPolicyDetails policyDetails) {
		this.policyDetails = policyDetails;
	}

	public TPolicyBase getPolicyBase() {
		return policyBase;
	}

	public void setPolicyBase(TPolicyBase policyBase) {
		this.policyBase = policyBase;
	}

	public TEventTypeInit getEventTypeInit() {
		return eventTypeInit;
	}

	public void setEventTypeInit(TEventTypeInit eventTypeInit) {
		this.eventTypeInit = eventTypeInit;
	}

	public TModuleInfoInit getModuleInfoInit() {
		return moduleInfoInit;
	}

	public void setModuleInfoInit(TModuleInfoInit moduleInfoInit) {
		this.moduleInfoInit = moduleInfoInit;
	}
}
