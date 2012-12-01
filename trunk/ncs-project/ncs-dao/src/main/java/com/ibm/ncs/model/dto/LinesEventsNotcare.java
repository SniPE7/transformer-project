package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class LinesEventsNotcare implements Serializable
{
	/** 
	 * This attribute maps to the column LINESNOTCARE in the LINES_EVENTS_NOTCARE table.
	 */
	protected String linesnotcare;

	/**
	 * Method 'LinesEventsNotcare'
	 * 
	 */
	public LinesEventsNotcare()
	{
	}

	/**
	 * Method 'getLinesnotcare'
	 * 
	 * @return String
	 */
	public String getLinesnotcare()
	{
		return linesnotcare;
	}

	/**
	 * Method 'setLinesnotcare'
	 * 
	 * @param linesnotcare
	 */
	public void setLinesnotcare(String linesnotcare)
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
		
		if (!(_other instanceof LinesEventsNotcare)) {
			return false;
		}
		
		final LinesEventsNotcare _cast = (LinesEventsNotcare) _other;
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
	 * Method 'createPk'
	 * 
	 * @return LinesEventsNotcarePk
	 */
	public LinesEventsNotcarePk createPk()
	{
		return new LinesEventsNotcarePk(linesnotcare);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.LinesEventsNotcare: " );
		ret.append( "linesnotcare=" + linesnotcare );
		return ret.toString();
	}

}
