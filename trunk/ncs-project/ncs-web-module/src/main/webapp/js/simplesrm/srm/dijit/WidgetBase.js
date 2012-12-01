//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.WidgetBase");

dojo.require("dojo.cookie");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError");
dojo.require("dojo.i18n");
dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");

// the WidgetBase class is the base class for other widgets
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.WidgetBase", null,
{	
	srmTokenName: "SimpleSRMToken",
	proxyUrl: "/SRMCommonsWeb/ProxyServlet",
	imageCacheUrl: "/SRMCommonsWeb/MaxImageCache/",
	_uiStringTable: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable"),

	// constructor declares properties
	constructor: function(/*object*/params, /*domNode*/domNode)
	{
		console.log("WidgetBase.ctor");
	},
	isLoggedIn: function() {
		return (null != dojo.cookie(this.srmTokenName));
	},
	getRelativePath: function(/* string */ pathFragment)
	{
		var relPath;
		if(this.iContext) {
			relPath = this.iContext.io.rewriteURI(pathFragment);
		}
		else {
			relPath = dojo.moduleUrl(this.declaredClass.substring(0, this.declaredClass.lastIndexOf('.')), pathFragment).toString();
		}
		return relPath;
	},
	_crossFade: function(outnode, innode) 
	{
		if(dojo.isIE) {
			// this still doesn't work!
			// for some reason, the inlist gets the correct styling, but never shows up
			if(outnode) {
				dojo.style(outnode, "display", "none");
			}
			dojo.style(innode, "display", "block");
		}
		else {
			var shower = dojo.fadeIn({
				node: innode,
				duration: 500,
				onPlay: function() {
					dojo.style(innode, "display", "block");
				}});
			if(outnode) {			
				var hider = dojo.fadeOut({
					node: outnode,
					duration: 500,
					onEnd:function(){
						dojo.style(outnode,"display", "none");
					}});
				dojo.fx.chain([hider, shower]).play();
			}
			else {
				shower.play();
			}
		}			
	}
});
