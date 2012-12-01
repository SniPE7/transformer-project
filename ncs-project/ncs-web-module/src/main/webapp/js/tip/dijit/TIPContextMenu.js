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
dojo.require("dijit.Menu");
dojo.require("ibm.tivoli.tip.dijit.TIPMessageDialog");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("ibm.tivoli.tip.dojo.Global");
dojo.require("ibm.tivoli.tip.dojo.Server");
dojo.provide("ibm.tivoli.tip.dijit.TIPContextMenu");
dojo.provide("ibm.tivoli.tip.dijit.TIPContextMenuProxy");
dojo.provide("ibm.tivoli.tip.dijit.TIPContextMenuProvider");
dojo.provide("ibm.tivoli.tip.dijit.CMSMenuProvider");

dojo.requireLocalization("ibm.tivoli.tip.dijit", "messages");


dojo.declare("ibm.tivoli.tip.dijit.TIPContextMenu",
              dijit.Menu,
{
	removeItemsOnClose: false,

	constructor: function () {
	},
	// override dijit..PopupMenu
	onOpen: function(/*Event*/ e){
		// summary: 
		//		Callback when menu is opened.
		this.inherited ("onOpen", arguments);
		if (this.removeItemsOnClose) {
			// hide temporarily while the menu is updated
			this.domNode.style.display = "none";
			this.removeItemsOnClose(e);
		}
	},

	// override parent
	closeAll: function(/*Boolean?*/force) {	
		this.inherited ("closeAll", arguments);
//		this._removeOnClose();
	},
	
/**
 * Display the context menu at an x,y position.
 *
 */
    displayContextMenu: function(x,y){
      //   dojo.stopEvent(e);

		var self=this;
		var savedFocus = dijit.getFocus(this);
		function closeAndRestoreFocus(){
			// user has clicked on a menu or popup
			dijit.focus(savedFocus);
			dijit.popup.close(self);
		}
		dijit.popup.open({
			popup: this,
			x: x,
			y: y,
			onExecute: closeAndRestoreFocus,
			onCancel: closeAndRestoreFocus,
			orient: this.isLeftToRight() ? 'L' : 'R'
		});
		this.focus();

		this._onBlur = function(){
			// Usually the parent closes the child widget but if this is a context
			// menu then there is no parent
			dijit.popup.close(this);
			// don't try to restore focus; user has clicked another part of the screen
			// and set focus there
		};

    },
/**
 * Destroy the menu and its submenus from the DOM. 
 * This is used to reuse IDs when dynamically creating
 * on demand context menus.
 * <pre>
 * Example below, checks to see if there is a menu already anmd removes
 * it before creating a new menu which is demand loaded.
 * 
 *          var cleanup=dijit.byId("progMenu");
 *          if (cleanup) {          //cleanup previous menu construction
 *             cleanup.destroyMenuDOM();
 *          }
 * </pre>
 **/
    destroyMenuDOM: function(){
      dojo.forEach(this.getDescendants(), function(child){ 
        child.destroy(true);
        if (child.popup) {
           child.popup.destroymenuDOM();
        }

      });
      this.destroy(true);
    }
});

