//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.PopupButton");

dojo.require("dijit.form.Button");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.data.srmQuery");

// Summary
//	There's a button, you click it and something pops up
//
// Attributes:
//		TODO: write me
//
// Events:
//		TODO: write me
//
//	Markup:
//		TODO: write me

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.PopupButton", [dijit.form.Button], {
	// Removed srm_button 
	//templateString : "<div title='${tooltip}' class='dijit dijitLeft dijitInline dijitButton'  dojoAttachPoint='focusNode' dojoAttachEvent='onclick:_onButtonClick,onmouseover:_onMouse,onmouseout:_onMouse,onmousedown:_onMouse,onkeypress:_onKeyPress'><div class='${iconClass}'></div></div>",
	//templateString:"<span class=\"dijit dijitReset dijitLeft dijitInline\"\n\t dojoAttachEvent='onclick:_onButtonClick,onmouseover:_onMouse,onmouseout:_onMouse,onmousedown:_onMouse,onkeypress:_onKeyPress'   \n\t><span class='dijitReset dijitRight dijitInline'\n\t\t><span class='dijitReset dijitInline dijitButtonNode'\n\t\t\t><button class=\"dijitReset dijitStretch dijitButtonContents\" \n\t\t\t\t${nameAttrSetting} type=\"${type}\" value=\"${value}\"\n\t\t\t\tdojoAttachPoint=\"focusNode,titleNode\" \n\t\t\t\twaiRole=\"button\" waiState=\"haspopup-true,labelledby-${id}_label\"\n\t\t\t\t><span class=\"dijitReset dijitInline\" \n\t\t\t\t\tdojoAttachPoint=\"iconNode\"\n\t\t\t\t></span\n\t\t\t\t><span class=\"dijitReset dijitInline dijitButtonText\"  \n\t\t\t\t\tdojoAttachPoint=\"containerNode,popupStateNode\" \n\t\t\t\t\tid=\"${id}_label\"\n\t\t\t\t></span\n\t\t\t\t><span class=\"dijitReset dijitInline dijitArrowButtonInner\">&thinsp;</span\n\t\t\t\t><span class=\"dijitReset dijitInline dijitArrowButtonChar\">&#9660;</span\n\t\t\t></button\n\t\t></span\n\t></span\n></span>\n",
	//iconClass: 'sliderbutton',
	//tooltip: '',
	_popupwidget: null,
	_opened: false,
	popupClass: null,
	popupArgs: {},
	
	constructor: function() 
	{
		console.debug("PopupButton.ctor");
	},
	postMixInProperties: function()
	{
		console.debug("PopupButton.postMixInProperties");
		this.inherited(arguments);
	},
	buildRendering: function()
	{
		this.inherited(arguments);
	},
	startup: function()
	{
		if(this._started){ return; }
		console.debug("PopupButton.startup");
		
		this.inherited(arguments);
	},
	_onButtonClick: function(evt)
	{
		dojo.stopEvent(evt);
		if(this.disabled){ return false; }
		if(typeof this.onClick == "function")
			return this.onClick(evt); // user click actions
		else 
			return false;
	},
	_onKeyPress: function(/* Event */ evt)
	{
		console.debug('PopupButton._onKeyPress');
		
		if(evt.type == 'keypress' && evt.keyCode == dojo.keys.ENTER) {
			// TODO: is this really what I want to do?
			this.onClick(evt);
		}
	},
	onClick: function(/*Event*/ e){
		// summary: user callback for when button is clicked
		//      if type="submit", return value != false to perform submit
		
		this._open();
	
		return false;
	},
	getPopup: function() {
		if (!this._popupwidget) {
			this._createPopup();
		}
		
		return this._popupwidget;
	},
	_createPopup: function() {
		//var PopupProto=dojo.getObject(this.popupClass, false);
		this._popupwidget = new this.popupClass(this.popupArgs);
		this._popupwidget.startup();
	},
	_open: function(){

		if(this.disabled || this.readOnly || this._opened){return;}
		
		if (!this._popupwidget) {
			this._createPopup();
		}
		
		var popupButton = this;
		
		console.debug("PopupButton._open");
		
		if(!this._opened){
			dijit.popup.open({
				parent: this,
				popup: this._popupwidget,
				around: this.domNode,
				onCancel: dojo.hitch(this, this._close),
				onClose: dojo.hitch(this, this._close)
			});
			this._opened=true;
		}
	
		this._opened=true;
		if(this._popupwidget.focus){
			this._popupwidget.focus();
		}

	},

	_onBlur: function(){
		this._close();

		this.inherited(arguments);
	},
	
	_close: function(){
		if(this._opened){
			dijit.popup.close(this._popupwidget);
			this._opened=false;
		}			
	},
	destroy: function(){
		if(this._popupwidget){
			this._popupwidget.destroyRecursive();
			delete this._popupwidget;
		}
		this.inherited(arguments);
	},
	_dummy:null

});
