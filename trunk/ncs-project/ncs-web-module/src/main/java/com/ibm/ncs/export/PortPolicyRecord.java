package com.ibm.ncs.export;

import java.io.PrintWriter;

public class PortPolicyRecord {
  private String pdid,
                ifindex,
                ifip,
                ifdescr,
                devid,
                devip,
                ipdecode,
                sysname, /* Device Name*/
                mrid, /*Manufactureror ID*/
                mrname, /*Manufactureror Name*/
                dtid,
                rcommunity,
                snmpversion,
                mobjectid,
                mpid,
                eveid,
                poll, /*Polling time*/
                eventTypeMajor, /*Event Type Name*/
                opid, /*OID Group ID*/
                oidGroupName,
                moduleName;
  
  public PortPolicyRecord() {
    super();
  }

  public String getPdid() {
    return pdid;
  }

  public void setPdid(String pdid) {
    this.pdid = pdid;
  }

  public String getIfindex() {
    return ifindex;
  }

  public void setIfindex(String ifindex) {
    this.ifindex = ifindex;
  }

  public String getIfip() {
    return ifip;
  }

  public void setIfip(String ifip) {
    this.ifip = ifip;
  }

  public String getIfdescr() {
    return ifdescr;
  }

  public void setIfdescr(String ifdescr) {
    this.ifdescr = ifdescr;
  }

  public String getDevid() {
    return devid;
  }

  public void setDevid(String devid) {
    this.devid = devid;
  }

  public String getDevip() {
    return devip;
  }

  public void setDevip(String devip) {
    this.devip = devip;
  }

  public String getIpdecode() {
    return ipdecode;
  }

  public void setIpdecode(String ipdecode) {
    this.ipdecode = ipdecode;
  }

  public String getSysname() {
    return sysname;
  }

  public void setSysname(String sysname) {
    this.sysname = sysname;
  }

  public String getMrid() {
    return mrid;
  }

  public void setMrid(String mrid) {
    this.mrid = mrid;
  }

  public String getMrname() {
    return mrname;
  }

  public void setMrname(String mrname) {
    this.mrname = mrname;
  }

  public String getDtid() {
    return dtid;
  }

  public void setDtid(String dtid) {
    this.dtid = dtid;
  }

  public String getRcommunity() {
    return rcommunity;
  }

  public void setRcommunity(String rcommunity) {
    this.rcommunity = rcommunity;
  }

  public String getSnmpversion() {
    return snmpversion;
  }

  public void setSnmpversion(String snmpversion) {
    this.snmpversion = snmpversion;
  }

  public String getMobjectid() {
    return mobjectid;
  }

  public void setMobjectid(String mobjectid) {
    this.mobjectid = mobjectid;
  }

  public String getMpid() {
    return mpid;
  }

  public void setMpid(String mpid) {
    this.mpid = mpid;
  }

  public String getEveid() {
    return eveid;
  }

  public void setEveid(String eveid) {
    this.eveid = eveid;
  }

  public String getPoll() {
    return poll;
  }

  public void setPoll(String poll) {
    this.poll = poll;
  }

  public String getEventTypeMajor() {
    return eventTypeMajor;
  }

  public void setEventTypeMajor(String eventTypeMajor) {
    this.eventTypeMajor = eventTypeMajor;
  }

  public String getOpid() {
    return opid;
  }

  public void setOpid(String opid) {
    this.opid = opid;
  }

  public String getOidGroupName() {
    return oidGroupName;
  }

