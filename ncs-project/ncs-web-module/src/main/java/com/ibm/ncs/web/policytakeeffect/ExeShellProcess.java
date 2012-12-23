package com.ibm.ncs.web.policytakeeffect;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.dao.TServerInfoDao;
import com.ibm.ncs.model.dao.TServerNodeDao;
import com.ibm.ncs.model.dao.TSvrmodMapDao;
import com.ibm.ncs.model.dao.TTakeEffectHistoryDao;
import com.ibm.ncs.model.dao.TUserDao;
import com.ibm.ncs.model.dto.TServerInfo;
import com.ibm.ncs.model.dto.TServerNode;
import com.ibm.ncs.model.dto.TSvrmodMap;
import com.ibm.ncs.model.dto.TTakeEffectHistory;
import com.ibm.ncs.model.dto.TTakeEffectHistoryPk;
import com.ibm.ncs.model.dto.TUser;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.model.exceptions.TServerInfoDaoException;
import com.ibm.ncs.model.exceptions.TSvrmodMapDaoException;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.test.JobHandler;

public class ExeShellProcess {

	Logger logger = Logger.getLogger(ExeShellProcess.class);

	private TTakeEffectHistoryDao takeEffectHistoryDao;
	private TServerNodeDao serverNodeDao;
	private TPolicyPublishInfoDao policyPublishInfoDao;
	private TUserDao userDao;
	private GenPkNumber genPkNumber;

	TServerInfoDao TServerInfoDao;
	TSvrmodMapDao TSvrmodMapDao;
	Writer writer;
	String message;
	boolean done;

	Thread process;
	Map<String, Object> stat = new TreeMap<String, Object>();

	private String operator = null;

	private boolean success = false;

	private int steps;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ExeShellProcess() {
		// init();
	}

	public void init() {
		process = new Thread() {
			public void run() {
				operations();
			}
		};
	}

	public void startProcess() {
		process.start();
	}

	public void stopProcess() {
		process.interrupt();
		// process.stop();
		// process.destroy();
	}

