/**
 * 
 */
package com.ibm.ncs.web.policytemplateapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.PredefmibPolMapDao;
import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.dao.TPolicyTemplateDao;
import com.ibm.ncs.model.dao.TPolicyTemplateScopeDao;
import com.ibm.ncs.model.dao.TPolicyTemplateVerDao;
import com.ibm.ncs.model.dao.spring.DeviceTypeTreeDaoImpl;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.PolicyTemplate;
import com.ibm.ncs.model.dto.PolicyTemplateScope;
import com.ibm.ncs.model.dto.PolicyTemplateVer;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

/**
 * @author root
 * 
 */
public class PolicyDefinitionController implements Controller {

	private TPolicyPublishInfoDao policyPublishInfoDao;
	private TPolicyTemplateDao policyTemplateDao;
	private TPolicyTemplateScopeDao policyTemplateScopeDao;
	private TPolicyTemplateVerDao policyTemplateVerDao;
	private DeviceTypeTreeDao deviceTypeTreeDao;
	private TManufacturerInfoInitDao manufacturerInfoDao;

	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	TDevpolMapDao TDevpolMapDao;
	TLinepolMapDao TLinepolMapDao;
	PredefmibPolMapDao predefmibPolMapDao;
	GenPkNumber genPkNumber;
	String pageView;
	String pageView4ReadOnly;
	String pageView4Delete;
	String message = "";

	public void setManufacturerInfoDao(TManufacturerInfoInitDao manufacturerInfoDao) {
		this.manufacturerInfoDao = manufacturerInfoDao;
	}

	public void setDeviceTypeTreeDao(DeviceTypeTreeDaoImpl deviceTypeTreeDao) {
		this.deviceTypeTreeDao = deviceTypeTreeDao;
	}

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

	public void setPolicyTemplateScopeDao(TPolicyTemplateScopeDao policyTemplateScopeDao) {
		this.policyTemplateScopeDao = policyTemplateScopeDao;
	}

	/**
	 * @param policyTemplateDao
	 *          the policyTemplateDao to set
	 */
	public void setPolicyTemplateDao(TPolicyTemplateDao policyTemplateDao) {
		this.policyTemplateDao = policyTemplateDao;
	}

	/**
	 * @param policyTemplateVerDao
	 *          the policyTemplateVerDao to set
	 */
	public void setPolicyTemplateVerDao(TPolicyTemplateVerDao policyTemplateVerDao) {
		this.policyTemplateVerDao = policyTemplateVerDao;
	}

