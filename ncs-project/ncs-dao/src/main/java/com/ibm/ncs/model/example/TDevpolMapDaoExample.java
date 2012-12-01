package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dto.TDevpolMap;
import com.ibm.ncs.model.exceptions.TDevpolMapDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TDevpolMapDaoExample
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
		// findWhereDevidEquals(0);
		// findWhereMpidEquals(0);
		// findWherePpidEquals(0);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TDevpolMapDao dao = DaoFactory.createTDevpolMapDao();
		List<TDevpolMap> _result = dao.findAll();
		for (TDevpolMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDevidEquals'
	 * 
	 * @param devid
	 * @throws Exception
	 */
	public static void findWhereDevidEquals(long devid) throws Exception
	{
		TDevpolMapDao dao = DaoFactory.createTDevpolMapDao();
		List<TDevpolMap> _result = dao.findWhereDevidEquals(devid);
		for (TDevpolMap dto : _result) {
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
		TDevpolMapDao dao = DaoFactory.createTDevpolMapDao();
		List<TDevpolMap> _result = dao.findWhereMpidEquals(mpid);
		for (TDevpolMap dto : _result) {
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
		TDevpolMapDao dao = DaoFactory.createTDevpolMapDao();
		List<TDevpolMap> _result = dao.findWherePpidEquals(ppid);
		for (TDevpolMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TDevpolMap dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getDevid() );
		buf.append( ", " );
		buf.append( dto.getMpid() );
		buf.append( ", " );
		buf.append( dto.getPpid() );
		System.out.println( buf.toString() );
	}

}
