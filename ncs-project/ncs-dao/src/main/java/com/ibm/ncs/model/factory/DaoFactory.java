package com.ibm.ncs.model.factory;

import com.ibm.ncs.model.dao.*;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class DaoFactory
{
	
	/**
	 * Method 'createTCfgoidgroupTmpDao'
	 * 
	 * @return TCfgoidgroupTmpDao
	 */
	public static TCfgoidgroupTmpDao createTCfgoidgroupTmpDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TCfgoidgroupTmpDao) bf.getBean( "TCfgoidgroupTmpDao" );
	}

	/**
	 * Method 'createTEventsubtypeInfoInitDao'
	 * 
	 * @return TEventsubtypeInfoInitDao
	 */
	public static TEventsubtypeInfoInitDao createTEventsubtypeInfoInitDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TEventsubtypeInfoInitDao) bf.getBean( "TEventsubtypeInfoInitDao" );
	}

	/**
	 * Method 'createTEventtypeInfoInitDao'
	 * 
	 * @return TEventtypeInfoInitDao
	 */
	public static TEventtypeInfoInitDao createTEventtypeInfoInitDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TEventtypeInfoInitDao) bf.getBean( "TEventtypeInfoInitDao" );
	}

	/**
	 * Method 'createTPolicyBaseDao'
	 * 
	 * @return TPolicyBaseDao
	 */
	public static TPolicyBaseDao createTPolicyBaseDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TPolicyBaseDao) bf.getBean( "TPolicyBaseDao" );
	}

	/**
	 * Method 'createTPolicyDefaultDao'
	 * 
	 * @return TPolicyDefaultDao
	 */
	public static TPolicyDefaultDao createTPolicyDefaultDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TPolicyDefaultDao) bf.getBean( "TPolicyDefaultDao" );
	}

	/**
	 * Method 'createTPolicyDetailsDao'
	 * 
	 * @return TPolicyDetailsDao
	 */
	public static TPolicyDetailsDao createTPolicyDetailsDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TPolicyDetailsDao) bf.getBean( "TPolicyDetailsDao" );
	}

	/**
	 * Method 'createTPolicyPeriodDao'
	 * 
	 * @return TPolicyPeriodDao
	 */
	public static TPolicyPeriodDao createTPolicyPeriodDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TPolicyPeriodDao) bf.getBean( "TPolicyPeriodDao" );
	}

	/**
	 * Method 'createTCfgoidgroupNewDao'
	 * 
	 * @return TCfgoidgroupNewDao
	 */
	public static TCfgoidgroupNewDao createTCfgoidgroupNewDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TCfgoidgroupNewDao) bf.getBean( "TCfgoidgroupNewDao" );
	}

	/**
	 * Method 'createTDevpolMapDao'
	 * 
	 * @return TDevpolMapDao
	 */
	public static TDevpolMapDao createTDevpolMapDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TDevpolMapDao) bf.getBean( "TDevpolMapDao" );
	}

	/**
	 * Method 'createTLinepolMapDao'
	 * 
	 * @return TLinepolMapDao
	 */
	public static TLinepolMapDao createTLinepolMapDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TLinepolMapDao) bf.getBean( "TLinepolMapDao" );
	}

	/**
	 * Method 'createTModgrpMapDao'
	 * 
	 * @return TModgrpMapDao
	 */
	public static TModgrpMapDao createTModgrpMapDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TModgrpMapDao) bf.getBean( "TModgrpMapDao" );
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
	 * Method 'createTTcpportpolMapDao'
	 * 
	 * @return TTcpportpolMapDao
	 */
	public static TTcpportpolMapDao createTTcpportpolMapDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans.xml") );
		return (TTcpportpolMapDao) bf.getBean( "TTcpportpolMapDao" );
	}
	
	/**
	 * -----------------------------------
	 */
	
	
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

	public static SequenceNMDao createSequenceNMDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans-policy.xml") );
		return (SequenceNMDao) bf.getBean( "SequenceNMDao" );
	}
	
	public static MaxNMSEQDao createMaxNMSEQDao()
	{
		BeanFactory bf = new XmlBeanFactory( new ClassPathResource("dao-beans-policy.xml") );
		return (MaxNMSEQDao) bf.getBean( "MaxNMSEQDao" );
	}

	
}
