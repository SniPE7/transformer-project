package com.ibm.lbs.mcc.hl.fsso.diameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdiameter.api.Answer;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.Message;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.Request;
import org.jdiameter.api.ResultCode;
import org.jdiameter.client.impl.parser.MessageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.lbs.mcc.hl.fsso.boss.Boss;
import com.ibm.lbs.mcc.hl.fsso.common.ConfigUtils;
import com.ibm.lbs.mcc.hl.fsso.common.LDAPHelper;

/**
 * 
 * Implement Business Related Function
 * 
 * @author weizi
 * 
 */
public class NasreqListener implements NetworkReqListener {
	public static final Logger log = LoggerFactory
			.getLogger(NasreqListener.class);

	public Answer processRequest(Request request) {

		logMessage(request);

		// business process :
		Answer answer = null;
		switch (request.getCommandCode()) {
		case 265:
			answer = processCmd265(request);
			break;
		default:
			answer = request.createAnswer(ResultCode.COMMAND_UNSUPPORTED);
		}

		logMessage(answer);
		return answer;
	}

	protected static Answer processCmd265(Request request) {
		String userName = null;
		String password = null;
		String nickName = null;
		int passwordType = 0; // 1：互联网密码 2：服务密码
		int operate = 0;// 01：创建用户通行证 02：重置通行证密码

		AvpSet requestAvps = request.getAvps();
		try {
			Avp avp = requestAvps.getAvp(Avp.USER_NAME);
			if (avp != null) {
				userName = avp.getUTF8String();
			}
			avp = requestAvps.getAvp(1868); // nickName
			if (avp != null) {
				nickName = avp.getUTF8String();
			}
			avp = requestAvps.getAvp(2);// password
			if (avp != null) {
				password = avp.getOctetString();
			}
			avp = requestAvps.getAvp(1860);// password type
			if (avp != null) {
				passwordType = (int) avp.getUnsigned32();
			}
			avp = requestAvps.getAvp(1871);// operate
			if (avp != null) {
				operate = (int) avp.getUnsigned32();
			}
			log.debug(
					"name({}) password({}) type({}) operate({}) nickName({})",
					new Object[] { userName, password, passwordType, operate,
							nickName });
			if (userName == null || password == null || passwordType == 0) {
				log.error("Missing required AVPs!");
				return request.createAnswer(ResultCode.MISSING_AVP);
			}
			if (operate == 1 && nickName == null) {
				log.error("Missing required AVPs!");
				return request.createAnswer(ResultCode.MISSING_AVP);
			}
		} catch (Exception e) {
			log.error("Process CMD265 incoming data error!", e);
			return request.createAnswer(ResultCode.MISSING_AVP);
		}

		// call BOSS
		Answer answer;
		switch (operate) {
		case 0: // 认证
			if (passwordType == 1) {
			  // Network Passwd
				answer = authNetworkPwd(request, userName, password, ConfigUtils.Key_UserPassword);
			} else {
			  //ServiceCode
        answer = authNetworkPwd(request, userName, password, ConfigUtils.Key_ServicePassword);
				//answer = authServiceCode(request, userName, password);
			}
			break;
		case 1: // 创建用户通行证
			answer = register(request, userName, nickName, password);
			break;
		case 2: // 重置通行证密码
			answer = register(request, userName, nickName, password);
			break;
		default:
			log.error("Invalid AVP(operate) value:{}", operate);
			answer = request.createAnswer(ResultCode.INVALID_AVP_VALUE);
		}
		return answer;
	}

	/**
	 * 认证互联网密码
	 * 
	 * @param request
	 * @param userName
	 * @param password
	 * @return
	 */
	protected static Answer authNetworkPwd(Request request, String userName,
			String password, String passwdAttrName) {
		// Map<String, String> map = Func.auth(userName, password);
		Map<String, String> bean = new LDAPHelper().checkNetworkPassword(
				userName, password, passwdAttrName);
		if (bean == null) {
			log.info("authNetworkPwd failed! ");
			return populateAnswer(request, null);
		}
		Map<String, String> bean2 = Boss.query(userName);
		if (bean2 != null) {
			bean.putAll(bean2);
		}
		return populateAnswer(request, bean);
	}

