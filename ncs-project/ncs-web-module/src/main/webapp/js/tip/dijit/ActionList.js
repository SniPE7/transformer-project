/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/

dojo.provide("ibm.tivoli.tip.dijit.ActionList"); 

dojo.require("ibm.tivoli.tip.dijit.DropDownList");

/** 
 * The action-list widget.
 * 
 * Such widget displays a drop-down list whose options are actions
 * to be executed. The first user's provided option is used as an 
 * explicative message and does not represent an action. Such option
 * is always shown in the box of the drop-down. 
 * Once an action is selected the onActionSelected callback is called.
 *  
 * The widget provides the following parameters inherited from the
 * ibm.tivoli.tip.dijit.DropDownList widget:
 * 
 *  - options: an object whose properties are the drop-down options.
 *    The first property is used as an explicative message.
 *  - size: the maximum number of options to display in the drop-down
 *    list. If not specified all the options will be shown.
 *
 * Example of a declaration:
 * 
 *          <div 
 *              dojoType="ibm.tivoli.tip.dijit.ActionList" 
 *              options="{action1: 'this is the action #1', action2: 'this is the action #2'}">
 *          </div>
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)   
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.ActionList",
    [ibm.tivoli.tip.dijit.DropDownList],
    {
        /**
         * Fills the widget with the options.
         */
        startup: function() 
        {
            // the first one must be selected
            this.selected = "";
            // call the superclass's method
            this.inherited("startup", arguments);
            // disable the first option
            if ( this._selectNode.options.length > 0 ) {
                this._selectNode.options[0].disabled = true;
                this._selectNode.selectedIndex = 0;
            }
        },
        
        /**
         * Called when an action has beed selected.
         * 
         *  @param value
         *      the selected value.
         *  @param text
         *      the selected text.
         */
        onActionSelected: function(value, text)
        {
        },
        
        /**
         * Hides/shows the option corresponding to the given value.
         * 
         *  @param value:
         *      the value to be hidden/shown.
         *  @param hide:
         *      true if the option must be hidden, false if must be shown.
         */
        _hideOption: function(value, hide)
        {
            // avoid to hide the message
            if ( this._getIndexByValue(value) !== 0 ) {
                this.inherited("_hideOption", arguments);
            }
        },
        
        /**
         * Called when an option has beed selected.
         */
        onValueChanged: function(value)
        {
            if ( this._selectNode.selectedIndex !== 0 ) {
                // call the callback
                this.onActionSelected(value, this.getSelectedText());
                // reselect the message
                this._selectNode.selectedIndex = 0;
            }
        }
    }
);

