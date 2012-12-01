/**
 * 
 */
package com.ibm.ncs.model.facade;

import com.ibm.ncs.model.dto.*;

/**
 * @author dlee
 * 
 */
public interface baseInfoFacade {

	
	TCategoryMapInit getCategory();
	
	TManufacturerInfoInit getManufacturer();
	
	TDeviceTypeInit getDeviceType();
	
	TEventTypeInit getEventType();
	
	//TEventSubTypeSub
	
	
}
