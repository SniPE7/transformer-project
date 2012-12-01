/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
 
dojo.provide("ibm.tivoli.tip.dijit.DropDownList"); 

dojo.require("dijit._Templated");
dojo.require("ibm.tivoli.tip.dijit.Widget");

/** 
 * A drop-down list widget.
 * 
 * Such widget displays a drop-down list based on the browser
 * drop-down control. The usage of that control limits a little
 * bit the look&feel (e.g. the drop-down button).
 * The widget accepts the following parameters:
 * 
 *  - options: an object whose properties are the drop-down options.
 *    Each property's name is used as option's value and the corresponding
 *    property'svalue is used as option's displayed text.
 *    For example the object:
 * 
 *          { 
 *              value1: "this is the value #1",  
 *              value2: "this is the value #2" 
 *          }
 *     
 *    maps to the following HTML code
 * 
 *          <select>
 *              <option value="value1">"this is the value #1"</option>
 *              <option value="value2">"this is the value #2"</option>
 *          </select>
 * 
 *    Values (the option's properties) are keys for the options.
 *  - selected: the value of the option to be selected. "" means no 
 *    selection.
 *  - size: the maximum number of options to display in the drop-down
 *    list. If not specified all the options will be shown.
 * 
 * Example of a declaration:
 * 
 *          <div 
 *              dojoType="ibm.tivoli.tip.dijit.DropDownList" 
 *              options="{value1: 'this is the value #1', value2: 'this is the value #2'}"
 *              selected="value2">
 *          </div>
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)     
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.DropDownList",
    [ibm.tivoli.tip.dijit.Widget, dijit._Templated],
    {
        /** the user's provided set of options */
        options: {},
        /** the user's provided selected option (the value) */
        selected: "",
        /** the user's provided number of options to show */
        size: "",
        
        /** the widget template path */
        templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/DropDownList.html"),
        
        /** select DOM node */
        _selectNode: null,
        /** the current set of options */
        _options: {},
        /** contains boolean properties which says if an option is hidden or not */
        _hidden: {},
        
        /**
         * Widget constructor.
         */
        constructor: function()
        {
            this.options = {};
            this._options = {};
            this._hidden = {};
        },
        
        /**
         * Sets the initial value.
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited("startup", arguments);

            // set the user's provided options
            this.setOptions(this.options);
            
            // select the required number of items to show
            if ( this.size !== "" ) {
                this._selectNode.length = parseInt(this.selected, 10);
            }        

            // select the required item
            if ( this.selected !== "" ) {
                this.setValue(this.selected);
            }
        },
        
        /**        
         * Returns the option's value pointed by the current selection.
         * Returns undefined if no selection is in place.
         */
        getValue: function()
        {
            // return the selected value
            if ( this._selectNode.selectedIndex !== -1 ) {
                return this._selectNode.options[this._selectNode.selectedIndex].value;
            } 
        },
        
        /**        
         * Sets the widget value.
         * 
         *  @param value: 
         *      the value of the option to be selected.
         */
        setValue: function(value)
        {
            var index = this._getIndexByValue(value);
            this._selectNode.selectedIndex = index;
        },
        
        /**
         * Returns true if the widget's value is not set.
         */
        isEmpty: function()
        {
            return ( this._selectNode.selectedIndex === -1 );
        },

        /**
         * Enables the widget for editing. 
         */
        setDisabled: function(isDisabled)
        {
            if ( isDisabled != this.isDisabled() ) {
                // update the enablement status
                this.inherited("setDisabled", arguments);
                // update the widget
                this._updateWidget();
            }
        },
        
        /**        
         * Sets the drop-down options.
         * 
         *  @param options: 
         *      an object whose properties are the drop-down options.
         *      Each property's name is used as option's value and 
         *      the corresponding is used as option's displayed text.
         */
        setOptions: function(options)
        {
            // clean-up the previous options
            var o = this._selectNode.options;
            o.length = 0; 
            // populate the options
            for ( var value in options ) {
                if ( ! this._hidden[value] ) {
                    o[o.length] = new Option(options[value], value);
                }
            }
            // store the options
            this._options = options;  
        },

        /**
         * Returns an object whose property's maps to the option's 
         * value and the property's value maps to the option's displayed text. 
         */
        getOptions: function()
        {
            return this._options;
        },
        
        /**
         * Hides the option corresponding to the given value.
         * 
         *  @param value:
         *      the value to be hidden.
         */
        hideOption: function(value)
        {
            this._hideOption(value, true);
        },
        
        /**
         * Shows the option corresponding to the given value.
         * 
         *  @param value:
         *      the value to be shown.
         */
        showOption: function(value)
        {
            this._hideOption(value, false);
        },
        
        /**
         * Disables/enables theoption corresponding to the given value.
         * 
         *  @param value:
         *      the value to be shown.
         */
        disableOption: function(value, disabled)
        {
            var option = this._selectNode.options[this._getIndexByValue(value)];
            option.disabled = disabled;
        },
        
        /**
         * Returns the text of the current selected item.
         */
        getSelectedText: function()
        {
            return this._selectNode.options[this._selectNode.selectedIndex].text;
        },
        
        /**
         * Handles the onchange event.  
         */
        _onChange: function()
        {
            // notify the change
            this.onValueChanged(this.getValue());
        },
        
        /**
         * Returns the index of the option which matches the given value.
         * If the option is not matched return -1.
         */
        _getIndexByValue: function(value)
        {
            var o = this._selectNode.options;
            var ol = o.length;
            for ( var i = 0; i < ol; i++ ) {
                if ( o[i].value === value ) {
                    return i;   
                }
            }
            return -1;
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
            // store the currently selected value
            var v = this.getValue();
            // hide/show the value
            this._hidden[value] = hide;
            // reset the options 
            this.setOptions(this._options);
            // select the previous value
            if ( v ) {
                this.setValue(v);
            }
        },
        
        /**
         * Updates the widget look&feel depending on the widget' status.
         */
        _updateWidget: function()
        {
            // disable the select node
            this._selectNode.disabled = this.isDisabled();             
            // set the style
            this.domNode.className = this.isDisabled() ? "dropDownListDisabled" : "dropDownListDefault";                
        }
    }
);

