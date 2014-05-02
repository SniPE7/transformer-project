package com.ibm.siam.am.idp.authn.service;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseTestCase extends TestCase {

	protected ApplicationContext context = null;

	public BaseTestCase() {
		super();
	}

	public BaseTestCase(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		String[] locations = {"/eai/conf/spring-beans-config.xml", 
				"/eai/conf/spring-beans-loginmodule-jaas.xml", 
				"/eai/conf/spring-beans-loginmodule-tamldap.xml", 
				"/eai/conf/spring-beans-matchcode.xml",
				"/eai/conf/spring-beans-sms.xml",
				"/eai/conf/spring-beans-util.xml"};
	  context = new ClassPathXmlApplicationContext(locations);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
}