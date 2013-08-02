package com.techcenter;

import com.techcenter.api.SubmitSender;

public class TestMain {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		SpringSMSClient springSMSClient = new SpringSMSClient();
		
		springSMSClient.init();
		System.out.println("init()------------------success");
		
		SubmitSender submitSender = springSMSClient.getSubmitSender();
		System.out.println("get sender------------------success");
		
		String mobile = "13917852966";
		String msg = "jkf";

		boolean ret;
		for (int i = 0; i < 10; i++) {
			String message = msg + (i+1);
			Thread.sleep(1000);
			ret = submitSender.send(mobile, message);
			System.out.println("send message result  " + (i+1) + ": " + ret);
		}
	}

}
