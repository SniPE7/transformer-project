package com.ibm.siam.am.idp.authn.service;

import java.net.Socket;

import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpPoolableObjectFactory implements PoolableObjectFactory {
	private final Logger log = LoggerFactory
			.getLogger(LdapUserServiceImpl.class);

	private String host;
	private int port;
	private int timeout = 50;

	private String username;
	private String password;
	private String commKey;

	public void activateObject(Object arg0) throws Exception {
		log.debug("enter activateObject()");
		log.debug("exit activateObject()");
	}

	public void destroyObject(Object arg0) throws Exception {
		log.debug("enter destroyObject()");
		Socket socket = (Socket) arg0;
		socket.close();
		log.debug("exit destroyObject()");
	}

	public Object makeObject() throws Exception {
		log.debug("enter makeObject()");

		Socket socket = new Socket(host, port);
		
		log.debug("exit makeObject()");
		return socket;
	}

	public void passivateObject(Object arg0) throws Exception {
		log.debug("enter passivateObject()");
		log.debug("exit passivateObject()");
	}

	public boolean validateObject(Object arg0) {
		// TODO Auto-generated method stub
		log.debug("enter validateObject()");
		log.debug("exit validateObject()");
		return true;
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
