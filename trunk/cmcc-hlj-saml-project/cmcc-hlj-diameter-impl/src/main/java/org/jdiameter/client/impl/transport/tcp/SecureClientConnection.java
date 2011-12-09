package org.jdiameter.client.impl.transport.tcp;

import static org.jdiameter.client.impl.helpers.Parameters.*;
import static org.jdiameter.client.impl.helpers.Parameters.KDManager;
import static org.jdiameter.client.impl.helpers.Parameters.KDPwd;
import static org.jdiameter.client.impl.helpers.Parameters.KDStore;
import static org.jdiameter.client.impl.helpers.Parameters.SDName;
import static org.jdiameter.client.impl.helpers.Parameters.SDProtocol;
import static org.jdiameter.client.impl.helpers.Parameters.SDUseClientMode;
import static org.jdiameter.client.impl.helpers.Parameters.Security;
import static org.jdiameter.client.impl.helpers.Parameters.TDFile;
import static org.jdiameter.client.impl.helpers.Parameters.TDManager;
import static org.jdiameter.client.impl.helpers.Parameters.TDPwd;
import static org.jdiameter.client.impl.helpers.Parameters.TDStore;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.jdiameter.api.Configuration;
import org.jdiameter.client.api.io.IConnection;
import org.jdiameter.client.api.io.IConnectionListener;
import org.jdiameter.client.api.parser.IMessageParser;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecureClientConnection extends TCPClientConnection implements
		IConnection {
	private static final Logger log = LoggerFactory
			.getLogger(SecureClientConnection.class);

	private SecureTransportClient client;

	private Configuration sslConfig;
	private SSLContext sslContext;
	private SSLEngine sslEngine;

	protected TCPTransportClient getClient() {
		return client;
	}

	public SecureClientConnection(Configuration config,
			IConcurrentFactory concurrentFactory, InetAddress remoteAddress,
			int remotePort, InetAddress localAddress, int localPort,
			IConnectionListener listener, IMessageParser parser, String ref)
			throws Exception {
		super(parser);
		fillSecurityData(config, ref);
		this.client = new SecureTransportClient(concurrentFactory, this,
				this.sslEngine);
		client.setDestAddress(new InetSocketAddress(remoteAddress, remotePort));
		client.setOrigAddress(new InetSocketAddress(localAddress, localPort));
		listeners.add(listener);
	}

	public SecureClientConnection(Configuration config,
			IConcurrentFactory concurrentFactory, InetAddress remoteAddress,
			int remotePort, InetAddress localAddress, int localPort,
			IMessageParser parser, String ref) throws Exception {
		super(parser);
		fillSecurityData(config, ref);
		this.client = new SecureTransportClient(concurrentFactory, this,
				this.sslEngine);
		client.setDestAddress(new InetSocketAddress(remoteAddress, remotePort));
		client.setOrigAddress(new InetSocketAddress(localAddress, localPort));
	}

	public SecureClientConnection(Configuration config,
			IConcurrentFactory concurrentFactory, Socket socket,
			IMessageParser parser, String ref) throws Exception {
		super(parser);
		fillSecurityData(config, ref);
		this.client = new SecureTransportClient(concurrentFactory, this,
				this.sslEngine);
		this.client.initialize(socket);
		this.client.start();
	}

	private void fillSecurityData(Configuration config, String ref)
			throws Exception {
		if (ref == null || ref.isEmpty()) {
			log.debug("security ref empty!");
			// ref = "HLJ_SSL";
			return;
		} else {
			log.debug("security ref: {}", ref);
		}
		Configuration sd[] = config.getChildren(Security.ordinal());
		for (Configuration i : sd) {
			if (i.getStringValue(SDName.ordinal(), "").equals(ref)) {
				sslConfig = i;
				break;
			}
		}
		if (sslConfig == null)
			throw new Exception("Incorrect reference to secutity data : " + ref);
		this.sslContext = getSSLContext(sslConfig);
		this.sslEngine = this.sslContext.createSSLEngine();
		this.sslEngine.setUseClientMode(sslConfig
				.getBooleanValue(SDUseClientMode.ordinal(),
						(Boolean) SDUseClientMode.defValue()));
		this.sslEngine.setNeedClientAuth(true);
		// TODO move parameter NeedClientAuth to config file
	}

	private SSLContext getSSLContext(Configuration sslConfig) throws Exception {
		SSLContext ctx = SSLContext.getInstance(sslConfig.getStringValue(
				SDProtocol.ordinal(), "TLS"));
		//
		Configuration keyData = sslConfig.getChildren(KeyData.ordinal())[0];
		KeyManagerFactory keyManagerFactory = KeyManagerFactory
				.getInstance(keyData.getStringValue(KDManager.ordinal(), ""));
		KeyStore keyStore = KeyStore.getInstance(keyData.getStringValue(KDStore
				.ordinal(), ""));
		char[] key = keyData.getStringValue(KDPwd.ordinal(), "").toCharArray();
		keyStore.load(new FileInputStream(keyData.getStringValue(KDFile
				.ordinal(), "")), key);
		keyManagerFactory.init(keyStore, key);
		KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
		//
		Configuration trustData = sslConfig.getChildren(TrustData.ordinal())[0];
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(trustData.getStringValue(TDManager.ordinal(), ""));
		KeyStore trustKeyStore = KeyStore.getInstance(trustData.getStringValue(
				TDStore.ordinal(), ""));
		char[] trustKey = trustData.getStringValue(TDPwd.ordinal(), "")
				.toCharArray();
		trustKeyStore.load(new FileInputStream(trustData.getStringValue(TDFile
				.ordinal(), "")), trustKey);
		trustManagerFactory.init(trustKeyStore);
		TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
		//
		ctx.init(keyManagers, trustManagers, null);
		return ctx;
	}

}
