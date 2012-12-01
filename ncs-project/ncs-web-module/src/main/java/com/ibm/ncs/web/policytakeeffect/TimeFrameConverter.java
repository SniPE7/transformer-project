package com.ibm.ncs.web.policytakeeffect;

import java.util.List;

import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.dto.TPolicyPeriodPk;
import com.ibm.ncs.model.exceptions.TPolicyPeriodDaoException;
import com.ibm.ncs.util.TimeToStr;

public class TimeFrameConverter {
	
	TPolicyPeriodDao TPolicyPeriodDao;

	public TimeFrameConverter() {
		
	}

	
	public  String[] composeTimeFrameString(TPolicyPeriod recored){
		StringBuffer  btime = new StringBuffer(); //StatTime format as " 00:00|
		StringBuffer  etime = new StringBuffer(); //EndTime
		
		if (recored == null) return new String[] {"",""} ;
		
		String wkday = recored.getWorkday();
//		boolean[] wkdaybool = new boolean [7];
//		for (int i =0;i<7;i++){
//			wkdaybool[i] = wkday.charAt(i)=='1'?true:false;
//		}
		String s1,s2,s3,s4,s5,s6,s7;
		String e1,e2,e3,e4,e5,e6,e7;
		
		s1 = TimeToStr.secondsToHHMMString(recored.getS1());
		s2 = TimeToStr.secondsToHHMMString(recored.getS2());
		s3 = TimeToStr.secondsToHHMMString(recored.getS3());
		s4 = TimeToStr.secondsToHHMMString(recored.getS4());
		s5 = TimeToStr.secondsToHHMMString(recored.getS5());
		s6 = TimeToStr.secondsToHHMMString(recored.getS6());
		s7 = TimeToStr.secondsToHHMMString(recored.getS7());
		
		e1 = TimeToStr.secondsToHHMMString(recored.getE1());		
		e2 = TimeToStr.secondsToHHMMString(recored.getE2());
		e3 = TimeToStr.secondsToHHMMString(recored.getE3());
		e4 = TimeToStr.secondsToHHMMString(recored.getE4());
		e5 = TimeToStr.secondsToHHMMString(recored.getE5());
		e6 = TimeToStr.secondsToHHMMString(recored.getE6());
		e7 = TimeToStr.secondsToHHMMString(recored.getE7());
		
//		for(int i=0;i<7;i++){
//			if(wkday.charAt(i)=='1'?true:false){
//				btime.append(s1);
//				etime.append(e1);
//			}else{
//				btime.append("00:00:00");
//				etime.append("00:00:00");
//			}
//			if(i <6){
//				btime.append("|");
//				etime.append("|");
//			}
//		}
		
		//--1
		if(wkday.charAt(0)=='1'?true:false){
			btime.append(s1);
			etime.append(e1);
		}else{
			btime.append("00:00:00");
			etime.append("00:00:00");
		}
		btime.append("|");
		etime.append("|");
		//--2
			if(wkday.charAt(1)=='1'?true:false){
				btime.append(s2);
				etime.append(e2);
			}else{
				btime.append("00:00:00");
				etime.append("00:00:00");
			}
			btime.append("|");
			etime.append("|");
			//--3
			if(wkday.charAt(2)=='1'?true:false){
				btime.append(s3);
				etime.append(e3);
			}else{
				btime.append("00:00:00");
				etime.append("00:00:00");
			}
			btime.append("|");
			etime.append("|");
			//--4
			if(wkday.charAt(3)=='1'?true:false){
				btime.append(s4);
				etime.append(e4);
			}else{
				btime.append("00:00:00");
				etime.append("00:00:00");
			}
			btime.append("|");
			etime.append("|");	
			//--5
			if(wkday.charAt(4)=='1'?true:false){
				btime.append(s5);
				etime.append(e5);
			}else{
				btime.append("00:00:00");
				etime.append("00:00:00");
			}
			btime.append("|");
			etime.append("|");	
			//--6
			if(wkday.charAt(5)=='1'?true:false){
				btime.append(s6);
				etime.append(e6);
			}else{
				btime.append("00:00:00");
				etime.append("00:00:00");
			}
			btime.append("|");
			etime.append("|");
			//--7
			if(wkday.charAt(6)=='1'?true:false){
				btime.append(s7);
				etime.append(e7);
			}else{
				btime.append("00:00:00");
				etime.append("00:00:00");
			}

		return new String[] {btime.toString(),etime.toString()};
	}
	
	public  int fillTimeFrameTableBtimeEtime(){
		System.out.println("fillTimeFrameTableBtimeEtime...");
		int ret = -1; // return flag;
		List<TPolicyPeriod> list =null;
		try {
			 list  = TPolicyPeriodDao.findAll();
			for(TPolicyPeriod dto : list){
				try {
					String[] ss = composeTimeFrameString(dto);
					if(ss!=null){
						dto.setBtime(ss[0]);
						dto.setEtime(ss[1]);
						TPolicyPeriodPk pk = dto.createPk();
						TPolicyPeriodDao.update(pk, dto);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (TPolicyPeriodDaoException e) {
			e.printStackTrace();
		}
		System.out.println("list="+list);
		ret = list==null?-1:list.size();
		return ret;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}


	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}




}
