package ibm.com.ncs.util;

import com.ibm.ncs.util.IPAddr;

import junit.framework.TestCase;

public class IPAddrTest extends TestCase {

	public void testIp2Long() {
		String ipaddr = "80.15.243.141";
		long ipdecode = IPAddr.encode(ipaddr);
		System.out.println(ipaddr +" = "+ipdecode);
	}

	public void testEncode() {
		fail("Not yet implemented"); // TODO
	}

	public void testDecode() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetMinIp() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetMaxIp() {
		fail("Not yet implemented"); // TODO
	}

}
