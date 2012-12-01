package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TGrpNetPk;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;
import com.ibm.ncs.model.exceptions.TGrpNetDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.IPAddr;
import com.ibm.ncs.util.Log4jInit;

public class SaveIpnetOfGidController implements Controller {

	TGrpNetDao TGrpNetDao;
	TDeviceInfoDao TDeviceInfoDao;
	TListIpDao TListIpDao;
	GenPkNumber GenPkNumber;
	String pageView ;
	
	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}

	public void setTListIpDao(TListIpDao listIpDao) {
		TListIpDao = listIpDao;
	}

	public void setTGrpNetDao(TGrpNetDao grpNetDao) {
		TGrpNetDao = grpNetDao;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try{
			model =  parseInput(request, model);		
			model = doCMD(request, model);
	
			//System.out.println("model="+model);
	
			// search ipdecode list for the certain node.
			String nodeid = request.getParameter("gid");
			if (null==nodeid||"".equals(nodeid.trim())){return null;}
			long gid = Long.parseLong(nodeid.trim());
			
			
			List<TListIp> listipbygid = TListIpDao.findWhereGidEquals(gid);
			
			//  ipdecode scopes for the device finder.
			long[] min = new long[listipbygid.size()], max= new long[listipbygid.size()];
			for (int i=0; i < listipbygid.size(); i++){
				min[i] = ((TListIp)listipbygid.get(i)).getIpdecodeMin();
				max[i] = ((TListIp)listipbygid.get(i)).getIpdecodeMax();
			}
			
			List<TDeviceInfo> deviceinfo = TDeviceInfoDao.findWhereIpdecodeBetweenScopes(min, max);
			
			TGrpNet grpnet =  TGrpNetDao.findByPrimaryKey(gid);
	
			model.put("deviceinfo", deviceinfo);
			model.put("listipbygid", listipbygid);
			model.put("ipdecodescopes", new long [][] {min, max} );
			Map seobj = (Map)request.getSession().getAttribute("grpnetNames");
			model.put("grpnetNames", seobj);
			model.put("gid", gid);
			model.put("gname", grpnet.getGname());
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(), "model", model);
	}

	private Map<String, Object> doCMD(HttpServletRequest request,
			Map<String, Object> model) {
		int actiontype = parseCommand(request,model);
		
		switch (actiontype){
		case  0: //add
			addingTheNode(model);
			break;
		case 1: //save
			saveTheNode(model);
			break;
		case 2: //delete
			deleteTheNode(model);
			break;
			
		}
		return model;
	}

	private ModelAndView deleteTheNode(Map<String, Object> model)   {
		//System.out.println("Willing to do Delete!");
		//if validated has no sub node
		//remove gid from T_LIST_IP
		Long gidL= (Long) model.get("gid");
		long gid = gidL.longValue();
		TGrpNetPk pk = new TGrpNetPk(gidL);
try{
		try {			
			TGrpNet dto = TGrpNetDao.findByPrimaryKey(pk);
			String flag = dto.getUnmallocedipsetflag();
			if (flag.equals("1")){
				throw new RuntimeException("Not Allowed to delete this unmalloced node directly!!"); 
			}
			//remove nodes, remove un malloced sub nodes, remove ip list of the nodes.
			List<TGrpNet> tmpdata = TGrpNetDao.findWhereSupidEquals(gid);
			if( tmpdata.size() >1){
				throw new RuntimeException("Not Allowed to delete, It may contains sub node!! ");
			}else if(tmpdata.size()==1){
				long gidsub = tmpdata.get(0).getGid();
				TGrpNetPk pkunmalloc = new TGrpNetPk(gidsub);
				TGrpNetDao.delete(pk);
				TGrpNetDao.delete(pkunmalloc);
				
				TListIpDao.deleteByGid(gid);
				
			}
		} catch (TGrpNetDaoException e) {
			e.printStackTrace();
			throw new RuntimeException("Exceptions on Remove Nodes" + gid+" : "+model.get("gname"));
			
		} catch (TListIpDaoException e) {
			e.printStackTrace();
			throw new RuntimeException("Exceptions on remove iplist when Removing Nodes" + gid+" : "+model.get("gname")+" ; ");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
		return new ModelAndView(getPageView(), "model", model);
	}

	private ModelAndView saveTheNode(Map<String, Object> model) {
		//System.out.println("Willing to implement Save node ");
		try {
			// update the nodes gname, description, unable to change node, super and node level attribute.
			Long gidL= (Long) model.get("gid");
			long gid = -1l ;
			if ( gidL !=null && ! "".equals(gidL) ){
				gid = gidL.longValue();
			}
			else {
				try {
					gid = GenPkNumber.getID();
					gidL = new Long(gid);
				} catch (SequenceNMDaoException e) {
					throw new RuntimeException("Exception on Node info update, Get pkid error!! ");
				}
			}
			TGrpNetPk pk = new TGrpNetPk(gidL);
			String [] ips = (String[])model.get("ips");
			String [] masks = (String[])model.get("masks");
			TGrpNet grpnet = TGrpNetDao.findByPrimaryKey(pk);
			try{
			if ( ! ( (String)model.get("gname") ).equals( grpnet.getGname() ) ){
				grpnet.setGid(gid);
				grpnet.setGname((String)model.get("gname"));
				grpnet.setSupid(Long.parseLong((String)model.get("supid")));
				grpnet.setDescription((String)model.get("description"));
				
			}
			}catch(Exception e){}
			
			
			//update new iplist of the gid node 
			//clear exist ip list
			TListIpDao.deleteByGid(gid);
			// append new ip list.
			//List<TListIp> data = new ArrayList<TListIp>();
			for (int i=0; i<ips.length; i++){
				long ipmin = IPAddr.getMinIp(ips[i],masks[i]);
				long ipmax = IPAddr.getMaxIp(ips[i],masks[i]);
				TListIp listip00 = new TListIp();
				listip00.setGid(gid);
			listip00.setCategory(1);	//??
				listip00.setIp(ips[i]);
				listip00.setMask(masks[i]);
				listip00.setIpdecodeMin(ipmin);
				listip00.setIpdecodeMax(ipmax);
				listip00.setDescription((String)model.get("description"));
				
				//data.add(listip00);
				TListIpDao.insert(listip00);
			}
			//TListIpDao.addingIpList(data);
			
		
		} catch (TGrpNetDaoException e) {
			throw new RuntimeException("Exception on Node info update!! ");
		} catch (TListIpDaoException e) {
			throw new RuntimeException("Exception on node info update , iplist update ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(), "model", model);
	}

	private ModelAndView addingTheNode(Map<String, Object> model) {
		//System.out.println("Willing to implement Adding node ");
		//model.clear();
		model.remove("description");
		model.remove("gid");
		model.remove("iplist");
		model.remove("ips");
		model.remove("masks");
		return new ModelAndView(getPageView(), "model", model);
	}

	private int parseCommand(HttpServletRequest request, Map<String, Object> model) {
		String actiontype = request.getParameter("formAction");
		if (actiontype!=null && ! "".equals(actiontype))
		{model.put("actiontype", actiontype);} //add, save , delete
		if("add".equals(actiontype)) return 0;
		if("save".equals(actiontype)) return 1;
		if("delete".equals(actiontype)) return 2;
		return -1;
	}

	private Map<String, Object> parseInput(HttpServletRequest request,
			Map<String, Object> model) {
		
		String description = request.getParameter("description");
		String supid = request.getParameter("supid");
		String gidstr = request.getParameter("gid");
		String level = request.getParameter("level");
		String[] iplist = request.getParameterValues("iplist");
		
		//System.out.println("iplist="+iplist);
	

		if(description!=null && !"".equals(description)){
			model.put("description", description);
		}
		if (supid!=null && !"".equals(supid)){
			model.put("supid", supid);
		}
		if(level!=null && "".equals(level)){
			model.put("level", level);
		}
		if (gidstr !=null && "".equals(gidstr)){
			long gid = Long.parseLong(gidstr);
			model.put("gid", gid);
		}
		if (iplist != null && iplist.length>0){
			String ipnetip[] = new String[iplist.length];
			String ipnetmask[] = new String[iplist.length];
			for (int i=0; i<iplist.length; i++){
				StringTokenizer st = new StringTokenizer(iplist[i],"||");
				ipnetip[i] = st.nextToken();
				ipnetmask[i] =st.nextToken();
			}
			for(int i=0;i<iplist.length;i++){System.out.println("\n iplist="+iplist[i]+"\t ip = "+ipnetip[i]+"\t mask="+ipnetmask[i]);}
			model.put("ips", ipnetip);
			model.put("masks", ipnetmask);
		
		}
		
		
		
		
		return model;
	}

	public GenPkNumber getGenPkNumber() {
		return GenPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		GenPkNumber = genPkNumber;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public TGrpNetDao getTGrpNetDao() {
		return TGrpNetDao;
	}

	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}

	public TListIpDao getTListIpDao() {
		return TListIpDao;
	}


}
