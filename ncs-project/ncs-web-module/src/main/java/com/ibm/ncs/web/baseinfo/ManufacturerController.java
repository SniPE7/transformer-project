package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

public class ManufacturerController implements Controller {
	
	private TCategoryMapInitManager tCategoryMapInitManager;
	
	

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String message = "";
		Map<String, Object> mfs = new HashMap<String,Object>();
		List<TManufacturerInfoInit> _result = null;
		try{

			_result = this.tCategoryMapInitManager.getManufacturers();
			SortList<TManufacturerInfoInit> srt = new SortList<TManufacturerInfoInit>();
			srt.Sort(_result, "getMrname", null);
			mfs.put("manufacturers", _result);
		}catch(DaoException e){
			System.out.println("inner---------------");
			e.printStackTrace();
			message = "global.db.error";
			mfs.put("message", message);
			return new ModelAndView("/dberror.jsp","model",mfs);
		}catch (Exception e) {
			System.out.println("outer--------------");
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		mfs.put("refresh", arg0.getParameter("refresh"));
		return new ModelAndView("/secure/baseinfo/manufacturers.jsp", "model", mfs);
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
