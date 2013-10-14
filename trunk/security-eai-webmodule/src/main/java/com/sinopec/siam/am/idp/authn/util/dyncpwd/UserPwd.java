/**
 * �û����봦����
 */
package com.sinopec.siam.am.idp.authn.util.dyncpwd;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class UserPwd {
    //ʱ������
	protected int period = 60 * 5;

	protected int codeLen = 6;

	public static final int forward = 1;// ʱ�䷽����ǰ

	public static final int backward = 2;// ʱ�䷽�����

	//public static final int timeOff = 4;// ʱ����Ϊ10����
	//modify by xuhong 20101223
	public static int timeOff = 4;// ʱ����Ϊ5����

	/* ��־������� */
	  protected static final Log log = LogFactory.getLog(UserPwd.class);

	public UserPwd() {// ��ʼ������ȡ�����ļ��ĵ����ò���
/*
		period = DpConfig.getPeriod();
		codeLen = DpConfig.getCodeLen();
		//add by xuhong 20101223
		timeOff = DpConfig.getFailwarp();*/
	}

	/**
	 * �õ���ǰʱ��Ķ�̬����
	 * 
	 * @param time
	 * @param dbSeed
	 * @return
	 */
	public String obtainCurrPwd(long time, String dbSeed) {
		return this.obtainWarpTimePwd(time, dbSeed, 0);
	}

	/**
	 * �õ�ʱ��ƫ��Ϊwarp�ķ�������̬����
	 * 
	 * @param time
	 * @param dbSeed
	 * @param warp
	 *            ʱ��ƫ��
	 * @return
	 */
	public String obtainWarpPwd(long time, String dbSeed, long warp) {
		return this.obtainWarpTimePwd(time, dbSeed, warp);
	}

	/**
	 * ����ǰ10���ӵĶ�̬����
	 * 
	 * @param t
	 * @param seed
	 * @return
	 */
	public String[] obtainF10Pwds(long time, String seed) {
		return this.obtainPwds(time, seed, UserPwd.forward, UserPwd.timeOff);
	}

	/**
	 * ���ɺ�10���ӵĶ�̬����
	 * 
	 * @param t
	 * @param seed
	 * @return
	 */
	public String[] obtainB10Pwds(long time, String seed) {
		return this.obtainPwds(time, seed, UserPwd.backward, UserPwd.timeOff);
	}

	/**
	 * ���ݴ��������������ǰʱ��time��������������seed��ʱ�䷽������type��ʱ����off����һϵ�е�����
	 * 
	 * @param time
	 * @param seed
	 * @param type
	 * @param off
	 * @return
	 */
	private String[] obtainPwds(long time, String seed, int type, int off) {
		String[] pwds = new String[off];
		long warp = 0L;// ʱ����
		for (int i = 0; i < off; i++) {
			switch (type) {
			case UserPwd.forward:// ʱ�䷽����ǰ
				warp = -1 * (i + 1);
				break;
			case UserPwd.backward:// ʱ�䷽�����
				warp = i + 1;
				break;
			default:
				break;// ����ʱ�䷽������
			}
			pwds[i] = this.obtainWarpTimePwd(time, seed, warp);
		}
		return pwds;
	}

	/**
	 * ����������Ϊseed����ǰʱ��Ϊtime��ʱ��ƫ��Ϊwarp������£�ȡ��ʱ��ƫ����ϵ�����
	 * 
	 * @param time
	 * @param dbSeed
	 * @param warp
	 *            ��λΪ����
	 * @return
	 */
	private String obtainWarpTimePwd(long time, String dbSeed, long warp) {
		long mytime = time + 60000L * (warp);
		long tmpTime = 0L;
		// if(warp == 0){//ȡ���ǵ�ǰʱ������
		// tmpTime = time / 1000L - 0x1e187e00L;
		// }else{
		// tmpTime = (time + 60000L*(warp)) / 1000L - 0x1e187e00L;
		// }

		tmpTime = mytime / 1000L - 0x1e187e00L;
		TokenCode tcode = new TokenCode();
		
		//String pwd = tcode.calcPRN(tmpTime, obtainRealSeed(dbSeed), codeLen, period);
		String pwd = tcode.calcPRN(tmpTime, dbSeed.getBytes(), codeLen, period);
		
		// logger.info("ʱ�䣺"+mytime+" ƫ��Ϊ: "+warp+" �Ķ�̬����Ϊ:"+pwd);
		return pwd;
	}

	/**
	 * �õ�������̬���������
	 * 
	 * @param dbSeed
	 *            ������������,��������ݱ���ʱ�Ǿ��������˵ġ�
	 * @return
	 */
	private byte[] obtainRealSeed(String dbSeed) {
		byte key[] = new byte[8];
		byte[] bb = ThreeDes.hex2byte(dbSeed); // ת���ɶ�����
		byte[] tt = ThreeDes.decryptMode(ThreeDes.keyBytes, bb); // ����
		dbSeed = new String(tt);
		String[] allSeed = dbSeed.split(",");
		// String szTokenNo = allSeed[0];
		for (int i = 1; i < 9; i++) {
			int val = 0;
			try {
				val = Integer.parseInt(allSeed[i]);
			} catch (Exception e) {
				val = 0;
			}
			key[i - 1] = (byte) val;
		}
		return key;
	}

	public static void main(String[] args) throws Exception {
		UserPwd up = new UserPwd();
		long time = System.currentTimeMillis();
		// Date d = new Date(time);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern("yyyy��MM��dd�� HHʱmm��ss��");
		System.out.println(sdf.format(new Timestamp(time)));


		// System.out.println(d.toLocaleString());
		//String dbSeed = "5CB64191A2224572E4FD52EA5CC2243E4D5C6F052D429F4704201098696C3163E80840886CC9007529586833D34058DDAC5CD8BBB5AA29EA";
		//String dbSeed = "53281B1AC85022EDB95C490F1EAB6545001CD94C8B8DC5878CD72973E5D4244324B55F41DE9C7E541E1F62FFAB0D7CE10E9B459B2A937738";
		
		String dbSeed = "074A1E7B2F07BEE3D2CFAD9D6C93F9C8976CCFF944DCAC48C28513D933181BBC56D438E46180A0AD00790A0D3244213D0F0DB1965EAB3A5C";
		//String dbSeed = "530D6BB9368DAA52F4C5050CCEFA71D7CC2EAE125A6990576C76192CD6AD74B63129DA4A09071501090B2CFBAF22B45D825F803138AFF273";
		
		System.out.println("��ǰʱ�䣺" + up.obtainCurrPwd(time,dbSeed));
		
		
		
		//System.out.println(up.obtainWarpTimePwd(time,dbSeed,0));
		//System.out.println(up.obtainWarpTimePwd(time,dbSeed,1));
		//System.out.println(up.obtainWarpTimePwd(time,dbSeed,60));
		 
		
		System.out.println("*****************obtainB10Pwds*************************"); 
		String[] time2 = up.obtainB10Pwds(time, dbSeed);
		for (int i = 0; i < time2.length; i++) {
			System.out.println("--------------��" + (i+1) + "���Ӻ� " + time2[i]);
		}
		
		
		System.out.println("*****************obtainF10Pwds*************************");
		
		String[] time3 = up.obtainF10Pwds(time, dbSeed);
		for (int i = 0; i < time3.length; i++) {
			System.out.println("--------------�ڣ�" + (i+1) + "����ǰ�� " + time3[i]);
		}
		System.out.println("******************************************");
		
		
	/*	String dbSeed = "C74E47359E8F2150D8FEA9A94D713EAFA5403563EB764476F937DFA97682EBF19CEACC5223585AE7702314AC45F3851F837868B5E82B59ECD663A63B5ACB6371";
		
		System.out.println("��ǰʱ�䣺" + up.obtainCurrPwd(time, dbSeed));
		
		dbSeed = "7C80090DA498C23F0471FD79A932BCB42CDCF21B6D7F7AECFCF0FA0C0EC869BEBA0BCC721A84F8965D4122D8E965863C6EAC7F67570A8D451088E5E1FF6FED1F";
		
		System.out.println("��ǰʱ�䣺" + up.obtainCurrPwd(time, dbSeed));*/
		
	}

}
