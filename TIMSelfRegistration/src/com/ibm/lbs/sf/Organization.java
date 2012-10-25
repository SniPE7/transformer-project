/**
 * 
 */
package com.ibm.lbs.sf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class Organization {

  private String name = null;
  private String dn = null;
  private String id = null;
  private List<Organization> children = new ArrayList<Organization>();
  private Organization parrent = null;
  /**
   * 
   */
  public Organization() {
    super();
  }

  public Organization(String name, String dn) {
    super();
    this.name = name;
    this.dn = dn;
  }

  public Organization(String name, String dn, List<Organization> children) {
    super();
    this.name = name;
    this.dn = dn;
    this.children = children;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDn() {
    return dn;
  }

  public void setDn(String dn) {
    this.dn = dn;
  }

  public List<Organization> getChildren() {
    return children;
  }

  public void setChildren(List<Organization> children) {
    this.children = children;
  }

  public Organization getParrent() {
    return parrent;
  }

  public void setParrent(Organization parrent) {
    if (parrent == null) {
      return;
    }
    this.parrent = parrent;
    this.parrent.getChildren().add(this);
  }

}
