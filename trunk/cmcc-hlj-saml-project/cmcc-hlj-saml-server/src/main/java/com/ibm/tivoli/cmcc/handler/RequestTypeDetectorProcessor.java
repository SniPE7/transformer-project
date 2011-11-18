/**
 * 
 */
package com.ibm.tivoli.cmcc.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.handler.activiate.ActivateServiceProcessor;
import com.ibm.tivoli.cmcc.handler.logout.LogoutServiceProcessor;
import com.ibm.tivoli.cmcc.handler.passwordreset.PasswordResetServiceProcessor;
import com.ibm.tivoli.cmcc.handler.query.QueryAttributeServiceProcessor;


/**
 * @author Zhao Dong Lu
 *
 */
public class RequestTypeDetectorProcessor extends AbstractProcessor implements Processor {

  private static Log log = LogFactory.getLog(RequestTypeDetectorProcessor.class);
  /**
   * 
   */
  public RequestTypeDetectorProcessor() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.server.handler.RequestProcessor#process(java.lang.String)
   */
  public String process(String in) throws Exception {
    if (in == null || in.trim().length() == 0) {
       return null;
    }
    
    Processor processor = null;
    if (in.indexOf("<samlp:ActivateRequest") > 0) {
      processor = new ActivateServiceProcessor(this.getProperties());
    } else if (in.indexOf("<samlp:AttributeQuery") > 0) {
      processor = new QueryAttributeServiceProcessor(this.getProperties());
    } else if (in.indexOf("<samlp:LogoutRequest") > 0) {
      processor = new LogoutServiceProcessor(this.getProperties());
    } else if (in.indexOf("<PasswordReset") > 0) {
      processor = new PasswordResetServiceProcessor(this.getProperties());
    } else if (in.indexOf("<SOAP-ENV:Envelope>cmcc-sso</SOAP-ENV:Envelope>") >=0 ) {
      log.debug("Get heartbeat, nothing to response.");
      return null;
    }

    if (processor != null) {
      if (processor instanceof AbstractProcessor) {
        ((AbstractProcessor)processor).setApplicationContext(this.getApplicationContext());
      }
      return processor.process(in);
    }
    
    
    return null;
  }

}
