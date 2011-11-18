/**
 * 
 */
package com.ibm.tivoli.cmcc.ldap;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Zhao Dong Lu
 *
 */
public class LDAPContactDAOTest extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testList() throws Exception {
    ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext(new String[] { "/com/ibm/tivoli/cmcc/server/spring/mainBean.xml" });
    PersonDAO dao = (LDAPPersonDAO) factory.getBean("ldapDao");
    List persons = dao.searchPerson("", "(uid=13916918120)");
    assertTrue(persons.size() > 0);
  }

  public void testUpdateUniqueIdentifier() throws Exception {
    ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext(new String[] { "/com/ibm/tivoli/cmcc/server/spring/mainBean.xml" });
    PersonDAO dao = (LDAPPersonDAO) factory.getBean("ldapDao");
    dao.updateUniqueIdentifier("", "(uniqueIdentify=0f23dedc4b7710562117804301596812)", "0f23dedc4b7710562117804301596812");
    assertTrue(true);
  }
  
  public void testModify() throws Exception {
    Hashtable<String, String> env = new Hashtable<String, String>();

    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

    env.put(Context.PROVIDER_URL, "ldap://10.110.21.55:389");
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, "cn=root");
    env.put(Context.SECURITY_CREDENTIALS, "passw0rd");

    DirContext ctx = new InitialDirContext(env);

    ModificationItem[] mods = new ModificationItem[2];

    Attribute mod0 = new BasicAttribute("description", "555-555-5555");
    Attribute mod1 = new BasicAttribute("st", "AAA");

    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
    mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod1);

    ctx.modifyAttributes("uid=13916918000,cn=user,dc=hljcmcc", mods);    
  }
}
