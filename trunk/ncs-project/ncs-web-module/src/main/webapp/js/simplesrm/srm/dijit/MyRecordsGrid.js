//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.MyRecordsGrid");

// include modules
dojo.require("dijit._Widget");
dojo.require("dojo.parser");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.CheckBox");
dojo.require("dojox.grid.DataGrid");
//dojo.require("dijit.Tooltip");
dojo.require("dojo.date.locale");
dojo.require("dojo.date.stamp");
dojo.require("dojo.currency");
dojo.require("dojox.color");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.WidgetBase");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.ProgressSpinner");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.BaguetteChart");
//dojo.require("ibm.tivoli.simplesrm.srm.dojo.Hoverer");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.ToolbarButton");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.MessageDialog");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.FormattedDataReadStore");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.Formatter");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.ContextButton");

dojo.require("ibm.tivoli.tip.dijit.TIPTableToolbar");
dojo.require("ibm.tivoli.tip.dijit.TIPTableRefreshButton");

// the MyRecordsGrid class is a widget that makes an
// XMLHttpRequest to a proxy servlet to reach an SRM object structure
// web service that provides information about service catalog requests.
// the widget displays the service catalog request information
// using a dojo grid
dojo.declare(
	"ibm.tivoli.simplesrm.srm.dijit.MyRecordsGrid",
	[dijit._Widget,
	 ibm.tivoli.simplesrm.srm.dijit.WidgetBase,
	 ibm.tivoli.simplesrm.srm.dojo.Formatter],
{		
	headingText: 'My Records',
	requestTimeout: 60000,			// timeout waiting for ajax requests, millisecs.
//	_showGridTooltipHandler: null,	// closure for showing the tooltip
//	_conn_onHover: null,			// connection handle for the hoverer
//	_hoverer: null,					// monitors grid cells for a hovering mouse
	sripeGraph: null,				// the stripe graph showing summary data from the query
	_outstandingDataQuery: null,	// deferred object for the async catalog request query
	simpleGrid: undefined,			// the grid showing the queried catalog requests
	showDetailsBtn: null,		// the show details button on the toolbar
	approveBtn: null,
	rejectBtn: null,
	contextBtns: null,				// context specific buttons
	imagespath: "",					// where my images are found
	header: null,					// DOM node of the header div, if you need to stick stuff in there.
	_initialization_complete: false,
	_column_defs: null,
	_data_key_field: -1,	// index into _column_defs of the key field
	_initial_view_cols: null,		// array of inceces into _column_defs
	_initial_sort_index: -1,
	_initial_sort_asc: false,
	_status_stats: null, 			// cache stats for the stripe graph
	_contextName: "",				// Synonym domain with this grid's context specific requests
	
 	_outstandingStatusDomainRequest: null, // deferred object for the status domain query
 	
 	_isApproval: "false",
 	
 	_requestNumbers: null,
 	
	constructor: function()
	{
		console.log("MyRecordsGrid.ctor");
		
//		this._hoverer = new  ibm.tivoli.simplesrm.srm.dojo.Hoverer();
//		this._conn_onHover = dojo.connect(this._hoverer, "OnHover", this, "_showGridTooltip");

		this.imagesPath = this.getRelativePath("images/");	
	},
	buildRendering: function()
	{
		console.log("MyRecordsGrid.buildRendering");
		this.inherited(arguments);
		
		// for whatever reason, when I try to create this as a templated widget, the grid won't show
		// its data.  Maybe it's because this code fills out the DOM node in the html page, and a 
		// templated widget replaces it.
		// For now, this is good enough.
		
		if(null == this.domNode) {	// should never happen
			this.domNode = document.createElement("div");
			this.domNode.id = this.id;
			this.domNode.className = "my_records_grid_main_div"
		}
		console.log("domNode: ", this.domNode);
		dojo.addClass(this.domNode, "my_records_grid_main_div");
		
//		this.header = document.createElement("div");
//		this.header.className = "my_records_grid_header";
//		dojo.place(this.header, this.domNode, "first");
//		
//		// the grid's title
//		var gridHeading = document.createElement("h2");
//		gridHeading.id = this.id+"_grid_heading";
//		gridHeading.className = "grid_heading";
//		gridHeading.innerHTML = this.headingText;
//		dojo.place(gridHeading, this.header, "first");
		
		{
			// the toolbar        
		    var div = dojo.doc.createElement( 'div' );
		    dojo.place(  div, this.domNode, "last"  );
	        
		    this.toolBar = new ibm.tivoli.tip.dijit.TIPTableToolbar( {}, div );
		    dojo.addClass(this.toolBar.domNode, "srmtoolbar");
	        
		    var button = new ibm.tivoli.tip.dijit.TIPTableRefreshButton({
		    	title:this._uiStringTable.RefreshTable,
    			label: this._uiStringTable.Refresh,	
    			showLabel: false,
		    	onClick: dojo.hitch( this, "refresh" ) 
		    });    
		    
		    button._scroll = false;	// prevents the page from jumping when the the user clicks anywhere in the toolbar.
		    						// I made this change in the TIPTableRefreshButton code too, but it took so long to find
		    						// this bug, that I didn't want an update of the TIP widgets to break it.
		    this.toolBar.toolbar.addChild( button );

		    if("function" == typeof this.showRecordDetails) {
		    			    	
		    	this.showDetailsBtn = new dijit.form.Button({		    		
		    			id: this.id+"_show_details_btn", 
		    			disabled: true,
		    			baseClass: "srm_button",
		    			iconClass:"show_details_btn", 
		    			title:this._uiStringTable.ShowSelectedDetails,
		    			label: this._uiStringTable.Details,	
		    			showLabel: false,
		    			onClick: dojo.hitch( this, "_showDetails" )		    		
		    	});
		    			    	
		    }
		    this.showDetailsBtn._scroll = false;
		    this.toolBar.getToolbar().addChild( this.showDetailsBtn );
		    
		    // get context specific buttons
		    if(this._contextName && this._contextName.length > 0) {
		    	this.contextBtns = new ibm.tivoli.simplesrm.srm.dijit.ContextButtonSet({contextName: this._contextName, toolbar: this.toolBar.getToolbar()});
		    	this.contextBtns.attr("disabled", true);
		    }	
		}
		
		// insert the html tag for dojo to use to insert the grid
		var gridContainer = document.createElement("div");
		gridContainer.id = this.id+"_grid_container";
		gridContainer.className = "my_records_grid_container";
		dojo.place(gridContainer, this.domNode, "last");
		
		// the stripe graph
		var footerDiv = document.createElement("div");
		footerDiv.className = "footer";
		dojo.place(footerDiv, this.domNode, "last");
		
		if (this._isApproval == "false"){
			var graphDiv = document.createElement("div");
			graphDiv.id = this.id+"_stripe_graph";
			//graphDiv.className = "footer";
			dojo.place(graphDiv, footerDiv, "first");
			var bgcolor = dojo.style(this.domNode, "backgroundColor");
			try {
				var total = this._uiStringTable.Total;

				this.baguetteChart = new ibm.tivoli.simplesrm.srm.dijit.BaguetteChart({
					barHeight:15, 
					captionHeight:13, captionFontSize:10, 
					showLegend: true, 
					showTotal: true,
					totalLegend: total}, graphDiv);
				// TODO: why does backgroundColor: transparent or inherited wind up white, when the table cell it's in has a background color?
				dojo.connect(this.baguetteChart, "onDatumClick", this, "_stripeClick");				
			}
			catch(ex) {
				// the baguette chart sometimes fails in ie.
				// don't let it take down the whole UI
				new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("Failed creating the BaguetteChart: " + ex);
				this.baguetteChart = null;
				dojo.style(graphDiv, "display", "none");
			}						
		}else {
			var totalDiv = document.createElement("div");
			totalDiv.id = "totalNumber";
			
			//totalDiv.className = "status_table";
			dojo.style(totalDiv, "textAlign", "right");
			
			var s = document.createElement("span");
			s.className = "requestnumber";
			//dojo.style(s, "textAlign": "right");
			//s.className = "status";
			
			dojo.place(s, totalDiv, "first");
			dojo.place(totalDiv, footerDiv, "first");
		}
		
		// now fill in the grid
		// html element to hang the grid
		var gridElement = document.createElement("span");
		gridElement.id = this.id+"_simple_grid";
		gridElement.className = "my_records_grid_simple_grid";
		dojo.place(gridElement, gridContainer, "first");
	
		// define the grid's initial view and data
		var cols = [];
		for(var c in this._initial_view_cols) {
			cols.push(this._column_defs[this._initial_view_cols[c]]);
		}
		cols[cols.length-1].width = "auto";
		
		// insert simple (non-subgrid) version of the grid showing service catalog request info
		this.simpleGrid = new dojox.grid.DataGrid(
			{
				structure: cols,
				autoHeight: false,
				autoWidth: false,
				selectionMode: "single",
				rowSelector: "1em"
			},
			gridElement
		);
		dojo.style(this.simpleGrid, "width", "100%");
		this.simpleGrid.startup();
		
		if(this._initial_sort_index >= 0)
			this.simpleGrid.setSortIndex(this._initial_sort_index, this._initial_sort_asc);
		
		//this._rowclickhandler = dojo.connect(this.simpleGrid, "onRowClick", this, "_onRowClick");
		this.connect(this.simpleGrid, "onSelected", "_onRowSelect");
		this.connect(this.simpleGrid, "onDeselected", "_onRowDeselect");
					
//		// add tooltip support
//		dojo.connect(this.simpleGrid,
//			"onCellMouseOver",
//			dojo.hitch(this, function(e)
//			{
//				if(e.cellIndex === 0 && e.cellNode.tagName !== "TH")
//				 {
//					//var msg = this.getGridToolTip(e);
//					//if (msg != null)
//					//{
//						this._hoverer.setDOMNode(e.cellNode);
//						this._hoverer.connect(e);
//						this._hoverer.setData("rowIndex", e.rowIndex);
//						//this._hoverer.setData("text", msg);
//					//}
//				}
//			}));
//		
//		dojo.connect(this.simpleGrid,
//			"onCellMouseOut", 
//			dojo.hitch(this, function(e)
//			{
////				console.log("onCellMouseOut");
//				dijit.hideTooltip(e.cellNode);
//				dijit._masterTT._onDeck=null;
//			}));
//			
//		dojo.connect(this.simpleGrid,
//			"onCellClick",
//			function(e)
//			{
//				console.log("onCellClick");
//				dijit.hideTooltip(e.cellNode);
//				dijit._masterTT._onDeck=null;
//			});				
//		// FYI: we don't get an onCellMouseOut when the mouse leaves a cell by leaving the 
//		// right-hand side of the last cell in a row
//		// I tried and we don't get an onMouseOut eiher.


	},
	postCreate: function()
	{
		this.toolBar.searchField.setTable( this.simpleGrid );  
		  
	},
	startup: function()
	{
		this.inherited(arguments);
		if(this.baguetteChart)
			this.baguetteChart.startup();
		this._initialization_complete = true;
		
		dojo.connect(this.toolBar.toolbar, "onblur", null, function(evt) {
			console.log("### toolbar blur");
			evt.stopPropagation();
		});
		dojo.connect(this.toolBar.toolbar, "onfocus", null, function(evt) {
			console.log("### toolbar focus");
			evt.stopPropagation();
		});
		dojo.connect(this.toolBar.toolbar, "onfocus", null, function(evt) {
			console.log("### toolbar focus");
			evt.stopPropagation();
		});
		dojo.connect(this.toolBar.toolbar, "onmousedown", null, function(evt) {
			console.log("### toolbar mousenown");
			evt.stopPropagation();
		});
	},
	destroy: function()
	{
		if(this.progressSpinner) {
			this.progressSpinner.destroy();
			this.progressSpinner = null;
		}
		if(this.simpleGrid) {
			try {
				this.simpleGrid.destroy();
			}
			catch(ex){
				new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
				// quietly fail
			}
			this.simpleGrid = null;
		}
		if(this._conn_onHover) {
			dojo.disconnect(this._conn_onHover);
			this._conn_onHover = null;
		}
//		if(this._hoverer) {
//			this._hoverer.destroy();
//			this._hoverer = null;
//		}
	},

	
	/*
	*********** status domain lookup ***************
	*/
	_ajaxRequestStatusDomain: function(domain_id)
	{
		console.log("CatalogRequestsGrid._ajaxRequestStatusDomain");
		var deferred = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getDomain({DOMAINID: domain_id});
		deferred.addCallbacks(dojo.hitch(this, "_processStatusDomain"), dojo.hitch(this, "_onQueryError"));
		deferred.addBoth(dojo.hitch(this, "_cleanupQuery"));
		return deferred;
	},
	_processStatusDomain: function(response)
	{	
		console.log("CatalogRequestsGrid._processStatusDomain");

		this._statusDomainValues = [];
		var synonym_domain = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getDomainSynonymTable('MRSTATUS');
		for (var i = 0; i < synonym_domain.length; ++i)
		{
			this._statusDomainValues[synonym_domain.synonyms[i].VALUE] = synonym_domain.synonyms[i].DESCRIPTION;
		}
		return response;
	},
	/*
	********* the data query ************
	*/
	/*
	 * This is the method that does the ajax query for the grid's data.
	 * Override in derived classes
	 */
	ajaxQueryData: function()
	{
		return null;
	},

	// invoke an xmlHttpRequest to get service catalog requests
	_ajaxDoQuery: function()
	{
		console.log("MyRecordsGrid._ajaxDoQuery");
		if(this._outstandingDataQuery != null) {
			this._outstandingDataQuery.cancel();
			this._outstandingDataQuery = null;
		}

		// clear the baguette chart
		if(this.baguetteChart)
			this.baguetteChart.setData([]);

		// clear the grid
		this.clearGrid();

		this.showProgressSpinner();
		
		this._outstandingDataQuery = this.ajaxQueryData();
		if(this._outstandingDataQuery) {
			this._outstandingDataQuery.addCallbacks(dojo.hitch(this, "_processQueryResult"), dojo.hitch(this, "_onQueryError"));
			this._outstandingDataQuery.addBoth(dojo.hitch(this, "_cleanupQuery"));
		}
		return this._outstandingDataQuery;
	},
	_processQueryResult: function(response)
	{
		this._fireOnRefresh(response);	// let the world know we're finished
		return response;
	},	
	_loadGrid: function(newdata)
	{	
		// keep count of status types
		this._requestNumbers = newdata.length;

		this._status_stats = {srm_status_count: [], srm_unique_stati: 0};
		try {
			var pmscmr_count = newdata.length;
			for(var i = 0; i < pmscmr_count; ++i) {
				var p = newdata[i];
				if(p["StatusString"]) {
					var status = p["StatusString"];
					if(typeof this._status_stats.srm_status_count[status] == 'number') {
						++this._status_stats.srm_status_count[status];
					}
					else {
						this._status_stats.srm_status_count[status] = 1;
						++this._status_stats.srm_unique_stati;
					}
				}				
			}
			var newstore = new ibm.tivoli.simplesrm.srm.dojo.data.FormattedDataReadStore({data: {items: dojo.clone(newdata)}});
			newstore.setStructure(this.simpleGrid.structure);
			newstore.comparatorMap = {};
			var gl = this.simpleGrid.structure;
			for(var i in gl) {
				if(undefined != gl[i].compare) {
					newstore.comparatorMap[gl[i].field] = gl[i].compare;
				}
			} 
			this.simpleGrid.setStore(newstore, null, {ignoreCase: true});
			
			// refresh the grid's data
			if(this.simpleGrid.getSortIndex() > 0)
				this.simpleGrid.sort();
		
			this.simpleGrid.resize();
			
			// refresh the graph's data
			if (this._isApproval == "false")
				this._refreshBaguetteChart();
			else 
				this._refreshTotal();
			
			this._enableFilter();
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			console.error("Failed processing catalog request data: ", ex);
			throw ex;
		}	
	},	
	_onQueryError: function(response) 
	{
		if(response && response.message == "xhr cancelled") {
			console.log("Catalog request canceled");
		}
		else {
			(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: this._queryErrorMessage})).show();
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("MyRecordsGrid._onQueryError: " + response);
			console.log("Failed retrieving grid data.");
			console.log(response);
		}
		return response;
	},
	_onProcessError: function(response) 
	{
		if(response && response.message == "xhr cancelled") {
			console.log("Process request canceled");
		}
		else {
			(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: this._processErrorMessage})).show();
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("MyRecordsGrid._onProcessError: " + response);
			console.log("Failed processing selected request.");
			console.log(response);
		}
		return response;
	},
	_cleanupQuery: function(response) 
	{
		this._outstandingDataQuery = null;
		this.hideProgressSpinner();
		
		return response;
	},
	_refreshBaguetteChart: function()
	{
//		if( !this.isDisplayed() ) {	// defer until later
//			return;
//		}
		if( this._status_stats == null ) {	// nothing to do
			return;
		}
		console.log("MyRecordsGrid._refreshBaguetteChart: refreshing");

		
		var counts = this._status_stats.srm_status_count;
		counts.sort();
		// set stripe graph data using colors generated by walking around the color wheel
		var stripeData = [];
		var i = 0;
		for(var s in counts) {
			stripeData.push({name: s, value: counts[s], color: ibm.tivoli.simplesrm.srm.dijit.BaguetteChart.getDefaultColor(i++)});
		}
		if(this.baguetteChart)
			this.baguetteChart.setData(stripeData);
	},	
	_refreshTotal: function()
	{
		dojo.query(".requestnumber")[0].innerHTML = "Total: " + this._requestNumbers;
	},

	/*
	*************** flyover popup  support
	*/
	// the function for getting the grid's rollover tooltip text
