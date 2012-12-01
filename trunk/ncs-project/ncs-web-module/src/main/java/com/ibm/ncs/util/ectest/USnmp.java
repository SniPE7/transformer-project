

package com.ibm.ncs.util.ectest;


import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class USnmp
{

    public USnmp()
    {
        ipAddr = null;
        target = null;
        pdu = new PDU();
        response = new PDU();
        init();
        target = new CommunityTarget();
    }

    public USnmp(String ipAddr, String rCommunity, int ver, long timeout)
    {
        this.ipAddr = null;
        target = null;
        pdu = new PDU();
        response = new PDU();
        init();
        target = new CommunityTarget();
        setTarget(ipAddr, rCommunity, ver, timeout);
    }

    private void init()
    {
        try
        {
       //     Logger.getRootLogger().setLevel(Level.OFF);
            response.setType(-94);
        }
        catch(IllegalArgumentException illegalargumentexception) { }
    }

    public void setTarget(String ipAddr, String rCommunity, int ver, long timeout)
    {
        String fullAddr = "udp:" + ipAddr + "/161";
        org.snmp4j.smi.Address deviceAdd = GenericAddress.parse(fullAddr);
        target.setAddress(deviceAdd);
        target.setCommunity(new OctetString(rCommunity));
        target.setVersion(ver);
        target.setTimeout(timeout);
        target.setRetries(3);
        this.ipAddr = ipAddr;
    }

    public void addOID(String oid)
    {
        pdu.add(new VariableBinding(new OID(oid)));
    }

    public void addOID(OID oid)
    {
        pdu.add(new VariableBinding(oid));
    }

    public String getOid(int index)
    {
        VariableBinding vb = response.get(index);
        return vb.getOid().toString();
    }

    public String getValue(int index)
    {
        VariableBinding vb = response.get(index);
        Variable v = vb.getVariable();
        return filterNull(v.toString());
    }

    public String[] getAllValue()
    {
        String arrayStr[] = new String[getResponseSize()];
        for(int i = 0; i < getResponseSize(); i++)
            arrayStr[i] = getValue(i);

        return arrayStr;
    }

    public String getHexToIp(int index)
    {
        VariableBinding vb = response.get(index);
        OctetString os = (OctetString)vb.getVariable();
        return os.toString('.', 10);
    }

    public String getHexString(int index)
    {
        VariableBinding vb = response.get(index);
        OctetString os = (OctetString)vb.getVariable();
        return os.toHexString(':').toUpperCase();
    }

    public int getResponseSize()
    {
        if(response == null)
            return 0;
        else
            return response.size();
    }

    public int getPduSize()
    {
        return pdu.size();
    }

    public void pduClear()
    {
        pdu.clear();
    }

    public void close()
    {
        pdu.clear();
        response.clear();
    }

    public int get()
    {
        if(pdu.size() == 0)
            return 0;
        int retVal = 1;
        response.clear();
        try
        {
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();
            Snmp snmp = new Snmp(transport);
            int pduSize = pdu.size();
            int pduCount = pduSize / 50 + 1;
            int curSize = 0;
            PDU requestPdu = new PDU();
            for(int iCount = 0; iCount < pduCount; iCount++)
            {
                curSize = iCount * 50;
                requestPdu.clear();
                for(int iRequest = 0; iRequest + curSize < pduSize && iRequest < 50; iRequest++)
                    requestPdu.add(pdu.get(iRequest + curSize));

                requestPdu.setType(-96);
                ResponseEvent responseEvent = snmp.send(requestPdu, target);
                PDU tempPdu = responseEvent.getResponse();
                if(tempPdu == null)
                {
                    retVal = 0;
                    break;
                }
                for(int i = 0; i < tempPdu.size(); i++)
                    response.add(tempPdu.get(i));

                tempPdu.clear();
            }

            requestPdu.clear();
            transport.close();
            snmp.close();
        }
        catch(IOException e)
        {
            return 0;
        }
        catch(Exception e)
        {
            return 0;
        }
        return retVal;
    }

    public int getNext()
    {
        if(pdu.size() == 0)
            return 0;
        int retVal = 1;
        response.clear();
        try
        {
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();
            Snmp snmp = new Snmp(transport);
            int pduSize = pdu.size();
            int pduCount = pduSize / 50 + 1;
            int curSize = 0;
            PDU requestPdu = new PDU();
            requestPdu.setType(-95);
            for(int iCount = 0; iCount < pduCount; iCount++)
            {
                curSize = iCount * 50;
                requestPdu.clear();
                for(int iRequest = 0; iRequest + curSize < pduSize && iRequest < 50; iRequest++)
                    requestPdu.add(pdu.get(iRequest + curSize));

                ResponseEvent responseEvent = snmp.send(requestPdu, target);
                PDU tempPdu = responseEvent.getResponse();
                if(tempPdu == null)
                {
                    retVal = 0;
                    break;
                }
                for(int i = 0; i < tempPdu.size(); i++)
                    response.add(tempPdu.get(i));

            }

            transport.close();
            snmp.close();
        }
        catch(Exception e)
        {
            return 0;
        }
        return retVal;
    }

    public int getBulk(int maxRepetitions)
    {
        if(pdu.size() == 0)
            return 0;
        response.clear();
        int curNum = 0;
        int oriPduSize = pdu.size();
        int oriMaxRepetitions = maxRepetitions;
        try
        {
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();
            Snmp snmp = new Snmp(transport);
            pdu.setType(-91);
            do
            {
                maxRepetitions = oriMaxRepetitions - response.size() / oriPduSize;
                pdu.setMaxRepetitions(maxRepetitions);
                ResponseEvent responseEvent = snmp.send(pdu, target);
                PDU tempPdu = responseEvent.getResponse();
                if(tempPdu != null)
                {
                    curNum = tempPdu.size() / oriPduSize;
                    for(int i = 0; i < curNum * oriPduSize; i++)
                        response.add(tempPdu.get(i));

                }
                pdu.clear();
                if(response.size() / oriPduSize < oriMaxRepetitions)
                {
                    curNum--;
                    for(int j = 0; j < oriPduSize; j++)
                        pdu.add(tempPdu.get(curNum * oriPduSize + j));

                }
            } while(response.size() / oriPduSize < oriMaxRepetitions);
            transport.close();
            snmp.close();
        }
        catch(Exception e)
        {
            return 0;
        }
        return 1;
    }

    public int getTable()
    {
        if(pdu.size() == 0)
            return 0;
        response.clear();
        String oriOid = pdu.get(0).getOid().toString();
        String newOid = new String();
        int oriPduSize = pdu.size();
        try
        {
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();
            Snmp snmp = new Snmp(transport);
            PDU requestPdu = new PDU(pdu);
            requestPdu.setType(-95);
            do
            {
                ResponseEvent responseEvent = snmp.send(requestPdu, target);
                PDU tempPdu = responseEvent.getResponse();
                if(tempPdu == null)
                    break;
                newOid = tempPdu.get(0).getOid().toString();
                if(!newOid.startsWith(oriOid))
                    break;
                for(int i = 0; i < oriPduSize; i++)
                    response.add(tempPdu.get(i));

                requestPdu.clear();
                requestPdu = tempPdu;
                requestPdu.setType(-95);
            } while(true);
            transport.close();
            snmp.close();
        }
        catch(Exception e)
        {
            return 0;
        }
        return 1;
    }

    public static int testSnmp(String ipAddr, String rCommunity, int ver, long timeout)
    {
        USnmp es = new USnmp(ipAddr, rCommunity, ver, timeout);
        es.addOID("1.3.6.1.2.1.1.3.0");
        int ret = es.get();
        es.close();
        return ret;
    }

    public static void main(String args[])
    {
        int ret = 0;
        String ip = null;
        String rComm = null;
        ip = "192.168.122.131";
        rComm = "public";
        
        USnmp uSnmp = new USnmp(ip, rComm, 0, 300L);
        uSnmp.addOID("1.3.6.1.2.1.1.2.0");
        uSnmp.addOID("1.3.6.1.2.1.1.5.0");
        uSnmp.addOID("1.3.6.1.2.1.1.1.0");
        uSnmp.addOID("1.3.6.1.4.1.9.3.6.6.0");
        uSnmp.addOID("1.3.6.1.4.1.9.3.6.7.0");
        uSnmp.addOID("1.3.6.1.2.1.47.1.1.1.1.9.1");
        uSnmp.addOID("1.3.6.1.4.1.9.3.6.3.0");
        uSnmp.addOID("1.3.6.1.2.1.2.1.0");
        ret = uSnmp.get();
        System.out.println(ret+""+uSnmp.pdu);
        if(ret == 1)
        {
            for(int i = 0; i < uSnmp.getPduSize(); i++)
                System.out.println(uSnmp.getValue(i));

        }
    }
    
    public static String filterNull(String str)
    {
        if(str == null)
            str = "";
        else
        if(str.equalsIgnoreCase("noSuchInstance")){
            str = "";
        }else if (str.equalsIgnoreCase("noSuchObject")){
        	str="";
        }
        else if(str.equalsIgnoreCase("Null")){
            str = "";            
        }
        return str;
    }

    public static final int v1 = 0;
    public static final int v2 = 1;
    public static final int v3 = 3;
    private static final int MAX_PDU_SIZE = 50;
    private String ipAddr;
    private CommunityTarget target;
    private PDU pdu;
    private PDU response;
	public PDU getPdu() {
		return pdu;
	}

	public void setPdu(PDU pdu) {
		this.pdu = pdu;
	}
}