	/**
	 * @param request
	 * @param map
	 * @return
	 */
	protected static Answer makeAnswer(Request request, Map<String, String> map) {
		Answer answer;
		if (map != null) {
			// Result-Code=4001
			// User-Name=净坛使者
			// User-RealName=猪八戒
			// User-Province=451
			// User-Brand=1
			// User-Status=1
			// Data=
			// AuthTimes=0
			// AuthThreshold=10
			// NickName=
			// FetionStatus=0
			// 139MailStatus=0

			String strValue = map.get("Result-Code");
			if (strValue == null || strValue.isEmpty()) {
				return answer = request.createAnswer(5004);
			}
			int iValue = Integer.parseInt(strValue);
			answer = request.createAnswer(iValue);
			if (iValue != ResultCode.SUCCESS) {
				return answer;
			}

			AvpSet requestAvps = request.getAvps();
			AvpSet answerAvps = answer.getAvps();
			answerAvps.addAvp(requestAvps.getAvp(Avp.USER_NAME));

			// User-RealName
			strValue = map.get("User-RealName");
			answerAvps.addAvp(1861, strValue, true, false, false);
			// User-Province : 451 hlj
			answerAvps.addAvp(1862, 451, true, false, true);
			// User-Brand
			strValue = map.get("User-Brand");
			if (strValue == null || strValue.isEmpty()) {
				answerAvps.removeAvp(Avp.RESULT_CODE);
				answerAvps.addAvp(Avp.RESULT_CODE, 5004, true, false, true);
				strValue = "0";
			}
			iValue = Integer.parseInt(strValue);
			answerAvps.addAvp(1863, iValue, true, false, true);
			// User-Status
			strValue = map.get("User-Status");
			if (strValue == null || strValue.isEmpty()) {
				answerAvps.removeAvp(Avp.RESULT_CODE);
				answerAvps.addAvp(Avp.RESULT_CODE, 5004, true, false, true);
				strValue = "0";
			}
			iValue = Integer.parseInt(strValue);
			answerAvps.addAvp(1864, iValue, true, false, true);
			// XML Data
			strValue = map.get("Data");
			answerAvps.addAvp(1865, strValue, true, false, false);
			// AuthTimes
			strValue = map.get("AuthTimes");
			if (strValue == null || strValue.isEmpty()) {
				answerAvps.removeAvp(Avp.RESULT_CODE);
				answerAvps.addAvp(Avp.RESULT_CODE, 5004, true, false, true);
				strValue = "0";
			}
			iValue = Integer.parseInt(strValue);
			answerAvps.addAvp(1866, iValue, true, false, true);
			// AuthThreshold
			strValue = map.get("AuthThreshold");
			if (strValue == null || strValue.isEmpty()) {
				answerAvps.removeAvp(Avp.RESULT_CODE);
				answerAvps.addAvp(Avp.RESULT_CODE, 5004, true, false, true);
				strValue = "5";
			}
			iValue = Integer.parseInt(strValue);
			answerAvps.addAvp(1867, iValue, true, false, true);
			// NickName
			strValue = map.get("NickName");
			answerAvps.addAvp(1868, strValue, true, false, false);
			// FetionStatus
			strValue = map.get("FetionStatus");
			if (strValue == null || strValue.isEmpty()) {
				strValue = "0";
			}
			iValue = Integer.parseInt(strValue);
			answerAvps.addAvp(1869, iValue, true, false, true);
			// 139MailStatus
			strValue = map.get("139MailStatus");
			if (strValue == null || strValue.isEmpty()) {
				strValue = "0";
			}
			iValue = Integer.parseInt(strValue);
			answerAvps.addAvp(1870, iValue, true, false, true);

			// User_level (1872) U32
			// 01 钻卡用户
			// 02 金卡用户
			// 03 银卡用户
			// 04 普通用户
			strValue = map.get("User_level");
			if (strValue == null || strValue.isEmpty()) {
				strValue = "4";
			}
			iValue = Integer.parseInt(strValue);
			answerAvps.addAvp(1872, iValue, true, false, true);
			// TODO User_level

		} else {
			answer = request.createAnswer(ResultCode.AUTHENTICATION_REJECTED);
		}
		return answer;
	}

