/**
 * 
 */
package com.ibm.tivoli.cmcc.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import com.ibm.tivoli.cmcc.handler.MyLoggingFilter;
import com.ibm.tivoli.cmcc.handler.SAMLRequestHanlder;

/**
 * @author Zhao Dong Lu
 *
 */
public class SAMLSocketServer {
  
  private static Log log = LogFactory.getLog(SAMLSocketServer.class);
  
  private int port = 9123;
  private String charset = "UTF-8";
  private IoHandlerAdapter requestHandler = new SAMLRequestHanlder();

  private IoAcceptor acceptor;
  
  /**
   * 
   */
  public SAMLSocketServer() {
    super();
  }
  
  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public IoHandlerAdapter getRequestHandler() {
    return requestHandler;
  }

  public void setRequestHandler(IoHandlerAdapter requestHandler) {
    this.requestHandler = requestHandler;
  }

  public void start() throws IOException {
    if (this.acceptor != null) {
       log.info("CMCC SAML server is running.");  
       return;
    }
    
    ByteBuffer.setUseDirectBuffers(false);
    ByteBuffer.setAllocator(new SimpleByteBufferAllocator());

    acceptor = new SocketAcceptor();

    SocketAcceptorConfig cfg = new SocketAcceptorConfig();
    if (log.isDebugEnabled()) {
       cfg.getFilterChain().addLast("logger", new MyLoggingFilter());
    }
    
    //cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(this.charset))));
    //cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new XMLCodecFactory(Charset.forName(this.charset))));

    acceptor.bind(new InetSocketAddress(this.port), requestHandler, cfg);
    log.info("CMCC SAML server started, listening on " + this.port);
  }
  
  public void stop() throws IOException {
    if (this.acceptor != null) {
      this.acceptor.unbindAll();
      log.info("CMCC SAML server stopped");
    }
  }

}
