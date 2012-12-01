/**
 * 
 */
package com.ibm.ncs.web.policyapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.TimeToStr;
import com.ibm.ncs.web.policyapply.bean.PolicyDefBean;

/**
 * @author root
 *
 */
public class DefaultPolicyPeriodDefController implements Controller {


	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	GenPkNumber GenPkNumber;
	String pageView;

	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
	}

	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String message = "";
		PolicyDefBean bean = new PolicyDefBean();
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			String ppidstr = request.getParameter("ppid");
			long ppid =0;
			if(null!=ppidstr&&!"".equals(ppidstr)){
				ppid = Long.parseLong(ppidstr);
			}
			List<TPolicyPeriod> pplist = TPolicyPeriodDao.findWhereDefaultflagEquals("1");	
			if(pplist.size()>0)
			bean = bean.beanTransfer(pplist.get(0));
			model.put("policyperiod", bean);
		}catch(DaoException e){
			e.printStackTrace();
			message = "global.db.error";
			model.put("message", message);
			return new ModelAndView("/dberror.jsp","model",model);
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		
		
		return new ModelAndView(getPageView(), "model",model);
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

	public GenPkNumber getGenPkNumber() {
		return GenPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		GenPkNumber = genPkNumber;
	}

}
