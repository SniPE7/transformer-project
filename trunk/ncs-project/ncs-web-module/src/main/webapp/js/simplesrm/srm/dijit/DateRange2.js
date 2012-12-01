//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.DateRange2");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.DateTextBox");

dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.RadioButton");
dojo.require("dojo.date.locale");
dojo.require("dojox.form.DropDownSelect");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError");
dojo.require("dojo.i18n");
dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.DateRange2",
	[dijit._Widget, dijit._Templated],
{
	widgetsInTemplate: true,
	templatePath: dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit", "templates/DateRange2.html"),
	
	startName: "StartDate",
	endName: "EndDate",
	valueStart: null,	// only used for setting startDate via markup
	valueEnd: null,		// only used for setting endDate via markup
	
	constraints: null,			// constraints on the date range.  valid values are min and max, where each may be a 
								// javascript date object, the number of millisecs since 1970-01-01, or an ISO 8601 datetime string
	_stringTable: null,
	_suppressOnChange: 0,
	_mode: "forever",
	
	_durationValueWidget: null,
	_durationUnitsWidget: null,
	_startDateWidget: null,
	_endDateWidget: null,
	_dateRegExp: null,
	_connects: null,
	_origStartValidator: null,
	_origEndValidator: null,
	
	_divDuration: null,
	_divUntil: null,
	_modeDropDown: null,
	
	postMixInProperties: function()
	{
		if("now" == this.valueStart) {
			this.valueStart = new Date();
		}
		this._stringTable = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");
		
		this.inherited(arguments);
	},
	buildRendering: function()
	{
		this.inherited(arguments);
	},
	postCreate: function()
	{
		
		this._divDuration = dojo.byId(this.id + "_divDuration");
		this._divUntil = dojo.byId(this.id + "_divUntil");
		this._modeDropDown = dijit.byId(this.id + "_modeDropDown");
	
		this._durationValueWidget = dijit.byId(this.id + "_durationValue");
		this._durationUnitsWidget = dijit.byId(this.id + "_durationUnits");
		this._startDateWidget = dijit.byId(this.id + "_startDate");
		this._endDateWidget = dijit.byId(this.id + "_endDate");
		this._dateRegExp =  new RegExp(dojo.date.locale.regexp({selector: "date"}));
		this._checkConstraints();
		this._syncDuration();
		this.inherited(arguments);
	},
	startup: function()
	{
		console.log("DateRange2.startup");
//!		// hook in my date range validator into the DateTextBox's validator
//!		this._origStartValidator = dojo.hitch(this._startDateWidget, this._startDateWidget.validator);
//!		this._startDateWidget.validator = dojo.hitch(this, this._startValidator);
//!		this._origEndValidator = dojo.hitch(this._endDateWidget, this._endDateWidget.validator);
//!		this._endDateWidget.validator = dojo.hitch(this, this._endValidator);
		
		this._connects = [];
		this._connects.push(this.connect(this._startDateWidget.focusNode, 'onkeypress', this._onStartDateKeyPress));
		this._connects.push(this.connect(this._endDateWidget.focusNode, 'onkeypress', this._onendDateKeyPress));
		this._syncWidgetStates();
		this.inherited(arguments);
	},
	destroy: function()
	{
		for(var c in this._connects) {
			dojo.disconnect(this._connects[c]);
		}
		this._startDateWidget.validator = this._endDateWidget.validator = null;
		this.inherited(arguments);
	},
	validate: function(/*Boolean*/ isFocused)
	{
		return (this._durationValueWidget.attr("disabled") || this._durationValueWidget.validate(isFocused))
		&& this._startDateWidget.validate(isFocused) && this._endDateWidget.validate(isFocused);
	},
	isValid: function()
	{
		return (this._durationValueWidget.attr("disabled") || this._durationValueWidget.isValid())
			&& this._startDateWidget.isValid() && this._endDateWidget.isValid(); 
	},
	_onChangeModeForever: function(val)
	{
		if(this._suppressOnChange) return;
		if(val) this._onChangeMode("forever");
	},
	_onChangeModeDuration: function(val)
	{
		if(this._suppressOnChange) return;
		if(val) this._onChangeMode("duration");
	},
	_onChangeModeUntil: function(val)
	{
		if(this._suppressOnChange) return;
		if(val) this._onChangeMode("until");
	},
	_onChangeMode: function(mode)
	{
		++this._suppressOnChange;
		this._mode = mode;
		
		switch(mode) {
		case "forever":
			this._syncEnd();
			break;
		case "duration":
			this._syncDuration();
			break;
		case "until":
			this._syncEnd();
			break;
		}
		this._syncWidgetStates();
		this._fireOnChange();
		--this._suppressOnChange;
	},
	_onChangeStart: function(val)
	{
		console.log("DateRange.onChangeSart: ", val);
		if(this._suppressOnChange) return;
		this._syncEnd();
		this._syncDuration();
		this._fireOnChange();
	},
	_onChangeEnd: function(val)
	{
		console.log("DateRange.onChangeEnd: ", val);
		if(this._suppressOnChange) return;
		
		this._syncDuration();
		var d = this._endDateWidget.attr('value');
		this.endDateValue.value = undefined == d ? "" : dojo.date.stamp.toISOString(d, {selector: 'date'});

		this._fireOnChange();
	},
	_onDurationValueChange: function()
	{
		console.log("DataRange._onDurationValueChange");
		if(this._suppressOnChange) return;
		this._syncEnd();
		this._fireOnChange();
	},
	_onDurationUnitChange: function(val)
	{
		console.log("DateRange._onDurationUnitChange: ", val);
		if(this._suppressOnChange) return;
		this._syncEnd();
		this._fireOnChange();
	},
	_onDurationKeyPress: function(e)
	{
		console.log("DateRange._onDurationKeyPress");
		window.setTimeout(dojo.hitch(this, "_onDurationValueChange"), 0);
	},
	_onStartDateKeyPress: function(e)
	{
		console.log("DateRange._onStartDateKeyPress: ", e.charOrCode);
		if(dojo.keys.ENTER == e.charOrCode) {
			window.setTimeout(dojo.hitch(this, "_validateAndChangeStartDate"), 0);
		}
	},
	_validateAndChangeStartDate: function()
	{
		// this._startDateWidget.validate() isn't picking up the new string value as the user types
		// validate ourselves.
		if(this._dateRegExp.test(this._startDateWidget.textbox.value)) {
			this._onChangeStart();
		}
		else {
			this._startDateWidget.displayMessage(this._startDateWidget.getErrorMessage(true));
		}
	},
	_onEndDateKeyPress: function(e)
	{
		console.log("DateRange._onendDateKeyPress: ", e.charOrCode);
		if(dojo.keys.ENTER == e.charOrCode) {
			window.setTimeout(dojo.hitch(this, "_validateAndChangeEndDate"), 0);
		}
	},
	_validateAndChangeEndDate: function()
	{
		// this._startDateWidget.validate() isn't picking up the new string value as the user types
		// validate ourselves.
		if(this._dateRegExp.test(this._endDateWidget.textbox.value)) {
			this._onChangeEnd();
		}
		else {
			this._endDateWidget.displayMessage(this._endDateWidget.getErrorMessage(true));
		}
	},
	_onendDateKeyPress: function(e)
	{
		console.log("DateRange._onendDateKeyPress: ", e.charOrCode);
		if(dojo.keys.ENTER == e.charOrCode) {
			window.setTimeout(dojo.hitch(this, "_validateAndChangeendDate"), 0);
		}
	},
	_validateAndChangeendDate: function()
	{
		// this._endDateWidget.validate() isn't picking up the new string value as the user types
		// validate ourselves.
		if(this._dateRegExp.test(this._endDateWidget.textbox.value)) {
			this._onChangeEnd();
		}
		else {
			this._endDateWidget.displayMessage(this._endDateWidget.getErrorMessage(true));
		}
	},
	_syncWidgetStates: function()
	{
		var dt = this.attr('value');
		this._durationUnitsWidget.attr("disabled", this._mode != "duration" || undefined == dt);
		this._durationValueWidget.attr("disabled", this._mode != "duration" || undefined == dt);
		this._endDateWidget.attr("disabled", this._mode != "until" || undefined == dt);
		
		this._durationValueWidget.attr("required", this._mode == "duration");
		this._endDateWidget.attr("required", this._mode == "until");
		
		if("duration" != this._mode) {
			this._durationValueWidget.attr('value', "");
		}
		else if("until" != this._mode) {
			this._endDateWidget.attr('value', null);
		}
		if(undefined != dt) {
			//Added to take care of existing start date earlier than now case
			var now = new Date();
			var minValue = dt.valueOf() < now.valueOf() ? now : dt;

			//if (minValue-dt)<1 day, set minValue to the very beginning of the next day
			//"End date should not be the same day as of Start date" according to PHYP0722BJA
			if( (minValue.getDate() - dt.getDate()) < 1 )
			{
				minValue.setDate(minValue.getDate()+1);
				minValue.setHours(0);
				minValue.setMilliseconds(0);
				minValue.setMinutes(0);
				minValue.setSeconds(0);
			}
			this._endDateWidget.constraints.min = minValue;

			//this._endDateWidget.constraints.min = dt;
			this._endDateWidget.validate();
		}

		dt = this._endDateWidget.attr('value');
		if(undefined != dt && "until" == this._mode) {			
			//constrain max to one day before end date - as per PHYP0722BJA
			dt.setDate(dt.getDate()-1);
			dt.setHours(23);
			dt.setMilliseconds(999);
			dt.setMinutes(59);
			dt.setSeconds(59);
			
			this._startDateWidget.constraints.max = dt;
			this._startDateWidget.validate();
		} else {
			this._startDateWidget.constraints.max = new Date(10000, 1, 0);
		}
	},
	_syncEnd: function()
	{
		console.log("DateRange._syncEnd");
		try {
			++this._suppressOnChange;
			if("until" == this._mode || "duration" == this._mode) {
				var start = this.attr('value');
				var dv = this.attr('duration');
				if(!isNaN(dv)) {
					var end = new Date(start.getTime() + dv);
					this.attr('valueEnd', end);
				}
			}
			else {
				this.attr('valueEnd', null);
			}
			this._syncWidgetStates();
		}
		catch(ex) {
			var msg = "DateRange._syncEnd error: " + ex;
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(msg);
		}
		finally {
			--this._suppressOnChange;
		}
	},
	_syncDuration:function()
	{
		console.log("DateRange._syncDuration");
		try {
			++this._suppressOnChange;
			
			if("duration" == this._mode) {
				var a = this.attr('value');
				if(null != a) {
					var z = this.attr('valueEnd');
					var d, du;
					if(null == z) {
						console.log("DateRange._syncDuration: no endDate")
						du = "forever";
						d = "";
					}
					else {
						console.log("DateRange._syncDuration: calculating");
						if(a.getDate() == z.getDate()) {
							du = 'month';
							d = dojo.date.difference(a, z, du);
							console.log("DateRange._syncDuration: %d months", d);
						}
						else {
							du = 'day';
							d = dojo.date.difference(a, z, du);
							console.log("DateRange._syncDuration: %d days", d);
							if(d % 7 == 0) {
								du = 'week';
								d /= 7;
								console.log("DateRange._syncDuration: %d weeks", d);
							}
						}
					}
					this._durationValueWidget.attr('value', d);
					this._durationUnitsWidget.attr('value', du);		
				}
			}
			this._syncWidgetStates();
		}
		catch(ex) {
			var msg = "DateRange._syncDuration error: " + ex;
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(msg);
		}
		finally {
			--this._suppressOnChange;
		}
	},

	_checkConstraints: function()
	{
		if(undefined == this.constraints) {
			this.constraints = {min: null, max: null};
		}
		var d = ibm.tivoli.simplesrm.srm.dijit.Date.parseDate(this.constraints.min);
		if(undefined == d || isNaN(d.getTime())) {
			d = new Date(0);
		}
		d.setHours(0,0,0,0);
		this.attr('minValue', d);
	
		d = ibm.tivoli.simplesrm.srm.dijit.Date.parseDate(this.constraints.max);
		if(undefined == d || isNaN(d.getTime())) {
			d = new Date(10000, 1, 0);
		}
		this.attr('maxValue', d);
	},

	// getters
	_getModeAttr: function()
	{
		return this._mode;
	},
	_getValueAttr: function()
	{
		return this._startDateWidget.attr('value');
	},
	_getValueEndAttr: function()
	{
		return ibm.tivoli.simplesrm.srm.dijit.Date.parseDate(this.endDateValue.value);
	},
	_getDurationAttr: function()
	{
		var dv = Number.NaN;
		try {
			if("forever" != this._mode) { 
				var du = this._durationUnitsWidget.attr('value');
				var dv = parseInt(this._durationValueWidget.attr('value'));
				if(!isNaN(dv)) {
					switch(du) {
					case "day":
						dv *= 86400000; break;
					case "week":
						dv *= 604800000; break;
					case "month":
						var a = this.attr('value');
						var z = new Date(a);
						z.setMonth(a.getMonth() + dv);
						dv = z.getTime() - a.getTime();
						break;
					default:
						throw new Error("Unexpected duration unit '" + du + "'");
					}
				}
			}
		}
		catch(ex) {
			var msg = "DateRange._getDurationAttr error: " + ex;
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(msg);
			dv = Number.NaN;
		}
		return dv;
	},
	_getDurationUnitAttr: function()
	{
		if("forever" == this._mode) 
			return "forever";
		return this._durationUnitsWidget.attr('value');	
	},
	_getUnboundedAttr: function()
	{
		return "forever" == this._mode;
	},
	// return the minimum permissible datetime, as integer millisecs, as
	// defined by Date.getTime()
	_getMinValueAttr: function()
	{
		return this.constraints.min;
	},
	_getMaxValueAttr: function()
	{
		return this.constraints.max;
	},

	_showHideEndDateDivs: function(val)
	{
		var dr1 = dojo.byId(this.id + "_divDuration");
		var dr2 = dojo.byId(this.id + "_divUntil");
		
		this._onChangeMode(val);
		switch(val)
		{
		case 'duration': 
			if(dr1)
				dojo.style(dr1, "display", "inline");
			if(dr2)
				dojo.style(dr2, "display", "none");
			break;
		case 'until': 
			if(dr1)
				dojo.style(dr1, "display", "none");
			if(dr2)
				dojo.style(dr2, "display", "inline");
			break;
		default:  // means 'forever' 
			if(dr1)
				dojo.style(dr1, "display", "none");
			if(dr2)
				dojo.style(dr2, "display", "none");
		}
	},
	
	// setters
	_setModeAttr: function(value)
	{
		if(value != "forever" && value != "duration" && value != "until")
			throw Error("Invalid value for DateRange mode");

		//this._onChangeModeDropDown(value);
		this._modeDropDown.setValue(value);
		this._showHideEndDateDivs(value);
		
		this._onChangeMode(value);
	},
	_setValueAttr: function(value)
	{
		this._startDateWidget.attr('value', value);
		this._startDateWidget.validate();
	},
	_setValueEndAttr: function(value)
	{
		this._endDateWidget.attr('value', value);
		this._endDateWidget.validate();

		var d = this._endDateWidget.attr('value');
		this.endDateValue.value = undefined == d ? "" : dojo.date.stamp.toISOString(d, {selector: 'date'});
	},
	_setDurationAttr: function(value)
	{
		var a = this.attr('value');
		var z = new Date(a.getTime() + value);
		this.attr('valueEnd', z);
	},
	_setDurationUnitAttr: function(value)
	{
		if(! /day|week|month|forever/.test(value))
			throw new Error("DateRange2 error: " + value + " is inot a valid duration unit");
		if("forever" == value)
			this._setModeAttr("forever");
		else
			this._durationUnitsWidget.attr('value', value);	
	},
	_setMinValueAttr: function(/*Date|String|Number*/newval)
	{
		try {
			if(null == newval) {
				this.constraints.min = new Date(0);
			}
			else {
				minval = ibm.tivoli.simplesrm.srm.dijit.Date.parseDate(newval);
				if(null != minval && !isNaN(minval.getTime())) {
					this.constraints.min = minval;
				}
			}
			this._startDateWidget.constraints.min = this.constraints.min;
			this._startDateWidget.validate();
			this._endDateWidget.constraints.min = this.constraints.min ? (new Date(this.constraints.min)).setDate(this.constraints.min.getDate()+1) : 0;
			this._endDateWidget.validate();
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
		}		
	},
	_setMaxValueAttr: function(/*Date|String|Number*/newval)
	{
		try {
			if(null == newval) {
				this.constraints.max = new Date(10000, 1, 0);
			}
			else {
				maxval = ibm.tivoli.simplesrm.srm.dijit.Date.parseDate(newval);
				if(null != maxval && !isNaN(maxval.getTime())) {
					this.constraints.max = maxval;
				}
			}
			this._endDateWidget.constraints.max = this.constraints.max;
			this._endDateWidget.validate();
			this._startDateWidget.constraints.max = (new Date(this.constraints.max)).setDate(this.constraints.max.getDate()-1);
			this._startDateWidget.validate();
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
		}	
	},
	_fireOnChange: function()
	{
		console.log("DateRange2._fireOnChange");
		if(this.isValid())
			this.onChange(this.attr("value"), this.attr("valueEnd"));
	},
	onChange: function(valueStart, valueEnd)
	{
		// override or sink to be notified if the displayed range changes
	},

	// switch between the end date modes
	_onChangeModeDropDown: function(val)
	{
		this._showHideEndDateDivs(val);
	},
	_dummy:null
}); 


