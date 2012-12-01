package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.IpAddrRangeCheckSonDao;
import com.ibm.ncs.model.dto.IpAddrRangeCheckSon;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckSonDaoException;
import java.util.List;

public interface IpAddrRangeCheckSonDao
{
	/**
	 * Method 'execute'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckSonDaoException
	 * @return List<IpAddrRangeCheckSon>
	 */
	public List<IpAddrRangeCheckSon> execute(long gid) throws IpAddrRangeCheckSonDaoException;

}
