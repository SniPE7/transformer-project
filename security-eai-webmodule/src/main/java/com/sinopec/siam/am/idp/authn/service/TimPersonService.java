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

	private String userOwnerLdapAttribute;

	protected LdapTemplate ldapTemplate;

	private String ldapUserBaseDN;

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

	public void updatePerson(String username, Map<String, String> attrs) throws PersonNotFoundException, MultiplePersonFoundException, PersonServiceException {
		if (log.isDebugEnabled()) {
			log.debug("Update User by username [{}], attrs[{}]", username, attrs);
		}

		List<LdapUserEntity> result = getUserByUsername(username);
		if (result.size() == 0) {
			log.error("Username not exists, username [{}]", username);
			throw new PersonNotFoundException(String.format("Username not exists, username[%s]", username));
		} else if (result.size() > 1) {
			log.error("Find more than one user, username [{}]", username);
			throw new MultiplePersonFoundException(String.format("Find more than one user by username, username[%s]",	username));
		}
		LdapUserEntity userEntity = result.get(0);
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
			
			String personDN = userEntity.getValueAsString(userOwnerLdapAttribute);
			if (log.isDebugEnabled()) {
				 log.debug(String.format("modify person[%s], wsAttrs:[%s], session[%s]", personDN, wsAttrs, wsSession));
			}
			wsItimService.modifyPerson(wsSession, personDN, wsAttrs.toArray(new WSAttribute[]{}), null);

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
			throw new PersonNotFoundException(String.format(
					"Username not exists, username[%s]", username));
		} else if (result.size() > 1) {
			log.error("Find more than one user, username [{}]", username);
			throw new MultiplePersonFoundException(String.format(
					"Find more than one user by username, username[%s]",
					username));
		}
		LdapUserEntity userEntity = result.get(0);
		ITIMWebServiceFactory webServiceFactory;
		try {
			webServiceFactory = new ITIMWebServiceFactory(timSoapEndpoint);

			WSItimService wsItimService = webServiceFactory.getWSItimService();
			Calendar scheduledTime = Calendar.getInstance();
			scheduledTime.setTime(new Date());
			// TIM WS �������޸Ŀ���ʱ����nullΪSchedule, ������ʱ��ǰ����30����
			scheduledTime.add(Calendar.MINUTE, -30);
			WSSession wsSession = wsItimService.login(itimManager, itimManagerPwd);
			
			String personDn = userEntity.getValueAsString("owner");
			wsItimService.synchPasswords(wsSession, personDn, password, null , false);
		} catch (Exception e) {
			log.error(String.format("fail to update password[%s]", username), e);
			throw new PersonServiceException(String.format("fail to update password[%s]", username), e);
		}
	}

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
