//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.ItemChooserGrid");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");
dojo.require("dojox.data.AndOrReadStore");
dojo.require("ibm.tivoli.tip.dijit.TIPTableToolbar");
dojo.require("dojox.grid.DataGrid");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.Comparator");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.ContextButton");

// Summary
//	ItemChooserGrid is essentially a very fancy set of radio buttons
//	For now, you have to derive a class from it and
//		1. provide the grid's column definition in this.gridLayout.  For example:
//			this.gridLayout = [
//	      			  {name: "Name",   		 field: 'imageName', width: 'auto',	compare: ibm.tivoli.simplesrm.srm.dojo.data.Comparator.stringCompare, formatter: dojo.hitch(this, this._noWrap)},
//	      			  {name: "Platform",     field: 'platform',  width: 'auto', compare: ibm.tivoli.simplesrm.srm.dojo.data.Comparator.stringCompare},
//	      			  {name: "CPUs",         field: 'cpus',      width: '4.1em',classes: 'numeric', compare: ibm.tivoli.simplesrm.srm.dojo.data.Comparator.intCompare},
//	      		];
//		2. provide the name of the primary key field
//	   		this.keyField = "imageName";
//		3. provide an implementation of queryData 
//			a. must return a dojo.Deferred
//			b. the data set must have an array type called "data" with the grid's data 
//			   for example:
//				queryData: function()
//				{
//					var d = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getDiscoveredImages({sync: false});
//					d.addCallback(function(response) {
//						var images = response.QueryTPV01IMGLIBENTRYMSTRResponse.TPV01IMGLIBENTRYMSTRSet.ILIMAGE;
//						response.data = [];
//						for(var i in images) {
//							var img = images[i]; 
//							response.data.push({NAME: img.imagename, platform: img.Platform, cpus: img.cpus);}
//						}
//					});
//					return d;
//				},
//
//	Getting and setting the value is via the standard dijit's attr('value') and attr('value', newvalue)
//		The value returned on get will be the value of the keyField
//		The value set must match a keyField in the data store
//
//	Updating data is done by calling refresh(), which will in turn call your queryData function to get the new data.
//
// Attributes:
//	keyField: Name of the field in your data that is the primary key
//	name: The name is the name on the set of radio buttons, and is the name used to submit data with a form
//	autoHeight: any valid DataGrid autoHeight value.  If 0 or false, you must provide a height in the style.
//	readOnly: if true, the radio buttons are not displayed, and row selection is turned off
//
// Events:
//		onChange is fired when the selected value changes. It is passed the corresponding item object from the grid's data store
//			Accessing the selected row's values must be through ItemFileReadStore's API.
//			For example:
//				function onChange(item)
//				{
//					var c = dijit.byId('my_item_chooser_grid');
//					var name = c.attr('value');
//					var cpu = c.getFieldValue("platform");
//					var mem = c.getFieldValue("cpus");
//				}
//		onDataReady is fired when the grid is loaded with a new data set.
//
//	Markup:
//		<div id='c' dojoType="ibm.tivoli.simplesrm.tsam.dijit.ImageChooserGrid" name="PMRDPCLCSWS_SWSTACKID" 
//			style="width:600px;" autoHeight="5" onChange="onChooserChange"></div>

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.ItemChooserGrid", [dijit._Widget, dijit._Templated],
{
	widgetsInTemplate: true,
	templateString: '<div class="ItemChooserGrid"><div id="${id}_toolbar" class="srmtoolbar" dojoAttachPoint="_toolbar" dojoType="ibm.tivoli.tip.dijit.TIPTableToolbar" ></div><div dojoAttachPoint="_griddiv" style="width:100%; height: 100%;"></div></div>',
	_grid: null,
	_searchField: null,
	_dataQuery: null,
	_selectedItem: null,
	contextBtns: null,

	
	gridLayout:  null,		// the grid's column layout, formatters, and such
	keyField: "id",			// your data's key
	name: "",				// you data's name, as you want it posted with the form
	autoHeight: 5,			// a valid DataGrid.autoHeight value
	readOnly: false,		// if readOnly is true, don't show the radio buttons, or do row selection
	contextName: "",		// name for context-specific toolbar buttons
	autoQuery: true,		// should I automatically query for my data on initialization?
	
	
	constructor: function()
	{
		console.log("ItemChooserGrid.ctor");
	},
	postMixInProperties: function()
	{
		console.log("ItemChooserGrid.postMixInProperties");

		this.inherited(arguments);
	},
	buildRendering: function()
	{
		this.inherited(arguments);
		
		// get context specific buttons
	    if(!this.readOnly && this.contextName && this.contextName.length > 0) {
	    	this.contextBtns = new ibm.tivoli.simplesrm.srm.dijit.ContextButtonSet({contextName: this.contextName, toolbar: this.getToolbar()});
	    	this.contextBtns.attr("disabled", true);
	    }
	    if(!this.contextBtns || this.contextBtns.length == 0) {
	    	this.getToolbar().tabIndex = -1;
	    }
	},
	postCreate: function()
	{
		console.log("ItemChooserGrid.postCreate");
		this._searchField = this._toolbar.searchField;
		var select = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").Select;
		
		var bReadOnly = this.attr("readOnly");
		if(!bReadOnly) {
			// push a column to the front of what's given to us for the radio button
			this.gridLayout.unshift({name: select,      field: this.keyField,   width: "4em",	classes: 'center', formatter: dojo.hitch(this, this._formatRadioButton), isSearchable: false, editable: true});
		}
		
		this._grid = new dojox.grid.DataGrid({
					structure: this.gridLayout,
					selectionMode: bReadOnly ? "none" : "single",
					autoWidth: false,
					autoHeight: this.autoHeight
				}, this._griddiv);

		if(!bReadOnly) {
			this.connect(this._grid, "onSelected", this._onRowSelected);
		}
		
		this._searchField.setTable(this._grid);
		this._enableFilter();
		
		this.inherited(arguments);
	},
	/**
	 * Call refresh() to update the data in the grid
	 * Will eventually call your queryData() function.
	 * Has the side-effect of reseting the selection to the first row, and clearing the filter
	 */
	refresh: function()
	{
		if(this._dataQuery) {
			this._dataQuery.cancel();
			delete this._dataQuery;
			this._dataQuery = null;
		}
		this._disableFilter();
		this._grid.selection.clear();
		this._selectedItem = null;
		this._grid.setStore(null);

		this._dataQuery = this.queryData();
		this._dataQuery.addCallback(dojo.hitch(this, this.loadData));
	},
	loadData: function(response)
	{
		if(!dojo.isArray(response.data) || response.data.length == 0) {
			return;
		}
		if(this._dataQuery) {
			delete this._dataQuery;
			this._dataQuery = null;
		}
		var newdata = dojo.clone(response.data);
		var store = new dojox.data.AndOrReadStore({data: {identifier: this.keyField, items: newdata}});
		store.comparatorMap = {};
		var gl = this.gridLayout
		for(var i in gl) {
			if(undefined != gl[i].compare) {
				store.comparatorMap[gl[i].field] = gl[i].compare;
			}
		}
		this._grid.attr("autoHeight", this.autoHeight);
		this._grid.setStore(store, null, {ignoreCase: true});
		this._grid.selection.select(0);
		this._enableFilter();
		//window.setTimeout(dojo.hitch(this, this._fixSize), 100);
		this._grid.resize();
		this.onDataReady();
	},
	_fixSize: function()
	{
		var sz = dojo.marginBox(this._grid.domNode);
		dojo.style(this._grid.domNode, "height", sz.h + "px");
		this._grid.attr('autoHeight', false);
	},
	startup: function()
	{
		console.log("ItemChooserGrid.startup");
		this._grid.startup();
		if(this.autoQuery)
			this.refresh();
		this.inherited(arguments);
	},
	destroy: function()
	{
		this._disableFilter();
		if(this._progressSpinner)
			this._progressSpinner.destroy();
	},
	
	_getValueAttr: function()
	{
		var retval = "";
		if(this._selectedItem) {
			retval = this._grid.store.getValue(this._selectedItem, this.keyField);
		}
		return retval;
	},
	// TODO: when the data query is async, then the grid might not be refilled
	// before someone calls .attr('value', val).  Maybe i should see if this._dataQuery exists,
	// and if so, add a callback to set the new value when the query completes.
	// For now, I'm going to make the serverChooserGrid2's query synchronous.
	_setValueAttr: function(newval)
	{
		var q = {};
		q[this.keyField] = newval;
		var item = this._grid.store.fetch({query:q, onComplete: dojo.hitch(this, function(items)
		{
			if(items.length > 0) {
				var r = this._grid.getItemIndex(items[0]);
				this._grid.selection.select(r);
				this._selectedItem = items[0];
				this._setCheck(this._selectedItem, true);
			}
		})});
	},
	_getSelectionAttr: function()
	{
		return this._selectedItem;
	},
	/**
	** pull a field value out of a row of data.
	** a: if an ojbect, is assumed to be an item object from the grid's store.
	**		this may be the item sent out with onChange.
	**	  if a string, is assumed to be the field name, and we pull the value from the
	**		currently selected row
	** b: if a is an item, then b is the field name we want to extract
	**
	** returns:
	**		the value of the requested field, null if it doesn't exist.
	*/
	getFieldValue: function(/*string|object*/ a, /*string?*/ b)
	{
		var retval = null;
		var item = null;
		var name = "";
		if("object" == typeof a) {
			item = a;
			name = b;
		}
		else {
			name = a;
			var row = this._grid.selection.selectedIndex;
			item = this._grid.getItem(row);
		}
		if(null != item) {
			retval = this._grid.store.getValue(item, name);
		}
		return retval;
	},
	/**
	 * similar to getFieldValue, but returns the field from the currently selected row
	 * and lets you provide a default if the field is undefined.
	 * fieldName: name of the field you want the value of
	 * defaultValue: if provided, returned if the requested field is missing
	 * 
	 * returns:
	 * 		value of the requested field, defaultValue, or null 
	 */
	getSelectedFieldValue: function(/*string*/fieldName, /*string?*/defaultValue)
	{
		var retval = null;
		var row = this._grid.selection.selectedIndex;
		var item = this._grid.getItem(row);
		if(null != item) {
			retval = this._grid.store.getValue(item, fieldName);
		}
		if(undefined == retval && undefined != defaultValue) 
			retval = defaultValue;
		return retval;
	},
	_onRowClick: function(evt)
	{
		console.log("MyRecordsGrid._onRowClick");
		this._onRowSelected(evt.rowIndex);
	},
	_onRowSelected: function(rowIndex)
	{
		console.log("ItemChooserGrid._onrowSelected ", rowIndex);
		this._selectedItem = this._grid.getItem(rowIndex);
		this._setCheck();
		if(this.contextBtns) {
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
		this.onChange(this._selectedItem);
	},
	_reSelectSelected: function()
	{
		if(this._selectedItem) {
			var r = this._grid.getItemIndex(this._selectedItem);
			if(r >= 0) {
				this._grid.selection.select(r);
			}
			else {
				if(this.contextBtns)
					this.contextBtns.attr("disabled", true);
				this._selectedItem = null;
				this._grid.selection.clear();
				this.onChange(null);
			}
		}
	},
	_setCheck:function()
	{
		if(null != this._selectedItem) {
			rbkey = this._grid.store.getValue(this._selectedItem, this.keyField);
			rbid = this._radioButtonIdFromKey(rbkey);
			rb = dojo.byId(rbid);
			if(rb)
				rb.checked = true;
		}		
	},
	_onSorted: function()
	{
		if(this._selectedItem) {
			var r = this._grid.getItemIndex(this._selectedItem);
			this._grid.selection.select(r);
			// grid.selection.select doesn't fire onSelection, plus, something after that
			// redraws the grid and looses the check again.
			window.setTimeout(dojo.hitch(this, this._setCheck), 200);
		}
	},
	_radioButtonIdFromKey: function(key)
	{
		return this.id + "_" + key.toString().replace(/\W/g, "");
	},
	// returns an object with data from the selected row pertinant to the grid's datatype
	// derived grids should override if you have any context specific actions
	getContext: function()
	{
		return {};
	},
	onChange: function(selected_item)
	{
		// connect to me
		// selected_item is an item from an ItemFileReadStore
		// if you want values out of it, getFieldValue(selected_item, name)
		// or getFieldValue(name)
	},
	onDataReady: function()
	{
		// connect to me if you want to be notified when the grid has been loaded with new data
	},
	/********* formaters ***********/
	_formatRadioButton: function(datum, rowIndex)
	{
		var selValue = this._getValueAttr();
		var s = "<input id='" + this._radioButtonIdFromKey(datum) + "' type='radio' value='"+ datum +"' name='" + this.name + "'";
		s += (datum == selValue) ? " checked='true'" : ""
		s += ">";
		return s;
	},
	_noWrap: function(v)
	{
		return "<div style='white-space:nowrap;overflow:hidden;' title='"+ v +"'>" + v + "</div>";
	},

	/*********** filtering ****************/
	_onFilterSubscription: null,
	_onFilterKeypressHandle: null,
	_onFilterDelay: 700,		// msecs from time user starts typing, until we automagically filter the data
	_filterTimerID: -1,			// timer for delayed auto-filtering

	_disableFilter: function()
	{
		dojo.unsubscribe ( this._onFilterSubscription );  
		dojo.disconnect(this._onFilterKeypressHandle); 
		this._onFilterSubscription = this._onFilterKeypressHandle = null;
		this._searchField.clear();
	},
	_enableFilter: function()
	{
		this._searchField.setTable(this._grid);
		if(!this._onFilterSubscription) {	// wire it up
			// published on hitting enter
			this._onFilterSubscription = dojo.subscribe ( this._searchField.id + "searchInvoked", this, "_onFilter" );    
			// we also want to automatically filter as the user types
			this._onFilterKeypressHandle = dojo.connect(this._searchField.searchField, "onkeypress", this, "_onSearchKeyPress");
		}
		// if the table was filtered, re-filter with new data
		if(this._searchField.searchField.value.length > 0) {
			this._onFilter();
		}
	},
	_disableFilter: function()
	{
		dojo.unsubscribe ( this._onFilterSubscription );  
		dojo.disconnect(this._onFilterKeypressHandle); 
		this._onFilterSubscription = this._onFilterKeypressHandle = null;
		this._searchField.clear();
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
		console.log("ItemChooserGrid._onFilter");
		if(this._filterTimerID >= 0) {
			window.clearTimeout(this._filterTimerID);
			this._filterTimerID = -1;
		}

		this._savedFilterCols = this._searchField.getSelectedColumns();
		var filterString = this._searchField.searchField.value;
 
		if ( filterString.length < 1 || /^\s+$/.test(filterString)) {
			this.clearFilter();
		}
		else {
			this.filter( this._savedFilterCols, filterString );
		}
		this._reSelectSelected();	// selections are by rowIndex.  need to redo it to keep the right record selected
		this._searchField.searchField.focus()
	},
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
		this._grid.setQuery({complexQuery: q}, {ignoreCase: true});
	 },
	 clearFilter: function()
	 {
	 	// you can't clear the query with DataGrid.setQuery
	 	this._grid.setQuery(null);
		this._grid.resize();
	 },
	 _progressSpinner:null,
	showProgressSpinner: function() 
	{
		try {
			if(undefined == this._progressSpinner) {
				this._progressSpinner = new ibm.tivoli.simplesrm.srm.dijit.ProgressSpinner({text: this._uiStringTable.Loading + "&nbsp;"});
				var sz = dojo.coords(this.simpleGrid.domNode);
				dojo.style(this._progressSpinner.domNode, {
					height: "1em",
					width: "100%",
					zIndex: "10",
					position: "absolute",
					top: sz.y + "px",
					left: "0px",
					display: "block",
					textAlign: "center"
				});
				dojo.place(this._progressSpinner.domNode, this.domNode, "first");
			}	
			
			this._progressSpinner.show();
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			console.error("Failed rendering MyRecordsGrid's progress spinner: ", ex);
			if(this._progressSpinner) {
				this._progressSpinner.destroyRecursive();
				this._progressSpinner = null;
			}
			window.status = "Retrieving...";
		}
	},
	hideProgressSpinner: function() {
		console.log("MyRecordsGrid.hideProgressSpinner");
		if(this._progressSpinner)
			this._progressSpinner.hide();
		else
			window.status = "Done.";
	},
	getToolbar: function()
	{
		return this._toolbar.getToolbar();
	}
});
