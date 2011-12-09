package com.ibm.lbs.mcc.hl.fsso.diameter;

import java.io.InputStream;
import java.util.List;

import org.jdiameter.api.ApplicationAlreadyUseException;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.MutablePeerTable;
import org.jdiameter.api.Network;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.Peer;
import org.jdiameter.api.PeerState;
import org.jdiameter.api.Stack;
import org.jdiameter.api.Statistic;
import org.jdiameter.api.StatisticRecord;
import org.jdiameter.server.impl.StackImpl;
import org.jdiameter.server.impl.helpers.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author weizi
 * 
 */
public class DiameterServer {
	public static final Logger log = LoggerFactory
			.getLogger(DiameterServer.class);

	private Stack diameter;
	private Network network;

	public DiameterServer() throws Exception {
		this("/diameter_server.xml");
	}

	public DiameterServer(String configFile) throws Exception {
		this.diameter = new StackImpl();
		// create a stack shared instance
		// StackManager.registerStack(this.diameter);

		InputStream io = this.getClass().getResourceAsStream(configFile);
		XMLConfiguration config = new XMLConfiguration(io);

		diameter.init(config);

		network = diameter.unwrap(Network.class);
	}

	/**
	 * Add default NASREQ application Listener (VendorID=0,AuthAppID=1)
	 * 
	 * @param listener
	 * @throws ApplicationAlreadyUseException
	 */
	public void addAppListener(NetworkReqListener listener)
			throws ApplicationAlreadyUseException {
		addAppListener(listener, ApplicationId.createByAuthAppId(1));
	}

	public void addAppListener(NetworkReqListener listener, ApplicationId appId)
			throws ApplicationAlreadyUseException {
		network.addNetworkReqListener(listener, appId);
	}

	public void start() throws IllegalDiameterStateException,
			InternalException, InterruptedException {
		diameter.start();

		while (true) {
			Thread.sleep(60000);
			logPeers(diameter);
		}
	}

	private void logPeers(Stack diameter) throws InternalException {
		MutablePeerTable mw = diameter.unwrap(MutablePeerTable.class);
		List<Peer> peers = mw.getPeerTable();
		log.info("===============");
		for (Peer p : peers) {
			log.info("peer: {}", p);
			log.info("getIPAddresses: {}", p.getIPAddresses());
			log.info("getFirmware: {}", p.getFirmware());
			log.info("getCommonApplications: {}", p.getCommonApplications());
			log.info("getProductName: {}", p.getProductName());
			log.info("getRealmName: {}", p.getRealmName());
			log.info("getVendorId: {}", p.getVendorId());
			log.info("getUri: {}", p.getUri());
			log.info("getState: {}", p.getState(PeerState.class));
			Statistic stat = mw.getStatistic(p.getUri().getFQDN());
			for (StatisticRecord sr : stat.getRecords()) {
				log.info("stat*** {} : {}", sr.getDescription(), sr);
			}
			log.info("-----------------------");
		}
	}

	public static final void main(String[] args) throws Exception {
		SSLProxyServer tcpSSLServer = new SSLProxyServer();
		tcpSSLServer.setTargetIP("localhost");
		tcpSSLServer.setTargetPort(8081);
		tcpSSLServer.setServerPort(8082);

		tcpSSLServer.setServerProtocol("SSL");
		tcpSSLServer.setTargetProtocol("TCP");
		tcpSSLServer.setKeyStore("/certs/server_pwd_importkey.jks");
		tcpSSLServer.setKeyFilePass("importkey");
		tcpSSLServer.setKeyFilePass("importkey");

		Thread t2 = new Thread(tcpSSLServer);
		t2.start();

		// ========

		DiameterServer ds = new DiameterServer();
		ds.addAppListener(new NasreqListener());
		ds.start();

	}
}
