package com.ibm.ncs.web.policyapply.bean;

import java.util.Date;

import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.util.TimeToStr;

public class PolicyDefBean {

	String[] workday = new String[7];
	long[] starttime =new long[7];
	long[] endtime = new long [7];
	String[] starttimeFormated = new String [7];
	String[] endtimeFormated   = new String[7];
	
	Date startDate;
	Date enddate;
	
	String enabled;
	String defaultflag;
	long ppid;
	String ppname;
	String description;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 

	}


	public PolicyDefBean beanTransfer(TPolicyPeriod policyperiod){
		String workday = policyperiod.getWorkday();
		
		String[] wkdayStr = new String []{ 
									workday.charAt(0)=='1'?"1":"0", 
									workday.charAt(1)=='1'?"1":"0", 
									workday.charAt(2)=='1'?"1":"0", 
									workday.charAt(3)=='1'?"1":"0", 
									workday.charAt(4)=='1'?"1":"0", 
									workday.charAt(5)=='1'?"1":"0", 
									workday.charAt(6)=='1'?"1":"0"	};
		long[] starttimelong = new long[]{
									policyperiod.getS1(),
									policyperiod.getS2(),
									policyperiod.getS3(),
									policyperiod.getS4(),
									policyperiod.getS5(),
									policyperiod.getS6(),
									policyperiod.getS7()		};
		long[] endtimelong = new long[]{
									policyperiod.getE1(),
									policyperiod.getE2(),
									policyperiod.getE3(),
									policyperiod.getE4(),
									policyperiod.getE5(),
									policyperiod.getE6(),
									policyperiod.getE7()	};
		String[] starttimeStr= new String[]{
									TimeToStr.secondsToString(	policyperiod.getS1() ),
									TimeToStr.secondsToString(	policyperiod.getS2() ),
									TimeToStr.secondsToString(	policyperiod.getS3() ),
									TimeToStr.secondsToString(	policyperiod.getS4() ),
									TimeToStr.secondsToString(	policyperiod.getS5() ),
									TimeToStr.secondsToString(	policyperiod.getS6() ),
									TimeToStr.secondsToString(	policyperiod.getS7() )
		};
		this.starttimeFormated = starttimeStr;
		String[] endtimeStr= new String[]{
				TimeToStr.secondsToString(	policyperiod.getE1() ),
				TimeToStr.secondsToString(	policyperiod.getE2() ),
				TimeToStr.secondsToString(	policyperiod.getE3() ),
				TimeToStr.secondsToString(	policyperiod.getE4() ),
				TimeToStr.secondsToString(	policyperiod.getE5() ),
				TimeToStr.secondsToString(	policyperiod.getE6() ),
				TimeToStr.secondsToString(	policyperiod.getE7() )
};		
		this.endtimeFormated = endtimeStr;
		this.setWorkday(wkdayStr);
		this.setStarttime(starttimelong);
		this.setEndtime(endtimelong);
		this.setPpid(policyperiod.getPpid());
		this.setPpname(policyperiod.getPpname());
		this.setDefaultflag(policyperiod.getDefaultflag());
		this.setDescription(policyperiod.getDescription());
		this.setEnabled(policyperiod.getEnabled());
		
		return this;
	}
	

//
//	public TPolicyPeriod  transferBeantoDto(){
//		
//		System.out.println(this);
//		System.out.println(this.starttimeFormated[0]);
//		
//		TPolicyPeriod dto = new TPolicyPeriod();
//		String workday = "";
//		long oStarttime [] = new long[] {0,0,0,0,0,0,0};
//		long oEndtime []   = new long[] {0,0,0,0,0,0,0};
//		for (int i=0;i<=6;i++){
//			workday += this.workday[i]; 
//			oStarttime[i] 	= TimeToStr.stringToTime(starttimeFormated[i]);
//			oEndtime[i]		= TimeToStr.stringToTime(endtimeFormated[i]);
//		}	
//		
//		dto.setWorkday(workday);
//		dto.setDefaultflag(this.getDefaultflag());
//		dto.setPpid(this.getPpid());
//		dto.setPpname(this.getPpname());
//		dto.setDescription(this.getDescription());
//		dto.setEnabled(this.getEnabled());
//		
//		dto.setS1(oStarttime[0]);
//		dto.setE1(oEndtime[0]);
//		
//		dto.setS2(oStarttime[1]);
//		dto.setE2(oEndtime[1]);
//		
//		dto.setS3(oStarttime[2]);
//		dto.setE3(oEndtime[2]);
//		
//		dto.setS4(oStarttime[3]);
//		dto.setE4(oEndtime[3]);
//		
//		dto.setS5(oStarttime[4]);
//		dto.setE5(oEndtime[4]);
//		
//		
//		dto.setS6(oStarttime[5]);
//		dto.setE6(oEndtime[5]);
//		
//		dto.setS7(oStarttime[6]);
//		dto.setE7(oEndtime[6]);
//		
//		return dto;
//	}
//


	public long[] getStarttime() {
		return starttime;
	}





	public void setStarttime(long[] starttime) {
		this.starttime = starttime;
	}





	public long[] getEndtime() {
		return endtime;
	}





	public void setEndtime(long[] endtime) {
		this.endtime = endtime;
	}








	public String[] getWorkday() {
		return workday;
	}








	public void setWorkday(String[] workday) {
		this.workday = workday;
	}



















	public long getPpid() {
		return ppid;
	}








	public void setPpid(long ppid) {
		this.ppid = ppid;
	}








	public String getPpname() {
		return ppname;
	}








	public void setPpname(String ppname) {
		this.ppname = ppname;
	}








	public String getDescription() {
		return description;
	}








	public void setDescription(String description) {
		this.description = description;
	}








	public Date getStartDate() {
		return startDate;
	}








	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}








	public Date getEnddate() {
		return enddate;
	}








	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}








	public String getDefaultflag() {
		return defaultflag;
	}








	public void setDefaultflag(String defaultflag) {
		this.defaultflag = defaultflag;
	}








	public String getEnabled() {
		return enabled;
	}








	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}


	public String[] getStarttimeFormated() {
		return starttimeFormated;
	}


	public void setStarttimeFormated(String[] starttimeFormated) {
		this.starttimeFormated = starttimeFormated;
	}


	public String[] getEndtimeFormated() {
		return endtimeFormated;
	}


	public void setEndtimeFormated(String[] endtimeFormated) {
		this.endtimeFormated = endtimeFormated;
	}






}
