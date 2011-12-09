package com.ibm.lbs.mcc.hl.fsso.boss;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.lbs.mcc.hl.fsso.common.ConfigUtils;
import com.sitech.esb.txdows.SLoginWebQryWSStub;
import com.sitech.esb.txdows.SLoginWebQryWSStub.CallServiceResponse;
import com.sitech.esb.txdows.SLoginWebQryWSStub.SrvReturnBean;
import com.sitech.esb.txdows.SLoginWebQryWSStub.TxdoBuf;

public class Boss {
	public static final Logger log = LoggerFactory.getLogger(Boss.class);

	public static final String ProductEndPoint = ConfigUtils.getConfig()
			.getString("fsso.boss.ProductEndPoint",
					"http://10.110.0.100:51000/esbWS/services/sLoginWebQryWS");
	public static final String TestEndPoint = ConfigUtils.getConfig()
			.getString("fsso.boss.TestEndPoint",
					"http://10.110.0.206:30005/esbWS/services/sLoginWebQryWS");
	public static final String DefaultEndPoint = ConfigUtils.getConfig()
			.getString("fsso.boss.DefaultEndPoint", ProductEndPoint);

	public static final String RetCode = "RetCode";
	public static final String RetMsg = "RetMsg";
	public static final String PhoneNo = "9";
	public static final String UserName = "9";
	public static final String UserCity = "8";
	public static final String UserBrandCode = "2";
	public static final String UserStatusCode = "5";

	private static SrvReturnBean authenticate(String targetEndPoint,
			String user, String password) {
		return callService(targetEndPoint, user, password, "Y");
	}

	private static SrvReturnBean queryInfo(String targetEndPoint, String user) {
		return callService(targetEndPoint, user, "", "N");
	}

	/**
	 * 
	 * @param targetEndPoint
	 * @param user
	 * @param password
	 * @param needPwd
	 *            "Y" or "N"
	 * @return
	 */
	private static SrvReturnBean callService(String targetEndPoint,
			String user, String password, String needPwd) {

		log.info("callService:{}({}) at {}", new Object[] { user, password,
				targetEndPoint });
		try {
			SLoginWebQryWSStub stub;
			stub = new SLoginWebQryWSStub(targetEndPoint);

			SLoginWebQryWSStub.CallService callService = SLoginWebQryWSStub.CallService.class
					.newInstance();

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

	public static Map<String, String> auth(String user, String password) {
		return auth(DefaultEndPoint, user, password);
	}

	public static Map<String, String> auth(String targetEndPoint, String user,
			String password) {
		SrvReturnBean bean = authenticate(targetEndPoint, user, password);
		Map<String, String> map = convert(bean);
		log.debug("auth result:{}", map);
		return map;
	}

	public static Map<String, String> query(String user) {
		return query(DefaultEndPoint, user);
	}

	public static Map<String, String> query(String targetEndPoint, String user) {
		SrvReturnBean bean = queryInfo(targetEndPoint, user);
		Map<String, String> map = convert(bean);
		log.debug("query result:{}", map);
		return map;
	}

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
}
