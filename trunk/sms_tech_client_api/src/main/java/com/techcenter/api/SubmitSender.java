package com.techcenter.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techcenter.Global;
import com.techcenter.LogObject;
import com.techcenter.client.api.ISubmitSender;
import com.techcenter.protocol.standard12.Standard_Head;
import com.techcenter.protocol.standard12.Standard_Submit;
import com.techcenter.queue.IQueue;

public class SubmitSender implements ISubmitSender {

	protected static final Logger logger = LoggerFactory
			.getLogger(SubmitSender.class);
	
	private IQueue sendQueue;

	public IQueue getSendQueue() {
		return sendQueue;
	}
	
	public void setSendQueue(IQueue sendQueue) {
		this.sendQueue = sendQueue;
	}
	
	public SubmitSender(IQueue sendQueue) {
		this.sendQueue = sendQueue;
	}
	
	public boolean send(String destMobile, String msg) {
		boolean bResult = false;
		
		if(null==destMobile || "".equals(destMobile) || null==msg || "".equals(msg)) {
			logger.error(String.format("send ssm error: Invalid Arguments[dest:%s, msg:%s]",  destMobile, msg));
			return bResult;
		}
		
		Standard_Submit ssm = createOneSendMessage();
		
		if(msg.length()<=60) {
			ssm.setMessageFormat((short) 15);
		} else {
			ssm.setMessageFormat((short) 32);
		}
		//ssm.setMessageFormat((short) 32); // 消息的编码格式 15 GBK编码 32 长短信
		ssm.setContentString(msg); // 消息的内容
		//ssm.setSrcNumber(""); // 下发的源号码
		ssm.setDestMobile(destMobile); // 目的手机号
		
		//send to mq
		try {
			bResult = this.sendQueue.push(ssm);
		} catch (Exception e) {
			logger.error("send ssm to queue error:" + e.getLocalizedMessage(), e);
		}
		
		return bResult;
	}
	
	private Standard_Submit createOneSendMessage(){
		Standard_Submit ssm = new Standard_Submit(); // 建立一个短信发送对象
		
		ssm.setMessagePriority((short) 3); // 消息的优先级：0 最低 --- 3 最高
		ssm.setReportType((short) 0); // 是否需要状态报告：0 不需要；1 需要
		ssm.setSequenceId(System.currentTimeMillis()); // 唯一序列号
		ssm.setProductID(Global.getInstance().clientId); // 产品id
		//ssm.setMessageFormat((short) 32); // 消息的编码格式 15 GBK编码 32 长短信
		//ssm.setContentString("一二三四五六七八九十"); // 消息的内容
		//ssm.setSrcNumber(""); // 下发的源号码
		//ssm.setDestMobile("15000819806"); // 目的手机号
		
		return ssm;
	}

	public Standard_Head send() {
		if(this.sendQueue == null) {
			return null;
		}

		Standard_Submit currentSSM = (Standard_Submit)this.sendQueue.pop();
		if(currentSSM!=null) {
			logger.debug("Standard_Head[SubmiSender.send]-->" + currentSSM.toString());
		}
		
		return currentSSM; // 返回发送对象，如果不发送时返回null
	}

	public void receive(Standard_Head submit, Standard_Head response) {
		LogObject.logSubmit("submit-->" + submit); // 发送日志
		LogObject.logResponse("response-->" + response); // 响应日志
		/**
		 * 下面是response返回的几个重要值 response.getSequenceId(); // 与发送时设置的SequenceId一至
		 * response.getCommandStatus(); //
		 * response返回状态码，为0是接收成功，其它为错误，错误原因参考《无线天利移动信息化服务平台通用业务接口协议》文档
		 */
	}
}
