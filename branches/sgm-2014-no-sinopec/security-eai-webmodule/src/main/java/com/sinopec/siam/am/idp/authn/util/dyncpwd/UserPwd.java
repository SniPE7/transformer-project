/**
 * 用户密码处理类
 */
package com.sinopec.siam.am.idp.authn.util.dyncpwd;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class UserPwd {
    //时间周期
	protected int period = 60 * 5;

	protected int codeLen = 6;

	public static final int forward = 1;// 时间方向向前

	public static final int backward = 2;// 时间方向向后

	//public static final int timeOff = 4;// 时间间隔为10分钟
	//modify by xuhong 20101223
	public static int timeOff = 4;// 时间间隔为5分钟

	/* 日志输出对象 */
	  protected static final Log log = LogFactory.getLog(UserPwd.class);

	public UserPwd() {// 初始化，读取参数文件的的设置参数
/*
		period = DpConfig.getPeriod();
		codeLen = DpConfig.getCodeLen();
		//add by xuhong 20101223
		timeOff = DpConfig.getFailwarp();*/
	}

	/**
	 * 得到当前时间的动态密码
	 * 
	 * @param time
	 * @param dbSeed
	 * @return
	 */
	public String obtainCurrPwd(long time, String dbSeed) {
		return this.obtainWarpTimePwd(time, dbSeed, 0);
	}

	/**
	 * 得到时间偏差为warp的服务器动态密码
	 * 
	 * @param time
	 * @param dbSeed
	 * @param warp
	 *            时间偏差
	 * @return
	 */
	public String obtainWarpPwd(long time, String dbSeed, long warp) {
		return this.obtainWarpTimePwd(time, dbSeed, warp);
	}

	/**
	 * 生成前10分钟的动态密码
	 * 
	 * @param t
	 * @param seed
	 * @return
	 */
	public String[] obtainF10Pwds(long time, String seed) {
		return this.obtainPwds(time, seed, UserPwd.forward, UserPwd.timeOff);
	}

	/**
	 * 生成后10分钟的动态密码
	 * 
	 * @param t
	 * @param seed
	 * @return
	 */
	public String[] obtainB10Pwds(long time, String seed) {
		return this.obtainPwds(time, seed, UserPwd.backward, UserPwd.timeOff);
	}

	/**
	 * 根据传入参数服务器当前时间time和密码生成种子seed，时间方向类型type和时间间隔off生成一系列的密码
	 * 
	 * @param time
	 * @param seed
	 * @param type
	 * @param off
	 * @return
	 */
	private String[] obtainPwds(long time, String seed, int type, int off) {
		String[] pwds = new String[off];
		long warp = 0L;// 时间间隔
		for (int i = 0; i < off; i++) {
			switch (type) {
			case UserPwd.forward:// 时间方向向前
				warp = -1 * (i + 1);
				break;
			case UserPwd.backward:// 时间方向向后
				warp = i + 1;
				break;
			default:
				break;// 错误时间方向类型
			}
			pwds[i] = this.obtainWarpTimePwd(time, seed, warp);
		}
		return pwds;
	}

	/**
	 * 在密码种子为seed，当前时间为time，时间偏差为warp的情况下，取得时间偏差点上的密码
	 * 
	 * @param time
	 * @param dbSeed
	 * @param warp
	 *            单位为分钟
	 * @return
	 */
	private String obtainWarpTimePwd(long time, String dbSeed, long warp) {
		long mytime = time + 60000L * (warp);
		long tmpTime = 0L;
		// if(warp == 0){//取的是当前时间密码
		// tmpTime = time / 1000L - 0x1e187e00L;
		// }else{
		// tmpTime = (time + 60000L*(warp)) / 1000L - 0x1e187e00L;
		// }

		tmpTime = mytime / 1000L - 0x1e187e00L;
		TokenCode tcode = new TokenCode();
		
		//String pwd = tcode.calcPRN(tmpTime, obtainRealSeed(dbSeed), codeLen, period);
		String pwd = tcode.calcPRN(tmpTime, dbSeed.getBytes(), codeLen, period);
		
		// logger.info("时间："+mytime+" 偏差为: "+warp+" 的动态密码为:"+pwd);
		return pwd;
	}

	/**
	 * 得到产生动态密码的种子
	 * 
	 * @param dbSeed
	 *            密码生成种子,存放在数据表中时是经过加密了的。
	 * @return
	 */
	private byte[] obtainRealSeed(String dbSeed) {
		byte key[] = new byte[8];
		byte[] bb = ThreeDes.hex2byte(dbSeed); // 转换成二进制
		byte[] tt = ThreeDes.decryptMode(ThreeDes.keyBytes, bb); // 解密
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
		sdf.applyPattern("yyyy年MM月dd日 HH时mm分ss秒");
		System.out.println(sdf.format(new Timestamp(time)));


		// System.out.println(d.toLocaleString());
		//String dbSeed = "5CB64191A2224572E4FD52EA5CC2243E4D5C6F052D429F4704201098696C3163E80840886CC9007529586833D34058DDAC5CD8BBB5AA29EA";
		//String dbSeed = "53281B1AC85022EDB95C490F1EAB6545001CD94C8B8DC5878CD72973E5D4244324B55F41DE9C7E541E1F62FFAB0D7CE10E9B459B2A937738";
		
		String dbSeed = "074A1E7B2F07BEE3D2CFAD9D6C93F9C8976CCFF944DCAC48C28513D933181BBC56D438E46180A0AD00790A0D3244213D0F0DB1965EAB3A5C";
		//String dbSeed = "530D6BB9368DAA52F4C5050CCEFA71D7CC2EAE125A6990576C76192CD6AD74B63129DA4A09071501090B2CFBAF22B45D825F803138AFF273";
		
		System.out.println("当前时间：" + up.obtainCurrPwd(time,dbSeed));
		
		
		
		//System.out.println(up.obtainWarpTimePwd(time,dbSeed,0));
		//System.out.println(up.obtainWarpTimePwd(time,dbSeed,1));
		//System.out.println(up.obtainWarpTimePwd(time,dbSeed,60));
		 
		
		System.out.println("*****************obtainB10Pwds*************************"); 
		String[] time2 = up.obtainB10Pwds(time, dbSeed);
		for (int i = 0; i < time2.length; i++) {
			System.out.println("--------------第" + (i+1) + "分钟后： " + time2[i]);
		}
		
		
		System.out.println("*****************obtainF10Pwds*************************");
		
		String[] time3 = up.obtainF10Pwds(time, dbSeed);
		for (int i = 0; i < time3.length; i++) {
			System.out.println("--------------第：" + (i+1) + "分钟前： " + time3[i]);
		}
		System.out.println("******************************************");
		
		
	/*	String dbSeed = "C74E47359E8F2150D8FEA9A94D713EAFA5403563EB764476F937DFA97682EBF19CEACC5223585AE7702314AC45F3851F837868B5E82B59ECD663A63B5ACB6371";
		
		System.out.println("当前时间：" + up.obtainCurrPwd(time, dbSeed));
		
		dbSeed = "7C80090DA498C23F0471FD79A932BCB42CDCF21B6D7F7AECFCF0FA0C0EC869BEBA0BCC721A84F8965D4122D8E965863C6EAC7F67570A8D451088E5E1FF6FED1F";
		
		System.out.println("当前时间：" + up.obtainCurrPwd(time, dbSeed));*/
		
	}

}
