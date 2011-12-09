package org.jdiameter.server.impl.io.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jdiameter.api.Configuration;
import org.jdiameter.client.api.io.IConnection;
import org.jdiameter.client.api.parser.IMessageParser;
import org.jdiameter.client.impl.transport.tcp.TCPClientConnection;
import org.jdiameter.common.api.concurrent.DummyConcurrentFactory;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;
import org.jdiameter.server.api.io.INetworkConnectionListener;
import org.jdiameter.server.api.io.INetworkGuard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkGuard implements INetworkGuard, Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkGuard.class);

	protected IMessageParser parser;
	protected IConcurrentFactory concurrentFactory;
	protected int port;
	protected CopyOnWriteArrayList<INetworkConnectionListener> listeners = new CopyOnWriteArrayList<INetworkConnectionListener>();
	protected boolean isWork = false;
	protected Selector selector;
	protected ServerSocket serverSocket;
	protected Configuration config;

	private Thread thread;

	@Deprecated
	public NetworkGuard(InetAddress inetAddress, int port, IMessageParser parser)
			throws Exception {
		this(inetAddress, port, null, parser);
	}

	public NetworkGuard(InetAddress inetAddress, int port,
			IConcurrentFactory concurrentFactory, IMessageParser parser)
			throws Exception {
		this(null, inetAddress, port, concurrentFactory, parser, "NetworkGuard");
	}

	protected NetworkGuard(Configuration config, InetAddress inetAddress,
			int port, IConcurrentFactory concurrentFactory,
			IMessageParser parser, String threadPrefix) throws Exception {
		this.config = config;
		this.port = port;
		this.parser = parser;
		this.concurrentFactory = concurrentFactory == null ? new DummyConcurrentFactory()
				: concurrentFactory;
		this.thread = this.concurrentFactory.getThread(threadPrefix, this);
		//
		try {
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			serverSocket = ssc.socket();
			serverSocket.bind(new InetSocketAddress(inetAddress, port));
			selector = Selector.open();
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			isWork = true;
			logger.info("Open server socket {} ", serverSocket);
			thread.start();
		} catch (Exception exc) {
			destroy();
			throw new Exception(exc);
		}
	}

	public void run() {
		try {
			while (isWork) {
				int num = selector.select();
				if (num == 0)
					continue;
				Set<SelectionKey> keys = selector.selectedKeys();
				for (SelectionKey key : keys) {
					if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
						try {
							Socket s = serverSocket.accept();
							logger.info("Open incomming connection {}", s);
							IConnection client = createNetworkConnection(s);
							for (INetworkConnectionListener listener : listeners) {
								listener.newNetworkConnection(client);
							}
						} catch (Exception e) {
							logger
									.warn("Can not create incoming connection",
											e);
						}
					}
				}
				keys.clear();
			}
		} catch (Exception exc) {
			logger.warn("Server socket stopped", exc);
		}
	}

	protected IConnection createNetworkConnection(Socket s) throws Exception {
		TCPClientConnection client = new TCPClientConnection(null,
				concurrentFactory, s, parser, null);
		return client;
	}

	public void addListener(INetworkConnectionListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void remListener(INetworkConnectionListener listener) {
		listeners.remove(listener);
	}

	@Override
	public String toString() {
		return "NetworkGuard:"
				+ (serverSocket != null ? serverSocket.toString() : "closed");
	}

	public void destroy() {
		try {
			isWork = false;
			try {
				if (thread != null) {
					thread.join(1000);
					if (thread.isAlive())
						thread.interrupt();
				}
			} catch (InterruptedException e) {
				logger.debug("Can not stop thread", e);
			}
			if (serverSocket != null) {
				serverSocket.close();
			}
			thread = null;
			serverSocket = null;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}