dojo.declare("ibm.tivoli.tip.dijit.TIPContextMenuProxy",
	[ dijit._Widget, dijit._Templated ],
{
	provider: null,  /* TIPContextMenuProvider */
	// Callback function that returns the selected data for the context menu
	getSelectedData: null,
	targetNodeIds: null,
	removeItemsOnClose: false,

	constructor: function() {
		// summary: A reusable widget for displaying a dynamic set of parameters to be 
		//  filled in at runtime.
	    this.menuItems_ = [];
	    this.menus_= [];
	    this.root_ =  null;
	    this.nodesToBind = [];
	    this.done_ = false; // this flag is needed to prevent redundant menus
	    this.messages_ = dojo.i18n.getLocalization("ibm.tivoli.tip.dijit", "messages");
	},
	postCreate: function() {
		this.inherited ("postCreate", arguments);
		if (this.provider !== null) {
			this.provider.initialize(this.id, dojo.hitch(this, this.addMenuItemsCallback), dojo.hitch (this, this.getSelectedData));
		} else {
			this.startup();
		}
	},
	startup: function () {
		this.inherited (arguments);
	},
	getTIPContextMenu: function () {
		return (this.root_);
	},
	bindDomNode: function (node) {
		if (this.root_) {
			this.root_.bindDomNode (node);
		} else {
			this.nodesToBind.push (node);
		}
	},
	bindPendingNodes: function () {
		for (var x in this.nodesToBind) {
			if (x) {
				this.root_.bindDomNode (this.nodesToBind[x]);
				this.nodesToBind.pop();
			}
		}
	},
	addMenuItemsCallback: function () {
		if (this.provider !== null && !this.done_) {
//			var items = this.provider.getItems();
			var menus = this.provider.getMenus();
			var aMenu = null;
			var items = null;
			var x = null;
			
			for (x in menus) {
				if (x) {
					aMenu = menus[x];
					items = aMenu.menuitems;
					if (items && items.length > 0) {
						// create a dijit:Menu
						var menu = null;
						if (aMenu.id !== this.id + "ROOT") {
							var pMenu = this.getById (this.menus_, aMenu.parentMenuId);
						 	menu = new dijit.Menu ({id: aMenu.id, parentMenu:pMenu });
						} else {
							this.id = this.id + "ROOT";
							menu = new dijit.Menu ({id: aMenu.id, targetNodeIds:this.targetNodeIds, removeItemsOnClose: this.removeItemsOnClose });
							this.root_ = menu;
							this.bindPendingNodes();
						}
						this.menus_.push(menu);
					}
				}
			}
			
			for (x in menus) {
				if (x) {
					aMenu = menus[x];
					items = aMenu.menuitems;
				
					if (items && items.length > 0) {
						var menu2 = this.getById (this.menus_, aMenu.id);
					
			            for (var i = 0; i < items.length; i++) {
			            	var item = null;
			            	if (items[i].id.toLowerCase() == "separator") {
			            		item = new dijit.MenuSeparator();
		    	        		menu2.addChild(item);
		        	    	} else if (items[i].submenuId) {
		            			var submenu = this.getById (this.menus_, items[i].submenuId);
		            			var submenuItem = new dijit.PopupMenuItem ({label:items[i].label, popup:submenu, id:items[i].id});
			            		menu2.addChild (submenuItem);
		    	        	} else { // dijit:MenuItem
		        	    		var fnc = new Function(
		            				"if (this.provider) { this.provider.getMenuCommand(\"" + items[i].id + "\", this.getSelectedData()); }"		);
//		            				+ "this._removeOnClose();"
			            		item = new dijit.MenuItem({ id: items[i].id,
    	       												label: items[i].label,
    	       												iconClass : (items[i].iconClass) ? items[i].iconClass : ""
           											});

								item.onClick = dojo.hitch (this, fnc);
           						if (items[i].enabled === false) {
            						item.setDisabled(true);
		            			} else {
	            					item.setDisabled(false);
    	        				}
  
    	        				menu2.addChild(item);
	    			       		this.menuItems_[item.id] = item;
	    			       		item.startup();
//	    			       		debugger;
		        	    	}
		            	}
					}
				}
			}

			// show the menu with the updates
			if (this.removeItemsOnClose) {
//				this.domNode.style.display = "";
			}
			
			// set flag to note that the menu has been built
			this.done_ = true;
		}


		for (var y in this.menus_) {
			if (y) {
				var myMenu = this.menus_[y];
				myMenu.startup();
			}
		}
	},
	
	getById: function (myArray, id) {
		for (var x = myArray.length - 1; x >= 0; x--) {
			var entry = myArray[x];
			if (entry.id == id) {
				return (entry);
			}
		}
		
		return (null);
	},
	
	onContextMenuOpen: function() {
		if (this.provider) {
			this.provider.onContextMenuOpen();
			var items = this.provider.getItems();
            for (var i = 0; i < items.length; i++) {
            	if (items[i].id.toLowerCase() != "separator") {
            		if (items[i].enabled === false) {
            			this.menuItems_[items[i].id].setDisabled(true);
            		}
            		else {
            			this.menuItems_[items[i].id].setDisabled(false);
            		}
            	}
            }
		}
	},
	
	updateProviderContext: function(/* JSON */ context) {
		// summary:
		// Updates the provider with the given context and then re-builds the 
		// menu items
		if (this.provider) {
			this._removeOnClose(); // in case closeAll was not called
			var callback = dojo.hitch(this, this.addMenuItemsCallback);
			this.provider.update(context, callback);
			this.provider.initialize(this.id, callback, dojo.hitch (this, this.getSelectedData));
		}
	},

	_removeOnClose: function() {
		if (this.root_.removeItemsOnClose) {
			this.removeAllItems();
		}
	},
	
	removeAllItems: function () {
		console.info ("enter removeAllItems");
		for (var x = this.menus_.length - 1; x >= 0; x--) {
			var menu = this.menus_[x];
			for (var y = menu.getChildren().length - 1; y >= 0; y--) {
				var menuItem = menu.getChildren()[y];
				menu.removeChild(menuItem);
//				console.log ("removing from dijit.registry: " + menuItem.id);
				dijit.registry.remove (menuItem.id);
			}
			
			// delete all menus but the root
			if (menu.id != this.id) {
//				console.log ("removing from dijit.registry: " + menu.id);
				dijit.registry.remove (menu.id);
				this.menus_.pop();
			}
		}
		
		// indicate that the menu is not built
		this.done_ = false;
		console.info ("exit removeAllItems");
	}
	
});


