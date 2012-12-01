package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TDeviceTypeInitPk;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class DeleteDeviceTypeController implements Controller {

	private BaseInfoServices baseInfoService;
	private TDeviceTypeInitDao TDeviceTypeInitDao;
	private TManufacturerInfoInitDao TManufacturerInfoInitDao;
	private TCategoryMapInitDao TCategoryMapInitDao;
	private TDeviceInfoDao tDeviceInfoDao;
	private TPortInfoDao tPortInfoDao;
	private PredefmibInfoDao predefmibInfoDao;
	private GenPkNumber genPkNumber;
	private String pageView;
	private String message="";

	
	
	public void setBaseInfoService(BaseInfoServices baseInfoService) {
		this.baseInfoService = baseInfoService;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		message = "";
		boolean isAlert = false;
		Map<String, Object> mfs = new HashMap<String,Object>();
		/*String check = request.getParameter("check");
		//System.out.println("check is-------"+check);
		mfs.put("check", check);*/
		String mf=null;
		String cate = null;
		String subcate = null;
		
		String dtidStr = request.getParameter("dtid");
		
		try{
			Long dtid = Long.parseLong(dtidStr);
			System.out.println("dtid is---"+dtid);
			/*TDeviceTypeInit dto = TDeviceTypeInitDao.findByPrimaryKey(dtid);
			System.out.println("dto is---"+dto);*/
			
			List<TDeviceInfo> tdevice = tDeviceInfoDao.findWhereDtidEquals(dtid);
	//		System.out.println("tdevice size is"+tdevice.size());
			
			if(tdevice.size()>0 && tdevice != null){
				isAlert=true;
				for(int j=0;j<tdevice.size();j++){
					
					TDeviceInfo device = tdevice.get(j);
	//				System.out.println("device is "+device);
					List<TPortInfo> ports = tPortInfoDao.findWhereDevidEquals(device.getDevid());
					List<PredefmibInfo> mibs = predefmibInfoDao.findWhereDevidEquals(device.getDevid());
					System.out.println("ports size is--"+ports.size());
					System.out.println("mib size is--"+mibs.size());
					if(ports.size()>0 || mibs.size()>0){
						isAlert = true;
						break;
					}
				}
			}
			System.out.println("alert is---"+isAlert);
			if(isAlert == true){
				
				message = "<script language='javascript'>alert('该厂商设备型号信息已经被引用，不能删除！');</script>";
				mfs.put("message", message);	
			}else{
				System.out.println("delete begin-------");
			    TDeviceTypeInitPk pk = new TDeviceTypeInitPk(dtid);
			    TDeviceTypeInitDao.delete(pk);
			    Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from TDeviceTypeInitDao " + pk.toString());
			}
			
			//mf = TManufacturerInfoInitDao.findByPrimaryKey(dto.getMrid()).getMrname();
			mf = request.getParameter("mf");
			subcate = request.getParameter("subcate");
			cate = request.getParameter("cate");
	//		System.out.println("parameter from jsp mf is:"+mf+" cate is:"+cate+" subcate is :"+subcate);
			//subcate = dto.getSubcategory();
			//cate = TCategoryMapInitDao.findByPrimaryKey(dto.getCategory()).getName();
		//	System.out.println("mf is**************8"+mf+" and cate is ************"+cate+" and subcate is***********"+subcate);
			
			
			
			List<DeviceTypeTree> _result = null;
			/*List<TManufacturerInfoInit> mflist = null;
			List<TCategoryMapInit> catelist = null; */
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
			}
			PagedListHolder pagedList = new PagedListHolder(_result);
			
	//		System.out.println("result is----"+_result);
			mfs.put("pagedDevice", pagedList);
			mfs.put("devicelist", _result);
			mfs.put("mf", mf);
			mfs.put("cate", cate);
			mfs.put("subcate", subcate);
		}catch(Exception e){
			String message = "delete failed";
			mfs.put("message", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error Message:\n" + e.getMessage());
			e.printStackTrace();
		}
		mfs.put("refresh","true");
		return new ModelAndView(getPageView(), "model", mfs);
		
	}



	public TCategoryMapInitDao getTCategoryMapInitDao() {
		return TCategoryMapInitDao;
	}



	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		TCategoryMapInitDao = categoryMapInitDao;
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


	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}



	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}



	public TDeviceTypeInitDao getTDeviceTypeInitDao() {
		return TDeviceTypeInitDao;
	}



	public void setTDeviceTypeInitDao(TDeviceTypeInitDao deviceTypeInitDao) {
		TDeviceTypeInitDao = deviceTypeInitDao;
	}



	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}



	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}

	public PredefmibInfoDao getPredefmibInfoDao() {
		return predefmibInfoDao;
	}



	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		this.predefmibInfoDao = predefmibInfoDao;
	}



	public TPortInfoDao getTPortInfoDao() {
		return tPortInfoDao;
	}



	public void setTPortInfoDao(TPortInfoDao portInfoDao) {
		tPortInfoDao = portInfoDao;
	}



	public TDeviceInfoDao getTDeviceInfoDao() {
		return tDeviceInfoDao;
	}



	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		tDeviceInfoDao = deviceInfoDao;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
