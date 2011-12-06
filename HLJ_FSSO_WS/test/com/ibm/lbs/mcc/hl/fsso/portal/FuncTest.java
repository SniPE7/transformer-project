package com.ibm.lbs.mcc.hl.fsso.portal;

import java.util.Map;

import junit.framework.TestCase;

public class FuncTest extends TestCase {
	public void test1() {
		Map map = Func.register("13945015858", "111111");
		System.out.println("return:" + map);
	}
}
