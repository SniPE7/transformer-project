package com.ibm.ncs.web.policyapply;

import java.io.FileWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.export.IcmpPolicyExporter;
import com.ibm.ncs.export.IcmpPolicyExporterImpl;
import com.ibm.ncs.export.SnmpPolicyExporter;
import com.ibm.ncs.export.SnmpPolicyExporterImpl;
import com.ibm.ncs.export.SrcTypeExporter;
import com.ibm.ncs.export.SrcTypeExporterImpl;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.web.policytakeeffect.TakeEffectProcess;

public class ExportXMLFileController implements Controller {
	
String pageView;
DataSource datasource;
	
TakeEffectProcess TakeEffectProcess;
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		Map model = new HashMap();
		////TakeEffectProcess progress = new TakeEffectProcess();
		//progress.init();      //asynchornize way, new thread
		//progress.startProcess();

		
//		try{
//			System.out.println("export the xml files... icmp.xml ; snmp.xml; srctype.xml");
//			Connection connection = datasource.getConnection();
//			
//		    IcmpPolicyExporter exp = new IcmpPolicyExporterImpl();
//		    exp.setServerID("BF");
//		    exp.setJdbcConnection(connection);
//		    exp.export(new FileWriter("/tmp/icmp.xml"), null);
//		    
//		    SnmpPolicyExporter exp1 = new SnmpPolicyExporterImpl();
//		    exp1.setServerID("BF");
//		    exp1.setJdbcConnection(connection);
//		    exp1.export(new FileWriter("/tmp/snmp.xml"), null);
//		    
//		    SrcTypeExporter exp2 = new SrcTypeExporterImpl();
//		    exp2.setJdbcConnection(connection);
//		    exp2.export(new FileWriter("/tmp/SrcType.xml"), null);
//		} catch (Exception e) {
//			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
//			e.printStackTrace();
//		}

		TakeEffectProcess.getStat().clear();
		TakeEffectProcess.setDone(false);
		try {
			TakeEffectProcess.stopProcess();
		} catch (Exception e) {
		}
		TakeEffectProcess.init();
		TakeEffectProcess.startProcess();
		//TakeEffectProcess.operations();  //or, synchornized same thread
		
		Map stat = TakeEffectProcess.getStat();
		model.put("stat", stat);
		request.getSession().setAttribute("progress", TakeEffectProcess);
		request.getSession().setAttribute("progressInfo", stat);
		return new ModelAndView(getPageView(), "model", model);
	}						
	
	public static void display(TCategoryMapInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getName() );
		buf.append( ", " );
		buf.append( dto.getFlag() );
		System.out.println( buf.toString() );
	}









	public String getPageView() {
		return pageView;
	}









	public void setPageView(String pageView) {
		this.pageView = pageView;
	}









	public DataSource getDatasource() {
		return datasource;
	}









	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public TakeEffectProcess getTakeEffectProcess() {
		return TakeEffectProcess;
	}

	public void setTakeEffectProcess(TakeEffectProcess takeEffectProcess) {
		TakeEffectProcess = takeEffectProcess;
	}





}
