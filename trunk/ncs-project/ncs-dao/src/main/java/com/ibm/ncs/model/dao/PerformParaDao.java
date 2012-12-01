package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.PerformParaDao;
import com.ibm.ncs.model.dto.PerformPara;
import com.ibm.ncs.model.exceptions.PerformParaDaoException;
import java.util.List;

public interface PerformParaDao
{
	/**
	 * Method 'execute'
	 * 
	 * @param dtid
	 * @param ecode
	 * @throws PerformParaDaoException
	 * @return List<PerformPara>
	 */
	public List<PerformPara> execute(long dtid, long ecode) throws PerformParaDaoException;
	public List<PerformPara> findByDtidEcode(long dtid, long ecode) throws PerformParaDaoException;

}
