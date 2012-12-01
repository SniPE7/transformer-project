

package com.ibm.ncs.util.ectest;

import java.util.ArrayList;
import java.util.List;

import com.ibm.ncs.util.AppException;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SnmpUtil;
import com.ibm.ncs.util.test.Device;
import com.ibm.ncs.util.test.Flashfile;
import com.ibm.ncs.util.test.Interface;

public class Detector
{

    public Detector()
    {
        nSnmp = null;
    }

    long snmpTimeout = 5000L;
    public Device getDeviceBySNMP(String ipAddr, String rCommunity, int ver, Device d)
        throws Exception
    {
        int ret = 0;
        int ifnum = 0;
        nSnmp = new USnmp(ipAddr, rCommunity, ver, snmpTimeout);
        nSnmp.addOID("1.3.6.1.2.1.1.3.0");
        if(nSnmp.get() == 0)
            return null;
        nSnmp.close();
        //System.out.println("++++++++snmp...before nSnmp get () ++++++++");
        Device device = d;
        device.setIp(ipAddr);
        device.setRCommunity(rCommunity);
        device.setSnmpversion(ver);
        nSnmp.addOID("1.3.6.1.2.1.1.2.0");
        nSnmp.addOID("1.3.6.1.2.1.1.5.0");
        nSnmp.addOID("1.3.6.1.2.1.1.1.0");
        nSnmp.addOID("1.3.6.1.4.1.9.3.6.6.0");
        nSnmp.addOID("1.3.6.1.4.1.9.3.6.7.0");
        nSnmp.addOID("1.3.6.1.2.1.47.1.1.1.1.9.1");
        nSnmp.addOID("1.3.6.1.4.1.9.3.6.3.0");
        nSnmp.addOID("1.3.6.1.2.1.2.1.0");
        ret = nSnmp.get();
        if(ret == 1)
        {
            device.getDevicetype().setObjectid(nSnmp.getValue(0));
            device.setName(nSnmp.getValue(1));
            device.setDesc(nSnmp.getValue(2));
            try {
				device.setRamsize(Long.parseLong(nSnmp.getValue(3)));
				device.setRamunit("B");
			} catch (Exception e) {
				//e.printStackTrace();
			}            
            try {
				device.setNvramsize(Long.parseLong(nSnmp.getValue(4)));
				device.setNvramunit("B");
			} catch (Exception e) {		/*e.printStackTrace();*/	}
            device.setSoftwareversion(nSnmp.getValue(5));
            try {
				if(device.getSoftwareversion().equalsIgnoreCase("noSuchInstance") || device.getSoftwareversion().equalsIgnoreCase("") || device.getSoftwareversion() == null)
				{
				    int flag = 0;
				    StringBuffer strTemp = new StringBuffer();
				    if(device.desc.indexOf("Catalyst Operating System") >= 0)
				        flag = 1;
				    int bool = Funct.regexVersion(device.desc, strTemp, flag);
				    if(bool != 0){
				        //NccLog.log("\u65E0\u6CD5\u4ECE\u8BBE\u5907" + device.name + "\u7684\u8F6F\u4EF6\u7248\u672C\u63CF\u8FF0\u4E2D\u53D6\u51FA\u8F6F\u4EF6\u7248\u672C\u53F7");
				    	//无法从设备+device.name+的软件版本描述中取出软件版本号
				    	System.out.println("\u65E0\u6CD5\u4ECE\u8BBE\u5907" + device.name + "\u7684\u8F6F\u4EF6\u7248\u672C\u63CF\u8FF0\u4E2D\u53D6\u51FA\u8F6F\u4EF6\u7248\u672C\u53F7");
				    }
				    device.setSoftwareversion(strTemp.toString());
				}
			} catch (Exception e) {		/*e.printStackTrace();*/	}
            device.setSerial(nSnmp.getValue(6));
            try {
				ifnum = Integer.parseInt(nSnmp.getValue(7));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        } else
        {
            return null;
        }
        nSnmp.close();
        device.setFlashsize(getFlashsize());
        device.setFlashunit("B");
        //System.out.println("++++++++snmp...before get FLASHFILE++++++++");
        device.flashfiles = getFlashfile(ipAddr, rCommunity, ver);
        //System.out.println("++++++++snmp...before getInterface++++++++");
        device.interfaces = getInterface(ipAddr, rCommunity, ver, device);
        return device;
    }

    public Device fillDeviceBySNMP(Device d, ArrayList al)
    {
        String ipAddr = d.getIp();
        String rCommunity = d.getRcommunity();
        int ver = d.snmpversion - 1;
        Device newd;
        try
        {
            newd = getDeviceBySNMP(ipAddr, rCommunity, ver, d);
        }
        catch(Exception e)
        {
        	//e.printStackTrace();
            newd = null;
        }
        if(newd == null)
        {
            return d;
        } else
        {
            al.add("11");
            newd.snmpversion++;
            return newd;
        }
    }

    private long getFlashsize()
    {
        int ret = 0;
        long ds = 0L;
        nSnmp.addOID("1.3.6.1.4.1.9.9.10.1.1.2.1.2");
        ret = nSnmp.getTable();
        if(ret == 1)
        {
            for(int i = 0; i < nSnmp.getResponseSize(); i++)
                ds += Long.parseLong(nSnmp.getValue(i));

        }
        nSnmp.close();
        return ds;
    }

    private Flashfile[] getFlashfile()
    {
        ArrayList al = new ArrayList();
        int ret = 0;
        nSnmp.addOID("1.3.6.1.4.1.9.9.10.1.1.4.2.1.1.5");
        ret = nSnmp.getTable();
        String fn[] = (String[])null;
        String fs[] = (String[])null;
        if(ret == 1)
            fn = nSnmp.getAllValue();
        nSnmp.close();
        nSnmp.addOID("1.3.6.1.4.1.9.9.10.1.1.4.2.1.1.2");
        ret = nSnmp.getTable();
        if(ret == 1)
            fs = nSnmp.getAllValue();
        if(fn != null && fs != null)
        {
            for(int i = 0; i < fn.length; i++)
                if(fn[i] != null && fn[i].indexOf("/html/") < 0)
                {
                    Flashfile ff = new Flashfile(fn[i], Long.parseLong(fs[i]));
                    al.add(ff);
                }

        }
        nSnmp.close();
        if(al.size() > 0)
        {
            Flashfile ffs[] = new Flashfile[al.size()];
            return (Flashfile[])al.toArray(ffs);
        } else
        {
            return null;
        }
    }

    private Flashfile[] getFlashfile(String ipAddr, String rCommunity, int ver)
    {
        ArrayList al = new ArrayList();
        String [] oidflash = new String[]{
        		"1.3.6.1.4.1.9.9.10.1.1.4.2.1.1.5", 	/*ciscoFlashFileName*/
        		"1.3.6.1.4.1.9.9.10.1.1.4.2.1.1.2"		/*ciscoFlashFileSize*/
        };
        try {
			List lstFlashname = SnmpUtil.snmpTable(ipAddr, rCommunity,	oidflash[0], ver, snmpTimeout);
			Log4jInit.ncsLog.debug(this.getClass().getName()	+ " List lstFlashname=" + lstFlashname);
			List lstFlashsize = SnmpUtil.snmpTable(ipAddr, rCommunity,	oidflash[1], ver, snmpTimeout);
			Log4jInit.ncsLog.debug(this.getClass().getName()	+ " List lstFlashsize=" + lstFlashsize);
			if (lstFlashname != null && lstFlashsize != null) {
				for (int i = 0; i < lstFlashname.size(); i++)
					if (lstFlashname.get(i) != null	&& ((String) lstFlashname.get(i)).indexOf("/html/") < 0) {
						Flashfile ff = new Flashfile(
								(String) lstFlashname.get(i), 
								 Long.parseLong((String) lstFlashsize.get(i))
						);
						al.add(ff);
					}
			}
			Log4jInit.ncsLog.debug(this.getClass().getName()	+ " List Flashfile=" + al);
			if (al.size() > 0) {
				Flashfile ffs[] = new Flashfile[al.size()];
				return (Flashfile[]) al.toArray(ffs);
			}
		} catch (AppException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " getFlashfile..AppException="+ e.getMessage() );
			e.printStackTrace();
		}
		return null;
    }

    
    private Interface[] getInterface(int ifnum)
    {
        ArrayList al = new ArrayList();
        int ret = 0;
        nSnmp.addOID("1.3.6.1.2.1.2.2.1.1.0");
        nSnmp.addOID("1.3.6.1.2.1.2.2.1.2.0");
        nSnmp.addOID("1.3.6.1.2.1.2.2.1.6.0");
        int oidNum = nSnmp.getPduSize();
        ret = nSnmp.getBulk(ifnum);
        int varNum = nSnmp.getResponseSize() / oidNum;
        if(ret == 1 && varNum > 0)
        {
            for(int i = 0; i < varNum; i++)
            {
                Interface ifInfo = new Interface();
                ifInfo.setIndex(Integer.parseInt(nSnmp.getValue(i * oidNum + 0)));
                ifInfo.setName(nSnmp.getValue(i * oidNum + 1));
                ifInfo.setMac(nSnmp.getHexString(i * oidNum + 2));
                al.add(ifInfo);
            }

        }
        nSnmp.close();
        nSnmp.addOID("1.3.6.1.2.1.4.20.1.2");
        nSnmp.addOID("1.3.6.1.2.1.4.20.1.1");
        nSnmp.addOID("1.3.6.1.2.1.4.20.1.3");
        ret = nSnmp.getTable();
        if(ret == 1)
        {
            Interface itf = null;
            oidNum = nSnmp.getPduSize();
            varNum = nSnmp.getResponseSize() / oidNum;
            for(int i = 0; i < varNum; i++)
            {
                for(int j = 0; j < al.size(); j++)
                {
                    itf = (Interface)al.get(j);
                    System.out.println("getIndex="+nSnmp.getValue(i * oidNum));
                    if(itf.getIndex() == Integer.parseInt(nSnmp.getValue(i * oidNum + 0))){
                        itf.setIp(Funct.portIpDispose(nSnmp.getValue(i * oidNum + 1)));
                    System.out.println("setip="+nSnmp.getValue(i * oidNum + 1));}
                }

            }

        }
        if(al.size() > 0)
        {
            Interface itf[] = new Interface[al.size()];
            return (Interface[])al.toArray(itf);
        } else
        {
            return null;
        }
    }
    
