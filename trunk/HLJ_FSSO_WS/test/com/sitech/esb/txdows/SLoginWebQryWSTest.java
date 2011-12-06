/**
 * SLoginWebQryWSTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */
package com.sitech.esb.txdows;

import com.sitech.esb.txdows.SLoginWebQryWSStub.CallServiceResponse;
import com.sitech.esb.txdows.SLoginWebQryWSStub.SrvReturnBean;
import com.sitech.esb.txdows.SLoginWebQryWSStub.TxdoBuf;

/*
 * SLoginWebQryWSTest Junit test case
 */

public class SLoginWebQryWSTest extends junit.framework.TestCase {

	/**
	 * Auto generated test method
	 */
	public void testcallService() throws java.lang.Exception {

		String eb = "http://10.110.0.206:30005/esbWS/services/sLoginWebQryWS";
		// product: http://10.110.0.100:51000/esbWS/services/sLoginWebQryWS
		// 18745825555 121212

		// test: http://10.110.0.206:30005/esbWS/services/sLoginWebQryWS
		// 13945047866 111111
		// 15045451565 111111
		// 13613623842 111111
		// 13836127297 111111
		// 13945015858 111111

		SLoginWebQryWSStub stub = new SLoginWebQryWSStub(eb);
		SLoginWebQryWSStub.CallService callService2 = (SLoginWebQryWSStub.CallService) getTestObject(SLoginWebQryWSStub.CallService.class);
		
		callService2.addParams("0");
		callService2.addParams("02");
		callService2.addParams("");
		callService2.addParams("");
		callService2.addParams("");
		callService2.addParams("13945047866");
		callService2.addParams("111111");
		callService2.addParams("Y");

		CallServiceResponse resp = stub.callService(callService2);
		assertNotNull(resp);

		SrvReturnBean r = resp.get_return();
		System.out.println(r.getEsbRetCode());
		System.out.println(r.getRetCode());
		System.out.println(r.getRetMsg());
		TxdoBuf[] m = r.getRetMatrix();
		for (int i = 0; i < m.length; i++) {
			if (m[i] != null) {
				String[] buf = m[i].getBuffer();
				if (buf != null) {
					for (int j = 0; j < buf.length; j++) {
						System.out.print(buf[j] + " - ");
					}
					System.out.println();
				}
			}
		}
	}

	// Create an ADBBean and provide it as the test object
	public org.apache.axis2.databinding.ADBBean getTestObject(
			java.lang.Class type) throws java.lang.Exception {
		return (org.apache.axis2.databinding.ADBBean) type.newInstance();
	}
}
