package com.ibm.ncs.web.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class SaveDeviceTypeTreeController implements Controller {
	
	private BaseInfoServices baseInfoService;
	private TCategoryMapInitDao TCategoryMapInitDao;
	private TManufacturerInfoInitDao TManufacturerInfoInitDao;
	private TDeviceTypeInitDao TDeviceTypeInitDao;
	private GenPkNumber genPkNumber;
	private String pageView;
	private String errorView;
	private String message="";
	
	
	public String getErrorView() {
		return errorView;
	}



	public void setErrorView(String errorView) {
		this.errorView = errorView;
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
		//href="<%=request.getContextPath() %>/secure/baseinfo/devicetypelist.wss?mf=${theManufacturer.key}&cate=${theCategory.key}&subcate=${theSubCategory.key}" 
		String mf = request.getParameter("mf");
		String cate = request.getParameter("cate");
		String subcate = request.getParameter("subcate");
	//	System.out.println("mf is :"+mf+" cate is "+cate+" subcate from jsp is"+subcate);
		Long mrid = null;
		String strTicketA = request.getParameter("sessiontime");
		String strTicketB = (String)request.getSession().getAttribute("sessiontime");
	//	System.out.println("strTicketB:---------"+strTicketB);
	//	System.out.println("strTicketA:---------"+strTicketA);
	//	System.out.println("category from jsp is--------"+request.getParameter("category"));
		Map<String, Object> mfs = new HashMap<String,Object>();
		List<DeviceTypeTree> _result = null;
		List<TManufacturerInfoInit> mflist = null;
		List<TCategoryMapInit> catelist = null; 
		String manu = "";
		String category = "";
		try{
			String manufromjsp = request.getParameter("manufacture");
			if(manufromjsp!=null)
			mrid = Long.parseLong(request.getParameter("manufacture"));
			Long categoryId = null;
			String categoryfromjsp = request.getParameter("category");
			if(categoryfromjsp != null)
			categoryId = Long.parseLong(request.getParameter("category"));
			String subCategory = request.getParameter("subCategory");
			String model = request.getParameter("model");
			String objectid = request.getParameter("objectid");
			String description = request.getParameter("description");
			//long dtid = genPkNumber.getID();
	//		System.out.println(mrid+"mrid"+categoryId+"categoryId"+"************");
			try{
			if(mrid!=null){
				 manu = (TManufacturerInfoInitDao.findByPrimaryKey(mrid)).getMrname();
			}
			if(categoryId!=null){
				 category = (TCategoryMapInitDao.findByPrimaryKey(categoryId)).getName();
			}
			
	//	System.out.println(mf+"mf---"+cate+"cate---"+subCategory+"subCategory---");
	//	System.out.println("model---"+model+"objectid--"+objectid+"description---"+description);
	//		System.out.println("subCategory is null?"+subCategory);
			
			//if((mf!=null ||(! ("".equals(mf))))&&(cate!=null || (! ("".equals(cate))))&&(subCategory!=null || (! ("".equals(subCategory))))){
				if((!("".equals(manu))) && (!("".equals(category))) && (!("".equals(subCategory)))){
			//if(mf != null || cate != null || subCategory != null){
	//		System.out.println("the three are all not null------");
			
			TDeviceTypeInit deviceType= new	TDeviceTypeInit();
				List<DeviceTypeTree> testresult = this.baseInfoService.findDeviceTypeByWhere(manu,category,subCategory);
				System.out.println("testresult is----"+testresult);
				if(testresult.size() <= 0){
					long dtid = genPkNumber.getID();
					deviceType.setDtid(dtid);
					deviceType.setCategory(categoryId);
					deviceType.setDescription(description);
					deviceType.setModel(model);
					deviceType.setMrid(mrid);
					deviceType.setObjectid(objectid);
					deviceType.setSubcategory(subCategory);
				//	subcate = subCategory;
		//			System.out.println(dtid+"|"+categoryId+"|"+description+"|"+model+"|"+mrid+"|"+objectid+"|"+subCategory+"|");
		//			System.out.println(deviceType.getDtid()+":"+deviceType.getCategory()+"-----------------");
					if(strTicketA.equals(strTicketB)){
		//				System.out.println("HERE EXECUTE FIRST TIME");
						try{
						TDeviceTypeInitDao.insert(deviceType);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TDeviceTypeInitDao: " + deviceType.toString());
						}catch(DataIntegrityViolationException e){
							message = "baseinfo.adddevice.error";
							mfs.put("message",message);
							mflist = TManufacturerInfoInitDao.findAll();
							catelist = TCategoryMapInitDao.findAll();
							
							mfs.put("mflist", mflist);
							mfs.put("catelist", catelist);
							mfs.put("mf", mf);
							mfs.put("cate", cate);
							mfs.put("category", category);
							mfs.put("subCategory", subCategory);
							mfs.put("subcate", subcate);
							mfs.put("model", model);
							mfs.put("objectid", objectid);
							mfs.put("description", description);
							
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
							Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
							e.printStackTrace();
							return new ModelAndView(getErrorView(),"model",mfs);
						}
						catch(Exception ee){
							mflist = TManufacturerInfoInitDao.findAll();
							catelist = TCategoryMapInitDao.findAll();
							message = "baseinfo.new.error";
							mfs.put("message", message);							
							mfs.put("mflist", mflist);
							mfs.put("catelist", catelist);
							mfs.put("mf", mf);
							mfs.put("cate", cate);
							mfs.put("category", category);
							mfs.put("subCategory", subCategory);
							mfs.put("subcate", subcate);
							mfs.put("model", model);
							mfs.put("objectid", objectid);
							mfs.put("description", description);
							ee.printStackTrace();
							
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
							Log4jInit.ncsLog.error(this.getClass().getName() + " Inserted to TDeviceTypeInitDao Failed: " + deviceType.toString() + "\n" + ee.getMessage());
							return new ModelAndView(getErrorView(),"model",mfs);
						}
						mfs.put("refresh", "true");
						request.getSession().setAttribute("sessiontime", Long.toString(System.currentTimeMillis()));
					}
					
					
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TDeviceTypeInitDao: " + deviceType.toString());
					
				}else if(testresult.size()>0){
		//			System.out.println("testresult -----------invoked");
					boolean exists = false;
					for(int i=0;i<testresult.size();i++){
		//				System.out.println("for invoked-------");
						DeviceTypeTree device = testresult.get(i);
	    //				System.out.println("the device is------"+device);
						if((device.getModel().equalsIgnoreCase(model))&& device.getObjectid().equalsIgnoreCase(objectid)){
							exists = true;
		//					System.out.println("all message are the same-----");
							message = "baseinfo.adddevice.dul";
		//					System.out.println("message is"+message);
							mflist = TManufacturerInfoInitDao.findAll();
							catelist = TCategoryMapInitDao.findAll();
							
							mfs.put("mflist", mflist);
							mfs.put("catelist", catelist);
							mfs.put("mf", mf);
							mfs.put("cate", cate);
							mfs.put("category", category);
							mfs.put("message", message);
							mfs.put("subcate", subcate);
							mfs.put("subCategory", subCategory);
							mfs.put("model", model);
							mfs.put("objectid", objectid);
							mfs.put("description", description);
							
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
							//System.out.println("subCategory is---"+subCategory+"subcate is---"+subcate);
							return new ModelAndView(getErrorView(),"model",mfs);
						}
						
					}
					if(exists==false){
						//TDeviceTypeInit deviceType= new	TDeviceTypeInit();
						long dtid = genPkNumber.getID();
						deviceType.setDtid(dtid);
						deviceType.setCategory(categoryId);
						deviceType.setDescription(description);
						deviceType.setModel(model);
						deviceType.setMrid(mrid);
						deviceType.setObjectid(objectid);
						deviceType.setSubcategory(subCategory);
						//subcate = subCategory;
			//			System.out.println(dtid+"|"+categoryId+"|"+description+"|"+model+"|"+mrid+"|"+objectid+"|"+subCategory+"|");
			//			System.out.println(deviceType.getDtid()+":"+deviceType.getCategory()+"-----------------");
			//			System.out.println("HERE EXECUTE SECOND TIME");
						if(strTicketA.equals(strTicketB)){
							try{
							TDeviceTypeInitDao.insert(deviceType);
							Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TDeviceTypeInitDao: " + deviceType.toString());
							}catch(DataIntegrityViolationException e){
								message = "baseinfo.adddevice.error";
								mfs.put("message",message);
								mflist = TManufacturerInfoInitDao.findAll();
								catelist = TCategoryMapInitDao.findAll();
								
								mfs.put("mflist", mflist);
								mfs.put("catelist", catelist);
								mfs.put("mf", mf);
								mfs.put("cate", cate);
								mfs.put("category", category);
								mfs.put("subCategory", subCategory);
								mfs.put("subcate", subcate);
								mfs.put("model", model);
								mfs.put("objectid", objectid);
								mfs.put("description", description);
								
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
								Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
								e.printStackTrace();
								return new ModelAndView(getErrorView(),"model",mfs);
							}
							catch(Exception ee){
								mflist = TManufacturerInfoInitDao.findAll();
								catelist = TCategoryMapInitDao.findAll();
								message = "baseinfo.new.error";
								mfs.put("message", message);								
								mfs.put("mflist", mflist);
								mfs.put("catelist", catelist);
								mfs.put("mf", mf);
								mfs.put("cate", cate);
								mfs.put("category", category);
								mfs.put("subCategory", subCategory);
								mfs.put("subcate", subcate);
								mfs.put("model", model);
								mfs.put("objectid", objectid);
								mfs.put("description", description);
								ee.printStackTrace();
								
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
								Log4jInit.ncsLog.error(this.getClass().getName() + " Inserted to TDeviceTypeInitDao Failed: " + deviceType.toString() + "\n" + ee.getMessage());
								return new ModelAndView(getErrorView(),"model",mfs);
							}
							mfs.put("refresh", "true");
							request.getSession().setAttribute("sessiontime", Long.toString(System.currentTimeMillis()));
						}
						//Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TDeviceTypeInitDao: " + deviceType.toString());
				}
				}
				
				}else{
					message = "baseinfo.adddevice.wrong";
					mfs.put("message",message);
					mflist = TManufacturerInfoInitDao.findAll();
					catelist = TCategoryMapInitDao.findAll();
					
					mfs.put("mflist", mflist);
					mfs.put("catelist", catelist);
					mfs.put("mf", mf);
					mfs.put("cate", cate);
					mfs.put("category", category);
					mfs.put("subCategory", subCategory);
					mfs.put("subcate", subcate);
					mfs.put("model", model);
					mfs.put("objectid", objectid);
					mfs.put("description", description);
					
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
					return new ModelAndView(getErrorView(),"model",mfs);
					
				}
			}catch(Exception e){
				message = "baseinfo.new.failed";
				mfs.put("message",message);
				
				mflist = TManufacturerInfoInitDao.findAll();
				catelist = TCategoryMapInitDao.findAll();
				
				mfs.put("mflist", mflist);
				mfs.put("catelist", catelist);
				mfs.put("mf", mf);
				mfs.put("cate", cate);
				mfs.put("category", category);
				mfs.put("subCategory", subCategory);
				mfs.put("subcate", subcate);
				mfs.put("model", model);
				mfs.put("objectid", objectid);
				mfs.put("description", description);
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
				
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
				return new ModelAndView(getErrorView(),"model",mfs);
			}
			
			/*String devMfCateSubcate =(String)request.getParameter("devMfCateSubcate");//composed string from filter-select-[->] button
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
					//System.out.println("splited as "+params);
				
					mf 		= params.get(0);
					cate	= params.get(1);
					subcate	= params.get(2);
				} catch (Exception e) {
					Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
					e.printStackTrace();
				}
			}*/
			
		
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
			
			
			
			mflist = TManufacturerInfoInitDao.findAll();
			catelist = TCategoryMapInitDao.findAll();
			
			mfs.put("mflist", mflist);
			mfs.put("catelist", catelist);
			mfs.put("mf", mf);
			mfs.put("cate", cate);
			mfs.put("subcate",subcate);

		}
		catch (Exception e){
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
}
