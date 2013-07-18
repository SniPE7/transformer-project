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
		Standard_Report report=(Standard_Report)obj;     // ת��ΪStandard_Report����
		LogObject.logReport("report:" + report);         // ���report��־ 
		/** �����ĸ��Ƿ���״̬�ļ�����Ҫֵ
		report.getSequenceId();       // �뷢��ʱ���õ�SequenceIdһ��
		report.getDestMobile();       // ����Ŀ���ֻ��ţ�����û�һ�������Ͷ���ţ��ü����ֻ���������������״̬
		report.getState();            // ��Ϣ״̬�� 0 ���ͳɹ� 1 ����ʧ�� 2 ����ʧ��
		report.getErrorCode();        // ��Ϣ������
		**/
	}
}
