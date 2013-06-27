package com.sinopec.siam.am.idp.authn.module;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import junit.framework.TestCase;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;

import com.sinopec.siam.am.idp.authn.provider.tamldap.TAMCallbackHandler;
import com.sinopec.siam.am.idp.authn.service.TimDBUserPassServiceImpl;
import com.sinopec.siam.am.idp.utils.message.I18NMessageUtils;
import com.sinopec.siam.am.idp.utils.message.SpringI18NMessage;

/**
 * 用户密码过期，强制修改口令LoginModule（基本Ldap实现）测试用例
 * @author zhangxianwen
 * @since 2012-6-20 上午10:31:44
 */

public class UserPassPastDueLdapLoginModuleTest extends TestCase {

  /**
   * @param name
   */
  public UserPassPastDueLdapLoginModuleTest(String name) {
    super(name);
  }

  /** {@inheritDoc} */
  protected void setUp() throws Exception {
    super.setUp();
    
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:com/sinopec/siam/am/idp/web/messages");
    SpringI18NMessage springI18NMessage = new SpringI18NMessage(messageSource);
    I18NMessageUtils messageUtils = new I18NMessageUtils();
    messageUtils.setI18NMessage(springI18NMessage);
  }

  /** {@inheritDoc} */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testPastDue() throws Exception {

    Subject subject = new Subject();
    
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("test.do");
    TAMCallbackHandler cbh = new TAMCallbackHandler("test1", null, request);
    
    UserPassPastDueLdapLoginModule jaasLoginCtx = new UserPassPastDueLdapLoginModule();
    Map<String, String> options = new HashMap<String, String>();
    options.put("userPassServiceBeanId", "userPassServiceBeanId");
    options.put("userPassPolicyServiceBeanId", "userPassPolicyServiceBeanId");
    
    options.put("userPassPastDueTime", "1000");
    
    jaasLoginCtx.initialize(subject, cbh, null, options);
    jaasLoginCtx.setApplicationContext(new ApplicationContextBen());
    
    try {
      jaasLoginCtx.login();
    } catch (LoginException e) {
      assertEquals("sendTo:/modify_password.do", e.getMessage());
    }
  }
  
  public void testNotPastDue() throws Exception {

    Subject subject = new Subject();
    
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("test.do");
    TAMCallbackHandler cbh = new TAMCallbackHandler("test", null, request);
    UserPassPastDueLdapLoginModule jaasLoginCtx = new UserPassPastDueLdapLoginModule();
    Map<String, String> options = new HashMap<String, String>();
    options.put("userPassServiceBeanId", "userPassServiceBeanId");
    options.put("userPassPolicyServiceBeanId", "userPassPolicyServiceBeanId");
    
    jaasLoginCtx.initialize(subject, cbh, null, options);
    jaasLoginCtx.setApplicationContext(new ApplicationContextBen());
    
    try {
      jaasLoginCtx.login();
    } catch (LoginException e) {
      fail(e.getMessage());
    }
  }
  
  public void testChangPassword() throws Exception {
    Subject subject = new Subject();
    
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("test.do");
    request.setParameter("op", "resetpassword");
    request.setParameter("j_username", "aijb");
    request.setParameter("j_password", "000000");
    request.setParameter("j_new_password", "000000");
    
    TAMCallbackHandler cbh = new TAMCallbackHandler("test", null, request);
    UserPassPastDueLdapLoginModule jaasLoginCtx = new UserPassPastDueLdapLoginModule();
    Map<String, String> options = new HashMap<String, String>();
    options.put("userPassServiceBeanId", "userPassServiceBeanId");
    options.put("userPassPolicyServiceBeanId", "userPassPolicyServiceBeanId");
    options.put("timSoapEndpoint", "http://10.5.86.161:9080/ITIMWebServices");
    
    jaasLoginCtx.initialize(subject, cbh, null, options);
    jaasLoginCtx.setApplicationContext(new ApplicationContextBen());
    try {
      jaasLoginCtx.login();
    } catch (LoginException e) {
      fail(e.getMessage());
    }
  }

