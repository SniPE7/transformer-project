package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SeqTask implements Serializable
{
	protected java.lang.Long genTaskID;

	/**
	 * Method 'SeqTask'
	 * 
	 */
	public SeqTask()
	{
	}

	/**
	 * Method 'getGenTaskID'
	 * 
	 * @return java.lang.Long
	 */
	public java.lang.Long getGenTaskID()
	{
		return genTaskID;
	}

	/**
	 * Method 'setGenTaskID'
	 * 
	 * @param genTaskID
	 */
	public void setGenTaskID(java.lang.Long genTaskID)
	{
		this.genTaskID = genTaskID;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.SeqTask: " );
		ret.append( "genTaskID=" + genTaskID );
		return ret.toString();
	}

}
