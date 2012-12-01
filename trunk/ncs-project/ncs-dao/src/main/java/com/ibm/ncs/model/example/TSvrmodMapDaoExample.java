package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TSvrmodMapDao;
import com.ibm.ncs.model.dto.TSvrmodMap;
import com.ibm.ncs.model.exceptions.TSvrmodMapDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TSvrmodMapDaoExample
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
		// findWhereNmsidEquals(0);
		// findWhereModidEquals(0);
		// findWherePathEquals("");
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TSvrmodMapDao dao = DaoFactory.createTSvrmodMapDao();
		List<TSvrmodMap> _result = dao.findAll();
		for (TSvrmodMap dto : _result) {
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
		TSvrmodMapDao dao = DaoFactory.createTSvrmodMapDao();
		List<TSvrmodMap> _result = dao.findWhereNmsidEquals(nmsid);
		for (TSvrmodMap dto : _result) {
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
		TSvrmodMapDao dao = DaoFactory.createTSvrmodMapDao();
		List<TSvrmodMap> _result = dao.findWhereModidEquals(modid);
		for (TSvrmodMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePathEquals'
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void findWherePathEquals(String path) throws Exception
	{
		TSvrmodMapDao dao = DaoFactory.createTSvrmodMapDao();
		List<TSvrmodMap> _result = dao.findWherePathEquals(path);
		for (TSvrmodMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDescriptionEquals'
	 * 
	 * @param description
	 * @throws Exception
	 */
	public static void findWhereDescriptionEquals(String description) throws Exception
	{
		TSvrmodMapDao dao = DaoFactory.createTSvrmodMapDao();
		List<TSvrmodMap> _result = dao.findWhereDescriptionEquals(description);
		for (TSvrmodMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TSvrmodMap dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getNmsid() );
		buf.append( ", " );
		buf.append( dto.getModid() );
		buf.append( ", " );
		buf.append( dto.getPath() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
