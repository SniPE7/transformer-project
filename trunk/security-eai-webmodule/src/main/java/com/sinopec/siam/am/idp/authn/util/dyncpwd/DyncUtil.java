package com.sinopec.siam.am.idp.authn.util.dyncpwd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DyncUtil {

	/* ��־������� */
	protected static final Log log = LogFactory.getLog(DyncUtil.class);
	
	private static UserPwd up = new UserPwd();

	/**
	 * ��ȡ��̬����
	 * 
	 * @param uid
	 * @param password
	 * @return
	 */
	public static String getPassword(String uid, String password) {

		// ��ȡ��ǰʱ��Ķ�̬����
		//UserPwd up = new UserPwd();
		long time = System.currentTimeMillis();
		// System.out.println("��ǰʱ�䣺" + up.obtainCurrPwd(time,dbSeed));

		String dbSeed = SeedCreate.CreateSeed2(uid);
		
		//System.out.println("dbSeed��" + dbSeed);
		
		return up.obtainCurrPwd(time, dbSeed);
	}

	/**
	 * ��֤��̬����
	 * 
	 * @param uid
	 * @param password
	 * @return
	 */
	/*
	 * public static boolean checkDyncPassword(String uid, String password,
	 * LdapUser user) {
	 * 
	 * //��֤�� AttestationAction objDo = new AttestationAction(); String dbSeed =
	 * SeedCreate.CreateSeed2(uid);
	 * 
	 * int retInt = objDo.simplevalidate2(uid, dbSeed, password);
	 * //System.out.println("retValue:" + retInt);
	 * 
	 * if (retInt == 0) return true; else return false; }
	 */

	/*
	 * public static boolean sendNewPwd(String pwd, String mode, String toaddr)
	 * { // logger.info("���Ͷ��Ÿ�"+mobile); // return true; //modify by xuhong
	 * 20101223 //String smsmsg = "VPN��֤��Ϊ��" + pwd; String smsmsg =
	 * RadiusConfig.getMsgContent() + pwd + "(" + UserPwd.timeOff + "��������Ч)";
	 * 
	 * if(mode.equalsIgnoreCase("email")){ String titleMail =
	 * "��̬��֤������ʾ";//��̬��֤������ʾ return sendMail(titleMail, smsmsg,toaddr); }
	 * 
	 * if (RadiusConfig.isSend2db()){ return sendSms2DB(smsmsg, toaddr); }
	 * 
	 * //axis2�ͻ��˵���sms���ͽӿ�
	 * 
	 * SmsSendSvrClient smscontrol = new SmsSendSvrClient(); String
	 * smsSendSvrSoap_address = RadiusConfig.getSmsSendSvrSoap_address(); try {
	 * boolean ret = smscontrol.sendSMSMsg(smsSendSvrSoap_address,smsmsg,
	 * toaddr); logger.info("���͸�" + toaddr + ": " + pwd);
	 * AuthProcess.setErrMsg("�ɹ����͸�" + toaddr ); return ret; } catch
	 * (ServiceException e) { // TODO �Զ����� catch �� logger.info("���͸�" + toaddr +
	 * "��ʧ�� " + e.getMessage()); AuthProcess.setErrMsg("���͸�" + toaddr + "��ʧ�� " +
	 * e.getMessage()); return false; } catch (RemoteException e) { // TODO �Զ�����
	 * catch �� logger.info("���͸�" + toaddr + "��ʧ�� " + e.getMessage());
	 * AuthProcess.setErrMsg("���͸�" + toaddr + "��ʧ�� " + e.getMessage()); return
	 * false; }catch(Exception e){ logger.info("���͸�" + toaddr + "��ʧ�� " +
	 * e.getMessage()); AuthProcess.setErrMsg("���͸�" + toaddr + "��ʧ�� " +
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
	 * logger.info("sendSms2DB ���ݿ�����ʧ��");
	 * AuthProcess.setErrMsg("sendSms2DB ���ݿ�����ʧ��"); return false; } try {
	 * PreparedStatement preparedStatement = conn
	 * .prepareStatement(sqlStatement); preparedStatement.setString(1, mobile);
	 * preparedStatement.setString(2, smsmsg);
	 * preparedStatement.executeUpdate(); preparedStatement.close();
	 * ss.freeConnection("oas", conn); return true; } catch (SQLException e) {
	 * DbUtil.CloseConnect(conn); logger.info("���͸�" + mobile + "��ʧ�� " +
	 * e.getMessage()); AuthProcess.setErrMsg("���͸�" + mobile + "��ʧ�� " +
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
	 * //msg.setSubject("�����ܲ�SSL VPN��֤����"); //msg.setSubject("��̬��֤������ʾ");
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
	 * // ������ת��ΪGB2312���� subject = StringUtil.getString(subject, "GB2312");
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
	 * { new InternetAddress(to) }); transport.close(); logger.info("�ɹ������ʼ���" +
	 * to ); AuthProcess.setErrMsg("�ɹ������ʼ���" + to ); return true;
	 * }catch(Exception e){ logger.info("�����ʼ�ʧ�ܣ�" + e.getMessage());
	 * AuthProcess.setErrMsg("�����ʼ�ʧ�ܣ�" + e.getMessage()); } return false; }
	 */

	public static void main(String[] args) {
		
		System.out.println("user password : "
				+ DyncUtil.getPassword("test1", ""));
		
		System.out.println("user password : "
				+ DyncUtil.getPassword("test2", ""));
		
		
		System.out.println("user password : "
				+ DyncUtil.getPassword("1test", ""));
		System.out.println("user password : "
				+ DyncUtil.getPassword("2test", ""));
		
		/*SimpleDateFormat strToDate = new SimpleDateFormat ("yyyy-MM-dd-hh:mm:ss:SSS");
		
		//System.out.println("Time: " + strToDate.format(new Date()));
		Date dateStart = new Date();
		
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
