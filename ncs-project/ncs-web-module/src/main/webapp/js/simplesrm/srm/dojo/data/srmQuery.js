//////////////////////////////////////////////////////////////////
// @JS_LONG_COPYRIGHT_BEGIN@
// @JS_LONG_COPYRIGHT_END@
//////////////////////////////////////////////////////////////////

/**
 * These classes provide all the data access for the UI.
 * The high-level get*() methods retrieve the data and reformat for
 * the UI's consumption.  If you want the raw data, call getObjectStore() directly.
 *  
 * The TSAM specific queries ought to be factored out into a class in TSAM_web,
 * but that would mean moving MyDeploymentsGrid and DeploymentDetails too, and I'm not ready to do that
 */
dojo.provide("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");
dojo.provide("ibm.tivoli.simplesrm.srm.dojo.data.SynonymDomain");
dojo.provide("ibm.tivoli.simplesrm.srm.dojo.data._ajaxQuery");
dojo.provide("ibm.tivoli.simplesrm.srm.dojo.data._srmQuery");

dojo.require("dojo.DeferredList");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.OfferingTree");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.Utilities");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError");
dojo.require("dojox.xml.DomParser");

/**
 * Handle ajax data queries.
 * Directs the query through the proxy servlet if necessary 
 */
dojo.declare("ibm.tivoli.simplesrm.srm.dojo.data._ajaxQuery", null, 
{
	_proxyUrl: "/SRMCommonsWeb/ProxyServlet",
	_timeout: Infinity,
	devMode: false,

	restOnly: true, /* For REST navigator */
	
	constructor: function()
	{
		this._timeout = parseInt(ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("QueryTimeout"));
		if(isNaN(this._timeout) || this._timeout < 0) {
			this._timeout = Infinity;
		}
		var dm = ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("Devmode");
		this.devMode = (dm == "true" || dm == "1");
	},
	useProxy: function(url)
	{
		var bUseProxy = true;

		if(!this.devMode) {
			if('/' == url.charAt(0)) {
				bUseProxy = false;
			}
			else {
				var m = url.match(/:\/\/([^/]+)/);
				if(m) {
					var data_host = m[1];
					bUseProxy = (data_host != window.location.host);
				}
			}
		}
		return bUseProxy;
	},
	get: function(/*string*/url, /*boolean*/sync, /*object*/query_args)
	{
		var _data = {};
		var _url = url;
		var _sync = undefined == sync ? true : sync;
		var _xtraHeaders = {};
	
		var useProxy = this.useProxy(_url);
		if(useProxy) {
			_url = this._proxyUrl;
			_xtraHeaders["x-targeturl"] = url;
		}
		if( url.match(/rest\/rest/) ) {
			_data._format = "json";
		}
		else {
			_data.format = "json";
		}

		for(var p in query_args) {
			_data[p] = query_args[p];
		}
		var errHandler = dojo.hitch(this, this._ajaxError);
		
		var _deferred = dojo.xhrGet({
			url: _url,
			content: _data,
			headers: _xtraHeaders,
			sync: _sync,
			timeout: this._timeout,
			handleAs: "json",
			load: function(response, ioArgs) 
			{
				console.log("loaded");
				return response;
			},
			error: function(response, ioArgs)
			{
				errHandler(response, ioArgs); 
			}
		});
		return _deferred;
	},
	// Summary:
	//	Same as get, but for xhrPost
	post: function(/*string*/url, /*boolean*/sync, /*object*/data) 
	{
		var _data = {};
		var _url = url;
		var _sync = undefined == sync ? true : sync;
		var _xtraHeaders = {};
	
		var useProxy = this.useProxy(_url);
		if(useProxy) {
			_url = this._proxyUrl;
			_xtraHeaders["x-targeturl"] = url;
		}
		if( url.match(/rest\/rest/) ) {
			_data._format = "json";
		}
		else {
			_data.format = "json";
		}
		for(var p in data) {
			_data[p] = data[p];
		}
		var _deferred = dojo.xhrPost({
			url: _url,
			content: _data,
			headers: _xtraHeaders,
			sync: _sync,
			handleAs: "json",
			load: function(response, ioArgs) 
			{
				console.log("loaded");
				return response;
			},
			error: function(response, ioArgs) 
			{
				if("cancel" === response.dojoType) {
					console.log("request canceled");
				}
				else {
					var msg = "ajaxQuery.post failure";
					if(ioArgs && ioArgs.xhr) {
						msg += "(" + ioArgs.xhr.status + "): " + ioArgs.xhr.responseText;
					}
					console.error(msg);
					new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(msg);
					new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(response);
				}
				return response;
			}
		});
		return _deferred;
	},

	/**
	 * Call a MEA webService sending a raw XML POST request.
	 * The call passes through the proxy servlet, the real WS url is
	 * passed in the x-targeturl header
	 */
	callWebService : function(params){
		var _url = params.wsUrl;
		var headers = {
			"SOAPAction": "urn:action", 
     		"Content-Type": "text/xml; charset=utf-8" 
		}
		var useProxy = this.useProxy(_url);
		if(useProxy) {
			_url = this._proxyUrl;
			headers["x-targeturl"] = params.wsUrl;
		}
		var _deferred = dojo.rawXhrPost({
	          		url		: _url,
	                headers : headers,
	                postData: params.postData,
					handleAs: "text",
					sync    : params.sync,
					load    : function(response, ioArgs) {
								console.log("WS response loaded");							
								return response;
					},
					error   : dojo.hitch(this, this._ajaxError)
					});
		return _deferred;
	},
	
	
	_ajaxError: function(response, ioArgs)
	{
		if("cancel" === response.dojoType) {
			console.log("request canceled");
		}
		else {
			var msg = "ajaxQuery.get failure: " + response.toString()
			console.error(msg);
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(msg);
		}
		return response;
	},
	
	/**
	 * summary: 
	 * 	returns the given value if it is defined.  
	 * 	If value is undefined, then returns defaultValue
	 * 	If defaultValue is undefined, then returns ""
	 *  
	 *  Used this function when accessing data fields from data returned from one of our
	 *  REST queries.  Depending on the circumstances, all fields may not be present, and
	 *  this function can be used to safely assign a field value to a variable.
	 *  
	 *  Example:
	 *    var mrlineid = this.safeGetValue(response.CreatePMZHBR1_PMSCMRCREATEResponse.PMZHBR1_PMSCMRCREATESet.PMSCMR.PMSCMRLINE[0].MRLINEID, "0")
	 *    
	 *  Warning:
	 *  	What this function cannot guard against is an undefined intermediate object in the response.  For example, if 
	 *  	response.CreatePMZHBR1_PMSCMRCREATEResponse.PMZHBR1_PMSCMRCREATESet.PMSCMR.PMSCMRLINE[0] is undefined, 
	 *  	response.CreatePMZHBR1_PMSCMRCREATEResponse.PMZHBR1_PMSCMRCREATESet.PMSCMR.PMSCMRLINE[0].MRLINEID will cause an error before
	 *  	safeGetValue is ever called.
	 *   
	 */
	safeGetValue: function(/*String*/value, /*any*/defaultValue) 
	{
		return undefined != value ? value : (undefined != defaultValue ? defaultValue : "");
	}
});

