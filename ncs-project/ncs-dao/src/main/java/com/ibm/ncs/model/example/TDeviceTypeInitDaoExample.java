package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.exceptions.TDeviceTypeInitDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TDeviceTypeInitDaoExample
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
		// findWhereCategoryEquals(0);
		// findWhereSubcategoryEquals("");
		// findWhereModelEquals("");
		// findWhereObjectidEquals("");
		// findWhereLogoEquals("");
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TDeviceTypeInitDao dao = DaoFactory.createTDeviceTypeInitDao();
		List<TDeviceTypeInit> _result = dao.findAll();
		for (TDeviceTypeInit dto : _result) {
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
		TDeviceTypeInitDao dao = DaoFactory.createTDeviceTypeInitDao();
		List<TDeviceTypeInit> _result = dao.findWhereMridEquals(mrid);
		for (TDeviceTypeInit dto : _result) {
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
		TDeviceTypeInitDao dao = DaoFactory.createTDeviceTypeInitDao();
		List<TDeviceTypeInit> _result = dao.findWhereDtidEquals(dtid);
		for (TDeviceTypeInit dto : _result) {
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
		TDeviceTypeInitDao dao = DaoFactory.createTDeviceTypeInitDao();
		List<TDeviceTypeInit> _result = dao.findWhereCategoryEquals(category);
		for (TDeviceTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSubcategoryEquals'
	 * 
	 * @param subcategory
	 * @throws Exception
	 */
	public static void findWhereSubcategoryEquals(String subcategory) throws Exception
	{
		TDeviceTypeInitDao dao = DaoFactory.createTDeviceTypeInitDao();
		List<TDeviceTypeInit> _result = dao.findWhereSubcategoryEquals(subcategory);
		for (TDeviceTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereModelEquals'
	 * 
	 * @param model
	 * @throws Exception
	 */
	public static void findWhereModelEquals(String model) throws Exception
	{
		TDeviceTypeInitDao dao = DaoFactory.createTDeviceTypeInitDao();
		List<TDeviceTypeInit> _result = dao.findWhereModelEquals(model);
		for (TDeviceTypeInit dto : _result) {
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
		TDeviceTypeInitDao dao = DaoFactory.createTDeviceTypeInitDao();
		List<TDeviceTypeInit> _result = dao.findWhereObjectidEquals(objectid);
		for (TDeviceTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereLogoEquals'
	 * 
	 * @param logo
	 * @throws Exception
	 */
	public static void findWhereLogoEquals(String logo) throws Exception
	{
		TDeviceTypeInitDao dao = DaoFactory.createTDeviceTypeInitDao();
		List<TDeviceTypeInit> _result = dao.findWhereLogoEquals(logo);
		for (TDeviceTypeInit dto : _result) {
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
		TDeviceTypeInitDao dao = DaoFactory.createTDeviceTypeInitDao();
		List<TDeviceTypeInit> _result = dao.findWhereDescriptionEquals(description);
		for (TDeviceTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TDeviceTypeInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getMrid() );
		buf.append( ", " );
		buf.append( dto.getDtid() );
		buf.append( ", " );
		buf.append( dto.getCategory() );
		buf.append( ", " );
		buf.append( dto.getSubcategory() );
		buf.append( ", " );
		buf.append( dto.getModel() );
		buf.append( ", " );
		buf.append( dto.getObjectid() );
		buf.append( ", " );
		buf.append( dto.getLogo() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
