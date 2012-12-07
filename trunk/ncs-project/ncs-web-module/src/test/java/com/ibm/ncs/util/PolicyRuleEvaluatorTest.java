/**
 * 
 */
package com.ibm.ncs.util;

import javax.script.*;
import javax.script.ScriptEngineManager;

import junit.framework.TestCase;

/**
 * @author zhaodonglu
 * 
 */
public class PolicyRuleEvaluatorTest extends TestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCase1() throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		Object v = engine.eval("'Hello World'");
		assertEquals("Hello World", v);
	}

	public void testCase2() throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		ScriptContext context = new SimpleScriptContext();
		context.setAttribute("greeting", "Hello from eval method", ScriptContext.ENGINE_SCOPE);
		Object v = engine.eval("print('Hello World, ' + greeting)", context);
	}
	
	public void testCase3() throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		ScriptContext context = new SimpleScriptContext();
		context.setAttribute("threshold", "6", ScriptContext.ENGINE_SCOPE);
		assertTrue(Boolean.parseBoolean(engine.eval("threshold > 5 && threshold < 10", context).toString()));
		context.setAttribute("threshold", "1", ScriptContext.ENGINE_SCOPE);
		assertFalse(Boolean.parseBoolean(engine.eval("threshold > 5 && threshold < 10", context).toString()));
	}
	
	public void testCase4() throws Exception {
		PolicyRuleEvaluator evaluator = new PolicyRuleEvaluatorImpl("JavaScript");
		assertTrue(evaluator.eval("threshold >= 5 && threshold < 10", "7"));
		assertFalse(evaluator.eval("threshold >= 5 && threshold < 10", "11"));
	}
}
