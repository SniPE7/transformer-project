package com.ibm.ncs.web.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.Log4jInit;

public class DeviceTypeTreeController implements Controller {
	
	private BaseInfoServices baseInfoService;
	TPortInfoDao tPortInfoDao;
	PredefmibInfoDao predefmibInfoDao;
	TDeviceInfoDao tDeviceInfoDao;
	
	private String pageView;
	private String message = "";
	private String check="";
	
	
	

	public String getCheck() {
		return check;
	}



	public void setCheck(String check) {
		this.check = check;
	}



	public TDeviceInfoDao getTDeviceInfoDao() {
		return tDeviceInfoDao;
	}



	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		tDeviceInfoDao = deviceInfoDao;
	}



	public TPortInfoDao getTPortInfoDao() {
		return tPortInfoDao;
	}



	public void setTPortInfoDao(TPortInfoDao portInfoDao) {
		tPortInfoDao = portInfoDao;
	}



	public PredefmibInfoDao getPredefmibInfoDao() {
		return predefmibInfoDao;
	}



	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		this.predefmibInfoDao = predefmibInfoDao;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public void setBaseInfoService(BaseInfoServices baseInfoService) {
		this.baseInfoService = baseInfoService;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		message = "";
		String mf = request.getParameter("mf");
		String cate = request.getParameter("cate");
		String subcate = request.getParameter("subcate");
	//	System.out.println("the cate is8888888"+cate);
		
		

	//	String devMfCateSubcate =(String)request.getParameter("devMfCateSubcate");
		//composed string from filter-select-[->] button
		
	/*	if (null!=devMfCateSubcate && !"".equals(devMfCateSubcate)){
			try{
			
				List<String> params = new ArrayList<String>();
				StringTokenizer st = new StringTokenizer(devMfCateSubcate,"||");
				while(st.hasMoreTokens()){
					String s = st.nextToken();
					params.add(s);
				}
				mf 		= params.get(0);
				cate	= params.get(1);
			//	System.out.println("cate param is"+cate);
				subcate	= params.get(2);
			} catch (Exception e) {
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
			}
		}*/
//		System.out.println("only manu the cate is***"+cate+"the subcate is***"+subcate);
		
		Map<String, Object> mfs = new HashMap<String,Object>();
		List<DeviceTypeTree> _result = null;
		try{
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
			
				mfs.put("pagedDevice", pagedList);
				mfs.put("devicelist", _result);
			}
			/*if(cate == null && subcate == null){
				mfs.put("dtid", 0);
				message = "The Manufacture has no device!";
			}*/
		/*	try{
			if(_result.size()>0){
				
			for(int i=0;i<_result.size();i++){
				long dtid = _result.get(i).getDtid();
				//System.out.println("dtid is---"+dtid);
				List<TDeviceInfo> tdevice = tDeviceInfoDao.findWhereDtidEquals(dtid);
				
		//		System.out.println("tdevice size is"+tdevice.size());
				if(tdevice != null &&tdevice.size()>0  ){
					for(int j=0;j<tdevice.size();j++){
						TDeviceInfo device = tdevice.get(j);
		//				System.out.println("device is "+device);
						List<TPortInfo> ports = tPortInfoDao.findWhereDevidEquals(device.getDevid());
						List<PredefmibInfo> mibs = predefmibInfoDao.findWhereDevidEquals(device.getDevid());
		//				System.out.println("ports size is--"+ports.size());
		//				System.out.println("mib size is--"+mibs.size());
						if(ports.size()>0 || mibs.size()>0){
							check = "yes";
							mfs.put("check", check);
						}else{
							check = "no";
							mfs.put("check", check);
						}
					}
				}else{
					check = "no";
					mfs.put("check", check);
				}
				
			}
			}}catch(Exception e){
				check = "no";
				mfs.put("check", check);
				e.printStackTrace();
			}*/
			
			mfs.put("mf", mf);
			mfs.put("cate", cate);
			mfs.put("subcate", subcate);
			
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		
		return new ModelAndView(getPageView(), "model", mfs);
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
