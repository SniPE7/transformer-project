package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IpAddrRangeCheck implements Serializable
{
	protected long ipdecodeMin;

	/** 
	 * This attribute represents whether the primitive attribute ipdecodeMin is null.
	 */
	protected boolean ipdecodeMinNull = true;

	protected long ipdecodeMax;

	/** 
	 * This attribute represents whether the primitive attribute ipdecodeMax is null.
	 */
	protected boolean ipdecodeMaxNull = true;

	/**
	 * Method 'IpAddrRangeCheck'
	 * 
	 */
	public IpAddrRangeCheck()
	{
	}

	/**
	 * Method 'getIpdecodeMin'
	 * 
	 * @return long
	 */
	public long getIpdecodeMin()
	{
		return ipdecodeMin;
	}

	/**
	 * Method 'setIpdecodeMin'
	 * 
	 * @param ipdecodeMin
	 */
	public void setIpdecodeMin(long ipdecodeMin)
	{
		this.ipdecodeMin = ipdecodeMin;
	}

	/** 
	 * Sets the value of ipdecodeMinNull
	 */
	public void setIpdecodeMinNull(boolean ipdecodeMinNull)
	{
		this.ipdecodeMinNull = ipdecodeMinNull;
	}

	/** 
	 * Gets the value of ipdecodeMinNull
	 */
	public boolean isIpdecodeMinNull()
	{
		return ipdecodeMinNull;
	}

	/**
	 * Method 'getIpdecodeMax'
	 * 
	 * @return long
	 */
	public long getIpdecodeMax()
	{
		return ipdecodeMax;
	}

	/**
	 * Method 'setIpdecodeMax'
	 * 
	 * @param ipdecodeMax
	 */
	public void setIpdecodeMax(long ipdecodeMax)
	{
		this.ipdecodeMax = ipdecodeMax;
	}

	/** 
	 * Sets the value of ipdecodeMaxNull
	 */
	public void setIpdecodeMaxNull(boolean ipdecodeMaxNull)
	{
		this.ipdecodeMaxNull = ipdecodeMaxNull;
	}

	/** 
	 * Gets the value of ipdecodeMaxNull
	 */
	public boolean isIpdecodeMaxNull()
	{
		return ipdecodeMaxNull;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.IpAddrRangeCheck: " );
		ret.append( "ipdecodeMin=" + ipdecodeMin );
		ret.append( ", ipdecodeMax=" + ipdecodeMax );
		return ret.toString();
	}

}
