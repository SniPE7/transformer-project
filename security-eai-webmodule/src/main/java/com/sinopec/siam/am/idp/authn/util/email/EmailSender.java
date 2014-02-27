package com.sinopec.siam.am.idp.authn.util.email;

/**
 * �ʼ�������
 */
public interface EmailSender {
    /**
     * ���ͷ�����IP
     * 
     * @return
     */
    public String getHost();

    /**
     * ���ͷ������˿�
     * 
     * @return
     */
    public Integer getPort();

    /**
     * ���ͱ���
     * 
     * @return
     */
    public String getEncoding();

    /**
     * ��¼��
     * 
     * @return
     */
    public String getUsername();

    /**
     * ����
     * 
     * @return
     */
    public String getPassword();

    /**
     * ������
     * 
     * @return
     */
    public String getPersonal();
}