//	getGridToolTip: function(cellDOMNode, rowIndex)
//	{
//		// if the cell is too low in the grid, so it's mostly 
//		// off the bottom, then don't show the tooltip.
//		// this isn't as straight forward as it should be, since
//		// the contents div has scrollbars, and there's no (easy) way
//		// to get their size
//		var gridcoords = dojo.coords(this.simpleGrid.domNode);
//		var cellcoords = dojo.coords(cellDOMNode);
//		if((gridcoords.y + gridcoords.h - cellcoords.y) < 30) {
//			return null;
//		}
//	
//		var row_data = this.simpleGrid.getItem(rowIndex);
//		var icon = null;
//	
//		tooltip2 = '<table border="0" cellspacing="1" cellpadding="2"><tbody>';
//		for(var i in this._column_defs) {
//			var coldef = this._column_defs[i];
//			if(coldef.showInTooltip == false) {
//				continue;
//			}
//
//			var field = coldef.field;
//			var datum = this.simpleGrid.store.getValue(row_data, field)
//			if(undefined == datum) {
//				continue;
//			}
//			if(field === "ImageName") {
//				icon = this._formatIconSz(datum, rowIndex, 50);
//			}
//			else {
//				tooltip2 += '<tr><td><b>';
//				tooltip2 += coldef.name;
//				tooltip2 += ': </b></td><td>';
//				tooltip2 += coldef.formatter ? coldef.formatter(datum) : datum;
//				tooltip2 += '</td></tr>';
//			}
//		}
//		tooltip2 += '</tbody></table>';
//		
//		if(icon) {
//			tooltip2  = '<table border=0 cellspacing=1 cellpadding=0><tbody><tr><td style="vertical-align:middle;"><td>'
//						+ icon
//						+ '</td><td>'
//						+ tooltip2
//						+ '</td></tr></tbody></table>';
//		}
//		
//		return '<div>' + tooltip2 + '</div>';
//				
//	},
//	_showGridTooltip: function(/*Hoverer*/hvr)
//	{
//		console.log("MyRecordsGrid._showGridTooltip");
//		var node = hvr.getDOMNode();
//		var msg = this.getGridToolTip(node, hvr.getData("rowIndex"));
//		dijit.showTooltip(msg, node);
//	},
	
	showProgressSpinner: function() 
	{
		console.log("MyRecordsGrid.showProgressSpinner");
		try {
			if(undefined == this.progressSpinner) {
				this.progressSpinner = new ibm.tivoli.simplesrm.srm.dijit.ProgressSpinner({text: this._uiStringTable.Loading + "&nbsp;"});
				var box = dojo.coords(this.simpleGrid.domNode);
				dojo.style(this.progressSpinner.domNode, {
					height: "1em",
					width: "100%",
					zIndex: "10",
					position: "absolute",
					top: "40%",
					left: "0px",
					display: "block",
					textAlign: "center"
				});
				dojo.place(this.progressSpinner.domNode, this.simpleGrid.domNode, "first");
			}	
			
			this.progressSpinner.show();
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			console.error("Failed rendering MyRecordsGrid's progress spinner: ", ex);
			if(this.progressSpinner) {
				this.progressSpinner.destroyRecursive();
				this.progressSpinner = null;
			}
			window.status = "Retrieving...";
		}
	},
	hideProgressSpinner: function() {
		console.log("MyRecordsGrid.hideProgressSpinner");
		if(this.progressSpinner)
			this.progressSpinner.hide();
		else
			window.status = "Done.";
	},
	clearGrid: function() 
	{
		console.log("MyRecordsGrid.clearGrid");
		
		if(this.showDetailsBtn)
			this.showDetailsBtn.attr("disabled", true);

		if(this.approveBtn)
			this.approveBtn.attr("disabled", true);

		if(this.rejectBtn)
			this.rejectBtn.attr("disabled", true);

		if(this.simpleGrid) {
			this.simpleGrid.selection.clear();
			this.simpleGrid.setStore(new ibm.tivoli.simplesrm.srm.dojo.data.FormattedDataReadStore({data: {items:[]}}));
		}
//		if(this._hoverer) {
//			this._hoverer.setDOMNode(null);
//		}
		
	},
	isDisplayed: function()
	{
		//console.group("MyRecordsGrid.isDisplayed");
		var d = true;
		for(var n = this.domNode; n && 'body' != n.tagName.toLowerCase(); n = n.parentNode) {
			//console.log(n);
			if("none" == dojo.style(n, "display")) {
				d = false;
				break;
			}
		}
		//console.log("isDisplayed = ", d);
		//console.groupEnd();
		return d;
	},
	/* refresh button handling */
	refresh: function(forceRequery) {
		console.log("MyRecordsGrid.refresh(%s)", forceRequery);
		
		if(!this._initialization_complete) {
			window.setTimeout(dojo.hitch(this, 'refresh'), 500);
		}

		if("boolean" != typeof forceRequery || forceRequery == true) {
 			this._disableFilter();
			this._ajaxDoQuery();
		}
		else {
			this.simpleGrid.update();
			
			if (this._isApproval == "false")
				this._refreshBaguetteChart();
			else
				this._refreshTotal();
			
			this.simpleGrid.resize();		// TODO: part of the realignment hack
		}
	},
	_fireOnRefresh: function(raw_data)
	{
		try {
			this.onRefresh(raw_data);
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex,"warning");
			console.warn("MyRecordsGrid.onRefresh handler failed: " + ex);
		}
	},
	onRefresh: function()
	{
		// override this puppy
	},
	/* show details button handling */
	_activeRow: -1,
	_onRowSelect: function(rowIndex)
	{
		console.log("MyRecordsGrid._onRowSelect ", rowIndex);
		
		this._activeRow = rowIndex;
		if (this.showDetailsBtn) {
			this.showDetailsBtn.attr("disabled", false);
			
			if (this.contextBtns) {
				this.contextBtns.attr("disabled", false);	// TODO: Probably depends on state of the selected object too
				// the row's data to the context buttons
				var context = {};
				try {
					context = this.getContext();
				}
				catch(ex) {
					new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex,"warning");
				}
				this.contextBtns.setContext(context);
			}
		}
		
		if (this.approveBtn)
			this.approveBtn.attr("disabled", false);	
		
		if (this.rejectBtn)
			this.rejectBtn.attr("disabled", false);	
		
	},
	_onRowDeselect: function(rowIndex)
	{
		console.log("MyRecordsGrid._onRowDeselect ", rowIndex);
		if (this.showDetailsBtn) {
			this.showDetailsBtn.attr("disabled", true);
			if (this.contextBtns) {
				this.contextBtns.attr("disabled", false);
			}
		}
		
		if (this.approveBtn) {
			this.approveBtn.attr("disabled", true);
		}
		
		if (this.rejectBtn) {
			this.rejectBtn.attr("disabled", true);
		}
	},
	_showDetails: function()
	{
		console.log("MyRecordsGrid._showDetails(row: %d)", this._activeRow);

		if(this._activeRow >= 0) {	
			var selected_record = this.simpleGrid.getItem(this._activeRow);
			selected_record = this._gridItemToObject(selected_record);
			this.showRecordDetails(selected_record);
		}
	},
	// returns an object with data from the selected row pertinant to the grid's datatype
	// derived grids should override if you have any context specific actions
	getContext: function()
	{
		return {};
	},
	// there ought to be a better way
	_gridItemToObject: function(item)
	{
		var attribs = this.simpleGrid.store.getAttributes(item);
		var obj = {};
		for(var i = 0; i < attribs.length; ++i) {
			obj[attribs[i]] = this.simpleGrid.store.getValue(item, attribs[i]);
		}
		return obj;
	},
	// ------------- event handlers -----------------------
	// TODO: I added code to save the current list of filtered columns and set it to just "Status"
	// when responding to a baguette chart click, then put things back if the user clicks on "total"
	// I'm not sure this is a good idea.  Talk to BJ
	_savedFilterCols: null,
	_stripeClick: function(evt, datum_name)
	{
		console.log("MyRecordsGrid._stripeClick ", datum_name);
		if(datum_name == "Total") {
			this.toolBar.searchField.searchField.value = "";
			this.clearFilter();
			if(dojo.isArray(this._savedFilterCols)) {
				this.toolBar.searchField.setSelectedColumns(this._savedFilterCols);
				this._savedFilterCols = null;
			}
			return;
		}
		
		this.toolBar.searchField.searchField.value = datum_name;
		if(undefined == this._savedFilterCols)
			this._savedFilterCols = this.toolBar.searchField.getSelectedColumns();
		this.toolBar.searchField.setSelectedColumns(["StatusString"]);
		this.filter( ['StatusString'], datum_name);
		this.toolBar.searchField.searchField.focus();
	},

	/*
	*********** FILERING SUPPORT *********************
	*/
	_onFilterSubscription: null,
	_onFilterKeypressHandle: null,
	_onFilterDelay: 700,		// msecs from time user starts typing, until we automagically filter the data
	_filterTimerID: -1,			// timer for delayed auto-filtering

	_disableFilter: function()
	{
		dojo.unsubscribe ( this._onFilterSubscription );  
		dojo.disconnect(this._onFilterKeypressHandle); 
		this._onFilterSubscription = this._onFilterKeypressHandle = null;
		this.toolBar.searchField.clear();
		//this.toolBar.searchField.searchField.value = "";	// you'd think clear() would take care of this.
	},
	_enableFilter: function()
	{
		this.toolBar.searchField.setTable(this.simpleGrid);
		if(!this._onFilterSubscription) {	// wire it up
			// published on hitting enter
			this._onFilterSubscription = dojo.subscribe ( this.toolBar.searchField.id + "searchInvoked", this, "_onFilter" );    
			// we also want to automatically filter as the user types
			this._onFilterKeypressHandle = dojo.connect(this.toolBar.searchField.searchField, "onkeypress", this, "_onSearchKeyPress");
		}
		// if the table was filtered, re-filter with new data
		if(this.toolBar.searchField.searchField.value.length > 0) {
			this._onFilter();
		}
	},
	_onSearchKeyPress: function(evt)
	{
		if(evt.keyCode == dojo.keys.ENTER || evt.keyCode == dojo.keys.TAB)  {
			// ENTER is already handled, and TAB just causes field to loose focus
			return;
		}
		//console.log("MyRecordsGrid._onSearchKeyPress");
		if(this._filterTimerID < 0) {
			this._filterTimerID = window.setTimeout(dojo.hitch(this, "_onFilter"), this._onFilterDelay);
		}
	},
	_onFilter: function()
	{
		console.log("CatalogRequestQery._onFilter");
		if(this._filterTimerID >= 0) {
			window.clearTimeout(this._filterTimerID);
			this._filterTimerID = -1;
		}

		// I'm here becuase the user is typing in the filter box
		// if there are saved filter cols (because of a baguetteChart click)
		// put the previous cols back
		if(dojo.isArray(this._savedFilterCols)) {
			this.toolBar.searchField.setSelectedColumns(this._savedFilterCols);
			this._savedFilterCols = null;
		}
	     // now that I'm automatically filtering on key presses, can't trim whitespace,
	     // or spaces disappear from between words as the user types
	     //this.toolBar.searchField.searchField.value = this.toolBar.searchField.searchField.value.replace(/^\s+|\s+$/g, '');
	     var filterString = this.toolBar.searchField.searchField.value;
	     
	     if ( filterString.length < 1 )
	     {
	       this.clearFilter();
	     }
	     else
	     {
	       this.filter( this.toolBar.searchField.getSelectedColumns(), filterString );
	     }
	     
    	 this.toolBar.searchField.searchField.focus()
	    	 
	},
	//************** client-side data filtering	 *********************
	filter: function( fields,        /* array of field names to filter on */
	                  filterString ) /* user entered string */
	{
		// surround the filter string with '*'s so we match substrings
		var t = "*" + filterString + "*";
		// create the query by ORing together a query on each of the fields
		var q = "";
		dojo.forEach(fields, function(f) {
			q += f + ":'" + t + "' OR ";
		});
		q = q.substring(0, q.lastIndexOf(" OR "));
		this.simpleGrid.setQuery({complexQuery: q});
		
		// because we use auto-width cols, needed to guarantee the column headings are aligned with the data
		this.simpleGrid.resize();
	 
		// I realize that this may be an unexpected side-effect, but it also works around some
		// unusual behaviors exhibited by the grid when the selected row is filtered out, or scrolled
		// out of view
		// TODO: is this still an issue with dojo1.3.1's DataGrid?
		this.clearGridSelection();
	 },
	 clearFilter: function()
	 {
	 	// you can't clear the query with DataGrid.setQuery
	 	this.simpleGrid.query = null;
		this.simpleGrid._refresh();
		this.simpleGrid.resize();	// get columns aligned
		this.clearGridSelection();
	 },
	 clearGridSelection: function()
	 {
		if(this.simpleGrid.selection.getSelectedCount() > 0) {
			this.simpleGrid.selection.clear();
		}
		this.simpleGrid.focus.setFocusIndex(0,0);	// move focus to first cell. Has the side-effect of scrolling there   
	 }
});

