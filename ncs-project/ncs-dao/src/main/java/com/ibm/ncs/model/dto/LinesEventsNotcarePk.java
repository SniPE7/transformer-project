package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the LINES_EVENTS_NOTCARE table.
 */
public class LinesEventsNotcarePk implements Serializable
{
	protected String linesnotcare;

	/** 
	 * Sets the value of linesnotcare
	 */
	public void setLinesnotcare(String linesnotcare)
	{
		this.linesnotcare = linesnotcare;
	}

	/** 
	 * Gets the value of linesnotcare
	 */
	public String getLinesnotcare()
	{
		return linesnotcare;
	}

	/**
	 * Method 'LinesEventsNotcarePk'
	 * 
	 */
	public LinesEventsNotcarePk()
	{
	}

	/**
	 * Method 'LinesEventsNotcarePk'
	 * 
	 * @param linesnotcare
	 */
	public LinesEventsNotcarePk(final String linesnotcare)
	{
		this.linesnotcare = linesnotcare;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof LinesEventsNotcarePk)) {
			return false;
		}
		
		final LinesEventsNotcarePk _cast = (LinesEventsNotcarePk) _other;
		if (linesnotcare == null ? _cast.linesnotcare != linesnotcare : !linesnotcare.equals( _cast.linesnotcare )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		if (linesnotcare != null) {
			_hashCode = 29 * _hashCode + linesnotcare.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.LinesEventsNotcarePk: " );
		ret.append( "linesnotcare=" + linesnotcare );
		return ret.toString();
	}

}