	public void operations() {
		System.out.println("ExeShellProcess start operation...");
		steps = 1;
		done = false;
		success = true;
		stat.clear();

		String[] syslogcmdstring = exeCommandStep1();// SYSLOG

		String[] icmpcmdstring = exeCommandStep2();// ICMP

		String[] snmpcmdstring = exeCommandStep3();// SNMP

		String[] restartcmdstring = exeCommandStep4();// Restart

		ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");
		String waituntil = prop.getString("exe.shell.at.time");
		waituntil = waituntil == null || waituntil.equals("") ? "5" : waituntil;
		String scr01 = "--------------------------- Shell Scripts Output ---------------------------";
		String scr02 = "--------------------------- End of Screen output ---------------------------";

		stat.put(setKS(steps++), sdf.format(new Date()) + " 开始运行生效脚本 syslog shell script  ...");
		String nodeCode = prop.getString("ncs.node.code");
		stat.put(setKS(steps++), "开始提取服务节点配置(ncs.node.code) =" + nodeCode);
		if (nodeCode == null || nodeCode.trim().length() == 0) {
			stat.put(setKS(steps++), "错误: 缺少配置参数: ncs.node.code");
			throw new RuntimeException("缺少配置参数: ncs.node.code");
		}
		TTakeEffectHistory history;
		try {
			history = getHistory(nodeCode);
		} catch (DaoException e) {
			stat.put(setKS(steps++), "错误: 组装操作历史数据信息失败, 原因: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}

		logger.info(" 开始运行生效脚本 syslog shell script  ...");
		try {
			// steps++;
			logger.info("syslogcmdstring=" + ArrayPrint(syslogcmdstring));
			String output1st = waitUntillFireExeShell(syslogcmdstring, "syslog");
			// System.out.println("output1st="+output1st);
			stat.put(setKS(steps++), "\n" + scr01 + "\n" + output1st + "\n" + scr02);
		} catch (Exception e) {
			success = false;
			logger.error("syslogcmdstring... Error occured:\n" + e);
			e.printStackTrace();
		}
		stat.put(setKS(steps++), sdf.format(new Date()) + " 完成 syslog shell script ");
		logger.info(" 完成 syslog shell script ");

		stat.put(setKS(steps++), sdf.format(new Date()) + " 开始运行生效脚本 icmp shell script  ...");
		logger.info(" 开始运行生效脚本 icmp shell script  ...");
		try {
			// steps++;
			logger.info("icmpcmdstring=" + ArrayPrint(icmpcmdstring));
			String output2nd = waitUntillFireExeShell(icmpcmdstring, "icmp");
			// System.out.println("output2nd="+output2nd);
			stat.put(setKS(steps++), "\n" + scr01 + "\n" + output2nd + "\n" + scr02);
		} catch (Exception e) {
			success = false;
			logger.error("icmpcmdstring... Error occured:\n" + e);
			e.printStackTrace();
		}
		stat.put(setKS(steps++), sdf.format(new Date()) + " 完成 icmp shell script");
		logger.info(" 完成 icmp shell script");

		stat.put(setKS(steps++), sdf.format(new Date()) + " 开始运行生效脚本 snmp shell script  ...");
		logger.info(" 开始运行生效脚本 snmp shell script  ...");
		try {
			// steps++;
			logger.info("snmpcmdstring=" + ArrayPrint(snmpcmdstring));
			String output3rd = waitUntillFireExeShell(snmpcmdstring, "snmp");
			// System.out.println("output3rd="+output3rd);
			stat.put(setKS(steps++), "\n" + scr01 + "\n" + output3rd + "\n" + scr02);
		} catch (Exception e) {
			success = false;
			logger.error("snmpcmdstring... Error occured:\n" + e);
			e.printStackTrace();
		}
		stat.put(setKS(steps++), sdf.format(new Date()) + " 完成 snmp shell script ");
		logger.info(" 完成 snmp shell script ");

		// -- only start when meet *5 minute
		// long delay = checkFireTime(waituntil); // no more delay for stop service
		//
		// delay = 0;
		// stat.put(setKS(steps++), "重启服务脚本将在下个时刻运行 :"
		// +sdf.format(System.currentTimeMillis() +delay));
		// try {
		// Thread.sleep(delay);
		// } catch (InterruptedException e) {
		// //e.printStackTrace();
		// }
		stat.put(setKS(steps++), sdf.format(new Date()) + " 开始进行重启服务脚本 ");
		logger.info(" 开始进行重启服务脚本 ");
		try {
			// steps++;
			logger.info("restartcmdstring=" + ArrayPrint(restartcmdstring));
			String output4th = waitUntillFireExeShell(restartcmdstring, "restart");
			// System.out.println("output4th="+output4th);
			stat.put(setKS(steps++), "\n" + scr01 + "\n" + output4th + "\n" + scr02);
		} catch (Exception e) {
			success = false;
			logger.error("restartcmdstring... Error occured:\n" + e);
			e.printStackTrace();
		}
		stat.put(setKS(steps++), sdf.format(new Date()) + " 完成重启服务脚本");
		logger.info(" 完成重启服务脚本");

		stat.put(setKS(steps++), "完成生效脚本");
		logger.info("完成生效脚本");
		System.out.println(stat);

		done = true;
		// model.put("done", "done");

		if (history != null) {
			try {
				if (success) {
					history.setEffectStatus("S");
				} else {
					history.setEffectStatus("H");
				}
				history.setEffectTime(new Date());
				stat.put(setKS(steps++), sdf.format(new Date()) + " 记录操作信息");
				this.takeEffectHistoryDao.update(new TTakeEffectHistoryPk(history.getTeId()), history);
			} catch (DaoException e) {
				stat.put(setKS(steps++), "错误: 保存操作历史数据信息失败, 原因: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private String ArrayPrint(String[] syslogcmdstring) {
		if (syslogcmdstring == null)
			return null;
		String ret = "";
		for (String tmp : syslogcmdstring) {
			ret += tmp + " ";
		}
		return ret;
	}

	/**
	 * SYSLOG exe
	 * 
	 * @return
	 */
	private String[] exeCommandStep1() {
		// prepare server info
		// String ip = "";
		String nmsname = "";
		String username = "";
		String password = "";
		String targetdir = "";
		String modname = "syslog"; // search for NCBF1
		String exeshell = "";
		String srcdir = "";
		String srcfile = "";
		try {
			List<TServerInfo> server = TServerInfoDao.findByModuleName(modname);
			List<TSvrmodMap> mod = TSvrmodMapDao.findByModuleName(modname);
			ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");

			if (server.size() > 0) {
				// ip = server.get(0).getNmsip();
				nmsname = server.get(0).getNmsname();
				username = server.get(0).getUsername();
				username = (username == null || username.equals("")) ? "empty" : username; // not
				                                                                           // used
				                                                                           // in
				                                                                           // this
				                                                                           // shell
				                                                                           // script
				password = server.get(0).getPassword();
				password = (password == null || password.equals("")) ? "empty" : password; // not
				                                                                           // used
				                                                                           // in
				                                                                           // this
				                                                                           // shell
				                                                                           // script
				targetdir = mod.get(0).getPath();
				exeshell = prop.getString("exe.shell.1st.script");
				srcdir = prop.getString("export.xml.generate.dir");
				srcfile = srcdir + "/SrcType";
			}

		} catch (TServerInfoDaoException e) {
			logger.error("exeCommandStep1 ... Error occured:\n" + e);
			e.printStackTrace();
		} catch (TSvrmodMapDaoException e) {
			logger.error("exeCommandStep1... Error occured:\n" + e);
			e.printStackTrace();
		}
		// Pattern as: sh 1st-script.sh nmsname user pass targetdir src
		// srcfile should have absolute dir + file
		// $0 $1 $2 $3 $4 $5
		String[] cmdstring = new String[] { exeshell, nmsname, username, password, targetdir, srcfile };
		System.out.println(cmdstring[0] + cmdstring[1] + cmdstring[2] + cmdstring[3] + cmdstring[4] + cmdstring[5]);
		return cmdstring;
	}

	/**
	 * ICMP upload ... exe
	 * 
	 * @return
	 */
	private String[] exeCommandStep2() {
		// prepare server info
		// String ip = "";
		String nmsname = "";
		String username = "";
		String password = "";
		String targetdir = "";
		String modname = "icmp"; // search for NCBF3
		String exeshell = "";
		String srcdir = "";
		String srcfile = "";
		try {
			List<TServerInfo> server = TServerInfoDao.findByModuleName(modname);
			List<TSvrmodMap> mod = TSvrmodMapDao.findByModuleName(modname);
			ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");

			if (server.size() > 0) {
				nmsname = server.get(0).getNmsname();
				username = server.get(0).getUsername();
				password = server.get(0).getPassword();
				targetdir = mod.get(0).getPath();
				exeshell = prop.getString("exe.shell.2nd.script");
				srcdir = prop.getString("export.xml.generate.dir");
				srcfile = "icmp.xml";
			}

		} catch (TServerInfoDaoException e) {
			logger.error("exeCommandStep2... Error occured:\n" + e);
			e.printStackTrace();
		} catch (TSvrmodMapDaoException e) {
			logger.error("exeCommandStep2... Error occured:\n" + e);
			e.printStackTrace();
		}
		// Pattern as: sh 1st-script.sh ip user pass path src
		// src only the name of the src
		// $0 $1 $2 $3 $4 $5
		String[] cmdstring = new String[] { exeshell, nmsname, username, password, targetdir, srcfile };
		return cmdstring;
	}

	/**
	 * SNMP upload ... exe
	 * 
	 * @return
	 */
	private String[] exeCommandStep3() {// SNMP
		// prepare server info
		String nmsname = "";
		String username = "";
		String password = "";
		String targetdir = "";
		String modname = "snmp";// search for NCBF3
		String exeshell = "";
		String srcdir = "";
		String srcfile = "";
		try {
			List<TServerInfo> server = TServerInfoDao.findByModuleName(modname);
			List<TSvrmodMap> mod = TSvrmodMapDao.findByModuleName(modname);
			ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");

			if (server.size() > 0) {
				nmsname = server.get(0).getNmsname();
				username = server.get(0).getUsername();
				password = server.get(0).getPassword();
				targetdir = mod.get(0).getPath();
				exeshell = prop.getString("exe.shell.3rd.script");
				srcdir = prop.getString("export.xml.generate.dir");
				srcfile = "snmp.xml";
			}

		} catch (TServerInfoDaoException e) {
			logger.error("exeCommandStep3... Error occured:\n" + e);
			e.printStackTrace();
		} catch (TSvrmodMapDaoException e) {
			logger.error("exeCommandStep3... Error occured:\n" + e);
			e.printStackTrace();
		}
		// Pattern as: sh 1st-script.sh ip user pass path src
		// $0 $1 $2 $3 $4 $5
		String[] cmdstring = new String[] { exeshell, nmsname, username, password, targetdir, srcfile };
		return cmdstring;
	}

	/**
	 * Re-Start exe
	 * 
	 * @return
	 */
	private String[] exeCommandStep4() {// Re-Start services
		// prepare server info
		String nmsname = "";
		String username = "";
		String password = "";
		// String targetdir = "";
		String modname = "snmp"; // search for NCBF3
		String exeshell = "";
		// String srcdir ="";
		// String srcfile = "";
		try {
			List<TServerInfo> server = TServerInfoDao.findByModuleName(modname);
			List<TSvrmodMap> mod = TSvrmodMapDao.findByModuleName(modname);
			ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");

			if (server.size() > 0) {
				nmsname = server.get(0).getNmsname();
				username = server.get(0).getUsername();
				password = server.get(0).getPassword();
				// targetdir = mod.get(0).getPath();
				exeshell = prop.getString("exe.shell.4th.script");
				// srcdir = prop.getString("export.xml.generate.dir") ;

			}

		} catch (TServerInfoDaoException e) {
			logger.error("exeCommandStep4... Error occured:\n" + e);
			e.printStackTrace();
		} catch (TSvrmodMapDaoException e) {
			logger.error("exeCommandStep4... Error occured:\n" + e);
			e.printStackTrace();
		}
		// Pattern as: sh 1st-script.sh ip user pass path src
		// $0 $1 $2 $3 $4 $5
		String[] cmdstring = new String[] { exeshell, nmsname, username, password
		// , targetdir
		// , srcfile
		};
		return cmdstring;
	}

	// private void waitUntillFireExeShell(String[] cmdstring, String jobid,
	// Writer output)
	private String waitUntillFireExeShell(String[] cmdstring, String jobid) {
		// cmdstring = new String []{"/B2B/test/testb2b.sh"};
		String output = "";
		try {
			JobHandler command = new JobHandler(Runtime.getRuntime().exec(cmdstring), jobid);
			// command.setTheOutput(output);
			command.startIn();
			command.startOut();
			command.startErr();
			command.startIn2();

			while (true) {
				Thread.sleep(5000);
				if (command.isStopoutput()) {
					command.interruptIn();
					command.interruptOut();
					command.interruptErr();
					command.interruptIn2();
					break;
				}
			}
			output = command.getStdOutText();
			// System.out.println("waitUntillFireExeShell...output="+output);
			// command.logclose();
		} catch (InterruptedException e) {
			output += "Interrupted occured:";
			logger.error("waitUntillFireExeShell... InterruptedException occured:\n" + e);
			// e.printStackTrace();
		} catch (IOException e) {
			output += "I/O Error occured:\n" + e;
			logger.error("waitUntillFireExeShell... I/O Error occured:\n" + e);
			// e.printStackTrace();
		} catch (Exception e) {
			output += "Error occured:\n" + e;
			logger.error("waitUntillFireExeShell... Error occured:\n" + e);
			// e.printStackTrace();
		}
		return output;
	}

	private String setKS(int steps) {
		String ss = (steps < 10 ? "0" : "") + steps;
		return ss;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExeShellProcess exsh = new ExeShellProcess();
		String[] mycmd = new String[] { "/B2B/test/testb2b.sh" };// test only

		try {
			exsh.waitUntillFireExeShell(mycmd, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String ppath = System.getProperty("user.dir");
		// Pattern as: sh 1st-script.sh ip user pass path src
		// $0 $1 $2 $3 $4 $5
		String[] cmdstring = new String[] { // "/usr/bin/ksh "
		"/B2B/test/exe-1st-step-syslog.sh", "10.10.10.88", "root", "passw0rd", "/tmp", "" };

		try {
			exsh.waitUntillFireExeShell(cmdstring, "jobid");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private TTakeEffectHistory getHistory(String nodeCode) throws DaoException {
		TServerNode serverNode = this.serverNodeDao.findByServerCode(nodeCode);
		if (serverNode == null) {
			throw new RuntimeException("数据错误, 无法找到对应的服务节点基础数据信息: , 请检查配置或加入此服务节点数据." + nodeCode);
		}
		PolicyPublishInfo released = this.policyPublishInfoDao.getReleasedVersion();
		if (released == null) {
			System.out.println("目前没有发布的策略集.");
			return null;
		}
		stat.put(setKS(steps++), String.format("%s 生效策略集: %s V[%s]", sdf.format(new Date()), released.getVersionTag(), released.getVersion()));

		List<TUser> users = this.userDao.findWhereUnameEquals(this.getOperator());
		if (users == null || users.size() == 0) {
			throw new RuntimeException("用户不存在: " + operator);
		}

		TTakeEffectHistory history = this.takeEffectHistoryDao.findLastItemByServerIdAndReleaseInfo(serverNode.getServerID(), released.getPpiid());
		if (history != null) {
			return history;
		}
		return null;
	}

	public TServerInfoDao getTServerInfoDao() {
		return TServerInfoDao;
	}

	public void setTServerInfoDao(TServerInfoDao serverInfoDao) {
		TServerInfoDao = serverInfoDao;
	}

	public TSvrmodMapDao getTSvrmodMapDao() {
		return TSvrmodMapDao;
	}

	public void setTSvrmodMapDao(TSvrmodMapDao svrmodMapDao) {
		TSvrmodMapDao = svrmodMapDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getStat() {
		return stat;
	}

	public void setStat(Map<String, Object> stat) {
		this.stat = stat;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Thread getProcess() {
		return process;
	}

	public void setProcess(Thread process) {
		this.process = process;
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

	public TPolicyPublishInfoDao getPolicyPublishInfoDao() {
		return policyPublishInfoDao;
	}

	public void setPolicyPublishInfoDao(TPolicyPublishInfoDao policyPublishInfoDao) {
		this.policyPublishInfoDao = policyPublishInfoDao;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
