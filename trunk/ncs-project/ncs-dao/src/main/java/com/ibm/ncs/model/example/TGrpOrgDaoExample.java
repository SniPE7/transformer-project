package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TGrpOrgDao;
import com.ibm.ncs.model.dto.TGrpOrg;
import com.ibm.ncs.model.exceptions.TGrpOrgDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TGrpOrgDaoExample
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
		// findWhereOrgabbrEquals("");
		// findWhereSupidEquals(0);
		// findWhereLevelsEquals(0);
		// findWhereDescriptionEquals("");
		// findWhereUnmallocedipsetflagEquals("");
		// findWhereOrgspellEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TGrpOrgDao dao = DaoFactory.createTGrpOrgDao();
		List<TGrpOrg> _result = dao.findAll();
		for (TGrpOrg dto : _result) {
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
		TGrpOrgDao dao = DaoFactory.createTGrpOrgDao();
		List<TGrpOrg> _result = dao.findWhereGidEquals(gid);
		for (TGrpOrg dto : _result) {
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
		TGrpOrgDao dao = DaoFactory.createTGrpOrgDao();
		List<TGrpOrg> _result = dao.findWhereGnameEquals(gname);
		for (TGrpOrg dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOrgabbrEquals'
	 * 
	 * @param orgabbr
	 * @throws Exception
	 */
	public static void findWhereOrgabbrEquals(String orgabbr) throws Exception
	{
		TGrpOrgDao dao = DaoFactory.createTGrpOrgDao();
		List<TGrpOrg> _result = dao.findWhereOrgabbrEquals(orgabbr);
		for (TGrpOrg dto : _result) {
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
		TGrpOrgDao dao = DaoFactory.createTGrpOrgDao();
		List<TGrpOrg> _result = dao.findWhereSupidEquals(supid);
		for (TGrpOrg dto : _result) {
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
		TGrpOrgDao dao = DaoFactory.createTGrpOrgDao();
		List<TGrpOrg> _result = dao.findWhereLevelsEquals(levels);
		for (TGrpOrg dto : _result) {
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
		TGrpOrgDao dao = DaoFactory.createTGrpOrgDao();
		List<TGrpOrg> _result = dao.findWhereDescriptionEquals(description);
		for (TGrpOrg dto : _result) {
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
		TGrpOrgDao dao = DaoFactory.createTGrpOrgDao();
		List<TGrpOrg> _result = dao.findWhereUnmallocedipsetflagEquals(unmallocedipsetflag);
		for (TGrpOrg dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOrgspellEquals'
	 * 
	 * @param orgspell
	 * @throws Exception
	 */
	public static void findWhereOrgspellEquals(String orgspell) throws Exception
	{
		TGrpOrgDao dao = DaoFactory.createTGrpOrgDao();
		List<TGrpOrg> _result = dao.findWhereOrgspellEquals(orgspell);
		for (TGrpOrg dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TGrpOrg dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getGid() );
		buf.append( ", " );
		buf.append( dto.getGname() );
		buf.append( ", " );
		buf.append( dto.getOrgabbr() );
		buf.append( ", " );
		buf.append( dto.getSupid() );
		buf.append( ", " );
		buf.append( dto.getLevels() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		buf.append( ", " );
		buf.append( dto.getUnmallocedipsetflag() );
		buf.append( ", " );
		buf.append( dto.getOrgspell() );
		System.out.println( buf.toString() );
	}

}
