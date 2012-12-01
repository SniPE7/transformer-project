/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
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
dojo.require("ibm.tivoli.tip.dijit.ContextMenuAdapter");
dojo.provide("ibm.tivoli.tip.dijit.TIPContextMenuV2");
dojo.provide("ibm.tivoli.tip.dijit.TIPContextMenuProviderV2");
dojo.provide("ibm.tivoli.tip.dijit.CMSMenuProviderV2");
dojo.provide("ibm.tivoli.tip.dijit.TIPContextMenuProxyV2");


/**
 *  Provides dynamic context menu support.
 *  How do you use this?
 *  First, you create an instance of this context menu similar to:
 *     this._contextMenuAdapter = new ibm.tivoli.tip.dijit.TIPContextMenuV2({cmProvider: this.cmProvider});
 *  Note: you pass in an instance of a context menu provider (see objects lower in this file)
 *
 *  Second, you latch whatever event you want to create the context menu.  For 
 *  Grids, you might do something like this:
 *     this._onRowContextMenuListener = dojo.connect( this.grid, "onRowContextMenu", this, "_onRowContextMenu" );
 *  Don't forget to disconnect this listener when your object is destroyed.
 *
 *  Third, you create the method that gets called by the above connect, and call
 *  the contextMenuOpen on this context menu with whatever context you want 
 *  processed.  Again, in a Grid example:
 *   _onRowContextMenu: function( e )
 *   { 
 *     var row = e.grid.model.getRow( e.rowIndex );
 *     
 *     if ( row )
 *     {
 *       //select the row that the context menu was initiated on
 *       e.grid.selection.unselectAll( e.rowIndex );
 *       e.grid.selection.setSelected( e.rowIndex, true );
 *               
 *       //tell the context menu adapter to show the menu
 *       // no context is provided because we overload the _loadMenuItems method
 *       this._contextMenuAdapter.contextMenuOpen( e, {selectedData: this.getSelectionData()} );
 *     }
 *   },
 *
 *  
 */
dojo.declare("ibm.tivoli.tip.dijit.TIPContextMenuV2",
              ibm.tivoli.tip.dijit.ContextMenuAdapter,
{
   cmProvider: null,       // TIPContextMenuProvider
   url: "no_url_needed",   // Note we have to pass something to ContextMenuAdapter
                           // for the url so that we can override _loadMenuItems
   
   /****************************************************************************
   * Callback that should be overloaded to be told when a menu item is clicked
   * and an action should occur
   ****************************************************************************/
   onMenuItemClick: function( menuItem )
   { 
      // console.debug("-------------- menuItem :",menuItem);
      this.cmProvider.getMenuCommand(menuItem.actionID, this._context, menuItem);
   },

   /****************************************************************************
   * Synchronously Call a JSON Url to get the menu items
   *
   ****************************************************************************/
   _loadMenuItems: function()
   {
     return this.cmProvider.getMenuItems( this._context );
   }

});

/**
 *  What is this?
 *  This is a helper for the TIPContextMenu.  It allows you pass in DOM nodes 
 *  and it will set up the context menu action for those nodes.  You must call 
 *  destroy on this object when you are done with it.  You provide the following
 *  when you create it:
 *   - targetNodeIds (required): these are string IDs for the DOM nodes you want 
 *                               context menus on
 *   - getContextCallback (optional): this is a function that will return any 
 *                                    context that you want passed along on the 
 *                                    context menu open call.  It will get 
 *                                    passed to your provider.
 */
