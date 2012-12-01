/****************************************************** {COPYRIGHT-TOP} ***
* Licensed Materials - Property of IBM
* xxxx-xxx 
*
* (C) Copyright IBM Corp. 2008
*
* US Government Users Restricted Rights - Use, duplication, or
* disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
********************************************************** {COPYRIGHT-END} **/
dojo.require("ibm.tivoli.tip.dijit.TIPMessageDialog");
dojo.requireLocalization("ibm.tivoli.tip.dojo", "resources");

dojo.provide("ibm.tivoli.tip.dojo.Global");
dojo.provide("ibm.tivoli.tip.dojo.Global.InvalidContextError");
dojo.provide("ibm.tivoli.tip.dojo._Global");

/****************************************************************************
* Global 
*
* (Q) What is this?
* (A) Since the TIP applications will be running inside a portal environment
*     it is possible that multiple instances of the application could be
*     running on one browser.  Therefore, it is necessary that our global
*     data and variables are referenced by Portlet Namespace to avoid
*     collisions between multipile instances.
*
*     This object is also used to provide common functions that are namespace
*     dependant.
****************************************************************************/

ibm.tivoli.tip.dojo.Global.IFrame        = "IframeTransport";
ibm.tivoli.tip.dojo.Global.instances     = [];
ibm.tivoli.tip.dojo.Global.anyException  = "*"; 

/****************************************************************************
* Global getSingleton function
*
* namespace (required) -- the namespace of the global object space
* context   (optional: used only if global object does NOT exist) an object of 
*			properties that will get mixed into the Global object for storage
*
*  When creating the context, you create it like this:
*  var context = {
*                  controllerURL: "/SimpleExample/AjaxController",
*                  namespace: myNamespace,
*                  nlsResources : dojo.i18n.getLocalization( "ibm.tivoli.tbsm", "resources" )
*                  contextPath: "<%=rootContextPath%>"
*                };
*   controllerURL (required) - specifies the URL to use to send requests to.
*                 this is the URL which handles the requests usually using the
*                 TIP CommandHandler.
*   namespace (optional) - Should always be specified.  If not specified, then
*             this routine will set it on the context.
*   nlsResources (optional) - Must be specified if you intend to use the nls 
*                facility provided in this object
*   contextPath (optional) - Should always be specified but not required. 
*               Must be specified if you intend to use the helper
*               function "contextualURL".
*   ===========
*   any other properties you add into the context will be avaible to your app.
****************************************************************************/
ibm.tivoli.tip.dojo.Global.getSingleton = function( namespace, context )
{
  var singleton = ibm.tivoli.tip.dojo.Global.instances[ namespace ];
  
  //if a singleton was not found then assume we need to create one now
  if ( singleton === undefined )
  {
  	//check to make sure a context object was passed before creating a Global object
  	//if the context object wasn't given then assume that an attempt was made to 
  	//access a Global object that hasn't been created -- throw an error
  	if ( context === undefined )
  	{
  		throw new ibm.tivoli.tip.dojo.Global.InvalidContextError( "An attempt was made to access a " + 
  		"singleton ibm.tivoli.tip.dojo.Global object that has not been created yet.  Or the context " + 
  		"object was not provided on creation." );
  	}
  	
  	//make sure the namespace is saved on the context object
  	if ( context.namespace === undefined )
  	{
  		context.namespace = namespace;	
  	}  	
  	
    singleton = new ibm.tivoli.tip.dojo._Global( context );
   
   	//save the new singleton instance on the Global memory space
    ibm.tivoli.tip.dojo.Global.instances[ namespace ] = singleton;
  }  
  
  return( singleton );	
};

/****************************************************************************
* Global setSingleton function
*
* namespace (required) -- the namespace of the global object space
* context   (required) an object of 
   properties that will get mixed into the Global object for storage
****************************************************************************/
ibm.tivoli.tip.dojo.Global.setSingleton = function (namespace, context) 
{
   //check to make sure a context object was passed before creating a Global object
   //if the context object wasn't given then assume that an attempt was made to 
   //access a Global object that hasn't been created -- throw an error
   if ( context === undefined )
   {
    throw new ibm.tivoli.tip.dojo.Global.InvalidContextError( "An attempt was made to access a " + 
    "singleton ibm.tivoli.tip.dojo.Global object that has not been created yet.  Or the context " + 
    "object was not provided on creation." );
   }
   
   //make sure the namespace is saved on the context object
   if ( context.namespace === undefined )
   {
    context.namespace = namespace; 
   }   
   
   var singleton = new ibm.tivoli.tip.dojo._Global( context );
   
   //save the new singleton instance on the Global memory space
   ibm.tivoli.tip.dojo.Global.instances[ namespace ] = singleton; 
   
   return (singleton);
};


