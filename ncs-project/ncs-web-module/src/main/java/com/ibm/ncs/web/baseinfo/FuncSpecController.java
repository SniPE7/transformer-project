package com.ibm.ncs.web.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.Log4jInit;

public class FuncSpecController implements Controller {
	
	private BaseInfoServices baseInfoService;
	
	private String pageView ;

	public void setBaseInfoService(BaseInfoServices baseInfoService) {
		this.baseInfoService = baseInfoService;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		String mf = request.getParameter("mf");
		String cate = request.getParameter("cate");
		String subcate = request.getParameter("subcate");

		Map<String, Object> funcs = new HashMap<String,Object>();
		List<DeviceTypeTree> _result = null;
		
		String devMfCateSubcate =(String)request.getParameter("devMfCateSubcate");//composed string from filter-select-[->] button
		//System.out.println("form get action string as: parameter="+devMfCateSubcate );

		try {
			if (null!=devMfCateSubcate && !"".equals(devMfCateSubcate)){
				//System.out.println(devMfCateSubcate);
				
				List<String> params = new ArrayList<String>();
				StringTokenizer st = new StringTokenizer(devMfCateSubcate,"||");
				while(st.hasMoreTokens()){
					String s = st.nextToken();
					params.add(s);
				}
				//System.out.println("splited as "+params);
				mf 		= params.get(0);
				cate	= params.get(1);
				subcate	= params.get(2);		
			}
		
			if (mf!=null){
				if(cate!=null){
					if(subcate!=null){
						_result = this.baseInfoService.findDeviceTypeByWhere(mf,cate,subcate);
					}else{
						_result = this.baseInfoService.findDeviceTypeByWhere(mf,cate);
					}
				}else{
					_result = this.baseInfoService.findDeviceTypeByManufacture(mf);
				}
	
				PagedListHolder pagedList = new PagedListHolder(_result);
			
				funcs.put("pagedDevice", pagedList);
				funcs.put("devicelist", _result);
			}
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		
		//return new ModelAndView("/secure/baseinfo/funcspec.jsp", "model", funcs);
		return new ModelAndView(getPageView(), "model", funcs);
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



	public String getPageView() {
		return pageView;
	}



	public void setPageView(String pageView) {
		this.pageView = pageView;
	}



	public BaseInfoServices getBaseInfoService() {
		return baseInfoService;
	}
}
