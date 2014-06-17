package com.ibm.siam.am.idp.authn;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class AccessThrotterTest extends TestCase {

  public void setUp() throws Exception {
  }

  public void tearDown() throws Exception {
  }

  public void testCase1() {
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();
    // req.setCookies(new Cookie[]{new Cookie(AccessThrotter.COOKIE_NAME, "")});
    AccessThrotter throtter = new AccessThrotter(3600 * 24 * 3, 10);

    for (int i = 1; i < 21; i++) {
      boolean overload = throtter.isOverLoad(req, resp);
      if (i == 10 || i == 20) {
         assertTrue(overload);
      } else {
        assertFalse(overload);
      }
      req.setCookies(resp.getCookies());
      assertNotNull(resp.getCookies());
      System.err.println(i + "#: " + resp.getCookies()[0].getValue());
    }

  }

  public void testCase2() throws Exception {
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();
    // req.setCookies(new Cookie[]{new Cookie(AccessThrotter.COOKIE_NAME, "")});
    AccessThrotter throtter = new AccessThrotter(0, 10);

    for (int i = 1; i < 21; i++) {
      boolean overload = throtter.isOverLoad(req, resp);
      assertFalse(overload);
      Thread.sleep(1000L);
      req.setCookies(resp.getCookies());
      assertNotNull(resp.getCookies());
      System.err.println(i + "#: " + resp.getCookies()[0].getValue());
    }

  }

}
