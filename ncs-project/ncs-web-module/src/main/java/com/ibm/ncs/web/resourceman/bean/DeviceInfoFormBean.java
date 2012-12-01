package com.ibm.ncs.web.resourceman.bean;

import java.util.List;
import java.util.Map;

import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.util.test.Device;
import com.ibm.ncs.util.test.Flashfile;

public class DeviceInfoFormBean {

	private String gid;
	private String devid;
	private String 	devname	 		;
	private String 	devalias	 	;
	private String 	devip	 		;
	private String 	resnum	 		;
	private String  domainid		;
	private String 	providerphone	;
	private String 	devtype		 	;
	private String 	devsubtype	 	;
	private String 	model	 		;
	private String 	manufacture	 	;
	private String 	objectid	 	;
	private String 	snmpversion	 	;
	private String 	rcommunity	 	;
	private String 	adminName	 	;
	private String 	adminPhone	 	;
	private String 	devpolicy	 	;
	private String 	timeframPolicy	;
	private String 	devSoftwareVer	;
	private String 	devSerialNum	;
	private String 	devgroup	 	;
	private String 	ramsize	 		;
	private String 	nvramsize		;
	private String 	flashSize	 	;
	private String 	description	 	;
	private long  dtid;

	private String mrid;
	
	private String flashfilename;
	private String flashfilesize;
	
	private String[] interfaceindex;
	private String[] interfacename;
	private String[] interfaceip;
	private String[] interfacemac;
	
	private Device device;
	
	private List<TPortInfo> portinfolist ;
	
	private Flashfile[] flashfile ;
	private String [] predefmibindex; //checkbox selected list
	private Map<Long, Object> pdmInfoByMidMap;
	
	
	public Map<Long, Object> getPdmInfoByMidMap() {
		return pdmInfoByMidMap;
	}
	public void setPdmInfoByMidMap(Map<Long, Object> pdmInfoByMidMap) {
		this.pdmInfoByMidMap = pdmInfoByMidMap;
	}
	public String[] getPredefmibindex() {
		return predefmibindex;
	}
	public void setPredefmibindex(String[] predefmibindex) {
		this.predefmibindex = predefmibindex;
	}
	public String getDevname() {
		return devname;
	}
	public void setDevname(String devname) {
		this.devname = devname;
	}
	public String getDevalias() {
		return devalias;
	}
	public void setDevalias(String devalias) {
		this.devalias = devalias;
	}
	public String getDevip() {
		return devip;
	}
	public void setDevip(String devip) {
		this.devip = devip;
	}
	public String getResnum() {
		return resnum;
	}
	public void setResnum(String resnum) {
		this.resnum = resnum;
	}
	public String getProviderphone() {
		return providerphone;
	}
	public void setProviderphone(String providerphone) {
		this.providerphone = providerphone;
	}
	public String getDevtype() {
		return devtype;
	}
	public void setDevtype(String devtype) {
		this.devtype = devtype;
	}
	public String getDevsubtype() {
		return devsubtype;
	}
	public void setDevsubtype(String devsubtype) {
		this.devsubtype = devsubtype;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getManufacture() {
		return manufacture;
	}
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}
	public String getObjectid() {
		return objectid;
	}
	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
	public String getSnmpversion() {
		return snmpversion;
	}
	public void setSnmpversion(String snmpversion) {
		this.snmpversion = snmpversion;
	}
	public String getRcommunity() {
		return rcommunity;
	}
	public void setRcommunity(String rcommunity) {
		this.rcommunity = rcommunity;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminPhone() {
		return adminPhone;
	}
	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}
	public String getDevpolicy() {
		return devpolicy;
	}
	public void setDevpolicy(String devpolicy) {
		this.devpolicy = devpolicy;
	}
	public String getTimeframPolicy() {
		return timeframPolicy;
	}
	public void setTimeframPolicy(String timeframPolicy) {
		this.timeframPolicy = timeframPolicy;
	}
	public String getDevSoftwareVer() {
		return devSoftwareVer;
	}
	public void setDevSoftwareVer(String devSoftwareVer) {
		this.devSoftwareVer = devSoftwareVer;
	}
	public String getDevSerialNum() {
		return devSerialNum;
	}
	public void setDevSerialNum(String devSerialNum) {
		this.devSerialNum = devSerialNum;
	}
	public String getDevgroup() {
		return devgroup;
	}
	public void setDevgroup(String devgroup) {
		this.devgroup = devgroup;
	}

