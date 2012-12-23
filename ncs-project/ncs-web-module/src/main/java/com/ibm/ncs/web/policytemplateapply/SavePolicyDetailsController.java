/**
 * 
 */
package com.ibm.ncs.web.policytemplateapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PolDetailDspDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyDetailsWithRuleDao;
import com.ibm.ncs.model.dto.DspEventsFromPolicySyslog;
import com.ibm.ncs.model.dto.DspSyslogEvents;
import com.ibm.ncs.model.dto.PolDetailDsp;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TPolicyDetailsWithRule;
import com.ibm.ncs.model.dto.TPolicyDetailsWithRulePk;
import com.ibm.ncs.model.exceptions.TEventTypeInitDaoException;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

/**
 * @author root
 * 
 */
public class SavePolicyDetailsController implements Controller {

	TPolicyDetailsWithRuleDao policyDetailsWithRuleDao;

	TModuleInfoInitDao TModuleInfoInitDao;
	PolDetailDspDao PolDetailDspDao;
	TEventTypeInitDao TEventTypeInitDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;

	GenPkNumber genPkNumber;
	String message;
	String pageView;

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

			String mode = request.getParameter("mode");
			String displayOption = request.getParameter("listSeled");
			String mpname = request.getParameter("mpname");
			String category = request.getParameter("cate");
			String manufacture = request.getParameter("manufselect");
			String ptvidStr = request.getParameter("ptvid");

