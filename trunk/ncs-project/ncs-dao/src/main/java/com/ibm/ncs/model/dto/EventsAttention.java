package com.ibm.ncs.model.dto;

import com.ibm.ncs.model.dao.*;
import com.ibm.ncs.model.factory.*;
import com.ibm.ncs.model.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class EventsAttention implements Serializable
{
	/** 
	 * This attribute maps to the column EVENTSATTENTION in the EVENTS_ATTENTION table.
	 */
	protected String eventsattention;

	/** 
	 * This attribute maps to the column SEVERITY in the EVENTS_ATTENTION table.
	 */
	protected long severity;

	/** 
	 * This attribute represents whether the primitive attribute severity is null.
	 */
	protected boolean severityNull = true;

	/** 
	 * This attribute maps to the column PROCESSSUGGEST in the EVENTS_ATTENTION table.
	 */
	protected String processsuggest;

	/**
	 * Method 'EventsAttention'
	 * 
	 */
	public EventsAttention()
	{
	}

	/**
	 * Method 'getEventsattention'
	 * 
	 * @return String
	 */
	public String getEventsattention()
	{
		return eventsattention;
	}

	/**
	 * Method 'setEventsattention'
	 * 
	 * @param eventsattention
	 */
	public void setEventsattention(String eventsattention)
	{
		this.eventsattention = eventsattention;
	}

	/**
	 * Method 'getSeverity'
	 * 
	 * @return long
	 */
	public long getSeverity()
	{
		return severity;
	}

	/**
	 * Method 'setSeverity'
	 * 
	 * @param severity
	 */
	public void setSeverity(long severity)
	{
		this.severity = severity;
		this.severityNull = false;
	}

	/**
	 * Method 'setSeverityNull'
	 * 
	 * @param value
	 */
	public void setSeverityNull(boolean value)
	{
		this.severityNull = value;
	}

	/**
	 * Method 'isSeverityNull'
	 * 
	 * @return boolean
	 */
	public boolean isSeverityNull()
	{
		return severityNull;
	}

	/**
	 * Method 'getProcesssuggest'
	 * 
	 * @return String
	 */
	public String getProcesssuggest()
	{
		return processsuggest;
	}

	/**
	 * Method 'setProcesssuggest'
	 * 
	 * @param processsuggest
	 */
	public void setProcesssuggest(String processsuggest)
	{
		this.processsuggest = processsuggest;
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
		
		if (!(_other instanceof EventsAttention)) {
			return false;
		}
		
		final EventsAttention _cast = (EventsAttention) _other;
		if (eventsattention == null ? _cast.eventsattention != eventsattention : !eventsattention.equals( _cast.eventsattention )) {
			return false;
		}
		
		if (severity != _cast.severity) {
			return false;
		}
		
		if (severityNull != _cast.severityNull) {
			return false;
		}
		
		if (processsuggest == null ? _cast.processsuggest != processsuggest : !processsuggest.equals( _cast.processsuggest )) {
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
		
		_hashCode = 29 * _hashCode + (int) (severity ^ (severity >>> 32));
		_hashCode = 29 * _hashCode + (severityNull ? 1 : 0);
		if (processsuggest != null) {
			_hashCode = 29 * _hashCode + processsuggest.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return EventsAttentionPk
	 */
	public EventsAttentionPk createPk()
	{
		return new EventsAttentionPk(eventsattention);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.ibm.ncs.model.dto.EventsAttention: " );
		ret.append( "eventsattention=" + eventsattention );
		ret.append( ", severity=" + severity );
		ret.append( ", processsuggest=" + processsuggest );
		return ret.toString();
	}

}
