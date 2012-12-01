package com.ibm.ncs.model.factory;

import com.ibm.ncs.model.dao.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class genDaoFactory
{
	/**
	 * Method 'createTCategoryMapInitDao'
	 * 
	 * @return TCategoryMapInitDao
	 */
	public static TCategoryMapInitDao createTCategoryMapInitDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TCategoryMapInitDao) bf.getBean( "TCategoryMapInitDao" );
	}

	/**
	 * Method 'createTDeviceInfoDao'
	 * 
	 * @return TDeviceInfoDao
	 */
	public static TDeviceInfoDao createTDeviceInfoDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TDeviceInfoDao) bf.getBean( "TDeviceInfoDao" );
	}

	/**
	 * Method 'createTDeviceTypeInitDao'
	 * 
	 * @return TDeviceTypeInitDao
	 */
	public static TDeviceTypeInitDao createTDeviceTypeInitDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TDeviceTypeInitDao) bf.getBean( "TDeviceTypeInitDao" );
	}

	/**
	 * Method 'createTEventOidInitDao'
	 * 
	 * @return TEventOidInitDao
	 */
	public static TEventOidInitDao createTEventOidInitDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TEventOidInitDao) bf.getBean( "TEventOidInitDao" );
	}

	/**
	 * Method 'createTEventTypeInitDao'
	 * 
	 * @return TEventTypeInitDao
	 */
	public static TEventTypeInitDao createTEventTypeInitDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TEventTypeInitDao) bf.getBean( "TEventTypeInitDao" );
	}

	/**
	 * Method 'createTGrpNetDao'
	 * 
	 * @return TGrpNetDao
	 */
	public static TGrpNetDao createTGrpNetDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TGrpNetDao) bf.getBean( "TGrpNetDao" );
	}

	/**
	 * Method 'createTGrpOrgDao'
	 * 
	 * @return TGrpOrgDao
	 */
	public static TGrpOrgDao createTGrpOrgDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TGrpOrgDao) bf.getBean( "TGrpOrgDao" );
	}

	/**
	 * Method 'createTLineInfoDao'
	 * 
	 * @return TLineInfoDao
	 */
	public static TLineInfoDao createTLineInfoDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TLineInfoDao) bf.getBean( "TLineInfoDao" );
	}

	/**
	 * Method 'createTListIpDao'
	 * 
	 * @return TListIpDao
	 */
	public static TListIpDao createTListIpDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TListIpDao) bf.getBean( "TListIpDao" );
	}

	/**
	 * Method 'createTManufacturerInfoInitDao'
	 * 
	 * @return TManufacturerInfoInitDao
	 */
	public static TManufacturerInfoInitDao createTManufacturerInfoInitDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TManufacturerInfoInitDao) bf.getBean( "TManufacturerInfoInitDao" );
	}

	/**
	 * Method 'createTModuleInfoInitDao'
	 * 
	 * @return TModuleInfoInitDao
	 */
	public static TModuleInfoInitDao createTModuleInfoInitDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TModuleInfoInitDao) bf.getBean( "TModuleInfoInitDao" );
	}

	/**
	 * Method 'createTOidgroupDetailsInitDao'
	 * 
	 * @return TOidgroupDetailsInitDao
	 */
	public static TOidgroupDetailsInitDao createTOidgroupDetailsInitDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TOidgroupDetailsInitDao) bf.getBean( "TOidgroupDetailsInitDao" );
	}

	/**
	 * Method 'createTOidgroupInfoInitDao'
	 * 
	 * @return TOidgroupInfoInitDao
	 */
	public static TOidgroupInfoInitDao createTOidgroupInfoInitDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TOidgroupInfoInitDao) bf.getBean( "TOidgroupInfoInitDao" );
	}

	/**
	 * Method 'createTPortlineMapDao'
	 * 
	 * @return TPortlineMapDao
	 */
	public static TPortlineMapDao createTPortlineMapDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TPortlineMapDao) bf.getBean( "TPortlineMapDao" );
	}

	/**
	 * Method 'createTPortInfoDao'
	 * 
	 * @return TPortInfoDao
	 */
	public static TPortInfoDao createTPortInfoDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TPortInfoDao) bf.getBean( "TPortInfoDao" );
	}

	/**
	 * Method 'createTServerInfoDao'
	 * 
	 * @return TServerInfoDao
	 */
	public static TServerInfoDao createTServerInfoDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TServerInfoDao) bf.getBean( "TServerInfoDao" );
	}

	/**
	 * Method 'createTSvrmodMapDao'
	 * 
	 * @return TSvrmodMapDao
	 */
	public static TSvrmodMapDao createTSvrmodMapDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TSvrmodMapDao) bf.getBean( "TSvrmodMapDao" );
	}

}