			// if(mode!=null && !mode.equalsIgnoreCase("syslog")){
			if (mode != null && mode.equalsIgnoreCase("icmp")) {
				model = saveIcmpDetails(request, response, model, message);
				message = (String) model.get("messageg");
			} else if (mode != null && mode.equalsIgnoreCase("snmp")) {

				String[] sel = request.getParameterValues("sel");
				String[] pre = request.getParameterValues("pre");

				String[] eveidStr = request.getParameterValues("eveid");
				String[] modidStr = request.getParameterValues("modid");
				String[] pollstr = request.getParameterValues("poll");
				String[] value1 = request.getParameterValues("value1");
				String[] value1Rule = request.getParameterValues("value1Rule");
				String[] severity1Str = request.getParameterValues("severity1");
				String[] severityAStr = request.getParameterValues("severityA");
				String[] filterAStr = request.getParameterValues("filterA");
				String[] value2 = request.getParameterValues("value2");
				String[] value2Rule = request.getParameterValues("value2Rule");
				String[] severity2Str = request.getParameterValues("severity2");
				String[] severityBStr = request.getParameterValues("severityB");
				String[] filterBStr = request.getParameterValues("filterB");
				String[] compareTypeStr = request.getParameterValues("compareType");
				String[] oidgroupStr = request.getParameterValues("oidgroup");
				String[] oidgroupSelStr = request.getParameterValues("oidgroupSel");
				// for port policy
				String[] value1lowStr = request.getParameterValues("value1low");
				String[] value1lowRuleStr = request.getParameterValues("value1lowRule");
				String[] v1lseverityAStr = request.getParameterValues("v1lseverityA");
				String[] value2lowStr = request.getParameterValues("value2low");
				String[] value2lowRuleStr = request.getParameterValues("value2lowRule");
				String[] v2lseverityBStr = request.getParameterValues("v2lseverityB");
				String[] v1lseverity1Str = request.getParameterValues("v1lseverity1");
				String[] v2lseverity2Str = request.getParameterValues("v2lseverity2");

				int numInDB = 0;
				if (pre != null)
					numInDB = pre.length; // number of original records in db
				// System.out.println("%%In SavePolicyDetailsController%%dto.length="
				// + sel.length + "\tnumInDb=" + numInDB );

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
						long ptvidTmp = 0;

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
						String value1RuleTmp = value1Rule[selIndex];
						String value2Tmp = value2[selIndex];
						String value2RuleTmp = value2Rule[selIndex];
						String comparTypeTmp = compareTypeStr[selIndex];
						String value1lowTmp = null, value2lowTmp = null;
						String value1lowRuleTmp = null, value2lowRuleTmp = null;
						if (category.equals("4")) {
							if (value1lowStr != null) {
								value1lowTmp = value1lowStr[selIndex];
							  value1lowRuleTmp = value1lowRuleStr[selIndex];
							}
							if (value2lowStr != null) {
								value2lowTmp = value2lowStr[selIndex];
								value2lowRuleTmp = value2lowRuleStr[selIndex];
							}
						}
						try {
							ptvidTmp = Long.parseLong(ptvidStr);
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

						if (value1[selIndex] == null || value1[selIndex].trim().length() == 0) {
							addMessage(eveidTmp, modidTmp, "必须设置阀值1/In1的取值规则和缺省值!");
							continue;
						}
						if (value1Rule[selIndex] == null || value1Rule[selIndex].trim().length() == 0) {
							addMessage(eveidTmp, modidTmp, "必须设置阀值1/In1的取值规则和缺省值!");
							continue;
						}
						if (severity1Str[selIndex] == null || severity1Str[selIndex].trim().length() == 0) {
							addMessage(eveidTmp, modidTmp, "必须设置阀值1/In1的时段内阀值!");
							continue;
						}
						if (severityAStr[selIndex] == null || severityAStr[selIndex].trim().length() == 0) {
							addMessage(eveidTmp, modidTmp, "必须设置阀值1/In1的时段外阀值!");
							continue;
						}
						if (value2[selIndex] != null && value2[selIndex].trim().length() > 0) {
							if (value2Rule[selIndex] == null || value2Rule[selIndex].trim().length() == 0) {
								addMessage(eveidTmp, modidTmp, "必须设置阀值2/Out1的取值规则和缺省值!");
								continue;
							}
							if (severity2Str[selIndex] == null || severity2Str[selIndex].trim().length() == 0) {
								addMessage(eveidTmp, modidTmp, "必须设置阀值2/Out1的时段内阀值!");
								continue;
							}
							if (severityBStr[selIndex] == null || severityBStr[selIndex].trim().length() == 0) {
								addMessage(eveidTmp, modidTmp, "必须设置阀值2/Out1的时段外阀值!");
								continue;
							}
						}
						if (value1lowStr[selIndex] != null && value1lowStr[selIndex].trim().length() > 0) {
							if (value1lowRuleStr[selIndex] == null || value1lowRuleStr[selIndex].trim().length() == 0) {
								addMessage(eveidTmp, modidTmp, "必须设置阀值In2的取值规则和缺省值!");
								continue;
							}
							if (v1lseverity1Str[selIndex] == null || v1lseverity1Str[selIndex].trim().length() == 0) {
								addMessage(eveidTmp, modidTmp, "必须设置阀值In2的时段内阀值!");
								continue;
							}
							if (v1lseverityAStr[selIndex] == null || v1lseverityAStr[selIndex].trim().length() == 0) {
								addMessage(eveidTmp, modidTmp, "必须设置阀值In2的时段外阀值!");
								continue;
							}
						}
						if (value2lowStr[selIndex] != null && value2lowStr[selIndex].trim().length() > 0) {
							if (value2lowRuleStr[selIndex] == null || value2lowRuleStr[selIndex].trim().length() == 0) {
								addMessage(eveidTmp, modidTmp, "必须设置阀值Out2的取值规则和缺省值!");
								continue;
							}
							if (v2lseverity2Str[selIndex] == null || v2lseverity2Str[selIndex].trim().length() == 0) {
								addMessage(eveidTmp, modidTmp, "必须设置阀值Out2的时段内阀值!");
								continue;
							}
							if (v2lseverityBStr[selIndex] == null || v2lseverityBStr[selIndex].trim().length() == 0) {
								addMessage(eveidTmp, modidTmp, "必须设置阀值Out2的时段外阀值!");
								continue;
							}
						}

						TPolicyDetailsWithRule dto = new TPolicyDetailsWithRule();
						dto.setPtvid(ptvidTmp);
						dto.setEveid(eveidTmp);
						dto.setModid(modidTmp);

						if (pollstr[selIndex] == null || pollstr[selIndex].equals("")) {
							dto.setPollNull(true);
						} else
							dto.setPoll(pollTmp);

						dto.setValue1(value1Tmp);
						dto.setValue1Rule(value1RuleTmp);
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
						dto.setValue2Rule(value2RuleTmp);
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
							dto.setValue1LowRule(value1lowRuleTmp);
							dto.setValue2Low(value2lowTmp);
							dto.setValue2LowRule(value2lowRuleTmp);
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
								TPolicyDetailsWithRulePk pk = dto.createPk();
								this.policyDetailsWithRuleDao.update(pk, dto);
								Log4jInit.ncsLog.info(this.getClass().getName() + " Updated to TPolicyDetailsWithRuleDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
								System.out.println(" Updated to TPolicyDetailsWithRuleDao: pk=" + pk.toString() + "\tdto= " + dto.toString());

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
								TPolicyDetailsWithRulePk pk1 = this.policyDetailsWithRuleDao.insert(dto);
								Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TPolicyDetailsWithRuleDao: " + dto.toString());
							} catch (DataIntegrityViolationException dupe) {
								dupe.printStackTrace();
								TPolicyDetailsWithRulePk pk = dto.createPk();
								this.policyDetailsWithRuleDao.update(pk, dto);
								Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TPolicyDetailsWithRuleDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
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
						TPolicyDetailsWithRule dtoDel = new TPolicyDetailsWithRule();
						dtoDel.setPtvid(Long.parseLong(ptvidStr));
						dtoDel.setEveid(Long.parseLong(eveidStr[i]));
						dtoDel.setModid(Long.parseLong(modidStr[i]));
						TPolicyDetailsWithRulePk pkDel = dtoDel.createPk();
						this.policyDetailsWithRuleDao.delete(pkDel);
						Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TPolicyDetailsWithRuleDao: " + pkDel.toString());
					}
				}
			}

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
			// TODO Auto-generated catch block
			// System.out.println("In catch exception====================");
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

