/**
 * 
 */
package com.ibm.ncs.web.policyapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DspEventsFromPolicySyslogDao;
import com.ibm.ncs.model.dao.DspSyslogEventsDao;
import com.ibm.ncs.model.dao.PolDetailDspDao;
import com.ibm.ncs.model.dao.PolicySyslogDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dao.TPolicyDetailsWithRuleDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.DspEventsFromPolicySyslog;
import com.ibm.ncs.model.dto.DspSyslogEvents;
import com.ibm.ncs.model.dto.PolDetailDsp;
import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.PolicySyslogPk;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyDetails;
import com.ibm.ncs.model.dto.TPolicyDetailsPk;
import com.ibm.ncs.model.dto.TPolicyDetailsWithRule;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.model.exceptions.PolicySyslogDaoException;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;
import com.ibm.ncs.model.exceptions.TEventTypeInitDaoException;
import com.ibm.ncs.model.exceptions.TPolicyDetailsDaoException;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.PolicyRuleEvaluator;
import com.ibm.ncs.util.SortList;

/**
 * @author root
 * 
 */
public class SavePolicyDetailsController implements Controller {

	PolicyRuleEvaluator policyRuleEvaluator;
	TPolicyDetailsWithRuleDao policyDetailsWithRuleDao;
	TGrpNetDao TGrpNetDao;
	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	TPolicyDetailsDao TPolicyDetailsDao;
	TModuleInfoInitDao TModuleInfoInitDao;
	PolDetailDspDao PolDetailDspDao;
	TEventTypeInitDao TEventTypeInitDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	DspSyslogEventsDao dspSyslogEventsDao;
	PolicySyslogDao policySyslogDao;
	DspEventsFromPolicySyslogDao DspEventsFromPolicySyslogDao;
	GenPkNumber genPkNumber;
	String message;
	String pageView;

	public PolicyRuleEvaluator getPolicyRuleEvaluator() {
		return policyRuleEvaluator;
	}

