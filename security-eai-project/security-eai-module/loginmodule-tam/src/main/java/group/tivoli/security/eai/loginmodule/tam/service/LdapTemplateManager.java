package group.tivoli.security.eai.loginmodule.tam.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.ldap.CommunicationException;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.AuthenticatedLdapEntryContextCallback;
import org.springframework.ldap.core.AuthenticationErrorCallback;
import org.springframework.ldap.core.ContextExecutor;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DirContextProcessor;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.NameClassPairCallbackHandler;
import org.springframework.ldap.core.NameClassPairMapper;
import org.springframework.ldap.core.SearchExecutor;
import org.springframework.ldap.pool.DirContextType;
import org.springframework.ldap.pool.validation.DirContextValidator;

/**
 * 类（接口）继承、实现、功能等描述
 * @author xuhong
 * @since 2012-12-10 下午5:35:48
 */

public class LdapTemplateManager extends LdapTemplate implements LdapOperations {
  
  private static Log logger = LogFactory.getLog(LdapTemplateManager.class);

  public LdapTemplateManager(){
    
  }
  
  @Override
  public ContextSource getContextSource() {
    
    Iterator<LdapTemplate> iter = getLdapTemplates().iterator();
    while (iter.hasNext()) {
      LdapTemplate ldapTemplate = iter.next();

      try {
        //test ContextSource 
        ContextSource context = ldapTemplate.getContextSource();
        
        org.springframework.ldap.pool.factory.MutablePoolingContextSource dd = (org.springframework.ldap.pool.factory.MutablePoolingContextSource)context;
        System.out.println("dd:getNumActive:" + dd.getNumActive());
        System.out.println("getNumIdle:" + dd.getNumIdle());
        
        DirContextValidator validator = dd.getDirContextValidator();
        try {
          if (!validator.validateDirContext(DirContextType.READ_ONLY, dd.getReadOnlyContext())) {
            continue;
          }
        } catch (DataAccessResourceFailureException err) {
            continue;
        } catch (Exception err) {
          if (err instanceof InvocationTargetException
              && ((InvocationTargetException) err).getTargetException() instanceof DataAccessResourceFailureException) {
            // throw (DataAccessResourceFailureException)
            // ((InvocationTargetException)err).getTargetException();
            continue;
          }
        }
        
        return context;
      } catch (DataAccessResourceFailureException err) {
        // for connect error, next continue
        // or throw exception to air
        logger.error("DataAccessResourceFailureException 目录连接通讯故障，请检查目录的可用性：", err);
       // throw err;
      } catch (CommunicationException err) {
        // for connect error, next continue
        // or throw exception to air
        logger.error("目录连接通讯故障，请检查目录的可用性：", err);
       // throw err;
      } catch (RuntimeException err) {
        // for connect error, next continue
        // or throw exception to air
        logger.error("目录方法调用异常 RuntimeException：", err);
        throw err;
      }
    }

    throw new RuntimeException("目录方法调用异常，无可用连接了！");
  }
  
  public void afterPropertiesSet() throws Exception {
    
  }
  
  private Set<LdapTemplate> ldapTemplates = new HashSet<LdapTemplate>();

  public Set<LdapTemplate> getLdapTemplates() {
    return ldapTemplates;
  }

  public void setLdapTemplates(Set<LdapTemplate> ldapTemplates) {
    this.ldapTemplates = ldapTemplates;
  }
  
