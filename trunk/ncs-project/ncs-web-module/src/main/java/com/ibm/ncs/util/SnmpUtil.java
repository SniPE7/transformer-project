package com.ibm.ncs.util;


import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.PDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

/**
 * SNMP Manager utility class
 */
public class SnmpUtil {

	//private static final Log log = LogFactory.getLog(SnmpUtil.class);
	public static Logger log = Logger.getLogger(SnmpUtil.class);
	private static int snmpversion = 0; // SNMP  SnmpConstants.[ version1 = 0; version2c = 1; version3 = 3;]
	
	private static String snmpprotocol = "udp"; // Protocol of monitoring
	
	private static String snmpport = "161"; // port of the monitoring
	
	private static long snmptimeout = 500l;  // timeout in milliseconds.
		
	/**
	 * retrieve SNMP oid value in default snmp ver= V1
	 * 
	 * @param ipAddress  target IP address
	 * @param community  
	 * @param oid 
	 * @return String returned value
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static String snmpGet(String ipAddress, String community, String oid) throws AppException {
		String resultStat = null; // returned value
		
		StringBuffer address = new StringBuffer();
		address.append(snmpprotocol);
		address.append(":");
		address.append(ipAddress);
		address.append("/");
		address.append(snmpport);
		
//		Address targetAddress = GenericAddress.parse(protocol + ":" + ipAddress + "/" + port);
		Address targetAddress = GenericAddress.parse(address.toString());
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(oid)));
		//pdu.add(new VariableBinding(new OID(new int[]{1,3,6,1,2,1,1,5,0})));
		pdu.setType(PDU.GET);

		// CommunityTarget
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		//target.setVersion(SnmpConstants.version1);// SnmpConstants.[ version1 = 0; version2c = 1; version3 = 3;]
		target.setVersion(snmpversion);
		target.setTimeout(snmptimeout);
		target.setRetries(2);

		DefaultUdpTransportMapping udpTransportMap = null;
		Snmp snmp = null;
		try {
			// send
			udpTransportMap = new DefaultUdpTransportMapping();
			udpTransportMap.listen();
			snmp = new Snmp(udpTransportMap);
			ResponseEvent response = snmp.send(pdu, target);
			PDU responsePdu = response.getResponse();

			if (responsePdu == null) {
				log.info(ipAddress + ": Request timed out.");
			} else {
				int errorStatus = responsePdu.getErrorStatus();
				String errorStatusText = responsePdu.getErrorStatusText();
				if (  errorStatus != 0 ){
					log.info(ipAddress + ": "+ responsePdu);  }
				Object obj = responsePdu.getVariableBindings().firstElement();
				VariableBinding variable = (VariableBinding) obj;
				resultStat = variable.getVariable().toString();
				//System.out.println(ipAddress+"] ; responsPdu["+responsePdu+"] ; ");
			}
		} catch (Exception e) {
			throw new AppException("Exception on SNMP get.", e);
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException e) {
					snmp = null;
				}
			}
			if (udpTransportMap != null) {
				try {
					udpTransportMap.close();
				} catch (IOException e) {
					udpTransportMap = null;
				}
			}
		}
		
		if (log.isInfoEnabled()) {
			log.info("IP:" + ipAddress + " resultStat:" + resultStat);
		}
		
		return resultStat;
	}
	
	/**
	 * retrieve SNMP oid valueֵ
	 * 
	 * @param ipAddress  target IP address
	 * @param community  
	 * @param oid 
	 * @param snmpVersion  SnmpConstants.[ version1 = 0; version2c = 1; version3 = 3;]
	 * @param snmpTimeout  millisecond for timeout
	 * @return String returned value
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static String snmpGet(String ipAddress, String community, String oid, int snmpVersion , long snmpTimeout ) throws AppException {
		String resultStat = null; // returned value
		
		StringBuffer address = new StringBuffer();
		address.append(snmpprotocol);
		address.append(":");
		address.append(ipAddress);
		address.append("/");
		address.append(snmpport);
		
//		Address targetAddress = GenericAddress.parse(protocol + ":" + ipAddress + "/" + port);
		Address targetAddress = GenericAddress.parse(address.toString());
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(oid)));
		//pdu.add(new VariableBinding(new OID(new int[]{1,3,6,1,2,1,1,5,0})));
		pdu.setType(PDU.GET);

		// CommunityTarget
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		//target.setVersion(SnmpConstants.version1);// SnmpConstants.[ version1 = 0; version2c = 1; version3 = 3;]
		target.setVersion(snmpVersion);
		target.setTimeout(snmpTimeout);
		target.setRetries(3);

		DefaultUdpTransportMapping udpTransportMap = null;
		Snmp snmp = null;
		try {
			// send
			udpTransportMap = new DefaultUdpTransportMapping();
			udpTransportMap.listen();
			snmp = new Snmp(udpTransportMap);
			ResponseEvent response = snmp.send(pdu, target);
			
			//ResponseEvent response = snmp.get(pdu, target);
			PDU responsePdu = response.getResponse();

			if (responsePdu == null) {
				log.info(ipAddress + ": Request timed out.");
			} else {
				int errorStatus = responsePdu.getErrorStatus();
				String errorStatusText = responsePdu.getErrorStatusText();
				if (  errorStatus != 0 ){
					log.info(ipAddress + ": "+ responsePdu);  }
				Object obj = responsePdu.getVariableBindings().firstElement();
				VariableBinding variable = (VariableBinding) obj;
				resultStat = variable.getVariable().toString();
				//System.out.println(ipAddress+" : responsePdu["+responsePdu+"] ; ");
			}
		} catch (Exception e) {
			throw new AppException("Exception on SNMP get.", e);
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException e) {
					snmp = null;
				}
			}
			if (udpTransportMap != null) {
				try {
					udpTransportMap.close();
				} catch (IOException e) {
					udpTransportMap = null;
				}
			}
		}
		
		if (log.isInfoEnabled()) {
			log.info("IP:" + ipAddress + " resultStat:" + resultStat);
		}
		
		return resultStat;
	}
	
	public static int testSnmp(String ipaddr, String rCommunity, int ver, long timeout){
		
		String ret = "";
		String oid = "1.3.6.1.2.1.1.3.0"; //sysuptime
		System.out.println("SnmpUtil - testSnmp "+ipaddr+", "+rCommunity+", "+oid+", "+ver+", "+timeout);
		try {
			ret = SnmpUtil.snmpGet(ipaddr, rCommunity, oid, ver, timeout);
		} catch (AppException e) {
			//System.out.println("SnmpUtil - testSnmp "+ipaddr+", "+rCommunity+", "+oid+", "+ver+", "+timeout);
			e.printStackTrace();
			
		}
		int res  = (null==ret||"".equals(ret))?0:1;
		//System.out.println("ret="+ret+" ; success="+res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public static String snmpBulkGet(String ipAddress, String community, String oid, int snmpVersion , long snmpTimeout ) throws AppException {
		String resultStat = ""; // returned value
		
		StringBuffer address = new StringBuffer();
		address.append(snmpprotocol);
		address.append(":");
		address.append(ipAddress);
		address.append("/");
		address.append(snmpport);
		
//		Address targetAddress = GenericAddress.parse(protocol + ":" + ipAddress + "/" + port);
		Address targetAddress = GenericAddress.parse(address.toString());
		PDU pdu = new PDU();
		
			pdu.add(new VariableBinding(new OID(oid)));
		
		//pdu.add(new VariableBinding(new OID(new int[]{1,3,6,1,2,1,1,5,0})));
		pdu.setType(PDU.GETBULK);

		// CommunityTarget
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		//target.setVersion(SnmpConstants.version1);// SnmpConstants.[ version1 = 0; version2c = 1; version3 = 3;]
		target.setVersion(snmpVersion);
		target.setTimeout(snmpTimeout);
		target.setRetries(1);

		DefaultUdpTransportMapping udpTransportMap = null;
		Snmp snmp = null;
		try {
			// send
			udpTransportMap = new DefaultUdpTransportMapping();
			udpTransportMap.listen();
			snmp = new Snmp(udpTransportMap);
			ResponseEvent response = snmp.send(pdu, target);
			
			//ResponseEvent response = snmp.getBulk(pdu, target);
			PDU responsePdu = response.getResponse();

			if (responsePdu == null) {
				log.info(ipAddress + ": Request timed out.");
			} else {
				int errorStatus = responsePdu.getErrorStatus();
				String errorStatusText = responsePdu.getErrorStatusText();
				if (  errorStatus != 0 ){
					log.info(ipAddress + ": "+ responsePdu);  }
				//Vector<VariableBinding> revBindings = responsePdu.getVariableBindings();
				Vector<VariableBinding> variable =  responsePdu.getVariableBindings();
				System.out.println("vector variable="+variable);
				for (int i=0; i<variable.size();i++){
					VariableBinding vbs = variable.elementAt(i);
					resultStat += variable.elementAt(i).getVariable().toString();
					System.out.println(vbs.getOid()+" value="+ vbs.getVariable());
				}
				System.out.println(ipAddress+" : responsePdu["+responsePdu+"] ; ");
				
			}
		} catch (Exception e) {
			throw new AppException("Exception on SNMP getbulk.", e);
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException e) {
					snmp = null;
				}
			}
			if (udpTransportMap != null) {
				try {
					udpTransportMap.close();
				} catch (IOException e) {
					udpTransportMap = null;
				}
			}
		}
		
		if (log.isInfoEnabled()) {
			log.info("IP:" + ipAddress + " resultStat:" + resultStat);
		}
		
		return resultStat;
	}
	
	@SuppressWarnings("unchecked")
	public static String snmpBulkGet(String ipAddress, String community, String[] oids, int snmpVersion , long snmpTimeout ) throws AppException {
		String resultStat = ""; // returned value
		
		StringBuffer address = new StringBuffer();
		address.append(snmpprotocol);
		address.append(":");
		address.append(ipAddress);
		address.append("/");
		address.append(snmpport);
		
//		Address targetAddress = GenericAddress.parse(protocol + ":" + ipAddress + "/" + port);
		Address targetAddress = GenericAddress.parse(address.toString());
		PDU pdu = new PDU();
		for (int i=0 ; i< oids.length;i++){
			pdu.add(new VariableBinding(new OID(oids[i])));
		}
		//pdu.add(new VariableBinding(new OID(new int[]{1,3,6,1,2,1,1,5,0})));
		pdu.setType(PDU.GETBULK);

		// CommunityTarget
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		//target.setVersion(SnmpConstants.version1);// SnmpConstants.[ version1 = 0; version2c = 1; version3 = 3;]
		target.setVersion(snmpVersion);
		target.setTimeout(snmpTimeout);
		target.setRetries(1);

		DefaultUdpTransportMapping udpTransportMap = null;
		Snmp snmp = null;
		try {
			// send
			udpTransportMap = new DefaultUdpTransportMapping();
			udpTransportMap.listen();
			snmp = new Snmp(udpTransportMap);
			//ResponseEvent response = snmp.send(pdu, target);
			
			ResponseEvent response = snmp.getBulk(pdu, target);
			PDU responsePdu = response.getResponse();

			if (responsePdu == null) {
				log.info(ipAddress + ": Request timed out.");
			} else {
				int errorStatus = responsePdu.getErrorStatus();
				String errorStatusText = responsePdu.getErrorStatusText();
				if (  errorStatus != 0 ){
					log.info(ipAddress + ": "+ responsePdu);  }
				//Vector<VariableBinding> revBindings = responsePdu.getVariableBindings();
				Vector<VariableBinding> variable =  responsePdu.getVariableBindings();
				System.out.println("vector variable="+variable);
				for (int i=0; i<variable.size();i++){
					VariableBinding vbs = variable.elementAt(i);
					resultStat += variable.elementAt(i).getVariable().toString();
					System.out.println(vbs.getOid()+" value="+ vbs.getVariable());
				}
				System.out.println(ipAddress+" : responsePdu["+responsePdu+"] ; ");
				
			}
		} catch (Exception e) {
			throw new AppException("Exception on SNMP getbulk.", e);
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException e) {
					snmp = null;
				}
			}
			if (udpTransportMap != null) {
				try {
					udpTransportMap.close();
				} catch (IOException e) {
					udpTransportMap = null;
				}
			}
		}
		
		if (log.isInfoEnabled()) {
			log.info("IP:" + ipAddress + " resultStat:" + resultStat);
		}
		
		return resultStat;
	}
	
	public static List snmpTable(String ipAddress, String community, String oid, int snmpVersion, long snmpTimeout) throws AppException {
		//String resultStat = null; // returned value
		List retlst = new ArrayList(); //returned value

		StringBuffer address = new StringBuffer();
		address.append(snmpprotocol);
		address.append(":");
		address.append(ipAddress);
		address.append("/");
		address.append(snmpport);

		// Address targetAddress = GenericAddress.parse(protocol + ":" +
		// ipAddress + "/" + port);
		Address targetAddress = GenericAddress.parse(address.toString());
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(oid)));
		// pdu.add(new VariableBinding(new OID(new int[]{1,3,6,1,2,1,1,5,0})));
		pdu.setType(PDU.GET);

		// CommunityTarget
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		// target.setVersion(SnmpConstants.version1);// SnmpConstants.[ version1
		// = 0; version2c = 1; version3 = 3;]
		target.setVersion(snmpVersion);
		target.setTimeout(snmpTimeout);
		target.setRetries(3);

		DefaultUdpTransportMapping udpTransportMap = null;
		Snmp snmp = null;
		try {
			// send
			udpTransportMap = new DefaultUdpTransportMapping();
			//udpTransportMap.listen();
			snmp = new Snmp(udpTransportMap);
			snmp.listen();
			
			PDUFactory pf = new DefaultPDUFactory();
			TableUtils tu = new TableUtils(snmp, pf);

		    OID[] columns = new OID[1];

		    columns[0] = new VariableBinding(new OID(oid)).getOid();
		    List list = tu.getTable(target, columns, null, null);
			
		    for (int i = 0; i < list.size(); i++)
		    {

		     TableEvent te = (TableEvent) list.get(i);
		     VariableBinding[] vb = te.getColumns();
		     for (int j = 0; j < vb.length; j++)
		     {
		     // System.out.println(vb[j]);
		      String s = vb[j].getVariable().toString();
		      retlst.add(s);
		     }
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("Exception on SNMP table.", e);
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException e) {
					snmp = null;
				}
			}
			if (udpTransportMap != null) {
				try {
					udpTransportMap.close();
				} catch (IOException e) {
					udpTransportMap = null;
				}
			}
		}

		if (log.isInfoEnabled()) {
			log.info("IP:" + ipAddress + " table list:" + retlst);
		}

		return retlst;
	}
	
/*	
	@SuppressWarnings("unchecked")
	public static String snmpTable(String ipAddress, String community, String oid, int snmpVersion , long snmpTimeout ) throws AppException {
		String resultStat = null; // returned value
		
		StringBuffer address = new StringBuffer();
		address.append(snmpprotocol);
		address.append(":");
		address.append(ipAddress);
		address.append("/");
		address.append(snmpport);
		
//		Address targetAddress = GenericAddress.parse(protocol + ":" + ipAddress + "/" + port);
		Address targetAddress = GenericAddress.parse(address.toString());
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(oid)));
		//pdu.add(new VariableBinding(new OID(new int[]{1,3,6,1,2,1,1,5,0})));
		pdu.setType(PDU.GETBULK);

		// CommunityTarget
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		//target.setVersion(SnmpConstants.version1);// SnmpConstants.[ version1 = 0; version2c = 1; version3 = 3;]
		target.setVersion(snmpVersion);
		target.setTimeout(snmpTimeout);
		target.setRetries(1);

		DefaultUdpTransportMapping udpTransportMap = null;
		Snmp snmp = null;
		try {
			// send
			udpTransportMap = new DefaultUdpTransportMapping();
			udpTransportMap.listen();
			snmp = new Snmp(udpTransportMap);
			ResponseEvent response = snmp.send(pdu, target);
			PDU responsePdu = response.getResponse();

			if (responsePdu == null) {
				log.info(ipAddress + ": Request timed out.");
			} else {
				int errorStatus = responsePdu.getErrorStatus();
				String errorStatusText = responsePdu.getErrorStatusText();
				if (  errorStatus != 0 ){
					log.info(ipAddress + ": "+ responsePdu);  }
				Object obj = responsePdu.getVariableBindings().firstElement();
				VariableBinding variable = (VariableBinding) obj;
				resultStat = variable.getVariable().toString();
				System.out.println(ipAddress+" : responsePdu["+responsePdu+"] ; ");
			}
		} catch (Exception e) {
			throw new AppException("Exception on SNMP get.", e);
		} finally {
			if (snmp != null) {
				try {
					snmp.close();
				} catch (IOException e) {
					snmp = null;
				}
			}
			if (udpTransportMap != null) {
				try {
					udpTransportMap.close();
				} catch (IOException e) {
					udpTransportMap = null;
				}
			}
		}
		
		if (log.isInfoEnabled()) {
			log.info("IP:" + ipAddress + " resultStat:" + resultStat);
		}
		
		return resultStat;
	}
*/

//	/**
//	 * SNMP walk
//	 * 
//	 * @param ipAddress 
//	 * @param community 
//	 * @param oid object id
//	 * @return String[] 
//	 * @throws AppException
//	 */
//	public static String[] snmpWalk(String ipAddress, String community, String oid) throws AppException {
//		String[] returnValueString = null; // oid walk result
//		
//		SNMPv1CommunicationInterface comInterface = null;
//		try {
//			InetAddress hostAddress = InetAddress.getByName(ipAddress);
//			comInterface = new SNMPv1CommunicationInterface(
//					version, hostAddress, community);
//			comInterface.setSocketTimeout(2000);
//			
//			// return mib value that start from oid 
//			SNMPVarBindList tableVars = comInterface.retrieveMIBTable(oid);
//			returnValueString = new String[tableVars.size()];
//			
//			// Recursively process all the value of  starting oid 
//			for (int i = 0; i < tableVars.size(); i++) {
//				SNMPSequence pair = (SNMPSequence) tableVars.getSNMPObjectAt(i); // (OID,value)
//				SNMPObject snmpValue = pair.getSNMPObjectAt(1); // return value of the node
//				String typeString = snmpValue.getClass().getName(); // retrieve name of the snmp type .
//				// return values
//				if (typeString.equals("snmp.SNMPOctetString")) {
//					String snmpString = snmpValue.toString();
//					int nullLocation = snmpString.indexOf('\0');
//					if (nullLocation >= 0)
//						snmpString = snmpString.substring(0, nullLocation);
//					returnValueString[i] = snmpString;
//				} else {
//					returnValueString[i] = snmpValue.toString();
//				}
//			}
//		} catch (SocketTimeoutException ste) {
//			if (log.isErrorEnabled()) {
//				log.error( ipAddress + ", OIDΪ" + oid + " timeout!");
//			}
//			returnValueString = null;
//		} catch (Exception e) {
//			throw new AppException("Exception on SNMP walk!", e);
//		} finally {
//			if (comInterface != null) {
//				try {
//					comInterface.closeConnection();
//				} catch (SocketException e) {
//					comInterface = null;
//				}
//			}
//		}
//		
//		return returnValueString;
//	}
//	
	
