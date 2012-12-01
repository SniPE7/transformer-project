/****************************************************** {COPYRIGHT-TOP} ***
* Licensed Materials - Property of IBM
* 5724-Q87 
*
* (C) Copyright IBM Corp. 2008
*
* US Government Users Restricted Rights - Use, duplication, or
* disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
********************************************************** {COPYRIGHT-END} **/
dojo.require("ibm.tivoli.tip.dojo.util");

dojo.provide("ibm.tivoli.tip.dojo.Server");
dojo.provide("ibm.tivoli.tip.dojo._Server");

ibm.tivoli.tip.dojo.Server.anyException  = "*"; 

ibm.tivoli.tip.dojo.Server._singleton    = null;

/****************************************************************************
* Get the "singleton" Server instance.
****************************************************************************/
/* "ibm.tivoli.tip.dojo._Server" */
ibm.tivoli.tip.dojo.Server.getSingleton = function( /* required: ibm.tivoli.tip.dojo._Global */ global )
{
  if ( global === undefined ) {
  	throw new Error( "ibm.tivoli.tip.dojo.Server requires a 'global' object reference" );
  }
  		
  //if the singleton doesn't exist yet --> create it now
  //there must be a unique singleton per global instance
  if ( global._serverSingleton === undefined || global._serverSingleton === null )
  {
    global._serverSingleton = new ibm.tivoli.tip.dojo._Server( { global : global } );
  }  
  
  return global._serverSingleton;	
};

