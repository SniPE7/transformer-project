//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.RequestDetails");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.CommLogGrid");

dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");

dojo.require("dijit.Dialog");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.SimpleTextarea");
dojo.require("dijit.form.TextBox");
dojo.require("ibm.tivoli.tip.dijit.TextInputBox");
dojo.require("dijit.form.Form");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.MessageDialog");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.OpenHelp");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.MultipleModal");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.MyRecordsGrid");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.ItemChooserGrid");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.Utilities");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.Formatter");

/**
 * This panel shows the details from a service request. A dialog is embedded,
 * so per default it will show up as a popup dialog.
 *  
 */
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.RequestDetails",
			 [dijit._Widget,
			  dijit._Templated,
			  ibm.tivoli.simplesrm.srm.dijit.MultipleModal,
			  ibm.tivoli.simplesrm.srm.dijit.OpenHelp],
{
	_uiStringTable: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable"),
	widgetsInTemplate: true,
	templatePath: dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit",
								 "templates/RequestDetails.html"),
	parseOnLoad: true,
	hasApproval: false, /* Tell whether the panel should have a approval subpanel */
	ALN: "ALN",
	NUMERIC: "NUMERIC",
	TABLE: "TABLE",
	
	_approvalDisplay: "none",
	_Width: "630px",
	
	_data: null,
	cshKey: "PMRDP_View_DetailsSubmittedRequests.htm",
	_processErrorMessage: "CTJZH2331E",
	
	constructor: function(params) {
		console.log("RequestDetails.constructor()", params);
		if (params.approval && params.approval == true) {
			this.hasApproval = true;
			/* Also modify the help key */
			this.cshKey = "PMRDP_View_ApprovalDetails.htm";
		}
		console.log("RequestDetails.constructor()", this.hasApproval);
	},
	
	buildRendering: function() {
		console.log("RequestDetails.buildRendering()");
		try {
			this._approvalDisplay = this.hasApproval ? "" : "none";
			this._width = this.hasApproval ? "880px" : "630px";
			this.inherited(arguments);
			console.log(this._dialog, this);
			
			dojo.connect(this._tabC, "selectChild", dojo.hitch(this, this.tabSel));
			this._comml_table.setOwner(this);
			this.addHelp();
		}
		catch(ex) {
			console.group("Failed generating input form from template");
			console.error(ex);
			console.groupEnd();
			throw new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
		}
	},
	
	postCreate: function() {
		console.log("RequestDetails.postCreate()", this.hasApproval);
		if (this.hasApproval == false) {
			/* Hide the approval subpanel in this case, as well as the OK &
			 * Cancel buttons */
			console.log("Approval set as disabled. Path working.", this._apprOKBtn);
			this._viewSrGenBannerText.innerHTML = this._uiStringTable.ViewSRGenBannerNoApproval;
			dojo.style(this._apprOKBtn.domNode, "display", "none");
			dojo.style(this._apprCancelBtn.domNode, "display", "none");
		} else {
			console.log("Approval set as enabled.", this._closeBtn);
			dojo.style(this._closeBtn.domNode, "display", "none");
		}
		
		var datatypeMap = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getDomainSynonymTable('DATATYPE');
		this.ALN = datatypeMap.valueByMaxvalue("ALN");
		this.NUMERIC = datatypeMap.valueByMaxvalue("NUMERIC");
		this.TABLE = datatypeMap.valueByMaxvalue("MAXTABLE");
		
		this.inherited(arguments);
	},
	
	tabSel: function(child) {
		this._comml_table._grid.update();
	},
	
	_onKey: function(event){		
		//changes original method to support multiple modal dialogs
		if (!this.preOnKeyTest(event)) {
			return; // it's not your event
		}				
		this._dialog._onKey(event);			
	},
	
	show: function() {
		this.patchCode();
		this._dialog.supportsMultipleModal=true; //hack to support multiple modal panels
		this._dialog.show();
		this._dialog.domNode.style.zIndex = dijit._underlay.getDialogZIndex();
	},
	
	setData: function(data)
	{
		/* Case with no SR */
		if (!data.QueryPMZHBR1_SRDETResponse.PMZHBR1_SRDETSet.SR) {
			console.log("Empty data for this request");
			(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog(
											{messageId: "CTJZH2321I", type: "info"})).show();
			return;
		}
		this._data = data.QueryPMZHBR1_SRDETResponse.PMZHBR1_SRDETSet.SR[0];
		console.log("Request details data", this._data);

		this._fillGeneral(this._data);
				
		this._fillAttrTable(this._data);
		
		if (!this._data.PMZHBSLOG) {
			console.log("Trying to hide worklog");
			this._workl_banner.innerHTML =
				"<b>" + this._uiStringTable.ViewSRNoWorkl + "</b>";
			dojo.style(this._workl_banner, "padding", "20px 0 0 50px");
		} else {
			console.log("Detected worklog");
			this._buildPmzLog(this._data);
		}
		if (!this._data.COMMLOG) {
			console.log("Trying to hide commlog", this._comml_banner);
			this._comml_banner.innerHTML =
				"<b>" + this._uiStringTable.ViewSRNoComml + "</b>";
			dojo.style(this._comml_banner, "padding", "20px 0 0 50px");
			dojo.style(this._comml_table.domNode, "display", "none");
		} else {
			console.log("Detected commlog");
			console.log("this._comml_table", this._comml_table);
			this._comml_table.refreshData(this._data.TICKETUID);
		}
		this.show();
	},

	/**
	 * Set the HTML content for the PMZBHSLOG section.
	 */
	_buildPmzLog: function(data) {
		console.log("RequestDetails._buildPmzLog()", data);
		var wlog = data.PMZHBSLOG;
		var newContent = "<table class=\"infotable\"><thead><tr><td class=\"title\">" +
						 this._uiStringTable.Date +
						 "</td><td class=\"title\">" +
						 this._uiStringTable.Summary +
						 "</td></tr></thead><tbody>";
		for (i = 0; i < wlog.length; i++) {
			newContent += "<tr class=\"stroke\"><td>";
			newContent += wlog[i].TIME.formatISODateString();
			newContent += "</td><td>";
			newContent += wlog[i].FULL_MSG_TEXT ? wlog[i].FULL_MSG_TEXT.htmlencode() : "";
			newContent += "</td><td>";
		}
		newContent += "</tbody></table>";
		console.log("Setting work log content", newContent);
		this._workl_table.innerHTML = newContent;
	},

	/**
	 * Set the HTML content for the general section.
	 */	
	_fillGeneral: function(data) {
		console.log("RequestDetails._fillGeneral()", data);
		var newContent = "<table class=\"infotable\"><thead><tr><td class=\"title\">"
			+ this._uiStringTable.ViewSRDetails +
			"</td><td class=\"status\" dojoAttachPoint=" + data.STATUS +
			"</td></tr></thead><tbody>";
		var genDesc = data.DESCRIPTION;
		if (genDesc) {
			newContent += "<tr class=\"stroke\"><td>"
						+ this._uiStringTable.Description + "</td><td>"
						+ genDesc.htmlencode() + "</td></tr>";
		}
		var genReq = data.CREATEDBY;
		if (genReq) {
			newContent += "<tr class=\"stroke\"><td>"
						+ this._uiStringTable.RequestedBy + "</td><td>"
						+ genReq.htmlencode() + "</td></tr>";
		}
		var genCreated = data.CREATIONDATE;
		if (genCreated) {
			newContent += "<tr class=\"stroke\"><td>"
						+ this._uiStringTable.CreatedOn + "</td><td>"
						+ genCreated.formatISODateString({sel: "datetime"}) + "</td></tr>";
		}
		var genStart = data.TARGETSTART;
		if (genStart) {
			newContent += "<tr class=\"stroke\"><td>"
						+ this._uiStringTable.StartDateLabel + "</td><td>"
						+ genStart.formatISODateString() + "</td></tr>";
		}
		var genEnd = data.TARGETFINISH;
		if (genEnd) {
			newContent += "<tr class=\"stroke\"><td>"
						+ this._uiStringTable.EndDateLabel + "</td><td>"
						+ genEnd.formatISODateString() + "</td></tr>";
		}
		var genLastUpdate = data.CHANGEDATE;
		if (genLastUpdate) {
			newContent += "<tr class=\"stroke\"><td>"
						+ this._uiStringTable.ViewSRLastUpdate + "</td><td>"
						+ genLastUpdate.formatISODateString({sel: "datetime"}) + "</td></tr>";
		}
		var genUpdatedBy = data.CHANGEBY;
		if (genUpdatedBy) {
			newContent += "<tr class=\"stroke\"><td> "
						+ this._uiStringTable.ViewSRUpdatedBy + "</td><td>"
						+ genUpdatedBy + "</td></tr>";
		}
		newContent += "</tbody></table>";
		this._gen_table.innerHTML = newContent;
	},
	
	_fillAttrTable: function(data) {
		console.log("RequestDetails._fillAttrTable()", data);
		var newContent = "<table class=\"infotable\"><thead><tr><td class=\"title\" colspan=2>"
			+ data.DESCRIPTION +"</td></tr></thead><tbody>";
		var attrs = data.TICKETSPEC;
		/* Exit in case of empty data */
		if (attrs == undefined) {
			console.log("RequestDetails._fillAttrTable(): no ticket spec found");
			return;
		}
		for (i = 0; i < attrs.length; i++) {
			/* check what type of value this is */
			var attrType = attrs[i].ASSETATTRIBUTE[0].DATATYPE;
			var val = "";
			if (attrType == "NUMERIC") {
				val = attrs[i].NUMVALUE;
			} else if (attrType == "ALN") {
				val = attrs[i].ALNVALUE;
			} else if (attrType == "TABLE") {
				if (attrs[i].ALNVALUE)
					val = attrs[i].ALNVALUE;
			} else {
				console.log("NEW TYPE", i, attrType);
				//debugger; /* STOP! */
			}
			if ((val != undefined) && (val != "")) {
				newContent += "<tr class=\"stroke\"><td>";
				newContent += attrs[i].ASSETATTRIBUTE[0].DESCRIPTION;
				newContent += "</td><td>";
				newContent += val.toString().htmlencode();
				newContent += "</td></tr>";
			}
		}		
		newContent += "</tbody></table>";
		this._attr_table.innerHTML = newContent;
	},
	
	_fillCommLogDetails: function(index) {
		console.log("RequestDetails._fillCommLogDetails()", index);
		
		var item = this._data.COMMLOG[index];
		var newContent2 = "<table class=\"infotable\"><thead><tr><td class=\"title\">"
			+ this._uiStringTable.Details + "</td></tr></thead><tbody>";
		newContent2 += "<tr><td>" + (item.MESSAGE ? item.MESSAGE.htmlencode() : "") + "</td></tr>";
		newContent2 += "</tbody></table>";
		this._comml_details_det.innerHTML = newContent2;
	},
	
	_onOKClick: function(evt) 
	{
		
		var tid = this._data.TICKETID;
		var approveCheck = dijit.byId(this.id + "_approve").checked		
		//var summaryInfo = dijit.byId(this.id + "_summary").value;	

		//var detailInfo = dijit.byId(this.id + "_detail").getValue();
		var detailInfoWidget = dijit.byId(this.id + "_detail");		
		var detailInfo = detailInfoWidget.getValue();
		
		//var detailInfo = dijit.byId(this.id + "_detail").value;

		//console.log("_onOKClick: tid is : " + tid + " ,approveCheck is : " + approveCheck + " ,detailInfo is : " + detailInfo);
		
		detailInfoWidget.validate();
		
		var hasError = detailInfoWidget._isInError;

		if (hasError){
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("Invalid Detail Input Value!"); 
			(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2336E"})).show();
			dojo.stopEvent(evt);			
		}else {
			var bSuccess = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().approveRequest(tid, detailInfo, approveCheck);

			if(!bSuccess) {
				new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("Failure approving/rejecting a request"); 
				(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2331E"})).show();
				dojo.stopEvent(evt);
			}
			else {
				this.hide();
			}
			return bSuccess;			
		}
	},
	
	hide: function()
	{
		this._dialog.hide();
	},
	
	/* Help stuff */

	//handler:used when user clicks '?'
	openHelp: function(event){
		this.openHelpWindow(this.cshKey);
	},

	//error handler: used when help url can't be retrieved from server
	_errorRetrievingUrl:function() {
		(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2302E"})).show();
	},

	//sets cshKey, adds '?' to title bar, connects help events 
	addHelp: function() {
		var helpNode = document.createElement("span");
		
		dojo.addClass(helpNode , "dijitDialogHelpIcon");			
		dojo.attr(helpNode , "id" , this.id + "_visual_" + this.cshKey);
		dojo.attr(helpNode , "title" , this._uiStringTable["Help"]);
		dojo.attr(helpNode , "tabindex" , 0);
		
		var res = dojo.place(helpNode, this._dialog.closeButtonNode ,"before");
		this.connect(helpNode , "onclick" , "openHelp");
		this.connect(helpNode , "onmouseenter","_onHelpEnter");		
		this.connect(helpNode , "onmouseleave","_onHelpLeave");
		this.connect(helpNode , "onkeypress" , "_onEnterPressed");
		
		var textHelpNode = document.createElement("span");
					
		dojo.addClass(textHelpNode , "closeText");
		dojo.attr(textHelpNode , "id" , this.id + "_text_" + this.cshKey);
		dojo.attr(textHelpNode , "title" , this._uiStringTable["Help"]);			
		
		var textNode = document.createTextNode("?");
		dojo.place(textNode , textHelpNode );
		
		dojo.place(textHelpNode, helpNode);
		
		this.connect(this.domNode,"keypress","_onHelpKey");
	},

	_onHelpEnter: function() {
		var helpNode = dojo.query(".dijitDialogHelpIcon",this.titleBar);
		dojo.addClass(helpNode[0], "dijitDialogHelpIcon-hover");
	},

	_onHelpLeave: function(){
		var helpNode = dojo.query(".dijitDialogHelpIcon-hover",this.titleBar);
		dojo.removeClass(helpNode[0] , "dijitDialogHelpIcon-hover");
	},

	_onHelpKey: function(event) {
		//console.log("current: " + event.keyCode + " / " + event.charOrCode + " target: " + dojo.keys.HELP); 
		
		// help key accessible from whole panel
		if(event.keyCode == dojo.keys.F1){
			
			// on IE dojo.stopEvent() is not enough to cancel this event
			// "onhelp" attribute is valid for IE only
			if (dojo.isIE){ 
				document.onhelp = function(){return false;};
				window.onhelp = function(){return false;};
			}
			
			if(this.cshKey && this.openHelp){				
				dojo.stopEvent(event);			
				this.openHelp(event);
				return false;
			}
		}
		return true;
	},
	
	_onEnterPressed: function(event) {
		//open only if Enter was pressed on help icon
		if ((event.keyCode == dojo.keys.ENTER) && 
			(dojo.hasClass(event.target ,"dijitDialogHelpIcon"))){
			dojo.stopEvent(event);
			this.openHelp(event);
			return false;
		}
		return true;
	}

});

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.CommLogGrid",
			 [ibm.tivoli.simplesrm.srm.dijit.ItemChooserGrid,
			  ibm.tivoli.simplesrm.srm.dojo.Formatter], {
	_uiStringTable: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit",
											  "uiStringTable"),
	autoQuery: false,

	constructor: function() {
		this.gridLayout = [
	      	{
	      	 name: this._uiStringTable.Application,
	      	 field: 'app',
	      	 width: 'auto',
	      	 compare: ibm.tivoli.simplesrm.srm.dojo.data.Comparator.stringCompare
	      	},
	     	{
	      	 name: this._uiStringTable.To,
	      	 field: 'sendto',
	      	 width: 'auto',
	      	 compare: ibm.tivoli.simplesrm.srm.dojo.data.Comparator.stringCompare
	      	},
	      	{
	      	 name: this._uiStringTable.From,
	      	 field: 'sendfrom',
	      	 width: 'auto',
	      	 compare: ibm.tivoli.simplesrm.srm.dojo.data.Comparator.stringCompare
	      	},
	      	{
	      	 name: this._uiStringTable.Date,
	      	 field: 'date',
	      	 width: 'auto',
	      	 compare: ibm.tivoli.simplesrm.srm.dojo.data.Comparator.stringCompare,
	      	 formatter: dojo.hitch(this, "_formatDatetime")
	      	},
	      	{
			 name: this._uiStringTable.Subject,
			 field: 'subject',
			 width: '30%',
			 compare: ibm.tivoli.simplesrm.srm.dojo.data.Comparator.stringCompare,
			 formatter: dojo.hitch(this, this._noWrap)
			},
	      	
   		];
   		
   		this.keyField = "msgid";
	},

	queryData: function() {
		console.log("CommLogGrid.queryData(): ", this._ticketUid);
		var d = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getRequestDetails(
														{id : this._ticketUid});
		d.addCallback(function(response) {
			var logItems = response.QueryPMZHBR1_SRDETResponse.PMZHBR1_SRDETSet.SR[0].COMMLOG;
			response.data = [];
			for(var i in logItems) {
				var item = logItems[i]; 
				response.data.push({app: item.CREATEBY,
									date: item.CREATEDATE,
									subject: item.SUBJECT,
									sendto: item.SENDTO,
									sendfrom: item.SENDFROM,
									msgid: i});
			}
		});
		return d;
	},
	
	refreshData: function(ticketUid) {
		this._ticketUid = ticketUid;
		this.refresh();
		this._grid.update();
	},
	
	setOwner: function(owner) {
		this._owner = owner;
	},
	
	onChange: function(item) {
		console.log("On Change CALLED", item, this._owner, this._grid.selection.selectedIndex);
		this._owner._fillCommLogDetails(this._grid.selection.selectedIndex);
	}

});
