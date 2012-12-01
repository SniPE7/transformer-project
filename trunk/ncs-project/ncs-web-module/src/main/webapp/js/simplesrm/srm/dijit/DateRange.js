//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.DateRange");
dojo.provide("ibm.tivoli.simplesrm.srm.dijit.TimeSlider");
	
dojo.require("dojo.cldr.supplemental");
dojo.require("dojo.date");
dojo.require("dojo.date.locale");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.form._DateTimeTextBox");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dojox.form.DropDownSelect");
dojo.require("ibm.tivoli.simplesrm.srm.dijit.ProgressSpinner");

// TODO: this code handles the case where the start time is "now", but it's sloppy.
// if you use the time slider to shift away from now, there's no getting back
dojo.declare(
	"ibm.tivoli.simplesrm.srm.dijit.DateRange",
	[dijit._Widget, dijit._Templated],
{
	widgetsInTemplate: true,
	templatePath: dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit", "templates/DateRange.html"),

	// you can provide 2 of the 3
	rangeStart: null,	// Date: start of the displayed date range (default to today)
	rangeEnd: null,		// Date: end of the displayed date range
	rangeDays: 28,		// number of days in the range.
	scrollDays: 5,		// how many days does the calendar scroll when user clicks the button
	
	value: null,	// integer: start of the selected date range (Date.valueOf()).  
	valueEnd: null,	// integer: end of the selected date range, inclusive.  if null, no end time
	startNow: false,	// is the start time now, or specified?
	unbounded: false,	// is the duration forever or specified?
	
	startName: 'StartDate',	// name of hidden input field that gets submitted with the form
	endName: 'EndDate',		// name of hidden end date input field that gets submitted with the form.
	durationName: 'Duration',	// name of the hidden duration input field submitted with the form
	
	constraints: {},			// constraints on the date range.  valid values are min and max, where each may be a 
								// javascript date object, the number of millisecs since 1970-01-01, or an ISO 8601 datetime string
	
	_durationInput: null,		// duration value input widget
	_durationUnits:null,		// duration unit input widget
	_timeSliderA: null,			// start time slider widget
	_timeSliderZ: null,			// end time slider widget
	
	disabled: false,
	
	_dayNames: null,	// localized day name abbreviations (e.g. S, M, T, ...)
	_monthNames: null,	// localized month abbreviations (Jan, Feb, ...)
	_totalMonths: 0,
	_nDaysPerMonth: [],
	_timeResolution: 3600000,	// 1 hour
	_autoAdjustSelectionOnSettingMinMax: true,	// if true, adjust the selection if setMinValue/setMaxValue put it out of bounds 
	
	_progressMessage: "Retrieving available start dates...",
	
	/**
	 * Set the starting datetime of the selection
	 * val may be a datetime string, either in the locale or ISO format, milliseconds since 1970-01-01, or a Date object
	 * if val is undefined or the string "now", it it set to the current time as of this call.
	 * the start of the selection is rounded up to the next increment of this._timeResolution, unless setting to "now" 
	 */
	setStartValue: function(/*string|number|Date*/val)
	{
		var selectionChanged = false;
		var dt = null;
		var t = typeof val;
		try {
			dt = this._getDateValue(val);
			this.startNow = (null == dt);
			if(null == dt)
				dt = (new Date()).valueOf();

			if(!isNaN(dt)) {
				if(!this.startNow) {
					dt = this._roundTime(dt);
				}
				if( this.isPermissible(dt) ) {
					if(this.value != dt) {
						this.value = dt;
						selectionChanged = true;
					}
					if(!this.unbounded) {
						// even if the start time of odd because we're starting 'now',
						// round the end time
						this.valueEnd = this._roundTime(dt + this._getDurationValueFromInput());
					}
					selectionChanged = true;
				}
			}
			if(selectionChanged) {
				this._setExternalValues();
				
				this._scrollIntoView(this.value);
				this._fireOnChange();
			}
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			console.error("Error setting DateRange start value: ", ex);
		}
	},
	setEndValue: function(val)
	{
		var selectionChanged = false;
		var dt = null;
		var t = typeof val;
		try {
			dt = this._getDateValue(val);
			if(null == dt)
				dt = (new Date()).valueOf();
			
			if(!isNaN(dt)) {
				dt = this._roundTime(dt);
				
				if( this.isPermissible(dt) ) {
					if(this.valueEnd != dt) {
						this.valueEnd = dt;
						selectionChanged = true;
					}
					if(this.valueEnd < this.value) {
						this.value = this.valueEnd;
						selectionChanged = true;
					}
				}
			}
			if(selectionChanged) {
				this._setExternalValues();
				
				this._showSelection();
				this._fireOnChange();
			}
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			console.error("Error setting DateRange start value: ", ex);
		}
	},
	attr: function(attrname, attrvalue)
	{
		if(attrname === "disabled" && undefined !== attrvalue) {
			this.setDisabled(attrvalue);
		}
		this.inherited(arguments);
	},
	setDisabled: function(d)
	{
		this.disabled = d;
		if(d) {	
			dojo.addClass(this.domNode, "dateRangeDisabled");
			dojo.query("input", this.domNode).forEach(function(i) {
				dojo.attr(i, "disabled", true);
			});
		}
		else {
			dojo.remoeClass(this.domNode, "dateRangeDisabled");
			dojo.query("input", this.domNode).forEach(function(i) {
				dojo.attr(i, "disabled", false);
			});
		}
	},
	getStartValue: function()
	{
		return this.value == null ? null : new Date(this.value);
	},
	getEndValue: function()
	{
		return this.valueEnd == null ? null : new Date(this.valueEnd);
	},
	setMinValue: function(newval)
	{
		var minval = this.constraints.min;
		try {
			minval = this._getDateValue(newval);
			if(null == minval) {
				minval = (new Date()).valueOf();
			}
			if(isNaN(minval))
				minval = 0;
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
		}		
		if(this.constraints.min != minval) {
			this.constraints.min = minval;
			m = new Date(this.rangeStart.valueOf());
			dojo.query(".dijitCalendarDateTemplate", this.domNode).forEach(function(dateCell, i)
			{		
				this._setCellStyle(dateCell);
			}, this);
		}
		if(this._autoAdjustSelectionOnSettingMinMax){
			if(undefined != this.value && this.value < minval) {
				this.setStartValue(minval);
			}
			if(undefined != this.valueEnd && this.valueEnd < minval) {
				this.setEndValue(minval);
			}
		}
		return minval;
	},
	// return the minimum permissible datetime, as integer millisecs, as
	// defined by Date.valueOf()
	getMinValue: function()
	{
		var minval = 0;
		if("number" === typeof this.constraints.min)
			minval = this.constraints.min;
		return minval;
	},
	setMaxValue: function(newval)
	{
		var maxval = this.constraints.max;	
		try {
			if(null == newval) 
				maxval = (new Date(10000, 1, 0)).valueOf();
			else {
				maxval = this._getDateValue(newval);
				if(maxval == null)
					maxval = (new Date()).valueOf();
				if(isNaN(maxval))
					maxval = (new Date(10000, 1, 0)).valueOf();
			}
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
		}
		if(this.constraints.max != maxval) {
			this.constraints.max = maxval;
			m = new Date(this.rangeStart.valueOf());
			dojo.query(".dijitCalendarDateTemplate", this.domNode).forEach(function(dateCell, i)
			{		
				this._setCellStyle(dateCell);
			}, this);
		}
		if(this._autoAdjustSelectionOnSettingMinMax){
			if(undefined != this.value && this.value > maxval) {
				this.setStartValue(maxval);
			}
			if(undefined != this.valueEnd && this.valueEnd > maxval) {
				this.setEndValue(maxval);
			}
		}
		return maxval;		
	},
	getMaxValue: function()
	{
		var maxval = (new Date(10000, 1, 0)).valueOf();
		if("number" === typeof this.constraints.max)
			maxval = this.constraints.max;
		return maxval;
	},
	// is the given datetime value w/in the permisiable range
	isPermissible: function(dt_val) 
	{
		var n = this.getMinValue();
		var x = this.getMaxValue();
		var b = dt_val >= n && dt_val <= x;
		console.log(n, dt_val, x, b);
		return b;
	},
	// true if there's any time in the day of the given datetime value
	// that's within the permissible range
	isPermissibleDay: function(dt_val)
	{
		var dt = new Date(dt_val);
		console.log("DateRange.isPermissibleDay: ", dt);
		
		var v = dt.setHours(0,0,0,0); 
		console.log("DateRange.isPermissibleDay max:", dt);
		if(v > this.getMaxValue())
			return false;
		v = dt.setHours(24);
		console.log("DateRange.isPermissibleDay min:", dt);
		if(v < this.getMinValue())
			return false;
		return true;
	},
	// Return the duration in the given units
	// units is any of the values accepted by dojo.date.differnece iterval 
	// One of the following:
	//			"year", "month", "day", "hour", "minute", "second",
	//			"millisecond", "quarter", "week", "weekday"
	// Defaults to "millisecond"
	// If the current duration is forever, returns -1
	getDuration: function(/*string*/units)
	{
		if(this.unbounded) {
			dur = -1;
		}
		if(undefined == units)
			units = "millisecond";
		
		var dur = 0;

		if(null != this.value && null != this.valueEnd) {
			dur = dojo.date.difference(new Date(this.value), new Date(this.valueEnd), units);
		}
		return dur;
	},
	getDurationUnits: function()
	{
		return this._durationUnits.attr("value");
	},
	// returns the duration in milliseconds, as calculated from 
	// values in the UI (and not by diff'ing end-start times,like getDuration(
	// if unbounded, returns -1
	// on error, returns 0
	_getDurationValueFromInput: function()
	{
		var dur_val = 0;
		var dur_units = this.getDurationUnits();
		if(dur_units === "forever") {
			dur_val = -1;
		}
		else {
			var dur_val = parseFloat(this._durationInput.attr("value"));
			if(isNaN(dur_val)) {
				dur_val = 0;
			}
			else {
				if(dur_units === "hour") {
					dur_val *= 3600000;  
				}
				else if(dur_units === "day") {
					dur_val *= 24 * 3600000;
				}
			}
		}
		return dur_val;
	},
	constructor: function()
	{
		this.constraints.min = 0;
		this.constraints.max = (new Date(10000, 1, 0)).valueOf();
	},
	postMixInProperties: function()
	{
		if(" q string" === typeof this.rangeStart) {
			this.rangeStart = this._parseDate(this.rangeStart);
		}
		if(null == this.rangeStart || "undefined" === typeof this.rangeStart) {
			this.rangeStart = new Date();
		}
		if("string" === typeof this.rangeEnd) {
			this.rangeEnd = this._parseDate(this.rangeEnd);
		}
		if(null == this.rangeEnd || "undefined" === typeof this.rangeEnd) {
			if("string" === typeof this.rangeDays) {
				this.rangeDays = parseInt(this.rangeDays);
			}
			this.rangeEnd = new Date(this.rangeStart.valueOf());
			this.rangeEnd.setDate(this.rangeEnd.getDate() + this.rangeDays-1);
		}
		this.rangeStart.setHours(0,0,0,0);
		this.rangeEnd.setHours(0,0,0,0);
		this._getRangeStats();
		this._monthNames = dojo.date.locale.getNames('months', 'abbr', 'standAlone', this.lang);
		this._dayNames = dojo.date.locale.getNames('days', 'narrow', 'standAlone', this.lang);
	},
	buildRendering: function()
	{
		this.inherited(arguments);
		var me = this;
		
		this._cloneClass(".dijitCalendarDayLabelTemplate", this.rangeDays - 1);
		this._cloneClass(".dijitCalendarDateTemplate", this.rangeDays - 1);

		this._emitMonthLabels();

		this._emitDayLabels();

		this._emitDates();

	},
	postCreate: function()
	{
		this.inherited(arguments);
	},
	startup: function()
	{
		this.inherited(arguments);

	
		this._durationInput = dijit.byId(this.id + "_duration_input");
		this._durationUnits = dijit.byId(this.id + "_durationUnits");
		this._timeSliderA = dijit.byId(this.id + "_tsa");
		this._timeSliderZ = dijit.byId(this.id + "_tsz");

		// this kind of creaps me out, creating a circular reference
		this._timeSliderA._parentCalendar = this;
		this._timeSliderZ._parentCalendar = this;
		
		// we don't want this value submitted with the form, so remove the 
		// name attribute from the embedded input element.
		dojo.query("input", this._durationInput.domNode).forEach(function(i) {
			i.removeAttribute("name");
		});
		
		// if any of our values have no name, remove the name attribute
		// to keep it from being submitted with the form
		if( 0 === this.endName.length) {
			this.endVal.removeAttribute("name");
		}
		if( 0 === this.durationName.length ) {
			this.durationVal.removeAttribute("name");
		}
		
		
		if(this.unbounded) {
			this._durationUnits.attr("value", "forever");
		}
		else {
			this._durationInput.attr("value", 1);
			this._durationUnits.attr("value", "day");
		}
		
		var now = (new Date()).valueOf();
		var next_start = this._roundTime(now)
		if(next_start <= now)
			next_start += this._timeResolution;
		
		this.setStartValue(next_start);
		
		this.connect(this._timeSliderA, "onChange", "_onStartTimeChange");
		this.connect(this._timeSliderZ, "onChange", "_onEndTimeChange");
	},
	destroy: function()
	{
		console.log("DateRange.destroy");
		// break the circular ref
		this._timeSliderA._parentCalendar = null;
		this._timeSliderZ._parentCalendar = null;
		this.inherited(arguments);
	},
	_emitMonthLabels: function()
	{
		var monthLabels = dojo.query(".dijitCalendarMonthTemplate", this.monthLabelRow);
		if(this._totalMonths < monthLabels.length) {
			// remove the extra month labels
			for(var i = this._totalMonths; i < monthLabels.length; ++i) {
				this.monthLabelRow.removeChild(monthLabels[i]);
			}
		}
		else {
			// add missing month labels
			this._cloneClass(".dijitCalendarMonthTemplate", this._totalMonths - monthLabels.length);
		}
		
		// emit month names
		var year_label;
		m = new Date(this.rangeStart.valueOf());
		m.setDate(1);
		dojo.query(".dijitCalendarMonthTemplate", this.monthLabelRow).forEach(function(monthLabel, i){
			monthLabel.setAttribute("colspan", this._nDaysPerMonth[m.getMonth()]);
			if(this._nDaysPerMonth[m.getMonth()] < 3) {
				year_label = this._monthNames[m.getMonth()].substring(0, 1);
			}
			else {
				year_label = this._monthNames[m.getMonth()] + " " + m.getFullYear();
			}
			this._setText(monthLabel.firstChild, year_label);
			m.setMonth(m.getMonth() + 1);
		}, this);

	},
	_emitDayLabels: function()
	{
		// insert localized day names in the header
		var dayOffset = this.rangeStart.getDay();
		dojo.query(".dijitCalendarDayLabelTemplate", this.domNode).forEach(function(dayCell, i){
			var day = (i + dayOffset) % 7;
			if(6 == day) {
				dojo.addClass(dayCell, "dijitCalendarLastDayOfWeek");
			}
			else {
				dojo.removeClass(dayCell, "dijitCalendarLastDayOfWeek");
			}
			this._setText(dayCell.firstChild, this._dayNames[day]);
		}, this);
	},
	_emitDates: function()
	{
		// insert dates
		m = new Date(this.rangeStart.valueOf());
		dojo.query(".dijitCalendarDateTemplate", this.domNode).forEach(function(dateCell, i)
		{		
			dateCell.dijitDateValue = m.valueOf();	// cache this date's date
			var d = m.getDate();
			this._setText(dateCell.firstChild, d);
			this._setCellStyle(dateCell);
			m.setDate(m.getDate() + 1);
		}, this);
	},
	_setCellStyle: function(/*td*/cell)
	{
			var date = new Date(cell.dijitDateValue);
			if(6 == date.getDay()) {
				dojo.addClass(cell, "dijitCalendarLastDayOfWeek");
			}
			else {
				dojo.removeClass(cell, "dijitCalendarLastDayOfWeek");
			}
			if(dojo.date.locale.isWeekend(date)) {
				dojo.addClass(cell, "dijitCalendarWeekend");
			}
			else {
				dojo.removeClass(cell, "dijitCalendarWeekend");
			}
			var now = new Date();
			now.setHours(0,0,0,0);
			var thisMonth = now.getMonth();
			var workingMonth = date.getMonth();
			if(workingMonth < thisMonth) {
				dojo.addClass(cell, "dijitCalendarPreviousMonth");
				dojo.removeClass(cell, "dijitCalendarCurrentMonth");
				dojo.removeClass(cell, "dijitCalendarNextMonth");
			}
			else if(workingMonth == thisMonth) {
				dojo.removeClass(cell, "dijitCalendarPreviousMonth");
				dojo.addClass(cell, "dijitCalendarCurrentMonth");
				dojo.removeClass(cell, "dijitCalendarNextMonth");
			}
			else {
				dojo.removeClass(cell, "dijitCalendarPreviousMonth");
				dojo.removeClass(cell, "dijitCalendarCurrentMonth");
				dojo.addClass(cell, "dijitCalendarNextMonth");
			}
			if(now.valueOf() == cell.dijitDateValue) {
				dojo.addClass(cell, "dijitCalendarCurrentDate");
			}
			else {
				dojo.removeClass(cell, "dijitCalendarCurrentDate");
			}
			if( cell.dijitDateValue < (new Date(this.constraints.min)).setHours(0,0,0,0) || (new Date(this.constraints.max)).setHours(0,0,0,0) < cell.dijitDateValue ) {
				dojo.addClass(cell, "dijitCalendarUnavailableDate");
			}
			else {
				dojo.removeClass(cell, "dijitCalendarUnavailableDate");
			}
	},
	_getRangeStats: function()
	{
		this.rangeDays = 0;
		this._totalMonths = 0;
		this._nDaysPerMonth = [];
		
		var month;
		var monthDays = 0;
		var m = new Date(this.rangeStart.valueOf());
		var zval = this.rangeEnd.valueOf();
		while(m.valueOf() < zval) {
			++this.rangeDays;
			++monthDays;
			month = m.getMonth();
			m.setDate(m.getDate()+1);
			if(m.getMonth() != month) {
				++this._totalMonths;
				this._nDaysPerMonth[month] = monthDays;
				monthDays = 0;
			}
		}
		this._nDaysPerMonth[m.getMonth()] = monthDays;
		if(m.getMonth() == month) {
			++this._totalMonths;	// range ends on last day of the month
		}
	},
	_cloneClass: function(clazz, n)
	{
		var template = dojo.query(clazz, this.domNode)[0];
 		for(var i=0; i<n; i++){
			dojo.place(template.cloneNode(true), template, "after");
		}
	},
	
	_autoscroll_timeout1: 1000,	// msec, time before mousedown and we start auto-scrolling
	_autoscroll_timeout2: 100,	// msec, time between auto-scrolls while mouse is down
	_autoscroll_timer: -1,
	_uphandler: null,
	_downhandler: null,
	_onMouse: function(evt) {
		if(this.disabled)
			return false;
			
		//console.log("DateRange._onMouse", evt.type);
		dojo.stopEvent(evt);
		if(null === this._uphandler) this._uphandler = dojo.hitch(this, "_rangeUp");
		if(null === this._downhandler) this._downhandler = dojo.hitch(this, "_rangeDown");
		var handler = (this.increaseArrowNode === evt.target) ? this._uphandler : this._downhandler;

		switch(evt.type) {
		case "mousedown":
			this._cancelAutoscrollTimer();
			this._autoscroll_timer = window.setTimeout(handler, this._autoscroll_timeout1);
			break;
		case "mouseup":
			this._cancelAutoscrollTimer();
			handler();
			break;
		case "mouseout":
			this._cancelAutoscrollTimer();
			break;
		}
		return false;
	},
	_cancelAutoscrollTimer: function()
	{
		if(this._autoscroll_timer > 0) {
			window.clearTimeout(this._autoscroll_timer);
		}
		this._autoscroll_timer = -1;
	},
	_scrollIntoView: function(dt)
	{
		var dval = this._getDateValue(dt);
		if(null == dval)
			dval = (new Date()).valueOf();
		if( !isNaN(dval)) {
			if(dval < this.rangeStart.valueOf() || this.rangeEnd.valueOf < dval ) {
				var curr_start = this.rangeStart.valueOf();
				this.rangeStart = new Date(dval); 
				this.rangeStart.setHours(0,0,0,0);
	
				this.rangeEnd = new Date(this.rangeStart.valueOf());
				this.rangeEnd.setHours(0,0,0,0);	
				this.rangeEnd.setDate(this.rangeEnd.getDate() + this.rangeDays);
				this._getRangeStats();
				
				this._emitMonthLabels();
				this._emitDayLabels();
				this._emitDates();
				
				if(curr_start != this.rangeStart.valueOf()) {
					this._fireOnRangeChange();
				}
			}
		}
		this._showSelection();
	},
	// TODO: a smooth sliding of the calendar would be really cool looking
	// moving the displayed range up means shifting the display down 
	_rangeUp: function()
	{
		//console.log("DateRange._rangeUp ", this._autoscroll_timer);
		if(this._autoscroll_timer > 0) { 	// got here due to timer going off
			this._autoscroll_timer = window.setTimeout(this._uphandler, this._autoscroll_timeout2);
		}
		
		var curr_start = this.rangeStart.valueOf();
		for(var k = 0; k < this.scrollDays; ++k) {
			// shift the range
			this.rangeStart.setDate(this.rangeStart.getDate() + 1);
			this.rangeEnd.setDate(this.rangeEnd.getDate() + 1);
			this._getRangeStats();
			
			// get the date cells
			var date_labels = dojo.query("td.dijitCalendarDateTemplate", this.dateLabelRow);
	
			// get the last date's date
			var lastDateCell = date_labels[date_labels.length-1];	// previous last date cell
			var lastDate = new Date(lastDateCell.dijitDateValue);	// the date it represents
			lastDate.setDate(lastDate.getDate() + 1);				// move to the next day
	
			// update the date cell's data
			date_labels[0].dijitDateValue = lastDate.valueOf();					// cache new value
			this._setText(date_labels[0].firstChild, lastDate.getDate());		// new date label
			this._setCellStyle(date_labels[0]);						// style the new date
	
			// move it to the end
			this.dateLabelRow.removeChild(date_labels[0]);
			dojo.place(date_labels[0], date_labels[date_labels.length-1], "after");
	
			// get the day label cells
			var day_labels = dojo.query("th.dijitCalendarDayLabelTemplate", this.dayLabelRow);
			
			// update
			this._setText(day_labels[0], this._dayNames[lastDate.getDay()]);	// new day label
			this._setCellStyle(day_labels[0]);						// style the new date
			
			// move to the end
			this.dayLabelRow.removeChild(day_labels[0]);
			dojo.place(day_labels[0], day_labels[day_labels.length-1], "after");
			
			this._emitMonthLabels();
		}
		this._showSelection();
		if(curr_start != this.rangeStart.valueOf()) {
			this._fireOnRangeChange();
		}
	},
	_rangeDown: function()
	{
		//console.log("DateRange._rangeDown");
		if(this._autoscroll_timer > 0) { 	// got here due to timer going off
			this._autoscroll_timer = window.setTimeout(this._downhandler, this._autoscroll_timeout2);
		}
		// never scroll into the past
		var today = new Date();
		today.setHours(0, 0, 0, 0);
		today = today.valueOf();
		
		var curr_start = this.rangeStart.valueOf();
		for(var k = 0; k < this.scrollDays && this.rangeStart.valueOf() > today; ++k) 
		{
			// shift the range
			this.rangeStart.setDate(this.rangeStart.getDate() - 1);
			this.rangeEnd.setDate(this.rangeEnd.getDate() - 1);
			this._getRangeStats();
			
			// get the date cells
			var date_labels = dojo.query("td.dijitCalendarDateTemplate", this.dateLabelRow);
	
			// get the first date's date
			var firstDateCell = date_labels[0];						// previous first date cell
			var firstDate = new Date(firstDateCell.dijitDateValue);	// the date it represents
			firstDate.setDate(firstDate.getDate() - 1);				// move to the previous day
	
			// update the date cell's data
			date_labels[date_labels.length-1].dijitDateValue = firstDate.valueOf();				// cache new value
			this._setText(date_labels[date_labels.length-1].firstChild, firstDate.getDate());	// new date label
			this._setCellStyle(date_labels[date_labels.length-1]);					// style the new date
			
			// move it to the beginning
			this.dateLabelRow.removeChild(date_labels[date_labels.length-1]);
			dojo.place(date_labels[date_labels.length-1], date_labels[0], "before");
	
			// get the day label cells
			var day_labels = dojo.query("th.dijitCalendarDayLabelTemplate", this.dayLabelRow);
			
			// update
			this._setText(day_labels[date_labels.length-1], this._dayNames[firstDate.getDay()]);	// new day label
			this._setCellStyle(day_labels[date_labels.length-1]);						// style the new date
			
			// move to the beginning
			this.dayLabelRow.removeChild(day_labels[date_labels.length-1]);
			dojo.place(day_labels[date_labels.length-1], day_labels[0], "before");
			
			this._emitMonthLabels();
		}
		this._showSelection();
		if(curr_start != this.rangeStart.valueOf()) {
			this._fireOnRangeChange();
		}		
	},
	_onDayClick: function(evt)
	{
		if(this.disabled)
			return;

		// unselect any text that got selected
		this._clearSelection();

		//console.log("DateRange._onDayClick")
		var selectionChanged = false;
		var node = evt.target;
		dojo.stopEvent(evt);
		while(this.domNode != node && !node.dijitDateValue){
			node = node.parentNode;
		}
		if(this.domNode == node || null == node) {	// not a date
			return;
		}
		
		var dv = node.dijitDateValue;	// midnight of the day clicked
		if( !this.isPermissibleDay(dv) ) {
			return;
		}
		
		// now we know that sometime on this day is OK, but we'll eventually
		// need to check the time of day
		var time_offset = 0;
		if(evt.shiftKey && this.valueEnd != null)
			time_offset = this.valueEnd - (new Date(this.valueEnd)).setHours(0,0,0,0);
		else
			time_offset = this.value - (new Date(this.value)).setHours(0,0,0,0);
		dv += time_offset;
		console.log("DateRange._onDayClick clicked date: ", dv);
		
		if(dv < this.getMinValue())
			dv = this.getMinValue();
		else if(dv > this.getMaxValue())
			dv = this.getMaxValue();
		
		if( null == this.value ) {
			this.value = dv;
			this.valueEnd = null;
			selectionChanged = true;
		}
		else {
			if(this.unbounded) {
				selectionChanged = this.value != dv ;
				this.value = dv;
				this.valueEnd = null;
			}
			else if( !evt.shiftKey) {
				selectionChanged = this.value != dv ;
				
				this.value = dv;
				this.valueEnd = this.value + this._getDurationValueFromInput();
				if(this.valueEnd > this.getMaxValue())
					this.valueEnd = this.getMaxValue();
			}
			else if(evt.shiftKey) {
				
				if(dv < this.value) {
					//this.valueEnd = this.value;
					this.value = dv;					
					selectionChanged = true;
				}
				else {
					selectionChanged = (this.valueEnd != dv);
					this.valueEnd = dv;
				}
			}
		}
		if(selectionChanged) {
			this._showSelection();
			this._setExternalValues();
			this._fireOnChange();
		}
	},
	// highlight the selection
	_showSelection: function()
	{
		console.log("DateRange._showSelection. value:", this.value);
		if( null == this.value )
			return;

		var master_ref = dojo.coords(this.masterReference);
		if(master_ref.x == 0) {
			// we haven't been rendered in a popup yet
			// TODO: is there a better way to handle this from the popup to force its container to re-render
			// this feels like a hack
			window.setTimeout(dojo.hitch(this, this._showSelection), 300);
			return;
		}
		var value = (new Date(this.value)).setHours(0,0,0,0);
		var valueEnd = this.valueEnd == null ? null : (new Date(this.valueEnd)).setHours(0,0,0,0);

		var cells = dojo.query(".dijitCalendarDateTemplate", this.dateLabelRow);
		cells.forEach(dojo.hitch(this, function(cell) {
			var dv = cell.dijitDateValue;
			//console.log("_showSelection: working: %s", this._formatDate(dv));
			if(null == this.valueEnd) {	// no end date
				if(value <= dv && value >= this.getMinValue() && value <= this.getMaxValue()) {
					dojo.addClass(cell, "dijitCalendarSelectedDate");
				}
				else {
					dojo.removeClass(cell, "dijitCalendarSelectedDate");
				}
			}
			else {
				if(value <= dv && dv <= valueEnd) {
					dojo.addClass(cell, "dijitCalendarSelectedDate");
				}
				else {
					dojo.removeClass(cell, "dijitCalendarSelectedDate");
				}
			}
			// position the time sliders
			if(dv == value) {
				var cb = dojo.coords(cell, false);
				var sb = dojo.contentBox(this._timeSliderA.domNode);
				var x = cb.x + cb.w-sb.w - master_ref.x -1; 
				if(value == valueEnd) {
					 x -= cb.w/2;
				}
				var y =  cb.y + cb.h - master_ref.y;
				dojo.style(this._timeSliderA.domNode, {left: x + "px", top: y + "px"});
				console.log("starttime at (%s, %s)", x, y);
			}
			if(dv == valueEnd) {
				var cb = dojo.coords(cell, false);
				var x = cb.x - master_ref.x +1;
				if(value == valueEnd) {
					x += cb.w/2;
				}
				var y =  cb.y + cb.h - master_ref.y;
				dojo.style(this._timeSliderZ.domNode,  {left: x + "px", top: y + "px"});
				console.log("endtime at (%s, %s)", x, y);
			}
		}));
		if(this.unbounded)
			this._durationInput.attr("value", "");
		else {
			var durval = this.getDuration("millisecond");
			if(this.getDurationUnits() === "day") {
				// don't want to show fractional days.
				// if the interval isn't an even number of days, convert to hours
				var xtra_hrs = durval % (24 * 3600000);
				if(xtra_hrs != 0) {
					this._durationUnits.attr("value", "hour");
				}
			}
			this._durationInput.attr("value", this.getDuration(this.getDurationUnits()));
		}
		this._durationInput.attr("disabled", this.unbounded);
		dojo.style(this._timeSliderZ.domNode, "display", this.unbounded ? "none" : "inline");
		
		// is the start or end time out of view?
		if(this.value < this.rangeStart.valueOf()) {	// start of reservation is before displayed range
			var cell = cells[0];
			var cb = dojo.coords(cell, false);
			var sb = dojo.contentBox(this._timeSliderA.domNode);
			var x = Math.round(cb.x - sb.w - master_ref.x); 
			var y =  Math.round(cb.y + cb.h - master_ref.y) +1;
			dojo.style(this._timeSliderA.domNode, {left: x + "px", top: y + "px"});	
			console.log("starttime at (%s, %s)", x, y);
		}
		else if(this.value > this.rangeEnd.valueOf()) {	// start of reservation is after displayed range
			var cell = cells[cells.length-1];
			var cb = dojo.coords(cell, false);
			var sb = dojo.contentBox(this._timeSliderA.domNode);
			var x = Math.round(cb.x + cb.w - sb.w - master_ref.x);
			var y =  Math.round(cb.y + cb.h - master_ref.y) +1;
			dojo.style(this._timeSliderA.domNode,  {left: x + "px", top: y + "px"});
			console.log("starttime at (%s, %s)", x, y);
		}
		dojo.style(this._timeSliderA.selectionBelowNode, "visibility", (this.value < this.rangeStart.valueOf()) ? "visible" : "hidden");
		dojo.style(this._timeSliderA.selectionAboveNode, "visibility", (this.value > this.rangeEnd.valueOf()) ? "visible" : "hidden");
		
		if(this.valueEnd > this.rangeEnd.valueOf()) {	// end of reservation is after displayed range
			var cell = cells[cells.length-1];
			var cb = dojo.coords(cell, false);
			var sb = dojo.contentBox(this._timeSliderA.domNode);
			var x = Math.round(cb.x + cb.w - master_ref.x); 
			var y =  Math.round(cb.y + cb.h - master_ref.y) +1;
			dojo.style(this._timeSliderZ.domNode, {left: x + "px", top: y + "px"});
			console.log("endtime at (%s, %s)", x, y);
		}
		else if(this.valueEnd < this.rangeStart.valueOf()) {	// end of reservation is before displayed range
			var cell = cells[0];
			var cb = dojo.coords(cell, false);
			var x = Math.round(cb.x - master_ref.x);
			var y =  Math.round(cb.y + cb.h - master_ref.y) +1;
			dojo.style(this._timeSliderZ.domNode,  {left: x + "px", top: y + "px"});
			console.log("endstime at (%s, %s)", x, y);
		}
		dojo.style(this._timeSliderZ.selectionBelowNode, "visibility", (this.valueEnd < this.rangeStart.valueOf()) ? "visible" : "hidden");
		dojo.style(this._timeSliderZ.selectionAboveNode, "visibility", (this.valueEnd > this.rangeEnd.valueOf()) ? "visible" : "hidden");

		value = this._timeSliderA.getValue(); 
		if(null == value || value.valueOf() != this.value)
			this._timeSliderA.setValue(this.value, false);
		value = this._timeSliderZ.getValue() 
		if(null == value || value.valueOf() != this.valueEnd)
			this._timeSliderZ.setValue(this.valueEnd, false);
	},
	// replace the given node's text
	_setText: function(node, text){
		while(node.firstChild != null && node.firstChild.nodeType != 3){ // looking for a text node
			node = node.firstChild;
		}
		if(node != null && node.firstChild != null) {
			node.removeChild(node.firstChild);
		}
		node.appendChild(document.createTextNode(text));
	},
	// when the user changes duration type between forever and specified
	_prevdurtype: "",
	_onDurationTypeChange: function(new_units)
	{
		console.log("DateRange._onDurationTypeChange: ", new_units);
		
		if(this._prevdurtype == new_units) {
			return;
		}
		this._prevdurtype = new_units;
		if(new_units == "forever") {
			this.unbounded = true;
			this.valueEnd = null;
		}
		else {
			if(this.unbounded) {
				// from unbounded, to specified.  Make default duration 1 day
				this.valueEnd = this.value + (24 * 3600000)
			}
			else {
				// I don't have to do anything. _showSelection will handle it
			}
			this.unbounded = false;
		}
		
		this._showSelection();
		this._setExternalValues();
		this._fireOnChange();
	},
	// this is a dijit event handler, not DOM
	_prevdurval: Math.NaN,	// in msecs
	_onDurationValueChange: function()
	{
		console.log("DateRange._onDurationValueChange");
		
		var durval = this._getDurationValueFromInput();
		if(this._prevdurval === durval) {
			return;
		}
		this._prevdurval = durval;
		
		this.valueEnd = this.value + durval;
		if(this.valueEnd > this.getMaxValue())
			this.setEndValue(this.getMaxValue());
		else {
			this._showSelection();
			this._setExternalValues();
			this._fireOnChange();
		}
		return true;
	},
	// _Widget's onKeyPress is only called for printable chars,
	// and onKeyUp is too late to stop the form from getting submitted on ENTER
	_durationKeyDown: function(evt)
	{
		if(evt.keyCode == dojo.keys.ENTER)  {
			dojo.stopEvent(evt);
			this._onDurationValueChange(evt);
			return false;
		}
		return true;
	},
	// called when the start or end time is changed from the time sliders
	_onStartTimeChange: function(/*Date*/newtime)
	{
		console.log("DateRange._onStartTimeChange: ", newtime);
		if(undefined == newtime)
			return;
		if(newtime.valueOf() != this.value) {
			this.setStartValue(newtime);
		}

	},
	_onEndTimeChange: function(/*Date*/newtime)
	{
		console.log("DateRange._onEndTimeChange: ", newtime);
		if(undefined == newtime)
			return;
		if(newtime.valueOf() != this.valueEnd) {
			this.setEndValue(newtime);
		}
	},
	
	_firedStart: null,
	_firedDuration: null,
	_fireOnChange: function()
	{
		console.log("DateRange._fireOnChange");
		var a = new Date(this.value);
		var d = this.getDuration("millisecond");
		if(a != this._firedStart || d != this._firedDuration) {
			this.onChange(a, d);
			this._firedStart = a;
			this._firedDuration = d;
		}
	},
	_fireOnRangeChange: function()
	{
		console.log("DateRange._fireOnRangeChange");
		this.onRangeChange();
	},
	_setExternalValues: function()
	{
		this.startVal.value = (null == this.value)    ? "" : this._formatDate(this.value);
		
		if(this.unbounded) {
			this.endVal.value = "";
			this.durationVal.value = "";
			this.endVal.removeAttribute("name");
			this.durationVal.removeAttribute("name");	// don't send duration with the form of unbounded
		}
		else {
			this.endVal.value   = (null == this.valueEnd) ? this.startVal.value : this._formatDate(this.valueEnd);
			this.durationVal.value = this._formatDuration();
			if(this.endName.length > 0) {
				this.endVal.setAttribute("name", this.endName);
			}
			if(this.durationName.length > 0) {
				this.durationVal.setAttribute("name", this.durationName);
			}
		}
	},
	_formatDate: function(dt)
	{
		if(null == dt) {
			return "";
		}
		if("number" === typeof dt) {
			dt = new Date(dt);
		}
		var s = dojo.date.stamp.toISOString(dt);
		return s;
	},
	// parse the incoming string and return a Date object set at midnight of the given date
	_parseDate: function(str)
	{
		var dt;
		if(str.match(/\d{4}-\d{2}-\d{2}T?/)) {
			dt = dojo.date.stamp.fromISOString(str);
		}
		else {
			dt = dojo.date.locale.parse(str, {})
		}
		return dt;		
	},
	_formatDuration: function()
	{
		if(this.unbounded)
			return "";
		
		dstr = "P0D";
		if(this.getDurationUnits() === "day") {
			dstr = "P" + this.getDuration("day") + "D";
		}
		else {
			dstr = "PT" + this.getDuration("hour") + "H";
		}
		return dstr;
	},
	_clearSelection: function()
	{
		
		if(dojo.isIE) {
			document.selection.empty();
		}
		else  {
			window.getSelection().removeAllRanges();
			// alternatively, but the above works in FF, Safari and Chrome
			//dojo.require("dijit._editor.range");
			//dijit.range.getSelection(window, true).removeAllRanges();
		}
	},
	_getDateCell: function(/*string|Date|Number*/ d)
	{
		var target_dval = this._getDateValue(d);
		if(null == target_dval) 
			target_dval = (new Date()).valueOf();
		if(!isNaN(target_dval)) {
			var cells = dojo.query(".dijitCalendarDateTemplate", this.dateLabelRow);
			for(var i in cells) {
				var cell = cells[i];
				if(cell.dijitDateValue === target_dval) {
					return cell;
				}
			}
		}
		return null;
	},
	_getDateValue: function(/*string|Date|Number*/d)
	{
		var dval = Number.NaN;
		var t = typeof d;
		switch(t) {
		case "number":
			dval = d;
			break;
		case "string":
			if("now" === d) {
				dval = null;
			}
			else {
				var m = d.match(/\d+/);
				if(m.length > 0 && m[0].length == d.length) {
					dval = parseInt(d);
				}
				else {
					var dobj = this._parseDate(d);
					dval = dobj.valueOf();
				}
			}
			break;
		case "object":
			if(null == d)
				dval = null;
			else
				dval = d.valueOf();
			break;
		}
		return dval;
	},
	// given a date value (millisecs since 1970-01-01), round
	// to multiple of this._timeResolution
	_roundTime: function(/*int*/dv) 
	{
		return Math.floor(dv / this._timeResolution) * this._timeResolution;
	},
	showProgress: function(/*boolean*/bShow)
	{
		if(bShow)
			dijit.byId(this.id + "_spinner").show();
		else
			dijit.byId(this.id + "_spinner").hide();
	},
	onChange: function(/*Date*/start, /*Date*/end)
	{
		// override or sink to be notified of selection changes
	},
	onRangeChange: function()
	{
		// override or sink to be notified if the displayed range changes
	},
	
	_dummy:null
});
dojo.declare("dijit.form.TimeTextOnlyBox", dijit.form._DateTimeTextBox,
{
	// summary:
	//		A validating, serializable, range-bound time text box with a popup time picker

	baseClass: "dijitTextBox dijitTimeTextOnlyBox",
	popupClass: "",
	_selector: "time",
//	resolution: 1800000,	// 15 minutes
		
	// TODO: I think setting constraints min, max will constrain the values 
	//constraints:{},

	_setValueAttr: function(/*Date*/ value, /*Boolean?*/ priorityChange, /*String?*/ formattedValue)
	{
		// round value to nearest OK value
//		if(null != value && "object" === typeof value) {
//			value = new Date(Math.round(value.valueOf() / this.resolution) * this.resolution);
//		}
		this.inherited(arguments);
	},
			
	_dummy:null
});
dojo.declare("ibm.tivoli.simplesrm.srm.dijit.TimeSlider", [ dijit._Widget, dijit._Templated], 
{
	templatePath: dojo.moduleUrl("ibm.tivoli.simplesrm.srm.dijit", "templates/TimeSlider.html"),
	widgetsInTemplate: true,
	required: 'false',
	timeid: 'time',
	_timeInput: null,
//	resolution: 3600000,	// 1 hour
	_dateval: "",
	_prev_value: null,
	_parentCalendar: null,
	_supressOnChange: true,

	constructor: function(params, domNode)
	{
	},
	startup: function()
	{
		console.log("TimeSlider.startup");
		this.inherited(arguments);
		this._timeInput = dijit.byId(this.id + "_" + this.timeid);
//		this._timeInput.resolution = this.resolution;
		this.connect(this._timeInput, "onChange", "_onTimeChange");
	},
	setValue: function(newval, fireOnChange)
	{
		console.log("TimeSlider[%s].setValue %s", this.id, new Date(newval));
		var v = this.getValue();
		this._prev_value = (undefined != v) ? v.valueOf() : null;

		var t = typeof newval;
		var val = null;
		if(t === "number") {
			val = newval;
		}
		else if(t === "string") {
			throw new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(new Error("TODO: implement this"));
		}
		else if(null != newval && t === "object") {
			val = newval.valueOf();
		}
		if(null == val) {
			this._dateval = this.dateDisplay.innerHTML = "";
			this._timeInput._setDisplayedValueAttr("", true);
		}
		else {
			var dt = new Date(val);
			this._dateval = this.dateDisplay.innerHTML = dojo.date.locale.format(dt, {selector:"date"});
			this._supressOnChange = !fireOnChange;
			this._timeInput._setValueAttr(dt, true);
		}
	},
	getValue: function()
	{
		var dstr = this._dateval + " " + this._timeInput.attr("displayedValue");
		var dt = dojo.date.locale.parse(dstr, {});
		return dt;
	},
	_onMouse: function(evt)
	{
		if(this.disabled)
			return false;
			
		if(evt.type === "mouseover") {
			dojo.addClass(evt.target, "hover");
		}
		else if(evt.type === "mouseout") {
			dojo.removeClass(evt.target, "hover");
			dojo.removeClass(evt.target, "down");
		}
		else if(evt.type === "mousedown") {
			dojo.addClass(evt.target, "down");
		}
		else if(evt.type === "mouseup") {
			dojo.removeClass(evt.target, "down");
			var dir = (this.decreaseTimeArrowNode === evt.target) ? "dec" : "inc";
			this._shiftTime(dir);
		}
	},
	_shiftTime: function(/*string*/increment_or_decrement)
	{
		var dir = 1;
		if("string" === typeof increment_or_decrement && increment_or_decrement.substring(0, 1) === "d") {
			dir = -1;
		}
		var tm = this.getValue();
		if(!tm) {
			tm = new Date();
		}
		tm = new Date(this._parentCalendar._roundTime(tm.valueOf()));	// normalize
		var newval = tm.valueOf() + (dir * this._parentCalendar._timeResolution);
		if(this._parentCalendar.isPermissible(newval)) {
			this.setValue(new Date(newval), true); // true -> fire onChange 
		}

		if(this._parentCalendar.isPermissible(newval + this._parentCalendar._timeResolution)) {
			dojo.removeClass(this.increaseTimeArrowNode, "dijitDisabled");
		}
		else {
			dojo.addClass(this.increaseTimeArrowNode, "dijitDisabled");
		}
		if(this._parentCalendar.isPermissible(newval - this._parentCalendar._timeResolution)) {
			dojo.removeClass(this.decreaseTimeArrowNode, "dijitDisabled");
		}
		else {
			dojo.addClass(this.decreaseTimeArrowNode, "dijitDisabled");
		}
		
	},
	onChange: function(newval)
	{
		// override
		console.log("TimeSlider.onChange: ", newval);
	},
	// TODO: maybe have a _parentCalendar property so I can check if the time is inbounds
	// up front, and not via some crazy round-about reset thing
	_onTimeChange: function(newval) 
	{
		console.log("TimeSlider._onTimeChange:", newval);
		if(!this._supressOnChange) {
			this.onChange(this.getValue());
			this._supressOnChange = true;
		}
	},
	// left over from when we let the user type a time in 
	_onTimeBlur: function()
	{
		console.log("TimeSlider._onTimeBlur");
		var bValid = this._timeInput.isValid();
		if(!bValid)
			this._timeInput.focus();
		return bValid;
	},
	_onTimeKeyDown: function(evt)
	{	
		console.log("TimeSlider._onTimeKeydown");
		if(evt.keyCode == dojo.keys.ENTER)  {
			dojo.stopEvent(evt);
			if(this._timeInput.validate()) {
				this.domNode.blur();
			}
			return false;
		}
		return true;
	},
	_onTimeKeyUp: function(evt)
	{
		console.log("TimeSlider._onTimeKekUp");
		if(evt.keyCode == dojo.keys.ENTER)  {
			dojo.stopEvent(evt);
			return false;
		}
		return true;
	},

	_dummy:null
  });