dojo.declare("ibm.tivoli.simplesrm.srm.dojo.data._srmQuery", ibm.tivoli.simplesrm.srm.dojo.data._ajaxQuery,
{
	baseTDIUrl: "", /* TODO SR Delete that variable later */
	baseRestUrl: "",
	restOS: "maxrest/rest/os/",
	baseWSUrl: "",
	
	_getLocaleDebugString: function()
	{
		var bdebug = window.location.search.search("debug=(1|true)") > 0;
		var blocale = window.location.search.search("locale=es") > 0;
		return bdebug && blocale ? "$$$ " : "";
	},
	constructor: function()
	{
		var val = ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("tdiURL");
		this.baseTDIUrl  = val.length == 0 ? window.location.host : val;

		val = ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("maxURL");
		this.baseRestUrl = val.length == 0 ? window.location.host : val;
		this.baseWSUrl = this.baseRestUrl + "meaweb/services/";
	},
	getObjectStructure: function(os, args)
	{
		console.log("srmQuery.getObjectStructure", os, args);
		
		var _url = this.baseRestUrl + this.restOS + os
		var _sync = false;
		var _args = {};
		if(undefined != args) {
			if(undefined != args.id) {
				_url += "/" + args.id;
			}
			if(undefined != args.sync) {
				_sync = args.sync;
			}
		}
		if("object" == typeof args) {
			for(var a in args) {
				if("id" != a && "sync" != a)
					_args[a] = args[a];
			}
		}
		if(undefined == _args._compact) {
			_args._compact = 1;
		}
		return this.get(_url, _sync, _args);
	},
	postToObjectStructure: function(os, sync, data)
	{
		var _url = this.baseRestUrl + this.restOS + os
		var _data = {};
		if(undefined != data) {
			if(undefined != data.id) {
				_url += "/" + data.id;
			}
		}
		if("object" == typeof data) {
			for(var a in data) {
				if("id" != a)
					_data[a] = data[a];
			}
		}
		return this.post(_url, sync, _data);
	},
	/**
	 * Create a service request
	 * This takes place in 2 phases
	 *	1. create the PMSCMR
	 * 	2. update it with PMSCMRLINE data
	 */
	createRequest: function(/*object*/form_data, /*object*/request_details)
	{
		var ret_deferred = new dojo.Deferred();
		var me = this;
		console.log("srmQuery.createRequest using REST API")
		
		var u = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getLoggedInUser();
		if(!u) {
			throw "CTJZH2337E";
		}
		
		// 1. create the request
		// first, the fixed data
		var create_data = {};
		create_data._compact = 1;
		create_data.SITEID = u.SITE[0].SITEID;
		create_data.ORGID = u.SITE[0].ORGID;
		create_data.REQUESTEDBY = this.safeGetValue(form_data.requestedby, "");
		create_data.REQUESTEDFOR = this.safeGetValue(form_data.requestedfor, "");
		create_data.DESCRIPTION = this.safeGetValue(form_data.Description, "");
		if(undefined != form_data.RequestedStartDate && form_data.RequestedStartDate.length > 0)
			create_data.REQUIREDDATE = form_data.RequestedStartDate;
		if(undefined != form_data.RequestedEndDate && form_data.RequestedEndDate.length > 0)
			create_data.ENDDATE = form_data.RequestedEndDate;
		create_data["PMSCMRLINE.1.CLASSSTRUCTUREID"] = form_data.csid;
		create_data["PMSCMRLINE.1.ITEMNUM"] = form_data.ItemNum;
		create_data["PMSCMRLINE.1.ITEMSETID"] = form_data.ItemSetID;
		create_data["PMSCMRLINE.1.ORDERUNIT"] = this.safeGetValue(request_details.ORDERUNIT, "EACH"); 
		create_data["PMSCMRLINE.1.LINETYPE"] = request_details.ITEMTYPE;
		create_data["PMSCMRLINE.1.QTY"] = "1";
		
		// next, if this is a WCA offering, we include the variable parameter data
		var wca_params = /^PMRDPVSRPARM/;
		for(var fd in form_data) {
			if(wca_params.test(fd))
			{
				create_data["PMSCMRLINE.1." + fd] = form_data[fd];
			}
		}

		// the actual create
		var create_deferred = me.postToObjectStructure("PMZHBR1_PMSCMRCREATE", true, create_data);
		create_deferred.addCallbacks(function(create_response)
		{
			// by now, the PMSCMR has been created
			console.log("...CMR created");
			var cmr = create_response.CreatePMZHBR1_PMSCMRCREATEResponse.PMZHBR1_PMSCMRCREATESet.PMSCMR;
			var statusMap = me.getDomainSynonymTable('MRSTATUS');
			var datatypeMap = me.getDomainSynonymTable('DATATYPE');
			var ALN = datatypeMap.valueByMaxvalue("ALN");
			var NUMERIC = datatypeMap.valueByMaxvalue("NUMERIC");
			var TABLE = datatypeMap.valueByMaxvalue("MAXTABLE");
			
			var line_data = {id: cmr.MRID};
			// fixed data
			line_data.MRID = cmr.MRID;
			line_data.STATUS = statusMap.valueByMaxvalue("WAPPR");
			if(undefined == line_data.STATUS)
				line_data.STATUS = "WAPPR";
			line_data["PMSCMRLINE.1.MRLINEID"] = cmr.PMSCMRLINE[0].MRLINEID;
			line_data["PMSCMRLINE.1.COMMODITY"] = request_details.COMMODITY;
			line_data["PMSCMRLINE.1.COMMODITYGROUP"] = request_details.COMMODITYGROUP;
			line_data["PMSCMRLINE.1.DESCRIPTION"] = create_data.DESCRIPTION;
			// attribute data
			var pdspecs = cmr.PMSCMRLINE[0].PDSPEC;
			var pdspec_count = pdspecs.length;
			var j  = 0;
			for(var i = 0; i < pdspec_count; ++i) {
				var pdspec = pdspecs[i];
				var attr_det = request_details.AttributeByID[pdspec.ASSETATTRID];
				var attr_val = form_data[pdspec.ASSETATTRID]
				if(undefined != attr_det && undefined != attr_val) {
					++j;
					var attr_spec = "PMSCMRLINE.1.PDSPEC."+ j + ".";

					if(ALN == attr_det.DataType) {
						line_data[attr_spec + "ALNVALUE"] = attr_val;
					}
					else if(NUMERIC == attr_det.DataType) {
						line_data[attr_spec + "NUMVALUE"] = attr_val;
					}
					else if(TABLE == attr_det.DataType) {
						line_data[attr_spec + "TABLEVALUE"] = attr_val;
					}
					else {
						// TODO: now what?
						continue;	// skip this attribute for now
					}
					line_data[attr_spec + "REFOBJECTID"] = pdspec.REFOBJECTID; 
					line_data[attr_spec + "REFOBJECTNAME"] = pdspec.REFOBJECTNAME;
					line_data[attr_spec + "ASSETATTRID"] = pdspec.ASSETATTRID;
					line_data[attr_spec + "SECTION"] = "";
					line_data[attr_spec + "PDSPECID"] = pdspec.PDSPECID;
				}
				else {
					console.log("\tskipping ", pdspec.ASSETATTRID);
				}
			}
			
			// update the PMSCMR with our PMSCMRLINE data
			var dupd = me.postToObjectStructure("PMZHBR1_PMSCMRCREATE", true, line_data);
			dupd.addCallbacks(function(modify_response)
			{
				console.log("...modified with PMSCMRLINE data\n", modify_response);
				ret_deferred.callback(modify_response);	
				return modify_response;
			},
			function(modify_response)
			{
				new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(new Error("srmQuery.createRequest: error updating PMSCMRLINE: " + modify_response));
				ret_deferred.errback("CTJZH2334E");
				return "CTJZH2334E";
			});
			return create_response;
		},
		function(create_response)
		{
			// the create failed.
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(new Error("srmQuery.createRequest: error creating PMSCMR: " + create_response));
			ret_deferred.errback("CTJZH2338E");
			return "CTJZH2338E";
		});
		return ret_deferred;
	},
	/**
	 * get the list of users
	 * if params.id is specified, return that specific user  
	 */
	getUsers: function(params)
	{
		return this.getObjectStructure("PMZHBR1_MAXUSER", params);
	},
	/**
	 * similar to getUsers, but more data
	 */
	getUserDetails: function(params)
	{
		return this.getObjectStructure("PMZHBR1_MAXUSERDET", params);
	},
	/**
	 * return the user record for the logged in user
	 */
	_loggedInUser: null,
	getLoggedInUser: function()
	{
		// TODO: replace when Anamitra provides the real deal
		dojo.require("ibm.tivoli.simplesrm.srm.dijit.UserValidation");
		if(undefined == this._loggedInUser) {
			// returnes the logged in user.
			// works in the dev environment, because the proxy will add a MAXAUTH header
			var d = this.getUserDetails({_fd:"PMZHBT_LOGGEDONUSR", sync: true});
			var me = this;
			d.addCallbacks(function(response)
			{
				if(response.QueryPMZHBR1_MAXUSERDETResponse.rsCount >= 1) {	// TODO: changed == to >= to cope with bug in query
					me._loggedInUser = response.QueryPMZHBR1_MAXUSERDETResponse.PMZHBR1_MAXUSERDETSet.MAXUSER[0];
					ibm.tivoli.simplesrm.srm.dijit.loggedInUsername = me._loggedInUser.USERID;
				}
				else { 
					me._loggedInUser = null;					
					ibm.tivoli.simplesrm.srm.dijit.loggedInUsername = "";
				}
			},
			function(response)
			{
				console.warn("Failed getting logged in user: ", response);
				me._loggedInUser = null;
				ibm.tivoli.simplesrm.srm.dijit.loggedInUsername = "";
			});
		}
		return this._loggedInUser;
	},
	/**
	 * get the list of user groups
	 * if params.id is specified, return that specific group
	 */
	getGroups: function(params)
	{
		return this.getObjectStructure("PMZHBR1_MAXGROUP", params);
	},
	getCloudTeams: function(domain,params)
	{
		return this.getObjectStructure(domain, params);
	},
	getCurrentUserRole: function()
	{
		var params = {_fd: "PMZHBT_CLOUDROLE",sync: true};
		var d = this.getObjectStructure("PMZHBR1_GROUPUSER",params);
		var roleitem = null;
		d.addCallback(dojo.hitch(this, function(response)
		{
			if(response.QueryPMZHBR1_GROUPUSERResponse.rsCount > 0)
				roleitem = response.QueryPMZHBR1_GROUPUSERResponse.PMZHBR1_GROUPUSERSet.GROUPUSER[0];
		}));
		return roleitem;
	},
	getUserRole: function(params)
	{
		if (undefined == params)
			params = {};
		params.sync = true;
		params._fd="PMZHBT_CLUSERROLE"
		params.GROUPNAME="PMRDP";
		var d = this.getObjectStructure("PMZHBR1_GROUPUSER",params);
		var roleitem;
		d.addCallback(dojo.hitch(this, function(response)
		{
			roleitem = response.QueryPMZHBR1_GROUPUSERResponse.PMZHBR1_GROUPUSERSet.GROUPUSER[0];
		}));
		return roleitem;
	},
	/**
	 * get the service request status domain mapping
	 */
	_domainCache: {},	// cache the results by DOMAINID
	getDomain: function(params)
	{
		var d = null;
		if(undefined != params.DOMAINID && undefined == this._domainCache[params.DOMAINID]) {
			d = this.getObjectStructure("PMZHBR1_MAXDOMAIN", params);
			d.addCallback(dojo.hitch(this, function(response)
			{
				if(response.QueryPMZHBR1_MAXDOMAINResponse.rsCount > 0) {
					this._domainCache[params.DOMAINID] = response;
				}
				return response;
			}));
		}
		else {
			d = new dojo.Deferred();
			d.callback(this._domainCache[params.id]);
		}
		return d;
	},
	_domainSynonyms: {},
	getDomainSynonymTable: function(domainid)
	{
		console.log("_srmQuery.getDomainSynonymTable");
		var _locale_debug = this._getLocaleDebugString();
		
		if(undefined == this._domainCache[domainid]) {
			// don't have the data yet. Get it now.
			this.getDomain({DOMAINID: domainid, sync: true});
		}
		if(undefined == this._domainSynonyms[domainid]) {
			if(undefined != this._domainCache[domainid]) {
				this._domainSynonyms[domainid] = [];
				var synonyms = this._domainCache[domainid].QueryPMZHBR1_MAXDOMAINResponse.PMZHBR1_MAXDOMAINSet.MAXDOMAIN[0].SYNONYMDOMAIN;
				if (domainid == "SRSTATUS") {
					for(var i = 0; i < synonyms.length; ++i) {
						if (synonyms[i].MAXVALUE == "CLOSED") {
							synonyms[i].DESCRIPTION = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable").FailedStatus;
						}
					}
				}
				this._domainSynonyms[domainid] = new ibm.tivoli.simplesrm.srm.dojo.data.SynonymDomain(synonyms);
			}
		}
		return this._domainSynonyms[domainid];
	},
	getLanguageTable: function(params)
	{
		console.log("_srmQuery.getLanguageTable");
		if (undefined == params)
			params = {};
		params.ENABLED = "true";
		return this.getObjectStructure("PMZHBR1_LANGUAGE", params);
	},
	/**
	 * get the list of this user's service and catalog requests
	 */
	getRequests: function(params)
	{
		var deferred = null;
		if(undefined == params) params = {};
		var _sync = undefined == params.sync ? false : params.sync;
		var _locale_debug = this._getLocaleDebugString();
		
		if(params.ItemNum && params.ItemSetID) {
			// I've been given the ItemNum and ItemSetID. This means we want the offering details.
			// After calling the PMZHBR1_OFFERINGDET, transform the data to match what the 
			// TDI getrequestdetails request returned.  This way none of the application code relying
			// on the data has to change.

				// local helper to find the offering's classificationID
			function _getClassificationID(offering)
			{
				var classificationID = "";
				var offeringStructureID = offering.CLASSSTRUCTUREID;
				var n = undefined == offering.CLASSSTRUCTURE? 0 : offering.CLASSSTRUCTURE.length;
				for(var i = 0; i < n; ++i) {
					if(offering.CLASSSTRUCTURE[i].CLASSSTRUCTUREID == offeringStructureID) {
						classificationID = offering.CLASSSTRUCTURE[i].CLASSIFICATIONID;
						break;
					}
				}
				return classificationID;
			}

			// get the data
			deferred = this.getObjectStructure("PMZHBR1_OFFERINGDET", {sync: _sync, _exactmatch: 1, ITEMNUM: params.ItemNum, ITEMSETID: params.ItemSetID});
			deferred.addCallback(dojo.hitch(this, function(response) {
				// swizzle the data to look like the TDI response
				var noff = response.QueryPMZHBR1_OFFERINGDETResponse.PMZHBR1_OFFERINGDETSet.PMSCSRVOFF[0];	// the new offering data
				var Request = [];
				var off = Request[0] = {
					ClassStructureID: noff.CLASSSTRUCTUREID,
					ClassificationID: _getClassificationID(noff),
					Description: (undefined == noff.DESCRIPTION ? "" : _locale_debug + noff.DESCRIPTION),
					LongDescription: (undefined == noff.LONGDESCRIPTION ? undefined : _locale_debug + noff.LONGDESCRIPTION[0].LDTEXT),
					ImageName: undefined == noff.IMGLIB ? undefined : noff.IMGLIB[0].IMAGENAME,
					ItemID: noff.ITEMID,
					ItemNum: noff.ITEMNUM,
					ItemSetID: noff.ITEMSETID,
					Price: noff.PRICE,
					ORDERUNIT: noff.ORDERUNIT,
					ITEMTYPE: noff.ITEMTYPE,
					COMMODITY: noff.COMMODITY,
					COMMODITYGROUP: noff.COMMODITYGROUP,
					Status: noff.STATUS,
					Attribute: []
				};
				off.AttributeByID = {};
				
				var nattrs = noff.PMSCATTRMAP;
				var num_attrs = nattrs.length;
				for(var i = 0; i < num_attrs; ++i) {
					var nattr = nattrs[i];
					if (undefined == nattr.ITEMSPEC) continue;
					var attr = {
						AssetAttrID: nattr.ITEMSPEC[0].ASSETATTRID,
						DataType: nattr.ITEMSPEC[0].ASSETATTRIBUTE[0].DATATYPE,
						Description: (undefined == nattr.ITEMSPEC[0].ASSETATTRIBUTE[0].DESCRIPTION ? "" : _locale_debug + nattr.ITEMSPEC[0].ASSETATTRIBUTE[0].DESCRIPTION),
						DisplaySequence: nattr.ITEMSPEC[0].DISPLAYSEQUENCE,
						Hidden: nattr.HIDDEN,
						//MaxValue: not in new API,
						ReadOnly: nattr.READONLY,
						Mandatory: nattr.MANDATORY
					};
					if(undefined != nattr.ITEMSPEC[0].NUMVALUE) 
						attr.NumValue = nattr.ITEMSPEC[0].NUMVALUE;
					if(undefined != nattr.ITEMSPEC[0].ALNVALUE)
						attr.ALNValue = nattr.ITEMSPEC[0].ALNVALUE;
					off.Attribute.push(attr);
					off.AttributeByID[attr.AssetAttrID] = attr;
				}
				response.Request = Request;	// Add the TDI style Request to the response
				this._patchRequestImageName(response.Request[0]);
				return response;
			}));
		}
		else if(undefined != params.id) {
			if("CMR" == params.type) {
				deferred = this.getObjectStructure("PMZHBR1_PMSCMR", {MRID: params.id});
			}
			else if("SR" == params.type) {
				deferred =  this.getObjectStructure("PMZHBR1_SR", {TICKETUID: params.id, _fd:"PMZHBT_SRUSRLIST"});
			}
		}
		return deferred;
	},

	/**
	 * Returns the details for a given request from the SRDET object structure.
	 * params.id is mandatory and contains the ID of the request.
	 */
	getRequestDetails: function(params)
	{
		var osname = "PMZHBR1_SRDET";
		deferred =  this.getObjectStructure("PMZHBR1_SRDET",
											{TICKETUID: params.id});
		return deferred;
	},
	getCatalogRequests: function(params)
	{
		// get the status domain we need
		var mrstatq = this.getDomain({DOMAINID: 'MRSTATUS'});
		// get the list of requests
		var cmrq = this.getObjectStructure("PMZHBR1_PMSCMR", params);

		deferred = new dojo.Deferred();	// this is the deferred returned by the function. (see comments in getProjectsServersFlat for details)
		deferred.addErrback(dojo.hitch(this, this._ajaxError));
		
		// when all the queries complete, add a "StatusString" property to each project with the pretty-formatted status
		var _queries = new dojo.DeferredList([mrstatq, cmrq]);
		_queries.addCallback(dojo.hitch(this, function(responses)
		{
			var reqs = [];
			// CMRS
			if(responses[1][0]) {
				var statusMap = this.getDomainSynonymTable('MRSTATUS');
				var cmrs = responses[1][1].QueryPMZHBR1_PMSCMRResponse.PMZHBR1_PMSCMRSet.PMSCMR;
				var count = undefined == cmrs ? 0 : cmrs.length;
				for(var i = 0; i < count; ++i) {
					try {
						var cmr = cmrs[i];
						
						req = {
							key: "CMR" + cmr.MRID,
							id: cmr.MRID,
							type: "CMR",
							STATUS: cmr.STATUS,
							StatusString: statusMap.descriptionByValue(cmr.STATUS),
							STATUSDATE: cmr.STATUSDATE,
							REQUESTEDBY: cmr.REQUESTEDBY,
							REQUESTEDFOR: cmr.REQUESTEDFOR,
							CHANGEDATE: cmr.CHANGEDATE,
							DESCRIPTION: cmr.DESCRIPTION ? cmr.DESCRIPTION : cmr.PMSCMRLINE[0].DESCRIPTION,
							item: cmr
						};
						reqs.push(req);
					}
					catch(ex) {
						new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("srmQuery._getRequests: error processing CMR " + i);
						new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
						deferred.errback(responses[1][1]);
					}
				}
				deferred.callback({Requests: reqs});	// call the callback the caller would have added
			}
			else {
				deferred.errback(responses[1][1]);	
			}
			return responses;		
		}));
		return deferred;
	},
	getServiceRequests: function(params)
	{
		var deferred = null;
		if(undefined == params) params = {};
		var _sync = undefined == params.sync ? false : params.sync;
		var _locale_debug = this._getLocaleDebugString();
		
		if(undefined == params.id) {
			// get the status domain we need
			var srstatq = this.getDomain({DOMAINID: 'SRSTATUS'});
			// get the list of requests
			if(undefined == params._fd) {
				params._fd = "PMZHBT_SRUSRLIST";	// limit to SRs the logged in user may see
			}
			var srq =  this.getObjectStructure("PMZHBR1_SR", params);

			deferred = new dojo.Deferred();	// this is the deferred returned by the function. 
			deferred.addErrback(dojo.hitch(this, this._ajaxError));
			
			// when all the queries complete, add a "StatusString" property to each SR with the pretty-formatted status
			var _queries = new dojo.DeferredList([srstatq, srq]);
			_queries.addCallback(dojo.hitch(this, function(responses)
			{
				var reqs = [];
				
				// SRs
				if(responses[1][0]) {
					var statusMap = this.getDomainSynonymTable('SRSTATUS');
					var srs = responses[1][1].QueryPMZHBR1_SRResponse.PMZHBR1_SRSet.SR;
					var count = undefined == srs ? 0 : srs.length;
					for(var i = 0; i < count; ++i) {
						try {
							var sr = srs[i];
							
							req = {
								key: "SR" + sr.TICKETUID,
								id: sr.TICKETUID,
								type: "SR",
								STATUS: sr.STATUS,
								StatusString: statusMap.descriptionByValue(sr.STATUS),
								STATUSDATE: sr.STATUSDATE,
								REQUESTEDBY: sr.CREATEDBY,
								REQUESTEDFOR: sr.AFFECTEDPERSON,
								DESCRIPTION: sr.DESCRIPTION,
								CREATIONDATE: sr.CREATIONDATE,
								TARGETSTART: sr.TARGETSTART,
								TICKETID: sr.TICKETID,
								item: sr
							}
							reqs.push(req);
						}
						catch(ex) {
							new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("srmQuery._getServiceRequests: error processing SR " + i);
							new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
							deferred.errback(responses[1][1]);
						}
					}
				}
				else {
					deferred.errback(responses[1][1]);
				}	
				deferred.callback({Requests: reqs});	// call the callback the caller would have added
				return responses;		
			}));
		}
		else {
			deferred =  this.getObjectStructure("PMZHBR1_SR", {TICKETUID: params.id, _fd:"PMZHBT_SRUSRLIST"});
		}
		return deferred;
	},
	
	/**
	 * get the list of server images
	 */
	getImages: function(params)
	{
		if(undefined == params) params = {};
		params.srmreq = 'listimages';
		return this.get("http://" +  window.location.host + "/SimpleSRM/testdata/testImages.json", true, params);
	},
	/**
	 * get the list of discovered images
	 */
	getDiscoveredImages: function(params)
	{
		if(undefined == params) 
			args = {};
		else 
			args = dojo.clone(params);
		
		if(undefined == args._compact)
			args._compact = 1;
		
		var url =this.baseRestUrl + "ilrest/rest/os/TPV01SOFTWARESTACK";
		return deferred = this.get(url, true, args);
	},
	/**
	 * get the list of master images
	 */
	getMasterImages: function(params)
	{
		if(undefined == params) 
			args = {};
		else 
			args = dojo.clone(params);
		
		if(undefined == args._compact)
			args._compact = 1;
		
		var url = this.baseRestUrl + "ilrest/rest/os/TPV01IMGLIBENTRYMSTR";
		var deferred = this.get(url, false, args);
		deferred.addCallback(this._findImageResourceConstraints);
		return deferred;
	},
	/**
	 * Summary:
	 * 	helper function to find the min and recommended values for the image resources, and
	 * 	make them more convenient to use.
	 * 	The function guarantees that each image contains a min_memory, min_storage, min_platformCPUs, min_virtualCPUs propertis,
	 * 	and their recommended_* counterparts.  If the data from the image library was missing any of this data, then it
	 * 	is added with an undefined value.
	 */ 
	_findImageResourceConstraints: function(response)
	{
		if(0 == response.QueryTPV01IMGLIBENTRYMSTRResponse.rsCount)
			return response;
		
		// enum of RESoURCE_TYPE_IDs
		var ResourceIDMap = {cpu: 3, storage: 2, memory: 6};
		// create default resource requirements object
		function _makeRequirementObj()
		{
			return {
				platformCPUs: undefined,
				virtualCPUs: undefined,
				memory: undefined,
				storage: undefined
			};
		}
		// fill in a resource requirements object with data found
		// in the given TPRESOURCEREQUIREMENT object
		function _getResourceRequirements(/*TPRESOURCEREQUIREMENT object*/resourceRequirements)
		{
			var req = _makeRequirementObj();
			if(undefined != resourceRequirements) 
			{
				var res_count = resourceRequirements.length;
				for(var i = 0; i < res_count; ++i) {
					var res_req = resourceRequirements[i];
					switch(res_req.RESOURCE_TYPE_ID) {
					case ResourceIDMap.cpu:
						if (undefined != res_req.RESOURCE_SIZE)
							req.platformCPUs = res_req.RESOURCE_SIZE / 10; 	// The server knows of this as tenths of a CPU, so lets convert it
						else
							req.platformCPUs = res_req.RESOURCE_SIZE;
						req.virtualCPUs = res_req.HOW_MANY;
						break;
					case ResourceIDMap.memory:
						req.memory = res_req.RESOURCE_SIZE;
						break;
					case ResourceIDMap.storage:
						req.storage = res_req.RESOURCE_SIZE;
						break;
					}
				}
			}
			return req;
		}
		// walk the list of images, extract the templateID for minimum and recommended resources,
		// walk the image's list of resource templates looking for the matching IDs
		// extract the data, and merge into the image
		var images = response.QueryTPV01IMGLIBENTRYMSTRResponse.TPV01IMGLIBENTRYMSTRSet.ILIMAGE;
		if(1 == response.QueryTPV01IMGLIBENTRYMSTRResponse.rsTotal && !dojo.isArray(response.QueryTPV01IMGLIBENTRYMSTRResponse.TPV01IMGLIBENTRYMSTRSet.ILIMAGE)) {
			images = [images];
		}
		var img_count = images.length;
		for(var i = 0; i < img_count; ++i) {	// for each image
			var found = 0;
			var img = images[i];
			if(undefined != img.TPVIRTUALSERVERTEMPLATE) {
				// we have data
				var recID = img.VIRTUALSERVERTEMPLATEID;		// template ID for recommended resources
				var minID = img.VIRTUALSERVERTEMPLATEMINID;		// template ID for minimum resources
				var tmplt_count = img.TPVIRTUALSERVERTEMPLATE.length;
				for(var j = 0; j < tmplt_count; ++j) {			// for each resource template
					var tmplt = img.TPVIRTUALSERVERTEMPLATE[j];
					if(tmplt.ID == minID) {						// this is the minimum resource template
						var req = _getResourceRequirements(tmplt.TPRESOURCEREQUIREMENT);
						img.minimumResources = req;
						//_flattenRequirements(img, req, "min_");
						found |= 1;
					}
					else if(tmplt.ID == recID) {				// this is the recommended resource template
						var req = _getResourceRequirements(tmplt.TPRESOURCEREQUIREMENT);
						//_flattenRequirements(img, req, "recommended_");
						img.recommendedResources = req;
						found |= 2;
					}
					if(found == 3) 	// stop looking once we have them both
						break;
				}
			}
			if((found & 1) != 1) {	// didn't find minimum values
//				img.minimumResources = _makeRequirementObj();
				img.minimumResources =  {
					platformCPUs: 1,
					virtualCPUs: 1,
					memory: 1,
					storage: 1
				};
			}
			if((found & 2) != 2) {	// didn't recommended values
//				img.recommendedResources = _makeRequirementObj();
				img.recommendedResources =  {
						platformCPUs: 1,
						virtualCPUs: 2,
						memory: 3,
						storage: 4
					};
			}
		}
		return response;
	},
	
	/**
	 * Get the list of projects through MEA REST.<br>
	 * This method accepts the standard selection method on REST queries. 
	 */
	getProjects: function(params)
	{
		console.log("srmQuery.getProjects() ", params);
		
		if(undefined == params) params = {};
		params.CLASSSTRUCTUREID = "PMRDPCLCPR";
		return this.getProjectsServersFlat(params);
	},
	/**
	 * Get the list of supported resource pools through MEA Web Service.
	 * Sample Response from the webservice:
	 * 	  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
	 * 	  <soapenv:Body>
	 *	  <pmrdpbcapigetAvailablePoolListResponse xmlns="http://www.ibm.com/maximo" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	 *	  						baseLanguage="EN" creationDateTime="2009-09-04T10:53:03+02:00" messageID="1252054383141664160" transLanguage="EN">
	 *	     <PMRDPBCPOOLSMboSet>
	 *	         <PMRDPBCPOOLS>
	 *	             <NAME>VMWARE</NAME> 
	 *               <ID>/CLOUD/REST/POOL/3</ID>
	 *  			 <PLATFORMTYPE>VMWARE</PLATFORMTYPE>
	 *           </PMRDPBCPOOLS> 
	 *      </PMRDPBCPOOLSMboSet>
	 *  </pmrdpbcapigetAvailablePoolListResponse>
	 *  </soapenv:Body>
	 *  </soapenv:Envelope>	
	 */			
	getResourcePools : function(params){		
		if(undefined == params) params = {};
		params.wsUrl = this.baseWSUrl + "PMRDPBCAPI";
		params.postData = '<?xml version="1.0" encoding="utf-8"?> '
					    + '    <soapenv:Envelope xmlns:q0="http://www.ibm.com/maximo" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">'
					    + '        <soapenv:Body>'
					    + '		       <q0:pmrdpbcapigetAvailablePoolList baseLanguage="en" transLanguage="en" creationDateTime="' + dojo.date.stamp.toISOString(new Date(),{zulu:true}) + '" maximoVersion="7115" messageID="' + Math.round(1000*Math.random()) + '" /> '
			 		    + '        </soapenv:Body>'
					    + '    </soapenv:Envelope>';
		console.log("srmQuery.getResourcePools() ", params);					    
		var deferred = this.callWebService(params);
		deferred.addCallback (
			dojo.hitch(this, function(response) {				
					var dom = dojox.xml.DomParser.parse(response.toString());
									
					var poolSet = dom.byName("PMRDPBCPOOLSMboSet");							
					var pools = poolSet[0].childNodes;
					var resourcePools = [];							
					for(var i=0; i<pools.length; i++){
						var pool = {};
						var poolContainer = pools[i].childNodes;
						for(var j=0; j<poolContainer.length; j++){
							var nodeName =  poolContainer[j].nodeName;
							var nodeValue = poolContainer[j].childNodes[0].nodeValue;
							pool[nodeName] = nodeValue;	
						}
						resourcePools[i] = pool;
					}
					//only filter is the param exists					
					if(undefined != params.platformType){
						resourcePools = dojo.filter(resourcePools,
			                  function(elem){
			                  	//TODO: elem.PLATFORMTYPE will be always returned by the "patched" webservice, remove this checks when it will be
			                  	// integrated into the build
			                  		if(elem.PLATFORMTYPE)
										return elem.PLATFORMTYPE == params.platformType;									
									else
										return true;
								}
			                );
					}
					//WARNING: attached callbacks will get a javascript response from here (no more xml-dom object)!!!!
					response = resourcePools;
					return response;
		}));
		return deferred;												
	},
	
	/**
	 * Get virtual server monitor data (including MEMORY, CPU, STORAGE, STATUS) through MEA Web Service.
	 * 
	 * WSDL for MEA web service:
	 * http://blue-tsamdev8.tivlab.raleigh.ibm.com:9080/meaweb/wsdl/PMRDPBCAPI.wsdl
	 *
	 * pmrdpbcapigetBCRealSysResMonitoringData
	 * 
	 * Usage:
	 * Params are:
	 *		vrProjectId (Project ID)
	 *		vrServerId (Virtual Server ID)
	 *		sync (synchronous or asynchronous WS call)
	 *
	 * Returns:
     * An object with four attributes, each one represents a particular monitor for a virtual server, e.g.:
     *	 PMRDPCLCVS_MEMORY
     *		"25"
     *	PMRDPCLCVS_NUMBER_PCPUS
     *		"44"
     *	PMRDPCLCVS_STORAGE_SIZE
     *		"40"
     *	PMRDPCLCVS_STATE
     *		"ONLINE"
	 */	 
	getServerMonitors : function(params) {		
		if (undefined == params) params = {};
		params.wsUrl = this.baseWSUrl + "PMRDPBCAPI";
		
		params.postData = '<?xml version="1.0" encoding="utf-8"?> '
					    + '    <soapenv:Envelope xmlns:q0="http://www.ibm.com/maximo" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">'
					    + '        <soapenv:Body>'
						+ '		       <q0:pmrdpbcapigetBCRealSysResMonitoringData baseLanguage="en" transLanguage="en" creationDateTime="' + dojo.date.stamp.toISOString(new Date(),{zulu:true}) + '" maximoVersion="7115" messageID="' + Math.round(1000*Math.random()) + '" /> '
						+ '            <q0:vrProjectId>' + params.vrProjectId + '</q0:vrProjectId>'
						+ '			   <q0:vrServerId>' + params.vrServerId + '</q0:vrServerId>'
			 		    + '		   </soapenv:Body>'
					    + '    </soapenv:Envelope>';
		console.log("srmQuery.getServerMonitors() ", params);		
					    
		var deferred = this.callWebService(params);
		deferred.addCallback (
			dojo.hitch(this, function(response) {				
				var dom = dojox.xml.DomParser.parse(response.toString());
				var serverMonitorSet = dom.byName("SERVERMONITORMboSet");
				var monitors = serverMonitorSet[0].childNodes;
				var vsMonitor = {};
				
				for (var i = 0; i < monitors.length; i++) {
					var nodeName = null;
					var nodeValue = null;
					
					var assetAttrId = monitors[i].childNodes[0];
					var numValue = monitors[i].childNodes[3];
					var state = monitors[i].childNodes[4];
					
					if (numValue.childNodes[0]) {
						nodeName = assetAttrId.childNodes[0].nodeValue;
						nodeValue = numValue.childNodes[0].nodeValue;
					}
					else {
						if (state.childNodes[0]) {
							nodeName = assetAttrId.childNodes[0].nodeValue;
							nodeValue = state.childNodes[0].nodeValue;
						}
					}
					
					vsMonitor[nodeName] = nodeValue;
				}
				
				response = vsMonitor;
				
				//WARNING: attached callbacks will get a javascript response from here (no more xml-dom object)!!!!
				return response;
		}));
		
		return deferred;												
	},
	
	/**
	 * Get available resource data through MEA Web Service.
	 * 
	 * WSDL for MEA web service:
	 * http://blue-tsamdev8.tivlab.raleigh.ibm.com:9080/meaweb/wsdl/PMRDPBCAPI.wsdl
	 *
	 * pmrdpbcapigetAvailableCapacityForPools
	 * 
	 * Usage:
	 * Similar to getResourcePools when its called from CreateProjectWithServer.js
	 * e.g., ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getAvailableResources({sync: true});
	 *
	 * Returns:
     * An array of objects, each one holds a particular capacity parameter for a pool, e.g.:
     *	 ASSETATTRID
     *		"PMRDPCLCVS_MEMORY"
     *	CINUM
     *		"/CLOUD/REST/POOLS/3/"
     *	MEASUREUNITID
     *		"null"
     *	NUMVALUE
     *		"250.0"
     *	SECTION
     *		"SINGLESER"
	 */	 
	 getAvailableResources : function(params){		
		if(undefined == params) params = {};
		params.wsUrl = this.baseWSUrl + "PMRDPBCAPI";

		//pmrdpbcapigetAvailableCapacityData
		//pmrdpbcapigetAvailableCapacityDataResponse		
		//pmrdpbcapigetAvailableCapacityForPools
		//pmrdpbcapigetAvailableCapacityForPoolsResponse
				
		params.postData = '<?xml version="1.0" encoding="utf-8"?> '
		    + '    <soapenv:Envelope xmlns:q0="http://www.ibm.com/maximo" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">'
		    + '        <soapenv:Body>'
		    + '		       <q0:pmrdpbcapigetAvailableCapacityForPools baseLanguage="en" transLanguage="en" creationDateTime="' + dojo.date.stamp.toISOString(new Date(),{zulu:true}) + '" maximoVersion="7115" messageID="' + Math.round(1000*Math.random()) + '" /> '
 		    + '        </soapenv:Body>'
		    + '    </soapenv:Envelope>';
		
		console.log("srmQuery.getAvailableResources() ", params);					    
		var deferred = this.callWebService(params);
		deferred.addCallbacks (
			dojo.hitch(this, function(response) {				
					var dom = dojox.xml.DomParser.parse(response.toString());
										
					var resourceSet = dom.byName("CIRCAPACITYVIEWMboSet");
					
					var resources = resourceSet[0].childNodes;
					var resourcePools = [];				
					for(var i=0; i<resources.length; i++){
						var resource = {};
						var resourceContainer = resources[i].childNodes;
						for(var j=0; j < resourceContainer.length; j++){
							var nodeName =  resourceContainer[j].nodeName;
							if(resourceContainer[j].childNodes[0])
								var nodeValue = resourceContainer[j].childNodes[0].nodeValue;
							else
								var nodeValue = null;
							resource[nodeName] = nodeValue;
						}
						resourcePools[i] = resource;
					}					
					//console.log("srmQuery.getAvailableResources()",resourcePools);
					
					//WARNING: attached callbacks will get a javascript response from here (no more xml-dom object)!!!!
					response = resourcePools;
					return response;
		}));
		return deferred;												
	},	
	
	/**
	 * Get available resource data by dates through MEA Web Service.
	 * 
	 * WSDL for MEA web service:
	 * http://blue-tsamdev8.tivlab.raleigh.ibm.com:9080/meaweb/wsdl/PMRDPBCAPI.wsdl
	 *
	 * pmrdpbcapigetAvailableCapacityData 
	 * 
	 * Usage:
	 * Similar to getResourcePools when its called from CreateProjectWithServer.js
	 * e.g., ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getAvailableResourcesDates({sync: true});
	 * takes vrpoolId startTime  endTime cpu  memMB stgGB
	 * if one of the latter three is empty, returns available value for it (in theory) 
	 */	 
	 getAvailableResourcesDates : function(params){
		if(undefined == params) params = {};
		params.wsUrl = this.baseWSUrl + "PMRDPBCAPI";

		//pmrdpbcapigetAvailableCapacityData
		//date e.g.: "2009-11-19T01:18:33Z"
		params.postData = '<?xml version="1.0" encoding="utf-8"?> '
		    + '    <soapenv:Envelope xmlns:q0="http://www.ibm.com/maximo" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">'
		    + '        <soapenv:Body>'
		    + '		       <q0:pmrdpbcapigetAvailableCapacityData baseLanguage="en" transLanguage="en" creationDateTime="' + dojo.date.stamp.toISOString(new Date(),{zulu:true}) + '" maximoVersion="7115" messageID="' + Math.round(1000*Math.random()) + '"> '
		    + '			   <q0:vrpoolId>' + params.vrpoolId +'</q0:vrpoolId>'
			+ '			   <q0:startTime>' + params.startTime + '</q0:startTime>'
			+ '			   <q0:endTime>' + params.endTime + '</q0:endTime>'
			+ '			   <q0:cpu>' +  params.cpu + '</q0:cpu>'
			+ '			   <q0:memMB>' + params.memMB + '</q0:memMB>'			
			+ '			   <q0:stgGB>' + params.stgGB + '</q0:stgGB></q0:pmrdpbcapigetAvailableCapacityData>'
 		    + '        </soapenv:Body>'
		    + '    </soapenv:Envelope>';
					    
		var deferred = this.callWebService(params);
		deferred.addCallbacks (
			dojo.hitch(this, function(response) {				
					//console.log("srmQuery.getAvailableResourcesDates() resp string",response.toString());
					var dom = dojox.xml.DomParser.parse(response.toString());
										
					var resourceSet = dom.byName("CIRCAPACITYVIEWMboSet");
					
					var resources = resourceSet[0].childNodes;
					var resourcePools = [];				
					for(var i=0; i<resources.length; i++){
						var resource = {};
						var resourceContainer = resources[i].childNodes;
						for(var j=0; j < resourceContainer.length; j++){
							var nodeName =  resourceContainer[j].nodeName;
							if(resourceContainer[j].childNodes[0])
								var nodeValue = resourceContainer[j].childNodes[0].nodeValue;
							else
								var nodeValue = null;
							resource[nodeName] = nodeValue;
						}
						resourcePools[i] = resource;
					}					
					//console.log("srmQuery.getAvailableResourcesDates()",resourcePools);
					
					//WARNING: attached callbacks will get a javascript response from here (no more xml-dom object)!!!!
					response = resourcePools;
					return response;
		}));
		return deferred;												
	},	
	approveRequest: function(/*number*/ticketID, /*string*/memo, /*boolean*/approve)
	{
		var statusMap = this.getDomainSynonymTable('SRSTATUS');
		var newstatus = approve ? statusMap.valueByMaxvalue('PENDING') : statusMap.valueByMaxvalue('CLOSED');

		var params = {
			wsUrl: this.baseWSUrl + "TICKET",
			sync: true
		}
				
		params.postData = 
			  '<?xml version="1.0" encoding="UTF-8"?>'
		    + '<soapenv:Envelope xmlns:q0="http://www.ibm.com/maximo" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">'
		    + '<soapenv:Body>'
			+ '<q0:ticketchangeStatus>'
			+ '<q0:ticket>'
			+ '<q0:TICKET>'
			+ '<q0:CLASS maxvalue="" changed="true">SR</q0:CLASS>'
			+ '<q0:TICKETID changed="true">'+ticketID+'</q0:TICKETID>'
			+ '</q0:TICKET>'
			+ '</q0:ticket>'
			+ '<q0:status>'+ newstatus + '</q0:status>'
//			+ '<q0:date></q0:date>'
			+ '<q0:memo><![CDATA['+ memo +']]></q0:memo>'
			+ '</q0:ticketchangeStatus>'
			+ '</soapenv:Body>'
			+ '</soapenv:Envelope>';
		
		var deferred = this.callWebService(params);
		// the response
		//		<?xml version="1.0" encoding="UTF-8"?>
		//		<max:ticketchangeStatusResponse 
		//			xmlns:max="http://www.ibm.com/maximo" 
		//			creationDateTime="ISO datetime" 
		//			baseLanguage="string" 
		//			transLanguage="string" 
		//			messageID="string" 
		//			maximoVersion="string" /> 

		var bSuccess = false;	// assume the worst
		deferred.addCallbacks(function(response) 
		{				
			console.log(response);
			bSuccess = true;
			return response;
		},
		function(response)
		{
			console.error(response);
			return response;
		});
		return bSuccess;				
	},
	/**
	 * Get the list of servers through MEA REST.<br>
	 * This method accepts the standard selection method on REST queries. 
	 */
	getServers: function(params)
	{
		console.log("srmQuery.getServers() ", params);
		
		if (undefined == params) params = {};
		params.CLASSSTRUCTUREID = "PMRDPCLCVS";
		return this.getProjectsServersFlat(params);
	},

	/**
	 * Get the list of projects and servers through MEA REST.<br>
	 * This method accepts the standard selection method on REST queries.
	 * Note that the result is a flat view with server and projects at the
	 * same level in the JSON structure. The result from PMRDPSIVIEW
	 * is <i>not</i> transformed in the process.
	 */
	getProjectsServersFlat: function(params)
	{
		console.log("srmQuery.getProjectsServersFlat() ", params);
		
		var deferred = null;
		if(undefined == params) {
			params = {};
		}
		else {
			params = dojo.clone(params);
		}
		params._fd = "PMZHBT_SRVPRJLUSER";	// restrict output to that visible by the logged in user
		
		// query for status domain
		var statq = this.getDomain({DOMAINID: 'PMZHBSISTATUS'});
		// query for vsstatus domain
		var vsstatq = this.getDomain({DOMAINID: 'PMZHBWTNSTATUS'});
		// query for the projects.  
		var projq = this.getObjectStructure("PMZHBR1_PMRDPSIVIEW", params);

		// Note: if we just return projq, and if the project query completes first, then its
		// callback gets called before we get a chance to merge in the StatusStrings.
		// Create a Deferred to return, then call its callback after we've taken care of the status
		var deferred = new dojo.Deferred();	// this is the deferred returned by the function.
		deferred.addErrback(dojo.hitch(this, this._ajaxError));
		
		// when both queries complete, add a "StatusString" property to each project with the pretty-formatted status
		var _queries = new dojo.DeferredList([statq, vsstatq, projq]);
		_queries.addCallbacks(dojo.hitch(this, function(responses)
		{
			if(responses[2][0]) {	// we have our projects list
				var _statusMap = responses[0][0] ? this.getDomainSynonymTable('PMZHBSISTATUS') : {};
				var _vsStatusMap = responses[1][0] ? this.getDomainSynonymTable('PMZHBWTNSTATUS') : {};
				var projects = responses[2][1].QueryPMZHBR1_PMRDPSIVIEWResponse.PMZHBR1_PMRDPSIVIEWSet.PMRDPSIVIEW;
				var proj_count = responses[2][1].QueryPMZHBR1_PMRDPSIVIEWResponse.rsCount;
				for(var i = 0; i < proj_count; ++i) {
					var status = projects[i].STATUS;
					var vsstatus = projects[i].VSSTATUS;
					projects[i].StatusString = _statusMap.descriptionByValue(status);

					/* Only add the VsStatus to servers */
					if (projects[i].CLASSSTRUCTUREID == "PMRDPCLCVS") {
						projects[i].ServerStatusString = _vsStatusMap.descriptionByValue(vsstatus);

						if (projects[i].VSPCPU != undefined)
							projects[i].VSPCPU = projects[i].VSPCPU / 10;
					}
				}
				deferred.callback(responses[2][1]);
			}
			else {
				deferred.errback(responses[2][1]);
			}
			return responses;		
		}));

		return deferred;
	},
	
	/**
	 * Builds a hierarchical structure of projects and servers,
	 * where servers are embedded into their respective projects.
	 * The method returns a deferred object that must get a callback
	 * method to process the results.
	 */
	 getProjectsServers: function(params) {
		console.log("srmQuery.getProjectsServers()", params);
		var d2 = new dojo.Deferred();
		d = this.getProjectsServersFlat(params);
		d.addCallback(function(response) {
			var flatview =
				response.QueryPMZHBR1_PMRDPSIVIEWResponse.PMZHBR1_PMRDPSIVIEWSet.PMRDPSIVIEW;
			var hrcview = new Array(); /* it's the returned hierarchical object */
			/* searching for projects first */
			for (var j = 0; j < flatview.length; j++){
				if (flatview[j].CLASSSTRUCTUREID == "PMRDPCLCPR") {
					flatview[j].Servers = new Array();
					hrcindex = hrcview.push(flatview[j]) - 1;
					/* searching for the corresponding servers (children) */
					for (var i = 0; i < flatview.length; i++){
						if ((flatview[i].CLASSSTRUCTUREID == "PMRDPCLCVS")
								&& (flatview[j].PMZHBSSVCIID == flatview[i].PMZHBSSVCIID)) {
							/* server and project are matching,
							 * append the server to the project */
							hrcview[hrcindex].Servers.push(dojo.clone(flatview[i]));
						}
					}
				}
		    }
			console.log ("Resulting hierarchical structure: " , hrcview);
			d2.callback(hrcview);
		});
		
		return d2;
	},
	
	/**
	 * Retrieves the hierarchy of catalog requests
	 * Caches the result for use later.
	 */
	_catalogCache: null,
	getRequestsCatalog: function(params)
	{
		if  ((this.restOnly == true) && ((params == undefined) || (params.ItemNum == undefined))) {
			console.log("Using REST navigation");
			return this.getListclf(params);
		}
		var deferred = null;
		if(undefined == params) params = {};
		var _sync = undefined == params.sync ? false : params.sync;
		
		// retrieve the entire hierarchy
		var requests_catalog = ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("RequestsCatalog");
		if(requests_catalog && requests_catalog.length > 0) {
			params.srmreq = requests_catalog;
			deferred = this.get(this.baseTDIUrl, _sync, params);
		}
		return deferred;
	},
	getOfferingInfo: function(itemNum)
	{
		var offering = null;
		if(undefined == this._catalogCache) {
			this.getRequestsCatalog({sync: true});
		}
		if(undefined != this._catalogCache) {
			
			function findOffering(category, itemNum) {
				//console.log("...searching category ", category.Description);
				for(var i = 0; i < category.length; ++i) {
					var cat = category[i];
					//console.log("...catagory: ", cat.Description);
					if(cat.Offering) {
						for(var j = 0; j < cat.Offering.length; ++j) {
							var off = cat.Offering[j];
							//console.log("......offering: ", off.ItemNum)
							if(itemNum == off.ItemNum) {
								//console.log("...... foundit.");
								return off;
							}
						}
					}
					if(cat.Category) {
						return findOffering(cat.Category, itemNum);
					}
				}
			}
			
			offering = findOffering(this._catalogCache.Category, itemNum);
			if(offering) {	// the in-context list and what's available to this user may not match
				this._patchRequestImageName(offering);
			}
		}
		return offering;
	},
	
	/**
	 * Returns the list of offerings in a tree format
	 */
	getListclf: function(params) {
		console.log("srmQuery.getListclf() enter ", params);
		
		if(undefined == params) params = {};
		 params._fd = "PMZHBT_USROFFERING";
		
		//console.log("params: ", params);
		var deferred = this.getObjectStructure("PMZHBR1_OFFERING", params);
		deferred.addCallback(dojo.hitch(this, function(response) {
			//console.log("Deferred response: ", response);
			var pmscsrvoff =
				response.QueryPMZHBR1_OFFERINGResponse.PMZHBR1_OFFERINGSet.PMSCSRVOFF;
			var offTable;	
			if (pmscsrvoff) {
				offTable = /* This is the offerings table */
					response.QueryPMZHBR1_OFFERINGResponse.PMZHBR1_OFFERINGSet.PMSCSRVOFF;
			} else {
				offTable = []; /* Fix to PTM PHYP0391GOC - in case of zero offerings */
			}
			var offTree = new ibm.tivoli.simplesrm.srm.dojo.data.OfferingTree;
			for (var i = 0; i < offTable.length; i++) {
				//console.log("current: ", offTable[i].DESCRIPTION);
				offTree.addOffering(offTable[i]);
			}
			var resTree = { Category : offTree._tree };
			console.log("srmQuery.getListclf() resTree:", resTree);
			this._catalogCache = resTree;
			return resTree;
		}));
		
		console.log("srmQuery.getListclf() exit");
		return deferred;
	},

	getIncidentsCatalog: function()
	{
		var deferred = null;
		var requests_catalog = ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty("IncidentsCatalog");
		if(requests_catalog && requests_catalog.length > 0) {
			deferred = this.get(this.baseTDIUrl, false, {srmreq: requests_catalog});
		}
		return deferred;
	},
	// returns a list of offerings that are appropriate in the given context
	getOfferingsInContext: function(contextName)
	{
		var off_in_context = [];
		var offlist = this.getDomainSynonymTable(contextName);
		if(offlist) {
			for(var i = 0; i < offlist.length; ++i) {
				var offering = this.getOfferingInfo(offlist.synonyms[i].MAXVALUE);
				if(offering) {
					off_in_context.push(offering);
				}
			}
		}
		console.log(off_in_context);
		return off_in_context;
	},
	
	// we get the request's icon image name from the query, but have to cook
	// up the path to the actual bits.
	imageCacheUrl: "/SRMCommonsWeb/MaxImageCache/",
	_patchRequestImageName: function(request) 
	{
		try {
			if(undefined == request.ImagePath || undefined == request.ImageName) {
				request.ImagePath = "";
				if(undefined == request.ImageName) {
					request.ImageName = "";
				}
				// build the URL to the offering's image
				if(request.ImageName.length > 0) {
					request.ImagePath =  this.imageCacheUrl + request.ImageName + "?REFID=" + request.ItemID;
				}
				else {
					request.ImagePath = "/SimpleSRM/js/simplesrm/srm/dijit/images/icons/default_request.png";
				}
			}
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("srmQuery._patchRequestImageName: " + ex);
		}
		return request;
	},
	
	getWCAPatterns : function(params){
		var url =this.baseRestUrl + "ilrest/rest/os/TPV01IMGLIBENTRYMSTR";	
		params.COLLECTION = true;
		params._compact='true';
		var deferred = this.get(url,true,params);
		deferred.addCallback(dojo.hitch(this, function(response) {
			return response;
		}));
		return deferred;		
	},
	
	/**
	 * Retrieves all the software stack data for the given ids
	 */
	getSoftwareStacksForPattern : function(/* swstack ids */params){
		var url =this.baseRestUrl + "ilrest/rest/os/TPV01WCASTACK";	
		var deferreds = [];
		for(var i=0; i<params.ids.length; i++){
			deferreds.push(this.get(url,true,{ID:params.ids[i],_compact:true}));
		}
		var deferred = new dojo.DeferredList(deferreds);
		deferred.addCallback(dojo.hitch(this, function(response) {
			var stacks = [];
			for(var i=0; i<response.length;i++){
				stacks.push(response[i][1].QueryTPV01WCASTACKResponse.TPV01WCASTACKSet.TPSOFTWARESTACK[0]);
			}
			response = stacks;
			return response;
		}));
		return deferred;			
	},
	
	_dummy:null
});
dojo.declare("ibm.tivoli.simplesrm.srm.dojo.data.SynonymDomain", null,
{
	synonyms: null,
	length: 0,
	
	constructor: function(synonyms) 
	{
		this.synonyms = synonyms;
		if(this.synonyms) {
			this.length = this.synonyms.length;
		} 
	},
	descriptionByValue: function(value)
	{
		var desc = this.fieldByKey("VALUE", "DESCRIPTION", value);
		if(undefined == desc)
			desc = value;
		return desc;
	},
	valueByMaxvalue: function(maxvalue)
	{
		return this.fieldByKey("MAXVALUE", "VALUE", maxvalue);
	},
	fieldByKey: function(keyfield, valfield, keyvalue)
	{
		for(var i = 0; i < this.length; ++i) {
			var s = this.synonyms[i];
			if(keyvalue === s[keyfield]) {
				return s[valfield];
			}
		}
		return null;
	}
});
//summary: the srmQuery singleton variable
ibm.tivoli.simplesrm.srm.dojo.data._srmquery = null;

