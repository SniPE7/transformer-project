package com.ibm.siam.am.idp.authn.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Match Code Service Interface
 * 
 * @author Jin Kaifeng
 * @since 2013-7-26
 */
public class MatchCodeServiceImpl implements MatchCodeService {
	private String server = "10.6.69.4";
	private int port = 10007;

	public String getMatchCode(String cardUid) {
		Socket socket = null;

		try {
			socket = new Socket(server, port);
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			PrintWriter pw = new PrintWriter(out, true);

			pw.println(cardUid);

			String info = br.readLine();
			
			socket.close();
			
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}

		return null;
	}

	public static void main(String args[]) {
		MatchCodeServiceImpl mc = new MatchCodeServiceImpl();
		String matchCode = mc.getMatchCode("9E70DFCAFB");
		System.out.println(matchCode);
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
