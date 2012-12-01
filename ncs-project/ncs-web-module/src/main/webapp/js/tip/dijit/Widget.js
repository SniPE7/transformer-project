dojo.provide("ibm.tivoli.tip.dijit.Widget"); 

dojo.require("dijit._Widget");
dojo.require("ibm.tivoli.tip.dijit.Tooltip");
dojo.require("ibm.tivoli.tip.dijit.Images");
dojo.requireLocalization("ibm.tivoli.tip.dijit", "resources");

/**
 * Widget base class. 
 * 
 * Provides the basic functionalities for a widget and defines 
 * some template methods that must be implemented by inheriting widgets.
 * 
 * The basic functionalities provided by such class are:
 *   
 *      - resources: the labels catalog is already loaded and can be accessed
 *        using the (protected) _resources property.
 *      - tooltip message: the widget shows a tooltip message depending 
 *        on the widget status. Inheriting widgets can specify a DOM node 
 *        (_tooltipNode) which will be used to show the tooltip. If not
 *        specified the widget root DOM node is used.
 *      - status: the widget L&F can be set using the setStatus method.
 *      - focus/blur: inheriting widgets can be notified by focus/blur
 *        events by overriding onFocus/onBlur methods.
 *      - focus node: inheriting widgets can specify a DOM node (_focusNode)
 *        which be used to determine if the widget has got or lost the focus.
 *        If not specified the widget root DOM node is used.
 *      - disabling: the widget can be disabled/enabled by calling the 
 *        setDisabled method. Inheriting widgets can override this method
 *        to add their behaviour.
 * 
 * The template methods are:
 * 
 *      - getValue: returns the widget value
 *      - setValue: sets the widget value
 *      - isEmpty: returns true if the widget's value is empty
 *      - setRequired: sets required the widget's value 
 *      - isRequired: returns true if the widget's value is required
 *      - onValueChanged: called when the widget's value is changed
 *      - onStatusChanged: called when the widget's status is changed
 *      - onFocus: called when the widget has got the focus
 *      - onBlur: called when the widget has lost the focus
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.Widget",
    dijit._Widget,
    {
        /** widget constants */
        constants: {
            // default view state
            DEFAULT:  0,   
            // required view state
            REQUIRED: 1,   
            // error view state
            ERROR:    2
        },

        /** editable attribute */
        editable: "true",
        /** true if the tooltip is shown if needed */
        useTooltip: "true", 
        
        /** true if the widget can be editable */
        _editable: true,
        /** the node which gives the focus to the whole widget */
        _focusNode: null,
        /** the node used to attach the tooltip message */
        _tooltipNode: null,
        /** true if the widget has focus */
        _hasFocus: false,
        /** the widget message (tooltip) */
        _tooltip: null,       
        /** the widget display status */
        _status: null,
        /** true if the widget is disabled */
        _disabled: false,
        /** widget connections */
        _connections: null,
        /** true if the widget value is wrong */
        _isInError: false,
        /** the focus node tab index */
        _tabIndex: 0,
        /** resources */
        _resources: null,
        /** images */
        _images: null,
       
        /**
         * Constructor
         */
        constructor: function()
        {
            // get the labels
            this._resources = dojo.i18n.getLocalization("ibm.tivoli.tip.dijit", "resources");
            // get the image
            this._images = ibm.tivoli.tip.dijit.Images.get();
        },
        
        /**
         * Starts-up the widget.
         */
        startup: function()
        {
            // call the superclass's method
            this.inherited(arguments);
            
            // create the tooltip message
            this._tooltip = new ibm.tivoli.tip.dijit.Tooltip({enabled:this.useTooltip === "true"});
            this._tooltip.startup();
            this._tooltip.setNode(this._tooltipNode || this.domNode);
           
            // set default widget status
            this.setStatus({ state: this.constants.DEFAULT, message: "" }); 

            // set the focus node
            if ( this._focusNode === null ) {
                // no focus node provided, use the root node
                this._focusNode = this.domNode;
            }
            // get the focus node tabindex
            this._tabIndex = this._focusNode.tabIndex;
            
            // connect callbacks
            this._connections = [];
            this._connections[this._connections.length] = dojo.connect(this._focusNode, "onfocus", this, "_onFocusEvent");
            this._connections[this._connections.length] = dojo.connect(this._focusNode, "onblur", this, "_onBlurEvent");
            
            // set the editable state
            if ( this.editable !== "" ) {
                this.setEditable(this.editable === "true" ? true : false);    
            }            
        },
        
        /**
         * Destroys the widget.
         */
        destroy: function()
        {
            // destroy the tooltip message
            this._tooltip.destroy();
            // disconnect callbacks
            for ( var i = 0; i < this._connections.length; i++ ) {
                dojo.disconnect(this._connections[i]);
            }
            // call the superclass's method
            this.inherited(arguments);
        },
        
        /**
         * Enables/disables the widget. 
         */
        setDisabled: function(isDisabled)
        {
            if ( isDisabled != this._disabled ) {
                // update the message
                if ( isDisabled === true ) {
                    // the widget is disabled, hide the message
                    this._tooltip.hide();
                }
                else if ( this._status.state !== this.constants.DEFAULT && this._hasFocus ) {
                    // the widget is in error and has focus                    
                    this._tooltip.show(this._status.message);
                }
                // store the enablement status
                this._disabled = isDisabled;
            }
        },
        
        /**
         * Returns true if the object is disabled.
         */
        isDisabled: function()
        {
            return this._disabled;
        },
        
        /**
         * Enables the widget for editing. 
         */
        setEditable: function(isEditable)
        {
            // store the editable status
            this._editable = isEditable;
            // update the focus node tabindex according to the editable state
            this._focusNode.tabIndex = isEditable ? this._tabIndex : -1;
            // manage the new status
            if ( this._hasFocus && isEditable === false ) {
                // remove the focus
                this._focusNode.blur();
            }
            else if ( isEditable ){
                // close the tooltip 
                this._tooltip.hide();
            }
        },
        
        /**
         * Returns true if the object is editable.
         */
        isEditable: function()
        {
            return this._editable;
        },
        
        /**
         * Sets the widget status: the status is an object with the
         * following two properties:
         * 
         *  - state: the state of the widget (e.g. constants.ERROR)
         *  - message: the message related to the current state.
         */
        setStatus: function(status)
        {
            
            // update the message
            if ( status.state === this.constants.DEFAULT ) {
                // no error in place, hide the message
                this._tooltip.hide();
            }
            else if ( this._hasFocus ) {
                // there is an error, show the message if no message
                // is currently shown or the currently shown message
                // is different from the current one 
                if ( ! this._tooltip.isShown() || status.message !== this._status.message ) {
                    this._tooltip.show(status.message);
                }                      
            }
            // cache old status
            var oldStatus = this._status;
            // set the status            
            this._status = status;
            // notify status changed
            if ( oldStatus && oldStatus.state !== status.state ) {
                this.onStatusChanged(status);    
            }
        },
        
        /**
         * Returns the status object.
         */
        getStatus: function()
        {
            return this._status;
        },
        
        /**        
         * Returns an object which represent the widget value.
         */
        getValue: function()
        {
        },
        
        /**        
         * Sets the widget value.
         */
        setValue: function(value)
        {
        },
        
        /**
         * Returns true if the widget's value is not set.
         */
        isEmpty: function()
        {
        },

        /**
         * Returns true if the widget's value is wrong.
         */
        isInError: function()
        {
            return this._isInError && ! this._disabled;  
        },

        /**
         * Sets the widget required constraint.
         * 
         *  @param required:
         *      true if the value is required, false otherwise.
         */
        setRequired: function(required)
        {
        },
        
        /**
         * Returns true if the widget's value is required.
         */
        isRequired: function()
        {
            return false;
        },
        
        /**
         * Asks the widget to revalidate its value and to set its status accordingly.
         */
        validate: function() 
        {
        },
        
        /**
         * Called by the widget if the widget looses the focus and the 
         * value has changed.
         * 
         *  @param value:
         *      the widget's value (same returned by getValue()). 
         */
        onValueChanged: function(value)
        {
        },
        
        /**
         * Called by the widget if the widget's status changes.
         * 
         *  @param status:
         *      the widget's status (same returned by getStatus()). 
         */
        onStatusChanged: function(status)
        {
        },
        
        /**
         * Returns true if the widget has the focus.
         */
        hasFocus: function()
        {
            return this._hasFocus;    
        },
        
        /**
         * Gives the focus to the widget.
         */
        setFocus: function()
        {
            this.domNode.focus();
        },
         
        /**
         * Called by the widget if the widget has got the focus.
         */
        onFocus: function()
        {
        },
        
        /**
         * Called by the widget if the widget has lost the focus.
         */
        onBlur: function()
        {
        },
        
        /**
         * Sets the tooltip node.
         */
        setTooltipNode: function(node)
        {
            this._tooltip.setNode(node || this.domNode);    
        },        
        
        /**
         * Handles the onfocus event.
         */
        _onFocusEvent: function()
        {
            if ( this._editable ) {
                // the widget is editable give the focus
                this._hasFocus = true;
                // show the message
                this._tooltip.show(this._status.message);
                // notify the event to the superclass
                this.onFocus();
            }
            else {
                // the widget is not editable don't give the focus
                this._tooltip.splash(this._resources.NOT_EDITABLE);
                this._focusNode.blur();
            }
        },
        
        /**
         * Handles the onblur event.
         */
        _onBlurEvent: function()
        {
            this._hasFocus = false;
            if ( this._editable ) {
                // hide the message
                this._tooltip.hide();
                // notify the event to the superclass
                this.onBlur();    
            }
        }
    }
);