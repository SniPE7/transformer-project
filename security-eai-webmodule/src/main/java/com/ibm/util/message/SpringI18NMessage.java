package com.ibm.util.message;

import java.util.Locale;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 基于Spring的消息国际化实现
 */
public class SpringI18NMessage implements I18NMessage, InitializingBean {
	private MessageSource messageSource = null;

	public SpringI18NMessage(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/** {@inheritDoc} */
	public String getMessage(String code) {
		return this.messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
	}

	/** {@inheritDoc} */
	public String getMessage(String code, Locale locale) {
		return this.messageSource.getMessage(code, null, locale);
	}

	/** {@inheritDoc} */
	public String getMessage(String code, String[] arguments) {
		return this.messageSource.getMessage(code, arguments, LocaleContextHolder.getLocale());
	}

	/** {@inheritDoc} */
	public String getMessage(String code, String[] arguments, Locale locale) {
		return this.messageSource.getMessage(code, arguments, locale);
	}

	public void afterPropertiesSet() throws Exception {
	  if (this.messageSource == null) {
		throw new Exception("messageSource can not be null");
	  }
	}
	
}