dojo.declare("ibm.tivoli.tip.dijit.TIPContextMenuProxyV2",
              ibm.tivoli.tip.dijit.TIPContextMenuV2,
{

   targetNodeIds: null,

   getContextCallback: null,
   
   _bindings: null,
   
   constructor: function() 
   {
      this._bindings = [];
      dojo.forEach(this.targetNodeIds, dojo.hitch(this, this.bindDomNode));
   },

   // Called when it is time to notify the user that something was clicked.
   _openContextMenu: function(event)
   {
      var result = null;
      if (this.getContextCallback !== null) 
      {
         result = this.getContextCallback(event);
      }
      this.contextMenuOpen( event, result );
   },
   
   // This method mostly copied from dijit.Menu to provide the binding to the 
   // DOM node.  I needed to copy it so that I could properly inject the 
   // contextMenuAdapter.  I'll note changes with <kjp> tags
	bindDomNode: function(/*String|DomNode*/ node){
		// summary: attach menu to given node
		node = dojo.byId(node);

		//TODO: this is to support context popups in Editor.  Maybe this shouldn't be in dijit.Menu
		var win = dijit.getDocumentWindow(node.ownerDocument);
		if(node.tagName.toLowerCase()=="iframe"){
			win = this._iframeContentWindow(node);
			node = dojo.withGlobal(win, dojo.body);
		}

		// to capture these events at the top level,
		// attach to document, not body
		var cn = (node == dojo.body() ? dojo.doc : node);

		node[this.id] = this._bindings.push([
         // <kjp> Changed this next line to point to my _openContextMenu.
         // The line previously pointed to _openMyself
			dojo.connect(cn, "oncontextmenu", this, "_openContextMenu"),
			dojo.connect(cn, "onkeydown", this, "_contextKey")
         // <kjp> I removed the next line (and removed the comma from the previous)
			//dojo.connect(cn, "onmousedown", this, "_contextMouse")
		]);
	},

   // This is a complete copy from dijit.Menu
	unBindDomNode: function(/*String|DomNode*/ nodeName){
		// summary: detach menu from given node
		var node = dojo.byId(nodeName);
		var bid = node[this.id]-1, b = this._bindings[bid];
		dojo.forEach(b, dojo.disconnect);
		delete this._bindings[bid];
	},
   
   // This method mostly copied from dijit.Menu to provide the binding to the 
   // DOM node.  I needed to copy it so that I could properly inject the 
   // contextMenuAdapter.  I'll note changes with <kjp> tags
	_contextKey: function(e){
		this._contextMenuWithMouse = false;
		if (e.keyCode == dojo.keys.F10) {
			dojo.stopEvent(e);
			if (e.shiftKey && e.type=="keydown") {
				// FF: copying the wrong property from e will cause the system
				// context menu to appear in spite of stopEvent. Don't know
				// exactly which properties cause this effect.
				var _e = { target: e.target, pageX: e.pageX, pageY: e.pageY };
				_e.preventDefault = _e.stopPropagation = function(){};
				// IE: without the delay, focus work in "open" causes the system
				// context menu to appear in spite of stopEvent.
            //<kjp> I changed the following line from calling this_openMyself to this._openContextMenu
				window.setTimeout(dojo.hitch(this, function(){ this._openContextMenu(_e); }), 1);
			}
		}
	},

   destroy: function()
   {
   	console.log ("enter TIPContextMenuV2.destroy");
      dojo.forEach(this.targetNodeIds, dojo.hitch(this, this.unBindDomNode));
      if (this.cmProvider !== null)
      {
         this.cmProvider.destroy();
         this.cmProvider = null;
      }
   }
});


