package com.sinopec.siam.am.idp.authn.util.dyncpwd;

import java.util.Random;

public class SeedCreate {

	/*public static void main(String[] args) throws IOException {
		SeedCreate sc = new SeedCreate();
		if (args == null || args.length == 0) {
			sc.PrintUse();
			return;
		} else {
			String[] arg = args[0].split(":");
			if (arg.length != 2) {
				sc.PrintUse();
				return;
			}
			if (arg[0].equalsIgnoreCase("-u")) {
				String seed = sc.CreateSeed(arg[1]);
				sc.Write(arg[1]+".dat", seed);
			} else if (arg[0].equalsIgnoreCase("-f")) {
				//String path = System.getProperty("user.dir") + arg[1];
				//System.out.println(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(arg[1])));
				String uid = null;
				while ((uid=br.readLine())!=null)
				{
					String seed = sc.CreateSeed(uid);
					sc.Write(uid+".dat", seed);
				}
			}else{
				sc.PrintUse();
			}				
		}
	}

	public void PrintUse() {
		System.out.println("java com.talkweb.Seed.SeedCreate -u:uid");
		System.out.println("OR");
		System.out.println("java com.talkweb.Seed.SeedCreate -f:filename");
	}

	public void Write(String file, String seed) throws IOException {
		FileWriter fw = new FileWriter(file);
		fw.write(seed);
		fw.flush();
		fw.close();
	}*/

	public static String CreateSeed(String uid) {
		String result = "";
		String key = "";
		byte[] rs = new byte[8];
		
		Random r = new Random();//必须随机产生，用作产生密码的秘钥
		r.nextBytes(rs);
		
		for (int i = 0; i < 8; i++)
			key += new Integer(rs[i]).toString() + ",";
		key = key.substring(0, key.length() - 2); // 去掉最后一个 ,//真实秘钥
		
		key = uid + "," + key;//混淆秘钥而加上了用户名称，每个用户混淆后都不同
		//System.out.println(key);
		int l = key.length() % 8;
		if ( l !=0 ) //需要在后面补,
		{
			int xx = (key.length()/8+1)*8 - key.length(); 
			for(int i=0;i<xx;i++)
				key += ',';		
		}
		
		// 加密
		byte[] encoded = ThreeDes
				.encryptMode(ThreeDes.keyBytes, key.getBytes());
		result = ThreeDes.byte2hex(encoded);
		//result = new String(encoded);
		//System.out.println(result);
		return result;
	}
	
	/**产生固定的用户密钥,非随机数产生密钥方式
	 * @param uid   用户标示
	 * @return 返回对应用户密钥
	 */
	public static String CreateSeed2(String uid) {
		String result = "";
		String key = "";
		
		//byte[] rs = new byte[8];
		//Random r = new Random();
		//r.nextBytes(rs);
		
		//byte[] rs = {1, 2, 3, 4, 5, 6, 7, 8};
		byte[] rs = {-55, 43, 101, -80, -111, -55, 116, -9};
		
		for (int i = 0; i < 8; i++)
			key += new Integer(rs[i]).toString() + ",";
		key = key.substring(0, key.length() - 2); // 去掉最后一个 ,
		key = uid + "," + key;
		//System.out.println(key);
		int l = key.length() % 8;
		if ( l !=0 ) //需要在后面补,
		{
			int xx = (key.length()/8+1)*8 - key.length(); 
			for(int i=0;i<xx;i++)
				key += ',';		
		}
		// 加密
		byte[] encoded = ThreeDes
				.encryptMode(ThreeDes.keyBytes, key.getBytes());
		result = ThreeDes.byte2hex(encoded);
		//result = new String(encoded);
		//System.out.println(result);
		return result;
	}
}
