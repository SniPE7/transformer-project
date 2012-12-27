package com.ibm.ncs.web.policytakeeffect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.script.ScriptException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ibm.ncs.model.dao.PolDetailDspDao;
import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyDetailsWithRuleDao;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.dao.TPolicyTemplateDao;
import com.ibm.ncs.model.dao.TPolicyTemplateVerDao;
import com.ibm.ncs.model.dto.PolDetailDsp;
import com.ibm.ncs.model.dto.PolicyTemplateVer;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyDetailsWithRule;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.model.exceptions.PolDetailDspDaoException;
import com.ibm.ncs.model.exceptions.TEventTypeInitDaoException;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;
import com.ibm.ncs.model.exceptions.TPolicyPublishInfoDaoException;
import com.ibm.ncs.model.exceptions.TPolicyTemplateVerDaoException;
import com.ibm.ncs.util.PolicyRuleEvaluator;

public class PolicyValidationProcessImpl implements PolicyValidationProcess {

	private static Logger logger = Logger.getLogger(PolicyValidationProcessImpl.class);

	private SimpleJdbcTemplate jdbcTemplate;

	private DataSource dataSource;

	private TPolicyPublishInfoDao policyPublishInfoDao;
	private TPolicyTemplateDao policyTemplateDao;
	private TPolicyTemplateVerDao policyTemplateVerDao;
	private TPolicyDetailsWithRuleDao policyDetailsWithRuleDao;
	private PolicyRuleEvaluator policyRuleEvaluator;

	private PolDetailDspDao polDetailDspDao;
	private TPolicyBaseDao policyBaseDao;
	private TEventTypeInitDao eventTypeInitDao;

	private String message;
	private boolean done;
	/**
	 * Success flag
	 */
	private boolean success = false;

	private Thread process;
	private Map<String, String> stat = new TreeMap<String, String>();

  public final class PolicyBaseWithDeviceType {
  	private long mpid;
  	private long ptvid;
  	private long dtid;
  	private long mrid;
		public PolicyBaseWithDeviceType(long mpid, long ptvid, long dtid, long mrid) {
	    super();
	    this.mpid = mpid;
	    this.ptvid = ptvid;
	    this.dtid = dtid;
	    this.mrid = mrid;
    }
		public long getMpid() {
			return mpid;
		}
		public void setMpid(long mpid) {
			this.mpid = mpid;
		}
		public long getDtid() {
			return dtid;
		}
		public void setDtid(long dtid) {
			this.dtid = dtid;
		}
		public long getMrid() {
			return mrid;
		}
		public void setMrid(long mrid) {
			this.mrid = mrid;
		}
		public long getPtvid() {
			return ptvid;
		}
		public void setPtvid(long ptvid) {
			this.ptvid = ptvid;
		}
  	
  	
  }

