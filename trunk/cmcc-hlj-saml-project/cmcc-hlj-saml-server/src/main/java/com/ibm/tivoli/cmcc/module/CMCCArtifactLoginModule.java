/**
 * 
 */
package com.ibm.tivoli.cmcc.module;

import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.client.QueryAttributeServiceClient;
import com.ibm.tivoli.cmcc.connector.Connector;
import com.ibm.tivoli.cmcc.connector.ConnectorManager;
import com.ibm.tivoli.cmcc.response.QueryAttributeResponse;
import com.ibm.tivoli.cmcc.server.connector.ConnectorManagerSupplier;
import com.ibm.tivoli.cmcc.service.auth.PersonDTOPrincipal;
import com.ibm.tivoli.cmcc.spi.PersonDTO;
import com.ibm.tivoli.cmcc.util.Helper;

/**
 * @author zhaodonglu
 * 
 */
public class CMCCArtifactLoginModule implements LoginModule, PrincipalAware {

  private static Log log = LogFactory.getLog(UserPasswordLoginModule.class);
  private Subject subject = new Subject();
  private CallbackHandler callbackHandler;
  private boolean debug = false;
  private boolean succeeded = false;
  private boolean commitSucceeded = false;
  private String artifactID;
  private String artifactDomain;

  private PersonDTOPrincipal userPrincipal;
  private String username = null;
  private PersonDTO personDTO = null;

  private ConnectorManagerSupplier connectorManagerSupplier;
  private QueryAttributeServiceClient queryAttributeServiceClient = null;

  /**
   * 
   */
  public CMCCArtifactLoginModule() {
    super();
  }

  /**
   * @return the callbackHandler
   */
  public CallbackHandler getCallbackHandler() {
    return callbackHandler;
  }

  /**
   * @param callbackHandler
   *          the callbackHandler to set
   */
  public void setCallbackHandler(CallbackHandler callbackHandler) {
    this.callbackHandler = callbackHandler;
  }

  /**
   * @return the debug
   */
  public boolean isDebug() {
    return debug;
  }

  /**
   * @param debug
   *          the debug to set
   */
  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cmcc.module.PrincipalAware#getPrincipal()
   */
  public Principal getPrincipal() {
    return userPrincipal;
  }

  /**
   * @return the queryAttributeServiceClient
   */
  public QueryAttributeServiceClient getQueryAttributeServiceClient() {
    return queryAttributeServiceClient;
  }

  /**
   * @param queryAttributeServiceClient
   *          the queryAttributeServiceClient to set
   */
  public void setQueryAttributeServiceClient(final QueryAttributeServiceClient queryAttributeServiceClient) {
    this.queryAttributeServiceClient = queryAttributeServiceClient;
  }

  /**
   * @return the connectorManagerSupplier
   */
  public ConnectorManagerSupplier getConnectorManagerSupplier() {
    return connectorManagerSupplier;
  }

