/**
 * 
 */
package com.ibm.ncs.web.policyapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PolDetailDspDao;
import com.ibm.ncs.model.dao.PolicySyslogDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TEventtypeInfoInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.PolDetailDsp;
import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyDetails;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;
import com.ibm.ncs.model.exceptions.TPolicyPeriodDaoException;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.excel.DBTOExcel;
import com.ibm.ncs.web.policyapply.bean.NonTimeFramePolicyDetails;
import com.ibm.ncs.web.policyapply.bean.SyslogPolicyDetails;

/**
 * @author root
 *
 */
public class ExportPolicyController implements Controller {

//	TGrpNetDao TGrpNetDao;
	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	TPolicyDetailsDao TPolicyDetailsDao;
	TModuleInfoInitDao  TModuleInfoInitDao;
	TEventTypeInitDao  TEventTypeInitDao;
//	PolDetailDspDao  PolDetailDspDao;
	PolicySyslogDao policySyslogDao;
	
	String pageView;
	String message;

	
	public PolicySyslogDao getPolicySyslogDao() {
		return policySyslogDao;
	}


	public void setPolicySyslogDao(PolicySyslogDao policySyslogDao) {
		this.policySyslogDao = policySyslogDao;
	}


	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}


	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}


	public TEventTypeInitDao getTEventTypeInitDao() {
		return TEventTypeInitDao;
	}


	public void setTEventTypeInitDao(TEventTypeInitDao eventTypeInitDao) {
		TEventTypeInitDao = eventTypeInitDao;
	}


	public String getPageView() {
		return pageView;
	}


	public void setPageView(String pageView) {
		this.pageView = pageView;
	}


	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}


	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}


	public TPolicyBaseDao getTPolicyBaseDao() {
		return TPolicyBaseDao;
	}


	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public TPolicyDetailsDao getTPolicyDetailsDao() {
		return TPolicyDetailsDao;
	}


	public void setTPolicyDetailsDao(TPolicyDetailsDao policyDetailsDao) {
		TPolicyDetailsDao = policyDetailsDao;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String formAction = request.getParameter("formAction");
		message = "";
		
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			if(formAction != null){
				if(formAction.equals("list")){		//list all the policies
					//prepare all the policy list for display
					getAllPolicyList(model);
					
				}else if(formAction.equals("export")){//export selected policies to excel 
					//get all the selected policies
					String[] selected = request.getParameterValues("sel");					
					List<TPolicyBase> policySelected = new ArrayList();
					List<NonTimeFramePolicyDetails> nonTimeframePolicyList = new ArrayList();
					List<NonTimeFramePolicyDetails> messageList = new ArrayList();
					List<SyslogPolicyDetails> syslogPolicyDetailsList = new ArrayList();
					List<SyslogPolicyDetails> syslogMessageList = new ArrayList();
					List<TPolicyPeriod> policyPeriodSelected = new ArrayList();
					if(selected != null){
						response.reset();
						response.setHeader("Content-disposition", TPolicyDetailsDao.getTableName());
						response.setContentType("application/vnd.ms-excel");
						
						for(int i=0; i<selected.length; i++){
							int count = selected[i].indexOf("|");
							String id = selected[i].substring(0, count);
							String category = selected[i].substring(count +1);
							if(count > 0 && id!= null && !id.equals("") && category!= null && !category.equals("")){
								if(category.equals("16")){ //time frame policy
									TPolicyPeriod periodBase = TPolicyPeriodDao.findByPrimaryKey(Long.parseLong(id));
									if(periodBase != null  && !periodBase.getDefaultflag().equals("1")){
										policyPeriodSelected.add(periodBase);
									}
								}else{	//other policies
									NonTimeFramePolicyDetails messageTmp = new NonTimeFramePolicyDetails();									
									TPolicyBase pBase = TPolicyBaseDao.findByPrimaryKey(Long.parseLong(id));
									if(pBase != null){
										policySelected.add(pBase);
										
										//non syslog policy details
										List<TPolicyDetails> pD = TPolicyDetailsDao.findWhereMpidEquals(pBase.getMpid());
										for(int j=0; j<pD.size(); j++){
											TPolicyDetails pDetailsTmp = pD.get(j);
											NonTimeFramePolicyDetails nonTfDetailsTmp = new NonTimeFramePolicyDetails();
											nonTfDetailsTmp.setPolicyDetails(pDetailsTmp);
											
											TPolicyBase pBaseTmp = TPolicyBaseDao.findByPrimaryKey(pDetailsTmp.getMpid());
											nonTfDetailsTmp.setPolicyBase(pBaseTmp);
											TModuleInfoInit moduleInfoInitTmp = TModuleInfoInitDao.findByPrimaryKey(pDetailsTmp.getModid());
											if(moduleInfoInitTmp != null){
												nonTfDetailsTmp.setModuleInfoInit(moduleInfoInitTmp);
												
												TEventTypeInit eventTypeInitTmp = TEventTypeInitDao.findByPrimaryKey(pDetailsTmp.getModid(),pDetailsTmp.getEveid()); 
												if(eventTypeInitTmp!=null){
													nonTfDetailsTmp.setEventTypeInit(eventTypeInitTmp);
													nonTimeframePolicyList.add(nonTfDetailsTmp);
												}else{ //event type init is null
													messageTmp.setMessage(messageTmp.getEVENT_TYPE_INIT_IS_NULL());
													messageTmp.setPolicyBase(pBaseTmp);
													messageTmp.setPolicyDetails(pDetailsTmp);
													messageTmp.setModuleInfoInit(moduleInfoInitTmp);
													messageList.add(messageTmp);
												}
											}else{ //module info init is null
												messageTmp.setMessage(messageTmp.getMODULE_INFO_INIT_IS_NULL());
												messageTmp.setPolicyBase(pBaseTmp);
												messageTmp.setPolicyDetails(pDetailsTmp);
												messageList.add(messageTmp);
											}
										}	
										//end of non syslog policy details
										
										//syslog policy details
										List<PolicySyslog> pS = policySyslogDao.findWhereMpidEquals(pBase.getMpid());
										for(int j=0; j<pS.size(); j++){
											PolicySyslog policySyslogTmp = pS.get(j);
											SyslogPolicyDetails spDetailsBeanTmp = new SyslogPolicyDetails();
											spDetailsBeanTmp.setPolicySyslog(policySyslogTmp);
											
											TPolicyBase pBaseTmp = TPolicyBaseDao.findByPrimaryKey(policySyslogTmp.getMpid());
											spDetailsBeanTmp.setPolicyBase(pBaseTmp);
											syslogPolicyDetailsList.add(spDetailsBeanTmp);
												
										}
									}
								}
							}
						}
						
						if((nonTimeframePolicyList != null && nonTimeframePolicyList.size()>0) ||(policyPeriodSelected!= null && policyPeriodSelected.size()>0)
								|| syslogPolicyDetailsList != null){
							
							ServletOutputStream outputStream = response.getOutputStream();
							response.addHeader("content-type", "application/x-msdownload");
							response.setHeader("Content-Disposition", "attachement;filename=\"ExportPolicy.xls\"");
							//create a excel file	& write to excel file
							DBTOExcel.exportPolicyDetails("NonTimeFrame_Policy_Details", nonTimeframePolicyList, 
									TPolicyPeriodDao.getTableName(), policyPeriodSelected, messageList, syslogPolicyDetailsList, "Syslog_Policy_Details").write(outputStream);
							outputStream.flush();
							outputStream.close();
						}else{
							message = "policy.export.noData";
							model.put("message", message);
							getAllPolicyList(model);					
							return new ModelAndView(getPageView(),	"model", model);
						}
							
					}else{
						message = "policy.export.noSelection";
						model.put("message", message);
						getAllPolicyList(model);					
						return new ModelAndView(getPageView(),	"model", model);
					}
					
				}
			}
		}catch(TPolicyBaseDaoException e){
			System.out.println("inner---------");
			e.printStackTrace();
			message = "global.db.error";
			model.put("message", message);
			return new ModelAndView("/dberror.jsp","model",model);
		}catch (Exception e) {
			System.out.println("outer------");
			model.put("message", message);	
			getAllPolicyList(model);						
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}

		model.put("message", message);					
		return new ModelAndView(getPageView(),	"model", model);
		
	}
	
	private void getAllPolicyList(Map<String, Object> model){
		List<TPolicyBase> policybase;
		try {
			policybase = TPolicyBaseDao.findAllOrderBy("CATEGORY");
			
			List<TPolicyPeriod> policyPeriodBase;
			policyPeriodBase = TPolicyPeriodDao.findAll();			
			if(policyPeriodBase != null )
				for(int i=0; i< policyPeriodBase.size(); i++){
					TPolicyBase p = new TPolicyBase();
					if(!policyPeriodBase.get(i).getDefaultflag().equals("1")){//filter default time frame policy
						p.setCategory(16);
						p.setMpid(policyPeriodBase.get(i).getPpid());
						p.setMpname(policyPeriodBase.get(i).getPpname());
						p.setDescription(policyPeriodBase.get(i).getDescription());
						policybase.add(p);
					}
				}
			model.put("policybase", policybase);		
		} catch (TPolicyBaseDaoException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}catch (TPolicyPeriodDaoException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
					
		return;
	}

}
