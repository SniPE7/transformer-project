/****************************************************** {COPYRIGHT-TOP} ***
* Licensed Materials - Property of IBM
* 5724-Q87 
*
* (C) Copyright IBM Corp. 2008
*
* US Government Users Restricted Rights - Use, duplication, or
* disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
********************************************************** {COPYRIGHT-END} **/

dojo.provide( "ibm.tivoli.tip.dojo.ActionBroker" );
dojo.provide( "ibm.tivoli.tip.dojo.Action" );

// Action Broker CONSTANTS
ibm.tivoli.tip.dojo.ActionBroker.TOPIC = "com.ibm.tivoli.tip.dojo.ActionBroker.perform";

/*******************************************************************************
* ActionBroker
*
* This manager is responsible for registering and running actions.
*
* The manager will subscribe to ibm.tivoli.tcr.ActionBroker.TOPIC.

* Use the runAction() method to initiate an action
*******************************************************************************/
dojo.declare( "ibm.tivoli.tip.dojo.ActionBroker", //className
              null,                               //extends
{
   _actionMap: {},
   
  /*****************************************************************************
  * Construct Object and mixin all params
  *****************************************************************************/
  constructor: function( params )
  {
    dojo.mixin( this, params );
    
    this._performActionTopic = dojo.subscribe( ibm.tivoli.tip.dojo.ActionBroker.TOPIC, this, "performAction" );
  },

  /*****************************************************************************
  * Return a string of all registered action IDs.
  *****************************************************************************/
  registeredActions: function()
  { 
    return( this._fieldsOf( this._actionMap ) );
  },
  
  /*****************************************************************************
  * Register a given action with the broker.  Action must extend 
  * ibm.tivoli.tcr.Action
  *****************************************************************************/
  registerAction: function( actionObj )
  { 
     if ( ! actionObj.id || ! actionObj.run )
     {
       console.log( "ERROR: Trying to register an action that does not extend ibm.tivoli.tip.dojo.Action" );
       return;
     }
      
    this._actionMap[ actionObj.id ] = actionObj;
  },

  /*****************************************************************************
  * Unregister an action
  *****************************************************************************/
  unregisterAction: function( actionObj )
  { 
    if( actionObj.id )
    {
      this.unregisterActionByID( actionObj.id );
    }
  },

  /*****************************************************************************
  * Unregister an action by id
  *****************************************************************************/
  unregisterActionByID: function( actionID )
  { 
    if( this._actionMap[ actionID ] )
    {
      delete this._actionMap[ actionID ];
    }
  },
  
  /*****************************************************************************
  * The topic com.ibm.tivoli.tcr.performAction has been trapped or this method 
  * has been invoked.  Run the action.
  *
  * If more than one contextObj is provided it is assumed that the action
  * should be fired for each contextObj.  (for multiselect scenarios)
  *
  * actionDef = [ { actionID: id, context: [ contextObj, contextObj, ... ] } ]
  *****************************************************************************/
  performAction: function( actionDef )
  {
//  console.debug( "ActionBroker.performAction", actionDef );
//  console.debug( "  >> defined actions: ", dojo.toJson(  this._actionMap ) );

    var action = this._actionMap[ actionDef.actionID ];

//  console.debug( "ActionBroker.performAction: action", dojo.toJson( action ) );

    if ( action )
    {
      action.run( actionDef.context );
    }
  },
  
  /*****************************************************************************
  * Destroy the action broker.
  *****************************************************************************/
  destroy: function()
  {
    dojo.unsubscribe( this._performActionTopic );
    
    for ( var i in this._actionMap )
    {
      this._actionMap[i].destroy();
    }
    
    delete this._actionMap;  
  },

  /*****************************************************************************
  * (Indirectly) Run an action. Includes support for "hierarchical" IDs
  *****************************************************************************/
  /* void */ 
  runAction: function( /* string */ actionID, /* Object */ context )
  {
//  console.debug( ">>> runAction", actionID, dojo.toJson( context ) );

    // create default publish data object

    publishData = { 
                    actionID: actionID,  // used in locating       actual action impl
                    context:  context    // ONLY this is passed to actual action impl     
                  };      

    // is this a hierarchical action ID?  format: actionID/subactionID

    index = actionID.indexOf( "/" );

    if ( index != -1) 
    {
      subActionID = actionID.substring( index + 1 );
         actionID = actionID.substring( 0, index );   

      publishData.           actionID =    actionID;
      publishData.context.   actionID =    actionID;
      publishData.context.subActionID = subActionID;  // used to locate subaction action impl
    }

    // finally, "publish" the action request

//  console.debug( ">>> runAction: topic data", dojo.toJson( publishData ) );

    dojo.publish( ibm.tivoli.tcr.ActionBroker.TOPIC, [ publishData ] );      
  },
  
  /*****************************************************************************
  * Return a string of all the fields contained in an object.
  *****************************************************************************/
  _fieldsOf: function( object )
  { 
    s = "";
  
    if ( object && ( object !== null ) ) 
    {
      for ( var field in object )
      {
        s += field + "; ";
      }
    }
  
    return( s );
  } 
});

/*******************************************************************************
* Action Interface
*
* Extend this to make new actions that can be registered with the broker
*******************************************************************************/
dojo.declare( "ibm.tivoli.tip.dojo.Action", //className
              null,                        //extends
{
  id:  null,                        /*required*/
  
  run: function( context ){ /*requried implement*/ }  
} );