dojo.declare("ibm.tivoli.simplesrm.srm.dijit.DateTextBox", dijit.form.DateTextBox,
{
	_stringTable: null,
	postMixInProperties: function()
	{
		this._stringTable = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");
		this.inherited(arguments);
	},
	validator: function(/*anything*/value, /*dijit.form.ValidationTextBox.__Constraints*/constraints)
	{
		var retval = this.inherited(arguments);

		if(!retval) {
			var d = ibm.tivoli.simplesrm.srm.dijit.Date.parseDate(value);
			if(undefined != this.constraints.min && undefined != d && d.getTime() < this.constraints.min.getTime()) {
				this.invalidMessage = this._stringTable.DateEarlyMessage.replace("\$\{0\}", dojo.date.locale.format(this.constraints.min, {selector:'date'}));
			}
			else if(undefined != this.constraints.min && undefined != d && d.getTime() > this.constraints.max.getTime()) {
				this.invalidMessage = this._stringTable.DateLateMessage.replace("\$\{0\}", dojo.date.locale.format(this.constraints.min, {selector:'date'}));
			}
			else {
				this.invalidMessage = this._stringTable.InvalidDateMessage;
			}
		}

		return retval;
	},
	_dummy:null
});

//Given a string, Date or number, return a Date
//or null if it's not a valid date.
ibm.tivoli.simplesrm.srm.dijit.Date = {};
ibm.tivoli.simplesrm.srm.dijit.Date.parseDate = function(/*string|Date|Number*/d)
{
	var dval = null;
	try {
		var t = typeof d;
		switch(t) {
		case "number":
			dval = new Date(d);
			break;
		case "string":
			if(d.length > 0) {	// empty strings happen a lot
				if("now" === d) {
					dval = new Date();
				}
				else {
					var m = d.match();
					if(undefined != m &&  m.length > 0 && m[0].length == d.length) {
						dval = new Date(parseInt(d));
					}
					else {
						if(/\d{4}-\d{2}-\d{2}T?/.test(d)) {
							dval = dojo.date.stamp.fromISOString(d);
						}
						else {
							dval = dojo.date.locale.parse(d, {selector:'date'})
						}
					}
				}
			}
			break;
		case "object":
			dval = d == null ? null : new Date(d);
			break;
		}
		if(undefined == dval || isNaN(dval.getTime())) {
			dval = null;
		}
	}
	catch(ex) {
		new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError("DateRange._parseDate error: " + ex);
	}
	return dval;	
}
// format an ISO duration string given duration in millisecs
ibm.tivoli.simplesrm.srm.dijit.Date.formatISODuration = function(/*int*/dur)
{
	if(isNaN(dur))
		return "";
	
	var days = Math.floor(dur / 86400000);
	var dstr = "P" + days + "D";
	return dstr;
}
