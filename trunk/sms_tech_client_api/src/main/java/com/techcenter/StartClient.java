package com.techcenter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

import com.techcenter.api.DeliverReceiver;
import com.techcenter.api.ReportReceiver;
import com.techcenter.api.SubmitSender;
import com.techcenter.client.engine.ClientEngine;
import com.techcenter.client.engine.ConnectorFactory;
import com.techcenter.client.handler.ClientIoHandler;
import com.techcenter.protocol.standard12.Standard_Login;

public class StartClient {
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

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

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
