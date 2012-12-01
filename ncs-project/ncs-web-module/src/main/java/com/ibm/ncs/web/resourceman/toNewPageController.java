package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.util.Log4jInit;

public class toNewPageController implements Controller {
	//TDeviceTypeInitDao TDeviceTypeInitDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	TCategoryMapInitDao TCategoryMapInitDao;
	DefMibGrpDao DefMibGrpDao;
	String pageView;
	
	public ModelAndView handleRequest(
			HttpServletRequest request,
			HttpServletResponse paramHttpServletResponse) throws Exception {
			
		Map model = new HashMap();
		try{
			List catelist = TCategoryMapInitDao.findAll();
			List mflist = TManufacturerInfoInitDao.findAll();
			
			model.put("mflist", mflist);
			model.put("catelist", catelist);
			model.put("gid", request.getParameter("gid"));
			
//			long devid = 0l;
//			String devidStr  = request.getParameter("devid");
//			if (null==devidStr||"".equals(devidStr.trim())){ //think of a new device
//				System.out.println(" devid==>null");
//				//return new ModelAndView("/secure/resourceman/newdeviceInfo.jsp");
//			}
//			 devid = Long.parseLong(devidStr.trim());
			 List<DefMibGrp> alldefmibgrp = DefMibGrpDao.findAll();

			 String [] predefmibindex = request.getParameterValues("predefmibindex");
			 Map<Long,String[]> pdmid2nameflag = conposePdmDsp(alldefmibgrp, predefmibindex);
			 
//				model.put("devid",devid);
	
				model.put("alldefmibgrp", alldefmibgrp);
				
				model.put("pdmid2nameflag", pdmid2nameflag);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		request.getSession().removeAttribute("DevInfoMap");
		request.getSession().setAttribute("DevInfoMap", model);
		//return new ModelAndView("/secure/baseinfo/devicetypelist.jsp", "model", mfs);
		return new ModelAndView(getPageView(), "model", model);
	}


	private Map<Long, String[]> conposePdmDsp(List<DefMibGrp> alldefmibgrp, String[] predefmibindex) {
		Map<Long, String[]> mapflag =  new TreeMap<Long, String[]>();
		for (DefMibGrp dto:alldefmibgrp){
			mapflag.put(dto.getMid(), new String[]{dto.getName(),"0"});
		}
		try {
			for(int i=0;i<predefmibindex.length;i++){
				StringTokenizer stk = new StringTokenizer(predefmibindex[i], "|");
				String s1 = stk.nextToken();//mid
//			String s2 = stk.nextToken();
//			String s3 = stk.nextToken();
//			String s4 = stk.nextToken();
				long midused = Long.parseLong(s1);
				String [] tmp = mapflag.get(midused);
				tmp[1] = "1";
				mapflag.put(midused, tmp);
			}
		} catch (Exception e) {
		}
		System.out.println("mapflag="+mapflag);
	return mapflag;
}
	
	private Map<Long, Object> getPdmInfoMap(List<PredefmibInfo> listpdmInfo) {
		Map<Long, Object> map = new TreeMap<Long, Object>();

		try {
			 for (PredefmibInfo dto: listpdmInfo){
				 long mid = dto.getMid();
				 List<PredefmibInfo> templst = null;
				 if(map.containsKey(mid)){
					 templst = (List)map.get(mid);
				 }else {
					 templst = new ArrayList<PredefmibInfo>();
				 }
				 templst.add(dto);
				 map.put(mid, templst);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("map="+map);
	return map;
}

	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}

	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public TCategoryMapInitDao getTCategoryMapInitDao() {
		return TCategoryMapInitDao;
	}

	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		TCategoryMapInitDao = categoryMapInitDao;
	}


	public DefMibGrpDao getDefMibGrpDao() {
		return DefMibGrpDao;
	}


	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		DefMibGrpDao = defMibGrpDao;
	}

}
