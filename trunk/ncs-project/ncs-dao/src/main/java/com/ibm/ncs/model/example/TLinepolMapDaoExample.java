package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dto.TLinepolMap;
import com.ibm.ncs.model.exceptions.TLinepolMapDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TLinepolMapDaoExample
{
	/**
	 * Method 'main'
	 * 
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String[] arg) throws Exception
	{
		// Uncomment one of the lines below to test the generated code
		
		// findAll();
		// findWherePtidEquals(0);
		// findWherePpidEquals(0);
		// findWhereMcodeEquals(0);
		// findWhereFlagEquals(null);
		// findWhereMpidEquals(0);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TLinepolMapDao dao = DaoFactory.createTLinepolMapDao();
		List<TLinepolMap> _result = dao.findAll();
		for (TLinepolMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePtidEquals'
	 * 
	 * @param ptid
	 * @throws Exception
	 */
	public static void findWherePtidEquals(long ptid) throws Exception
	{
		TLinepolMapDao dao = DaoFactory.createTLinepolMapDao();
		List<TLinepolMap> _result = dao.findWherePtidEquals(ptid);
		for (TLinepolMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePpidEquals'
	 * 
	 * @param ppid
	 * @throws Exception
	 */
	public static void findWherePpidEquals(long ppid) throws Exception
	{
		TLinepolMapDao dao = DaoFactory.createTLinepolMapDao();
		List<TLinepolMap> _result = dao.findWherePpidEquals(ppid);
		for (TLinepolMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMcodeEquals'
	 * 
	 * @param mcode
	 * @throws Exception
	 */
	public static void findWhereMcodeEquals(long mcode) throws Exception
	{
		TLinepolMapDao dao = DaoFactory.createTLinepolMapDao();
		List<TLinepolMap> _result = dao.findWhereMcodeEquals(mcode);
		for (TLinepolMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFlagEquals'
	 * 
	 * @param flag
	 * @throws Exception
	 */
	public static void findWhereFlagEquals(Integer flag) throws Exception
	{
		TLinepolMapDao dao = DaoFactory.createTLinepolMapDao();
		List<TLinepolMap> _result = dao.findWhereFlagEquals(flag);
		for (TLinepolMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMpidEquals'
	 * 
	 * @param mpid
	 * @throws Exception
	 */
	public static void findWhereMpidEquals(long mpid) throws Exception
	{
		TLinepolMapDao dao = DaoFactory.createTLinepolMapDao();
		List<TLinepolMap> _result = dao.findWhereMpidEquals(mpid);
		for (TLinepolMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TLinepolMap dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getPtid() );
		buf.append( ", " );
		buf.append( dto.getPpid() );
		buf.append( ", " );
		buf.append( dto.getMcode() );
		buf.append( ", " );
		buf.append( dto.getFlag() );
		buf.append( ", " );
		buf.append( dto.getMpid() );
		System.out.println( buf.toString() );
	}

}
