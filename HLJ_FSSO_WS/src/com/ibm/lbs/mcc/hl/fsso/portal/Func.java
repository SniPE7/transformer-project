package com.ibm.lbs.mcc.hl.fsso.portal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.lbs.mcc.hl.fsso.common.ConfigUtils;

/**
 * 调用Portal开发的接口: <br>
 * 注册通行证密码（包含修改密码）<br>
 * 通行证密码认证<br>
 * 
 * @author weizi
 * 
 */
public class Func {

	private static final Logger log = LoggerFactory.getLogger(Func.class);

	private static final String CALL_URI = ConfigUtils.getConfig().getString(
			"fsso.portal.CallUri");

	/**
	 * 注册通行证密码（包含修改密码）
	 * 
	 * @param userName
	 * @param password
	 * @param passwordType
	 * @return
	 */
	public static Map<String, String> register(String userName, String password) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("mobile", userName);
		params.put("passwd", password);
		params.put("pwdType", "3");// 注册
		try {
			Map<String, String> p = doPost(CALL_URI, params);
			return p;
		} catch (Exception e) {
			log.error("Call register failed on {}:{} ", CALL_URI, e);
			return null;
		}
	}

	/**
	 * 通行证密码认证
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public static Map<String, String> auth(String userName, String password) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("mobile", userName);
		params.put("passwd", password);
		params.put("pwdType", "1");// 通行证密码认证
		try {
			Map<String, String> p = doPost(CALL_URI, params);
			return p;
		} catch (Exception e) {
			log.error("Call Auth failed on {} :{}", CALL_URI, e);
			return null;
		}
	}

	static Map<String, String> doGet(String uri, Map<String, String> params)
			throws IllegalStateException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(uri);
		// TODO process params!
		HttpResponse response = httpclient.execute(httpget);

		return parseResponse(response);
	}

	static Map<String, String> doPost(String uri, Map<String, String> params)
			throws IllegalStateException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		return doPost(httpclient, uri, params);
	}

	static Map<String, String> doPost(HttpClient httpclient, String uri,
			Map<String, String> params) throws IllegalStateException,
			IOException {
		log.debug("portal func call:{} at {}", params, uri);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : params.entrySet()) {
			formParams.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams,
				"UTF-8");
		HttpPost httppost = new HttpPost(uri);
		httppost.setEntity(entity);

		HttpResponse response = httpclient.execute(httppost);
		return parseResponse(response);
	}

	private static Map<String, String> parseResponse(HttpResponse response)
			throws IllegalStateException, IOException {
		Map<String, String> props = new HashMap<String, String>();

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();

			BufferedReader isr = new BufferedReader(new InputStreamReader(
					instream));
			String line;
			log.debug("--------- start parsing content ----------------");
			while ((line = isr.readLine()) != null) {
				log.debug(line);
				parseLine(props, line);
			}
			log.debug("--------- ending parsing content ----------------");
			log.debug("{}", props);
		}
		return props;
	}

	private static Map<String, String> parseLine(Map<String, String> props,
			String line) {
		int equalSign = line.indexOf('=');
		if (equalSign > 0) {
			String pkey = line.substring(0, equalSign).trim();
			String pvalue = line.substring(equalSign + 1).trim();
			props.put(pkey, pvalue);
		} else if ("".equals(line.trim())) {
			// Semantically equivalent to an empty Properties
			// object.
		} else {
			throw new IllegalArgumentException('\'' + line
					+ "' does not contain an equals sign");
		}
		return props;
	}

}
