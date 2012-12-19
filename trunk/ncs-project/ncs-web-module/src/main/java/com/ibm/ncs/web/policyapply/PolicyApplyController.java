/**
 * 
 */
package com.ibm.ncs.web.policyapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dao.PredefmibPolMapDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.dao.TPolicyTemplateDao;
import com.ibm.ncs.model.dao.TPolicyTemplateVerDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.PolicyTemplateVer;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.PredefmibPolMap;
import com.ibm.ncs.model.dto.PredefmibPolMapPk;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDevpolMap;
import com.ibm.ncs.model.dto.TDevpolMapPk;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TLinepolMap;
import com.ibm.ncs.model.dto.TLinepolMapPk;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.model.exceptions.PredefmibInfoDaoException;
import com.ibm.ncs.model.exceptions.PredefmibPolMapDaoException;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TDevpolMapDaoException;
import com.ibm.ncs.model.exceptions.TGrpNetDaoException;
import com.ibm.ncs.model.exceptions.TLinepolMapDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;
import com.ibm.ncs.model.exceptions.TPolicyPeriodDaoException;
import com.ibm.ncs.model.exceptions.TPortInfoDaoException;
import com.ibm.ncs.service.PolicyAppServices;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

/**
 * @author root
 * 
 */
public class PolicyApplyController implements Controller {

