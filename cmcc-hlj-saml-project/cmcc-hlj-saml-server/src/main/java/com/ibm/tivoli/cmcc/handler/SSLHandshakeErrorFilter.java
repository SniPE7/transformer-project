/**
 * 
 */
package com.ibm.tivoli.cmcc.handler;

import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoFilterAdapter;
import org.apache.mina.common.IoSession;

/**
 * Detect handshake error, and reset connection.
 * 
 */
public class SSLHandshakeErrorFilter extends IoFilterAdapter {

  private static Log log = LogFactory.getLog(SSLHandshakeErrorFilter.class);

  /**
   * Creates a new instance.
   */
  public SSLHandshakeErrorFilter() {
  }

  public void sessionCreated(NextFilter nextFilter, IoSession session) {
    nextFilter.sessionCreated(session);
  }

  public void sessionOpened(NextFilter nextFilter, IoSession session) {
    nextFilter.sessionOpened(session);
  }

  public void sessionClosed(NextFilter nextFilter, IoSession session) {
    nextFilter.sessionClosed(session);
  }

  public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) {
    nextFilter.sessionIdle(session, status);
  }

  public void exceptionCaught(NextFilter nextFilter, IoSession session, Throwable cause) {
    nextFilter.exceptionCaught(session, cause);

    if (cause instanceof SSLHandshakeException) {
      log.warn(String.format("[%s] SSLHandshake, reset connection.", session, cause.getMessage()), cause);
      log.debug(String.format("[%s] SSLHandshakeException: ", session, cause.getMessage()), cause);
      session.close();
    }
  }

  public void messageReceived(NextFilter nextFilter, IoSession session, Object message) {
    nextFilter.messageReceived(session, message);
  }

  public void messageSent(NextFilter nextFilter, IoSession session, Object message) {
    nextFilter.messageSent(session, message);
  }

  public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) {
    nextFilter.filterWrite(session, writeRequest);
  }

  public void filterClose(NextFilter nextFilter, IoSession session) throws Exception {
    nextFilter.filterClose(session);
  }

}
