package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TPolicyDefaultDao;
import com.ibm.ncs.model.dto.TPolicyDefault;
import com.ibm.ncs.model.exceptions.TPolicyDefaultDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TPolicyDefaultDaoExample
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
		// findWhereModidEquals(0);
		// findWhereEveidEquals(0);
		// findWhereStartTimeEquals(null);
		// findWhereEndTimeEquals(null);
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
		// findWhereS1Equals(0);
		// findWhereS2Equals(0);
		// findWhereS3Equals(0);
		// findWhereS4Equals(0);
		// findWhereS5Equals(0);
		// findWhereS6Equals(0);
		// findWhereS7Equals(0);
		// findWhereE1Equals(0);
		// findWhereE2Equals(0);
		// findWhereE3Equals(0);
		// findWhereE4Equals(0);
		// findWhereE5Equals(0);
		// findWhereE6Equals(0);
		// findWhereE7Equals(0);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findAll();
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereModidEquals(modid);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereEveidEquals(eveid);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereStartTimeEquals'
	 * 
	 * @param startTime
	 * @throws Exception
	 */
	public static void findWhereStartTimeEquals(Date startTime) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereStartTimeEquals(startTime);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEndTimeEquals'
	 * 
	 * @param endTime
	 * @throws Exception
	 */
	public static void findWhereEndTimeEquals(Date endTime) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereEndTimeEquals(endTime);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereValue1Equals(value1);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereSeverity1Equals(severity1);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereFilterAEquals(filterA);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereValue2Equals(value2);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereSeverity2Equals(severity2);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereFilterBEquals(filterB);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereSeverityAEquals(severityA);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereSeverityBEquals(severityB);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereOidgroupEquals(oidgroup);
		for (TPolicyDefault dto : _result) {
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
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereOgflagEquals(ogflag);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereS1Equals'
	 * 
	 * @param s1
	 * @throws Exception
	 */
	public static void findWhereS1Equals(long s1) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereS1Equals(s1);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereS2Equals'
	 * 
	 * @param s2
	 * @throws Exception
	 */
	public static void findWhereS2Equals(long s2) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereS2Equals(s2);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereS3Equals'
	 * 
	 * @param s3
	 * @throws Exception
	 */
	public static void findWhereS3Equals(long s3) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereS3Equals(s3);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereS4Equals'
	 * 
	 * @param s4
	 * @throws Exception
	 */
	public static void findWhereS4Equals(long s4) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereS4Equals(s4);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereS5Equals'
	 * 
	 * @param s5
	 * @throws Exception
	 */
	public static void findWhereS5Equals(long s5) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereS5Equals(s5);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereS6Equals'
	 * 
	 * @param s6
	 * @throws Exception
	 */
	public static void findWhereS6Equals(long s6) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereS6Equals(s6);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereS7Equals'
	 * 
	 * @param s7
	 * @throws Exception
	 */
	public static void findWhereS7Equals(long s7) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereS7Equals(s7);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereE1Equals'
	 * 
	 * @param e1
	 * @throws Exception
	 */
	public static void findWhereE1Equals(long e1) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereE1Equals(e1);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereE2Equals'
	 * 
	 * @param e2
	 * @throws Exception
	 */
	public static void findWhereE2Equals(long e2) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereE2Equals(e2);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereE3Equals'
	 * 
	 * @param e3
	 * @throws Exception
	 */
	public static void findWhereE3Equals(long e3) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereE3Equals(e3);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereE4Equals'
	 * 
	 * @param e4
	 * @throws Exception
	 */
	public static void findWhereE4Equals(long e4) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereE4Equals(e4);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereE5Equals'
	 * 
	 * @param e5
	 * @throws Exception
	 */
	public static void findWhereE5Equals(long e5) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereE5Equals(e5);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereE6Equals'
	 * 
	 * @param e6
	 * @throws Exception
	 */
	public static void findWhereE6Equals(long e6) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereE6Equals(e6);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereE7Equals'
	 * 
	 * @param e7
	 * @throws Exception
	 */
	public static void findWhereE7Equals(long e7) throws Exception
	{
		TPolicyDefaultDao dao = DaoFactory.createTPolicyDefaultDao();
		List<TPolicyDefault> _result = dao.findWhereE7Equals(e7);
		for (TPolicyDefault dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TPolicyDefault dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getModid() );
		buf.append( ", " );
		buf.append( dto.getEveid() );
		buf.append( ", " );
		buf.append( dto.getStartTime() );
		buf.append( ", " );
		buf.append( dto.getEndTime() );
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
		buf.append( dto.getS1() );
		buf.append( ", " );
		buf.append( dto.getS2() );
		buf.append( ", " );
		buf.append( dto.getS3() );
		buf.append( ", " );
		buf.append( dto.getS4() );
		buf.append( ", " );
		buf.append( dto.getS5() );
		buf.append( ", " );
		buf.append( dto.getS6() );
		buf.append( ", " );
		buf.append( dto.getS7() );
		buf.append( ", " );
		buf.append( dto.getE1() );
		buf.append( ", " );
		buf.append( dto.getE2() );
		buf.append( ", " );
		buf.append( dto.getE3() );
		buf.append( ", " );
		buf.append( dto.getE4() );
		buf.append( ", " );
		buf.append( dto.getE5() );
		buf.append( ", " );
		buf.append( dto.getE6() );
		buf.append( ", " );
		buf.append( dto.getE7() );
		System.out.println( buf.toString() );
	}

}
