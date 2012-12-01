package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TEventtypeInfoInitDao;
import com.ibm.ncs.model.dto.TEventtypeInfoInit;
import com.ibm.ncs.model.exceptions.TEventtypeInfoInitDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TEventtypeInfoInitDaoExample
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
		// findWhereEtidEquals(0);
		// findWhereEvetypeEquals("");
		// findWhereDescriptionEquals("");
		// findWhereEtcodeEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TEventtypeInfoInitDao dao = DaoFactory.createTEventtypeInfoInitDao();
		List<TEventtypeInfoInit> _result = dao.findAll();
		for (TEventtypeInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEtidEquals'
	 * 
	 * @param etid
	 * @throws Exception
	 */
	public static void findWhereEtidEquals(long etid) throws Exception
	{
		TEventtypeInfoInitDao dao = DaoFactory.createTEventtypeInfoInitDao();
		List<TEventtypeInfoInit> _result = dao.findWhereEtidEquals(etid);
		for (TEventtypeInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEvetypeEquals'
	 * 
	 * @param evetype
	 * @throws Exception
	 */
	public static void findWhereEvetypeEquals(String evetype) throws Exception
	{
		TEventtypeInfoInitDao dao = DaoFactory.createTEventtypeInfoInitDao();
		List<TEventtypeInfoInit> _result = dao.findWhereEvetypeEquals(evetype);
		for (TEventtypeInfoInit dto : _result) {
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
		TEventtypeInfoInitDao dao = DaoFactory.createTEventtypeInfoInitDao();
		List<TEventtypeInfoInit> _result = dao.findWhereDescriptionEquals(description);
		for (TEventtypeInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEtcodeEquals'
	 * 
	 * @param etcode
	 * @throws Exception
	 */
	public static void findWhereEtcodeEquals(String etcode) throws Exception
	{
		TEventtypeInfoInitDao dao = DaoFactory.createTEventtypeInfoInitDao();
		List<TEventtypeInfoInit> _result = dao.findWhereEtcodeEquals(etcode);
		for (TEventtypeInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TEventtypeInfoInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getEtid() );
		buf.append( ", " );
		buf.append( dto.getEvetype() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		buf.append( ", " );
		buf.append( dto.getEtcode() );
		System.out.println( buf.toString() );
	}

}
