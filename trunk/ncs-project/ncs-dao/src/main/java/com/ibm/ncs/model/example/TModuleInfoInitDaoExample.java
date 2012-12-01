package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.exceptions.TModuleInfoInitDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TModuleInfoInitDaoExample
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
		// findWhereModidEquals(0);
		// findWhereMnameEquals("");
		// findWhereMcodeEquals(0);
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TModuleInfoInitDao dao = DaoFactory.createTModuleInfoInitDao();
		List<TModuleInfoInit> _result = dao.findAll();
		for (TModuleInfoInit dto : _result) {
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
		TModuleInfoInitDao dao = DaoFactory.createTModuleInfoInitDao();
		List<TModuleInfoInit> _result = dao.findWhereModidEquals(modid);
		for (TModuleInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMnameEquals'
	 * 
	 * @param mname
	 * @throws Exception
	 */
	public static void findWhereMnameEquals(String mname) throws Exception
	{
		TModuleInfoInitDao dao = DaoFactory.createTModuleInfoInitDao();
		List<TModuleInfoInit> _result = dao.findWhereMnameEquals(mname);
		for (TModuleInfoInit dto : _result) {
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
		TModuleInfoInitDao dao = DaoFactory.createTModuleInfoInitDao();
		List<TModuleInfoInit> _result = dao.findWhereMcodeEquals(mcode);
		for (TModuleInfoInit dto : _result) {
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
		TModuleInfoInitDao dao = DaoFactory.createTModuleInfoInitDao();
		List<TModuleInfoInit> _result = dao.findWhereDescriptionEquals(description);
		for (TModuleInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TModuleInfoInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getModid() );
		buf.append( ", " );
		buf.append( dto.getMname() );
		buf.append( ", " );
		buf.append( dto.getMcode() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
