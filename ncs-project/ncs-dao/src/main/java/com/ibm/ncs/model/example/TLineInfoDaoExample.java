package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TLineInfoDao;
import com.ibm.ncs.model.dto.TLineInfo;
import com.ibm.ncs.model.exceptions.TLineInfoDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TLineInfoDaoExample
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
		// findWhereLeidEquals(0);
		// findWhereLinenameEquals("");
		// findWhereLenoEquals("");
		// findWhereCategoryEquals(0);
		// findWhereBandwidthEquals(0);
		// findWhereBandwidthunitEquals("");
		// findWhereApplytimeEquals(null);
		// findWhereOpentimeEquals(null);
		// findWhereDescriptionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TLineInfoDao dao = DaoFactory.createTLineInfoDao();
		List<TLineInfo> _result = dao.findAll();
		for (TLineInfo dto : _result) {
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
		TLineInfoDao dao = DaoFactory.createTLineInfoDao();
		List<TLineInfo> _result = dao.findWhereLeidEquals(leid);
		for (TLineInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereLinenameEquals'
	 * 
	 * @param linename
	 * @throws Exception
	 */
	public static void findWhereLinenameEquals(String linename) throws Exception
	{
		TLineInfoDao dao = DaoFactory.createTLineInfoDao();
		List<TLineInfo> _result = dao.findWhereLinenameEquals(linename);
		for (TLineInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereLenoEquals'
	 * 
	 * @param leno
	 * @throws Exception
	 */
	public static void findWhereLenoEquals(String leno) throws Exception
	{
		TLineInfoDao dao = DaoFactory.createTLineInfoDao();
		List<TLineInfo> _result = dao.findWhereLenoEquals(leno);
		for (TLineInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereCategoryEquals'
	 * 
	 * @param category
	 * @throws Exception
	 */
	public static void findWhereCategoryEquals(long category) throws Exception
	{
		TLineInfoDao dao = DaoFactory.createTLineInfoDao();
		List<TLineInfo> _result = dao.findWhereCategoryEquals(category);
		for (TLineInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereBandwidthEquals'
	 * 
	 * @param bandwidth
	 * @throws Exception
	 */
	public static void findWhereBandwidthEquals(long bandwidth) throws Exception
	{
		TLineInfoDao dao = DaoFactory.createTLineInfoDao();
		List<TLineInfo> _result = dao.findWhereBandwidthEquals(bandwidth);
		for (TLineInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereBandwidthunitEquals'
	 * 
	 * @param bandwidthunit
	 * @throws Exception
	 */
	public static void findWhereBandwidthunitEquals(String bandwidthunit) throws Exception
	{
		TLineInfoDao dao = DaoFactory.createTLineInfoDao();
		List<TLineInfo> _result = dao.findWhereBandwidthunitEquals(bandwidthunit);
		for (TLineInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereApplytimeEquals'
	 * 
	 * @param applytime
	 * @throws Exception
	 */
	public static void findWhereApplytimeEquals(Date applytime) throws Exception
	{
		TLineInfoDao dao = DaoFactory.createTLineInfoDao();
		List<TLineInfo> _result = dao.findWhereApplytimeEquals(applytime);
		for (TLineInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereOpentimeEquals'
	 * 
	 * @param opentime
	 * @throws Exception
	 */
	public static void findWhereOpentimeEquals(Date opentime) throws Exception
	{
		TLineInfoDao dao = DaoFactory.createTLineInfoDao();
		List<TLineInfo> _result = dao.findWhereOpentimeEquals(opentime);
		for (TLineInfo dto : _result) {
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
		TLineInfoDao dao = DaoFactory.createTLineInfoDao();
		List<TLineInfo> _result = dao.findWhereDescriptionEquals(description);
		for (TLineInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TLineInfo dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getLeid() );
		buf.append( ", " );
		buf.append( dto.getLinename() );
		buf.append( ", " );
		buf.append( dto.getLeno() );
		buf.append( ", " );
		buf.append( dto.getCategory() );
		buf.append( ", " );
		buf.append( dto.getBandwidth() );
		buf.append( ", " );
		buf.append( dto.getBandwidthunit() );
		buf.append( ", " );
		buf.append( dto.getApplytime() );
		buf.append( ", " );
		buf.append( dto.getOpentime() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		System.out.println( buf.toString() );
	}

}
