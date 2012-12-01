// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IPModel.java

package com.ibm.ncs.util.ectest;

import java.io.Serializable;

public class IPModel
    implements Serializable
{

    public IPModel()
    {
    }

    public String ip;
    public String mask;
    public long minIpdecode;
    public long maxIpdecode;
    public String desc;
}