	public static void main(String args[]){
		String mibv;
		String ipAddress = args[0];
		String community = args[1];
		String oid = args[2];
		String v = args[3];
		int ver = (null==v || "".equals(v))?0:Integer.parseInt(v);
		//SnmpManager snmpmgr = new SnmpManager();
		try {
			System.out.println("-------------snmp getTable test -------------------------------");
			ipAddress= "10.10.10.86";
			community = "public";
			oid = "1.3.6.1.2.1.2.2.1.2";
			ver = 0;
			List list = SnmpUtil.snmpTable(ipAddress, community, oid, ver, 300l);
			
			System.out.println("-------------snmp test -------------------------------");
			int res = SnmpUtil.testSnmp(ipAddress, community, ver, 300l);
			System.out.println("success="+res);
			System.out.println("-------------snmp version: V1 =  0   -----------------");
			mibv = SnmpUtil.snmpGet(ipAddress, community, oid);
			System.out.println(mibv);
			//SnmpManager.snmpversion=1;
			System.out.println("-------------snmp version: V2c = 1   -----------------");
			 ver = 1 ; //snmpverion V2c =1;
				String oids[] = new String [] {"1.3.6.1.2.1.2.1","1.3.6.1.2.1.2.2.1.1.0","1.3.6.1.2.1.2.2.1.2.0","1.3.6.1.2.1.2.2.1.6.0"};
			mibv = SnmpUtil.snmpBulkGet(ipAddress, community, oids, ver, 500l);
			System.out.println(oid+" : snmpGetBulk="+mibv);
			//SnmpManager.snmpversion=3;
			System.out.println("-------------snmp version: V3 =  3   -----------------");
			ver =3 ; //snmpver V3 = 3;
			mibv = SnmpUtil.snmpGet(ipAddress, community, oid, ver, 500l);
			System.out.println(mibv);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}