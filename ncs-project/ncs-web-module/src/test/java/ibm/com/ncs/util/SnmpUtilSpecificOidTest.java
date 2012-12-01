package ibm.com.ncs.util;

import com.ibm.ncs.util.AppException;
import com.ibm.ncs.util.SnmpUtil;

import junit.framework.TestCase;

public class SnmpUtilSpecificOidTest extends TestCase {

	public final void testSnmpGet() {
		//fail("Not yet implemented"); 
		String mibv,oid;
		//SnmpManager snmpmgr = new SnmpManager();
		try {
			//interface...by v2c 
			int snmpversion = 1 ;
			System.out.println("-------------snmp version: V2c =1   -----------------");
			oid = "1.3.6.1.2.1.2.2.1.1.0";
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.2.2.1.2.0";
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);

			oid = "1.3.6.1.2.1.2.2.1.6.0";
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			

			oid = "1.3.6.1.2.1.2.2.1.1";
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.2.2.1.2";
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.2.2.1.6";
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.4.20.1.2";
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.4.20.1.1";
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.4.20.1.3";
			mibv = SnmpUtil.snmpGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			System.out.println("\n-------------snmp version: V2c =1  getBulk() -----------------");
			oid = "1.3.6.1.2.1.2.2.1.1.0";
			mibv = SnmpUtil.snmpBulkGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.2.2.1.2.0";
			mibv = SnmpUtil.snmpBulkGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);

			oid = "1.3.6.1.2.1.2.2.1.6.0";
			mibv = SnmpUtil.snmpBulkGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			

			oid = "1.3.6.1.2.1.2.2.1.1";
			mibv = SnmpUtil.snmpBulkGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.2.2.1.2";
			mibv = SnmpUtil.snmpBulkGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.2.2.1.6";
			mibv = SnmpUtil.snmpBulkGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.4.20.1.2";
			mibv = SnmpUtil.snmpBulkGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.4.20.1.1";
			mibv = SnmpUtil.snmpBulkGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
			oid = "1.3.6.1.2.1.4.20.1.3";
			mibv = SnmpUtil.snmpBulkGet("10.10.10.86", "public", oid, snmpversion, 500l);
			System.out.println(oid+"\t\t="+mibv);
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
