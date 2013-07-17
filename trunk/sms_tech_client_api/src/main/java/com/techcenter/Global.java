package com.techcenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.apache.mina.handler.demux.ExceptionHandler;
import org.apache.mina.handler.demux.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techcenter.client.api.IDeliverReceiver;
import com.techcenter.client.api.IReportReceiver;
import com.techcenter.client.engine.ConnectorFactory;
import com.techcenter.client.engine.IMessageTool;
import com.techcenter.client.msghandler.CommonExceptionHandler;
import com.techcenter.client.msghandler.receiver.ContentMSSRespReceiverHandler;
import com.techcenter.client.msghandler.receiver.DeliverReceiverHandler;
import com.techcenter.client.msghandler.receiver.LoginRespReceiverHandler;
import com.techcenter.client.msghandler.receiver.ReportReceiverHandler;
import com.techcenter.client.msghandler.receiver.SubmitRespReceiverHandler;
import com.techcenter.client.sender.MsgSendAdapter;
import com.techcenter.protocol.base.IDataPackage;
import com.techcenter.protocol.standard12.Standard_Content_MMS_Resp;
import com.techcenter.protocol.standard12.Standard_Deliver;
import com.techcenter.protocol.standard12.Standard_Login_Resp;
import com.techcenter.protocol.standard12.Standard_Report;
import com.techcenter.protocol.standard12.Standard_Submit_Response;
import com.techcenter.queue.MemoryQueue;
import com.techcenter.util.PropertyFileReader;

public class Global {
	private static final Logger logger = LoggerFactory.getLogger(Global.class);

	private static Global global = null;
	// ip地址
	public String ip = "192.168.1.61";
	// 端口
	public int port = 1236;
	// 客户端ID
	public int clientId = 1006;
	// 滑动窗口大小
	public int controlWindowSize = 500;
	// 登录用户名
	public String loginName = "1006";
	// 密码
	public String password = "1006";
	// idle时间
	public int idleTime = 10;
	// 接收端口缓冲大小
	public int inBufferSize = 1024;
	// 发送端口缓冲大小
	public int outBufferSize = 1024;
	public String longCode = "";
	public String resourceFilePath = "D:/logs";
	public String copyPath = "D:/copyBak";
	public String resourceIdFilePath = "d:/properties";
	public String submitBakPath = "d:/submitBak";
	public String submitPath = "d:/submit";
	private MemoryQueue queue = new MemoryQueue();
	private int keepDate = 7;
	public long maxQueueSize = 10000;
	public long clearTimeOut = 60000;
	public long clearSleepTime = 30000;
	
	private Global() {
		/*try {
			PropertyFileReader propertyFileReader = PropertyFileReader
					.getInstance("./conf/config.properties");
			ip = propertyFileReader.getPropertyValues("ip");
			clientId = propertyFileReader.getPropertyIntValues("clientId");
			port = propertyFileReader.getPropertyIntValues("port");
			loginName = propertyFileReader.getPropertyValues("loginName");
			password = propertyFileReader.getPropertyValues("password");
			resourceFilePath = propertyFileReader
					.getPropertyValues("resourceFilePath");
			copyPath = propertyFileReader.getPropertyValues("copyPath");
			resourceIdFilePath = propertyFileReader
					.getPropertyValues("resourceIdFilePath");
			submitPath=propertyFileReader.getPropertyValues("submitPath");
			submitBakPath=propertyFileReader.getPropertyValues("submitBakPath");
			longCode=propertyFileReader.getPropertyValues("longCode");
			controlWindowSize = propertyFileReader.getPropertyIntValues("controlWindowSize");
			
			keepDate = propertyFileReader.getPropertyIntValues("keepDate");
			maxQueueSize = propertyFileReader.getPropertyLongValues("maxQueueSize");
			clearTimeOut = propertyFileReader.getPropertyLongValues("clearTimeOut");
			clearSleepTime = propertyFileReader.getPropertyLongValues("clearSleepTime");
			idleTime = propertyFileReader.getPropertyIntValues("idleTime");
			inBufferSize = propertyFileReader.getPropertyIntValues("inBufferSize");
			outBufferSize = propertyFileReader.getPropertyIntValues("outBufferSize");
			
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}*/
	}

	public synchronized static Global getInstance() {
		if(global == null){
			global = new Global();
		}
		return global;
	}

	public static HashMap<Class<Throwable>, ExceptionHandler<Throwable>> getExceptionHandlerList() {
		HashMap<Class<Throwable>, ExceptionHandler<Throwable>> ret = new HashMap<Class<Throwable>, ExceptionHandler<Throwable>>();
		ret.put(Throwable.class, new CommonExceptionHandler());
		return ret;
	}

