
package com.ibm.ncs.util.test;


public class DeviceType
{

    public DeviceType()
    {
        id = -1;
        type = "";
        subtype = "";
        model = "";
        manuf = "";
        objectid = "";
        desc = "";
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getManuf()
    {
        return manuf;
    }

    public void setManuf(String manuf)
    {
        this.manuf = manuf;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getObjectid()
    {
        return objectid;
    }

    public void setObjectid(String objectid)
    {
        this.objectid = objectid;
    }

    public String getSubtype()
    {
        return subtype;
    }

    public void setSubtype(String subtype)
    {
        this.subtype = subtype;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int id;
    public String type;
    public String subtype;
    public String model;
    public String manuf;
    public String objectid;
    public String desc;
    
    public String toString(){
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.util.test.DeviceType: " );
		ret.append( "   id=" + id );
		ret.append( ",  type=" + type );
		ret.append( ",  model=" +model);
		ret.append( ",  manuf="+manuf);
		ret.append( ",  objectid="+objectid);
		ret.append( ",  desc="+desc);
		return ret.toString();
        
    }
    
    
}
