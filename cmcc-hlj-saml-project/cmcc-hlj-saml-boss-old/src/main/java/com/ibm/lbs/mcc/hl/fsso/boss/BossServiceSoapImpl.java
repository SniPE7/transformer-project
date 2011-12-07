package com.ibm.lbs.mcc.hl.fsso.boss;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sitech.esb.txdows.SLoginWebQryWSStub;
import com.sitech.esb.txdows.SLoginWebQryWSStub.CallServiceResponse;
import com.sitech.esb.txdows.SLoginWebQryWSStub.SrvReturnBean;
import com.sitech.esb.txdows.SLoginWebQryWSStub.TxdoBuf;

public class BossServiceSoapImpl implements BossService {
  public static final Log log = LogFactory.getLog(BossServiceSoapImpl.class);

  /*
   * public static final String ProductEndPoint =
   * ConfigUtils.getConfig().getString("fsso.boss.ProductEndPoint",
   * "http://10.110.0.100:51000/esbWS/services/sLoginWebQryWS"); public static
   * final String TestEndPoint =
   * ConfigUtils.getConfig().getString("fsso.boss.TestEndPoint",
   * "http://10.110.0.206:30005/esbWS/services/sLoginWebQryWS"); public static
   * final String DefaultEndPoint =
   * ConfigUtils.getConfig().getString("fsso.boss.DefaultEndPoint",
   * ProductEndPoint);
   */

  public static final String RetCode = "RetCode";
  public static final String RetMsg = "RetMsg";
  public static final String PhoneNo = "9";
  public static final String UserName = "9";
  public static final String UserCity = "8";
  public static final String UserBrandCode = "2";
  public static final String UserStatusCode = "5";

  private String serviceEndPoint = "http://10.110.0.100:51000/esbWS/services/sLoginWebQryWS";

  /**
   * 
   */
  public BossServiceSoapImpl() {
    super();
  }

  /**
   * @param serviceEndPoint
   */
  public BossServiceSoapImpl(String serviceEndPoint) {
    super();
    this.serviceEndPoint = serviceEndPoint;
  }

  /**
   * @return the serviceEndPoint
   */
  public String getServiceEndPoint() {
    return serviceEndPoint;
  }

  /**
   * @param serviceEndPoint the serviceEndPoint to set
   */
  public void setServiceEndPoint(String serviceEndPoint) {
    this.serviceEndPoint = serviceEndPoint;
  }

  private static SrvReturnBean authenticate(String targetEndPoint, String user, String password) {
    return callService(targetEndPoint, user, password, "Y");
  }

  private static SrvReturnBean queryInfo(String targetEndPoint, String user) {
    return callService(targetEndPoint, user, "", "N");
  }

  /**
   * 
   * @param targetEndPoint
   * @param user MSISDN
   * @param password service password
   * @param needPwd
   *          "Y" or "N"
   * @return
   */
  private static SrvReturnBean callService(String targetEndPoint, String user, String password, String needPwd) {

    log.info(String.format("callService:%s(%s) at %s", user, password, targetEndPoint));
    try {
      SLoginWebQryWSStub stub;
      stub = new SLoginWebQryWSStub(targetEndPoint);

      SLoginWebQryWSStub.CallService callService = SLoginWebQryWSStub.CallService.class.newInstance();

      callService.addParams("0");
      callService.addParams("02");
      callService.addParams("");
      callService.addParams("");
      callService.addParams("");
      callService.addParams(user);
      callService.addParams(password);
      callService.addParams(needPwd); // need password

      CallServiceResponse resp = stub.callService(callService);
      return resp.get_return();
    } catch (Exception e) {
      log.error("Call BOSS error", e);
      return null;
    }
  }

  /**
   * @param bean
   * @return
   */
  private static Map<String, String> convert(SrvReturnBean bean) {
    if (bean == null)
      return null;
    Map<String, String> map = new LinkedHashMap<String, String>();

    map.put("EsbRetCode", String.valueOf(bean.getEsbRetCode()));
    map.put("RetCode", bean.getRetCode());
    map.put("RetMsg", bean.getRetMsg());

    TxdoBuf[] m = bean.getRetMatrix();
    if (m != null) {
      for (int i = 0; i < m.length; i++) {
        if (m[i] != null) {
          String[] buf = m[i].getBuffer();
          if (buf != null) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < buf.length; j++) {
              sb.append(buf[j]);
            }
            map.put(String.valueOf(i), sb.toString());
          }
        }
      }
    }
    return map;
  }

  /**
   * @param user
   * @param passwordType SP - Service Password, NP - Network password
   * @param password
   * @return
   */
  public Map<String, String> auth(String user, String passwordType, String password) {
    SrvReturnBean bean = authenticate(this.getServiceEndPoint(), user, password);
    Map<String, String> map = convert(bean);
    log.debug(String.format("auth result: %s", map));
    return map;
  }

  /**
   * @param targetEndPoint
   * @param user
   * @return
   */
  public Map<String, String> query(String user) {
    SrvReturnBean bean = queryInfo(this.getServiceEndPoint(), user);
    Map<String, String> map = convert(bean);
    log.debug(String.format("query result: %s", map));
    return map;
  }

}