/****************************************************************************
* Provides I/O access to the controller servlet.
* Handles TIP protocol of returning server-side exceptions in a valid
* data response besides Dojo's error and timeout conditions.
****************************************************************************/
dojo.declare("ibm.tivoli.tip.dojo._Server", 
             null,
{	 
	_totalRequests:   0,        // total server requests made
  _activeRequests:  0,        // # server request(s) currently active
	
  /************************************************************************
  * Object constructor
  ************************************************************************/
	constructor: function( mixinData )
	{
    dojo.mixin( this, mixinData );
	},
	
	/************************************************************************
	* Perform a HTTP GET request.
	************************************************************************/
	/* void */ get: function( /* Object */ requestKW )
	{
    this._preRequest( requestKW );
	
    //console.debug( "Ready to xhrGet", requestKW );
    //console.debug( ibm.tivoli.tip.dojo.util.fieldsOf(requestKW) );
    //console.debug( "Timeout", requestKW.timeout );

	  dojo.xhrGet( requestKW ); 
	},
	
	/************************************************************************
	* Perform a HTTP POST request.  Preferred for data forms that do
  * NOT contain any file-input fields...
	************************************************************************/
	/* void */ post: function( /* Object */ requestKW )
	{
    this._preRequest( requestKW );
	
	  dojo.xhrPost( requestKW ); 
	},

  /************************************************************************
  * Common PRE request processing of the request KW object.
  ************************************************************************/
  /* Object */ _preRequest: function( /* Object */ requestKW )
  {
    this._requestStarting( ( requestKW.showBusyDialog !== undefined ) ? requestKW.showBusyDialog : true );                 

    requestKW._server      = this;                 // save self reference for use in load/error handlers below   
    requestKW._requestID   = this._totalRequests;  // unofficial request ID

    requestKW.url          = requestKW.appendURL ? 
      this.global.controller + requestKW.appendURL :
      this.global.controller;

    requestKW.handleAs	   = "json-comment-filtered";  
    requestKW.mimetype     = "application/json";
    
    requestKW.content.requestType  = "global";     // indicates that the request was sent here

    requestKW.preventCache = true;     // fix IE cache bug

    // provide default timeout if none specified

    if ( ! requestKW.timeout ) {
           requestKW.timeout = 0; 
    }

    // our code handles it all!  NOTE: trying to set callbacks on "deferred" object did NOT work

    requestKW.load  = this._loadHandler;
    requestKW.error = this._errorHandler;

    return( requestKW ); 
  },
	
	/************************************************************************
	* Standard JSON request "load" handler.
  *
  * Use the appropriate "how to handle" directions from the request:
  * - "onExc"      for server-side errors returned in the 'exc' field of response
  * - "onResponse" for response data
  *
  *	NOTE "this" will be the object passed as argument to dojo.xhrXxxx() used (aka requestKW)
	************************************************************************/
  /* response */ _loadHandler: function( response, ioArgs ) 
	{
    //console.debug( "************************ Server._loadHandler ************************" );

//  console.debug( ibm.tivoli.tip.dojo.util.fieldsOf(this) );      proved its requestKW
//  console.debug( ibm.tivoli.tip.dojo.util.fieldsOf(response) );
//  console.debug( ibm.tivoli.tip.dojo.util.fieldsOf(ioArgs) );    ioArgs.args == requestKW

    //console.debug( "  ** response", dojo.toJson(response) );

    var server = this._server;

    server._requestCompleted();

	  // was an exception detected/reported from the server-side?

    var exc = response.exc;

	  if ( exc )
	  { 
      //console.debug( "    !!!!! handling EXC !!!!!" );

      // ISC session timeout?
      if ( exc == "ISCSessionTimeoutException" ) 
      {
        ibm.tivoli.tip.dojo.util.refreshPage();
        return;
      }

      var howTo  = this.onExc;

      if ( ! howTo ) {
        howTo = { showMsg : true };    // use default
      }

      //console.debug( "  ?? howTo",  dojo.toJson(howTo) );

      // Crop the exc message to maximum size
      server.cropExcMsg( response );

      //TODO: handle new NLS enabled exceptions...
      var msgKW = null;
      
      if (response.excID) {
	      msgKW = { 
                   messageId : response.excID,
                   message   : response.excMsg
                  };
      } else {
    	  msgKW = { 
                   messageId : server.global.tipnls( "BIND_ERROR_ID" ),
                   message   : server.global.tipnls( "BIND_ERROR_MSG", response )
                  };
	  }

      //console.debug( "  ?? msgKW",  dojo.toJson(msgKW) );

      if ( howTo.showMsg ) {
        server.global.showErrorMessage( msgKW );
      }

      if ( howTo.callback ) {
        howTo.callback( msgKW, this /* requestKW */ );
      }
	  }
	
	  // No exception --> forward to callback
	  else
	  {
      howTo = this.onResponse;

      if ( howTo && howTo.callback ) {
	      howTo.callback( response, this /* requestKW */ );
      }
	  }

    return( response ); // Dojo best practice 
	},

	/************************************************************************
	* Standard JSON request error handler
  *
  * How to handle directions are contained in the request's "onExc" field.
  *
  *	NOTE "this" will be the object passed as argument to dojo.xhrXxxx() used
  *
  * error.message:    specific error message, "too much recursion"
  * error.fileName:   "https://localhost:16316/ibm/console/secure/isclite/scripts/dojo/dojo.js"
  * error.lineNumber: 20
  * error.stack:      callback stack (can be very deep )
  * error.name:       "InternalError"
  *
  * error.dojoType:   "timeout" or "cancel", but other error types are possible.
  * dojoType=timeout message=timeout exceeded
  *
  * Error: bad http response code:404 message=bad http response code:404
  * 
  * console.error("HTTP status code: ", ioArgs.xhr.status); 
	************************************************************************/
	/* data */ _errorHandler: function( error, ioArgs ) 
	{
    //console.debug( "************************ Server._errorHandler ************************" );

    //console.debug( "  >> this",   ibm.tivoli.tip.dojo.util.fieldsOf(this) );
    //console.debug( "  >> error",  ibm.tivoli.tip.dojo.util.fieldsOf(error) );
    //console.debug( "  >> ioArgs", ibm.tivoli.tip.dojo.util.fieldsOf(ioArgs) );
    //console.debug( "  >> ioArgs.args", ibm.tivoli.tip.dojo.util.fieldsOf(ioArgs.args) );

    //console.debug( "  -- error",  dojo.toJson(error) );

    var server = this._server;

    server._requestCompleted();

    var howTo = this.onError;
    var msgKW = null;

    if ( ! howTo ) {
      howTo = { showMsg : true };    // use default
    }

    if ( error.dojoType && ( error.dojoType === "timeout" ) ) 
    {
      msgKW = { 
                messageId : server.global.tipnls( "BIND_TIMEOUT_ID" ),
                message   : server.global.tipnls( "BIND_TIMEOUT_MSG", error )
              }; 
    }
    else if ( error.name && ( error.name === "InternalError" ) ) 
    {
      msgKW = { 
                messageId : server.global.tipnls( "JS_ERROR_ID" ),
                message   : server.global.tipnls( "JS_ERROR_MSG", error )
              }; 
    }
    else
    {
      msgKW = { 
                messageId : server.global.tipnls( "XHR_ERROR_ID" ),
                message   : server.global.tipnls( "XHR_ERROR_MSG", error )
              }; 
    }

    if ( howTo.showMsg ) {
	    server.global.showErrorMessage( msgKW );
    }

    if ( howTo.callback ) {
      howTo.callback( msgKW, this /* requestKW */ );
    }

    return( error ); // Dojo best practice 
	},

  /************************************************************************
  * Starting a new server request.
  ************************************************************************/
  _requestStarting: function( showBusyDialog ) 
  {
    // increment total server requests 
    this._totalRequests++;

    // increment outstanding server requests 
    this._activeRequests++;

    // show wait dialog on first active request
    if ( showBusyDialog === true && this._activeRequests == 1 )
    {
      this.wait_timer = setInterval( dojo.hitch( this, "_showBusyPane" ), 100 );
    }
  },
  
  /************************************************************************
  * Show Busy Pane
  *    
  * Constructing a ibm.tivoli.tip.dijit.BusyPane and passing its ID on 
  * busyPaneID when constructing Server object will provide feedback
  * when requests are made
  ************************************************************************/
  _showBusyPane: function()
  {
    clearInterval( this.wait_timer );
    // in case of race condition, make sure that active requests > 0
    if ( this._activeRequests > 0 ) {
	    var busyPane = dijit.byId( this.busyPaneID );
	    if ( busyPane )
	    {
	      busyPane.show(true); // size to portlet
	    }
    }
  },

  /************************************************************************
  * Completing a server request.
  ************************************************************************/
  _requestCompleted: function() 
  {
	  //decrement the # outstanding requests 
	  this._activeRequests--;

	  // if no more active requests --> close wait dialog
	  if ( this._activeRequests === 0 )
	  {
		  clearInterval( this.wait_timer );
      
          var busyPane = dijit.byId( this.busyPaneID );
          if ( busyPane )
          {
            busyPane.hide();
          }
	  }  	
  },

  /***************************************************************************
  * Crop the message in the 'excMsg' field to the maximum allowed length.
  ***************************************************************************/
  cropExcMsg: function( response )
  {
    // provide a default exc message if field isn't already available
    if ( ! response.excMsg ) {
      response.excMsg = "-?-";
    }

    // access externalized MAX length
    var msgMax = this.global.nlsTIPResources.MAX_WIDTH_EXC_MSG;

    // exc message too long?
    if ( response.excMsg.length > msgMax ) 
    {
      response.excMsg = response.excMsg.substring( 0, msgMax ) + this.global.tipnls( "TEXT_CROPPED" );
    }
  }

} );
