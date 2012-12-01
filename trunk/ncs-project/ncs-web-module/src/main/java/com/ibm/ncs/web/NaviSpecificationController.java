package com.ibm.ncs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.Log4jInit;

public class NaviSpecificationController implements Controller {
	
	private BaseInfoServices baseInfoService;
	
	

	public void setBaseInfoService(BaseInfoServices baseInfoService) {
		this.baseInfoService = baseInfoService;
	}



	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		
		Map<String, Object> mfs = new HashMap<String,Object>();
		try{
			List<DeviceTypeTree> _result = this.baseInfoService.findDeviceTypeTree();
			mfs.put("deviceTypes", _result);
			//System.out.println(_result);
			
			Map<String, Object> mfTree = new TreeMap<String,Object>(String.CASE_INSENSITIVE_ORDER);
			for (DeviceTypeTree dto: _result){
				//-- Tree node level one.
				Map<String, Object> cateTree = null;
				if(  mfTree.containsKey(dto.getMrName()))
				{
					//Map<String, Object> 
					cateTree = (Map)mfTree.get(dto.getMrName());
	
				}else //if (null!=dto.getMrName())//&&"".equals(dto.getMrName())) 
				{
					//Map<String,Object> 
					cateTree = new TreeMap<String,Object>(String.CASE_INSENSITIVE_ORDER);
					mfTree.put(dto.getMrName(), cateTree);
				}
				
				//-- Tree node level two ...plus level condition
				Map<String, Object> subCateTree = null;
				if(  cateTree.containsKey(dto.getCateName()))
				{
					//Map<String, Object> 
					subCateTree = (Map<String,Object>)cateTree.get(dto.getCateName());
	
				}else //if (null!=dto.getCateName())//&&"".equals(dto.getCateName()))
				{
					//Map<String,Object> 
					subCateTree = new TreeMap<String,Object>(String.CASE_INSENSITIVE_ORDER);
					if (null!=dto.getCateName())
					cateTree.put(dto.getCateName(), subCateTree);
				}
				
				//-- Tree node level three
				Map<String, Object> deviceList = null;
				if(  subCateTree.containsKey(dto.getSubCategory()))
				{
					//Map<String, Object> 
					deviceList = (Map)subCateTree.get(dto.getSubCategory());
	
				}else //if (null!=dto.getSubCategory())//&&"".equals(dto.getSubCategory()))
				{
					//Map<String,Object> 
					deviceList = new TreeMap<String,Object>();
					if (null!=dto.getSubCategory())
					subCateTree.put(dto.getSubCategory(), deviceList);
				}
				deviceList.put(dto.getDtid()+"", dto);//cast to Map<String, Object>
			}
		
			mfs.put("mfTree", mfTree);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("/secure/baseinfo/naviSpecification.jsp", "model", mfs);
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
