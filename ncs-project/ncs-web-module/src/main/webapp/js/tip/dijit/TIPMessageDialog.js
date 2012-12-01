/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2007, 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/

dojo.provide("ibm.tivoli.tip.dijit.TIPMessageDialog");
dojo.require("dijit._Widget");
dojo.require("dijit.Dialog"); 
dojo.require("dijit.form.CheckBox");
dojo.require("ibm.tivoli.tip.dijit.TIPButton");
dojo.require("dojo.i18n");
dojo.require("dojox.layout.ResizeHandle");

// NLS
dojo.requireLocalization("ibm.tivoli.tip.dijit", "resources");

// define the widget class
dojo.declare(
	"ibm.tivoli.tip.dijit.TIPMessageDialog", 
    [ dijit._Widget ],
{
    // resizeAxis: String
    //  x | xy | y to limit pane's sizing direction
    resizeAxis: "xy",

	// type: String: defines the type of message dialog ("Error", "Warning", "Info", "Confirm")
	type: "Error",
	
	// portlet title associated w/ this message
	portletTitle: null,

	// message: String: the displayable translated message
	message: null,

	// messageId: String: the displayable translated message's unique identifier
	messageId: null,
	
	// width: String: gives the pixel width of the dialog (Default: 500px)
	width: "380px",
	
	// height: String: gives the pixel height of the dialog (Default: 200px)
	height: "225px",
	
	// buttons: String Array: a list of buttons to display (Default: OK)
	buttons: [],
	
	// callback: Function: a callback function when the dialog is closed
	callback: null,
	
	// parameters to pass on callback when dialog closes
	callbackParms: null,
	
	// url: String: a URL that links the message to an InfoCenter page
	url: null,
	
	// show "Never show again checkbox"
	showCheckbox: false,
	
	// Destroy the dialog on close
	destroyOnClose: true,

	constructor: function() {
		// summary: An extension to the Dojo Dialog widget that supports 
		//      advanced features.
		// resourceMessages_: JSON
		//		Resource bundle.
		// dialog_: dojo.widget.FloatingPane
		//		An instance of the Dialog widget.
		// title_: String
		//		Title bar string.
		// icon_: String
		//		The dialog icon's uri.
		// buttonPressedId_: String
		//		The ID of the button pressed; this value is passed to the callback function.

	    this.resources_ = dojo.i18n.getLocalization("ibm.tivoli.tip.dijit", "resources");
	    
	    this.dialog_ = null;

	    this.title_ = null;
	    this.icon_ = null;
	    this.buttonPressedId_ = -1;
	},


	postCreate: function() { 
	    if (this.buttons.length === 0) {
	        this.buttons.push(this.resources_.DIALOG_BUTTON_OK);
	    }

		var className = null;
	    if (this.type.toLowerCase() == "error") {
	    	if (this.portletTitle) {
		    	this.title_ = dojo.string.substitute(this.resources_.MESSAGE_TITLE, { 0: this.resources_.DIALOG_ERROR, 1: this.portletTitle });
	    	} else {
		        this.title_ = this.resources_.DIALOG_ERROR;
		    }
	        this.icon_ = dojo.moduleUrl("ibm.tivoli.tip.dijit", "themes/images/i_message_critical.png");
	        className = "critical";
	    }
	    else if (this.type.toLowerCase() == "warning") {
	    	if (this.portletTitle) {
		    	this.title_ = dojo.string.substitute(this.resources_.MESSAGE_TITLE, { 0: this.resources_.DIALOG_WARNING, 1: this.portletTitle });
	    	} else {
		        this.title_ = this.resources_.DIALOG_WARNING;
		    }
	        this.icon_ = dojo.moduleUrl("ibm.tivoli.tip.dijit", "themes/images/i_message_warning.png");
	        className = "warning";
	    }
	    else if (this.type.toLowerCase() == "confirm") {
	    	if (this.portletTitle) {
		    	this.title_ = dojo.string.substitute(this.resources_.MESSAGE_TITLE, { 0: this.resources_.DIALOG_CONFIRM, 1: this.portletTitle });
	    	} else {
		        this.title_ = this.resources_.DIALOG_CONFIRM;
		    }
	        this.icon_ = dojo.moduleUrl("ibm.tivoli.tip.dijit", "themes/images/i_message_confirm.png");
	        className = "confirm";
	    }
	    else if (this.type.toLowerCase() == "validation") {
	    	if (this.portletTitle) {
		    	this.title_ = dojo.string.substitute(this.resources_.MESSAGE_TITLE, { 0: this.resources_.DIALOG_VALIDATION, 1: this.portletTitle });
	    	} else {
		        this.title_ = this.resources_.DIALOG_VALIDATION;
		    }
	        this.icon_ = dojo.moduleUrl("ibm.tivoli.tip.dijit", "themes/images/i_message_validation.png");
	        className = "validation";
	    }
	    else { // if (this.type.toLowerCase() == "info") {
	    	if (this.portletTitle) {
		    	this.title_ = dojo.string.substitute(this.resources_.MESSAGE_TITLE, { 0: this.resources_.DIALOG_INFO, 1: this.portletTitle });
	    	} else {
		        this.title_ = this.resources_.DIALOG_INFO;
		    }
	        this.icon_ = dojo.moduleUrl("ibm.tivoli.tip.dijit", "themes/images/i_message_info.png");
	        className = "info";
	    }
	    
		var div = document.createElement('div'); 
//		div.style.width = this.width; 
//		div.style.height = this.height;
        // The following div settings are needed to ensure the dialog is seen when the bottom frames
        // are rendered due to refresh or re-submit during error scenarios (see main_ruleset.jsp).
        // Without the settings, the bottom frame was overlaying the error message. 
		//div.style.top = 0; 
		//div.style.left = 0;
		document.body.appendChild(div);

        this.dialog_ = new dijit.Dialog (
			                             { title: this.title_
		                                 },
		                                 div); 

		var dojoAttachpoint = this.dialog_.containerNode;
		dojoAttachpoint.innerHTML = this.getContent(className);

        //Build and place resize handle       
        var resizeHandleDOM = dojo.doc.createElement( 'span' );
        resizeHandleDOM.className = "dojoxFloatingResizeHandle";    
        dojo.place( resizeHandleDOM, this.dialog_.domNode, "last" );
    
        this.resizeHandle = new dojox.layout.ResizeHandle(
        { 
              targetId: this.dialog_.id, 
            resizeAxis: this.resizeAxis 
        }, resizeHandleDOM );   
	
        this.dialog_.domNode.style.width = this.width;	
        this.dialog_.domNode.style.height = this.height;	
        //this.dialog_.containerNode.style.overflow="auto";
        this.dialog_.domNode.style.overflow = "hidden";        
    
		for (var i = 0; i < this.buttons.length; i++) {
			var button = dojo.byId(this.id + "mmd_button_" + i);
			dojo.connect(button, 'onclick', this, 'onClick');    
		}
    
        this.onCloseListener  = dojo.connect( this.dialog_, "hide", this, "_onHide" );
	  this.onResizeListener = dojo.connect( this.dialog_, "resize", this, "customLayout" );    
	},
	
    /**************************************************************************
    * The dialog has been hidden.  Wait 500 miliseconds to destroy the dialog.
    *
    * HACK -- Can't figure out a better way to destroy the dialog on close.
    **************************************************************************/
    _onHide:function()
    {
      setTimeout( dojo.hitch( this, "destroy" ), 500 );
    },  
	
	onClick: function(/* HTMLEvent */e) {
		var index = this.id.length + "mmd_button_".length;
		this.buttonPressedId_ = parseInt(e.target.id.substring(index), 0);
	    console.log ("TIPMessageDialog.onClick: buttonPressedId_: " + this.buttonPressedId_);
	    var checkbox = dojo.byId (this.id + "neverShowAgain");
	    var checked = checkbox ? checkbox.checked : false;

	    this.dialog_.hide();
	    
	    if (this.callback) {
	    	this.callback(this.buttonPressedId_, this.messageId, checked, this.callbackParms);
	    }
    },

	show: function() {
		this.dialog_.show();
		this.customLayout();
		this.correctPNG();

	},

      customLayout: function() 
      {
		console.debug( dojo.byId( this.id + '_messagediv' ) );
		console.debug( dojo.contentBox( this.dialog_.domNode ) );
		
		var messageDiv = dojo.byId( this.id + '_messagediv' );
		var dialogSize = dojo.contentBox( this.dialog_.domNode );

		var titleBarSize = dojo.contentBox( this.dialog_.titleBar );

		var newSize = {w:0,h:0};

		newSize.w = dialogSize.w - 75;
		if (this.showCheckbox === true) {
			var neverShowCoords = dojo.coords (this.id + "neverShowAgain");
			newSize.h = dialogSize.h - titleBarSize.h - neverShowCoords.h - 90;
		} else {		
			newSize.h = dialogSize.h - titleBarSize.h - 90;
		}

	    dojo.contentBox( messageDiv, newSize );
	},
	
	getContent: function(className) {
		var innerHTML = "";

		innerHTML += "<table class=\"" + className + "\">";
		innerHTML += "<tr valign=\"top\">";
		innerHTML += "<td>";

		innerHTML += "<table>";
		innerHTML += "<tr>";
		innerHTML += "<td rowspan=\"2\">";
		innerHTML += "&nbsp;";
		innerHTML += "<img align=\"middle\" src=\"" + this.icon_ + "\"/>";
		innerHTML += "&nbsp;&nbsp;";
		innerHTML += "</td>";
		innerHTML += "<td class=\"message-id\" align=\"left\">";
		
		/* new code
		 * 1) id added
		 * 2) url (if present) put into href
		 * 3) custom handler can be connected to onclick event
		 */
		innerHTML +="<a href=\"" 
		if(this.url){
			innerHTML += this.url;
		}
		else{
			innerHTML += "javascript:void(0)";
		}
		innerHTML += "\" id=\"" + this.id + "_" + this.messageId +"\">";
		innerHTML += this.messageId;
		innerHTML += "</a>";

		/* original TIP code
		if (this.url) {
			//innerHTML += '<a href="javascript:void(0)" onclick="window.open(\'' + this.url + '\')">';			
			innerHTML +="<a href=\"javascript:void(0)\" id=\"" + this.messageId +"\">";
			innerHTML += this.messageId;
			innerHTML += "</a>";
		}
		else {
			innerHTML += this.messageId;
		}
		*/
		innerHTML += "</td>";
		innerHTML += "</tr>";
		innerHTML += "<tr>";
		innerHTML += "<td class=\"message-description\" align=\"left\">";
		innerHTML += "<div id='" + this.id + "_messagediv" + "' style='overflow:auto; width:100%;'>";
		innerHTML += "<p>";
		innerHTML += this.message;
		innerHTML += "</p>";
		innerHTML += "</div>";
		innerHTML += "</td>";
		innerHTML += "</tr>";
		innerHTML += "</table>";

		innerHTML += "</td>";
		innerHTML += "</tr>";

		if (this.showCheckbox === true) {
			innerHTML += "<tr>";
			innerHTML += "<td class=\"checkbox\" valign=\"bottom\">";
			innerHTML += "&nbsp;&nbsp;&nbsp;<input type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\"" + this.id + "neverShowAgain\"/><label for=\"" + this.id + "neverShowAgain\">" + this.resources_.NEVER_SHOW + "</label>";
			innerHTML += "</td>";
			innerHTML += "</tr>";
		}

		innerHTML += "<tr>";
		innerHTML += "<td valign=\"bottom\">";

		innerHTML += "<div>";
		innerHTML += "<table width='100%'>";
		innerHTML += "<tr>";
		innerHTML += "<td valign=\"bottom\">";
		innerHTML += "<div align=\"right\">";
		for (var i = 0; i < this.buttons.length; i++) {
			innerHTML += "&nbsp;<button dojoType=\"ibm.tivoli.tip.dijit.TIPButton\" id=\"" + this.id + "mmd_button_" + i + "\" value=\"" + this.buttons[i] + "\">";
			innerHTML += this.buttons[i] + "</button>";
		}
		innerHTML += "</td>";
		innerHTML += "</tr>";
		innerHTML += "</table>";
		innerHTML += "</div>";

		innerHTML += "</td>";
		innerHTML += "</tr>";
		innerHTML += "</table>";

		return innerHTML;
	},
	
	// correctly handle PNG transparency in Win IE 5.5 & 6.
	correctPNG: function() {
		var arVersion = navigator.appVersion.split("MSIE");
		var version = parseFloat(arVersion[1]);
		if ((version >= 5.5) && document.body.filters) {
			for (var i = 0; i < document.images.length; i++) {
				var img = document.images[i];
				var imgName = img.src.toUpperCase();
				if (imgName.substring(imgName.length-3, imgName.length) == "PNG") {
					var imgID = img.id ? "id='" + img.id + "' " : "";
					var imgClass = img.className ? "class='" + img.className + "' " : "";
					var imgTitle = img.title ? "title='" + img.title + "' " : "title='" + img.alt + "' ";
					var imgStyle = "display:inline-block;" + img.style.cssText;
					if (img.align == "left") {
						imgStyle = "float:left;" + imgStyle;
					}
					if (img.align == "right") {
						imgStyle = "float:right;" + imgStyle;
					}
					if (img.parentElement.href) {
						imgStyle = "cursor:hand;" + imgStyle;
					}
					var strNewHTML = "<span " + imgID + imgClass + imgTitle +
									 " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";" +
									 "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader" +
									 "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>";
					img.outerHTML = strNewHTML;
					i = i - 1;
				}
			}
		}    
	},
	
	destroy: function () {
        dojo.disconnect( this.onCloseListener );
        dojo.disconnect( this.onResizeListener );
        
        //TODO:  The fact this can NOT be called means we have not created the widget correctly
        //       This widget should directly extend dialog to be created correctly
        //this.inherited( "destroy", arguments );
        
        if ( this.dialog_ ) { this.dialog_.destroy(); dijit.registry.remove (this.dialog_.id); delete this.dialog_; }
        
		if (dijit.byId (this.id + "neverShowAgain")) {
			dijit.registry.remove (this.id + "neverShowAgain");
		}

		for (var i = 0; i < this.buttons.length; i++) {
			dijit.registry.remove (this.id + "mmd_button_" + i);
		}

		delete this.type;
		delete this.message;
		delete this.messageId;
		delete this.width;
		delete this.height;
		delete this.buttons;
		delete this.callbackParms;
		delete this.url;
		delete this.showCheckbox;
		delete this.destroyOnClose;
		delete this.title_;
		delete this.icon_;
		delete this.resources_;

		dijit.registry.remove (this.id);
	}

});


