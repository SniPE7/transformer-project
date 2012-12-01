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

// NLS
dojo.requireLocalization("ibm.tivoli.tip.dijit", "resources");

dojo.provide("ibm.tivoli.tip.dijit.TIPTableFooter");
/******************************************************************************
* The footer for the TIP Table
*
* Authors: Joseph Firebaugh, Joshua Allen
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.TIPTableFooter",
            [ dijit._Widget, dijit._Templated ],
{
  widgetsInTemplate: true,

  templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit","templates/TIPTableFooter.html"),
  
  //the dojox.Grid that this footer belongs to
  table: null,
  
  constructor: function () 
  {
    this.resources_ = dojo.i18n.getLocalization("ibm.tivoli.tip.dijit", "resources");    
  },
  
  postCreate: function()
  {
    if ( this.table !== null )
    {
      this.setTable( this.table );
    }        
  },
  
  setTable: function( table )
  {
    this.table = table;
    
    this._rowClickListener = dojo.connect( this.table, "onRowClick", this, "_onRowClick");            
    this._updateListener   = dojo.connect( this.table, "setModel",   this, "_onRowClick" );            
    
  },
  
  _onRowClick: function()
  {      
    var numSelected = 0;
    var totalRows   = 0;
    
    if ( this.table.model )
    {
		if (this.table.model.getRealRowCount) {
			totalRows = this.table.model.getRealRowCount();
		} else {
			totalRows = this.table.model.getRowCount();
		}
    }

    numSelected = this.table.selection.getSelectedCount();
    
    this.updateFooter( numSelected, totalRows );
  },
    
  updateFooter: function( numSelected, totalRows )
  {
    this.setInfoAreaHTML( dojo.string.substitute( this.resources_.TABLE_FOOTER_TEXT, { 0: numSelected, 1: totalRows } ) );  
  },
  
  setInfoAreaHTML: function ( html )
  {
    this.infoArea.innerHTML = html;
  },
  
  destroy: function()
  {
    if ( this._rowClickListener ) { dojo.disconnect( this._rowClickListener ); }
    if ( this._updateListener )   { dojo.disconnect( this._updateListener ); }
    
    this.toolbar.destroy();
    
    this.inherited( "destroy", arguments );
  }  
});
