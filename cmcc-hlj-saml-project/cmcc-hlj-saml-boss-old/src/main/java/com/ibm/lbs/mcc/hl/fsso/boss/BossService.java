package com.ibm.lbs.mcc.hl.fsso.boss;

import java.util.Map;

public interface BossService {
  /**
   * @param user
   * @param passwordType SP - Service Password, NP - Network password
   * @param password
   * @return
   */
  public Map<String, String> auth(String user, String passwordType, String password);

  /**
   * Query User info
   * @param targetEndPoint
   * @param user
   * @return
   */
  public Map<String, String> query(String user);

}