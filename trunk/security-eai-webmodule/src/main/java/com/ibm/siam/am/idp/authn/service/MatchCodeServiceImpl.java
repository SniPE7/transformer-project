package com.ibm.siam.am.idp.authn.service;

import java.io.BufferedReader;
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
			.getLogger(LdapUserServiceImpl.class);

	@Autowired
	@Qualifier("tcpConnectionPool")
	private TcpConnectionPool tcpConnectionPool;

	public String getMatchCode(String cardUid) {
		Socket socket = null;

		try {
			socket = new Socket("10.6.69.4", 10007);
			
//			tcpConnectionPool = new TcpConnectionPool();
//			tcpConnectionPool.init();
//			
//			socket = tcpConnectionPool.getConnection();
			
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			PrintWriter pw = new PrintWriter(out, true);

			pw.println(cardUid);

			String info = br.readLine();

			socket.close();
//			tcpConnectionPool.returnConnection(socket);

			return info;
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(e.toString());
		} finally {
		}

		return null;
	}

	public static void main(String args[]) {
		MatchCodeServiceImpl mc = new MatchCodeServiceImpl();
		String matchCode = mc.getMatchCode("9E70DFCAFB");
		System.out.println(matchCode);
	}

	public TcpConnectionPool getTcpConnectionPool() {
		return tcpConnectionPool;
	}

	public void setTcpConnectionPool(TcpConnectionPool tcpConnectionPool) {
		this.tcpConnectionPool = tcpConnectionPool;
	}

	public Logger getLog() {
		return log;
	}

}
