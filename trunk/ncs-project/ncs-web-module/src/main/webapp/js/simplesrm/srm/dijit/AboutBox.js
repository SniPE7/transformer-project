//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.AboutBox");

dojo.require("dijit.form.Button");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");

dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");

/*
 *  
 */
dojo.declare(
		"ibm.tivoli.simplesrm.srm.dijit.AboutBox",
		[dijit._Widget,dijit._Templated],
{		
	widgetsInTemplate: true,
	_uiStringTable: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable"),
	templatePath: dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit", "templates/AboutBox.html"),
	imgHeight:460,
	imgWidth: 650,
	buildVersion: "BUILD-20091027D",
	
	_onclick: function(event){
		console.log("click!");
		dojo.style(this.domNode,"display","none");
		dojo.stopEvent(event);
	},
	
	show: function(){
		var vp = dijit.getViewport();
		var l = (vp.w - this.imgWidth) / 2;
		console.log("vp.w:" +vp.w + " l: " + l);
		var t = (vp.h - this.imgHeight) / 2;
		console.log("vp.h: " + vp.h + " t: " + t);
		dojo.style(this.domNode, {left: l + "px", top: t + "px", display: "block"});
		this.close.focus();
	}
});
