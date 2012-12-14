package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.PolDetailDspDao;
import com.ibm.ncs.model.dto.PolDetailDsp;
import com.ibm.ncs.model.exceptions.PolDetailDspDaoException;
import java.util.List;

public interface PolDetailDspDao
{
	/**
	 * Method 'execute'
	 * 
	 * @param mpid
	 * @param modid
	 * @param general
	 * @param ecode
	 * @param mcode
	 * @param mname
	 * @throws PolDetailDspDaoException
	 * @return List<PolDetailDsp>
	 */
	public List<PolDetailDsp> execute(long mpid, long modid, long general, long ecode, long mcode, java.lang.String mname) throws PolDetailDspDaoException;
	public List<PolDetailDsp> findByMpidMcode(long mpid,  long mcode) throws PolDetailDspDaoException;
	public List<PolDetailDsp> findByMpid(long mpid) throws PolDetailDspDaoException;
	
	public List<PolDetailDsp> findByPtvid(long ptvid) throws PolDetailDspDaoException;
}
