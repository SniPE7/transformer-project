//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Overview.RequestsPod");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Overview.RequestsView");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Overview.RequestDetails");

dojo.require("ibm.tivoli.simplesrm.srm.dijit.Overview.Pod");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");
dojo.require("dojo.DeferredList");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.BaguetteChart");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.MyCatalogRequestsGrid");
dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Overview.RequestsPod", ibm.tivoli.simplesrm.srm.dijit.Overview.Pod,
{
	viewType: "ibm.tivoli.simplesrm.srm.dijit.Overview.RequestsView",
	detailsType: "ibm.tivoli.simplesrm.srm.dijit.Overview.RequestDetails",
	autoRefreshProperty: "RequestsAutoRefresh",
	
	postMixInProperties: function()
	{
		console.log("Overview.RequestsPod.postMixInProperties");
		this.heading = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").MyCatalogRequestHeading;
		this.detailsLinkLabel = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").ManageRequestsLink;
		this.detailsUrl = "#requests";
		
		this.inherited(arguments);
	},
	postCreate: function()
	{
		this.refresh();
		this.inherited(arguments);
	},
	/**
	 * Requery my data and update my UI
	 */
	refresh: function()
	{
		this._cancelPoll();
		var dq = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getServiceRequests();
		dq.addCallbacks(dojo.hitch(this, this._updateData), dojo.hitch(this, this._refreshError));
	},

	/**
	 * Update the UI, given a new data set
	 */
	_updateData: function(response)
	{
		try {
			this._cancelPoll();
			this._refreshErrorCount = 0;
			var requests = response.Requests;
			if(this._detailsData === requests)
				return;
			requests.sort(function(a, b) 
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
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("RequestsPod._updateData failure");
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
		}
		finally {
			this._resetPoll();
		}
	},
	_poll: function()
	{
		var dq = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getServiceRequests({_orderbydesc: "STATUSDATE", _maxItems: 1});
		dq.addCallbacks(dojo.hitch(this, function(response) {
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
		}),
		dojo.hitch(this, this._refreshError));
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

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Overview.RequestsView", 
		[ibm.tivoli.simplesrm.srm.dijit.Overview.View, ibm.tivoli.simplesrm.srm.dijit.ShowRequestDetails],
{
	widgetsInTemplate: true,
	templateString: '<div>\n' +
					'	<div dojoType="ibm.tivoli.simplesrm.srm.dijit.BaguetteChart" barHeight="15" captionHeight="13" captionFontSize="10"\n' + 
					'			showTotal="true" totalLegend="${totalStr}"\n' + 
					'		dojoAttachPoint="baguetteChart" style="overflow:hidden; margin-bottom: 10px;"></div>\n' +
					'	<div dojoType="ibm.tivoli.simplesrm.srm.dijit.Overview.DataTable" dojoAttachPoint="dataTable" heading="${tableCaption}"></div>\n' +
					'</div>\n',
	tableCaption: "",
	totalStr: "Total",
	showDetais: "Show details",
	constructor: function()
	{
		console.log("Overview.RequestsView.ctor");
	},
	postMixInProperties: function()
	{
		console.log("Overview.RequestsView.postMixInProperties");
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
		var maxoverview =  Math.min(5, l);
		for(var i = 0; i < l; ++i) {
			var cmr = requests[i];
			var status = cmr.StatusString;
			if(i < maxoverview) {
				var link = "<a href='#req_"+ cmr.id +"' title='"+this.showDetails.htmlencode()+"'>" +(cmr.DESCRIPTION ? cmr.DESCRIPTION.htmlencode() : '...')+ "</a>";
				this.dataTable.addRow(link, status);
			}
			if('number' == typeof status_stats.srm_status_count[status]) {
				++status_stats.srm_status_count[status];
			}
			else {
				status_stats.srm_status_count[status] = 1;
				++status_stats.srm_unique_stati;
			}
		}
		if(l > 0) {
			var links = dojo.query("a", this.dataTable.domNode);
			for(var i = 0; i < links.length; ++i) {
				this.connect(links[i], "onclick", this._showRequestDetails);
			}
		}
		if(l > 0)
			this._refreshBaguetteChart(status_stats);
		else 
			this.dataTable.addRow(dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").NoRecentActivity, "");
	},
	clear: function()
	{
		this.dataTable.clear();
		if(this.baguetteChart)
			this.baguetteChart.setData([]);
	},	
	_refreshBaguetteChart: function(status_stats)
	{
		if(undefined == this.baguetteChart)
			return;

		console.log("Overview.RequestsView._refreshBaguetteChart: refreshing");

		var counts = status_stats.srm_status_count;
		counts.sort();
		// set stripe graph data using colors generated by walking around the color wheel
		var stripeData = [];
		var i = 0;
		for(var s in counts) {
			stripeData.push({name: s, value: counts[s], color: ibm.tivoli.simplesrm.srm.dijit.BaguetteChart.getDefaultColor(i++)});
		}
		this.baguetteChart.setData(stripeData);
	},
	_showRequestDetails: function(evt)
	{
		console.log(evt);
		try {
			var href = evt.target.href;
			var reqid = href.substring(href.lastIndexOf("_")+1);
			console.log("RequestsPod._showRequestDetails(%s)", reqid);
			for(var i = 0; i < this._detailsData.length; ++i) {
				var req = this._detailsData[i];
				if(reqid == req.id) {
					this.showRecordDetails(req);
					break;
				}
			}
		}
		catch(ex){
			console.warn(ex);
		}
	}
});

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Overview.RequestDetails", ibm.tivoli.simplesrm.srm.dijit.Overview.Details,
{
	headingText: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").ManageRequests,
	detailsType: "ibm.tivoli.simplesrm.srm.dijit.MyCatalogRequestsGrid",
	
	_cshKey: "PMRDP_View_StatusSubmittedRequests.htm",
	
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
		this.detailsWidget.baguetteChart.resize()
	}
});

