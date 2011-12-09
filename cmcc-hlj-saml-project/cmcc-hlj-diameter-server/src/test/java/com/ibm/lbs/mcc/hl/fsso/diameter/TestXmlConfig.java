package com.ibm.lbs.mcc.hl.fsso.diameter;

import static org.jdiameter.client.impl.helpers.Parameters.Assembler;

import java.io.InputStream;

import junit.framework.TestCase;

import org.jdiameter.api.Configuration;
import org.jdiameter.client.impl.helpers.Parameters;
import org.jdiameter.server.impl.helpers.XMLConfiguration;

public class TestXmlConfig extends TestCase {
	public static final String configFile = "/diameter_server.xml";

	public void test1() throws Exception {
		InputStream io = this.getClass().getResourceAsStream(configFile);
		XMLConfiguration config = new XMLConfiguration(io);

		System.out.println(config.getStringValue(Assembler.ordinal(),
				(String) Assembler.defValue()));
		log(config);
		System.out.println("======================");
		System.out.println(config.getStringValue(Parameters.SecurityRef
				.ordinal(), "*******"));

		Configuration[] s = config.getChildren(Parameters.Security.ordinal());
		log(s);

		System.out.println("======================");

		Configuration a = s[0];
		System.out.println(a.getStringValue(Parameters.SDName.ordinal(),
				(String) Parameters.SDName.defValue()));
		System.out.println(a.getStringValue(Parameters.CipherSuites.ordinal(),
				(String) Parameters.CipherSuites.defValue()));

		s = a.getChildren(Parameters.KeyData.ordinal());
		log(s);
		System.out.println(s[0].getStringValue(Parameters.KDFile.ordinal(),
				(String) Parameters.KDFile.defValue()));
	}

	private void log(Configuration[] config) {
		for (int i = 0; i < config.length; i++) {
			System.out.println("<" + i + ">---------");
			log(config[i]);
		}
	}

	private void log(Configuration config) {
		System.out.println(config);
	}
}
