dojo.provide("ibm.tivoli.tip.dijit.TimeInputBox"); 

dojo.require("dijit._TimePicker");
dojo.require("ibm.tivoli.tip.dijit.Images");
dojo.require("ibm.tivoli.tip.dijit.DateInputBox");
dojo.require("ibm.tivoli.tip.dijit.picker.TimePicker");

/** 
 * A time input box widget.
 * 
 * Such widget shows a text input box and a time picker icon on the right.
 * By clicking on such icon a time picker is shown to help the user
 * to select a time.
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
    "ibm.tivoli.tip.dijit.TimeInputBox",
    [ibm.tivoli.tip.dijit.DateInputBox],
    {
        /** the picker class */
        pickerClass: "ibm.tivoli.tip.dijit.picker.TimePicker",
        /** the picker icon */
        pickerIcon: ibm.tivoli.tip.dijit.Images.get()["TIME_ICON"],
        /** constraints */
        constraints: {selector: "time", formatLength: "short"},
        
        /**
         * Constructor.
         */
        constructor: function()
        {
            // set the regex for the text
            this.textRegExp = "^(" + dojo.date.locale.regexp(this.constraints) + "){0,1}$";
            // set the regex message for the text
            this.textRegExpMessage = this._resources.TIME_INVALID_VALUE;
        }
    }        
);

