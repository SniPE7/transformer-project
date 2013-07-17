package com.techcenter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techcenter.api.DeliverReceiver;
import com.techcenter.api.ReportReceiver;
import com.techcenter.api.SubmitSender;
import com.techcenter.client.engine.ClientEngine;
import com.techcenter.client.engine.ConnectorFactory;
import com.techcenter.client.handler.ClientIoHandler;
import com.techcenter.protocol.standard12.Standard_Login;

public class SpringSMSClient {
	
	private static final Logger logger = LoggerFactory.getLogger(SpringSMSClient.class);
	
	private SubmitSender submitSender = null;	
	
	public void setIp(String ip) {
		Global.getInstance().ip = ip;
	}

	public void setPort(int port) {
		Global.getInstance().port = port;
	}

	public void setClientId(int clientId) {
		Global.getInstance().clientId = clientId;
	}

	public void setControlWindowSize(int controlWindowSize) {
		Global.getInstance().controlWindowSize = controlWindowSize;
	}

	public void setLoginName(String loginName) {
		Global.getInstance().loginName = loginName;
	}

	public void setPassword(String password) {
		Global.getInstance().password = password;
	}

	public void setIdleTime(int idleTime) {
		Global.getInstance().idleTime = idleTime;
	}

	public void setInBufferSize(int inBufferSize) {
		Global.getInstance().inBufferSize = inBufferSize;
	}

	public void setOutBufferSize(int outBufferSize) {
		Global.getInstance().outBufferSize = outBufferSize;
	}

	public void setLongCode(String longCode) {
		Global.getInstance().longCode = longCode;
	}

	public void setResourceFilePath(String resourceFilePath) {
		Global.getInstance().resourceFilePath = resourceFilePath;
	}

	public void setCopyPath(String copyPath) {
		Global.getInstance().copyPath = copyPath;
	}

	public void setResourceIdFilePath(String resourceIdFilePath) {
		Global.getInstance().resourceIdFilePath = resourceIdFilePath;
	}

	public void setSubmitBakPath(String submitBakPath) {
		Global.getInstance().submitBakPath = submitBakPath;
	}

	public void setSubmitPath(String submitPath) {
		Global.getInstance().submitPath = submitPath;
	}

	public void setKeepDate(int keepDate) {
		Global.getInstance().setKeepDate(keepDate);
	}

	public void setMaxQueueSize(long maxQueueSize) {
		Global.getInstance().maxQueueSize = maxQueueSize;
	}

	public void setClearTimeOut(long clearTimeOut) {
		Global.getInstance().clearTimeOut = clearTimeOut;
	}

	public void setClearSleepTime(long clearSleepTime) {
		Global.getInstance().clearSleepTime = clearSleepTime;
	}

	public SubmitSender getSubmitSender() {
		return submitSender;
	}

	public void setSubmitSender(SubmitSender submitSender) {
		this.submitSender = submitSender;
	}

