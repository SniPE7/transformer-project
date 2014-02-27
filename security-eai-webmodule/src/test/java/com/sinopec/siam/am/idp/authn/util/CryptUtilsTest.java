package com.sinopec.siam.am.idp.authn.util;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class CryptUtilsTest extends TestCase {

    private static String key = "012=456789ab-def";
    private static String value = "{name:hello world!, date: 2014-02-25 12:21:20}";
    
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    private static String decStr = "";
    private static String enStr = "";
    
    @Test
    public void testEncrypt() {
        decStr = CryptUtils.encrypt(value, key);
        System.out.println("decStr:" + decStr);
    }

    @Test
    public void testDecrypt() throws Exception {
        enStr = CryptUtils.decrypt(decStr, key);
        System.out.println("enStr:" + enStr);
        Assert.assertEquals(enStr, value);
    }
    
    @Test
    public void testJson() throws Exception {
        Token token = new Token();
        token.setId("jsmith");
        token.setDate("2014-02-25 12:20:21");
        
        String jsonString = JSON.toJSONString(token);
        System.out.println("jsonString:" + jsonString);
        
        String desString = CryptUtils.encrypt(jsonString, key);
        System.out.println("desString:" + desString);
        
        String okString = CryptUtils.decrypt(desString, key);
        System.out.println("okString:" + okString);
        
        Token resultToken = JSON.parseObject(okString, Token.class);
        System.out.println("resultToken:" + resultToken);
        
    }
}
