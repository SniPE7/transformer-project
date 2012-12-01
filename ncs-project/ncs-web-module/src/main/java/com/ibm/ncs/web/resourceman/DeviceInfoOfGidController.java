package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.service.PolicyAppServices;

public class DeviceInfoOfGidController implements Controller {

	TGrpNetDao TGrpNetDao;
	TDeviceInfoDao TDeviceInfoDao;
	TListIpDao TListIpDao;
	TManufacturerInfoInitDao  TManufacturerInfoInitDao;
	TDeviceTypeInitDao   TDeviceTypeInitDao;
	TCategoryMapInitDao TCategoryMapInitDao;
	PolicyAppServices PolicyAppServices;
	String pageView;

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}

	public void setTListIpDao(TListIpDao listIpDao) {
		TListIpDao = listIpDao;
	}

	//public void setTGrpNetDao(TGrpNetDao grpNetDao) {
	//	TGrpNetDao = grpNetDao;
	//}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try{
			// search ipdecode list for the certain node.
			String nodeid = request.getParameter("gid");
			if (null==nodeid||"".equals(nodeid.trim())){return null;}
			long gid = Long.parseLong(nodeid.trim());
			
//			List<TListIp> listipbygid = TListIpDao.findWhereGidEquals(gid);
//			
//			//  ipdecode scopes for the device finder.
//			long[] min = new long[listipbygid.size()], max= new long[listipbygid.size()];
//			for (int i=0; i < listipbygid.size(); i++){
//				min[i] = ((TListIp)listipbygid.get(i)).getIpdecodeMin();
//				max[i] = ((TListIp)listipbygid.get(i)).getIpdecodeMax();
//			}
//			
//			List<TDeviceInfo> deviceinfo = TDeviceInfoDao.findWhereIpdecodeBetweenScopes(min, max);
			
			List<TDeviceInfo> deviceinfo = PolicyAppServices.getDeviceInfoListOfTheGidNode(gid);
			
			List<TManufacturerInfoInit> manufactures = TManufacturerInfoInitDao.findAll();
			Map<Long,Object> manufmap = new HashMap<Long, Object>();
			for(TManufacturerInfoInit mf: manufactures){
				manufmap.put(mf.getMrid(), mf);
			}
			List<TDeviceTypeInit> devicetype = TDeviceTypeInitDao.findAll();
			Map<Long,Object> dtmap = new HashMap<Long, Object>();
			for(TDeviceTypeInit dt :  devicetype ){
	//			String devtype = "";
				dtmap.put(dt.getDtid(), dt);
	//			 List<TCategoryMapInit> tCatDto =TCategoryMapInitDao.findWhereIdEquals(dt.getCategory());
	//			 if(tCatDto != null && tCatDto.size()>0)
	//				 devtype = tCatDto.get(0).getName();
	//			dtmap.put(dt.getCategory(), devtype);
			}
			Map<Long,Object> catemap = new HashMap<Long,Object>();
			List<TCategoryMapInit> catelist =TCategoryMapInitDao.findAll();
			for(TCategoryMapInit catedto: catelist){
				catemap.put(catedto.getId(), catedto.getName());
			}
			model.put("catemap", catemap);
	
			model.put("deviceinfo", deviceinfo);
//			model.put("listipbygid", listipbygid);
//			model.put("ipdecodescopes", new long [][] {min, max} );
			Map seobj = (Map)request.getSession().getAttribute("grpnetNames");
			model.put("grpnetNames", seobj);
			model.put("manufmap",manufmap);
			model.put("dtmap", dtmap);
			model.put("gid", gid);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(), "model", model);
	}

	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}

	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}

	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}

	public TListIpDao getTListIpDao() {
		return TListIpDao;
	}

	public TDeviceTypeInitDao getTDeviceTypeInitDao() {
		return TDeviceTypeInitDao;
	}

	public void setTDeviceTypeInitDao(TDeviceTypeInitDao deviceTypeInitDao) {
		TDeviceTypeInitDao = deviceTypeInitDao;
	}

	public TGrpNetDao getTGrpNetDao() {
		return TGrpNetDao;
	}

	public void setTGrpNetDao(TGrpNetDao grpNetDao) {
		TGrpNetDao = grpNetDao;
	}

	public PolicyAppServices getPolicyAppServices() {
		return PolicyAppServices;
	}

	public void setPolicyAppServices(PolicyAppServices policyAppServices) {
		PolicyAppServices = policyAppServices;
	}

	public TCategoryMapInitDao getTCategoryMapInitDao() {
		return TCategoryMapInitDao;
	}

	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		TCategoryMapInitDao = categoryMapInitDao;
	}



}
