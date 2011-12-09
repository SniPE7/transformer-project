package com.ibm.lbs.mcc.hl.fsso.diameter;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.Configuration;
import org.jdiameter.api.Answer;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.EventListener;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.Mode;
import org.jdiameter.api.Request;
import org.jdiameter.api.Session;
import org.jdiameter.api.SessionFactory;
import org.jdiameter.api.Stack;
import org.jdiameter.client.impl.StackImpl;
import org.jdiameter.client.impl.helpers.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.lbs.mcc.hl.fsso.common.ConfigUtils;

/**
 * Diameter client for test
 * 
 * @author weizi
 * 
 */
public class DiameterClient {
	private static final Logger log = LoggerFactory
			.getLogger(DiameterClient.class);

	private Stack diameter;
	private Session session;
	private MyEventHandler handler;

	public DiameterClient(String configFile) throws Exception {
		this.diameter = new StackImpl();
		// create a stack shared instance
		// StackManager.registerStack(this.diameter);
		handler = new MyEventHandler(this);

		InputStream io = this.getClass().getResourceAsStream(configFile);
		XMLConfiguration config = new XMLConfiguration(io);

		SessionFactory sf = diameter.init(config);
		session = sf.getNewSession();

		// logSession();
		diameter.start(Mode.ALL_PEERS, 20, TimeUnit.SECONDS);
		logSession();

	}

	public void sendTestAuth() {
		sendTestAuth("13945047866", "abc", "111111", 1);
	}

	public void sendTestAuth(String userName, String nickName, String password,
			int passwordType) {
		Request r = session.createRequest(265, ApplicationId
				.createByAuthAppId(1), "test");
		AvpSet avpSet = r.getAvps();
		avpSet.addAvp(Avp.USER_NAME, userName, true, false, true);
		avpSet.addAvp(1868 /* nickName */, nickName, true, false, true);
		avpSet.addAvp(2 /* password */, password, true, false, true);
		avpSet.addAvp(1860, passwordType, true, false, true);

		try {
			session.send(r, handler);
		} catch (Exception e) {
			log.error("Send Err ", e);
		}
	}

	public void sendTestRegister(String userName, String nickName,
			String password, int passwordType) {
		Request r = session.createRequest(265, ApplicationId
				.createByAuthAppId(1), "test");
		AvpSet avpSet = r.getAvps();
		avpSet.addAvp(Avp.USER_NAME, userName, true, false, false);
		avpSet.addAvp(1868 /* nickName */, nickName, true, false, true);
		avpSet.addAvp(2 /* password */, password, true, false, true);
		avpSet.addAvp(1860, passwordType, true, false, true);
		avpSet.addAvp(1871 /* operate */, 1, true, false, true);

		try {
			session.send(r, handler);
		} catch (Exception e) {
			log.error("Send Err ", e);
		}
	}

	public void close() throws IllegalDiameterStateException, InternalException {
		session.release();
		logSession();
		diameter.stop(10, TimeUnit.SECONDS);
		diameter.destroy();
	}

	public DiameterClient() throws Exception {
		this("/diameter_client.xml");
	}

	private void logSession() {
		log.info("================================================");
		log.info("session.getSessionId : {}", session.getSessionId());
		log.info("session.isValid: {}", session.isValid());
		log.info("session.getCreationTime: {}", session.getCreationTime());
		log.info("session.getLastAccessedTime: {}", session
				.getLastAccessedTime());
		log.info("------------------------------------------------");
	}

	public class MyEventHandler implements EventListener<Request, Answer> {
		DiameterClient dc;

		public MyEventHandler(DiameterClient diameterClient) {
			dc = diameterClient;
		}

		public void receivedSuccessMessage(Request request, Answer answer) {
			log.info("**** receivedMessage *** ");
			NasreqListener.logMessage(answer);
		}

		public void timeoutExpired(Request request) {
			log.info("**** timeoutExpired *** ");
			NasreqListener.logMessage(request);
		}

	}

	public static final void main(String[] args) throws Exception {
		DiameterClient ds = new DiameterClient();

		Configuration c = ConfigUtils.getConfig();
		String[] name = c.getStringArray("test.account.name");
		String[] password = c.getStringArray("test.account.password");
		String[] passwordType = c.getStringArray("test.account.passwordType");
		String[] operate = c.getStringArray("test.account.operate");
		String[] nickName = c.getStringArray("test.account.nickName");

		for (int i = 0; i < name.length; i++) {
			int op = Integer.parseInt(operate[i]);
			if (op == 0) {
				// 认证
				ds.sendTestAuth(name[i], nickName[i], password[i], Integer
						.parseInt(passwordType[i]));
			} else {
				// 注册通行证
				ds.sendTestRegister(name[i], nickName[i], password[i], Integer
						.parseInt(passwordType[i]));
			}
			Thread.sleep(1000);
		}
		// ds.sendTestAuth("18745825555", "121212", 1);//prod
		// ds.sendTestAuth("13945047866", "111111", 1);
		// ds.sendTestRegister("13945015858", "111111", 1);
		// ds.sendTestAuth("13613623842", "111111", 1);
		// ds.sendTestAuth("13836127297", "111111", 1);
		// ds.sendTestAuth("13945015858", "111111", 1);

		System.in.read();
		System.out.println("Shutdown in 3 seconds...");
		Thread.sleep(3000);
		ds.close();
	}
}
