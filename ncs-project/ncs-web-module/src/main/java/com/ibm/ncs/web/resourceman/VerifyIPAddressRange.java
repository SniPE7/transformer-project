package com.ibm.ncs.web.resourceman;

import java.util.List;

import com.ibm.ncs.model.dao.IpAddrRangeCheckDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dto.IpAddrRangeCheck;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TGrpNetPk;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckDaoException;
import com.ibm.ncs.model.exceptions.TGrpNetDaoException;
import com.ibm.ncs.util.IPAddr;
import com.ibm.ncs.util.Log4jInit;

public class VerifyIPAddressRange {

	IpAddrRangeCheckDao  IpAddrRangeCheckDao;
	TGrpNetDao TGrpNetDao ;
	
	
	public static int verifyIpAddrRange(long gid, String ip){
		int x=-1;
		
		
		
		return x;
	}
	
	public  int verifyIpAddrRange(long gid, long supid, String[] ip, String[] mask){
		//a vlaid ip should be inside parent, not overlap with sibling, covered subips
		int x=0;
		try {
		//	System.out.println("ip="+ip+"  ip[0]="+ip[0]);
			boolean p = verifyInParentIpRangeBySupid(gid, supid, ip, mask);
			boolean n = verifyInSiblingIpRange(gid, supid, ip, mask);
			boolean s = verifyIncludedSubIpRange(gid, ip, mask);
			
			if (p==false) x = -1; 			/*not inside parent range*/
			else if (n==true) x = -2;		/*has at least one ip range that sibling uses*/
			else if (s==false) x = -3;		/*has at least one sup ip that not in the inputs range*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}
	
//	public boolean verifyInParentIpRange(long gid, String[] ip,String[] mask){
//		//all input ip should be in the parent range. 
//		//then true means all ips wthin the parent range.
//		//false means at least one ip is out of the range.
//		
//		try {/* if super id =0 ; ignore the verify */
//			TGrpNetPk pk = new TGrpNetPk(gid);
//			TGrpNet node =  TGrpNetDao.findByPrimaryKey(pk);
//			System.out.println("====gid="+gid+" :node="+node);
//			 long supid =node.getSupid();
//			 if (supid==0) return true;
//		} catch (TGrpNetDaoException e1) {
//			// TODO Auto-generated catch block
//			System.out.println("verify ip got exceptions...long supid = "+gid);
//			e1.printStackTrace();
//		}
//		boolean isInParentIpRange= false;
//		
//		try {
//			List<IpAddrRangeCheck> parentRange = IpAddrRangeCheckDao.findParentIpRange(gid);
//			boolean test[] = new boolean[ip.length];
//			boolean testall =true;
//			for (int i=0;i<ip.length;i++){
//				test[i] = false;
//				for (IpAddrRangeCheck  range: parentRange){
//					
//					long ipMin = IPAddr.getMinIp(ip[i], mask[i]);
//					long ipMax = IPAddr.getMaxIp(ip[i], mask[i]);
//					
//					long min =  range.getIpdecodeMin();
//					long max =  range.getIpdecodeMax();
//					
//					if( min <= ipMin && ipMax <= max){
//						test[i] =  true;
//						break;
//					}
//				}
//				System.out.println("verifyIPAddr...test[i]="+test[i]);
//				testall = testall&test[i];
//				isInParentIpRange =  testall;
//				
//				if (test[i]==false) {
//					isInParentIpRange = false;
//					break;
//				}
//		
//			}
//			
//		} catch (IpAddrRangeCheckDaoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return isInParentIpRange;
//		
//	}
//	

	public boolean verifyInParentIpRangeBySupid(long gid, long supid,String[] ip,String[] mask){
		//all input ip should be in the parent range. 
		//then true means all ips wthin the parent range.
		//false means at least one ip is out of the range.
		
//		try {/* if super id =0 ; ignore the verify */

			
			 if (supid==0) return true;
//		} catch (TGrpNetDaoException e1) {
//			// TODO Auto-generated catch block
//			System.out.println("verify ip got exceptions...long supid = "+gid);
//			e1.printStackTrace();
//		}
		boolean isInParentIpRange= false;
		if (ip==null ||ip.length==0){
			return true;		
		}
		try {
			List<IpAddrRangeCheck> parentRange = IpAddrRangeCheckDao.findParentIpRangeBySupid(gid,supid);
			boolean test[] = new boolean[ip.length];
			boolean testall =true;
			for (int i=0;i<ip.length;i++){
				test[i] = false;
				for (IpAddrRangeCheck  range: parentRange){
					
					long ipMin = IPAddr.getMinIp(ip[i], mask[i]);
					long ipMax = IPAddr.getMaxIp(ip[i], mask[i]);
					
					long min =  range.getIpdecodeMin();
					long max =  range.getIpdecodeMax();
					
					if( min <= ipMin && ipMax <= max){
						test[i] =  true;
						continue;
					}
				}
				System.out.println("verifyIPAddr...parent ip...test["+i+"]="+test[i]);
				testall = testall&test[i];
				isInParentIpRange =  testall;
				
				if (test[i]==false) {
					isInParentIpRange = false;
					break;
				}
		
			}
			
		} catch (IpAddrRangeCheckDaoException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			
			e.printStackTrace();
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			
			e.printStackTrace();
		}
		System.out.println("verifyIPAddr...parent ip...isInParentIpRange="+isInParentIpRange);
		return isInParentIpRange;
		
	}
	
	
	
	public boolean verifyInSiblingIpRange(long gid, long supid, String[] ip,String[] mask){
		//verify te given ips not in the sibling list.
		//True means at least one ip is in the Sibling range.
		boolean isInSiblingIpRange= true;
		if (ip==null||ip.length==0){
			return false;		
		}
		try {
			List<IpAddrRangeCheck> siblingRange = IpAddrRangeCheckDao.findSiblingIpRange(gid, supid);
			boolean test[] = new boolean[ip.length];
			boolean testall = false ;   //diff
			for (int i=0;i<ip.length;i++){
				test[i] = false;
				for (IpAddrRangeCheck  range: siblingRange){
					
					long ipMin = IPAddr.getMinIp(ip[i], mask[i]);
					long ipMax = IPAddr.getMaxIp(ip[i], mask[i]);
					
					long min =  range.getIpdecodeMin();
					long max =  range.getIpdecodeMax();
					//ip inside range
					if( min <= ipMin && ipMax <= max){
						test[i] =  true;
						continue;
					}
					//ip cover the range
					if( ipMin <= min && max <= ipMax){
						test[i] =  true;
						continue;
					}
					//ip overlap left side
					if( ipMin <= min && ipMax <= max  && min <= ipMax){
						test[i] =  true;
						continue;
					}
					//ip overlap right side
					if( min <= ipMin && max <= ipMax && ipMin <= max){
						test[i] =  true;
						continue;
					}
				}
				testall = testall | test[i];  // diff
				isInSiblingIpRange =  testall;		
				System.out.println("verifyIPAddr...sibling ip...test["+i+"]="+test[i]+";gid="+gid+";siblingRange="+siblingRange);
			}
			
		} catch (IpAddrRangeCheckDaoException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			
			e.printStackTrace();
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			
			e.printStackTrace();
		}
		System.out.println("verifyIPAddr...sibling ip...isInSiblingIpRange="+isInSiblingIpRange+";gid="+gid);
		return isInSiblingIpRange;
		
	}
	
	
	
		
	public boolean verifyIncludedSubIpRange(long gid, String ip[],String[] mask){
		//verify all subip within the given ip range
		//true means ips covered sub range.
		//false means at least one subip not included in ips
		boolean isCoveredSubIpRange= false;

		try {
			
			List<IpAddrRangeCheck> subRange = IpAddrRangeCheckDao.findSubIpRange(gid);
			if(subRange.size()==0){
				return true;
			}
			//System.out.println("verifyInclucdeSub.... ip="+ip);
			if (ip==null||ip.length==0){ //when sub range has values but with empty input to cover.
				throw new Exception("Input parameter ip[] is null or ip[] length=0 .");
				//return true;		
			}
			boolean test[] = new boolean[subRange.size()];
			boolean testall = true ;   //diff
			for (int x=0;x <subRange.size();x++){
				IpAddrRangeCheck range = (IpAddrRangeCheck)subRange.get(x);
				boolean looptest = false; //testing if the sub range got include in ip list;
				for (int i=0;i<ip.length;i++){	
					test[x] = false;	// compare the ip and the range
					long ipMin = IPAddr.getMinIp(ip[i], mask[i]);System.out.println("ipMin="+ip[i]+":"+ipMin);
					long ipMax = IPAddr.getMaxIp(ip[i], mask[i]);System.out.println("ipMax="+ip[i]+":"+ipMax);
					
					long min =  range.getIpdecodeMin();System.out.println("range..min="+min+":");//+IPAddr.decode(min));
					long max =  range.getIpdecodeMax();System.out.println("range..max="+max+":");//+IPAddr.decode(max));
					
					if( ipMin <=min  && max <= ipMax ){
						test[x] =  true;
						
						//continue;
					}
					looptest = looptest | test[x];  // diff //test[x] in all [OR] 
					//testall = testall & test[x];  // diff
					//System.out.println("verify ...subip...loop ..test["+x+"]="+test[x]+" | "+looptest);
				}
				testall = testall & looptest;
				//System.out.println("verify ...subip...loop ..testall="+test[x]+" | "+looptest);
			}
			isCoveredSubIpRange =  testall;	
		} catch (IpAddrRangeCheckDaoException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			
			e.printStackTrace();
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			
			e.printStackTrace();
		}
		//System.out.println("verifyIPAddr...includesub ip...isCoveredSubIpRange="+isCoveredSubIpRange);
		return isCoveredSubIpRange;
		
	}
	
	
	public boolean verifyRange(long gid, String ip[],String[] mask){
		//verify all subip within the given ip range
		//true means ips covered sub range.
		//false means at least one subip not included in ips
		boolean isInRange= false;

		try {
			
			List<IpAddrRangeCheck> range = IpAddrRangeCheckDao.findInRange(gid);
			if(range.size()==0){
				return false; //no ip range at all
			}
			//System.out.println("verifyInclucdeSub.... ip="+ip);
			if (ip==null||ip.length==0){ //when sub range has values but with empty input to cover.
				throw new Exception("Input parameter ip[] is null or ip[] length=0 .");
				//return true;		
			}
			boolean test[] = new boolean[range.size()];
			boolean testall = false ;   //diff
			for (int x=0;x <range.size();x++){
				IpAddrRangeCheck derange = (IpAddrRangeCheck)range.get(x);
				boolean looptest = false; //testing if the sub range got include in ip list;
				for (int i=0;i<ip.length;i++){	
					test[x] = false;	// compare the ip and the range
					long ipMin = IPAddr.getMinIp(ip[i].trim(), mask[i].trim());System.out.println("ipMin="+ip[i]+":"+ipMin);
					long ipMax = IPAddr.getMaxIp(ip[i].trim(), mask[i].trim());System.out.println("ipMax="+ip[i]+":"+ipMax);
					
					long min =  derange.getIpdecodeMin();System.out.println("range..min="+min+":");//+IPAddr.decode(min));
					long max =  derange.getIpdecodeMax();System.out.println("range..max="+max+":");//+IPAddr.decode(max));
					
			//		if( ipMin <=min  && max <= ipMax ){
					if( min <= ipMin  && ipMax <= max ){
						test[x] =  true;
						System.out.println("test[x]="+test[x]+"; x="+x);
						//break;
						//continue;
					}
					looptest = looptest | test[x];  // diff //test[x] in all [OR] 
					//testall = testall & test[x];  // diff
					System.out.println("verify ...subip...loop ..test["+x+"]="+test[x]+" | "+looptest);
				}
				testall = testall | looptest;
				System.out.println("verify ...subip...loop ..testall="+test[x]+" | "+looptest);
			}
			isInRange =  testall;	
			System.out.println("verify ...subip...loop ..testall"+testall);
		} catch (IpAddrRangeCheckDaoException e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			
			e.printStackTrace();
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			
			e.printStackTrace();
		}
		System.out.println("verifyIPAddr...includesub ip...isCoveredSubIpRange="+isInRange);
		return isInRange;
		
	}
	
	
    public String getVerfyResultString(int result)
    {
        String answer = null;
        //int result ;//= verifyIpAddrRange(gid, ip, mask);
        switch(result)
        {
        case -1: 
            answer = "IP\u5730\u5740\u6BB5\u8303\u56F4\u4E0D\u5728\u7236\u7ED3\u70B9IP\u5730\u5740\u6BB5\u8303\u56F4\u5185";
            break;/* IP地址段范围不在父结点IP地址段范围内 */

        case -2: 
            answer = "IP\u5730\u5740\u6BB5\u8303\u56F4\u4E0E\u5144\u5F1F\u7ED3\u70B9IP\u5730\u5740\u6BB5\u8303\u56F4\u91CD\u53E0";
            break;/* IP地址段范围与兄弟结点IP地址段范围重叠 */

        case -3: 
            answer = "IP\u5730\u5740\u6BB5\u8303\u56F4\u4E0E\u5B50\u7ED3\u70B9IP\u5730\u5740\u6BB5\u8303\u56F4\u91CD\u53E0";
            break; /*  IP地址段范围与子结点IP地址段范围重叠  */

        case 1: // '\001'
            answer = "Succ";
            break;

        case 0: // '\0'
        default:
            answer = "Unknown";
            break;
        }
        return answer;
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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

	public void setTGrpNetDao(TGrpNetDao grpNetDao) {
		TGrpNetDao = grpNetDao;
	}

}
