package com.ibm.ncs.util.excel;

public class ExcelTableMapping {

	String name;	//column name in db
	String type;	//column type in DB
	boolean isNull;	//is null in db
	int index;		//column index in excel
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isNull() {
		return isNull;
	}
	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
	public String toString(){
		return index+":"+name+":"+type+":"+isNull;
	}
	
}
