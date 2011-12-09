package org.jdiameter.common.impl.app.auth;

import static org.jdiameter.api.Avp.AUTH_APPLICATION_ID;
import static org.jdiameter.api.Avp.RE_AUTH_REQUEST_TYPE;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Message;
import org.jdiameter.api.auth.events.ReAuthRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;

public class ReAuthRequestImpl extends AppRequestEventImpl implements ReAuthRequest {

  private static final long serialVersionUID = 1L;

  public ReAuthRequestImpl(Message message) {
    super(message);
  }

  public long getAuthApplicationId() throws AvpDataException {
    Avp authApplicationIdAvp = message.getAvps().getAvp(AUTH_APPLICATION_ID);
    if (authApplicationIdAvp != null) {
      return authApplicationIdAvp.getUnsigned32();
    }
    else {
      throw new AvpDataException("Avp AUTH_APPLICATION_ID not found");
    }
  }

  public int getReAuthRequestType() throws AvpDataException {
    Avp reAuthRequestTypeAvp = message.getAvps().getAvp(RE_AUTH_REQUEST_TYPE);
    if (reAuthRequestTypeAvp != null) {
      return reAuthRequestTypeAvp.getInteger32();
    }
    else {
      throw new AvpDataException("Avp RE_AUTH_REQUEST_TYPE not found");
    }
  }
}
