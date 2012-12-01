package com.ibm.ncs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.Log4jInit;

public class TCategoryMapInitController implements Controller {
	
	private TCategoryMapInitManager tCategoryMapInitManager;
	
	

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		
		Map<String, Object> category = new HashMap<String,Object>();
		try{
			List<TCategoryMapInit> _result = this.tCategoryMapInitManager.getCategory();
			category.put("category", _result);
			for (TCategoryMapInit dto : _result) {
				//category.put(""+((TCategoryMapInit)dto).getId(), dto);
				display(dto);
			}
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("baseinfo/categorytest.jsp", "model", category);
	}


	public TCategoryMapInitManager getTCategoryMapInitManager() {
		return tCategoryMapInitManager;
	}


	public void setTCategoryMapInitManager(
			TCategoryMapInitManager categoryMapInitManager) {
		tCategoryMapInitManager = categoryMapInitManager;
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
