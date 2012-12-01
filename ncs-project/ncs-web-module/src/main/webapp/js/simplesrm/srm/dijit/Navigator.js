//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

// TODO:
// 	- if you rapidly click twice on a list entry, it gets pushed to the breadcrumbs twice before transition effect plays out
//  - disable click on the right-most breadcrumb morsel
//  - do I need to expose list pushing and popping events to the outside?

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Navigator");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dojo.fx");
dojo.require("dojo.parser");
dojo.require("dojo.DeferredList");
dojo.require("dijit.ProgressBar");

dojo.require("ibm.tivoli.simplesrm.srm.dijit.WidgetBase");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.ListTree");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.Breadcrumb");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.CreateCatalogRequest");
dojo.require("ibm.tivoli.tip.dijit.TIPButton");
dojo.require("ibm.tivoli.tip.dijit.TextInputBox");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.ToolbarButton");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");

/*
 * Navigator
 * Loads data representing the taxonomy of Service Request and Incident categories
 * Events:
 * 	onSrmNavigatorClick(item_label): fired when user clicks on a leaf node of the tree
*/
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Navigator", 
	[dijit._Widget, dijit._Templated, ibm.tivoli.simplesrm.srm.dijit.WidgetBase, ibm.tivoli.simplesrm.srm.dijit.CreatorFactory], 
	{	
		widgetsInTemplate: true,
		templatePath: dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit", "templates/SrmNavigator.html"),

		listLabel: '',
		imagesPath: '',
		requestsOP: '$_unset_$',
		incidentsOP: '$_unset_$',
		rootList: null,
		_UIComplete: false,
		fillOrder: "NavFrontPage",
		keyField: "ItemNum", 
		labelField: "Description",
		descField: "LongDescription",
		iconField: "ImagePath",
		iconSizeField: "iconSize",
		childField: "Category",
		leafField: "Offering",
		
				
		// ******** lifecycle methods ********
		constructor: function(/*object*/params, /*domNode*/domNode) 
		{
			console.log("Navigator.ctor()");
			this.rootList = null;
			if(!this.imagesPath)
				this.imagesPath = dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit", "images");
		},
		buildRendering: function()
		{
			// preload toolbar button images
			// these paths need to be kept in sync with the background-images
			// specified in SimpleSRM_Tundra.css
			var needed_images = ["images/tbutton_over.png", "images/tbutton_down.png", 
								 "images/ac22_home_24.png", 
								 "images/ac22_home_disabled_24.png", 
								 "images/ac16_follow.png", 
								 "images/ac16_new_sprite.png", 
								 "images/ac16_follow_hover.png", 
								 "images/ac16_new_sprite_hover.png",
								 "images/icons/incident_folder.png", 
								 "images/icons/request_folder.png"];
			
			var root = dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit") + "/";
			for(var i in needed_images) {
				var img = new Image();
				img.src =  root + needed_images[i];
			}
			this.inherited(arguments);
		},
		postCreate: function()
		{
			if("$_unset_$" == this.requestsOP)
				this.requestsOP = ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("RequestsCatalog");
			if("$_unset_$" == this.incidentsOP)
				this.incidentsOP = ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("IncidentsCatalog");
		},
		startup: function()
		{
			console.log("Navigator.startup()");
			var iconpath = this.getRelativePath("images/icons/");
			this.inherited(arguments);
			var frontPage = {
					Category: [
					{
						Description: this._uiStringTable.IncidentsLabel,
						LongDescription: this._uiStringTable.IncidentsDesc,
						ImagePath: iconpath+"default_incident.png",
						iconSize: 48,
						Category: null
					},
					{
						Description: this._uiStringTable.RequestsLabel,
						LongDescription: this._uiStringTable.RequestsDesc,
						ImagePath: iconpath+"default_request.png",
						iconSize: 48,
						Category: null
					},
					{
						Description: this._uiStringTable.RecentsLabel,
						LongDescription: this._uiStringTable.RecentsDesc,
						ImagePath: iconpath+"recent_request.png",
						iconSize: 48,
						Category: null
					},
					{
						Description: "Search Results",
						LongDescription: "",
						ImagePath: null,
						Category: null
					}
				]
			};
			this.rootList = dijit.byId(this.id + "_list");
			this.rootList.addChildren(frontPage, "NavFrontPage Waiting", 1); // even though this is NavFrontPage, the children will be RowMajor
															  		// TODO: fix that if you can.
						
			// these ids have to be kept in sync with their defs in the template
			this.crumb_id = this.id + "_crumbs";	// id of my Breadcrumb object
			this.list_id = this.id + "_list";		// id of the domNode housing the lists
			this.connect(this.list_search_btn, 'onClick', '_search');
			this.connect(this.list_search_term, 'onkeypress', '_search');

			// connect the navigation buttons
			this.connect(this.home_btn, 'onClick', '_goHome');

			this.rootList.show();
			// hide unused branches (you have to show() before the dom nodes exist to hide)
			if(this.incidentsOP.length === 0) {
				dojo.style(this.rootList.subitems[0].domNode, "display", "none");
			}
			if(this.requestsOP.length === 0) {
				dojo.style(this.rootList.subitems[1].domNode, "display", "none");
			}
			// always hide the search results list
			this._searchResultsList = this.rootList.subitems[3];
			dojo.style(this._searchResultsList.domNode , "display", "none");
			
			// the event handlers aren't connected yet
			// manually call a couple
			this._onpushlist(null, this.rootList);
			this._onactivatelist(this.rootList);
			this._setNavButtonState();
			
			this._loadUI();
		},
		_loadUI: function()
		{
			var data_deferreds = [];
			var deferred = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getRequestsCatalog();
			if(deferred) {
				deferred.addCallback(dojo.hitch(this, "_requestsLoadHandler"));
				data_deferreds.push(deferred);
			}
			deferred = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getIncidentsCatalog();
			if(deferred) {
				deferred.addCallback(dojo.hitch(this, "_incidentsLoadHandler"));
				data_deferreds.push(deferred);
			}
			
			// don't wire-up list event handlers until all the data is in
			var last_deferred = new dojo.DeferredList(data_deferreds);
			last_deferred.addCallback(dojo.hitch(this, function(results) 
			{
				// wire up ListTree events
				this.connect(this.rootList, "onPushList", "_onpushlist");
				this.connect(this.rootList, "onPopList", "_onpoplist");
				this.connect(this.rootList, "onActivateList",  "_onactivatelist");
				this.connect(this.rootList, "onClick", "_onlistclick");
				
				// wire up the breadcrumbs
				var my_crumbs = dijit.byId(this.crumb_id);
				this.connect(my_crumbs, "onClick", "_oncrumbclick");
				
				dojo.forEach(dojo.query(".Waiting", this.rootList.domNode), function(elem)
				{
					dojo.removeClass(elem, "Waiting");
				});
				
				this._UIComplete = true;
				return results;
			}));
		},
		_requestsLoadHandler: function(response, ioArgs)
		{
			console.log("Navigator._requestsLoadHandler(): ", response, ioArgs);
			if(response == null
				 || !dojo.isArray(response.Category)
				 || response.Category.length == 0) {
				dojo.removeClass(this.rootList.subitems[1].domNode, "leaf");
				dojo.addClass(this.rootList.subitems[1].domNode, "empty");
			}
			else {
				this._pruneTree(response);
				this._swizzleData(response.Category, null, "request_folder.png", "default_request.png");
				this._sortTree(response);
				this.rootList.subitems[1].addChildren(response, "RowMajor", 2);
			}
			return response;
		},
		_incidentsLoadHandler: function(response, ioArgs)
		{
			console.log("Navigator._incidentsLoadHandler");
			if(response == null || !dojo.isArray(response.Category) ) {
				dojo.removeClass(this.rootList.subitems[0].domNode, "leaf");
				dojo.addClass(this.rootList.subitems[0].domNode, "empty");
			}
			else {
				this._pruneTree(response);
				this._swizzleData(response.Category, null, "incident_folder.png", "default_incident.png");
				this._sortTree(response);
				this.rootList.subitems[0].keyField = "ID";
				this.rootList.subitems[0].addChildren(response, "RowMajor", 2);
			}
			return response;
		},
		_recentsLoadHandler: function(response, ioArgs)
		{
			console.log("Navigator._recentsLoadHandler");
			if(response == null || !dojo.isArray(response.Category) ) {
				dojo.removeClass(this.rootList.subitems[2].domNode, "leaf");
				dojo.addClass(this.rootList.subitems[2].domNode, "empty");				
			}
			else {
				this._pruneTree(response);
				this._swizzleData(response.Category, null, null, null);
				this._sortTree(response);
				this.rootList.subitems[2].addChildren(response, "RowMajor", 2);
			}
			return response;
		},
		_swizzleData: function(rlist, classification, default_folder_icon, default_leaf_icon)
		{
			var static_iconpath = this.getRelativePath("images/icons/");
			for(i in rlist) {
				var r = rlist[i];
				if(undefined == r.ImageName) {
					if(r.Category || r.Offering) {
						if(default_folder_icon) {
							r.ImageName = default_folder_icon;
						}
					}
					else {
						if(default_leaf_icon) {
							r.ImageName = default_leaf_icon;
						}
					}
					r.ImagePath = static_iconpath + r.ImageName;
				}
				else {
					r.ImagePath = this.imageCacheUrl + r.ImageName + "?REFID=" + r.ItemID;
				}
				// I need to copy ClassificationID from a parent into leaf nodes
				if(r.ClassificationID) {
					classification = r.ClassificationID;
				}
				if(classification && !r.ClassificationID) {
					r.ClassificationID = classification;
				}
				if(r.Category) {
					this._swizzleData(r.Category, classification, default_folder_icon, default_leaf_icon);
				}
				if(r.Offering) {
					this._swizzleData(r.Offering, classification, default_folder_icon, default_leaf_icon);
				}
			}
		},
		// if a branch has only folders
		_pruneTree: function(tree) 
		{
			console.log("Navigator._pruneTree()", tree);
			for (var i in tree.Category) {
				/* SR Commented out for future use (post GA) */
				/*if ((tree.Category.length == 1)
					 && tree.Category[i].Category) { // check for existence
					if (undefined !== tree.Category[i].Offering
						&& tree.Category[i].Offering.length !== 0) {
						tree.Offering = tree.Category[i].Offering
					}
					console.log("prune 1");
					tree.Category = tree.Category[i].Category;
				}*/
				if ((undefined !== tree.Category[i].Category
						&& tree.Category[i].Category.length == 1)
					 && (undefined == tree.Category[i].Offering
					 	|| tree.Category[i].Offering.length == 0)) {
					// console.log("prune 2");
					tree.Category[i] = tree.Category[i].Category[0];
				}
				this._pruneTree(tree.Category);
			}
		},
		_sortTree: function(tree)
		{
			if(undefined != tree.Offering) {
				tree.Offering.sort(function(a, b)
				{
					if(a.Description < b.Description) return -1;
					if(a.Description > b.Description) return 1;
					return 0;
				});
			}
			if(undefined !== tree.Category) {
				tree.Category.sort(function(a, b) 
				{
					if(a.Description < b.Description) return -1;
					if(a.Description > b.Description) return 1;
					return 0;
				});
				for(var i in tree.Category) {
					this._sortTree(tree.Category[i]);
				}
			}
		},
		_saved_req_data: null,
		refreshRecentRequests: function(req_data)
		{
			console.log("Navigator.refreshRecentRequests");
			
			if(!this._UIComplete) {
				console.log("*** recycle");
				if(null == this._saved_req_data && undefined !== req_data && "object" === typeof req_data) {
					this._saved_req_data = req_data;
				}
				window.setTimeout(dojo.hitch(this, "refreshRecentRequests"), 1000);
				return;
			}
			
			if(undefined == req_data || "object" !== typeof req_data) {
				req_data = this._saved_req_data;
			}
			if(undefined == req_data || null == req_data) {
				return;
			}

			// first, count all the unique request types
			var type_counts = {};	// can't be an array, because some of the itemnums are very larg integers
			dojo.forEach(req_data.Requests, function(elem, idx) {
				if(elem.item && elem.item.PMSCMRLINE && elem.item.PMSCMRLINE[0].ITEMNUM) {
					var t = elem.item.PMSCMRLINE[0].ITEMNUM;
					if(undefined == type_counts[t]) {
						type_counts[t] = 1;
					}
					else {
						++type_counts[t];
					}
				}
			});
			console.log("type counts: ", type_counts);
			

			// next, find the 4 top runners
			var invert_counts = [];
			var k = 0;
			for(var t in type_counts) {
				invert_counts[k++] = {itemnum: t, count:type_counts[t]};
			}
			invert_counts.sort(compareItems);
			
			var recents = {Category: []};
			for(var c in invert_counts) {
				console.log("%d: %s", c, invert_counts[c].itemnum);
				var request_item = walkNavigatorAndFindItem(this.rootList, invert_counts[c].itemnum);
				if(request_item) {
					recents.Category.push(request_item);
				}
				if(recents.Category.length == 4)
					break;
			}
			this.rootList.subitems[2].removeChildren();

			if(recents.Category.length > 0) {
				this.rootList.subitems[2].addChildren(recents, "RowMajor", 2);
			}
			else {
				dojo.removeClass(this.rootList.subitems[2].domNode, "leaf");
				dojo.addClass(this.rootList.subitems[2].domNode, "empty");				
			}			
	
			// if the recent requests list is active, re-show it to refresh its contents		
			if(this.rootList.getActiveList() === this.rootList.subitems[2])
				this.rootList.subitems[2].show();
			
			this._saved_req_data = null;
			return;			
			
			/********* local helpers ***********/
			function walkNavigatorAndFindItem(item, target)
			{
				var retval = null;
				//console.group("searching %s (%s) for %s", item.label, (item.data ? item.data[item.keyField] : "no item.data!"), target);
				if(item && (!item.subitems || item.subitems.length == 0 /*only find leaf nodes*/) && item.data && item.data[item.keyField] == target) {
					//console.log("*** found recent: %s ***!", target);
					retval = item.data;
				}
				else {
					for(var i in item.subitems) {
						retval = walkNavigatorAndFindItem(item.subitems[i], target);
						if(retval) {
							break;
						}
					}
				}
				//console.groupEnd();
				return retval;
			}
			
			function compareItems(a, b) {
				return b.count - a.count;	// sort decending
			}
		},

		/************* search *********************/
		_searchResultsList: null,
		_searchListConn: null,
		_search: function(evt)
		{
			if(evt.type == 'click' || (evt.type == 'keypress' && evt.keyCode == dojo.keys.ENTER && evt.target == this.list_search_term)) {
				var search_str = dojo.byId(this.list_id + '_search_term').value;
				if(search_str.length == 0) {
					return;
				}
				var search_results = this.rootList.Search(dojo.byId(this.id+'_list_search_term').value);
				if(search_results.length == 0) {
					(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({type:"Info", messageId: "CTJZH2339I"})).show();
				}
				else {
//					if(this._searchResultsList) {
//						dojo.disconnect(this._searchListConn);
//						this.searchResultsContainer.removeChild(this._searchResultsList.domNode);
//						this._searchResultsList.destroy();
//						this._searchResultsList = null;
//					}
//					this._searchResultsList = new ibm.tivoli.simplesrm.srm.dijit.ListTree({fillOrder: "RowMajor"});
//					this._searchResultsList.label = "Search Results"
					this._searchResultsList.removeChildren();
					this._searchResultsList.addChildren({Category: search_results}, "RowMajor", 2);
//					this.searchResultsContainer.appendChild(this._searchResultsList.domNode);
//					this._searchListConn = dojo.connect(this._searchResultsList, "onClick", this, "_onlistclick");

					var bPushBreadcrumb = this._searchResultsList !== this.rootList.getActiveList();
					this._searchResultsList.show();
					if(bPushBreadcrumb)
						this._onpushlist(null, this._searchResultsList);	// TODO: something's not quite right with ListTree or Navigator event handlin that I have to do this
					
//					this._showSearchResults();
				}
			}
		},
//		_showSearchResults: function()
//		{
//			var nav = this;
//			// swap the search results header in for the breadcrumbs
//			dojo.style(nav.breadcrumbContainer, "display", "none");
//			dojo.style(nav.searchHeaderContainer, "display", "block");
//			
//			// show the results
//			var sz = dojo.marginBox(nav.treeContainer);
//			dojo.style(nav.searchResultsContainer, "width", sz.w +"px");
//			dojo.style(nav.searchResultsContainer, "height", sz.h + "px");
//			dojo.style(nav.searchResultsContainer, "display", "block");
//		},
//		_hideSearchResults: function()
//		{
//			var nav = this;
//			// put the breadcrumbs back
//			dojo.style(nav.searchHeaderContainer, "display", "none");
//			dojo.style(nav.breadcrumbContainer, "display", "block");
//			// hide the search results
//			dojo.style(nav.searchResultsContainer,"display", "none");
//		},
		// ************** Toolbar navigation ****************/
		// experiment with maintaining state an supporting back/forward
		_activeListHistory: [],		// array of what lists have been active
		_activeListIndex: -1,		// index into _activeListHistory where we currently are
		_navInHistory: false,		// true if navigating thru the history, and not via direct clicks in the UI
		_goHome: function()
		{
			console.log("Navigator._goHome [%d[%d]]", this._activeListHistory.length, this._activeListIndex);
			this._navInHistory = true;
			this._activeListHistory.splice(1, this._activeListHistory.length);
			this._activeListIndex = 0;
			this._activeListHistory[0].show();
			dijit.byId(this.crumb_id).popToMorsel(0);
		},
		_setNavButtonState: function()
		{
			//dijit.byId(this.id + '_home_btn').attr("disabled",  (this._activeListIndex >= 0 && this._activeListHistory[this._activeListIndex] === this.rootList) );
		},
		// ************** ListTree event handlers ************
		_onpushlist: function(prevList, newList) {
			console.log("Navigator._onpushlist(%s, %s)", prevList?prevList.label:"null", newList?newList.label:"null");
			var newmorsel = dijit.byId(this.crumb_id).pushMorsel(newList.label, newList);
			var scrolling_box = dojo.byId(this.id + "_box")
			if(scrolling_box)
				scrolling_box.scrollTop = 0;
		},
		_onpoplist: function(prevList, newList) {
			console.log("Navigator._onpoplist(%s, %s)", prevList?prevList.label:"null", newList?newList.label:"null");
			dijit.byId(this.crumb_id).popMorsel();
			var scrolling_box = dojo.byId(this.id + "_box")
			if(scrolling_box)
				scrolling_box.scrollTop = 0;
		},
		_onlistclick: function(listtree)
		{
			console.log("Navigator._onlistclick()", listtree);
			var bCreatePopup = true;
			if ("function" == typeof this.onSrmNavigatorClick) {
				bCreatePopup = this.onSrmNavigatorClick(listtree);
			}
			if (bCreatePopup) {
				if ((listtree.data.Category != null
						&& listtree.data.Category.length > 0)
					 || listtree.data.ItemNum) {
					this.createAndShowInputForm(listtree.data, false);	// CreatorFactory mixin method
				} else {
					console.log("Navigator._onlistclick(): click action avoided")
				}
			}
		},
		_onactivatelist: function(newList)
		{
			console.log("Navigator._onactivatelist(%s) [%d[%d]]", newList.label, this._activeListHistory.length, this._activeListIndex);
			
			if(!this._navInHistory) {
				// activating a list by clicking in the UI.
				// truncate the history 
				this._activeListHistory.splice(++this._activeListIndex, this._activeListHistory.length);
				this._activeListHistory.push(newList);
			}
			this._setNavButtonState();
			this._navInHistory = false;
			console.log("-----> [%d[%d]]", this._activeListHistory.length, this._activeListIndex);
		},
		// ************* breadcrumb event handlers **********
		_oncrumbclick: function(morsel)
		{
			console.log("Navigator._oncrumbclick(%s)", morsel.text);
			morsel.trimRight();	// remove all morsels to the right of this one
			if(morsel.data) {
				// walk the history looking for this list, and truncate there
				for(var i = this._activeListHistory.length-1; i >= 0; --i) {
					if(morsel.data === this._activeListHistory[i]) {
						this._activeListHistory.splice(i, this._activeListHistory.length);
						this._activeListIndex = i-1;
						console.log("-----> [%d[%d]]", this._activeListHistory.length, this._activeListIndex);
						break;
					}
				}

				morsel.data.show();	// show the list cached in this morsel's .data				
			}
		},

		_onUIComplete: function()
		{
			console.log("Navigator._onUIComplete");
			if("function" == typeof this.onUIComplete) {
				try {
					this.OnUIComplete();
				}
				catch(ex) {
					new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
					console.error("onUIComplete event handler failed:\n", ex);
				}
			}
		}
	}
);


