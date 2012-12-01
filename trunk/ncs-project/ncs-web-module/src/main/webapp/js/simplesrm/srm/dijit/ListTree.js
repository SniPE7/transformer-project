//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.ListTree");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.ListItem");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit._Container");
dojo.require("dojo.fx");
dojo.require("dijit._base.place");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.Hoverer");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.Utilities");
dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");

/**
it's html representation of ListTree node. this code was removed from ListTree
to simplify the creation of DOM nodes. after creation instance of
this widget is stored as a field in ListTree object.
note that for legacy issues ListTree.domNode==ListItem.domNode
*/
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.ListItem", 
		[dijit._Widget,dijit._Templated], 
{	
	_uiStringTable: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable"),
	templatePath: dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit", "templates/ListItem.html"),
	label: null,
	description: null,
	icon: null,
	iconSize:0,
	iconWrapperSize: 0,
	
	iconTextBranch: ">",
	iconTextLeaf: "*",
	iconText: null ,
		
	iconTitle:null,	
	iconTitleBranch:null,
	iconTitleLeaf:null,
	
	itemStyle: null,
	itemStyleBranch: "branch",
	itemStyleLeaf: "leaf",
	
	isBranch: false,
	
	constructor: function(){
		this.iconTitleLeaf= this._uiStringTable["Panel"];
		this.iconTitleBranch= this._uiStringTable["Directory"];
		this.iconText = this.iconTextLeaf;
		this.iconTitle = this.iconTitleLeaf;
		this.itemStyle = this.itemStyleLeaf;
	},
	toBranch: function(){
		if(!this.isBranch){
			this.iconText = this.iconTextBranch;
			this.iconTitle = this.iconTitleBranch;
			this.itemStyle = this.itemStyleBranch;
			this.isBranch = true;
			this.refresh();
		}
	},
	toLeaf: function(){
		if(this.isBranch){
			this.iconText = this.iconTextLeaf;
			this.iconTitle = this.iconTitleLeaf;
			this.itemStyle = this.itemStyleLeaf
			this.isBranch = false;
			this.refresh();
		}
	},
	refresh: function(){
		dojo.attr(this.iconNode,"title", this.iconTitle);
		dojo.attr(this.iconTextNode,"title", this.iconTitle);
		this.iconTextNode.firstChild.nodeValue = this.iconText;
		dojo.removeClass(this.domNode, this.itemStyleBranch);
		dojo.removeClass(this.domNode, this.itemStyleLeaf);
		dojo.addClass(this.domNode, this.itemStyle);
	}
});


