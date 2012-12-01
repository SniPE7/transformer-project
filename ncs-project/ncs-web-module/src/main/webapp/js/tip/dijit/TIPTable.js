/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2007 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.form.TextBox");
dojo.require("dojox.grid.Grid");
dojo.require("dijit.Toolbar");
//dojo.require("dojox.data.QueryReadStore");

dojo.require("ibm.tivoli.tip.dijit.TIPButton");
dojo.require("ibm.tivoli.tip.dijit.TIPTableSearchField");
dojo.require("ibm.tivoli.tip.dijit.TIPContextMenuV2");
dojo.require("ibm.tivoli.tip.dojo.TIPDojoData");
dojo.require("ibm.tivoli.tip.dojo.TIPQueryReadStore");
dojo.require("ibm.tivoli.tip.dojo.Global");
dojo.require("ibm.tivoli.tip.dojo.Server");

// NLS
dojo.requireLocalization("ibm.tivoli.tip.dijit", "resources");

dojo.provide("ibm.tivoli.tip.dijit.TIPTable");
dojo.provide("ibm.tivoli.tip.dijit.TIPTableContainer");
/******************************************************************************
* This container places the search bar and toolbar around a given 
* dojox.grid.Grid 
*
* Authors: Joseph Firebaugh, Joshua Allen, Ken Parzygnat
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.TIPTableContainer",
            [ dijit._Widget, dijit._Templated ],
{
	templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit","templates/TIPTable.html"),	
	widgetsInTemplate: true,
	
	/* Do you want the default buttons added to the toolbar? */
	makeDefaultButtons: true,	

	/* Reference to the dojox.grid.Grid */
	grid: null,
	
	/* Default timeout for requests made to the server */
	requestTimeout: 30000,
	
	multiSelect: true,
	rowDblClick: null,
	
	// open: Boolean
	//    Whether toolbar is opened or closed on first render.
	open: false,

	// duration: Integer
	//    Time in milliseconds to wipe in/wipe out toolbar
	duration: 300,
	
	// null means auto width
	// 100% takes all available width
	width: "100%",
	
	height: "200px",

	constructor: function () 
	{
		this.resources_ = dojo.i18n.getLocalization("ibm.tivoli.tip.dijit", "resources");	
		this.tooltips = [];
		this.connections = [];
		this.topics = [];
	},
	
	/**************************************************************************
	* Called after template has been processed.
	*
	* Lets check to see if a dojox.grid.Grid was passed in on params.  If so,
	* add it to the DOM tree and setup.
	**************************************************************************/
	postCreate: function ()
	{
	    if( !this.open ) {
			this.wipeNode.style.display = "none";
	    }
		this._updateToggleClass();    
	
		//call super
		this.inherited( "postCreate", arguments );
		
		//if grid passed on params then set it
		if ( this.grid !== null )
		{
			this.setGrid( this.grid );
		}
		
		//initialize the toolbar
		if ( this.makeDefaultButtons === true ) 
		{ 
			this.initializeToolbar(); 
		}
		
		// set width
		if (this.width !== null) {
			this.setWidth (this.width);
		}
		
		// set height
		if (this.height !== null) {
			this.setHeight (this.height);
		}
		
		this.topics.push (dojo.subscribe (this.searchField.id + "searchInvoked", this, "onSearch"));
		this.setupAnimation();
	},
	
	setupAnimation: function () {
	    // setup open/close animations
		var wipeNode = this.wipeNode;
        
	    this._wipeIn = dojo.fx.wipeIn({
	      node: this.wipeNode,
	      duration: this.duration,
	      beforeBegin: function(){
	      }
	    });
    
	    this._wipeOut = dojo.fx.wipeOut({
	      node: this.wipeNode,
	      duration: this.duration,
	      onEnd: function(){
	      }
	    });
	},
	
	/**************************************************************************
	* Can be called at any time to place a grid within this "container"
	**************************************************************************/
	setGrid: function( grid, append )
	{
		this.grid = grid;
		
		this.searchField.setTable( grid );

		if (append === false) 
		{
		} 
		else 
		{
		  this.gridContainer.appendChild( grid.domNode );
		}
		
		this.grid.selection.multiSelect = this.multiSelect;
	},
	
	setWidth: function (width) 
	{
		this.width = width;
		//dojo.byId (this.id + "tableContainer").width = this.width;
		if (this.tableContainer && this.tableContainer.width) {
			this.tableContainer.width = width;
		}
	},
	
	setHeight: function (height)
	{
		this.height = height;
//		this.gridContainerCell.height = this.height;
//		dojo.byId (this.id + "gridContainerCell").height = this.height;
	},
	
	/**************************************************************************
	* Get the dojox.grid.Grid object
	**************************************************************************/
	getGrid: function()
	{
		return this.grid;
	},
	
	/**************************************************************************
	* Get the dijit.Toolbar attached to the table
	**************************************************************************/
	getToolbar: function() 
	{
		return this.toolbar;
	},
	
	getFooterToolbar: function () 
	{
		return this.footerToolbar;
	},
	
	/**************************************************************************
	* Initialize the toolbar with default buttons (like refresh)
	**************************************************************************/
	initializeToolbar: function() 
	{
		var button = new ibm.tivoli.tip.dijit.TIPButton(
			{	label: this.resources_.REFRESH, 
				id: this.id + "refreshButton",
				showLabel: false, 
				iconClass: "refreshIcon",
			 	onClick: dojo.hitch (this, this.onRefresh) 
			});
		this.toolbar.addChild(button);
		
		if (this.multiSelect) {
			this.toolbar.addChild (new dijit.ToolbarSeparator());
		
			button = new ibm.tivoli.tip.dijit.TIPButton(
				{	label: this.resources_.SELECT_ALL, 
					showLabel: false, 
					iconClass: "selectAllIcon",
				 	onClick: dojo.hitch (this, this.selectAll) 
				});
			this.toolbar.addChild(button);
			
			button = new ibm.tivoli.tip.dijit.TIPButton(
				{	label: this.resources_.DESELECT_ALL, 
					showLabel: false, 
					iconClass: "deselectAllIcon",
				 	onClick: dojo.hitch (this, this.deselectAll) 
				});
			this.toolbar.addChild(button);
		}
	},
	
	/**************************************************************************
	* This method fires a widgetID#Refresh event when the refresh buttons is
	* pressed.  OR you can overload this method instead. If you overload this
	* method you should call inherited so the table is cleared of contents.
	**************************************************************************/
	onRefresh: function() 
	{
//		this.setLoading();
		
		//create empty model
		this._model = new dojox.grid.data.DojoData(null, null);
		
		//clear table
		this.grid.setModel( this._model );
		
		//post publish event
		dojo.publish (this.id + "#Refresh", [ "Refresh" ]);
	},
	
	setLoading: function () 
	{
		var btn = dojo.byId (this.id + "refreshButton");
		if (btn) {
			dojo.removeClass (btn, "refreshIcon");
			dojo.addClass (btn, "loadingIcon");
		}
	},
	
	setSteady: function () 
	{
		var btn = dojo.byId (this.id + "refreshButton");
		if (btn) {
			dojo.removeClass (btn, "loadingIcon");
			dojo.addClass (btn, "refreshIcon");
		}
	},
	
	selectAll: function () 
	{
		var count = 0;
		if (this._model.getRealRowCount) {
			count = this._model.getRealRowCount();
		} else {
			count = this._model.getRowCount();
		}
		
		for (var i=0; i<count; i++) {
			this.grid.selection.addToSelection(i);
		}
		
		if (this.updateFooter) {
			this.updateFooter (this._model.id);
		}
	},
	
	deselectAll: function () 
	{
		this.grid.selection.unselectAll();
		if (this.updateFooter) {
			this.updateFooter (this._model.id);
		}
	},
	
	onSearch: function (tipSearchField, event) 
	{
		console.log ("enter onSearch with search value: " + tipSearchField.searchField.value);
		
		if (this.search) 
		{
			var columns = this.searchField.getSelectedColumns();
			this.search (columns, tipSearchField.searchField.value);
		}
	},
	onToggleKeyPress: function (event) {
	  	if (event.keyCode == 13) {
		  	event.cancelBubble = true;
	  		event.returnValue = false;
	  		console.log ("in onToggleKeyPress: pressed enter");
	  		this.toggleToolbar();
	  	}
	},
	
  /*****************************************************************************
  * The toggle open/close button div was clicked -- toggle open/close state
  *****************************************************************************/
  toggleToolbar: function()
  {
    dojo.forEach([this._wipeIn, this._wipeOut], function(animation){
      if(animation.status() == "playing"){
        animation.stop();
      }
    });

    this[this.open ? "_wipeOut" : "_wipeIn"].play();
    this.open =! this.open;    
    this._updateToggleClass();    
	dojo.publish (this.id + "#ToggleToolbar", [ "ToggleToolbar" ]);
  },
  openToolbar: function () {
  	this.open = false;
  	this.toggleToolbar();
  },
  closeToolbar: function () {
  	this.open = true;
  	this.toggleToolbar();
  },
  
  /*****************************************************************************
  * based on the open/close state of widget change CSS to update button
  *****************************************************************************/
  _updateToggleClass: function()
  {
    if ( this.open === true )
    {
      dojo.removeClass( this.domNode, "close"  );
         dojo.addClass( this.domNode, "open" );          
    }
    else
    {
      dojo.removeClass( this.domNode, "open" );
         dojo.addClass( this.domNode, "close"  );          
    }  
  },
  
  resize: function () {
  	// placeholder... do nothing for now
  }
	
});

