// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tool.java

package com.ibm.ncs.util.test;


public class Tool
{

    public Tool()
    {
    }

    public int getIfStatus(int adminStatus, int operStatus)
    {
        int ret = 0;
        switch(adminStatus + operStatus)
        {
        case 2: // '\002'
            ret = 1;
            break;

        case 3: // '\003'
            ret = 2;
            break;

        case 4: // '\004'
            ret = 3;
            break;

        case 6: // '\006'
            ret = 4;
            break;

        case 5: // '\005'
        default:
            ret = 0;
            break;
        }
        return ret;
    }
}
