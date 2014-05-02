package com.ibm.siam.am.idp.authn.service;

/**
 * Match Code Service Interface
 * @author Jin Kaifeng
 * @since 2013-7-26
 */
public interface MatchCodeService {
  
  public String getMatchCode(String cardUid) throws Exception;
  
}
