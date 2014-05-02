package com.ibm.siam.am.idp.authn.service;

import java.util.Map;

public interface PersonService {

	/**
	 * �����û�����������Ϣ.
	 * 
	 * @param username
	 *           ����Ŀ���û����û���
	 * @param attrs
	 *            ���µ�������Ϣ
	 * @throws PersonNotFoundException 
	 * @throws MultiplePersonFoundException 
	 * @throws PersonServiceException 
	 */
	public void updatePerson(String username, Map<String, String> attrs) throws PersonNotFoundException, MultiplePersonFoundException, PersonServiceException;
	
	/**
	 * @param username
	 * @param password
	 * @throws PersonNotFoundException
	 * @throws MultiplePersonFoundException
	 * @throws PersonServiceException
	 */
	public void updatePassword(String username, String password) throws PersonNotFoundException, MultiplePersonFoundException, PersonServiceException;
}
