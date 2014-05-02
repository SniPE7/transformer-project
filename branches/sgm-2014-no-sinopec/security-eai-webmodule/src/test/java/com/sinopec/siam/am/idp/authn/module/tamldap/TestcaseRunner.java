/**
 * 
 */
package com.sinopec.siam.am.idp.authn.module.tamldap;

import java.lang.reflect.Method;

import junit.framework.TestCase;

/**
 * @author zhaodonglu
 *
 */
public class TestcaseRunner implements Runnable {
  
  private TestCase testCase = null;
  private String testMethodName = null;
  private String name;
  private PerformanceMonitor performanceMonitor = null;

  public static class MonitorThread implements Runnable {
    private PerformanceMonitor performanceMonitor = null;

    public MonitorThread(PerformanceMonitor performanceMonitor) {
      super();
      this.performanceMonitor = performanceMonitor;
    }

    public void run() {
      while (true) {
        try {
          System.err.println(this.performanceMonitor.toString());
          Thread.sleep(1 * 1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

  }

  public TestcaseRunner(String name, TestCase testCase, String testMethodName, PerformanceMonitor performanceMonitor) {
    super();
    this.testCase = testCase;
    this.testMethodName = testMethodName;
    this.name = name;
    this.performanceMonitor = performanceMonitor;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void run() {
    Method testMethod = null;
    try {
      Method setUpMethod = testCase.getClass().getMethod("setUp");
      //setUpMethod.invoke(this.testCase);
      testMethod = testCase.getClass().getMethod(this.testMethodName );
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    while (true) {
      long beginTime = System.currentTimeMillis();
      try {
        testMethod.invoke(this.testCase);
        performanceMonitor.incSuccess();
      } catch (Exception e) {
        e.printStackTrace();
        performanceMonitor.incFailure();
      } finally {
        long endTime = System.currentTimeMillis();
        this.performanceMonitor.addElapseTime(endTime - beginTime);
      }
    }
  }
  
  /**
   * 
   */
  public TestcaseRunner() {
    super();
  }
  
  public static void main(String[] args) throws Exception {
    TamLdapUserPassLoginModuleTest testcase = new TamLdapUserPassLoginModuleTest();
    testcase.setUp();
    
    PerformanceMonitor performanceMonitor = new PerformanceMonitor();
    Thread monitorThread = new Thread(new MonitorThread(performanceMonitor));
    monitorThread.start();
    
    int totalThread = 50;
    Thread[] threads = new Thread[totalThread ];
    for (int i = 0; i < threads.length; i++) {
        TestcaseRunner runner = new TestcaseRunner("testSuccess" + i, testcase, "testSuccess", performanceMonitor);
        Thread thread = new Thread(runner);
        threads[i] = thread;
        thread.start();
    }
    for (int i = 0; i < threads.length; i++) {
      threads[i].join();
    }
    monitorThread.interrupt();
    
  }


}
