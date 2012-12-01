/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * 5724-C51
 *
 * (C) Copyright IBM Corp. 2007 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.provide("ibm.tivoli.tip.dojo.TIPQueryReadStore");
dojo.require("dojox.data.QueryReadStore");
dojo.require("ibm.tivoli.tip.dojo.Global");
dojo.require("ibm.tivoli.tip.dojo.Server");

// Override QueryReadStore to make it work with paging
// See changes between: Begin Change - JSA
//                      End Change - JSA

dojo.declare("ibm.tivoli.tip.dojo.TIPQueryReadStore",
	          dojox.data.QueryReadStore,
{
    global: null,
    tableID: null,
    loadCompleteCallback: null, 

	/* Default timeout for requests made to the server */
	requestTimeout: 30000,
	
    
    setLoadCompleteCallback: function(aFunction){
        this.loadCompleteCallback = aFunction;
    },
    
	fetch:function(/* Object? */ request){
		//	summary:
		//		See dojo.data.util.simpleFetch.fetch() this is just a copy and I adjusted
		//		only the paging, since it happens on the server if doClientPaging is
		//		false, thx to http://trac.dojotoolkit.org/ticket/4761 reporting this.
		//		Would be nice to be able to use simpleFetch() to reduce copied code,
		//		but i dont know how yet. Ideas please!
		console.log ("enter fetch w/ request:");
		console.log (request);
		request = request || {};
		if(!request.store){
			request.store = this;
		}
		var self = this;
	
		var _errorHandler = function(errorData, requestObject){
			if(requestObject.onError){
				var scope = requestObject.scope || dojo.global;
				requestObject.onError.call(scope, errorData, requestObject);
			}
		};
	
	    
		// Begin Change - JSA
		var _fetchHandler = function(items, requestObject, data){
		// End Change - JSA
			var oldAbortFunction = requestObject.abort || null;
			var aborted = false;
			
			var startIndex = requestObject.start?requestObject.start:0;
			if (self.doClientPaging==false) {
				// For client paging we dont need no slicing of the result.
				startIndex = 0;
			}
			var endIndex   = requestObject.count?(startIndex + requestObject.count):items.length;
	
			requestObject.abort = function(){
				aborted = true;
				if(oldAbortFunction){
					oldAbortFunction.call(requestObject);
				}
			};
	
			var scope = requestObject.scope || dojo.global;
			if(!requestObject.store){
				requestObject.store = self;
			}
			if(requestObject.onBegin){
				// Begin Change - JSA 
				requestObject.onBegin.call(scope, data.rowCount, requestObject);
				// End Change - JSA
			}
			if(requestObject.sort){
				items.sort(dojo.data.util.sorter.createSortFunction(requestObject.sort, self));
			}
			if(requestObject.onItem){
				for(var i = startIndex; (i < items.length) && (i < endIndex); ++i){
					var item = items[i];
					if(!aborted){
						requestObject.onItem.call(scope, item, requestObject);
					}
				}
			}
			if(requestObject.onComplete && !aborted){
				var subset = null;
				if (!requestObject.onItem) {
					subset = items.slice(startIndex, endIndex);
				}
				requestObject.onComplete.call(scope, subset, requestObject);   
			}
		};
		
		
		this._fetchItems(request, _fetchHandler, _errorHandler);
		return request;	// Object
	},
	
	_fetchItems: function(request, fetchHandler, errorHandler){
		//	summary:
		// 		The request contains the data as defined in the Read-API.
		// 		Additionally there is following keyword "serverQuery".
		//
		//	The *serverQuery* parameter, optional.
		//		This parameter contains the data that will be sent to the server.
		//		If this parameter is not given the parameter "query"'s
		//		data are sent to the server. This is done for some reasons:
		//		- to specify explicitly which data are sent to the server, they
		//		  might also be a mix of what is contained in "query", "queryOptions"
		//		  and the paging parameters "start" and "count" or may be even
		//		  completely different things.
		//		- don't modify the request.query data, so the interface using this
		//		  store can rely on unmodified data, as the combobox dijit currently
		//		  does it, it compares if the query has changed
		//		- request.query is required by the Read-API
		//
		// 		I.e. the following examples might be sent via GET:
		//		  fetch({query:{name:"abc"}, queryOptions:{ignoreCase:true}})
		//		  the URL will become:   /url.php?name=abc
		//
		//		  fetch({serverQuery:{q:"abc", c:true}, query:{name:"abc"}, queryOptions:{ignoreCase:true}})
		//		  the URL will become:   /url.php?q=abc&c=true
		//		  // The serverQuery-parameter has overruled the query-parameter
		//		  // but the query parameter stays untouched, but is not sent to the server!
		//		  // The serverQuery contains more data than the query, so they might differ!
		//

		var serverQuery = request.serverQuery || request.query || {};
		//Need to add start and count
		if(!this.doClientPaging){
			serverQuery.start = request.start || 0;
			// Count might not be sent if not given.
			if (request.count) {
				serverQuery.count = request.count;
			}
		}
		// Compare the last query and the current query by simply json-encoding them,
		// so we dont have to do any deep object compare ... is there some dojo.areObjectsEqual()???
		if(this.doClientPaging && this._lastServerQuery!==null &&
			dojo.toJson(serverQuery)==dojo.toJson(this._lastServerQuery)
			){
			fetchHandler(this._items, request);
		}else{
			// var xhrFunc = this.requestMethod.toLowerCase()=="post" ? dojo.xhrPost : dojo.xhrGet;
			// var xhrHandler = xhrFunc({url:this.url, handleAs:"json-comment-optional", content:serverQuery});
			var kResponseHandler = dojo.hitch(this, function(data){
				data=this._filterResponse(data);
				this._items = [];
				// Store a ref to "this" in each item, so we can simply check if an item
				// really origins form here (idea is from ItemFileReadStore, I just don't know
				// how efficient the real storage use, garbage collection effort, etc. is).
				dojo.forEach(data.items,function(e){ 
					this._items.push({i:e, r:this}); 
				},this); 
				
				var identifier = data.identifier;
				this._itemsByIdentity = {};
				if(identifier){
					this._identifier = identifier;
					for(i = 0; i < this._items.length; ++i){
						var item = this._items[i].i;
						var identity = item[identifier];
						if(!this._itemsByIdentity[identity]){
							this._itemsByIdentity[identity] = item;
						}else{
							throw new Error("dojo.data.QueryReadStore:  The json data as specified is malformed.  Items within the list have identifier: [" + identifier + "].  Value collided: [" + identity + "]");
						}
					}
				}else{
					this._identifier = Number;
					for(i = 0; i < this._items.length; ++i){
						this._items[i].n = i;
					}
				}
				
				// TODO actually we should do the same as dojo.data.ItemFileReadStore._getItemsFromLoadedData() to sanitize
				// (does it really sanititze them) and store the data optimal. should we? for security reasons???
				// Begin Change - JSA
				fetchHandler(this._items, request, data);
				
				// If a callback is registered, then call it now.  Recall this is used to help
				// smoothly replace models on a refresh to avoid flashing.
				if (this.loadCompleteCallback) {
				    this.loadCompleteCallback();
				}
				// End Change - JSA
			});
			
			var kErrorHandler = function(error){
				errorHandler(error, request);
			};
			
			// Generate the hash using the time in milliseconds and a randon number.
			// Since Math.randon() returns something like: 0.23453463, we just remove the "0."
			// probably just for esthetic reasons :-).
			this.lastRequestHash = new Date().getTime()+"-"+String(Math.random()).substring(2);
			this._lastServerQuery = serverQuery;

            var kw = {
                    content:   {
                                  command: "getTableRows",
                                  arg_fqTableId: this.tableID,
                                  arg_portletNamespace: this.global.namespace
                               },
                    timeout:   this.requestTimeout,
                    onResponse:{
                                  callback: kResponseHandler
                               },
                    onError:{
                                  callback: kErrorHandler
                               }
            };
            
            // Add server query stuff
            kw.content = dojo.mixin(kw.content,serverQuery);
            
            if (this.requestMethod.toLowerCase()=="post") {
                ibm.tivoli.tip.dojo.Server.getSingleton( this.global ).post( kw );
            } else {
                ibm.tivoli.tip.dojo.Server.getSingleton( this.global ).get( kw );
            }
			
		}
	},
	
	_filterResponse: function(data){
		//	summary:
		//		If the data from servers needs to be processed before it can be processed by this
		//		store, then this function should be re-implemented in subclass. This default 
		//		implementation just return the data unchanged.
		//	data:
		//		The data received from server
		return data;
	}
	
	
});
	
