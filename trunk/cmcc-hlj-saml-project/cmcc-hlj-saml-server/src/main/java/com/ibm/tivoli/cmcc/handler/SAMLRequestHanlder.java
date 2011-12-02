/**
 * 
 */
package com.ibm.tivoli.cmcc.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

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
public class SAMLRequestHanlder extends IoHandlerAdapter implements ApplicationContextAware {

  private Log log = LogFactory.getLog(SAMLRequestHanlder.class);

  private MyPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = null;

  private ApplicationContext applicationContext;

  /**
   * Set max content length, and protect memory.
   */
  private int maxContentLength = 16 * 1024;

  /**
   * 
   */
  public SAMLRequestHanlder() {
    super();
  }

  public int getMaxContentLength() {
    return maxContentLength;
  }

  public void setMaxContentLength(int maxContentLength) {
    this.maxContentLength = maxContentLength;
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
    if (msg instanceof String) {
      str = msg.toString();
      String content = (String) session.getAttribute("CONTENT");
      content = (content == null) ? "" : content;

      if (this.maxContentLength > 0 && content.length() > this.maxContentLength) {
        throw new Exception("Content is too long, given up all of content and reset content buffer, content length: [" + content.length() + "], content: ["
            + content + "]");
      }

      if (str.trim().indexOf("</SOAP-ENV:Envelope>") < 0) {
        content += str + "\n";
        session.setAttribute("CONTENT", content);
      } else {
        content += "</SOAP-ENV:Envelope>\n";
        session.setAttribute("CONTENT", content);

        RequestTypeDetectorProcessor processor = new RequestTypeDetectorProcessor();
        if (this.propertyPlaceholderConfigurer != null) {
          processor.setProperties(this.propertyPlaceholderConfigurer.getProperties());
        }
        if (processor instanceof AbstractProcessor) {
          ((AbstractProcessor) processor).setApplicationContext(this.applicationContext);
        }

        log.debug("Request received, client IP: [" + session.getRemoteAddress().toString() + "], request SAML: " + content);
        String result = processor.process(content);
        if (result != null) {
          session.write(result);
          log.debug("Send reponse, client IP: [" + session.getRemoteAddress().toString() + "], response SAML: " + result);
          // session.close();
        }

        session.setAttribute("CONTENT", "");
      }
    } else if (msg instanceof ByteBuffer) {
      ByteBuffer byteBuf = (ByteBuffer) msg;
      int position = byteBuf.position();
      int limit = byteBuf.limit();
      
      String input = new String(byteBuf.array(), position, limit, "UTF-8");
      String last = (String)session.getAttribute("CONTENT");
      String currentContext = last + input;
      
      log.debug(String.format("Input:[%s], Buf Content: [%s]", input, currentContext));
      
      boolean includeBegin = includeBegin(currentContext);
      boolean includeEnd = includeEnd(currentContext);
      if (includeBegin && includeEnd) {
        procesRequest(session, currentContext);
        session.setAttribute("CONTENT", "");
      } else if (includeBegin) {
        if (currentContext.length() < 4096) {
           session.setAttribute("CONTENT", currentContext);
        } else {
          // Clear buf
          session.setAttribute("CONTENT", "");
        }
      } else if (includeEnd) {
        procesRequest(session, currentContext);
        session.setAttribute("CONTENT", "");
      } else {
        if (currentContext.length() < 4096) {
          session.setAttribute("CONTENT", currentContext);
       } else {
         // Clear buf
         session.setAttribute("CONTENT", "");
       }
      }      
    }
  }

  private boolean includeEnd(String input) {
    return (input.trim().indexOf("</SOAP-ENV:Envelope>") >= 0) || (input.trim().indexOf("</PasswordReset>") >= 0);
  }

  private boolean includeBegin(String input) {
    return input.trim().startsWith("<SOAP-ENV:Envelope") || input.trim().startsWith("<?xml");
  }

  private void procesRequest(IoSession session, String content) throws IOException, Exception, UnsupportedEncodingException, CharacterCodingException {
    RequestTypeDetectorProcessor processor = new RequestTypeDetectorProcessor();
    if (this.propertyPlaceholderConfigurer != null) {
      processor.setProperties(this.propertyPlaceholderConfigurer.getProperties());
    }
    if (processor instanceof AbstractProcessor) {
      ((AbstractProcessor) processor).setApplicationContext(this.applicationContext);
    }

    content = content.trim();
    log.debug("Request received, client IP: [" + session.getRemoteAddress().toString() + "], request SAML: " + content);
    String result = processor.process(content);

    Charset ch = Charset.forName("utf-8");
    CharsetEncoder encoder = ch.newEncoder();
    if (result == null) {
      log.error("invalidate SAML message, fail to parse: " + content);
      ByteBuffer out = ByteBuffer.allocate(1);
      out.put((byte)0);
      session.write(out);
    } else {

      byte[] bs = result.getBytes("UTF-8");
      ByteBuffer out = ByteBuffer.allocate(bs.length + 1);
      out.putString(result, encoder);
      out.flip();
      session.write(out);
      log.debug("Send reponse, client IP: [" + session.getRemoteAddress().toString() + "], response SAML: " + result);
    }
    // session.close();
  }

  public void sessionCreated(IoSession session) throws Exception {
    log.info("SAML session created, client IP: [" + session.getRemoteAddress().toString() + "]");

    if (session.getTransportType() == TransportType.SOCKET)
      ((SocketSessionConfig) session.getConfig()).setReceiveBufferSize(20 * 1024);

    session.setIdleTime(IdleStatus.BOTH_IDLE, 10);
    
    session.setAttribute("CONTENT", "");
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
