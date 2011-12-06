/**
 * 
 */
package com.ibm.lbs.mcc.hl.fsso.common;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zhao Dong Lu
 * 
 */
public class LDAPHelper {

	public static final Logger log = LoggerFactory.getLogger(LDAPHelper.class);

	private String url = "ldap://10.110.21.58:389";
	private String base = "dc=hljcmcc";
	private String userName = "cn=root";
	private String password = "passw0rd";
	private String ldapCtxFactory = "com.sun.jndi.ldap.LdapCtxFactory";

	private Hashtable<String, String> env;

	/**
   * 
   */
	public LDAPHelper() {
		super();
		env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);
		env.put(Context.PROVIDER_URL, this.getUrl());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, this.getUserName());
		env.put(Context.SECURITY_CREDENTIALS, this.getPassword());
	}

	public String getLdapCtxFactory() {
		return ldapCtxFactory;
	}

	public void setLdapCtxFactory(String ldapCtxFactory) {
		this.ldapCtxFactory = ldapCtxFactory;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private Map<String, String> register(DirContext ctx, String msisdn,
			String nickName, String networkPassword) throws NamingException {
		String attributePwd = "userPassword";
		String attributeNickName = "displayName";
		ModificationItem[] mods = new ModificationItem[2];
		Attribute mod0 = new BasicAttribute(attributePwd, networkPassword);
		Attribute aNickName = new BasicAttribute(attributeNickName, nickName);
		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
		mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, aNickName);

		String targetDN = getUserDNByMsisdn(msisdn);
		ctx.modifyAttributes(targetDN, mods);

		Map<String, String> bean = query(ctx, msisdn);
		return bean;
	}

	/**
	 * 
	 * @param msisdn
	 * @param nickName
	 * @param userPassword
	 * @return
	 * @throws NamingException
	 */
	public Map<String, String> registerNetworkPassword(String msisdn,
			String nickName, String userPassword) {
		log.debug("Register network password for [{}]", msisdn);
		if (nickName == null || userPassword == null
				|| userPassword.length() < 6) {
			// 检查密码策略
			throw new RuntimeException("缺少昵称、密码或者密码强度不足!");
		}
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
			return register(ctx, msisdn, nickName, userPassword);
		} catch (Exception e) {
			log.error("Register error!", e);
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					log.error("context close error", e);
				}
			}
		}
		return null;
	}

	/**
	 * @param msisdn
	 * @param networkPassword
	 * @return
	 * @throws NamingException
	 */
	public boolean updatePassword(String msisdn, String networkPassword)
			throws NamingException {
		if (networkPassword == null || networkPassword.length() < 6) {
			// 检查密码策略
			throw new RuntimeException("密码强度不足!");
		}
		String attributeName = "userPassword";

		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);

		env.put(Context.PROVIDER_URL, this.getUrl());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, this.getUserName());
		env.put(Context.SECURITY_CREDENTIALS, this.getPassword());

		ModificationItem[] mods = new ModificationItem[1];
		Attribute mod0 = new BasicAttribute(attributeName, networkPassword);
		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);

		boolean success = false;
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
			String targetDN = getUserDNByMsisdn(msisdn);
			ctx.modifyAttributes(targetDN, mods);
			success = true;
			log
					.debug(String
							.format(
									"Update network password, msisdn: [%s], service password: [%s]",
									msisdn, networkPassword));

		} catch (NamingException e) {
			throw e;
		} finally {
			if (ctx != null) {
				ctx.close();
			}
		}
		return success;
	}

	private String getUserDNByMsisdn(String msisdn) {
		String dn = "cn=" + msisdn + ",cn=user," + this.base;
		return dn;
	}

	/**
	 * 程序异常返回NULL
	 * 
	 * @param msisdn
	 * @param userPassword
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkNetworkPassword(String msisdn,
			String userPassword) {
		log.debug("Check network password for [{}]", msisdn);
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
			return checkPassword(ctx, msisdn, userPassword);
		} catch (Exception e) {
			log.error("Check error!", e);
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					log.error("context close error", e);
				}
			}
		}
		return null;
	}

	/**
	 * 查询用户信息<br>
	 * 查询不到用户返回Key_ResultCode=5002<br>
	 * 已经锁定Key_ResultCode=5003<br>
	 * 程序异常返回NULL
	 * 
	 * @param msisdn
	 * @return
	 */
	public Map<String, String> query(String msisdn) {
		log.debug("Query user for [{}]", msisdn);
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
			return query(ctx, msisdn);
		} catch (Exception e) {
			log.error("query error!", e);
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					log.error("context close error", e);
				}
			}
		}
		return null;
	}

	/**
	 * 查询用户信息<br>
	 * 查询不到用户返回Key_ResultCode=5002<br>
	 * 已经锁定Key_ResultCode=5003
	 * 
	 * @param ctx
	 * @param msisdn
	 * @return
	 * @throws NamingException
	 */
	private Map<String, String> query(DirContext ctx, String msisdn)
			throws NamingException {
		Map<String, String> bean = new LinkedHashMap<String, String>();

		String filter = "(uid=" + msisdn + ")";
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> answer = ctx.search(base, filter, sc);
		if (answer == null || !answer.hasMoreElements()) {
			log.warn(" user ({}) not found! ", msisdn);
			// 用户不存在
			bean.put(ConfigUtils.Key_ResultCode, "5002");
		} else {
			SearchResult sr = answer.next();

			Attribute attr = sr.getAttributes().get("displayName");
			String strValue = (String) attr.get();
			bean.put(ConfigUtils.Key_NickName, strValue);

			attr = sr.getAttributes().get("erhljmccAuthTimes");
			strValue = attr.get().toString();
			bean.put(ConfigUtils.Key_AuthTimes, strValue);

			attr = sr.getAttributes().get("erhljmccAuthThreshold");
			strValue = attr.get().toString();
			bean.put(ConfigUtils.Key_AuthThreshold, strValue);

			attr = sr.getAttributes().get("userPassword");
			strValue = new String((byte[]) attr.get());
			bean.put(ConfigUtils.Key_UserPassword, strValue);

			attr = sr.getAttributes().get("erhljmccStatus");
			strValue = attr.get().toString();
			bean.put(ConfigUtils.Key_UserStatus, strValue);

			int authTimes = Integer
					.valueOf(bean.get(ConfigUtils.Key_AuthTimes));
			int authThreshold = Integer.valueOf(bean
					.get(ConfigUtils.Key_AuthThreshold));
			if (authTimes >= authThreshold) {
				// 已经锁定
				bean.put(ConfigUtils.Key_ResultCode, "5003");
			}
			answer.close();
		}
		log.debug("LDAP query info: {}", bean);
		return bean;
	}

	private Map<String, String> checkPassword(DirContext ctx, String msisdn,
			String userPassword) throws UnsupportedEncodingException,
			NamingException {
		// Verify network password
		Map<String, String> bean = query(ctx, msisdn);
		// 2001：认证成功（Diameter_SUCCESS）
		// 4001：密码错误（Diameter_AUTHENTICATION_REJECTED）
		// 5002：用户不存在（Diameter_UNKNOWN_SESSION_ID）
		// 5003：用户无权登录DIAMETER_AUTHORIZATION_REJECTED
		// 5004其它：未知错误
		if (bean.containsKey(ConfigUtils.Key_ResultCode)) {
			return bean;
		}
		// if (bean == null) {
		// bean = new LinkedHashMap<String, String>();
		// // 用户不存在
		// bean.put(ConfigUtils.Key_ResultCode, "5002");
		// return bean;
		// }

		int authTimes = Integer.valueOf(bean.get(ConfigUtils.Key_AuthTimes));
		int authThreshold = Integer.valueOf(bean
				.get(ConfigUtils.Key_AuthThreshold));
		// if (authTimes >= authThreshold) {
		// // 已经锁定
		// bean.put(ConfigUtils.Key_ResultCode, "5003");
		// return bean;
		// }
		if (!bean.get(ConfigUtils.Key_UserPassword).equals(userPassword)) {
			Attributes attrs = new BasicAttributes();
			authTimes = authTimes + 1;
			attrs.put("erhljmccAuthTimes", String.valueOf(authTimes));
			ctx.modifyAttributes(getUserDNByMsisdn(msisdn),
					DirContext.REPLACE_ATTRIBUTE, attrs);
			bean.put(ConfigUtils.Key_AuthTimes, String.valueOf(authTimes));
			if (authTimes >= authThreshold) {
				bean.put(ConfigUtils.Key_ResultCode, "5003");
			} else {
				bean.put(ConfigUtils.Key_ResultCode, "4001");
			}
			return bean;
		}
		// 密码正确，需重置authTimes
		if (authTimes > 0) {
			Attributes attrs = new BasicAttributes();
			attrs.put("erhljmccAuthTimes", "0");
			ctx.modifyAttributes(getUserDNByMsisdn(msisdn),
					DirContext.REPLACE_ATTRIBUTE, attrs);
			bean.put(ConfigUtils.Key_AuthTimes, "0");
			return bean;
		}
		return bean;
	}

	public void updateAttribute(String uid, String key, String value) {
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
			Attributes attrs = new BasicAttributes();
			attrs.put(key, String.valueOf(value));
			ctx.modifyAttributes(getUserDNByMsisdn(uid),
					DirContext.REPLACE_ATTRIBUTE, attrs);
		} catch (Exception e) {
			log.error("query error!", e);
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					log.error("context close error", e);
				}
			}
		}
	}

	public static final void main(String args[]) {
		String uid = "15904604742";
		String nickName = "WERETRE";
		String pwd = "100866";
		LDAPHelper ldap = new LDAPHelper();
		System.out.println(ldap.query(uid));
		System.out.println(ldap.checkNetworkPassword(uid, pwd));
		System.out.println(ldap.registerNetworkPassword(uid, "abc", "abc123"));
		System.out.println(ldap.checkNetworkPassword(uid, "abc123"));
		System.out.println(ldap.registerNetworkPassword(uid, nickName, pwd));
	}
}