  /** 执行非静态方法，自动修改权限 
   * @throws Exception */
  private Object invokeMethod(Object target, String methodName, Object... args)
      throws IllegalStateException, RuntimeException {
    Class<?> clazz = target.getClass();
    try {
      
      Class<?>[] argTypes = new Class[args.length];
      for(int i=0; i< args.length; i++){
        if(args[i] instanceof ContextMapper){
          argTypes[i] = ContextMapper.class;
        }else{
          argTypes[i] = args[i].getClass();
        }
      }
      
      //Method method = clazz.getDeclaredMethod(methodName, argTypes);
      Method method = clazz.getMethod(methodName, argTypes);

      if (method.isAccessible()){
        return method.invoke(clazz, args);
      }
      method.setAccessible(true);
      try {
        return method.invoke(target, args);
      } finally {
        method.setAccessible(false);
      }
    } catch (InvocationTargetException err) {
      // for connect error, next continue
      // or throw exception to air
      logger.error("目录方法调用异常 InvocationTargetException：", err);
      
      if(err.getTargetException() instanceof DataAccessResourceFailureException){
        throw (DataAccessResourceFailureException) err.getTargetException();
      }
      
      Throwable e = (Exception) err.getTargetException().getCause();
      if(e instanceof CommunicationException){
        throw (CommunicationException)e;
      } 
      
      throw e instanceof RuntimeException ? (RuntimeException) e : new IllegalStateException(clazz + "执行方法["
          + methodName + "]异常: " + e.getMessage(), e);
    } catch (Exception e) {
      throw e instanceof RuntimeException ? (RuntimeException) e : new IllegalStateException(clazz + "执行方法["
          + methodName + "]异常: " + e.getMessage(), e);
    }
  }
  
  private Object proxyInvoke(String methodName, Object... inArgs) {

    Iterator<LdapTemplate> iter = getLdapTemplates().iterator();
    while (iter.hasNext()) {
      LdapTemplate ldapTemplate = iter.next();
      
      ContextSource context = ldapTemplate.getContextSource();
      
      org.springframework.ldap.pool.factory.MutablePoolingContextSource dd = (org.springframework.ldap.pool.factory.MutablePoolingContextSource)context;
      System.out.println("dd:getNumActive:" + dd.getNumActive());
      System.out.println("getNumIdle:" + dd.getNumIdle());
      DirContextValidator validator = dd.getDirContextValidator();
      try {
        if (!validator.validateDirContext(DirContextType.READ_ONLY, dd.getReadOnlyContext())) {
          continue;
        }
      } catch (DataAccessResourceFailureException err) {
          continue;
      } catch (Exception err) {
        if (err instanceof InvocationTargetException
            && ((InvocationTargetException) err).getTargetException() instanceof DataAccessResourceFailureException) {
          // throw (DataAccessResourceFailureException)
          // ((InvocationTargetException)err).getTargetException();
          continue;
        }
      }

      try {
        return invokeMethod(ldapTemplate, methodName, inArgs);
      } catch (DataAccessResourceFailureException err) {
        // for connect error, next continue
        // or throw exception to air
        logger.error("DataAccessResourceFailureException 目录连接通讯故障，请检查目录的可用性：", err);
       // throw err;
      } catch (CommunicationException err) {
        // for connect error, next continue
        // or throw exception to air
        logger.error("目录连接通讯故障，请检查目录的可用性：", err);
       // throw err;
      } catch (RuntimeException err) {
        // for connect error, next continue
        // or throw exception to air
        logger.error("目录方法调用异常 RuntimeException：", err);
        throw err;
      } catch (Exception err) {
        
        if(err instanceof InvocationTargetException && ((InvocationTargetException)err).getTargetException() instanceof DataAccessResourceFailureException){
          //throw (DataAccessResourceFailureException) ((InvocationTargetException)err).getTargetException();
          continue;
        }
        
        logger.error("目录方法调用异常 RuntimeException：", err);
        //throw err;
      } 
    }

    throw new RuntimeException("目录方法调用异常，无可用连接了！");
  }

  @Override
  public void search(SearchExecutor se, NameClassPairCallbackHandler handler, DirContextProcessor processor)
      throws NamingException {

      proxyInvoke("search", se, handler, processor);
  }

  @Override
  public void listBindings(String base, NameClassPairCallbackHandler handler) throws NamingException {
    proxyInvoke("listBindings", base, handler);
  }

  @Override
  public void listBindings(Name base, NameClassPairCallbackHandler handler) throws NamingException {
    proxyInvoke("listBindings", base, handler);
  }

  @Override
  public List listBindings(String base, NameClassPairMapper mapper) throws NamingException {
    return (List) proxyInvoke("listBindings", base, mapper);
  }

  @Override
  public List listBindings(Name base, NameClassPairMapper mapper) throws NamingException {
    return (List) proxyInvoke("listBindings", base, mapper);
  }

  @Override
  public List listBindings(String base, ContextMapper mapper) throws NamingException {
    return (List) proxyInvoke("listBindings", base, mapper);
  }