	public void setPolicyRuleEvaluator(PolicyRuleEvaluator policyRuleEvaluator) {
		this.policyRuleEvaluator = policyRuleEvaluator;
	}

	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
	}

	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		message = "";
		try {

			String mpidstr = request.getParameter("mpid");
			long mpid = Long.parseLong(mpidstr);
			TPolicyBase policyBase = TPolicyBaseDao.findByPrimaryKey(mpid);
			model.put("policyBase", policyBase);
			if (policyBase.getPtvid() > 0) {
				 model.put("ptvid", policyBase.getPtvid());
			}

			String mode = request.getParameter("mode");
			String displayOption = request.getParameter("listSeled");
			String mpname = request.getParameter("mpname");

			if (mode != null && mode.equalsIgnoreCase("icmp")) {
				model = saveIcmpDetails(request, response, model);
				message = (String) model.get("messageg");
			} else if (mode != null && mode.equalsIgnoreCase("snmp")) {
				saveSnmpDetails(request, response, model);
			} else { // syslog policies
				saveSyslogDetails(request, response, model);
			}// end of syslog policies

			if (mpname != null)
				model.put("mpname", mpname);
			if (displayOption != null)
				model.put("displayOption", displayOption);
		} catch (DataIntegrityViolationException de) {
			message += "策略定制保存失败，数据一致性错误";
			model.put("messageFromSave", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + de.getMessage());
			buildPolicyDetailsList(request, response, model);
			de.printStackTrace();
			return new ModelAndView(getPageView(), "model", model);
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			message += "策略定制保存失败";
			model.put("messageFromSave", message);
			buildPolicyDetailsList(request, response, model);
			e.printStackTrace();
			return new ModelAndView(getPageView(), "model", model);
		}

		model.put("messageFromSave", message);
		buildPolicyDetailsList(request, response, model);
		return new ModelAndView(getPageView(), "model", model);
	}

	private void saveSyslogDetails(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception, PolicySyslogDaoException,
	    SequenceNMDaoException {
		String manufacture = request.getParameter("manufselect");
		String mpidStr = request.getParameter("mpid");
		for (int eveCount = 1; eveCount <= 8; eveCount++) {
			// System.out.println("\tFor eveCount=" + eveCount);
			String[] sel = request.getParameterValues("sel" + eveCount);
			String[] pre = request.getParameterValues("pre" + eveCount);

			String[] severity1Str = request.getParameterValues("severity1" + eveCount);
			String[] filterAStr = request.getParameterValues("filterA" + eveCount);
			String[] filterBStr = request.getParameterValues("filterB" + eveCount);
			String[] severity2Str = request.getParameterValues("severity2" + eveCount);

			String[] eventtypeStr = request.getParameterValues("eventtype" + eveCount);

			// used in syslog
			String[] markStr = request.getParameterValues("mark" + eveCount);

			int numInDB = 0;
			if (pre != null)
				numInDB = pre.length; // number of original records in db

			int preSelIndex = 0;
			int[] selIndexValue = null;
			if (sel != null)
				selIndexValue = new int[sel.length];
			else
				selIndexValue = new int[0];

			if (sel != null) {
				for (int i = 0; i < sel.length; i++) {
					int selIndex = Integer.parseInt(sel[i].substring(0, sel[i].indexOf("|")));
					selIndexValue[i] = selIndex;

					long severity1 = 0;
					long severity2 = 0;
					long mpidTmp = 0;

					// non syslog policy
					String mark = markStr[selIndex];
					if (mark == null || mark.equals("") || manufacture == null || manufacture.equalsIgnoreCase("")) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						message += "事件名称：" + (mark == null ? "" : mark) + " Mark or Manufacture is null<br/>";
						continue;
					}
					long filterflag1 = 0;
					long filterflag2 = 0;
					long eventtype = 0;
					try {
						mpidTmp = Long.parseLong(mpidStr);
						if (severity1Str != null && severity1Str[selIndex] != null && !severity1Str[selIndex].equals(""))
							severity1 = Long.parseLong(severity1Str[selIndex]);
						if (severity2Str != null && severity2Str[selIndex] != null && !severity2Str[selIndex].equals(""))
							severity2 = Long.parseLong(severity2Str[selIndex]);
						filterflag1 = Long.parseLong(filterAStr[selIndex]);
						filterflag2 = Long.parseLong(filterBStr[selIndex]);
						eventtype = Long.parseLong(eventtypeStr[selIndex]);
					} catch (Exception e) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						message += "事件名称：" + (mark == null ? "" : mark) + ".  原因： 阈值级别应为数字类型 <br/>";
						continue;
					}
					// validate severity is beween 1 to 7 or 100
					if ((severity1Str != null && severity1Str[selIndex] != null && !severity1Str[selIndex].equals("")) && ((severity1 > 7 && severity1 != 100) || severity1 < 1)
					    || (severity2Str != null && severity2Str[selIndex] != null && !severity2Str[selIndex].equals("")) && ((severity2 > 7 && severity2 != 100) || severity2 < 1)) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						message += "事件名称：" + (mark == null ? "" : mark) + ".  原因： 告警级别范围应为1-7或100<br/>";
						continue;
					}

					PolicySyslog dto = new PolicySyslog();
					if (severity1Str[selIndex] == null || severity1Str[selIndex].equals("")) {
						dto.setSeverity1Null(true);
					} else
						dto.setSeverity1(severity1);

					dto.setFilterflag1(filterflag1);
					dto.setFilterflag2(filterflag2);
					if (severity2Str[selIndex] == null || severity2Str[selIndex].equals("")) {
						dto.setSeverity2Null(true);
					} else
						dto.setSeverity2(severity2);

					dto.setMark(mark);
					dto.setMpid(mpidTmp);
					dto.setManufacture(manufacture);
					dto.setEventtype(eventtype);

					if (selIndex < numInDB) { // delete or update record in db
						try {
							// update current record
							long spid = Long.parseLong(pre[i].substring(0, pre[i].indexOf("|")));
							dto.setSpid(spid);
							PolicySyslogPk pk = dto.createPk();
							policySyslogDao.update(pk, dto);
							Log4jInit.ncsLog.info(this.getClass().getName() + " Updated to policySyslogDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
						} catch (Exception ep) {
							Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ep.getMessage());
							// TODO Auto-generated catch block
							if (!message.equals(""))
								message = "策略定制保存失败";
							model.put("messageFromSave", message);
							buildPolicyDetailsList(request, response, model);
							ep.printStackTrace();
						}
					} else { // insert records to db
						try {
							List<PolicySyslog> tmpLst = policySyslogDao.findWhereMarkAndMpidEquals(mark, mpidTmp);
							if (tmpLst != null && tmpLst.size() > 1) {// error
								if (message.equals(""))
									message = "以下策略定制失败：<br/>";
								message += "事件名称：" + (mark == null ? "" : mark) + ".  原因： 数据库中有超过一条记录与此事件同名，请更正数据库中记录再进行策略定制";
								model.put("messageFromSave", message);
								Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n策略定制保存失败，数据一致性错误");
								continue;
							} else if (tmpLst != null && tmpLst.size() == 1) {// update
								dto.setSpid(tmpLst.get(0).getSpid());
								PolicySyslogPk pk = dto.createPk();
								policySyslogDao.update(pk, dto);
								Log4jInit.ncsLog.info(this.getClass().getName() + " updated to policySyslogDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
							} else {// insert
								dto.setSpid(genPkNumber.getID());
								PolicySyslogPk pk1 = policySyslogDao.insert(dto);
								Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to policySyslogDao: " + dto.toString());
							}
						} catch (DataIntegrityViolationException dupe) {
							dupe.printStackTrace();
							long spid = Long.parseLong(pre[i].substring(0, pre[i].indexOf("|")));
							dto.setSpid(spid);
							PolicySyslogPk pk = dto.createPk();
							policySyslogDao.update(pk, dto);
							Log4jInit.ncsLog.info(this.getClass().getName() + " updated to policySyslogDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
						}
					}
				}// end of for
			}// end of if

			for (int i = 0; i < numInDB; i++) {
				int index = -1;
				for (int j = 0; j < selIndexValue.length; j++) {
					if (selIndexValue[j] == i)
						index = j;
				}
				if (index < 0) {
					PolicySyslog dtoDel = new PolicySyslog();
					dtoDel.setMpid(Long.parseLong(mpidStr));
					dtoDel.setMark(markStr[i]);
					long spid = Long.parseLong(pre[i].substring(0, pre[i].indexOf("|")));
					dtoDel.setSpid(spid);
					PolicySyslogPk pkDel = dtoDel.createPk();
					policySyslogDao.delete(pkDel);
					Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from policySyslogDao: " + pkDel.toString());
				}
			}
		}// enf of for (iterate event type)
	}

	private void saveSnmpDetails(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws TEventTypeInitDaoException, Exception,
	    TPolicyDetailsDaoException {
		String category = request.getParameter("cate");
		String mpidStr = request.getParameter("mpid");

		String[] sel = request.getParameterValues("sel");
		String[] pre = request.getParameterValues("pre");

		String[] eveidStr = request.getParameterValues("eveid");
		String[] modidStr = request.getParameterValues("modid");
		String[] pollstr = request.getParameterValues("poll");
		String[] value1 = request.getParameterValues("value1");
		String[] severity1Str = request.getParameterValues("severity1");
		String[] severityAStr = request.getParameterValues("severityA");
		String[] filterAStr = request.getParameterValues("filterA");
		String[] value2 = request.getParameterValues("value2");
		String[] severity2Str = request.getParameterValues("severity2");
		String[] severityBStr = request.getParameterValues("severityB");
		String[] filterBStr = request.getParameterValues("filterB");
		String[] compareTypeStr = request.getParameterValues("compareType");
		String[] oidgroupStr = request.getParameterValues("oidgroup");
		String[] oidgroupSelStr = request.getParameterValues("oidgroupSel");
		// for port policy
		String[] value1lowStr = request.getParameterValues("value1low");
		String[] v1lseverityAStr = request.getParameterValues("v1lseverityA");
		String[] value2lowStr = request.getParameterValues("value2low");
		String[] v2lseverityBStr = request.getParameterValues("v2lseverityB");
		String[] v1lseverity1Str = request.getParameterValues("v1lseverity1");
		String[] v2lseverity2Str = request.getParameterValues("v2lseverity2");

		int numInDB = 0;
		if (pre != null)
			numInDB = pre.length; // number of original records in db

		int[] selIndexValue = null;
		if (sel != null)
			selIndexValue = new int[sel.length];
		else
			selIndexValue = new int[0];

		if (sel != null) {
			for (int i = 0; i < sel.length; i++) {
				int selIndex = Integer.parseInt(sel[i].substring(0, sel[i].indexOf("|")));
				selIndexValue[i] = selIndex;

				long severity1 = 0;
				long severity2 = 0;
				long mpidTmp = 0;

				// validate input:
				long eveidTmp = 0;
				long modidTmp = 0;
				long pollTmp = 0;
				long severityA = 0;
				long severityB = 0;
				long v1lseverity1 = 0;
				long v2lseverity2 = 0;
				long v1lseverityA = 0;
				long v2lseverityB = 0;
				String value1Tmp = value1[selIndex];
				String value2Tmp = value2[selIndex];
				String comparTypeTmp = compareTypeStr[selIndex];
				String value1lowTmp = null, value2lowTmp = null;
				if (category.equals("4")) {
					if (value1lowStr != null)
						value1lowTmp = value1lowStr[selIndex];
					if (value2lowStr != null)
						value2lowTmp = value2lowStr[selIndex];
				}
				try {
					mpidTmp = Long.parseLong(mpidStr);
					eveidTmp = Long.parseLong(eveidStr[selIndex]);
					modidTmp = Long.parseLong(modidStr[selIndex]);
					if (pollstr != null && pollstr[selIndex] != null && !pollstr[selIndex].equals(""))
						pollTmp = Long.parseLong(pollstr[selIndex]);

					if (severity1Str != null && severity1Str[selIndex] != null && !severity1Str[selIndex].equals(""))
						severity1 = Long.parseLong(severity1Str[selIndex]);
					if (severity2Str != null && severity2Str[selIndex] != null && !severity2Str[selIndex].equals(""))
						severity2 = Long.parseLong(severity2Str[selIndex]);
					if (severityAStr != null && severityAStr[selIndex] != null && !severityAStr[selIndex].equals(""))
						severityA = Long.parseLong(severityAStr[selIndex]);
					if (severityBStr != null && severityBStr[selIndex] != null && !severityBStr[selIndex].equals(""))
						severityB = Long.parseLong(severityBStr[selIndex]);
					if (category.equals("4")) {
						if (v1lseverity1Str != null && v1lseverity1Str[selIndex] != null && !v1lseverity1Str[selIndex].equals(""))
							v1lseverity1 = Long.parseLong(v1lseverity1Str[selIndex]);
						if (v2lseverity2Str != null && v2lseverity2Str[selIndex] != null && !v2lseverity2Str[selIndex].equals(""))
							v2lseverity2 = Long.parseLong(v2lseverity2Str[selIndex]);
						if (v1lseverityAStr != null && v1lseverityAStr[selIndex] != null && !v1lseverityAStr[selIndex].equals(""))
							v1lseverityA = Long.parseLong(v1lseverityAStr[selIndex]);
						if (v2lseverityBStr != null && v2lseverityBStr[selIndex] != null && !v2lseverityBStr[selIndex].equals(""))
							v2lseverityB = Long.parseLong(v2lseverityBStr[selIndex]);
					}
				} catch (Exception e) {
					if (message.equals(""))
						message = "以下策略定制失败：<br/>";
					TEventTypeInit t = null;
					if (eveidTmp > 0 && modidTmp > 0)
						t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
					message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 阈值级别和Poll间隔应为数字类型 <br/>";
					e.printStackTrace();
					continue;
				}
				// validate severity is beween 1 to 7 or 100
				if (((severity1Str[selIndex] != null && !severity1Str[selIndex].equals("")) && ((severity1 > 7 && severity1 != 100) || severity1 < 1))
				    || ((severity2Str != null && severity2Str[selIndex] != null && !severity2Str[selIndex].equals("")) && ((severity2 > 7 && severity2 != 100) || severity2 < 1))
				    || ((severityAStr != null && severityAStr[selIndex] != null && !severityAStr[selIndex].equals("")) && ((severityA > 7 && severityA != 100) || severityA < 1))
				    || ((severityBStr != null && severityBStr[selIndex] != null && !severityBStr[selIndex].equals("")) && ((severityB > 7 && severityB != 100) || severityB < 1))) {
					if (message.equals(""))
						message = "以下策略定制失败：<br/>";
					TEventTypeInit t = null;
					if (eveidTmp > 0 && modidTmp > 0)
						t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
					message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 告警级别范围应为1-7或100<br/>";
					continue;
				}
				// validate if severity is not null, then the value should not be
				// null
				if ((severity1Str != null && severity1Str[selIndex] != null && !severity1Str[selIndex].equals(""))
				    || (severityAStr != null && severityAStr[selIndex] != null && !severityAStr[selIndex].equals(""))) {
					if (value1 == null || value1Tmp == null || value1Tmp.equalsIgnoreCase("")) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						TEventTypeInit t = null;
						if (eveidTmp > 0 && modidTmp > 0)
							t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
						message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 阈值1/阈值In1不可为空 <br/>";
						continue;
					}
				}
				if ((severity2Str != null && severity2Str[selIndex] != null && !severity2Str[selIndex].equals(""))
				    || (severityBStr != null && severityBStr[selIndex] != null && !severityBStr[selIndex].equals(""))) {
					if (value2 == null || value2Tmp == null || value2Tmp.equalsIgnoreCase("")) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						TEventTypeInit t = null;
						if (eveidTmp > 0 && modidTmp > 0)
							t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
						message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 阈值2/阈值Out1不可为空 <br/>";
						continue;
					}
				}

				if (category.equals("4")) {
					if (((v1lseverity1Str != null && v1lseverity1Str[selIndex] != null && !v1lseverity1Str[selIndex].equals("")) && ((v1lseverity1 > 7 && v1lseverity1 != 100) || v1lseverity1 < 1))
					    || ((v2lseverity2Str != null && v2lseverity2Str[selIndex] != null && !v2lseverity2Str[selIndex].equals("")) && ((v2lseverity2 > 7 && v2lseverity2 != 100) || v2lseverity2 < 1))
					    || ((v1lseverityAStr != null && v1lseverityAStr[selIndex] != null && !v1lseverityAStr[selIndex].equals("")) && ((v1lseverityA > 7 && v1lseverityA != 100) || v1lseverityA < 1))
					    || ((v2lseverityBStr != null && v2lseverityBStr[selIndex] != null && !v2lseverityBStr[selIndex].equals("")) && ((v2lseverityB > 7 && v2lseverityB != 100) || v2lseverityB < 1))) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						TEventTypeInit t = null;
						if (eveidTmp > 0 && modidTmp > 0)
							t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
						message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 告警级别范围应为1-7或100<br/>";
						continue;
					}
					if (value2lowTmp != null && !value2lowTmp.equals("")) {
						if (comparTypeTmp == null || comparTypeTmp.equals("") || comparTypeTmp.equals("NULL")) {
							if (message.equals(""))
								message = "以下策略定制失败：<br/>";
							TEventTypeInit t = null;
							if (eveidTmp > 0 && modidTmp > 0)
								t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
							message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 当阈值Out2内容非空时，阈值比较方式不可为空<br/>";
							continue;
						}
					}
					if (value1lowTmp != null && !value1lowTmp.equals("")) {
						if (comparTypeTmp == null || comparTypeTmp.equals("") || comparTypeTmp.equals("NULL")) {
							if (message.equals(""))
								message = "以下策略定制失败：<br/>";
							TEventTypeInit t = null;
							if (eveidTmp > 0 && modidTmp > 0)
								t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
							message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 当阈值In2内容非空时，阈值比较方式不可为空<br/>";
							continue;
						}
					}
					// validate if severity is not null, then the value should not be
					// null
					if ((v1lseverity1Str != null && v1lseverity1Str[selIndex] != null && !v1lseverity1Str[selIndex].equals(""))
					    || (v1lseverityAStr != null && v1lseverityAStr[selIndex] != null && !v1lseverityAStr[selIndex].equals(""))) {
						if (value1lowStr == null || value1lowTmp == null || value1lowTmp.equalsIgnoreCase("")) {
							if (message.equals(""))
								message = "以下策略定制失败：<br/>";
							TEventTypeInit t = null;
							if (eveidTmp > 0 && modidTmp > 0)
								t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
							message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 阈值In2不可为空 <br/>";
							continue;
						}
					}

					if ((v2lseverity2Str != null && v2lseverity2Str[selIndex] != null && !v2lseverity2Str[selIndex].equals(""))
					    || (v2lseverityBStr != null && v2lseverityBStr[selIndex] != null && !v2lseverityBStr[selIndex].equals(""))) {
						if (value2lowStr == null || value2lowTmp == null || value2lowTmp.equalsIgnoreCase("")) {
							if (message.equals(""))
								message = "以下策略定制失败：<br/>";
							TEventTypeInit t = null;
							if (eveidTmp > 0 && modidTmp > 0)
								t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
							message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 阈值Out2不可为空 <br/>";
							continue;
						}
					}
				}
				if (value1Tmp != null && !value1Tmp.equals("")) {
					if (comparTypeTmp == null || comparTypeTmp.equals("NULL") || comparTypeTmp.equals("")) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						TEventTypeInit t = null;
						if (eveidTmp > 0 && modidTmp > 0)
							t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
						message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 当阈值1/In1内容非空时，阈值比较方式不可为空<br/>";
						continue;
					}
				}
				if (value2Tmp != null && !value2Tmp.equals("")) {
					if (comparTypeTmp == null || comparTypeTmp.equals("NULL") || comparTypeTmp.equals("")) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						TEventTypeInit t = null;
						if (eveidTmp > 0 && modidTmp > 0)
							t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
						message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 当阈值2/Out1内容非空时，阈值比较方式不可为空<br/>";
						continue;
					}
				}

				// 验证策略规则
				String vrMsg = validateRule(TEventTypeInitDao, TPolicyBaseDao, policyDetailsWithRuleDao, policyRuleEvaluator, request, selIndex, new String[] { "阀值2", "阀值3", "", "" });
				if (vrMsg != null && vrMsg.trim().length() > 0) {
					message += vrMsg;
					continue;
				}

				TPolicyDetails dto = new TPolicyDetails();
				dto.setMpid(mpidTmp);
				dto.setEveid(eveidTmp);
				dto.setModid(modidTmp);

				if (pollstr[selIndex] == null || pollstr[selIndex].equals("")) {
					dto.setPollNull(true);
				} else
					dto.setPoll(pollTmp);

				dto.setValue1(value1Tmp);
				if (severity1Str[selIndex] == null || severity1Str[selIndex].equals("")) {
					dto.setSeverity1Null(true);
				} else
					dto.setSeverity1(severity1);

				if (severityAStr == null || severityAStr[selIndex] == null || severityAStr[selIndex].equals("")) {
					dto.setSeverityANull(true);
				} else
					dto.setSeverityA(severityA);

				dto.setFilterA(filterAStr[selIndex]);
				dto.setFilterB(filterBStr[selIndex]);
				dto.setValue2(value2Tmp);
				if (severity2Str == null || severity2Str[selIndex] == null || severity2Str[selIndex].equals("")) {
					dto.setSeverity2Null(true);
				} else
					dto.setSeverity2(severity2);
				if (comparTypeTmp.endsWith("NULL"))
					comparTypeTmp = "";
				dto.setComparetype(comparTypeTmp);

				if (severityBStr == null || severityBStr[selIndex] == null || severityBStr[selIndex].equals("")) {
					dto.setSeverityBNull(true);
				} else
					dto.setSeverityB(severityB);

				if (category.equals("4")) {
					if (v1lseverity1Str == null || v1lseverity1Str[selIndex] == null || v1lseverity1Str[selIndex].equals("")) {
						dto.setV1lSeverity1Null(true);
					} else
						dto.setV1lSeverity1(v1lseverity1);

					if (v2lseverity2Str == null || v2lseverity2Str[selIndex] == null || v2lseverity2Str[selIndex].equals("")) {
						dto.setV2lSeverity2Null(true);
					} else
						dto.setV2lSeverity2(v2lseverity2);

					if (v1lseverityAStr == null || v1lseverityAStr[selIndex] == null || v1lseverityAStr[selIndex].equals("")) {
						dto.setV1lSeverityANull(true);
					} else
						dto.setV1lSeverityA(v1lseverityA);

					if (v2lseverityBStr == null || v2lseverityBStr[selIndex] == null || v2lseverityBStr[selIndex].equals("")) {
						dto.setV2lSeverityBNull(true);
					} else
						dto.setV2lSeverityB(v2lseverityB);

					dto.setValue1Low(value1lowTmp);
					dto.setValue2Low(value2lowTmp);
				}
				// if check box for OID group is checked, then save the input
				if (oidgroupSelStr != null) {
					boolean flag = false;
					for (int c = 0; c < oidgroupSelStr.length; c++) {
						if (oidgroupSelStr[c].equalsIgnoreCase(String.valueOf(selIndex))) {
							flag = true;
							break;
						}
					}
					if (flag) {
						dto.setOidgroup(oidgroupStr[selIndex]);
					} else {
						dto.setOidgroup(null);
					}
				} else {
					dto.setOidgroup(null);
				}

				if (selIndex < numInDB) { // delete or update record in db
					try {
						// update current record
						TPolicyDetailsPk pk = dto.createPk();
						TPolicyDetailsDao.update(pk, dto);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Updated to TPolicyDetailsDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
						System.out.println(" Updated to TPolicyDetailsDao: pk=" + pk.toString() + "\tdto= " + dto.toString());

					} catch (Exception ep) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ep.getMessage());
						// TODO Auto-generated catch block
						if (!message.equals(""))
							message = "savePolicyDetailCtl.error";
						model.put("messageFromSave", message);
						buildPolicyDetailsList(request, response, model);
						ep.printStackTrace();
					}
				} else { // insert records to db
					try {
						TPolicyDetailsPk pk1 = TPolicyDetailsDao.insert(dto);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TPolicyDetailsDao: " + dto.toString());
					} catch (DataIntegrityViolationException dupe) {
						dupe.printStackTrace();
						TPolicyDetailsPk pk = dto.createPk();
						TPolicyDetailsDao.update(pk, dto);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TPolicyDetailsDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
					}
				}
			}// end of for
		}// end of if

		for (int i = 0; i < numInDB; i++) {
			int index = -1;
			for (int j = 0; j < selIndexValue.length; j++) {
				if (selIndexValue[j] == i)
					index = j;
			}
			if (index < 0) {
				TPolicyDetails dtoDel = new TPolicyDetails();
				dtoDel.setMpid(Long.parseLong(mpidStr));
				dtoDel.setEveid(Long.parseLong(eveidStr[i]));
				dtoDel.setModid(Long.parseLong(modidStr[i]));
				TPolicyDetailsPk pkDel = dtoDel.createPk();
				TPolicyDetailsDao.delete(pkDel);
				Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TPolicyDetailsDao: " + pkDel.toString());
			}
		}
	}

	private Map<String, Object> saveIcmpDetails(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws DataIntegrityViolationException,
	    Exception {

		String mpidStr = request.getParameter("mpid");
		String[] sel = request.getParameterValues("sel");
		String[] pre = request.getParameterValues("pre");

		String[] eveidStr = request.getParameterValues("eveid");
		String[] modidStr = request.getParameterValues("modid");
		String[] pollstr = request.getParameterValues("poll");
		String[] value1 = request.getParameterValues("value1");
		String[] severity1Str = request.getParameterValues("severity1");
		String[] severityAStr = request.getParameterValues("severityA");
		String[] filterAStr = request.getParameterValues("filterA");
		String[] value2 = request.getParameterValues("value2");
		String[] severity2Str = request.getParameterValues("severity2");
		String[] severityBStr = request.getParameterValues("severityB");
		String[] filterBStr = request.getParameterValues("filterB");
		String[] compareTypeStr = request.getParameterValues("compareType");
		String[] oidgroupStr = request.getParameterValues("oidgroup");
		String[] oidgroupSelStr = request.getParameterValues("oidgroupSel");
		// for port policy
		String[] value1lowStr = request.getParameterValues("value1low");
		String[] v1lseverityAStr = request.getParameterValues("v1lseverityA");
		String[] value2lowStr = request.getParameterValues("value2low");
		String[] v2lseverityBStr = request.getParameterValues("v2lseverityB");
		String[] v1lseverity1Str = request.getParameterValues("v1lseverity1");
		String[] v2lseverity2Str = request.getParameterValues("v2lseverity2");

		int numInDB = 0;
		if (pre != null)
			numInDB = pre.length; // number of original records in db

		int[] selIndexValue = null;
		if (sel != null)
			selIndexValue = new int[sel.length];
		else
			selIndexValue = new int[0];

		if (sel != null) {
			for (int i = 0; i < sel.length; i++) {
				int selIndex = Integer.parseInt(sel[i].substring(0, sel[i].indexOf("|")));
				selIndexValue[i] = selIndex;

				long severity1 = 0;
				long severity2 = 0;
				long mpidTmp = 0;

				// validate input:
				long eveidTmp = 0;
				long modidTmp = 0;
				long pollTmp = 0;
				long severityA = 0;
				long severityB = 0;
				long v1lseverity1 = 0;
				long v2lseverity2 = 0;
				long v1lseverityA = 0;
				long v2lseverityB = 0;
				String value1Tmp = value1[selIndex];
				String value2Tmp = value2[selIndex];
				String comparTypeTmp = compareTypeStr[selIndex];
				String value1lowTmp = null, value2lowTmp = null;
				if (value1lowStr != null)
					value1lowTmp = value1lowStr[selIndex];
				if (value2lowStr != null)
					value2lowTmp = value2lowStr[selIndex];
				try {
					mpidTmp = Long.parseLong(mpidStr);
					eveidTmp = Long.parseLong(eveidStr[selIndex]);
					modidTmp = Long.parseLong(modidStr[selIndex]);
					if (pollstr != null && pollstr[selIndex] != null && !pollstr[selIndex].equals(""))
						pollTmp = Long.parseLong(pollstr[selIndex]);

					if (severity1Str != null && severity1Str[selIndex] != null && !severity1Str[selIndex].equals(""))
						severity1 = Long.parseLong(severity1Str[selIndex]);
					if (severity2Str != null && severity2Str[selIndex] != null && !severity2Str[selIndex].equals(""))
						severity2 = Long.parseLong(severity2Str[selIndex]);
					if (severityAStr != null && severityAStr[selIndex] != null && !severityAStr[selIndex].equals(""))
						severityA = Long.parseLong(severityAStr[selIndex]);
					if (severityBStr != null && severityBStr[selIndex] != null && !severityBStr[selIndex].equals(""))
						severityB = Long.parseLong(severityBStr[selIndex]);
					if (v1lseverity1Str != null && v1lseverity1Str[selIndex] != null && !v1lseverity1Str[selIndex].equals(""))
						v1lseverity1 = Long.parseLong(v1lseverity1Str[selIndex]);
					if (v2lseverity2Str != null && v2lseverity2Str[selIndex] != null && !v2lseverity2Str[selIndex].equals(""))
						v2lseverity2 = Long.parseLong(v2lseverity2Str[selIndex]);
					if (v1lseverityAStr != null && v1lseverityAStr[selIndex] != null && !v1lseverityAStr[selIndex].equals(""))
						v1lseverityA = Long.parseLong(v1lseverityAStr[selIndex]);
					if (v2lseverityBStr != null && v2lseverityBStr[selIndex] != null && !v2lseverityBStr[selIndex].equals(""))
						v2lseverityB = Long.parseLong(v2lseverityBStr[selIndex]);
				} catch (Exception e) {
					if (message.equals(""))
						message = "以下策略定制失败：<br/>";
					TEventTypeInit t = null;
					if (eveidTmp > 0 && modidTmp > 0)
						t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
					message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 阈值级别应为数字类型 <br/>";
					e.printStackTrace();
					continue;
				}
				// validate severity is beween 1 to 7 or 100
				if (((severity2Str != null && severity2Str[selIndex] != null && !severity2Str[selIndex].equals("")) && ((severity2 > 7 && severity2 != 100) || severity2 < 1))
				    || ((severityAStr != null && severityAStr[selIndex] != null && !severityAStr[selIndex].equals("")) && ((severityA > 7 && severityA != 100) || severityA < 1))) {
					if (value1lowTmp != null && value1lowTmp.trim().length() > 0) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						TEventTypeInit t = null;
						if (eveidTmp > 0 && modidTmp > 0)
							t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
						message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 告警级别范围应为1-7或100<br/>";
						continue;
					}
				}
				if (((severity2Str != null && severity2Str[selIndex] != null && !severity2Str[selIndex].equals("")) && ((severity2 > 7 && severity2 != 100) || severity2 < 1))
				    || ((severityAStr != null && severityAStr[selIndex] != null && !severityAStr[selIndex].equals("")) && ((severityA > 7 && severityA != 100) || severityA < 1))) {
					if (value2lowTmp != null && value2lowTmp.trim().length() > 0) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						TEventTypeInit t = null;
						if (eveidTmp > 0 && modidTmp > 0)
							t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
						message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 告警级别范围应为1-7或100<br/>";
						continue;
					}
				}
				if (value2Tmp != null && !value2Tmp.equals("")) {
					if (comparTypeTmp == null || comparTypeTmp.equals("NULL") || comparTypeTmp.equals("")) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						TEventTypeInit t = null;
						if (eveidTmp > 0 && modidTmp > 0)
							t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
						message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 当阈值3内容非空时，阈值比较方式不可为空<br/>";
						continue;
					}
				}
				if (value1Tmp != null && !value1Tmp.equals("")) {
					if (comparTypeTmp == null || comparTypeTmp.equals("NULL") || comparTypeTmp.equals("")) {
						if (message.equals(""))
							message = "以下策略定制失败：<br/>";
						TEventTypeInit t = null;
						if (eveidTmp > 0 && modidTmp > 0)
							t = TEventTypeInitDao.findByPrimaryKey(modidTmp, eveidTmp);
						message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： 当阈值2内容非空时，阈值比较方式不可为空<br/>";
						continue;
					}
				}

				// 验证策略规则
				String vrMsg = validateRule(TEventTypeInitDao, TPolicyBaseDao, policyDetailsWithRuleDao, policyRuleEvaluator, request, selIndex, new String[] { "阀值2", "阀值3", "", "" });
				if (vrMsg != null && vrMsg.trim().length() > 0) {
					message += vrMsg;
					continue;
				}

				TPolicyDetails dto = new TPolicyDetails();
				dto.setMpid(mpidTmp);
				dto.setEveid(eveidTmp);
				dto.setModid(modidTmp);

				if (pollstr[selIndex] == null || pollstr[selIndex].equals("")) {
					dto.setPollNull(true);
				} else
					dto.setPoll(pollTmp);

				dto.setValue1(value1Tmp);
				if (severity1Str[selIndex] == null || severity1Str[selIndex].equals("")) {
					dto.setSeverity1Null(true);
				} else
					dto.setSeverity1(severity1);

				if (severityAStr == null || severityAStr[selIndex] == null || severityAStr[selIndex].equals("")) {
					dto.setSeverityANull(true);
				} else
					dto.setSeverityA(severityA);

				dto.setFilterA(filterAStr[selIndex]);
				dto.setFilterB(filterBStr[selIndex]);
				dto.setValue2(value2Tmp);
				if (severity2Str == null || severity2Str[selIndex] == null || severity2Str[selIndex].equals("")) {
					dto.setSeverity2Null(true);
				} else
					dto.setSeverity2(severity2);
				if (comparTypeTmp.endsWith("NULL"))
					comparTypeTmp = "";
				dto.setComparetype(comparTypeTmp);

				if (severityBStr == null || severityBStr[selIndex] == null || severityBStr[selIndex].equals("")) {
					dto.setSeverityBNull(true);
				} else
					dto.setSeverityB(severityB);

				// if(category.equals("4")){
				if (v1lseverity1Str == null || v1lseverity1Str[selIndex] == null || v1lseverity1Str[selIndex].equals("")) {
					dto.setV1lSeverity1Null(true);
				} else
					dto.setV1lSeverity1(v1lseverity1);

				if (v2lseverity2Str == null || v2lseverity2Str[selIndex] == null || v2lseverity2Str[selIndex].equals("")) {
					dto.setV2lSeverity2Null(true);
				} else
					dto.setV2lSeverity2(v2lseverity2);

				if (v1lseverityAStr == null || v1lseverityAStr[selIndex] == null || v1lseverityAStr[selIndex].equals("")) {
					dto.setV1lSeverityANull(true);
				} else
					dto.setV1lSeverityA(v1lseverityA);

				if (v2lseverityBStr == null || v2lseverityBStr[selIndex] == null || v2lseverityBStr[selIndex].equals("")) {
					dto.setV2lSeverityBNull(true);
				} else
					dto.setV2lSeverityB(v2lseverityB);

				dto.setValue1Low(value1lowTmp);
				dto.setValue2Low(value2lowTmp);
				// }
				// if check box for OID group is checked, then save the input
				if (oidgroupSelStr != null) {
					boolean flag = false;
					for (int c = 0; c < oidgroupSelStr.length; c++) {
						if (oidgroupSelStr[c].equalsIgnoreCase(String.valueOf(selIndex))) {
							flag = true;
							break;
						}
					}
					if (flag) {
						dto.setOidgroup(oidgroupStr[selIndex]);
					} else {
						dto.setOidgroup(null);
					}
				} else {
					dto.setOidgroup(null);
				}

				if (selIndex < numInDB) { // delete or update record in db
					try {
						// update current record
						TPolicyDetailsPk pk = dto.createPk();
						TPolicyDetailsDao.update(pk, dto);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Updated to TPolicyDetailsDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
						System.out.println(" Updated to TPolicyDetailsDao: pk=" + pk.toString() + "\tdto= " + dto.toString());

					} catch (Exception ep) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ep.getMessage());
						// TODO Auto-generated catch block
						if (!message.equals(""))
							message = "savePolicyDetailCtl.error";
						model.put("messageFromSave", message);
						buildPolicyDetailsList(request, response, model);
						ep.printStackTrace();
					}
				} else { // insert records to db
					try {
						TPolicyDetailsPk pk1 = TPolicyDetailsDao.insert(dto);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TPolicyDetailsDao: " + dto.toString());
					} catch (DataIntegrityViolationException dupe) {
						dupe.printStackTrace();
						TPolicyDetailsPk pk = dto.createPk();
						TPolicyDetailsDao.update(pk, dto);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TPolicyDetailsDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
					}
				}
			}// end of for
		}// end of if

		for (int i = 0; i < numInDB; i++) {
			int index = -1;
			for (int j = 0; j < selIndexValue.length; j++) {
				if (selIndexValue[j] == i)
					index = j;
			}
			if (index < 0) {
				TPolicyDetails dtoDel = new TPolicyDetails();
				dtoDel.setMpid(Long.parseLong(mpidStr));
				dtoDel.setEveid(Long.parseLong(eveidStr[i]));
				dtoDel.setModid(Long.parseLong(modidStr[i]));
				TPolicyDetailsPk pkDel = dtoDel.createPk();
				TPolicyDetailsDao.delete(pkDel);
				Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TPolicyDetailsDao: " + pkDel.toString());
			}
		}
		model.put("messageg", message);
		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	private void buildPolicyDetailsList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {

		try {
			message = request.getParameter("message");
			String mpidstr = request.getParameter("mpid");
			long mpid = Long.parseLong(mpidstr);

			String mpname = request.getParameter("mpname");

			String category = request.getParameter("cate");
			String displayOption = request.getParameter("listSeled");
			String selectedEveType = request.getParameter("listeve_type");
			// System.out.println("&&&&&&&&&&%%%%%%%%%%%%%%selEvetype=" +
			// selectedEveType);
			String mode = request.getParameter("mode");

			if (mode == null || mode.equals(""))
				mode = "ICMP";

			// get manufacture list
			String manufacture = request.getParameter("manufselect");
			// System.out.println("Manufacture selection is: " + manufacture);
			Map<Integer, List<DspSyslogEvents>> unselectedSyslog = new HashMap<Integer, List<DspSyslogEvents>>();
			Map<String, Map> DspSyslogMap = new TreeMap<String, Map>();
			List<TManufacturerInfoInit> tmflist = TManufacturerInfoInitDao.findAll();
			model.put("mflist", tmflist);
			if (mode.equalsIgnoreCase("syslog")) {
				List<DspSyslogEvents> sysloglst = null; /* */
				sysloglst = dspSyslogEventsDao.listSyslogEventsOnMpid(mpid);
				for (DspSyslogEvents sdto : sysloglst) {
					String themark = sdto.getMark();
					String themanufacture = sdto.getManufacture();
					if (!themanufacture.equalsIgnoreCase(manufacture))
						continue;
					Long theeventtype = sdto.getEventtype();
					if (theeventtype == null || theeventtype <= 0 || theeventtype > 8)
						theeventtype = (long) 8;
					// if(!category.equalsIgnoreCase("4") && (theeventtype==2 ||
					// theeventtype==1)) //do not display port event for non port policy
					// continue;
					if (category.equalsIgnoreCase("1") && (theeventtype == 2 || theeventtype == 1)) // device
					{
						continue;
					}
					if (category.equalsIgnoreCase("4") && !(theeventtype == 2 || theeventtype == 1)) // port
					                                                                                 // events
					                                                                                 // only
					                                                                                 // ...ignore
					                                                                                 // others
					                                                                                 // except
					                                                                                 // 1wan&2lan
					{
						continue;
					}
					if (category.equalsIgnoreCase("9") && !(theeventtype == 2 || theeventtype == 1)) // predefmib
					                                                                                 // work
					                                                                                 // as
					                                                                                 // port
					                                                                                 // events
					                                                                                 // ??
					{
						continue;
					}
					// System.out.println("Begin For:\n\t****event type is:" +
					// theeventtype);
					Map<Integer, List<DspSyslogEvents>> theMFsyslog = null;
					try {
						theMFsyslog = DspSyslogMap.get(themanufacture.toUpperCase());
					} catch (Exception e) {
					}
					if (theMFsyslog == null) {
						theMFsyslog = new TreeMap<Integer, List<DspSyslogEvents>>();
						// System.out.println("\tthe MFsyslog is null");
					}

					List<DspSyslogEvents> sysloglstdsp = null;
					try {
						sysloglstdsp = theMFsyslog.get(Integer.parseInt(String.valueOf(theeventtype)));
					} catch (Exception e) {
					}
					if (sysloglstdsp == null) {
						sysloglstdsp = new ArrayList<DspSyslogEvents>();
						// System.out.println("\tthe sysloglstdsp is null");
					}
					sysloglstdsp.add(sdto);
					SortList<DspSyslogEvents> sortunselect = new SortList<DspSyslogEvents>();
					sortunselect.Sort(sysloglstdsp, "getEvents", null);
					// System.out.println("\tsyslog length=" + sysloglstdsp.size());
					theMFsyslog.put(Integer.parseInt(String.valueOf(theeventtype)), sysloglstdsp);
					DspSyslogMap.put(themanufacture.toUpperCase(), theMFsyslog);
					// System.out.println("End of for\n");
				}
				// ---
				unselectedSyslog = DspSyslogMap.get(manufacture.toUpperCase());
				// System.out.println("unselectedSyslog:" + unselectedSyslog + "\n" +
				// "DspSyslogMap"+DspSyslogMap);
			}

			List<PolDetailDsp> details = new ArrayList<PolDetailDsp>();
			Map<String, Object> detailMap = new HashMap<String, Object>();
			Map<Integer, Object> syslogdetailMap = new HashMap<Integer, Object>();
			// List<PolicySyslog> syslogDetails = new ArrayList<PolicySyslog>();
			List<DspEventsFromPolicySyslog> eventsSyslogDetails = new ArrayList<DspEventsFromPolicySyslog>();
			Map<String, Object> eventsDictionary = new HashMap<String, Object>();
			if (!mode.equalsIgnoreCase("syslog")) {
				details = PolDetailDspDao.findByMpid(mpid);
				SortList<PolDetailDsp> sortpdd = new SortList<PolDetailDsp>();
				sortpdd.Sort(details, "getMajor", null);

				for (PolDetailDsp dto : details) {
					String key = dto.getMname();
					key = (key == null) ? "" : key;
					List<PolDetailDsp> templst = null;
					if (detailMap.containsKey(key)) {
						templst = (List<PolDetailDsp>) detailMap.get(key);
					} else {
						templst = new ArrayList<PolDetailDsp>();
					}
					templst.add(dto);
					detailMap.put(key, templst);

				}
				// System.out.println("detailMap="+detailMap);
				details = (List<PolDetailDsp>) detailMap.get(mode.toLowerCase());
			} else {

				try {
					eventsSyslogDetails = DspEventsFromPolicySyslogDao.findDspEventsByManufactureAndMpid(manufacture, mpid);
					SortList<DspEventsFromPolicySyslog> sortpol = new SortList<DspEventsFromPolicySyslog>();
					sortpol.Sort(eventsSyslogDetails, "getEvents", null);
				} catch (Exception e) {
					e.printStackTrace();
				}

				for (DspEventsFromPolicySyslog dto : eventsSyslogDetails) {
					int key = Integer.parseInt(String.valueOf(dto.getEventtype()));
					List<DspEventsFromPolicySyslog> templst = null;
					if (syslogdetailMap.containsKey(key)) {
						templst = (List<DspEventsFromPolicySyslog>) syslogdetailMap.get(key);
					} else {
						templst = new ArrayList<DspEventsFromPolicySyslog>();
					}
					templst.add(dto);
					syslogdetailMap.put(key, templst);

				}
			}

			List<TModuleInfoInit> module = TModuleInfoInitDao.findAll();

			List<TEventTypeInit> unselected = null; /* */
			List<TEventTypeInit> snmplst = null; /* */
			if (category.equals("1")) { // cate=1 as DEVECE policy
				snmplst = TEventTypeInitDao.listForDeviceSnmp(mpid);
				SortList<TEventTypeInit> sortpdd = new SortList<TEventTypeInit>();
				sortpdd.Sort(snmplst, "getMajor", null);
			} else if (category.equals("4")) // treated as PORT policy
			{
				snmplst = TEventTypeInitDao.listForPortSnmp(mpid);
				SortList<TEventTypeInit> sortpdd = new SortList<TEventTypeInit>();
				sortpdd.Sort(snmplst, "getMajor", null);
			} else if (category.equals("9")) // treated as PreDefMib policy
			{
				snmplst = TEventTypeInitDao.listForPreDefMibSnmp(mpid);
				SortList<TEventTypeInit> sortpdd = new SortList<TEventTypeInit>();
				sortpdd.Sort(snmplst, "getMajor", null);
			}
			List<TEventTypeInit> icmplst = null; /* */
			if (category.equals("1")) { // cate=1 as DEVECE policy
				icmplst = TEventTypeInitDao.listForDeviceIcmp(mpid);
			} else if (category.equals("4")) // treated as PORT policy
			{
				icmplst = TEventTypeInitDao.listForPortIcmp(mpid);
			} else if (category.equals("9")) // treated as PreDefMib policy
			{
				icmplst = TEventTypeInitDao.listForPortIcmp(mpid);
			}

			if (mode.equalsIgnoreCase("icmp")) {
				unselected = icmplst;
			} else if (mode.equalsIgnoreCase("snmp")) {
				unselected = snmplst;
			}
			if (mode.equalsIgnoreCase("syslog")) {
				model.put("details", syslogdetailMap);
				model.put("unselected", unselectedSyslog);
			} else {
				if (details != null) {
					for (PolDetailDsp pdd : details) {
						TPolicyDetailsWithRule policyDetailsWithRule = this.policyDetailsWithRuleDao.findByEveidAndModid(pdd.getPtvid(), pdd.getEveid(), pdd.getModid());
						pdd.setPolicyDetailsWithRule(policyDetailsWithRule);
					}
				}
				model.put("details", details);
				model.put("unselected", unselected);
			}
			model.put("mpid", mpidstr);
			model.put("mpname", mpname);
			model.put("displayOption", displayOption);
			model.put("selectedEveType", selectedEveType);
			model.put("cate", category);
			model.put("mode", mode);
			model.put("eventType", getEventTypeMap());
			model.put("syslog", DspSyslogMap);
			model.put("pmpmanu", manufacture);
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}

	}

	public Map<Integer, String> getEventTypeMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, "All");
		map.put(1, "广域网端口事件");
		map.put(2, "局域网端口类事件");
		map.put(3, "网络设备类事件");
		map.put(4, "SNASW事件");
		map.put(5, "路由事件");
		map.put(6, "阀值事件");
		map.put(7, "安全事件");
		map.put(8, "其他类事件");
		return map;
	}

	public static String validateRule(TEventTypeInitDao TEventTypeInitDao, TPolicyBaseDao TPolicyBaseDao, TPolicyDetailsWithRuleDao policyDetailsWithRuleDao,
	    PolicyRuleEvaluator policyRuleEvaluator, HttpServletRequest request, int selIndex, String[] thresholdNames) throws TEventTypeInitDaoException {
		String message = "";

		String mpidStr = request.getParameter("mpid");
		String[] eveidStr = request.getParameterValues("eveid");
		String[] modidStr = request.getParameterValues("modid");

		String[] value1Str = request.getParameterValues("value1");
		String[] value2Str = request.getParameterValues("value2");

		long eveid = Long.parseLong(eveidStr[selIndex]);
		long modid = Long.parseLong(modidStr[selIndex]);
		String value1 = value1Str[selIndex];
		String value2 = value2Str[selIndex];

		TEventTypeInit t = null;
		try {
			if (eveid > 0 && modid > 0) {
				t = TEventTypeInitDao.findByPrimaryKey(modid, eveid);
			}

			TPolicyBase pb = TPolicyBaseDao.findByPrimaryKey(Long.parseLong(mpidStr));
			if (pb == null) {
				return "";
			}
			long ptvid = pb.getPtvid();
			TPolicyDetailsWithRule rule = policyDetailsWithRuleDao.findByEveidAndModid(ptvid, eveid, modid);
			if (rule == null) {
				return "";
			}
			{
				String expression = rule.getValue1RuleExpression();
				if (expression != null && expression.trim().length() > 0) {
					String display = rule.getValue1RuleDisplayInfo();
					boolean ok = policyRuleEvaluator.eval(expression, value1);
					if (!ok) {
						message += String.format("'%s'事件设置的阀值为 %s, 其不匹配'%s'预制策略： %s.<br/>", t.getMajor(), value1, thresholdNames[0], display);
					}
				}
			}
			{
				String expression = rule.getValue2RuleExpression();
				if (expression != null && expression.trim().length() > 0) {
					String display = rule.getValue2RuleDisplayInfo();
					boolean ok = policyRuleEvaluator.eval(expression, value2);
					if (!ok) {
						message += String.format("'%s'事件设置的阀值为 %s, 其不匹配'%s'预制策略： %s.<br/>", t.getMajor(), value2, thresholdNames[1], display);
					}
				}
			}
			String[] value1lowStr = request.getParameterValues("value1low");
			if (value1lowStr != null) {
				String value1Low = value1lowStr[selIndex];
				String expression = rule.getValue1LowRuleExpression();
				if (expression != null && expression.trim().length() > 0 && !"var1".equals(value1Low)) {
					String display = rule.getValue1LowRuleDisplayInfo();
					boolean ok = policyRuleEvaluator.eval(expression, value1Low);
					if (!ok) {
						message += String.format("'%s'事件设置的阀值为 %s, 其不匹配'%s'预制策略： %s.<br/>", t.getMajor(), value1Low, thresholdNames[2], display);
					}
				}
			}
			String[] value2lowStr = request.getParameterValues("value2low");
			if (value2lowStr != null) {
				String value2Low = value2lowStr[selIndex];
				String expression = rule.getValue2LowRuleExpression();
				if (expression != null && expression.trim().length() > 0 && !"var2".equals(value2Low)) {
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

	public TPolicyDetailsDao getTPolicyDetailsDao() {
		return TPolicyDetailsDao;
	}

	public void setTPolicyDetailsDao(TPolicyDetailsDao policyDetailsDao) {
		TPolicyDetailsDao = policyDetailsDao;
	}

	public TPolicyBaseDao getTPolicyBaseDao() {
		return TPolicyBaseDao;
	}

	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}

	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}

	public TGrpNetDao getTGrpNetDao() {
		return TGrpNetDao;
	}

	public void setTGrpNetDao(TGrpNetDao grpNetDao) {
		TGrpNetDao = grpNetDao;
	}

	public PolDetailDspDao getPolDetailDspDao() {
		return PolDetailDspDao;
	}

	public void setPolDetailDspDao(PolDetailDspDao polDetailDspDao) {
		PolDetailDspDao = polDetailDspDao;
	}

	public TEventTypeInitDao getTEventTypeInitDao() {
		return TEventTypeInitDao;
	}

	public void setTEventTypeInitDao(TEventTypeInitDao eventTypeInitDao) {
		TEventTypeInitDao = eventTypeInitDao;
	}

	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}

	public void setTManufacturerInfoInitDao(TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}

	public DspSyslogEventsDao getDspSyslogEventsDao() {
		return dspSyslogEventsDao;
	}

	public void setDspSyslogEventsDao(DspSyslogEventsDao dspSyslogEventsDao) {
		this.dspSyslogEventsDao = dspSyslogEventsDao;
	}

	public PolicySyslogDao getPolicySyslogDao() {
		return policySyslogDao;
	}

	public void setPolicySyslogDao(PolicySyslogDao policySyslogDao) {
		this.policySyslogDao = policySyslogDao;
	}

	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}

	public DspEventsFromPolicySyslogDao getDspEventsFromPolicySyslogDao() {
		return DspEventsFromPolicySyslogDao;
	}

	public void setDspEventsFromPolicySyslogDao(DspEventsFromPolicySyslogDao dspEventsFromPolicySyslogDao) {
		DspEventsFromPolicySyslogDao = dspEventsFromPolicySyslogDao;
	}

	public TPolicyDetailsWithRuleDao getPolicyDetailsWithRuleDao() {
		return policyDetailsWithRuleDao;
	}

	public void setPolicyDetailsWithRuleDao(TPolicyDetailsWithRuleDao policyDetailsWithRuleDao) {
		this.policyDetailsWithRuleDao = policyDetailsWithRuleDao;
	}

}
