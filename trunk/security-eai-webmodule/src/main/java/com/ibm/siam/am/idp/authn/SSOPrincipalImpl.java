/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author zhaodonglu
 * 
 */
public class SSOPrincipalImpl implements Principal, SSOPrincipal {

  private static final String AUTHEN_LEVEL_FOR_UNAUTHEN = "0";

  private List<String> authenMethods = new ArrayList<String>();

  private String uid = null;
  private String cn = null;

  private Set<String> finishedAuthenLevels = new TreeSet<String>();

  private Map<String, List<String>> attributes = new HashMap<String, List<String>>();

  /**
   * @param uid
   * @param cn
   */
  public SSOPrincipalImpl(String authenMethod, String uid, String cn) {
    super();
    this.authenMethods.add(authenMethod);
    this.uid = uid;
    this.cn = cn;
  }

  public String getMaxSucceedAuthenLevel() {
    List<String> result = new ArrayList<String>(this.finishedAuthenLevels);
    return (result != null && result.size() > 0) ? result.get(result.size() - 1) : AUTHEN_LEVEL_FOR_UNAUTHEN;
  }

  public void addSuccessAuthenLevel(String successAuthenLevel) {
    this.finishedAuthenLevels.add(successAuthenLevel);
  }

  /**
   * 
   */
  public SSOPrincipalImpl() {
    super();
  }

  /** {@inheritDoc} */
  public String getUid() {
    return uid;
  }

  /**
   * @param uid
   *          the uid to set
   */
  public void setUid(String uid) {
    this.uid = uid;
  }

  /** {@inheritDoc} */
  public String getCn() {
    return cn;
  }

  /**
   * @param cn
   *          the cn to set
   */
  public void setCn(String cn) {
    this.cn = cn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.security.Principal#getName()
   */
  public String getName() {
    return this.uid;
  }

  /** {@inheritDoc} */
  public String getLastAuthenMethod() {
    return (this.authenMethods != null && this.authenMethods.size() > 0) ? this.authenMethods.get(this.authenMethods
        .size() - 1) : "";
  }

  /** {@inheritDoc} */
  public boolean containsAuthenMethod(String authenMethod) {
    return this.authenMethods.contains(authenMethod);
  }

  public void addSuccessAuthenMethod(String successAuthenMethod) {
    this.authenMethods.add(successAuthenMethod);
  }

  public void setAttribute(String name, List<String> values) {
    this.attributes.put(name, values);
  }

  /** {@inheritDoc} */
  public List<String> getAttributeNames() {
    List<String> names = new ArrayList<String>();
    if (attributes != null && !attributes.isEmpty()) {
      names.addAll(attributes.keySet());
    }
    return names;
  }

  public String getSingleValue(String attrName) {
    if (attrName == null || attrName.length() == 0) {
      throw new IllegalArgumentException("attrName can not be empty.");
    }
    if (attributes == null) {
      return null;
    }
    
    List<String> values = this.getValueAsList(attrName);
    if (values == null || values.isEmpty()) {
       return null;
    }
    return values.get(0);
  }

  public List<String> getValueAsList(String attrName) {
    if (attrName == null || attrName.length() == 0) {
      throw new IllegalArgumentException("attrName can not be empty.");
    }
    return attributes.get(attrName);
  }

  @Override
  public String toString() {
    return String.format("SSOPrincipalImpl [uid=%s, cn=%s, attributes=%s, finishedAuthenLevels=%s, authenMethods=%s]", uid, cn, attributes, finishedAuthenLevels, authenMethods);
  }

}
