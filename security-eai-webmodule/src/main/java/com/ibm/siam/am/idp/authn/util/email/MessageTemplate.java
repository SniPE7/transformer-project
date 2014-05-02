package com.ibm.siam.am.idp.authn.util.email;

/**
 * �ʼ�ģ��
 */
public interface MessageTemplate {
    /**
     * �һ���������
     * 
     * @return
     */
    public String getForgotPasswordSubject();

    /**
     * �һ���������
     * 
     * @return
     */
    public String getForgotPasswordText();
    
    /**
     * ��Աע������
     * 
     * @return
     */
    public String getRegisterSubject();

    /**
     * ��Աע������
     * 
     * @return
     */
    public String getRegisterText();
}
