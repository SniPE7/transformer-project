package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SequenceNM implements Serializable
{
	protected java.lang.Long genID;

	/**
	 * Method 'SequenceNM'
	 * 
	 */
	public SequenceNM()
	{
	}

	/**
	 * Method 'getGenID'
	 * 
	 * @return java.lang.Long
	 */
	public java.lang.Long getGenID()
	{
		return genID;
	}

	/**
	 * Method 'setGenID'
	 * 
	 * @param genID
	 */
	public void setGenID(java.lang.Long genID)
	{
		this.genID = genID;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.SequenceNM: " );
		ret.append( "genID=" + genID );
		return ret.toString();
	}

}
