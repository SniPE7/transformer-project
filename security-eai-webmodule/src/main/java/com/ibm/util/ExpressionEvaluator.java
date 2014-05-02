/**
 * 
 */
package com.ibm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhaodonglu
 *
 */
public class ExpressionEvaluator<T> {
  
  private static final Pattern DEFAULT_VARAIBLE_PATTERN = Pattern.compile("\\{[a-zA-Z\\.]*\\}");
  private VariableResolver<T> variableResolver = null;
  private Pattern pattern = DEFAULT_VARAIBLE_PATTERN;

  /**
   * 
   */
  public ExpressionEvaluator() {
    super();
  }
  
  public ExpressionEvaluator(VariableResolver<T> variableResolver) {
    super();
    this.variableResolver = variableResolver;
  }

  public ExpressionEvaluator(VariableResolver<T> variableResolver, String variablePattern) {
    this(variableResolver);
    this.pattern = Pattern.compile(variablePattern);
  }

  public String evaluate(String expression, T context) {
    String result = expression;
    Matcher regularMatcher = pattern.matcher(expression);
    /* 替换过滤条件中变量在证书中用户信息属性值 */
    while (regularMatcher.find()) {
      String name = regularMatcher.group();
      name = name.replace("{", "");
      name = name.replace("}", "");
      String value = variableResolver.resolve(context, name);

      result = result.replace(regularMatcher.group(), (value==null)?"":value);
    }
    return result;
  }
  
}
