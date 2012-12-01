//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.DropDownSelect");
dojo.require("dojox.form.DropDownSelect");


// dojox.form.DropDownSelect doesn't submit anything with the form!
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.DropDownSelect", dojox.form.DropDownSelect, 
{
	submitter: null,
	constraints: null,
	
	postMixInProperties: function()
	{
		// all the forms use constraints="{required:true}", but this widget
		// doesn't do it that way, it simply has a required property.
		// handle the conversion
		if(undefined != this.constraints && undefined != this.constraints.required) {
			this.required = this.constraints.required;
		}
		this.inherited(arguments);
	},
	removeOptions: function()
	{
		var nopts = this.getOptions().length;
		if(nopts > 0) {
			var indexArray = [];
			for(var i = 0; i < nopts; ++i)
				indexArray.push(i);
			this.removeOption(indexArray);
		}		
	}
});
