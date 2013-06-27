package com.sinopec.siam.am.idp.authn.module;

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

import com.sinopec.siam.am.idp.authn.provider.tamldap.TAMCallbackHandler;
import com.sinopec.siam.am.idp.authn.service.TimDBUserPassServiceImpl;
import com.sinopec.siam.am.idp.authn.service.TimUserPassServiceImpl;

/**
 * 类（接口）继承、实现、功能等描述
 * 
 * @author Booker
 */

public class SetPasswordHintLoginModuleTest extends TestCase {

  public SetPasswordHintLoginModuleTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * 测试设置密码提示问题。
   * 
   * @throws Exception
   */
  public void testSetPasswordHint() throws Exception {
    Subject subject = new Subject();

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("test.do");

    TAMCallbackHandler cbh = new TAMCallbackHandler("test1", null, request);
    SetPasswordHintLoginModule jaasLoginCtx = new SetPasswordHintLoginModule();
    Map<String, String> options = new HashMap<String, String>();
    options.put("userPassServiceBeanId", "userPassServiceBeanId");

    jaasLoginCtx.initialize(subject, cbh, null, options);
    jaasLoginCtx.setApplicationContext(new ApplicationContextBen());

    try {
      jaasLoginCtx.login();
      fail("testSetPasswordHint failed");
    } catch (LoginException e) {
      assertEquals("sendTo:/set_password_hint.do", e.getMessage());
    }
  }

  /**
   * 测试设置密码提示问题。
   * 
   * @throws Exception
   */
  public void testNotSetPassHint() throws Exception {
    Subject subject = new Subject();

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("test.do");

    TAMCallbackHandler cbh = new TAMCallbackHandler("test", null, request);
    SetPasswordHintLoginModule jaasLoginCtx = new SetPasswordHintLoginModule();
    Map<String, String> options = new HashMap<String, String>();
    options.put("userPassServiceBeanId", "userPassServiceBeanId");

    jaasLoginCtx.initialize(subject, cbh, null, options);
    jaasLoginCtx.setApplicationContext(new ApplicationContextBen());

    try {
      jaasLoginCtx.login();
    } catch (LoginException e) {
      fail(e.getMessage());
    }
  }

  /**
   * 测试失败情况。
   * 
   * @throws Exception
   */
  public void testFailure() throws Exception {
    Subject subject = new Subject();

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI("test.do");

    TAMCallbackHandler cbh = new TAMCallbackHandler("", null, request);
    SetPasswordHintLoginModule jaasLoginCtx = new SetPasswordHintLoginModule();
    Map<String, String> options = new HashMap<String, String>();
    options.put("userPassServiceBeanId", "userPassServiceBeanId");

    jaasLoginCtx.initialize(subject, cbh, null, options);
    jaasLoginCtx.setApplicationContext(new ApplicationContextBen());
    try {
      jaasLoginCtx.login();
      fail("testFailure failed!");
    } catch (LoginException e) {
      assertEquals("username is null; usernsme:.", e.getMessage());
    }
  }

  public class ApplicationContextBen implements ApplicationContext {

    public Environment getEnvironment() {
      return null;
    }

    public boolean containsBeanDefinition(String beanName) {
      return false;
    }

    public int getBeanDefinitionCount() {
      return 0;
    }

    public String[] getBeanDefinitionNames() {
      return null;
    }

    public String[] getBeanNamesForType(Class<?> type) {
      return null;
    }

    public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
      return null;
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
      return null;
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
        throws BeansException {
      return null;
    }

    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
      return null;
    }

    public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
      return null;
    }

    public Object getBean(String name) throws BeansException {
      return new TimDBUserPassServiceImpl() {

        @SuppressWarnings("unused")
        public String[] getPassRecoveryQuestion(String username) {
          if (username != null && "test".equals(username)) {
            return new String[] {"Password Hint"};
          }
          return null;
        }
      };
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
      return (T) getBean(name);
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
      return null;
    }

    public Object getBean(String name, Object... args) throws BeansException {
      return null;
    }

    public boolean containsBean(String name) {
      return false;
    }

    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
      return false;
    }

    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
      return false;
    }

    public boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
      return false;
    }

    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
      return null;
    }

    public String[] getAliases(String name) {
      return null;
    }

    public BeanFactory getParentBeanFactory() {
      return null;
    }

    public boolean containsLocalBean(String name) {
      return false;
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
      return null;
    }

    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
      return null;
    }

    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
      return null;
    }

    public void publishEvent(ApplicationEvent event) {

    }

    public Resource[] getResources(String locationPattern) throws IOException {
      return null;
    }

    public Resource getResource(String location) {
      return null;
    }

    public ClassLoader getClassLoader() {
      return null;
    }

    public String getId() {
      return null;
    }

    public String getDisplayName() {
      return null;
    }

    public long getStartupDate() {
      return 0;
    }

    public ApplicationContext getParent() {
      return null;
    }

    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
      return null;
    }

  }

}
