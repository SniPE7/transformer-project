package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.MaxNMSEQDao;
import com.ibm.ncs.model.dto.MaxNMSEQ;
import com.ibm.ncs.model.exceptions.MaxNMSEQDaoException;
import java.util.List;

public interface MaxNMSEQDao
{
	/**
	 * Method 'getMaxNMSeq'
	 * 
	 * @throws MaxNMSEQDaoException
	 * @return long
	 */
	//public MaxNMSEQ execute() throws MaxNMSEQDaoException;
	
	public long getMaxNMSeq() throws MaxNMSEQDaoException;
	
	public long setNMSeqNumber(long newNumber) throws MaxNMSEQDaoException;

}