dojo.declare("ibm.tivoli.tip.dijit.TIPContextMenuProvider", 
	         [ dijit._Widget, dijit._Templated ],
{
        iscPortletNamespace: null,
        getItemsCallback: null,

	    constructor: function() {
    	    // summary:
	        //      The base class for all providers that populate the TIPContextMenu
			this.id_ = "1_";
	        this.menus_ = [];
	    },

		initialize : function (id, callback, getSelectedData) {
			console.info ("enter initialize: " + this.id);
			this.id_ = id;
			if (this.getItemsCallback) {
				console.info ("getting items callback");
				var menuItems = this.getItemsCallback();
				console.info ("about to recur add menu items");
				this.recursivelyAddMenuItems (menuItems);
			}
			
			if (callback) {
				callback();
			}
		},

		addMenu: function(/*dijit:Menu*/ menu, /*String*/ parentMenuId) {
        	parentMenuId = (parentMenuId) ? parentMenuId : this.id_ + "ROOT";
        	var parentMenu = this.menus_[parentMenuId];
        	if (!parentMenu && parentMenuId == this.id_ + "ROOT") {
        		parentMenu = {  id: this.id_ + "ROOT", menuitems: [] };
        		this.menus_[parentMenu.id] = parentMenu;
        	}
        	else if (!parentMenu) {
        		console.debug("ibm.tivoli.tip.dijit.TIPContextMenuProvider.addMenu: Unknown parentMenuId: " + parentMenuId);
        		return;
        	}
        	
        	var menuitem = { id: "menu_" + menu.id, submenuId: menu.id, label: menu.label, iconClass: menu.iconClass, parentMenuId: parentMenuId };
        	parentMenu.menuitems.push(menuitem);
			this.menus_[menu.id] = menu;
		},
		
		getMenus: function() {
			return this.menus_;
		},
				
        addItem: function(/*dijit:MenuItem */ item, /*String*/parentMenuId) {
        	parentMenuId = (parentMenuId) ? parentMenuId : this.id_ + "ROOT";
        	var parentMenu = this.menus_[parentMenuId];
        	if (!parentMenu && parentMenuId == this.id_ + "ROOT") {
        		parentMenu = {  id: this.id_ + "ROOT", menuitems: [] };
        	}
        	else if (!parentMenu) {
        		console.debug("ibm.tivoli.tip.dijit.TIPContextMenuProvider.addItem: Unknown parentMenuId: " + parentMenuId);
        		return;
        	}
        	
        	parentMenu.menuitems.push(item);
        	this.menus_[parentMenuId] = parentMenu;
//        	this.items_.push (item);
        },

        getItems: function(/*String*/parentMenuId) {
        	var menuitems = [];
        	parentMenuId = (parentMenuId) ? parentMenuId : this.id_ + "ROOT";
        	var parentMenu = this.menus_[parentMenuId];
        	if (parentMenu) {
        		menuitems = parentMenu.menuitems;
        	}
        	return menuitems;
//        	return (this.items_);
        },
        getItem: function(/*String*/ id) {
        	for (x in this.menus_) {
        		if (x) {
	        		var menuitems = this.menus_[x].menuitems;
		        	for (var i=0; i<menuitems.length; i++) {
	    	    		if (menuitems[i].id == id) {
							return (menuitems[i]);
	        			}
	        		}
	        	}
	        }
        },
        getItemWithParent: function(/*String*/ id, /*String*/parentMenuId) {
        	var menuitems = this.getItems(parentMenuId);
        	for (var i=0; i<menuitems.length; i++) {
        		if (menuitems[i].id == id) {
					return (menuitems[i]);
        		}
        	}
        },
        removeItem: function (/*String*/ id) {
        	loop:
        	for (x in this.menus_) {
        		if (x) {
	        		var menuItems = this.menus_[x].menuitems;
		        	for (var i=0; i<menuitems.length; i++) {
	    	    		if (menuitems[i].id == id) {
		        			// remove item i
	    	    			menuitems = menuitems.slice(0, i-1).concat (menuitems.slice(i));
	    	    			break loop;
    	    			}
	        		}
        		}
        	}
        },
        removeItemWithParent: function (/*String*/ id, /*String*/parentMenuId) {
        	var menuitems = this.getItems(parentMenuId);
        	for (var i=0; i<menuitems.length; i++) {
        		if (menuitems[i].id == id) {
        			// remove item i
        			menuitems = menuitems.slice(0, i-1).concat (menuitems.slice(i));
        		}
        	}
        },
	    setEnabled: function (/*String*/ id, /*boolean*/ enabled) {
        	loop:
        	for (x in this.menus_) {
        		if (x) {
	        		var menuItems = this.menus_[x].menuitems;
			       	for (var i=0; i<menuitems.length; i++) {
	        			if (menuitems[i].id == id) {
	        				menuitems[i].enabled = enabled;
	    	    			break loop;
	        			}
	        		}
	        	}
        	}
    	},
	    setEnabled: function (/*String*/ id, /*boolean*/ enabled, /*String*/parentMenuId) {
        	var menuitems = this.getItems(parentMenuId);
        	for (var i=0; i<menuitems.length; i++) {
        		if (menuitems[i].id == id) {
        			menuitems[i].enabled = enabled;
        		}
        	}
    	},
	    isEnabled: function (/*String*/ id) {
        	for (x in this.menus_) {
        		if (x) {
	        		var menuItems = this.menus_[x].menuitems;
    	    		for (var i=0; i<menuitems.length; i++) {
	        			if (menuitems[i].id == id) {
    		    			return menuitems[i].enabled;
	    	    		}
	        		}
        		}
        	}
    	},
	    isEnabled: function (/*String*/ id, /*String*/parentMenuId) {
        	var menuitems = this.getItems(parentMenuId);
        	for (var i=0; i<menuitems.length; i++) {
        		if (menuitems[i].id == id) {
        			return menuitems[i].enabled;
        		}
        	}
    	},
        onContextMenuOpen: function () {
        },
        destroy: function() {
            // summary: Destructor
        },
		clear: function() {
			this.menus_ = [];
		},
		update: function(/* JSON */context, /* Function */callback) {
   			this.clear();
		},
   		recursivelyAddMenuItems: function( menuItems ) {
   			console.info ("enter recursivelyAddMenuItems");
   			console.info (menuItems);
		     var newMenu = null;
     
		     for ( var i = 0; i < menuItems.length; i++ )
		     {
		     	if ( menuItems[i].submenu ) {
		    	    newMenu = {    id: menuItems[i].id, 
	    	                    label: menuItems[i].label,
	    	                     iconClass: menuItems[i].iconClass, 
   	                     parentMenuId: menuItems[i].parentMenuId ? menuItems[i].parentMenuId : this.id_ + "ROOT",
    		                menuitems:[] };
     	     		    	
					this.addMenu( newMenu, (menuItems[i].parentMenuId) ? menuItems[i].parentMenuId : this.id_ + "ROOT" );
					
					this.recursivelyAddMenuItems( menuItems[i].submenu_items );
		     	} else {
		       		this.addItem ( menuItems[i], (menuItems[i].parentMenuId) ? menuItems[i].parentMenuId : this.id_ + "ROOT" );
		     	}
		     }
	   },
	   getMenuCommand: function (commandID, selectedData) {
	   		//alert ("running command: " + commandID + " with selectedData: " + selectedData);
	   		var item = this.getItem (commandID);
	   		this.handleMenuAction (item);
	   },
   handleMenuAction: function (data, ioArgs) {
   		console.info ("handleMenuAction: ");
   		console.info (data);
		if (data.objectType === "serverException") {
			this.handleServerException (data);
		}

      	if (data.launchType == "JavaScript") {
      		console.info ("calling handleJavascript");
			this.handleJavascript (data);
       	} else if (data.launchType == "WEB_URL") {
			this.launchURL (data);
      	} else if (data.launchType == "CLIENT_EVENT") {
			this.sendEvent (data);
      	} else if (data.launchType == "PORTLET") {
      		this.launchPortlet (data);
      	} else if (data.launchType == "PORTAL_PAGE") {
      		this.launchPage(data);
      	} else {
      		console.debug ("unsupported launchtype: " + data.launchType);
      	}
   },
   handleJavascript: function (data) {
   		console.info ("enter handleJavascript");
     	var fnc = new Function ("eval (\"" + data.uri + "\");");
     	fnc();
   },
   launchURL: function(data) {
       	window.open (data.uri);
   },
   launchPortlet: function (data) {
      		console.info ("enter launchPortlet");
      		var portletEvent = {
				name: "http://ibm.com/isclite#launchPortlet",
				portletName: data.portletName,
				portletApplicationID: data.portletAppUID
			};
			if (data.parameters) {
	      		for (var j=0; j<data.parameters.length; j++) {
					eval ("portletEvent." + data.parameters[j].name + "= '" + data.parameters[j].value + "'");
	      		}
      		}
//      		alert ("about to send portlet event: " + portletEvent.portletName);
      		try {
//      			console.log ("about to send event with iscnamespace: " + this.iscPortletNamespace);
				EventBroker.sendEvent (this.iscPortletNamespace, portletEvent);
			} catch (e) {
				console.error ("Caught javascript exception in TIPContextMenuProvider.launchPortlet: " + e);
				alert (e);
			}
//     		alert ("sent event: " + portletEvent.name);
   },
   launchPage: function (data) {
      		var pageEvent = {
				name: "http://ibm.com/isclite#launchPage",
				NavigationNode: data.portletPageID,
				pageInstanceRef: null,
				switchPage: true
			};
			if (data.parameters) {
	      		for (var k=0; k<data.parameters.length; k++) {
					eval ("pageEvent." + data.parameters[k].name + "= '" + data.parameters[k].value + "'");
	      		}
      		}
//      		alert ("about to send page event: " + pageEvent.NavigationNode);
      		try {
				EventBroker.sendEvent (this.iscPortletNamespace, pageEvent);
			} catch (ex) {
				console.error ("Caught javascript exception in TIPContextMenuProvider.launchPage: " + ex);
				alert (ex);
			}
//      		alert ("sent event: " + pageEvent.name);
   
   },
   
   // Example
   /* {"applicationURLAsRegistered":null,"appID":"com.ibm.sendeventportlet.SendEventPortlet.4318727441"

,"parameters":[{"type":2,"value":"managed_system_name=Primary:LOCO:NT","name":"srcToken"},{"type":2,"value"

:"ibm-cdm:\/\/\/CDMMSS\/Hostname=loco.raleigh.ibm.com,ManufacturerName=IBM,ProductName=IBM Tivoli Monitoring"

,"name":"mssName"}],"portletAppUID":"com.ibm.sendeventportlet.SendEventPortlet.4318727441","description"

:"Send Node Selected Event Description","event":"http:\/\/tivoli.ibm.com\/tip#nodeSelectedEvent","portletReuse"

:null,"browserWindowID":null,"launchType":"CLIENT_EVENT","id":"ibm_tivoli_tip_dijit_TIPContextMenuProxy_0com

.ibm.tivoli.itm.menu5","uri":null,"isSeparator":false,"label":"Send Node Selected Event","portletPageID"

:null,"ordinal":3,"portletName":"SendEventPortlet","enabled":"true","icon":""} */
   sendEvent: function (data) {
      		var parms = data.parameters;
	        var event = {
                name: data.event
    	    };
			if (data.parameters) {
	      		for (var i=0; i<parms.length; i++) {
					eval ("event." + parms[i].name + "= '" + parms[i].value + "'");
	      		}
	      	}
      		
//      		alert ("about to send event: " + event.name);
			EventBroker.sendEvent (this.iscPortletNamespace, event);
//      		alert ("sent event: " + event.name);
   },
   destroy: function () {
   	console.log ("enter TIPContextMenuProvider.destroy");
   	this.inherited (arguments);
   	console.log ("exit TIPContextMenuProvider.destroy");
   }
	   
});

