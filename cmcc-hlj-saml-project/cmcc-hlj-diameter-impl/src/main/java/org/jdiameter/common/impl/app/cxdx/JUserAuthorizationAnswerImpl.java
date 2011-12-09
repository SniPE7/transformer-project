package org.jdiameter.common.impl.app.cxdx;

import org.jdiameter.api.Message;
import org.jdiameter.api.cxdx.events.JUserAuthorizationAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 * Start time:13:45:50 2009-08-17<br>
 * Project: diameter-parent<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JUserAuthorizationAnswerImpl extends AppAnswerEventImpl implements JUserAuthorizationAnswer {

  private static final long serialVersionUID = 1L;

  /**
   * 	
   * @param message
   */
  public JUserAuthorizationAnswerImpl(Message message) {
    super(message);
    message.setRequest(false);
  }

}
