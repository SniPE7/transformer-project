package com.ibm.ncs.web.policytakeeffect;

import java.util.Map;

public interface PolicyValidationProcess {

	public abstract void init();

	public abstract void startProcess();

	public abstract void stopProcess();

	public abstract boolean isDone();

	public abstract Map<String, String> getStat();

	public abstract boolean isSuccess();

}