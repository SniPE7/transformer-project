package com.ibm.ncs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.IpAddrRangeCheckDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dao.TPortlineMapDao;
import com.ibm.ncs.model.dao.TTcpportpolMapDao;

import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.IpAddrRangeCheck;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TDevpolMap;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TLinepolMap;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.exceptions.DeviceTypeTreeDaoException;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckDaoException;
import com.ibm.ncs.model.exceptions.TCategoryMapInitDaoException;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TDeviceTypeInitDaoException;
import com.ibm.ncs.model.exceptions.TGrpNetDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.model.exceptions.TManufacturerInfoInitDaoException;
import com.ibm.ncs.model.exceptions.TPortInfoDaoException;
import com.ibm.ncs.model.facade.baseInfoFacade;


public class PolicyAppServices  {

	TGrpNetDao TGrpNetDao;
	TDeviceInfoDao TDeviceInfoDao;
	TListIpDao TListIpDao;
	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	
	TDevpolMapDao TDevpolMapDao;
	TLinepolMapDao  TLinepolMapDao;
	TPortlineMapDao TPortlineMapDao;
	TTcpportpolMapDao  TTcpportpolMapDao;
	IpAddrRangeCheckDao IpAddrRangeCheckDao;
	
	TPortInfoDao TPortInfoDao;




	public void setTPortInfoDao(TPortInfoDao portInfoDao) {
		TPortInfoDao = portInfoDao;
	}

	/** Build up a Tree node info from a recursive hierarchy table.
	 * 
	 * @return
	 * @throws TGrpNetDaoException
	 */
	public Map<String, Object> buildupIpnetNodeTree() throws TGrpNetDaoException{
		
		Map<String, Object> grpnet = new HashMap<String, Object>();
		
		List<TGrpNet> _result = TGrpNetDao.findAll();

		Map<Long, Object> tree = new TreeMap<Long, Object>();
		Map<Long, Object> grpnetNames = new TreeMap<Long, Object>();
		//Set<Long> roots = new HashSet<Long>();
		for (TGrpNet dto : _result) {

			Map<Long, Object> listsub = null;
			if (tree.containsKey(dto.getSupid()) == true) {
				listsub = (Map<Long, Object>) tree.get(dto.getSupid());
			} else {
				listsub = new TreeMap<Long, Object>();
				tree.put(new Long(dto.getSupid() ), listsub);
			}
			Long subkey = new Long(dto.getGid());
			if (listsub.containsKey(subkey) == false) {
				listsub.put(subkey, dto);
			}
			
			//roots.add(new Long(dto.getGid()));
			grpnetNames.put(subkey, dto);
		}
		List<Long> rootlist = new ArrayList<Long>();
		for (Long s : tree.keySet()) {
			if (grpnetNames.containsKey(s) == false) {
				rootlist.add(s);				
			}
		}
		grpnet.put("ipnettree", tree);
		grpnet.put("rootlist", rootlist);
		grpnet.put("grpnetNames", grpnetNames);
		//--end of node list 

		return grpnet;
	}
	
	public List<TDeviceInfo> getDeviceInfoListOfTheGidNode(long gid) throws TDeviceInfoDaoException, TListIpDaoException{
		
		TGrpNet tmpdto = null;
		long realgid=-1, unmgid=-1;
		boolean realnode = false;
		try {
			 tmpdto = TGrpNetDao.findByPrimaryKey(gid);
			 String unm = tmpdto.getUnmallocedipsetflag();
			 if(unm.equals("1")){
				 realnode = false;
				 realgid= tmpdto.getSupid();
				 unmgid= gid;
			 }else{
				 realnode=true;
				 realgid = gid;
			 }
		} catch (TGrpNetDaoException e) {
			e.printStackTrace();
		}
		List<TListIp> listipbygid = TListIpDao.findWhereGidEquals(realgid);
		if (listipbygid.size()==0){return null;}
		//  ipdecode scopes for the device finder.
		long[] min = new long[listipbygid.size()], max= new long[listipbygid.size()];
		for (int i=0; i < listipbygid.size(); i++){
			min[i] = ((TListIp)listipbygid.get(i)).getIpdecodeMin();
			max[i] = ((TListIp)listipbygid.get(i)).getIpdecodeMax();
		}	
		List<TDeviceInfo> deviceinfo = TDeviceInfoDao.findWhereIpdecodeBetweenScopes(min, max);
		
		if(realnode==true) {		
			return deviceinfo;
		}else{  // unmalloced ip set node
			List<IpAddrRangeCheck> subrange = null;
			try {
				subrange = IpAddrRangeCheckDao.findSubIpRange(realgid);
				if (subrange.size()==0){return null;}
				//System.out.println("subrange="+subrange);
				long[] minsub = new long[subrange.size()], maxsub= new long[subrange.size()];
				for (int i=0; i < subrange.size(); i++){
					minsub[i] = ((IpAddrRangeCheck)subrange.get(i)).getIpdecodeMin();
					maxsub[i] = ((IpAddrRangeCheck)subrange.get(i)).getIpdecodeMax();
				}	
				List<TDeviceInfo> devsublst = TDeviceInfoDao.findWhereIpdecodeBetweenScopes(minsub, maxsub);
				for (TDeviceInfo dto: devsublst){				
					deviceinfo.contains(dto);
					deviceinfo.remove(dto);					
				}
			} catch (IpAddrRangeCheckDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return deviceinfo;
	}
	
	public List<TPortInfo> findPortInfoByDevid(long devid) throws TPortInfoDaoException{
		return TPortInfoDao.findWhereDevidEquals(devid);
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
	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}

	public void setTListIpDao(TListIpDao listIpDao) {
		TListIpDao = listIpDao;
	}
	public void setTDevpolMapDao(TDevpolMapDao devpolMapDao) {
		TDevpolMapDao = devpolMapDao;
	}

	public void setTLinepolMapDao(TLinepolMapDao linepolMapDao) {
		TLinepolMapDao = linepolMapDao;
	}

	public void setTPortlineMapDao(TPortlineMapDao portlineMapDao) {
		TPortlineMapDao = portlineMapDao;
	}

	public void setTTcpportpolMapDao(TTcpportpolMapDao tcpportpolMapDao) {
		TTcpportpolMapDao = tcpportpolMapDao;
	}

	public IpAddrRangeCheckDao getIpAddrRangeCheckDao() {
		return IpAddrRangeCheckDao;
	}

	public void setIpAddrRangeCheckDao(IpAddrRangeCheckDao ipAddrRangeCheckDao) {
		IpAddrRangeCheckDao = ipAddrRangeCheckDao;
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

	public TPolicyBaseDao getTPolicyBaseDao() {
		return TPolicyBaseDao;
	}

	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}

	public TDevpolMapDao getTDevpolMapDao() {
		return TDevpolMapDao;
	}

	public TLinepolMapDao getTLinepolMapDao() {
		return TLinepolMapDao;
	}

	public TPortlineMapDao getTPortlineMapDao() {
		return TPortlineMapDao;
	}

	public TTcpportpolMapDao getTTcpportpolMapDao() {
		return TTcpportpolMapDao;
	}

	public TPortInfoDao getTPortInfoDao() {
		return TPortInfoDao;
	}
}
