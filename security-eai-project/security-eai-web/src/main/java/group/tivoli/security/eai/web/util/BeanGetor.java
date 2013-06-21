package group.tivoli.security.eai.web.util;

import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * 类（接口）继承、实现、功能等描述
 * 
 * @author xuhong
 * @since 2012-12-5 下午11:14:57
 */

public class BeanGetor {
  public static Object getBean(String beanName) throws ServletException {
    // Get Spring Bean Factory
    ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
    return appContext.getBean(beanName);
  }
}