  public void setOidGroupName(String oidGroupName) {
    this.oidGroupName = oidGroupName;
  }
  
  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  /**
   * <pre>
   * <element active="true" checksum="zeit_1277444139_1962206808" datalogpath="snmp80.4.243.31_ifCRC_cisco_125_161" profile="BF_snmp_ifCRC">
      <fields>
        <server>80.4.243.31</server>
        <oidgroupname>ifCRC_cisco_125</oidgroupname>
        <communitystring crypt="false">BFR99O</communitystring>
        <port>161</port>
        <version>2</version>
        <securityname />
        <authenticationphrase />
        <privacyphrase />
        <authenticationprotocol>MD5</authenticationprotocol>
        <privacyprotocol>DES</privacyprotocol>
        <timeout>20</timeout>
        <retries>2</retries>
        <poll>300</poll>
        <failureretests>0</failureretests>
        <retestinterval>0</retestinterval>
        <description>BF BF65SW31-C1 ifCRC PortName:GigabitEthernet3/29 LineName:NA</description>
      </fields>
      <dvc defaultdvcstatus="GOOD">
        <dvcgroup dvcstatus="FAILED">
          <discretevalueclassification dvccondition="GT">
            <dvcfieldname>totalTime</dvcfieldname>
            <dvcvalue>20</dvcvalue>
            <dvcdescription>totalTime GT 20</dvcdescription>
          </discretevalueclassification>
        </dvcgroup>
        <dvcgroup dvcstatus="MARGINAL">
          <discretevalueclassification dvccondition="GT">
            <dvcfieldname>totalTime</dvcfieldname>
            <dvcvalue>10</dvcvalue>
            <dvcdescription>totalTime GT 10</dvcdescription>
          </discretevalueclassification>
        </dvcgroup>
      </dvc>
    </element>
    </pre>
   * @param output
   */
  public void toSnmpXML(PrintWriter output, String snmpPort, String serverID) {
    long ipID = Math.abs(System.currentTimeMillis());
    if (this.getDevip() != null) {
      String[] ips = this.getDevip().split(".");
      if (ips.length > 3) {
         ipID = Integer.parseInt(ips[0]) * Integer.parseInt(ips[1]) * Integer.parseInt(ips[2]) * Integer.parseInt(ips[3]);
      }
    }
    output.println("    <element active=\"true\" checksum=\"zeit_" + ipID + "_" + Math.abs(this.hashCode()) + "\" datalogpath=\"" + this.getModuleName() + this.getDevip() + "_" + this.getOidGroupName() + "_" + this.getIfindex() + "_" + snmpPort + "\" profile=\"" + serverID + "_" + this.getModuleName() + "_" + this.getEventTypeMajor() + "\">");
    output.println("      <fields>");
    output.println("        <server>" + this.getDevip() + "</server>");
    output.println("        <oidgroupname>" + this.getOidGroupName() + "_" + this.getIfindex() + "</oidgroupname>");
    output.println("        <communitystring crypt=\"false\">" + this.getRcommunity() + "</communitystring>");
    output.println("        <port>" + snmpPort + "</port>");
    output.println("        <version>" + this.getSnmpversion() + "</version>");
    output.println("        <securityname />");
    output.println("        <authenticationphrase />");
    output.println("        <privacyphrase />");
    output.println("        <authenticationprotocol>MD5</authenticationprotocol>");
    output.println("        <privacyprotocol>DES</privacyprotocol>");
    output.println("        <timeout>20</timeout>");
    output.println("        <retries>2</retries>");
    output.println("        <poll>" + this.getPoll() + "</poll>");
    output.println("        <failureretests>0</failureretests>");
    output.println("        <retestinterval>0</retestinterval>");
    output.println("        <description>" + serverID + " " + this.getSysname() + " " + this.getEventTypeMajor() + " PortName:" + this.getIfdescr() + " LineName:NA</description>");
    output.println("      </fields>");
    output.println("      <dvc defaultdvcstatus=\"GOOD\">");
    output.println("        <dvcgroup dvcstatus=\"FAILED\">");
    output.println("          <discretevalueclassification dvccondition=\"GT\">");
    output.println("            <dvcfieldname>totalTime</dvcfieldname>");
    output.println("            <dvcvalue>20</dvcvalue>");
    output.println("            <dvcdescription>totalTime GT 20</dvcdescription>");
    output.println("          </discretevalueclassification>");
    output.println("        </dvcgroup>");
    output.println("        <dvcgroup dvcstatus=\"MARGINAL\">");
    output.println("          <discretevalueclassification dvccondition=\"GT\">");
    output.println("            <dvcfieldname>totalTime</dvcfieldname>");
    output.println("            <dvcvalue>10</dvcvalue>");
    output.println("            <dvcdescription>totalTime GT 10</dvcdescription>");
    output.println("          </discretevalueclassification>");
    output.println("        </dvcgroup>");
    output.println("      </dvc>");
    output.println("    </element>");
    
    output.flush();
  }

