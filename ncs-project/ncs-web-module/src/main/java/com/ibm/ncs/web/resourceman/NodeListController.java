package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.util.Log4jInit;

public class NodeListController implements Controller {

	//TGrpNetDao TGrpNetDao;
	TDeviceInfoDao TDeviceInfoDao;
	TListIpDao TListIpDao;

	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}

	public void setTListIpDao(TListIpDao listIpDao) {
		TListIpDao = listIpDao;
	}

	//public void setTGrpNetDao(TGrpNetDao grpNetDao) {
	//	TGrpNetDao = grpNetDao;
	//}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try{
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
	
			model.put("deviceinfo", deviceinfo);
			model.put("listipbygid", listipbygid);
			model.put("ipdecodescopes", new long [][] {min, max} );
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("/secure/resourceman/nodelist.jsp", "model", model);
	}


}