	protected static Answer register(Request request, String userName,
			String nickName, String password) {
		// Map<String, String> map = Func.register(userName, password);
		Map<String, String> bean = new LDAPHelper().registerNetworkPassword(
				userName, nickName, password);
		if (bean == null) {
			log.info("register failed! ");
			return populateAnswer(request, null);
		}

		Map<String, String> bean2 = Boss.query(userName);
		if (bean2 != null) {
			bean.putAll(bean2);
		}
		return populateAnswer(request, bean);
	}

	protected static Answer authServiceCode(Request request, String userName,
			String password) {
		Map<String, String> bean = new LDAPHelper().query(userName);
		Map<String, String> bean2 = Boss.auth(userName, password);
		if (bean != null && bean2 != null) {
			bean.putAll(bean2);
		} else if (bean == null) {
			bean = bean2;
		}
		return populateAnswer(request, bean);
	}

	private static void populateBasicItem(Request request, Answer answer,
			Map<String, String> bean) {
		AvpSet requestAvps = request.getAvps();
		AvpSet answerAvps = answer.getAvps();
		answerAvps.addAvp(requestAvps.getAvp(Avp.USER_NAME));
		// AuthTimes
		String strValue = bean.get(ConfigUtils.Key_AuthTimes);
		int iValue = strValue == null ? 0 : Integer.valueOf(strValue);
		answerAvps.addAvp(1866, iValue, true, false, true);
		// AuthThreshold
		strValue = bean.get(ConfigUtils.Key_AuthThreshold);
		iValue = strValue == null ? 0 : Integer.valueOf(strValue);
		answerAvps.addAvp(1867, iValue, true, false, true);
		// NickName
		strValue = bean.get(ConfigUtils.Key_NickName);
		strValue = strValue == null ? "" : strValue;
		answerAvps.addAvp(1868, strValue, true, false, false);
		// User-Status
		/*
		strValue = bean.get(ConfigUtils.Key_UserStatus);
		iValue = strValue == null ? 0 : Integer.valueOf(strValue);
		answerAvps.addAvp(1864, iValue, true, false, true);
		*/
    strValue = bean.get("erhljmccstatus");
    answerAvps.addAvp(1864, saftyConvert2Int(strValue, 1), true, false, true);
	}

/*	12:23.348 INFO  [FSM-aaa://localhost:8082-0] - **** receivedMessage *** 
	  12:23.349 INFO  [FSM-aaa://localhost:8082-0] - ===============================
	  12:23.350 INFO  [FSM-aaa://localhost:8082-0] - Answer Message : MessageImpl{commandCode=265, flags=0}
	  12:23.350 INFO  [FSM-aaa://localhost:8082-0] - getVersion:??1??
	  12:23.350 INFO  [FSM-aaa://localhost:8082-0] - getFlags:??0??
	  12:23.350 INFO  [FSM-aaa://localhost:8082-0] - getCommandCode:??265??
	  12:23.350 INFO  [FSM-aaa://localhost:8082-0] - getHeaderApplicationId:??1??
	  12:23.350 INFO  [FSM-aaa://localhost:8082-0] - getSingleApplicationId:??AppId [Vendor-Id:0; Auth-Application-Id:1; Acct-Application-Id:0]??
	  12:23.350 INFO  [FSM-aaa://localhost:8082-0] - getHopByHopIdentifier:??1227882498??
	  12:23.350 INFO  [FSM-aaa://localhost:8082-0] - getEndToEndIdentifier:??2036948941??
	  12:23.350 INFO  [FSM-aaa://localhost:8082-0] - Avp Size:??17??
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - SESSION_ID(263)=??10.110.5.11;309;2036948938??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - AUTH_APPLICATION_ID(258)=??1??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - ORIGIN_HOST(264)=??hl.ac.10086.cn??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - ORIGIN_REALM(296)=??hl.ac.10086.cn??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - RESULT_CODE(268)=??2001??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - USER_NAME(1)=??15904604742??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - AUTH_TIMES(1866)=??13??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - AuthThreshold(1867)=??300??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - NickName(1868)=??WERETRER??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - USER_STATUS(1864)=??1??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - USER_REALNAME(1861)=??各类新业务测试??(10-0)
	  12:23.351 INFO  [FSM-aaa://localhost:8082-0] - USER_PROVINCE(1862)=??451??(10-0)
	  12:23.352 INFO  [FSM-aaa://localhost:8082-0] - USER_BRAND(1863)=??2??(10-0)
	  12:23.352 INFO  [FSM-aaa://localhost:8082-0] - XML_DATA(1865)=????(10-0)
	  12:23.352 INFO  [FSM-aaa://localhost:8082-0] - FetionStatus(1869)=??0??(10-0)
	  12:23.352 INFO  [FSM-aaa://localhost:8082-0] - 139MailStatus(1870)=??0??(10-0)
	  12:23.352 INFO  [FSM-aaa://localhost:8082-0] - User_level(1872)=??4??(10-0)
	  12:23.352 INFO  [FSM-aaa://localhost:8082-0] - ---------------------
*/	    
	    /**
	 * 
	 * @param request
	 * @param bean
	 * @return
	 */
	private static Answer populateAnswer(Request request,
			Map<String, String> bean) {

		if (bean == null) {
			return request.createAnswer(ResultCode.AUTHENTICATION_REJECTED);
		}

		String resultCode = bean.get(ConfigUtils.Key_ResultCode);
		// 2001：认证成功（Diameter_SUCCESS）
		// 4001：密码错误（Diameter_AUTHENTICATION_REJECTED）
		// 5002：用户不存在（Diameter_UNKNOWN_SESSION_ID）
		// 5003：用户无权登录DIAMETER_AUTHORIZATION_REJECTED
		// 5004其它：未知错误
		if (StringUtils.equals(resultCode, "5002")) {
			Answer answer = request.createAnswer(ResultCode.UNKNOWN_SESSION_ID);
			return answer;
		}
		if (StringUtils.equals(resultCode, "4001")) {
			Answer answer = request
					.createAnswer(ResultCode.AUTHENTICATION_REJECTED);
			populateBasicItem(request, answer, bean);
			return answer;
		}
		if (StringUtils.equals(resultCode, "5003")) {
			Answer answer = request
					.createAnswer(ResultCode.AUTHORIZATION_REJECTED);
			populateBasicItem(request, answer, bean);
			return answer;
		}
		String retCode = bean.get(Boss.RetCode);
		if ("000000".equals(retCode)) {
			// ok
			Answer answer = request.createAnswer(ResultCode.SUCCESS);
			populateBasicItem(request, answer, bean);
			AvpSet answerAvps = answer.getAvps();
			// User-RealName
			String strValue = bean.get(Boss.UserName);
			answerAvps.addAvp(1861, strValue, true, false, false);
			// User-Province : 451 hlj
			answerAvps.addAvp(1862, 451, true, false, true);
			// User-Brand
			strValue = bean.get(Boss.UserBrandCode);
			int iValue = "gn".equals(strValue) ? 1 : "dn".equals(strValue) ? 3
					: 2;
			answerAvps.addAvp(1863, iValue, true, false, true);
			// User-Status
			strValue = bean.get(Boss.UserStatusCode);
			iValue = "A".equals(strValue) ? 1 : 2;
			// answerAvps.addAvp(1864, iValue, true, false, true);
			// TODO 测试完成后需要生效上一行代码

			// XML Data
			strValue = getXMLData();
			answerAvps.addAvp(1865, strValue, true, false, false);
			// AuthTimes
			// AuthThreshold
			// NickName
			
			// FetionStatus
      strValue = bean.get("erhljmccFetionStatus");
			answerAvps.addAvp(1869, saftyConvert2Int(strValue, 1), true, false, true);
			// 139MailStatus
      strValue = bean.get("erhljmcc139MailStatus");
			answerAvps.addAvp(1870, saftyConvert2Int(strValue, 1), true, false, true);
			// User_level (1872) U32
			// 01 钻卡用户
			// 02 金卡用户
			// 03 银卡用户
			// 04 普通用户
      strValue = bean.get("erhljmccuserlevel");
			answerAvps.addAvp(1872, saftyConvert2Int(strValue, 4), true, false, true);// TODO User_level
			return answer;
		}
		if ("100100".equals(retCode)) {
			// user not found
			Answer answer = request.createAnswer(ResultCode.UNKNOWN_SESSION_ID);
			populateBasicItem(request, answer, bean);
			return answer;
		}
		if ("-00001".equals(retCode)) {
			// wrong password

			int authTimes = Integer
					.valueOf(bean.get(ConfigUtils.Key_AuthTimes));
			int authThreshold = Integer.valueOf(bean
					.get(ConfigUtils.Key_AuthThreshold));
			authTimes = authTimes + 1;
			bean.put(ConfigUtils.Key_AuthTimes, String.valueOf(authTimes));
			Answer answer = request
					.createAnswer(ResultCode.AUTHENTICATION_REJECTED);
			if (authTimes >= authThreshold) {
				answer = request
						.createAnswer(ResultCode.AUTHORIZATION_REJECTED);
			}
			populateBasicItem(request, answer, bean);
			String uid;
			try {
				uid = request.getAvps().getAvp(Avp.USER_NAME).getUTF8String();
				new LDAPHelper().updateAttribute(uid, "erhljmccAuthTimes",
						String.valueOf(authTimes));
			} catch (AvpDataException e) {
				log.error("", e);
			}
			return answer;
		}
		return request.createAnswer(ResultCode.AUTHENTICATION_REJECTED);
	}
	
