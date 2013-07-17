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
		//ssm.setMessageFormat((short) 32); // ��Ϣ�ı����ʽ 15 GBK���� 32 ������
		ssm.setContentString(msg); // ��Ϣ������
		//ssm.setSrcNumber(""); // �·���Դ����
		ssm.setDestMobile(destMobile); // Ŀ���ֻ���
		
		//send to mq
		try {
			bResult = this.sendQueue.push(ssm);
		} catch (Exception e) {
			logger.error("send ssm to queue error:" + e.getLocalizedMessage(), e);
		}
		
		return bResult;
	}
	
	private Standard_Submit createOneSendMessage(){
		Standard_Submit ssm = new Standard_Submit(); // ����һ�����ŷ��Ͷ���
		
		ssm.setMessagePriority((short) 3); // ��Ϣ�����ȼ���0 ��� --- 3 ���
		ssm.setReportType((short) 0); // �Ƿ���Ҫ״̬���棺0 ����Ҫ��1 ��Ҫ
		ssm.setSequenceId(System.currentTimeMillis()); // Ψһ���к�
		ssm.setProductID(Global.getInstance().clientId); // ��Ʒid
		//ssm.setMessageFormat((short) 32); // ��Ϣ�ı����ʽ 15 GBK���� 32 ������
		//ssm.setContentString("һ�����������߰˾�ʮ"); // ��Ϣ������
		//ssm.setSrcNumber(""); // �·���Դ����
		//ssm.setDestMobile("15000819806"); // Ŀ���ֻ���
		
		return ssm;
	}

	@Override
	public Standard_Head send() {
		if(this.sendQueue == null) {
			return null;
		}

		Standard_Submit currentSSM = (Standard_Submit)this.sendQueue.pop();
		if(currentSSM!=null) {
			logger.debug("Standard_Head[SubmiSender.send]-->" + currentSSM.toString());
		}
		
		return currentSSM; // ���ط��Ͷ������������ʱ����null
	}

	@Override
	public void receive(Standard_Head submit, Standard_Head response) {
		LogObject.logSubmit("submit-->" + submit); // ������־
		LogObject.logResponse("response-->" + response); // ��Ӧ��־
		/**
		 * ������response���صļ�����Ҫֵ response.getSequenceId(); // �뷢��ʱ���õ�SequenceIdһ��
		 * response.getCommandStatus(); //
		 * response����״̬�룬Ϊ0�ǽ��ճɹ�������Ϊ���󣬴���ԭ��ο������������ƶ���Ϣ������ƽ̨ͨ��ҵ��ӿ�Э�顷�ĵ�
		 */
	}
}
