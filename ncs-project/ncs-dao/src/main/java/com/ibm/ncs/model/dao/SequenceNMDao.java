package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.SequenceNMDao;
import com.ibm.ncs.model.dto.SequenceNM;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;
import java.util.List;

public interface SequenceNMDao
{
	/**
	 * Method 'execute'
	 * 
	 * @throws SequenceNMDaoException
	 * @return SequenceNM
	 */
	public List<SequenceNM> execute() throws SequenceNMDaoException;

	public List<SequenceNM> usedMax() throws SequenceNMDaoException;
	
	
}