/****************************************************************************
* _Global -- the actual Global object
*
* Notice that there is a '_' underscore prefixing this object.  This is
* important because removing it will delete the Global module functions
* such as getSingleton.  This is OK because we will never construct one
* of these things without calling getSingleton.
****************************************************************************/
dojo.declare("ibm.tivoli.tip.dojo._Global",null,
{	 
  _actionBroker:  null,
  _portletTitle: null,
	
	constructor: function( context )
	{
      //add all functions, fields, objects inside context to this object
      dojo.mixin( this, context );
      
      //create path to JSON controller servlet
      this.controller = context.controllerURL;
         
      // Create resource bundle for TIP based resource strings
      this.nlsTIPResources = dojo.i18n.getLocalization( "ibm.tivoli.tip.dojo", "resources" );
         
        
	},

  /************************************************************************
  * Get the action broker.
  ************************************************************************/
  /* ibm.tivoli.tip.dojo.ActionBroker */ 
  getActionBroker : function() 
  {
    return( _actionBroker );
  },

  /************************************************************************
  * Set the action broker.
  ************************************************************************/
  /* ibm.tivoli.tip.dojo.ActionBroker */ 
  setActionBroker : function( /* ibm.tivoli.tip.dojo.ActionBroker */ broker ) 
  {
    return( _actionBroker = broker );
  },
  
  getPortletTitle : function () {
  	return this._portletTitle;
  },
  
  setPortletTitle: function (/*String*/ title) {
  	this._portletTitle = title;
  },
	
	/************************************************************************
	* Generate Conextual URL
	************************************************************************/
	contextualURL : function( baseURL ) 
	{
  	return( this.contextPath + baseURL );
	},

	/************************************************************************
	* Generate namespaced ID from a BASE ID.
	************************************************************************/
	namespaceID: function( baseID ) 
	{
  	return( this.namespace + baseID );
	},	

  /************************************************************************
  * show error message dialog
  ************************************************************************/
  showErrorMessage: function( msgMixin )
  {
    var msgKW = {
              type:         "Error",
              showCheckbox: false,
              portletTitle: this._portletTitle
            };

    dojo.mixin( msgKW, msgMixin );

    var x = new ibm.tivoli.tip.dijit.TIPMessageDialog( msgKW );
    x.show();
  },

  /************************************************************************
  * Retrieve a specific NLS string from the TIP resource bundle.  Deployers
  * could create a method like this for their own product that calls nls
  * with their resource bundle for convenience.  This is meant to only
  * be called by TIP code.
  ************************************************************************/
  tipnls: function( nlsKey, inserts )
  {
     return this.nls( nlsKey, inserts, this.nlsTIPResources );
  },

  /************************************************************************
  * Retrieve a specific NLS string optionally performing substitution(s).
  * This is a fairly generic routine meant to be used by exploiters of 
  * the Global object.  If an exploiter specifies a resource bundle in 
  * the Global context via the name 'nlsResources' when creating the Global object
  * then a bundle need not be specified on the invocation of this method
  * because it will use nlsResources instead.
  ************************************************************************/
  nls: function( nlsKey, inserts, myBundle )
  {
    // attempt to retrieve the specified string via its key
    var s = null;
    
    // If a bundle has been specified, then use it, else
    // use the bundle specified on nlsResources.
    if (myBundle !== null)
    {
       s = myBundle[ nlsKey ];
    }
    else
    {
       s = this.nlsResources[ nlsKey ];
    }

    // substitute "key" if not able to resolve
    if ( ! s )
    {
      s = nlsKey;
    }

    // string insert(s) specified?
    if ( inserts ) 
    {
      // if not already a KW object -or- array 
      if ( ! ( typeof inserts == 'object' ) )
      {
        // convert single number/string/... to array
        inserts = [ inserts ];
      }

      // perform the substitutions
      s = dojo.string.substitute( s, inserts );
    }

    return( s );
  }
    
} );

//Declare a new Error type to throw when a Global context error is detected
dojo.declare("ibm.tivoli.tip.dojo.Global.InvalidContextError", Error, {});
