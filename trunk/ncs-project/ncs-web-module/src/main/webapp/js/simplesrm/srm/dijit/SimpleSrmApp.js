//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

/*
** SimpleSrmApp is an object that brings together all the widgets into a useful application
*/
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.SimpleSrmApp");

dojo.require("dijit.Dialog");
dojo.require("dojo.fx");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.Utilities");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.Navigator");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.MyCatalogRequestsGrid");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.CreateCatalogRequest");	// pulls in CreatorPopupdialog
dojo.require("ibm.tivoli.simplesrm.srm.dijit.CreatorPopupDialog");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.Overview.RequestsPod");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.Overview.ApprovalsPod");

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.SimpleSrmApp", 
	[dijit._Widget, dijit._Templated, ibm.tivoli.simplesrm.srm.dijit.WidgetBase], 
{	
	widgetsInTemplate: true,
	templatePath: dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit", "templates/SimpleSrmApp.html"),
	
	imagesPath: "",
	requestsPod: null,	// always exists
	_pod_div_list: "",	// expanded in the template
	_pod_id_list: [],

	constructor: function()
	{
		console.log("SimpleSrmApp.ctor");
		
		if(!this.imagesPath)
			this.imagesPath = dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit", "images");
	},
	buildRendering: function()
	{
		/* Query the config.properties from WAS */
		var podlist = ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("POD_LIST").split(',');
		var podtype = "";
		for(var i = 0; i < podlist.length; ++i) {
			podtype = podlist[i].trim();
			try {
				console.log("SimpleSrmApp.startup creating pod '%s'", podtype);
				dojo.require(podtype);
				this._pod_div_list += "<div dojoType='"+podtype+"' dojoAttachPoint='pod_"+i+"'></div>";
				this._pod_id_list.push("pod_" + i);
			}
			catch(ex){
				new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("Failed loading pod " + podtype);
				new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			}
		}
		try {
			var approver_groups = ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("APPROVER_GROUP_LIST");
			if(approver_groups.length > 0) {
				var r = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getCurrentUserRole();
				if(r && r.GROUPNAME && r.GROUPNAME.match("^(?:"+approver_groups+")$")) {
					this._pod_div_list += "<div dojoType='ibm.tivoli.simplesrm.srm.dijit.Overview.ApprovalsPod' dojoAttachPoint='pod_"+i+"'></div>";
					this._pod_id_list.push("pod_" + i);
				}
			}
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("Failed loading ApprovalsPod");
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
		}
		this.inherited(arguments);
	},
	postCreate: function()
	{
		// when the list of requests is refreshed, update the recent requests also
		var cmrq = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getCatalogRequests();
		cmrq.addCallback(dojo.hitch(this, function(response) {
			this.navigator.refreshRecentRequests(response);
		}));
		//dojo.connect(this.requestsPod, "onRefresh", this.navigator, "refreshRecentRequests");
		this.inherited(arguments);
	},
	startup: function()
	{	
		console.log("SimpleSrmApp.startup");
		
		// when a new request is created, refresh the pods
		dojo.connect(this.navigator,  'onSrmRequestCreated', this.requestsPod, 'refresh');
		for(var i = 0; i < this._pod_id_list.length; ++i) {
			dojo.connect(this.navigator,  'onSrmRequestCreated', this[this._pod_id_list[i]], 'refresh');
		}
		this.inherited(arguments);		
	},
	
	logout: function()
	{
		window.location.assign("devlogin.jsp" + window.location.search);
	},
	
	dummy_:null
});

