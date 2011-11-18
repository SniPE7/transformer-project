package com.ibm.tivoli.cmcc.handler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

public abstract class BaseProcessor extends AbstractProcessor {

  public Object parseRequest(Object request, String in) throws IOException, SAXException {
    Digester digester = getDigester();
    digester.push(request);
    try {
      if (!in.trim().toLowerCase().startsWith("<?xml")) {
        in = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + in;
      }
      digester.parse(new StringReader(in));
      return request;
    } catch (IOException e) {
      throw e;
    } catch (SAXException e) {
      throw e;
    }
  }

  public BaseProcessor() {
    super();
  }

  public BaseProcessor(Properties properties) {
    super(properties);
  }

  public String process(String in) throws Exception {
    Object resp = doBusiness(in);
    
    String templateFile = getTemplateFile();
    String result = getTemplate(templateFile);

    result = evaluateTemplate(result, "", BeanUtils.describe(resp));
    result = evaluateTemplate(result, "", this.getProperties());
    result = evaluateTemplate(result, "", this.getProperties());
    
    result = postEvaluateTemplate(result);
    
    // Clear all of unused variable
    result = result.replaceAll("\\$\\{[a-zA-Z0-9.]*\\}", "");

    return result;
  }

  protected String evaluateTemplate(String template, String nameSpace, Map map) {
    Set names = map.keySet();
    Iterator i = names.iterator();
    while (i.hasNext()) {
          String name = (String)i.next();
          String value = "";
          if (null != map.get(name)) {
             value = map.get(name).toString();
          }
          String macroName = (StringUtils.isEmpty(nameSpace))?"${" + name + "}":"${" + nameSpace + "." + name + "}";
          template = StringUtils.replace(template, macroName, value);
    }
    return template;
  }

  private String getTemplate(String templateFile) throws IOException {
    InputStream ins = null;
    if (templateFile.toLowerCase().startsWith("classpath:")) {
       ins = this.getClass().getResourceAsStream(templateFile.substring("classpath:".length()));
    } else {
      String file = templateFile;
      if (templateFile.toLowerCase().startsWith("file:")) {
         file = templateFile.substring("file:".length());
      }
      ins = new FileInputStream(file);
    }
    BufferedReader template = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
    StringWriter out = new StringWriter();
    String line = template.readLine();
    while (line != null) {
      out.write(line + "\n");
      line = template.readLine();
    }
    String result = out.toString();
    return result;
  }
  
  protected Digester getDigester() {
    Digester digester = new Digester();
    digester.setNamespaceAware(false);
    digester.setValidating(false);

    this.setDigesterRules(digester);
    return digester;

  }
  
  /**
   * Hook point for post process of template, you can override this method in subclass
   * @param result
   * @return
   */
  protected String postEvaluateTemplate(String result) {
    return result;
  }
  
  protected abstract Digester setDigesterRules(Digester digester);

  protected abstract String getTemplateFile();

  protected abstract Object doBusiness(String in) throws IOException, SAXException;
  
}