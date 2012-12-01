package com.ibm.ncs.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * This class represents the primary key of the EVENTS_ATTENTION table.
 */
public class EventsAttentionPk implements Serializable
{
	protected String eventsattention;

	/** 
	 * Sets the value of eventsattention
	 */
	public void setEventsattention(String eventsattention)
	{
		this.eventsattention = eventsattention;
	}

	/** 
	 * Gets the value of eventsattention
	 */
	public String getEventsattention()
	{
		return eventsattention;
	}

	/**
	 * Method 'EventsAttentionPk'
	 * 
	 */
	public EventsAttentionPk()
	{
	}

	/**
	 * Method 'EventsAttentionPk'
	 * 
	 * @param eventsattention
	 */
	public EventsAttentionPk(final String eventsattention)
	{
		this.eventsattention = eventsattention;
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
		
		if (!(_other instanceof EventsAttentionPk)) {
			return false;
		}
		
		final EventsAttentionPk _cast = (EventsAttentionPk) _other;
		if (eventsattention == null ? _cast.eventsattention != eventsattention : !eventsattention.equals( _cast.eventsattention )) {
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
		if (eventsattention != null) {
			_hashCode = 29 * _hashCode + eventsattention.hashCode();
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
		ret.append( "com.ibm.ncs.model.dto.EventsAttentionPk: " );
		ret.append( "eventsattention=" + eventsattention );
		return ret.toString();
	}

}
