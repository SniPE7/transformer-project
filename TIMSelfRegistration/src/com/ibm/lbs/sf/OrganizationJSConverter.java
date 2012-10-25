/**
 * 
 */
package com.ibm.lbs.sf;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class OrganizationJSConverter {

  /**
   * 
   */
  public OrganizationJSConverter() {
    super();
  }

  public void convert(Writer out, List<Organization> topNodes) {
    PrintWriter writer = new PrintWriter(out);
    writer.format("// Top nodes\n");
    writer.format("var topNodes = new ArrayList();\n");
    writer.format("// node and child Map\n");
    writer.format("var nodeAndChildrenMap = new Map();\n");
    writer.format("// node id and node Map\n");
    writer.format("var idAndNodeMap = new Map();\n");
    writer.format("\n");
    writer.flush();
    writeNodes(topNodes, writer);
  }

  private void writeNodes(List<Organization> topNodes, PrintWriter writer) {
    for (Organization o : topNodes) {
      writer.format("\n");
      writer.format("node%s = new Object();\n", o.getId());
      writer.format("node%s.name = '%s';\n", o.getId(), o.getName());
      writer.format("node%s.dn = \"%s\";\n", o.getId(), o.getDn());
      writer.format("node%s.id = '%s';\n", o.getId(), o.getId());
      writer.format("idAndNodeMap.put(node%s.id, node%s);\n", o.getId(), o.getId());
      if (o.getParrent() == null) {
        writer.format("node%s.parentId = null;\n", o.getId());
        writer.format("topNodes.add(node%s);\n", o.getId());
      } else {
        writer.format("node%s.parentId = '%s';\n", o.getId(), o.getParrent().getId());
        writer.format("nodeAndChildrenMap.get(node%s.parentId).add(node%s);", o.getId(), o.getId());
      }
      if (o.getChildren() != null && o.getChildren().size() > 0) {
        writer.format("nodeAndChildrenMap.put(node%s.id, new ArrayList());\n", o.getId());
      }
      writer.format("\n");
      writer.flush();
      
      if (o.getChildren() != null && o.getChildren().size() > 0) {
        this.writeNodes(o.getChildren(), writer);
      }
      
    }
  }

}
