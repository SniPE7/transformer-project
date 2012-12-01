dojo.provide("ibm.tivoli.tip.dijit.Throttler");

/**
 * A generic throttler utility class.
 * 
 * Such class can be used to throttle on some events.
 * The class's user provides a DOM node and an event to by monitored.
 * The user also provides a callback.
 * The throttler monitors the event and invokes the callback only
 * if a specified delay is elapsed after the last event occurrency.
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.Throttler",
	[],
	{
        /** the callback to call in case the throttling delay is passed */
        _callback: null,
    	/** the throttling time interval */
    	_delay: 500,
        /** the event filter function */
        _eventFilter: function() { return true; },
    	/** the timer */
        _timer: null,
        /** the last time the event occurred */
        _lastTime: 0,
        /** the setTimeout handler (the hitched version of _checkTimeframe method) */
        _timeoutHandler: null, 
        /** the connect handle */
        _connection: null, 
        
        /**
         * Constructor.
         *      
         *      @param node: (DOM node) the node to be throttled.
         *      @param eventName: (String) the name of the event to be throttled.
         *      @param callback: (Function) the callback to invoke.
         *      @param delay: (Number) the throttling time interval in ms.
         *      @param eventFilter: (Function) an optional function which 
         *          evaluates an occurred event and returns true if the event
         *          must be taken in account for the throttling.
         */
        constructor: function(node, eventName, callback, delay, eventFilter)
        {
            // set throttler parameters
    		this._callback = callback;
    		this._delay = delay || this._delay;
    		if ( delay ) {
    		     this._delay = delay;
    		}
    		if ( eventFilter ) {
    		  this._eventFilter = eventFilter;    
    		} 
    		  
            this._timeoutHandler = dojo.hitch(this, this._checkTimeframe);
            
    		// if correctly configured connect to the event callback
    		if ( this._callback !== null && node !== null && eventName !== null ) {
    			this._connection = dojo.connect(node, eventName, this, "_onEvent");
    		}
        },
      
        /**
         * Cleans-up the throttler.
         */
        cleanup: function()
        {
            // clear the timeout if any
    		if ( this._timer !== null ) {
    			clearTimeout(this._timer);
    		}
    		// disconnect the event
    		if ( this._connection !== null ) {
                dojo.disconnect(this._connection);    
    		}
    		
        },

        /**
         * Handles the event coming from the node. 
         */
        _onEvent: function(event)
    	{
    	    if ( this._eventFilter(event) ) {
                // store the last event timestamp
                this._lastTime = (new Date()).getTime();
                // if we don't have a timer, schedule it
                if ( this._timer === null ) {
                    this._timer = setTimeout(this._timeoutHandler, this._delay);
                }
    	    } 
    	},
    	
        /**
         * Invokes the callback if it's time to do it. 
         */    	
        _checkTimeframe: function()
    	{
    		// reset the time
    		this._timer = null;
			// call the callback only if the timeframe has elapsed
			var elapsed = (new Date()).getTime() - this._lastTime;
			if ( elapsed > this._delay ) {
				// the timeframe has elapsed, invoke the callback
				this._callback();
			}
			else {
				// the timeframe has not elapsed, schedule the next timer
				this._timer = setTimeout(this._timeoutHandler, this._delay - elapsed);
			}
    	}
	}
);
