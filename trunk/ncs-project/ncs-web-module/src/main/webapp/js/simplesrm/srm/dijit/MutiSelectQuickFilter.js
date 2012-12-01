//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.MutiSelectQuickFilter");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");

dojo.declare("ibm.tivoli.simplesrm.srm.dijit.MutiSelectQuickFilter", [dijit._Widget, dijit._Templated],
{
	_uiStringTable: dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable"),
	selectID: "",
	timer: -1,
	isIE: -1,
	templateString : '<div class="quick-filter" style="padding: 0px 0px 0px 0px">'+
    					'<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
    					'	<tr>'+
    					'		<td class="qf-lcap" width="15px">&nbsp;</td>'+
    					'      	<td class="qf-mid" width="180px" valign="middle">'+
    					'			<label style="display:none;" for="${id}_quickFilter">${_uiStringTable.QuickFilter}</label>'+
    					'       	<input id="${id}_quickFilter" title="${_uiStringTable.QuickFilter}" dojoAttachPoint="_quickSearchField" type="text" class="qf-textfield"'+
    					'				dojoAttachEvent="onblur: quickFilterDefault, onfocus: quickFilterFocus, onkeyup: filterTable"/>'+
    					'        </td>'+
    					'      <td class="qf-rcap" width="15px">&nbsp;</td>'+
    					'    </tr>'+
    					'  </table>'+
    					'</div>',
	constructor: function()
	{
		console.log("MutiSelectQuickFilter.ctor");
	},
	postCreate: function()
	{
		console.log("MutiSelectQuickFilter.postCreate");
		this.isIE = navigator.userAgent.indexOf( "MSIE" ) != -1;
		this.inherited(arguments);
	},
    getAncestorByName: function (element, elementName){
  		var result=null;
  		try{
  			while(result==null && element!=null){
  				element=element.parentNode;
  				if(element.nodeName.toLowerCase()==elementName.toLowerCase()){
  					result=element;
  				}
  			}
  		}catch(e) {
  			//wasn't a node
  		}
  		return(result);
  	},
  	quickFilterFocus: function() {
  		//alert("evt.nodeName = " + evt.nodeName);
  		var element=this.getAncestorByName(this._quickSearchField, "div");
  		element.className="quick-filter-focus";
  		//alert("quickFilterFocus = " + element.className);
  	},
  	quickFilterDefault: function() {
		//alert("quickFilterDefault()");
		var element=this.getAncestorByName(this._quickSearchField, "div");
		element.className="quick-filter";
	},
	quickFilterOver: function() {
		//alert("quickFilterDefault()");
		var element=this.getAncestorByName(this._quickSearchField, "div");
		element.className="quick-filter-over";
	},
	filterTable: function ()
	{
		if( null == this.selectID)
		{
			console.log("ERROR: Unable to locate the table id" );
			return;
		}
		if(null == document.getElementById( this.selectID ))
		{
			console.log("ERROR: No select found with name:" + this.selectID);
		}
		this.startFilter();
	},

	startFilter: function(){
		var context = this;
		if( this.timer == -1 )
			this.timer = window.setTimeout( function(){ context.filter.apply( context ); return null; }, 70 );
			return;
	},
	
	stopFilter: function()
	{
	  var context = this;
	  window.clearTimeout( this.timer );
	  return;
	},
	filter: function()
	{
		var select = document.getElementById( this.selectID );
		var filter = this._quickSearchField.value.toLowerCase();
	  // rest the timer element
	  this.timer = -1;

	  // Walk all the rows in the table
	  for( i = 0; i < select.length; i++ )
	  {
		  var display = false;
		  // Explore each cell looking for a match
		  display = this.ExploreNodeWithKey( select.options[i], filter );
		  if( !display )
		  {
			  select.options[i].style.display = "none";
		  }
		  else
		  {
			  select.options[i].style.display = (this.isIE)?"block":"table-row";
		  }
	  }
	},
	
	ExploreNodeWithKey: function( el, key )
	{
	  var keyFound = false;

	  if( el.hasChildNodes() )
	  {
	    var i;
		// We are looking for the first match in the element.
		// After we find this match we stop looking at the children
	    for( i=0; i < el.childNodes.length && !keyFound; i++ )
		{
		  var child = el.childNodes[i];

		  // Expore each child element
		  //console.log( "[ExploreNodeWithKey] Explore child element type is " + child.nodeType + " value is " + child.nodeValue );
		  try{
		    if( child.nodeValue != null )
			{
			  // Determine if the key is anywhere in the string.
			  if( child.nodeValue.toLowerCase().indexOf( key ) != -1 )
			  {
			    keyFound = true;
			  }
			}
			else
			{
			  keyFound = this.ExploreNodeWithKey( child, key );
			}
		  }catch( e ){
		    console.log( "[ExploreNodeWithKey] Exception thrown, must not be a text node. Explore this object further." );
		    keyFound = this.ExploreNodeWithKey( child, key );
		  }
		}
	  }
	  else
	  {
		// Has no children but it could just be a text node leaf
	    try{
	  	  if( el.nodeValue != null )
		  {
		    if( el.nodeValue.toLowerCase().indexOf( key ) != -1 )
		    {
		      keyFound = true;
		    }
		  }
		 }catch( e ){
		    console.log( "[ExploreNodeWithKey] Exception thrown, no children." );
		  }
	  }

	  //console.log( "[ExploreNodeWithKey] keyFound is " + keyFound );
	  return keyFound;
	},
	emptyString:null
	
});
