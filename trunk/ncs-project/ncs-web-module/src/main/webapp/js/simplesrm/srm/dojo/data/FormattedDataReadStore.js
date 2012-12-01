//////////////////////////////////////////////////////////////////
// @JS_LONG_COPYRIGHT_BEGIN@
// @JS_LONG_COPYRIGHT_END@
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dojo.data.FormattedDataReadStore");

dojo.require("dojox.data.AndOrReadStore");

dojo.declare("ibm.tivoli.simplesrm.srm.dojo.data.FormattedDataReadStore", dojox.data.AndOrReadStore, 
{
	formatterMap: null,
	
	setStructure: function(structure)
	{
		this.formatterMap = {};
		for(var i in structure) {
			var col = structure[i];
			if(col.formatter) {
				this.formatterMap[col.field] = col.formatter;
			}
		}
	},
	_containsValue: function(	/* item */ item, 
								/* attribute-name-string */ attribute, 
								/* anything */ value,
								/* RegExp?*/ regexp)
	{
		//	summary: 
		//		Internal function for looking at the values contained by the item.
		//	description: 
		//		Internal function for looking at the values contained by the item.  This 
		//		function allows for denoting if the comparison should be case sensitive for
		//		strings or not (for handling filtering cases where string case should not matter)
		//	
		//	item:
		//		The data item to examine for attribute values.
		//	attribute:
		//		The attribute to inspect.
		//	value:	
		//		The value to match.
		//	regexp:
		//		Optional regular expression generated off value if value was of string type to handle wildcarding.
		//		If present and attribute values are string, then it can be used for comparison instead of 'value'
		var vals = this.getValues(item, attribute);
		if(this.formatterMap[attribute]) {
			vals = dojo.map(vals, dojo.hitch(this, function(v) 
			{
				return this.formatterMap[attribute](v);
			}));
		}
		// the rest of this code is verbatim from AndOrReadStore._containsValue
		return dojo.some(vals, function(possibleValue){
		
			if(possibleValue !== null && !dojo.isObject(possibleValue) && regexp){
				if(possibleValue.toString().match(regexp)){
					return true; // Boolean
				}
			}else if(value === possibleValue){
				return true; // Boolean
			}
		});
			
	}
});