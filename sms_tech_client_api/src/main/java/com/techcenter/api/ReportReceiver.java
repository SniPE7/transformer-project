package com.techcenter.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techcenter.LogObject;
import com.techcenter.client.api.IReportReceiver;
import com.techcenter.protocol.standard12.Standard_Head;
import com.techcenter.protocol.standard12.Standard_Report;

public class ReportReceiver implements IReportReceiver {
	
	protected static final Logger logger = LoggerFactory.getLogger(ReportReceiver.class);
	
	public void receive(Standard_Head obj) {
		Standard_Report report=(Standard_Report)obj;     // 转换为Standard_Report对象
		LogObject.logReport("report:" + report);         // 输出report日志 
		/** 下面四个是返回状态的几个重要值
		report.getSequenceId();       // 与发送时设置的SequenceId一至
		report.getDestMobile();       // 发送目标手机号，如果用户一个包发送多个号，得加上手机号做条件来更新状态
		report.getState();            // 消息状态， 0 发送成功 1 网关失败 2 发送失败
		report.getErrorCode();        // 消息错误码
		**/
	}
}