	/*static {
		String logbackCfg = "classpath:/conf/logback.xml";
		try {
			// ��ʼ��log����
			LoggerContext lc = (LoggerContext) LoggerFactory
					.getILoggerFactory();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(lc);
			lc.reset();
			configurator.doConfigure(logbackCfg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	//�������ŷ��ͺ�̨�߳�
	public void init() {
		
		Global g = Global.getInstance();
		//�ͻ����з�����   �����ǲ��Ե����ӣ�ʵ����Ŀ�ɿͻ����п���
		SubmitSender submitSender = new SubmitSender(g.getQueue());
		this.submitSender = submitSender;
		//MMSSubmitSender submitSender=new MMSSubmitSender();
		//����������   �����ǲ��Ե����ӣ�ʵ����Ŀ�ɿͻ����п���
		DeliverReceiver deliverReceiver = new DeliverReceiver();
		//����״̬������  �����ǲ��Ե����ӣ�ʵ����Ŀ�ɿͻ����п���
		ReportReceiver reportRecceiver = new ReportReceiver();
		
		//��ʼ���ͻ��ķ��ͽ�����
		g.setDeliverReceiver(deliverReceiver);
		g.setReportRecceiver(reportRecceiver);
		g.setSubmitSender(submitSender);
				
		//���д���handler
		ClientIoHandler mthandler = new ClientIoHandler();
		//����ClientId
		mthandler.setClientId(g.clientId);
		//���û������ڴ�С
		mthandler.setControlWindowsSize(g.controlWindowSize);
		//���õ�¼�û���
		mthandler.setLoginName(g.loginName);
		//��������
		mthandler.setPassword(g.password);
		//����IdleTime
		mthandler.setIdleTime(g.idleTime);
		//���ն˿ڻ����С
		mthandler.setInBufferSize(g.inBufferSize);
		//���Ͷ˿ڻ����С
		mthandler.setOutBufferSize(g.outBufferSize);
		//����Decoder�б�
		mthandler.setDecoderList(Global.getDecoderList());
		//����EncoderMap����
		mthandler.setEncoderMap(Global.getEncoderMap());
		//�����ѷ���handler�б�
		mthandler.setSentHandlerList(Global.getSentHandlerList());
		//���ý���handler�б�
		mthandler.setReceivedHandlerList(g.getHandler());
		//�����쳣handler�б�
		mthandler.setExceptionHandlerList(Global.getExceptionHandlerList());
		//���õ�¼����
		mthandler.setSessionType(Standard_Login.SESSION_MT);
		//���û������ڵ�����ʱ��
		mthandler.setClearTimeOut(g.clearTimeOut);
		//���û������ڵ�����ʱ��
		mthandler.setClearSleepTime(g.clearSleepTime);
		//�����ط���־
		mthandler.setReSend(false);
		
		//���д���handler
		ClientIoHandler mohandler = new ClientIoHandler();
		//����ClientId
		mohandler.setClientId(g.clientId);
		//���û������ڴ�С
		mohandler.setControlWindowsSize(g.controlWindowSize);
		//���õ�¼�û���
		mohandler.setLoginName(g.loginName);
		//��������./s
		mohandler.setPassword(g.password);
		//����IdleTime
		mohandler.setIdleTime(g.idleTime);
		//���ն˿ڻ����С
		mohandler.setInBufferSize(g.inBufferSize);
		//���Ͷ˿ڻ����С
		mohandler.setOutBufferSize(g.outBufferSize);	
		//����Decoder�б�
		mohandler.setDecoderList(g.getDecoderList());
		//����EncoderMap����
		mohandler.setEncoderMap(g.getEncoderMap());
		//�����ѷ���handler�б�
		mohandler.setSentHandlerList(g.getSentHandlerList());
		//���ý���handler�б�
		mohandler.setReceivedHandlerList(g.getHandler());
		//�����쳣handler�б�
		mohandler.setExceptionHandlerList(g.getExceptionHandlerList());
		//���õ�¼����
		mohandler.setSessionType(Standard_Login.SESSION_MO);
		//���û������ڵ�����ʱ��
		mohandler.setClearTimeOut(g.clearTimeOut);
		//���û������ڵ�����ʱ��
		mohandler.setClearSleepTime(g.clearSleepTime);
		//�����ط���־
		mohandler.setReSend(false);
		
		
		//handler�б�
		List<ConnectorFactory> conFactoryList = new ArrayList<ConnectorFactory>();
		//��������handler
		conFactoryList.add(g.getConnectorFactory(mthandler));
		//��������handler
		conFactoryList.add(g.getConnectorFactory(mohandler));
		
		//�ͻ�������
		ce = new ClientEngine();
		ce.setRunning(true);
		ce.setConFactoryList(conFactoryList);
		//�����ͻ�������
		ce.start();
		//�ж϶�����������״̬
		logger.info("*********** �������������ɹ�������״̬Ϊ:" + String.valueOf(ce.isConnected()));
		//System.out.println("*********** �������������ɹ�������״̬Ϊ:" + String.valueOf(ce.isConnected()));
		
	}
	
	private ClientEngine ce = null;
	
	//������ŷ��͵ĺ�̨�߳�
	public void destory() {
		if(null!=ce) {
			ce.stop();
			ce = null;
			
			//ֹͣ��������
			logger.info("*********** ��������ֹͣ�ɹ�");
		}
	}

	
	/**	For Test Start
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		//ֹͣ��������
		logger.info("***********log���� �������� Start...");

		Global g = Global.getInstance();
		//�ͻ����з�����   �����ǲ��Ե����ӣ�ʵ����Ŀ�ɿͻ����п���
		SubmitSender submitSender = new SubmitSender(g.getQueue());
		//MMSSubmitSender submitSender=new MMSSubmitSender();
		//����������   �����ǲ��Ե����ӣ�ʵ����Ŀ�ɿͻ����п���
		DeliverReceiver deliverReceiver = new DeliverReceiver();
		//����״̬������  �����ǲ��Ե����ӣ�ʵ����Ŀ�ɿͻ����п���
		ReportReceiver reportRecceiver = new ReportReceiver();
		
		//��ʼ���ͻ��ķ��ͽ�����
		g.setDeliverReceiver(deliverReceiver);
		g.setReportRecceiver(reportRecceiver);
		g.setSubmitSender(submitSender);
				
		//���д���handler
		ClientIoHandler mthandler = new ClientIoHandler();
		//����ClientId
		mthandler.setClientId(g.clientId);
		//���û������ڴ�С
		mthandler.setControlWindowsSize(g.controlWindowSize);
		//���õ�¼�û���
		mthandler.setLoginName(g.loginName);
		//��������
		mthandler.setPassword(g.password);
		//����IdleTime
		mthandler.setIdleTime(g.idleTime);
		//���ն˿ڻ����С
		mthandler.setInBufferSize(g.inBufferSize);
		//���Ͷ˿ڻ����С
		mthandler.setOutBufferSize(g.outBufferSize);
		//����Decoder�б�
		mthandler.setDecoderList(Global.getDecoderList());
		//����EncoderMap����
		mthandler.setEncoderMap(Global.getEncoderMap());
		//�����ѷ���handler�б�
		mthandler.setSentHandlerList(Global.getSentHandlerList());
		//���ý���handler�б�
		mthandler.setReceivedHandlerList(g.getHandler());
		//�����쳣handler�б�
		mthandler.setExceptionHandlerList(Global.getExceptionHandlerList());
		//���õ�¼����
		mthandler.setSessionType(Standard_Login.SESSION_MT);
		//���û������ڵ�����ʱ��
		mthandler.setClearTimeOut(g.clearTimeOut);
		//���û������ڵ�����ʱ��
		mthandler.setClearSleepTime(g.clearSleepTime);
		//�����ط���־
		mthandler.setReSend(false);
		
		//���д���handler
		ClientIoHandler mohandler = new ClientIoHandler();
		//����ClientId
		mohandler.setClientId(g.clientId);
		//���û������ڴ�С
		mohandler.setControlWindowsSize(g.controlWindowSize);
		//���õ�¼�û���
		mohandler.setLoginName(g.loginName);
		//��������./s
		mohandler.setPassword(g.password);
		//����IdleTime
		mohandler.setIdleTime(g.idleTime);
		//���ն˿ڻ����С
		mohandler.setInBufferSize(g.inBufferSize);
		//���Ͷ˿ڻ����С
		mohandler.setOutBufferSize(g.outBufferSize);	
		//����Decoder�б�
		mohandler.setDecoderList(g.getDecoderList());
		//����EncoderMap����
		mohandler.setEncoderMap(g.getEncoderMap());
		//�����ѷ���handler�б�
		mohandler.setSentHandlerList(g.getSentHandlerList());
		//���ý���handler�б�
		mohandler.setReceivedHandlerList(g.getHandler());
		//�����쳣handler�б�
		mohandler.setExceptionHandlerList(g.getExceptionHandlerList());
		//���õ�¼����
		mohandler.setSessionType(Standard_Login.SESSION_MO);
		//���û������ڵ�����ʱ��
		mohandler.setClearTimeOut(g.clearTimeOut);
		//���û������ڵ�����ʱ��
		mohandler.setClearSleepTime(g.clearSleepTime);
		//�����ط���־
		mohandler.setReSend(false);
		
		
		//handler�б�
		List<ConnectorFactory> conFactoryList = new ArrayList<ConnectorFactory>();
		//��������handler
		conFactoryList.add(g.getConnectorFactory(mthandler));
		//��������handler
		conFactoryList.add(g.getConnectorFactory(mohandler));
		
		//�ͻ�������
		ClientEngine ce = new ClientEngine();
		ce.setRunning(true);
		ce.setConFactoryList(conFactoryList);
		//�����ͻ�������
		ce.start();
		//�ж϶�����������״̬
		System.out.println(ce.isConnected());
		
	}
}
