/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
 dojo.provide("ibm.tivoli.tip.dijit.CheckBox"); 

dojo.require("ibm.tivoli.tip.dijit.HtmlSelection");

/** 
 * A Check Box widget.
 * 
 * Such widget displays a check box. The widget calls the 
 * onValueChanged callback when the checked state changes
 * even if the widget still has the focus. 
 *   
 * The widget accepts the following parameters:
 * 
 *  - value: the initial value for the check box. 
 *      - "true": the check box must be checked
 *      - "false": the check box must not be checked
 *  - checked: same as the HTML input attribute.
 *      - "", "checked", "true": the check box must be checked
 *      - any other value: the check box must not be checked
 * 
 * Example of a declaration:
 * 
 *       <div 
 *          dojoType="ibm.tivoli.tip.dijit.CheckBox" 
 *          checked="checked"/>
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)   
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.CheckBox",
    [ibm.tivoli.tip.dijit.HtmlSelection],
    {
        /** HTML input type */
        _type: "checkbox" 
    }
);

