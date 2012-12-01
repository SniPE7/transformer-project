package com.ibm.ncs.web.policytakeeffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.EventsAttentionDao;
import com.ibm.ncs.model.dao.IcmpThresholdsDao;
import com.ibm.ncs.model.dao.LinesEventsNotcareDao;
import com.ibm.ncs.model.dao.PolicySyslogDao;
import com.ibm.ncs.model.dao.SnmpThresholdsDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.SyslogEventsProcessNsPk;
import com.ibm.ncs.model.dto.SyslogEventsProcessPk;
import com.ibm.ncs.model.exceptions.LinesEventsNotcareDaoException;
import com.ibm.ncs.model.exceptions.PolicySyslogDaoException;
import com.ibm.ncs.util.Log4jInit;

public class EffectGeneratorController implements Controller {
	
String pageView;
DataSource datasource;
	
PolicySyslogDao PolicySyslogDao;	
SyslogEventsProcessDao SyslogEventsProcessDao;
SyslogEventsProcessNsDao SyslogEventsProcessNsDao;
EventsAttentionDao EventsAttentionDao;
LinesEventsNotcareDao LinesEventsNotcareDao;
TimeFrameConverter timeframeConverter;
SnmpThresholdsDao SnmpThresholdsDao;
IcmpThresholdsDao IcmpThresholdsDao;
TPolicyPeriodDao TPolicyPeriodDao;
TakeEffectProcess TakeEffectProcess;
String message;


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		message = "";
//			TakeEffectProcess progress = new TakeEffectProcess();
//			progress.startProcess();
//			message =  progress.getMessage();
//			Map model = progress.getModel();
		
			Map model = new HashMap();
			//generate xml files "<%=request.getContextPath()%>/secure/policyapply/exportxmlfile.wss";
			
			
			//prepare data: time frame re-fill btime and etime for all action;
			//prepare data: clear target tables before insertion for 4 xls tables;
			//update syslog process tables on attention flag=1 when severity<=7
			
			int i=0;
			model.put(""+i++, "prepare the data.");
			//
			timeframeConverter.fillTimeFrameTableBtimeEtime();
			

			settingSyslogEventsFlags();
	

			settingSyslogEventsNsFlags();
		
			onEventsAttention();
			
			onLinesEventsNotCare();
			
			onSnmpThresholds();
			
			onIcmpThresholds();
			
			
		
