package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dto.TOidgroupDetailsInit;
import com.ibm.ncs.model.exceptions.TOidgroupDetailsInitDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TOidgroupDetailsInitDaoExample
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
		// findWhereOidvalueEquals("");
		// findWhereOidnameEquals("");
		// findWhereOidunitEquals("");
		// findWhereFlagEquals("");
		// findWhereOidindexEquals(0);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TOidgroupDetailsInitDao dao = DaoFactory.createTOidgroupDetailsInitDao();
		List<TOidgroupDetailsInit> _result = dao.findAll();
		for (TOidgroupDetailsInit dto : _result) {
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
		TOidgroupDetailsInitDao dao = DaoFactory.createTOidgroupDetailsInitDao();
		List<TOidgroupDetailsInit> _result = dao.findWhereOpidEquals(opid);
		for (TOidgroupDetailsInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidvalueEquals'
	 * 
	 * @param oidvalue
	 * @throws Exception
	 */
	public static void findWhereOidvalueEquals(String oidvalue) throws Exception
	{
		TOidgroupDetailsInitDao dao = DaoFactory.createTOidgroupDetailsInitDao();
		List<TOidgroupDetailsInit> _result = dao.findWhereOidvalueEquals(oidvalue);
		for (TOidgroupDetailsInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidnameEquals'
	 * 
	 * @param oidname
	 * @throws Exception
	 */
	public static void findWhereOidnameEquals(String oidname) throws Exception
	{
		TOidgroupDetailsInitDao dao = DaoFactory.createTOidgroupDetailsInitDao();
		List<TOidgroupDetailsInit> _result = dao.findWhereOidnameEquals(oidname);
		for (TOidgroupDetailsInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidunitEquals'
	 * 
	 * @param oidunit
	 * @throws Exception
	 */
	public static void findWhereOidunitEquals(String oidunit) throws Exception
	{
		TOidgroupDetailsInitDao dao = DaoFactory.createTOidgroupDetailsInitDao();
		List<TOidgroupDetailsInit> _result = dao.findWhereOidunitEquals(oidunit);
		for (TOidgroupDetailsInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFlagEquals'
	 * 
	 * @param flag
	 * @throws Exception
	 */
	public static void findWhereFlagEquals(String flag) throws Exception
	{
		TOidgroupDetailsInitDao dao = DaoFactory.createTOidgroupDetailsInitDao();
		List<TOidgroupDetailsInit> _result = dao.findWhereFlagEquals(flag);
		for (TOidgroupDetailsInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidindexEquals'
	 * 
	 * @param oidindex
	 * @throws Exception
	 */
	public static void findWhereOidindexEquals(long oidindex) throws Exception
	{
		TOidgroupDetailsInitDao dao = DaoFactory.createTOidgroupDetailsInitDao();
		List<TOidgroupDetailsInit> _result = dao.findWhereOidindexEquals(oidindex);
		for (TOidgroupDetailsInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TOidgroupDetailsInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getOpid() );
		buf.append( ", " );
		buf.append( dto.getOidvalue() );
		buf.append( ", " );
		buf.append( dto.getOidname() );
		buf.append( ", " );
		buf.append( dto.getOidunit() );
		buf.append( ", " );
		buf.append( dto.getFlag() );
		buf.append( ", " );
		buf.append( dto.getOidindex() );
		System.out.println( buf.toString() );
	}

}
