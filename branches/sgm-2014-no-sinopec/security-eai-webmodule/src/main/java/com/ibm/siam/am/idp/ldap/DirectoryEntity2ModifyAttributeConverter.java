package com.ibm.siam.am.idp.ldap;

import java.util.Arrays;
import java.util.List;

import com.ibm.siam.am.idp.ldap.entity.DirectoryEntity;

/**
 * <p>�ļ�����: DirectoryEntity2ModifyAttributeConverter.java</p>
 * <p>�ļ�����: Ŀ¼ʵ��תΪ���޸ĵ�����</p>
 * <p>��Ȩ����: ��Ȩ����(C)2010</p>
 * <p>����ժҪ: ��Ҫ�������ļ������ݣ�������Ҫģ�顢�������ܵ�˵��</p>
 * <p>����˵��: �������ݵ�˵��</p>
 * <p>�������: 2012-3-26</p>
 * <p>�޸ļ�¼1:</p>
 * <pre>
 *    �޸�����:
 *    �� �� ��:
 *    �޸�����:
 * </pre>
 * <p>�޸ļ�¼2����</p>
 * @author  Wuqingming
 */
public class DirectoryEntity2ModifyAttributeConverter {

	public static ModifyAttribute[] convert (DirectoryEntity entity) {
		if (entity == null) {
			return null;
		}
		List<String> attrNames = entity.getAttributeNames();
		if (attrNames == null || attrNames.size() == 0) {
			return null;
		}
		ModifyAttribute[] attributes = new ModifyAttribute[attrNames.size()];
		int cursor = 0;
		for (String attrName : attrNames) {
			ModifyAttribute attribute = new ModifyAttribute(attrName);
			String[] values = entity.getValueAsStringArray(attrName);
			List<String> attrValue = values == null ? null : Arrays.asList(values);
			attribute.setAttrValue(attrValue);
			attributes[cursor++] = attribute;
		}
		return attributes;
	}
}