	private static HashMap<Class<IDataPackage>, MessageHandler<IDataPackage>> getReceivedHandlerList() {
		HashMap<Class<IDataPackage>, MessageHandler<IDataPackage>> ret = new HashMap<Class<IDataPackage>, MessageHandler<IDataPackage>>();
		try {
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Active_Resp"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.client.msghandler.receiver.ActiveRespReceiverHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Deliver"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.client.msghandler.receiver.DeliverReceiverHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Login_Resp"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.client.msghandler.receiver.LoginRespReceiverHandler")
									.newInstance());
			// ret.put((Class<IDataPackage>)Class.forName("com.techcenter.protocol.standard12.Standard_Logout_Resp"),
			// (MessageHandler<IDataPackage>)
			// Class.forName("com.techcenter.client.msghandler.receiver.LogoutRespHandler").newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Report"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.client.msghandler.receiver.ReportReceiverHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Submit_Response"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.client.msghandler.receiver.SubmitRespReceiverHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Content_MMS_Resp"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.client.msghandler.receiver.ContentMSSRespReceiverHandler")
									.newInstance());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		return ret;
	}

	public static HashMap<Class<IDataPackage>, MessageHandler<IDataPackage>> getSentHandlerList() {
		HashMap<Class<IDataPackage>, MessageHandler<IDataPackage>> ret = new HashMap<Class<IDataPackage>, MessageHandler<IDataPackage>>();

		try {
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Active"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.codec.active.StandardActiveHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Deliver_Resp"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.codec.deliverresp.StandardDeliverRespHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Login"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.codec.login.StandardLoginHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Logout"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.codec.logout.StandardLogoutHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Report_Resp"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.codec.reportresp.StandardReportRespHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Submit"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.codec.submit.StandardSubmitHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Submit_MMS"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.codec.submitmms.StandardSubmitMMSHandler")
									.newInstance());
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Content_MMS"),
							(MessageHandler<IDataPackage>) Class
									.forName(
											"com.techcenter.codec.contentmms.StandardContentMMSHandler")
									.newInstance());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		return ret;
	}

	public static HashMap<Class<IDataPackage>, Class<MessageEncoder<?>>> getEncoderMap() {
		HashMap<Class<IDataPackage>, Class<MessageEncoder<?>>> ret = new HashMap<Class<IDataPackage>, Class<MessageEncoder<?>>>();

		try {
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Active"),
							(Class<MessageEncoder<?>>) Class
									.forName("com.techcenter.codec.active.StandardActiveEncoder"));
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Deliver_Resp"),
							(Class<MessageEncoder<?>>) Class
									.forName("com.techcenter.codec.deliverresp.StandardDeliverRespEncoder"));
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Login"),
							(Class<MessageEncoder<?>>) Class
									.forName("com.techcenter.codec.login.StandardLoginEncoder"));
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Logout"),
							(Class<MessageEncoder<?>>) Class
									.forName("com.techcenter.codec.logout.StandardLogoutEncoder"));
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Report_Resp"),
							(Class<MessageEncoder<?>>) Class
									.forName("com.techcenter.codec.reportresp.StandardReportRespEncoder"));
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Submit"),
							(Class<MessageEncoder<?>>) Class
									.forName("com.techcenter.codec.submit.StandardSubmitEncoder"));
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Submit_MMS"),
							(Class<MessageEncoder<?>>) Class
									.forName("com.techcenter.codec.submitmms.StandardSubmitMMSEncoder"));
			ret
					.put(
							(Class<IDataPackage>) Class
									.forName("com.techcenter.protocol.standard12.Standard_Content_MMS"),
							(Class<MessageEncoder<?>>) Class
									.forName("com.techcenter.codec.contentmms.StandardContentMMSEncoder"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		return ret;
	}

	public static List<Class<MessageDecoder>> getDecoderList() {
		List<Class<MessageDecoder>> ret = new ArrayList<Class<MessageDecoder>>();
		try {
			ret
					.add((Class<MessageDecoder>) Class
							.forName("com.techcenter.codec.activeresp.StandardActiveRespDecoder"));
			ret
					.add((Class<MessageDecoder>) Class
							.forName("com.techcenter.codec.deliver.StandardDeliverDecoder"));
			ret
					.add((Class<MessageDecoder>) Class
							.forName("com.techcenter.codec.loginresp.StandardLoginRespDecoder"));
			ret
					.add((Class<MessageDecoder>) Class
							.forName("com.techcenter.codec.logoutresp.StandardLogoutRespDecoder"));
			ret
					.add((Class<MessageDecoder>) Class
							.forName("com.techcenter.codec.report.StandardReportDecoder"));
			ret
					.add((Class<MessageDecoder>) Class
							.forName("com.techcenter.codec.submitresponse.StandardSubmitResponseDecoder"));
			ret
					.add((Class<MessageDecoder>) Class
							.forName("com.techcenter.codec.contentmmsresp.StandardContentMMSRespDecoder"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		return ret;
	}

	// 接收协议处理Map
	private  HashMap<Class<IDataPackage>, MessageHandler<IDataPackage>> receivedHandlerList = null;
	private  IMessageTool submitSender;
	private  IDeliverReceiver deliverReceiver;
	private  IReportReceiver reportRecceiver;

	public  HashMap<Class<IDataPackage>, MessageHandler<IDataPackage>> getHandler() {
		if (receivedHandlerList == null) {
			receivedHandlerList = getReceivedHandlerList();
		}
		if (submitSender == null) {
			throw new RuntimeException("submitSender is null");
		}
		if (deliverReceiver == null) {
			throw new RuntimeException("deliverReceiver is null");
		}
		if (reportRecceiver == null) {
			throw new RuntimeException("reportRecceiver is null");
		}
		// 发送对象拦截适配器
		MsgSendAdapter msgSendAdapter = new MsgSendAdapter();
		msgSendAdapter.setTool(submitSender);

		// 上行接收处理handler注入reportRecceiver
		DeliverReceiverHandler deliverHandler = (DeliverReceiverHandler) receivedHandlerList
				.get(Standard_Deliver.class);
		deliverHandler.setReceiver(deliverReceiver);

		// 登录响应接收handler注入loginHandler
		LoginRespReceiverHandler loginHandler = (LoginRespReceiverHandler) receivedHandlerList
				.get(Standard_Login_Resp.class);
		loginHandler.setTool(msgSendAdapter);

		// 下行响应接收handler注入submitRespHandler
		SubmitRespReceiverHandler submitRespHandler = (SubmitRespReceiverHandler) receivedHandlerList
				.get(Standard_Submit_Response.class);

		// 状态报告接收handler注入reportHandler
		ReportReceiverHandler reportHandler = (ReportReceiverHandler) receivedHandlerList
				.get(Standard_Report.class);
		reportHandler.setReceiver(reportRecceiver);

		// 彩信资源响应接收handler注入contentmmsHandler
		ContentMSSRespReceiverHandler contentmmsHandler = (ContentMSSRespReceiverHandler) receivedHandlerList
				.get(Standard_Content_MMS_Resp.class);

		return receivedHandlerList;
	}

	public  IMessageTool getSubmitSender() {
		return submitSender;
	}

	public  void setSubmitSender(IMessageTool submitSender) {
		this.submitSender = submitSender;
	}

	public  IDeliverReceiver getDeliverReceiver() {
		return deliverReceiver;
	}

	public  void setDeliverReceiver(IDeliverReceiver deliverReceiver) {
		this.deliverReceiver = deliverReceiver;
	}

	public  IReportReceiver getReportRecceiver() {
		return reportRecceiver;
	}

	public  void setReportRecceiver(IReportReceiver reportRecceiver) {
		this.reportRecceiver = reportRecceiver;
	}

	public ConnectorFactory getConnectorFactory(IoHandler mohandler) {
		ConnectorFactory mocf = new ConnectorFactory();
		mocf.setCount(1);
		mocf.setHandler(mohandler);
		mocf.setHost(ip);
		mocf.setPort(port);
		return mocf;
	}

	public String getResourceFilePath() {
		return resourceFilePath;
	}

	public String getCopyPath() {
		return copyPath;
	}

	public String getResourceIdFilePath() {
		return resourceIdFilePath;
	}

	public MemoryQueue getQueue() {
		return queue;
	}

	public void setQueue(MemoryQueue queue) {
		this.queue = queue;
	}

	public int getKeepDate() {
		return keepDate;
	}

	public void setKeepDate(int keepDate) {
		this.keepDate = keepDate;
	}

	public int getInBufferSize() {
		return inBufferSize;
	}

	public void setInBufferSize(int inBufferSize) {
		this.inBufferSize = inBufferSize;
	}

	public int getOutBufferSize() {
		return outBufferSize;
	}

	public void setOutBufferSize(int outBufferSize) {
		this.outBufferSize = outBufferSize;
	}

	public long getClearTimeOut() {
		return clearTimeOut;
	}

	public void setClearTimeOut(long clearTimeOut) {
		this.clearTimeOut = clearTimeOut;
	}

	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}
}
