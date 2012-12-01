dojo.provide("ibm.tivoli.tip.dijit.picker.Picker"); 

dojo.require("dijit._Widget");

/**
 * The base interface for a picker. 
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.picker.Picker",
    [dijit._Widget],
    {
        
        /**
         * Opens the picker.
         */
        open: function()
        {
        },

        /**
         * Closes the picker.
         */
        close: function()
        {
        },
        
        /**
         * Opens the picker if closed, closes the picker if opened.
         */
        toggle: function()
        {
            this.isOpen() ? this.close() : this.open();
        },
        
        /**
         * Returns true if the picker is opened.
         */
        isOpen: function()
        {
            return false;
        },        

        /**
         * Called back when a value has been selected from the picker.
         * 
         *      @param value: the value selected. 
         *          The value is provided by the picker and its
         *          type dependes on the specific picker implementation.
         */
        onValueSelected: function(value)
        {
        },
            
        /**
         * Sets the start-value to be shown by the picker when opened.
         * 
         *      @param value: the value to be shown by the picker. 
         *          The value's type dependes on the specific picker 
         *          implementation.
         */                    
        setValue: function(value)
        {
        }
    }        
);

