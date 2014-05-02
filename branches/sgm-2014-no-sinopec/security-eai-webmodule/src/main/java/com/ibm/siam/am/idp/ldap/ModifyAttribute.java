package com.ibm.siam.am.idp.ldap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * �ļ�����: ModifyAttribute.java
 * </p>
 * <p>
 * �ļ�����: ���޸ĵ�����
 * </p>
 * <p>
 * ��Ȩ����: ��Ȩ����(C)2010
 * </p>
 * <p>
 * ����ժҪ: ��Ҫ�������ļ������ݣ�������Ҫģ�顢�������ܵ�˵��
 * </p>
 * <p>
 * ����˵��: �������ݵ�˵��
 * </p>
 * <p>
 * �������: 2012-3-26
 * </p>
 * <p>
 * �޸ļ�¼1:
 * </p>
 * 
 * <pre>
 *    �޸�����:
 *    �� �� ��:
 *    �޸�����:
 * </pre>
 * <p>
 * �޸ļ�¼2����
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
	 * ����ӵ�����
	 */
	public final static int ADD_ATTRIBUTE = 1;
	/**
	 * ���޸ĵ�����
	 */
	public final static int REPLACE_ATTRIBUTE = 2;
	/**
	 * ��ɾ��������
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
