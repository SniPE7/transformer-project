/**
 * 
 */
package com.ibm.tivoli.cmcc.handler;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoFilterAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.util.SessionLog;

/**
 * Logs all MINA protocol events to {@link Logger}.
 * 
 * @author The Apache Directory Project (mina-dev@directory.apache.org)
 * @version $Rev: 587373 $, $Date: 2007-10-23 11:54:05 +0900 (Tue, 23 Oct 2007)
 *          $
 * 
 * @see SessionLog
 */
public class MyLoggingFilter extends IoFilterAdapter {

  private static Log log = LogFactory.getLog(MyLoggingFilter.class);

  /**
   * Session attribute key: prefix string
   */
  public static final String PREFIX = SessionLog.PREFIX;

  /**
   * Session attribute key: {@link Logger}
   */
  public static final String LOGGER = SessionLog.LOGGER;

  /**
   * Creates a new instance.
   */
  public MyLoggingFilter() {
  }

  public void sessionCreated(NextFilter nextFilter, IoSession session) {
    SessionLog.info(session, "CREATED");
    log.debug(String.format("[%s] CREATED", session));
    nextFilter.sessionCreated(session);
  }

  public void sessionOpened(NextFilter nextFilter, IoSession session) {
    SessionLog.info(session, "OPENED");
    log.debug(String.format("SAML session OPENED, session: [%s]", session));
    nextFilter.sessionOpened(session);
  }

  public void sessionClosed(NextFilter nextFilter, IoSession session) {
    SessionLog.info(session, "CLOSED");
    log.debug(String.format("[%s] CLOSED", session));
    nextFilter.sessionClosed(session);
  }

  public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) {
    if (log.isDebugEnabled()) {
      log.debug(String.format("[%s] IDLE", session));
    }
    if (SessionLog.isInfoEnabled(session)) {
      SessionLog.info(session, "IDLE: " + status);
    }
    nextFilter.sessionIdle(session, status);
  }

  public void exceptionCaught(NextFilter nextFilter, IoSession session, Throwable cause) {
    if (log.isDebugEnabled()) {
      log.debug(String.format("[%s] EXCEPTION: ", session, cause.getMessage()), cause);
    }
    if (SessionLog.isWarnEnabled(session)) {
      SessionLog.warn(session, "EXCEPTION:", cause);
    }
    nextFilter.exceptionCaught(session, cause);
  }

  public void messageReceived(NextFilter nextFilter, IoSession session, Object message) {
    if (log.isTraceEnabled()) {
      log.trace(String.format("[%s] RECEIVED: [%s]", session, makeLineWrap(message)));
    }
    /*
    if (SessionLog.isTraceEnabled(session)) {
      SessionLog.trace(session, "RECEIVED: " + makeLineWrap(message));
    }
    */
    nextFilter.messageReceived(session, message);
  }

  public void messageSent(NextFilter nextFilter, IoSession session, Object message) {
    if (log.isTraceEnabled()) {
      log.trace(String.format("[%s] SENT: [%s]", session, makeLineWrap(message)));
    }
    /*
    if (SessionLog.isTraceEnabled(session)) {
      SessionLog.trace(session, "SENT: " + makeLineWrap(message));
    }
    */
    nextFilter.messageSent(session, message);
  }

  public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) {
    if (log.isTraceEnabled()) {
      log.trace(String.format("[%s] WRITE: [%s]", session, makeLineWrap(writeRequest)));
    }
    /*
    if (SessionLog.isTraceEnabled(session)) {
      SessionLog.trace(session, "WRITE: " + makeLineWrap(writeRequest));
    }
    */
    nextFilter.filterWrite(session, writeRequest);
  }

  public void filterClose(NextFilter nextFilter, IoSession session) throws Exception {
    if (log.isDebugEnabled()) {
      log.debug(String.format("[%s] CLOSE", session));
    }
    SessionLog.info(session, "CLOSE");
    nextFilter.filterClose(session);
  }

  private static String makeLineWrap(Object o) {
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
}