	public String getNvramsize() {
		return nvramsize;
	}

	public String getFlashSize() {
		return flashSize;
	}
	public void setFlashSize(String flashSize) {
		this.flashSize = flashSize;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}



	public long getDtid() {
		return dtid;
	}
	public void setDtid(long l) {
		this.dtid = l;
	}
	public String getDevid() {
		return devid;
	}
	public void setDevid(String devid) {
		this.devid = devid;
	}
	public String getMrid() {
		return mrid;
	}
	public void setMrid(String mrid) {
		this.mrid = mrid;
	}
	public String getFlashfilename() {
		return flashfilename;
	}
	public void setFlashfilename(String flashfilename) {
		this.flashfilename = flashfilename;
	}
	public String getFlashfilesize() {
		return flashfilesize;
	}
	public void setFlashfilesize(String flashfilesize) {
		this.flashfilesize = flashfilesize;
	}
	public String[] getInterfaceindex() {
		return interfaceindex;
	}
	public void setInterfaceindex(String[] interfaceindex) {
		this.interfaceindex = interfaceindex;
	}
	public String[] getInterfacename() {
		return interfacename;
	}
	public void setInterfacename(String[] interfacename) {
		this.interfacename = interfacename;
	}
	public String[] getInterfaceip() {
		return interfaceip;
	}
	public void setInterfaceip(String[] interfaceip) {
		this.interfaceip = interfaceip;
	}
	public String[] getInterfacemac() {
		return interfacemac;
	}
	public void setInterfacemac(String[] interfacemac) {
		this.interfacemac = interfacemac;
	}
	public void setNvramsize(String nvramsize) {
		this.nvramsize = nvramsize;
	}
	public String getRamsize() {
		return ramsize;
	}
	public void setRamsize(String ramsize) {
		this.ramsize = ramsize;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public List<TPortInfo> getPortinfolist() {
		return portinfolist;
	}
	public void setPortinfolist(List<TPortInfo> portinfolist) {
		this.portinfolist = portinfolist;
	}
	public Flashfile[] getFlashfile() {
		return flashfile;
	}
	public void setFlashfile(Flashfile[] flashfile) {
		this.flashfile = flashfile;
	}
	
	public String toString(){
		StringBuffer buf =  new StringBuffer();
		buf.append("DeviceInfoFormBean: " );
		buf.append(" devid=").append(devid);
		buf.append(", devname=").append(devname);
		buf.append(", devalias=").append(devalias);
		buf.append(", devip=").append(devip);
		buf.append(", resnum=").append(resnum);
		buf.append(", providerphone=").append(providerphone);
		buf.append(", devtype=").append(devtype);
		buf.append(", devsubtype=").append(devsubtype);
		buf.append(", model=").append(model);
		buf.append(", manufacture=").append(manufacture);
		buf.append(", objectid=").append(objectid);
		buf.append(", snmpversion=").append(snmpversion);
		buf.append(", rcommunity=").append(rcommunity);
		buf.append(", adminName=").append(adminName);
		buf.append(", devpolicy=").append(devpolicy);
		buf.append(", timeframPolicy=").append(timeframPolicy);
		buf.append(", devSoftwareVer=").append(devSoftwareVer);
		buf.append(", devdevSerialNumid=").append(devSerialNum);
		buf.append(", devgroup=").append(devgroup);
		buf.append(", ramsize=").append(ramsize);
		buf.append(", nvramsize=").append(nvramsize);
		buf.append(", flashSize=").append(flashSize);
		buf.append(", description=").append(description);
		buf.append(", dtid=").append(dtid);
		buf.append(", mrid=").append(mrid);
		buf.append(", flashfilename=").append(flashfilename);
		buf.append(", flashfilesize=").append(flashfilesize);
		return buf.toString();
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getDomainid() {
		return domainid;
	}
	public void setDomainid(String domainid) {
		this.domainid = domainid;
	}
	
}