  @Override
  public List listBindings(Name base, ContextMapper mapper) throws NamingException {
    return (List) proxyInvoke("listBindings", base, mapper);
  }

  @Override
  public Object lookup(Name dn) throws NamingException {
    return proxyInvoke("lookup", dn);
  }

  @Override
  public Object lookup(String dn) throws NamingException {
    return proxyInvoke("lookup", dn);
  }

  @Override
  public Object lookup(Name dn, AttributesMapper mapper) throws NamingException {
    return proxyInvoke("lookup", dn, mapper);
  }

  @Override
  public Object lookup(String dn, AttributesMapper mapper) throws NamingException {
    return proxyInvoke("lookup", dn, mapper);
  }

  @Override
  public Object lookup(Name dn, ContextMapper mapper) throws NamingException {
    return proxyInvoke("lookup", dn, mapper);
  }

  @Override
  public Object lookup(String dn, ContextMapper mapper) throws NamingException {
    return proxyInvoke("lookup", dn, mapper);
  }

  @Override
  public Object lookup(Name dn, String[] attributes, AttributesMapper mapper) throws NamingException {
    return proxyInvoke("lookup", dn, attributes, mapper);
  }

  @Override
  public Object lookup(String dn, String[] attributes, AttributesMapper mapper) throws NamingException {
    return proxyInvoke("lookup", dn, attributes, mapper);
  }

  @Override
  public Object lookup(Name dn, String[] attributes, ContextMapper mapper) throws NamingException {
    return proxyInvoke("lookup", dn, attributes, mapper);
  }

  @Override
  public Object lookup(String dn, String[] attributes, ContextMapper mapper) throws NamingException {
    return proxyInvoke("lookup", dn, attributes, mapper);
  }

  @Override
  public void modifyAttributes(Name dn, ModificationItem[] mods) throws NamingException {
    proxyInvoke("modifyAttributes", dn, mods);
  }

  @Override
  public void modifyAttributes(String dn, ModificationItem[] mods) throws NamingException {
    proxyInvoke("modifyAttributes", dn, mods);
  }

  @Override
  public DirContextOperations lookupContext(Name dn) throws NamingException, ClassCastException {
    return (DirContextOperations) proxyInvoke("lookupContext", dn);
  }

  @Override
  public DirContextOperations lookupContext(String dn) throws NamingException, ClassCastException {
    return (DirContextOperations) proxyInvoke("lookupContext", dn);
  }

  @Override
  public void modifyAttributes(DirContextOperations ctx) throws IllegalStateException, NamingException {
    proxyInvoke("modifyAttributes", ctx);
  }

  @Override
  public boolean authenticate(Name base, String filter, String password) {
    return (Boolean) proxyInvoke("authenticate", base, filter, password);
  }

  @Override
  public boolean authenticate(String base, String filter, String password) {
    return (Boolean) proxyInvoke("authenticate", base, filter, password);
  }

  @Override
  public boolean authenticate(Name base, String filter, String password, AuthenticatedLdapEntryContextCallback callback) {
    return (Boolean) proxyInvoke("authenticate", base, filter, password, callback);
  }

  @Override
  public boolean authenticate(String base, String filter, String password,
      AuthenticatedLdapEntryContextCallback callback) {
    return (Boolean) proxyInvoke("authenticate", base, filter, password, callback);
  }

  @Override
  public boolean authenticate(Name arg0, String arg1, String arg2, AuthenticationErrorCallback arg3) {
    return (Boolean) proxyInvoke("authenticate", arg0, arg1, arg2, arg3);
  }

  @Override
  public boolean authenticate(String arg0, String arg1, String arg2, AuthenticationErrorCallback arg3) {
    return (Boolean) proxyInvoke("authenticate", arg0, arg1, arg2, arg3);
  }

  @Override
  public boolean authenticate(Name arg0, String arg1, String arg2, AuthenticatedLdapEntryContextCallback arg3,
      AuthenticationErrorCallback arg4) {
    return (Boolean) proxyInvoke("authenticate", arg0, arg1, arg2, arg3, arg4);
  }

  @Override
  public boolean authenticate(String arg0, String arg1, String arg2, AuthenticatedLdapEntryContextCallback arg3,
      AuthenticationErrorCallback arg4) {
    return (Boolean) proxyInvoke("authenticate", arg0, arg1, arg2, arg3, arg4);
  }

