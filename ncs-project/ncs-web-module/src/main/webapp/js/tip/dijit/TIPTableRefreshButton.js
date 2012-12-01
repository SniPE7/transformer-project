/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * 5724-C51
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.require("dijit.form.Button");
dojo.require("dijit.Tooltip");

// NLS
dojo.requireLocalization("ibm.tivoli.tip.dijit", "resources");

dojo.provide("ibm.tivoli.tip.dijit.TIPTableRefreshButton");
/******************************************************************************
* Provides Table Refresh button that allows for "busy" animation. 
*
* Authors: Joseph Firebaugh
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.TIPTableRefreshButton", dijit.form.Button,
{
    baseClass: "tipTableRefreshButton",

	templateString:"<div class=\"dijit dijitLeft dijitInline ${baseClass}\"\n\tdojoAttachEvent=\"onclick:_onButtonClick,onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\"\n\t><div class='dijitRight'\n\t\t><button class=\"dijitStretch dijitButtonNode dijitButtonContents\" dojoAttachPoint=\"focusNode,titleNode\"\n\t\t\ttype=\"${type}\" waiRole=\"button\" waiState=\"labelledby-${id}_label\"\n\t\t\t><span class=\"dijitInline ${iconClass}\" dojoAttachPoint=\"iconNode\" \n \t\t\t\t><span class=\"dijitToggleButtonIconChar\">&#10003</span \n\t\t\t></span\n\t\t\t><span class=\"dijitButtonText\" id=\"${id}_label\" dojoAttachPoint=\"containerNode\">${label}</span\n\t\t></button\n\t></div\n></div>\n",
    
    postCreate: function()
    {
		var resources = dojo.i18n.getLocalization("ibm.tivoli.tip.dijit", "resources");
		this.titleNode.title = resources.REFRESH;
		// in dojo 1.2.3, _FormWidgets scroll themselves into view when they, or their container get focus.
		// this is causing my UI to jump when even clicking on widgets that are already visible
		this._scroll = false;
    }
    
});