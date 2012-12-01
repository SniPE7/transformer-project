package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TCfgoidgroupTmpDao;
import com.ibm.ncs.model.dto.TCfgoidgroupTmp;
import com.ibm.ncs.model.exceptions.TCfgoidgroupTmpDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TCfgoidgroupTmpDaoExample
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
		TCfgoidgroupTmpDao dao = DaoFactory.createTCfgoidgroupTmpDao();
		List<TCfgoidgroupTmp> _result = dao.findAll();
		for (TCfgoidgroupTmp dto : _result) {
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
		TCfgoidgroupTmpDao dao = DaoFactory.createTCfgoidgroupTmpDao();
		List<TCfgoidgroupTmp> _result = dao.findWhereSNoEquals(sNo);
		for (TCfgoidgroupTmp dto : _result) {
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
		TCfgoidgroupTmpDao dao = DaoFactory.createTCfgoidgroupTmpDao();
		List<TCfgoidgroupTmp> _result = dao.findWhereOidgroupnameEquals(oidgroupname);
		for (TCfgoidgroupTmp dto : _result) {
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
		TCfgoidgroupTmpDao dao = DaoFactory.createTCfgoidgroupTmpDao();
		List<TCfgoidgroupTmp> _result = dao.findWhereOidvalueEquals(oidvalue);
		for (TCfgoidgroupTmp dto : _result) {
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
		TCfgoidgroupTmpDao dao = DaoFactory.createTCfgoidgroupTmpDao();
		List<TCfgoidgroupTmp> _result = dao.findWhereOidnameEquals(oidname);
		for (TCfgoidgroupTmp dto : _result) {
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
		TCfgoidgroupTmpDao dao = DaoFactory.createTCfgoidgroupTmpDao();
		List<TCfgoidgroupTmp> _result = dao.findWhereOidindexEquals(oidindex);
		for (TCfgoidgroupTmp dto : _result) {
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
		TCfgoidgroupTmpDao dao = DaoFactory.createTCfgoidgroupTmpDao();
		List<TCfgoidgroupTmp> _result = dao.findWhereOidunitEquals(oidunit);
		for (TCfgoidgroupTmp dto : _result) {
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
		TCfgoidgroupTmpDao dao = DaoFactory.createTCfgoidgroupTmpDao();
		List<TCfgoidgroupTmp> _result = dao.findWhereNmsipEquals(nmsip);
		for (TCfgoidgroupTmp dto : _result) {
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
		TCfgoidgroupTmpDao dao = DaoFactory.createTCfgoidgroupTmpDao();
		List<TCfgoidgroupTmp> _result = dao.findWhereTarfilenameEquals(tarfilename);
		for (TCfgoidgroupTmp dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TCfgoidgroupTmp dto)
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
