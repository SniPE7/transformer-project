package ibm.com.ncs.util;

import com.ibm.ncs.util.AppException;
import com.ibm.ncs.util.SnmpUtil;

import junit.framework.TestCase;

public class SnmpUtilTest extends TestCase {

	public final void testSnmpGet() {
		//fail("Not yet implemented"); 
		String mibv;
		//SnmpManager snmpmgr = new SnmpManager();
		try {
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.2.2.1");
			System.out.println("1.3.6.1.2.1.1.2.0="+mibv);
//			assertEquals("1.3.6.1.4.1.8072.3.2.10", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.1.5.0");
			System.out.println("1.3.6.1.2.1.1.5.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.1.1.0");
			System.out.println("1.3.6.1.2.1.1.1.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.4.1.9.3.6.6.0");
			System.out.println("1.3.6.1.4.1.9.3.6.6.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.4.1.9.3.6.7.0");
			System.out.println("1.3.6.1.4.1.9.3.6.7.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.47.1.1.1.1.9.1");
			System.out.println("1.3.6.1.2.1.47.1.1.1.1.9.1="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.4.1.9.3.6.3.0");
			System.out.println("1.3.6.1.4.1.9.3.6.3.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);

			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.2.1.0");
			System.out.println("1.3.6.1.2.1.2.1.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			//---------------------------------------------------------------------------------
			int snmpversion = 1 ;
			System.out.println("-------------snmp version: V2c =1   -----------------");
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.1.2.0", snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.1.2.0="+mibv);
//			assertEquals("1.3.6.1.4.1.8072.3.2.10", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.1.5.0", snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.1.5.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.1.1.0", snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.1.1.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.4.1.9.3.6.6.0", snmpversion, 500l);
			System.out.println("1.3.6.1.4.1.9.3.6.6.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.4.1.9.3.6.7.0", snmpversion, 500l);
			System.out.println("1.3.6.1.4.1.9.3.6.7.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.47.1.1.1.1.9.1", snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.47.1.1.1.1.9.1="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.4.1.9.3.6.3.0", snmpversion, 500l);
			System.out.println("1.3.6.1.4.1.9.3.6.3.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);

			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.2.1.0", snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.2.1.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
		/*	
			//---------------------------------------------------------------------------------
			snmpversion = 3 ;
			System.out.println("-------------snmp version: V3 =3   -----------------");
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.1.2.0", snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.1.2.0="+mibv);
//			assertEquals("1.3.6.1.4.1.8072.3.2.10", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.1.5.0", snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.1.5.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.1.1.0", snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.1.1.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.4.1.9.3.6.6.0", snmpversion, 500l);
			System.out.println("1.3.6.1.4.1.9.3.6.6.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.4.1.9.3.6.7.0", snmpversion, 500l);
			System.out.println("1.3.6.1.4.1.9.3.6.7.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.47.1.1.1.1.9.1", snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.47.1.1.1.1.9.1="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.4.1.9.3.6.3.0", snmpversion, 500l);
			System.out.println("1.3.6.1.4.1.9.3.6.3.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);

			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", "1.3.6.1.2.1.2.1.0", snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.2.1.0="+mibv);
//			assertEquals("ygsuse11-9", mibv);
			*/
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* test getBulk */
		try {

			
			//---------------------------------------------------------------------------------
			int snmpversion = 1 ;
			System.out.println("-------------snmp getbulk version: V2c =1   -----------------");
			String oids[] = new String [] {"1.3.6.1.2.1.2.1","1.3.6.1.2.1.2.2","1.3.6.1.2.1.2.2.1.2.0","1.3.6.1.2.1.2.2.1.6.0"};
			mibv = SnmpUtil.snmpBulkGet("10.10.10.86", "public", oids, snmpversion, 500l);
			System.out.println("1.3.6.1.2.1.2.2.1.1="+mibv);
//			assertEquals("1.3.6.1.4.1.8072.3.2.10", mibv);
			

			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
