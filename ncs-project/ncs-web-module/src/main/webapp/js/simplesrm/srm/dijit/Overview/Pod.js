//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Overview.Pod");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Overview.View");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Overview.DataTable");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.Overview.Details");

dojo.require("dojo.i18n");
dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.form.Button");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.DialogUnderlay");
dojo.require("dijit.Dialog");
dojo.require("dijit._DialogMixin");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.MultipleModal");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.OpenHelp");

// Summary:
//	Overview.Pod is the rectangular box holding the overveiw data.
//	Overview.View is the contents contained within the Pod
//	Overview.DataTable is a 2-column table, with the styling specified for the Pods
//
//	It's up to classes derived from Overview.Pod to create their own view, and know how
//	to get their data, making each Pod type self-contained.
//
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Overview.Pod", [dijit._Widget, dijit._Templated],
{
	widgetsInTemplate: true,
	templateString: '<div><table class="pod" border="0" cellpadding="0" cellspacing="0">\n'
				+ '	<tr><td class="top-lt"></td><td class="top-ctr"></td><td class="top-rt"></td></tr>\n'
				+ '	<tr><td class="lt"></td><td class="ctr">\n'
				+ '		<div class="OverviewPod">\n'
				+ '				<h2 class="title">${heading}</h2>\n'
				+ '				<div dojoAttachPoint="_view" dojoType="${viewType}"></div>\n'
				+ '				<div dojoAttachPoint="footer" class="footer"><a href="${detailsUrl}" dojoAttachPoint="detailsLink">${detailsLinkLabel}</a></div>\n'
				+ '			</div>\n'
				+ '		</td><td class="rt"></td></tr>\n'
				+ '	<tr><td class="bot-lt"></td><td class="bot-ctr"></td><td class="bot-rt"></td></tr>\n'
				+ '</table></div>',

	viewType: "ibm.tivoli.simplesrm.srm.dijit.Overview.View",
	detailsType: "",
	autoRefreshProperty: "",
	heading: "",
	detailsLinkLabel: "",
	detailsUrl: "#",
	autoRefreshInterval: Number.NaN,
	_mostRecent: 0,		// time of the most recent record.  Used to compare records when auto-updating
	_autoRefreshTimerId: -1,

	_view: null,		// my Overview.View object
	_details: null,		// my details pop-up window
	_detailsData: null,	// my details data
	
	constructor: function()
	{
		console.log("OverviewPod.Pod.ctor");
	},
	postMixInProperties: function()
	{
		this.inherited(arguments);
		if(isNaN(this.autoRefreshInterval) && this.autoRefreshProperty.length > 0) {
			this.autoRefreshInterval = parseInt(ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty(this.autoRefreshProperty));
			if(isNaN(this.autoRefreshInterval) || this.autoRefreshInterval < 0) {
				this.autoRefreshInterval = Number.NaN;
			}
		}
	},
	postCreate: function()
	{
		this.inherited(arguments);
		this.connect(this.detailsLink, "onclick", this.onShowDetails);
	},
	// requery data
	refresh: function()
	{
		// override in derived pods to requery data
	},
	// _onDataReady should be called when the derived class' data is updated
	//	If you provide an implementation in your derived class, you should probably
	//	call this.inherited(arguments)
	_onDataReady: function()
	{
		dojo.removeClass(this.footer, "invisible");
		try {
			this.onRefresh(this._detailsData);
		}
		catch(ex) {
			console.log("Error in onRefresh handler");
		}
	},
	_refreshErrorCount: 0,
	__refreshError: function(response)
	{
		++this._refreshErrorCount;
		new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMMessage("RequestPod refresh failure: " + response, "warning");
		this._resetPoll();
	},
	_cancelPoll: function()
	{
		if(this._autoRefreshTimerId > 0) {
			window.clearTimeout(this._autoRefreshTimerId);
			this._autoRefreshTimerId = 0;
		}
	},
	_resetPoll: function()
	{
		if(!isNaN(this.autoRefreshInterval)) {
			this._cancelPoll();
			this._autoRefreshTimerId = window.setTimeout(dojo.hitch(this, this._poll), (this._refreshErrorCount +1) * this.autoRefreshInterval);
		}
	},
	_poll: function()
	{
		// override for your autoupdate polling query
	},
	// Summary:
	// 	event fired when the pod is refreshed with new data
	// 	latest_data: the new data set
	onRefresh: function(latest_data)
	{
	},
	// Summary:
	//	Called when the user clicks the details link
	onShowDetails: function()
	{
		// lazy create
		if(undefined == this._details) {
			dojo.require(this.detailsType);
			var cls = dojo.getObject(this.detailsType);
			this._details = new cls();
		}
		this._details.refresh(this._detailsData);
		this._details.show();
	}
});
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Overview.View", [dijit._Widget, dijit._Templated],
{
	_detailsData: null
});

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Overview.DataTable", [dijit._Widget, dijit._Templated],
{
	templateString: '<div class="OverviewPodDataTable">\n' +
					'	<table class="OverviewPodDataTable" dojoAttachPoint="overviewPodDataTable">\n' +
					'		<caption dojoAttachPoint="tableCaption">${heading}</caption>\n' +
					'		<tbody></tbody>\n' +
					'	</table>\n' +
					'</div>\n',
	heading: "",
	maxRows: 5,
	
	addRow: function(leftcol, rightcol)
	{
		if(undefined == this.overviewPodDataTable) return null;
		var i = 0;
		try {
			i = this.overviewPodDataTable.rows.length;
		}
		catch(ex) {}
		if(i > this.maxRows) return null;

		var r = this.overviewPodDataTable.insertRow(i);
		var cell = r.insertCell(0);
		cell.className = "left";
		cell.innerHTML = undefined == leftcol ? "..." : leftcol;
				
		cell = r.insertCell(1);
		cell.className = "right";
		cell.innerHTML = undefined == rightcol ? "..." : "<span class='nowrap'>" + rightcol + "</span>";
		return r;
	},
	deleteRow: function(rowIndex)
	{
		this.overviewPodDataTable.deleteRow(rowIndex);
	},
	clear: function()
	{
		var nrows = this.overviewPodDataTable.rows.length;
		for(var i = 0; i < nrows; ++i) {
			this.deleteRow(0);
		}
	},
	_setHeadingAttr: function(newcap)
	{
		this.heading = this.tableCaption.innerHTML = newcap;
	}
});

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.Overview.Details", 
		[dijit._Widget,
		 dijit._Templated,ibm.tivoli.simplesrm.srm.dijit.MultipleModal,
		 ibm.tivoli.simplesrm.srm.dijit.OpenHelp],
{
	//used to control tabbing/focus 
	_getFocusItems : dijit._DialogMixin.prototype._getFocusItems,
	widgetsInTemplate: true,
	templatePath: dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit.Overview", "OverviewDetails.html"),
	//templateString: "",
	detailsType: "",
	headingText: "",
	closeBtnLabel: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").Close,
	helpBtnLabel: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").Help,
	
	_detailsData: {},
	_fadeTime: 1000,	// millisecs
	_animIn: null,
	_animOut: null,
	_cshKey: "",
	_firstFocusItem: null,	//read-only,set by _getFocusItems
	_lastFocusItem: null, //read-only,set by _getFocusItems
		
	constructor: function(params){
		//console.log("Overview.Details: constructor");
		/*if (params.cshKey) {
			this._cshKey = params.cshKey;
		} else {
			console.log("Details: no help file given");
		}*/
	},
	
	postCreate: function()
	{
		try {
			// create the faders
			var node = this.domNode;
			this._animIn = dojo.fadeIn({
				node: node, 
				duration: this._fadeTime,
				//set focus on the first (focusable) element on panel 
				onEnd: dojo.hitch(this ,function(){
					this._getFocusItems(this.domNode);
					//console.log("first focus:"+ this._firstFocusItem);
					dijit.focus(this._firstFocusItem);
				})
			});
			
			this._animOut = dojo.fadeOut({
				node: node,
				duration: this._fadeTime,
				onEnd: function(){
					node.style.visibility="hidden";
					node.style.top = "-9999px";
				}
			 });
			
			// start hidden
			dojo.style(this.domNode, {
				visibility:"hidden",
				position:"absolute",
				display:"",
				top:"-9999px"
			});
			dojo.body().appendChild(this.domNode);

			this.connect(window, "onscroll","layout");
			this.connect(window, "onresize","resize");			
//			// load my data
//			this.refresh();
//			this.resize();
		}
		catch(ex) {
			ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("Failed creating ibm.tivoli.simplesrm.srm.dijit.Overview.Details");
			ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
		}
		this.inherited(arguments);
	},
	destroy: function()
	{
		if(this._animIn.status() == "playing"){
			this._animIn.stop();
		}
		if(this._animOut.status() == "playing"){
			this._animOut.stop();
		}
		if(this._keyhandle) {
			dojo.disconnect(this._keyhandle);
		}
	},
	
	_onkey: function(evt)
	{	/*
		 * uses _onKey method from dijit.Dialog
		 * features:
		 * 1) closing with Esc
		 * 2) tabbing only inside panel
		 * 3)if event belongs to other modal panel(widget) it doesn't proccess it
		 */
		if(!this.preOnKeyTest(evt)){
			return; // it's not your event
		}			
		var onKey = dojo.hitch(this,dijit.Dialog.prototype._onKey);
		onKey(evt);		
	},

	refresh: function()
	{
		if(this.detailsWidget && "function" == typeof this.detailsWidget.refresh)
			this.detailsWidget.refresh();
	},
	resize: function()
	{
		this.layout();
	},
	_position: function(){
		// partially copied from dijit.Dialog._position
		
		var node = this.containerNode;
		var viewport = dijit.getViewport();
		var theapp = dojo.coords("srmapp");
		var left = viewport.l > theapp.x ? viewport.l : theapp.x;
		var top = viewport.t > theapp.y ? viewport.t : theapp.y;
		var width = viewport.w < theapp.w ? viewport.w : theapp.w;
		var height = viewport.h < theapp.h ? viewport.h - top : theapp.h;
		dojo.style(node,{
			left: left + "px",
			top: top + "px",
			width: width + "px",
			height: height + "px",
			position: "absolute"
		});

	},
	layout: function(){
		// copied from dijit.Dialog._position
		// summary:
		//		Position the Dialog and the underlay		
		if(this.domNode.style.visibility != "hidden"){
			dijit._underlay.layout();
			this._position();
		}
	},	
	
	onCancel: function(){
		//	required by _onkey (dijit.Dialog._onKey)
		this.hide();
	},
		
	_keyhandle:null,
	show: function()
	{			
		// modify dijit.DialogUnderlay
		this.patchCode(); 		
		
		//set dijit._underlay (if not set already by other modal widget)
		// code from dijit.Dialog._setup
		var underlayAttrs = {
			dialogId: this.id,
			"class": ""			
		};				
		var underlay = dijit._underlay;
		if(!underlay){ 
			underlay = dijit._underlay = new dijit.DialogUnderlay(underlayAttrs); 
		}
		underlay.show();
				
		//set z-index above all current modal panels
		this.domNode.style.zIndex = dijit._underlay.getDialogZIndex();
				
		if(this._animOut.status() == "playing"){
			this._animOut.stop();
		}
		dojo.style(this.domNode, {
			opacity:0,
			visibility:""
		});
		this.resize();
		//onkeydown doesn't set evt.charOrCode which is used by _onkey (dijit.Dialog._onKey)
		this._keyhandle = dojo.connect(dojo.doc.documentElement, "onkeypress", this, this._onkey);
		this._animIn.play();
	},
	hide: function()
	{		
		dijit._underlay.hide();
		
		if(this._animIn.status() == "playing"){
			this._animIn.stop();
		}
		dojo.disconnect(this._keyhandle);
		this._animOut.play();
	},
	/**
	 * Displays the help related to this panel. The cshKey variable
	 * indicates the file name of the help document to open.
	 */
	showHelp: function() {
		this.openHelpWindow(this._cshKey);
	}
});
