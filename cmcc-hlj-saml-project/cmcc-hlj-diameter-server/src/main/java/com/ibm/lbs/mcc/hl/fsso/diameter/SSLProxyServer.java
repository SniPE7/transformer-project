/**
 * 
 */
package com.ibm.lbs.mcc.hl.fsso.diameter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 * 
 */
public class SSLProxyServer implements Runnable {
	private static Log log = LogFactory.getLog(SSLProxyServer.class);

	private int serverPort = 8081;
	private String serverProtocol = "SSL";

	private String targetIP = "127.0.0.1";
	private int targetPort = 8080;
	private String targetProtocol = "TCP";

	private String keyFilePass = "importkey";
	private String keyPass = "importkey";

	private boolean stopFlag = false;

	private String keyStore = "/certs/client_pwd_importkey.jks";

	public class Throtter implements Runnable {
		private Socket source = null;
		private Socket target = null;
		private String name = null;

		/**
		 * @param source
		 * @param target
		 */
		private Throtter(String name, Socket source, Socket target) {
			super();
			this.name = name;
			this.source = source;
			this.target = target;
		}

		/**
		 * @return the source
		 */
		public Socket getSource() {
			return source;
		}

		/**
		 * @param source
		 *            the source to set
		 */
		public void setSource(Socket source) {
			this.source = source;
		}

		/**
		 * @return the target
		 */
		public Socket getTarget() {
			return target;
		}

		/**
		 * @param target
		 *            the target to set
		 */
		public void setTarget(Socket target) {
			this.target = target;
		}

