/**
 * 
 */
package com.ibm.ncs.util;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

/**
 * @author zhaodonglu
 *
 */
public class PolicyRuleEvaluatorImpl implements PolicyRuleEvaluator {
	
	private String language = "JavaScript";

	/**
	 * 
	 */
	public PolicyRuleEvaluatorImpl() {
		super();
	}

	public PolicyRuleEvaluatorImpl(String language) {
		super();
		this.language = language;
	}

	/* (non-Javadoc)
	 * @see com.ibm.ncs.util.PolicyRuleEvaluator#evaluate(java.lang.String, java.lang.Object)
	 */
	public boolean eval(String expression, String threshold) throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName(this.language);
		ScriptContext context = new SimpleScriptContext();
		context.setAttribute("threshold", threshold, ScriptContext.ENGINE_SCOPE);
		return Boolean.parseBoolean(engine.eval(expression, context).toString());
	}

}
