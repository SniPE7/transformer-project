/**
 * 
 */
package com.ibm.ncs.util;

import javax.script.ScriptException;

/**
 * @author zhaodonglu
 *
 */
public interface PolicyRuleEvaluator {
	
  public boolean eval(String expression, String threshold) throws ScriptException;
}
