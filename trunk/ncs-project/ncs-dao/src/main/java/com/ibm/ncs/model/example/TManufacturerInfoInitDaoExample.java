package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.exceptions.TManufacturerInfoInitDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TManufacturerInfoInitDaoExample
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
		// findWhereMrnameEquals("");
		// findWhereObjectidEquals("");
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TManufacturerInfoInitDao dao = DaoFactory.createTManufacturerInfoInitDao();
		List<TManufacturerInfoInit> _result = dao.findAll();
		for (TManufacturerInfoInit dto : _result) {
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
		TManufacturerInfoInitDao dao = DaoFactory.createTManufacturerInfoInitDao();
		List<TManufacturerInfoInit> _result = dao.findWhereMridEquals(mrid);
		for (TManufacturerInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMrnameEquals'
	 * 
	 * @param mrname
	 * @throws Exception
	 */
	public static void findWhereMrnameEquals(String mrname) throws Exception
	{
		TManufacturerInfoInitDao dao = DaoFactory.createTManufacturerInfoInitDao();
		List<TManufacturerInfoInit> _result = dao.findWhereMrnameEquals(mrname);
		for (TManufacturerInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereObjectidEquals'
	 * 
	 * @param objectid
	 * @throws Exception
	 */
	public static void findWhereObjectidEquals(String objectid) throws Exception
	{
		TManufacturerInfoInitDao dao = DaoFactory.createTManufacturerInfoInitDao();
		List<TManufacturerInfoInit> _result = dao.findWhereObjectidEquals(objectid);
		for (TManufacturerInfoInit dto : _result) {
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
		TManufacturerInfoInitDao dao = DaoFactory.createTManufacturerInfoInitDao();
		List<TManufacturerInfoInit> _result = dao.findWhereDescriptionEquals(description);
		for (TManufacturerInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TManufacturerInfoInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getMrid() );
		buf.append( ", " );
		buf.append( dto.getMrname() );
		buf.append( ", " );
		buf.append( dto.getObjectid() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