	public PolicyValidationProcessImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ncs.web.policytakeeffect.PolicyValidationProcess#init()
	 */
	public void init() {
		process = new Thread() {
			public void run() {
				operations();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.ncs.web.policytakeeffect.PolicyValidationProcess#startProcess()
	 */
	public void startProcess() {
		process.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ncs.web.policytakeeffect.PolicyValidationProcess#stopProcess()
	 */
	public void stopProcess() {
		process.interrupt();
	}

	private void operations() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		success = true;

		System.out.println("PolicyValidationProcess start operation...");
		int steps = 1;
		done = false;
		stat.clear();

		stat.put(setKS(steps++), "开始获取最新的发布策略集.");

		try {
			PolicyPublishInfo policyPublishInfo = this.policyPublishInfoDao.getReleasedVersion();
			if (policyPublishInfo == null) {
				stat.put(setKS(steps++), "未发现已发布的策略集.");
			}
			stat.put(
			    setKS(steps++),
			    String.format("%s 最新发布的策略集为: %s - V [%s], 发布时间为: %s", sdf.format(new Date()), policyPublishInfo.getVersionTag(), policyPublishInfo.getVersion(),
			        sdf.format(policyPublishInfo.getPublishTime())));

			// 检查策略集数量及内容是否一致
			steps = checkPolicySet(steps, policyPublishInfo);

			// 检查是否匹配阀值规则
			steps = checkThresholdRule(sdf, steps);

			// 检查型号是否匹配 -- 设备类
			stat.put(setKS(steps++), String.format("%s 检查应用的设备类策略是否符合设备类型约束.", sdf.format(new Date())));
			String sql = "select distinct mpid from t_devpol_map dm where (dm.mpid, dm.devid) not in (select mpid, devid from V_MP_DEVICE_SCOPE)";
			List<Integer> missmatchedItems = this.jdbcTemplate.query(sql, new ParameterizedRowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
					return rs.getInt(1);
				}
			});
			if (missmatchedItems.size() > 0) {
				stat.put(setKS(steps++), String.format("%s <font color='red'>设备类策略未通过设备类型约束检查.</font>", sdf.format(new Date())));
				this.success = false;
				for (int mpid: missmatchedItems) {
					TPolicyBase pb = this.policyBaseDao.findByPrimaryKey(mpid);
					if (pb == null) {
						 continue;
					}
					stat.put(setKS(steps++), String.format("%s <font color='red'>未通过设备类型约束的设备类策略: %s</font>", sdf.format(new Date()), pb.getMpname()));
				}
			} else {
				stat.put(setKS(steps++), String.format("%s 所有设备类策略通过设备类型约束检查.", sdf.format(new Date())));
			}
			// 检查型号是否匹配 -- 端口类
			stat.put(setKS(steps++), String.format("%s 检查应用的端口类策略是否符合设备类型约束.", sdf.format(new Date())));
			sql = "select distinct mpid from t_linepol_map lm where (lm.mpid, lm.ptid) not in (select mpid, p.ptid from V_MP_DEVICE_SCOPE mds inner join t_port_info p on p.devid=mds.devid)";
			missmatchedItems = this.jdbcTemplate.query(sql, new ParameterizedRowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
					return rs.getInt(1);
				}
			});
			if (missmatchedItems.size() > 0) {
				stat.put(setKS(steps++), String.format("%s <font color='red'>端口类策略未通过设备类型约束检查.</font>", sdf.format(new Date())));
				this.success = false;
				for (int mpid: missmatchedItems) {
					TPolicyBase pb = this.policyBaseDao.findByPrimaryKey(mpid);
					if (pb == null) {
						 continue;
					}
					stat.put(setKS(steps++), String.format("%s <font color='red'>未通过设备类型约束检查的端口类策略: %s</font>", sdf.format(new Date()), pb.getMpname()));
				}
			} else {
				stat.put(setKS(steps++), String.format("%s 所有端口类策略通过设备类型约束检查.", sdf.format(new Date())));
			}

			// 检查型号是否匹配 -- 私有MIB
			stat.put(setKS(steps++), String.format("%s 检查应用的私有MIB类策略是否符合设备类型约束.", sdf.format(new Date())));
			sql = "select distinct mpid from PREDEFMIB_POL_MAP pm where (pm.mpid, pm.pdmid) not in (select mpid, p.pdmid from V_MP_DEVICE_SCOPE mds inner join PREDEFMIB_INFO p on p.devid=mds.devid)";
			missmatchedItems = this.jdbcTemplate.query(sql, new ParameterizedRowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
					return rs.getInt(1);
				}
			});
			if (missmatchedItems.size() > 0) {
				stat.put(setKS(steps++), String.format("%s <font color='red'>私有MIB类策略未通过设备类型约束检查.</font>", sdf.format(new Date())));
				this.success = false;
				for (int mpid: missmatchedItems) {
					TPolicyBase pb = this.policyBaseDao.findByPrimaryKey(mpid);
					if (pb == null) {
						 continue;
					}
					stat.put(setKS(steps++), String.format("%s <font color='red'>未通过设备类型约束检查的私有MIB类策略: %s</font>", sdf.format(new Date()), pb.getMpname()));
				}
			} else {
				stat.put(setKS(steps++), String.format("%s 所有私有MIB类策略通过设备类型约束检查.", sdf.format(new Date())));
			}

		} catch (Exception e) {
			message = "error in generate icmp.xml...IOException ";
			logger.error("error in get server pre id..." + e.getMessage());
			e.printStackTrace();
			this.success = false;
		} finally {
			if (success) {
				stat.put(setKS(steps++), sdf.format(new Date()) + " 通过监控配置检查!");
			} else {
				stat.put(setKS(steps++), String.format("%s 未通过监控配置检查! 错误: ", sdf.format(new Date()), message));
			}
			done = true;
		}
	}

	private int checkThresholdRule(SimpleDateFormat sdf, int steps) throws TPolicyBaseDaoException, TPolicyTemplateVerDaoException, TPolicyPublishInfoDaoException,
      PolDetailDspDaoException, TEventTypeInitDaoException {
	  stat.put(setKS(steps++), "检查是否匹配阀值规则 .");
	  List<TPolicyBase> policyBases = this.policyBaseDao.findAll();
	  for (TPolicyBase policyBase : policyBases) {
	  	long ptvid = policyBase.getPtvid();
	  	if (ptvid > 0) {
	  		PolicyTemplateVer ptv = this.policyTemplateVerDao.findById(Long.toString(ptvid));
	  		policyBase.setPolictTemplateVer(ptv);
	  		if (ptv != null) {
	  			PolicyPublishInfo ppi = this.policyPublishInfoDao.findById(Long.toString(ptv.getPpiid()));
	  			ptv.setPolicyPublishInfo(ppi);
	  		}
	  	}
	  }

	  int totalCheckedRule = 0;
	  for (TPolicyBase policyBase : policyBases) {
	  	if (policyBase.getPolictTemplateVer() != null) {
	  		// Check Rule
	  		List<PolDetailDsp> details = this.polDetailDspDao.findByMpid(policyBase.getMpid());
	  		for (PolDetailDsp detail : details) {
	  			String vMsg = validateRule(policyBase, detail, new String[]{"1", "2", "3", "4"});
	  			totalCheckedRule++;
	  			if (vMsg != null && vMsg.trim().length() > 0) {
	  				stat.put(setKS(steps++), String.format("%s 未通过阀值规则检查, 原因: %s", sdf.format(new Date()), vMsg));
	  				this.success = false;
	  			}
	  		}
	  	}
	  }
	  stat.put(setKS(steps++), String.format("%s 共检查 %s 个策略.", sdf.format(new Date()), totalCheckedRule));
	  return steps;
  }

	private int checkPolicySet(int steps, PolicyPublishInfo policyPublishInfo) {
		// PolicyBase是否包含了所有发布集的内容
		stat.put(setKS(steps++), "检查是否存在使用错误策略集的策略定义信息.");
		String sql = "select pb.ptvid " + "from " + "  t_policy_base pb " + "where " + "  ptvid is not null and " + "  ptvid not in  "
		    + "    (select ptvid from t_policy_template_ver where ppiid=?) ";
		List<Long> unkownPtvIds = this.jdbcTemplate.query(sql, new ParameterizedRowMapper<Long>() {

			public Long mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getLong(1);
			}
		}, policyPublishInfo.getPpiid());
		if (unkownPtvIds.size() > 0) {
			stat.put(setKS(steps++), String.format("--- 错误的策略信息数量为 %s 个", unkownPtvIds.size()));
		} else {
			stat.put(setKS(steps++), String.format("--- 不存在使用错误策略集的策略定义信息."));
		}

		stat.put(setKS(steps++), "检查是否存在遗漏的策略信息定义.");
		sql = "select ptv.ptvid " + "from " + "  t_policy_template_ver ptv " + "where " + "  ppiid=? and " + "  ptvid not in  "
		    + "    (select ptvid from t_policy_base where ptvid > 0) ";
		List<Long> missingPtvIds = this.jdbcTemplate.query(sql, new ParameterizedRowMapper<Long>() {

			public Long mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getLong(1);
			}
		}, policyPublishInfo.getPpiid());
		if (missingPtvIds.size() > 0) {
			stat.put(setKS(steps++), String.format("--- 遗漏的策略信息定义为 %s 个", missingPtvIds.size()));
		} else {
			stat.put(setKS(steps++), String.format("--- 不存在遗漏的策略信息定义."));
		}
		return steps;
	}

	public String validateRule(TPolicyBase pb, PolDetailDsp detail, String[] thresholdNames) throws TEventTypeInitDaoException {
		String message = "";

		TEventTypeInit t = null;
		try {
			if (detail.getEveid() > 0 && detail.getModid() > 0) {
				t = eventTypeInitDao.findByPrimaryKey(detail.getModid(), detail.getEveid());
			}

			long ptvid = pb.getPtvid();
			TPolicyDetailsWithRule rule = detail.getPolicyDetailsWithRule();
			if (rule == null) {
				return "";
			}
			{
				String expression = rule.getValue1RuleExpression();
				if (expression != null) {
					String display = rule.getValue1RuleDisplayInfo();
					boolean ok = policyRuleEvaluator.eval(expression, detail.getValue1());
					if (!ok) {
						message += String.format("'%s'事件设置的阀值为 %s, 其不匹配'%s'预制策略： %s.<br/>", t.getMajor(), detail.getValue1(), thresholdNames[0], display);
					}
				}
			}
			{
				String expression = rule.getValue2RuleExpression();
				if (expression != null) {
					String display = rule.getValue2RuleDisplayInfo();
					boolean ok = policyRuleEvaluator.eval(expression, detail.getValue2());
					if (!ok) {
						message += String.format("'%s'事件设置的阀值为 %s, 其不匹配'%s'预制策略： %s.<br/>", t.getMajor(), detail.getValue2(), thresholdNames[1], display);
					}
				}
			}
			{
				String value1Low = detail.getValue1low();
				String expression = rule.getValue1LowRuleExpression();
				if (expression != null && !"var1".equals(value1Low)) {
					String display = rule.getValue1LowRuleDisplayInfo();
					boolean ok = policyRuleEvaluator.eval(expression, value1Low);
					if (!ok) {
						message += String.format("'%s'事件设置的阀值为 %s, 其不匹配'%s'预制策略： %s.<br/>", t.getMajor(), value1Low, thresholdNames[2], display);
					}
				}
			}
			{
				String value2Low = detail.getValue2low();
				String expression = rule.getValue2LowRuleExpression();
				if (expression != null && !"var2".equals(value2Low)) {
					String display = rule.getValue2LowRuleDisplayInfo();
					boolean ok = policyRuleEvaluator.eval(expression, value2Low);
					if (!ok) {
						message += String.format("'%s'事件设置的阀值为 %s, 其不匹配'%s'预制策略： %s.<br/>", t.getMajor(), value2Low, thresholdNames[3], display);
					}
				}
			}
		} catch (DaoException e) {
			message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： " + e.getMessage() + "<br/>";
			e.printStackTrace();
		} catch (ScriptException e) {
			message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： " + e.getMessage() + "<br/>";
			e.printStackTrace();
		}
		return message;
	}

	private String setKS(int steps) {
		String ss = (steps < 10 ? "0" : "") + steps;
		return ss;
	}

	public Thread getProcess() {
		return process;
	}

	public void setProcess(Thread process) {
		this.process = process;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ncs.web.policytakeeffect.PolicyValidationProcess#isDone()
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
	 * @see com.ibm.ncs.web.policytakeeffect.PolicyValidationProcess#getStat()
	 */
	public Map<String, String> getStat() {
		return stat;
	}

	public void setStat(Map<String, String> stat) {
		this.stat = stat;
	}

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	public TPolicyPublishInfoDao getPolicyPublishInfoDao() {
		return policyPublishInfoDao;
	}

	public void setPolicyPublishInfoDao(TPolicyPublishInfoDao policyPublishInfoDao) {
		this.policyPublishInfoDao = policyPublishInfoDao;
	}

	public TPolicyTemplateDao getPolicyTemplateDao() {
		return policyTemplateDao;
	}

	public void setPolicyTemplateDao(TPolicyTemplateDao policyTemplateDao) {
		this.policyTemplateDao = policyTemplateDao;
	}

	public TPolicyTemplateVerDao getPolicyTemplateVerDao() {
		return policyTemplateVerDao;
	}

	public void setPolicyTemplateVerDao(TPolicyTemplateVerDao policyTemplateVerDao) {
		this.policyTemplateVerDao = policyTemplateVerDao;
	}

	public PolDetailDspDao getPolDetailDspDao() {
		return polDetailDspDao;
	}

	public void setPolDetailDspDao(PolDetailDspDao polDetailDspDao) {
		this.polDetailDspDao = polDetailDspDao;
	}

	public TPolicyBaseDao getPolicyBaseDao() {
		return policyBaseDao;
	}

	public void setPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		this.policyBaseDao = policyBaseDao;
	}

	public TEventTypeInitDao getEventTypeInitDao() {
		return eventTypeInitDao;
	}

	public void setEventTypeInitDao(TEventTypeInitDao eventTypeInitDao) {
		this.eventTypeInitDao = eventTypeInitDao;
	}

	public TPolicyDetailsWithRuleDao getPolicyDetailsWithRuleDao() {
		return policyDetailsWithRuleDao;
	}

	public void setPolicyDetailsWithRuleDao(TPolicyDetailsWithRuleDao policyDetailsWithRuleDao) {
		this.policyDetailsWithRuleDao = policyDetailsWithRuleDao;
	}

	public PolicyRuleEvaluator getPolicyRuleEvaluator() {
		return policyRuleEvaluator;
	}

	public void setPolicyRuleEvaluator(PolicyRuleEvaluator policyRuleEvaluator) {
		this.policyRuleEvaluator = policyRuleEvaluator;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
