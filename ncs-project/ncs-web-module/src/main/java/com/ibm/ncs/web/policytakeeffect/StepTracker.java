package com.ibm.ncs.web.policytakeeffect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class StepTracker {
	private Map<String, String> stat = new LinkedHashMap<String, String>();

	private int step;

	private boolean done = false;

	private String setKS(int steps) {
		String ss = (steps < 10 ? "0" : "") + steps;
		return ss;
	}

	public void start() {
		this.step = 1;
		this.done = false;
		this.stat.clear();
	}
	
	public void writeState(String msg) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	  stat.put(setKS(step++), String.format("%s %s", sdf.format(new Date()), msg));
  }
	
	public void done() {
		this.done  = true;
	}
	
	public Map<String, String> getState() {
		Map<String, String> result = new LinkedHashMap<String, String>(this.stat);
		if (this.done) {
			result.put("  ", "<div>已经处理完成</div> ");
			return result;
		} else {
			result.put("  ", "<div><img src='../../images/icon_progress.gif'> 正在处理中 ... ...</div> ");
		  return result;
		}
	}
	
}