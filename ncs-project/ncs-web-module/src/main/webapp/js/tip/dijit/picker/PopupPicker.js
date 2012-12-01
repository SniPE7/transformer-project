dojo.provide("ibm.tivoli.tip.dijit.picker.PopupPicker"); 

dojo.require("ibm.tivoli.tip.dijit.picker.Picker"); 

/**
 * A pop-up picker base class. 
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.picker.PopupPicker",
    [ibm.tivoli.tip.dijit.picker.Picker],
    {
        /** true if the picker is opened */
        _pickerOpened: false,
        /** the widget used to place the popup-picker */
        _parent: null,

        /**
         * Constructor.
         * 
         *      @param params: a set of parameters:
         *          - parent: the widget used to place the popup-picker.
         */
        constructor: function(params)
        {
            this._parent = params.parent;
        },
                       
        /**
         * Opens the picker.
         */
        open: function()
        {
            // open the picker (if we've a parent and if not opened)
            if ( this._parent && ! this._pickerOpened ) {
                // open a popup containing the picker
                dijit.popup.open({
                    parent: this.parent,
                    popup: this,
                    around: this._parent.domNode,
                    onCancel: dojo.hitch(this, this.close),
                    onClose: dojo.hitch(this, function(){ this._pickerOpened = false; })
                });
                this._pickerOpened = true;
                dojo.marginBox(this.domNode, { w: this.parent.domNode.offsetWidth });
            }
        },

        /**
         * Closes the picker.
         */
        close: function()
        {
            if ( this._pickerOpened ) {
                dijit.popup.close(this);
                this._pickerOpened = false;
            }            
        },
        
        /**
         * Returns true if the picker is opened.
         */
        isOpen: function()
        {
            return this._pickerOpened;
        }
    }        
);

