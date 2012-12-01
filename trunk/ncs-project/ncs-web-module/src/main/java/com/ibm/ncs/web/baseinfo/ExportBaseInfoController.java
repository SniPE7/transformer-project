/**
 * 
 */
package com.ibm.ncs.web.baseinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dao.VEventTypeDao;
import com.ibm.ncs.model.dao.VMfCateDevtypeDao;
import com.ibm.ncs.model.dao.VOidGroupDao;
import com.ibm.ncs.model.dao.VPerformParamDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.SnmpEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TOidgroupDetailsInit;
import com.ibm.ncs.model.dto.TOidgroupInfoInit;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyDetails;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.dto.VEventType;
import com.ibm.ncs.model.dto.VMfCateDevtype;
import com.ibm.ncs.model.dto.VOidGroup;
import com.ibm.ncs.model.dto.VPerformParam;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.excel.DBTOExcel;
import com.ibm.ncs.web.policyapply.bean.NonTimeFramePolicyDetails;
import com.ibm.ncs.web.policyapply.bean.SyslogPolicyDetails;

/**
 * @author root
 *
 */
public class ExportBaseInfoController implements Controller {

	
	String pageView;
	String message;
	VMfCateDevtypeDao VMfCateDevtypeDao;
	VOidGroupDao VOidGroupDao;
	VEventTypeDao VEventTypeDao;
	VPerformParamDao VPerformParamDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	TCategoryMapInitDao TCategoryMapInitDao;
	DefMibGrpDao defMibGrpDao;
	TModuleInfoInitDao TModuleInfoInitDao;
	TOidgroupInfoInitDao TOidgroupInfoInitDao;
	SyslogEventsProcessNsDao syslogEventsProcessNsDao;
	SyslogEventsProcessDao syslogEventsProcessDao;
	SnmpEventsProcessDao snmpEventsProcessDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	

	DataSource datasource;	
	protected SimpleJdbcTemplate jdbcTemplate;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		message = "";
		Connection con = datasource.getConnection();
	      		 	       
