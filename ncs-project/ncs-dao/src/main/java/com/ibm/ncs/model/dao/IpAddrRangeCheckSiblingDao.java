package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.IpAddrRangeCheckSiblingDao;
import com.ibm.ncs.model.dto.IpAddrRangeCheckSibling;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckSiblingDaoException;
import java.util.List;

public interface IpAddrRangeCheckSiblingDao
{
	/**
	 * Method 'execute'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckSiblingDaoException
	 * @return List<IpAddrRangeCheckSibling>
	 */
	public List<IpAddrRangeCheckSibling> execute(long gid) throws IpAddrRangeCheckSiblingDaoException;

}
