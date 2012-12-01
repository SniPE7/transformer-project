/**
 * 
 */
package com.ibm.ncs.web.policyapply;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PredefmibPolMapDao;
import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.PredefmibPolMap;
import com.ibm.ncs.model.dto.TDevpolMap;
import com.ibm.ncs.model.dto.TLinepolMap;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.dto.TPolicyPeriodPk;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.TimeToStr;
import com.ibm.ncs.web.policyapply.bean.PolicyDefBean;

/**
 * @author root
 *
 */
public class DeletePolicyPeriodDefController implements Controller {


	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	TDevpolMapDao TDevpolMapDao;
	TLinepolMapDao TLinepolMapDao;
	PredefmibPolMapDao predefmibPolMapDao;
	String pageView;
	String message = "";

	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
	}

	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}

	
	public void setTDevpolMapDao(TDevpolMapDao devpolMapDao) {
		TDevpolMapDao = devpolMapDao;
	}

	public void setTLinepolMapDao(TLinepolMapDao linepolMapDao) {
		TLinepolMapDao = linepolMapDao;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPredefmibPolMapDao(PredefmibPolMapDao predefmibPolMapDao) {
		this.predefmibPolMapDao = predefmibPolMapDao;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PolicyDefBean bean = new PolicyDefBean();
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			String[] wkdayParam = request.getParameterValues("chkworkday");
			String [] starttime = request.getParameterValues("starttime");
			String [] endtime 	= request.getParameterValues("endtime");
			String    ppidStr	= request.getParameter("ppid");
			String 	ppname		= request.getParameter("ppname");
			String description	= request.getParameter("description");
			String enabled		= request.getParameter("enabled");
			
			try{
				long ppid =-1;
				if(null!=ppidStr&& ! "".equals(ppidStr)){
					ppid = Long.parseLong(ppidStr);
				}
			
				List<TDevpolMap> policys = TDevpolMapDao.findWherePpidEquals(ppid);
				List<TLinepolMap> linepol = TLinepolMapDao.findWherePpidEquals(ppid);
				List<PredefmibPolMap> mibpol = predefmibPolMapDao.findWherePpidEquals(ppid);
		//		System.out.println("policys size is "+policys.size()+"and linepol size is "+linepol.size()+" and mibpol size is "+mibpol.size());
				if(policys.size()>0 || linepol.size()>0 || mibpol.size()>0){
					message = "policyapply.delete.ocuppied";
					model.put("message", message);
					bean.setPpid(ppid);
					TPolicyPeriod  pp = TPolicyPeriodDao.findByPrimaryKey(bean.getPpid());	
					
					bean = bean.beanTransfer(pp);
					model.put("policyperiod", bean);
					return new ModelAndView(getPageView()+"?refresh=true", "model",model);
				}
				bean.setPpid(ppid);
				TPolicyPeriodPk pk = new TPolicyPeriodPk(ppid);
				TPolicyPeriodDao.delete(pk);
				Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TPolicyPeriodDao: pk= " + pk.toString());
				
			} catch (Exception e) {
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
			}
			TPolicyPeriod  pp = TPolicyPeriodDao.findByPrimaryKey(bean.getPpid());	
			
			bean = bean.beanTransfer(pp);
			model.put("policyperiod", bean);
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}

		return new ModelAndView(getPageView()+"?refresh=true", "model",model);
	}
	
	public Map converPolicyperiod2Map(TPolicyPeriod pp){
		
		
		Map<String , Object> days, def1 ,def2, def3, def4, def5, def6, def7;

			def1 = new HashMap<String, Object>();
			def1.put("workday", pp.getWorkday().charAt(0)=='1'?true:false );
			def1.put("startlong", pp.getS1());
			def1.put("endlong", pp.getE1());
			def1.put( "startStr", TimeToStr.secondsToString((pp.getS1() )));
			def1.put( "endStr", TimeToStr.secondsToString((pp.getE1() )));
			
			def2 = new HashMap<String, Object>();
			def2.put("workday", pp.getWorkday().charAt(1)=='1'?true:false );
			def2.put("startlong", pp.getS2());
			def2.put("endlong", pp.getE2());
			def2.put( "startStr", TimeToStr.secondsToString((pp.getS2() )));
			def2.put( "endStr", TimeToStr.secondsToString((pp.getE2() )));
			
			def3 = new HashMap<String, Object>();
			def3.put("workday", pp.getWorkday().charAt(2)=='1'?true:false );
			def3.put("startlong", pp.getS3());
			def3.put("endlong", pp.getE3());
			def3.put( "startStr", TimeToStr.secondsToString((pp.getS3() )));
			def3.put( "endStr", TimeToStr.secondsToString((pp.getE3() )));
			
			def4 = new HashMap<String, Object>();
			def4.put("workday", pp.getWorkday().charAt(3)=='1'?true:false );
			def4.put("startlong", pp.getS4());
			def4.put("endlong", pp.getE4());
			def4.put( "startStr", TimeToStr.secondsToString((pp.getS4() )));
			def4.put( "endStr", TimeToStr.secondsToString((pp.getE4() )));
			
			def5 = new HashMap<String, Object>();
			def5.put("workday", pp.getWorkday().charAt(4)=='1'?true:false );
			def5.put("startlong", pp.getS5());
			def5.put("endlong", pp.getE5());
			def5.put( "startStr", TimeToStr.secondsToString((pp.getS5() )));
			def5.put( "endStr", TimeToStr.secondsToString((pp.getE5() )));
			
			def6 = new HashMap<String, Object>();
			def6.put("workday", pp.getWorkday().charAt(5)=='1'?true:false );
			def6.put("startlong", pp.getS6());
			def6.put("endlong", pp.getE6());
			def6.put( "startStr", TimeToStr.secondsToString((pp.getS6() )));
			def6.put( "endStr", TimeToStr.secondsToString((pp.getE6() )));
			
			def7 = new HashMap<String, Object>();
			def7.put("workday", pp.getWorkday().charAt(6)=='1'?true:false );
			def7.put("startlong", pp.getS7());
			def7.put("endlong", pp.getE7());
			def7.put( "startStr", TimeToStr.secondsToString((pp.getS7() )));
			def7.put( "endStr", TimeToStr.secondsToString((pp.getE7() )));
			
			days = new HashMap<String, Object>();
			days.put("1", def1);
			days.put("2", def2);
			days.put("3", def3);
			days.put("4", def4);
			days.put("5", def5);
			days.put("6", def6);
			days.put("7", def7);

		return days;
	}
	
