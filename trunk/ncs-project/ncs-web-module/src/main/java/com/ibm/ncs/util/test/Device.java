

package com.ibm.ncs.util.test;

import java.util.ArrayList;





public class Device
{

    private static int hashCode(Object array[])
    {
        int PRIME = 31;
        if(array == null)
            return 0;
        int result = 1;
        for(int index = 0; index < array.length; index++)
            result = 31 * result + (array[index] != null ? array[index].hashCode() : 0);

        return result;
    }

    public Device()
    {
        id = -1L;
        name = "";
        nameb = "";
        ip = "";
        rCommunity = "";
        admin = "";
        phone = "";
        snmpversion = -1;
        srno = "";
        equippolicy = "";
        periodpolicy = "";
        softwareversion = "";
        serial = "";
        nvramsize = 0L;
        nvramunit = "";
        ramsize = 0L;
        ramunit = "";
        flashsize = 0L;
        flashunit = "";
        desc = "";
        devicetype = new DeviceType();
//        service = new Service();
//        devicegroup = new DeviceGroup();
    }

    public String getflashfilenameDBStr()
    {
        return getstr("Name");
    }

    public String getflashfilesizeDBStr()
    {
        return getstr("Size");
    }

    private String getstr(String s)
    {
        String temp = "<m_FlashFile " + s;
        if(flashfiles != null)
        {
            for(int i = 0; i < flashfiles.length; i++)
                temp = temp + " Attr" + i + "=\"" + (s.equals("Name") ? flashfiles[i].name : (new StringBuffer(String.valueOf(flashfiles[i].size))).toString()) + "\"";

        }
        temp = temp + "/>";
        return temp;
    }

    public String getflashfilenamedaoStr()
    {
        return getdaostr("Name");
    }

    public String getflashfilesizedaoStr()
    {
        return getdaostr("Size");
    }

    private String getdaostr(String s)
    {
        String temp = "";
        if(flashfiles != null)
        {
            for(int i = 0; i < flashfiles.length; i++)
            {
                temp = temp + s + i + "=" + (s.equals("Name") ? flashfiles[i].name : (new StringBuffer(String.valueOf(flashfiles[i].size))).toString()) + " ";
                if(i < flashfiles.length - 1)
                    temp = temp + "|";
            }

        }
        return temp;
    }

    public void setflashfiles(String names, String sizes)
    {
        String ns[];
        String ss[];
        ns = names.split(" Attr");
        ss = sizes.split(" Attr");
        if(ns.length != ss.length || ns.length <= 1)
            return;
        try
        {
            ArrayList al = new ArrayList();
            for(int i = 0; i < ns.length - 1; i++)
                try
                {
                    Flashfile ff = new Flashfile(ns[i + 1].substring(ns[i + 1].indexOf("\"") + 1, ns[i + 1].lastIndexOf("\"")), Long.parseLong(ss[i + 1].substring(ss[i + 1].indexOf("\"") + 1, ss[i + 1].lastIndexOf("\""))));
                    al.add(ff);
                }
                catch(NumberFormatException numberformatexception) { }

            flashfiles = new Flashfile[al.size()];
            for(int i = 0; i < flashfiles.length; i++)
                flashfiles[i] = (Flashfile)al.get(i);

        }
        catch(Exception e)
        {
            flashfiles = new Flashfile[0];
        }
        return;
    }

    public String getAdmin()
    {
        return admin;
    }