    private Interface[] getInterface(String ipAddr, String rCommunity, int ver, Device device)
    {
    	ArrayList al = new ArrayList();
    	boolean useIfName = false;
    	if (device!=null){
    	if (device.desc.indexOf("Catalyst Operating System") >= 0){
    		useIfName = true;
    	}}
    	String [] oidiftab = new String[]{
    		"1.3.6.1.2.1.2.2.1.1",		/*ifIndex*/
    		"1.3.6.1.2.1.2.2.1.2",		/*ifDescr*/
    		"1.3.6.1.2.1.2.2.1.6",		/*ifPhysAddress*/
    		"1.3.6.1.2.1.31.1.1.1.1"	/*ifName*/
    	};
    	
    	List lstIfIndex;
		List lstIfMac;
		List lstIfName;
		try {
			lstIfIndex = SnmpUtil.snmpTable(ipAddr, rCommunity, oidiftab[0], ver, snmpTimeout);   
			//System.out.println(" List lstIfIndex="+ lstIfIndex );
			Log4jInit.ncsLog.debug(this.getClass().getName() + " List lstIfIndex="+ lstIfIndex );
			lstIfMac = SnmpUtil.snmpTable(ipAddr, rCommunity, oidiftab[2], ver, snmpTimeout);
			//System.out.println(" List lstIfMac="+ lstIfMac );
			Log4jInit.ncsLog.debug(this.getClass().getName() + " List lstIfMac="+ lstIfMac );
			if(useIfName == false){
				lstIfName 	= SnmpUtil.snmpTable(ipAddr, rCommunity, oidiftab[1], ver, snmpTimeout);
				//System.out.println(" List lstIfDescr="+ lstIfName );
				Log4jInit.ncsLog.debug(this.getClass().getName() + " List lstIfDescr="+ lstIfName  );
			}else{
				lstIfName 	= SnmpUtil.snmpTable(ipAddr, rCommunity, oidiftab[3], ver, snmpTimeout);
				//System.out.println(" List lstIfName="+ lstIfName );
				Log4jInit.ncsLog.debug(this.getClass().getName() + " List lstIfName="+ lstIfName );
			}
			for(int i = 0; i < lstIfIndex.size(); i++)
	        {
	            Interface ifInfo = new Interface();
	            ifInfo.setIndex(Integer.parseInt((String) lstIfIndex.get(i)));
	            String smac = "";	try {	smac = ((String) lstIfMac.get(i)).toUpperCase();} catch (Exception e) {	}
	            ifInfo.setMac(smac);
	            ifInfo.setName((String) lstIfName.get(i));
	            al.add(ifInfo);
	        }
		} catch (AppException e1) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured ..getInterface:\n" + e1.getMessage());
			e1.printStackTrace();
		}        

