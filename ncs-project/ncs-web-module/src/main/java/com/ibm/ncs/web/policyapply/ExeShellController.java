package com.ibm.ncs.web.policyapply;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import com.ibm.ncs.web.policytakeeffect.ExeShellProcess;

public class ExeShellController implements Controller {
	
String pageView;
DataSource datasource;
	
ExeShellProcess ExeShellProcess;
	

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

		ExeShellProcess.getStat().clear();
		ExeShellProcess.setDone(false);
		try {
			ExeShellProcess.stopProcess();
		} catch (Exception e) {
		}
		ExeShellProcess.init();
		ExeShellProcess.startProcess();
		//TakeEffectProcess.operations();  //or, synchornized same thread
		
		Map stat = ExeShellProcess.getStat();
		stat.clear();
		model.put("stat", stat);
		request.getSession().setAttribute("progress", ExeShellProcess);
		request.getSession().setAttribute("progressInfo", stat);
		return new ModelAndView(getPageView(), "model", model);
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

	public ExeShellProcess getExeShellProcess() {
		return ExeShellProcess;
	}

	public void setExeShellProcess(ExeShellProcess exeShellProcess) {
		ExeShellProcess = exeShellProcess;
	}






}
