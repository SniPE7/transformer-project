package com.sinopec.siam.am.idp.authn.module;

import java.io.IOException;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.UncategorizedLdapException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.support.LdapUtils;

import edu.vt.middleware.ldap.jaas.LdapDnPrincipal;
import edu.vt.middleware.ldap.jaas.LdapPrincipal;

/**
 * <code>LdapLoginModule</code> provides a JAAS authentication hook into LDAP
 * authentication.<br/>
 * 
 * <pre>
 * ���ò���:
 * * storePassΪtrueʱ, ��sharedState�б����������?
 *      javax.security.auth.login.password - �û���¼����
 *      javax.security.auth.login.name     - �û���¼��uid
 *      edu.vt.middleware.ldap.jaas.login.entryDn  - �û���LDAP DN
 * 
 * </pre>
 */
public class CommonLdapAuthLoginModule extends AbstractSpringLoginModule implements LoginModule {

  private static Log logger = LogFactory.getLog(CommonLdapAuthLoginModule.class);
  
  /** ��¼��֤Դֵ */
  private String authnSource = "SIAM";

  /**
   * Filter to find user
   */
  private String userFilter = "(&(uid={0})(objectclass=inetOrgPerson))";

  /**
   * Filter to compare password
   */
  private String passwordFilter = "(userpassword={0})";
  
  /**
   * Set returned attributes
   */
  private String[] returnAttributeNames = null;

  /** Whether ldap principal data should be set. */
  private boolean setLdapPrincipal = true;

  /** Whether credentials should be stored in the shared state map. */
  private boolean storePass;

  /** Whether ldap dn principal data should be set. */
  private boolean setLdapDnPrincipal;

  /**
   * Spring LDAP template bean name
   */
  private String ldapTemplateBeanName = "tamLdapTemplate";
  
  /**
   * �Ƿ�ʹ��bindģʽ
   */
  private boolean bindMode = false;
  
  /**
   * LDAP baseDN
   */
  private String baseDn = "";

  /**
   * true --- Subtree scope for searching user
   */
  private Boolean subTree = true;

  public static class DnAndAttributes {
    private String dn = null;
    Attributes attributes = null;

    public DnAndAttributes() {
      super();
    }

    public String getDn() {
      return dn;
    }

    public DnAndAttributes(String dn, Attributes attributes) {
      super();
      this.dn = dn;
      this.attributes = attributes;
    }

    public void setDn(String dn) {
      this.dn = dn;
    }

    public Attributes getAttributes() {
      return attributes;
    }

    public void setAttributes(Attributes attributes) {
      this.attributes = attributes;
    }

  }

  /**
   * 
   */
  public CommonLdapAuthLoginModule() {
    super();
  }

  /**
   * This attempts to retrieve credentials for the supplied name and password
   * callbacks. If useFirstPass or tryFirstPass is set, then name and password
   * data is retrieved from shared state. Otherwise a callback handler is used
   * to get the data. Set useCallback to force a callback handler to be used.
   * 
   * @param nameCb
   *          to set name for
   * @param passCb
   *          to set password for
   * @param useCallback
   *          whether to force a callback handler
   * 
   * @throws LoginException
   *           if the callback handler fails
   */
  protected void getCredentials(final NameCallback nameCb, final PasswordCallback passCb, final boolean useCallback) throws LoginException {
    if (logger.isTraceEnabled()) {
      logger.trace("Begin getCredentials");
      logger.trace("  useCallback = " + useCallback);
      logger.trace("  callbackhandler class = " + this.callbackHandler.getClass().getName());
      logger.trace("  name callback class = " + nameCb.getClass().getName());
      logger.trace("  password callback class = " + passCb.getClass().getName());
    }
    try {
      if (this.callbackHandler != null) {
        this.callbackHandler.handle(new Callback[] { nameCb, passCb });
      } else {
        throw new LoginException("No CallbackHandler available. " + "Set useFirstPass, tryFirstPass, or provide a CallbackHandler");
      }
    } catch (IOException e) {
      if (logger.isErrorEnabled()) {
        logger.error("Error reading data from callback handler", e);
      }
      this.authenticated = false;
      throw new LoginException(e.getMessage());
    } catch (UnsupportedCallbackException e) {
      if (logger.isErrorEnabled()) {
        logger.error("Unsupported callback", e);
      }
      this.authenticated = false;
      throw new LoginException(e.getMessage());
    }
  }

  /**
   * @return
   */
  private LdapTemplate getLdapTemplate() {
    return (LdapTemplate) this.applicationContext.getBean(ldapTemplateBeanName, LdapTemplate.class);
  }

