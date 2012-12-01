/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * 5724-C51
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dojox.grid.Grid");
dojo.require("dojox.grid._data.model");

dojo.require("ibm.tivoli.tip.dijit.TIPTableRefreshButton");
dojo.require("ibm.tivoli.tip.dijit.TIPTableFooter");
dojo.require("ibm.tivoli.tip.dijit.TIPTableToolbar");

// NLS
dojo.requireLocalization("ibm.tivoli.tip.dijit", "resources");

dojo.provide("ibm.tivoli.tip.dijit.TIPTableV3");
dojo.provide("ibm.tivoli.tip.dijit.TableFilterAdapter");
/******************************************************************************
* Extends dojox.data.Grid to add client side filtering and a toolbar.<b> 
*
* Authors: Joseph Firebaugh
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.TIPTableV3", dojox.Grid,
{
  _backupModel: null, /*temporary storage of original model when filtering is on*/
  
  filterAdapter: null, /*provide a custom filter adapter if warrented*/
  
  /*****************************************************************************
  * Constructor
  *****************************************************************************/
  constructor: function()
  {
    //ensure that any model setting clears table filter
    this._modelListener = dojo.connect( this, "setModel", this, "clearFilter" );
  },
  
  /*****************************************************************************
  * After Grid Construction -- setup toolbar and footer
  *****************************************************************************/
  postCreate: function()
  {
    //ensure "super()" is called
    this.inherited( "postCreate", arguments );
        
    var div = dojo.doc.createElement( 'div' );
    dojo.place(  div, this.domNode, "before"  );
        
    this.filterBar = new ibm.tivoli.tip.dijit.TIPTableToolbar( {}, div );
    this.filterBar.searchField.setTable( this );    
        
    dojo.subscribe ( this.filterBar.searchField.id + "searchInvoked", this, "onFilter" );    
    
    div = dojo.doc.createElement( 'div' );
    dojo.place(  div, this.domNode, "after"  );
        
    this.footer = new ibm.tivoli.tip.dijit.TIPTableFooter( {}, div );        
    this.footer.setTable( this );
    
    var button = new ibm.tivoli.tip.dijit.TIPTableRefreshButton( { onClick: dojo.hitch( this, "onRefresh" ) } );    
    this.filterBar.toolbar.addChild( button );
  },
  
  onRefresh: function()
  {
    //post publish event
    dojo.publish (this.id + "#Refresh", [ "Refresh" ]);  
  },
  
  /*****************************************************************************
  * Enter was pressed on the Filter Fields
  *****************************************************************************/
  onFilter: function()
  {
     var fields       = this.filterBar.searchField.getSelectedColumns();
     var filterString = this.filterBar.searchField.searchField.value;
     
     if ( filterString.length < 1 )
     {
       this.clearFilter();
     }
     else
     {
       this.filter( fields, filterString );
     }
  },
    
  /****************************************************************************
  * Client side filtering of data model currently set on the table
  ****************************************************************************/
  filter: function( fields,        /* array of field numbers to filter on */
                    filterString ) /* simple string that will perform AND search in all fields */
  {
    if ( this._backupModel === null )
    {
      this._backupModel = this.model;
    }
    
    if ( this.filterAdapter === null )
    {
      this.filterAdapter = new ibm.tivoli.tip.dijit.TableFilterAdapter();
    }
            
    var newModelData = [];
        
    var rowCount = this._backupModel.getRowCount();
    var rowData  = null;
    
    for ( var i = 0; i < rowCount; i++ )
    {
      var keep     = false;
      rowData = this._backupModel.getRow( i );
      
      for ( var j in fields )
      {
        if ( this.filterAdapter.rowIsVisible( rowData[ fields[j] ], filterString ) )
        {
          newModelData.push( rowData );
          break;
        }
      }
    }
    
    var newModel = new this._backupModel.constructor(null, newModelData);
    
    //borrowed this code from Grid.js -- can't call this.setModel() because clearFilter will occur
    if( this.model )
    {
	  this.model.notObserver(this);
    }
    this._setModel( newModel );
    
    this.update();    
  },
  
  /****************************************************************************
  * Clear client side filter
  ****************************************************************************/
  clearFilter: function()
  {
    if ( this._backupModel !== null )
    {
      //borrowed this code from Grid.js -- can't call this.setModel() because clearFilter will occur
      if( this.model )
      {
        this.model.notObserver(this);
      }

      this.filterBar.searchField.searchField.value = "";

      this._setModel( this._backupModel );
            
      this._backupModel = null;
      
      this.update();    
    }
  },
  
  destroy: function()
  {
    dojo.disconnect( this._modelListener );
    
    this.clearFilter();
    this.filterBar.searchField.destroy();
    this.filterBar.destroy();
    this.footer.destroy();    
    
    this.inherited( "destroy", arguments );
  }
      
});

/******************************************************************************
* Filter Adapter (Simple Implementation -- extend and create new ones if 
* needed)
*
* This simple implementation looks for any instance of a string with the
* string form of the object -- case insensitive.
*
* Authors: Joseph Firebaugh
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.TableFilterAdapter", null,
{
  /*****************************************************************************
  * This row visible or filtered out?
  *
  * This simple implementation looks for any instance of a string with the
  * string form of the object -- case insensitive.
  *
  * cellData = raw javascript object in table model for a cell
  *****************************************************************************/
  rowIsVisible: function( cellData, filterString )
  { 
    var cellDataStr = cellData.toString().toLowerCase();
     
    return ( cellDataStr.indexOf( filterString.toLowerCase() ) != -1 );
  }
});
