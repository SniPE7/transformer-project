package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.IpAddrRangeCheckParentDao;
import com.ibm.ncs.model.dto.IpAddrRangeCheckParent;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckParentDaoException;
import java.util.List;

public interface IpAddrRangeCheckParentDao
{
	/**
	 * Method 'execute'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckParentDaoException
	 * @return List<IpAddrRangeCheckParent>
	 */
	public List<IpAddrRangeCheckParent> execute(long gid) throws IpAddrRangeCheckParentDaoException;

}
