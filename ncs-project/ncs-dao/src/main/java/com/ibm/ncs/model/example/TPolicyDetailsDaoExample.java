package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dto.TPolicyDetails;
import com.ibm.ncs.model.exceptions.TPolicyDetailsDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TPolicyDetailsDaoExample
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
		// findWhereMpidEquals(0);
		// findWhereModidEquals(0);
		// findWhereEveidEquals(0);
		// findWherePollEquals(0);
		// findWhereValue1Equals("");
		// findWhereSeverity1Equals(0);
		// findWhereFilterAEquals("");
		// findWhereValue2Equals("");
		// findWhereSeverity2Equals(0);
		// findWhereFilterBEquals("");
		// findWhereSeverityAEquals(0);
		// findWhereSeverityBEquals(0);
		// findWhereOidgroupEquals("");
		// findWhereOgflagEquals("");
		// findWhereValue1LowEquals("");
		// findWhereValue2LowEquals("");
		// findWhereV1lSeverity1Equals(0);
		// findWhereV1lSeverityAEquals(0);
		// findWhereV2lSeverity2Equals(0);
		// findWhereV2lSeverityBEquals(0);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findAll();
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMpidEquals'
	 * 
	 * @param mpid
	 * @throws Exception
	 */
	public static void findWhereMpidEquals(long mpid) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereMpidEquals(mpid);
		for (TPolicyDetails dto : _result) {
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
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereModidEquals(modid);
		for (TPolicyDetails dto : _result) {
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
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereEveidEquals(eveid);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePollEquals'
	 * 
	 * @param poll
	 * @throws Exception
	 */
	public static void findWherePollEquals(long poll) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWherePollEquals(poll);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereValue1Equals'
	 * 
	 * @param value1
	 * @throws Exception
	 */
	public static void findWhereValue1Equals(String value1) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereValue1Equals(value1);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSeverity1Equals'
	 * 
	 * @param severity1
	 * @throws Exception
	 */
	public static void findWhereSeverity1Equals(long severity1) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereSeverity1Equals(severity1);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFilterAEquals'
	 * 
	 * @param filterA
	 * @throws Exception
	 */
	public static void findWhereFilterAEquals(String filterA) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereFilterAEquals(filterA);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereValue2Equals'
	 * 
	 * @param value2
	 * @throws Exception
	 */
	public static void findWhereValue2Equals(String value2) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereValue2Equals(value2);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSeverity2Equals'
	 * 
	 * @param severity2
	 * @throws Exception
	 */
	public static void findWhereSeverity2Equals(long severity2) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereSeverity2Equals(severity2);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFilterBEquals'
	 * 
	 * @param filterB
	 * @throws Exception
	 */
	public static void findWhereFilterBEquals(String filterB) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereFilterBEquals(filterB);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSeverityAEquals'
	 * 
	 * @param severityA
	 * @throws Exception
	 */
	public static void findWhereSeverityAEquals(long severityA) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereSeverityAEquals(severityA);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSeverityBEquals'
	 * 
	 * @param severityB
	 * @throws Exception
	 */
	public static void findWhereSeverityBEquals(long severityB) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereSeverityBEquals(severityB);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOidgroupEquals'
	 * 
	 * @param oidgroup
	 * @throws Exception
	 */
	public static void findWhereOidgroupEquals(String oidgroup) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereOidgroupEquals(oidgroup);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOgflagEquals'
	 * 
	 * @param ogflag
	 * @throws Exception
	 */
	public static void findWhereOgflagEquals(String ogflag) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereOgflagEquals(ogflag);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereValue1LowEquals'
	 * 
	 * @param value1Low
	 * @throws Exception
	 */
	public static void findWhereValue1LowEquals(String value1Low) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereValue1LowEquals(value1Low);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereValue2LowEquals'
	 * 
	 * @param value2Low
	 * @throws Exception
	 */
	public static void findWhereValue2LowEquals(String value2Low) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereValue2LowEquals(value2Low);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereV1lSeverity1Equals'
	 * 
	 * @param v1lSeverity1
	 * @throws Exception
	 */
	public static void findWhereV1lSeverity1Equals(long v1lSeverity1) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereV1lSeverity1Equals(v1lSeverity1);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereV1lSeverityAEquals'
	 * 
	 * @param v1lSeverityA
	 * @throws Exception
	 */
	public static void findWhereV1lSeverityAEquals(long v1lSeverityA) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereV1lSeverityAEquals(v1lSeverityA);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereV2lSeverity2Equals'
	 * 
	 * @param v2lSeverity2
	 * @throws Exception
	 */
	public static void findWhereV2lSeverity2Equals(long v2lSeverity2) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereV2lSeverity2Equals(v2lSeverity2);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereV2lSeverityBEquals'
	 * 
	 * @param v2lSeverityB
	 * @throws Exception
	 */
	public static void findWhereV2lSeverityBEquals(long v2lSeverityB) throws Exception
	{
		TPolicyDetailsDao dao = DaoFactory.createTPolicyDetailsDao();
		List<TPolicyDetails> _result = dao.findWhereV2lSeverityBEquals(v2lSeverityB);
		for (TPolicyDetails dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TPolicyDetails dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getMpid() );
		buf.append( ", " );
		buf.append( dto.getModid() );
		buf.append( ", " );
		buf.append( dto.getEveid() );
		buf.append( ", " );
		buf.append( dto.getPoll() );
		buf.append( ", " );
		buf.append( dto.getValue1() );
		buf.append( ", " );
		buf.append( dto.getSeverity1() );
		buf.append( ", " );
		buf.append( dto.getFilterA() );
		buf.append( ", " );
		buf.append( dto.getValue2() );
		buf.append( ", " );
		buf.append( dto.getSeverity2() );
		buf.append( ", " );
		buf.append( dto.getFilterB() );
		buf.append( ", " );
		buf.append( dto.getSeverityA() );
		buf.append( ", " );
		buf.append( dto.getSeverityB() );
		buf.append( ", " );
		buf.append( dto.getOidgroup() );
		buf.append( ", " );
		buf.append( dto.getOgflag() );
		buf.append( ", " );
		buf.append( dto.getValue1Low() );
		buf.append( ", " );
		buf.append( dto.getValue2Low() );
		buf.append( ", " );
		buf.append( dto.getV1lSeverity1() );
		buf.append( ", " );
		buf.append( dto.getV1lSeverityA() );
		buf.append( ", " );
		buf.append( dto.getV2lSeverity2() );
		buf.append( ", " );
		buf.append( dto.getV2lSeverityB() );
		System.out.println( buf.toString() );
	}

}
