package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.exceptions.TEventTypeInitDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TEventTypeInitDaoExample
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
		// findWhereModidEquals(0);
		// findWhereEveidEquals(0);
		// findWhereEstidEquals(0);
		// findWhereEveothernameEquals("");
		// findWhereEcodeEquals(0);
		// findWhereGeneralEquals(0);
		// findWhereMajorEquals("");
		// findWhereMinorEquals("");
		// findWhereOtherEquals("");
		// findWhereDescriptionEquals("");
		// findWhereUseflagEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findAll();
		for (TEventTypeInit dto : _result) {
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
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereEtidEquals(etid);
		for (TEventTypeInit dto : _result) {
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
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereModidEquals(modid);
		for (TEventTypeInit dto : _result) {
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
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereEveidEquals(eveid);
		for (TEventTypeInit dto : _result) {
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
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereEstidEquals(estid);
		for (TEventTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEveothernameEquals'
	 * 
	 * @param eveothername
	 * @throws Exception
	 */
	public static void findWhereEveothernameEquals(String eveothername) throws Exception
	{
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereEveothernameEquals(eveothername);
		for (TEventTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEcodeEquals'
	 * 
	 * @param ecode
	 * @throws Exception
	 */
	public static void findWhereEcodeEquals(long ecode) throws Exception
	{
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereEcodeEquals(ecode);
		for (TEventTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereGeneralEquals'
	 * 
	 * @param general
	 * @throws Exception
	 */
	public static void findWhereGeneralEquals(long general) throws Exception
	{
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereGeneralEquals(general);
		for (TEventTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMajorEquals'
	 * 
	 * @param major
	 * @throws Exception
	 */
	public static void findWhereMajorEquals(String major) throws Exception
	{
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereMajorEquals(major);
		for (TEventTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMinorEquals'
	 * 
	 * @param minor
	 * @throws Exception
	 */
	public static void findWhereMinorEquals(String minor) throws Exception
	{
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereMinorEquals(minor);
		for (TEventTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOtherEquals'
	 * 
	 * @param other
	 * @throws Exception
	 */
	public static void findWhereOtherEquals(String other) throws Exception
	{
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereOtherEquals(other);
		for (TEventTypeInit dto : _result) {
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
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereDescriptionEquals(description);
		for (TEventTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereUseflagEquals'
	 * 
	 * @param useflag
	 * @throws Exception
	 */
	public static void findWhereUseflagEquals(String useflag) throws Exception
	{
		TEventTypeInitDao dao = DaoFactory.createTEventTypeInitDao();
		List<TEventTypeInit> _result = dao.findWhereUseflagEquals(useflag);
		for (TEventTypeInit dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TEventTypeInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getEtid() );
		buf.append( ", " );
		buf.append( dto.getModid() );
		buf.append( ", " );
		buf.append( dto.getEveid() );
		buf.append( ", " );
		buf.append( dto.getEstid() );
		buf.append( ", " );
		buf.append( dto.getEveothername() );
		buf.append( ", " );
		buf.append( dto.getEcode() );
		buf.append( ", " );
		buf.append( dto.getGeneral() );
		buf.append( ", " );
		buf.append( dto.getMajor() );
		buf.append( ", " );
		buf.append( dto.getMinor() );
		buf.append( ", " );
		buf.append( dto.getOther() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		buf.append( ", " );
		buf.append( dto.getUseflag() );
		System.out.println( buf.toString() );
	}

}
