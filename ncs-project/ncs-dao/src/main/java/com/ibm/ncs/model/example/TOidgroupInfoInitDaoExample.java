package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.TOidgroupInfoInit;
import com.ibm.ncs.model.exceptions.TOidgroupInfoInitDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TOidgroupInfoInitDaoExample
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
		// findWhereOpidEquals(0);
		// findWhereOidgroupnameEquals("");
		// findWhereOtypeEquals(0);
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TOidgroupInfoInitDao dao = DaoFactory.createTOidgroupInfoInitDao();
		List<TOidgroupInfoInit> _result = dao.findAll();
		for (TOidgroupInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOpidEquals'
	 * 
	 * @param opid
	 * @throws Exception
	 */
	public static void findWhereOpidEquals(long opid) throws Exception
	{
		TOidgroupInfoInitDao dao = DaoFactory.createTOidgroupInfoInitDao();
		List<TOidgroupInfoInit> _result = dao.findWhereOpidEquals(opid);
		for (TOidgroupInfoInit dto : _result) {
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
		TOidgroupInfoInitDao dao = DaoFactory.createTOidgroupInfoInitDao();
		List<TOidgroupInfoInit> _result = dao.findWhereOidgroupnameEquals(oidgroupname);
		for (TOidgroupInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOtypeEquals'
	 * 
	 * @param otype
	 * @throws Exception
	 */
	public static void findWhereOtypeEquals(long otype) throws Exception
	{
		TOidgroupInfoInitDao dao = DaoFactory.createTOidgroupInfoInitDao();
		List<TOidgroupInfoInit> _result = dao.findWhereOtypeEquals(otype);
		for (TOidgroupInfoInit dto : _result) {
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
		TOidgroupInfoInitDao dao = DaoFactory.createTOidgroupInfoInitDao();
		List<TOidgroupInfoInit> _result = dao.findWhereDescriptionEquals(description);
		for (TOidgroupInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TOidgroupInfoInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getOpid() );
		buf.append( ", " );
		buf.append( dto.getOidgroupname() );
		buf.append( ", " );
		buf.append( dto.getOtype() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
