/**
 * 
 */
package com.ibm.tivoli.cmcc.handler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.TransportType;
import org.apache.mina.transport.socket.nio.SocketSessionConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ibm.tivoli.cmcc.server.utils.MyPropertyPlaceholderConfigurer;

/**
 * @author Zhao Dong Lu
 * 
 */
public class SSLProxyRequestHandler extends IoHandlerAdapter implements ApplicationContextAware {

  private Log log = LogFactory.getLog(SSLProxyRequestHandler.class);

  private MyPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = null;

  private ApplicationContext applicationContext;

  private String targetServer = null;
  private int targetPort;

  /**
   * 
   */
  public SSLProxyRequestHandler() {
    super();
  }

  public MyPropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
    return propertyPlaceholderConfigurer;
  }

  public void setPropertyPlaceholderConfigurer(MyPropertyPlaceholderConfigurer propertyPlaceholderConfigurer) {
    this.propertyPlaceholderConfigurer = propertyPlaceholderConfigurer;
  }

  public void exceptionCaught(IoSession session, Throwable t) throws Exception {
    log.error("Failure to process request, cause: " + t.getMessage(), t);
    // Reset content buffer
    session.setAttribute("CONTENT", "");
    // session.close();
  }

  public void messageReceived(IoSession session, Object msg) throws Exception {
    String str = null;
    if (msg instanceof ByteBuffer) {
      ByteBuffer byteBuf = (ByteBuffer) msg;
      int limit = byteBuf.limit();
      
      Socket targetSocket = (Socket)session.getAttribute("TARGET_SOCKET");
      InputStream in = targetSocket.getInputStream();
      OutputStream out = targetSocket.getOutputStream();
      
      out.write(byteBuf.array(), 0, limit);
      byte[] buf = new byte[512];
      int len = in.read(buf);
    }
  }

  public void sessionCreated(IoSession session) throws Exception {
    log.info(String.format("SSL/TLS Session created, client: [%s] -> server: [%s]", session.getRemoteAddress(), session.getLocalAddress()));

    if (session.getTransportType() == TransportType.SOCKET)
      ((SocketSessionConfig) session.getConfig()).setReceiveBufferSize(20 * 1024);

    session.setIdleTime(IdleStatus.BOTH_IDLE, 10);
    
    Socket targetSocket = new Socket(this.targetServer, this.targetPort);
    session.setAttribute("TARGET_SOCKET", targetSocket);
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
