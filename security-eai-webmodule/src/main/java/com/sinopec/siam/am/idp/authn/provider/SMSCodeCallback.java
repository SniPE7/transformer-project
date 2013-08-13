package com.sinopec.siam.am.idp.authn.provider;

import java.io.Serializable;
import javax.security.auth.callback.Callback;

public class SMSCodeCallback implements Callback, Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = -8773266382479992513L;

	/**
	 * ��̨���ɵĶ�����֤��
	 */
	private String smsCode = null;

	/**
	 * MatchCode����ѯ�û�
	 */
	private String matchCode = null;

	/**
	 * �û�����Ķ�����֤��
	 */
	private String codeFromInput = null;

	public SMSCodeCallback() {
		super();
	}

	public String getSMSCode() {
		return smsCode;
	}

	public void setSMSCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getCodeFromInput() {
		return codeFromInput;
	}

	public void setCodeFromInput(String userInputCode) {
		this.codeFromInput = userInputCode;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getMatchCode() {
		return matchCode;
	}

	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}

}
