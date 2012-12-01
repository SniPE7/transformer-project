/*********************************************************** {COPYRIGHT-TOP} ***
* Licensed Materials - Property of IBM
* 5724-Q87 
*
* (C) Copyright IBM Corp. 2008
*
* US Government Users Restricted Rights - Use, duplication, or
* disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
************************************************************ {COPYRIGHT-END} **/
dojo.require( "dijit.Menu" );

dojo.provide( "ibm.tivoli.tip.dijit.ContextMenuAdapter" );
/*******************************************************************************
* ContextMenuAdapter is responsible for constructing and displaying dynamic
* context menus.  That is it will ask for the items that should be displayed
* each time it is told to present the context menu. 
*
* It can handle calling a JSON Url to retreive the items for you.
*
* It also has a method that can be overloaded to provide context to the code
* building the menu. (For instance -- the row of a table that is selected)
*
* The menu model is as follows:
* 
* [ { 
*     id: "uniqueID", 
*     label: "label", 
*     iconClass: "iconClass", 
*     submenu_items: [ {},{} ] 
*     actionID: "actionToFire" 
    } 
* ... ]
*
* id, iconClass, and submenu_items are all optional
*
* NOTE:
* ! IF "_separator_" is passed as actionID then a menu separator is created !
*******************************************************************************/
dojo.declare( "ibm.tivoli.tip.dijit.ContextMenuAdapter", null,
{
   /* [Optional] The JSON Url to request context menu items from */
   url: null,
   
   /* [Optional] The unique name of the context menu -- for lookup of items */
   name: null,
   
   /****************************************************************************
   * The constructor of the adapter.  Mixes in parameters.
   ****************************************************************************/
   constructor: function( args )
   {
     dojo.mixin( this, args );
     
     this.menuItems = [];
     this.subMenus = [];
   },
      
   /****************************************************************************
   * Ensures that only one context menu is used during the life of this adapter.
   * It destroys any previously created menus and recreates a new menu.
   ****************************************************************************/
   createContextMenu: function()
   {
     this._menu = new dijit.Menu();
     
     this._onCloseListener = dojo.connect( this._menu, "onClose", this, "_destroyMenu" );
     
     //populate the menu
     this.populateMenu( this._menu ); 
     
     return this._menu;         
   },
   
   /****************************************************************************
   * When the menu closes -- cleanly destroy it.  There is a bug in Dojo that
   * requires us to find all orphaned pieces of the menu to cleanly destroy
   ****************************************************************************/   
   _destroyMenu: function()
   {
     dojo.disconnect( this._onCloseListener );
     
     delete this._onCloseListener;

	 for ( var i in this.menuItems )
	 {
	   if (i) {
	 	this.menuItems[ i ].destroy();
	   }
	 }
     
	 for ( i in this.subMenus )
	 {
	   if (i) {
	 	this.subMenus[ i ].destroy();
	   }
	 }

     this._menu.destroyRecursive();

     dojo.query( ".dijitPopup" ).forEach( function( domNode ) { dojo._destroyElement( domNode ); } );       
     
     delete this._menu;            
   },
   
   /****************************************************************************
   * This method must be called with a mouse event to show the Context menu.
   * You could pass in an object with x:xPos and y:yPos for event to trick the 
   * context menu to open at any given point on screen.
   *
   * The context object is any object that you want to send back to the server
   * or pass to the getMenuItems method.
   ****************************************************************************/
   contextMenuOpen: function ( event, context )
   {
      this._context = context;
      
      var menu = this.createContextMenu();

      menu._contextMenuWithMouse = true;
      menu._openMyself( event ); 
   },
   
   /****************************************************************************
   * Asks the JSON URL or callback method for the menu items then recursively
   * builds the menu.
   ****************************************************************************/   
   populateMenu: function( menu )
   {
     var model = null;
     
     if ( this.url !== null )
     { 
       model = this._loadMenuItems(); 
     }
     else
     {
       model = this.getMenuItems( this._context );
     }
     
     if ( model !== undefined && model !== null )
     {
       this._addItemsToMenu( menu, model );
       
       menu.startup();
     }               
   },
   
   /****************************************************************************
   * Recursive function to construct menu from array of items
   ****************************************************************************/
   _addItemsToMenu:function( parent, items )
   {
      var params;
      var subMenuIndex;
      var menuItemIndex;
      
      for ( var i in items )
      {
         if ( items[i].submenu_items )
         {
           subMenuIndex = this.subMenus.length;
           this.subMenus[ subMenuIndex ] = new dijit.Menu();
 
           params = 
           {
               popup: this.subMenus[ subMenuIndex ]
           };

           dojo.mixin( params, items[i] );
        
           parent.addChild( new dijit.PopupMenuItem( params ) );  
           
           this._addItemsToMenu( this.subMenus[ subMenuIndex ], items[i].submenu_items );           
         }
         else
         {
           // [2/15/08] The following if clause added for backward compatibility
           // with the previous version of TIPContextMenu
           if (items[i].id && items[i].id.toLowerCase() === "separator")
           {
              // Previous TIPContextMenu users indicated separators via the id
              items[i].actionID = "_separator_";
           } 
           else if (items[i].id && !items[i].actionID) 
           {
              // Previous TIPContextMenu users didn't use actionID
              items[i].actionID = items[i].id;
           }

           if ( items[i].actionID === "_separator_" )
           {
             menuItemIndex = this.menuItems.length;
             
             this.menuItems[ menuItemIndex ]  = new dijit.MenuSeparator();
             
             parent.addChild( this.menuItems[ menuItemIndex ] );
           }
           else
           {
             params = {
               adapter: this,
               onClick: function() { this.adapter.onMenuItemClick( this ); }
             };
             
             dojo.mixin( params, items[i] );

             menuItemIndex = this.menuItems.length;
             
             this.menuItems[ menuItemIndex ] = new dijit.MenuItem( params );
             
             parent.addChild( this.menuItems[ menuItemIndex ] );  
           }
         }         
      }
   },
      
   /****************************************************************************
   * Callback that should be overloaded to be told when a menu item is clicked
   * and an action should occur
   ****************************************************************************/
   onMenuItemClick: function( menuItem ){ /*required overload*/ },
   
   /****************************************************************************
   * Synchronously Call a JSON Url to get the menu items
   *
   * Calls the provided url with the following:
   *    name: this.name (unique name of context menu to retrieve)
   *    content: this._context (the context object passed to contextMenuOpen)
   *
   * Theoretically this method could be overloaded to do something other than
   * dojo.xhrPost()
   ****************************************************************************/
   _loadMenuItems: function()
   {
     var kw = {
            sync:         true,
            url:          this.url,
            name:         this.name,
            content:      this._context,
            handleAs:     "json",
            mimetype:     "application/json",            
            load:         dojo.hitch( this, function( response ) { this._menuitems = response; } )       
          };
     
     //call synchronously                                                                       
     dojo.xhrGet( kw );
     
     return this._menuitems;
   },  

   /****************************************************************************
   * This method will be called if 'url' parameter is not set.  Provides a means
   * for javascript to build the menu items.
   ****************************************************************************/
   getMenuItems: function( context ){ /* must overload to provide menu items if URL is not used for JSON */ }
   
});
