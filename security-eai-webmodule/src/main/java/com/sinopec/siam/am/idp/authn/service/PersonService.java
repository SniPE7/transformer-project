package com.sinopec.siam.am.idp.authn.service;

import java.util.Map;

public interface PersonService {

	/**
	 * �޸��û�����
	 * 
	 * @param username
	 *            �޸ĵ���Ա��ʾ
	 * @param attrs
	 *            �޸����Լ���
	 */
	public void updatePerson(String username, Map<String, String> attrs);
	
	public void updatePassword(String username, String password);
}
