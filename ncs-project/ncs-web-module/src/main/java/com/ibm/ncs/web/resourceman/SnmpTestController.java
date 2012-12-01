package com.ibm.ncs.web.resourceman;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SnmpUtil;

public class SnmpTestController implements Controller {


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try{
			String devip = request.getParameter("devip");
			String rcommunity = request.getParameter("rcommunity");
			String snmpversion= request.getParameter("snmpversion");
			int ver = (null==snmpversion||"".equals(snmpversion.trim()))?0:Integer.parseInt(snmpversion);
			SnmpUtil snmp = new SnmpUtil();
			System.out.println("snmp test ="+devip+" : rcommunity="+rcommunity+" : ver="+ver);
			int res = snmp.testSnmp(devip, rcommunity, ver-1, 500l);
			System.out.print("snmp test ="+res);
			//return new ModelAndView("/secure/resourceman/newdeviceInfo.jsp", "response", res);
			response.setHeader("Cache-Control","no-cache");
			response.setHeader("Pragma","no-cache");
			response.setDateHeader("Expires",-1);
			response.getWriter().println(res);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}



}
