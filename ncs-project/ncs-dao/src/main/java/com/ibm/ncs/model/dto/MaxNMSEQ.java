package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MaxNMSEQ implements Serializable
{
	protected long max;

	/** 
	 * This attribute represents whether the primitive attribute max is null.
	 */
	protected boolean maxNull = true;

	/**
	 * Method 'MaxNMSEQ'
	 * 
	 */
	public MaxNMSEQ()
	{
	}

	/**
	 * Method 'getMax'
	 * 
	 * @return long
	 */
	public long getMax()
	{
		return max;
	}

	/**
	 * Method 'setMax'
	 * 
	 * @param max
	 */
	public void setMax(long max)
	{
		this.max = max;
	}

	/** 
	 * Sets the value of maxNull
	 */
	public void setMaxNull(boolean maxNull)
	{
		this.maxNull = maxNull;
	}

	/** 
	 * Gets the value of maxNull
	 */
	public boolean isMaxNull()
	{
		return maxNull;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.MaxNMSEQ: " );
		ret.append( "max=" + max );
		return ret.toString();
	}

}