  @Override
  public void search(SearchExecutor se, NameClassPairCallbackHandler handler) throws NamingException {
    proxyInvoke("search", se, handler);
  }

  @Override
  public Object executeReadOnly(ContextExecutor ce) throws NamingException {
    return proxyInvoke("executeReadOnly", ce);
  }

  @Override
  public Object executeReadWrite(ContextExecutor ce) throws NamingException {
    return proxyInvoke("executeReadWrite", ce);
  }

  @Override
  public void search(Name base, String filter, SearchControls controls, NameClassPairCallbackHandler handler)
      throws NamingException {
    proxyInvoke("search", base, filter, controls, handler);
  }

  @Override
  public void search(String base, String filter, SearchControls controls, NameClassPairCallbackHandler handler)
      throws NamingException {
    proxyInvoke("search", base, filter, controls, handler);

  }

  @Override
  public void search(Name base, String filter, SearchControls controls, NameClassPairCallbackHandler handler,
      DirContextProcessor processor) throws NamingException {
    proxyInvoke("search", base, filter, controls, handler, processor);
  }

  @Override
  public List search(String base, String filter, SearchControls controls, AttributesMapper mapper,
      DirContextProcessor processor) throws NamingException {
    return (List) proxyInvoke("search", base, filter, controls, mapper, processor);
  }

  @Override
  public List search(Name base, String filter, SearchControls controls, AttributesMapper mapper,
      DirContextProcessor processor) throws NamingException {
    return (List) proxyInvoke("search", base, filter, controls, mapper, processor);
  }

  @Override
  public List search(String base, String filter, SearchControls controls, ContextMapper mapper,
      DirContextProcessor processor) throws NamingException {
    return (List) proxyInvoke("search", base, filter, controls, mapper, processor);
  }

  @Override
  public List search(Name base, String filter, SearchControls controls, ContextMapper mapper,
      DirContextProcessor processor) throws NamingException {
    return (List) proxyInvoke("search", base, filter, controls, mapper, processor);
  }

  @Override
  public void search(String base, String filter, SearchControls controls, NameClassPairCallbackHandler handler,
      DirContextProcessor processor) throws NamingException {
     proxyInvoke("search", base, filter, controls, handler, processor);
  }

  @Override
  public void search(Name base, String filter, int searchScope, boolean returningObjFlag,
      NameClassPairCallbackHandler handler) throws NamingException {
    proxyInvoke("search", base, filter, searchScope, returningObjFlag, handler);
  }

  @Override
  public void search(String base, String filter, int searchScope, boolean returningObjFlag,
      NameClassPairCallbackHandler handler) throws NamingException {
    proxyInvoke("search", base, filter, searchScope, returningObjFlag, handler);
  }

  @Override
  public void search(Name base, String filter, NameClassPairCallbackHandler handler) throws NamingException {
    proxyInvoke("search", base, filter, handler);
  }

  @Override
  public void search(String base, String filter, NameClassPairCallbackHandler handler) throws NamingException {
    proxyInvoke("search", base, filter, handler);
  }

  @Override
  public List search(Name base, String filter, int searchScope, String[] attrs, AttributesMapper mapper)
      throws NamingException {
    return (List) proxyInvoke("search", base, filter, searchScope, attrs, mapper);
  }

  @Override
  public List search(String base, String filter, int searchScope, String[] attrs, AttributesMapper mapper)
      throws NamingException {
    return (List) proxyInvoke("search", base, filter, searchScope, attrs, mapper);
  }