	private TPolicyPublishInfoDao policyPublishInfoDao;
	private TPolicyTemplateDao policyTemplateDao;
	private TPolicyTemplateVerDao policyTemplateVerDao;

	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	TGrpNetDao TGrpNetDao;
	PolicyAppServices PolicyAppServices;
	String pageView;
	TDevpolMapDao TDevpolMapDao;
	TLinepolMapDao TLinepolMapDao;
	TDeviceInfoDao TDeviceInfoDao;
	TPortInfoDao TPortInfoDao;
	PredefmibInfoDao PredefmibInfoDao;
	PredefmibPolMapDao PredefmibPolMapDao;
	private String message = "";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}

	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}

	public TPortInfoDao getTPortInfoDao() {
		return TPortInfoDao;
	}

	public void setTPortInfoDao(TPortInfoDao portInfoDao) {
		TPortInfoDao = portInfoDao;
	}

	public TLinepolMapDao getTLinepolMapDao() {
		return TLinepolMapDao;
	}

	public void setTLinepolMapDao(TLinepolMapDao linepolMapDao) {
		TLinepolMapDao = linepolMapDao;
	}

	public PolicyAppServices getPolicyAppServices() {
		return PolicyAppServices;
	}

	public void setPolicyAppServices(PolicyAppServices PolicyAppServices) {
		this.PolicyAppServices = PolicyAppServices;
	}

	public void setTGrpNetDao(TGrpNetDao grpNetDao) {
		TGrpNetDao = grpNetDao;
	}

	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
	}

	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}

	public TPolicyBaseDao getTPolicyBaseDao() {
		return TPolicyBaseDao;
	}

	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}

	public TGrpNetDao getTGrpNetDao() {
		return TGrpNetDao;
	}

	public TDevpolMapDao getTDevpolMapDao() {
		return TDevpolMapDao;
	}

	public void setTDevpolMapDao(TDevpolMapDao devpolMapDao) {
		TDevpolMapDao = devpolMapDao;
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
			parseInput(request, model);

			// Initialize PolicyBase which will dispay in content
			getPolicyBase(request, model);

			// Initialize contents listed in "Group"
			getNodeList(model);
			// System.out.println("step1="+model);

			// Initialize device related contents listed in "Device/Port",
			// "Unselected" list and "Selected" list
			getDevicesOfNodes(model);

			String[] selecting = (String[]) request.getParameterValues("selecting");
			String[] unselected = (String[]) request.getParameterValues("unselected");
		  String mpid = request.getParameter("mpid");
			String ppid = request.getParameter("ppid");
			String tcate = request.getParameter("cate");
			String selectDevice = request.getParameter("selectDevice");

			if (selecting != null || unselected != null) {
				if (tcate.equals("9")) { // predef mib policy

					// add to PreDefMib_pol_map
					PredefmibPolMap pdm = null;
					try {
						List<PredefmibInfo> preSelected = null;
						if (mpid != null && !mpid.equals("")) { // previously selected
																										// objects for this device
							long devidLong = 0;
							if (selectDevice != null && !selectDevice.equals(""))
								devidLong = Long.parseLong(selectDevice);
							preSelected = getPreSelectedPDMinfo(devidLong, tcate, Long.parseLong(mpid));
							if (preSelected != null)
								System.out.println("\t??????----???????/after  remove=============preSelected.size=" + preSelected.size());
						}

						if (selecting != null) {
							for (int i = 0; i < selecting.length; i++) {
								PredefmibInfo tp = PredefmibInfoDao.findByPrimaryKey(Long.parseLong(selecting[i]));
								if (preSelected != null && preSelected.contains(tp)) {
									preSelected.remove(tp);
								}
								pdm = PredefmibPolMapDao.findByPrimaryKey(Long.parseLong(selecting[i]));
								if (pdm == null) {
									pdm = new PredefmibPolMap();
									pdm.setPdmid(Long.parseLong(selecting[i]));
									if (mpid != null && !mpid.equals(""))
										pdm.setMpid(Long.parseLong(mpid));
									PredefmibPolMapDao.insert(pdm);
									Log4jInit.ncsLog.info(this.getClass().getName() + " inserted to PredefmibPolMapDao: " + pdm.toString());
								} else {
									// update the record in t_devpol_map table when devid is
									// already existed in it
									pdm.setMpid(Long.parseLong(mpid));
									PredefmibPolMapPk pk = pdm.createPk();
									PredefmibPolMapDao.update(pk, pdm);
									Log4jInit.ncsLog.info(this.getClass().getName() + " updated to PredefmibPolMapDao: pk= " + pk + " dto=" + pdm.toString());
								}
							}
						}
						// delete records that deselected
						if (preSelected != null) {
							for (int i = 0; i < preSelected.size(); i++) {
								pdm = PredefmibPolMapDao.findByPrimaryKey(preSelected.get(i).getPdmid());
								PredefmibPolMapPk pk = pdm.createPk();
								if (pdm.getPpid() > 0) {
									pdm.setMpid(0);
									PredefmibPolMapDao.update(pk, pdm);
									Log4jInit.ncsLog.info(this.getClass().getName() + " updated to PredefmibPolMapDao: pk= " + pk + " dto=" + pdm.toString());

								} else {
									PredefmibPolMapDao.delete(pk);
									// System.out.println("\t\t&&&&&&&&" +
									// this.getClass().getName() +
									// " delete to TLinepolMapDao: pk= " + pk + " dto=" +
									// tm.toString());
									Log4jInit.ncsLog.info(this.getClass().getName() + " delete from PredefmibPolMapDao: pk= " + pk);
								}
							}
						}
					} catch (Exception e) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
						e.printStackTrace();
					}
				} else if (tcate.equals("4")) { // port policy , ready, tested

					// add to t_devpol_map
					TLinepolMap tm = null;
					try {
						List<TPortInfo> preSelected = null;
						if (mpid != null && !mpid.equals("")) { // previously selected
																										// objects for this device
							long devidLong = 0;
							if (selectDevice != null && !selectDevice.equals(""))
								devidLong = Long.parseLong(selectDevice);
							preSelected = getPreSelectedPorts(devidLong, tcate, Long.parseLong(mpid));
							if (preSelected != null)
								System.out.println("\t??????----???????/after  remove=============preSelected.size=" + preSelected.size());
						}

						if (selecting != null) {
							for (int i = 0; i < selecting.length; i++) {
								TPortInfo tp = TPortInfoDao.findByPrimaryKey(Long.parseLong(selecting[i]));
								if (preSelected != null && preSelected.contains(tp)) {
									preSelected.remove(tp);
								}
								tm = TLinepolMapDao.findByPrimaryKey(Long.parseLong(selecting[i]));
								if (tm == null) {
									tm = new TLinepolMap();
									tm.setPtid(Long.parseLong(selecting[i]));
									if (mpid != null && !mpid.equals(""))
										tm.setMpid(Long.parseLong(mpid));
									TLinepolMapDao.insert(tm);
									Log4jInit.ncsLog.info(this.getClass().getName() + " inserted to TLinepolMapDao: " + tm.toString());
								} else {
									// update the record in t_devpol_map table when devid is
									// already existed in it
									tm.setMpid(Long.parseLong(mpid));
									TLinepolMapPk pk = tm.createPk();
									TLinepolMapDao.update(pk, tm);
									Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TLinepolMapDao: pk= " + pk + " dto=" + tm.toString());
								}
							}
						}
						// delete records that deselected
						if (preSelected != null) {
							for (int i = 0; i < preSelected.size(); i++) {
								tm = TLinepolMapDao.findByPrimaryKey(preSelected.get(i).getPtid());
								TLinepolMapPk pk = tm.createPk();
								if (tm.getPpid() > 0) {
									tm.setMpid(0);
									TLinepolMapDao.update(pk, tm);
									Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TLinepolMapDao: pk= " + pk + " dto=" + tm.toString());

								} else {
									TLinepolMapDao.delete(pk);
									// System.out.println("\t\t&&&&&&&&" +
									// this.getClass().getName() +
									// " delete to TLinepolMapDao: pk= " + pk + " dto=" +
									// tm.toString());
									Log4jInit.ncsLog.info(this.getClass().getName() + " delete from TLinepolMapDao: pk= " + pk);
								}
							}
						}
					} catch (Exception e) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
						e.printStackTrace();
					}
				} else if (tcate.equals("16")) { // timeframe MIB Policy
				// System.out.println("Step 1: \tIn tcate.equals(16 mpid=" + mpid);
					TDevpolMap tdm = null;
					TLinepolMap tlm = null;
					PredefmibPolMap pdmmap = null;
					boolean devFlag = false, portFlag = false, pdmFlag = false;
					if (selectDevice != null && !selectDevice.equals("0")) {
						portFlag = true;
						pdmFlag = true;
					} else if (selectDevice != null && selectDevice.equals("0"))
						devFlag = true;
					try {
						List<TDevpolMap> preSelectedDevice = null;
						List<TPortInfo> preSelectedPort = null;
						List<PredefmibInfo> preSelectedPdm = null;
						if (ppid != null && !ppid.equals("")) { // previously selected
																										// objects devices
							preSelectedDevice = TDevpolMapDao.findWherePpidEquals(Long.parseLong(ppid));
							long devidLong = 0;
							if (selectDevice != null && !selectDevice.equals(""))
								devidLong = Long.parseLong(selectDevice);
							preSelectedPort = getPreSelectedPorts(devidLong, tcate, Long.parseLong(ppid));
							preSelectedPdm = getPreSelectedPDMinfo(devidLong, tcate, Long.parseLong(ppid));
							// if(preSelectedPort!= null)
							// System.out.println("\t??????----???????/after  remove=============preSelected.size="
							// + preSelectedPort.size());
						}

						if (selecting != null) {
							for (int i = 0; i < selecting.length; i++) {
								long ptid;
								long devid;
								long pdmid;
								if (selecting[i].startsWith("device_")) {
									devFlag = true;
									devid = Long.parseLong(selecting[i].substring(selecting[i].indexOf("_") + 1));
									tdm = TDevpolMapDao.findByPrimaryKey(devid);
									if (preSelectedDevice != null && preSelectedDevice.contains(tdm)) {
										System.out.println("selecting starts with device_: i=" + i + "\tremove TDevelopMap:" + tdm);
										preSelectedDevice.remove(tdm);
									}

									if (tdm == null) {
										// insert to t_devpol_map when no record about this devid in
										// this table
										tdm = new TDevpolMap();
										tdm.setDevid(devid);
										if (ppid != null && !ppid.equals(""))
											tdm.setPpid(Long.parseLong(ppid));
										TDevpolMapDao.insert(tdm);
										Log4jInit.ncsLog.info(this.getClass().getName() + " inserted to TDevpolMapDao: " + tdm.toString());
									} else {
										// update the record in t_devpol_map table when devid is
										// already existed in it
										tdm.setPpid(Long.parseLong(ppid));
										TDevpolMapPk pk = tdm.createPk();
										TDevpolMapDao.update(pk, tdm);
										Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TDevpolMapDao: pk= " + pk + " dto=" + tdm.toString());
									}

								} else if (selecting[i].startsWith("port_")) {
									portFlag = true;
									ptid = Long.parseLong(selecting[i].substring(selecting[i].indexOf("_") + 1));

									TPortInfo tp = TPortInfoDao.findByPrimaryKey(ptid);
									if (preSelectedPort != null && preSelectedPort.contains(tp)) {
										preSelectedPort.remove(tp);
										System.out.println("selecting starts with port_: i=" + i + "\tremove TPortInfoDao:" + tp);
									}
									tlm = TLinepolMapDao.findByPrimaryKey(ptid);
									if (tlm == null) {
										tlm = new TLinepolMap();
										tlm.setPtid(ptid);
										if (ppid != null && !ppid.equals(""))
											tlm.setPpid(Long.parseLong(ppid));
										TLinepolMapDao.insert(tlm);
										Log4jInit.ncsLog.info(this.getClass().getName() + " inserted to TLinepolMapDao: " + tlm.toString());
									} else {
										// update the record in t_devpol_map table when devid is
										// already existed in it
										tlm.setPpid(Long.parseLong(ppid));
										TLinepolMapPk pk = tlm.createPk();
										TLinepolMapDao.update(pk, tlm);
										Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TLinepolMapDao: pk= " + pk + " dto=" + tlm.toString());
									}
								} else if (selecting[i].startsWith("pdm_")) {
									pdmFlag = true;
									pdmid = Long.parseLong(selecting[i].substring(selecting[i].indexOf("_") + 1));

									PredefmibInfo tp = PredefmibInfoDao.findByPrimaryKey(pdmid);
									if (preSelectedPdm != null && preSelectedPdm.contains(tp)) {
										preSelectedPdm.remove(tp);
										System.out.println("selecting starts with pdm_: i=" + i + "\tremove PredefmibInfoDao:" + tp);
									}
									pdmmap = PredefmibPolMapDao.findByPrimaryKey(pdmid);
									if (pdmmap == null) {
										pdmmap = new PredefmibPolMap();
										pdmmap.setPdmid(pdmid);
										if (ppid != null && !ppid.equals(""))
											pdmmap.setPpid(Long.parseLong(ppid));
										PredefmibPolMapDao.insert(pdmmap);
										Log4jInit.ncsLog.info(this.getClass().getName() + " inserted to PredefmibPolMapDao: " + pdmmap.toString());
									} else {
										// update the record in t_devpol_map table when devid is
										// already existed in it
										pdmmap.setPpid(Long.parseLong(ppid));
										PredefmibPolMapPk pk = pdmmap.createPk();
										PredefmibPolMapDao.update(pk, pdmmap);
										Log4jInit.ncsLog.info(this.getClass().getName() + " updated to PredefmibPolMapDao: pk= " + pk + " dto=" + pdmmap.toString());
									}
								}
							}
						}// end of if selecting is null

						// delete records that deselected
						if (preSelectedPort != null && portFlag) {
							for (int i = 0; i < preSelectedPort.size(); i++) {
								tlm = TLinepolMapDao.findByPrimaryKey(preSelectedPort.get(i).getPtid());
								TLinepolMapPk pk = tlm.createPk();
								if (tlm.getMpid() > 0) {
									tlm.setPpid(0);
									TLinepolMapDao.update(pk, tlm);
									Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TLinepolMapDao: pk= " + pk + " dto=" + tlm.toString());
								} else {
									TLinepolMapDao.delete(pk);
									Log4jInit.ncsLog.info(this.getClass().getName() + " delete from TLinepolMapDao: pk= " + pk);
								}
							}
						}
						// delete records that deselected
						if (preSelectedPdm != null && pdmFlag) {
							for (int i = 0; i < preSelectedPdm.size(); i++) {
								pdmmap = PredefmibPolMapDao.findByPrimaryKey(preSelectedPdm.get(i).getPdmid());
								PredefmibPolMapPk pk = pdmmap.createPk();
								if (pdmmap.getMpid() > 0) {
									pdmmap.setPpid(0);
									PredefmibPolMapDao.update(pk, pdmmap);
									Log4jInit.ncsLog.info(this.getClass().getName() + " updated to PredefmibPolMapDao: pk= " + pk + " dto=" + pdmmap.toString());
								} else {
									PredefmibPolMapDao.delete(pk);
									Log4jInit.ncsLog.info(this.getClass().getName() + " delete from PredefmibPolMapDao: pk= " + pk);
								}
							}
						}
						// delete records that deselected
						if (preSelectedDevice != null && devFlag) {
							for (int i = 0; i < preSelectedDevice.size(); i++) {
								System.out.println("preSelectedDevice!=null" + i + "=" + preSelectedDevice.get(i));
								tdm = preSelectedDevice.get(i);
								// --filter out device which not in certain node
								if (isWithinTheNode(tdm, model) == false) {
									continue;
								}// --filter end
								TDevpolMapPk pk = tdm.createPk();
								if (tdm.getMpid() > 0) {
									tdm.setPpid(0);
									TDevpolMapDao.update(pk, tdm);
									Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TDevpolMapDao: pk= " + pk + " dto=" + tdm.toString());
								} else {
									TDevpolMapDao.delete(pk);
									Log4jInit.ncsLog.info(this.getClass().getName() + " delete from TDevPolMapDao: pk= " + pk);
								}
							}
						}
					} catch (Exception e) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
						e.printStackTrace();
					}
				} else { // device Policy ready, tested
					TDevpolMap tm = null;
					try {
						List<TDevpolMap> preSelected = null;
						if (mpid != null && !mpid.equals("")) { // previously selected
																										// objects
							preSelected = TDevpolMapDao.findWhereMpidEquals(Long.parseLong(mpid));
						}

						if (selecting != null) {
							for (int i = 0; i < selecting.length; i++) {
								tm = TDevpolMapDao.findByPrimaryKey(Long.parseLong(selecting[i]));
								if (preSelected != null && preSelected.contains(tm)) {
									// System.out.println("\t=============before remove=============preSelected.size="
									// + preSelected.size());
									preSelected.remove(tm);
									// System.out.println("\t=============after remove=============preSelected.size="
									// + preSelected.size());
								}
								if (tm == null) {
									// insert to t_devpol_map when no record about this devid in
									// this table
									tm = new TDevpolMap();
									tm.setDevid(Long.parseLong(selecting[i]));
									if (mpid != null && !mpid.equals(""))
										tm.setMpid(Long.parseLong(mpid));
									TDevpolMapDao.insert(tm);
									// System.out.println("\t\t&&&&&&&&" +
									// this.getClass().getName() + " inserted to TDevpolMapDao: "
									// + tm.toString());
									Log4jInit.ncsLog.info(this.getClass().getName() + " inserted to TDevpolMapDao: " + tm.toString());
								} else {
									// update the record in t_devpol_map table when devid is
									// already existed in it
									tm.setMpid(Long.parseLong(mpid));
									TDevpolMapPk pk = tm.createPk();
									TDevpolMapDao.update(pk, tm);
									// System.out.println("\t\t&&&&&&&&" +
									// this.getClass().getName() +
									// " updated to TDevpolMapDao: pk= " + pk + " dto=" +
									// tm.toString());
									Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TDevpolMapDao: pk= " + pk + " dto=" + tm.toString());
								}
							}
						}// end of if(selecting!=null)

						// delete records that deselected
						if (preSelected != null) {
							for (int i = 0; i < preSelected.size(); i++) {
								tm = preSelected.get(i);
								// --filter out device which not in certain node
								if (isWithinTheNode(tm, model) == false) {
									continue;
								}// --filter end
								TDevpolMapPk pk = tm.createPk();
								if (tm.getPpid() > 0) {
									tm.setMpid(0);
									TDevpolMapDao.update(pk, tm);
									// System.out.println("\t\t&&&&&&&&" +
									// this.getClass().getName() +
									// " updated to TDevpolMapDao: pk= " + pk + " dto=" +
									// tm.toString());
									Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TDevpolMapDao: pk= " + pk + " dto=" + tm.toString());
								} else {
									TDevpolMapDao.delete(pk);
									Log4jInit.ncsLog.info(this.getClass().getName() + " delete from TDevpolMapDao: pk= " + pk);
								}
							}
						}
					} catch (Exception e) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
						e.printStackTrace();
					}
				}// end of device policy
			}// end of if(selecting!=null||unselected!=null)

			parseInput(request, model);
			// model = encodeCateName(model); //not used

			// Initialize contents listed in "Group"
			getNodeList(model);
			// System.out.println("step1="+model);

			// Initialize device related contents listed in "Device/Port",
			// "Unselected" list and "Selected" list
			getDevicesOfNodes(model);

			String cate = (String) model.get("cate");

			try {
				// if "Port/Predefined Mib/Timeframe Policy" is selected,
				if (cate.equals("4") || cate.equals("16")) {
					getPortsOfDevice(model);
				}
				if (cate.equals("9") || cate.equals("16")) {
					getPdmsOfDevice(model);
				}
			} catch (Exception e) {
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
			}

			String selectNode = (String) model.get("selectNode");
			if (selectNode != null && !selectNode.equals("") && !selectNode.equals("0")) {
				// display device/port select list when choosing port/timeframe/predef MIB index
				if (cate.equals("4") || cate.equals("16") || cate.equals("9")) {
					model.put("dispdevinfo", true);
				}
				// display all the selection parts when a valid group name is selected
				model.put("dispdualselect", true);
			}

		} catch (DaoException e) {
			e.printStackTrace();
			message = "global.db.error";
			model.put("message", message);
			return new ModelAndView("/dberror.jsp", "model", model);
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(), "model", model);

	}

	private void getPolicyBase(HttpServletRequest request, Map<String, Object> model) throws DaoException {
	  String mpid = request.getParameter("mpid");
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
  }

	private boolean isWithinTheNode(TDevpolMap tm, Map<String, Object> model) {
		boolean ret = false;
		try {
			List<TDeviceInfo> deviceinfo = (List<TDeviceInfo>) model.get("devicetmp");
			Long tm_devid = tm.getDevid();
			for (TDeviceInfo dev : deviceinfo) {
				if (dev.getDevid() == tm_devid) {
					ret = true;
					break;
				}
			}
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured at isWithinTheNode():\n" + e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	private void parseInput(HttpServletRequest request, Map<String, Object> model) {
		String cate = request.getParameter("cate");
		String mpid = request.getParameter("mpid"); // for other policies
		String ppid = request.getParameter("ppid"); // for timeframe policy
		String mpname = request.getParameter("mpname");
		String selectNode = request.getParameter("selectNode");
		String selectDevice = request.getParameter("selectDevice");
		String selectDevice1 = request.getParameter("selectDevice1");
		// System.out.println("^^^^^^^^^^^^^^^^^select Service =" + selectDevice +
		// "\t selectSercie1=" + selectDevice1);
		if (mpname == null || mpname.equals("")) {
			if (cate != null && !cate.equalsIgnoreCase("") && !cate.equalsIgnoreCase("16")) {
				TPolicyBase pBase = null;
				if (mpid != null && !mpid.equals("")) {
					try {
						pBase = TPolicyBaseDao.findByPrimaryKey(Long.parseLong(mpid));
						if (pBase != null) {
							model.put("mpname", pBase.getMpname());
						}
					} catch (NumberFormatException e) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
						e.printStackTrace();
					} catch (TPolicyBaseDaoException e) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
						e.printStackTrace();
					}
				}
			} else {
				TPolicyPeriod pPeriod = null;
				if (ppid != null && !ppid.equals("")) {
					try {
						pPeriod = TPolicyPeriodDao.findByPrimaryKey(Long.parseLong(ppid));
						if (pPeriod != null) {
							model.put("mpname", pPeriod.getPpname());
						}
					} catch (NumberFormatException e) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
						e.printStackTrace();
					} catch (TPolicyPeriodDaoException e) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
						e.printStackTrace();
					}
				}

			}
		} else {
			model.put("mpname", mpname);
		}
		// System.out.println("\t\tmpname=^^^" + mpname);

		model.put("cate", cate);
		model.put("mpid", mpid);
		model.put("ppid", ppid);
		model.put("selectNode", selectNode);
		if (selectDevice != null && !selectDevice.equals("0")) { // has a valid
																														 // selection in
																														 // jsp, but not
																														 // seleting
																														 // "Please select a group"
			model.put("selectDevice", selectDevice);
		} else {
			model.put("selectDevice", selectDevice1);
			selectDevice = selectDevice1;
		}
	}
	private void getNodeList(Map<String, Object> model) throws TGrpNetDaoException {
		// There are 6 fields in table T_GRP_NET they are
		// gid,gname,supid,levels,description,unm
		List<TGrpNet> _result = TGrpNetDao.findAll();

		Map<String, Object> tree = new TreeMap<String, Object>();
		Map<String, Object> names = new TreeMap<String, Object>();
		// Set<Long> roots = new HashSet<Long>();
		for (TGrpNet dto : _result) {

			String supkey, curkey;
			// if(dto.getUnmallocedipsetflag().equals("1")){
			// supkey= "z_"+dto.getSupid();
			// }else{
			// supkey = dto.getSupid()+"";
			// }
			supkey = dto.getSupid() + "";
			if (dto.getUnmallocedipsetflag().equals("1")) {
				curkey = "z_" + dto.getGid();
			} else {
				curkey = dto.getGid() + "";
			}

			Map<String, Object> listsub = null;
			if (tree.containsKey(supkey) == true) {
				listsub = (Map<String, Object>) tree.get(supkey);
			} else {
				listsub = new TreeMap<String, Object>();
				tree.put(supkey, listsub);
			}
			// Long subkey = new Long(dto.getGid());
			if (listsub.containsKey(curkey) == false) {
				listsub.put(curkey, dto);
			}

			// roots.add(new Long(dto.getGid()));
			names.put(curkey, dto);
		}
		List<String> rootlist = new ArrayList<String>();
		for (String s : tree.keySet()) {
			// if (roots.contains(s) == false) {
			// rootlist.add(s);
			// }
			if (names.containsKey(s) == false) {
				rootlist.add(s);
			}
		}
		// grpnet.put("ipnettree", tree);
		// grpnet.put("rootlist", rootlist);
		// grpnet.put("names", names);
		// --end of node list

		model.put("ipnettree", tree);
		model.put("rootlist", rootlist);
		model.put("names", names);
	}

	private void getDevicesOfNodes(Map<String, Object> model) throws TDeviceInfoDaoException, TListIpDaoException, TDevpolMapDaoException {
		String selectNode = (String) model.get("selectNode");
		String selectDevice = (String) model.get("selectDevice");
		String cate = (String) model.get("cate");
		String mpid = (String) model.get("mpid");
		String ppid = (String) model.get("ppid");
		
		List<TDeviceInfo> deviceinfo = null;
		List<TDeviceInfo> deviceinfoed = new ArrayList<TDeviceInfo>();
		Map<Long, String> policyApplied = new HashMap<Long, String>();
		List<TDeviceInfo> devtmp = null;
		TDeviceInfo tDeviceInfo = null;
		// System.out.println("********in getDevicesOfNodes**********");
		try {
			if (selectNode != null && !selectNode.equals("") && !selectNode.equals("0")) { 
				 //when a valid group is selected
				long agid = Long.parseLong(selectNode);
				// System.out.println("\tgid="+agid);
				deviceinfo = PolicyAppServices.getDeviceInfoListOfTheGidNode(agid);
				devtmp     = PolicyAppServices.getDeviceInfoListOfTheGidNode(agid);
				SortList<TDeviceInfo> sortdevinfo = new SortList<TDeviceInfo>();
				sortdevinfo.Sort(devtmp, "getDevip", null);
				if (deviceinfo != null) {
					for (int i = 0; i < deviceinfo.size(); i++) {
						tDeviceInfo = deviceinfo.get(i);
						long devid = tDeviceInfo.getDevid();
						// find the devices that already defined policy for it, and remove it from the selectable list
						TDevpolMap tm = TDevpolMapDao.findByPrimaryKey(devid);
						if (tm != null) {
							if (cate != null && !cate.equalsIgnoreCase("") && !cate.equalsIgnoreCase("16")) {
								// 非时段策略
								long mpidLong = Long.parseLong(mpid);
								if (tm.getMpid() == mpidLong) {
									// 设备已经应用此策略
									devtmp.remove(tDeviceInfo);
									deviceinfoed.add(tDeviceInfo);
								} else {
									// construct the applied policy
									TPolicyBase tpbase = TPolicyBaseDao.findByPrimaryKey(tm.getMpid());
									if (tpbase != null) {
										// System.out.println("getDevicesOfNodes TPolicyBase mpid="
										// + tpbase.getMpid() + " \t mpname is" +
										// tpbase.getMpname());
										policyApplied.put(tm.getDevid(), tpbase.getMpname());
									}
								}
							} else if (cate != null && cate.equalsIgnoreCase("16")) {
								// 时段策略
								long ppidLong = 0;
								if (ppid != null)
									ppidLong = Long.parseLong(ppid);
								if (tm.getPpid() == ppidLong) {
									devtmp.remove(tDeviceInfo);
									deviceinfoed.add(tDeviceInfo);
								} else {
									// construct the applied policy
									TPolicyPeriod tpbase = TPolicyPeriodDao.findByPrimaryKey(tm.getPpid());
									if (tpbase != null) {
										// System.out.println("getDevicesOfNodes TPolicyPeriodDao ppid="
										// + tpbase.getPpid() + " \t mpname is" +
										// tpbase.getPpname());
										policyApplied.put(tm.getDevid(), tpbase.getPpname());
									}
								}
							}
						}
					}// end of for
				}

				SortList<TDeviceInfo> sortdevselecting = new SortList<TDeviceInfo>();
				sortdevselecting.Sort(deviceinfoed, "getDevip", null);
				model.remove("devicepolicyapplied");
				model.put("devicepolicyapplied", policyApplied);

				// to be confirmed: what will be listed in "Device/Port" selection and
				// selected list
				if (selectDevice != null && !selectDevice.equals("0") && !selectDevice.equals("")) {// if
																																														// device/prot
																																														// is
																																														// selected
					model.put("deviceinfoed", null);
					model.put("deviceinfo", null);
				} else { // if device/port is not selected
					model.put("deviceinfoed", deviceinfoed);
					model.put("deviceinfo", devtmp);
				}
				model.put("devicetmp", deviceinfo); // confirmed: display all the IPs in
																						// this group
				/*
				 * model.put("deviceinfoed", deviceinfoed); model.put("deviceinfo",
				 * devtmp);
				 */
			}
		} catch (NumberFormatException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		} catch (Exception exp) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + exp.getMessage());
			exp.printStackTrace();
		}
		// System.out.println("*********End of getDevicesOfNodes*********");
	}

	private void getPortsOfDevice(Map<String, Object> model) throws TPortInfoDaoException, TLinepolMapDaoException {
		String selectDevice = (String) model.get("selectDevice");
		List<TPortInfo> portinfo = null;
		List<TPortInfo> portinfoed = new ArrayList<TPortInfo>();
		String ppid = (String) model.get("ppid");
		String mpid = (String) model.get("mpid");
		List<TPortInfo> tmpport = null;
		TPortInfo tPortInfo = null;
		TLinepolMap tm = null;
		Map<Long, String> policyApplied = new HashMap<Long, String>();
		// System.out.println("*********In getPortsOfDevice*********");
		try {
			// if port/device is selected
			if (selectDevice != null && !selectDevice.equals("") && !selectDevice.equals("0")) {
				// System.out.println("\tselectDevice="+selectDevice);
				long adevid = Long.parseLong(selectDevice);
				// System.out.println("\tadevid="+adevid);
				portinfo = PolicyAppServices.findPortInfoByDevid(adevid);
				tmpport = PolicyAppServices.findPortInfoByDevid(adevid);
				SortList<TPortInfo> sortportinfo = new SortList<TPortInfo>();
				sortportinfo.Sort(tmpport, "getIfdescr", null);
				for (int i = 0; i < portinfo.size(); i++) {
					tPortInfo = portinfo.get(i);
					long ptid = tPortInfo.getPtid();
					tm = TLinepolMapDao.findByPrimaryKey(ptid);
					if (tm != null) {
						String cate = (String) model.get("cate");
						if (cate != null && !cate.equalsIgnoreCase("") && !cate.equalsIgnoreCase("16")) {
							long mpidLong = Long.parseLong(mpid);
							if (tm.getMpid() == mpidLong) {
								tmpport.remove(tPortInfo);
								portinfoed.add(tPortInfo);
							} else {
								// construct the applied policy
								TPolicyBase tpbase = null;
								try {
									tpbase = TPolicyBaseDao.findByPrimaryKey(tm.getMpid());

									if (tpbase != null) {
										// System.out.println("getPortsOfDevice TPolicyBase ptid=" +
										// tm.getPtid() + " \t mpname is" + tpbase.getMpname());
										policyApplied.put(tm.getPtid(), tpbase.getMpname());
									}
								} catch (TPolicyBaseDaoException e) {
									e.printStackTrace();
								}
							}
						} else if (cate != null && cate.equalsIgnoreCase("16")) {
							long ppidLong = 0;
							if (ppid != null)
								ppidLong = Long.parseLong(ppid);
							if (tm.getPpid() == ppidLong) {
								tmpport.remove(tPortInfo);
								portinfoed.add(tPortInfo);
							} else {
								// construct the applied policy
								TPolicyPeriod tpbase = null;
								try {
									tpbase = TPolicyPeriodDao.findByPrimaryKey(tm.getPpid());

									if (tpbase != null) {
										// System.out.println("getPortsOfDevice TPolicyBase ptid=" +
										// tm.getPtid() + " \t mpname is" + tpbase.getPpname());
										policyApplied.put(tm.getPtid(), tpbase.getPpname());
									}
								} catch (TPolicyPeriodDaoException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}

				SortList<TPortInfo> sortportselcting = new SortList<TPortInfo>();
				sortportselcting.Sort(portinfoed, "getIfdescr", null);
				model.remove("portpolicyapplied");
				model.put("portpolicyapplied", policyApplied);
				model.put("portinfoed", portinfoed);
				model.put("portinfo", tmpport);
			}
		} catch (NumberFormatException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}

		// System.out.println("*********end of getPortsOfDevice*********");
	}

	private void getPdmsOfDevice(Map<String, Object> model) throws PredefmibPolMapDaoException, PredefmibInfoDaoException {
		String selectDevice = (String) model.get("selectDevice");
		List<PredefmibInfo> pdminfo = null;
		List<PredefmibInfo> pdminfoed = new ArrayList<PredefmibInfo>();
		String ppid = (String) model.get("ppid");
		String mpid = (String) model.get("mpid");
		List<PredefmibInfo> tmppdm = null;
		PredefmibInfo tPdmInfo = null;
		PredefmibPolMap tm = null;
		Map<Long, String> policyApplied = new HashMap<Long, String>();
		try {
			// if port/device is selected
			if (selectDevice != null && !selectDevice.equals("") && !selectDevice.equals("0")) {
				// System.out.println("\tselectDevice="+selectDevice);
				long adevid = Long.parseLong(selectDevice);
				// System.out.println("\tadevid="+adevid);
				pdminfo = PredefmibInfoDao.findWhereDevidEquals(adevid);
				tmppdm = PredefmibInfoDao.findWhereDevidEquals(adevid);
				SortList<PredefmibInfo> sortmib = new SortList<PredefmibInfo>();
				sortmib.Sort(tmppdm, "getOidindex", null);
				for (int i = 0; i < pdminfo.size(); i++) {
					tPdmInfo = pdminfo.get(i);
					long pdmid = tPdmInfo.getPdmid();
					tm = PredefmibPolMapDao.findByPrimaryKey(pdmid);
					if (tm != null) {
						String cate = (String) model.get("cate");
						if (cate != null && !cate.equalsIgnoreCase("") && !cate.equalsIgnoreCase("16")) {
							long mpidLong = Long.parseLong(mpid);
							if (tm.getMpid() == mpidLong) {
								tmppdm.remove(tPdmInfo);
								pdminfoed.add(tPdmInfo);
							} else {
								// construct the applied policy
								TPolicyBase tpbase = null;
								try {
									tpbase = TPolicyBaseDao.findByPrimaryKey(tm.getMpid());

									if (tpbase != null) {
										policyApplied.put(tm.getPdmid(), tpbase.getMpname());
									}
								} catch (TPolicyBaseDaoException e) {
									e.printStackTrace();
								}
							}
						} else if (cate != null && cate.equalsIgnoreCase("16")) {
							long ppidLong = 0;
							if (ppid != null)
								ppidLong = Long.parseLong(ppid);
							if (tm.getPpid() == ppidLong) {
								tmppdm.remove(tPdmInfo);
								pdminfoed.add(tPdmInfo);
							} else {
								// construct the applied policy
								TPolicyPeriod tpbase = null;
								try {
									tpbase = TPolicyPeriodDao.findByPrimaryKey(tm.getPpid());

									if (tpbase != null) {
										// System.out.println("getPdmsOfDevice TPolicyBase mpid=" +
										// tpbase.getPpid() + " \t mpname is" + tpbase.getPpname());
										policyApplied.put(tm.getPdmid(), tpbase.getPpname());
									}
								} catch (TPolicyPeriodDaoException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}

				SortList<PredefmibInfo> sortmibselecting = new SortList<PredefmibInfo>();
				sortmibselecting.Sort(pdminfoed, "getOidindex", null);
				model.remove("pdmdpolicyapplied");
				model.put("pdmspolicyapplied", policyApplied);
				model.put("pdminfoed", pdminfoed);
				model.put("pdminfo", tmppdm);
			}
		} catch (NumberFormatException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	private List<TPortInfo> getPreSelectedPorts(long devidLong, String cate, long idLong) {

		List<TPortInfo> portinfo = null;
		try {
			portinfo = PolicyAppServices.findPortInfoByDevid(devidLong);
		} catch (TPortInfoDaoException e1) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e1.getMessage());
			e1.printStackTrace();
		}
		List<TPortInfo> preSelected = new ArrayList<TPortInfo>();
		if (portinfo != null)
			for (int i = 0; i < portinfo.size(); i++) {
				TPortInfo tPortInfo = portinfo.get(i);
				long ptid = tPortInfo.getPtid();
				TLinepolMap tm = null;
				try {
					tm = TLinepolMapDao.findByPrimaryKey(ptid);
				} catch (TLinepolMapDaoException e) {
					Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
					e.printStackTrace();
				}
				if (tm != null) {
					if (cate != null && !cate.equalsIgnoreCase("") && !cate.equalsIgnoreCase("16")) {
						if (tm.getMpid() == idLong) {
							preSelected.add(tPortInfo);
						}
					} else if (cate != null && cate.equalsIgnoreCase("16")) {
						if (tm.getPpid() == idLong) {
							preSelected.add(tPortInfo);
						}
					}
				}
			}

		return preSelected;
	}

	private List<PredefmibInfo> getPreSelectedPDMinfo(long devidLong, String cate, long idLong) {

		List<PredefmibInfo> pdminfo = null;
		try {
			pdminfo = PredefmibInfoDao.findWhereDevidEquals(devidLong);
		} catch (PredefmibInfoDaoException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		List<PredefmibInfo> preSelected = new ArrayList<PredefmibInfo>();
		if (pdminfo != null)
			for (int i = 0; i < pdminfo.size(); i++) {
				PredefmibInfo tPdminfo = pdminfo.get(i);
				long pdmid = tPdminfo.getPdmid();
				PredefmibPolMap pdmPolMap = null;
				try {
					pdmPolMap = PredefmibPolMapDao.findByPrimaryKey(pdmid);
				} catch (PredefmibPolMapDaoException e) {
					Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
					e.printStackTrace();
				}
				if (pdmPolMap != null) {
					if (cate != null && !cate.equalsIgnoreCase("") && !cate.equalsIgnoreCase("16")) {
						if (pdmPolMap.getMpid() == idLong) {
							preSelected.add(tPdminfo);
						}
					} else if (cate != null && cate.equalsIgnoreCase("16")) {
						if (pdmPolMap.getPpid() == idLong) {
							preSelected.add(tPdminfo);
						}
					}
				}
			}

		return preSelected;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public PredefmibInfoDao getPredefmibInfoDao() {
		return PredefmibInfoDao;
	}

	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		PredefmibInfoDao = predefmibInfoDao;
	}

	public PredefmibPolMapDao getPredefmibPolMapDao() {
		return PredefmibPolMapDao;
	}

	public void setPredefmibPolMapDao(PredefmibPolMapDao predefmibPolMapDao) {
		PredefmibPolMapDao = predefmibPolMapDao;
	}
}
