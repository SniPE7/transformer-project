package com.ibm.siam.am.idp.ldap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文件名称: ModifyAttribute.java
 * </p>
 * <p>
 * 文件描述: 待修改的属性
 * </p>
 * <p>
 * 版权所有: 版权所有(C)2010
 * </p>
 * <p>
 * 内容摘要: 简要描述本文件的内容，包括主要模块、函数及能的说明
 * </p>
 * <p>
 * 其他说明: 其它内容的说明
 * </p>
 * <p>
 * 完成日期: 2012-3-26
 * </p>
 * <p>
 * 修改记录1:
 * </p>
 * 
 * <pre>
 *    修改日期:
 *    修 改 人:
 *    修改内容:
 * </pre>
 * <p>
 * 修改记录2：…
 * </p>
 * 
 * @author Wuqingming
 */
public class ModifyAttribute implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8001054753673296646L;
	/**
	 * 待添加的属性
	 */
	public final static int ADD_ATTRIBUTE = 1;
	/**
	 * 待修改的属性
	 */
	public final static int REPLACE_ATTRIBUTE = 2;
	/**
	 * 待删除的属性
	 */
	public final static int REMOVE_ATTRIBUTE = 3;

	private String attrName;

	private List<String> attrValue;

	private int mod_op;

	public ModifyAttribute() {
		super();
		this.mod_op = REPLACE_ATTRIBUTE;
	}

	public ModifyAttribute(String attrName) {
		super();
		this.attrName = attrName;
		this.mod_op = REPLACE_ATTRIBUTE;
	}

	public ModifyAttribute(String attrName, int mod_op) {
		super();
		this.attrName = attrName;
		this.mod_op = mod_op;
	}

	public int getMod_op() {
		return mod_op;
	}

	public void setMod_op(int mod_op) {
		this.mod_op = mod_op;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public List<String> getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(List<String> attrValue) {
		this.attrValue = attrValue;
	}

	public void addValue(String value) {
		if (attrValue == null) {
			attrValue = new ArrayList<String>();
		}
		attrValue.add(value);
	}
}
