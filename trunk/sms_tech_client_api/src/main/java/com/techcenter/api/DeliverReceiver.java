package com.techcenter.api;

import com.techcenter.LogObject;
import com.techcenter.client.api.IDeliverReceiver;
import com.techcenter.protocol.standard12.Standard_Deliver;

public class DeliverReceiver implements IDeliverReceiver {
	
	@Override
	public void receive(Standard_Deliver obj) {
		LogObject.logdeliver("mobile:" + obj.getSrcMobile() + " context:"
				+ obj.getContentString() + " number:" + obj.getDestNumber());
	}
}
