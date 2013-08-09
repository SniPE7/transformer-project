package com.sinopec.siam.am.idp.authn.service;

import java.util.Map;

public interface PersonService {

	/**
	 * 更新用户基本属性信息.
	 * 
	 * @param username
	 *           更新目标用户的用户名
	 * @param attrs
	 *            更新的属性信息
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
