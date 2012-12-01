package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.exceptions.TPolicyPeriodDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TPolicyPeriodDaoExample
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
		// findWherePpidEquals(0);
		// findWherePpnameEquals("");
		// findWhereStartTimeEquals(null);
		// findWhereEndTimeEquals(null);
		// findWhereDescriptionEquals("");
		// findWhereWorkdayEquals("");
		// findWhereEnabledEquals("");
		// findWhereDefaultflagEquals("");
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findAll();
		for (TPolicyPeriod dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePpidEquals'
	 * 
	 * @param ppid
	 * @throws Exception
	 */
	public static void findWherePpidEquals(long ppid) throws Exception
	{
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWherePpidEquals(ppid);
		for (TPolicyPeriod dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePpnameEquals'
	 * 
	 * @param ppname
	 * @throws Exception
	 */
	public static void findWherePpnameEquals(String ppname) throws Exception
	{
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWherePpnameEquals(ppname);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereStartTimeEquals(startTime);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereEndTimeEquals(endTime);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereDescriptionEquals(description);
		for (TPolicyPeriod dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereWorkdayEquals'
	 * 
	 * @param workday
	 * @throws Exception
	 */
	public static void findWhereWorkdayEquals(String workday) throws Exception
	{
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereWorkdayEquals(workday);
		for (TPolicyPeriod dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEnabledEquals'
	 * 
	 * @param enabled
	 * @throws Exception
	 */
	public static void findWhereEnabledEquals(String enabled) throws Exception
	{
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereEnabledEquals(enabled);
		for (TPolicyPeriod dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDefaultflagEquals'
	 * 
	 * @param defaultflag
	 * @throws Exception
	 */
	public static void findWhereDefaultflagEquals(String defaultflag) throws Exception
	{
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereDefaultflagEquals(defaultflag);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereS1Equals(s1);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereS2Equals(s2);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereS3Equals(s3);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereS4Equals(s4);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereS5Equals(s5);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereS6Equals(s6);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereS7Equals(s7);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereE1Equals(e1);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereE2Equals(e2);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereE3Equals(e3);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereE4Equals(e4);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereE5Equals(e5);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereE6Equals(e6);
		for (TPolicyPeriod dto : _result) {
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
		TPolicyPeriodDao dao = DaoFactory.createTPolicyPeriodDao();
		List<TPolicyPeriod> _result = dao.findWhereE7Equals(e7);
		for (TPolicyPeriod dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TPolicyPeriod dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getPpid() );
		buf.append( ", " );
		buf.append( dto.getPpname() );
		buf.append( ", " );
		buf.append( dto.getStartTime() );
		buf.append( ", " );
		buf.append( dto.getEndTime() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		buf.append( ", " );
		buf.append( dto.getWorkday() );
		buf.append( ", " );
		buf.append( dto.getEnabled() );
		buf.append( ", " );
		buf.append( dto.getDefaultflag() );
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
