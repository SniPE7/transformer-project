package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TEventsubtypeInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.Log4jInit;

public class SyslogEventSubTypeController implements Controller {
	
	TEventsubtypeInfoInitDao TEventsubtypeInfoInitDao;
	
	

	public void setTEventsubtypeInfoInitDao(
			TEventsubtypeInfoInitDao eventsubtypeInfoInitDao) {
		TEventsubtypeInfoInitDao = eventsubtypeInfoInitDao;
	}


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		Map<String, Object> eventSubtype = new HashMap<String,Object>();
		try{
			List<TEventsubtypeInfoInit> _result = TEventsubtypeInfoInitDao.findAll();
			eventSubtype.put("eventSubtype", _result);
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}	

		return new ModelAndView("/security/baseinfo/syslogEventSubType.jsp", "model", eventSubtype);
	}








	public static void display(TCategoryMapInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getName() );
		buf.append( ", " );
		buf.append( dto.getFlag() );
		System.out.println( buf.toString() );
	}
}
