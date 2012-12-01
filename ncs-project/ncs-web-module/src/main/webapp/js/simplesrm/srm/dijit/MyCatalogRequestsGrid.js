//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.MyCatalogRequestsGrid");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.ShowRequestDetails");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.MyApprovalsGrid");

// include modules
dojo.require("ibm.tivoli.simplesrm.srm.dijit.MyRecordsGrid");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.RequestDetails");
dojo.require("dijit.Dialog");


// Summary:
//	A mixin for showing request details.
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.ShowRequestDetails",
			 ibm.tivoli.simplesrm.srm.dijit.CreatorFactory,
{
	_approveRights: false,

	popup: null,
	
	showRecordDetails: function(selected_record)
	{
		//this.createAndShowInputForm(selected_record, true);
		if(this.popup) {
			this.popup.destroy();
			this.popup = null;
		}
		if(selected_record) {
			console.log("ShowRequestDetails", selected_record);
			var d = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getRequestDetails(selected_record);
			d.addCallback(dojo.hitch(this, function(response)
			{
				var rd = new ibm.tivoli.simplesrm.srm.dijit.RequestDetails({approval: this._approveRights});
				rd.setData(response);
			}));
		}
	},
	
	/**
	 * Specify whether the parent grid allows the approval panel to be displayed
	 * or not.
	 */
	setApprContext: function(approvalContext) {
		this._approveRights = approvalContext;
	}
});

// Summary:
//	TODO: write this
dojo.declare(
	"ibm.tivoli.simplesrm.srm.dijit.MyCatalogRequestsGrid",
	[dijit._Widget, ibm.tivoli.simplesrm.srm.dijit.MyRecordsGrid, ibm.tivoli.simplesrm.srm.dijit.ShowRequestDetails],
{
 
	// constructor defines "static" properties for this class, and
	// declares "instance" properties for this class
	constructor: function()
	{
		console.log("CatalogRequestsGrid.ctor");
		
//		this._hoverer = new  ibm.tivoli.simplesrm.srm.dojo.Hoverer();
//		this._conn_onHover = dojo.connect(this._hoverer, "OnHover", this, "_showGridTooltip");
		
 		// the column definitions
 		// the order they are listed here is the order they will be displayed in the tooltip
		this._column_defs = [
			 {field: 'key',			name: "",							 	width: 'auto',	compare: dojo.hitch(this, "_sortDefault"), showInTooltip: false}
			,{field: 'id', 			name: 'id',								width: 'auto',	compare: dojo.hitch(this, "_sortNumber")}
			,{field: 'type', 		name: "Object Type",					width: 'auto',	compare: dojo.hitch(this, "_sortString"), showInTooltip: false}
			,{field: 'STATUSDATE',  name: this._uiStringTable.StatusDate,   width: 'auto',  compare: dojo.hitch(this, "_sortDefault"), formatter: dojo.hitch(this, "_formatDateTimeReally")}		
			,{field: 'DESCRIPTION', name: this._uiStringTable.Description, 	width: '40%',	compare: dojo.hitch(this, "_sortString"), formatter: dojo.hitch(this, "_formatStringSafe")}
			,{field: 'REQUESTEDBY',	name: this._uiStringTable.RequestedBy, 	width: 'auto',	compare: dojo.hitch(this, "_sortString"), formatter: dojo.hitch(this, "_formatStringSafe")}
			,{field: 'REQUESTEDFOR',name: this._uiStringTable.RequestedFor,	width: 'auto',	compare: dojo.hitch(this, "_sortString"), formatter: dojo.hitch(this, "_formatStringSafe")}
			,{field: 'StatusString',name: this._uiStringTable.Status,		width: 'auto',	compare: dojo.hitch(this, "_sortDefault"), formatter: dojo.hitch(this, "_formatStringSafe")}
		];
		this._data_key_field = 0;

		this._initial_view_cols = [4, 7,  3, 6];	// indeces into _column_defs
		this._initial_sort_index = 2;	 	// change date (column index, not field index)
		this._initial_sort_asc = false;		// descending
		
		// query parameters
		this._queryErrorMessage = "CTJZH2313E";

	},
	ajaxQueryData: function()
	{
		return ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getServiceRequests();
	},
	
	// load handler for the data query
	_processQueryResult: function(response)
	{
		if(!dojo.isArray(response.Requests)) {
			response.requests = [];
		}
		var requests = response.Requests;
		this._loadGrid(response.Requests);
		return this.inherited(arguments);
	},

	_dummy:null
});

