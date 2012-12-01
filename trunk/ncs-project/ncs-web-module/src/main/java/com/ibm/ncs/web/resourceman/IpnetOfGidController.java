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
import com.ibm.ncs.util.SortList;

public class IpnetOfGidController implements Controller {

	
	
	
	TGrpNetDao TGrpNetDao;
	TDeviceInfoDao TDeviceInfoDao;
	TListIpDao TListIpDao;
	GenPkNumber GenPkNumber;
	String pageView;
	int actiontypei=-2;
	VerifyIPAddressRange VerifyIPAddressRange ;

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
//		model = (Map)request.getSession().getAttribute("model");
//		model = (model != null) ? model: new HashMap<String, Object>();
		String refresh = "<script language='javascript' type='text/javascript'>function refresh(){window.parent.frames[2].location.reload();}</script>";
		try {
	
			model = parseInput(request, model);
//			System.out.println("model="+model);
			try{
			model = doCMD(request, model);
//			System.out.println("2...actiontypei="+actiontypei);
			if(actiontypei==0) {/*prepare add ...not yet save insertion.*/
				model.remove("iplist");
				model.remove("gname");
				return new ModelAndView(getPageView(), "model", model);
			}else if(actiontypei==1){/*add save insertion*/
				//model.put("gid", model.get("gid"));
				model.put("refresh", refresh);
//				System.out.println(" model ="+model);
				String gidnew = (String)model.get("gid");
				String gid = (gidnew!=null)?gidnew:(String)model.get("gidOriginal");
				model.put("gid", gid);
				//System.out.println("after add new node...the gid is :"+gid);
				//return new ModelAndView(getPageView()+"?gid="+model.get("gid"), "model", model);
			}else if(actiontypei==2){/*update*/				
			
				//return new ModelAndView(getPageView(), "model", model);
			}else if(actiontypei==3){/*delete*/
				model.remove("iplist");
				model.remove("gname");
				model.put("gid", model.get("supidOriginal"));
				model.put("refresh", refresh);
				//return new ModelAndView(getPageView()+"?gid="+model.get("supid"), "model", model);
			}

//			 System.out.println("1st...model="+model);
			}catch (Exception e) {
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
			}
			// search ipdecode list for the certain node.
//			String nodeid = request.getParameter("gid");
//			String mgid = (String)model.get("gid");
//			if (null == nodeid || "".equals(nodeid.trim())) {
//				if(null==mgid ){
//					throw new RuntimeException ("gid invalid"+nodeid);
//				}
//				//return null;
//			}
			long gid = -1;
			 String gidstr = (String)model.get("gid");
			try{
				gid = Long.parseLong(gidstr.trim());
			}catch (Exception e) {
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
			}
//			System.out.println(model);

			List<TListIp> listipbygid = TListIpDao.findWhereGidEquals(gid);
			SortList sortip = new SortList();
			sortip.Sort(listipbygid, "getIp", null);
			

			// ipdecode scopes for the device finder.
			long[] min = new long[listipbygid.size()], max = new long[listipbygid
					.size()];
			for (int i = 0; i < listipbygid.size(); i++) {
				min[i] = ((TListIp) listipbygid.get(i)).getIpdecodeMin();
				max[i] = ((TListIp) listipbygid.get(i)).getIpdecodeMax();
			}

			List<TDeviceInfo> deviceinfo = TDeviceInfoDao
					.findWhereIpdecodeBetweenScopes(min, max);

			TGrpNet grpnet = TGrpNetDao.findByPrimaryKey(gid);

			model.put("deviceinfo", deviceinfo);
			model.put("listipbygid", listipbygid);
			model.put("ipdecodescopes", new long[][] { min, max });
			Map seobj = (Map) request.getSession().getAttribute("grpnetNames");
			model.put("grpnetNames", seobj);
			model.put("gid", gid);
			model.put("supid", grpnet.getSupid());
			if(grpnet.getSupid() ==0) model.put("supName", "--");
			else if(grpnet.getSupid() >0)
				try{model.put("supName", TGrpNetDao.findByPrimaryKey(grpnet.getSupid()).getGname());}catch(Exception e){};			
			model.put("levels", grpnet.getLevels());
			model.put("gname", grpnet.getGname());
			model.put("grpnet", grpnet);
			model.put("gidOriginal", gid);
			model.put("supidOriginal", grpnet.getSupid());
			model.put("description", grpnet.getDescription());
//			System.out.println("before page...last...model="+model);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(), "model", model);
	}

