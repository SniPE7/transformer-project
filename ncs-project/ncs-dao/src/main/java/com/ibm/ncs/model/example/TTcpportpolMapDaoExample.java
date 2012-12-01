package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TTcpportpolMapDao;
import com.ibm.ncs.model.dto.TTcpportpolMap;
import com.ibm.ncs.model.exceptions.TTcpportpolMapDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TTcpportpolMapDaoExample
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
		// findWhereHidEquals(0);
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
		TTcpportpolMapDao dao = DaoFactory.createTTcpportpolMapDao();
		List<TTcpportpolMap> _result = dao.findAll();
		for (TTcpportpolMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereHidEquals'
	 * 
	 * @param hid
	 * @throws Exception
	 */
	public static void findWhereHidEquals(long hid) throws Exception
	{
		TTcpportpolMapDao dao = DaoFactory.createTTcpportpolMapDao();
		List<TTcpportpolMap> _result = dao.findWhereHidEquals(hid);
		for (TTcpportpolMap dto : _result) {
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
		TTcpportpolMapDao dao = DaoFactory.createTTcpportpolMapDao();
		List<TTcpportpolMap> _result = dao.findWhereMpidEquals(mpid);
		for (TTcpportpolMap dto : _result) {
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
		TTcpportpolMapDao dao = DaoFactory.createTTcpportpolMapDao();
		List<TTcpportpolMap> _result = dao.findWherePpidEquals(ppid);
		for (TTcpportpolMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TTcpportpolMap dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getHid() );
		buf.append( ", " );
		buf.append( dto.getMpid() );
		buf.append( ", " );
		buf.append( dto.getPpid() );
		System.out.println( buf.toString() );
	}

}
