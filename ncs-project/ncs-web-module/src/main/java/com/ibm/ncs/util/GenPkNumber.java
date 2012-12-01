package com.ibm.ncs.util;

import java.util.List;

import com.ibm.ncs.model.dao.MaxNMSEQDao;
import com.ibm.ncs.model.dao.SeqTaskDao;
import com.ibm.ncs.model.dao.SequenceNMDao;
import com.ibm.ncs.model.dto.SeqTask;
import com.ibm.ncs.model.dto.SequenceNM;
import com.ibm.ncs.model.exceptions.MaxNMSEQDaoException;
import com.ibm.ncs.model.exceptions.SeqTaskDaoException;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class GenPkNumber {

	 MaxNMSEQDao MaxNMSEQDao ;//= DaoFactory.createMaxNMSEQDao();
	
	 SequenceNMDao  SequenceNMDao ;//= DaoFactory.createSequenceNMDao();
	
	 SeqTaskDao SeqTaskDao;

	/**
	 * Get last number of the NM_SEQ;
	 * @return
	 * @throws MaxNMSEQDaoException
	 */
	public  long getMaxNM() throws MaxNMSEQDaoException{
		return MaxNMSEQDao.getMaxNMSeq();
	}
	/**
	 * Get next sequence form the NM_SEQ
	 * @return long
	 * @throws SequenceNMDaoException
	 */
	public  long getID() throws SequenceNMDaoException{

		List<SequenceNM> l =
			SequenceNMDao.execute();
		long ret =  (l.get(0)).getGenID().longValue();
		//System.out.println("getseqnumber ?  ?="+ret);
		long maxused = getMaxUsed();
//		System.out.println("getID...ret="+ret+"; maxused="+maxused);
		if (ret<maxused) { 
			try {
				long newseq = setNMSeqNumber(maxused +1);
				//System.out.println("after chage seq...getseqnumber ="+newseq);
				ret = newseq;
			} catch (MaxNMSEQDaoException e) {
				Log4jInit.ncsLog .error("GenPkNumber... reset NM_SEQ sequences has error"+e);
				e.printStackTrace();
			}
		}
		return ret;
	}

	public   long setNMSeqNumber(long newNumber) throws MaxNMSEQDaoException{
		return MaxNMSEQDao.setNMSeqNumber(newNumber);
		
	}
	public  MaxNMSEQDao getMaxNMSEQDao() {
		return MaxNMSEQDao;
	}
	public  void setMaxNMSEQDao(MaxNMSEQDao maxNMSEQDao) {
		MaxNMSEQDao = maxNMSEQDao;
	}
	public  SequenceNMDao getSequenceNMDao() {
		return SequenceNMDao;
	}
	public  void setSequenceNMDao(SequenceNMDao sequenceNMDao) {
		SequenceNMDao = sequenceNMDao;
	}
	public long getMaxUsed() throws SequenceNMDaoException{
		List<SequenceNM> l =
			SequenceNMDao.usedMax();
		long ret = l.get(0).getGenID().longValue();
		//System.out.println("getMaxUsed="+ret);
		return ret;
	}
	
	public static void main(String args[]){
		GenPkNumber g =  new GenPkNumber();
		try {
			long maxused = g.getMaxUsed();
			System.out.println("maxused="+maxused);
			g.setNMSeqNumber(maxused+1);
		} catch (SequenceNMDaoException e) {
			e.printStackTrace();
		} catch (MaxNMSEQDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get next sequence form the TASK_SEQ
	 * @return long
	 * @throws SeqTaskDaoException
	 */
	public  long getTaskID() throws SeqTaskDaoException{

	
		List<SeqTask> l = SeqTaskDao.execute();
		long ret =  (l.get(0)).getGenTaskID().longValue();
		List<SeqTask> l2 = SeqTaskDao.usedTaskMax();
		long maxtask = (l2.get(0)).getGenTaskID().longValue();
//	System.out.println("getTaskID...ret="+ret+";  maxtask="+maxtask);
		if (ret<maxtask) { 	
			try{
				long newseq = SeqTaskDao.setSeqTaskNumber(maxtask +1);
				//System.out.println("after chage seq...getseqnumber ="+newseq);
				ret = newseq;	
			}catch(SeqTaskDaoException e){
				Log4jInit.ncsLog .error("GenPkNumber... reset TASK_SEQ sequences has error"+e);
				e.printStackTrace();
			}
		}
		return ret;
	
		
	}
	public SeqTaskDao getSeqTaskDao() {
		return SeqTaskDao;
	}
	public void setSeqTaskDao(SeqTaskDao seqTaskDao) {
		SeqTaskDao = seqTaskDao;
	}
}
