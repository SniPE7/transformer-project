/**
 * 
 */
package com.ibm.tivoli.cmcc.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.filter.LoggingFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import com.ibm.tivoli.cmcc.handler.SAMLRequestHanlder;

/**
 * @author Zhao Dong Lu
 * 
 */
public class SocketServer {

  /**
   * 
   */
  public SocketServer() {
    super();
  }

  private static final int PORT = 9123;

  public static void main(String[] args) throws IOException {
    ByteBuffer.setUseDirectBuffers(false);
    ByteBuffer.setAllocator(new SimpleByteBufferAllocator());

    IoAcceptor acceptor = new SocketAcceptor();

    SocketAcceptorConfig cfg = new SocketAcceptorConfig();
    cfg.getFilterChain().addLast("logger", new LoggingFilter());
    cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

    acceptor.bind(new InetSocketAddress(PORT), new SAMLRequestHanlder(), cfg);
    System.out.println("MINA SAML server started.");
  }
}
