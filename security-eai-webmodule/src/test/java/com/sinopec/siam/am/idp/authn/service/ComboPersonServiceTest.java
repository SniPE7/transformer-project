package com.sinopec.siam.am.idp.authn.service;

import java.util.HashMap;
import java.util.Map;

public class ComboPersonServiceTest extends BaseTestCase {

	@Override
  protected void setUp() throws Exception {
	  super.setUp();
  }

	@Override
  protected void tearDown() throws Exception {
	  super.tearDown();
  }
  
	public void testUpdatePerson() throws Exception {
		PersonService personService = this.context.getBean("personService", PersonService.class);
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("mobile", "12345678901");
		personService.updatePerson("spark01", attrs);
	}

	public void testUpdatePassword() throws Exception {
		PersonService personService = this.context.getBean("personService", PersonService.class);
		personService.updatePassword("spark01", "Pass1234");
	}
}
