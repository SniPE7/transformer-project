package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TServerInfoDao;
import com.ibm.ncs.model.dto.TServerInfo;
import com.ibm.ncs.model.exceptions.TServerInfoDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TServerInfoDaoExample
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
		// findWhereNmsidEquals(0);
		// findWhereNmsipEquals("");
		// findWhereNmsnameEquals("");
		// findWhereUsernameEquals("");
		// findWherePasswordEquals("");
		// findWhereOstypeEquals("");
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TServerInfoDao dao = DaoFactory.createTServerInfoDao();
		List<TServerInfo> _result = dao.findAll();
		for (TServerInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereNmsidEquals'
	 * 
	 * @param nmsid
	 * @throws Exception
	 */
	public static void findWhereNmsidEquals(long nmsid) throws Exception
	{
		TServerInfoDao dao = DaoFactory.createTServerInfoDao();
		List<TServerInfo> _result = dao.findWhereNmsidEquals(nmsid);
		for (TServerInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereNmsipEquals'
	 * 
	 * @param nmsip
	 * @throws Exception
	 */
	public static void findWhereNmsipEquals(String nmsip) throws Exception
	{
		TServerInfoDao dao = DaoFactory.createTServerInfoDao();
		List<TServerInfo> _result = dao.findWhereNmsipEquals(nmsip);
		for (TServerInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereNmsnameEquals'
	 * 
	 * @param nmsname
	 * @throws Exception
	 */
	public static void findWhereNmsnameEquals(String nmsname) throws Exception
	{
		TServerInfoDao dao = DaoFactory.createTServerInfoDao();
		List<TServerInfo> _result = dao.findWhereNmsnameEquals(nmsname);
		for (TServerInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereUsernameEquals'
	 * 
	 * @param username
	 * @throws Exception
	 */
	public static void findWhereUsernameEquals(String username) throws Exception
	{
		TServerInfoDao dao = DaoFactory.createTServerInfoDao();
		List<TServerInfo> _result = dao.findWhereUsernameEquals(username);
		for (TServerInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePasswordEquals'
	 * 
	 * @param password
	 * @throws Exception
	 */
	public static void findWherePasswordEquals(String password) throws Exception
	{
		TServerInfoDao dao = DaoFactory.createTServerInfoDao();
		List<TServerInfo> _result = dao.findWherePasswordEquals(password);
		for (TServerInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOstypeEquals'
	 * 
	 * @param ostype
	 * @throws Exception
	 */
	public static void findWhereOstypeEquals(String ostype) throws Exception
	{
		TServerInfoDao dao = DaoFactory.createTServerInfoDao();
		List<TServerInfo> _result = dao.findWhereOstypeEquals(ostype);
		for (TServerInfo dto : _result) {
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
		TServerInfoDao dao = DaoFactory.createTServerInfoDao();
		List<TServerInfo> _result = dao.findWhereDescriptionEquals(description);
		for (TServerInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TServerInfo dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getNmsid() );
		buf.append( ", " );
		buf.append( dto.getNmsip() );
		buf.append( ", " );
		buf.append( dto.getNmsname() );
		buf.append( ", " );
		buf.append( dto.getUsername() );
		buf.append( ", " );
		buf.append( dto.getPassword() );
		buf.append( ", " );
		buf.append( dto.getOstype() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
