package com.ibm.siam.am.idp.authn.module;

import java.io.IOException;
import java.lang.annotation.Annotation;
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
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;

import com.ibm.siam.am.idp.authn.service.LocalUserLockService;
import com.ibm.siam.am.idp.authn.provider.tamldap.TAMCallbackHandler;

/**
 * 用户锁定状态检查LoginModule测试用例
 * @author zhangxianwen
 * @since 2012-6-18 下午2:50:25
 */

public class UserLockStateCheckLoginModuleTest extends TestCase {

  public UserLockStateCheckLoginModuleTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testSuccess() throws Exception {

    Subject subject = new Subject();
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("test.do");
    TAMCallbackHandler cbh = new TAMCallbackHandler("test", null, request);
    CheckUserLockStateLoginModule jaasLoginCtx = new CheckUserLockStateLoginModule();
    Map<String, String> options = new HashMap<String, String>();
    options.put("userLockServiceBeanId", "userLockServiceBeanId");
    
    jaasLoginCtx.initialize(subject, cbh, null, options);
    jaasLoginCtx.setApplicationContext(new ApplicationContextBen());
    
    try {
      jaasLoginCtx.login();
    } catch (LoginException e) {
      fail("testSuccess failed:"+e.getMessage());
    }
  }

  public void testFailure() throws Exception {
    Subject subject = new Subject();
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("test.do");
    TAMCallbackHandler cbh = new TAMCallbackHandler("test1", null, request);
    CheckUserLockStateLoginModule jaasLoginCtx = new CheckUserLockStateLoginModule();
    Map<String, String> options = new HashMap<String, String>();
    options.put("userLockServiceBeanId", "userLockServiceBeanId");
    
    jaasLoginCtx.initialize(subject, cbh, null, options);
    jaasLoginCtx.setApplicationContext(new ApplicationContextBen());
    try {
      jaasLoginCtx.login();
      fail("testFailure failed!");
    } catch (LoginException e) {
      assertEquals("User are being locked,usernsme:test1.", e.getMessage());
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
      return new LocalUserLockService(){
        
        public boolean isLocked(String username) {
          if(username != null && "test".equals(username)){
            return false;
          }
          return true;
        }
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
