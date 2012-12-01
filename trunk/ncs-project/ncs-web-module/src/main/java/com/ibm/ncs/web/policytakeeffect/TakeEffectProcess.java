package com.ibm.ncs.web.policytakeeffect;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.ibm.ncs.export.IcmpPolicyExporter;
import com.ibm.ncs.export.IcmpPolicyExporterImpl;
import com.ibm.ncs.export.SnmpPolicyExporter;
import com.ibm.ncs.export.SnmpPolicyExporterImpl;
import com.ibm.ncs.export.SrcTypeExporter;
import com.ibm.ncs.export.SrcTypeExporterImpl;
import com.ibm.ncs.model.dao.EventsAttentionDao;
import com.ibm.ncs.model.dao.IcmpThresholdsDao;
import com.ibm.ncs.model.dao.LinesEventsNotcareDao;
import com.ibm.ncs.model.dao.PolicySyslogDao;
import com.ibm.ncs.model.dao.PredefmibPolMapDao;
import com.ibm.ncs.model.dao.SnmpThresholdsDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dao.TPpDevDao;
import com.ibm.ncs.model.dao.TPpPortDao;
import com.ibm.ncs.model.dao.TServerInfoDao;
import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.SyslogEventsProcessNsPk;
import com.ibm.ncs.model.dto.SyslogEventsProcessPk;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.dto.TPolicyPeriodPk;
import com.ibm.ncs.model.dto.TServerInfo;
import com.ibm.ncs.model.dto.TSvrmodMap;
import com.ibm.ncs.model.exceptions.LinesEventsNotcareDaoException;
import com.ibm.ncs.model.exceptions.PolicySyslogDaoException;
import com.ibm.ncs.model.exceptions.PredefmibPolMapDaoException;
import com.ibm.ncs.model.exceptions.TDevpolMapDaoException;
import com.ibm.ncs.model.exceptions.TLinepolMapDaoException;
import com.ibm.ncs.model.exceptions.TPolicyPeriodDaoException;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.TimeToStr;
import com.ibm.ncs.util.db.DbRestore;

public class TakeEffectProcess {
	
	static Logger logger = Logger.getLogger(TakeEffectProcess.class);

	DataSource datasource;
	PolicySyslogDao PolicySyslogDao;	
	SyslogEventsProcessDao SyslogEventsProcessDao;
	SyslogEventsProcessNsDao SyslogEventsProcessNsDao;
	EventsAttentionDao EventsAttentionDao;
	LinesEventsNotcareDao LinesEventsNotcareDao;
	TPpDevDao TPpDevDao;
	TPpPortDao TPpPortDao;
	TimeFrameConverter timeframeConverter;
	public TServerInfoDao getTServerInfoDao() {
		return TServerInfoDao;
	}

	public void setTServerInfoDao(TServerInfoDao serverInfoDao) {
		TServerInfoDao = serverInfoDao;
	}


	SnmpThresholdsDao SnmpThresholdsDao;
	IcmpThresholdsDao IcmpThresholdsDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	TServerInfoDao TServerInfoDao;
	PredefmibPolMapDao PredefmibPolMapDao;
	TLinepolMapDao TLinepolMapDao;
	TDevpolMapDao TDevpolMapDao;
	
	String message;
	boolean done;
	
	Thread process;
	Map<String, String> stat = new TreeMap<String, String>();

	public TakeEffectProcess() {
		//init();
	}

	public void init() {
		process = new Thread(){
			public void run(){
				operations();
			}
		};
	}
	
	public void startProcess(){
		process.start();
	}
	
	public void stopProcess(){
		process.interrupt();
		//process.stop();
		//process.destroy();
	}

