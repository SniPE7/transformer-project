package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TCfgoidgroupNewDao;
import com.ibm.ncs.model.dto.TCfgoidgroupNew;
import com.ibm.ncs.model.exceptions.TCfgoidgroupNewDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TCfgoidgroupNewDaoExample
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
		// findWhereSNoEquals(null);
		// findWhereOidgroupnameEquals("");
		// findWhereOidvalueEquals("");
		// findWhereOidnameEquals("");
		// findWhereOidindexEquals(null);
		// findWhereOidunitEquals("");
		// findWhereNmsipEquals("");
		// findWhereTarfilenameEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TCfgoidgroupNewDao dao = DaoFactory.createTCfgoidgroupNewDao();
		List<TCfgoidgroupNew> _result = dao.findAll();
		for (TCfgoidgroupNew dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSNoEquals'
	 * 
	 * @param sNo
	 * @throws Exception
	 */
	public static void findWhereSNoEquals(Long sNo) throws Exception
	{
		TCfgoidgroupNewDao dao = DaoFactory.createTCfgoidgroupNewDao();
		List<TCfgoidgroupNew> _result = dao.findWhereSNoEquals(sNo);
		for (TCfgoidgroupNew dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidgroupnameEquals'
	 * 
	 * @param oidgroupname
	 * @throws Exception
	 */
	public static void findWhereOidgroupnameEquals(String oidgroupname) throws Exception
	{
		TCfgoidgroupNewDao dao = DaoFactory.createTCfgoidgroupNewDao();
		List<TCfgoidgroupNew> _result = dao.findWhereOidgroupnameEquals(oidgroupname);
		for (TCfgoidgroupNew dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidvalueEquals'
	 * 
	 * @param oidvalue
	 * @throws Exception
	 */
	public static void findWhereOidvalueEquals(String oidvalue) throws Exception
	{
		TCfgoidgroupNewDao dao = DaoFactory.createTCfgoidgroupNewDao();
		List<TCfgoidgroupNew> _result = dao.findWhereOidvalueEquals(oidvalue);
		for (TCfgoidgroupNew dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidnameEquals'
	 * 
	 * @param oidname
	 * @throws Exception
	 */
	public static void findWhereOidnameEquals(String oidname) throws Exception
	{
		TCfgoidgroupNewDao dao = DaoFactory.createTCfgoidgroupNewDao();
		List<TCfgoidgroupNew> _result = dao.findWhereOidnameEquals(oidname);
		for (TCfgoidgroupNew dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidindexEquals'
	 * 
	 * @param oidindex
	 * @throws Exception
	 */
	public static void findWhereOidindexEquals(Long oidindex) throws Exception
	{
		TCfgoidgroupNewDao dao = DaoFactory.createTCfgoidgroupNewDao();
		List<TCfgoidgroupNew> _result = dao.findWhereOidindexEquals(oidindex);
		for (TCfgoidgroupNew dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidunitEquals'
	 * 
	 * @param oidunit
	 * @throws Exception
	 */
	public static void findWhereOidunitEquals(String oidunit) throws Exception
	{
		TCfgoidgroupNewDao dao = DaoFactory.createTCfgoidgroupNewDao();
		List<TCfgoidgroupNew> _result = dao.findWhereOidunitEquals(oidunit);
		for (TCfgoidgroupNew dto : _result) {
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
		TCfgoidgroupNewDao dao = DaoFactory.createTCfgoidgroupNewDao();
		List<TCfgoidgroupNew> _result = dao.findWhereNmsipEquals(nmsip);
		for (TCfgoidgroupNew dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereTarfilenameEquals'
	 * 
	 * @param tarfilename
	 * @throws Exception
	 */
	public static void findWhereTarfilenameEquals(String tarfilename) throws Exception
	{
		TCfgoidgroupNewDao dao = DaoFactory.createTCfgoidgroupNewDao();
		List<TCfgoidgroupNew> _result = dao.findWhereTarfilenameEquals(tarfilename);
		for (TCfgoidgroupNew dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TCfgoidgroupNew dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getSNo() );
		buf.append( ", " );
		buf.append( dto.getOidgroupname() );
		buf.append( ", " );
		buf.append( dto.getOidvalue() );
		buf.append( ", " );
		buf.append( dto.getOidname() );
		buf.append( ", " );
		buf.append( dto.getOidindex() );
		buf.append( ", " );
		buf.append( dto.getOidunit() );
		buf.append( ", " );
		buf.append( dto.getNmsip() );
		buf.append( ", " );
		buf.append( dto.getTarfilename() );
		System.out.println( buf.toString() );
	}

}
