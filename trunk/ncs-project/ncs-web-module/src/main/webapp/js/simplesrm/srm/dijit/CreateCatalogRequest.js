//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

// this file provides the definition of the CreateCatalogRequest class
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.CreateCatalogRequest");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.CreatorPopupDialog");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.CreatorFactory");

// include modules
dojo.require("dijit.Dialog")
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dojo.parser");
dojo.require("dojo.date");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.WidgetBase");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.ProgressSpinner");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.PopupDialog");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.MessageDialog");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.InlineMessage");

// the CreateCatalogRequest class is a widget that makes an
// XMLHttpRequest to a proxy servlet to reach an SRM object structure
// web service that can create service catalog requests
// Events:
//	onSrmRequestCreated(null)
//
// TODO: parameterize for the type of request.
dojo.declare(
	"ibm.tivoli.simplesrm.srm.dijit.CreateCatalogRequest",
	[dijit._Widget, dijit._Templated, ibm.tivoli.simplesrm.srm.dijit.WidgetBase],
{
	_requestAttributeCache: new Array(),	// creates a static member
	
	//context sensitive help key. mapped on server side to help topic
	// no key == no csh help 
	cshKey: "",
	
	widgetsInTemplate: true,
	// swizzle this per the type of request we're creating
	templateString: null,
	templatePath: null,
	ALN: "ALN",
	NUMERIC: "NUMERIC",
	TABLE: "TABLE",

	requestName: '',
	headingText: '',
	description: '',
	imagesPath: '',
	requestDetails: null,
	_localized_strings: null,	// will contain the i18n string table for the specific request type
	
	// input parameters
	requestType: '',		// required
	ItemNum: '',			// required
	ObjectType: 'Catalog', 	// required
	ItemSetID: '',		// need this or MRLineID
	MRLineID: '',			// need this or ItemSetID
	readOnly: false,

	_bStarted: false,		// startup will get called when this widget is created, and again when the popup dialog is initialized.
							// derived classes can use this flag to see if they've already been startedW
	
	_inputParams: null,

	constructor: function(/*ojbect*/params, /*DOMNode*/domNode)
	{
		console.log("CreateCatalogRequest.ctor");
		this.imagesPath = this.getRelativePath("images");
		this._inputParams = params;
	},
	/*
	** Default implementation loads i18n strings and sets the templatePath as a function of the requestName
	** If you need to do something different, override.
	*/
	postMixInProperties: function()
	{
		console.log("CreateCatalogRequest.postMixInProperties");
		
		// context sensitive help key is the ItemNum
		this.cshKey = this.ItemNum + ".htm";
		
		if(this.MRLineID) {
			this.requestDetails = this.getRequestDetails(this.MRLineID);
		}
		else {
			this.requestDetails = this.queryRequestTypeDetails(this.ItemNum, this.ItemSetID);
		}
		if( this.requestDetails ) {
			var offStatusMap = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getDomainSynonymTable('ITEMSTATUS');
			var offStatus = offStatusMap.valueByMaxvalue("ACTIVE");
			if(this.requestDetails.Status != offStatus) {   // This can only happen if the request's status has been changed since the user logged on
				var cache_key = this.ItemNum + "_" + this.ItemSetID;
				this._requestAttributeCache[cache_key] = null;
				throw "CTJZH2325E";
			}
			if(undefined === this.requestDetails.Attribute) {
				this.requestDetails.Attribute = [];
			}
			else {
				this.requestDetails.Attribute = this.requestDetails.Attribute.sort(dojo.hitch(this, "_cmpDisplaySequence"));
			}
			this.requestName = this.requestDetails.Description.htmlencode();
			this.headingText = this.requestName.htmlencode();
			this.description = (undefined != this.requestDetails.LongDescription ? this.requestDetails.LongDescription : this.requestDetails.Description).htmlencode();
			
		} else {
			throw "CTJZH2301E";
		}
		for(var i in this.requestDetails.Attribute) {
			var attr = this.requestDetails.Attribute[i];
			attr.Description = attr.Description.htmlencode();			
		}
		
		// default templatePath is a function of the request type
		if(!this.templateString && !this.templatePath) {
			//this.templatePath = dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit.request", "templates/"+this.requestType+".html");
			var mod = this.declaredClass.substring(0, this.declaredClass.lastIndexOf("."));  
			var typ = this.declaredClass.substring(this.declaredClass.lastIndexOf(".")+1);
			this.templatePath = dojo.moduleUrl(mod) +  "templates/" + typ + ".html";
		}
		this.inherited(arguments);
	},
	buildRendering: function()
	{
		console.log("CreateCatalogRequest.buildRendering");
		
		try {
			this.inherited(arguments);
		}
		catch(ex) {
			console.group("Failed generating input form from template");
			console.error(ex);
			console.groupEnd();
			throw new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);;	// really have to bail at this point.
		}
		

		if( !this.readOnly ) {
			var the_form = dojo.query("form", this.domNode)[0];
			var d = document.createElement('div');
			var uname = ibm.tivoli.simplesrm.srm.dijit.loggedInUsername;
			d.innerHTML = '<input type="hidden" name="srmreq" value="create" >'
				+ '<input type="hidden" name="user" value="'+uname+'" >'
				+ '<input type="hidden" name="requestedby" value="'+uname+'" >'

				// common attributes
				+ '<input type="hidden" name="csid" value="'+this.requestDetails.ClassStructureID+'" >'
				+ '<input type="hidden" name="CLASSIFICATIONID" value="'+this.requestDetails.ClassificationID+'" >'
				+ '<input type="hidden" name="ItemNum" value="'+this.requestDetails.ItemNum+'" >'
				+ '<input type="hidden" name="ItemSetID" value="'+this.requestDetails.ItemSetID+'" >'
			the_form.appendChild(d);	

//			// attributes may have been added to the offering that aren't in custom forms
//			// guarantee they're not missing.
//			// If you create a custom form, and purposfully want to omit an attribute, create a hidden input
//			// element and mark it disabled.
//			for(var i in this.requestDetails.Attribute) {
//				var attr = this.requestDetails.Attribute[i];
//				if(attr.Mandatory && attr.Description !== "TSAM Substitution Expression") {
//					if(dojo.query("[name="+attr.AssetAttrID+"]", the_form).length == 0 &&
//					  (dojo.isIE && dojo.query("[submitName="+attr.AssetAttrID+"]", the_form).length == 0)) { // WTF?
//						var ipt = document.createElement("input");
//						ipt.type = "hidden";
//						ipt.name = attr.AssetAttrID;
//						ipt.id = this.id + "_" + attr.AssetAttrID;
//						d.appendChild(ipt);
//					}
//				}
//			}				
		}	
	},
	postCreate: function()
	{
		this.inherited(arguments);

	},
	startup: function()
	{
		console.log("CreateCatalogRequest.startup");
		if(this._bStarted)
			return;
		this._bStarted = true;
		this.inherited(arguments);

		var datatypeMap = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getDomainSynonymTable('DATATYPE');
		this.ALN = datatypeMap.valueByMaxvalue("ALN");
		this.NUMERIC = datatypeMap.valueByMaxvalue("NUMERIC");
		this.TABLE = datatypeMap.valueByMaxvalue("MAXTABLE");
		
		if(this.readOnly) {
			this.disableAll(true);
		}

		// set defaults
		// TODO: this doesn't handle when derived classes asyncronously retrieve table value lists
		// that my need to get a default value
		var attributes = this.requestDetails.Attribute;
		for(var i = 0; i < attributes.length; ++i) 
		{
			try {
				var attr = attributes[i];
				var val = this._getAttrValue(attr);
				if("number" === typeof val || ("string" === typeof val && val.length > 0)) {
					var obj_id = this.id + '_' + attr.AssetAttrID;
					var widget = dijit.byId(obj_id);
					if(widget) {
						try {
							if("function" === typeof widget.attr)
								widget.attr("value", val);
							else if("function" === typeof widget.setValue)
								widget.setValue(val);
						}
						catch(ex) {
						}
					}
					else { 
						var html_obj = dojo.byId(obj_id);
						if(html_obj && "INPUT" === html_obj.tagName.toUpperCase() ) {
							html_obj.value = val;
						}
					}
				}
	
			}
			catch(ex) {
				new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
				console.group("Failed setting default on form field for ", attr.AssetAttrID);
				console.error(ex);
				console.groupEnd();
			}
		}
	},
	destroy: function()
	{
		this.disableAll(false);
		this.inherited(arguments);
	},
	reset: function()
	{
		dojo.byId(this.id + "_input_form").reset();
	},
	// query attributes for the given request type
	queryRequestTypeDetails: function(itemnum, itemsetid)
	{
		var cache_key = this.ItemNum + "_" + this.ItemSetID;
		var attrs = this._requestAttributeCache[cache_key];
		if( !attrs ) {
			var deferred = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getRequests({ItemNum: this.ItemNum, ItemSetID: this.ItemSetID, sync: true});
			deferred.addCallback(dojo.hitch(this, function(response)
			{
				attrs = response.Request[0];
				// add in common attributes that are not part of the offering proper
				if(undefined === attrs.Attribute) {
					attrs.Attribute = [];
				}
				attrs.Attribute.push({AssetAttrID: "requestedfor", DataType: this.ALN, ALNValue: ibm.tivoli.simplesrm.srm.dijit.loggedInUsername, Description: "Requested For", Hidden: "false", Mandatory: "true", ReadOnly: "false"});

				this._requestAttributeCache[cache_key] = attrs;
				return response;
			}));
		}
		return attrs;
	},
	getRequestDetails: function()
	{
		//return this._doGetRequestDetails({MRLineID: this.MRLineID, ObjectType: this.ObjectType});
		var request_details = null;
		var deferred = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getRequests({id: this.MRLineID, ObjectType: this.ObjectType, sync: true});
		deferred.addCallback(function(response)
		{
			try {
				request_details = response.Request[0];
			}
			catch(ex) {
				(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog("CTJZH2302E")).show();
			}
			return response;
		});
		return request_details;
	},
	// get an attribute's value
	// the value may come from 1 of 2 places
	// 1. it may have been provided to our constructor and is stored in this._inputParams
	// 2. the passed-in attr (from requestDetails) may povide a default value 
	_getAttrValue: function(attr)
	{
		var value = this._inputParams[attr.AssetAttrID];
		if(undefined == value) {
			if(this.ALN === attr.DataType) {
				value = dojo.isString(attr.ALNValue) ? attr.ALNValue : "";
			}
			else if(this.NUMERIC === attr.DataType) {
				value = dojo.isString(attr.NumValue) ? attr.NumValue : "";
			}
			else if(this.TABLE === attr.DataType) {
				value = dojo.isString(attr.TableValue) ? attr.TableValue : "";
			}
			else {
				console.log("Unexpected attribute data type: '%s'", attr.DataType);
			}
		}
		if(this.NUMERIC === attr.DataType) {
			value = parseInt(value);
			if(isNaN(value)) {
				value = "";
			}
		}
		return value;	
	},
	_getPostData: function()
	{
		// by default, a creation request sends all the form data.
		// if you need to so something special, override 
		var form_id = this.id + "_input_form";
		return dojo.formToObject(form_id);
	},
	
	// invoke an ajax request to create a request
	createRequest: function()
	{		
		console.log("CreateCatalogRequest.createRequest");

		// the form never actually is submitted, but give subclasses
		// an opportunity to do some last minute processing.
		try {
			var bContinue = this.onSubmit();
			if(!bContinue) {
				return null;
			}
		}
		catch(ex){
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex, "warning");
			console.warn("Derived class onSubmit threw an exception: ", ex);
		}
		
		var deferred = null;
		try {
			var form_id = this.id + "_input_form";
			var post_data = this._getPostData();
			if(post_data != null) {
				form_id = null;
				for(var k in post_data) {
					// There is a bug in Dojo 1.3.1 fixed by http://trac.dojotoolkit.org/changeset/18030
					// That causes radio buttons to render as arrays with all values after the first one blank
					// However a multi select box or checkbox array can also have multiple values
					// so even when fixed we have to be able to handle array types
					var post_datum = post_data[k];
					if ( dojo.isArray(post_datum) ) {
						for (var pdidx = 0; pdidx < post_datum.length; pdidx++) {
							//Todo: remove this if statement hack after upgrading DOJO
							if("" === post_datum[pdidx] ) {
								post_datum.splice(pdidx,1);
								pdidx--;
							} else if ("string" === typeof post_datum[pdidx]) {
								post_datum[pdidx] = post_datum[pdidx].trim().safeencode();
							}
						}
						post_data[k] = post_datum;
					} else if ("string" === typeof post_datum) {
						post_data[k] = post_datum.trim().safeencode();
					}
				}
			}
			console.log("CreateCatalogRequest.createRequest: calling srmQuery.createRequest()");
			deferred = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().createRequest(post_data, this.requestDetails);
			deferred.addCallbacks( dojo.hitch(this, "_processCreateResponse"), dojo.hitch(this, "_processCreateError"))
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			console.log("CreateCatalogRequest.creatRequest: failed miserably");
			console.log(ex);
			if("string" === typeof ex) {
				(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: ex})).show();
			}
			else {
				(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2342E"})).show();
			}
		}
		return deferred;
	},
	cancelRequest: function()
	{
		this.disableAll(false);
	},
	onSubmit: function()
	{
		var bValid = false;	// assume the worst
		try{			
			bValid = this.isValid();
			
			console.log("In CreateCatalogRequest.js: bValid is : " + bValid);
			
			if (bValid){
				bValid = dojo.query("input[name]", this.domNode).every(function(in_elem)
						{
							var widget = dijit.getEnclosingWidget(in_elem);
							if(widget) {
								var bValid = true;
								if("function" === typeof widget.isInError) {
									widget.validate();
									if(widget.isInError()) {
										bValid = false;
									}
								}
								else if("function" === typeof widget.isValid) {
									if( !widget.isValid() ) {
										bValid = false;
									}
								}
								if(!bValid) {
									if(widget.declaredClass.match(/^ibm\.tivoli\.tip\.dijit/)) {
										widget.setFocus();
										widget.validate();	// tip widgets have to have focus before it will show error
									}
									else if("function" === typeof widget.focus)
										widget.focus();
								}
								return bValid;
							}
						});
									
			}
		}catch(ex){
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex, "warning");
			console.warn("CreateCatalogRequest.onSubmit: failed  ", ex);
			bValid = false;
		}
		if( !bValid ) {
			(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2305E"})).show();
		}
		return bValid;

	},
	isValid: function()
	{
		return true;
	},
	_processCreateResponse: function(response)
	{
		var status = -1;
		var bUseRest = Boolean(parseInt(ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("CreateViaRest")));
		if(bUseRest) {
			status = 0;
		}
		else {
			// parse the resonse.  just because the request succeeded 
			// doesn't mean the request was created successfully
			var msg = "";
			for(var i = 0; i < response.TEXT.length; ++i) {
				var r = response.TEXT[i];
				if(undefined !== r.Message) {
					msg += "\n" + r.Message;
				}
				if(undefined !== r.ReturnCode) {
					status = parseInt(r.ReturnCode);
				}
			}
		}
		if(0 == status) {
			console.log("CreateCatalogRequest: request created:" + msg);
			this._fireSrmRequestCreated();
		}
		else {
			(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2306E"})).show();
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(new Error("request failed (" + status + "):" + msg));
		}
	},
	
	_processCreateError: function(create_response) {
		console.group("Error creating catalog request")
		console.log(create_response);

		// If the failure is due to the offering being modified so it is no longer available show a specific message
		var deferred = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getRequests({ItemNum: this.ItemNum, ItemSetID: this.ItemSetID, sync:true});
		deferred.addCallback(function(req_response)
		{
			attrs = req_response.Request[0];
			var offStatusMap = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getDomainSynonymTable('ITEMSTATUS');
			var offStatus = offStatusMap.valueByMaxvalue("ACTIVE");
			if( attrs && attrs.Status != offStatus) {
               (new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2325E"})).show();
               var cache_key = this.ItemNum + "_" + this.ItemSetID;
               this._requestAttributeCache[cache_key] = null;
            } else {
    			if("string" === typeof create_response) {
    				(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: create_response})).show();
    			}
    			else {
    				(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2335E"})).show();
    			}
            }
			return req_response;
		});
      
		console.groupEnd();
	},

	
	// ------------- event handlers -----------------------
	_fireSrmRequestCreated: function() {
		console.log("CreateCatalogRequest._fireSrmRequestCreated()");

		if(undefined == this.iContext) {
			if("function" == typeof this.onSrmRequestCreated) {
				this.onSrmRequestCreated();
			}
		}
		else {
			console.log("CreateCatalogRequest is firing iWidget event SrmRequestCreated");
			this.iContext.iEvents.fireEvent("SrmRequestCreated", "string", "");
		}
	},
	onSrmRequestCreated: function()
	{
		console.log("CreateCatalogRequest.onSrmRequestCreated");
		// TODO: figure out why.
		// Sometimes you need to have an event handler function defined in the class (like this one)
		// for dojo.connect to wire external event handlers successfully, and sometimes
		// you don't.  Why?
	},
	_cmpDisplaySequence: function(a, b)
	{
		var dsa = parseInt(a.DisplaySequence);
		var dsb = parseInt(b.DisplaySequence);
		var diff = (isNaN(dsa) ? 1000000 : dsa) - (isNaN(dsb) ? 1000000 : dsb);
		return diff;
	},
	_working: function(b)
	{
		if(this.domNode)
			dojo.style(this.domNode, "cursor", b ? "wait" : "default");
	},
	// prevent the user from interacting
	_screen: null,
	disableAll: function(bState)
	{
		try {
			var theform = dojo.query("form", this.domNode)[0];
			if(bState) {
				// disable
				var cw = dojo.query("div.content_wrapper", this.domNode)[0];
				var box = dojo.contentBox(theform);
				if(this._screen == null) {
					this._screen = document.createElement("div");
				}
				dojo.style(theform, "position", "relative");
				dojo.style(this._screen, {
					position: "absolute",
					left: box.l + "px",
					top: box.t + "px",
					width: ((cw.scrollWidth > box.w) ? cw.scrollWidth : box.w) + "px",
					height: ((cw.scrollHeight > box.h) ? cw.scrollHeight : box.h) + "px",
					cursor: "not-allowed",
					margin: "0px",
					padding: "0px",
					zIndex: 1000,
					backgroundColor: "transparent"
				});
				dojo.place(this._screen, theform, "first");
			}
			else {
				// enable
				try {
					if(this._screen)
						theform.removeChild(this._screen);
				}
				catch(ignore){}
			}
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			console.log("An exception was thrown in CreateCatalogRequest.disableAll", ex);
		}
	},
	_createMessageHolder: function()
	{
		console.log('CreateCatalogRequest._createMessageHolder');
		var nodes = dojo.query("div.banner", this.domNode);
		this.messageHolder = dojo.place("<div></div>", nodes[0], 'last');
	},
	addMessage: function(/*String*/messageId, /*widget*/ fieldToFocus)
	{
		try {
			console.log('CreateCatalogRequest.addMessage');
			this.removeMessage(messageId, true);
			var d = dojo.place("<div></div>", this.messageHolder, 'last');
			this._messages[messageId] = new ibm.tivoli.simplesrm.srm.dijit.InlineMessage({messageId: messageId}, d);
			this.makeRoomForMessages();
			if(fieldToFocus != undefined){
				if(fieldToFocus.declaredClass.match(/^ibm\.tivoli\.tip\.dijit/)) {
					console.log("Invoking setFocus() on " + fieldToFocus.id);
					fieldToFocus.setFocus();
				}
				else if("function" === typeof fieldToFocus.focus){
					console.log("Invoking focus() on " + fieldToFocus.id);
					fieldToFocus.focus();
				}
			}
			console.log('CreateCatalogRequest.addMessage exit');
			
		}
		catch(ex) {
			console.error("CreateCatalogRequest.addMessage failure: " + ex.message);
		}
		
	},
	removeMessage: function(/*String*/messageId, /*boolean?*/suppressResize)
	{
		var message = this._messages[messageId];
		
		if ((message != undefined) && (message != null)) {
			message.destroy();
			delete this._messages[messageId];
			if(!suppressResize)
				this.makeRoomForMessages();
		}
		else
			console.debug('No message with messageId ' + messageId + ' to delete.');
	},
	clearMessages: function()
	{
		for (i in this._messages) {
			var message = this._messages[i];
			message.destroy();
		}
		this.makeRoomForMessages();
		this._messages = [];
	},
	makeRoomForMessages: function()
	{
		var content_wrapper = dojo.query(".content_wrapper", this.domNode)[0];
		var wsz = dojo.contentBox(content_wrapper);
		if(0 == wsz.h)
			return;
		var might_be_my_popup = dijit.getEnclosingWidget(this.messageHolder);
		if("function" === typeof might_be_my_popup._size) {
			might_be_my_popup._size();
		}
	},
	_dummy:null
});

