package com.ibm.lbs.mcc.hl.fsso.diameter;

import junit.framework.TestCase;

public class TestDC extends TestCase {

	public void test1() throws Exception {
		DiameterClient ds = new DiameterClient();
		ds.sendTestAuth();
		ds.sendTestAuth();
		ds.sendTestAuth();

		System.in.read();
		System.out.println("Shutdown in 10 seconds...");
		Thread.sleep(10000);
		ds.close();
	}
}
