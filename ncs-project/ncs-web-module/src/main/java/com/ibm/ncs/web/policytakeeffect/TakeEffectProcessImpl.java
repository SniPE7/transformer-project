package com.ibm.ncs.web.policytakeeffect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ibm.ncs.export.IcmpPolicyExporter;
import com.ibm.ncs.export.IcmpPolicyExporterImpl;
import com.ibm.ncs.export.SnmpPolicyExporter;
import com.ibm.ncs.export.SnmpPolicyExporterImpl;
import com.ibm.ncs.export.SrcTypeExporter;
import com.ibm.ncs.export.SrcTypeExporterImpl;
import com.ibm.ncs.model.dao.EventsAttentionDao;
import com.ibm.ncs.model.dao.IcmpThresholdsDao;
import com.ibm.ncs.model.dao.LinesEventsNotcareDao;
import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.PolicySyslogDao;
import com.ibm.ncs.model.dao.PredefmibPolMapDao;
import com.ibm.ncs.model.dao.SnmpThresholdsDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.dao.TPpDevDao;
import com.ibm.ncs.model.dao.TPpPortDao;
import com.ibm.ncs.model.dao.TServerInfoDao;
import com.ibm.ncs.model.dao.TServerNodeDao;
import com.ibm.ncs.model.dao.TTakeEffectHistoryDao;
import com.ibm.ncs.model.dao.TUserDao;
import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.SyslogEventsProcessNsPk;
import com.ibm.ncs.model.dto.SyslogEventsProcessPk;
import com.ibm.ncs.model.dto.TServerInfo;
import com.ibm.ncs.model.dto.TServerNode;
import com.ibm.ncs.model.dto.TTakeEffectHistory;
import com.ibm.ncs.model.dto.TUser;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.model.exceptions.LinesEventsNotcareDaoException;
import com.ibm.ncs.model.exceptions.PolicySyslogDaoException;
import com.ibm.ncs.model.exceptions.PredefmibPolMapDaoException;
import com.ibm.ncs.model.exceptions.TDevpolMapDaoException;
import com.ibm.ncs.model.exceptions.TLinepolMapDaoException;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class TakeEffectProcessImpl implements TakeEffectProcess {

	static Logger logger = Logger.getLogger(TakeEffectProcessImpl.class);

	private DataSource datasource;

	private TTakeEffectHistoryDao takeEffectHistoryDao;
	private TServerNodeDao serverNodeDao;
	private TPolicyPublishInfoDao policyPublishInfoDao;
	private TUserDao userDao;
	private GenPkNumber genPkNumber;

	private PolicySyslogDao PolicySyslogDao;
	private SyslogEventsProcessDao SyslogEventsProcessDao;
	private SyslogEventsProcessNsDao SyslogEventsProcessNsDao;
	private EventsAttentionDao EventsAttentionDao;
	private LinesEventsNotcareDao LinesEventsNotcareDao;
	private TPpDevDao TPpDevDao;
	private TPpPortDao TPpPortDao;
	private TimeFrameConverter timeframeConverter;

	private SnmpThresholdsDao SnmpThresholdsDao;
	private IcmpThresholdsDao IcmpThresholdsDao;
	private TPolicyPeriodDao TPolicyPeriodDao;
	private TServerInfoDao TServerInfoDao;
	private PredefmibPolMapDao PredefmibPolMapDao;
	private TLinepolMapDao TLinepolMapDao;
	private TDevpolMapDao TDevpolMapDao;

	private String message;
	private boolean done = true;

	private boolean success = true;

	private String operator = null;

	Thread process;

	private StepTracker stepTracker = new StepTracker();

	public TakeEffectProcessImpl() {
		// init();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public TPolicyPublishInfoDao getPolicyPublishInfoDao() {
		return policyPublishInfoDao;
	}

	public void setPolicyPublishInfoDao(TPolicyPublishInfoDao policyPublishInfoDao) {
		this.policyPublishInfoDao = policyPublishInfoDao;
	}

	public TServerInfoDao getTServerInfoDao() {
		return TServerInfoDao;
	}

	public void setTServerInfoDao(TServerInfoDao serverInfoDao) {
		TServerInfoDao = serverInfoDao;
	}

	public TTakeEffectHistoryDao getTakeEffectHistoryDao() {
		return takeEffectHistoryDao;
	}

	public void setTakeEffectHistoryDao(TTakeEffectHistoryDao takeEffectHistoryDao) {
		this.takeEffectHistoryDao = takeEffectHistoryDao;
	}

	public TServerNodeDao getServerNodeDao() {
		return serverNodeDao;
	}

	public void setServerNodeDao(TServerNodeDao serverNodeDao) {
		this.serverNodeDao = serverNodeDao;
	}

	public TUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(TUserDao userDao) {
		this.userDao = userDao;
	}

	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ncs.web.policytakeeffect.TakeEffectProcess#init()
	 */
	public synchronized void init() {
		this.stepTracker.start();
		this.done = false;
		process = new Thread() {
			public void run() {
				operations();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ncs.web.policytakeeffect.TakeEffectProcess#startProcess()
	 */
	public synchronized void startProcess() {
		process.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ncs.web.policytakeeffect.TakeEffectProcess#stopProcess()
	 */
	public synchronized void stopProcess() {
		if (process != null) {
			process.interrupt();
		}
		// process.stop();
		// process.destroy();
	}

	private void operations() {

		try {
			this.stepTracker.start();

			success = true;

			System.out.println("TakeEffectProcess start operation...");
			// this.stepTracker.writeState("开始进行  文件处理 :"+sdf.format(new Date()));
			this.stepTracker.writeState("开始准备数据.");
			done = false;

			ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");
			// = (String)prop.getObject("export.xml.server.pre.id");
			String preid = ""; // ICBC rule to get the server pre id.
			String nodeCode = prop.getString("ncs.node.code");
			this.stepTracker.writeState("开始提取服务节点配置(ncs.node.code) =" + nodeCode);
			if (nodeCode == null || nodeCode.trim().length() == 0) {
				this.stepTracker.writeState("错误: 缺少配置参数: ncs.node.code");
				throw new RuntimeException("缺少配置参数: ncs.node.code");
			}
			String xmldir = (String) prop.getObject("export.xml.generate.dir");
			xmldir = (xmldir == null || xmldir.trim().equals("")) ? "/tmp/" : xmldir;

			this.stepTracker.writeState(" 开始清理策略应用中无映射引用的数据（设备，端口，私有index）...");
			logger.info(" 开始清理策略应用中无映射引用的数据（设备，端口，私有index）...");
			removeNoUsedData();
			this.stepTracker.writeState(" 完成清理策略应用中无映射引用的数据（设备，端口，私有index）");
			logger.info(" 完成清理策略应用中无映射引用的数据（设备，端口，私有index） ");

			TTakeEffectHistory history;
			try {
				history = getHistory(nodeCode);
			} catch (DaoException e) {
				this.stepTracker.writeState("错误: 组装操作历史数据信息失败, 原因: " + e.getMessage());
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			}

			this.stepTracker.writeState("开始准备前缀名server.pre.id数据.");
			try {

				List<TServerInfo> server = TServerInfoDao.findAll();

				if (server.size() > 0) {
					String nmsname = server.get(0).getNmsname();
					this.stepTracker.writeState("server数据 =" + nmsname);
					preid = nmsname.substring(2, 4);
					logger.info("ICBC rule...server.pre.id=" + preid);
					this.stepTracker.writeState("前缀名server.pre.id数据 =" + preid);
				}
			} catch (Exception e) {
				this.success = false;
				logger.error("error in get server pre id..." + e.getMessage(), e);
			}
			// 开始准备时段数据
			this.stepTracker.writeState("开始准备时段数据.");
			try {
				timeframeConverter.fillTimeFrameTableBtimeEtime();
			} catch (Exception e) {
				this.success = false;
				logger.error("error in timeframeConverter ..." + e.getMessage(), e);
			}
			// sleeping2000();
			Connection connectionDS = null;
			try {
				logger.info("export the xml files... icmp.xml ; snmp.xml; srctype.xml");
				connectionDS = datasource.getConnection();
				// connectionDS = DataSourceUtils.getConnection(datasource);
				this.stepTracker.writeState(" 开始进行 ICMP xml 文件处理...");
				logger.info(" 开始进行 ICMP xml 文件处理...");
				try {
					IcmpPolicyExporter exp = new IcmpPolicyExporterImpl();
					exp.setServerID(preid);
					exp.setJdbcConnection(connectionDS);
					exp.export(new FileWriter(xmldir + "icmp.xml"), null);
					if (history != null) {
						history.setIcmpXMLFile(this.fileToString(new File(xmldir + "icmp.xml")));
					}
				} catch (IOException e) {
					this.success = false;
					message = "error in generate icmp.xml...IOException ";
					logger.error("error in generate icmp.xml..." + e.getMessage(), e);
				} catch (Exception e) {
					this.success = false;
					message = "Error in generate icmp.xml...Exception ";
					logger.error(message + e.getMessage(), e);
				}
				this.stepTracker.writeState(" 完成 ICMP xml 生成文件在 " + xmldir + "icmp.xml");
				logger.info(" 完成 ICMP xml 生成文件在, " + xmldir + "icmp.xml");
				this.stepTracker.writeState(" process Snmp xml ");
				logger.info(" process Snmp xml ");
				// sleeping2000();
				this.stepTracker.writeState(" 开始进行 Snmp xml 文件处理...");
				logger.info(" 开始进行 Snmp xml 文件处理...");
				try {
					SnmpPolicyExporter exp1 = new SnmpPolicyExporterImpl();
					exp1.setServerID(preid);
					exp1.setJdbcConnection(connectionDS);
					exp1.export(new FileWriter(xmldir + "snmp.xml"), null);

					if (history != null) {
						history.setSnmpXMLFile(this.fileToString(new File(xmldir + "snmp.xml")));
					}
				} catch (IOException e) {
					this.success = false;
					message = "error in generate snmp.xml...IOException ";
					logger.error("error in generate snmp.xml..." + e.getMessage(), e);
				} catch (Exception e) {
					this.success = false;
					message = "error in generate snmp.xml...Exception ";
					logger.error("error in generate snmp.xml..." + e.getMessage(), e);
				}
				this.stepTracker.writeState("  完成 xml 生成文件在 " + xmldir + "snmp.xml");
				logger.info("  完成 xml 生成文件在, " + xmldir + "snmp.xml");
				// sleeping2000();
				this.stepTracker.writeState(" 开始进行 SrcType 文件处理...");
				logger.info(" 开始进行 SrcType 文件处理...");
				try {
					SrcTypeExporter exp2 = new SrcTypeExporterImpl();
					exp2.setJdbcConnection(connectionDS);
					exp2.export(new FileWriter(xmldir + "SrcType"), null);

					if (history != null) {
						history.setSrcTypeFile(this.fileToString(new File(xmldir + "SrcType")));
					}
				} catch (IOException e) {
					this.success = false;
					message = "error in generate srctype...IOException ";
					logger.error("error in generate srctype.xml..." + e.getMessage(), e);
				} catch (Exception e) {
					this.success = false;
					message = "error in generate srcType...Exception ";
					logger.error("error in generate srcType..." + e.getMessage(), e);
				}
				this.stepTracker.writeState(" 完成 SrcType  生成文件在 " + xmldir + "SrcType");
				logger.info(" 完成 SrcType  生成文件在 " + xmldir + "SrcType");
				// sleeping2000();
				// System.out.println(model);
			} catch (SQLException e) {
				this.success = false;
				message = "error in generate 3 xml files... icmp.xml ; snmp.xml; srctype ...SQLException ";
				logger.error(message + e.getMessage(), e);
			} catch (Exception e) {
				this.success = false;
				message = "error in generate 3 xml files... icmp.xml ; snmp.xml; srctype ...Exception ";
				logger.error(message + e.getMessage(), e);
			} finally {
				try {
					if (connectionDS != null)
						connectionDS.close();
				} catch (SQLException e) {
				}
			}
			this.stepTracker.writeState(" 开始进行 Syslog events process, 设置使用标志.");
			logger.info(" 开始进行 Syslog events process, 设置使用标志.");
			settingSyslogEventsFlags();
			this.stepTracker.writeState(" Syslog events process, 标志设置完成.");
			logger.info(" Syslog events process, 标志设置完成.");
			// sleeping2000();

			this.stepTracker.writeState(" 开始进行 Syslog events process NS, 设置使用标志.");
			logger.info(" 开始进行 Syslog events process NS, 设置使用标志.");
			settingSyslogEventsNsFlags();
			this.stepTracker.writeState("  Syslog events process NS, 设置标志完成.");
			logger.info("  Syslog events process NS, 设置标志完成.");
			// sleeping2000();

			this.stepTracker.writeState(" 开始进行 Syslog events attention 的处理...");
			logger.info(" 开始进行 Syslog events attention 的处理...");
			onEventsAttention();
			this.stepTracker.writeState(" 完成 Syslog events attention ");
			logger.info(" 完成 Syslog events attention ");
			// sleeping2000();

			this.stepTracker.writeState(" 开始处理 Syslog events LineEventsNotCare...");
			logger.info(" 开始处理 Syslog events LineEventsNotCare...");
			onLinesEventsNotCare();
			this.stepTracker.writeState(" 完成 Syslog events LineEventsNotCare...");
			logger.info(" 完成 Syslog events LineEventsNotCare...");
			// sleeping2000();

			this.stepTracker.writeState(" 开始进行 SNMP thresholds 处理...");
			logger.info(" 开始进行 SNMP thresholds 处理...");
			onSnmpThresholds();
			this.stepTracker.writeState(" 完成 SNMP thresholds ");
			logger.info(" 完成 SNMP thresholds ");
			// sleeping2000();
			history.setSnmpThreshold(this.dumpSnmpThresholdsIntoString());

			this.stepTracker.writeState(" 开始进行 ICMP thresholds 处理...");
			logger.info(" 开始进行 ICMP thresholds 处理...");
			onIcmpThresholds();
			this.stepTracker.writeState(" 完成 ICMP thresholds ");
			logger.info(" 完成 ICMP thresholds ");
			// sleeping2000();
			history.setIcmpThreshold(this.dumpIcmpThresholdsIntoString());

			this.stepTracker.writeState(" 开始进行 PP_DEV 处理...");
			logger.info(" 开始进行 PP_DEV 处理...");
			onPpDev();
			this.stepTracker.writeState(" 完成 PP_DEV ");
			logger.info(" 完成 PP_DEV ");
			// sleeping2000();
			// System.out.println(model);

			this.stepTracker.writeState(" 开始进行 PP_PORT 处理...");
			logger.info(" 开始进行 PP_PORT 处理...");
			onPpPort();
			this.stepTracker.writeState(" 完成 PP_PORT ");
			logger.info(" 完成 PP_PORT ");
			// sleeping2000();
			// System.out.println(model);

			if (history != null) {
				try {
					this.stepTracker.writeState(" 记录操作信息");
					this.takeEffectHistoryDao.insert(history);
				} catch (DaoException e) {
					this.stepTracker.writeState("错误: 保存操作历史数据信息失败, 原因: " + e.getMessage());
					logger.info(e.getMessage(), e);
				}
			}

			this.stepTracker.writeState(" 完成生成监控配置文件!");
			logger.info(" 完成生成监控配置文件!");
			// System.out.println(stat);
		} catch (Exception e) {
			this.success = false;
			message = e.getMessage();
			logger.error(e.getMessage(), e);
		} finally {
			this.stepTracker.done();
			done = true;
		}
	}

	private String dumpSnmpThresholdsIntoString() {
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(this.datasource);
		String sql = "select PERFORMANCE||','||BTIME||','||ETIME||','||THRESHOLD||','||COMPARETYPE||','||SEVERITY1||','||SEVERITY2||','||FILTERFLAG1||','||FILTERFLAG2 from snmp_thresholds";
		List<String> result = jdbcTemplate.query(sql, new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		StringBuffer buf = new StringBuffer();
		for (String s : result) {
			buf.append(s);
			buf.append('\n');
		}
		return buf.toString();
	}

	private String dumpIcmpThresholdsIntoString() {
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(this.datasource);
		String sql = "select IPADDRESS||','||BTIME||','||ETIME||','||THRESHOLD||','||COMPARETYPE||','||SEVERITY1||','||SEVERITY2||','||FILTERFLAG1||','||FILTERFLAG2||','||VARLIST||','||SUMMARYCN from icmp_thresholds";
		List<String> result = jdbcTemplate.query(sql, new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		StringBuffer buf = new StringBuffer();
		for (String s : result) {
			buf.append(s);
			buf.append('\n');
		}
		return buf.toString();
	}

	private TTakeEffectHistory getHistory(String nodeCode) throws DaoException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		TTakeEffectHistory history = new TTakeEffectHistory();
		long id = genPkNumber.getID();
		history.setTeId(id);

		TServerNode serverNode = this.serverNodeDao.findByServerCode(nodeCode);
		if (serverNode == null) {
			return null;
		}
		history.setServerId(serverNode.getServerID());

		history.setGeneredTime(new Date());
		PolicyPublishInfo released = this.policyPublishInfoDao.getReleasedVersion();
		if (released == null) {
			return null;
		}
		history.setPpiid(released.getPpiid());
		this.stepTracker.writeState(String.format("%s 生效策略集: %s V[%s]", sdf.format(new Date()), released.getVersionTag(), released.getVersion()));

		List<TUser> users = this.userDao.findWhereUnameEquals(this.getOperator());
		if (users == null || users.size() == 0) {
			return null;
		}
		history.setUsid(users.get(0).getUsid());
		return history;
	}

	private String fileToString(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringWriter out = new StringWriter();
			String line = reader.readLine();
			while (line != null) {
				out.write(String.format("%s\n", line));
				line = reader.readLine();
			}
			reader.close();
			out.close();
			return out.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
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
		String ss = (steps < 10 ? "0" : "") + steps;
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

	private void settingSyslogEventsFlags() {
		try {
			SyslogEventsProcessDao.resetAllAttentionFlags();
		} catch (Exception e1) {
			message = "Error in SyslogEventsProcessDao.resetAllAttentionFlags...Exception ";
			logger.error("error in SyslogEventsProcessDao.resetAllAttentionFlags..." + e1.getMessage());
		}
		try {
			SyslogEventsProcessDao.resetAllNotCareFlags();
		} catch (Exception e1) {
			message = "Error in SyslogEventsProcessDao.resetAllNotCareFlags();...Exception ";
			logger.error("error in SyslogEventsProcessDao.resetAllNotCareFlags();..." + e1.getMessage());
		}
		try {
			List<PolicySyslog> devlst = PolicySyslogDao.findDeviceEvents();
			for (PolicySyslog dto : devlst) {

				try {
					String mark = dto.getMark();
					String manufacture = dto.getManufacture();
					SyslogEventsProcessPk pk = new SyslogEventsProcessPk(mark, manufacture);
					SyslogEventsProcess dd = SyslogEventsProcessDao.findByPrimaryKey(pk);
					if (dd == null) {
						continue;
					}
					dd.setAttentionflag(1);
					SyslogEventsProcessDao.update(pk, dd);
				} catch (Exception e) {
					message = "Error in SyslogEventsProcessDao.resetAllAttentionFlags...Exception ";
					logger.error("error in SyslogEventsProcessDao.resetAllAttentionFlags..." + e.getMessage());
					e.printStackTrace();
					Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on syslog events process at device flags settings process.\n" + dto);
				}

			}
		} catch (PolicySyslogDaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			List<PolicySyslog> portlst = PolicySyslogDao.findPortEvents();
			for (PolicySyslog dto : portlst) {

				try {
					String mark = dto.getMark();
					String manufacture = dto.getManufacture();
					SyslogEventsProcessPk pk = new SyslogEventsProcessPk(mark, manufacture);
					SyslogEventsProcess dd = SyslogEventsProcessDao.findByPrimaryKey(pk);
					if (dd == null) {
						continue;
					}
					if (dto.getSeverity1() <= 7) {
						dd.setAttentionflag(1);
					} else {
						dd.setNotcareflag(1);
					}
					SyslogEventsProcessDao.update(pk, dd);
				} catch (Exception e) {
					e.printStackTrace();
					Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on syslog events process setting flags at port settings process.\n" + dto);
				}

			}
		} catch (PolicySyslogDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void settingSyslogEventsNsFlags() {
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
			for (PolicySyslog dto : devlst) {

				try {
					String mark = dto.getMark();
					String manufacture = dto.getManufacture();
					SyslogEventsProcessNsPk pk = new SyslogEventsProcessNsPk(mark, manufacture);
					SyslogEventsProcessNs dd = SyslogEventsProcessNsDao.findByPrimaryKey(pk);
					if (dd == null) {
						continue;
					}
					dd.setAttentionflag(1);
					SyslogEventsProcessNsDao.update(pk, dd);
				} catch (Exception e) {
					e.printStackTrace();
					Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on syslog events process NS flags device settings process.\n" + dto);
				}

			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			List<PolicySyslog> portlst = PolicySyslogDao.findPortEvents();
			for (PolicySyslog dto : portlst) {

				try {
					String mark = dto.getMark();
					String manufacture = dto.getManufacture();
					SyslogEventsProcessNsPk pk = new SyslogEventsProcessNsPk(mark, manufacture);
					SyslogEventsProcessNs dd = SyslogEventsProcessNsDao.findByPrimaryKey(pk);
					if (dd == null) {
						continue;
					}
					if (dto.getSeverity1() <= 7) {
						dd.setAttentionflag(1);
					} else {
						dd.setNotcareflag(1);
					}
					SyslogEventsProcessNsDao.update(pk, dd);
				} catch (Exception e) {
					e.printStackTrace();
					Log4jInit.ncsLog.error(this.getClass().getName() + "There are  errors on syslog events process NS flags port settings process.\n" + dto);
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

	public void setSyslogEventsProcessDao(SyslogEventsProcessDao syslogEventsProcessDao) {
		SyslogEventsProcessDao = syslogEventsProcessDao;
	}

	public SyslogEventsProcessNsDao getSyslogEventsProcessNsDao() {
		return SyslogEventsProcessNsDao;
	}

	public void setSyslogEventsProcessNsDao(SyslogEventsProcessNsDao syslogEventsProcessNsDao) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ncs.web.policytakeeffect.TakeEffectProcess#isDone()
	 */
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ncs.web.policytakeeffect.TakeEffectProcess#getStat()
	 */
	public Map<String, String> getStat() {
		return this.stepTracker.getState();
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