	public void operations(){
		System.out.println("TakeEffectProcess start operation...");
		int steps=1 ;
		done=false;
		stat.clear();
		ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");
		String preid //= (String)prop.getObject("export.xml.server.pre.id");
					= "";  //ICBC rule to get the server pre id.
		String xmldir= (String)prop.getObject("export.xml.generate.dir");
		xmldir = (xmldir==null||xmldir.trim().equals(""))?"/tmp/":xmldir;
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ; 
	
		//stat.put(setKS(steps++), "开始进行  文件处理 :"+sdf.format(new Date()));
		stat.put(setKS(steps++), "开始准备数据.");

		stat.put(setKS(steps++), sdf.format(new Date())+" 开始清理策略应用中无映射引用的数据（设备，端口，私有index）...");
		logger.info(" 开始清理策略应用中无映射引用的数据（设备，端口，私有index）...");
		removeNoUsedData();
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成清理策略应用中无映射引用的数据（设备，端口，私有index）");
		logger.info(" 完成清理策略应用中无映射引用的数据（设备，端口，私有index） ");
		
		stat.put(setKS(steps++), "开始准备前缀名server.pre.id数据.");
		try{
			List<TServerInfo> server =  TServerInfoDao.findAll();
			
			if(server.size()>0) {
				String nmsname = server.get(0).getNmsname();
				stat.put(setKS(steps++), "server数据 ="+nmsname);
				preid = nmsname.substring(2,4);
				logger.info("ICBC rule...server.pre.id="+preid);
				stat.put(setKS(steps++), "前缀名server.pre.id数据 ="+preid);
			}
		}catch(Exception e){
			logger.error("error in get server pre id..."+e.getMessage());
			e.printStackTrace();
		}
		//开始准备时段数据
		stat.put(setKS(steps++), "开始准备时段数据.");
		try {
			timeframeConverter.fillTimeFrameTableBtimeEtime();
		} catch (Exception e3) {
			logger.error("error in timeframeConverter ..."+e3.getMessage());
			e3.printStackTrace();
		}
		//sleeping2000();
		Connection connectionDS = null;
		try {
			logger.info("export the xml files... icmp.xml ; snmp.xml; srctype.xml");
			 connectionDS = datasource.getConnection();
			 //connectionDS = DataSourceUtils.getConnection(datasource);
			stat.put(setKS(steps++), sdf.format(new Date())+" 开始进行 ICMP xml 文件处理...");
			logger.info(" 开始进行 ICMP xml 文件处理...");
			try {
				IcmpPolicyExporter exp = new IcmpPolicyExporterImpl();
				exp.setServerID(preid);
				exp.setJdbcConnection(connectionDS);
				exp.export(new FileWriter(xmldir+"icmp.xml"), null);
			} catch (IOException e2) {
				message = "error in generate icmp.xml...IOException ";
				logger.error("error in generate icmp.xml..."+e2.getMessage());
				e2.printStackTrace();
			} catch (Exception e2) {
				message = "Error in generate icmp.xml...Exception ";
				logger.error(message+e2.getMessage());
				e2.printStackTrace();
			}
			stat.put(setKS(steps++), sdf.format(new Date())+" 完成 ICMP xml 生成文件在 "+xmldir+"icmp.xml");
			logger.info(" 完成 ICMP xml 生成文件在, "+xmldir+"icmp.xml");
			stat.put(setKS(steps++), sdf.format(new Date())+" process Snmp xml ");
			logger.info(" process Snmp xml ");
			//sleeping2000();
			stat.put(setKS(steps++), sdf.format(new Date())+" 开始进行 Snmp xml 文件处理...");
			logger.info(" 开始进行 Snmp xml 文件处理...");
			try {
				SnmpPolicyExporter exp1 = new SnmpPolicyExporterImpl();
				exp1.setServerID(preid);
				exp1.setJdbcConnection(connectionDS);
				exp1.export(new FileWriter(xmldir+"snmp.xml"), null);
			} catch (IOException e1) {
				message = "error in generate snmp.xml...IOException ";
				logger.error("error in generate snmp.xml..."+e1.getMessage());
				e1.printStackTrace();
			} catch (Exception e1) {
				message = "error in generate snmp.xml...Exception ";
				logger.error("error in generate snmp.xml..."+e1.getMessage());
				e1.printStackTrace();
			}
			stat.put(setKS(steps++), sdf.format(new Date())+"  完成 xml 生成文件在 "+ xmldir+"snmp.xml");
			logger.info("  完成 xml 生成文件在, "+ xmldir+"snmp.xml");
			//sleeping2000();
			stat.put(setKS(steps++), sdf.format(new Date())+" 开始进行 SrcType 文件处理...");
			logger.info(" 开始进行 SrcType 文件处理...");
			try {
				SrcTypeExporter exp2 = new SrcTypeExporterImpl();
				exp2.setJdbcConnection(connectionDS);
				exp2.export(new FileWriter(xmldir+"SrcType"), null);
			} catch (IOException e) {
				message = "error in generate srctype...IOException ";
				logger.error("error in generate srctype.xml..."+e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				message = "error in generate srcType...Exception ";
				logger.error("error in generate srcType..."+e.getMessage());
				e.printStackTrace();
			}
			stat.put(setKS(steps++), sdf.format(new Date())+" 完成 SrcType  生成文件在 "+xmldir+"SrcType");
			logger.info(" 完成 SrcType  生成文件在 "+xmldir+"SrcType");
			//sleeping2000();
			//System.out.println(model);
		} catch (SQLException e) {
			message = "error in generate 3 xml files... icmp.xml ; snmp.xml; srctype ...SQLException ";
			logger.error(message+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			message = "error in generate 3 xml files... icmp.xml ; snmp.xml; srctype ...Exception ";
			logger.error(message+e.getMessage());
			e.printStackTrace();
		
		}finally{
			try {
				if (connectionDS != null)connectionDS.close();
			} catch (SQLException e) {
			}
		}
		stat.put(setKS(steps++), sdf.format(new Date())+" 开始进行 Syslog events process, 设置使用标志.");
		logger.info(" 开始进行 Syslog events process, 设置使用标志.");
		settingSyslogEventsFlags();
		stat.put(setKS(steps++), sdf.format(new Date())+" Syslog events process, 标志设置完成.");
		logger.info(" Syslog events process, 标志设置完成.");
		//sleeping2000();
		
		stat.put(setKS(steps++), sdf.format(new Date())+" 开始进行 Syslog events process NS, 设置使用标志.");
		logger.info( " 开始进行 Syslog events process NS, 设置使用标志.");
		settingSyslogEventsNsFlags();
		stat.put(setKS(steps++), sdf.format(new Date())+"  Syslog events process NS, 设置标志完成.");
		logger.info( "  Syslog events process NS, 设置标志完成.");
		//sleeping2000();
		
		stat.put(setKS(steps++), sdf.format(new Date())+" 开始进行 Syslog events attention 的处理...");
		logger.info(" 开始进行 Syslog events attention 的处理...");
		onEventsAttention();
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成 Syslog events attention ");
		logger.info(" 完成 Syslog events attention ");
		//sleeping2000();
		
		stat.put(setKS(steps++), sdf.format(new Date())+" 开始处理 Syslog events LineEventsNotCare...");
		logger.info(" 开始处理 Syslog events LineEventsNotCare...");
		onLinesEventsNotCare();
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成 Syslog events LineEventsNotCare...");
		logger.info(" 完成 Syslog events LineEventsNotCare...");
		//sleeping2000();
		
		stat.put(setKS(steps++), sdf.format(new Date())+" 开始进行 SNMP thresholds 处理...");
		logger.info(" 开始进行 SNMP thresholds 处理...");
		onSnmpThresholds();
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成 SNMP thresholds ");
		logger.info(" 完成 SNMP thresholds ");
		//sleeping2000();
		
		stat.put(setKS(steps++), sdf.format(new Date())+" 开始进行 ICMP thresholds 处理...");
		logger.info(" 开始进行 ICMP thresholds 处理...");
		onIcmpThresholds();
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成 ICMP thresholds ");
		logger.info(" 完成 ICMP thresholds ");
		//sleeping2000();
		//System.out.println(model);
		

		stat.put(setKS(steps++), sdf.format(new Date())+" 开始进行 PP_DEV 处理...");
		logger.info(" 开始进行 PP_DEV 处理...");
		onPpDev();
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成 PP_DEV ");
		logger.info(" 完成 PP_DEV ");
		//sleeping2000();
		//System.out.println(model);
		

		stat.put(setKS(steps++), sdf.format(new Date())+" 开始进行 PP_PORT 处理...");
		logger.info(" 开始进行 PP_PORT 处理...");
		onPpPort();
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成 PP_PORT ");
		logger.info(" 完成 PP_PORT ");
		//sleeping2000();
		//System.out.println(model);
		
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成生成监控配置文件!");
		logger.info( " 完成生成监控配置文件!");
		//System.out.println(stat);
		
		done=true;
		//model.put("done", "done");
	}






	private void removeNoUsedData() {
		try {
			TDevpolMapDao.removeNoUseData();
		} catch (TDevpolMapDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4jInit.ncsLog.error(this.getClass().getName() + "There error on [TDevpolMapDao.removeNoUseData();] in effections process.\n");
		}
		try {
			TLinepolMapDao.removeNoUseData();
		} catch (TLinepolMapDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4jInit.ncsLog.error(this.getClass().getName() + "There error on [TLinepolMapDao.removeNoUseData();] in effections process.\n");
		}
		try {
			PredefmibPolMapDao.removeNoUseData();
		} catch (PredefmibPolMapDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4jInit.ncsLog.error(this.getClass().getName() + "There error on [PredefmibPolMapDao.removeNoUseData();] in effections process.\n");
		}
	}

	private String setKS(int steps) {		
		String ss = (steps <10 ?"0":"")+steps;
		return ss;
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
	
	private void onPpPort() {
		try {
			TPpPortDao.deleteAll();
			TPpPortDao.insertEffect();
		} catch (Exception e) {
			e.printStackTrace();
			Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on T_PP_PORT effections process.\n");
		}
		
	}
	
	private void onPpDev() {
		try {
			TPpDevDao.deleteAll();
			TPpDevDao.insertEffect();
		} catch (Exception e) {
			e.printStackTrace();
			Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on T_PP_DEV effections process.\n");
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

	private void settingSyslogEventsFlags()  {
		try {
			SyslogEventsProcessDao.resetAllAttentionFlags();
		} catch (Exception e1) {
			message = "Error in SyslogEventsProcessDao.resetAllAttentionFlags...Exception ";
			logger.error("error in SyslogEventsProcessDao.resetAllAttentionFlags..."+e1.getMessage());
		}
		try {
			SyslogEventsProcessDao.resetAllNotCareFlags();
		} catch (Exception e1) {
			message = "Error in SyslogEventsProcessDao.resetAllNotCareFlags();...Exception ";
			logger.error("error in SyslogEventsProcessDao.resetAllNotCareFlags();..."+e1.getMessage());
		}
		try {
			List<PolicySyslog> devlst = PolicySyslogDao.findDeviceEvents();
			for (PolicySyslog dto: devlst){
				
				try {
					String mark = dto.getMark();
					String manufacture = dto.getManufacture();
					SyslogEventsProcessPk  pk = new SyslogEventsProcessPk(mark, manufacture);
					SyslogEventsProcess dd = SyslogEventsProcessDao.findByPrimaryKey(pk);
					if(dd==null){continue;}
					dd.setAttentionflag(1);
					SyslogEventsProcessDao.update(pk, dd);
				} catch (Exception e) {
					message = "Error in SyslogEventsProcessDao.resetAllAttentionFlags...Exception ";
					logger.error("error in SyslogEventsProcessDao.resetAllAttentionFlags..."+e.getMessage());
					e.printStackTrace();
					Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on syslog events process at device flags settings process.\n"+dto);
				}
				
			}
		} catch (PolicySyslogDaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		try {
			List<PolicySyslog> portlst = PolicySyslogDao.findPortEvents();
			for (PolicySyslog dto: portlst){
				
				try {
					String mark = dto.getMark();
					String manufacture = dto.getManufacture();
					SyslogEventsProcessPk  pk = new SyslogEventsProcessPk(mark, manufacture);
					SyslogEventsProcess dd = SyslogEventsProcessDao.findByPrimaryKey(pk);
					if(dd==null){continue;}
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
		} catch (PolicySyslogDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}						
	
	private void settingSyslogEventsNsFlags()  {
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
		try {
			List<PolicySyslog> devlst = PolicySyslogDao.findDeviceEvents();
			for (PolicySyslog dto: devlst){
				
				try {
					String mark = dto.getMark();
					String manufacture = dto.getManufacture();
					SyslogEventsProcessNsPk  pk = new SyslogEventsProcessNsPk(mark, manufacture);
					SyslogEventsProcessNs dd = SyslogEventsProcessNsDao.findByPrimaryKey(pk);
					if(dd==null){continue;}
					dd.setAttentionflag(1);
					SyslogEventsProcessNsDao.update(pk, dd);
				} catch (Exception e) {
					e.printStackTrace();
					Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on syslog events process NS flags device settings process.\n"+dto);
				}
				
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		try {
			List<PolicySyslog> portlst = PolicySyslogDao.findPortEvents();
			for (PolicySyslog dto: portlst){
				
				try {
					String mark = dto.getMark();
					String manufacture = dto.getManufacture();
					SyslogEventsProcessNsPk  pk = new SyslogEventsProcessNsPk(mark, manufacture);
					SyslogEventsProcessNs dd = SyslogEventsProcessNsDao.findByPrimaryKey(pk);
					if(dd==null){continue;}
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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

	public Thread getProcess() {
		return process;
	}

	public void setProcess(Thread process) {
		this.process = process;
	}



	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}

	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Map getStat() {
		return stat;
	}

	public void setStat(Map stat) {
		this.stat = stat;
	}


    private void sleeping2000(){
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public PredefmibPolMapDao getPredefmibPolMapDao() {
		return PredefmibPolMapDao;
	}

	public void setPredefmibPolMapDao(PredefmibPolMapDao predefmibPolMapDao) {
		PredefmibPolMapDao = predefmibPolMapDao;
	}

	public TLinepolMapDao getTLinepolMapDao() {
		return TLinepolMapDao;
	}

	public void setTLinepolMapDao(TLinepolMapDao linepolMapDao) {
		TLinepolMapDao = linepolMapDao;
	}

	public TDevpolMapDao getTDevpolMapDao() {
		return TDevpolMapDao;
	}

	public void setTDevpolMapDao(TDevpolMapDao devpolMapDao) {
		TDevpolMapDao = devpolMapDao;
	}

	public TPpDevDao getTPpDevDao() {
		return TPpDevDao;
	}

	public void setTPpDevDao(TPpDevDao ppDevDao) {
		TPpDevDao = ppDevDao;
	}

	public TPpPortDao getTPpPortDao() {
		return TPpPortDao;
	}

	public void setTPpPortDao(TPpPortDao ppPortDao) {
		TPpPortDao = ppPortDao;
	}



}
