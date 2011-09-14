package com.ibm.tivoli.tools;

import java.util.Date;

import javax.mail.internet.InternetAddress;

import junit.framework.TestCase;

public class InternetAddressTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testCase1() throws Exception {
    InternetAddress[] result = InternetAddress.parse("a@a.com, b@b.com");
    assertEquals(2, result.length);

  }

  public void testCase2() throws Exception {
    Date now = new Date(1314031037000L);
    System.out.println(now);
  }
}