  public void testFailure() throws Exception {
    Subject subject = new Subject();
    
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("test.do");
    TAMCallbackHandler cbh = new TAMCallbackHandler(null, null, request);
    UserPassPastDueLdapLoginModule jaasLoginCtx = new UserPassPastDueLdapLoginModule();
    Map<String, String> options = new HashMap<String, String>();
    options.put("userPassServiceBeanId", "userPassServiceBeanId");
    options.put("userPassPolicyServiceBeanId", "userPassPolicyServiceBeanId");
    
    jaasLoginCtx.initialize(subject, cbh, null, options);
    jaasLoginCtx.setApplicationContext(new ApplicationContextBen());
    try {
      jaasLoginCtx.login();
      fail("testFailure failed!");
    } catch (LoginException e) {
      assertEquals("username is null; usernsme:null.", e.getMessage());
    }
  }

  public class ApplicationContextBen implements ApplicationContext{

    public Environment getEnvironment() {
      // TODO Auto-generated method stub
      return null;
    }

    public boolean containsBeanDefinition(String beanName) {
      // TODO Auto-generated method stub
      return false;
    }

    public int getBeanDefinitionCount() {
      // TODO Auto-generated method stub
      return 0;
    }

    public String[] getBeanDefinitionNames() {
      // TODO Auto-generated method stub
      return null;
    }

    public String[] getBeanNamesForType(Class<?> type) {
      // TODO Auto-generated method stub
      return null;
    }

    public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
      // TODO Auto-generated method stub
      return null;
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
      // TODO Auto-generated method stub
      return null;
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
        throws BeansException {
      // TODO Auto-generated method stub
      return null;
    }

    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
      // TODO Auto-generated method stub
      return null;
    }

    public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
      // TODO Auto-generated method stub
      return null;
    }

    public Object getBean(String name) throws BeansException {
      
      return new TimDBUserPassServiceImpl(){
        
        @SuppressWarnings("unused")
        public Date getPassLastChanged(String username) {
          if(username != null && "test".equals(username)){
            Date date = new Date();
            date.setTime(new Date().getTime() + 1000000000000L);
            return date;
          }
          return new Date();
        }
        
        @SuppressWarnings("unused")
        public void updatePassword(String username, String password, String newPassword) { }
      };
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
      return (T) getBean(name);
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
      // TODO Auto-generated method stub
      return null;
    }

    public Object getBean(String name, Object... args) throws BeansException {
      // TODO Auto-generated method stub
      return null;
    }

    public boolean containsBean(String name) {
      // TODO Auto-generated method stub
      return false;
    }

    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
      // TODO Auto-generated method stub
      return false;
    }

    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
      // TODO Auto-generated method stub
      return false;
    }

    public boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
      // TODO Auto-generated method stub
      return false;
    }

    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
      // TODO Auto-generated method stub
      return null;
    }

    public String[] getAliases(String name) {
      // TODO Auto-generated method stub
      return null;
    }

    public BeanFactory getParentBeanFactory() {
      // TODO Auto-generated method stub
      return null;
    }

    public boolean containsLocalBean(String name) {
      // TODO Auto-generated method stub
      return false;
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
      // TODO Auto-generated method stub
      return null;
    }

    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
      // TODO Auto-generated method stub
      return null;
    }

    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
      // TODO Auto-generated method stub
      return null;
    }

    public void publishEvent(ApplicationEvent event) {
      // TODO Auto-generated method stub
      
    }

    public Resource[] getResources(String locationPattern) throws IOException {
      // TODO Auto-generated method stub
      return null;
    }

    public Resource getResource(String location) {
      // TODO Auto-generated method stub
      return null;
    }

    public ClassLoader getClassLoader() {
      // TODO Auto-generated method stub
      return null;
    }

    public String getId() {
      // TODO Auto-generated method stub
      return null;
    }

    public String getDisplayName() {
      // TODO Auto-generated method stub
      return null;
    }

    public long getStartupDate() {
      // TODO Auto-generated method stub
      return 0;
    }

    public ApplicationContext getParent() {
      // TODO Auto-generated method stub
      return null;
    }

    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
      // TODO Auto-generated method stub
      return null;
    }
    
  }

}
