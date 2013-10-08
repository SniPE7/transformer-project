package com.ibm.siam.am.idp.authn.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Match Code Service Interface
 * 
 * @author Jin Kaifeng
 * @since 2013-7-26
 */
public class MatchCodeServiceImpl implements MatchCodeService {
	private final Logger log = LoggerFactory
			.getLogger(MatchCodeServiceImpl.class);

	@Autowired
	@Qualifier("tcpConnectionPool")
	private TcpConnectionPool tcpConnectionPool;

	public String getMatchCode(String cardUid) throws Exception {
		log.debug("enter getMatchCode");
		Socket socket = null;

		try {
			tcpConnectionPool.init();
			socket = tcpConnectionPool.getConnection();

			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			PrintWriter pw = new PrintWriter(out, true);
			pw.println(cardUid);
			
			if (in.available() < 9) { // TODO 9 to 10
				Thread.sleep(2000);
			}

			if (in.available() < 9) {
				throw new IOException("can not get the match code.");
			}
			
			String info = br.readLine();

			return info;
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(e.toString());
			// throw e;
			return null;
		} finally {
			if (socket != null) {
				try {
					tcpConnectionPool.returnConnection(socket);
				} catch (Exception ex) {
				}
			}
			log.debug("exit getMatchCode");
		}
	}

	public TcpConnectionPool getTcpConnectionPool() {
		return tcpConnectionPool;
	}

	public void setTcpConnectionPool(TcpConnectionPool tcpConnectionPool) {
		this.tcpConnectionPool = tcpConnectionPool;
	}

}