	private static int saftyConvert2Int(String s, int defaultValue) {
	  try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
	}

	private static String getXMLData() {
		// TODO create XML Data
		return "";
	}

	protected static void logMessage(Message msg) {
		String mType = msg.isRequest() ? "Request" : "Answer";
		if (msg instanceof MessageImpl) {
			MessageImpl m = (MessageImpl) msg;
			log.info("===============================");
			log.info("{} Message : {}", mType, m);
			log.info("getVersion:【{}】", m.getVersion());
			log.info("getFlags:【{}】", Integer.toBinaryString(m.getFlags()));
			log.info("getCommandCode:【{}】", m.getCommandCode());
			log.info("getHeaderApplicationId:【{}】", m.getHeaderApplicationId());
			log.info("getSingleApplicationId:【{}】", m.getSingleApplicationId());
			log.info("getHopByHopIdentifier:【{}】", m.getHopByHopIdentifier());
			log.info("getEndToEndIdentifier:【{}】", m.getEndToEndIdentifier());
			log.info("Avp Size:【{}】", m.getAvps().size());
			for (Avp avp : m.getAvps()) {
				try {
					Object[] ret = getAvpInfo(avp);
					log.info("{}({})=【{}】({}-{})", ret);
				} catch (AvpDataException e) {
					log.info("{}", avp);
				}
			}
			log.info("---------------------");
		} else {
			log.info("{} Message {} [{}]", new Object[] { mType, msg,
					msg.getAvps() });
		}
	}