	/**
	 * @param policyPublishInfoDao
	 *          the policyPublishInfoDao to set
	 */
	public void setPolicyPublishInfoDao(TPolicyPublishInfoDao policyPublishInfoDao) {
		this.policyPublishInfoDao = policyPublishInfoDao;
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
			String cate = request.getParameter("cate");
			String ptvid = request.getParameter("ptvid");
			String mpname = request.getParameter("mpname");

			String description = request.getParameter("description");
			// String insert = request.getParameter("insertOrUpdate");
			String ppiid = request.getParameter("ppiid");

			String formAction = request.getParameter("formAction");
			// System.out.println("In Policy Definition: formaction=" + formAction);
			model.put("cate", cate);
			model.put("ptvid", ptvid);
			model.put("mpname", mpname);
			model.put("ppiid", ppiid);

			if (ptvid == null || ptvid.equals(""))
				ptvid = null;

			String mpid = null;
			try {

				PolicyPublishInfo policyPublishInfo = this.policyPublishInfoDao.findById(ppiid);
				model.put("currentPolicyPublishInfo", policyPublishInfo);

				if (formAction != null) {
					if (formAction.equalsIgnoreCase("delete")) { // delete, tested
						PolicyTemplateVer policyTemplateVer = this.policyTemplateVerDao.findById(ptvid);
						if (policyTemplateVer != null) {
							this.policyTemplateVerDao.delete(policyTemplateVer.getPtvid());
							Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TPolicyTemplateVerDao: " + ptvid);
							// TODO ZHAO delete all children
						}
						model.put("refresh", "true");
						model.put("message", "message.common.delete.success");
						return new ModelAndView(this.pageView4Delete, "definition", model);
					} else if (formAction.equalsIgnoreCase("save") && ptvid != null) { // update;
						                                                                 // tested
						PolicyTemplateVer policyTemplateVer = this.policyTemplateVerDao.findById(ptvid);
						policyTemplateVer.setDescription(description);

						this.policyTemplateVerDao.update(Long.parseLong(ptvid), policyTemplateVer);
						Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TPolicyTemplateVerDao: pk= " + ptvid + "\tdto=" + policyTemplateVer.toString());

						this.policyTemplateScopeDao.deleteAllByPtvid(Long.parseLong(ptvid));
						String[] selectedDeviceTypeIDs = request.getParameterValues("selected_device_type_list");
						if (selectedDeviceTypeIDs != null && selectedDeviceTypeIDs.length > 0) {
							for (String dtid : selectedDeviceTypeIDs) {
								PolicyTemplateScope policyTemplateScope = new PolicyTemplateScope();
								policyTemplateScope.setPtvid(Long.parseLong(ptvid));
								policyTemplateScope.setDtid(Long.parseLong(dtid));
								this.policyTemplateScopeDao.insert(policyTemplateScope);
							}
						}

						model.put("message", "message.common.update.success");

					} else if (formAction.equalsIgnoreCase("add")) {
						PolicyTemplate template = new PolicyTemplate();
						PolicyTemplateVer templateVer = new PolicyTemplateVer();
						String category = cate;
						long ptid = genPkNumber.getID();
						ptvid = Long.toString(genPkNumber.getID());
						if (policyTemplateDao.findByMpname(mpname) != null) {
							Log4jInit.ncsLog.info(this.getClass().getName() + " inserted to PolicyTemplate: " + mpname + ", MPName exists!");
							return new ModelAndView(getPageView(), "definition", model);
						}
						String des = description;
						String name = mpname;
						template.setPtid(ptid);
						template.setMpname(name);
						template.setStatus("D");
						template.setCategory(Long.parseLong(category));
						template.setDescription(des);

						templateVer.setPtvid(Long.parseLong(ptvid));
						templateVer.setPtVersion("1");
						templateVer.setPtid(ptid);
						templateVer.setPpiid(Long.parseLong(ppiid));
						templateVer.setStatus("D");
						templateVer.setDescription(des);

						model.put("ppiid", ppiid);
						model.put("ptid", ptid);
						model.put("ptvid", ptvid);
						model.put("cate", cate);
						model.put("policyTemplate", template);
						model.put("policyTemplateVer", templateVer);
						this.policyTemplateDao.insert(template);
						this.policyTemplateVerDao.insert(templateVer);

						String[] selectedDeviceTypeIDs = request.getParameterValues("selected_device_type_list");
						if (selectedDeviceTypeIDs != null && selectedDeviceTypeIDs.length > 0) {
							PolicyTemplateScope policyTemplateScope = new PolicyTemplateScope();
							for (String dtid : selectedDeviceTypeIDs) {
								policyTemplateScope.setPtvid(Long.parseLong(ptvid));
								policyTemplateScope.setDtid(Long.parseLong(dtid));
								this.policyTemplateScopeDao.insert(policyTemplateScope);
							}
						}

						model.put("message", "message.common.create.success");
						model.put("refresh", "true");
						Log4jInit.ncsLog.info(this.getClass().getName() + " inserted to TPolicyTemplateDao: " + template.toString());
						Log4jInit.ncsLog.info(this.getClass().getName() + " inserted to TPolicyTemplateVerDao: " + templateVer.toString());
						//return new ModelAndView(getPageView(), "definition", model);
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

			List<DeviceTypeTree> selectedDeviceTypes = new ArrayList<DeviceTypeTree>();
			try {
				if (ptvid != null) {
					PolicyTemplateVer policyTemplateVer = null;
					policyTemplateVer = this.policyTemplateVerDao.findById(ptvid);
					if (policyTemplateVer != null) {
						PolicyTemplate policyTempalte = this.policyTemplateDao.findById(Long.toString(policyTemplateVer.getPtid()));
						model.put("policyTemplateVer", policyTemplateVer);
						model.put("mpname", policyTempalte.getMpname());

						List<PolicyTemplateScope> policyTemplateScopes = this.policyTemplateScopeDao.findByPtvd(ptvid);
						for (PolicyTemplateScope scope : policyTemplateScopes) {
							if (scope.getDtid() > 0) {
								DeviceTypeTree deviceType = this.deviceTypeTreeDao.findByDtid(scope.getDtid());
								if (deviceType != null) {
									selectedDeviceTypes.add(deviceType);
								}
							}
						}
						model.put("selectedDeviceTypes", new TreeSet<DeviceTypeTree>(selectedDeviceTypes));
					}
				}
				// Load all device type and manufacturers
				List<DeviceTypeTree> deviceTypes = filterForSearch(this.deviceTypeTreeDao.findDeviceTypeByWhereClause(""), request.getParameter("deveiceTypeSearchText"), request.getParameter("manufacturer_list"));
				deviceTypes.removeAll(selectedDeviceTypes);
				model.put("deviceTypes", new TreeSet<DeviceTypeTree>(deviceTypes));

				List<TManufacturerInfoInit> manufacturers = this.manufacturerInfoDao.findAll();
				model.put("manufacturers", manufacturers);

				if (formAction != null && formAction.equalsIgnoreCase("view")) {
          return new ModelAndView(this.getPageView4ReadOnly(), "definition", model);
				} else {
					return new ModelAndView(getPageView(), "definition", model);
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
		return new ModelAndView(this.getPageView(), "definition", model);

	}

	private List<DeviceTypeTree> filterForSearch(List<DeviceTypeTree> deviceTypes, String searchText, String mrid) {
		if ((searchText == null || searchText.trim().length() == 0) && (mrid == null || mrid.trim().length() == 0)) {
			 return deviceTypes;
		}
		List<DeviceTypeTree> result = new ArrayList<DeviceTypeTree>(deviceTypes);
		for (DeviceTypeTree dt: deviceTypes) {
			  if (mrid != null && mrid.trim().length() > 0 && dt.getMrid() != Long.parseLong(mrid)) {
			  	result.remove(dt);
			  }
			  if (searchText != null && searchText.trim().length() > 0) {
			  	 if (dt.getModel() != null && dt.getModel().toLowerCase().indexOf(searchText.toLowerCase()) < 0) {
			  		 result.remove(dt);
			  	 }
			  }
		}
		return result;
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

	public String getPageView4ReadOnly() {
		return pageView4ReadOnly;
	}

	public void setPageView4ReadOnly(String pageView4ReadOnly) {
		this.pageView4ReadOnly = pageView4ReadOnly;
	}

	public void setPageView4Delete(String pageView4Delete) {
		this.pageView4Delete = pageView4Delete;
	}

}
