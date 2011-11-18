package com.ibm.tivoli.cmcc.msg;

import junit.framework.TestCase;

public class MessageTest extends TestCase {
 
  public void testCase1() throws Exception {
    byte[] bs = new byte[]{(byte)0x3C, (byte)0x53, (byte)0x4F, (byte)0x41, (byte)0x50, (byte)0x2D, (byte)0x45, (byte)0x4E, (byte)0x56, 
        (byte)0x3A, (byte)0x45, (byte)0x6E, (byte)0x76, (byte)0x65, (byte)0x6C, (byte)0x6F, (byte)0x70, (byte)0x65, (byte)0x20, (byte)0x78, 
        (byte)0x6D, (byte)0x6C, (byte)0x6E, (byte)0x73, (byte)0x3A, (byte)0x53, (byte)0x4F, (byte)0x41, (byte)0x50, (byte)0x2D, (byte)0x45, 
        (byte)0x4E, (byte)0x56, (byte)0x3D, (byte)0x22, (byte)0x68, (byte)0x74, (byte)0x74, (byte)0x70, (byte)0x3A, (byte)0x2F, (byte)0x2F, 
        (byte)0x73, (byte)0x63, (byte)0x68, (byte)0x65, (byte)0x6D, (byte)0x61, (byte)0x73, (byte)0x2E, (byte)0x78, (byte)0x6D, (byte)0x6C, 
        (byte)0x73, (byte)0x6F, (byte)0x61, (byte)0x70, (byte)0x2E, (byte)0x6F, (byte)0x72, (byte)0x67, (byte)0x2F, (byte)0x73, (byte)0x6F, 
        (byte)0x61, (byte)0x70, (byte)0x2F, (byte)0x65, (byte)0x6E, (byte)0x76, (byte)0x65, (byte)0x6C, (byte)0x6F, (byte)0x70, (byte)0x65, 
        (byte)0x2F, (byte)0x22, (byte)0x3E, (byte)0x3C, (byte)0x53, (byte)0x4F, (byte)0x41, (byte)0x50, (byte)0x2D, (byte)0x45, (byte)0x4E, 
        (byte)0x56, (byte)0x3A, (byte)0x42, (byte)0x6F, (byte)0x64, (byte)0x79, (byte)0x3E, (byte)0x3C, (byte)0x73, (byte)0x61, (byte)0x6D, 
        (byte)0x6C, (byte)0x70, (byte)0x3A, (byte)0x41, (byte)0x63, (byte)0x74, (byte)0x69, (byte)0x76, (byte)0x61, (byte)0x74, (byte)0x65, 
        (byte)0x52, (byte)0x65, (byte)0x71, (byte)0x75, (byte)0x65, (byte)0x73, (byte)0x74, (byte)0x20, (byte)0x78, (byte)0x6D, (byte)0x6C, 
        (byte)0x6E, (byte)0x73, (byte)0x3A, (byte)0x73, (byte)0x61, (byte)0x6D, (byte)0x6C, (byte)0x70, (byte)0x3D, (byte)0x22, (byte)0x75, 
        (byte)0x72, (byte)0x6E, (byte)0x3A, (byte)0x6F, (byte)0x61, (byte)0x73, (byte)0x69, (byte)0x73, (byte)0x3A, (byte)0x6E, (byte)0x61, 
        (byte)0x6D, (byte)0x65, (byte)0x73, (byte)0x3A, (byte)0x74, (byte)0x63, (byte)0x3A, (byte)0x53, (byte)0x41, (byte)0x4D, (byte)0x4C, 
        (byte)0x3A, (byte)0x32, (byte)0x2E, (byte)0x30, (byte)0x3A, (byte)0x70, (byte)0x72, (byte)0x6F, (byte)0x74, (byte)0x6F, (byte)0x63, 
        (byte)0x6F, (byte)0x6C, (byte)0x22, (byte)0x20, (byte)0x78, (byte)0x6D, (byte)0x6C, (byte)0x6E, (byte)0x73, (byte)0x3A, (byte)0x73, 
        (byte)0x61, (byte)0x6D, (byte)0x6C, (byte)0x3D, (byte)0x22, (byte)0x75, (byte)0x72, (byte)0x6E, (byte)0x3A, (byte)0x6F, (byte)0x61, 
        (byte)0x73, (byte)0x69, (byte)0x73, (byte)0x3A, (byte)0x6E, (byte)0x61, (byte)0x6D, (byte)0x65, (byte)0x73, (byte)0x3A, (byte)0x74, 
        (byte)0x63, (byte)0x3A, (byte)0x53, (byte)0x41, (byte)0x4D, (byte)0x4C, (byte)0x3A, (byte)0x32, (byte)0x2E, (byte)0x30, (byte)0x3A, 
        (byte)0x61, (byte)0x73, (byte)0x73, (byte)0x65, (byte)0x72, (byte)0x74, (byte)0x69, (byte)0x6F, (byte)0x6E, (byte)0x22, (byte)0x20, 
        (byte)0x49, (byte)0x44, (byte)0x3D, (byte)0x22, (byte)0x63, (byte)0x61, (byte)0x33, (byte)0x36, (byte)0x38, (byte)0x61, (byte)0x37, 
        (byte)0x34, (byte)0x37, (byte)0x35, (byte)0x39, (byte)0x37, (byte)0x34, (byte)0x36, (byte)0x31, (byte)0x63, (byte)0x62, (byte)0x34, 
        (byte)0x63, (byte)0x33, (byte)0x38, (byte)0x39, (byte)0x37, (byte)0x36, (byte)0x36, (byte)0x35, (byte)0x35, (byte)0x63, (byte)0x61, 
        (byte)0x30, (byte)0x38, (byte)0x64, (byte)0x22, (byte)0x20, (byte)0x49, (byte)0x73, (byte)0x73, (byte)0x75, (byte)0x65, (byte)0x49, 
        (byte)0x6E, (byte)0x73, (byte)0x74, (byte)0x61, (byte)0x6E, (byte)0x74, (byte)0x3D, (byte)0x22, (byte)0x32, (byte)0x30, (byte)0x31, 
        (byte)0x30, (byte)0x2D, (byte)0x30, (byte)0x37, (byte)0x2D, (byte)0x31, (byte)0x33, (byte)0x54, (byte)0x30, (byte)0x35, (byte)0x3A, 
        (byte)0x34, (byte)0x30, (byte)0x3A, (byte)0x31, (byte)0x31, (byte)0x3A, (byte)0x33, (byte)0x33, (byte)0x37, (byte)0x5A, (byte)0x22, 
        (byte)0x20, (byte)0x56, (byte)0x65, (byte)0x72, (byte)0x73, (byte)0x69, (byte)0x6F, (byte)0x6E, (byte)0x3D, (byte)0x22, (byte)0x32, 
        (byte)0x2E, (byte)0x30, (byte)0x22, (byte)0x3E, (byte)0x20, (byte)0x3C, (byte)0x73, (byte)0x61, (byte)0x6D, (byte)0x6C, (byte)0x3A, 
        (byte)0x49, (byte)0x73, (byte)0x73, (byte)0x75, (byte)0x65, (byte)0x72, (byte)0x3E, (byte)0x68, (byte)0x74, (byte)0x74, (byte)0x70, 
        (byte)0x3A, (byte)0x2F, (byte)0x2F, (byte)0x31, (byte)0x30, (byte)0x2E, (byte)0x31, (byte)0x2E, (byte)0x32, (byte)0x34, (byte)0x39, 
        (byte)0x2E, (byte)0x34, (byte)0x36, (byte)0x3A, (byte)0x37, (byte)0x30, (byte)0x30, (byte)0x31, (byte)0x2F, (byte)0x73, (byte)0x73, 
        (byte)0x6F, (byte)0x2F, (byte)0x53, (byte)0x53, (byte)0x4F, (byte)0x3C, (byte)0x2F, (byte)0x73, (byte)0x61, (byte)0x6D, (byte)0x6C, 
        (byte)0x3A, (byte)0x49, (byte)0x73, (byte)0x73, (byte)0x75, (byte)0x65, (byte)0x72, (byte)0x3E, (byte)0x20, (byte)0x3C, (byte)0x73, 
        (byte)0x61, (byte)0x6D, (byte)0x6C, (byte)0x3A, (byte)0x4E, (byte)0x61, (byte)0x6D, (byte)0x65, (byte)0x49, (byte)0x44, (byte)0x20, 
        (byte)0x46, (byte)0x6F, (byte)0x72, (byte)0x6D, (byte)0x61, (byte)0x74, (byte)0x3D, (byte)0x22, (byte)0x75, (byte)0x72, (byte)0x6E, 
        (byte)0x3A, (byte)0x6F, (byte)0x61, (byte)0x73, (byte)0x69, (byte)0x73, (byte)0x3A, (byte)0x6E, (byte)0x61, (byte)0x6D, (byte)0x65, 
        (byte)0x73, (byte)0x3A, (byte)0x74, (byte)0x63, (byte)0x3A, (byte)0x53, (byte)0x41, (byte)0x4D, (byte)0x4C, (byte)0x3A, (byte)0x32, 
        (byte)0x2E, (byte)0x30, (byte)0x3A, (byte)0x6E, (byte)0x61, (byte)0x6D, (byte)0x65, (byte)0x69, (byte)0x64, (byte)0x66, (byte)0x6F, 
        (byte)0x72, (byte)0x6D, (byte)0x61, (byte)0x74, (byte)0x3A, (byte)0x74, (byte)0x72, (byte)0x61, (byte)0x6E, (byte)0x73, (byte)0x69, 
        (byte)0x65, (byte)0x6E, (byte)0x74, (byte)0x22, (byte)0x3E, (byte)0x33, (byte)0x37, (byte)0x61, (byte)0x66, (byte)0x63, (byte)0x39, 
        (byte)0x66, (byte)0x61, (byte)0x35, (byte)0x63, (byte)0x66, (byte)0x37, (byte)0x34, (byte)0x31, (byte)0x32, (byte)33, (byte)0x61, 
        (byte)0x62, (byte)0x61, (byte)0x63, (byte)0x63, (byte)0x63, (byte)0x36, (byte)0x62, (byte)0x66, (byte)0x30, (byte)0x64, (byte)0x38, 
        (byte)0x32, (byte)0x61, (byte)0x36, (byte)0x38, (byte)0x3C, (byte)0x2F, (byte)0x73, (byte)0x61, (byte)0x6D, (byte)0x6C, (byte)0x3A, 
        (byte)0x4E, (byte)0x61, (byte)0x6D, (byte)0x65, (byte)0x49, (byte)0x44, (byte)0x3E, (byte)0x20, (byte)0x3C, (byte)0x2F, (byte)0x73, 
        (byte)0x61, (byte)0x6D, (byte)0x6C, (byte)0x70, (byte)0x3A, (byte)0x41, (byte)0x63, (byte)0x74, (byte)0x69, (byte)0x76, (byte)0x61, 
        (byte)0x74, (byte)0x65, (byte)0x52, (byte)0x65, (byte)0x71, (byte)0x75, (byte)0x65, (byte)0x73, (byte)0x74, (byte)0x3E, (byte)0x3C, 
        (byte)0x2F, (byte)0x53, (byte)0x4F, (byte)0x41, (byte)0x50, (byte)0x2D, (byte)0x45, (byte)0x4E, (byte)0x56, (byte)0x3A, (byte)0x42, 
        (byte)0x6F, (byte)0x64, (byte)0x79, (byte)0x3E, (byte)0x3C, (byte)0x2F, (byte)0x53, (byte)0x4F, (byte)0x41, (byte)0x50, (byte)0x2D, 
        (byte)0x45, (byte)0x4E, (byte)0x56, (byte)0x3A, (byte)0x45, (byte)0x6E, (byte)0x76, (byte)0x65, (byte)0x6C, (byte)0x6F, (byte)0x70, 
        (byte)0x65, (byte)0x3E};
    System.out.println(new String());
  }
}
