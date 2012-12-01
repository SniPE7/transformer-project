/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.provide("ibm.tivoli.tip.dojo.TIPHelper");

dojo.declare("ibm.tivoli.tip.dojo.TIPHelper",
	          null,
{
	constructor: function () {
	},
	// childNode must be a node within your portlet
	// padding is the amount of padding you want around your portlet
	//   e.g., "0px"
	setPortletPadding: function (childNode, padding) {
		var node = this.getAncestorByClass (childNode, "isc-portlet-bkg");
		dojo.style (node, "padding", padding);
	    var nextnode = dojo.query( 'table[id$="_portlet_content"]', node )[0];
        if (nextnode) {
        	dojo.style( nextnode, "padding", padding );
        }
	},
	getAncestorByClass: function (element, className) {
		var result=null;
		try {
			while(result===null && element!==null) {
				element=element.parentNode;
				if(dojo.hasClass (element, className)) {
					result=element;
				}
			}
		} catch(e) {
			//no node
		}
		//console.log ("Nodename = " + result);
		return(result);
	},
	getAncestorByName: function (element, name) {
		var result=null;
		try {
			while(result===null && element!==null) {
				element=element.parentNode;
				var elementname = element.getAttribute ("name");
				if (elementname == name) {
					result=element;
				}
			}
		} catch(e) {
			//no node
		}
		//console.log ("Nodename = " + result);
		return(result);
	},
	getAncestorByID: function (element, id) {
		var result=null;
		try {
			while(result===null && element!==null) {
				element=element.parentNode;
				var elementid = element.getAttribute ("id");
				if (elementid == id) {
					result=element;
				}
			}
		} catch(e) {
			//no node
		}
		//console.log ("Nodename = " + result);
		return(result);
		
	},
	getPortletOID: function (element) {
		var node = this.getAncestorByName (element, "IscPortlet");
		if (node) {
			console.log ("get portlet data: ");
			console.log (node.modelObject);
			var result = node.modelObject.portletData.oid;
			return (result);
		}
	},
	getPortletWindowID: function (element) {
		var node = this.getAncestorByName (element, "IscPortlet");
		if (node) {
			var result = node.modelObject.portletData.portletName;
			return (result);
		}
	},
	getPortletPageID: function (element) {
		var node = this.getAncestorByName (element, "IscPage");
		if (node) {
			var result = node.modelObject.pageData.uniquePageName;
			return (result);
		}
	},
	getPortletPageName: function (element) {
		var parent = this.getAncestorByName (element, "IscPage");
		if (parent) {
		var node = dojo.query ("td.isc-layout-titletext", parent);
//		var node = dojo.query ("td.isc-tab-sel-l", frames["taskbar"].document);
		if (node) {
			if (node.length == 1) {
//				var result = node[0].innerHTML.alt;
				var result = node[0].childNodes[0].nodeValue;
				if (result) {
					// strip leading white space
					result = result.replace(/^\s+/,"");
					// strip trailing white space
					result = result.replace(/\s+$/,"");
				}
				console.log ("portlet page name: ");
				console.log (result);
				return (result);
			} else if (node.length === 0) {
				console.log ("could not find node for portlet page name");
			} else {
				console.log ("found multiple nodes for portlet page names");
				console.log (node);
			}
		} else {
			console.log ("could not find node for portlet page name");
		}
		} else {
			console.log ("no parent node to get portlet page name");
		}
	},
	getTopPortletNode: function (element) {
		var node = this.getAncestorByName (element, "IscPortlet");
		return (node);		
	},
	getPortletTitle: function (element) {
		var oid = this.getPortletOID (element);
		if (oid) {
			var node = dojo.byId ("titleText." + oid);
			if (node) {
				return node.innerHTML;
			} else {
				console.log ("cannot find node for title text: titleText." + oid);
			}
		} else {
			console.log ("cannot find oid for element: ");
			console.log (element);
		}
	},
	setPortletTitle: function (element, title) {
		console.log ("enter setPortletTitle: " + title);
		var oid = this.getPortletOID (element);

		if (oid) {
			var node = dojo.byId ("titleText." + oid);
			if (node) {
				node.innerHTML = title;
			} else {
				console.log ("cannot find node for title text: titleText." + oid);
			}
		} else {
			console.log ("cannot find oid for element: ");
			console.log (element);
		}
	}
});
