package com.sinopec.siam.am.idp.authn.util.email;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class EmailSendToolTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSend() {
        
        EmailConfig mailConf = new EmailConfig();
        mailConf.setHost("smtp.163.com");
        mailConf.setUsername("xrogzu@163.com");
        mailConf.setPassword("******");
        mailConf.setPersonal("用户管理员");
        
        EmailSendTool sendEmail = new EmailSendTool(mailConf.getHost(),
                                                    mailConf.getUsername(), mailConf.getPassword(), "276577301@qq.com",
                                                    "测试", "文本内容", mailConf.getPersonal(), "", "");
                                            try {
                                                sendEmail.send();
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
    }

}
