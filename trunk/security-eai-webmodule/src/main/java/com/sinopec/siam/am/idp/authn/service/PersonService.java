package com.sinopec.siam.am.idp.authn.service;

import java.util.Map;

/**
 * 人员属性修改服务接口
 * 
 * @author zhangxianwen
 * @since 2012-6-20 上午9:35:40
 */

public interface PersonService {

	/**
	 * 修改用户属性
	 * 
	 * @param username
	 *            修改的人员标示
	 * @param attrs
	 *            修改属性集合
	 */
	public void updatePerson(String username, Map<String, String> attrs);
}