/**
 *  You provide a context menu provider to do things like populate the context menu with
 *  items, and to handle the actions when one of those items are clicked.
 *  You provide:
 *  - getMenuItemsCallback (required)   (optional alternative: override getMenuItems)
 *      Parameters: (context)
 *                 context (that was passed on the contextMenuOpen call (in your code)
 *
 *      Summary: This callback provides the menu items to be inserted into the context menu.          
 *      The following defines the callback that is used to return menu items to appear
 *      in the list.  This method should return JSON data in the form:
 *       [ 
 *       { 
 *            "label": "testAction",
 *            "iconClass": "tipMenuIcon tipPropertiesIcon", 
 *            "actionID": "testActionID" 
 *       },
 *       { 
 *            "actionID": "_separator_" 
 *       },
 *       { 
 *            "label": "submenu", 
 *            "iconClass": "tipMenuIcon", 
 *            "submenu_items": 
 *            [ 
 *             { 
 *                  "label": "Delete", 
 *                 "iconClass": "tipMenuIcon tipDeleteIcon", 
 *                 "actionID": "testDeleteActionID" 
 *             }
 *            ] 
 *       } 
 *       ]
 *
 *  - getMenuCommandCallback (optional)  (optional alternative: override getMenuCommand)
 *      Parameters: (commandName, context, menuItem)
 *                 commandName - the command name of the context menu item.  Provided on the
 *                               'actionID' item above. 
 *                 context - (that was passed on the contextMenuOpen call (in your code)
 *                 menuItem - the actual menu item object.
 *      This callback provides the function needed to process a menu item click.
 *      The response depends largely on the action you wish to take.  A sample response:
 *
 *      {
 *            "launchType":"JavaScript",
 *            "uri":"alert('Hello World');"
 *      }
 *      Launch types include: JavaScript, WEB_URL, CLIENT_EVENT, PORTLET, PORTAL_PAGE, NOOP
 *
 *      The actions listed above may be included in the original information returned by 
 *      the getMenuItemsCallback.  If these actions are included, you need not specify
 *      a getMenuCommandCallback, and the provider will process these actions for you
 *      automatically.  For example:
 *       { 
 *            "label": "testAction",
 *            "iconClass": "tipMenuIcon tipPropertiesIcon",
 *            "actionID": "testActionID", 
 *            "launchType":"JavaScript",
 *            "uri":"alert('Hello World');"
 *       },
 */