	private final static Object[] getAvpInfo(Avp avp) throws AvpDataException {
		List<Object> ret = new ArrayList<Object>();

		String avpName = null;
		int avpCode = avp.getCode();
		Object avpValue = null;
		long vendorId = avp.getVendorId();

		switch (avp.getCode()) {
		case Avp.AUTH_REQUEST_TYPE:
			avpName = "AUTH_REQUEST_TYPE";
			avpValue = avp.getUnsigned32();
			break;
		case Avp.AUTH_APPLICATION_ID:
			avpName = "AUTH_APPLICATION_ID";
			avpValue = avp.getUnsigned32();
			break;
		case 30:
			avpName = "Called-Station-Id";
			avpValue = avp.getUTF8String();
			break;
		case 31:
			avpName = "Calling-Station-Id";
			avpValue = avp.getUTF8String();
			break;
		case Avp.DESTINATION_HOST:
			avpName = "DESTINATION_HOST";
			avpValue = avp.getDiameterIdentity();
			break;
		case Avp.DESTINATION_REALM:
			avpName = "DESTINATION_REALM";
			avpValue = avp.getDiameterIdentity();
			break;
		case Avp.FIRMWARE_REVISION:
			avpName = "FIRMWARE_REVISION";
			avpValue = avp.getUnsigned32();
			break;
		case 8:/* Framed-IP-Address */
			avpName = "Framed-IP-Address";
			avpValue = avp.getOctetString();
			break;
		case Avp.HOST_IP_ADDRESS:
			avpName = "HOST_IP_ADDRESS";
			avpValue = avp.getAddress();
			break;
		case Avp.ORIGIN_HOST:
			avpName = "ORIGIN_HOST";
			avpValue = avp.getDiameterIdentity();
			break;
		case Avp.ORIGIN_REALM:
			avpName = "ORIGIN_REALM";
			avpValue = avp.getDiameterIdentity();
			break;
		case Avp.PRODUCT_NAME:
			avpName = "PRODUCT_NAME";
			avpValue = avp.getUTF8String();
			break;
		case Avp.RESULT_CODE:
			avpName = "RESULT_CODE";
			avpValue = avp.getUnsigned32();
			break;
		case Avp.SESSION_ID:
			avpName = "SESSION_ID";
			avpValue = avp.getUTF8String();
			break;
		case Avp.USER_NAME:
			avpName = "USER_NAME";
			avpValue = avp.getUTF8String();
			break;
		case Avp.VENDOR_ID:
			avpName = "VENDOR_ID";
			avpValue = avp.getUnsigned32();
			break;
		case Avp.ROUTE_RECORD:
			avpName = "ROUTE_RECORD";
			avpValue = avp.getDiameterIdentity();
			break;
		case 2:/* USER_PASSWORD */
			avpName = "USER_PASSWORD";
			avpValue = avp.getOctetString();
			break;
		case 1860:/* PASSWORD_TYPE */
			avpName = "PASSWORD_TYPE";
			avpValue = avp.getUnsigned32(); // Enumerated ?
			break;
		case 1861:/* USER_REALNAME */
			avpName = "USER_REALNAME";
			avpValue = avp.getUTF8String();
			break;
		case 1862:/* USER_PROVINCE */
			avpName = "USER_PROVINCE";
			avpValue = avp.getUnsigned32();
			break;
		case 1863:/* USER_BRAND */
			avpName = "USER_BRAND";
			avpValue = avp.getUnsigned32();// Enumerated
			break;
		case 1864:/* USER_STATUS */
			avpName = "USER_STATUS";
			avpValue = avp.getUnsigned32();// Enumerated
			break;
		case 1865:/* XML_DATA */
			avpName = "XML_DATA";
			avpValue = avp.getUTF8String();
			break;
		case 1866:/* AUTH_TIMES 认证不成功次数 */
			avpName = "AUTH_TIMES";
			avpValue = avp.getUnsigned32();
			break;
		case 1867:/* AuthThreshold 认证不成功次数的阀值 */
			avpName = "AuthThreshold";
			avpValue = avp.getUnsigned32();
			break;
		case 1868:/* NickName */
			avpName = "NickName";
			avpValue = avp.getUTF8String();
			break;
		case 1869:/* FetionStatus */
			avpName = "FetionStatus";
			avpValue = avp.getUnsigned32();// Enumerated
			break;
		case 1870:/* 139MailStatus */
			avpName = "139MailStatus";
			avpValue = avp.getUnsigned32();// Enumerated
			break;
		case 1871:/* Operate */
			avpName = "Operate";
			avpValue = avp.getUnsigned32();// Enumerated
			break;
		case 1872:/* User_level */
			avpName = "User_level";
			avpValue = avp.getUnsigned32();// Enumerated
			break;
		default:
			avpName = "-";
			avpValue = avp.getRawData();
		}
		int flag = avp.isVendorId() ? 100 : 0;
		flag += avp.isMandatory() ? 10 : 0;
		flag += avp.isEncrypted() ? 1 : 0;

		ret.add(avpName);
		ret.add(avpCode);
		ret.add(avpValue);
		ret.add(flag);
		ret.add(vendorId);
		return ret.toArray();
	}
}