// TODO: ListTree is too tied to the data structure we return from maximo
// need to define ListTree's data and decouple the two

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.ListTree", 
	[dijit._Widget], 
{	
	label: "",					// the label shown in the tre
	description: "",			// the optional description, shown below the label
	icon: "",					// the icon filename
	data: null,					// the whole object passed in.  Kept for reference
	iconPath: "",				// path to the icon files
	iconSize: "",				// icon size, in pixels
	defaultIconSize: 48,		// default icon size, in pixels
	fillOrder: "ColMajor",		// RowMajor, ColMajor, or any other class you want to write CSS for
	colCount: 1,				// how many columns of data
	_crossFader: null,			// dojo._Animation object that handles the cross fade
	_xfadeend_handle: null,		// event connection handle for cross fade animation onEnd
	_connHandles: null,			// my event connection handles
	_flymgr: null,				// handles the popup long description
	_scrollbarWidth: 0,			// width of the browser's scrollbars
	
	listItemWidget: null,		//read only, filled in _buildItemDomNodes
	
	// teach this ListTree how to parse its data
	keyField: '',				// the data field that is the unique key/index
	labelField: '', 			// the data field holding the label
	descField: '',			// the data field holding the description
	iconField: '',			// the data field holding the path to the icon file
	iconSizeField: '',		// the data field holding the icon size
	childField: '',			// the data field holding the children sub-trees of the current node
	leafField: '',			// the data field holding the chilren leaf-nodes of the current node
	
	// ******** lifecycle methods ********
	constructor: function(/*object*/params, /*domnode?*/domNode) 
	{
		//console.log("ListTree.ctor");
		this.containerNode = null;	// the div containing the rendered list
		this.listNode = null;		// the ul implementing the rendered list
		this.subitems = [];			// the sub-ListTrees that are my list items
		this.parentList = null;		// my parent list in the tree
		this.activeList = null;		// the currently active list.  only populated in the root node of the tree
		this._xfadeDuration = 300;
		this._connHandles = [];
	},
	startup: function() 
	{
		//console.log("ListTree.startup");
		this.inherited(arguments);
		this._crossFade(null, this);
	},
	buildRendering: function()
	{
		this.inherited(arguments);	
		this._buildListDomNodes();
	},
	// dojo 1.2 book I have says to override uninitialize, but
	// the dojo version we have here calls destroy
	destroy: function()
	{
		console.log("ListTree.destroy");
		if(this._flymgr) {
			this._flymgr.destroy();
		}
		var h;
		while(h = this._connHandles.pop()) {
			dojo.disconnect(h);
		}
		this.inherited(arguments);
	},
	setIconPath: function(/*string*/path)
	{
		this.iconPath = path;
		if(path.charAt(path.length-1) != '/') {
			this.iconPath += '/';
		}
	},
	getIconPath: function()
	{
		return this.getTreeRoot().iconPath;
	},
	// ******  tree building methods *********
	// add the children of the given object to this tree node
	addChildren: function(/*obj*/ofThisObj, /*string?*/ fillOrder, /*integer?*/ colCount)  {
		// walk the incoming data array, creating the sub-tree of ListTree objects
		
		//console.group(this.label, ": add Children");
		if((ofThisObj[this.childField] && ofThisObj[this.childField].length > 0) ||
			(ofThisObj[this.leafField] && ofThisObj[this.leafField].length > 0)	) {
			// adding children may change this node from a leaf to a branch node
			
			if(this.listItemWidget) {
				this.listItemWidget.toBranch();
			};
			if(!fillOrder || !dojo.isString(fillOrder)) {
				fillOrder = this.fillOrder;
			}
			else {
				this.fillOrder = fillOrder
			}
			colCount = parseInt(colCount);
			if(isNaN(colCount)) {
				colCount = this.colCount;
			}
			else {
				this.colCount = colCount;
			}
			var subarrays = [ofThisObj[this.childField], ofThisObj[this.leafField]];
			for(var k in subarrays) {
				var childArray = subarrays[k];
				if(!childArray)
					continue;

				dojo.forEach(childArray, dojo.hitch(this, function(child) { 
					childitem = new ibm.tivoli.simplesrm.srm.dijit.ListTree({
							fillOrder: fillOrder, colCount: colCount,
							keyField: this.keyField, labelField: this.labelField, descField: this.descField, iconField: 
							this.iconField, childField: this.childField, leafField: this.leafField});
					childitem.data = child;
					childitem.label = child[this.labelField].htmlencode();
					
					if(child[this.descField]) {
						var truncate = 100;
						var d = child[this.descField];
						if(d.length > truncate*2) {
							var d = d.substring(0, truncate).replace(/\s+$/, "").htmlencode();	// trim trailing spaces
							d += "...";	// TODO: put in nls string table.  uiStingTables have to move up a level
								
							var more = child[this.descField].htmlencode();
							more = more.replace(/\r/g, "")
										.replace(/\n[ \t]{3,}(.*)/g, "<br><span style=\"margin-left:2em;\">&nbsp;</span>$1")
										.replace(/\n/g, "<br>")
									;
							d += "<div class='more'>" + more + "</div>";
						}
						childitem.description = d;
					}
					else {
						childitem.description = "&nbsp;"
					}
					
					// if no icon is given, use the parent's
					childitem.icon = child[this.iconField] ? child[this.iconField] : this.icon;
					childitem.iconSize = (undefined !== this.iconSizeField && typeof child[this.iconSizeField] == "number") ? child[this.iconSizeField] : this.defaultIconSize;
					//console.log("child.icon = ", childitem.icon);
					this.subitems.push(childitem);			// add the new ListTree node to my sublist
					childitem.parentList = this;			// back-pointer to the new node's parent ListTree
					// TODO: eventyally handle boh sub-trees and leaf-nodes
					if(child[this.childField] || child[this.leafField]) {
						childitem.addChildren(child);	// down another level
					}
				}));
			}
		}
		//console.groupEnd();
	},
	removeChildren: function()
	{
//		if(this.getActiveList() == this) {
//			this.popList();
//		}
		var item;
		while(item = this.subitems.pop()) {
			console.group("removing ", item.label);
			item.removeChildren();
			if(item.containerNode) {
				item.containerNode.parentNode.removeChild(this.containerNode);
				item.containerNode = null;
			}
			item.destroy();
			console.groupEnd();
		}
		if(this.containerNode) {
			this.containerNode.parentNode.removeChild(this.containerNode);
			this.containerNode = null;
		}
	},
	_buildItemDomNodes: function()
	{
		//console.log("Treelist._buildItemDomNodes(%s)[%s]", this.label, this.icon);
		// the list item
		var cell = document.createElement('td');	
		
		var itemNode = new ibm.tivoli.simplesrm.srm.dijit.ListItem({
			label: this.label,		
			description: this.description,
			icon: this.icon,
			iconSize: this.iconSize,
			iconWrapperSize: this.iconSize + 2
			});
		this.listItemWidget = itemNode;
		this.domNode = itemNode.domNode;//there's code that depends on it
		var node = itemNode.domNode;
		cell.appendChild(node);
		if(this.subitems.length == 0) {
			dojo.addClass(node, "leaf");
			itemNode.toLeaf();
		}
		else {
			dojo.addClass(node, "branch");
			itemNode.toBranch();
		}
		this._connHandles.push(dojo.connect(node, 'onkeypress', dojo.hitch(this, '_onEnterKeyPressed')));
		this._connHandles.push(dojo.connect(node, 'onclick', dojo.hitch(this, '_onclick')));
		this._connHandles.push(dojo.connect(node, 'onmouseover', dojo.hitch(this, '_onmouseover')));
		this._connHandles.push(dojo.connect(node, 'onmouseout', dojo.hitch(this, '_onmouseout')));
			
		return cell;
		
		/*
		//TODO: remove previous version of this method
		// the list item
		var cell = document.createElement('td');
		var itemnode = this.domNode = document.createElement("div");  
		cell.appendChild(itemnode);
		if(this.subitems.length == 0) {
			dojo.addClass(itemnode, "leaf");
		}
		else {
			dojo.addClass(itemnode, "branch");
		}
		dojo.addClass(itemnode, "ListItem");

		s = "<table><tr>"
			+ "<td class='icon' style='width:"+(this.iconSize+2)+"px;'><img src='" + this.icon +"' style='width:"+this.iconSize+"px;'></td>"
			+  "<td class='content'>\n"
			+  "<p class='label'>" + this.label + "</p>\n"
			+  "<div class='description'>" + this.description + "</div>\n"
			+  "</td>\n"
			+  "<td class='sprite'><div class='sprite'></div></td>\n"
			+ "</tr></table>";
		itemnode.innerHTML = s;
		//console.log(li.innerHTML);
		
		dojo.attr(itemnode,"tabindex", 0);
	
		// wire it up
		this._connHandles.push(dojo.connect(itemnode, 'onkeypress', dojo.hitch(this, '_onEnterKeyPressed')));
		this._connHandles.push(dojo.connect(itemnode, 'onclick', dojo.hitch(this, '_onclick')));
		this._connHandles.push(dojo.connect(itemnode, 'onmouseover', dojo.hitch(this, '_onmouseover')));
		this._connHandles.push(dojo.connect(itemnode, 'onmouseout', dojo.hitch(this, '_onmouseout')));
			
		return cell;
		*/
	},
	_buildListDomNodes: function()
	{
		//console.log("ListTree._buildListDomNodes(%s)", this.label);
		if(this.subitems.length > 0 && this.containerNode == null) {	// this list hasn't been rendered in the DOM yet
			var treeDomNode = this.getTreeRoot().domNode;

			// holds the list at this level of the tree
			this.containerNode = document.createElement('div');
			this.containerNode.id = this.id + "_cn_box";
			dojo.addClass(this.containerNode, "ListTree");
			dojo.style(this.containerNode, "position", "relative");
			dojo.style(this.containerNode, "display", "none");
			dojo.place(this.containerNode, treeDomNode, "last");

			this.listNode = document.createElement('div');
			this.listNode.id = this.id + "_listnode";
			dojo.addClass(this.listNode, "ListTree");
			dojo.addClass(this.listNode, this.fillOrder);
		
			var thelist = this.listNode;
/*
 * TODO: when i changed from a ul to a table implementation, I didn't have time to fix the colmajor code
 * so it won't render right
 */
			if("ColMajor" == this.fillOrder)
			{
//				// 2 columns filled column-major order
//				var itemCount = 0;
//				var itemsPerCol = this.subitems.length / 2;
//				dojo.forEach(this.subitems, dojo.hitch(this, function(item) {
//					var li = item._buildItemDomNodes();
//					dojo.place(li, thelist, "last");
//					if(++itemCount >= itemsPerCol) {
//						itemCount = 0;
//						dojo.place(thelist, this.containerNode, "last");
//						thelist = document.createElement('thelist');
//						dojo.addClass(thelist, "ListTree");
//						dojo.addClass(thelist, "ColMajor");
//					}	
//				}));
//				dojo.place(thelist, this.containerNode, "last");
//
//				var clearer = document.createElement("div");
//				dojo.style(clearer, "clear", "both");
//				dojo.style(clearer, "height", "0");
//				dojo.place(clearer, this.containerNode, "last");
			}
			else {
				// Fill in RowMajor
				//console.log("filling ", this.fillOrder);
				this.containerNode.appendChild(thelist);
				var tbl = document.createElement("table");
				dojo.addClass(tbl, "ListTree");
				dojo.addClass(tbl, this.fillOrder);
				thelist.appendChild(tbl);
				var tb = document.createElement("tbody");
				tbl.appendChild(tb);
				thelist = tb;
				// columns filled row-major order
				var itemCount = 0;
				var curr_row = null;
				dojo.forEach(this.subitems, dojo.hitch(this, function(item) {
					if(itemCount++ % this.colCount == 0) {
						curr_row = document.createElement("tr");
						thelist.appendChild(curr_row);
					}
					var one_item = item._buildItemDomNodes();
					curr_row.appendChild(one_item);
				}));
				if(itemCount % this.colCount !== 0 ) {
					var cell = document.createElement("td");
					cell.innerHTML = "&nbsp;";
					curr_row.appendChild(cell);
				}
				
			}
			// the tree's DOM nodes are in place, now wire up the flyover manager to the descriptions
			this._flymgr = new ibm.tivoli.simplesrm.srm.dijit.ListTreeFlyManager();
			var d_list = dojo.query(".description", this.containerNode);
			this._flymgr.connect(d_list);
		}
		
	},
	_getScrollbarWidth: function()
	{
		if(this._scrollbarWidth == 0) {
			var sbszr = document.createElement("div");
			dojo.style(sbszr, "width", "30px");
			dojo.style(sbszr, "position", "absolute");
			dojo.style(sbszr, "left", "-40px");
			dojo.style(sbszr, "top", "0px");
			dojo.style(sbszr, "overflowY", "auto");
			dojo.style(sbszr, "backgroundColor", "blue");
			document.body.appendChild(sbszr);
			var w_auto = sbszr.clientWidth;
			dojo.style(sbszr, "overflowY", "scroll");
			var w_scroll = sbszr.clientWidth;
			document.body.removeChild(sbszr);
			this._scrollbarWidth = w_auto - w_scroll;
		}
		return this._scrollbarWidth;
	},	
	getTreeRoot: function()
	{
		var treeNode = this; 
		while(treeNode.parentList != null) {
			treeNode = treeNode.parentList;
		}
		return treeNode;
	},
	getActiveList: function()
	{
		return this.getTreeRoot().activeList;
	},

	// ********* state management methods ***********
	// shows this list's sublist
	// fires onPushList and onChangeList
	showSubList: function() {
		//console.log("ListTree.showSubList");
		this._stopCrossFade();
		
		this._setActiveList(this);
		this._crossFade(this.parentList, this);
		this._onpushlist(this.parentList, this);
	},
	// shows this list's parent list
	// fires onPopList. and onChangeList
	popList: function() {
		this._stopCrossFade();
		
		// hide me
		if(this.containerNode) {
			if(this.parentList) {	// this is a sublist
				this._setActiveList(this.parentList);
				this._crossFade(this, this.parentList);
				this._onpoplist(this, this.parentList);
			}
			else {		// this is the root list
				this._onactivatelist(this);
			}
		}
	},
	// shows this list
	// fires onChangeList
	show: function() {
		this._stopCrossFade();
		
		this._show();
		//this._onchangelist(this.getActiveList(), this);
	},
	reset: function() {
		this._stopCrossFade();
		
		this.getTreeRoot()._show();
		this._onactivatelist(this);
	},
	_show: function()
	{
		//console.log("ListTree._show(%s)", this.label);
		var active = this.getActiveList();
		this._setActiveList(this);
		this._crossFade(active, this);
		this._onactivatelist(this);
	},
	_setActiveList: function(list)
	{
		this.getTreeRoot().activeList = list;
	},

	// ****** events **********
	_onEnterKeyPressed: function(evt) {
		//console.log("ListTree._onEnterKeyPressed");
		if (evt.keyCode == dojo.keys.ENTER) {
			dojo.stopEvent(evt);
			this._onclick(evt);
			return false;
		}
		return true;
	},
	_onmouseover: function(evt) {
//		console.log("ListTree._onmouseover, domNode:", this.domNode);
		dojo.addClass(this.domNode, 'highlight');
	},

	_onmouseout: function(evt) {
		var current_target = null;
		if(dojo.isIE) {
			if( evt.toElement && evt.toElement.className !== "popupmore") {
//				console.log("ListTree._onmouseout: ie out, domNode:", this.domNode);
				dojo.removeClass(this.domNode, "highlight");
			}
			else {
				current_target = evt.toElement;
			}
		}
		else {
			current_target = evt.relatedTarget;
		}
//		console.log("ListTree._onmouseout: current_target: ", current_target);
		for(var e = current_target; e != null && e != document.body; e = e.parentNode) {
			if(e == this.domNode) {
				// still w/in the dom node
//				console.log("ListTree._onmouseout: still w/in domNode");
				return;
			}
		}
		// 
		//console.log("ListTree._onmouseout: out, domNode:", this.domNode);
		dojo.removeClass(this.domNode, "highlight");
	},
	_onclick: function(evt) {
		//console.log("ListTree._onclick");
		this._stopCrossFade();
		
		if(this.subitems.length > 0) {
			this.showSubList();
		}
		else {
			if(this.onClick(this)) {
				this.getTreeRoot().onClick(this);
			}
		}
	},
	_onpushlist: function(prevList, newList) {
		//console.log("ListTree._onpushlist");
		this.getTreeRoot().onPushList(prevList, newList);
		this._onchangelist(prevList, newList);
		this._onactivatelist(newList);
	},
	_onpoplist: function(prevList, newList) {
		//console.log("ListTree._onpoplist");
		this.getTreeRoot().onPopList(prevList, newList);
		this._onchangelist(prevList, newList);
	},
	_onactivatelist: function(newList) {
		this.getTreeRoot().onActivateList(newList);
	},
	_onchangelist: function(prevList, newList) {
		this.getTreeRoot().onChangeList(prevList, newList);
	},
	onClick: function(listNode) {
		return true;
	},
	onPushList: function(prevList, newList) {
		return true;
	},
	onPopList: function(prevList, newList) {
		return true;
	},
	onChangeList: function(prevList, newList) {
		return true;
	},
	onActivateList: function(newList) {
		return true;
	},

	// ********* helpers ***********
	_gethider: function(list) {
		if(list == undefined) {
			list = this;
		}
		return dojo.fadeOut({
			node: list.containerNode, 
			duration: this._xfadeDuration,
			onEnd:function(){
				dojo.style(list.containerNode,"display", "none");
			}});
	},
	_getshower: function(list) {
		if(list == undefined) {
			list = this;
		}
		return dojo.fadeIn({
			node: list.containerNode,
			duration: this._xfadeDuration,
			onPlay: function() {
				dojo.style(list.containerNode, "display", "block");
			},
			onEnd: dojo.hitch(this,"putFocus",list)
			});
	},
	_crossFade: function(outlist, inlist)
	{
		console.group("ListTree._crossFade");
		if(inlist) {
			inlist._buildListDomNodes();
		}
				
		
			if(this._xfadeend_handle) {
				dojo.disconnect(this._xfadeend_handle);
				this._xfadeend_handle = null;
			}
			this._crossFader = null;
			
			var xfadechain = [];
			if(outlist && outlist.containerNode) {
				console.log("out: %s (%s)", outlist.containerNode.id, outlist.label);
				h = this._gethider(outlist);
				xfadechain.push(h);
			}
			if(inlist && inlist.containerNode) {
				console.log("in: %s (%s)", inlist.containerNode.id, inlist.label);
				s = this._getshower(inlist);
				xfadechain.push(s);
			}
			if(xfadechain.length > 0) {
				this._crossFader = dojo.fx.chain(xfadechain);				
				this._crossFader.play();
			}
		
		console.groupEnd();
	},
	putFocus: function(inlist){
		//console.log("ListTree.putFocus");		
		var isVisible = function(x){
			if(dojo.style(x,"display")!="block")	{
				return false;
			} 
			return true;
		};
		
		var allItems = dojo.query("div.ListItem",inlist.containerNode);
		//console.log("allItems: " + (allItems ?  allItems.length : "not array" ));
		var visibleItems = dojo.filter(	allItems , isVisible	);		
		//console.log("visisbleItems: " + (visibleItems ?  visibleItems.length : "not array" ));
		if(visibleItems && visibleItems.length>0){
			visibleItems[0].focus();
		}
	},
	_stopCrossFade: function()
	{
		if(this._crossFader && this._crossFader.status() == "playing") {
			this._crossFader.stop(true);
		}	
	},
	/*
	** Search the list tree rooted at this list for the given text (which can be a regular expression)
	** Return the results as an array of he data objects originally cached in the ListTree objects
	*/
	Search: function(/*string*/search_text) 
	{
		var found_items = {};
		var regexp = new RegExp(search_text, "i");
		this._doSearch(regexp, found_items);
		console.group("ListTree.Search found:");
		var found_items_array = [];
		for(var i in found_items) {
			found_items_array.push(found_items[i]);
			//console.log(i, ": ", found_items[i].Description);
		}
		console.groupEnd();
		return found_items_array;
	},
	_doSearch: function(/*regexp*/search_exp, /*Array*/found_items)
	{
		//console.group("ListTree._doSearch(%s): %s", search_exp, this.label);
		if(this.label.match(search_exp)) {
			//console.log("found label for", this.label);
			if(this.data && this.data[this.keyField]) {
				found_items[this.data[this.keyField]] = this.data;
			}

		}
		else if(this.description.match(search_exp)) {
			//console.log("found description for ", this.label);
			if(this.data && this.data[this.keyField]) {
				found_items[this.data[this.keyField]] = this.data;
			}
		}
		else if(this.data && this.descField) {
			if(this.data[this.descField] && this.data[this.descField].match(search_exp)) {
				//console.log("found long description for ", this.label);
				if(this.data && this.data[this.keyField]) {
					found_items[this.data[this.keyField]] = this.data;
				}
			}
			else if(this.data[this.keyField] && this.data[this.keyField].match(search_exp)) {
				found_items[this.data[this.keyField]] = this.data;
			}
		}
		for(var i in this.subitems) {
			this.subitems[i]._doSearch(search_exp, found_items)			
		}
		//console.groupEnd();
	},	
	
	dump: function()
	{
		console.group(this.label);
		if(this.subitems) {
			dojo.forEach(this.subitems, function(item) {	// don't need hitch, because never using 'this'
				item.dump();
			});
		}
		console.groupEnd();
	}
});
/*
** class ListTreeFlyManager
** Manages the flyover popup of list item's long description
*/
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.ListTreeFlyManager", null,
{
	activeDesc: null,
	activeMore: null,
	overHandler: null,
	outHandler: null,
	hideHandler: null,
	showHandler: null,
	connHandles: null,
	outConnHandles: null,		// array of event handler connections
	_hoverers: null,			// one for each node we're watching
	_popupDiv: document.createElement("div"),
	
	
	constructor: function()
	{
		//console.log("FlyManager.ctor");
		this.connHandles = [];
		this.outConnHandles = [];
		this._hoverers = [];
		this._highlighter = dojo.hitch(this, "_highlightListNode");
		
		// some basic initialization of the popup
		this._popupDiv.className = "popupmore";
		document.body.appendChild(this._popupDiv);
	},
	destroy: function()
	{
		//console.log("FlyManager.destroy");
		this.disconnect();
		var hvr;
		while(hvr = this._hoverers.pop()) {	
			hvr.destroy();
		}
	},
	connect: function(/*Array of DOM nodes*/elem_array)
	{
		//console.log("FlyManager.connect");

		if(elem_array && elem_array.length > 0) {
			// cache event handlers
			this.overHandler = dojo.hitch(this, "_over");
			this.outHandler = dojo.hitch(this, "_out");
			this.showHandler = dojo.hitch(this, "_showFly");
			this.hideHandler = dojo.hitch(this, "_hideFly");
		}

		elem_array.forEach(dojo.hitch(this, dojo.hitch(this, function(d) 
		{
			if(dojo.query("div.more", d).length == 1) {
				//console.log("connecting", d);
				this.connHandles.push(dojo.connect(d, 'onmouseover', null, this.overHandler));
				this.connHandles.push(dojo.connect(d, 'onmouseout', null, this.outHandler));
				var hvr = new ibm.tivoli.simplesrm.srm.dojo.Hoverer();
				hvr.setDOMNode(d);
				this.connHandles.push(dojo.connect(hvr, "OnHover", null, this.showHandler));
				this._hoverers.push(hvr);
			}
		})));
	},
	disconnect: function()
	{
		dojo.forEach(this.connHandles, function(x) 
		{
			dojo.disconnect(x);
		});
		this.connHandles = null;
	},
	_over: function(evt)
	{
		//console.log("FlyManager._over");
		if(dojo.hasClass(evt.target,'more')) return;
		//console.log("over target: %s activeDesc: %o this.activeMore: %o", evt.target.id, this.activeDesc, this.activeMore);
		this.activeDesc = evt.target;
	},
	_out: function(evt)
	{
		//console.log("FlyManager._out");
		if(this.outConnHandles.length > 0 || !this.activeMore) return;
		//console.log("out  target: %s this.activeDesc: %o this.activeMore: %o", evt.target.id, this.activeDesc, this.activeMore);
		if(undefined!= this.activeMore) {
			//dojo.style(this.activeMore, 'display', 'none');
			this.activeMore = null;
		}
	},
	_findMore: function()
	{
		//console.log("LisTree._findMore: activeDesc=", this.activeDesc);
		if(this.activeDesc) {
			var more = dojo.query(".more", this.activeDesc);
			if(more.length > 0) {
				//console.log("ListTree._findMore found: ", more[0]);
				return more[0];
			}
		}
		return null;
	},
	_showFly: function()
	{
		if(this.activeMore)
			return;

		this.activeMore = this._findMore();
		//console.log("More = ", this.activeMore);
		if(this.activeMore) {
			//console.log("FlyManager._showFly", this.activeMore);
			var sz = dojo.boxModel == "content-box" ? dojo.contentBox(this.activeDesc) : dojo.marginBox(this.activeDesc);
			var borderLeft = dojo.style(this.activeMore, "borderLeftWidth");
			var borderTop = dojo.style(this.activeMore, "borderTopWidth");
			var padLeft = dojo.style(this.activeDesc, "paddingLeft");
			var padRight = padLeft > 0 ? padLeft : 5;
			var padTopBottom = 5;
			var bodyScrollX = document.documentElement.scrollLeft; 
			var bodyScrollY = document.documentElement.scrollTop;
			var loc = dojo.coords(this.activeDesc);
			var fontSize = dojo.style(this.activeDesc, "fontSize"); 
			if(fontSize.indexOf("em") > 0) {
				fontSize = (parseFloat(fontSize) / .0625);
				if(dojo.isIE) {
					fontSize *= 0.8;	// go figure
				}
				fontSize += "px";
			}
			var lineHeight = dojo.style(this.activeDesc, "lineHeight");
			if(dojo.isIE) {
				loc.y += document.body.scrollTop;
				loc.x += document.body.scrollLeft;
			}

			this._popupDiv.innerHTML = this.activeMore.innerHTML;
			dojo.style(this._popupDiv, "padding", padTopBottom + "px " + padRight + "px " + padTopBottom + "px " + padLeft + "px");
			dojo.style(this._popupDiv, 'left', (loc.x - borderLeft + bodyScrollX) + "px");
			dojo.style(this._popupDiv, 'top',  (loc.y - borderTop - padTopBottom + bodyScrollY)  + "px");
			dojo.style(this._popupDiv, 'width', (sz.w) + "px");
			//dojo.style(this._popupDiv, 'height', "auto");
			dojo.style(this._popupDiv, 'fontSize', fontSize);
			dojo.style(this._popupDiv, 'fontFamily', dojo.style(this.activeDesc, "fontFamily"));
			if(lineHeight) {
				dojo.style(this._popupDiv, 'lineHeight', dojo.style(this.activeDesc, "lineHeight")+"px");
			}
			dojo.style(this._popupDiv, "color", dojo.style(this.activeDesc, "color"));
			dojo.style(this._popupDiv, 'display', 'block');
			//console.log("FlyManager _popupDiv: ", this._popupDiv);
			window.setTimeout(this._highlighter, 10);		
			this.outConnHandles.push(dojo.connect(this._popupDiv, "onmouseout", this.hideHandler));
			this.outConnHandles.push(dojo.connect(this._popupDiv, "onclick", this.hideHandler));
		}
	},
	_highlightListNode: function()
	{
		//console.log("FlyManager._highlightListNode");
		// when the popup displays, the list item gets an onmouseout and looses its highlight.
		// keep that from happening
		for(var n = this.activeDesc; undefined != n.tagName; n = n.parentNode) {
			if(n.tagName.toLowerCase() == 'div') {
				if(dojo.hasClass(n, "ListItem")) {
					dojo.addClass(n, 'highlight');
					break;
				}
			}
		}
	},
	
	_hideFly: function(evt)
	{
		//console.log("hide", this.activeMore, evt);
		if(evt.type == "click") {
			// clicking in the "more" popup hides it, but won't click on the underlying list item
			evt.stopPropagation();
		}
		while(c = this.outConnHandles.pop()) {
			dojo.disconnect(c);
		}
//		dojo.style(this.activeMore, 'display', 'none');
		dojo.style(this._popupDiv, "display", "none");
		this.activeMore = this.activeDesc = null;
	},
	_dummy:null
});
