//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Overview.ApprovalsPod");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Overview.ApprovalsView");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Overview.ApprovalDetails");

dojo.require("ibm.tivoli.simplesrm.srm.dijit.Overview.Pod");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.Overview.RequestDetails");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.MyCatalogRequestsGrid");

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Overview.ApprovalsPod", ibm.tivoli.simplesrm.srm.dijit.Overview.Pod,
{
	viewType: "ibm.tivoli.simplesrm.srm.dijit.Overview.ApprovalsView",
	detailsType: "ibm.tivoli.simplesrm.srm.dijit.Overview.ApprovalDetails",
	autoRefreshProperty: "ApprovalsAutorefresh",

	postMixInProperties: function()
	{
		//console.log("Overview.ApprovalsPod.postMixInProperties");
		this.heading = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").MyApprovalHeading;
		this.detailsLinkLabel = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").ManageApprovalsLink;
		this.detailsUrl = "#requests";
		
		this.inherited(arguments);
	},
	postCreate: function()
	{
		this.refresh();
		this.inherited(arguments);
	},
	refresh: function()
	{
		this._cancelPoll();
		var params = {
			_fd: "PMZHBT_SRAPPRLIST"
		}
		var dq = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getServiceRequests(params);
		dq.addCallbacks(dojo.hitch(this, this._updateData), dojo.hitch(this, this._refreshError));
	},
	_updateData: function(response)
	{
		try {
			this._cancelPoll();
			this._refreshErrorCount = 0;
			var requests = response.Requests;
			if(this._detailsData === requests)
				return;
			requests.sort(function(b, a) 
			{
				// sort descending
				return a.STATUSDATE < b.STATUSDATE ? 1 : a.STATUSDATE > b.STATUSDATE ? -1 : 0;
			});
			for(var i = 0; i < requests.length; ++i) {
				var dt = dojo.date.stamp.fromISOString(requests[0].STATUSDATE);
				if(dt) {
					this._mostRecent = dt.getTime();
					break;
				}
			}
			this._detailsData = requests;
			this._view.setData(requests);
			this._onDataReady();
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("ApprovalsPod._updateData failure");
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
		}
		finally {
			this._resetPoll();
		}
	},
	_poll: function()
	{
		var dq = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getServiceRequests({_fd: "PMZHBT_SRAPPRLIST", _orderbydesc: "STATUSDATE", _maxItems: 1});
		dq.addCallback(dojo.hitch(this, function(response) {
			var requests = response.Requests;
			if(requests.length > 0) {
				var dt = dojo.date.stamp.fromISOString(requests[0].STATUSDATE);
				if(dt && this._mostRecent < dt.getTime()) {
					this.refresh();
				}
				else {
					this._resetPoll();
				}
			}
		}));
	},
	_onDataReady: function()
	{
		this.inherited(arguments);
	},
	onShowDetails: function()
	{
		var bNeedsConnect = undefined == this._details;
		this.inherited(arguments);
		if(bNeedsConnect && this._details) {	// only connect once
			this.connect(this._details.detailsWidget, "onRefresh", this._updateData);
		}
	}
});

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Overview.ApprovalsView", 
		[ibm.tivoli.simplesrm.srm.dijit.Overview.View, ibm.tivoli.simplesrm.srm.dijit.ShowRequestDetails],
{
	widgetsInTemplate: true,
	templateString: '<div>\n' +
					'	<div dojoType="ibm.tivoli.simplesrm.srm.dijit.Overview.DataTable" dojoAttachPoint="dataTable" heading="${tableCaption}"></div>\n' +
					'</div>\n',
	tableCaption: "",
	totalStr: "Total",
	showDetais: "Show details",
	constructor: function()
	{
		//console.log("Overview.ApprovalsView.ctor");
	},
	postMixInProperties: function()
	{
		//console.log("Overview.ApprovalsView.postMixInProperties");
		this.tableCaption = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").RecentActivity;
		this.totalStr =     dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").Total;
		this.showDetails =	dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").ShowDetails;
		this.inherited(arguments);
	},
	setData: function(requests)
	{
		this.clear();
		this._detailsData = requests;
		var status_stats = {srm_status_count: [], srm_unique_stati: 0};
		var l = requests.length;
		var maxoverview =  Math.min(10, l);
		
		if(l <= 0)
			this.dataTable.addRow(dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").NoRecentActivity, "");
		else {
			for(var i = 0; i < l; ++i) {
				var cmr = requests[i];
				var status = cmr.StatusString;
				var dueDate = this._formatDate(cmr.STATUSDATE);
				
				if(i < maxoverview) {
					var link = "<a href='#req_"+ cmr.id +"' title='"+this.showDetails.htmlencode()+"'>" +(cmr.DESCRIPTION ? cmr.DESCRIPTION.htmlencode() : '...')+ "</a>";
					//this.dataTable.addRow(link, status);
					this.dataTable.addRow(link, dueDate);
				}
			}
			if(l > 0) {
				var links = dojo.query("a", this.dataTable.domNode);
				for(var i = 0; i < links.length; ++i) {
					this.connect(links[i], "onclick", this._showRequestDetails);
				}
			}			
		}
	},
	clear: function()
	{
		this.dataTable.clear();
	},	
	_showRequestDetails: function(evt)
	{
		console.log(evt);
		try {
			var href = evt.target.href;
			var reqid = href.substring(href.lastIndexOf("_")+1);

			for(var i = 0; i < this._detailsData.length; ++i) {
				var req = this._detailsData[i];
				if(reqid == req.id) {
					this.setApprContext(true);
					this.showRecordDetails(req);
					break;
				}
			}
		}
		catch(ex){
			console.warn(ex);
		}
	}, 
	_formatDate: function(d) {
		if(d) {
			if(d.search("9999") == 0) {
				d = this._uiStringTable.ForeverLabel;
			}
			else {
				var isod = d.replace(' ', 'T');
				var format_opts = {fullYear:true, selector: "date"};
				var dt = dojo.date.stamp.fromISOString(isod);
				if(dt) {
					d = dojo.date.locale.format(dt, format_opts);
					//d = "<div style='oveflow:hidden;white-space:nowrap' title='"+d+"'>" + d + "</div>";
				}
			}
		}
		return d;
	}
});

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Overview.ApprovalDetails", ibm.tivoli.simplesrm.srm.dijit.Overview.Details,
{
	headingText: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").ManageApprovals,
	detailsType: "ibm.tivoli.simplesrm.srm.dijit.MyApprovalsGrid",
	
	_cshKey: "PMRDP_View_RequestsforApproval.htm",
	
	refresh: function(data_set)
	{
		if(data_set) {
			this._detailsData = data_set;
			this.detailsWidget.clearGrid();
			this.detailsWidget._loadGrid(this._detailsData);
		}
		else 
			this.inherited(arguments);	// will refresh detailsWidget
	},
	resize: function()
	{
		this.inherited(arguments);
		//var containerSz = dojo.contentBox(this.containerNode)
		//dojo.style(this.detailsWidget.id + "_grid_container", "height", containerSz.h + "px");
		this.detailsWidget.simpleGrid.resize();
	}
});
