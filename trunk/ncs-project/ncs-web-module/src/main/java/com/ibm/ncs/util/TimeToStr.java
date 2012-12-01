package com.ibm.ncs.util;

import java.util.StringTokenizer;

public class TimeToStr {

	public static String secondsToString(long time){
		int seconds = (int) (time % 60) ;
		int minutes = (int) ((time / 60) % 60);
		int hours 	= (int) ((time /3600) % 24);
		
		String secondsStr = (seconds <10 ?"0":"")+seconds;
		String minutesStr = (minutes <10 ?"0":"")+minutes;
		String hoursStr   = (hours <10   ?"0":"")+hours;
//		System.out.println(hours+" "+minutes+" "+seconds);
		return new String(hoursStr+":"+minutesStr+":"+secondsStr);
		
		
	}
	
	public static String secondsToHHMMString(long time){
		int seconds = (int) (time % 60) ;
		int minutes = (int) ((time / 60) % 60);
		int hours 	= (int) ((time /3600) % 24);
		
		String secondsStr = (seconds <10 ?"0":"")+seconds;
		String minutesStr = (minutes <10 ?"0":"")+minutes;
		String hoursStr   = (hours <10   ?"0":"")+hours;
//		System.out.println(hours+" "+minutes+" "+seconds);
		return new String(hoursStr+":"+minutesStr+":"+secondsStr);
		
		
	}
	
	public static long stringToTime(String s){
		StringTokenizer st = new StringTokenizer(s, ":");
		String hoursStr = null;
		String minutesStr = null;
		String secondsStr = null;
		try {
			hoursStr = st.nextToken();
			minutesStr = st.nextToken();
			secondsStr = st.nextToken();
		} catch (Exception e) {
		}
		
		hoursStr=(hoursStr==null||hoursStr.equals(""))?"0":hoursStr;
		minutesStr=(minutesStr==null||minutesStr.equals(""))?"0":minutesStr;
		secondsStr=(secondsStr==null||secondsStr.equals(""))?"0":secondsStr;
		
		long hours   = Long.parseLong(hoursStr)*3600;
		long minutes = Long.parseLong(minutesStr)*60;
		long seconds = Long.parseLong(secondsStr);
		
		return hours+minutes+seconds;
		
		
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		long t = 86398;
		String t1 = TimeToStr.secondsToString(t);
		System.out.println(t1+"..."+t+" ");

		String s ="23:59:58";
		long ti = TimeToStr.stringToTime(s);
		System.out.println(ti);
		
		String sss7 = "1111011";
		char c0 = sss7.charAt(0);
		char c1 = sss7.charAt(1);
		char c2 = sss7.charAt(2);
		char c3 = sss7.charAt(3);
		char c4 = sss7.charAt(4);
		char c5 = sss7.charAt(5);
		char c6 = sss7.charAt(6);		
		
		System.out.println("c0="+c0);
		System.out.println("c0=1? ..."+ (c0=='1'?true:false) );
		System.out.println("c1=1? ..."+ (c1=='1'?true:false) );
		System.out.println("c2=1? ..."+ (c2=='1'?true:false) );
		System.out.println("c3=1? ..."+ (c3=='1'?true:false) );
		System.out.println("c4=1? ..."+ (c4=='1'?true:false) );
		System.out.println("c5=1? ..."+ (c5=='1'?true:false) );
		System.out.println("c6=1? ..."+ (c6=='1'?true:false) );
		
		
		
	}

}
