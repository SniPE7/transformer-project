
package com.ibm.ncs.util.test;


// Referenced classes of package com.eccom.ncs.data:
//            Device

public class Interface
{

    public Interface()
    {
        id = "-1";
        index = -1;
        name = "";
        ip = "";
        mac = "";
        leid = "-1";
        d = new Device();
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getMac()
    {
        return mac;
    }

    public void setMac(String mac)
    {
        this.mac = mac;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Device getD()
    {
        return d;
    }

    public void setD(Device d)
    {
        this.d = d;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLeid()
    {
        return leid;
    }

    public void setLeid(String leid)
    {
        this.leid = leid;
    }

    public String id;
    public int index;
    public String name;
    public String ip;
    public String mac;
    public String leid;
    public Device d;
    
    public String toString(){
    	StringBuffer ret = new StringBuffer();
    	ret.append("com.ibm.ncs.util.test.Interface: ");
    	ret.append(" id="+id);
    	ret.append(", index="+index);
    	ret.append(", name="+name);
    	ret.append(", ip="+ip);
    	ret.append(", mac="+mac);
    	ret.append(", leid="+leid);
    	ret.append(", d="+d);   	
    	
    	return ret.toString();
    }
    
    
}
