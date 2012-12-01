// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RefString.java

package com.ibm.ncs.util.ectest;


public class RefString
{

    public RefString()
    {
        m_data = null;
    }

    public RefString(String sData)
    {
        m_data = sData;
    }

    public String getString()
    {
        return m_data;
    }

    public void setString(String sData)
    {
        m_data = sData;
    }

    public String m_data;
}
