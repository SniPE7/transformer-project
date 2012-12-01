package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

public class NaviIPnetController implements Controller {

	TGrpNetDao TGrpNetDao;

	public void setTGrpNetDao(TGrpNetDao grpNetDao) {
		TGrpNetDao = grpNetDao;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> grpnet = new HashMap<String, Object>();
		try{
			List<TGrpNet> _result = TGrpNetDao.findAll();
			SortList<TGrpNet> sort = new SortList<TGrpNet>();
			sort.sortmulti(_result, "getUnmallocedipsetflag", "getLevels", "getGname" );//sort.Sort(_result, "getGname", null);
			//System.out.println("sorted list="+_result);
	
			Map<String, Object> tree = new LinkedHashMap<String, Object>();
			Map<String, Object> grpnetNames = new LinkedHashMap<String, Object>();
			//Set<Long> roots = new HashSet<Long>();
			for (TGrpNet dto : _result) {
				
				String supkey,curkey;
//				if(dto.getUnmallocedipsetflag().equals("1")){
//					supkey= "z_"+dto.getSupid();
//				}else{
//					supkey = dto.getSupid()+"";
//				}
				supkey = dto.getSupid()+"";
				if(dto.getUnmallocedipsetflag().equals("1")){
					curkey= "z_"+dto.getGid();
				}else{
					curkey = dto.getGid()+"";
				}
				
				Map<String, Object> listsub = null;
				if (tree.containsKey(supkey) == true) { 
					listsub = (Map<String, Object>) tree.get(supkey);
				} else {
					listsub = new LinkedHashMap<String, Object>();
					tree.put(supkey, listsub);
				}
				//Long subkey = new Long(dto.getGid());
				if (listsub.containsKey(curkey) == false) {
					listsub.put(curkey, dto);
				}
				
				//roots.add(new Long(dto.getGid()));
				grpnetNames.put(curkey, dto);
			}
			List<String> rootlist = new ArrayList<String>();
			for (String s : tree.keySet()) {
	//			if (roots.contains(s) == false) {
	//				rootlist.add(s);				
	//			}
				if (grpnetNames.containsKey(s) == false) {
					rootlist.add(s);				
				}
			}
	
			// grpnet.put("grpnet", _result);
			grpnet.put("ipnettree", tree);
			grpnet.put("rootlist", rootlist);
			//grpnet.put("roots", roots);
			grpnet.put("grpnetNames", grpnetNames);
			
			Object seobj = request.getSession().getAttribute("grpnetNames");
			if (seobj==null) request.getSession().setAttribute("grpnetNames", grpnetNames);
			
			//for testing
			System.out.println("=============rootList:==========");
			for(int i=0; i< rootlist.size(); i++){
				System.out.println("\trootList " + i + "=" + rootlist);
			}
			System.out.println("=============rootList End==========");
			System.out.println("=============ipnettree:==========");
		//		System.out.println("\tipnettree \n\t" + tree);
			System.out.println("=============ipnettree End==========");
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("/secure/resourceman/naviipnet.jsp", "model", grpnet);
	}

	public void main(String args[]) {
		NaviIPnetController navi = new NaviIPnetController();
		try {
			ModelAndView modelandview = navi.handleRequest(null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void display(TCategoryMapInit dto) {
		StringBuffer buf = new StringBuffer();
		buf.append(dto.getId());
		buf.append(", ");
		buf.append(dto.getName());
		buf.append(", ");
		buf.append(dto.getFlag());
		System.out.println(buf.toString());
	}
}