		Map<String, Object> model = new HashMap<String, Object>();
		try{					
			Map<String, Object> exportList = new HashMap<String, Object>();
			List<VMfCateDevtype> VMfCateDevtypeList = new ArrayList();
			List<VOidGroup> VOidGroupList = new ArrayList();
			List<VEventType> VEventTypeList = new ArrayList();
			List<VPerformParam> VPerformParamList = new ArrayList();
			List<TManufacturerInfoInit> TManufacturerInfoInitList = new ArrayList();
			List<TCategoryMapInit> TCategoryMapInitList = new ArrayList();
			List<DefMibGrp> DefMibGrpList = new ArrayList();
			List<TModuleInfoInit> TModuleInfoInitList = new ArrayList();
			List<TOidgroupInfoInit> TOidgroupInfoInitList = new ArrayList();
			List<SyslogEventsProcess> SyslogEventsProcessList = new ArrayList();
			List<SyslogEventsProcessNs> SyslogEventsProcessNsList = new ArrayList();
			List<SnmpEventsProcess> SnmpEventsProcessList = new ArrayList();
			List<TPolicyPeriod> policydefault = new ArrayList();
			
			Map<String,Map<String,Object>> msgList = new HashMap<String, Map<String,Object>>();
			
			VMfCateDevtypeList = VMfCateDevtypeDao.findAll();
			VOidGroupList = VOidGroupDao.findAll();
			VEventTypeList = VEventTypeDao.findAll();
			VPerformParamList = VPerformParamDao.findAll();
			TManufacturerInfoInitList = TManufacturerInfoInitDao.findAll();
			TCategoryMapInitList = TCategoryMapInitDao.findAll();
			DefMibGrpList = defMibGrpDao.findAll();
			TModuleInfoInitList = TModuleInfoInitDao.findAll();
			TOidgroupInfoInitList = TOidgroupInfoInitDao.findAll();
			SyslogEventsProcessList = syslogEventsProcessDao.findAll();
			SyslogEventsProcessNsList = syslogEventsProcessNsDao.findAll();
			SnmpEventsProcessList = snmpEventsProcessDao.findAll();
			policydefault = TPolicyPeriodDao.findWhereDefaultflagEquals("1");
				
			PreparedStatement pstmt_id = null, pstmt_mrid = null,pstmt_opid= null, pstmt_modid= null, pstmt_dtid= null, pstmt_eveid= null,pstmt_oidgrpName= null;
			java.sql.ResultSet rs_id= null, rs_mrid= null,rs_opid= null, rs_modid= null, rs_dtid= null, rs_eveid= null, rs_oidgrpName= null;
			
			if((VMfCateDevtypeList != null && VMfCateDevtypeList.size()>0) ||(VOidGroupList!= null && VOidGroupList.size()>0)
					|| (VEventTypeList != null&& VEventTypeList.size()>0) || (VPerformParamList !=null && VPerformParamList.size()>0)
					|| (TManufacturerInfoInitList!=null && TManufacturerInfoInitList.size()>0) 
					|| (TCategoryMapInitList!=null && TCategoryMapInitList.size()>0) 
					|| (DefMibGrpList!=null && DefMibGrpList.size()>0) 
					|| (TModuleInfoInitList!=null && TModuleInfoInitList.size()>0) 
					|| (TOidgroupInfoInitList!=null && TOidgroupInfoInitList.size()>0)
					|| (SnmpEventsProcessList!=null && SnmpEventsProcessList.size()>0)
					|| (SyslogEventsProcessList!=null && SyslogEventsProcessList.size()>0)
					|| (SyslogEventsProcessNsList!=null && SyslogEventsProcessNsList.size()>0)
					|| (policydefault != null && policydefault.size()>0)){
				
				ServletOutputStream outputStream = response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "BaseInfoExport.xls");
				response.setContentType("application/vnd.ms-excel");				
				response.addHeader("content-type", "application/x-msdownload");
				response.setHeader("Content-Disposition", "attachement;filename=\"BaseInfoExport.xls\"");
				
				if(VMfCateDevtypeList != null && VMfCateDevtypeList.size()>0){
					exportList.put(VMfCateDevtypeDao.getTableName().toUpperCase(), VMfCateDevtypeList);

					Map<String,Object> tabName =  new HashMap<String, Object>();
					String sqls="select * from T_DEVICE_TYPE_INIT where category not in (select id from V_MF_CATE_DEVTYPE)";
					String sqls_count="select count(*) from T_DEVICE_TYPE_INIT where category not in (select id from V_MF_CATE_DEVTYPE)";
					pstmt_id =con.prepareStatement(sqls);
					rs_id =  pstmt_id.executeQuery();
					pstmt_id = con.prepareStatement(sqls_count);
					ResultSet rs = pstmt_id.executeQuery();
			 	    boolean flag = false;
			 	    rs.next();
			 	    if(rs_id!=null && rs.getInt(1)>0){
			 	    	flag=true;
			 	    	tabName.put("ID", rs_id);
			 	    	tabName.put("ID_Message", "Category in T_DEVICE_TYPE_INIT is not exist in V_MF_CATE_DEVTYPE");
			 	    }
			 	    rs.close();
			 	    
			 	    sqls="select * from T_DEVICE_TYPE_INIT where mrid not in (select mrid from V_MF_CATE_DEVTYPE)";
			 	    sqls_count="select count(*) from T_DEVICE_TYPE_INIT where mrid not in (select mrid from V_MF_CATE_DEVTYPE)";
			 	    pstmt_mrid=con.prepareStatement(sqls);
			 	    rs_mrid=  pstmt_mrid.executeQuery();
			 	    ResultSet rs_count = pstmt_id.executeQuery();
			 	    rs_count.next();
			 	   if(rs_mrid!=null && rs_count.getInt(1)>0){
			 		   flag=true;
			 	    	tabName.put("MRID", rs_mrid);
			 	    	tabName.put("MRID_Message", "MRID in T_DEVICE_TYPE_INIT is not exist in V_MF_CATE_DEVTYPE");
			 	    }
			 	   rs_count.close();
			 	   
			 	    if(flag)
			 	    	msgList.put("V_MF_CATE_DEVTYPE", tabName);
				}
				if(VOidGroupList != null && VOidGroupList.size()>0){
					exportList.put(VOidGroupDao.getTableName().toUpperCase(), VOidGroupList);
					Map<String,Object> tabName =  new HashMap<String, Object>();
					String sqls="select * from T_OIDGROUP_DETAILS_INIT where OPID not in (select opid from V_OID_GROUP)";		
					String sqls_count="select count(*) from T_OIDGROUP_DETAILS_INIT where OPID not in (select opid from V_OID_GROUP)";		
			 	    pstmt_opid=con.prepareStatement(sqls);
			 	    rs_opid=  pstmt_opid.executeQuery();
			 	    pstmt_opid = con.prepareStatement(sqls_count);
			 	    ResultSet rs = pstmt_opid.executeQuery();
			 	    rs.next();
			 	   if(rs_opid!=null && rs.getInt(1) >0){
			 	    	tabName.put("OPID", rs_opid);
			 	    	tabName.put("OPID_Message", "OPID in T_OIDGROUP_DETAILS_INIT is not exist in V_OID_GROUP");
			 	    	msgList.put("V_OID_GROUP", tabName);
			 	   }
			 	   rs.close();
				}
				if(VEventTypeList != null && VEventTypeList.size()>0){
					exportList.put(VEventTypeDao.getTableName().toUpperCase(), VEventTypeList);
					Map<String,Object> tabName =  new HashMap<String, Object>();
					String sqls="select * from T_EVENT_TYPE_INIT where MODID not in (select modid from V_EVENT_TYPE)";
					String sqls_count="select count(*) from T_EVENT_TYPE_INIT where MODID not in (select modid from V_EVENT_TYPE)";
			 	    pstmt_modid=con.prepareStatement(sqls);
			 	    rs_modid=  pstmt_modid.executeQuery();
			 	    pstmt_modid= con.prepareStatement(sqls_count);
			 	    ResultSet rs = pstmt_modid.executeQuery();
			 	    rs.next();
			 	   if(rs_modid!=null && rs.getInt(1)>0){
			 	    	tabName.put("MODID", rs_modid);
			 	    	tabName.put("MODID_Message", "MODID in T_EVENT_TYPE_INIT is not exist in V_EVENT_TYPE");
				 	    msgList.put("V_EVENT_TYPE", tabName);
			 	   }
				}
				if(VPerformParamList != null && VPerformParamList.size()>0){
					exportList.put(VPerformParamDao.getTableName().toUpperCase(), VPerformParamList);
					Map<String,Object> tabName =  new HashMap<String, Object>();
					String sqls="select * from T_EVENT_OID_INIT where eveid not in (select eventtype_EVEID from V_PERFORM_PARAM)";
					String sqls_count="select count(*) from T_EVENT_OID_INIT where eveid not in (select eventtype_EVEID from V_PERFORM_PARAM)";
			 	    pstmt_eveid=con.prepareStatement(sqls);
			 	    rs_eveid =  pstmt_eveid.executeQuery();
			 	    boolean flag = false;
			 	    pstmt_eveid= con.prepareStatement(sqls_count);
			 	    ResultSet rs = pstmt_eveid.executeQuery();
			 	    rs.next();
				 	if(rs_eveid!=null && rs.getInt(1)>0){
			 		   flag=true;
			 	    	tabName.put("EVEID", rs_eveid);
			 	    	tabName.put("EVEID_Message", "EVEID in T_EVENT_OID_INIT is not exist in V_PERFORM_PARAM");
			 	    }
				 	rs.close();
				 	
			 	    sqls="select * from T_EVENT_OID_INIT where DTID not in (select devtype_DTID from V_PERFORM_PARAM)";
			 	    sqls_count="select count(*) from T_EVENT_OID_INIT where DTID not in (select devtype_DTID from V_PERFORM_PARAM)";
			 	    pstmt_dtid=con.prepareStatement(sqls);
			 	    rs_dtid=  pstmt_dtid.executeQuery();
			 	    
			 	    pstmt_dtid= con.prepareStatement(sqls_count);
			 	    ResultSet rs_2 = pstmt_dtid.executeQuery();
			 	    rs_2.next();
				 	
			 	   if(rs_dtid!=null && rs_2.getInt(1)>0){
			 		   flag=true;
			 	    	tabName.put("DTID", rs_dtid);
			 	    	tabName.put("DTID_Message", "DTID in T_EVENT_OID_INIT is not exist in V_PERFORM_PARAM");
			 	    }
			 	   	rs_2.close();
			 	    sqls="select * from T_EVENT_OID_INIT where oidgroupname not in (select OIDGROUPNAME from V_PERFORM_PARAM)";
			 	    sqls_count="select count(*) from T_EVENT_OID_INIT where oidgroupname not in (select OIDGROUPNAME from V_PERFORM_PARAM)";
			 	    pstmt_oidgrpName=con.prepareStatement(sqls);
			 	    rs_oidgrpName=  pstmt_oidgrpName.executeQuery();
			 	    pstmt_oidgrpName= con.prepareStatement(sqls_count);
			 	    ResultSet rs_3 = pstmt_oidgrpName.executeQuery();
			 	    rs_3.next();
				 	
			 	   if(rs_oidgrpName!=null && rs_3.getInt(1)>0){
			 		   flag=true;
			 	    	tabName.put("OIDGROUPNAME", rs_oidgrpName);
			 	    	tabName.put("OIDGROUPNAME_Message", "OIDGROUPNAME in T_EVENT_OID_INIT is not exist in V_PERFORM_PARAM");
			 	    }
			 	   rs_3.close();
			 	   
			 	   if(flag)
			 	    msgList.put("V_PERFORM_PARAM", tabName);
				}
				if(TManufacturerInfoInitList != null && TManufacturerInfoInitList.size()>0){
					exportList.put(TManufacturerInfoInitDao.getTableName().toUpperCase(), TManufacturerInfoInitList);
				}
				if(TCategoryMapInitList != null && TCategoryMapInitList.size()>0){
					exportList.put(TCategoryMapInitDao.getTableName().toUpperCase(), TCategoryMapInitList);
				}
				if(DefMibGrpList != null && DefMibGrpList.size()>0){
					exportList.put(defMibGrpDao.getTableName().toUpperCase(), DefMibGrpList);
				}
				if(TModuleInfoInitList != null && TModuleInfoInitList.size()>0){
					exportList.put(TModuleInfoInitDao.getTableName().toUpperCase(), TModuleInfoInitList);
				}
				if(TOidgroupInfoInitList != null && TOidgroupInfoInitList.size()>0){
					exportList.put(TOidgroupInfoInitDao.getTableName().toUpperCase(), TOidgroupInfoInitList);
				}
				if(SnmpEventsProcessList != null && SnmpEventsProcessList.size()>0){
					exportList.put(snmpEventsProcessDao.getTableName().toUpperCase(), SnmpEventsProcessList);
				}
				if(SyslogEventsProcessList != null && SyslogEventsProcessList.size()>0){
					exportList.put(syslogEventsProcessDao.getTableName().toUpperCase(), SyslogEventsProcessList);
				}
				if(SyslogEventsProcessNsList != null && SyslogEventsProcessNsList.size()>0){
					exportList.put(syslogEventsProcessNsDao.getTableName().toUpperCase(), SyslogEventsProcessNsList);
				}
				if(policydefault != null && policydefault.size()>0){
					exportList.put(TPolicyPeriodDao.getTableName().toUpperCase(), policydefault);
				}
				
				//create a excel file	& write to excel file
				try {
					DBTOExcel.exportBaseInfo(exportList, msgList).write(outputStream);
					Log4jInit.ncsLog.info("Exported Base Info");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
				outputStream.flush();
				outputStream.close();
				if(pstmt_id!=null)
					pstmt_id.close();
				if(pstmt_mrid!=null)
					pstmt_mrid.close();
				if(pstmt_opid!=null)
					pstmt_opid.close();
				if(pstmt_modid!=null)
					pstmt_modid.close();
				if(pstmt_dtid!=null)
					pstmt_dtid.close();
				if(pstmt_eveid!=null)
					pstmt_eveid.close();
				if(pstmt_oidgrpName!=null)
					pstmt_oidgrpName.close();
				
				if(rs_id!=null)
					rs_id.close();
				if(rs_mrid!=null)
					rs_mrid.close();
				if(rs_opid!=null)
					rs_opid.close();
				if(rs_modid!=null)
					rs_modid.close();
				if(rs_dtid!=null)
					rs_dtid.close();
				if(rs_eveid!=null)
					rs_eveid.close();
				if(rs_oidgrpName!=null)
					rs_oidgrpName.close();
				if(con!=null)
					con.close();
			}else{
				message = "baseinfo.export.noData";
				model.put("message", message);				
				return new ModelAndView(getPageView(),	"model", model);
			}
		}catch(Exception e){
			e.printStackTrace();
			Log4jInit.ncsLog.error("InterruptedException in Export BaseInfo, message: " + e.getMessage());
			
			message = "baseinfo.export.failed";
			model.put("message", message);				
			return new ModelAndView(getPageView(),	"model", model);
		}
					
