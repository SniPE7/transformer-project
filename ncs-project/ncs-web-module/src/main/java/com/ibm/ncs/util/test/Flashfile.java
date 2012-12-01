
package com.ibm.ncs.util.test;


public class Flashfile
{

    public Flashfile(String n, long s)
    {
        name = "";
        size = -1L;
        name = n;
        size = s;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public String name;
    public long size;
    
    public String toString(){
    	StringBuffer ret = new StringBuffer();
    	ret.append("com.ibm.ncs.util.test.Flashfile ");
    	ret.append(" name="+name);
    	ret.append(", size="+size);
    	
    	return ret.toString();
    }
    
}
