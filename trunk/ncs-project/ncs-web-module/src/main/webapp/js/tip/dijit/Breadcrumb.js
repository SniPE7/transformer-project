/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.provide("ibm.tivoli.tip.dijit.Breadcrumb");
dojo.provide("ibm.tivoli.tip.dijit.BreadcrumbBar");
dojo.provide("ibm.tivoli.tip.dijit.BreadcrumbSeparator");

dojo.require("dijit._Widget");
dojo.require("dijit._Container");
dojo.require("dijit._Templated");
dojo.require("dijit.form.Button");

dojo.declare(
	"ibm.tivoli.tip.dijit.BreadcrumbBar",
	[dijit._Widget, dijit._Templated, dijit._KeyNavContainer],
{
	templateString:
		'<div class="dijit dijitToolbar" waiRole="toolbar" tabIndex="${tabIndex}" dojoAttachPoint="containerNode">' +
		'</div>',

	tabIndex: "0",

	postCreate: function(){
		this.connectKeyNavHandlers(
			this.isLeftToRight() ? [dojo.keys.LEFT_ARROW] : [dojo.keys.RIGHT_ARROW],
			this.isLeftToRight() ? [dojo.keys.RIGHT_ARROW] : [dojo.keys.LEFT_ARROW]
		);
	},

	startup: function(){
		this.startupKeyNavChildren();
	},
	
	destroy: function () {
//		this.destroyDescendants();
//		this.inherited (arguments);
	}
}
);

dojo.declare(
	"ibm.tivoli.tip.dijit.BreadcrumbSeparator",
	[ dijit._Widget, dijit._Templated ],
{
	// summary
	//	A line between two menu items
	templateString: '<div class="breadcrumbDivider dijitInline">&gt;</div>',
	postCreate: function(){ dojo.setSelectable(this.domNode, false); },
	isFocusable: function(){ return false; }
});

dojo.declare(
	"ibm.tivoli.tip.dijit.Breadcrumb",
	[ dijit.form.Button ],
{
	templateString:"<div class=\"dijit dijitLeft dijitInline breadcrumb\"\n\tdojoAttachEvent=\"onclick:_onButtonClick,onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\"\n\t><div class='dijitRight'\n\t\t><button class=\"dijitStretch dijitButtonNode dijitButtonContents\" dojoAttachPoint=\"focusNode,titleNode\"\n\t\t\ttype=\"${type}\" waiRole=\"button\" waiState=\"labelledby-${id}_label\"\n\t\t\t><span class=\"dijitInline ${iconClass}\" dojoAttachPoint=\"iconNode\" \n \t\t\t\t><span class=\"dijitToggleButtonIconChar\">&#10003</span \n\t\t\t></span\n\t\t\t><span class=\"dijitButtonText\" id=\"${id}_label\" dojoAttachPoint=\"containerNode\">${label}</span\n\t\t></button\n\t></div\n></div>\n",

	constructor: function () {
	},
	postCreate: function() {
		this.inherited (arguments);
	},
	destroy: function () {
//		this.destroyDescendants();
//		this.inherited (arguments);
	}
});