	private Map<String, Object> saveIcmpDetails(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String message2)
	    throws DataIntegrityViolationException, Exception {

		String mode = request.getParameter("mode");
		String displayOption = request.getParameter("listSeled");
		String mpname = request.getParameter("mpname");
		String category = request.getParameter("cate");
		String manufacture = request.getParameter("manufselect");
		String ptvidStr = request.getParameter("ptvid");
		String[] sel = request.getParameterValues("sel");
		String[] pre = request.getParameterValues("pre");

		String[] eveidStr = request.getParameterValues("eveid");
		String[] modidStr = request.getParameterValues("modid");
		String[] pollstr = request.getParameterValues("poll");
		String[] value1 = request.getParameterValues("value1");
		String[] value1Rule = request.getParameterValues("value1Rule");
		String[] severity1Str = request.getParameterValues("severity1");
		String[] severityAStr = request.getParameterValues("severityA");
		String[] filterAStr = request.getParameterValues("filterA");
		String[] value2 = request.getParameterValues("value2");
		String[] value2Rule = request.getParameterValues("value2Rule");
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
				long ptvidTmp = 0;

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
				String value1RuleTmp = value1Rule[selIndex];
				String value2Tmp = value2[selIndex];
				String value2RuleTmp = value2Rule[selIndex];
				String comparTypeTmp = compareTypeStr[selIndex];
				String value1lowTmp = null, value2lowTmp = null;
				// if(category.equals("4")){
				if (value1lowStr != null)
					value1lowTmp = value1lowStr[selIndex];
				if (value2lowStr != null)
					value2lowTmp = value2lowStr[selIndex];
				// }
				try {
					ptvidTmp = Long.parseLong(ptvidStr);
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
					// if(category.equals("4")){
					if (v1lseverity1Str != null && v1lseverity1Str[selIndex] != null && !v1lseverity1Str[selIndex].equals(""))
						v1lseverity1 = Long.parseLong(v1lseverity1Str[selIndex]);
					if (v2lseverity2Str != null && v2lseverity2Str[selIndex] != null && !v2lseverity2Str[selIndex].equals(""))
						v2lseverity2 = Long.parseLong(v2lseverity2Str[selIndex]);
					if (v1lseverityAStr != null && v1lseverityAStr[selIndex] != null && !v1lseverityAStr[selIndex].equals(""))
						v1lseverityA = Long.parseLong(v1lseverityAStr[selIndex]);
					if (v2lseverityBStr != null && v2lseverityBStr[selIndex] != null && !v2lseverityBStr[selIndex].equals(""))
						v2lseverityB = Long.parseLong(v2lseverityBStr[selIndex]);
					// }
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
				
				if (v1lseverity1Str[selIndex] == null || v1lseverity1Str[selIndex].trim().length() == 0) {
					addMessage(eveidTmp, modidTmp, "必须设置阀值1的时段内Ping不通级别!");
					continue;
				}
				if (v1lseverityAStr[selIndex] == null || v1lseverityAStr[selIndex].trim().length() == 0) {
					addMessage(eveidTmp, modidTmp, "必须设置阀值1的时段外Ping不通级别!");
					continue;
				}
				if (value1lowStr[selIndex] != null && value1lowStr[selIndex].trim().length() > 0) {
					if (value1[selIndex] == null || value1[selIndex].trim().length() == 0) {
						addMessage(eveidTmp, modidTmp, "由于选择的阀值2，必须设置阀值2的缺省值!");
						continue;
					}
					if (value1Rule[selIndex] == null || value1Rule[selIndex].trim().length() == 0) {
						addMessage(eveidTmp, modidTmp, "由于选择的阀值2，必须设置阀值2的取值规则!");
						continue;
					}
					if (severity1Str[selIndex] == null || severity1Str[selIndex].trim().length() == 0) {
						addMessage(eveidTmp, modidTmp, "由于选择的阀值2，必须设置阀值2的时段内Ping不通级别!");
						continue;
					}
					if (severityAStr[selIndex] == null || severityAStr[selIndex].trim().length() == 0) {
						addMessage(eveidTmp, modidTmp, "由于选择的阀值2，必须设置阀值2的时段外Ping不通级别!");
						continue;
					}
				}
				if (value2lowStr[selIndex] != null && value2lowStr[selIndex].trim().length() > 0) {
					if (value2[selIndex] == null || value2[selIndex].trim().length() == 0) {
						addMessage(eveidTmp, modidTmp, "由于选择的阀值3，必须设置阀值2的缺省值!");
						continue;
					}
					if (value2Rule[selIndex] == null || value2Rule[selIndex].trim().length() == 0) {
						addMessage(eveidTmp, modidTmp, "由于选择的阀值3，必须设置阀值2的取值规则!");
						continue;
					}
					if (severity2Str[selIndex] == null || severity2Str[selIndex].trim().length() == 0) {
						addMessage(eveidTmp, modidTmp, "由于选择的阀值3，必须设置阀值2的时段内Ping不通级别!");
						continue;
					}
					if (severityBStr[selIndex] == null || severityBStr[selIndex].trim().length() == 0) {
						addMessage(eveidTmp, modidTmp, "由于选择的阀值3，必须设置阀值2的时段外Ping不通级别!");
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

				TPolicyDetailsWithRule dto = new TPolicyDetailsWithRule();
				dto.setPtvid(ptvidTmp);
				dto.setEveid(eveidTmp);
				dto.setModid(modidTmp);

				if (pollstr[selIndex] == null || pollstr[selIndex].equals("")) {
					dto.setPollNull(true);
				} else
					dto.setPoll(pollTmp);

				dto.setValue1(value1Tmp);
				dto.setValue1Rule(value1RuleTmp);
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
				dto.setValue2Rule(value2RuleTmp);
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
						TPolicyDetailsWithRulePk pk = dto.createPk();
						this.policyDetailsWithRuleDao.update(pk, dto);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Updated to TPolicyDetailsWithRuleDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
						System.out.println(" Updated to TPolicyDetailsWithRuleDao: pk=" + pk.toString() + "\tdto= " + dto.toString());

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
						TPolicyDetailsWithRulePk pk1 = this.policyDetailsWithRuleDao.insert(dto);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TPolicyDetailsWithRuleDao: " + dto.toString());
					} catch (DataIntegrityViolationException dupe) {
						dupe.printStackTrace();
						TPolicyDetailsWithRulePk pk = dto.createPk();
						this.policyDetailsWithRuleDao.update(pk, dto);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TPolicyDetailsWithRuleDao: pk=" + pk.toString() + "\tdto= " + dto.toString());
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
				TPolicyDetailsWithRule dtoDel = new TPolicyDetailsWithRule();
				dtoDel.setPtvid(Long.parseLong(ptvidStr));
				dtoDel.setEveid(Long.parseLong(eveidStr[i]));
				dtoDel.setModid(Long.parseLong(modidStr[i]));
				TPolicyDetailsWithRulePk pkDel = dtoDel.createPk();
				this.policyDetailsWithRuleDao.delete(pkDel);
				Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TPolicyDetailsWithRuleDao: " + pkDel.toString());
			}
		}
		model.put("messageg", message);
		return model;
	}

	private void addMessage(long eveId, long modId, String msg) throws TEventTypeInitDaoException {
	  if (message.equals(""))
	  	message = "以下策略定制失败：<br/>";
	  TEventTypeInit t = null;
	  if (eveId > 0 && modId > 0)
	  	t = TEventTypeInitDao.findByPrimaryKey(modId, eveId);
	  message += "事件名称：" + (t == null ? "" : t.getMajor()) + ".  原因： " + msg + "<br/>";
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
			String ptvidstr = request.getParameter("ptvid");
			long ptvid = Long.parseLong(ptvidstr);

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

			List<PolDetailDsp> details = new ArrayList<PolDetailDsp>();
			Map<String, Object> detailMap = new HashMap<String, Object>();
			Map<Integer, Object> syslogdetailMap = new HashMap<Integer, Object>();
			// List<PolicySyslog> syslogDetails = new ArrayList<PolicySyslog>();
			List<DspEventsFromPolicySyslog> eventsSyslogDetails = new ArrayList<DspEventsFromPolicySyslog>();
			Map<String, Object> eventsDictionary = new HashMap<String, Object>();
			details = PolDetailDspDao.findByPtvid(ptvid);
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

			List<TModuleInfoInit> module = TModuleInfoInitDao.findAll();

			List<TEventTypeInit> unselected = null; /* */
			List<TEventTypeInit> snmplst = null; /* */
			if (category.equals("1")) { // cate=1 as DEVECE policy
				snmplst = TEventTypeInitDao.listForDeviceSnmpRule(ptvid);
				SortList<TEventTypeInit> sortTei = new SortList<TEventTypeInit>();
				sortTei.Sort(snmplst, "getMajor", null);
			} else if (category.equals("4")) // treated as PORT policy
			{
				snmplst = TEventTypeInitDao.listForPortSnmpRule(ptvid);
				SortList<TEventTypeInit> sortTei = new SortList<TEventTypeInit>();
				sortTei.Sort(snmplst, "getMajor", null);
			} else if (category.equals("9")) // treated as PreDefMib policy
			{
				snmplst = TEventTypeInitDao.listForPreDefMibSnmpRule(ptvid);
				SortList<TEventTypeInit> sortTei = new SortList<TEventTypeInit>();
				sortTei.Sort(snmplst, "getMajor", null);
			}
			List<TEventTypeInit> icmplst = null; /* */
			if (category.equals("1")) { // cate=1 as DEVECE policy
				icmplst = TEventTypeInitDao.listForDeviceIcmpRule(ptvid);
			} else if (category.equals("4")) // treated as PORT policy
			{
				icmplst = TEventTypeInitDao.listForPortIcmpRule(ptvid);
			} else if (category.equals("9")) // treated as PreDefMib policy
			{
				icmplst = TEventTypeInitDao.listForPortIcmpRule(ptvid);
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
				model.put("details", details);
				model.put("unselected", unselected);
			}
			model.put("ptvid", ptvidstr);
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

	public TPolicyDetailsWithRuleDao getPolicyDetailsWithRuleDao() {
		return policyDetailsWithRuleDao;
	}

	public void setPolicyDetailsWithRuleDao(TPolicyDetailsWithRuleDao policyDetailsWithRuleDao) {
		this.policyDetailsWithRuleDao = policyDetailsWithRuleDao;
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

	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}

}