  /**
   * <pre>
   *             <element active="true" checksum="zeit_1277444139_1926604003" datalogpath="icmp83.252.4.1" profile="BF_icmp_Ports">
      <fields>
        <server>83.252.4.1</server>
        <timeout>10</timeout>
        <numberofpings>20</numberofpings>
        <packetinterval>1</packetinterval>
        <packetsize>64</packetsize>
        <typeofservice>0</typeofservice>
        <retries>2</retries>
        <poll>600</poll>
        <failureretests>0</failureretests>
        <retestinterval>0</retestinterval>
        <description>BF BF65RT0A-A4 PortName:FastEthernet9/19 LineName:NA</description>
      </fields>
      <dvc defaultdvcstatus="GOOD">
        <dvcgroup dvcstatus="FAILED">
          <discretevalueclassification dvccondition="LT">
            <dvcfieldname>respondPercent</dvcfieldname>
            <dvcvalue>50</dvcvalue>
            <dvcdescription>respondPercent LT 50</dvcdescription>
          </discretevalueclassification>
        </dvcgroup>
        <dvcgroup dvcstatus="FAILED">
          <discretevalueclassification dvccondition="GT">
            <dvcfieldname>averageRTT</dvcfieldname>
            <dvcvalue>1.0</dvcvalue>
            <dvcdescription>averageRTT GT 1.0</dvcdescription>
          </discretevalueclassification>
        </dvcgroup>
        <dvcgroup dvcstatus="MARGINAL">
          <discretevalueclassification dvccondition="GT">
            <dvcfieldname>averageRTT</dvcfieldname>
            <dvcvalue>0.2</dvcvalue>
            <dvcdescription>averageRTT GT 0.2</dvcdescription>
          </discretevalueclassification>
        </dvcgroup>
      </dvc>
    </element>

    </pre>
   * @param output
   */
  public void toIcmpXML(PrintWriter output, String serverID) {
    long ipID = Math.abs(System.currentTimeMillis());
    if (this.getIfip() != null) {
      String[] ips = this.getIfip().split(".");
      if (ips.length > 3) {
         ipID = Integer.parseInt(ips[0]) * Integer.parseInt(ips[1]) * Integer.parseInt(ips[2]) * Integer.parseInt(ips[3]);
      }
    }
    output.println("    <element active=\"true\" checksum=\"zeit_" + ipID + "_" + Math.abs(this.hashCode()) + "\" datalogpath=\"" + this.getModuleName() + this.getIfip() + "\" profile=\"" + serverID + "_" + this.getModuleName() + "_Ports\">");
    output.println("      <fields>");
    output.println("        <server>" + this.getIfip() + "</server>");
    output.println("        <timeout>10</timeout>");
    output.println("        <numberofpings>20</numberofpings>");
    output.println("        <packetinterval>1</packetinterval>");
    output.println("        <packetsize>64</packetsize>");
    output.println("        <typeofservice>0</typeofservice>");
    output.println("        <retries>2</retries>");
    output.println("        <poll>" + this.getPoll() + "</poll>");
    output.println("        <failureretests>0</failureretests>");
    output.println("        <retestinterval>0</retestinterval>");
    output.println("        <description>" + serverID + " " + this.getSysname() + " " + this.getEventTypeMajor() + " PortName:" + this.getIfdescr() + " LineName:NA</description>");
    output.println("      </fields>");
    output.println("      <dvc defaultdvcstatus=\"GOOD\">");
    output.println("        <dvcgroup dvcstatus=\"FAILED\">");
    output.println("          <discretevalueclassification dvccondition=\"LT\">");
    output.println("            <dvcfieldname>respondPercent</dvcfieldname>");
    output.println("            <dvcvalue>50</dvcvalue>");
    output.println("            <dvcdescription>respondPercent LT 50</dvcdescription>");
    output.println("          </discretevalueclassification>");
    output.println("        </dvcgroup>");
    output.println("        <dvcgroup dvcstatus=\"FAILED\">");
    output.println("          <discretevalueclassification dvccondition=\"GT\">");
    output.println("            <dvcfieldname>averageRTT</dvcfieldname>");
    output.println("            <dvcvalue>1.0</dvcvalue>");
    output.println("            <dvcdescription>averageRTT GT 1.0</dvcdescription>");
    output.println("          </discretevalueclassification>");
    output.println("        </dvcgroup>");
    output.println("        <dvcgroup dvcstatus=\"MARGINAL\">");
    output.println("          <discretevalueclassification dvccondition=\"GT\">");
    output.println("            <dvcfieldname>averageRTT</dvcfieldname>");
    output.println("            <dvcvalue>0.2</dvcvalue>");
    output.println("            <dvcdescription>averageRTT GT 0.2</dvcdescription>");
    output.println("          </discretevalueclassification>");
    output.println("        </dvcgroup>");
    output.println("      </dvc>");
    output.println("    </element>");
    
    output.flush();
  }
}
