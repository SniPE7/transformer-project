/**
 * 
 */
package com.ibm.ncs.web.policyapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.PredefmibPolMapDao;
import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.dao.TPolicyTemplateDao;
import com.ibm.ncs.model.dao.TPolicyTemplateVerDao;
import com.ibm.ncs.model.dto.PolicyTemplateVer;
import com.ibm.ncs.model.dto.PredefmibPolMap;
import com.ibm.ncs.model.dto.TDevpolMap;
import com.ibm.ncs.model.dto.TLinepolMap;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyBasePk;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

/**
 * @author root
 * 
 */
public class PolicyDefinitionController implements Controller {

	private TPolicyPublishInfoDao policyPublishInfoDao;
	private TPolicyTemplateDao policyTemplateDao;
	private TPolicyTemplateVerDao policyTemplateVerDao;

	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	TDevpolMapDao TDevpolMapDao;
	TLinepolMapDao TLinepolMapDao;
	PredefmibPolMapDao predefmibPolMapDao;
	GenPkNumber genPkNumber;
	String pageView;
	String message = "";

	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
	}

	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}

	public void setTDevpolMapDao(TDevpolMapDao devpolMapDao) {
		TDevpolMapDao = devpolMapDao;
	}

	public void setTLinepolMapDao(TLinepolMapDao linepolMapDao) {
		TLinepolMapDao = linepolMapDao;
	}

	public void setPredefmibPolMapDao(PredefmibPolMapDao predefmibPolMapDao) {
		this.predefmibPolMapDao = predefmibPolMapDao;
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

		message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String idxname = request.getParameter("navpolicy");

			String cate = request.getParameter("cate");
			String mpid = request.getParameter("mpid");
			String mpname = request.getParameter("mpname");
			String ppid = request.getParameter("ppid");
			String ppname = request.getParameter("ppname");
			String description = request.getParameter("description");
			// String insert = request.getParameter("insertOrUpdate");

			String formAction = request.getParameter("formAction");
			// System.out.println("In Policy Definition: formaction=" + formAction);
			model.put("cate", cate);
			model.put("mpid", mpid);
			model.put("mpname", mpname);
			model.put("ppid", ppid);
			model.put("ppname", ppname);
			if (mpid == null || mpid.equals(""))
				mpid = null;
			// System.out.println("request string as:cate="+cate+" : mpid="+mpid+" : mpname"+mpname);
			// System.out.println("form get action string as: parameter="+idxname );
			try {

				if (formAction != null) {
					if (formAction.equalsIgnoreCase("delete")) { // delete, tested
						System.out.println("*******In delete policy def");
						// System.out.println("mpid is "+mpid);
						List<TDevpolMap> policys = TDevpolMapDao.findWhereMpidEquals(Long.parseLong(mpid));
						List<TLinepolMap> linepol = TLinepolMapDao.findWhereMpidEquals(Long.parseLong(mpid));
						List<PredefmibPolMap> mibpol = predefmibPolMapDao.findWhereMpidEquals(Long.parseLong(mpid));
						// System.out.println("policys is null ? "+(policys == null));
						if (policys.size() > 0 || linepol.size() > 0 || mibpol.size() > 0) {
							message = "policyapply.delete.ocuppied";
							model.put("message", message);
							model.put("mpid", null);
							model.put("refresh", "true");
							return new ModelAndView(getPageView(), "definition", model);

						}
						TPolicyBasePk pk = new TPolicyBasePk(Long.parseLong(mpid));
						TPolicyBaseDao.delete(pk);
						Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TPolicyBaseDao: " + pk.toString());

						model.put("mpid", null);
						model.put("refresh", "true");
					} else if (formAction.equalsIgnoreCase("save") && mpid != null) { // update;
																																						// tested
						System.out.println("*******In update policy def");
						TPolicyBase policybase;
						policybase = TPolicyBaseDao.findByPrimaryKey(Long.parseLong(mpid));
						policybase.setDescription(description);
						policybase.setMpname(mpname);
						TPolicyBasePk pk = new TPolicyBasePk(Long.parseLong(mpid));
						TPolicyBaseDao.update(pk, policybase);
						Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TPolicyBaseDao: pk= " + pk.toString() + "\tdto=" + policybase.toString());

					} else if (formAction.equalsIgnoreCase("add") || mpid == null) {
						System.out.println("How many times!");
						TPolicyBase policybase = new TPolicyBase();
						TPolicyBase policybase2 = new TPolicyBase();
						String category = cate;
						long id = genPkNumber.getID();
						// policybase2 =
						// TPolicyBaseDao.findWhereMpnameEquals(mpname)==null?null:TPolicyBaseDao.findWhereMpnameEquals(mpname).get(0);
						if (TPolicyBaseDao.findWhereMpnameEquals(mpname) == null || TPolicyBaseDao.findWhereMpnameEquals(mpname).size() == 0)
							policybase2 = null;
						if (policybase2 == null) {
							String des = description;
							String name = mpname;
							policybase.setCategory(Long.parseLong(category));
							policybase.setMpid(id);
							policybase.setMpname(name);
							policybase.setDescription(des);
							model.put("mpid", id);
							model.put("cate", cate);
							model.put("policybase", policybase);
							TPolicyBaseDao.insert(policybase);
							model.put("refresh", "true");
							Log4jInit.ncsLog.info(this.getClass().getName() + " inserted to TPolicyBaseDao: " + policybase.toString());
							return new ModelAndView(getPageView(), "definition", model);
						}
					}
				}
			} catch (UncategorizedSQLException ue) {
				if (formAction.equalsIgnoreCase("delete")) { // delete, tested
					message = "policyDefinitionController.delete.error";
				} else if (formAction.equalsIgnoreCase("save") && mpid != null) { // update;
																																					// tested
					message = "policyDefinitionController.save.error";
				} else if (formAction.equalsIgnoreCase("add") || mpid == null) {
					message = "policyDefinitionController.add.error";
				}
				model.put("message", message);
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ue.getMessage());
				ue.printStackTrace();
			} catch (Exception e) {
				message = "policyDefinitionController.error";
				model.put("message", message);
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
			}

			try {
				if (null != idxname && !"".equals(idxname)) {
					System.out.println("idxname =" + idxname);
					List<String> params = new ArrayList<String>();
					StringTokenizer st = new StringTokenizer(idxname, "||");
					while (st.hasMoreTokens()) {
						String s = st.nextToken();
						params.add(s);
					}
					// System.out.println("splited as "+params);
					cate = params.get(0);
					if (cate.equals("16")) {
						ppid = params.get(1);
						ppname = params.get(2);

						model.put("cate", cate);
						model.put("ppid", ppid);
						model.put("ppname", ppname);
					} else {
						mpid = params.get(1);
						mpname = params.get(2);

						model.put("cate", cate);
						model.put("mpid", mpid);
						model.put("mpname", mpname);
					}
				}

				if (cate != null && cate.equals("16") == true) {// Timeframe
					TPolicyPeriod policyperiod;
					if (ppid != null) {
						policyperiod = TPolicyPeriodDao.findByPrimaryKey(Long.parseLong(ppid));
						model.put("policyperiod", policyperiod);
					}
					// System.out.println("policy definition model: " + model);
					return new ModelAndView("/secure/policyapply/policyPeriodDef.jsp", "definition", model);
				} else {
					// if (cate.equals("16") == false) {//PolicyBase

					TPolicyBase policybase;
					if (mpid != null) {
						policybase = TPolicyBaseDao.findByPrimaryKey(Long.parseLong(mpid));
						if (policybase != null) {
							long ptvid = policybase.getPtvid();
							if (ptvid > 0) {
								PolicyTemplateVer ptv = this.policyTemplateVerDao.findById(Long.toString(ptvid));
								policybase.setPolictTemplateVer(ptv);
								if (ptv != null) {
									PolicyPublishInfo ppi = this.policyPublishInfoDao.findById(Long.toString(ptv.getPpiid()));
									ptv.setPolicyPublishInfo(ppi);
								}
							}
						}
						model.put("policybase", policybase);
					}
					// System.out.println("policy definition model: " + model);
					return new ModelAndView("/secure/policyapply/policyDefinition.jsp", "definition", model);
				}
			} catch (Exception e) {
				message = "policyDefinitionController.error";
				model.put("message", message);
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
			}

			model.put("message", message);
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("/secure/policyapply/policyDefinition.jsp", "definition", model);

	}

	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
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

}