	private Map<String, Object> doCMD(HttpServletRequest request,
			Map<String, Object> model) {
		actiontypei = parseCommand(request, model);

		switch (actiontypei) {
		case 0: //prepareadd
			prepareTheNode(model);
//			System.out.println("prepare");
			break;
		case 1: // add
			addingTheNode(model);
//			System.out.println("add");
			break;
		case 2: // save
			saveTheNode(model);
//			System.out.println("save");
			break;
		case 3: // delete
			deleteTheNode(model);
//			System.out.println("delete");
			break;

		}
		return model;
	}



	private void deleteTheNode(Map<String, Object> model) {
		// System.out.println("Willing to do Delete!");
		// if validated has no sub node
		// remove gid from T_LIST_IP
		System.out.println("delete method");
		String gidL = (String) model.get("gid");
		String message="";

		long gid = Long.parseLong(gidL);

		TGrpNetPk pk = new TGrpNetPk(gid);
		try {
//			String supidstr = (String)model.get("supid");
//			long supid = Long.parseLong(supidstr);
//			String[] ips = (String[]) model.get("ips");
//			String[] masks = (String[]) model.get("masks");
			//validate  all ip in the parent range; 
			//only if parent (supid) = 0 ; ignore parent range verify;
			//validate all ip not included in sibling nodes
			//validate  subnode ip is included ;
//			int verify = VerifyIPAddressRange.verifyIpAddrRange(gid, supid, ips, masks);
//			if (verify < 0){
//				if (verify==-3){ //only verify subnode;
//				message = VerifyIPAddressRange.getVerfyResultString(verify);
//				model.put("message", message);
//				return null; // by pass when verify failure;
//				}
//			}
			try {
				TGrpNet dto = TGrpNetDao.findByPrimaryKey(pk);
				String flag = dto.getUnmallocedipsetflag();
				if (flag.equals("1")) {
					message=	"Not Allowed to delete this unmalloced node directly!!";
					throw new RuntimeException(
							"Not Allowed to delete this unmalloced node directly!!");
				}
				// remove nodes, remove un malloced sub nodes, remove ip list of
				// the nodes.
				List<TGrpNet> tmpdata = TGrpNetDao.findWhereSupidEquals(gid);
				if (tmpdata.size() > 1) {
					message="Not Allowed to delete, It may contains sub node!! ";
					throw new RuntimeException(
							"Not Allowed to delete, It may contains sub node!! ");
				} else if (tmpdata.size() == 1) {
					long gidsub = tmpdata.get(0).getGid();
					TGrpNetPk pkunmalloc = new TGrpNetPk(gidsub);
					TGrpNetDao.delete(pk);
					TGrpNetDao.delete(pkunmalloc);

//					TListIp deleteGid = new TListIp();
//					deleteGid.setGid(gid);
					TListIpDao.deleteByGid(gid );

				}
			} catch (TGrpNetDaoException e) {
				
				e.printStackTrace();
				throw new RuntimeException("Exceptions on Remove Nodes" + gid
						+ " : " + model.get("gname"));

			} catch (TListIpDaoException e) {
				e.printStackTrace();
				throw new RuntimeException(
						"Exceptions on remove iplist when Removing Nodes" + gid
								+ " : " + model.get("gname") + " ; ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.clear();
		model.put("message", message);
//		model.put("refresh", "true");
//		return new ModelAndView(getPageView(), "model", model);
	}

	private void saveTheNode(Map<String, Object> model) {
		// System.out.println("Willing to implement Save node ");
		String message  ="";
		try {
			// try {
			// update the nodes gname, description, unable to change node, super
			// and node level attribute.
			System.out.println("save method");
			String gidL = (String) model.get("gid");
			long gid = -1l;
			if (gidL != null && !"".equals(gidL)) {
				gid = Long.parseLong(gidL);
			} else {
				
				message = "invalid node gid.";
				model.put("message", message);
				return ;	//return null;
				//throw new RuntimeException ("invalid node gid.");
//				try {
//					gid = GenPkNumber.getID();
//					gidL = new Long(gid);
//				} catch (SequenceNMDaoException e) {
//					throw new RuntimeException(
//							"Exception on Node info update, Get pkid error!! ");
//				}
			}
			String[] ips = (String[]) model.get("ips");
			String[] masks = (String[]) model.get("masks");
			String supidstr = (String)model.get("supid");
			long supid = Long.parseLong(supidstr);
			//validate  all ip in the parent range; 
			//only if parent (supid) = 0 ; ignore parent range verify;
			//validate all ip not included in sibling nodes
			//validate  subnode ip is included ;
			int verify = VerifyIPAddressRange.verifyIpAddrRange(gid, supid, ips, masks);
			if (verify < 0){
				
				message = VerifyIPAddressRange.getVerfyResultString(verify);
				model.put("message", message);
				return ;	//return null; // by pass when verify failure;
			}
			
			TGrpNetPk pk = new TGrpNetPk(Long.parseLong(gidL));
//			System.out.print("TGrpNetPk pk=" + pk);
//			String[] ips = (String[]) model.get("ips");
//			String[] masks = (String[]) model.get("masks");
			TGrpNet grpnet = TGrpNetDao.findByPrimaryKey(pk);
			// if ( ! ( (String)model.get("gname") ).equals( grpnet.getGname() )
			// ){
			grpnet.setGid(gid);
			grpnet.setGname((String) model.get("gname"));
			grpnet.setSupid(Long.parseLong((String) model.get("supid")));
			grpnet.setDescription((String) model.get("description"));
			TGrpNetDao.update(pk, grpnet);
			//System.out.println("save mode ...grpnet="+grpnet);
			// }

			// update new iplist of the gid node
			// clear exist ip list
//			TListIp deleteGid = new TListIp();
//			deleteGid.setGid(gid);
			TListIpDao.deleteByGid(gid);
			// append new ip list.
			// List<TListIp> data = new ArrayList<TListIp>();
			for (int i = 0; i < ips.length; i++) {
				long ipmin = IPAddr.getMinIp(ips[i],masks[i]);
				long ipmax = IPAddr.getMaxIp(ips[i],masks[i]);
				TListIp listip00 = new TListIp();
				listip00.setGid(gid);
				listip00.setCategory(1); // ??
				listip00.setIp(ips[i]);
				listip00.setMask(masks[i]);
				listip00.setIpdecodeMin(ipmin);
				listip00.setIpdecodeMax(ipmax);
				listip00.setDescription((String) model.get("description"));
//	System.out.println("save mode ...listip00="+listip00);
				// data.add(listip00);
				TListIpDao.insert(listip00);
			}
			// TListIpDao.addingIpList(data);

		} catch (TGrpNetDaoException e) {
			message = "Exception on Node info update." ;
			throw new RuntimeException("Exception on Node info update. ");
		} catch (TListIpDaoException e) {
			message = "Exception on node info update , iplist update. ";
			throw new RuntimeException(
					"Exception on node info update , iplist update. ");
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("message", message);
//		model.put("refresh", "true");
//		return new ModelAndView(getPageView(), "model", model);
	}
	
	private void prepareTheNode(Map<String, Object> model) {
		String message ="";
		String gid = (String)model.get("gid");
		try {
			TGrpNet supdto = TGrpNetDao.findByPrimaryKey(new TGrpNetPk(Long.parseLong(gid)));
			if (supdto!= null && supdto.getUnmallocedipsetflag().equals("1")){
				message = "Not allowed to create node from here.";
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TGrpNetDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.put("supid", gid);
		model.put("supName", model.get("gname"));
		 model.remove("description");
		 model.remove("gid");
		 model.remove("iplist");
		 model.remove("ips");
		 model.remove("masks");
		
		 model.put("message", message);
		 model.put("prepareAdd", "add");
//		System.out.println("-------------prepareAdd-----------model="+model);
//		return new ModelAndView(getPageView(), "model", model);
	}

	private void addingTheNode(Map<String, Object> model) {
		// System.out.println("Willing to implement Adding node ");
		// model.clear();
		//System.out.println("adding method");
		//System.out.println("model=" + model);
		// model.remove("description");
		// model.remove("gid");
		// model.remove("iplist");
		// model.remove("ips");
		// model.remove("masks");

		// not allowed if unmallocflag=1
		//Long gidL = (Long) model.get("gid");///should be "" from the requestParameter("gid) 
		String supidL = (String) model.get("supid"); // remove old supid and copied from gip by jsp javascript,so the supid=current selected node gid. 
		String message = "";
		//System.out.println("Tgrpnet ...gid..supid=" + gidL);
		try {
			long gid = GenPkNumber.getID();
			String[] ips = (String[]) model.get("ips");
			String[] masks = (String[]) model.get("masks");
			String supidstr = (String)model.get("supid");
			long supid = Long.parseLong(supidstr);
			//validate  all ip in the parent range; 
			//only if parent (supid) = 0 ; ignore parent range verify;
			//validate all ip not included in sibling nodes
			//validate  subnode ip is included ;
			int verify = VerifyIPAddressRange.verifyIpAddrRange(gid, supid,ips, masks);
			if (verify < 0){
				if(verify != -3){// no need to check subnode; because it's a new node, no subnode at all
				message = VerifyIPAddressRange.getVerfyResultString(verify);
				model.put("message", message);
				model.put("prepareAdd", "add");
				return ;	//return null; // by pass when verify failure;
				}
			}		
			
			
			TGrpNet supdto = TGrpNetDao.findByPrimaryKey(new TGrpNetPk(Long.parseLong(supidL)));
			if (supdto!= null && supdto.getUnmallocedipsetflag().equals("1")) {
				message = "Not allowed to add sub node from this node.";
				throw new RuntimeException(message);
			}
			// create a new subnode
			String gname = (String) model.get("gname");
			if (gname==null||gname.equals("")) {
				message="Name empty!";
				return ;	//return null;
			}
			String description = (String) model.get("description");
			TGrpNet dto = new TGrpNet();
			dto.setSupid(Long.parseLong(supidL));
			dto.setGname(gname);
			dto.setDescription(description);
			
			if(supdto==null)
				dto.setLevels(0l);
			else
				dto.setLevels(supdto.getLevels() + 1l);
			dto.setUnmallocedipsetflag("0");
			
			dto.setGid(gid);
			TGrpNetPk pk =TGrpNetDao.insert(dto);
			model.put("gid", pk.getGid()+"");
			// add a unmalloc node as well
			TGrpNet dto2 = new TGrpNet();
			dto2.setSupid(gid);
			dto2.setGname(gname + "_未分类");
			dto2.setDescription(description);
			dto2.setLevels(dto.getLevels() + 1l);
			dto2.setUnmallocedipsetflag("1"); // unmalloc=1
			long gid2 = GenPkNumber.getID();
			dto2.setGid(gid2);
			TGrpNetPk pk2 =   TGrpNetDao.insert(dto2);
			
			// iplist update
//			TListIp deleteGid = new TListIp();
//			deleteGid.setGid(gid);
			TListIpDao.deleteByGid(gid );
			//String[] ips = (String[]) model.get("ips");
			//String[] masks = (String[]) model.get("masks");
			//System.out.println("ips="+ips +"("+ips[0]+")"+" |"+masks+"("+masks[0]+")");
			if (ips!=null){
			for (int i = 0; i < ips.length; i++) {
				TListIp ip = new TListIp();
				ip.setGid(gid);
				ip.setIp(ips[i]);
				ip.setIpdecodeMin(IPAddr.getMinIp(ips[i],masks[i]));
				ip.setIpdecodeMax(IPAddr.getMaxIp(ips[i],masks[i]));
				long category = 1; /* ?? */
				ip.setCategory(category);
				ip.setMask(masks[i]);
				TListIpDao.insert(ip);
			}}
		} catch (TGrpNetDaoException e) {
			message="Failure to insert a node.";
			e.printStackTrace();
			throw new RuntimeException(message);
		} catch (SequenceNMDaoException e) {
			message ="Cannot get Sequence number, maybe DB connection problem!";
			e.printStackTrace();
			throw new RuntimeException(message);
		} catch (TListIpDaoException e) {
			message="IP list save failure, may be ip conflict";
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(message);
		} catch (Exception e) {
			//message="errors on insert a node.";
			e.printStackTrace();
		}
model.put("message", message);
//model.put("refresh", "true");
//		return new ModelAndView(getPageView(), "model", model);
	}

	private int parseCommand(HttpServletRequest request,
			Map<String, Object> model) {
		String actiontype = request.getParameter("formAction");
		String prepareAdd = request.getParameter("prepareAdd");
		//System.out.println("actiontype="+actiontype+" : prepareAdd="+prepareAdd);
		if (actiontype != null && !"".equals(actiontype)) {
			model.put("actiontype", actiontype);
		} // add, save , delete
		if ("add".equals(prepareAdd)&&(!"save".equals(actiontype)))
			return 0;
		if ("add".equals(prepareAdd)&&("save".equals(actiontype)))/*first step clear coluumn, and then input new info to create a new node;*/
			return 1;
//		if ("save".equals(actiontype)&&"prepareAdd".equals(prepareAdd))/*first step clear coluumn, and then input new info to create a new node;*/
//			return 1;
		if ("save".equals(actiontype)&&(!"add".equals(prepareAdd)))
			return 2;
		if ("delete".equals(actiontype))
			return 3;
		return -2;
	}

	private Map<String, Object> parseInput(HttpServletRequest request,
			Map<String, Object> model) {

		String description = request.getParameter("description");
		String supid = request.getParameter("supid");
		String gidstr = request.getParameter("gid");
		String supidOriginal = request.getParameter("supidOriginal");
		String gidOriginal = request.getParameter("gidOriginal");
		String levels = request.getParameter("levels");
		String gname = request.getParameter("gname");
		String[] iplist = request.getParameterValues("iplist");

//		System.out.println("description="+description);
//		System.out.println("supid="+supid);
//		System.out.println("gidstr="+gidstr);
//		System.out.println("levels="+levels);
//		System.out.println("gname="+gname);
//		System.out.println("iplist="+iplist);

		if (description != null && !"".equals(description)) {
			model.put("description", description);
		}
		if (supidOriginal != null && !"".equals(supidOriginal)) {
			model.put("supidOriginal", supidOriginal);
		}
		if (gidOriginal != null && !"".equals(gidOriginal)) {
			model.put("gidOriginal", gidOriginal);
		}
		if (supid != null && !"".equals(supid)) {
			model.put("supid", supid);
		}
		if (levels != null && !"".equals(levels)) {
			model.put("levels", levels);
		}
		if (gname != null && !"".equals(gname)) {
			model.put("gname", gname);
		}		
		if (gidstr != null && !"".equals(gidstr)) {
			//long gid = Long.parseLong(gidstr);
			model.put("gid", gidstr);
		}
		if (iplist != null && iplist.length > 0) {
			String ipnetip[] = new String[iplist.length];
			String ipnetmask[] = new String[iplist.length];
			for (int i = 0; i < iplist.length; i++) {
				StringTokenizer st = new StringTokenizer(iplist[i], "||");
				ipnetip[i] = st.nextToken();
				ipnetmask[i] = st.nextToken();
			}
//			for (int i = 0; i < iplist.length; i++) {
//				System.out.println("\n iplist=" + iplist[i] + "\t ip = "
//						+ ipnetip[i] + "\t mask=" + ipnetmask[i]);
//			}
			model.put("ips", ipnetip);
			model.put("masks", ipnetmask);
//System.out.println("iplist="+ipnetip +"("+ipnetip[0]+")"+" |"+ipnetmask+"("+ipnetmask[0]+")");
		}
		//System.out.println("ipnetofgid...parseinput ...model="+model);

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

	public VerifyIPAddressRange getVerifyIPAddressRange() {
		return VerifyIPAddressRange;
	}

	public void setVerifyIPAddressRange(VerifyIPAddressRange verifyIPAddressRange) {
		VerifyIPAddressRange = verifyIPAddressRange;
	}

}
