package com.ibm.siam.am.idp.authn.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.HardcodedFilter;

import com.ibm.siam.am.idp.ldap.DirectoryEntityContextMapper;
import com.ibm.siam.am.idp.entity.LdapUserEntity;

public class TamPersonService implements PersonService {

	/** Class logger. */
	private final Logger log = LoggerFactory.getLogger(TamPersonService.class);

	protected LdapTemplate ldapTemplate;

	private String ldapUserBaseDN;

	private String ldapSecurityUserBaseDN;

	private String ldapSecurityUserNameAttr = "principalName";

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public String getLdapUserBaseDN() {
		return ldapUserBaseDN;
	}

	public void setLdapUserBaseDN(String ldapUserBaseDN) {
		this.ldapUserBaseDN = ldapUserBaseDN;
	}

	public String getLdapSecurityUserBaseDN() {
		return ldapSecurityUserBaseDN;
	}

	public void setLdapSecurityUserBaseDN(String ldapSecurityUserBaseDN) {
		this.ldapSecurityUserBaseDN = ldapSecurityUserBaseDN;
	}

	public String getLdapSecurityUserNameAttr() {
		return ldapSecurityUserNameAttr;
	}

	public void setLdapSecurityUserNameAttr(String ldapSecurityUserNameAttr) {
		this.ldapSecurityUserNameAttr = ldapSecurityUserNameAttr;
	}

	public void updatePerson(String username, Map<String, String> attrs) throws PersonNotFoundException, MultiplePersonFoundException, PersonServiceException {
		if (log.isDebugEnabled()) {
			log.debug("Update User Pass by username [{}]", username);
		}

		List<LdapUserEntity> result = getUserByUsername(username);
		if (result.size() == 0) {
			log.error("Username not exists, username [{}]", username);
			throw new PersonNotFoundException(String.format("Username not exists, username[%s]", username));
		} else if (result.size() > 1) {
			log.error("Find more than one user, username [{}]", username);
			throw new MultiplePersonFoundException(String.format("Find more than one user by username, username[%s]", username));
		}
		LdapUserEntity userEntity = result.get(0);
		try {
			List<ModificationItem> mitems = new ArrayList<ModificationItem>();
			for (String name : attrs.keySet()) {
				mitems.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(name, attrs.get(name))));
			}
			this.ldapTemplate.modifyAttributes(userEntity.getDn(), mitems.toArray(new ModificationItem[0]));
		} catch (Exception e) {
			log.error(String.format("fail to update person[%s] with attrs:[%s]", username, attrs), e);
			throw new PersonServiceException(String.format("fail to update person[%s] with attrs:[%s]", username, attrs), e);
		}

	}

	public void updatePassword(String username, String password) throws PersonNotFoundException, MultiplePersonFoundException, PersonServiceException {
		if (log.isDebugEnabled()) {
			log.debug("Update User Pass by username [{}]", username);
		}

		List<LdapUserEntity> result = getUserByUsername(username);
		if (result.size() == 0) {
			log.error("Username not exists, username [{}]", username);
			throw new PersonNotFoundException(String.format("Username not exists, username[%s]", username));
		} else if (result.size() > 1) {
			log.error("Find more than one user, username [{}]", username);
			throw new MultiplePersonFoundException(String.format("Find more than one user by username, username[%s]", username));
		}
		LdapUserEntity userEntity = result.get(0);
		try {
			// Update password
			ModificationItem mi = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", password));
			this.ldapTemplate.modifyAttributes(userEntity.getDn(), new ModificationItem[] { mi });
			
			// Update SecUser password modify time
			List<LdapUserEntity> r = getSecUserByUsername(username);
			if (result.size() == 0) {
				log.warn("SecUser not exists, username [{}]", username);
			} else if (result.size() > 1) {
				log.warn("Find more than one SecUser, username [{}]", username);
			}
			String lastChangeTime = (new SimpleDateFormat("yyyyMMddHHmmssZ")).format(new Date());
			mi = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("secPwdLastChanged", lastChangeTime));
			this.ldapTemplate.modifyAttributes(r.get(0).getDn(), new ModificationItem[] { mi });
			
		} catch (Exception e) {
			log.error(String.format("fail to update password[%s]", username), e);
			throw new PersonServiceException(String.format("fail to update password[%s]", username), e);
		}
	}

	protected List<LdapUserEntity> getUserByUsername(String username) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("Get User by username:%s", username));
		}
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("uid", username));
		filter.and(new HardcodedFilter("(objectclass=inetOrgPerson)"));
		return ldapTemplate.search(ldapUserBaseDN, filter.encode(), new DirectoryEntityContextMapper(LdapUserEntity.class));
	}

	protected List<LdapUserEntity> getSecUserByUsername(String username) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("Get User by username:%s", username));
		}
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(ldapSecurityUserNameAttr, username));
		filter.and(new HardcodedFilter("(objectclass=secUser)"));
		return ldapTemplate.search(ldapSecurityUserBaseDN, filter.encode(), new DirectoryEntityContextMapper(LdapUserEntity.class));
	}

}
