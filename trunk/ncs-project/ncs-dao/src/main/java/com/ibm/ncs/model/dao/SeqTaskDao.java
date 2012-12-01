package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.SeqTaskDao;
import com.ibm.ncs.model.dto.SeqTask;
import com.ibm.ncs.model.exceptions.SeqTaskDaoException;
import java.util.List;

public interface SeqTaskDao
{
	/**
	 * Method 'execute'
	 * 
	 * @throws SeqTaskDaoException
	 * @return SeqTask
	 */
	public List<SeqTask> execute() throws SeqTaskDaoException;
	
	public List<SeqTask> usedTaskMax() throws SeqTaskDaoException;

	public long setSeqTaskNumber(long newNumber) throws SeqTaskDaoException;
	
	public long getMaxTaskSeq() throws SeqTaskDaoException;
	
}
