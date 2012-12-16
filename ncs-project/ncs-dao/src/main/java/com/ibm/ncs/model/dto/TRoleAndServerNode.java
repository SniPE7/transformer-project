package com.ibm.ncs.model.dto;

import java.io.Serializable;

public class TRoleAndServerNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5015732348570802194L;

	private String roleName = null;
	private TServerNode targetServerNode = null;

	public TRoleAndServerNode() {
		super();
	}

	public TRoleAndServerNode(String roleName, TServerNode targetServerNode) {
	  super();
	  this.roleName = roleName;
	  this.targetServerNode = targetServerNode;
  }

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public TServerNode getTargetServerNode() {
		return targetServerNode;
	}

	public void setTargetServerNode(TServerNode targetServerNode) {
		this.targetServerNode = targetServerNode;
	}

}
