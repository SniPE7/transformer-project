package com.ibm.ncs.model.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.factory.DaoFactory;

public class TDeviceInfoDaoExample
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
		// findWhereDevidEquals(0);
		// findWhereDevipEquals("");
		// findWhereIpdecodeEquals(0);
		// findWhereSysnameEquals("");
		// findWhereSysnamealiasEquals("");
		// findWhereRsnoEquals("");
		// findWhereSridEquals(0);
		// findWhereAdminEquals("");
		// findWherePhoneEquals("");
		// findWhereMridEquals(0);
		// findWhereDtidEquals(0);
		// findWhereSerialidEquals("");
		// findWhereSwversionEquals("");
		// findWhereRamsizeEquals(0);
		// findWhereRamunitEquals("");
		// findWhereNvramsizeEquals(0);
		// findWhereNvramunitEquals("");
		// findWhereFlashsizeEquals(0);
		// findWhereFlashunitEquals("");
		// findWhereFlashfilenameEquals("");
		// findWhereFlashfilesizeEquals("");
		// findWhereRcommunityEquals("");
		// findWhereWcommunityEquals("");
		// findWhereDescriptionEquals("");
		// findWhereDomainidEquals(0);
		// findWhereSnmpversionEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findAll();
		for (TDeviceInfo dto : _result) {
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
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereDevidEquals(devid);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDevipEquals'
	 * 
	 * @param devip
	 * @throws Exception
	 */
	public static void findWhereDevipEquals(String devip) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereDevipEquals(devip);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIpdecodeEquals'
	 * 
	 * @param ipdecode
	 * @throws Exception
	 */
	public static void findWhereIpdecodeEquals(long ipdecode) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereIpdecodeEquals(ipdecode);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSysnameEquals'
	 * 
	 * @param sysname
	 * @throws Exception
	 */
	public static void findWhereSysnameEquals(String sysname) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereSysnameEquals(sysname);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSysnamealiasEquals'
	 * 
	 * @param sysnamealias
	 * @throws Exception
	 */
	public static void findWhereSysnamealiasEquals(String sysnamealias) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereSysnamealiasEquals(sysnamealias);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereRsnoEquals'
	 * 
	 * @param rsno
	 * @throws Exception
	 */
	public static void findWhereRsnoEquals(String rsno) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereRsnoEquals(rsno);
		for (TDeviceInfo dto : _result) {
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
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereSridEquals(srid);
		for (TDeviceInfo dto : _result) {
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
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereAdminEquals(admin);
		for (TDeviceInfo dto : _result) {
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
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWherePhoneEquals(phone);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMridEquals'
	 * 
	 * @param mrid
	 * @throws Exception
	 */
	public static void findWhereMridEquals(long mrid) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereMridEquals(mrid);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDtidEquals'
	 * 
	 * @param dtid
	 * @throws Exception
	 */
	public static void findWhereDtidEquals(long dtid) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereDtidEquals(dtid);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSerialidEquals'
	 * 
	 * @param serialid
	 * @throws Exception
	 */
	public static void findWhereSerialidEquals(String serialid) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereSerialidEquals(serialid);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSwversionEquals'
	 * 
	 * @param swversion
	 * @throws Exception
	 */
	public static void findWhereSwversionEquals(String swversion) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereSwversionEquals(swversion);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereRamsizeEquals'
	 * 
	 * @param ramsize
	 * @throws Exception
	 */
	public static void findWhereRamsizeEquals(long ramsize) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereRamsizeEquals(ramsize);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereRamunitEquals'
	 * 
	 * @param ramunit
	 * @throws Exception
	 */
	public static void findWhereRamunitEquals(String ramunit) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereRamunitEquals(ramunit);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereNvramsizeEquals'
	 * 
	 * @param nvramsize
	 * @throws Exception
	 */
	public static void findWhereNvramsizeEquals(long nvramsize) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereNvramsizeEquals(nvramsize);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereNvramunitEquals'
	 * 
	 * @param nvramunit
	 * @throws Exception
	 */
	public static void findWhereNvramunitEquals(String nvramunit) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereNvramunitEquals(nvramunit);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFlashsizeEquals'
	 * 
	 * @param flashsize
	 * @throws Exception
	 */
	public static void findWhereFlashsizeEquals(long flashsize) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereFlashsizeEquals(flashsize);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFlashunitEquals'
	 * 
	 * @param flashunit
	 * @throws Exception
	 */
	public static void findWhereFlashunitEquals(String flashunit) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereFlashunitEquals(flashunit);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFlashfilenameEquals'
	 * 
	 * @param flashfilename
	 * @throws Exception
	 */
	public static void findWhereFlashfilenameEquals(String flashfilename) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereFlashfilenameEquals(flashfilename);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereFlashfilesizeEquals'
	 * 
	 * @param flashfilesize
	 * @throws Exception
	 */
	public static void findWhereFlashfilesizeEquals(String flashfilesize) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereFlashfilesizeEquals(flashfilesize);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereRcommunityEquals'
	 * 
	 * @param rcommunity
	 * @throws Exception
	 */
	public static void findWhereRcommunityEquals(String rcommunity) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereRcommunityEquals(rcommunity);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereWcommunityEquals'
	 * 
	 * @param wcommunity
	 * @throws Exception
	 */
	public static void findWhereWcommunityEquals(String wcommunity) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereWcommunityEquals(wcommunity);
		for (TDeviceInfo dto : _result) {
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
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereDescriptionEquals(description);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDomainidEquals'
	 * 
	 * @param domainid
	 * @throws Exception
	 */
	public static void findWhereDomainidEquals(long domainid) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereDomainidEquals(domainid);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereSnmpversionEquals'
	 * 
	 * @param snmpversion
	 * @throws Exception
	 */
	public static void findWhereSnmpversionEquals(String snmpversion) throws Exception
	{
		TDeviceInfoDao dao = DaoFactory.createTDeviceInfoDao();
		List<TDeviceInfo> _result = dao.findWhereSnmpversionEquals(snmpversion);
		for (TDeviceInfo dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TDeviceInfo dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getDevid() );
		buf.append( ", " );
		buf.append( dto.getDevip() );
		buf.append( ", " );
		buf.append( dto.getIpdecode() );
		buf.append( ", " );
		buf.append( dto.getSysname() );
		buf.append( ", " );
		buf.append( dto.getSysnamealias() );
		buf.append( ", " );
		buf.append( dto.getRsno() );
		buf.append( ", " );
		buf.append( dto.getSrid() );
		buf.append( ", " );
		buf.append( dto.getAdmin() );
		buf.append( ", " );
		buf.append( dto.getPhone() );
		buf.append( ", " );
		buf.append( dto.getMrid() );
		buf.append( ", " );
		buf.append( dto.getDtid() );
		buf.append( ", " );
		buf.append( dto.getSerialid() );
		buf.append( ", " );
		buf.append( dto.getSwversion() );
		buf.append( ", " );
		buf.append( dto.getRamsize() );
		buf.append( ", " );
		buf.append( dto.getRamunit() );
		buf.append( ", " );
		buf.append( dto.getNvramsize() );
		buf.append( ", " );
		buf.append( dto.getNvramunit() );
		buf.append( ", " );
		buf.append( dto.getFlashsize() );
		buf.append( ", " );
		buf.append( dto.getFlashunit() );
		buf.append( ", " );
		buf.append( dto.getFlashfilename() );
		buf.append( ", " );
		buf.append( dto.getFlashfilesize() );
		buf.append( ", " );
		buf.append( dto.getRcommunity() );
		buf.append( ", " );
		buf.append( dto.getWcommunity() );
		buf.append( ", " );
		buf.append( dto.getDescription() );
		buf.append( ", " );
		buf.append( dto.getDomainid() );
		buf.append( ", " );
		buf.append( dto.getSnmpversion() );
		System.out.println( buf.toString() );
	}

}
