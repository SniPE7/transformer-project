package com.sinopec.siam.am.idp.authn.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.HardcodedFilter;

import com.ibm.itim.ws.model.WSAttribute;
import com.ibm.itim.ws.model.WSSession;
import com.ibm.itim.ws.services.WSItimService;
import com.ibm.itim.ws.services.facade.ITIMWebServiceFactory;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;
import com.sinopec.siam.am.idp.ldap.DirectoryEntityContextMapper;

public class TimPersonService implements PersonService {

	/** Class logger. */
	private final Logger log = LoggerFactory.getLogger(TimPersonService.class);

	/** ITIM API */
	private String timSoapEndpoint;

	/** ITIM manager account */
	private String itimManager;

	/** ITIM manager account password */
	private String itimManagerPwd;

	/** ITIM update user's password notify by mail */
	private boolean notifyByMail;

	/** 锟矫伙拷拥锟斤拷锟斤拷DNLdap锟斤拷锟斤拷锟斤拷 */
	private String userOwnerLdapAttribute;

	protected LdapTemplate ldapTemplate;

	private String ldapUserBaseDN;

	/** userName锟斤拷应ldap锟斤拷锟斤拷锟斤拷锟�*/
	private String userNameLdapAttribute;

	public String getTimSoapEndpoint() {
		return timSoapEndpoint;
	}

	public void setTimSoapEndpoint(String timSoapEndpoint) {
		this.timSoapEndpoint = timSoapEndpoint;
	}

	public String getItimManager() {
		return itimManager;
	}

	public void setItimManager(String itimManager) {
		this.itimManager = itimManager;
	}

	public String getItimManagerPwd() {
		return itimManagerPwd;
	}

	public void setItimManagerPwd(String itimManagerPwd) {
		this.itimManagerPwd = itimManagerPwd;
	}

	public boolean isNotifyByMail() {
		return notifyByMail;
	}

	public void setNotifyByMail(boolean notifyByMail) {
		this.notifyByMail = notifyByMail;
	}

	public String getUserOwnerLdapAttribute() {
		return userOwnerLdapAttribute;
	}

	public void setUserOwnerLdapAttribute(String userOwnerLdapAttribute) {
		this.userOwnerLdapAttribute = userOwnerLdapAttribute;
	}

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

	public String getUserNameLdapAttribute() {
		return userNameLdapAttribute;
	}

	public void setUserNameLdapAttribute(String userNameLdapAttribute) {
		this.userNameLdapAttribute = userNameLdapAttribute;
	}

	public Logger getLog() {
		return log;
	}

	public void updatePerson(String username, Map<String, String> attrs) {
		if (log.isDebugEnabled()) {
			log.debug("Update User Pass by username [{}]", username);
		}

		List<LdapUserEntity> result = getUserByUsername(username);
		if (result.size() == 0) {
			log.error("Username not exists, username [{}]", username);
			throw new RuntimeException(String.format(
					"Username not exists, username[%s]", username));
		} else if (result.size() > 1) {
			log.error("Find more than one user, username [{}]", username);
			throw new RuntimeException(String.format(
					"Find more than one user by username, username[%s]",
					username));
		}
		LdapUserEntity userEntity = result.get(0);
		// 锟睫革拷TIM Person 锟斤拷锟皆ｏ拷API锟斤拷
		ITIMWebServiceFactory webServiceFactory;
		try {
			webServiceFactory = new ITIMWebServiceFactory(timSoapEndpoint);

			WSItimService wsItimService = webServiceFactory.getWSItimService();
			Calendar scheduledTime = Calendar.getInstance();
			scheduledTime.setTime(new Date());
			WSSession wsSession = wsItimService.login(itimManager,
					itimManagerPwd);
			
			List<WSAttribute> wsAttrs =  new ArrayList<WSAttribute>();
			for(String key: attrs.keySet()) {
				WSAttribute wsAttr = new WSAttribute(key, new String[]{attrs.get(key)});
				wsAttrs.add(wsAttr);
			} 
			
			wsItimService.modifyPerson(wsSession, userEntity.getValueAsString(userOwnerLdapAttribute), wsAttrs.toArray(new WSAttribute[]{}), scheduledTime);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("updatePerson fail: ", e);
		}

	}
	
	public void updatePassword(String username, String password) {
		if (log.isDebugEnabled()) {
			log.debug("Update User Pass by username [{}]", username);
		}

		List<LdapUserEntity> result = getUserByUsername(username);
		if (result.size() == 0) {
			log.error("Username not exists, username [{}]", username);
			throw new RuntimeException(String.format(
					"Username not exists, username[%s]", username));
		} else if (result.size() > 1) {
			log.error("Find more than one user, username [{}]", username);
			throw new RuntimeException(String.format(
					"Find more than one user by username, username[%s]",
					username));
		}
		LdapUserEntity userEntity = result.get(0);
		// 锟睫革拷TIM Person 锟斤拷锟皆ｏ拷API锟斤拷
		ITIMWebServiceFactory webServiceFactory;
		try {
			webServiceFactory = new ITIMWebServiceFactory(timSoapEndpoint);

			WSItimService wsItimService = webServiceFactory.getWSItimService();
			Calendar scheduledTime = Calendar.getInstance();
			scheduledTime.setTime(new Date());
			WSSession wsSession = wsItimService.login(itimManager, itimManagerPwd);
			
			String personDn = userEntity.getValueAsString("owner");
			wsItimService.synchPasswords(wsSession, personDn, password, scheduledTime , false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("updatePerson fail: ", e);
		}
	}

	/**
	 * 锟斤拷取锟矫伙拷锟斤拷息
	 * 
	 * @param username
	 *            锟矫伙拷锟斤拷
	 * @return List<LdapUserEntity> 锟矫伙拷锟斤拷息锟斤拷锟斤拷锟斤拷没锟斤拷锟斤拷锟斤拷锟�list.size=0
	 */
	@SuppressWarnings("unchecked")
	protected List<LdapUserEntity> getUserByUsername(String username) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("Get User by username:%s", username));
		}
		Filter filter = getUserSearchFilterByUsername(username);
		return ldapTemplate.search(ldapUserBaseDN, filter.encode(),
				new DirectoryEntityContextMapper(LdapUserEntity.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinopec.siam.am.idp.authn.service.AbstractUserPassService#
	 * getUserSearchFilterByUsername(java.lang.String)
	 */
	protected Filter getUserSearchFilterByUsername(String username) {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter(this.getUserNameLdapAttribute(),
				username));
		andFilter.and(new HardcodedFilter("(objectclass=erSystemUser)"));
		return andFilter;
	}

}
