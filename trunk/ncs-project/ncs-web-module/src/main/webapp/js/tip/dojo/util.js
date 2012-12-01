/****************************************************** {COPYRIGHT-TOP} ***
* Licensed Materials - Property of IBM
* 5724-Q87 
*
* (C) Copyright IBM Corp. 2007
*
* US Government Users Restricted Rights - Use, duplication, or
* disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
********************************************************** {COPYRIGHT-END} **/
dojo.require("dojo.date.locale");

dojo.provide("ibm.tivoli.tip.dojo.util");

ibm.tivoli.tip.dojo.util._stdDateFormat = { // selector:     'dateOnly', 
                                      formatLength: 'short' 
                                    };

/************************************************************************
* "apply" any GLOBAL-based data of interest.
************************************************************************/	
ibm.tivoli.tip.dojo.util.applyGlobal = function( global ) 
{
  ibm.tivoli.tip.dojo.util._stdDateFormat = global.l10n.date.stdTS;

  console.debug( "** util.applyGlobal **", dojo.toJson(ibm.tivoli.tip.dojo.util._stdDateFormat ) ); 
};

/************************************************************************
* Format a DATE with NLS rules
************************************************************************/	
ibm.tivoli.tip.dojo.util.formatDate = function( date ) 
{
	fDate = ( date == null )
          ? ""
          : dojo.date.locale.format( date, ibm.tivoli.tip.dojo.util._stdDateFormat );
 
	return( fDate );
};

/************************************************************************
* Standard formatter for a table column containing Date objects.
************************************************************************/	
ibm.tivoli.tip.dojo.util.dateColumnFormater = function( /* Date */ cellValue )
 {
   // console.debug( "cell: ", cellValue );

   return( ibm.tivoli.tip.dojo.util.formatDate( cellValue ) );
 };

/************************************************************************
* Refresh the page
************************************************************************/	
ibm.tivoli.tip.dojo.util.refreshPage = function()
{
	window.location.reload( false );
};

/*****************************************************************************
* Return a string of all the fields contained in an object.
*****************************************************************************/
ibm.tivoli.tip.dojo.util.fieldsOf = function( object )
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
};

/************************************************************************
* Clear a datastore (must implement dojo.data.api.Write)
* 
* Returns dojo.Deferred() -- must add Callback
*
* example usage:
* ibm.tivoli.tip.dojo.util.clearDataStore( store ).addCallback( function(){} );
************************************************************************/ 
ibm.tivoli.tip.dojo.util.clearDataStore = function( store )
{
  var deferred = new dojo.Deferred();
    
  var callback = 
  {  
        deferred: deferred,
           store: store,        
      onComplete: function( items, request ) 
                  {                    
                    for ( var i in items )
                    {
                      this.store.deleteItem( items[i] );
                    }

                    this.deferred.callback();
                  }
  };
  
  store.fetch( { onComplete: dojo.hitch( callback, "onComplete" ) } );
  
  return deferred;
};

/************************************************************************
* Clear a datastore (must implement dojo.data.api.Write)
************************************************************************/ 
ibm.tivoli.tip.dojo.util.addItemsToDataStore = function( store, items )
{
  for ( var i in items )
  {
    store.newItem( items[i] );
  }

  store.save();
};

/************************************************************************
* Find the default name for a specific value using a name/value array.
*
* [
*  { value: "-1", name: this.global.nls( "CBOX_CONFIG_DEFAULT", { config : config.maxSnapshotsDefault } ) }, 
*  { value:  "0", name: bundle.CBOX_DISABLED }, 
*  { value:  "1", name:  "1" }
* ]
************************************************************************/ 
ibm.tivoli.tip.dojo.util.findDefaultName = function( value, nameValueArray )
{
  name = nameValueArray[ 0 ].name;     // default to first element

  for ( var i in nameValueArray )
  {
    nv = nameValueArray[ i ];

    if ( nv.value == value ) 
    {
      name = nv.name;
      break;
    }
  }

  // console.debug( "value: " + value + ", name: " + name );

  return( name );
};

