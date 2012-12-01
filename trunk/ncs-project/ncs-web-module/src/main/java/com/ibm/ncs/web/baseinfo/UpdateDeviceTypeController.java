package com.ibm.ncs.web.baseinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TDeviceTypeInitPk;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class UpdateDeviceTypeController implements Controller {

	private BaseInfoServices baseInfoService;
	private TCategoryMapInitDao TCategoryMapInitDao;
	private TManufacturerInfoInitDao TManufacturerInfoInitDao;
	private TDeviceTypeInitDao TDeviceTypeInitDao;
	private GenPkNumber genPkNumber;
	
	private String pageView;
	private String message = "";

	
	
	public void setBaseInfoService(BaseInfoServices baseInfoService) {
		this.baseInfoService = baseInfoService;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		message = "";
		//href="<%=request.getContextPath() %>/secure/baseinfo/devicetypelist.wss?mf=${theManufacturer.key}&cate=${theCategory.key}&subcate=${theSubCategory.key}" 
		String mf = null;
		String cate = null;
		String subcate = null;
		Long mrid = null;
		Map<String, Object> mfs = new HashMap<String,Object>();
		List<DeviceTypeTree> _result = null;
		List<TManufacturerInfoInit> mflist = null;
		List<TCategoryMapInit> catelist = null; 
		String dtidStr = request.getParameter("dtid");
		try{
			if(dtidStr == null) dtidStr = "";
			Long dtid = Long.parseLong(dtidStr);
	//		System.out.println("the manufacture from jsp is-------"+request.getParameter("manufacture"));
			if(request.getParameter("manufacture")!=null)
			mrid = Long.parseLong(request.getParameter("manufacture"));
			//mrid = Long.parseLong(request.getParameter("mrid"));
			Long categoryId = null;
			if(request.getParameter("category")!=null)
			categoryId = Long.parseLong(request.getParameter("category"));
	
			String subCategory = request.getParameter("subCategory");
			String model = request.getParameter("model");
			String objectid = request.getParameter("objectid");
			String description = request.getParameter("description");
			//long dtid = genPkNumber.getID();
	        try{
			if(mrid!=null){
				mf = (TManufacturerInfoInitDao.findByPrimaryKey(mrid)).getMrname();
			}
			if(categoryId!=null){
				cate = (TCategoryMapInitDao.findByPrimaryKey(categoryId)).getName();
			}
			
			if(mf!=null&&cate!=null&&subCategory!=null){
				
				TDeviceTypeInit deviceType= new	TDeviceTypeInit();
				deviceType = TDeviceTypeInitDao.findByPrimaryKey(dtid);
				System.out.println("deviceType is---"+deviceType);
				System.out.println("from jsp--mrid:"+mrid+" categoryId: "+categoryId+"subCategory: "+subCategory+"model: "+model+"objectid: "+objectid);
//				List<DeviceTypeTree> testresult = this.baseInfoService.findDeviceTypeByWhere(mf,cate,subCategory);
//				if(testresult != null && testresult.size()>0){
//					for(int i=0;i<testresult.size();i++){
//						DeviceTypeTree device = testresult.get(i);
//						if((model.equalsIgnoreCase(device.getModel()))&&(objectid.equalsIgnoreCase(device.getObjectid())))
//						{
//							System.out.println("equal---------");
//							message = "baseinfo.adddevice.dul";
//							mflist = TManufacturerInfoInitDao.findAll();
//							catelist = TCategoryMapInitDao.findAll();
//							
//							mfs.put("message", message);
//							mfs.put("mflist", mflist);
//							mfs.put("catelist", catelist);
//							mfs.put("dtid", dtid);
//							mfs.put("subCategory",subCategory);
//							mfs.put("model", model);
//							mfs.put("objectid", objectid);
//							mfs.put("description", description);
//							mfs.put("category",categoryId);
//							mfs.put("mrid", mrid);
//							mfs.put("mf", mf);
//							mfs.put("cate", cate);
//							mfs.put("subcate", subcate);
//							if (description.equalsIgnoreCase(device.getDescription()) == true){
//								return new ModelAndView(getPageView(), "model", mfs);
//							}
//							
//						}
//					}
//				}
				
				TDeviceTypeInitPk pk = new TDeviceTypeInitPk(dtid);
				
				//long dtid = genPkNumber.getID();
				//deviceType.setDtid(dtid);
				deviceType.setCategory(categoryId);
				deviceType.setDescription(description);
				deviceType.setModel(model);
				deviceType.setMrid(mrid);
				deviceType.setObjectid(objectid);
				deviceType.setSubcategory(subCategory);
				subcate = subCategory;
				try{
				TDeviceTypeInitDao.update(pk, deviceType);
				Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TDeviceTypeInitDao: pk=" + pk.toString() + " \tdto=" + deviceType.toString());
				}catch(DataIntegrityViolationException dupk){
					dupk.printStackTrace();
					Log4jInit.ncsLog.error(this.getClass().getName() + " updated to TDeviceTypeInitDao Failed: pk=" + pk.toString() + " \tdto=" + deviceType.toString()
							+ "\n" + dupk.getMessage());
					message = "baseinfo.updatedevice.error";
					mflist = TManufacturerInfoInitDao.findAll();
					catelist = TCategoryMapInitDao.findAll();
					
					mfs.put("message", message);
					mfs.put("mflist", mflist);
					mfs.put("catelist", catelist);
					mfs.put("dtid", dtid);
					mfs.put("subCategory",subCategory);
					mfs.put("model", model);
					mfs.put("objectid", objectid);
					mfs.put("description", description);
					mfs.put("category",categoryId);
					mfs.put("mrid", mrid);
					mfs.put("mf", mf);
					mfs.put("cate", cate);
					mfs.put("subcate", subcate);
					
					return new ModelAndView("/secure/baseinfo/updateDevicetype.jsp", "model", mfs);
					
				}
				catch(Exception ee){
					ee.printStackTrace();
					Log4jInit.ncsLog.error(this.getClass().getName() + " updated to TDeviceTypeInitDao Failed: pk=" + pk.toString() + " \tdto=" + deviceType.toString()
							+ "\n" + ee.getMessage());
					message = "baseinfo.update.failed";
					mflist = TManufacturerInfoInitDao.findAll();
					catelist = TCategoryMapInitDao.findAll();
					
					mfs.put("message", message);
					mfs.put("mflist", mflist);
					mfs.put("catelist", catelist);
					mfs.put("dtid", dtid);
					mfs.put("subCategory",subCategory);
					mfs.put("model", model);
					mfs.put("objectid", objectid);
					mfs.put("description", description);
					mfs.put("category",categoryId);
					mfs.put("mrid", mrid);
					mfs.put("mf", mf);
					mfs.put("cate", cate);
					mfs.put("subcate", subcate);
					
					return new ModelAndView("/secure/baseinfo/updateDevicetype.jsp", "model", mfs);
				}
	//			System.out.println("after updating the device is"+deviceType);
				
			}
	
			String devMfCateSubcate =(String)request.getParameter("devMfCateSubcate");//composed string from filter-select-[->] button
			//System.out.println("form get action string as: parameter="+devMfCateSubcate );
			
			if (null!=devMfCateSubcate && !"".equals(devMfCateSubcate)){
				//System.out.println(devMfCateSubcate);
				try {
					List<String> params = new ArrayList<String>();
					StringTokenizer st = new StringTokenizer(devMfCateSubcate,"||");
					while(st.hasMoreTokens()){
						String s = st.nextToken();
						params.add(s);
					}
					mf 		= params.get(0);
					cate	= params.get(1);
					subcate	= params.get(2);
				} catch (Exception e) {
					Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
					e.printStackTrace();
				}				
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
			
				mfs.put("pagedDevice", pagedList);
				mfs.put("devicelist", _result);
			}
	//		System.out.println("result is------"+_result);
			mflist = TManufacturerInfoInitDao.findAll();
			catelist = TCategoryMapInitDao.findAll();
			
			mfs.put("mflist", mflist);
			mfs.put("catelist", catelist);
			mfs.put("dtid", dtid);
			mfs.put("subCategory",subCategory);
			mfs.put("model", model);
			mfs.put("objectid", objectid);
			mfs.put("description", description);
			mfs.put("category",categoryId);
			mfs.put("mrid", mrid);
	        }catch(Exception e){
	        	message = "baseinfo.updatedevice.error";
	        	Log4jInit.ncsLog.error(this.getClass().getName() + " updated to TDeviceTypeInitDao Failed: "
						+ "\n" + e.getMessage());
	        	mflist = TManufacturerInfoInitDao.findAll();
				catelist = TCategoryMapInitDao.findAll();
				
				mfs.put("message", message);
				mfs.put("mflist", mflist);
				mfs.put("catelist", catelist);
				mfs.put("dtid", dtid);
				mfs.put("subCategory",subCategory);
				mfs.put("model", model);
				mfs.put("objectid", objectid);
				mfs.put("description", description);
				mfs.put("category",categoryId);
				mfs.put("mrid", mrid);
				mfs.put("mf", mf);
				mfs.put("cate", cate);
				mfs.put("subcate", subcate);
				
				return new ModelAndView("/secure/baseinfo/updateDevicetype.jsp", "model", mfs);
	        	
	        }
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}	
		mfs.put("mf", mf);
		mfs.put("cate", cate);
		mfs.put("subcate", subcate);
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



	public TCategoryMapInitDao getTCategoryMapInitDao() {
		return TCategoryMapInitDao;
	}



	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		TCategoryMapInitDao = categoryMapInitDao;
	}



	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}



	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
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



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}
	
	


}