dojo.declare("ibm.tivoli.tip.dijit.CMSMenuProvider", ibm.tivoli.tip.dijit.TIPContextMenuProvider, 
{
	getMenuItemsCallback_ : null,
	global: null,
	rcfItems: null, 
	menuName: null, 
	parentid: null, 

	constructor: function () {
		this.id_ = "1_";
	    this.messages_ = dojo.i18n.getLocalization("ibm.tivoli.tip.dijit", "messages");
	},
	
	setParentId: function (parentid) {
		this.parentid = parentid;
	},
	
	setResourceContextFilters: function (rcfItems) {
		this.rcfItems = rcfItems;
	},
	
	getRCFURL: function (kw) {
		if (this.rcfItems) {
			for (var i in this.rcfItems) {
				if (i) {
					for (var name in this.rcfItems[i]) {
						if (name) {
					        kw.content["rcf_" + name]=this.rcfItems[i][name];
						}
					}
				}
			}
		}	
	},

	initialize: function(id, getMenuItemsCallback, getSelectedData) {
	  	var kw = null;
		this.id_ = id;
		this.getMenuItemsCallback_ = getMenuItemsCallback;
		
  	    // We use this context if we do have a Global
        kw = {
                content:   {
                              command: "getMenuItems",
                              arg_idPrefix: this.id_,
                              arg_portletNamespace: this.global.namespace,
                              arg_parentId: this.parentid
                           },
                onResponse:{
                              callback: dojo.hitch(this, this.onGetMenuItemsCallback)
                           }
             };

   		this.dataToURL (getSelectedData(),kw);
		
		var url = null;
	    
        this.getRCFURL(kw);
			
		if (this.menuName) {
	        kw.content.arg_menuName = this.menuName;
		}
		
        ibm.tivoli.tip.dojo.Server.getSingleton( this.global ).get( kw );
	},
	
	handleError: function (response, ioArgs) {
		console.error ("HTTP status code: ", ioArgs.xhr.status);
		return response;
	},
	
	handleTimeout: function (response, ioArgs) {
		console.error ("Timeout for request: " + ioArgs.xhr);
		return response;
	},
	handleServerException: function (exception) {
		console.error ("Received server exception: " + exception.messageID + ": " + exception.messageString);
		console.error ("with server stack trace: ");
		console.error (exception.stackTrace);
		this.showMessageDialog (exception.type, exception.messageID, exception.messageString);
	},
	showMessageDialog: function (type, msgID, msg) {
		if (msg === null) {
			msg = this.messages_.TIPUI1001E;
			msgID = "TIPUI1001E";
		}
	    var modalWindow = new ibm.tivoli.tip.dijit.TIPMessageDialog (
		                                            { 
		                                              type: type,
	    	                                          message: msg,
	        	                                      messageId: msgID,
	        	                                      showCheckbox: false
	                    	                        }); 
		modalWindow.show();
	},
    onGetMenuItemsCallback: function(data, ioArgs) {
		if (data.objectType === "serverException") {
			this.handleServerException(data);
		} else {
	  	    this.recursivelyAddMenuItems( data );		
			if ( this.getMenuItemsCallback_ !== null) {
				 this.getMenuItemsCallback_();
			}
		}
   },
   


//    onGetMenuItemsCallback: function(type,data,evt) {
//        var items = data;
//        for (var i=0; i<items.length; i++) {
//      		this.addItem (items[i]);
//        }
//
//		if (this.getMenuItemsCallback_ !== null) {
//			this.getMenuItemsCallback_();
//		}
//   },

   onContextMenuOpen: function() {
//    	onContextMenuOpenCallback();
   },   
   destroy: function() {
            // summary: Destructor
   },
   getMenuCommand: function (commandName, selectedData) {
        var kw = {
                    content:   {
                                  command: "getMenuCommand",
                                  arg_menuCommandName: commandName,
                                  arg_idPrefix: this.id_,
                                  arg_portletNamespace: this.global.namespace,
                                  arg_parentId: this.parentid
                               },
                    onResponse:{
                                  callback: dojo.hitch(this, this.handleMenuAction)
                               }
                 };
                 
   		this.dataToURL (selectedData,kw);
   		this.getRCFURL(kw);
   		
   		if (this.menuName) {
   		    kw.content.arg_menuName = this.menuName;
		}
		
        ibm.tivoli.tip.dojo.Server.getSingleton( this.global ).get( kw );
   },
   
   getIdPrefix: function() {
       return (this.id_);
   },
   
   dataToURL: function (selectedData, kw) {
   		var columns = selectedData.columns;
   		var rows = selectedData.rows;
   		var row = null;
   		if (this.isArray (rows)) {
	   		row = rows[0];
	   	} else {
	   		row = rows;
	   	}
   		
   		for (var i=0; i<columns.length; i++) {
   			var rowField = columns[i].field;
//   			var value = eval ("row." + rowField);
			var value = row[columns[i].field];
			if (!value) {
				value = "";
			}
			if (columns[i].isNamingAttribute === "true") {
			    kw.content["rn_"+columns[i].field]=value;
			} else {
	            kw.content["sv_"+columns[i].field]=value;
	   		}
   		}
   		
   		if (row.id) {
	        kw.content.arg_rowId = row.id;
   		}
   },
   isArray: function (obj) {
	   if (obj.constructor.toString().indexOf("Array") == -1) {
	      return false;
	   } else {
	      return true;   	
	   }
   },
   getMenuItemsSample: function() {
  	// Example menu items JSON
	//   [{"applicationURLAsRegistered":null,"appID":"com.ibm.sendeventportlet.SendEventPortlet.4318727441","submenu_items" :[{"applicationURLAsRegistered":null,"appID":"com.ibm.sendeventportlet.SendEventPortlet.4318727441","portletAppUID":null,"description":"","event":null,"portletReuse":null,"browserWindowID":null,"launchType":"WEB_URL","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.menu1","uri":null,"isSeparator":"false","label":"Launch TEP (sourceToken)","portletPageID":null,"ordinal":1,"parentMenuId":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.launchMenu","enabled":"true","iconClass":""},
	//    {"applicationURLAsRegistered":null,"appID":"com.ibm.sendeventportlet.SendEventPortlet.4318727441","portletAppUID":null,"description":"","event":null,"portletReuse":null,"browserWindowID":null,"launchType":"WEB_URL","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.menu2","uri":null,"isSeparator":"false","label":"Launch TEP (DotNotation)","portletPageID":null,"ordinal":2,"parentMenuId":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.launchMenu","enabled":"true","icon":""},
	//    {"applicationURLAsRegistered":null,"appID":"com.ibm.sendeventportlet.SendEventPortlet.4318727441","portletAppUID":null,"description":"","event":null,"portletReuse":null,"browserWindowID":null,"launchType":"WEB_URL","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.menu3","uri":null,"isSeparator":"false","label":"Launch TEP (Server_Name)","portletPageID":null,"ordinal":3,"parentMenuId":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.launchMenu","enabled":"true","icon":""},
	//    {"applicationURLAsRegistered":null,"appID":"com.ibm.sendeventportlet.SendEventPortlet.4318727441","portletAppUID":null,"description":"","event":null,"portletReuse":null,"browserWindowID":null,"launchType":"WEB_URL","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.menu4","uri":null,"isSeparator":"false","label":"Launch TEP (ManagedSystemName)","portletPageID":null,"ordinal":3,"parentMenuId":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.launchMenu","enabled":"true","icon":""}],
	//    "portletAppUID":null,"description":"","event":null,"portletReuse":null,"browserWindowID":null,"launchType":"","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.launchMenu","uri":null,"submenu":"true","isSeparator":"false","label":"com.ibm.tivoli.itm.launchMenuDisplay","portletPageID":null,"ordinal":0,"enabled":"true","icon":""},
	//    {"applicationURLAsRegistered":null,"appID":"com.ibm.sendeventportlet.SendEventPortlet.4318727441","portletAppUID":null,"description":"","event":null,"portletReuse":null,"browserWindowID":null,"launchType":"CLIENT_EVENT","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.menu5","uri":null,"isSeparator":"false","label":"Send Node Selected Event","portletPageID":null,"ordinal":3,"enabled":"true","icon":""},
	//    {"applicationURLAsRegistered":null,"appID":"com.ibm.sendeventportlet.SendEventPortlet.4318727441","portletAppUID":null,"description":"","event":null,"portletReuse":null,"browserWindowID":null,"launchType":"PORTAL_PAGE","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.menu7","uri":null,"isSeparator":"false","label":"Launch Sample Page","portletPageID":null,"ordinal":3,"enabled":"true","icon":""},
	//    {"applicationURLAsRegistered":null,"appID":"com.ibm.sendeventportlet.SendEventPortlet.4318727441","portletAppUID":null,"description":"","event":null,"portletReuse":null,"browserWindowID":null,"launchType":"PORTLET","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0com.ibm.tivoli.itm.menu6","uri":null,"isSeparator":"false","label":"Launch Sample Portlet","portletPageID":null,"ordinal":6,"enabled":"true","icon":""},{"id":"Separator"},{"label":"Print Preview","icon":"/ibm/TIPChartPortlet/images/toggle_window_16.gif","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0PrintPreviewTable"},
	//    {"label":"Save to File","icon":"/ibm/TIPChartPortlet/images/toggle_window_16.gif","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0SaveTableAsFile"},{"label":"Show/Hide Toolbar","icon":"/ibm/TIPChartPortlet/images/toggle_window_16.gif","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0ToggleToolbar"},{"id":"Separator"},{"label":"Refresh","icon":"/ibm/TIPChartPortlet/images/refresh_enabled.gif","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0Refresh"},{"label":"Properties","icon":"/ibm/TIPChartPortlet/images/preferences.gif","id":"ibm.tivoli.tip.dojo_TIPContextMenu_0Properties"}]
   
		var menuItems = [
			{ id: "Properties", label: "Properties", iconClass: "", launchType: "External", uri: "http://www.google.com", enabled: true },
			{ id: "Refresh", label: "Refresh", iconClass: "", launchType: "JavaScript", uri: "alert ('clicked refresh');", enabled: false }
		];
		
		return (menuItems);
   }
});


dojo.declare("ibm.tivoli.tip.dijit.TIPPopupManager", dijit.PopupManager, 
{
	constructor: function () {
		this.registerWin = function(/*Window*/win) {
		// summary: 
		//		Override the parent to only register a window so that when clicks in it, the popup 
		//		can be closed automatically.  The parent closed when the user clicked on the scroll,
		//		which is problemmatic if the context menu is displayed outside of the scroll area.
			if(!win.__PopupManagerRegistered) {
				dojo.connect(win.document, 'onmousedown', this, 'onClick');
	//			dojo.connect(win, "onscroll", this, "onClick");
				dojo.connect(win.document, "onkey", this, 'onKey');
				win.__PopupManagerRegistered = true;
				this.registeredWindows.push(win);
			}
		};

		this.unRegisterWin = function(/*Window*/win){
			// summary: 
			//		remove listeners on the registered window
			if(win.__PopupManagerRegistered) {
				dojo.disconnect(win.document, 'onmousedown', this, 'onClick');
	//			dojo.disconnect(win, "onscroll", this, "onClick");
				dojo.disconnect(win.document, "onkey", this, 'onKey');
				win.__PopupManagerRegistered = false;
			}
		};
	}
});

