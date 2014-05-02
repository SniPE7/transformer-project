package com.ibm.util.message;

import java.util.Locale;

/**
 * i18n消息处理接口
 * @author  Wuqingming
 * @since 2012-4-13
 */
public interface I18NMessage {

	/**
	 * 获取国际化消息
	 * @param code 消息编号
	 * @return
	 */
	public String getMessage (String code);
	
	/**
	 * 获取国际化消息
	 * @param code 消息编号
	 * @param locale 语言
	 * @return
	 */
	public String getMessage (String code, Locale locale);


	/**
	 * 获取国际化消息
	 * @param code 消息编号
	 * @param arguments 变量参数
	 * @return
	 */
	public String getMessage (String code, String[] arguments);

	/**
	 * 获取国际化消息
	 * @param code 消息编号
	 * @param arguments 变量参数
	 * @param locale 语言
	 * @return
	 */
	public String getMessage (String code, String[] arguments, Locale locale);
}

