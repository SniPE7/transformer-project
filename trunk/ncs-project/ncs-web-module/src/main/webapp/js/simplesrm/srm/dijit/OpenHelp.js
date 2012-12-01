//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.OpenHelp");

/*
 * groups methods/queries responsible for providing help 
 */

dojo.declare(
		"ibm.tivoli.simplesrm.srm.dijit.OpenHelp",
		[],
{		
				
		//retrieves url to help topic that maps to given helpKey
		//after successful retrieval "action(url)" is launched	
		_help:function(helpKey , action){			
			console.log("_help called");
			var params={};
			if(dojo.isString(helpKey)){
				dojo.mixin(params,{helpKey: helpKey});
			}
			else if (dojo.isArray(helpKey)){
				//assume that array contains valid parameters
				dojo.forEach(helpKey, function(x){
					dojo.mixin(params,x);
				});
			}
			var helpUrl;
			var deferred = dojo.xhrGet({
				url: "/SRMCommonsWeb/SimpleSrm",
				content: params,
				handleAs: "text",
				load: function(response){					
					if(response){
						helpUrl = response;
					}
					else{
						console.log("invalid result");
						return new Error();
					}
				},
				error: function(error){
					console.log("xhrGet failed: " + error.message);					
				}
			}
			);	
					
			deferred.addCallback(function(){								
				action(helpUrl);						
			});
			deferred.addErrback(this._errorRetrievingUrl);
								
		},
		
		addInfocenterUrl: function(link){
			var action = function(link,url){
				dojo.attr(link , "href" , url);
			};
			this._help("PMRDP_UI_Help.htm",dojo.hitch(null, action, link));
		},	
		openHelpWindow: function(helpKey){
			var action = function(url){
				window.open(url);
			}
			this._help(helpKey , action);
		},
		//extension point 
		_errorRetrievingUrl: function(){				
			//default == no action	
			console.log("error retrieving help url");
		}
		
		});
