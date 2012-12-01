package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TEventsubtypeInfoInitDao;
import com.ibm.ncs.model.dto.TEventsubtypeInfoInit;
import com.ibm.ncs.model.exceptions.TEventsubtypeInfoInitDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TEventsubtypeInfoInitDaoExample
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
		// findWhereEstidEquals(0);
		// findWhereEvesubtypeEquals("");
		// findWhereDescriptionEquals("");
		// findWhereEstcodeEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TEventsubtypeInfoInitDao dao = DaoFactory.createTEventsubtypeInfoInitDao();
		List<TEventsubtypeInfoInit> _result = dao.findAll();
		for (TEventsubtypeInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEstidEquals'
	 * 
	 * @param estid
	 * @throws Exception
	 */
	public static void findWhereEstidEquals(long estid) throws Exception
	{
		TEventsubtypeInfoInitDao dao = DaoFactory.createTEventsubtypeInfoInitDao();
		List<TEventsubtypeInfoInit> _result = dao.findWhereEstidEquals(estid);
		for (TEventsubtypeInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEvesubtypeEquals'
	 * 
	 * @param evesubtype
	 * @throws Exception
	 */
	public static void findWhereEvesubtypeEquals(String evesubtype) throws Exception
	{
		TEventsubtypeInfoInitDao dao = DaoFactory.createTEventsubtypeInfoInitDao();
		List<TEventsubtypeInfoInit> _result = dao.findWhereEvesubtypeEquals(evesubtype);
		for (TEventsubtypeInfoInit dto : _result) {
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
		TEventsubtypeInfoInitDao dao = DaoFactory.createTEventsubtypeInfoInitDao();
		List<TEventsubtypeInfoInit> _result = dao.findWhereDescriptionEquals(description);
		for (TEventsubtypeInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEstcodeEquals'
	 * 
	 * @param estcode
	 * @throws Exception
	 */
	public static void findWhereEstcodeEquals(String estcode) throws Exception
	{
		TEventsubtypeInfoInitDao dao = DaoFactory.createTEventsubtypeInfoInitDao();
		List<TEventsubtypeInfoInit> _result = dao.findWhereEstcodeEquals(estcode);
		for (TEventsubtypeInfoInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TEventsubtypeInfoInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getEstid() );
		buf.append( ", " );
		buf.append( dto.getEvesubtype() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		buf.append( ", " );
		buf.append( dto.getEstcode() );
		System.out.println( buf.toString() );
	}

}
