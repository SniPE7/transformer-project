package com.ibm.util.message;

import java.util.Locale;

import org.springframework.beans.factory.InitializingBean;

/**
 * i18n��Ϣ������
 * @author Wuqingming
 * @since 2012-5-7 ����4:23:28
 */

public class I18NMessageUtils implements InitializingBean {
	private static I18NMessage i18NMessage;
	
	/**
	 * ��ȡ���ʻ���Ϣ
	 * @param code ��Ϣ���
	 * @return
	 */
	public static String getMessage (String code) {
		assertI18NMessage();
		return i18NMessage.getMessage(code);
	}
	
	/**
	 * ��ȡ���ʻ���Ϣ
	 * @param code ��Ϣ���
	 * @param locale ����
	 * @return
	 */
	public static String getMessage (String code, Locale locale) {
		assertI18NMessage();
		return i18NMessage.getMessage(code, locale);
	}


	/**
	 * ��ȡ���ʻ���Ϣ
	 * @param code ��Ϣ���
	 * @param arguments ��������
	 * @return
	 */
	public static String getMessage (String code, String[] arguments) {
		assertI18NMessage();
		return i18NMessage.getMessage(code, arguments);
	}

	/**
	 * ��ȡ���ʻ���Ϣ
	 * @param code ��Ϣ���
	 * @param arguments ��������
	 * @param locale ����
	 * @return
	 */
	public static String getMessage (String code, String[] arguments, Locale locale) {
		assertI18NMessage();
		return i18NMessage.getMessage(code, arguments, locale);
	}

	public void setI18NMessage(I18NMessage i18nMessage) {
		i18NMessage = i18nMessage;
	}
	
	/**
	 * ���ApplicationContext��Ϊ��.
	 */
	private static void assertI18NMessage() {
		if (i18NMessage == null) {
			throw new IllegalStateException("i18NMessage can not be null.");
		}
	}

	public void afterPropertiesSet() throws Exception {
      if (this.i18NMessage == null) {
        throw new Exception("i18NMessage can not be null");
      }
	}
}