    public void setAdmin(String admin)
    {
        this.admin = admin;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getEquippolicy()
    {
        return equippolicy;
    }

    public void setEquippolicy(String equippolicy)
    {
        this.equippolicy = equippolicy;
    }

    public DeviceType getDevicetype()
    {
        return devicetype;
    }

    public void setDevicetype(DeviceType et)
    {
        devicetype = et;
    }

    public Flashfile[] getFlashfiles()
    {
        return flashfiles;
    }

    public void setFlashfiles(Flashfile flashfiles[])
    {
        this.flashfiles = flashfiles;
    }

    public long getFlashsize()
    {
        return flashsize;
    }

    public long getrealflashsize()
    {
        return getreal(flashsize, flashunit);
    }

    public void setFlashsize(long flashsize)
    {
        this.flashsize = flashsize;
    }

    public String getFlashunit()
    {
        return flashunit;
    }

    public void setFlashunit(String flashunit)
    {
        this.flashunit = flashunit;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Interface[] getInterfaces()
    {
        return interfaces;
    }

    public void setInterfaces(Interface interfaces[])
    {
        this.interfaces = interfaces;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNameb()
    {
        return nameb;
    }

    public void setNameb(String nameb)
    {
        this.nameb = nameb;
    }

    public long getrealnvramsize()
    {
        return getreal(nvramsize, nvramunit);
    }

    public long getNvramsize()
    {
        return nvramsize;
    }

    public void setNvramsize(long nvramsize)
    {
        this.nvramsize = nvramsize;
    }

    public String getNvramunit()
    {
        return nvramunit;
    }

    public void setNvramunit(String nvramunit)
    {
        this.nvramunit = nvramunit;
    }

    public String getPeriodpolicy()
    {
        return periodpolicy;
    }

    public void setPeriodpolicy(String periodpolicy)
    {
        this.periodpolicy = periodpolicy;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public long getRamsize()
    {
        return ramsize;
    }

    public long getrealramsize()
    {
        return getreal(ramsize, ramunit);
    }

    public void setRamsize(long ramsize)
    {
        this.ramsize = ramsize;
    }

    public String getRamunit()
    {
        return ramunit;
    }

    public void setRamunit(String ramunit)
    {
        this.ramunit = ramunit;
    }

//    public Service getService()
//    {
//        return service;
//    }
//
//    public void setService(Service s)
//    {
//        service = s;
//    }

    public String getSerial()
    {
        return serial;
    }

    public void setSerial(String serial)
    {
        this.serial = serial;
    }

    public int getSnmpversion()
    {
        return snmpversion;
    }

    public void setSnmpversion(int snmpversion)
    {
        this.snmpversion = snmpversion;
    }

    public String getSoftwareversion()
    {
        return softwareversion;
    }

    public void setSoftwareversion(String softwareversion)
    {
        this.softwareversion = softwareversion;
    }

    public String getSrno()
    {
        return srno;
    }

    public void setSrno(String srno)
    {
        this.srno = srno;
    }

    public String getRcommunity()
    {
        return rCommunity;
    }

    public void setRCommunity(String community)
    {
        rCommunity = community;
    }

//    public void setDevicegroup(DeviceGroup equipgroup)
//    {
//        devicegroup = equipgroup;
//    }
//
//    /**
//     * @return
//     */
//    public DeviceGroup getDevicegroup()
//    {
//        return devicegroup;
//    }

    public long id;
    public String name;
    public String nameb;
    public String ip;
    public String rCommunity;
    public DeviceType devicetype;
    public String admin;
    public String phone;
    public int snmpversion;
    public String srno;
//    public Service service;
    public String equippolicy;
    public String periodpolicy;
    public String softwareversion;
    public String serial;
    public long nvramsize;
    public String nvramunit;
//    public DeviceGroup devicegroup;
    public long ramsize;
    public String ramunit;
    public long flashsize;
    public String flashunit;
    public String desc;
    public Interface interfaces[];
    public Flashfile flashfiles[];
    
    
    

    public static long getreal(long l, String s)
    {
        if(s.equals("K"))
            return l / 1024L;
        if(s.equals("M"))
            return l / 1024L / 1024L;
        if(s.equals("G"))
            return l / 1024L / 1024L / 1024L;
        if(s.equals("T"))
            return l / 1024L / 1024L / 1024L / 1024L;
        else
            return l;
    }

    public static long setreal(long l, String s)
    {
        if(s.equals("K"))
            return l * 1024L;
        if(s.equals("M"))
            return l * 1024L * 1024L;
        if(s.equals("G"))
            return l * 1024L * 1024L * 1024L;
        if(s.equals("T"))
            return l * 1024L * 1024L * 1024L * 1024L;
        else
            return l;
    }

    public static float setreal(float l, String s)
    {
        if(s.equals("K"))
            return l * 1024F;
        if(s.equals("M"))
            return l * 1024F * 1024F;
        if(s.equals("G"))
            return l * 1024F * 1024F * 1024F;
        if(s.equals("T"))
            return l * 1024F * 1024F * 1024F * 1024F;
        else
            return l;
    }
    
    public String toString(){        
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.util.test.Device: " );
		ret.append( "   id=" + id );
		ret.append( ",  name=" + name );
		ret.append( ",  nameb=" +nameb);
		ret.append( ",  ip="+ip);
		ret.append( ",  rCommunity="+rCommunity);
		ret.append( ",  admin="+admin);
		ret.append( ",  phone="+phone);
		ret.append( ",  snmpversion="+ snmpversion);
		ret.append( ",  srno="+srno);
		ret.append( ",  equippolicy="+equippolicy);
		ret.append( ",  periodpolicy="+periodpolicy);
		ret.append( ",  softwareversion="+softwareversion);
		ret.append( ",  serial="+serial);
		ret.append( ",  nvramsize="+nvramsize);
		ret.append( ",  nvramunit="+nvramunit);
		ret.append( ",  ramsize="+ramsize);
		ret.append( ",  ramunit="+ramunit);
		ret.append( ",  flashsize="+flashsize);
		ret.append( ",  flashunit="+flashunit);
		ret.append( ",  desc="+desc);
		ret.append( ",  \ninterfaces[]="+interfaces);
		if(interfaces!=null){
			for (int i=0; i<interfaces.length;i++){
				ret.append("interface[i="+i+"]"+interfaces[i].toString());
			}
		}
		ret.append( ",  \nflashfiles[]="+flashfiles);
		if(flashfiles!=null){
			for (int i=0; i<flashfiles.length;i++){
				ret.append("interface[i="+i+"]"+flashfiles[i].toString());
			}
		}
		ret.append( ",  \ndevicetype="+devicetype);
		return ret.toString();
        
    }
    
}
