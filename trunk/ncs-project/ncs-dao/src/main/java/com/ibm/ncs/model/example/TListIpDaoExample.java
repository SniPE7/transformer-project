package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TListIpDaoExample
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
		// findWhereCategoryEquals(0);
		// findWhereIpEquals("");
		// findWhereMaskEquals("");
		// findWhereIpdecodeMinEquals(0);
		// findWhereIpdecodeMaxEquals(0);
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TListIpDao dao = DaoFactory.createTListIpDao();
		List<TListIp> _result = dao.findAll();
		for (TListIp dto : _result) {
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
		TListIpDao dao = DaoFactory.createTListIpDao();
		List<TListIp> _result = dao.findWhereGidEquals(gid);
		for (TListIp dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereCategoryEquals'
	 * 
	 * @param category
	 * @throws Exception
	 */
	public static void findWhereCategoryEquals(long category) throws Exception
	{
		TListIpDao dao = DaoFactory.createTListIpDao();
		List<TListIp> _result = dao.findWhereCategoryEquals(category);
		for (TListIp dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIpEquals'
	 * 
	 * @param ip
	 * @throws Exception
	 */
	public static void findWhereIpEquals(String ip) throws Exception
	{
		TListIpDao dao = DaoFactory.createTListIpDao();
		List<TListIp> _result = dao.findWhereIpEquals(ip);
		for (TListIp dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMaskEquals'
	 * 
	 * @param mask
	 * @throws Exception
	 */
	public static void findWhereMaskEquals(String mask) throws Exception
	{
		TListIpDao dao = DaoFactory.createTListIpDao();
		List<TListIp> _result = dao.findWhereMaskEquals(mask);
		for (TListIp dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIpdecodeMinEquals'
	 * 
	 * @param ipdecodeMin
	 * @throws Exception
	 */
	public static void findWhereIpdecodeMinEquals(long ipdecodeMin) throws Exception
	{
		TListIpDao dao = DaoFactory.createTListIpDao();
		List<TListIp> _result = dao.findWhereIpdecodeMinEquals(ipdecodeMin);
		for (TListIp dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIpdecodeMaxEquals'
	 * 
	 * @param ipdecodeMax
	 * @throws Exception
	 */
	public static void findWhereIpdecodeMaxEquals(long ipdecodeMax) throws Exception
	{
		TListIpDao dao = DaoFactory.createTListIpDao();
		List<TListIp> _result = dao.findWhereIpdecodeMaxEquals(ipdecodeMax);
		for (TListIp dto : _result) {
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
		TListIpDao dao = DaoFactory.createTListIpDao();
		List<TListIp> _result = dao.findWhereDescriptionEquals(description);
		for (TListIp dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TListIp dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getGid() );
		buf.append( ", " );
		buf.append( dto.getCategory() );
		buf.append( ", " );
		buf.append( dto.getIp() );
		buf.append( ", " );
		buf.append( dto.getMask() );
		buf.append( ", " );
		buf.append( dto.getIpdecodeMin() );
		buf.append( ", " );
		buf.append( dto.getIpdecodeMax() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
