package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.exceptions.TGrpNetDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TGrpNetDaoExample
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
		// findWhereGidEquals(0);
		// findWhereGnameEquals("");
		// findWhereSupidEquals(0);
		// findWhereLevelsEquals(0);
		// findWhereDescriptionEquals("");
		// findWhereUnmallocedipsetflagEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TGrpNetDao dao = DaoFactory.createTGrpNetDao();
		List<TGrpNet> _result = dao.findAll();
		for (TGrpNet dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereGidEquals'
	 * 
	 * @param gid
	 * @throws Exception
	 */
	public static void findWhereGidEquals(long gid) throws Exception
	{
		TGrpNetDao dao = DaoFactory.createTGrpNetDao();
		List<TGrpNet> _result = dao.findWhereGidEquals(gid);
		for (TGrpNet dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereGnameEquals'
	 * 
	 * @param gname
	 * @throws Exception
	 */
	public static void findWhereGnameEquals(String gname) throws Exception
	{
		TGrpNetDao dao = DaoFactory.createTGrpNetDao();
		List<TGrpNet> _result = dao.findWhereGnameEquals(gname);
		for (TGrpNet dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSupidEquals'
	 * 
	 * @param supid
	 * @throws Exception
	 */
	public static void findWhereSupidEquals(long supid) throws Exception
	{
		TGrpNetDao dao = DaoFactory.createTGrpNetDao();
		List<TGrpNet> _result = dao.findWhereSupidEquals(supid);
		for (TGrpNet dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereLevelsEquals'
	 * 
	 * @param levels
	 * @throws Exception
	 */
	public static void findWhereLevelsEquals(long levels) throws Exception
	{
		TGrpNetDao dao = DaoFactory.createTGrpNetDao();
		List<TGrpNet> _result = dao.findWhereLevelsEquals(levels);
		for (TGrpNet dto : _result) {
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
		TGrpNetDao dao = DaoFactory.createTGrpNetDao();
		List<TGrpNet> _result = dao.findWhereDescriptionEquals(description);
		for (TGrpNet dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereUnmallocedipsetflagEquals'
	 * 
	 * @param unmallocedipsetflag
	 * @throws Exception
	 */
	public static void findWhereUnmallocedipsetflagEquals(String unmallocedipsetflag) throws Exception
	{
		TGrpNetDao dao = DaoFactory.createTGrpNetDao();
		List<TGrpNet> _result = dao.findWhereUnmallocedipsetflagEquals(unmallocedipsetflag);
		for (TGrpNet dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TGrpNet dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getGid() );
		buf.append( ", " );
		buf.append( dto.getGname() );
		buf.append( ", " );
		buf.append( dto.getSupid() );
		buf.append( ", " );
		buf.append( dto.getLevels() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		buf.append( ", " );
		buf.append( dto.getUnmallocedipsetflag() );
		System.out.println( buf.toString() );
	}

}