/******************************************************************************
* TIP Based Table that creates the underlying dojox.grid.Grid for you based on
* a given data store or Global that uses defined JSON returns for model data
* and column structure.
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.TIPTable",
              ibm.tivoli.tip.dijit.TIPTableContainer,
{
	global: null,
	tableID: null,
	cmProvider: null,
	store: null,
	tableArgs: null,
	gridParms: {autoWidth: false, autoHeight:false, rowsPerPage: "10", rowCount: "5" },
	query: null,
	_contextMenu: null,
	_model: null,
   _onRowContextMenuListener: null,
   _contextMenuAdapter : null,

   clientViewStructure : null, // Provide if you want to provide view structure from client
   clientRfcItems : null,      // Provide if you are providing above view structure.

  
	constructor: function() {
	},
	
	postCreate: function () {
		this.inherited (arguments);
		this.topics.push (dojo.subscribe ("/ibm.tip/TIPDojoData#rowCountUpdated", this, "updateFooter"));
	},

	startup: function() {
		console.info ("enter TIPTable.startup");
		try {
			console.info (this.grid);
			if (!this.grid) {
				this.makeGrid();
			}

			if (this.store) {
				this.configureTableModel();
				this.afterViewLoad();
			} else if (this.global) {
				this.getView();
				this.getStore();
//  		dojo.connect (this.grid, 'onmouseover', this, 'getSelectedData');
			}
			
			this.grid.startup();
		} catch (ex) {
			console.error ("Caught exception in TIPTable.startup(): ");
			console.error (ex);
		}
		
		console.info ("exit TIPTable.startup");
	},
	
	afterViewLoad: function (rcfItems) {
		console.log ("Enter TIPTable.afterViewLoad");
		if (this.cmProvider) {
			this.configureContextMenu(rcfItems);  			
	  	}	  	
	},

	makeGrid: function () {
		console.info ("enter makeGrid");
		this.setGrid( new dojox.Grid(this.gridParms, dojo.byId(this.id + "gridContainer")), false);
		this.connections.push (dojo.connect (this.grid, "onRowClick", this, "onRowClick"));
		this.connections.push (dojo.connect (this.grid, "onRowDblClick", this, "onRowDblClick"));
	},
  
   getContextMenuAdapter: function()
   {
      if (this._contextMenuAdapter === null) {
         // Create the contextMenuAdapter.
         this._contextMenuAdapter = new ibm.tivoli.tip.dijit.TIPContextMenuV2({cmProvider: this.cmProvider});
      }
      return this._contextMenuAdapter;
   },
  
    /************************************************************************
    * This method is called each time a context menu is requred on a row
    ************************************************************************/
    _onRowContextMenu: function( e )
    { 
      var row = e.grid.model.getRow( e.rowIndex );
      
      if ( row )
      {
        //select the row that the context menu was initiated on
        e.grid.selection.unselectAll( e.rowIndex );
        e.grid.selection.setSelected( e.rowIndex, true );
                
        //tell the context menu adapter to show the menu
        // no context is provided because we overload the _loadMenuItems method
        this.getContextMenuAdapter().contextMenuOpen( e, {selectedData: this.getSelectionData()} );
      }
    },

	configureContextMenu: function (rcfItems) {
		if ((this.cmProvider.parentid === null) || (this.cmProvider.parentid === "")) {
			this.cmProvider.setParentId (this.id);
		}
		if ((this.cmProvider.rcfItems === null) && (rcfItems)) {
			this.cmProvider.rcfItems = rcfItems;
		}

      this._onRowContextMenuListener = dojo.connect( this.grid, "onRowContextMenu", this, "_onRowContextMenu" );
      	 //FIXES CONTEXT MENU BUG with selected table rows IN DOJO 1.0.2
		 this.grid.focus.styleRow = function(inRow){};
      
	},
	
	getView: function () {
      if (this.clientViewStructure === null) {
          var kw = {
                      content:   {
                                    command: "getTableColumns",
                                    arg_fqTableId: this.id,
                                    arg_portletNamespace: this.global.namespace
                                 },
                      timeout:   this.requestTimeout,
                      onResponse:{
                                    callback: dojo.hitch(this, this.getViewCallback)
                                 }
                   };

          if (this.tableArgs) {
             for (var key in this.tableArgs) {
                if (key) {
                      kw.content[key] = this.tableArgs[key];
                }	
             }
          }

          ibm.tivoli.tip.dojo.Server.getSingleton( this.global ).get( kw );
      }
      else
      {
         this.setupView( this.clientViewStructure, this.clientRfcItems );
      }
	},
	
	handleError: function (response, ioArgs) {
		console.error ("HTTP status code: ", ioArgs.xhr.status);
		return response;
	},
	
	handleTimeout: function (response, ioArgs) {
		console.error ("Timeout for request: " + ioArgs.xhr);
		return response;
	},
	
	setupView: function (structure, rcfItems) {
		this.grid.setStructure( structure );	
		this.searchField.setTable( this.grid );
 	    this.afterViewLoad(rcfItems);
	},
  
	getViewCallback: function (data, ioArgs) {
      var view1 = { cells: [ data.cells ] };
      // a grid layout is an array of views.
      var layout = [ view1 ];
      this.setupView( layout, data.rcfItems );
	},
  
	getStore: function () {
	  	console.info ("enter getStore");
		// TODO: Change doClientPaging:false when paging is fixed
	  	this.store = new ibm.tivoli.tip.dojo.TIPQueryReadStore ({ global: this.global, tableID: this.id, jsId: "jsonStore", doClientPaging:false, requestTimeout: this.requestTimeout });
  		console.log ("got the store: ");
	  	console.log (this.store);
//	  	this.store.fetch();
	  	this.configureTableModel();
  	},
  	
	onRefresh: function() {
//		this.setLoading();
	
		//create a new model to force a refresh
		//this._model = new ibm.tivoli.tip.dojo.TIPDojoData (null, null, { store: this.store, rowsPerPage: 20, query:{query:this.query}, clientSort: true });

		//clear table
		//this.grid.setModel( this._model );
		
		//post publish event
		//dojo.publish (this.id + "#Refresh", [ "Refresh" ]);

        // What is going on here?
        // Previously, the preceding three lines were used in this function to refresh
        // the table.  Create a new model, set the model, and issue the event.  However,
        // the problem is that TIPTable gets its data from the server, so the new model
        // was set prior to it having any data.  This resulted in the table 
        // "flashing".  That is the table would go all blank, then turn around and fill
        // with the data from the server.  This didn't look good, so the below code fixes
        // this.  A callback was added to the store so that when it finishes handling
        // data from the server, it will call the callback.  This enables us to first 
        // create the model, and load its first page, then swap it in to the table.
        
        // Create the callback function that will swap in the new model when it has some data
        var cb = dojo.hitch(this,function(){  console.debug("----- Inside the refresh callback ----");
                                              this.store.setLoadCompleteCallback(null);
                                              this._model=this.my_new_model; 
                                              this.grid.setModel(this.my_new_model);
                                              // I found that the following update is needed when only
                                              // one row is in the model.
                                              this.grid.update();
                                              this.my_new_model = null; 
                                              this.updateFooter(this._model.id);
                                              dojo.publish (this.id + "#Refresh", [ "Refresh" ]);
                                            });
                                            
        this.store.setLoadCompleteCallback(cb);  // set the callback

		this.my_new_model = new ibm.tivoli.tip.dojo.TIPDojoData (null, null, { store: this.store, rowsPerPage: 20, query:{query:this.query, refresh:"true"}, clientSort: false });
		// preserve sort on a refresh
		this.my_new_model.sortColumn = this._model.sortColumn;
		
		// Now need to set the row count equal to the rowsPerPage and make a request for the first page
		// in order to pre-load the model.  Notice that when this request completes, our callback above
		// will be invoked.
		this.my_new_model.setRowCount(20);
		this.my_new_model.getRow(0);
		
	},
	
	refreshWithArgs: function (args) {
		this.tableArgs = args;
		var dataQuery = {query:this.query};
	  	if (this.tableArgs) {
	  		for (var key in this.tableArgs) {
	  			if (key) {
	  				dataQuery[key] = this.tableArgs[key];
	  			}	
	  		}
	  	}


		//create a new model to force a refresh
		this._model = new ibm.tivoli.tip.dojo.TIPDojoData (null, null, { store: this.store, rowsPerPage: 20, query:dataQuery, clientSort: false });
		
		//clear table
		this.grid.setModel( this._model );
		
		//post publish event
		dojo.publish (this.id + "#Refresh", [ "Refresh" ]);
	},
  	
  	configureTableModel: function () {
  		console.info ("enter TIPTable.configureTableModel");
  		this.query = '';
		this._model = new ibm.tivoli.tip.dojo.TIPDojoData (null, null, { store: this.store, rowsPerPage: 20, query:{query:this.query}, clientSort: false });
		this.grid.setModel( this._model );
		console.info ("exit TIPTable.configureTableModel");
	},
	
	search: function (columns, value) {
		console.log ("enter search with columns: ");
		console.log (columns);
		console.log ("and value: ");
		console.log (value);
		this.query = '';
		for (var x in columns) {
			if (this.query === '') {
				this.query += columns[x];
			} else {
				this.query += "," + columns[x];
			}
		}
		this.query += ":" + value;
		console.log ("in search with query: " + this.query);
		// preserve sort
		var sort = this._model.sortColumn;
		this._model = new ibm.tivoli.tip.dojo.TIPDojoData (null, null, { store: this.store, rowsPerPage: 20, query:{query:this.query, refresh:"true"}, clientSort: false });
		this._model.sortColumn = sort;
		
		//clear table
		this.grid.setModel( this._model );
	},

	getSelectedRowIndexes: function () {
		return this.grid.selection.getSelected();
	},
	
	getValueAt: function (row, column) {
		return this.grid.model.getDatum (row, column);
	},
	
	getViewColumnsIDs: function () {
	  	var columns = this.grid.structure[0].cells[0];
	  	var columnIDs = [];
	  	for ( var i in columns ) {
	  		if (i) {
				columnIDs.push (columns[i].field);
			}
		}
		
		return (columnIDs);
	},
	
	getAllColumnIndexes: function () {
		var count = this.grid.model.getColCount();
		var columnIDs = [];
		for (var i=0; i<count; i++) {
			columnIDs.push (i);
		}
		
		return (columnIDs);
	},
	
	isNamingAttribute: function (field) {
	  	var columns = this.grid.structure[0].cells[0];
	  	if (columns[field]) {
		  	return columns[field].isNamingAttribute;
		} else {
			return (false);
		}
	},

	getModelData: function () {
		var selectedRows = this.getSelectedRowIndexes ();
		var row = selectedRows[0];
		var columnIDs = this.getAllColumnIndexes();

		for (var column in columnIDs) {
			if (column) {
				var data = this.grid.model.getDatum (row, column);
				console.log ("cell value for row: " + row + ", and column: " + column + " : " + data);
			}
		}
	},
	
	getIdentifier: function () {
//	  	var columns = this.grid.structure[0].cells[0];
	  	return (this.grid.model.getColCount() - 1);
	},

	getSelectionData: function () {
		this.getModelData();
		var selectedRows = this.getSelectedRowIndexes ();
		console.log ("selectedRows: " + selectedRows);

		var rowData = null;
		var columnIDs = this.getAllColumnIndexes();
		var identifier = this.getIdentifier();
		var rowJSON = "{ ";
		
		dojo.forEach (selectedRows,
			function (row) {
				var firstTime = true;
				rowJSON += '"id": "' + this.getValueAt (row, identifier) + '", ';
			
				for (var column in columnIDs) {
					if (column) {
						console.log ("getting cell value for row: " + row + " and column: " + column);
						var cellValue = this.getValueAt (row, column);
						if (!firstTime) {
							rowJSON += ', ';
						} else {
							firstTime = false;
						}
						rowJSON += '"' + column + '": "' + cellValue + '"';
					}
				}
			}, this);

		rowJSON += "}";
		
		var columnsJSON = "";
		var firstLoop = true;
		for (var c in columnIDs) {
			if (c) {
				if (!firstLoop) {
					columnsJSON += ', ';
				} else {
					firstLoop = false;
				}
				columnsJSON += '{ "field": "' + c + '", "isNamingAttribute": "' + this.isNamingAttribute(c) + '" }';
			}
		}
		
		var resultStr = "result = {";
		resultStr += "columns: [ ";
		resultStr += columnsJSON;
		resultStr += " ], rows: ";
		resultStr += rowJSON;
		resultStr += "};";
		
		console.log ("resultStr: " + resultStr);

		var result = eval (resultStr);
		
//		var sample = { 
//		columns: [{ field: "srcToken", hidden: true },
//				  { field: "ManagedSystemName", label: "Managed System Name" },
//				  { field: "column_2", label: "baz" }],
//		
//		 rows: { srcToken: "1234", ManagedSystemName: "bar", column_2: "bang" }
//		 		};
		
		return (result);
	},

	onRowClick: function (evt) {
		console.log ("enter TIPTable.onRowClick");
		this.updateFooter (this._model.id);
	},

    // The following processes double clicks on a row.  The user will add the double
    // click context on construction such as:
    // var dblClickContext = { content: { command: "myDblClickCommand" },
    //                         callback: dojo.hitch(this, this.myDblClickAction)  
    //                       };
    // and include it on the declaration of TIPTable with "rowDblClick: dlbClickContext".
    // Acceptable attributes:
    //     content: (optional) content to be sent to the callback
    //     callback: (required) a javascript function to be called.  It can have the form:
    //             myfunction: function(command, table)
    //                   where command is the command passed in the context,
    //                         table is a reference to this table
    
	onRowDblClick: function (evt) { 
		console.log ("enter TIPTable.onRowDblClick");
		if (this.rowDblClick) {
		    if (this.rowDblClick.callback) {
		        this.rowDblClick.callback(this, this.rowDblClick.content);
		    }
		}
	},

	updateFooter: function (modelID) {
		if (modelID == this._model.id) {
			var numSelected = 0;
			var total = this._model.getRealRowCount();
			var selected = this.getSelectedRowIndexes();
			if (selected) {
				numSelected = selected.length; 
			}
			var footerText = dojo.string.substitute(this.resources_.TABLE_FOOTER_TEXT, { 0: numSelected, 1: total });
			this.footer.innerHTML = '<p class="footerLabel">' + footerText +  '</p>';
		}
	},
	
	
	destroy: function () {
		console.info ("enter TIPTable.destroy");
		dojo.forEach (this.tooltips, function (tt) {
			tt.destroy();
		});
		dojo.forEach (this.topics, function (topic) {
			dojo.unsubscribe (topic);
		});
		dojo.forEach (this.connections, function (conn) {
			dojo.disconnect (conn);
		});
		
		// remove the context menu because it lives outside of this domNode		
		if (this._contextMenu) {
			this._contextMenu.removeAllItems();
			this._contextMenu.destroy();
			dijit.registry.remove (this._contextMenu.id);
		} else {
			console.info ("no context menu");
		}

      if (this._onRowContextMenuListener) {
         dojo.disconnect(this._onRowContextMenuListener);
      }
      this._contextMenuAdapter = null;
      if (this.cmProvider) {
         this.cmProvider.destroy();
         this.cmProvider = null;
      }

		this.inherited (arguments);
	}
});