dojo.declare("ibm.tivoli.tip.dijit.TIPContextMenuProviderV2", null,
{
   iscPortletNamespace: null,

   getMenuItemsCallback: null,
   
   getMenuCommandCallback: null,

   menus_: null, // List of menus saved from getMenuItems

   constructor: function( args ) 
   {
      // summary:
      //      The base class for all providers that populate the TIPContextMenu
      dojo.mixin( this, args );

      this.id_ = "1_";
   },

   getMenuItems: function(context)
   {
      this.menus_ = this.getMenuItemsCallback(context);
      return this.menus_;
   },

   getItem: function(/*String*/ id) 
   {
      var submenus = [];
      var currentMenu = null;
      var x = null;
      submenus.push(this.menus_);
      
      currentMenu = submenus.pop();
      while (currentMenu)
      {
         x = currentMenu.pop();
         while (x)
         {
            if (x) 
            {
               if (x.submenu_items)
               {
                  submenus.push(x.submenu_items);
               }
               else
               {
                  if (x.id == id || x.actionID == id)
                  {
                     return x;
                  }
               }
            }
            x = currentMenu.pop();
         }
         currentMenu = submenus.pop();
      }
   },
   
   isArray: function (obj) 
   {
      if (obj.constructor.toString().indexOf("Array") == -1) 
      {
         return false;
      } else {
         return true;      
      }
   },

   getMenuCommand: function (commandName, context, menuItem) 
   {
      var result = null;
      if (this.getMenuCommandCallback !== null) 
      {
         result = this.getMenuCommandCallback(commandName, context, menuItem);
      }
      else
      {
         result = this.getItem(commandName);
      }

      // console.debug("------------ handleMenuAction result",result);
      this.handleMenuAction(result);
   },

   handleMenuAction: function (data, ioArgs) 
   {
      // console.info ("handleMenuAction: ");
      // console.info (data);

      if (data.launchType == "JavaScript") {
         this.handleJavascript (data);

      } else if (data.launchType == "WEB_URL") {
         this.launchURL (data);

      } else if (data.launchType == "CLIENT_EVENT") {
         this.sendEvent (data);

      } else if (data.launchType == "PORTLET") {
         this.launchPortlet (data);

      } else if (data.launchType == "PORTAL_PAGE") {
         this.launchPage(data);

      } else if (data.launchType == "NOOP") {
         // Do nothing in the case of a no operation type

      } else {
         console.error ("unsupported launchtype: " + data.launchType);
      }
   },

   handleJavascript: function (data) 
   {
      // console.log ("enter handleJavascript");
      var fnc = new Function ("eval (\"" + data.uri + "\");");
      fnc();
   },

   launchURL: function(data) 
   {
      // console.log ("enter launchURL");
      window.open (data.uri);
   },

   launchPortlet: function (data) 
   {
      // console.log ("enter launchPortlet");
      var portletEvent = {
                            name: "http://ibm.com/isclite#launchPortlet",
                            portletName: data.portletName,
                            portletApplicationID: data.portletAppUID
                         };

      if (data.parameters) 
      {
         for (var j=0; j<data.parameters.length; j++) 
         {
            eval ("portletEvent." + data.parameters[j].name + "= '" + data.parameters[j].value + "'");
         }
      }

      try 
      {
         EventBroker.sendEvent (this.iscPortletNamespace, portletEvent);
      } catch (e) {
         console.error ("Caught javascript exception in TIPContextMenuProvider.launchPortlet: " + e);
         alert (e);
      }
   },
   
   launchPage: function (data) 
   {
      var pageEvent = {
                         name: "http://ibm.com/isclite#launchPage",
                         NavigationNode: data.portletPageID,
                         pageInstanceRef: null,
                         switchPage: true
                      };

      if (data.parameters) 
      {
         for (var k=0; k<data.parameters.length; k++) 
         {
            eval ("pageEvent." + data.parameters[k].name + "= '" + data.parameters[k].value + "'");
         }
      }

      try 
      {
         EventBroker.sendEvent (this.iscPortletNamespace, pageEvent);
      } catch (ex) {
         console.error ("Caught javascript exception in TIPContextMenuProvider.launchPage: " + ex);
         alert (ex);
      }
   },
   
   sendEvent: function (data) 
   {
      var parms = data.parameters;
      var event = {
                     name: data.event
                  };

      if (data.parameters) 
      {
         for (var i=0; i<parms.length; i++) 
         {
            eval ("event." + parms[i].name + "= '" + parms[i].value + "'");
         }
      }

	  console.log ("TIPContextMenuV2: sending event: ");            
	  console.log (event);
      EventBroker.sendEvent (this.iscPortletNamespace, event);
   },

   destroy: function()
   {
      // Placeholder: for overrides.  Exploiters should call this as best practice. 
   }
      
});

/**
 *  The CMSMenuProviderV2 is a specialized menu provider for use with the 
 *  context menu service.  Certainly you may use this provider if not using CMS,
 *  but it returns extra data that is used for CMS.  The CMS provider will
 *  make requests to the server to obtain both the menu items as well as the
 *  menu commands.
 *
 *  The CMSMenuProviderV2 expects the following to be included in the context 
 *  passed to contextMenuOpen
 *  {
 *     selectedData: <the selection data>
 *  }
 */
