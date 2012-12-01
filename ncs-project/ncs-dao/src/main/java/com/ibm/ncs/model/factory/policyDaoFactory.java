package com.ibm.ncs.model.factory;

import com.ibm.ncs.model.dao.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class policyDaoFactory
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

}
