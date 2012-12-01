dojo.provide("ibm.tivoli.tip.dijit.picker.TimePicker"); 

dojo.require("ibm.tivoli.tip.dijit.picker.PopupPicker"); 
dojo.require("dijit._Calendar");

/**
 * The time-picker popup. 
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.picker.TimePicker",
    [ibm.tivoli.tip.dijit.picker.PopupPicker, dijit._TimePicker],
    {
        /** constraints */
        constraints: {selector: "time", formatLength: "short"}
    }        
);

