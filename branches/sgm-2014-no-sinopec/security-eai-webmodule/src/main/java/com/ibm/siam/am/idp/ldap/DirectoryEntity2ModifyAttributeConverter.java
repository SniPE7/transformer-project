package com.ibm.siam.am.idp.ldap;

import java.util.Arrays;
import java.util.List;

import com.ibm.siam.am.idp.ldap.entity.DirectoryEntity;

/**
 * <p>文件名称: DirectoryEntity2ModifyAttributeConverter.java</p>
 * <p>文件描述: 目录实体转为可修改的属性</p>
 * <p>版权所有: 版权所有(C)2010</p>
 * <p>内容摘要: 简要描述本文件的内容，包括主要模块、函数及能的说明</p>
 * <p>其他说明: 其它内容的说明</p>
 * <p>完成日期: 2012-3-26</p>
 * <p>修改记录1:</p>
 * <pre>
 *    修改日期:
 *    修 改 人:
 *    修改内容:
 * </pre>
 * <p>修改记录2：…</p>
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