  /**
   * @param connectorManagerSupplier the connectorManagerSupplier to set
   */
  public void setConnectorManagerSupplier(ConnectorManagerSupplier connectorManagerSupplier) {
    this.connectorManagerSupplier = connectorManagerSupplier;
  }

  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> arg2, Map<String, ?> arg3) {
    this.callbackHandler = callbackHandler;
  }

  public boolean abort() throws LoginException {
    if (succeeded == false) {
      return false;
    } else if (succeeded == true && commitSucceeded == false) {
      // login succeeded but overall authentication failed
      succeeded = false;
      artifactID = null;
      username = null;
      userPrincipal = null;
    } else {
      // overall authentication succeeded and commit succeeded,
      // but someone else's commit failed
      logout();
    }
    return true;
  }

  public boolean commit() throws LoginException {
    if (succeeded == false) {
      return false;
    } else {
      // add a Principal (authenticated identity)
      // to the Subject

      // assume the user we authenticated is the NamePrincipal
      userPrincipal = new PersonDTOPrincipal(this.username, this.personDTO);
      if (!subject.getPrincipals().contains(userPrincipal))
        subject.getPrincipals().add(userPrincipal);

      if (debug) {
        log.info("\t\t[UserPasswordLoginModule] " + "added NamePrincipal to Subject");
      }

      // in any case, clean out state
      artifactID = null;
      commitSucceeded = true;
      return true;
    }
  }

  public boolean login() throws LoginException {
    // prompt for a user name and password
    if (callbackHandler == null)
      throw new LoginException("Error: no CallbackHandler available " + "to garner authentication information from the user");

    Callback[] callbacks = new Callback[1];
    callbacks[0] = new CMCCArtifactIDCallback();

    try {
      this.callbackHandler.handle(callbacks);
      this.artifactID = ((CMCCArtifactIDCallback) callbacks[0]).getArtifactID();
      this.artifactDomain = ((CMCCArtifactIDCallback) callbacks[0]).getArtifactDomain();
      if (StringUtils.isEmpty(artifactID)) {
        succeeded = false;
        artifactID = null;
        return false;
      }
      Helper.validateArtifactID(artifactID);
    } catch (java.io.IOException e) {
      log.error(e.getMessage(), e);
      throw new LoginException(e.toString());
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      throw new LoginException("Error: " + e.getCallback().toString() + " not available to garner authentication information " + "from the user");
    }

    // print debugging information
    if (debug) {
      log.info("Resolv artifactId from cookie: " + artifactID);
    }

    // verify the artifactID/password
    boolean correct;
    try {
      correct = authenticate(artifactID, artifactDomain);
    } catch (Exception e) {
      log.error("Failure to check artifactID.", e);
      throw new LoginException(e.getMessage());
    }
    if (correct) {
      // authentication succeeded!!!
      if (debug)
        log.info("Success to verify artifactID: [" + artifactID + "]");
      succeeded = true;
      return true;
    } else {

      // authentication failed -- clean out state
      if (debug)
        log.info("Invalidate artifactID: [" + artifactID + "]");
      succeeded = false;
      artifactID = null;
      artifactDomain = null;
      return false;
    }
  }

  public boolean logout() throws LoginException {

    subject.getPrincipals().remove(userPrincipal);
    succeeded = false;
    succeeded = commitSucceeded;
    artifactID = null;
    artifactDomain = null;
    userPrincipal = null;
    username = null;
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.module.AbstractMobileUserLoginModule#authenticate(java
   * .lang.String, java.lang.String, char[])
   */
  protected boolean authenticate(String artifactID, String artifactDoamin) throws Exception {
    // TODO 根据artifactDomain来决定查询哪一个SAML服务器
    Connector connector = null;
    ConnectorManager conMgr = this.connectorManagerSupplier.getConnectorManager(artifactDomain);
    connector = conMgr.getConnector();
    QueryAttributeResponse resp = queryAttributeServiceClient.submitAndParse(connector, artifactID);
    if (resp.getStatusCode() != null && resp.getStatusCode().equalsIgnoreCase("urn:oasis:names:tc:SAML:2.0:status:Success")) {
      // Success
      this.username = resp.getAttributeByIndex(0);
      if (this.username != null && this.username.trim().length() > 0) {
        this.personDTO = new PersonDTO();
        this.personDTO.setBrand(resp.getAttributeByIndex(3));
        this.personDTO.setCommonName(resp.getAttributeByIndex(2));
        this.personDTO.setCurrentPoint(resp.getAttributeByIndex(5));
        this.personDTO.setFetionStatus(resp.getAttributeByIndex(8));
        this.personDTO.setLastName(resp.getAttributeByIndex(2));
        this.personDTO.setMail139Status(resp.getAttributeByIndex(7));
        this.personDTO.setMsisdn(resp.getAttributeByIndex(0));
        this.personDTO.setNickname(resp.getAttributeByIndex(6));
        this.personDTO.setProvince(resp.getAttributeByIndex(1));
        this.personDTO.setStatus(resp.getAttributeByIndex(4));
        this.personDTO.setUserLevel(resp.getAttributeByIndex(9));
        return true;
      }
    }
    return false;
  }

}
