/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * 5724-C51
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.provide("ibm.tivoli.tip.dijit.TIPTableTitleBar");
/******************************************************************************
* Provides title bar for table that looks like TIP portlet title
*
* Authors: Joseph Firebaugh
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.TIPTableTitleBar", [dijit._Widget, dijit._Templated],
{
  label: "",
  
  templateString: "<div class='tipTableTitleBar'><div class='tipTableTitleBarLabel' dojoAttachPoint='titleNode'>${label}</div></div>",

  /*****************************************************************************
  * Programmatically allow changing of the table title. String argument can
  * containe any HTML.
  *****************************************************************************/
  setTitle: function( label /*string*/ )
  {
    this.titleNode.innerHTML = label;
  }  

});
