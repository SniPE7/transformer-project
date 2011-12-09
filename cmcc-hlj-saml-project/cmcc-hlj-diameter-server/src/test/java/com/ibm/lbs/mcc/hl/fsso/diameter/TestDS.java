package com.ibm.lbs.mcc.hl.fsso.diameter;

import junit.framework.TestCase;

public class TestDS extends TestCase {
	public void test1() throws Exception {
		DiameterServer ds = new DiameterServer();
		ds.addAppListener(new NasreqListener());
		ds.start();
	}
}
