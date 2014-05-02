package com.ibm.util;

/**
 * ±äÁ¿½âÎöÆ÷
 */

public interface VariableResolver<T> {
  
  public String resolve(T context, String name);

}
