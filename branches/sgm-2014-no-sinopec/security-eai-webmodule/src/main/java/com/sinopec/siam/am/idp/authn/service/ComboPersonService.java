package com.sinopec.siam.am.idp.authn.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ��ϸ��·���, ���TIM���޷��ҵ���ص��û������ٳ���ͨ��TAMLDAPֱ���޸�.
 *
 */
public class ComboPersonService implements PersonService {
	
	private final Logger log = LoggerFactory.getLogger(TimPersonService.class);
	
	private PersonService timPersonService = null;
	private PersonService tamPersonService = null;

	public ComboPersonService() {
		super();
	}

	public PersonService getTimPersonService() {
		return timPersonService;
	}

	public void setTimPersonService(PersonService timPersonService) {
		this.timPersonService = timPersonService;
	}

	public PersonService getTamPersonService() {
		return tamPersonService;
	}

	public void setTamPersonService(PersonService tamPersonService) {
		this.tamPersonService = tamPersonService;
	}

	public void updatePerson(String username, Map<String, String> attrs) throws PersonNotFoundException, MultiplePersonFoundException, PersonServiceException {
		try {
	    this.timPersonService.updatePerson(username, attrs);
    } catch (PersonNotFoundException e) {
    	log.debug(String.format("Could not find person[%s], try to directly update TAM LDAP", username));
	    this.tamPersonService.updatePerson(username, attrs);
    }
	}

	public void updatePassword(String username, String password) throws PersonNotFoundException, MultiplePersonFoundException, PersonServiceException {
		try {
	    this.timPersonService.updatePassword(username, password);
    } catch (PersonNotFoundException e) {
    	log.debug(String.format("Could not find person[%s], try to directly update TAM LDAP", username));
	    this.tamPersonService.updatePassword(username, password);
    }
	}

}