		message = "baseinfo.export.success";
		model.put("message", message);					
		return new ModelAndView(getPageView(),	"model", model);
		
	}

	public String getPageView() {
		return pageView;
	}


	public void setPageView(String pageView) {
		this.pageView = pageView;
	}



	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	public VMfCateDevtypeDao getVMfCateDevtypeDao() {
		return VMfCateDevtypeDao;
	}

	public void setVMfCateDevtypeDao(VMfCateDevtypeDao mfCateDevtypeDao) {
		VMfCateDevtypeDao = mfCateDevtypeDao;
	}

	public VOidGroupDao getVOidGroupDao() {
		return VOidGroupDao;
	}

	public void setVOidGroupDao(VOidGroupDao oidGroupDao) {
		VOidGroupDao = oidGroupDao;
	}

	public VEventTypeDao getVEventTypeDao() {
		return VEventTypeDao;
	}

	public void setVEventTypeDao(VEventTypeDao eventTypeDao) {
		VEventTypeDao = eventTypeDao;
	}

	public VPerformParamDao getVPerformParamDao() {
		return VPerformParamDao;
	}

	public void setVPerformParamDao(VPerformParamDao performParamDao) {
		VPerformParamDao = performParamDao;
	}

	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}

	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}

	public TCategoryMapInitDao getTCategoryMapInitDao() {
		return TCategoryMapInitDao;
	}

	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		TCategoryMapInitDao = categoryMapInitDao;
	}

	public DefMibGrpDao getDefMibGrpDao() {
		return defMibGrpDao;
	}

	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		this.defMibGrpDao = defMibGrpDao;
	}
	
	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
		jdbcTemplate = new SimpleJdbcTemplate(datasource);
	}

	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}

	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}
	

	public TOidgroupInfoInitDao getTOidgroupInfoInitDao() {
		return TOidgroupInfoInitDao;
	}

	public void setTOidgroupInfoInitDao(TOidgroupInfoInitDao oidgroupInfoInitDao) {
		TOidgroupInfoInitDao = oidgroupInfoInitDao;
	}

	public SyslogEventsProcessNsDao getSyslogEventsProcessNsDao() {
		return syslogEventsProcessNsDao;
	}

	public void setSyslogEventsProcessNsDao(
			SyslogEventsProcessNsDao syslogEventsProcessNsDao) {
		this.syslogEventsProcessNsDao = syslogEventsProcessNsDao;
	}

	public SyslogEventsProcessDao getSyslogEventsProcessDao() {
		return syslogEventsProcessDao;
	}

	public void setSyslogEventsProcessDao(
			SyslogEventsProcessDao syslogEventsProcessDao) {
		this.syslogEventsProcessDao = syslogEventsProcessDao;
	}

	public SnmpEventsProcessDao getSnmpEventsProcessDao() {
		return snmpEventsProcessDao;
	}

	public void setSnmpEventsProcessDao(SnmpEventsProcessDao snmpEventsProcessDao) {
		this.snmpEventsProcessDao = snmpEventsProcessDao;
	}

	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}

	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}
	


}