        String [] oidiptab = new String []{
        		"1.3.6.1.2.1.4.20.1.2",		/*ipAdEntIfIndex*/
        		"1.3.6.1.2.1.4.20.1.1",		/*ipAdEntAddr*/
        		"1.3.6.1.2.1.4.20.1.3"		/*ipAdEntNetMask*/
        };

        List lstIpIfIndex;
		List lstIpAddr;
//	   	List lstIpNetMask;		
		try {
			lstIpIfIndex = SnmpUtil.snmpTable(ipAddr, rCommunity, oidiptab[0], ver, snmpTimeout);    
			//System.out.println(" List lstIpIfIndex="+ lstIpIfIndex );  
			//System.out.println("Detector...snmp version...ver="+ver);
			Log4jInit.ncsLog.debug(this.getClass().getName() + " List lstIpIfIndex="+ lstIpIfIndex );
			lstIpAddr = SnmpUtil.snmpTable(ipAddr, rCommunity, oidiptab[1], ver, snmpTimeout);   
			//System.out.println(" List lstIpAddr="+ lstIpAddr );
			Log4jInit.ncsLog.debug(this.getClass().getName() + " List lstIpAddr="+ lstIpAddr );
//	   	List lstIpNetMask 	= SnmpUtil.snmpTable(ipAddr, rCommunity, oidiptab[2], ver, snmpTimeout); 
//	        System.out.println(" List lstIpNetMask="+ lstIpNetMask );
			
			Interface itf = null;
			for(int i = 0; i < lstIpIfIndex.size(); i++)
			{
			    for(int j = 0; j < al.size(); j++)
			    {
			        itf = (Interface)al.get(j);
			        if(itf.getIndex() == Integer.parseInt((String)lstIpIfIndex.get(i))){
			            itf.setIp(Funct.portIpDispose((String)lstIpAddr.get(i)));
			        System.out.println("setip="+lstIpAddr.get(i));}
			    }
			}
		} catch (AppException e1) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured ..getInterface(catch AppException):\n" + e1.getMessage());
			e1.printStackTrace();
		}catch (NumberFormatException e2){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured ..getInterface NumberFormatException:\n" + e2.getMessage());
			e2.printStackTrace();
		}
        
        if(al.size() > 0)
        {
            Interface itf[] = new Interface[al.size()];
            return (Interface[])al.toArray(itf);
        } else
        {
            return null;
        }
    }

    public static void main(String args[])
    {
        Detector dd = new Detector();
        String ipAddr="192.168.122.131";
        String rCommunity = "public";
        int ver =2;       
        
        Device d = new Device();
        d.setIp(ipAddr);
        d.setRCommunity(rCommunity);
        d.setSnmpversion(ver);
        ArrayList al = new ArrayList();        
		Device newdev = dd.fillDeviceBySNMP(d, al);
		System.out.println("device="+d);

		

    }

    USnmp nSnmp;
}
