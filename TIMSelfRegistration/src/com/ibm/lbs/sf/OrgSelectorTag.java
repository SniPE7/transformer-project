package com.ibm.lbs.sf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author zhangpeng 提供获取版本信息tag
 */
public class OrgSelectorTag extends TagSupport {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1L;

  private PageContext pageContext;

  private int deepth = 5;
  private String boxElementIdPrefix = "level";
  private String elementId = "l";
  private String elementName = "l";
  private String label4BlankOption = "Select an item";
  private boolean showBoxResult = true;

  private String ldapUrl = "ldap://127.0.0.1:3890";
  private String ldapBaseDn = "ou=jke, DC=ITIM";
  private String ldapBindDn = null;
  private String ldapBindPassword = null;

  private int cacheExpireInSeconds = 1800;

  private static OrganizationService orgnizationService;

  public OrgSelectorTag() {
    super();
  }

  public int getDeepth() {
    return deepth;
  }

  public void setDeepth(int deepth) {
    this.deepth = deepth;
  }

  public String getBoxElementIdPrefix() {
    return boxElementIdPrefix;
  }

  public void setBoxElementIdPrefix(String boxElementIdPrefix) {
    this.boxElementIdPrefix = boxElementIdPrefix;
  }

  public String getElementId() {
    return elementId;
  }

  public void setElementId(String elementId) {
    this.elementId = elementId;
  }

  public String getElementName() {
    return elementName;
  }

  public void setElementName(String elementName) {
    this.elementName = elementName;
  }

  public String getLabel4BlankOption() {
    return label4BlankOption;
  }

  public void setLabel4BlankOption(String label4BlankOption) {
    this.label4BlankOption = label4BlankOption;
  }

  public boolean isShowBoxResult() {
    return showBoxResult;
  }

  public void setShowBoxResult(boolean showBoxResult) {
    this.showBoxResult = showBoxResult;
  }

  public void setPageContext(PageContext pageContext) {
    this.pageContext = pageContext;
  }

  public String getLdapUrl() {
    return ldapUrl;
  }

  public void setLdapUrl(String ldapUrl) {
    this.ldapUrl = ldapUrl;
  }

  public String getLdapBaseDn() {
    return ldapBaseDn;
  }

  public void setLdapBaseDn(String ldapBaseDn) {
    this.ldapBaseDn = ldapBaseDn;
  }

  public String getLdapBindDn() {
    return ldapBindDn;
  }

  public void setLdapBindDn(String ldapBindDn) {
    this.ldapBindDn = ldapBindDn;
  }

  public String getLdapBindPassword() {
    return ldapBindPassword;
  }

  public void setLdapBindPassword(String ldapBindPassword) {
    this.ldapBindPassword = ldapBindPassword;
  }

  public int getCacheExpireInSeconds() {
    return cacheExpireInSeconds;
  }

  public void setCacheExpireInSeconds(int cacheExpireInSeconds) {
    this.cacheExpireInSeconds = cacheExpireInSeconds;
  }

  public int doEndTag() throws JspException {
    return EVAL_PAGE;
  }

  private void doOutputFormElements(List<Organization> topNodes, JspWriter out) throws Exception {
    for (int i = 1; i < this.deepth + 1; i++) {
      String levelValue = this.pageContext.getRequest().getParameter(this.boxElementIdPrefix + i);
      if (i == 1 || (levelValue != null && levelValue.trim().length() > 0)) {
        out.println(String.format("<select id=\"%s%s\" name=\"%s%s\" onchange=\"__initNextLevelSelectBox(this.options[this.selectedIndex].value, %s)\">", boxElementIdPrefix, i,
            boxElementIdPrefix, i, i));
      } else {
        out.println(String.format(
            "<select id=\"%s%s\" name=\"%s%s\" onchange=\"__initNextLevelSelectBox(this.options[this.selectedIndex].value, %s)\" style=\"display:none\"><option value=\"\">",
            boxElementIdPrefix, i, boxElementIdPrefix, i, i));
      }
      out.println(String.format("  <option value=\"\">%s</option>", this.label4BlankOption));
      if (i == 1 || (levelValue != null && levelValue.trim().length() > 0)) {
        List<Organization> nodes = null;
        if (i == 1) {
          nodes = topNodes;
        } else {
          nodes = this.findBrothersById(topNodes, topNodes, levelValue);
        }
        for (Organization o : nodes) {
          if (o.getId().equals(levelValue)) {
            out.println(String.format("  <option value=\"%s\" selected>%s</option>", o.getId(), o.getName()));
          } else {
            out.println(String.format("  <option value=\"%s\">%s</option>", o.getId(), o.getName()));
          }
        }
      }
      out.println(String.format("</select>"));
    }
    String v = this.pageContext.getRequest().getParameter(this.elementName);
    out.println(String.format("<input type=\"%s\" name=\"%s\" id=\"%s\" value=\"%s\"/>", (this.showBoxResult) ? "text" : "hidden", this.elementId, this.elementName,
        (v != null) ? v : ""));
  }

