/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.provide("ibm.tivoli.tip.dijit.DateInputBox"); 

dojo.require("ibm.tivoli.tip.dijit.Images");
dojo.require("ibm.tivoli.tip.dijit.picker.PickerInputBox");
dojo.require("ibm.tivoli.tip.dijit.picker.DatePicker");

/** 
 * A date input box widget.
 * 
 * Such widget shows a text input box and a calendar picker icon on the right.
 * By clicking on such icon a calendar picker is shown to help the user
 * to select a date.
 * 
 * The widget supports the following attributes:
 * 
 *      - value (Date | String): the initial value.
 *      - required (String): "true" if the date is required.
 *      - tabindex (Number): the widget tabindex.
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)   
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.DateInputBox",
    [ibm.tivoli.tip.dijit.picker.PickerInputBox],
    {
        /** the initial value (Date or String) */
        value: null,

        /** the picker class */
        pickerClass: "ibm.tivoli.tip.dijit.picker.DatePicker",
        /** the picker icon */
        pickerIcon: ibm.tivoli.tip.dijit.Images.get()["CALENDAR_ICON"],
        /** constraints */
        constraints: {selector: "date"},
        
        /** the value (Date) */
        _value: null,

        /**
         * Constructor.
         */
        constructor: function()
        {
            // set the regex for the text
            this.textRegExp = "^(" + dojo.date.locale.regexp(this.constraints) + "){0,1}$";
            // set the regex message for the text
            this.textRegExpMessage = this._resources.DATE_INVALID_VALUE;
        },
        /**
         * Creates the picker and sets the initial value.  
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited("startup", arguments);

            // get the picker protoype
            var PickerClass = dojo.getObject(this.pickerClass, false);
            // create the picker
            var picker = new PickerClass({parent: this._textNode});
            // set the picker
            this.setPicker(picker);

            // set the initial value
            this.setValue(this.value);

            if(undefined != this.constraints.required)
	            this.setRequired(this.constraints.required);
	        else
	        	this.setRequired(this.required);
        },

        /**        
         * Returns a Date object representing the widget value.
         */
        getValue: function()
        {
            // return the value
            return this._value;
        },
        
        /**        
         * Sets the date value.
         * 
         *      @param value
         *          a Date object.
         */
        setValue: function(value)
        {
            if ( value === null ) {
                // nule value means null (empty) data
                this._value = null;
            }
            else {
                // in case of string parse to a date, otherwise a date object is assumed
                this._value = 
                    dojo.isString(value) ? 
                    dojo.date.locale.parse(value, this.constraints) : 
                    value;
            }
            
            // call the superclass's method 
            arguments[0] = 
                this._value === null ? 
                "" : 
                dojo.date.locale.format(value, this.constraints);
            
            this.inherited("setValue", arguments);
        },
        
        /**
         * Returns true if the widget's value is not set.
         */
        isEmpty: function()
        {
            return this._value === null;
        },

        /**        
         * Returns the text representation of the value (a String)
         */
        getTextValue: function()
        {
            // return the text value
            return ibm.tivoli.tip.dijit.DateInputBox.superclass.getValue.call(this);
        },
        
        /**
         * Returns the picker start value.
         */
        getPickerStartValue: function()
        {
            return this._value || new Date();  
        },
        
        /**
         * Returns the picker text value.
         */
        getPickerTextValue: function(value)
        {
            return dojo.date.locale.format(value, this.constraints);  
        },
        
        /**
         * Handles a change called if the text value has changed.
         * 
         *      @param value (String)
         *          the text value.
         */        
        onTextValueChanged: function(value)
        {
            this._value = dojo.date.locale.parse(value, this.constraints);
            this.onValueChanged(value);
        }        
    }        
);

