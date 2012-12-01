package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TPolicyBaseDaoExample
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
		// findWhereMpidEquals(0);
		// findWhereMpnameEquals("");
		// findWhereCategoryEquals(0);
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TPolicyBaseDao dao = DaoFactory.createTPolicyBaseDao();
		List<TPolicyBase> _result = dao.findAll();
		for (TPolicyBase dto : _result) {
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
		TPolicyBaseDao dao = DaoFactory.createTPolicyBaseDao();
		List<TPolicyBase> _result = dao.findWhereMpidEquals(mpid);
		for (TPolicyBase dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMpnameEquals'
	 * 
	 * @param mpname
	 * @throws Exception
	 */
	public static void findWhereMpnameEquals(String mpname) throws Exception
	{
		TPolicyBaseDao dao = DaoFactory.createTPolicyBaseDao();
		List<TPolicyBase> _result = dao.findWhereMpnameEquals(mpname);
		for (TPolicyBase dto : _result) {
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
		TPolicyBaseDao dao = DaoFactory.createTPolicyBaseDao();
		List<TPolicyBase> _result = dao.findWhereCategoryEquals(category);
		for (TPolicyBase dto : _result) {
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
		TPolicyBaseDao dao = DaoFactory.createTPolicyBaseDao();
		List<TPolicyBase> _result = dao.findWhereDescriptionEquals(description);
		for (TPolicyBase dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TPolicyBase dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getMpid() );
		buf.append( ", " );
		buf.append( dto.getMpname() );
		buf.append( ", " );
		buf.append( dto.getCategory() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
