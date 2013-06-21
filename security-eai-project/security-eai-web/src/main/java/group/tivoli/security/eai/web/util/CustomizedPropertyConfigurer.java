package group.tivoli.security.eai.web.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Spring Property 属性获取类
 * @author xuhong
 * @since 2012-12-4 下午4:03:34
 */

public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {
  private static Map<String, String> ctxPropertiesMap;  
  
  @Override  
  protected void processProperties(ConfigurableListableBeanFactory beanFactory,  
          Properties props)throws BeansException {  

      super.processProperties(beanFactory, props);  
      //load properties to ctxPropertiesMap  
      ctxPropertiesMap = new HashMap<String, String>();  
      for (Object key : props.keySet()) {  
          String keyStr = key.toString();  
          String value = props.getProperty(keyStr);  
          ctxPropertiesMap.put(keyStr, value);  
      }  
  }  

  //static method for accessing context properties  
  public static String getContextProperty(String name) {  
      return ctxPropertiesMap.get(name);
  }  
}
