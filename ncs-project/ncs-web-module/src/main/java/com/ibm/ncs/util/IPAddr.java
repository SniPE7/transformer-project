package com.ibm.ncs.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class IPAddr {
	
    public static long ip2Long(String ipaddr)
    {
        if(ipaddr == null || ipaddr == "")
        {
            return 0L;
        } else
        {
            StringTokenizer stk = new StringTokenizer(ipaddr, ".");
            long fields1 = Long.parseLong(stk.nextToken());
            long fields2 = Long.parseLong(stk.nextToken());
            long fields3 = Long.parseLong(stk.nextToken());
            long fields4 = Long.parseLong(stk.nextToken());
            String s1 = Long.toString(fields1, 16);
            String s2 = Long.toString(fields2, 16);
            String s3 = Long.toString(fields3, 16);
            String s4 = Long.toString(fields4, 16);
            String temp = (s1.length() <= 1 ? "0" + s1 : s1) + (s2.length() <= 1 ? "0" + s2 : s2) + (s3.length() <= 1 ? "0" + s3 : s3) + (s4.length() <= 1 ? "0" + s4 : s4);
            return Long.parseLong(temp, 16);
        }
    }
    public static long encode(String ipAddr){
    	return ip2Long(ipAddr);
    }
    
    public static String decode(long decimal){

    	String hexval = Long.toHexString(decimal);
    	//System.out.println("----"+hexval);
    	for (int i=hexval.length();i<8;i++){
    		hexval = "0"+hexval;
    	}
    	//if (hexval.length()<8) hexval="0"+hexval;
    	//System.out.println("----"+hexval);
    	
    	String s1 = hexval.substring(0, 2);
    	String s2 = hexval.substring(2, 4);
    	String s3 = hexval.substring(4, 6);
    	String s4 = hexval.substring(6, 8);
    	long l1 = Long.parseLong(s1,16);
    	long l2 = Long.parseLong(s2,16);
    	long l3 = Long.parseLong(s3,16);
    	long l4 = Long.parseLong(s4,16);

    	//System.out.println("----"+s1+"."+s2+"."+s3+"."+s4);
    		
    	return l1+"."+l2+"."+l3+"."+l4;
    }

//    public static long parseIpDecodeMax(String ipAddr){
//        StringTokenizer stk = new StringTokenizer(ipAddr, ".");
//        long fields1 = Long.parseLong(stk.nextToken());
//        long fields2 = Long.parseLong(stk.nextToken());
//        long fields3 = Long.parseLong(stk.nextToken());
//        long fields4 = Long.parseLong(stk.nextToken());
//        String s1 = Long.toString(fields1, 16);
//        String s2 = Long.toString(255, 16);
//        String s3 = Long.toString(255, 16);
//        String s4 = Long.toString(255, 16);
//        String temp = (s1.length() <= 1 ? "0" + s1 : s1) + (s2.length() <= 1 ? "0" + s2 : s2) + (s3.length() <= 1 ? "0" + s3 : s3) + (s4.length() <= 1 ? "0" + s4 : s4);
//        return Long.parseLong(temp, 16);
//    }

    public static long getMinIp(String ipaddr, String mask)
        throws Exception
    {
        long max = encode("255.255.255.255");
        long lIp = encode(ipaddr);
        long lMask = encode(mask);
        long minIpdecode = lIp & lMask;
        return minIpdecode;
    }

    public static long getMaxIp(String ipaddr, String mask)
        throws Exception
    {
        long max = encode("255.255.255.255");
        long lIp = encode(ipaddr);
        long lMask = encode(mask);
        long minIpdecode = lIp & lMask;
        long tmp = max ^ lMask;
        long maxIpdecode = minIpdecode + tmp;
        return maxIpdecode;
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("192.168.1.1\t="+ip2Long("192.168.1.1"));//3232235777
		System.out.println("80.0.0.0\t="+ip2Long("80.0.0.0"));//1342177280
		System.out.println("80.255.255.255\t="+ip2Long("80.255.255.255"));//1358954495
		
		System.out.println("172.0.0.0\t="+encode("172.0.0.0"));//2885681152
//		System.out.println("172.0.0.0max\t="+parseIpDecodeMax("172.0.0.0"));
		
		System.out.println("2885681152\t="+decode(2885681152l));//172.0.0.0
		System.out.println("1358954495\t="+decode(1358954495l));//80.255.255.255
		System.out.println("3232235777\t="+decode(3232235777l));//192.168.1.1
		
	}

}
