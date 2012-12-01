package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.exceptions.TPortInfoDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TPortInfoDaoExample
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
		// findWherePtidEquals(0);
		// findWhereDevidEquals(0);
		// findWhereIfindexEquals(0);
		// findWhereIfipEquals("");
		// findWhereIpdecodeIfipEquals(0);
		// findWhereIfmacEquals("");
		// findWhereIfoperstatusEquals("");
		// findWhereIfdescrEquals("");
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TPortInfoDao dao = DaoFactory.createTPortInfoDao();
		List<TPortInfo> _result = dao.findAll();
		for (TPortInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePtidEquals'
	 * 
	 * @param ptid
	 * @throws Exception
	 */
	public static void findWherePtidEquals(long ptid) throws Exception
	{
		TPortInfoDao dao = DaoFactory.createTPortInfoDao();
		List<TPortInfo> _result = dao.findWherePtidEquals(ptid);
		for (TPortInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDevidEquals'
	 * 
	 * @param devid
	 * @throws Exception
	 */
	public static void findWhereDevidEquals(long devid) throws Exception
	{
		TPortInfoDao dao = DaoFactory.createTPortInfoDao();
		List<TPortInfo> _result = dao.findWhereDevidEquals(devid);
		for (TPortInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIfindexEquals'
	 * 
	 * @param ifindex
	 * @throws Exception
	 */
	public static void findWhereIfindexEquals(long ifindex) throws Exception
	{
		TPortInfoDao dao = DaoFactory.createTPortInfoDao();
		List<TPortInfo> _result = dao.findWhereIfindexEquals(ifindex);
		for (TPortInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIfipEquals'
	 * 
	 * @param ifip
	 * @throws Exception
	 */
	public static void findWhereIfipEquals(String ifip) throws Exception
	{
		TPortInfoDao dao = DaoFactory.createTPortInfoDao();
		List<TPortInfo> _result = dao.findWhereIfipEquals(ifip);
		for (TPortInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIpdecodeIfipEquals'
	 * 
	 * @param ipdecodeIfip
	 * @throws Exception
	 */
	public static void findWhereIpdecodeIfipEquals(long ipdecodeIfip) throws Exception
	{
		TPortInfoDao dao = DaoFactory.createTPortInfoDao();
		List<TPortInfo> _result = dao.findWhereIpdecodeIfipEquals(ipdecodeIfip);
		for (TPortInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIfmacEquals'
	 * 
	 * @param ifmac
	 * @throws Exception
	 */
	public static void findWhereIfmacEquals(String ifmac) throws Exception
	{
		TPortInfoDao dao = DaoFactory.createTPortInfoDao();
		List<TPortInfo> _result = dao.findWhereIfmacEquals(ifmac);
		for (TPortInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIfoperstatusEquals'
	 * 
	 * @param ifoperstatus
	 * @throws Exception
	 */
	public static void findWhereIfoperstatusEquals(String ifoperstatus) throws Exception
	{
		TPortInfoDao dao = DaoFactory.createTPortInfoDao();
		List<TPortInfo> _result = dao.findWhereIfoperstatusEquals(ifoperstatus);
		for (TPortInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIfdescrEquals'
	 * 
	 * @param ifdescr
	 * @throws Exception
	 */
	public static void findWhereIfdescrEquals(String ifdescr) throws Exception
	{
		TPortInfoDao dao = DaoFactory.createTPortInfoDao();
		List<TPortInfo> _result = dao.findWhereIfdescrEquals(ifdescr);
		for (TPortInfo dto : _result) {
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
		TPortInfoDao dao = DaoFactory.createTPortInfoDao();
		List<TPortInfo> _result = dao.findWhereDescriptionEquals(description);
		for (TPortInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TPortInfo dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getPtid() );
		buf.append( ", " );
		buf.append( dto.getDevid() );
		buf.append( ", " );
		buf.append( dto.getIfindex() );
		buf.append( ", " );
		buf.append( dto.getIfip() );
		buf.append( ", " );
		buf.append( dto.getIpdecodeIfip() );
		buf.append( ", " );
		buf.append( dto.getIfmac() );
		buf.append( ", " );
		buf.append( dto.getIfoperstatus() );
		buf.append( ", " );
		buf.append( dto.getIfdescr() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
