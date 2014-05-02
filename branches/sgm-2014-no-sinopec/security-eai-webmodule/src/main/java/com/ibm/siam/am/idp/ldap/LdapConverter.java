package com.ibm.siam.am.idp.ldap;

import java.util.List;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.ModificationItem;

import org.springframework.util.CollectionUtils;

import com.ibm.siam.am.idp.ldap.entity.DirectoryEntity;

/**
 * <p>文件名称: LdapConverter.java</p>
 * <p>文件描述: Ldap相关数据转换器</p>
 * <p>版权所有: 版权所有(C)2010</p>
 * <p>内容摘要: 简要描述本文件的内容，包括主要模块、函数及能的说明</p>
 * <p>其他说明: 其它内容的说明</p>
 * <p>完成日期: 2012-2-17</p>
 * <p>修改记录1:</p>
 * <pre>
 *    修改日期:
 *    修 改 人:
 *    修改内容:
 * </pre>
 * <p>修改记录2：…</p>
 * @author  Wuqingming
 */
public class LdapConverter {

	/**
	 * 
	 * @author：Wuqingming
	 * @date：2012-3-23
	 * @Description：将目录实体对象转为目录中的属性
	 * @param entity 目录实体对象
	 * @return
	 */
	public static Attributes convertDirectoryEntity2Attributes (DirectoryEntity entity) {
		Attributes attributes = new BasicAttributes();
		if (entity == null) {
			return attributes;
		}
		List<String> attrNames = entity.getAttributeNames();
		if (CollectionUtils.isEmpty(attrNames)) {
			return attributes;
		}
		for (String attrName : attrNames) {
			String[] values = entity.getValueAsStringArray(attrName);
			if (values == null || values.length == 0) {
				attributes.put(attrName, null);
				continue;
			} else if (values.length == 1) {
				attributes.put(attrName, values[0]);
			} else {
				BasicAttribute multiValueAttr = new BasicAttribute(attrName);
				for (String value : values) {
					multiValueAttr.add(value);
				}
				attributes.put(multiValueAttr);
			}
		}
		return attributes;
	}
	
	/**
   * 
   * @author：Wuqingming           
   * @date：2012-3-26
   * @Description：将待修改的属性转化为目录修改属性
   * @param attributes
   * @return
   */
  public static ModificationItem[] convertModifyAttribute2ModificationItem (ModifyAttribute[] attributes) {
    if (attributes == null || attributes.length == 0) {
      return null;
    }
    ModificationItem[] items = new ModificationItem[attributes.length];
    int cursor = 0;
    for (ModifyAttribute attribute : attributes) {
      BasicAttribute basicAttribute = new BasicAttribute(attribute.getAttrName());
      List<String> values = attribute.getAttrValue();
      if (values == null || values.size() == 0) {
        
      } else if (values.size() == 1) {
        basicAttribute.add(values.get(0));
      } else {
        for (String value : values) {
          basicAttribute.add(value);
        }
      }
      items[cursor++] = new ModificationItem(attribute.getMod_op(), basicAttribute);
    }
    return items;
  }
	
}

