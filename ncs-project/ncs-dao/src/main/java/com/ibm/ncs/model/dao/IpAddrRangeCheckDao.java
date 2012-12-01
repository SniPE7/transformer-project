package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.IpAddrRangeCheckDao;
import com.ibm.ncs.model.dto.IpAddrRangeCheck;
import com.ibm.ncs.model.exceptions.IpAddrRangeCheckDaoException;
import java.util.List;

public interface IpAddrRangeCheckDao
{
	/**
	 * Method 'execute'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> execute(long gid) throws IpAddrRangeCheckDaoException;

	/**
	 * Method 'findParentIpRange'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> findParentIpRange(long gid) throws IpAddrRangeCheckDaoException;
	
	
	
	public List<IpAddrRangeCheck> findParentIpRangeBySupid(long gid,long supid) throws IpAddrRangeCheckDaoException;
	/**
	 * Method 'findSiblingIpRange'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> findSiblingIpRange(long gid) throws IpAddrRangeCheckDaoException;
	
	public List<IpAddrRangeCheck> findSiblingIpRange(long gid, long supid) throws IpAddrRangeCheckDaoException;
	
	/**
	 * Method 'findSubIpRange'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> findSubIpRange(long gid) throws IpAddrRangeCheckDaoException;

	/**
	 * Method 'findInRange'
	 * 
	 * @param gid
	 * @throws IpAddrRangeCheckDaoException
	 * @return List<IpAddrRangeCheck>
	 */
	public List<IpAddrRangeCheck> findInRange(long gid) throws IpAddrRangeCheckDaoException;
}
