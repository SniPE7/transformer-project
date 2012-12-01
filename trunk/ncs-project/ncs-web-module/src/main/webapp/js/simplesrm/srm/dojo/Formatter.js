//////////////////////////////////////////////////////////////////
// @JS_LONG_COPYRIGHT_BEGIN@
// @JS_LONG_COPYRIGHT_END@
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dojo.Formatter");

dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");

dojo.declare("ibm.tivoli.simplesrm.srm.dojo.Formatter", null, {

	_uiStringTable: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable"),

	_formatDate: function(d) {
		//console.log("MyRecordsGrid._formatDate(%s)", d);
		// When we changed from the webservice to TDI, the date lost the 'T'
		if (d) {
			if (d.search("9999") == 0) {
				d = this._uiStringTable.ForeverLabel;
			} else {
				var isod = d.replace(' ', 'T');
				var format_opts = {fullYear:true, selector: "date"};
				var dt = dojo.date.stamp.fromISOString(isod);
				if(dt) {
					d = dojo.date.locale.format(dt, format_opts);
					d = "<div style='oveflow:hidden;white-space:nowrap' title='"
						+ d + "'>" + d + "</div>";
				}
			}
		}
		return d;
	},

	/**
	 * Format a date displaying with the date and WITHOUT time.
	 * TODO Rename this function to _formatDate
	 */	
	_formatDatetime: function(d) {
		if(d) {
			if(d.search("9999") == 0) {
				d = this.__uiStringTable.ForeverLabel; 
			}
			else {
				var dt = d.formatISODateString();
				if(dt) {
					d = "<div style='oveflow:hidden;white-space:nowrap' title='"
						+ dt + "'>" + dt + "</div>";
				}
			}
		}
		return d;
	},
	
	/**
	 * Format a date displaying date AND time.
	 * TODO Rename this function to _formatDateTime
	 */
	_formatDateTimeReally: function(d) {
		if (d) {
			if(d.search("9999") == 0) {
				d = this.__uiStringTable.ForeverLabel; 
			} else {
				var dt = d.formatISODateString({sel: "datetime"});
				if (dt) {
					d = "<div style='oveflow:hidden;white-space:nowrap' title='"
						+ dt + "'>" + dt + "</div>";
				}
			}
		}
		return d;
	},
	
	_formatStringSafe: function(s) {
		return undefined == s ? "" : s;
	},
	
	_formatCost: function(c) {
		if(c) {
			c = dojo.currency.format(c, {currency: "USD"});
		}
		return c;
	},

	_formatIcon: function(s, rowIndex) {
		return this._formatIconSz(s, rowIndex, 20)
	},
	
	_formatIconSz: function(s, rowIndex, w) {
		if(s) {
			//var icon = this.imagesPath + "icons/" + s;
			var r = this.simpleGrid.getItem(rowIndex);
			var icon = this.imageCacheUrl + s + "?REFID=" + r.ItemID;
			return "<img height='"+w+"' width='"+w+"' src='"+ icon + "' >";
		}
		return '';
	
	},
	
	_sortDefault: function(a, b) {
		return (a < b) ? -1 : (a > b) ? 1 : 0
	},
	
	_sortString: function(a, b) {
		if(a && b) {
			return a.localeCompare(b);
		}
		return 0;
	},
	
	_sortNumber: function(a, b) {
		if(a && b) {
			return (a - b);
		}
		return 0;
	}

});
