package com.ibm.util.message;

import java.util.Locale;

import org.springframework.beans.factory.InitializingBean;

/**
 * i18n消息处理工具
 * @author Wuqingming
 * @since 2012-5-7 下午4:23:28
 */

public class I18NMessageUtils implements InitializingBean {
	private static I18NMessage i18NMessage;
	
	/**
	 * 获取国际化消息
	 * @param code 消息编号
	 * @return
	 */
	public static String getMessage (String code) {
		assertI18NMessage();
		return i18NMessage.getMessage(code);
	}
	
	/**
	 * 获取国际化消息
	 * @param code 消息编号
	 * @param locale 语言
	 * @return
	 */
	public static String getMessage (String code, Locale locale) {
		assertI18NMessage();
		return i18NMessage.getMessage(code, locale);
	}


	/**
	 * 获取国际化消息
	 * @param code 消息编号
	 * @param arguments 变量参数
	 * @return
	 */
	public static String getMessage (String code, String[] arguments) {
		assertI18NMessage();
		return i18NMessage.getMessage(code, arguments);
	}

	/**
	 * 获取国际化消息
	 * @param code 消息编号
	 * @param arguments 变量参数
	 * @param locale 语言
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
	 * 检查ApplicationContext不为空.
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
