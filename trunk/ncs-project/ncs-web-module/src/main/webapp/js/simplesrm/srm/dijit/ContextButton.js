//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.ContextButton");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.RequestButton")
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.MultiRequestButton");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.ContextButtonSet");


dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");
dojo.require("dijit.Toolbar");
dojo.require("dijit.form.Button");
dojo.require("dijit.Menu");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.CreateCatalogRequest");


dojo.declare("ibm.tivoli.simplesrm.srm.dijit.RequestButton", dijit.form.Button, 
{
	requestInfo: null,
	
	postMixInProperties: function()
	{
		this.inherited(arguments);
		this.title = this.requestInfo.Description.htmlencode();
		this.label = this.requestInfo.Description.htmlencode();
		this.showLabel = false;
		this.iconClass = "request_button";
		this.baseClass="srm_button";
	},
	postCreate: function()
	{
		this.iconNode.innerHTML = "<img alt='' src='" + this.requestInfo.ImagePath + "'>";
		this.inherited(arguments);
	},
	onClick: function(evt)
	{
	}
});
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.MultiRequestButton", dijit.form.DropDownButton, 
{
	requestInfo: null,
	
	postMixInProperties: function()
	{
		console.log("MultiRequestButton.posMixInProperties");
		this.showLabel = false;
		this.iconClass = "request_button";
		this.inherited(arguments);
	},
	postCreate: function()
	{
		//dojo.addClass(this.domNode, "srm_button");
		this.iconNode.innerHTML = "<img alt='' src='" + this.requestInfo[0].ImagePath + "'>";

		this.dropDown = new dijit.Menu();
		for(var i= 0; i < this.requestInfo.length; ++i) {
			var off =  this.requestInfo[i];
			var mi = new dijit.MenuItem({label: off.Description.htmlencode(), iconClass: "nodisplay"});
			dojo.addClass(mi, "srmDropDownMenuItem")
			dojo.mixin(mi, {requestInfo: off});
			this.connect(mi, "onClick", this.onClick);
			this.dropDown.addChild(mi);
		}
		this.inherited(arguments);
	}
});
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.ContextButtonSet", [dijit._Widget, ibm.tivoli.simplesrm.srm.dijit.CreatorFactory], 
{
	contextName: "",
	offeringList: null,
	toolbar: null,
	click_context: {},
	length: 0,

	_buttonSet: null,
	
	_createLabel: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").ContextButtonSetLabelCreate,
	
	constructor: function()
	{
		console.log("ContextButtonSet.ctor");
	},

	postCreate: function()
	{
		console.log("ContextButtonSet.postCreate");

		this._buttonSet = new dijit.WidgetSet();
		
		var ctxItems = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getOfferingsInContext(this.contextName);
		this.length = ctxItems.length;
		var groups = this._groupRequests(ctxItems);
		for(var key in groups) {
			var btn;
			var ctxItems = groups[key];
			if(ctxItems.length == 1) {
				btn = new ibm.tivoli.simplesrm.srm.dijit.RequestButton({requestInfo: ctxItems[0], onClick: dojo.hitch(this, "onClick")});
			}
			else {
				btn = new ibm.tivoli.simplesrm.srm.dijit.MultiRequestButton({label: this._createLabel, requestInfo: ctxItems, onClick: dojo.hitch(this, "onClick")});
			}
			this.toolbar.addChild(btn);
			this._buttonSet.add(btn);
		}
	},
	setContext: function(ctx)
	{
		this.click_context = ctx;
	},
	// group requests by icon	
	_groupRequests: function(req_list)
	{
		var reqGroups = {};
		for(var i =0; i < req_list.length; ++i) {
			var req = req_list[i];
			if(!reqGroups[req.ImageName]) {
				reqGroups[req.ImageName] = [];
			}
			reqGroups[req.ImageName].push(req);
		}
		return reqGroups;
	},
	_setDisabledAttr: function(newval)
	{
		this._buttonSet.forEach(function(w) {w.attr("disabled", newval);}); 
	},
	onClick: function(evt)
	{
		var w = dijit.getEnclosingWidget(evt.target);
		var off = w.requestInfo;
		if(dojo.isObject(this.click_context))
			dojo.mixin(off, this.click_context);
		console.log("ContextButtonSet.onClick: ", off.ItemNum);
		this.createAndShowInputForm(off, false);
	}

});
