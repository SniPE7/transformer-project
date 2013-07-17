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
			// 初始化log配置
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
		//客户下行发送类   该类是测试的例子，实际项目由客户自行开发
		SubmitSender submitSender = new SubmitSender(g.getQueue());
		//MMSSubmitSender submitSender=new MMSSubmitSender();
		//接收上行类   该类是测试的例子，实际项目由客户自行开发
		DeliverReceiver deliverReceiver = new DeliverReceiver();
		//接收状态报告类  该类是测试的例子，实际项目由客户自行开发
		ReportReceiver reportRecceiver = new ReportReceiver();
		
		//初始化客户的发送接收类
		g.setDeliverReceiver(deliverReceiver);
		g.setReportRecceiver(reportRecceiver);
		g.setSubmitSender(submitSender);
				
		//下行处理handler
		ClientIoHandler mthandler = new ClientIoHandler();
		//设置ClientId
		mthandler.setClientId(g.clientId);
		//设置滑动窗口大小
		mthandler.setControlWindowsSize(g.controlWindowSize);
		//设置登录用户名
		mthandler.setLoginName(g.loginName);
		//设置密码
		mthandler.setPassword(g.password);
		//设置IdleTime
		mthandler.setIdleTime(g.idleTime);
		//接收端口缓冲大小
		mthandler.setInBufferSize(g.inBufferSize);
		//发送端口缓冲大小
		mthandler.setOutBufferSize(g.outBufferSize);
		//设置Decoder列表
		mthandler.setDecoderList(Global.getDecoderList());
		//设置EncoderMap对象
		mthandler.setEncoderMap(Global.getEncoderMap());
		//设置已发送handler列表
		mthandler.setSentHandlerList(Global.getSentHandlerList());
		//设置接收handler列表
		mthandler.setReceivedHandlerList(g.getHandler());
		//设置异常handler列表
		mthandler.setExceptionHandlerList(Global.getExceptionHandlerList());
		//设置登录属性
		mthandler.setSessionType(Standard_Login.SESSION_MT);
		//设置滑动窗口的清理时间
		mthandler.setClearTimeOut(g.clearTimeOut);
		//设置滑动窗口的休眠时间
		mthandler.setClearSleepTime(g.clearSleepTime);
		//设置重发标志
		mthandler.setReSend(false);
		
		//上行处理handler
		ClientIoHandler mohandler = new ClientIoHandler();
		//设置ClientId
		mohandler.setClientId(g.clientId);
		//设置滑动窗口大小
		mohandler.setControlWindowsSize(g.controlWindowSize);
		//设置登录用户名
		mohandler.setLoginName(g.loginName);
		//设置密码./s
		mohandler.setPassword(g.password);
		//设置IdleTime
		mohandler.setIdleTime(g.idleTime);
		//接收端口缓冲大小
		mohandler.setInBufferSize(g.inBufferSize);
		//发送端口缓冲大小
		mohandler.setOutBufferSize(g.outBufferSize);	
		//设置Decoder列表
		mohandler.setDecoderList(g.getDecoderList());
		//设置EncoderMap对象
		mohandler.setEncoderMap(g.getEncoderMap());
		//设置已发送handler列表
		mohandler.setSentHandlerList(g.getSentHandlerList());
		//设置接收handler列表
		mohandler.setReceivedHandlerList(g.getHandler());
		//设置异常handler列表
		mohandler.setExceptionHandlerList(g.getExceptionHandlerList());
		//设置登录属性
		mohandler.setSessionType(Standard_Login.SESSION_MO);
		//设置滑动窗口的清理时间
		mohandler.setClearTimeOut(g.clearTimeOut);
		//设置滑动窗口的休眠时间
		mohandler.setClearSleepTime(g.clearSleepTime);
		//设置重发标志
		mohandler.setReSend(false);
		
		
		//handler列表
		List<ConnectorFactory> conFactoryList = new ArrayList<ConnectorFactory>();
		//设置下行handler
		conFactoryList.add(g.getConnectorFactory(mthandler));
		//设置上行handler
		conFactoryList.add(g.getConnectorFactory(mohandler));
		
		//客户端引擎
		ClientEngine ce = new ClientEngine();
		ce.setRunning(true);
		ce.setConFactoryList(conFactoryList);
		//启动客户端引擎
		ce.start();
		//判断短信引擎连接状态
		System.out.println(ce.isConnected());
		
	}
}