//	public PolicyDefBean beanTransfer(TPolicyPeriod policyperiod){
//		String workday = policyperiod.getWorkday();
//		PolicyDefBean bean = new PolicyDefBean();
//		String[] wkdayStr = new String []{ 
//									workday.charAt(0)=='1'?"1":"0", 
//									workday.charAt(1)=='1'?"1":"0", 
//									workday.charAt(2)=='1'?"1":"0", 
//									workday.charAt(3)=='1'?"1":"0", 
//									workday.charAt(4)=='1'?"1":"0", 
//									workday.charAt(5)=='1'?"1":"0", 
//									workday.charAt(6)=='1'?"1":"0"	};
//		long[] starttimelong = new long[]{
//									policyperiod.getS1(),
//									policyperiod.getS2(),
//									policyperiod.getS3(),
//									policyperiod.getS4(),
//									policyperiod.getS5(),
//									policyperiod.getS6(),
//									policyperiod.getS7()		};
//		long[] endtimelong = new long[]{
//									policyperiod.getE1(),
//									policyperiod.getE2(),
//									policyperiod.getE3(),
//									policyperiod.getE4(),
//									policyperiod.getE5(),
//									policyperiod.getE6(),
//									policyperiod.getE7()	};
//		bean.setWorkday(wkdayStr);
//		
//		bean.setStarttime(starttimelong);
//		bean.setEndtime(endtimelong);
//		bean.setPpid(policyperiod.getPpid());
//		bean.setPpname(policyperiod.getPpname());
//		bean.setDefaultflag(policyperiod.getDefaultflag());
//		bean.setDescription(policyperiod.getDescription());
//		bean.setEnabled(policyperiod.getEnabled());
//		
////		
////		for (int i=0;i<=6;i++){
////			System.out.println(wkdayStr[i]+"\t"+bean.getWorkday()[i]);
////		}
//		return bean;
//	}
//	


	public TPolicyPeriod  transferBeantoDto(PolicyDefBean bean){
		
		
		TPolicyPeriod dto = new TPolicyPeriod();
		String workday = "";
		String wkd[] = bean.getWorkday();
		String starttimeFormated[] = bean.getStarttimeFormated();
		String [] endtimeFormated = bean.getEndtimeFormated();
	
		long oStarttime [] = new long[] {0,0,0,0,0,0,0};
		long oEndtime []   = new long[] {0,0,0,0,0,0,0};
		for (int i=0;i<=6;i++){
			workday += wkd[i]; 
			System.out.println(starttimeFormated[i]);
			oStarttime[i] 	= TimeToStr.stringToTime(starttimeFormated[i]);
			oEndtime[i]		= TimeToStr.stringToTime(endtimeFormated[i]);
		}	
System.out.println("bean.ppname="+bean.getPpname()+"workday"+workday);		
		dto.setWorkday(workday);
		dto.setDefaultflag(bean.getDefaultflag());
		dto.setPpid(bean.getPpid());
		dto.setPpname(bean.getPpname());
		dto.setDescription(bean.getDescription());
		dto.setEnabled(bean.getEnabled());
		
		dto.setS1(oStarttime[0]);
		dto.setE1(oEndtime[0]);
		
		dto.setS2(oStarttime[1]);
		dto.setE2(oEndtime[1]);
		
		dto.setS3(oStarttime[2]);
		dto.setE3(oEndtime[2]);
		
		dto.setS4(oStarttime[3]);
		dto.setE4(oEndtime[3]);
		
		dto.setS5(oStarttime[4]);
		dto.setE5(oEndtime[4]);
		
		
		dto.setS6(oStarttime[5]);
		dto.setE6(oEndtime[5]);
		
		dto.setS7(oStarttime[6]);
		dto.setE7(oEndtime[6]);
		
		dto.setStartTime(new Date());
		dto.setEndTime(new Date(+365));
		
		return dto;
	}


	
	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public TPolicyBaseDao getTPolicyBaseDao() {
		return TPolicyBaseDao;
	}

	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}

}
