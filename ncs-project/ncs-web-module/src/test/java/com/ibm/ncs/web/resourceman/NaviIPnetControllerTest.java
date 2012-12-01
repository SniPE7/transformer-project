package com.ibm.ncs.web.resourceman;

import org.springframework.web.servlet.ModelAndView;

import junit.framework.TestCase;

public class NaviIPnetControllerTest extends TestCase {

	public final void testHandleRequest() throws Exception {
		NaviIPnetController navi = new NaviIPnetController();
		ModelAndView modelandview = navi.handleRequest(null,null);
		assertEquals("/secure/resourceman/naviipnet.jsp", modelandview.getViewName());
		assertEquals("Map", modelandview.getModel());
		fail("Not yet implemented"); // TODO
	}

}
