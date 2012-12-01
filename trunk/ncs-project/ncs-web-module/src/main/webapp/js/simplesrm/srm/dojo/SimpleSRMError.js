//////////////////////////////////////////////////////////////////
// @JS_LONG_COPYRIGHT_BEGIN@
// @JS_LONG_COPYRIGHT_END@
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dojo.SimpleSRMMessage");
dojo.provide("ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError");

/**
 * Create a message object, and log it on the server
 * @param msg: the message text
 * @param level: the log level.  one of ["fine", "finer", "finest", "info", "severe", "warning"
 */
dojo.declare("ibm.tivoli.simplesrm.srm.dojo.SimpleSRMMessage", null, 
{
	level: "info",
	constructor: function(/*string*/msg, /*string?*/level)
	{
		try {
			if(undefined !== level) 
				this.level = level;
			this.message = msg;
			this.log();
		}
		catch(ex) {
			try {
				console.warn("Failed creating SimpleSRMMessage object: ", ex);
			}
			catch(ex2) {
				// this ctor is often called during exception handling.  can't afford to fail
			}
		}
	},

	log: function()
	{
		// TODO: show a popup?
		console.log("Error: (" + this.level + ") " + this.message);
		try {
			dojo.xhrPost({
				url: "/SRMCommonsWeb/MessageLogger",
				content: {level: this.level, message: this.message},
				sync: false,
				error: function(response, ioArgs) {
					console.warn("Failed logging message: ", this.content.message);
				}
			});
		}
		catch(ex) {
			try {
				console.warn("Failed logging SimpleSRMMEssage: ", this.message);
			}
			catch(ex2){
				// we're hosed.  ignore
			}
		}
	}
});
/**
 * An Error object. Use in place of javascript Error()
 * 
 * If you construct a SimpleSRMError using a javascript Error object
 * 		new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(new Error("error message"));
 * then the logged error message will include the filename and line number.
 */
dojo.declare("ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError", null, 
{
	constructor: function(/*object|string*/err, /*string?*/severity)
	{
		try {
			this.message = "";
			this.severity = "severe";
			
			if("object" === typeof err) {
				if(undefined !== err.message) {
					this.message = err.message;
					if(undefined !== err.fileName) {
						this.message += " at " + err.fileName;
					}
					if(undefined !== err.lineNumber) {
						this.message += " (" + err.lineNumber + ")";
					}
				}
				else {
					this.message = err.toString();
				}
			}
			else {
				this.message = err;
			} 
			if(undefined !== severity) {
				this.severity = severity;
			}
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMMessage(this.message, this.severity);
		}
		catch(ex) {
			try {
				console.warn("Failed creating SimpleSRMError object: ", ex);
			}
			catch(ex2) {
				// this ctor is often called during exception handling.  can't afford to fail
			}
		}
	}
});