		private String makeLineWrap(Object o) {
			if (o == null) {
				return null;
			}
			String s = o.toString();
			try {
				StringWriter result = new StringWriter();
				StringReader in = new StringReader(s);
				int c = in.read();
				int i = 0;
				while (c >= 0) {
					result.write(c);
					if (i != 0 && i % 128 == 0) {
						result.write('\n');
					}
					i++;
					c = in.read();
				}
				return result.toString();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			return s;
		}

		public void run() {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = this.source.getInputStream();
				out = this.target.getOutputStream();
				byte[] buf = new byte[512];
				int len = in.read(buf);
				while (len > 0) {
					log.debug(String.format(
							"[%s]: [%s] -> [%s], len: %s, [%s]", this.name,
							this.source, this.target, len, new String(buf, 0,
									len)));
					out.write(buf, 0, len);
					out.flush();
					len = in.read(buf);
				}
				in.close();
				out.close();
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						log.warn(e.getMessage(), e);
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						log.warn(e.getMessage(), e);
					}
				}
				if (source != null) {
					try {
						this.source.close();
					} catch (IOException e) {
						log.warn(e.getMessage(), e);
					}
				}
				if (target != null) {
					try {
						this.target.close();
					} catch (IOException e) {
						log.warn(e.getMessage(), e);
					}
				}
			}
		}
	}

	/**
   * 
   */
	public SSLProxyServer() {
		super();
	}

	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort
	 *            the serverPort to set
	 */
	public void setServerPort(int port) {
		this.serverPort = port;
	}

	/**
	 * @return the targetIP
	 */
	public String getTargetIP() {
		return targetIP;
	}

	/**
	 * @param targetIP
	 *            the targetIP to set
	 */
	public void setTargetIP(String targetIP) {
		this.targetIP = targetIP;
	}

	/**
	 * @return the targetPort
	 */
	public int getTargetPort() {
		return targetPort;
	}

	/**
	 * @param targetPort
	 *            the targetPort to set
	 */
	public void setTargetPort(int targetPort) {
		this.targetPort = targetPort;
	}

	/**
	 * @return the keyFilePass
	 */
	public String getKeyFilePass() {
		return keyFilePass;
	}

	/**
	 * @param keyFilePass
	 *            the keyFilePass to set
	 */
	public void setKeyFilePass(String keyFilePass) {
		this.keyFilePass = keyFilePass;
	}

	/**
	 * @return the keyPass
	 */
	public String getKeyPass() {
		return keyPass;
	}

	/**
	 * @param keyPass
	 *            the keyPass to set
	 */
	public void setKeyPass(String keyPass) {
		this.keyPass = keyPass;
	}

	/**
	 * @return the keyStore
	 */
	public String getKeyStore() {
		return keyStore;
	}

	/**
	 * @param keyStore
	 *            the keyStore to set
	 */
	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}

	/**
	 * @return the serverProtocol
	 */
	public String getServerProtocol() {
		return serverProtocol;
	}

	/**
	 * @param serverProtocol
	 *            the serverProtocol to set
	 */
	public void setServerProtocol(String serverProtocol) {
		this.serverProtocol = serverProtocol;
	}

	/**
	 * @return the targetProtocol
	 */
	public String getTargetProtocol() {
		return targetProtocol;
	}

	/**
	 * @param targetProtocol
	 *            the targetProtocol to set
	 */
	public void setTargetProtocol(String targetProtocol) {
		this.targetProtocol = targetProtocol;
	}

	public void run() {
		try {
			ServerSocket serverSocket = getServerSocket();
			log.info(String.format("Listening on [%s] ...", this.serverPort));
			while (!this.stopFlag) {
				Socket ssocket = serverSocket.accept();

				try {
					Socket tsocket = getTargetSocket();
					Thread thortterIn = new Thread(new Throtter("REQUEST ",
							ssocket, tsocket));
					Thread thortterOut = new Thread(new Throtter("RESPONSE",
							tsocket, ssocket));
					thortterIn.start();
					thortterOut.start();
				} catch (Exception e) {
					log.warn(e.getMessage(), e);
				}
			}

			// Close everything
			serverSocket.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private Socket getTargetSocket() throws UnknownHostException, IOException {
    log.info(String.format("Creating an remote %s socket: [%s, %s]", this.targetProtocol, this.targetIP, this.targetPort));
		if (this.targetProtocol == null || this.targetProtocol.equals("TCP")) {
			return this.getTargetTCPSocket();
		} else {
			return this.getTargetSecureSocket();
		}
	}

	/**
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private Socket getTargetTCPSocket() throws UnknownHostException,
			IOException {
		return new Socket(this.targetIP, this.targetPort);
	}

	/**
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private Socket getTargetSecureSocket() throws UnknownHostException,
			IOException {
		SSLContext sslc = getSSLContext(this.targetProtocol);
		Socket socket = sslc.getSocketFactory().createSocket(this.targetIP,
				this.targetPort);
		return socket;
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private ServerSocket getServerSocket() throws IOException {
		if (this.serverProtocol == null || this.serverProtocol.equals("TCP")) {
			return this.getServerTCPSocket();
		} else {
			return this.getServerSecureSocket();
		}
	}

	private ServerSocket getServerTCPSocket() throws IOException {
		log.info(String.format("Initliazing Server Socket, protocol [%s, %s] ...",
				this.serverProtocol, this.serverPort));
		return new ServerSocket(this.serverPort);
	}

	private SSLServerSocket getServerSecureSocket() throws IOException {
		SSLContext sslc = getSSLContext(this.serverProtocol);

		log.info(String.format("Initliazing Server Socket, protocol [%s, %s] ...",
				this.serverProtocol, this.serverPort));
		SSLServerSocketFactory sslssf = sslc.getServerSocketFactory();
		SSLServerSocket sslsocket = (SSLServerSocket) sslssf
				.createServerSocket(serverPort);
		return sslsocket;
	}

	/**
	 * @return
	 */
	private SSLContext getSSLContext(String protocol) {
		KeyStore ks;
		KeyManagerFactory kmf;
		SSLContext sslc = null;
		try {
			ks = KeyStore.getInstance("JKS");
			InputStream in = this.getClass().getResourceAsStream(keyStore);
			if (in == null) {
				throw new Exception(String.format(
						"Could not load key store: %s", keyStore));
			}
			ks.load(in, keyFilePass.toCharArray());
			
      String keyManagerAlgorithm;
      // "IbmX509"
      if (System.getProperty("java.vm.vendor", "").startsWith("IBM")) {
        keyManagerAlgorithm = "IbmX509";
      } else {
        keyManagerAlgorithm = "SunX509";
      }

			kmf = KeyManagerFactory.getInstance(keyManagerAlgorithm);
			kmf.init(ks, keyPass.toCharArray());
			sslc = SSLContext.getInstance(protocol);
      TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
          log.debug(String.format("Client certs;[%s], authType: [%s]", certs,
              authType));
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
          log.debug(String.format("Client certs;[%s], authType: [%s]", certs,
              authType));
        }
      } };
			sslc.init(kmf.getKeyManagers(), trustAllCerts, null);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return sslc;
	}

	public static void main(String[] args) throws Exception {
		// SSL to TCP
		SSLProxyServer tcpSSLServer = new SSLProxyServer();
		tcpSSLServer.setTargetIP("10.110.21.58");
		tcpSSLServer.setTargetPort(8081);
		tcpSSLServer.setServerPort(8443);

		tcpSSLServer.setServerProtocol("SSL");
		tcpSSLServer.setTargetProtocol("TCP");
		tcpSSLServer.setKeyStore("/certs/client_pwd_importkey.jks");
		tcpSSLServer.setKeyFilePass("importkey");
		tcpSSLServer.setKeyFilePass("importkey");

		Thread t2 = new Thread(tcpSSLServer);
		t2.start();

	}

}
