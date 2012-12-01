dojo.provide("ibm.tivoli.tip.dijit.picker.DialogPicker"); 

dojo.require("ibm.tivoli.tip.dijit.picker.Picker"); 
dojo.require("dijit.Dialog");

/**
 * A dialog picker base class. 
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.picker.DialogPicker",
    [ibm.tivoli.tip.dijit.picker.Picker],
    {
        /** the widget used to place the popup-picker */
        _parent: null,
        /** the dialog */
        _dialog: null,

        /**
         * Constructor.
         * 
         *      @param params: a set of parameters:
         *          - parent: the widget used to place the popup-picker.
         */
        constructor: function(params)
        {
            this._parent = params.parent;
            this._title = params.title;
        },
        
        startup: function()
        {
            // call the superclass's method
            this.inherited("startup", arguments);
            // create the dialog
            this._dialog = new dijit.Dialog({title: this._title}, this._parent);            
        },
                       
        destroy: function()
        {
            // close the dialog
            this.close();
            // destroy the dialog
            this._dialog.destroy();
            // call the superclass's method
            this.inherited("destroy", arguments);
        },
        
        /**
         * Opens the picker.
         */
        open: function()
        {
            // open the picker (if we've a parent and if not opened)
            if ( ! this._dialog.open ) {
                // open the dialog containing the picker
                this._dialog.show();
            }
        },

        /**
         * Closes the picker.
         */
        close: function()
        {
            if ( this._dialog.open ) {
                this._dialog.hide();
            }            
        },
        
        /**
         * Returns true if the picker is opened.
         */
        isOpen: function()
        {
            return this._dialog.open;
        }
    }        
);

