package com.ibm.ncs.util;

import junit.framework.TestCase;

public class PasswordUtilTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testEncode() {
		assertEquals("{SHA1}XCpDY0r7Odv+l4PC6eeGUv16ojw=", PasswordUtil.encode("passsw0rd"));
	}

	public void testVerify() {
		assertTrue(PasswordUtil.verify("passsw0rd", "{SHA1}XCpDY0r7Odv+l4PC6eeGUv16ojw="));
		assertTrue(PasswordUtil.verify("passsw0rd", "passsw0rd"));
		assertTrue(PasswordUtil.verify("passsw0rd", "cGFzc3N3MHJk"));

		assertFalse(PasswordUtil.verify("passsw0rd1", "{SHA1}XCpDY0r7Odv+l4PC6eeGUv16ojw="));
		assertFalse(PasswordUtil.verify("passsw0rd1", "passsw0rd"));
		assertFalse(PasswordUtil.verify("passsw0rd1", "cGFzc3N3MHJk"));
}

}
