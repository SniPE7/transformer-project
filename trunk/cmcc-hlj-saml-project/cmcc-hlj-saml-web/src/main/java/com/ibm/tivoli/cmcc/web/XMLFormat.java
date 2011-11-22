package com.ibm.tivoli.cmcc.web;

import java.io.IOException;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XMLFormat {
  public static String formatXml(String str) throws DocumentException, IOException {
    Document document = null;
    document = DocumentHelper.parseText(str);
    // 格式化输出格式
    OutputFormat format = OutputFormat.createPrettyPrint();
    format.setEncoding("UTF-8");
    StringWriter writer = new StringWriter();
    // 格式化输出流
    XMLWriter xmlWriter = new XMLWriter(writer, format);
    // 将document写入到输出流
    xmlWriter.write(document);
    xmlWriter.close();
    return writer.toString();
   }
}
