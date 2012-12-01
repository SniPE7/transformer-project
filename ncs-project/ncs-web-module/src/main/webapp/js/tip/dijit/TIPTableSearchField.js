/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * 5724-C51
 *
 * (C) Copyright IBM Corp. 2007 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.Dialog");
dojo.require("dijit.Menu");
dojo.require("dijit.Tooltip");

// NLS
dojo.requireLocalization("ibm.tivoli.tip.dijit", "resources");

dojo.provide("ibm.tivoli.tip.dijit.TIPTableSearchField");

dojo.declare("ibm.tivoli.tip.dijit.TIPTableSearchField",
            [ dijit._Widget, dijit._Templated ],
{
  widgetsInTemplate: true,
  
  /* The TIPTable that this search bar is attached to */
  table: null,
  
  _ALL_COLS_NAME: "QF_ALL_COLS",
  
  /** An array of column names that should participate in filtering **/
  columnsToFilterOn: { "QF_ALL_COLS": true },
  
  /** Keeps track of all topic subscriptions to cleanly destroy later **/
  topics: [],
  
  _qf_arrow_src: dojo.moduleUrl("ibm.tivoli.tip.dijit","themes/images/qf_arrow.gif"),
  
  /** Keeps track of all event connections **/
  objectConnections: [],
  
  /** Keeps track of all children widgets in dropdown **/
  childrenWidgets: [],
  
  templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit","templates/TIPTableSearchField.html"),

  constructor: function( parms ) 
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
   
  /********************************************************************
  * This method must be called to set the underlying table that the 
  * search bar will filter on.  As soon as this method is called
  * the table is queried for its "structure" (columns) and the dropdown
  * column filter dialog is populated
  *********************************************************************/
  setTable: function( table )
  {
  	// Save a reference to the table
  	this.table = table;
  	
  	this.clear(); //ensure that we start with a fresh slate
  	  	
  	var div  = document.createElement( 'DIV' );
  	var div2 = document.createElement( 'DIV' );
  	div2.className="qf-col-filter-title";	  
  
  	div2.appendChild( document.createTextNode( this.resources_.FILTER_ON ) );
  	div.appendChild( div2 );

  	div2 = document.createElement( 'DIV' );
	div2.className="qf-col-chkbox-label-row";
  	
  	var checkbox = new dijit.form.CheckBox( { id     : table.id + this._ALL_COLS_NAME,
  											  pId    : table.id,
  	                                          name   : this._ALL_COLS_NAME,
  	                                          checked: true } );
//  	                                          checked: this.columnsToFilterOn[ this._ALL_COLS_NAME ] } );

	this.childrenWidgets[ checkbox.id ] = checkbox;
	
	checkbox.onColsFilter = function()
	{
		this.setChecked( false );
	};
	
	checkbox.onCheckedEvent = function ()
	{
  		if ( this.checked )
  		{
	  		dojo.publish( this.pId + "_filterOnAll", ["Filter on all rows"] );
  		}
	};  	           
	
  	this.topics[ this._ALL_COLS_NAME ] = dojo.subscribe( table.id + "_filterByCol", checkbox, "onColsFilter" );                                                                         
	this.objectConnections[ this._ALL_COLS_NAME ] = dojo.connect( checkbox, "setChecked", checkbox, "onCheckedEvent" );  	                                          

	var label = document.createElement( 'LABEL' );
	label.className = "qf-col-chkbox-label";
	//label["for"] = checkbox.id; //doesn't work on FF
	dojo.attr(label,"for",checkbox.id);
	label.title = this.resources_.ALL_COLUMNS;
	
	label.appendChild( document.createTextNode( this.resources_.ALL_COLUMNS ) );
	
	div2.appendChild( checkbox.domNode );  	                                         
	div2.appendChild( label );  	                                         

	div.appendChild( div2 );

	var columns = [];
	var i = 0;
	var j = 0;
	
	table.views.forEach( function( view, i )
	{
		if ( view.structure.cells )
		{
			for ( i in view.structure.cells )
			{
				for ( j in view.structure.cells[i] )
				{
					 var v = view.structure.cells[i][j];				
		             if (v.field !== undefined) {
                         columns.push( v );
	                 }
				}			
			}
		}
	});  	
  	  	
  	for ( i in columns )
  	{
	  if ( columns[i].isSearchable === false ) {
	    continue;
	  }
  		div2 = document.createElement( 'DIV' );
  		div2.className="qf-col-chkbox-label-row";
  		
  		checkbox = new dijit.form.CheckBox( { //id     : table.id + columns[i].name,//name can contain chars that are illegal for html id type, default id is better
  											  pId    : table.id,
  											  columnID: columns[i].field,
  	                                          name   : columns[i].name,
  	                                          checked: this.columnsToFilterOn[ columns[i].name ] } );

		this.childrenWidgets[ checkbox.id ] = checkbox;
		
		checkbox.onAllColsFilter = function()
		{
			this.setChecked( false );
		};
		
		checkbox.onCheckedEvent = function ()
		{
	  		if ( this.checked )
	  		{
		  		dojo.publish( this.pId + "_filterByCol", ["Filter on individual columns"] );		
	  		}
		};  	           
		
		this.topics[ columns[i].name ] = dojo.subscribe( table.id + "_filterOnAll", checkbox, "onAllColsFilter" );                               
  	    
		this.objectConnections[ columns[i].name ] = dojo.connect( checkbox, "setChecked", checkbox, "onCheckedEvent" );  	                                          
  	                                          
		div2.appendChild( checkbox.domNode );  	                            

		label = document.createElement( 'LABEL' );
		//label["for"] = checkbox.id;//doesn't work on FF
		dojo.attr(label,"for",checkbox.id);
		label.title = columns[i].name;
		label.className = "qf-col-chkbox-label";
		
		label.appendChild( document.createTextNode( columns[i].name ) );
		
		div2.appendChild( label );  	                                         		             
  		
  		div.appendChild( div2 );  		
  	}
  	  	
  	var button = new dijit.form.Button( { id: table.id + "close_btn",
                                        type: "submit", 
  								       label: this.resources_.OK } );

	this.childrenWidgets[ button.id ] = button;	  								       
  								     
	div.appendChild( button.domNode );  								     
  	
  	var tooltipDialog = new dijit.TooltipDialog( { id: table.id + "gf_tooltipdialog" } );

	this.childrenWidgets[ tooltipDialog.id ] = tooltipDialog;	  								       
  	  	
  	tooltipDialog.containerNode.appendChild( div );
  	
  	/** OVERLOAD to allow our own styling to be added **/
  	tooltipDialog.orient = function(/*Object*/ corner)
  	{
		// summary: configure widget to be displayed in given position relative to the button
// TODO: dojo 1.2.3: corner is a domNode, and doesn't have a charAt method.
//		this.domNode.className="dijitTooltipDialog " +" dijitTooltipAB"+(corner.charAt(1)=='L'?"Left":"Right")+" dijitTooltip"+(corner.charAt(0)=='T' ? "Below" : "Above" ) + " qf-TooltipDialog";
	};  	
  	
  	tooltipDialog.execute = function( args )
  	{  		
  	    this.columnsToFilterOn = [];
  	    
  		for ( var i in args )
  		{
			this.columnsToFilterOn[i] = ( args[i][0] ) ? true : false ;
  		}  	
  	};
  	
	var btn = new dijit.form.DropDownButton( { 	id: table.id + "_coltrigger", 
												dropDown: tooltipDialog,
												label: "",
												baseClass: "qf-col-trigger" } );
												
	this.tooltip_dropdownhelp = new dijit.Tooltip({label:this.resources_.SEARCH_DROP_DOWN_HELP, connectId:[btn.id]}, document.createElement("div"));
												
	
	this.childrenWidgets[ btn.id ] = btn;	  								       

	this.colFilter.appendChild( btn.domNode );
		
	btn.startup();
	
	var downArrowSpan =  dojo.query("span.dijitArrowButtonInner", btn.domNode );	
	
	dojo.query("span.dijitButtonText", btn.domNode ).orphan();
	
	downArrowSpan[0].innerHTML = "";
	dojo.removeClass( downArrowSpan[0], "dijitArrowButtonInner" );
	downArrowSpan[0].style.padding="0px";
	
	var image = dojo.doc.createElement( 'IMG' );	
	image.src = this._qf_arrow_src;
	image.border = 0;
	image.style.padding="0px";
	image.style.margin="0px";
	image.alt = this.resources_.SEARCH_DROP_DOWN_HELP;
	
	downArrowSpan[0].appendChild( image );	

  },
  
  _onFocus: function()
  {
	this.domNode.className="quick-filter-focus";  
  },
  
  _onBlur: function()
  {
	this.domNode.className="quick-filter";
  },
  
  _onKeyPress: function(event)
  {
  	if (event.keyCode == 13) {
	  	event.cancelBubble = true;
  		event.returnValue = false;
  		dojo.publish ("onEnter", [this, event]);
  		dojo.publish (this.id + "searchInvoked", [this, event]);
	}
  },
  
  _onHover: function()
  {
	this.colFilter.className="qf-lcap-over";
  },
  
  _onUnHover: function()
  {
	this.colFilter.className="qf-lcap";
  },
  
  _setFilterCol: function ()
  {  	
  },
  

  getSelectedColumns: function ()
  {
  	var result = [];
  	
  	if (this.childrenWidgets[this.table.id + this._ALL_COLS_NAME].checked) {
	  	for (var x in this.childrenWidgets) {
	  		if (x) {
	  			if (this.childrenWidgets[x].columnID !== undefined ) {
		  			result.push (this.childrenWidgets[x].columnID);
		  		}
  			}
  		}
  	} else {
	  	for (var y in this.childrenWidgets) {
	  		if (y) {
	  			if (this.childrenWidgets[y].checked) {
		  			if (this.childrenWidgets[y].columnID !== undefined ) {
		  				result.push (this.childrenWidgets[y].columnID);
		  			}
	  			}
	  		}
	  	}
  	}
  	
	return (result);
  },
  
  setSelectedColumns: function(/*Array*/ cols)
  {
  	for(var y in this.childrenWidgets) {
  		if(y) {
  			this.childrenWidgets[y].attr("checked", dojo.indexOf(cols, this.childrenWidgets[y].columnID) >= 0 );
  		}
  	}
  },
  
  /*************************************************************
  * Clears the contents of the search drop down to be recreated
  *************************************************************/
  clear: function()
  {
  	/** Destroy all subscriptions **/
  	for ( var i in this.topics )
  	{
  		dojo.unsubscribe( this.topics[i] );
  	}
  	
  	delete this.topics;
  	this.topics = [];

  	/** Destroy all event connections **/
  	for ( i in this.objectConnections )
  	{
  		dojo.disconnect( this.objectConnections[i] );
  	}  	

  	delete this.objectConnections;
  	this.objectConnections = [];
  	
  	/** Destroy all children widgets **/
  	for ( i in this.childrenWidgets )
  	{
  		this.childrenWidgets[i].destroy();
  	}  
  	
  	if ( this.tooltip_dropdownhelp )
  	{
  		this.tooltip_dropdownhelp.destroy();
  	}

  	delete this.childrenWidgets;
  	this.childrenWidgets = [];
  	
  	delete this.columnsToFilterOn;
  	this.columnsToFilterOn = [];
  },
  
  /*************************************************************
  * Destroy this widget -- remove all subscriptions and event
  * connections
  *************************************************************/
  destroy: function()
  {  	
  	this.clear();
  	
  	/** call super **/
  	this.inherited( arguments );
  }
  
});
