package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TPortlineMapDao;
import com.ibm.ncs.model.dto.TPortlineMap;
import com.ibm.ncs.model.exceptions.TPortlineMapDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TPortlineMapDaoExample
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
		// findWhereLeidEquals(0);
		// findWhereSridEquals(0);
		// findWhereAdminEquals("");
		// findWherePhoneEquals("");
		// findWhereSideEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TPortlineMapDao dao = DaoFactory.createTPortlineMapDao();
		List<TPortlineMap> _result = dao.findAll();
		for (TPortlineMap dto : _result) {
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
		TPortlineMapDao dao = DaoFactory.createTPortlineMapDao();
		List<TPortlineMap> _result = dao.findWherePtidEquals(ptid);
		for (TPortlineMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereLeidEquals'
	 * 
	 * @param leid
	 * @throws Exception
	 */
	public static void findWhereLeidEquals(long leid) throws Exception
	{
		TPortlineMapDao dao = DaoFactory.createTPortlineMapDao();
		List<TPortlineMap> _result = dao.findWhereLeidEquals(leid);
		for (TPortlineMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSridEquals'
	 * 
	 * @param srid
	 * @throws Exception
	 */
	public static void findWhereSridEquals(long srid) throws Exception
	{
		TPortlineMapDao dao = DaoFactory.createTPortlineMapDao();
		List<TPortlineMap> _result = dao.findWhereSridEquals(srid);
		for (TPortlineMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereAdminEquals'
	 * 
	 * @param admin
	 * @throws Exception
	 */
	public static void findWhereAdminEquals(String admin) throws Exception
	{
		TPortlineMapDao dao = DaoFactory.createTPortlineMapDao();
		List<TPortlineMap> _result = dao.findWhereAdminEquals(admin);
		for (TPortlineMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePhoneEquals'
	 * 
	 * @param phone
	 * @throws Exception
	 */
	public static void findWherePhoneEquals(String phone) throws Exception
	{
		TPortlineMapDao dao = DaoFactory.createTPortlineMapDao();
		List<TPortlineMap> _result = dao.findWherePhoneEquals(phone);
		for (TPortlineMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSideEquals'
	 * 
	 * @param side
	 * @throws Exception
	 */
	public static void findWhereSideEquals(String side) throws Exception
	{
		TPortlineMapDao dao = DaoFactory.createTPortlineMapDao();
		List<TPortlineMap> _result = dao.findWhereSideEquals(side);
		for (TPortlineMap dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TPortlineMap dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getPtid() );
		buf.append( ", " );
		buf.append( dto.getLeid() );
		buf.append( ", " );
		buf.append( dto.getSrid() );
		buf.append( ", " );
		buf.append( dto.getAdmin() );
		buf.append( ", " );
		buf.append( dto.getPhone() );
		buf.append( ", " );
		buf.append( dto.getSide() );
		System.out.println( buf.toString() );
	}

}