  /**
   * Get LDAP dn
   * 
   * @param userName
   * @return
   */
  private DnAndAttributes searchUserDNByAccount(String userName) {
    String filter = MessageFormat.format(this.userFilter, userName);

    try {
      if (logger.isDebugEnabled()) {
        logger.debug(String.format("Search user DN by filter [%s], base [%s]", filter, baseDn));
      }
      SearchControls controls = new SearchControls();
      controls.setReturningAttributes(this.returnAttributeNames);
      if (this.subTree) {
         controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
      } else {
        controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
      }
      List<DnAndAttributes> result = (List<DnAndAttributes>)this.getLdapTemplate().search(baseDn, filter, controls, new AbstractContextMapper() {

        @Override
        protected Object doMapFromContext(DirContextOperations ctx) {
          String dn = ctx.getNameInNamespace();
          Attributes attrs = ctx.getAttributes();
          return new DnAndAttributes(dn, attrs);
        }
      });
      if (result.size() == 1) {
         return result.get(0);
      } else {
        return null;
      }
    } catch (EmptyResultDataAccessException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(String.format("Search user DN exception", e.getMessage()));
      }
      return null;
    }
  }

  /**
   * ��֤�û��Ŀ���
   * 
   * @param ctx
   * @param userDn
   * @param password
   * @throws NamingException
   */
  private void checkPassword(DirContext ctx, String userDn, String userName, char[] password) throws NamingException {
    if(bindMode){
      checkPasswordInBindMode(ctx, userDn, userName, password);
    }else{
      checkPasswordInCompareMode(ctx, userDn, password);
    }
  }

  private void checkPasswordInBindMode(DirContext ctx, String userDn, String userName, char[] password) throws NamingException {
    String filter = MessageFormat.format(this.userFilter, userName);
    boolean result = this.getLdapTemplate().authenticate(userDn, filter, new String(password));
    if(!result){
      throw LdapUtils.convertLdapException(new NamingException("the password is wrong"));
    }
  }
  
  private void checkPasswordInCompareMode(DirContext ctx, String userDn, char[] password) throws NamingException {
    SearchControls cons = new SearchControls();
    cons.setReturningAttributes(new String[0]); // Return no attrs
    cons.setSearchScope(SearchControls.OBJECT_SCOPE); // Search object only

    NamingEnumeration answer = ctx.search(userDn, passwordFilter, new Object[] { (new String(password)).getBytes() }, cons);
    if (answer == null || !answer.hasMoreElements()) {
      throw LdapUtils.convertLdapException(new NamingException("the password is wrong"));
    }
    answer.close();
  }
  
  /**
   * This will store the supplied name, password, and entry dn in the stored
   * state map. storePass must be set for this method to have any affect.
   * 
   * @param nameCb
   *          to store
   * @param passCb
   *          to store
   * @param loginDn
   *          to store
   */
  @SuppressWarnings("unchecked")
  protected void storeCredentials(final NameCallback nameCb, final PasswordCallback passCb, final String loginDn) {
    if (this.storePass) {
      if (nameCb != null && nameCb.getName() != null) {
        this.sharedState.put(LOGIN_NAME, nameCb.getName());
      }
      if (passCb != null && passCb.getPassword() != null) {
        this.sharedState.put(LOGIN_PASSWORD, passCb.getPassword());
      }
      if (loginDn != null) {
        this.sharedState.put(LOGIN_DN, loginDn);
      }
    }
  }

  /** {@inheritDoc} */
  public void initialize(final Subject subject, final CallbackHandler callbackHandler, final Map<String, ?> sharedState, final Map<String, ?> options) {
    this.setLdapPrincipal = true;

    super.initialize(subject, callbackHandler, sharedState, options);

    if (logger.isTraceEnabled()) {
      logger.trace("Begin initialize");
    }
    this.subject = subject;
    this.callbackHandler = callbackHandler;
    this.sharedState = sharedState;

    final Iterator<String> i = options.keySet().iterator();
    while (i.hasNext()) {
      final String key = i.next();
      final String value = (String) options.get(key);
      if (key.equalsIgnoreCase("authnSource")) {
        this.authnSource = value;
      } else if (key.equalsIgnoreCase("storePass")) {
        this.storePass = Boolean.valueOf(value);
      } else if (key.equalsIgnoreCase("userFilter")) {
        this.userFilter = value;
      } else if (key.equalsIgnoreCase("passwordFilter")) {
        this.passwordFilter = value;
      } else if (key.equalsIgnoreCase("setLdapPrincipal")) {
        this.setLdapPrincipal = Boolean.valueOf(value);
      } else if (key.equalsIgnoreCase("setLdapDnPrincipal")) {
        this.setLdapDnPrincipal = Boolean.valueOf(value);
      } else if (key.equalsIgnoreCase("subTree")) {
        this.subTree  = Boolean.valueOf(value);
      } else if (key.equalsIgnoreCase("ldapTemplateBeanName")) {
        this.ldapTemplateBeanName = value;
      } else if (key.equalsIgnoreCase("returnAttributeNames")) {
        this.returnAttributeNames = StringUtils.split(value, ',');
      } else if (key.equalsIgnoreCase("baseDn")) {
        this.baseDn = value;
      } else if (key.equalsIgnoreCase("bindMode")) {
        this.bindMode  = Boolean.valueOf(value);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("storePass = " + this.storePass);
      logger.debug("setLdapPrincipal = " + this.setLdapPrincipal);
      logger.debug("setLdapDnPrincipal = " + this.setLdapDnPrincipal);
    }

    this.principals = new TreeSet<Principal>();
  }

  /** {@inheritDoc} */
  public boolean login() throws LoginException {
    try {
      final NameCallback nameCb = new NameCallback("Enter user: ");
      final PasswordCallback passCb = new PasswordCallback("Enter user password: ", false);
      this.getCredentials(nameCb, passCb, false);

      DirContext ctx = null;
      Exception authEx = null;
      Attributes attrs = null;
      String userDn = null;
      String userUid = null;
      
      DnAndAttributes dnAndAttrs = this.searchUserDNByAccount(nameCb.getName());
      if (dnAndAttrs == null){
        //Login Authentication Code
        if(this.getSessionLevelState(AbstractLoginModule.LOGIN_AUTH_CODE_KEY) == null){
          this.setSessionLevelState(AbstractLoginModule.LOGIN_AUTH_CODE_KEY, AbstractLoginModule.LOGIN_USER_NOT_EXISTS);
          this.setSessionLevelState(AbstractLoginModule.LOGIN_AUTH_SOURCE_KEY, authnSource);
        }else if(AbstractLoginModule.LOGIN_USER_NOT_EXISTS ==(Integer) this.getSessionLevelState(AbstractLoginModule.LOGIN_AUTH_CODE_KEY)){
          this.setSessionLevelState(AbstractLoginModule.LOGIN_AUTH_SOURCE_KEY, authnSource);
        }
      } else {
        userDn = dnAndAttrs.getDn();
        attrs = dnAndAttrs.getAttributes();
        ctx = getLdapTemplate().getContextSource().getReadOnlyContext();
        
        userUid = dnAndAttrs.getAttributes().get("uid").get(0).toString();
        
        try {
          checkPassword(ctx, userDn, nameCb.getName(), passCb.getPassword());
          this.authenticated = true;
        } catch (AuthenticationException e) {
          authEx = e;
          this.authenticated = false;
          //Login Authentication Code
          this.setSessionLevelState(AbstractLoginModule.LOGIN_AUTH_CODE_KEY, AbstractLoginModule.LOGIN_AUTH_PASS_ERROR);
          this.setSessionLevelState(AbstractLoginModule.LOGIN_AUTH_SOURCE_KEY, authnSource);
        } catch (UncategorizedLdapException e){
          authEx = e;
            this.authenticated = false;
            //Login Authentication Code
            this.setSessionLevelState(AbstractLoginModule.LOGIN_AUTH_CODE_KEY, AbstractLoginModule.LOGIN_AUTH_PASS_ERROR);
            this.setSessionLevelState(AbstractLoginModule.LOGIN_AUTH_SOURCE_KEY, authnSource);
        } finally {
          LdapUtils.closeContext(ctx);
        }
      }
      
      if (!this.authenticated) {
        if (logger.isDebugEnabled()) {
          logger.debug("Authentication failed", authEx);
        }
        throw new LoginException(authEx != null ? authEx.getMessage() : "Authentication failed");
      } else {
        
        if (this.setLdapPrincipal) {
          final LdapPrincipal lp = new LdapPrincipal(userUid);
          if (attrs != null) {
            lp.getLdapAttributes().addAttributes(attrs);
          }
          this.principals.add(lp);
          this.setSessionLevelState("SUBJECT.PRICIPAL.LDAPPRINCIPAL", lp);
        }

        if (userDn != null && this.setLdapDnPrincipal) {
          final LdapDnPrincipal lp = new LdapDnPrincipal(userDn);
          if (attrs != null) {
            lp.getLdapAttributes().addAttributes(attrs);
          }
          this.principals.add(lp);
        }
        this.storeCredentials(nameCb, passCb, userDn);
        
        //��ad��tam mapping module�޸�����ʱʹ��
        this.setSessionLevelState(AbstractSpringLoginModule.PRINCIPAL_PASSWORD_KEY, sharedState.get(LOGIN_PASSWORD));
        //Login Authentication Code
        this.setSessionLevelState(AbstractLoginModule.LOGIN_AUTH_CODE_KEY, AbstractLoginModule.LOGIN_AUTH_SUCCESS);
        this.setSessionLevelState(AbstractLoginModule.LOGIN_AUTH_SOURCE_KEY, authnSource);
        
      }
    } catch (NamingException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Error occured attempting authentication", e);
      }
      this.authenticated = false;
      throw new LoginException(e != null ? e.getMessage() : "Authentication Error");
    } catch (Exception e) {
          logger.debug("Error occured attempting authentication", e);
        this.authenticated = false;
        throw new LoginException(e != null ? e.getMessage() : "Authentication Error");
    } finally {
    }
    return true;
  }

}
