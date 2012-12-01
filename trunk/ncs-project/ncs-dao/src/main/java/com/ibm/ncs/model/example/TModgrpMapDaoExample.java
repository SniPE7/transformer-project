package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TModgrpMapDao;
import com.ibm.ncs.model.dto.TModgrpMap;
import com.ibm.ncs.model.exceptions.TModgrpMapDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TModgrpMapDaoExample
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
		// findWhereGidEquals(0);
		// findWhereNmsidEquals(0);
		// findWhereModidEquals(0);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TModgrpMapDao dao = DaoFactory.createTModgrpMapDao();
		List<TModgrpMap> _result = dao.findAll();
		for (TModgrpMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereGidEquals'
	 * 
	 * @param gid
	 * @throws Exception
	 */
	public static void findWhereGidEquals(long gid) throws Exception
	{
		TModgrpMapDao dao = DaoFactory.createTModgrpMapDao();
		List<TModgrpMap> _result = dao.findWhereGidEquals(gid);
		for (TModgrpMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereNmsidEquals'
	 * 
	 * @param nmsid
	 * @throws Exception
	 */
	public static void findWhereNmsidEquals(long nmsid) throws Exception
	{
		TModgrpMapDao dao = DaoFactory.createTModgrpMapDao();
		List<TModgrpMap> _result = dao.findWhereNmsidEquals(nmsid);
		for (TModgrpMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereModidEquals'
	 * 
	 * @param modid
	 * @throws Exception
	 */
	public static void findWhereModidEquals(long modid) throws Exception
	{
		TModgrpMapDao dao = DaoFactory.createTModgrpMapDao();
		List<TModgrpMap> _result = dao.findWhereModidEquals(modid);
		for (TModgrpMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TModgrpMap dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getGid() );
		buf.append( ", " );
		buf.append( dto.getNmsid() );
		buf.append( ", " );
		buf.append( dto.getModid() );
		System.out.println( buf.toString() );
	}

}