//Summary:
//TODO: write this
dojo.declare(
"ibm.tivoli.simplesrm.srm.dijit.MyApprovalsGrid",
[dijit._Widget, ibm.tivoli.simplesrm.srm.dijit.MyRecordsGrid, ibm.tivoli.simplesrm.srm.dijit.ShowRequestDetails],
{		

// constructor defines "static" properties for this class, and
// declares "instance" properties for this class
	approveBtn: null,
	rejectBtn: null,
	
 	_processErrorMessage: null,
	
constructor: function()
{
	console.log("MyApprovalsGrid.ctor()");
	
//	this._hoverer = new  ibm.tivoli.simplesrm.srm.dojo.Hoverer();
//	this._conn_onHover = dojo.connect(this._hoverer, "OnHover", this, "_showGridTooltip");
	
		// the column definitions
		// the order they are listed here is the order they will be displayed in the tooltip
	this._column_defs = [
		 {field: 'key',			name: "",							 	width: 'auto',	compare: dojo.hitch(this, "_sortDefault"), showInTooltip: false}
		,{field: 'id', 			name: 'id',								width: 'auto',	compare: dojo.hitch(this, "_sortNumber")}
		,{field: 'type', 		name: "Object Type",					width: 'auto',	compare: dojo.hitch(this, "_sortString"), showInTooltip: false}
		,{field: 'DESCRIPTION', name: this._uiStringTable.Description, 	width: '30%',	compare: dojo.hitch(this, "_sortString"), formatter: dojo.hitch(this, "_formatStringSafe")}
		,{field: 'StatusString',name: this._uiStringTable.Status,		width: 'auto',	compare: dojo.hitch(this, "_sortDefault"), formatter: dojo.hitch(this, "_formatStringSafe")}
		,{field: 'STATUSDATE',	name: this._uiStringTable.DueDate, 		width: 'auto',	compare: dojo.hitch(this, "_sortDefault"), formatter: dojo.hitch(this, "_formatDate")}
		,{field: 'CREATIONDATE',name: this._uiStringTable.CreatedDate,  width: 'auto',  compare: dojo.hitch(this, "_sortDefault"), formatter: dojo.hitch(this, "_formatDateTimeReally")} /* datetime */
		,{field: 'TARGETSTART', name: this._uiStringTable.StartDate,    width: 'auto',  compare: dojo.hitch(this, "_sortDefault"), formatter: dojo.hitch(this, "_formatDate")} /* date */
	];
	this._data_key_field = 0;

	this._initial_view_cols = [3, 5, 6];	// indeces into _column_defs
	this._initial_sort_index = 5;	 	// change date (column index, not field index)
	this._initial_sort_asc = true;		// accending
	
	this._queryErrorMessage = "CTJZH2313E";

	this._isApproval = "true";
	
	this._processErrorMessage = "CTJZH2331E";
},
buildRendering: function()
{
	this.inherited(arguments);
	
    this.approveBtn = new ibm.tivoli.simplesrm.srm.dijit.ToolbarButton(
    		{id: this.id+"_approve_btn", disabled:true, iconClass:"approve_btn", tooltip:this._uiStringTable.ApproveSelectedRequest.htmlencode(),
    			onClick: dojo.hitch( this, "_approveRequest" )
    	} );
    this.approveBtn._scroll = false;
    this.toolBar.getToolbar().addChild(this.approveBtn);
    
    this.rejectBtn = new ibm.tivoli.simplesrm.srm.dijit.ToolbarButton(
    		{id: this.id+"_reject_btn", disabled:true, iconClass:"reject_btn", tooltip:this._uiStringTable.RejectSelectedRequest.htmlencode(),
    			onClick: dojo.hitch( this, "_rejectRequest" )
    	} );
    this.rejectBtn._scroll = false;
    this.toolBar.getToolbar().addChild(this.rejectBtn);
},
	
postMixInProperties: function() {
	this.setApprContext(true);
	this.inherited(arguments);
},

ajaxQueryData: function()
{
	var statusMap = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getDomainSynonymTable('SRSTATUS');
	var params = {
		_fd: "PMZHBT_SRAPPRLIST"
	}
	var df = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getServiceRequests(params);

	return df;
},

// load handler for the data query
_processQueryResult: function(response)
{
	console.log("MyApprovalGrid._processQueryResult()");
	if(!dojo.isArray(response.Requests)) {
		response.requests = [];
	}
	var requests = response.Requests;
	this._loadGrid(requests);
	return this.inherited(arguments);
},

_processReq: function(decision)
{
	var tid = this._getSelectedTid();
	
	if (tid){
		this.showProgressSpinner();
		
		console.log("In _processReq: tid is : " + tid + " ,decision is : " + decision);
		
		var emptyStr = "";
		
		var bSuccess = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().approveRequest(tid, emptyStr, decision);

		if(!bSuccess) {
			bSuccess.addCallbacks(dojo.hitch(this, "_processQueryResult"), dojo.hitch(this, "_onProcessError"));
			bSuccess.addBoth(dojo.hitch(this, "_cleanupQuery"));
		}else{
			this._cleanupQuery();
			this.refresh();
		}
		
		return bSuccess;
	}
},
_getSelectedTid: function()
{
	var tid = null;
	
	if(this._activeRow >= 0) {	
		var selected_record = this.simpleGrid.getItem(this._activeRow);
		selected_record = this._gridItemToObject(selected_record);
		var tid = selected_record.TICKETID;
		var tuid = selected_record.TICKETUID;
		console.log("_getSelectedTid: tid is : " + tid);
		console.log("_getSelectedTid: tuid is : " + tuid);
	}
	
	return tid;		
},
_approveRequest: function()
{
	this._processReq(true);	
},
_rejectRequest: function()
{
	this._processReq(false);
},

_dummy:null
});