ibm.tivoli.simplesrm.srm.dojo.data.srmQuery = function(){
	// summary: returns the singleton srmQuery object
	if(!ibm.tivoli.simplesrm.srm.dojo.data._srmquery){
		ibm.tivoli.simplesrm.srm.dojo.data._srmquery = new ibm.tivoli.simplesrm.srm.dojo.data._srmQuery();
	}
	return ibm.tivoli.simplesrm.srm.dojo.data._srmquery;	// Object
};

ibm.tivoli.simplesrm.srm.dojo.data._propmap = {};
ibm.tivoli.simplesrm.srm.dojo.data.getConfigProperty = function(pname)
{
	if(undefined == ibm.tivoli.simplesrm.srm.dojo.data._propmap[pname]) 
	{
		dojo.xhrGet({
			url: "/SRMCommonsWeb/SimpleSrm",
			content: {p: pname},
			sync: true,
			handleAs: "text",
			load: function(response, ioArgs) 
			{
				ibm.tivoli.simplesrm.srm.dojo.data._propmap[pname] = response.trim();
				return response;
			},
			error: function(response, ioArgs) 
			{
				var msg = "getConfigProperty failure";
				if(ioArgs && ioArgs.xhr) {
					msg += "(" + ioArgs.xhr.status + "): " + ioArgs.xhr.responseText;
				}
				console.error(msg);
				new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(msg);
				return response;
			}
		});
	}
	return ibm.tivoli.simplesrm.srm.dojo.data._propmap[pname];
}
