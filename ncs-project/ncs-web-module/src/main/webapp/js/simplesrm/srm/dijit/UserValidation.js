//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.UserValidation");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.UserValidator");

// include modules
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dojo.parser");
dojo.require("dijit.form.Button");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.WidgetBase");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.ProgressSpinner");


ibm.tivoli.simplesrm.srm.dijit.loggedInUsername = "";	// a global. gasp!

/**
** handles maximo user validation
*/
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.UserValidator", null,
{
	statusValues: {UNKNOWN: -1, SUCCESS: 0, INVALID_USER_OR_PASS: 1, FAILURE: 2, SERVER_ERROR: 3, UNEXPECTED: 4},
	validationStatus: -1,
	validationMessage: "CTJZH2307E",
	username: "",
	validationURL: null,
	
	constructor: function()
	{
		// set servlet URL
		this.validationURL = "/SRMCommonsWeb/MaxUserAuthentication";
	},
	validateUser: function(username, password)
	{
		this._ajaxRequestValidateUser(username, password);
		return this.validationStatus == this.statusValues.SUCCESS;
	},
	_ajaxRequestValidateUser: function(username, password)
	{
		console.log("UserValidator._ajaxRequestActiveValidation");

		this.validationStatus = this.statusValues.UNKNOWN;
		this.validationMessage = "SMRMSG0003";
		this.username = "";
		ibm.tivoli.simplesrm.srm.dijit.loggedInUsername = "";
		
		var loginHandler = dojo.hitch(this, this._onLogin);
		var loginErrHandler = dojo.hitch(this, this._onLoginErr);
		
		username = ("undefined" === typeof username) ? "" : username;
		password = ("undefined" === typeof password) ? "" : password;
		var post_data = (username.length > 0) ? "targetUser="+username+"&targetPassword="+password : "";
		
		// have to issue a request that actually succeeds, or we can't tell a login failure from
		// some other failure	
		dojo.rawXhrPost({
			url: this.validationURL,
			postData: post_data,
			sync: true,			// login should be synchronous
			timeout: 15000,
			handleAs: "xml",			
			load: loginHandler,
			error: loginErrHandler
		});
	},
	_onLogin: function(response, ioArgs)
	{
		if(response.documentElement.tagName.match(/error/i)) {
			this.validationStatus = this.statusValues.SERVER_ERROR;
			this.validationMessage = "CTJZHE237";
		}
		else {
			this.validationStatus = this.statusValues.SUCCESS;
			this.validationMessage = "CTJZH231I";
			try {
				var userid = response.getElementsByTagName("USERID");
				if(userid.length > 0) {
					for(var node = userid[0].firstChild; node != null && node.nodeType != 3; node = node.firstChild);
					if(node) {
						this.username = node.nodeValue;
					}
				}
				else
					this.username = "";
			}
			catch(ex) {
				this.username = "";
			}
			ibm.tivoli.simplesrm.srm.dijit.loggedInUsername = this.username;
			this._fireSrmSetLogin();
		}
	},
	_onLoginErr: function(response, ioArgs)
	{
		console.log("UserValidator.error");
		var respstr = ioArgs.xhr.responseText;
		console.log(respstr);
		if( 401 == ioArgs.xhr.status ) {	
			console.log("401: Invalid username or password");
			this.validationStatus = this.statusValues.INVALID_USER_OR_PASS;
			this.validationMessage = "CTJZHE2307"
		}
		else if ( 500 == ioArgs.xhr.status ){
			console.log("500: Error processing login: " + response.message);
			this.validationStatus = this.statusValues.FAILURE;
			this.validationMessage = "CTJZHE2307";
		}
		else {
			console.log("Login failed:\n" + response.message);
			this.validationStatus = this.statusValues.UNEXPECTED;
			this.validationMessage = "CTJZHE2307";
		}
	},
		
	_fireSrmSetLogin: function() {
		console.log("UserValidator._fireSrmSetLogin(%s)", this.username);
		
		try {
			if("function" == typeof this.onSrmSetLogin) {
				this.onSrmSetLogin(this.username);
			}
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			console.error("Error in UserValidator.SrmSetLogin handler:", ex);
		}
	}
});

/*
** Puts a user interface on top of the UserValidator
*/
dojo.declare(
	"ibm.tivoli.simplesrm.srm.dijit.UserValidation",
	[dijit._Widget, dijit._Templated, ibm.tivoli.simplesrm.srm.dijit.WidgetBase, ibm.tivoli.simplesrm.srm.dijit.UserValidator],
{		
	widgetsInTemplate: true,
	templateString: "<div class='srmuservalidation'>"
		+"<h2>${_uiStringTable.LoginHeading}</h2>"
		+"<table border='0'>"
		+"<tr><td colspan='2' class='heading'>${_uiStringTable.Username}</td></tr>\n"
		+"<tr><td class='value'><input type='text' id='${id}_username_input' class='srmusername'></td><td><div id='${id}_spinner' dojoType='ibm.tivoli.simplesrm.srm.dijit.ProgressSpinner' style='padding-left:5px;'></div></td></tr>\n"
		+"<tr><td colspan='2' class='heading'>${_uiStringTable.Password}</td></tr>\n"
		+"<td class='value'><input type='password' id='${id}_password_input' class='srmpassword' dojoAttachEvent='onkeypress:_onPasswordKeypress'></td><td><input id='${id}_login_btn' type='button' value='${_uiStringTable.Login}' dojoAttachEvent='onclick:validate'></td></tr>\n"
		+"</table></div>",
	usernameText: 'Username',
	passwordText: 'Password',
	loginText: 'Login',
	
	constructor: function(/*object*/params, /*domNode*/domNode)
	{
		console.log("UserValidation.ctor:");
		
		this.progressSpinner = null;
	},
	startup: function()
	{
		console.log("UserValidation.startup");
		this.inherited(arguments);
				
		// login on clicking the button, or pressing <enter> in the password field
		//dojo.connect(dojo.byId(this.id+'_login_btn'), "onclick", this, "validate");
		//dojo.connect(dojo.byId(this.id+'_password_input'), "onkeypress", this, "_onPasswordKeypress");
		
		this.progressSpinner = dijit.byId(this.id + "_spinner");

	},
	validate: function()
	{
		this.srmUsername = null;
		try {
			this.progressSpinner.show();
			dojo.style(document.body, "cursor", "wait");
			dojo.byId(this.id+'_username_input').disabled = true;
			dojo.byId(this.id+'_password_input').disabled = true;
			dojo.byId(this.id+'_login_btn').disabled = true;
			
			var username = dojo.byId(this.id+"_username_input").value;
			var password = dojo.byId(this.id+"_password_input").value;
			if( this.validateUser(username, password) ) {
				this.srmUsername = this.username;
				console.log("srmUsername: ", this.srmUsername);
			}
			else {
				(new ibm.tivoli.simplesrm.srm.dijit.MessageDialog({messageId: this.validationMessage})).show();
			}
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			console.log("Uservalidation error: " + ex);
		}
		finally {
			this._postValidationCleanup();
		}

	},
	_postValidationCleanup: function() 
	{
		console.log("UserValidation._postValidationCleanup");
		this.progressSpinner.hide();
		dojo.style(document.body, "cursor", "default");
		dojo.byId(this.id+'_username_input').disabled = false;
		dojo.byId(this.id+'_password_input').disabled = false;
		dojo.byId(this.id+'_login_btn').disabled = false;
	},
	_onPasswordKeypress: function(evt)
	{
		if(evt.keyCode == 13) {
			this.validate();
		}
	}	
});
