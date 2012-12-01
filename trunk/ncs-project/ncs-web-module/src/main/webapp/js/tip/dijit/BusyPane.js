/****************************************************** {COPYRIGHT-TOP} ***
* Licensed Materials - Property of IBM
* 5724-Q87 
*
* (C) Copyright IBM Corp. 2008
*
* US Government Users Restricted Rights - Use, duplication, or
* disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
********************************************************** {COPYRIGHT-END} **/
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("ibm.tivoli.tip.dojo.TIPHelper");

dojo.requireLocalization("ibm.tivoli.tip.dijit", "resources");

dojo.provide( "ibm.tivoli.tip.dijit.BusyPane" );
/******************************************************************************
* Busy Pane that blocks all user input and shows progress message.
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.BusyPane", [dijit._Widget, dijit._Templated],
{
  processingImageSrc: dojo.moduleUrl("ibm.tivoli.tip.dijit","themes/images/processing-toolbar.gif"),
    
  //processing label var
  processingLabel : "",  
  
  templateString: "<div class='tipBusyPane'><table width='100%' height='100%' cellspacing='0' cellpadding='0' border='0'><tr><td valign='middle' align='middle'><div class='tipBusyPaneProcessing'><table width='100%' cellspacing='0' cellpadding='0' border='0'><tbody><tr><td class='process-anim' width='1%'><img width='16' height='16' src='${processingImageSrc}'/></td><td class='process-message' dojoAttachPoint='messageArea'>${processingLabel}</td></tr></tbody></table></div></td></tr></table></div>",
  
  constructor: function () {
	  this.processingLabel = dojo.i18n.getLocalization( "ibm.tivoli.tip.dijit", "resources" ).PROCESSING;
  },
  
  postCreate: function()
  {
    this.inherited( arguments );
    
    this.hide();
  },
  
  show: function( sizeToPortlet )
  {
  	var tipHelper = null;
  	var div       = null;
  	var coords    = null;
  	
	if ( sizeToPortlet ) 
	{
		
		tipHelper = new ibm.tivoli.tip.dojo.TIPHelper();
		
		div = tipHelper.getTopPortletNode (this.domNode);
		
		if ( div ) 
		{
		    coords = dojo.coords( div );
		    //console.log ("in BusyPane.show.  got coords: ");
		    //console.log (coords);
		    dojo.style (this.domNode, "top",  ( coords.y < 0 ) ? 0 : coords.y );
		    dojo.style (this.domNode, "left", ( coords.x < 0 ) ? 0 : coords.x );
		    dojo.style (this.domNode, "width", coords.w);
			dojo.style (this.domNode, "height", coords.h);
		}
	} 
	else 
	{
	     //console.log ("in BusyPane.show.  covering whole page/frame.");
	    dojo.style (this.domNode, "top", "0");
	    dojo.style (this.domNode, "left", "0");
	    dojo.style (this.domNode, "width", "100%");
		dojo.style (this.domNode, "height", "100%");
	}
    
    this.domNode.style.display="";
  },
  
  hide: function()
  {
    this.domNode.style.display="none";
  }
  
});
