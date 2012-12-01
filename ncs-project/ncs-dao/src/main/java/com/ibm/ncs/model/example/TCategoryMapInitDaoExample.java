package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.exceptions.TCategoryMapInitDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TCategoryMapInitDaoExample
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
		// findWhereIdEquals(0);
		// findWhereNameEquals("");
		// findWhereFlagEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TCategoryMapInitDao dao = DaoFactory.createTCategoryMapInitDao();
		List<TCategoryMapInit> _result = dao.findAll();
		for (TCategoryMapInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIdEquals'
	 * 
	 * @param id
	 * @throws Exception
	 */
	public static void findWhereIdEquals(long id) throws Exception
	{
		TCategoryMapInitDao dao = DaoFactory.createTCategoryMapInitDao();
		List<TCategoryMapInit> _result = dao.findWhereIdEquals(id);
		for (TCategoryMapInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereNameEquals'
	 * 
	 * @param name
	 * @throws Exception
	 */
	public static void findWhereNameEquals(String name) throws Exception
	{
		TCategoryMapInitDao dao = DaoFactory.createTCategoryMapInitDao();
		List<TCategoryMapInit> _result = dao.findWhereNameEquals(name);
		for (TCategoryMapInit dto : _result) {
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
		TCategoryMapInitDao dao = DaoFactory.createTCategoryMapInitDao();
		List<TCategoryMapInit> _result = dao.findWhereFlagEquals(flag);
		for (TCategoryMapInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TCategoryMapInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getName() );
		buf.append( ", " );
		buf.append( dto.getFlag() );
		System.out.println( buf.toString() );
	}

}
