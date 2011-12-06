package com.ibm.lbs.mcc.hl.fsso.boss;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

public class BossTest extends TestCase {
	// product: http://10.110.0.100:51000/esbWS/services/sLoginWebQryWS
	// 18745825555 121212

	// test: http://10.110.0.206:30005/esbWS/services/sLoginWebQryWS
	// 13946099676 111111
	// 13945047866 111111
	// 15045451565 111111
	// 13613623842 111111
	// 13836127297 111111
	// 13945015858 111111
	Map<String, String> prodUsers = new HashMap<String, String>();
	{
		prodUsers.put("18745825555", "121212");
	}
	Map<String, String> testUsers = new HashMap<String, String>();
	{
		testUsers.put("13946099676", "121212");
		testUsers.put("13945047866", "111111");
		testUsers.put("15045451565", "111111");
		testUsers.put("13613623842", "111111");
		testUsers.put("13836127297", "111111");
		testUsers.put("13945015858", "111111");
	}

	public void test1() {
		for (Entry<String, String> entry : testUsers.entrySet()) {
			System.out.println("========================= test env");
			Map<String, String> bean = Boss.auth(Boss.TestEndPoint, entry
					.getKey(), entry.getValue());
			System.out.println(bean);
		}
	}

	public void test2() {
		for (Entry<String, String> entry : prodUsers.entrySet()) {
			System.out.println("========================= product env ok");
			Map<String, String> bean = Boss.auth(entry.getKey(), entry
					.getValue());
			System.out.println(bean);
		}
	}

	public void test3() {
		for (Entry<String, String> entry : prodUsers.entrySet()) {
			System.out
					.println("========================= product env password failed");
			Map<String, String> bean = Boss.auth(entry.getKey(), "wrongpassword");
			System.out.println(bean);
		}
	}
}
