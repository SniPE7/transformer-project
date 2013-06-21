package group.tivoli.security.eai.loginmodule.tam.service;

import java.util.List;

import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;

import com.sinopec.siam.am.idp.entity.LdapUserEntity;
import com.sinopec.siam.am.idp.ldap.DirectoryEntityContextMapper;

/**
 * TIM LDAP 操作类
 * 
 * @author xuhong
 * @since 2012-12-7 上午10:31:21
 */

public class TimUserLdapUtil {

  private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

  private final static String PEOPLE_FILTER = "(&(uid=%s)(objectclass=erPersonItem))";
  private final static String SYSTEMUSER_OWNER_FILTER = "(&(owner=%s)(objectclass=erSystemUser))";
  private final static String SYSTEMUSER_FILTER = "(&(eruid=%s)(objectclass=erSystemUser))";
  private final static String ACCOUNTS_OWNER_FILTER = "(&(owner=%s)(objectclass=erAccountItem))";
  
  private final static String TIM_CONFIGURE_FILTER = "(objectclass=ertenant)";

  private String timLdapBaseDn = "";
  
  private String confEntityDn = "";

  protected LdapTemplate ldapTemplate;

  public String getTimLdapBaseDn() {
    return timLdapBaseDn;
  }

  public void setTimLdapBaseDn(String timLdapBaseDn) {
    this.timLdapBaseDn = timLdapBaseDn;
  }

  public LdapTemplate getLdapTemplate() {
    return ldapTemplate;
  }

  public String getConfEntityDn() {
    return confEntityDn;
  }

  public void setConfEntityDn(String confEntityDn) {
    this.confEntityDn = confEntityDn;
  }

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  public LdapUserEntity getSystemUserByOwner(String owner) {
    String filter = String.format(SYSTEMUSER_OWNER_FILTER, owner);

    List<LdapUserEntity> result = searchEntity(this.timLdapBaseDn, filter);
    if (null==result || result.size() == 0) {

      log.error(String.format("systemuser not exists, owner: %s;", owner));
      return null;
    } else if (result.size() > 1) {

      log.error(String.format("Find more than one systemuser by owner, owner: %s;", owner));
      return null;
    }

    return result.get(0);
  }
  
  public LdapUserEntity getSystemUser(String eruid) {
    String filter = String.format(SYSTEMUSER_FILTER, eruid);

    List<LdapUserEntity> result = searchEntity(this.timLdapBaseDn, filter);
    if (null==result || result.size() == 0) {

      log.error(String.format("systemuser not exists, eruid: %s;", eruid));
      return null;
    } else if (result.size() > 1) {

      log.error(String.format("Find more than one systemuser by eruid, eruid: %s;", eruid));
      return null;
    }

    return result.get(0);
  }

  public LdapUserEntity getPeople(String uid) {

    String filter = String.format(PEOPLE_FILTER, uid);

    List<LdapUserEntity> result = searchEntity(this.timLdapBaseDn, filter);
    if (null==result || result.size() == 0) {

      log.error(String.format("tim people not exists, uid: %s;", uid));
      return null;
    } else if (result.size() > 1) {

      log.error(String.format("Find more than one tim people by uid, uid: %s;", uid));
      return null;
    }

    return result.get(0);
  }

  public List<LdapUserEntity> getAccountsByOwner(String owner) {
    String filter = String.format(ACCOUNTS_OWNER_FILTER, owner);

    List<LdapUserEntity> result = searchEntity(this.timLdapBaseDn, filter);
    if (null==result || result.size() == 0) {
      log.warn(String.format("tim accounts not exists, owner: %s;", owner));
      return null;
    }

    return result;
  }

  public void modifyEntity(LdapUserEntity entity, ModificationItem[] mods) {
    ldapTemplate.modifyAttributes(entity.getDn(), mods);
  }
  
  public void modifyEntity(String entityDn, ModificationItem[] mods) {
    ldapTemplate.modifyAttributes(entityDn, mods);
  }
  
  public void modifyEntity(LdapUserEntity entity, String propertyName, String value) {
    modifyEntity(entity,
        new ModificationItem[] { getOneModificationItem(propertyName, value) });
  }
  
  public void modifyEntity(String entityDn, String propertyName, String value) {
    modifyEntity(entityDn,
        new ModificationItem[] { getOneModificationItem(propertyName, value) });
  }

  @SuppressWarnings("unchecked")
  public List<LdapUserEntity> searchEntity(String baseDn, String filter) {
    return ldapTemplate.search(baseDn, filter, new DirectoryEntityContextMapper(LdapUserEntity.class));
  }
  
  public ModificationItem getOneModificationItem(String key, Object value){
    return new ModificationItem(DirContext.REPLACE_ATTRIBUTE, getOneAtrribute(key, value)); 
  }
  
  private Attribute getOneAtrribute(String key, Object value) {
    Attribute addr = new BasicAttribute(key, value);
    return addr;
  }
  
  //ou=IBM,DC=TIM,DC=COM
  public LdapUserEntity getTimConfigureEntity(){
    String filter = TIM_CONFIGURE_FILTER;

    List<LdapUserEntity> result = searchEntity(this.confEntityDn, filter);
    if (null==result || result.size() == 0) {
      log.error(String.format(" TimConfigureEntity not exists, owner: %s;", this.confEntityDn));
      return null;
    } else if (result.size() > 1) {

      log.error(String.format("Find more than one TimConfigureEntity by owner, owner: %s;", this.confEntityDn));
      return null;
    }

    return result.get(0);
  }

}
