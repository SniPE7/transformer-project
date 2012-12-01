//////////////////////////////////////////////////////////////////
// @JS_LONG_COPYRIGHT_BEGIN@
// @JS_LONG_COPYRIGHT_END@
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dojo.data.Comparator");


ibm.tivoli.simplesrm.srm.dojo.data.Comparator.intCompare = function(a, b)
{
	if(a && b) {
		var a_int = parseInt(a);
		var b_int = parseInt(b);
		return a_int > b_int ? 1 : a_int < b_int ? -1 : 0;
	}
	return 0;
}

ibm.tivoli.simplesrm.srm.dojo.data.Comparator.stringCompare = function(a, b)
{
	if(a && b) {
		return -a.localeCompare(b);
	}
	return 0;
}