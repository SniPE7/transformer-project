package com.ibm.util;

/**
 * ����������
 */

public interface VariableResolver<T> {
  
  public String resolve(T context, String name);

}