ibm.tivoli.simplesrm.srm.dijit.CreateCatalogRequest.createInstanceOf = function(/*string*/className, params, domNode)
{
	dojo.require(className);
	var cls = dojo.getObject(className);
	return new cls(params, domNode);
};

	
/*****************************************************************************************
**
** CreatorPopupDialog is the popup dialog for displaying CreateCatalogRequest derived objects
*/
dojo.require("dijit.layout.BorderContainer");
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.CreatorPopupDialog", ibm.tivoli.simplesrm.srm.dijit.PopupDialog,
{
	createRequestButtonLabel: 'OK',
	cancelButtonLabel: 'Cancel',
	progressSpinner: null,

	
	creator: null,		// gets the request creating widget instance
	_connections: [],
	_inputParams: null,
	// possible input parameters
	requestType: '',		// required
	creatorType: "ibm.tivoli.simplesrm.srm.dijit.request.GenericForm",	// required
	ItemNum: null,			// required
	ObjectType: 'Cataog',	// required
	ItemSetID: null,		// need this or MRLineID
	MRLineID: null,			// need this or ItemSetID
	
	constructor: function(params, domNode)
	{
		console.log("CreatorPopupDialog.ctor");
		// cache the incoming params for when we create the creator
		this._inputParams = params;
	},
	postMixInProperties: function()
	{
		console.log("CreatorPopupDialog.postMixInProperties");
		
		// get i18n text
		this.createRequestButtonLabel = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").CreateRequestButtonLabel;
		this.cancelButtonLabel = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").CancelButtonLabel;
		
		this.inherited(arguments);	
	},
	buildRendering: function()
	{
		console.log("CreatorPopupDialog.buildRendering");
 
		this.inherited(arguments);
			
		this.creator = ibm.tivoli.simplesrm.srm.dijit.CreateCatalogRequest.createInstanceOf(this.creatorType, this._inputParams);
		this._createdHandle = dojo.connect(this.creator, 'onSrmRequestCreated', this, "_handleOnSrmRequestCreated");

		this.title = this.creator.headingText;
		
		// adding a class to the top-level div lets me style this popup differently than the generic dijit.Dialog
		dojo.addClass(this.domNode, "popupCreator");
	
	},
	postCreate: function(){	
		console.log("CreatorPopupDialog.postCreate");
		this.inherited(arguments);
		
		// add the footer to the bottom of the dialog
		var footer = document.createElement("div");
		footer.className = "footer";
		footer.innerHTML = "\n		<button id=\""+this.creator.id+"_createButton"+"\" dojoType=\"dijit.form.Button\">"+this.createRequestButtonLabel+"</button>"
						+"\n		<button id=\""+this.creator.id+"_cancelButton"+"\" dojoType=\"dijit.form.Button\">"+this.cancelButtonLabel+"</button>"
						+"\n		<span  id=\""+this.creator.id+"_spinner"+"\"dojoType=\"ibm.tivoli.simplesrm.srm.dijit.ProgressSpinner\" style=\"vertical-align:middle;\"></span>"
						;
		dojo.parser.parse(footer);
		dojo.place(footer, this.domNode, "last");
		
		// wire up the buttons
		this.connect(dijit.byId(this.creator.id+"_createButton"), "onClick", "_createRequest");
		this.connect(dijit.byId(this.creator.id+"_cancelButton"), "onClick", "_cancelRequest");

		if(this.readOnly) {
			dijit.byId(this.creator.id+"_createButton").setLabel("Close");
			dojo.style(dijit.byId(this.creator.id+"_cancelButton").domNode, "display", "none");
		}
		
		// create a div to hold our content, the creator + the footer
		var content_div = document.createElement("div")
		// add the creator widget 
		dojo.place(this.creator.domNode, content_div, "first");

		// give our content to the popup
		this.attr('content', content_div);		
		
		// move the banner from the creator to the Dialog.
		// TODO: should we move the banner from offering templates and add it here?
		divlist = dojo.query(".banner", this.containerNode);
		var banner = divlist.length > 0 ? divlist[0] : null;
		if(banner) {
			banner.parentNode.removeChild(banner);
			var d = document.createElement("div");
			d.appendChild(banner);
			dojo.place(d, this.titleBar, "after");
		}
		
		//if cshKey was provided add help to this dialog
		if(this.creator.cshKey){
			this.addHelp(this.creator.cshKey);			
		}
	},
	startup: function()
	{
		console.log("CreatorPopupDialog.startup");
		this.inherited(arguments);
		this.progressSpinner = dijit.byId(this.creator.id + "_spinner");
	},
	destroy: function()
	{
		console.log("CreatorPopupDialog.destroy");
		
		if(this.creator) {
			this.creator.destroy();
			this.creator = null;
		}
		var c;
		while(c = this._connections.pop()) {
			dojo.disconnect(c);
		}
		this.inherited(arguments);
	},

	_createRequest: function()
	{
		if( this.readOnly ) {
			this.hide();
			this.disableAll(false);
			return;
		}
		this.disableAll(true);
		this.progressSpinner.show();
		var deferred = this.creator.createRequest();
		if(deferred) {
			deferred.addBoth(dojo.hitch(this, function() { 
				this.progressSpinner.hide();
				this.disableAll(false);
			}));
		}
		else {
			this.progressSpinner.hide();
			this.disableAll(false);
		}
	},
	_cancelRequest: function()
	{
		this.creator.cancelRequest();
		// this is a bit of a hack
		if(dijit._masterTT) {
			// or validation messages get left behind
			dijit.hideTooltip(dijit._masterTT.aroundNode);	
		}
		this.hide();
	},
	/*
	** _handleOnSrmRequestCreated was registered as the CreateCatalogRequest.onRequestCreated
	** event handler.  This funstion is simply thunk that will forward the event on to external handlers
	** listening for CreatorPopupDialog.onSrmRequestCreated
	*/
	_handleOnSrmRequestCreated: function()
	{
		console.log("CreatorPopupDialog._handleOnSrmRequestCreated()");

		if(undefined == this.iContext) {
			this.onSrmRequestCreated();
		}
		else {
			console.log("CreatePopupDialog is firing iWidget event SrmRequestCreated");
			this.iContext.iEvents.fireEvent("SrmRequestCreated", "string", "");
		}	
	},
	onSrmRequestCreated: function()
	{
	}

});	

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.CreatorFactory", null,
{
	createEventHandles: [],		// dojo.connect handles I create wiring in the creator popup
	_popup: null,				// the creator popup
	_overlay: null,				// keep user from clicking while the popup is getting created

	destroy: function()
	{
		this.cleanup();
		if(this._overlay) {
			this._overlay.destroy();
			this._overlay = null;
		}
	},
	createAndShowInputForm: function(record_data, bReadOnly)
	{
		console.log("createAndShowInputForm :", record_data, bReadOnly);
		
		this.cleanup();
		
		//although all modal panel use dijit._underlay we still need this additional underlay
		// time between user click and displaying dijit._underlay is long enough to open many panels		
		if(this._overlay == null) {
			this._overlay = new dijit.DialogUnderlay();
			dojo.style(this._overlay.node, "cursor", "wait");
			dojo.style(this._overlay.node, "backgroundColor", "transparent");
		}
		this._overlay.show();
			
		try {
			if(record_data.ItemNum)	{
				try {
					var itemnum = record_data.ItemNum;
					var requestType = itemnum;
					var APPPATH = ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("APP_PATH").split(',');
					var inputFormFullName = null;
					for(var p in APPPATH) {
						try {
							var apath = APPPATH[p].trim();
							console.log("looking for %s in %s", requestType, apath);
							module = dojo.require(apath + ".dijit.request." + itemnum);
							if(module) {
								inputFormFullName = apath + ".dijit.request." + itemnum;
								console.log("found ", inputFormFullName);
								break;
							}
						}
						catch(ex) {
							// keep trying
							console.log("nope, not here");
						}
					}
					if(!inputFormFullName) {
						// failed loading the custom creator.  Let's go generic
						requestType = "GenericForm";
						dojo.require("ibm.tivoli.simplesrm.srm.dijit.request.GenericForm");
						inputFormFullName = "ibm.tivoli.simplesrm.srm.dijit.request.GenericForm";
						console.log("using GenericForm");
					}

					try {
						create_params = {
				  			requestType: requestType,
				  			creatorType: inputFormFullName,
				  			readOnly: bReadOnly
				  		}
				  		for(var k in record_data) {
				  			create_params[k] = record_data[k];
				  		}
					  	this._popup = new ibm.tivoli.simplesrm.srm.dijit.CreatorPopupDialog(create_params);
						this.createEventHandles.push( dojo.connect(this._popup, 'onSrmRequestCreated', this, "oncreated") );
						this._popup.startup();
					 	this._popup.show();	// warning, asynchronous
					 }
					 catch(ex) {
						new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("Failed creating " + itemnum + " editor: " + ex);
						if ("string" === typeof ex) {      // If we have a string it is the message ID to show
							(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: ex})).show();
						} else {
							(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2301E"})).show();
							console.error("failed creating popup editor:", ex);
						}
					 	this.cleanup();
					 }
				}
				catch(ex) {
					new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
					console.log(requestType + " not found");
					this.cleanup();
				 	(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2308E"})).show();		
				}
			}
			else {
				// if we get here, we don' know what to do
			 	(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2309E"})).show();
			}
		}
		catch(ex)
		{
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
		 	(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2309E"})).show();
		}
		if(this._overlay) {
			this._overlay.hide();
		}
		return this._popup;
	},
	cleanup: function()
	{
		while(handle = this.createEventHandles.pop()) {
			dojo.disconnect(handle);
		}
		if(this._popup != null) {
			this._popup.destroy();
			this._popup = null;
		}
	},
	oncreated: function()
	{
		if(this._popup)
			this._popup.hide();	// warning: asynchronous

		if(typeof this.onSrmRequestCreated == "function") {
			this.onSrmRequestCreated();
		}			
	},
	_dummy: null
});
