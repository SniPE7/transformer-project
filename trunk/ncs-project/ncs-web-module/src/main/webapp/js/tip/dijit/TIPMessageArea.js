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
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dojo.fx");
// NLS
dojo.requireLocalization("ibm.tivoli.tip.dijit", "resources");

dojo.provide("ibm.tivoli.tip.dijit.TIPMessageArea");
dojo.provide("ibm.tivoli.tip.dijit.TIPMessageAreaManager");
/******************************************************************************
* Collapsable Message Area (Info, Warning, Error)
*
* The purpose of this widget is to be placed into dialogs and forms to aid in
* providing feedback on form errors.
*
* Author: Joseph Firebaugh
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.TIPMessageArea", [dijit._Widget, dijit._Templated],
{
	//the message box type: critical, info, warning
	type: "critical",
	
	//the message in the are -- can be HTML string
	message: "",
	
	//if you provide a message id then it will use bigger message images
	messageId: null,

	//the duration of time to play the wipe fx on show and close	
	duration: 200,
	
	// url: String: a URL that links the message to an InfoCenter page	
	url: null,
	
	// boolean: create the message area already open?
	isShowing: false,
	
	//the HTML template for the label placement and null value checkbox
	templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit","templates/TIPMessageArea.html"),
	   closeIcon: dojo.moduleUrl("ibm.tivoli.tip.dijit","themes/images/message_close.gif"),
	criticalIcon: dojo.moduleUrl("ibm.tivoli.tip.dijit","themes/images/i_message_critical_10.png"),	  
	
	constructor: function () 
	{
		this.resources = dojo.i18n.getLocalization("ibm.tivoli.tip.dijit", "resources");	
	}, 			
	/**************************************************************************
	* Setup the widget
	**************************************************************************/	
	postCreate: function()
	{
		//call super
		this.inherited( "postCreate", arguments );

		//initialize message
		this.setMessageId( this.messageId );
		
		//initialize message
		this.setMessage( this.message );

		//initialize type
		this.setType( this.type );		
		
		//create a WipeIn Effect Object
		this._wipeIn = dojo.fx.wipeIn(
		{
			node: this.wipeNode,
			duration: this.duration
		});
		
		//create a WipeOut Effect Object
		this._wipeOut = dojo.fx.wipeOut(
		{
			node: this.wipeNode,
			duration: this.duration			
		});
		
		if ( !this.isShowing ) 
		{
			this.wipeNode.style.display="none";
		}
	},

	/**************************************************************************
	* Set the type of the message box (critical, warning, info)
	**************************************************************************/		
	setType: function( type )
	{
		this.type = type;
		
		this._updateClass();
	},
	
	/**************************************************************************
	* Set the message ( can be HTML string )
	**************************************************************************/		
	setMessage: function( message )
	{
		this.messageNode.innerHTML = message;
	},
	
	/**************************************************************************
	* Set the messageId ( can be HTML string )
	**************************************************************************/		
	setMessageId: function( messageId )
	{
	   this.messageId = messageId;
	   
	   var suffix = "_30.png";
	   
	   if ( messageId === undefined || messageId === null || messageId == "" )
	   {
	     suffix = "_10.png";
	     
	     if( this.messageIdNode )
	     {
	     	this.messageIdNode.parentNode.removeChild( this.messageIdNode );
	     	delete this.messageIdNode;
	     }
	   }
	   else
	   {
	     if( !this.messageIdNode )
	     {
		   	 this.messageIdNode = dojo.doc.createElement( 'DIV' );
		   	 this.messageIdNode.className = "message-id";
		     dojo.place( this.messageIdNode, this.messageNode, "before" );
		 }
		   	 
	   	 var innerHTML = "";
	   	 
	   	 //if infocenter URL set then create hyperlink
	   	 if ( this.url ) 
	   	 {
	   	   innerHTML += '<a href="javascript:void(0)" onclick="window.open(\'' + this.url + '\')">';
		   innerHTML += this.messageId;
		   innerHTML += "</a>";
		 }
		 else 
		 {
		   innerHTML += this.messageId;
	     }
	   	 
	     this.messageIdNode.innerHTML = innerHTML;		     
	   }
	   
	   this.criticalIcon = dojo.moduleUrl("ibm.tivoli.tip.dijit","themes/images/i_message_critical" + suffix);
           this.infoIcon = dojo.moduleUrl("ibm.tivoli.tip.dijit","themes/images/i_message_info"  + suffix);
	   this. warningIcon = dojo.moduleUrl("ibm.tivoli.tip.dijit","themes/images/i_message_warning"  + suffix);	    
	   
	   this._updateClass();	    
	},
	
	/**************************************************************************
	* Show a info message
	* messageID is optional
	**************************************************************************/		
	showInfoMessage: function( message, messageID )
	{
		this.setMessage( message );
		if (messageID)
		{
			this.setMessageId( messageID );
		}
		else
		{
			this.setMessageId( null );
		}
		this.setType( "info" );
		
		this.show();
	},

	/**************************************************************************
	* Show a warning message
	* messageID is optional
	**************************************************************************/		
	showWarningMessage: function( message, messageID )
	{
		this.setMessage( message );
		
		if (messageID)
		{
			this.setMessageId( messageID );
		}
		else
		{
			this.setMessageId( null );
		}
		
		this.setType( "warning" );
		
		this.show();
	},

	/**************************************************************************
	* Show an error message
	* messageID is optional
	**************************************************************************/		
	showErrorMessage: function( message, messageID )
	{
		this.setMessage( message );
		
		if (messageID)
		{
			this.setMessageId( messageID );
		}
		else
		{
			this.setMessageId( null );
		}
		
		this.setType( "critical" );
		
		this.show();
	},
	
	/**************************************************************************
	* Show the message area -- ensures message area not already visible for you
	**************************************************************************/		
	show: function()
	{
		if ( !this.isShowing )
		{
			this.isShowing = true;
			this._wipeIn.play();
		}
	},
	
	/**************************************************************************
	* Hide the message area -- ensures message area not already hidden for you
	**************************************************************************/		
	hide: function()
	{
		if ( this.isShowing )
		{
			this.isShowing = false;
			this._wipeOut.play();
		}
	},
	
	/**************************************************************************
	* updates the CSS styling and icon based on current type setting
	**************************************************************************/		
	_updateClass: function()
	{
		var type = this.type.toLowerCase();
		
		dojo.removeClass( this.styleArea, "critical" );
		dojo.removeClass( this.styleArea, "info" );
		dojo.removeClass( this.styleArea, "warning" );
		
		if( type == "critical" )
		{
			this.messageIcon.src = this.criticalIcon;
			this.messageIcon.alt = this.resources.DIALOG_ERROR;
		}
		else if ( type == "info" )
		{
			this.messageIcon.src = this.infoIcon;
			this.messageIcon.alt = this.resources.DIALOG_INFO;
		}
		else
		{
			this.messageIcon.src = this.warningIcon;
			this.messageIcon.alt = this.resources.DIALOG_WARNING;
		}
		
		dojo.addClass( this.styleArea, type );
		
		//overload the stylesheet (because it was written for a dialog)
		this.styleArea.style.padding="2px";
	},
		
	/**************************************************************************
	* Called when the close button is clicked
	**************************************************************************/				
	_onCloseClick: function()
	{
		this.hide();
	}
});