  private List<Organization> getTopOrganizations() throws Exception {
    OrganizationService service = getOrganizationService();
    return service.getAllOrganization();
  }

  private OrganizationService getOrganizationService() {
    synchronized (OrgSelectorTag.class) {
      if (orgnizationService == null) {
        orgnizationService = new OrganizationServiceImpl(this.ldapUrl, this.ldapBaseDn, this.ldapBindDn, this.ldapBindPassword);
        if (this.cacheExpireInSeconds > 0) {
          orgnizationService = new CachableOrgnizationServiceImpl(orgnizationService, this.cacheExpireInSeconds);
  
        }
      }
      return orgnizationService;
    }
  }

  private void doOutputCommonJs(JspWriter out) throws IOException {
    out.println("<script language=\"javascript\" type=\"text/javascript\">");
    BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/com/ibm/lbs/sf/common.js"), "UTF-8"));
    String line = reader.readLine();
    while (line != null) {
      out.println(line);
      line = reader.readLine();
    }
    out.flush();
    reader.close();
    out.println("</script>");
  }

  private void doOutputDataJs(List<Organization> topNodes, JspWriter out) throws Exception {
    out.println("<script language=\"javascript\" type=\"text/javascript\">");
    OrganizationJSConverter converter = new OrganizationJSConverter();

    converter.convert(out, topNodes);
    out.println("</script>");
  }

  private List<Organization> findBrothersById(List<Organization> topNodes, List<Organization> nodes, String id) throws Exception {
    if (nodes == null || nodes.size() == 0) {
      return new ArrayList<Organization>();
    }
    for (Organization o : nodes) {
      if (o.getId().equalsIgnoreCase(id)) {
        if (o.getParrent() == null) {
          return topNodes;
        } else {
          return o.getParrent().getChildren();
        }
      }
      if (o.getChildren().size() > 0) {
        List<Organization> result = this.findBrothersById(topNodes, o.getChildren(), id);
        if (result.size() > 0) {
          return result;
        }
      }
    }
    return new ArrayList<Organization>();
  }

  private void doOutputInitJs(JspWriter out) throws IOException {
    out.println("<script language=\"javascript\" type=\"text/javascript\">");
    out.println(String.format("var _box_id_prefix = \"%s\";", this.boxElementIdPrefix));
    out.println(String.format("var _box_max_deepth = %s;", this.deepth));
    out.println(String.format("var _box_final_value_id = \"%s\"", this.elementId));
    out.println(String.format("var _default_select_hint = \"%s\"", this.label4BlankOption));
    // out.println("__initNextLevelSelectBox(null, 0);");
    out.println("</script>");
  }

  public int doStartTag() throws JspException {
    try {
      JspWriter out = pageContext.getOut();
      
      List<Organization> topOrganizations = getTopOrganizations();
      this.doOutputFormElements(topOrganizations, out);
      out.flush();
      this.doOutputCommonJs(out);
      out.flush();
      this.doOutputDataJs(topOrganizations, out);
      out.flush();
      this.doOutputInitJs(out);
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return EVAL_PAGE;
  }

}
