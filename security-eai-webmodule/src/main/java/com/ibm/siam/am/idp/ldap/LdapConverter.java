package com.ibm.siam.am.idp.ldap;

import java.util.List;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.ModificationItem;

import org.springframework.util.CollectionUtils;

import com.ibm.siam.am.idp.ldap.entity.DirectoryEntity;

/**
 * <p>�ļ�����: LdapConverter.java</p>
 * <p>�ļ�����: Ldap�������ת����</p>
 * <p>��Ȩ����: ��Ȩ����(C)2010</p>
 * <p>����ժҪ: ��Ҫ�������ļ������ݣ�������Ҫģ�顢�������ܵ�˵��</p>
 * <p>����˵��: �������ݵ�˵��</p>
 * <p>�������: 2012-2-17</p>
 * <p>�޸ļ�¼1:</p>
 * <pre>
 *    �޸�����:
 *    �� �� ��:
 *    �޸�����:
 * </pre>
 * <p>�޸ļ�¼2����</p>
 * @author  Wuqingming
 */
public class LdapConverter {

	/**
	 * 
	 * @author��Wuqingming
	 * @date��2012-3-23
	 * @Description����Ŀ¼ʵ�����תΪĿ¼�е�����
	 * @param entity Ŀ¼ʵ�����
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
   * @author��Wuqingming           
   * @date��2012-3-26
   * @Description�������޸ĵ�����ת��ΪĿ¼�޸�����
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