dojo.declare("ibm.tivoli.tip.dijit.CMSMenuProviderV2", 
             ibm.tivoli.tip.dijit.TIPContextMenuProviderV2, 
{
   global: null,
   rcfItems: null, 
   menuName: null, 
   parentid: null,
   
   // When the following is true, then this provider will first look 
   // at the menu items for a launchType, and if one exists, it will
   // execute that action here instead of going back to the host.
   hasLocalActions: true, 

	/* Default timeout for requests made to the server */
	requestTimeout: 30000,

   getIdPrefix: function() 
   {
       return this.id_;
   },
   
   setParentId: function (parentid) 
   {
      this.parentid = parentid;
   },
   
   setResourceContextFilters: function (rcfItems) 
   {
      this.rcfItems = rcfItems;
   },
   
   getRCFURL: function (kw) 
   {
      if (this.rcfItems) 
      {
         for (var i in this.rcfItems) 
         {
            if (i) 
            {
               for (var name in this.rcfItems[i]) 
               {
                  if (name) 
                  {
                       kw.content["rcf_" + name]=this.rcfItems[i][name];
                  }
               }
            }
         }
      }   
   },

   getMenuItems: function(context) 
   {
      var kw = null;

      kw = {
              sync:         true,
              noBusyDialog: true,
              content:      {
                               command:              "getMenuItems",
                               arg_idPrefix:         this.getIdPrefix(),
                               arg_portletNamespace: this.global.namespace,
                               arg_parentId:         this.parentid
                            },
              timeout:      this.requestTimeout,
              onResponse:   {
                               callback: dojo.hitch( this, function( response ) { this.menus_ = response; } )
                            }
            };

      this.dataToURL (context.selectedData,kw);
      
      this.getRCFURL(kw);
         
      if (this.menuName) 
      {
         kw.content.arg_menuName = this.menuName;
      }
      
      ibm.tivoli.tip.dojo.Server.getSingleton( this.global ).get( kw );

      return this.menus_;
   },
   
   getMenuCommand: function (commandName, context, menuItem) 
   {
      var result = null;
      
      if (this.getMenuCommandCallback !== null) 
      {
         result = this.getMenuCommandCallback(commandName, context, menuItem);
         this.handleMenuAction(result);
      }
      else
      {
         this.getMenuCommandNoCallback(commandName, context, menuItem);
      }
   },

   getMenuCommandNoCallback: function (commandName, context, menuItem) 
   {
      var actionHandled = false;
      if (this.hasLocalActions === true)
      {
         // Check if the menu item contains a launchType.  If so,
         // handle it locally.
	 var result = this.getItem(commandName);
         if (result && result.launchType)
         {
             actionHandled = true;
             this.handleMenuAction(result);
         }
      }
      
      if (actionHandled === false)
      {
         this.getMenuCommandFromServer(commandName, context, menuItem);
      }
   },
   
   getMenuCommandFromServer: function (commandName, context, menuItem) 
   {
      var kw = {
                  content:   {
                                command: "getMenuCommand",
                                arg_menuCommandName: commandName,
                                arg_idPrefix: this.getIdPrefix(),
                                arg_portletNamespace: this.global.namespace,
                                arg_parentId: this.parentid
                             },
                  timeout:   this.requestTimeout,
                  onResponse:{
                                callback: dojo.hitch(this, this.handleMenuAction)
                             }
               };
               
       this.dataToURL (context.selectedData,kw);
       this.getRCFURL(kw);
       
       if (this.menuName) 
       {
          kw.content.arg_menuName = this.menuName;
       }
      
       ibm.tivoli.tip.dojo.Server.getSingleton( this.global ).get( kw );
   },   
   
   dataToURL: function (selectedData, kw) 
   {
      // if you get an exception here, it means you did not provide
      // selection data in your context.  I'll put a catch of the 
      // exception here because I bet this happens a lot.
      var columns = null; 
      try 
      {
         columns = selectedData.columns;
      } catch (ex) {
         console.error ("Caught javascript exception in cmsContextMenuProvider.dataToURL: " + ex);
         console.error ("You must provide 'selectedData' on your context.");
         alert (ex);
      }
      var rows = selectedData.rows;
      var row = null;
      if (this.isArray (rows)) {
         row = rows[0];
      } else {
         row = rows;
      }
      
      for (var i=0; i<columns.length; i++) 
      {
         var rowField = columns[i].field;
         var value = row[columns[i].field];

         if (!value) 
         {
            value = "";
         }

         if (columns[i].isNamingAttribute === "true") 
         {
            kw.content["rn_"+columns[i].field]=value;
         } else {
            kw.content["sv_"+columns[i].field]=value;
         }
      }
         
      if (row.id) 
      {
        kw.content.arg_rowId = row.id;
      }
   }
   
});

