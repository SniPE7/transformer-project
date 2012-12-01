package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dto.TEventOidInit;
import com.ibm.ncs.model.exceptions.TEventOidInitDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TEventOidInitDaoExample
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
		// findWhereMridEquals(0);
		// findWhereDtidEquals(0);
		// findWhereModidEquals(0);
		// findWhereEveidEquals(0);
		// findWhereOidgroupnameEquals("");
		// findWhereOidEquals("");
		// findWhereUnitEquals("");
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TEventOidInitDao dao = DaoFactory.createTEventOidInitDao();
		List<TEventOidInit> _result = dao.findAll();
		for (TEventOidInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMridEquals'
	 * 
	 * @param mrid
	 * @throws Exception
	 */
	public static void findWhereMridEquals(long mrid) throws Exception
	{
		TEventOidInitDao dao = DaoFactory.createTEventOidInitDao();
		List<TEventOidInit> _result = dao.findWhereMridEquals(mrid);
		for (TEventOidInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDtidEquals'
	 * 
	 * @param dtid
	 * @throws Exception
	 */
	public static void findWhereDtidEquals(long dtid) throws Exception
	{
		TEventOidInitDao dao = DaoFactory.createTEventOidInitDao();
		List<TEventOidInit> _result = dao.findWhereDtidEquals(dtid);
		for (TEventOidInit dto : _result) {
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
		TEventOidInitDao dao = DaoFactory.createTEventOidInitDao();
		List<TEventOidInit> _result = dao.findWhereModidEquals(modid);
		for (TEventOidInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEveidEquals'
	 * 
	 * @param eveid
	 * @throws Exception
	 */
	public static void findWhereEveidEquals(long eveid) throws Exception
	{
		TEventOidInitDao dao = DaoFactory.createTEventOidInitDao();
		List<TEventOidInit> _result = dao.findWhereEveidEquals(eveid);
		for (TEventOidInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidgroupnameEquals'
	 * 
	 * @param oidgroupname
	 * @throws Exception
	 */
	public static void findWhereOidgroupnameEquals(String oidgroupname) throws Exception
	{
		TEventOidInitDao dao = DaoFactory.createTEventOidInitDao();
		List<TEventOidInit> _result = dao.findWhereOidgroupnameEquals(oidgroupname);
		for (TEventOidInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidEquals'
	 * 
	 * @param oid
	 * @throws Exception
	 */
	public static void findWhereOidEquals(String oid) throws Exception
	{
		TEventOidInitDao dao = DaoFactory.createTEventOidInitDao();
		List<TEventOidInit> _result = dao.findWhereOidEquals(oid);
		for (TEventOidInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereUnitEquals'
	 * 
	 * @param unit
	 * @throws Exception
	 */
	public static void findWhereUnitEquals(String unit) throws Exception
	{
		TEventOidInitDao dao = DaoFactory.createTEventOidInitDao();
		List<TEventOidInit> _result = dao.findWhereUnitEquals(unit);
		for (TEventOidInit dto : _result) {
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
		TEventOidInitDao dao = DaoFactory.createTEventOidInitDao();
		List<TEventOidInit> _result = dao.findWhereDescriptionEquals(description);
		for (TEventOidInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TEventOidInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getMrid() );
		buf.append( ", " );
		buf.append( dto.getDtid() );
		buf.append( ", " );
		buf.append( dto.getModid() );
		buf.append( ", " );
		buf.append( dto.getEveid() );
		buf.append( ", " );
		buf.append( dto.getOidgroupname() );
		buf.append( ", " );
		buf.append( dto.getOid() );
		buf.append( ", " );
		buf.append( dto.getUnit() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
