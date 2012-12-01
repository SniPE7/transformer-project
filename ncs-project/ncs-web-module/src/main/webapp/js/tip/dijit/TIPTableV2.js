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
dojo.require("dijit.layout.LayoutContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dojox.grid.Grid");
dojo.require("dojox.grid._data.model");

dojo.require("ibm.tivoli.tip.dijit.TIPTableTitleBar");
dojo.require("ibm.tivoli.tip.dijit.TIPTableToolbar");
dojo.require("ibm.tivoli.tip.dijit.TIPTableFooter");
dojo.require("ibm.tivoli.tip.dijit.TIPTableRefreshButton");

// NLS
dojo.requireLocalization("ibm.tivoli.tip.dijit", "resources");

dojo.provide("ibm.tivoli.tip.dijit.TIPTableV2");
dojo.provide("ibm.tivoli.tip.dijit.TableFilterAdapter");
/******************************************************************************
* Provides dojox.grid within a layout that contains Title Bar (optional),
* toolbar, filter, and footer.  You must pass all Grid parameters on
* construction.
*
* Authors: Joseph Firebaugh
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.TIPTableV2", dijit.layout.LayoutContainer,
{
  /*** PRIVATE VARIABLES ***/
  _backupModel: null,     /*temporary storage of original model when filtering is on*/
  
  /*** INSTANCE VARIABLES ***/
  label:            null,  /* set label to have table title bar */
  filterAdapter:    null,  /* provide a custom filter adapter if warrented */
  tableParameters:  null,  /* required dojox.Grid params */
  addRefreshButton: true,  /* have refresh button? */
  
  multiselect:      false, /* table has multi-select? */
  
  /*****************************************************************************
  * After Grid Construction -- setup toolbar and footer
  *****************************************************************************/
  postCreate: function()
  {
    //ensure "super()" is called
    this.inherited( "postCreate", arguments );
    
    var cPane = new dijit.layout.ContentPane( { layoutAlign: "top" } );
    this.addChild( cPane );
            
    var div = dojo.doc.createElement( 'div' );
    cPane.setContent( div );

    //-----------------------------
    // Conditionally add Title bar
    //-----------------------------
    if ( this.tableTitle !== null )
    {
      this.titleBar = new ibm.tivoli.tip.dijit.TIPTableTitleBar( { label: this.label }, div );
    
      cPane = new dijit.layout.ContentPane( { layoutAlign: "top" } );
      this.addChild( cPane );

      div   = dojo.doc.createElement( 'div' );
      cPane.setContent( div );
    }

    //-----------------------------
    // Add Toolbar and filter area
    //-----------------------------
    this.filterBar = new ibm.tivoli.tip.dijit.TIPTableToolbar( {}, div );
    
    //-----------------------------
    // Create and add dojox.Grid            
    //-----------------------------    
    cPane = new dijit.layout.ContentPane( { layoutAlign: "client" } );
    cPane.style.padding="0px";
    cPane.style.margin="0px";    
    this.addChild( cPane );

    div   = dojo.doc.createElement( 'div' );
    cPane.setContent( div );

    dojo.connect( cPane, "resize", this, function() 
    {
     this.grid.resize();
    });    

    this.grid = new dojox.Grid( this.tableParameters, div );

    this.grid.selection.multiSelect = this.multiselect;    
    
    this._modelListener = dojo.connect( this.grid, "setModel", this, "clearFilter" );    
    //-----------------------------
    // Create and add footer            
    //-----------------------------    
    cPane = new dijit.layout.ContentPane( { layoutAlign: "bottom" } );
    this.addChild( cPane );

    div   = dojo.doc.createElement( 'div' );
    cPane.setContent( div );

    this.footer = new ibm.tivoli.tip.dijit.TIPTableFooter( {}, div );        
    this.footer.setTable( this.grid );
    
    /** Add refresh button **/
    if ( this.addRefreshButton )
    {
      this.filterBar.toolbar.addChild( new ibm.tivoli.tip.dijit.TIPTableRefreshButton( { onClick: dojo.hitch( this, "onRefresh" ) } ) );
    }
    
    this.filterBar.searchField.setTable( this.grid );    
    dojo.subscribe ( this.filterBar.searchField.id + "searchInvoked", this, "onFilter" );        
    
    this.resize();
  },
  
  /*****************************************************************************
  * Refresh button was pressed
  *****************************************************************************/
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
      this._backupModel = this.grid.model;
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
      var keep    = false;
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
    if( this.grid.model )
    {
	  this.grid.model.notObserver( this.grid );
    }
    
    this.grid._setModel( newModel );
    
    this.grid.update();    
  },
  
  /****************************************************************************
  * Clear client side filter
  ****************************************************************************/
  clearFilter: function()
  {
    if ( this._backupModel !== null )
    {
      //borrowed this code from Grid.js -- can't call this.setModel() because clearFilter will occur
      if( this.grid.model )
      {
        this.grid.model.notObserver( this.grid );
      }

      this.filterBar.searchField.searchField.value = "";

      this.grid._setModel( this._backupModel );
            
      this._backupModel = null;
      
      this.grid.update();    
    }
  },
  
  destroy: function()
  {
    dojo.disconnect( this._modelListener );
    
    this.clearFilter();
    this.filterBar.searchField.destroy();
    this.filterBar.destroy();
    this.footer.destroy();
    
    this.grid.destroy();    
    
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
