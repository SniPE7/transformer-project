//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

/**
** Implementation that dynamically generates an input form.
** Not as specialized as a custom-crafted form, but can get the job done.
*/
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.request.GenericForm");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.CreateCatalogRequest");

dojo.require("dojo.parser");
dojo.require("ibm.tivoli.tip.dijit.TextInputBox");		// has no name attribute, so form data is hosed when posted
dojo.require("dijit.form.ValidationTextBox");
//dojo.require("dijit.form.SimpleTextarea");	// not until 1.2
dojo.require("ibm.tivoli.tip.dijit.TextArea");
dojo.require("ibm.tivoli.tip.dijit.NumberInputBox");
	

// SPECIALIZATION: you must make a call to dojo.requireLocalization
//dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit.request", "PMSC_0001A");

dojo.declare(
	"ibm.tivoli.simplesrm.srm.dijit.request.GenericForm",
	[ibm.tivoli.simplesrm.srm.dijit.CreateCatalogRequest],
{
	_text_size: 25,
	_num_size: 6,
	ALN: "ALN",
	NUMERIC: "NMERIC",
	TABLE: "TABLE",
	
	constructor: function(/*ojbect*/params, /*DOMNode*/domNode)
	{
		console.log("GenericForm.ctor");
		// since this class is in a subfolder, need to reset imagesPath
		this.imagesPath = this.getRelativePath("../images");
	},
	/*
	** Default implementation loads i18n strings and sets the templatePath as a function of the requestName
	** SPECIALIZATION: If you need to do something different, override.
	*/
	postMixInProperties: function()
	{
		console.log("GenericForm.postMixInProperties");
		this.inherited(arguments);
	},
	buildRendering: function()
	{
		this.inherited(arguments);		

		// now generate requst speciic part of the form
		this._createInputForm();

	},
	startup: function()
	{
		this.inherited(arguments);
		var datatypeMap = ibm.tivoli.simplesrm.srm.dojo.data.srmQuery().getDomainSynonymTable('DATATYPE');
		this.ALN = datatypeMap.valueByMaxvalue("ALN");
		this.NUMERIC = datatypeMap.valueByMaxvalue("NUMERIC");
		this.TABLE = datatypeMap.valueByMaxvalue("MAXTABLE");
//		if(this.readOnly) {
//			dojo.byId(this.id + "_Description").disabled = true;
//			dijit.byId(this.id + "_Priority").setDisabled(true);
//			dijit.byId(this.id + "_requestedfor").setDisabled(true);
//		}
	},
	_createInputForm: function()
	{
		this._genForm(this.requestDetails);
	},
	_genForm: function(attributes)
	{
		try {
			if( !this.requestDetails ) {
				throw new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(new Error("Can't generate form, missing request attributes"));
			}
			
			var desc = this.requestDetails.Description;
			
			var f = "<table class='GenericForm form_table'>"
			
			// first, the common attributes
			// TOOD: dojo 1.2 has dijit.form.SimpleTexarea.  Until then, put some dijit classes on a generic textarea to get it looking right
			f += "<tr><td><label for='"+this.id+"_Description' class='required'>Description</label></td><td>"
			  + this._genTextArea({id: this.id+"_Description", required: true, value: desc});
			  + "</td></tr>\n"
			  + "<tr><td><label for='"+this.id+"_Priority'>Priority</label></td><td>"+this._genInputElement({AssetAttrID: "Priority", Mandatory: "false", DataType: this.ALN, ALNValue: 1})+"</td></tr>\n"
			  + "<tr><td><label for='"+this.id+"_requestedfor' class='required'>${_uiStringTable.RequestedFor}</label></td><td>"+this._genInputElement({AssetAttrID: "requestedfor", Mandatory: "true", DataType: this.ALN, ALNValue: ibm.tivoli.simplesrm.srm.dijit.loggedInUsername})+"</td></tr>\n";
			
			
			var hiddens = "";
			attributes = this.requestDetails.Attribute;
			for(var i in attributes) 
			{
				var attr = attributes[i];
				if("TSAM Substitution Expression" == attr.Description) {
					continue;	// skip it. 
				}
			
				if(attr.Hidden === "true") {
					hiddens += this._genHiddenInput(attr);
				}
				else {
					req = attr.Mandatory == "true" ? "required" : "";
					f += "<tr><td><label for='" + attr.AssetAttrID + "' class='"+req+"'>" + attr.Description + "</label></td><td>"+this._genInputElement(attr)+"</td>";
				}
			}
			f += "</table>\n";
			this.form_container.innerHTML = f + hiddens;
			dojo.parser.parse(this.form_container);
			
			
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex); 
			(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: "CTJZH2301E"})).show();
		}
	},
	_genHiddenInput: function(attr)
	{
		var elem_id = this.id + "_" +  attr.AssetAttrID;
		var elem_name = attr.AssetAttrID;
		var value = this._getAttrValue(attr);
		return "<input type='hidden' id='"+elem_id+"' name='"+elem_name+"' value='"+value+"'>";
	},
	_genInputElement: function(attr)
	{
		var constraints = "{required: " + (("true" == attr.Mandatory) ? "true" : "false");
		if(typeof attr.MinValue == "number") {
			constraints += ", min: " + attr.MinValue;
		}
		else if(attr.DataType == this.NUMERIC) {
			constraints += ", min: 0";
		}
		constraints +=  "}";
		var size = "size='" + (this.NUMERIC == attr.DataType ? this._num_size : this._text_size) + "'";
		
		var input_elem = '';
		var elem_id = this.id + "_" + attr.AssetAttrID;
		var elem_name = attr.AssetAttrID;
		var value = this._getAttrValue(attr);
		var errmsg = attr.Mandatory ? "A value is required" : "This value is not valid";
		
		switch(attr.DataType) {
		case this.NUMERIC:
			input_elem = "<input id='"+elem_id+"' dojoType='ibm.tivoli.tip.dijit.NumberInputBox' name='"+elem_name+"' constraints='"+constraints+"' value='"+value+"' "+size+" regexpMessage='"+errmsg+"'>";
			break;
		case this.ALN:
			input_elem = "<input id='" +elem_id+ "' dojoType='ibm.tivoli.tip.dijit.TextInputBox' constraints='"+constraints+"' name='" + elem_name + "' value='"+value+"' "+size+" regexpMessage='"+errmsg+"'>";
			break;
		case this.TABLE:
			input_elem = "<input id='" +elem_id+ "' dojoType='ibm.tivoli.tip.dijit.TextInputBox' constraints='"+constraints+"' name='" + elem_name + "' value='"+value+"' "+size+" regexpMessage='"+errmsg+"'>";
			break;
		default:
			break;
		}

		return input_elem;
	},

	_genTextArea: function(params)
	{
		var clazz = params.required ? "tip-tib-required" : "tip-tib-normal";
		return "<table cellspacing='0' cellpadding='0' border='0' style='' class='"+clazz+"'>"
			+ "<tr>"
			+ 	"<td class='tf-l'> </td>"
			+ 	"<td class='tf-m' valign='middle'>"
			+ 		"<textarea class='tundra dijitTextArea tf' id='"+params.id+"' cols='25' rows='1' name='Description' >"+params.value+"</textarea>"
// doesn't look right
//			+ 		"<textarea dojoType='ibm.tivoli.tip.dijit.TextArea' id='"+params.id+"' cols='25' rows='2' name='Description' >"+params.value+"</textarea>"
			+ 	"</td>"
			+ 	"<td class='tf-r'> </td>"
			+ "</tr>"
			+ "</table>";
	},

	
	_dummy:null
});