		return new ModelAndView(getPageView(), "model", model);
	}










	private void onLinesEventsNotCare() {
		try {
			LinesEventsNotcareDao.deleteAll();
			LinesEventsNotcareDao.insertEffect();
		} catch (LinesEventsNotcareDaoException e) {
			e.printStackTrace();
			Log4jInit.ncsLog.error(this.getClass().getName() + "There error on lines Events NotCare effections process.\n");
		}
		
		
	}

	private void onSnmpThresholds() {
		try {
			SnmpThresholdsDao.deleteAll();
			SnmpThresholdsDao.insertEffect();
		} catch (Exception e) {
			e.printStackTrace();
			Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on SNmp thresholds effections process.\n");
		}
		
	}

	private void onIcmpThresholds() {
		try {
			IcmpThresholdsDao.deleteAll();
			IcmpThresholdsDao.insertEffect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on Icmp thresholds effections process.\n");
		}
		
	}

	private void onEventsAttention() {
		try {
			EventsAttentionDao.deleteAll();
			EventsAttentionDao.insertEffect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on Events attention effections process.\n");
		}
		
	}

	private void settingSyslogEventsFlags() throws PolicySyslogDaoException {
		try {
			SyslogEventsProcessDao.resetAllAttentionFlags();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			SyslogEventsProcessDao.resetAllNotCareFlags();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<PolicySyslog> devlst = PolicySyslogDao.findDeviceEvents();
		for (PolicySyslog dto: devlst){
			
			try {
				String mark = dto.getMark();
				String manufacture = dto.getManufacture();
				SyslogEventsProcessPk  pk = new SyslogEventsProcessPk(mark, manufacture);
				SyslogEventsProcess dd = SyslogEventsProcessDao.findByPrimaryKey(pk);
				dd.setAttentionflag(1);
				SyslogEventsProcessDao.update(pk, dd);
			} catch (Exception e) {
				e.printStackTrace();
				Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on syslog events process at device flags settings process.\n"+dto);
			}
			
		}
			
		List<PolicySyslog> portlst = PolicySyslogDao.findPortEvents();
		for (PolicySyslog dto: portlst){
			
			try {
				String mark = dto.getMark();
				String manufacture = dto.getManufacture();
				SyslogEventsProcessPk  pk = new SyslogEventsProcessPk(mark, manufacture);
				SyslogEventsProcess dd = SyslogEventsProcessDao.findByPrimaryKey(pk);
				if (dto.getSeverity1()<=7) {
					dd.setAttentionflag(1);
				}else {
					dd.setNotcareflag(1);
				}
				SyslogEventsProcessDao.update(pk, dd);
			} catch (Exception e) {
				e.printStackTrace();
				Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on syslog events process setting flags at port settings process.\n"+dto);
			}
			
		}
	}						
	
	private void settingSyslogEventsNsFlags() throws PolicySyslogDaoException {
		try {
			SyslogEventsProcessNsDao.resetAllAttentionFlags();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			SyslogEventsProcessNsDao.resetAllNotCareFlags();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<PolicySyslog> devlst = PolicySyslogDao.findDeviceEvents();
		for (PolicySyslog dto: devlst){
			
			try {
				String mark = dto.getMark();
				String manufacture = dto.getManufacture();
				SyslogEventsProcessNsPk  pk = new SyslogEventsProcessNsPk(mark, manufacture);
				SyslogEventsProcessNs dd = SyslogEventsProcessNsDao.findByPrimaryKey(pk);
				dd.setAttentionflag(1);
				SyslogEventsProcessNsDao.update(pk, dd);
			} catch (Exception e) {
				e.printStackTrace();
				Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on syslog events process NS flags device settings process.\n"+dto);
			}
			
		}
			
		List<PolicySyslog> portlst = PolicySyslogDao.findPortEvents();
		for (PolicySyslog dto: portlst){
			
			try {
				String mark = dto.getMark();
				String manufacture = dto.getManufacture();
				SyslogEventsProcessNsPk  pk = new SyslogEventsProcessNsPk(mark, manufacture);
				SyslogEventsProcessNs dd = SyslogEventsProcessNsDao.findByPrimaryKey(pk);
				if (dto.getSeverity1()<=7) {
					dd.setAttentionflag(1);
				}else {
					dd.setNotcareflag(1);
				}
				SyslogEventsProcessNsDao.update(pk, dd);
			} catch (Exception e) {
				e.printStackTrace();
				Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on syslog events process NS flags port settings process.\n"+dto);
			}
			
		}
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










	public PolicySyslogDao getPolicySyslogDao() {
		return PolicySyslogDao;
	}










	public void setPolicySyslogDao(PolicySyslogDao policySyslogDao) {
		PolicySyslogDao = policySyslogDao;
	}










	public SyslogEventsProcessDao getSyslogEventsProcessDao() {
		return SyslogEventsProcessDao;
	}










	public void setSyslogEventsProcessDao(
			SyslogEventsProcessDao syslogEventsProcessDao) {
		SyslogEventsProcessDao = syslogEventsProcessDao;
	}










	public SyslogEventsProcessNsDao getSyslogEventsProcessNsDao() {
		return SyslogEventsProcessNsDao;
	}










	public void setSyslogEventsProcessNsDao(
			SyslogEventsProcessNsDao syslogEventsProcessNsDao) {
		SyslogEventsProcessNsDao = syslogEventsProcessNsDao;
	}










	public EventsAttentionDao getEventsAttentionDao() {
		return EventsAttentionDao;
	}










	public void setEventsAttentionDao(EventsAttentionDao eventsAttentionDao) {
		EventsAttentionDao = eventsAttentionDao;
	}










	public LinesEventsNotcareDao getLinesEventsNotcareDao() {
		return LinesEventsNotcareDao;
	}










	public void setLinesEventsNotcareDao(LinesEventsNotcareDao linesEventsNotcareDao) {
		LinesEventsNotcareDao = linesEventsNotcareDao;
	}










	public TimeFrameConverter getTimeframeConverter() {
		return timeframeConverter;
	}










	public void setTimeframeConverter(TimeFrameConverter timeframeConverter) {
		this.timeframeConverter = timeframeConverter;
	}










	public String getMessage() {
		return message;
	}










	public void setMessage(String message) {
		this.message = message;
	}










	public SnmpThresholdsDao getSnmpThresholdsDao() {
		return SnmpThresholdsDao;
	}










	public void setSnmpThresholdsDao(SnmpThresholdsDao snmpThresholdsDao) {
		SnmpThresholdsDao = snmpThresholdsDao;
	}










	public IcmpThresholdsDao getIcmpThresholdsDao() {
		return IcmpThresholdsDao;
	}










	public void setIcmpThresholdsDao(IcmpThresholdsDao icmpThresholdsDao) {
		IcmpThresholdsDao = icmpThresholdsDao;
	}










	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}










	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}










	public TakeEffectProcess getTakeEffectProcess() {
		return TakeEffectProcess;
	}










	public void setTakeEffectProcess(TakeEffectProcess takeEffectProcess) {
		TakeEffectProcess = takeEffectProcess;
	}
}
