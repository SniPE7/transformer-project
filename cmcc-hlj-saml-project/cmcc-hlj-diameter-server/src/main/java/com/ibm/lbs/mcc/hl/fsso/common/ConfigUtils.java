package com.ibm.lbs.mcc.hl.fsso.common;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtils {
	public static final Logger log = LoggerFactory.getLogger(ConfigUtils.class);

	public static final String Key_ResultCode = "ResultCode";
	public static final String Key_AuthTimes = "AuthTimes";
	public static final String Key_AuthThreshold = "AuthThreshold";
	public static final String Key_NickName = "NickName";
	/**
	 * ͨ��֤����/����������
	 */
	public static final String Key_UserPassword = "userPassword";
	public static final String Key_UserStatus = "erhljmccStatus";

  public static final String Key_ServicePassword = "erhljmccServiceCode";
	

	private static Configuration config;
	static {
		try {
			// ConfigurationFactory factory = new ConfigurationFactory();
			config = new PropertiesConfiguration("config.properties");

		} catch (ConfigurationException e) {
			log.error("Inital config error!", e);
		}
	}

	public static Configuration getConfig() {
		return config;
	}

	public static final void main(String[] args) {
		Configuration c = ConfigUtils.getConfig();
		ConfigurationUtils.dump(c, System.out);
		System.out.println();
		System.out.println("=======================");
		System.out.println("*** fsso.boss.DefaultEndPoint: "
				+ c.getString("fsso.boss.DefaultEndPoint"));
		System.out.println("=======================");
		String key = "test.account.operate";
		System.out.println(key + ":" + c.getStringArray(key).length);
		key = "fsso.boss.TestEndPoint";
		System.out.println(key + ":" + c.getStringArray(key).length);

	}
}
