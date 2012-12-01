dojo.provide("ibm.tivoli.tip.trace.Trace");

/** 
 * The Trace class. 
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.trace.Trace",
    [],
    {
        /** the trace name */
        traceName: "",
        
        constructor: function(name)
        {
        	/** the trace name */
        	this.traceName = name;
        },
        
    	info: function(dbgString)
    	{
    		this.trace("INFO", this.traceName, dbgString);
    	},
    	
        object: function(dbgObject)
    	{
    		this.trace("INFO", this.traceName, dojo.toJson(dbgObject));
    	},
    	
	    warn: function(dbgString)
    	{
    		this.trace("WARN", this.traceName, dbgString);
    	},
    	
	    assert: function(predicate, message)
        {
            if ( predicate === false ) {
                this.trace("WARN", this.traceName, "assertion failed: " + message);
		    }		
	    },
	    
	    error: function(dbgString)
	    {
            if (dbgString && dbgString instanceof Error) {
                this.trace("EXCEPTION", this.traceName, dbgString.toString());
	    	} else {
                this.trace("ERROR", this.traceName, dbgString);
	    	}
	    },
	    
	    trace: function(level, traceName, message)
	    {
	        console.debug(level + " " + traceName + " " + message);
	    }
    }
);

