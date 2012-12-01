package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.util.Log4jInit;

public class CategoryMapInitController implements Controller {
	

	private TCategoryMapInitDao tCategoryMapInitDao;
	private TManufacturerInfoInitDao tManufacturerInfoInitDao;
	
	

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String message = "";
		Map<String, Object> category = new HashMap<String,Object>();
		try{
			List<TCategoryMapInit> _result = this.tCategoryMapInitDao.findAll();
			category.put("category", _result);
			for (TCategoryMapInit dto : _result) {
				//category.put(""+((TCategoryMapInit)dto).getId(), dto);
				display(dto);
			}
		}catch(DaoException e){
			e.printStackTrace();
			message = "global.db.error";
			category.put("message", message);
			return new ModelAndView("/dberror.jsp","model",category);
		}catch(Exception e){
			Log4jInit.ncsLog.error("Error occured in " + this.getClass().getName() + " :\n" + e.getMessage());
			e.printStackTrace();
		}
		
		return new ModelAndView("/secure/baseinfo/categorys.jsp", "model", category);
	}


	





	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		tCategoryMapInitDao = categoryMapInitDao;
	}








	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		tManufacturerInfoInitDao = manufacturerInfoInitDao;
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