  @Override
  public List search(Name base, String filter, int searchScope, AttributesMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, searchScope, mapper);
  }

  @Override
  public List search(String base, String filter, int searchScope, AttributesMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, searchScope, mapper);
  }

  @Override
  public List search(Name base, String filter, AttributesMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, mapper);
  }

  @Override
  public List search(String base, String filter, AttributesMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, mapper);
  }

  @Override
  public List search(Name base, String filter, int searchScope, String[] attrs, ContextMapper mapper)
      throws NamingException {
    return (List) proxyInvoke("search", base, filter, searchScope, attrs, mapper);
  }

  @Override
  public List search(String base, String filter, int searchScope, String[] attrs, ContextMapper mapper)
      throws NamingException {
    return (List) proxyInvoke("search", base, filter, searchScope, attrs, mapper);
  }

  @Override
  public List search(Name base, String filter, int searchScope, ContextMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, searchScope, mapper);
  }

  @Override
  public List search(String base, String filter, int searchScope, ContextMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, searchScope, mapper);
  }

  @Override
  public List search(Name base, String filter, ContextMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, mapper);
  }

  @Override
  public List search(String base, String filter, ContextMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, mapper);
  }

  @Override
  public List search(String base, String filter, SearchControls controls, ContextMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, controls, mapper);
  }

  @Override
  public List search(Name base, String filter, SearchControls controls, ContextMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, controls, mapper);
  }

  @Override
  public List search(String base, String filter, SearchControls controls, AttributesMapper mapper)
      throws NamingException {
    return (List) proxyInvoke("search", base, filter, controls, mapper);
  }

  @Override
  public List search(Name base, String filter, SearchControls controls, AttributesMapper mapper) throws NamingException {
    return (List) proxyInvoke("search", base, filter, controls, mapper);
  }

  @Override
  public void list(String base, NameClassPairCallbackHandler handler) throws NamingException {
    proxyInvoke("list", base, handler);
  }

  @Override
  public void list(Name base, NameClassPairCallbackHandler handler) throws NamingException {
    proxyInvoke("list", base, handler);
  }

  @Override
  public List list(String base, NameClassPairMapper mapper) throws NamingException {
    return (List) proxyInvoke("list", base, mapper);
  }

  @Override
  public List list(Name base, NameClassPairMapper mapper) throws NamingException {
    return (List) proxyInvoke("list", base, mapper);
  }

  @Override
  public List list(String base) throws NamingException {
    return (List) proxyInvoke("list", base);
  }

  @Override
  public List list(Name base) throws NamingException {
    return (List) proxyInvoke("list", base);
  }

  @Override
  public List listBindings(String base) throws NamingException {
    return (List) proxyInvoke("listBindings", base);
  }

  @Override
  public List listBindings(Name base) throws NamingException {
    return (List) proxyInvoke("listBindings", base);
  }

  @Override
  public void bind(Name dn, Object obj, Attributes attributes) throws NamingException {
    proxyInvoke("bind", dn, obj, attributes);
  }

  @Override
  public void bind(String dn, Object obj, Attributes attributes) throws NamingException {
    proxyInvoke("bind", dn, obj, attributes);
  }

  @Override
  public void unbind(Name dn) throws NamingException {
    proxyInvoke("unbind", dn);
  }

  @Override
  public void unbind(String dn) throws NamingException {
    proxyInvoke("unbind", dn);
  }

  @Override
  public void unbind(Name dn, boolean recursive) throws NamingException {
    proxyInvoke("unbind", dn, recursive);
  }

  @Override
  public void unbind(String dn, boolean recursive) throws NamingException {
    proxyInvoke("unbind", dn, recursive);
  }

  @Override
  public void rebind(Name dn, Object obj, Attributes attributes) throws NamingException {
    proxyInvoke("rebind", dn, obj, attributes);
  }

  @Override
  public void rebind(String dn, Object obj, Attributes attributes) throws NamingException {
    proxyInvoke("rebind", dn, obj, attributes);
  }

  @Override
  public void rename(Name oldDn, Name newDn) throws NamingException {
    proxyInvoke("rename", oldDn, newDn);
  }

  @Override
  public void rename(String oldDn, String newDn) throws NamingException {
    proxyInvoke("rename", oldDn, newDn);
  }

  @Override
  public void bind(DirContextOperations ctx) {
    proxyInvoke("bind", ctx);
  }

  @Override
  public void rebind(DirContextOperations ctx) {
    proxyInvoke("rebind", ctx);
  }

  @Override
  public Object searchForObject(Name base, String filter, ContextMapper mapper) {
    return proxyInvoke("searchForObject", base, filter, mapper);
  }

  @Override
  public Object searchForObject(String base, String filter, ContextMapper mapper) {
    return proxyInvoke("searchForObject", base, filter, mapper);
  }

}
