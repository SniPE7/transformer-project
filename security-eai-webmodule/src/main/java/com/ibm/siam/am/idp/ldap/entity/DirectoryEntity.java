package com.ibm.siam.am.idp.ldap.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * Ŀ¼ʵ����
 * 
 * @author Booker
 */
public class DirectoryEntity implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -9140581132486712534L;

  private Map<String, Object> attributes = new HashMap<String, Object>();

  private String dn;

  /**
   * ��������ֵ
   * 
   * @param attrName
   *          ��������
   * @param attrValue
   *          ����ֵ
   */
  public void setValue(String attrName, Object attrValue) {
    Assert.hasText(attrName, "attrName can not be empty.");
    if (attributes == null) {
      attributes = new HashMap<String, Object>();
    }
    attributes.put(attrName, attrValue);
  }

  /**
   * �������ֵ�������Դ��ڽ���������ֵ�������ۼ�
   * 
   * @param attrName
   *          ��������
   * @param attrValue
   *          ����ֵ
   * @param ignoreEmptyValue
   *          ���Կ�ֵ
   */
  @SuppressWarnings("unchecked")
  public void addValue(String attrName, Object attrValue, boolean ignoreEmptyValue) {
    Assert.hasText(attrName, "attrName can not be empty.");
    if (ignoreEmptyValue && attrValue == null || attrValue.toString().length() == 0) {
      return;
    }
    if (attributes == null) {
      attributes = new HashMap<String, Object>();
    }
    Object _value = null;
    Object _originValue = attributes.get(attrName); // ����ԭ��ֵ
    boolean containAttr = containAttribute(attrName); // �ж�ʵ���Ƿ����
    if (_originValue != null) {
      if (_originValue instanceof List) {
        ((List<Object>) _originValue).add(attrValue);
        _value = _originValue;
      } else {
        List<Object> list = new ArrayList<Object>(2);
        list.add(_originValue);
        list.add(attrValue);
        _value = list;
      }
    } else if (containAttr) {
      List<Object> list = new ArrayList<Object>(2);
      list.add(_originValue);
      list.add(attrValue);
      _value = list;
    } else {
      _value = attrValue;
    }
    attributes.put(attrName, _value);
  }

  /**
   * ��ȡ����ֵ
   * 
   * @param attrName
   *          ��������
   * @return
   */
  public Object getValue(String attrName) {
    Assert.hasText(attrName, "attrName can not be empty.");
    if (attributes == null) {
      return null;
    }
    return attributes.get(attrName);
  }

  /**
   * ��ȡ����ֵ���������ֵΪ�����򷵻ؼ����еĵ�һ��ֵ
   * 
   * @param attrName
   *          ��������
   * @return
   */
  @SuppressWarnings("unchecked")
  public Object getSingleValue(String attrName) {
    Assert.hasText(attrName, "attrName can not be empty.");
    if (attributes == null) {
      return null;
    }

    // ldap ��Сд������
    Object attrValue = null;
    Iterator<String> nameIt = attributes.keySet().iterator();

    while (nameIt.hasNext()) {
      String tmpName = nameIt.next();

      if (attrName.equalsIgnoreCase(tmpName)) {
        attrValue = attributes.get(tmpName);
        break;
      }
    }

    if (attrValue == null) {
      return null;
    } else if (attrValue instanceof List) {
      return ((List<Object>) attrValue).isEmpty() ? null : ((List<Object>) attrValue).get(0);
    } else {
      return attrValue;
    }
  }

  /**
   * ��ȡ����ֵ��ת��Ϊ�ַ���
   * 
   * @param attrName
   *          ��������
   * @return
   */
  public String getValueAsString(String attrName) {
    Assert.hasText(attrName, "attrName can not be empty.");
    Object attrValue = getSingleValue(attrName);
    return attrValue == null ? null : attrValue.toString();
  }

  /**
   * ��ȡ����ֵ��ת��Ϊ�ַ�������
   * 
   * @param attrName
   *          ��������
   * @return
   */
  public String[] getValueAsStringArray(String attrName) {
    Assert.hasText(attrName, "attrName can not be empty.");
    List<Object> valueList = getValueAsList(attrName);
    if (CollectionUtils.isEmpty(valueList)) {
      return null;
    }
    String[] result = new String[valueList.size()];
    int cusor = 0;
    for (Object value : valueList) {
      result[cusor++] = value == null ? "" : value.toString();
    }
    return result;
  }

  /**
   * ��ȡ����ֵ��תΪList��ʽ
   * 
   * @param attrName
   *          ��������
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Object> getValueAsList(String attrName) {
    Assert.hasText(attrName, "attrName can not be empty.");
    List<Object> result = null;
    if (attributes == null) {
      return null;
    }
    Object attrValue = attributes.get(attrName);
    if (attrValue == null) {
      return null;
    }
    if (attrValue instanceof List) {
      return (List<Object>) attrValue;
    }
    if (attrValue instanceof String[]) {
      Object[] attrValueStrArray = (String[]) attrValue;
      return Arrays.asList(attrValueStrArray);
    }
    result = new ArrayList<Object>(1);
    result.add(attrValue);
    return result;
  }

  /**
   * ��ȡ������������
   * 
   * @return
   */
  @JsonIgnore
  public List<String> getAttributeNames() {
    if (attributes == null || attributes.isEmpty()) {
      return null;
    }
    List<String> names = new ArrayList<String>();
    names.addAll(attributes.keySet());
    return names;
  }

  /**
   * �ж��Ƿ����ĳ����
   * 
   * @param attrName
   * @return
   */
  public boolean containAttribute(String attrName) {
    Assert.hasText(attrName, "attrName can not be empty.");
    if (attributes != null && !CollectionUtils.isEmpty(attributes.keySet()) && attributes.keySet().contains(attrName)) {
      return true;
    }
    return false;
  }

  /**
   * ��ȡʵ����������
   * 
   * @return
   */
  /*
   * private Map<String, Object> getAttributes() { return attributes; }
   */

  public String getDn() {
    return dn;
  }

  public void setDn(String dn) {
    this.dn = dn;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
