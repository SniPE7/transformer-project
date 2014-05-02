package com.ibm.util.message;

import java.util.Locale;

/**
 * i18n��Ϣ����ӿ�
 * @author  Wuqingming
 * @since 2012-4-13
 */
public interface I18NMessage {

	/**
	 * ��ȡ���ʻ���Ϣ
	 * @param code ��Ϣ���
	 * @return
	 */
	public String getMessage (String code);
	
	/**
	 * ��ȡ���ʻ���Ϣ
	 * @param code ��Ϣ���
	 * @param locale ����
	 * @return
	 */
	public String getMessage (String code, Locale locale);


	/**
	 * ��ȡ���ʻ���Ϣ
	 * @param code ��Ϣ���
	 * @param arguments ��������
	 * @return
	 */
	public String getMessage (String code, String[] arguments);

	/**
	 * ��ȡ���ʻ���Ϣ
	 * @param code ��Ϣ���
	 * @param arguments ��������
	 * @param locale ����
	 * @return
	 */
	public String getMessage (String code, String[] arguments, Locale locale);
}

