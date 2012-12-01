/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/

dojo.provide("ibm.tivoli.tip.dijit.TIPButton");
dojo.require("dijit.form.Button");
dojo.require("dijit.form._FormWidget");
dojo.require("dijit._Container");

dojo.declare("ibm.tivoli.tip.dijit.TIPButton",
              dijit.form._FormWidget,
{

	// label: String
	//	text to display in button
	label: "",

	// showLabel: Boolean
	//	whether or not to display the text label in button
	showLabel: true,

	// iconClass: String
	//	class to apply to div in button to make it display an icon
	iconClass: "",

	type: "button",
	baseClass: "dijitButton",
	templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit","templates/TIPButton.html"),	

	constructor: function () {
	},
	_onClick: function(/*Event*/ e){
		// summary: internal function to handle click actions
		if(this.disabled){ return false; }
		this._clicked(); // widget click actions
		return this.onClick(e); // user click actions
	},

	_onButtonClick: function(/*Event*/ e){
		// summary: callback when the user mouse clicks the button portion
		dojo.stopEvent(e);
		var okToSubmit = this._onClick(e) !== false; // returning nothing is same as true

		// for some reason type=submit buttons don't automatically submit the form; do it manually
		if(this.type=="submit" && okToSubmit){
			for(var node=this.domNode; node; node=node.parentNode){
				var widget=dijit.byNode(node);
				if(widget && widget._onSubmit){
					widget._onSubmit(e);
					break;
				}
				if(node.tagName.toLowerCase() == "form"){
					if(!node.onsubmit || node.onsubmit()){ node.submit(); }
					break;
				}
			}
		}
	},

	postCreate: function(){
		// summary:
		//	get label and set as title on button icon if necessary
		if (this.showLabel === false){
			var labelText = "";
			this.label = this.containerNode.innerHTML;
			labelText = dojo.trim(this.containerNode.innerText || this.containerNode.textContent);
			// set title attrib on iconNode
			this.titleNode.title=labelText;
			dojo.addClass(this.containerNode,"dijitDisplayNone");
    } else {
        // need to set button div width to 100% because
        // firefox has issues fitting the label into the button
        this.buttonDiv.style.width="100%";
    }
		this.inherited(arguments);
		//this.connectEvents();
	},

	onClick: function(/*Event*/ e){
		// summary: user callback for when button is clicked
		//      if type="submit", return value != false to perform submit
		return true;
	},

	_clicked: function(/*Event*/ e){
		// summary: internal replaceable function for when the button is clicked
	},

	setLabel: function(/*String*/ content){
		// summary: reset the label (text) of the button; takes an HTML string
		this.containerNode.innerHTML = this.label = content;
		if(dojo.isMozilla){ // Firefox has re-render issues with tables
			var oldDisplay = dojo.getComputedStyle(this.domNode).display;
			this.domNode.style.display="none";
			var _this = this;
			setTimeout(function(){_this.domNode.style.display=oldDisplay;},1);
		}
		if (this.showLabel === false){
				this.titleNode.title=dojo.trim(this.containerNode.innerText || this.containerNode.textContent);
		}
	},
	connectEvents: function () {
		dojo.connect (this, "onmousedown", this, this._onMouseDown);
		dojo.connect (this, "onmouseup", this, this._onMouseUp);
		dojo.connect (this, "onkeydown", this, this._onMouseDown);
		dojo.connect (this, "onkeydown", this, this._onMouseUp);
		
		dojo.connect (this, "onmouseover", this, this._onFocus);
		dojo.connect (this, "onmouseout", this, this._onBlur);
		dojo.connect (this, "onfocus", this, this._onFocus);
		dojo.connect (this, "onblur", this, this._onBlur);
		
	},
	_onMouseDown: function () {
		this.buttonDiv.className = "button-down";
	},
	_onMouseUp: function () {
		this.buttonDiv.className = "button-up";
	},
 	_onFocus: function () {
		this.buttonDiv.className = "button-over";
 	},
 	_onBlur: function () {
		this.buttonDiv.className = "button-out";
 	},
 	destroy: function () {
 		console.log ("enter TIPButton.destroy");
 		this.inherited (arguments);
 		console.log ("exit TIPButton.destroy");
 	}

});
