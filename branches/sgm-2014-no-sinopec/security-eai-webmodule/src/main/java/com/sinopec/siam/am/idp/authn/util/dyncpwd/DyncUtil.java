package com.sinopec.siam.am.idp.authn.util.dyncpwd;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DyncUtil {

	/* 日志输出对象 */
	protected static final Log log = LogFactory.getLog(DyncUtil.class);
	
	private static UserPwd up = new UserPwd();

	/**
	 * 获取动态密码
	 * 
	 * @param uid
	 * @param password
	 * @return
	 */
	public static String getPassword(String uid, String password) {

		// 获取当前时间的动态密码
		//UserPwd up = new UserPwd();
		long time = System.currentTimeMillis();
		// System.out.println("当前时间：" + up.obtainCurrPwd(time,dbSeed));

		String dbSeed = SeedCreate.CreateSeed2(uid);

		//System.out.println("dbSeed：" + dbSeed);
		
		return up.obtainCurrPwd(time, dbSeed);
	}
	
	/** 检测用户的动态密码在有效期内是否正确
     * @param uid  用户id
     * @param password 被验证的密码
     * @param minute   几分钟内的有效期
     * @return true 验证通过； false 验证失败
     */
    public static boolean checkPwd(String uid, String password) {
        return checkPwd(uid, password, 5);
    }
	
	/** 检测用户的动态密码在有效期内是否正确
	 * @param uid  用户id
	 * @param password 被验证的密码
	 * @param minute   几分钟内的有效期
	 * @return true 验证通过； false 验证失败
	 */
	public static boolean checkPwd(String uid, String password, int minute) {
	    boolean result = false;
	    
	    String dbSeed = SeedCreate.CreateSeed2(uid);

        long time = System.currentTimeMillis();
        
        String curPwd = up.obtainCurrPwd(time,dbSeed);
        if(curPwd.equalsIgnoreCase(password)) {
            return true;
        } else {
            String[] timePwds = up.obtainF10Pwds(time, dbSeed);
            for(String pwd : timePwds) {
                if(pwd.equalsIgnoreCase(password)) {
                    return true;
                }
            }
            
        }

	    return result;
	}

	/**
	 * 验证动态密码
	 * 
	 * @param uid
	 * @param password
	 * @return
	 */
	/*
	 * public static boolean checkDyncPassword(String uid, String password,
	 * LdapUser user) {
	 * 
	 * //验证块 AttestationAction objDo = new AttestationAction(); String dbSeed =
	 * SeedCreate.CreateSeed2(uid);
	 * 
	 * int retInt = objDo.simplevalidate2(uid, dbSeed, password);
	 * //System.out.println("retValue:" + retInt);
	 * 
	 * if (retInt == 0) return true; else return false; }
	 */

	/*
	 * public static boolean sendNewPwd(String pwd, String mode, String toaddr)
	 * { // logger.info("发送短信给"+mobile); // return true; //modify by xuhong
	 * 20101223 //String smsmsg = "VPN验证码为：" + pwd; String smsmsg =
	 * RadiusConfig.getMsgContent() + pwd + "(" + UserPwd.timeOff + "分钟内有效)";
	 * 
	 * if(mode.equalsIgnoreCase("email")){ String titleMail =
	 * "动态验证密码提示";//动态验证密码提示 return sendMail(titleMail, smsmsg,toaddr); }
	 * 
	 * if (RadiusConfig.isSend2db()){ return sendSms2DB(smsmsg, toaddr); }
	 * 
	 * //axis2客户端调用sms发送接口
	 * 
	 * SmsSendSvrClient smscontrol = new SmsSendSvrClient(); String
	 * smsSendSvrSoap_address = RadiusConfig.getSmsSendSvrSoap_address(); try {
	 * boolean ret = smscontrol.sendSMSMsg(smsSendSvrSoap_address,smsmsg,
	 * toaddr); logger.info("发送给" + toaddr + ": " + pwd);
	 * AuthProcess.setErrMsg("成功发送给" + toaddr ); return ret; } catch
	 * (ServiceException e) { // TODO 自动生成 catch 块 logger.info("发送给" + toaddr +
	 * "，失败 " + e.getMessage()); AuthProcess.setErrMsg("发送给" + toaddr + "，失败 " +
	 * e.getMessage()); return false; } catch (RemoteException e) { // TODO 自动生成
	 * catch 块 logger.info("发送给" + toaddr + "，失败 " + e.getMessage());
	 * AuthProcess.setErrMsg("发送给" + toaddr + "，失败 " + e.getMessage()); return
	 * false; }catch(Exception e){ logger.info("发送给" + toaddr + "，失败 " +
	 * e.getMessage()); AuthProcess.setErrMsg("发送给" + toaddr + "，失败 " +
	 * e.getMessage()); return false; }
	 * 
	 * }
	 */

	/*
	 * public static boolean sendSms2DB(String smsmsg, String mobile) { String
	 * sqlStatement = "INSERT INTO T_SMS_SEND " +
	 * "( USER_ID, USER_NAME, MOBILE, SMS_TYPE, SEND_MODE, CONTENT, ID ) VALUES "
	 * + "( 'vpn', 'vpn', ?, 'vpn', 0, ?,T_SMS_SEND_ID.nextval) ";
	 * DBConnectionManager ss = DBConnectionManager.getInstance(); Connection
	 * conn = ss.getConnection("oas", 3000); if (conn == null){
	 * logger.info("sendSms2DB 数据库连接失败");
	 * AuthProcess.setErrMsg("sendSms2DB 数据库连接失败"); return false; } try {
	 * PreparedStatement preparedStatement = conn
	 * .prepareStatement(sqlStatement); preparedStatement.setString(1, mobile);
	 * preparedStatement.setString(2, smsmsg);
	 * preparedStatement.executeUpdate(); preparedStatement.close();
	 * ss.freeConnection("oas", conn); return true; } catch (SQLException e) {
	 * DbUtil.CloseConnect(conn); logger.info("发送给" + mobile + "，失败 " +
	 * e.getMessage()); AuthProcess.setErrMsg("发送给" + mobile + "，失败 " +
	 * e.getMessage()); return false; }
	 * 
	 * }
	 */

	/*
	 * public static boolean sendMail(String title, String smsmsg, String to){
	 * Properties props = new Properties();
	 * props.setProperty("mail.transport.protocol", "smtp");
	 * props.setProperty("mail.smtp.auth", "true"); Session session =
	 * Session.getInstance(props);
	 * 
	 * session.setDebug(true);
	 * 
	 * try{ Message msg = new MimeMessage(session);
	 * 
	 * msg.setFrom(new InternetAddress(RadiusConfig.getMailUser() + "@" +
	 * RadiusConfig.getDomain())); //msg.setFrom(new
	 * InternetAddress("janus1999@talkweb.com.cn"));
	 * //msg.setSubject("集团总部SSL VPN验证密码"); //msg.setSubject("动态验证密码提示");
	 * 
	 * msg.setSubject(title); msg.setText(smsmsg);
	 * 
	 * msg.setSubject(MimeUtility.encodeText(title)); msg.setText(smsmsg);
	 * 
	 * //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
	 * //msg.setSubject("=?GB2312?B?"+enc.encode(title.getBytes())+"?=");
	 * //msg.setSubject(new String(title.getBytes("UTF-8"),"UTF-8"));
	 * //msg.setText(new String(smsmsg.getBytes("UTF-8"),"UTF-8"));
	 * 
	 * //msg.setContent(msg.getContent(),"text/html;charset=GB2312");
	 * 
	 * //System.out.println("444444444");
	 * 
	 * // msg.setSubject("=?GB2312?B?"+Base64.encode(subject.getBytes())+"?=");
	 * // 将中文转化为GB2312编码 subject = StringUtil.getString(subject, "GB2312");
	 * subject = new String(Base64.encode((subject).getBytes()));
	 * msg.setSubject("=?GB2312?B?" + subject + "?="); msg.setFrom(new
	 * InternetAddress("david.li@maxcard.com"));
	 * msg.setText(StringUtil.getString(content,"GB2312"));
	 * //wangzhenxing@hnxt.gov.cn
	 * 
	 * Transport transport = session.getTransport();
	 * transport.connect(RadiusConfig.getSmtpServer(),
	 * RadiusConfig.getSmtpPort(), RadiusConfig.getMailUser(),
	 * RadiusConfig.getMailPassword()); transport.sendMessage(msg, new Address[]
	 * { new InternetAddress(to) }); transport.close(); logger.info("成功发送邮件给" +
	 * to ); AuthProcess.setErrMsg("成功发送邮件给" + to ); return true;
	 * }catch(Exception e){ logger.info("发送邮件失败：" + e.getMessage());
	 * AuthProcess.setErrMsg("发送邮件失败：" + e.getMessage()); } return false; }
	 */

	public static void main(String[] args) {
	    
	    SimpleDateFormat strToDate = new SimpleDateFormat ("yyyy-MM-dd-hh:mm:ss:SSS");
        System.out.println("Time: " + strToDate.format(new Date()));
        
        System.out.println("user password : "
                + SeedCreate.CreateSeed2("Jsmith"));
        System.out.println("user password : "
                + SeedCreate.CreateSeed2("jsmith"));
        
		System.out.println("user password : "
				+ DyncUtil.getPassword("jsmith", ""));
		
		System.out.println("user password : "
				+ DyncUtil.getPassword("888888", ""));
		
		
		System.out.println("user password : "
				+ DyncUtil.getPassword("1test", ""));
		System.out.println("user password : "
				+ DyncUtil.getPassword("2test", ""));
		
		
		/*Date dateStart = new Date();
		
		for (int i = 0; i < 10000; i++) {
			
			String test = "zzz" ;
			System.out.println("test:" + test + "  "+ test.getBytes().hashCode() + "  "+ String.valueOf(test.getBytes().hashCode()).getBytes());
			
			System.out.println("user password : "
					+ DyncUtil.getPassword(test, ""));
			
			
		}
		
		Date dateEnd = new Date();
		System.out.println("Time: " + strToDate.format(dateStart) + "    Time: " + strToDate.format(dateEnd));
		*/
		
		/*SimpleDateFormat strToDate = new SimpleDateFormat ("yyyy-MM-dd-hh:mm:ss:SSS");
		
		//System.out.println("Time: " + strToDate.format(new Date()));
		Date dateStart = new Date();
		
		for (int i = 0; i < 10000; i++) {
			System.out.println("user password : "
					+ SeedCreate.CreateSeed2(i + "zhangming"));
		}
		
		Date dateEnd = new Date();
		System.out.println("Time: " + strToDate.format(dateStart) + "    Time: " + strToDate.format(dateEnd));*/
		
	}
}
