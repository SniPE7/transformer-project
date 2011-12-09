package org.jdiameter.server.impl.io.tcp;

import static org.jdiameter.client.impl.helpers.Parameters.SecurityRef;

import java.net.InetAddress;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.jdiameter.api.Configuration;
import org.jdiameter.client.api.io.IConnection;
import org.jdiameter.client.api.parser.IMessageParser;
import org.jdiameter.client.impl.transport.tcp.SecureClientConnection;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;

public class TLSNetWorkGuard extends NetworkGuard {

	/**
	 * 传入configuration参数，为了构建SSLContext
	 * 
	 * @param config
	 * @param inetAddress
	 * @param port
	 * @param concurrentFactory
	 * @param parser
	 * @throws Exception
	 */
	public TLSNetWorkGuard(Configuration config, InetAddress inetAddress,
			int port, IConcurrentFactory concurrentFactory,
			IMessageParser parser) throws Exception {
		super(config, inetAddress, port, concurrentFactory, parser,
				"TLSNetworkGuard");
	}

	@Override
	protected IConnection createNetworkConnection(Socket s) throws Exception {
		String sf = config.getStringValue(SecurityRef.ordinal(),
				(String) SecurityRef.defValue());
		if (StringUtils.isEmpty(sf)) {
			return super.createNetworkConnection(s);
		}
		IConnection client = new SecureClientConnection(config,
				concurrentFactory, s, parser, sf);
		return client;
	}

	@Override
	public String toString() {
		return "TLSNetworkGuard:"
				+ (serverSocket != null ? serverSocket.toString() : "closed");
	}

}
