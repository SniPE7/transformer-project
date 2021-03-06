package com.ibm.ncs.export;

import java.io.PrintWriter;

public class DevicePolicyRecord {
  private String devid,
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
  
  public DevicePolicyRecord() {
    super();
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
   *     <element active="true" checksum="zeit_1277444139_1513762256" datalogpath="snmp10.0.19.129_CpuUtilization_cisco_FWCPUTotal1min_4_161" profile="BF_snmp_CpuUtilization">
      <fields>
        <server>10.0.19.129</server>
        <oidgroupname>CpuUtilization_cisco_FWCPUTotal1min_4</oidgroupname>
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
        <description>BF BJ-FW-TEL2.default.domain.invalid CpuUtilization</description>
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
    output.println("    <element active=\"true\" checksum=\"zeit_" + ipID + "_" + Math.abs(this.hashCode()) + "\" datalogpath=\"" + this.getModuleName() + this.getDevip() + "_" + this.getOidGroupName() + "_" + snmpPort + "\" profile=\"" + serverID + "_" + this.getModuleName() + "_" + this.getEventTypeMajor() + "\">");
    output.println("      <fields>");
    output.println("        <server>" + this.getDevip() + "</server>");
    output.println("        <oidgroupname>" + this.getOidGroupName() + "</oidgroupname>");
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
    output.println("        <description>" + serverID + " " + this.getSysname() + " " + this.getEventTypeMajor() + "</description>");
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
   *         <element active="true" checksum="zeit_1277444138_1733315509" datalogpath="icmp10.0.19.129" profile="BF_icmp_Devices">
      <fields>
        <server>10.0.19.129</server>
        <timeout>10</timeout>
        <numberofpings>20</numberofpings>
        <packetinterval>1</packetinterval>
        <packetsize>64</packetsize>
        <typeofservice>0</typeofservice>
        <retries>2</retries>
        <poll>60</poll>
        <failureretests>0</failureretests>
        <retestinterval>0</retestinterval>
        <description>BF BJ-FW-TEL2.default.domain.invalid</description>
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
    if (this.getDevip() != null) {
      String[] ips = this.getDevip().split(".");
      if (ips.length > 3) {
         ipID = Integer.parseInt(ips[0]) * Integer.parseInt(ips[1]) * Integer.parseInt(ips[2]) * Integer.parseInt(ips[3]);
      }
    }
    output.println("    <element active=\"true\" checksum=\"zeit_" + ipID + "_" + Math.abs(this.hashCode()) + "\" datalogpath=\"" + this.getModuleName() + this.getDevip() + "\" profile=\"" + serverID + "_" + this.getModuleName() + "_Devices\">");
    output.println("      <fields>");
    output.println("        <server>" + this.getDevip() + "</server>");
    output.println("        <timeout>10</timeout>");
    output.println("        <numberofpings>20</numberofpings>");
    output.println("        <packetinterval>1</packetinterval>");
    output.println("        <packetsize>64</packetsize>");
    output.println("        <typeofservice>0</typeofservice>");
    output.println("        <retries>2</retries>");
    output.println("        <poll>" + this.getPoll() + "</poll>");
    output.println("        <failureretests>0</failureretests>");
    output.println("        <retestinterval>0</retestinterval>");
    output.println("        <description>" + serverID + " " + this.getSysname() + "</description>");
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
