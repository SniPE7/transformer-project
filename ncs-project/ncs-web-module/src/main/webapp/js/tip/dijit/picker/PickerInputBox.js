dojo.provide("ibm.tivoli.tip.dijit.picker.PickerInputBox"); 

dojo.require("dijit._Templated");
dojo.require("ibm.tivoli.tip.dijit.TextInputBox"); 
dojo.require("ibm.tivoli.tip.dijit.Widget");

/** 
 * A text input box with a picker on the right.
 * 
 * Such widget is the base for specific picker implementations.
 * An actual picker must inherit from such widget and set
 * the following attributes and attach points:
 * 
 *      - pickerIcon: the picker icon.
 *      - textMinLength: (String) the minimum number of required characters.
 *      - textMaxLength: (String) the maximum number of required characters.
 *      - textRegExp: a regular expression used to validate the text
 *        displayed in the text input box.
 *      - setPicker: must be used to provide the picker object. 
 *      - getPickerStartValue: must be overridden if the inheriting
 *        class want provide a specific start value to show when the 
 *        picker is popped-up.
 *      - getPickerTextValue: must be overridden by the inheriting
 *        class in order to get a textual representation from an actual
 *        value (which could not be a String but an Object).
 *      - onTextValueChanged: must be overridden by the inheriting
 *        class in order to get notified about changes happened in the 
 *        text input box.
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.picker.PickerInputBox",
    [ibm.tivoli.tip.dijit.Widget, dijit._Templated],
    {
        /** the initial value */
        value: "",
        /** required (String) */
        required: "false",
        /** the picker icon */
        pickerIcon: "",
        /** the minlen */
        textMinLength: "",
        /** the maxlen */
        textMaxLength: "",
        /** regular expression for validation on the text value */
        textRegExp: "",
        /** the message to show if the regular expression is not matched */
        textRegExpMessage: "",        
        /** the tab index */
        tabindex: "0",

        /** the widget contains other widget */        
        widgetsInTemplate: true,
        /** the widget template path */
        templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit.picker", "templates/PickerInputBox.html"),

        /** the text node */
        _textNode: null,
        /** the picker button node */
        _pickerButtonNode: null,
        /** the picker widget */
        _picker: null,
        /** true if the picker is opened */
        _pickerOpened: false,
        /** true if the picker button must not open the picker  */
        _pickerIconDisabled: false,
        
        /**
         * Sets the initial value.  
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited("startup", arguments);
            // set the value
            this.setValue(this.value);
            // attach the text "on value changed" event
            this._textNode.onValueChanged = dojo.hitch(this, this.onTextValueChanged);
            // change the tooltip node
            this._textNode.setTooltipNode(this.domNode);
        },
        
        /**
         * Destroys the widget.
         */
        destroy: function()
        {
            // destroy the picker if any
            if ( this._picker ) {
                this._picker.destroy();
            }            
            // call the superclass's method
            this.inherited("destroy", arguments);
        },
                
        /**
         * Sets the picker widget.
         * 
         *      @param picker
         *          The picker widget.
         */
        setPicker: function(picker)
        {
            // destroy the previous picker if any
            if ( this._picker ) {
                this._picker.destroy();
            }
            
            // set-up the picker
            this._picker = picker;
            if ( this._picker ) {
                // hitch the "onValueSelected" callback
                this._picker.onValueSelected = dojo.hitch(this, this._onPickerValueSelected);
                // set the picker value                    
                this._picker.setValue(this.getPickerStartValue());
            }            
        },
        
        /**        
         * Disables/enables the picker button.
         * 
         *  @param disable: (Boolean) if true the picker button is disabled.
         */
        disablePickerIcon: function(disabled)
        {
            this._pickerIconDisabled = disabled;
        },
        
        /**        
         * Returns an object which represent the widget value.
         */
        getValue: function()
        {
            // return the value from the text box
            return this._textNode.getValue();
        },
        
        /**        
         * Sets the widget value.
         */
        setValue: function(value)
        {
            // update the text box value
            this._textNode.setValue(value);
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
                this._textNode.setDisabled(isDisabled);
            }
        },
                
        /**
         * Returns true if the widget's value is wrong.
         */
        isInError: function()
        {
            return this._textNode.isInError();
        },
        
        /**
         * Sets the widget required constraint.
         * 
         *  @param required:
         *      true if the value is required, false otherwise.
         */        
        setRequired: function(required)
        {
            this._textNode.setRequired(required);
        },

        /**
         * Returns if the the widget required constraint is required, false otherwise.
         */        
        isRequired: function(required)
        {
        	return this._textNode.isRequired();
        },

        /**
         * Returns the picker start value.
         */
        getPickerStartValue: function()
        {
            return this.getValue();  
        },

        /**
         * Returns the picker text value.
         */
        getPickerTextValue: function(value)
        {
            return value.toString();  
        },
        
        /**
         * Gives the focus to the widget.
         */
        setFocus: function()
        {
            this._textNode.setFocus();
        },
        
        /**
         * Handles a change called if the text value has changed.
         * 
         *      @param value (String)
         *          the text value.
         */        
        onTextValueChanged: function(value)
        {
        },    
         
        /**
         * Opens the picker if closed, closes the picker if opened.
         */
        _onKeyUp: function(e)
        {
            if ( e.target === this._pickerButtonNode && e.keyCode === 32 ) {
                // manage like an onclick event
                this._onClick();
                // stop event propagation
                e.stopPropagation();
            }
        },
               
        /**
         * Opens the picker if closed, closes the picker if opened.
         */
        _onClick: function()
        {
		    // if disabled, skip
		    if ( this._pickerIconDisabled || this.isDisabled() ) {
		        return;
		    }
		    // if the picker ig going to be opened, set its start value
		    if ( ! this._picker.isOpen() ) {
                var value = this.getValue();
                if ( value ) {
                    this._picker.setValue(this.getValue());
                }
		    }
		    // toggle the picker
            this._picker.toggle();
        },
        
        /**
         * Handles the "picker value selected" event.
         * 
         *      @param value (Object):
         *          The value provided by the specific picker implementation.
         *          Its text form is asked to the picker implementation.
         */        
        _onPickerValueSelected: function(value)
        {
            // close the picker
            this._picker.close();
            // set the text value
            this._textNode.setValue(this.getPickerTextValue(value));
            // because the text box has not focus, it will not automatically
            // call the onChangeValue callback, do it
            this.onTextValueChanged(this._textNode.getValue());
        },

        /**
         * Handles onblur event.
         */
        _onBlur: function()
        {
            this._picker.close();
        }        
    }        
);

