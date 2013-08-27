package com.ibm.siam.am.idp.authn.service;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpConnectionPool {
	private final Logger log = LoggerFactory
			.getLogger(LdapUserServiceImpl.class);

	private GenericObjectPool pool;
	private boolean isInit = false;

	private String host = "10.6.69.4";
	private int port = 10007;
	private int timeout = 50;

	private int maxActive = 1;
	private int maxIdle = 1;
	private int minIdle = 1;
	private int maxWait = 1;

	private String username;
	private String password;
	private String commKey;

	public TcpConnectionPool() {

	}

	public boolean checkConnection() {
		try {
			Socket socket = new Socket();
			SocketAddress endpoint = new InetSocketAddress(host, port);
			socket.connect(endpoint, timeout * 1000);
			socket.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public void init() {
		log.debug("enter init");
		try {
			if (isInit) {
				return;
			}
			
			log.info("Matchcode server connection pool is starting......");
			TcpPoolableObjectFactory factory = new TcpPoolableObjectFactory();
			factory.setHost(host);
			factory.setPort(port);
			factory.setTimeout(timeout);
			factory.setUsername(username);
			factory.setPassword(password);
			factory.setCommKey(commKey);

			pool = new GenericObjectPool(factory, maxActive,
					GenericObjectPool.WHEN_EXHAUSTED_BLOCK, maxWait, true, true);
			
			isInit = true;
			log.info("Init Matchcode server connection pool success.");
		} catch (Exception ex) {
			log.error("Init Matchcode server connection pool error.");
			log.error(ex.getMessage());
			log.error(ex.toString());
		}
		
		log.debug("exit destroy");
	}
	
	public void destroy() {
		log.debug("enter destroy");
		log.info("Matchcode server connection pool is closing......");
		pool.clear();
		log.debug("exit destroy");
	}

	public Socket getConnection() throws Exception {
		return (Socket) pool.borrowObject();
	}

	public void returnConnection(Socket socket) throws Exception {
		pool.returnObject(socket);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCommKey() {
		return commKey;
	}

	public void setCommKey(String commKey) {
		this.commKey = commKey;
	}

}
