dojo.provide("ibm.tivoli.tip.dijit.TextArea"); 

dojo.require("dijit._Templated");
dojo.require("dijit.Tooltip");
dojo.require("ibm.tivoli.tip.dijit.Widget");

/** 
 * A Text Area widget.
 * 
 * Such widget displays a text area.
 * A limit on the number of characters to be accepted can be speficied.
 * If such limit is passed a warning message is shown, but the user
 * can still insert them.
 *   
 * The widget accepts the following parameters:
 * 
 *  - value: the initial value the widget must show when created.
 *  - constraints: an object containing the validation constraints.
 *    The same constraints can be provided as single properties.
 *    A constraint provided as a single property overrides the 
 *    corresponding constraint provided in the constraints object.
 *    The possible constraints are:
 *      
 *      - maxlen: (Number) the maximum number of characters.   
 *      - maxLength: (String) the same of constraints.maxlen.
 *      - rows: (String) the number of rows to display.
 *      - cols: (String) the number of coloumns to display.
 * 
 * Example of a declaration:
 * 
 *       <div 
 *          dojoType="ibm.tivoli.tip.dijit.TextArea" 
 *          maxLength="100"
 *          rows="10"
 *          cols="50"/>
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.TextArea",
    [ibm.tivoli.tip.dijit.Widget, dijit._Templated],
    {
        /** the widget template path */
        templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/TextArea.html"),
        /** attribute map */
        attributeMap: dojo.mixin(
            dojo.clone(dijit._Widget.prototype.attributeMap),
            {cols: "_textAreaNode", rows: "_textAreaNode"}),
        
        /** the initial value */
        value: "",
        /** the constraints */
        constraints: null,
        /** the maxlen */
        maxLength: "",
        /** number of visible rows for the text area */  
        rows: "10",
        /** number of visible columns for the text area */
        cols: "30",
        
        /** text area DOM node */
        _textAreaNode: null,
        /** message DOM node */
        _messageNode: null,

        /**
         * Widget constructor.
         */
        constructor: function()
        {
            /** the widget's constraints */
            this.constraints = {};
        },
        
        /**
         * Sets the initial value.
         * The initial value can be externally provided by setting the 
         * value property.  
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited("startup", arguments);

            // get the maxlen
            if ( this.maxLength !== "" ) {
                this.constraints.maxlen = parseInt(this.maxLength, 10);
            }          
            // set the value
            this.setValue( this.value || "" );
        },

        /**        
         * Returns an object which represent the widget value.
         */
        getValue: function()
        {
            // return the value
            return this._textAreaNode.value;
        },
        
        /**        
         * Sets the widget value.
         */
        setValue: function(value)
        {
            // update the input box
            this._textAreaNode.value = value;
            // update the counter
            this._updateCounter();
        },
        
        /**
         * Returns true if the widget's value is not set.
         */
        isEmpty: function()
        {
            return ( this._textAreaNode.value === "" );
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
                if ( isDisabled === true ) {
                    // the widget must be disabled
                    this._textAreaNode.disabled = true;
                    this._textAreaNode.className = "textAreaDisabled";
                    this._messageNode.className = "textAreaMessageDisabled";
                }
                else {
                    // the widget must be enabled
                    this._textAreaNode.disabled = false;
                    this._textAreaNode.className = "textAreaDefault";
                    this._messageNode.className = "textAreaMessageDefault";
                }
            }
        },

        /**
         * Clear the message text value.
         */
        clear: function()
        {
            this.setValue("");
        },
        
        /**
         * Handles the onkeypress event. Prevents container to catch
         * the key pression. 
         */
        _onKeyPress: function(e)
        {
            // let the tab to be propagated
            if ( e.keyCode !== 9 ) {
                // stop event propagation
                e.stopPropagation();
            }
        },
        
        /**
         * Handles the onkeypress event. The internal value is updated and
         * the validation is triggered. 
         */
        _onKeyUp: function(e)
        {
            // update the counter
            this._updateCounter();
            // stop event propagation
            e.stopPropagation();
        },
        
        /**
         * Handles the onchange event.  
         */
        _onChange: function()
        {
            // notify the change
            this.onValueChanged(this._textAreaNode.value);
        },
        
        /**
         * Handles the onfocus event.  
         */
        onFocus: function()
        {
            this._updateCounter();
        },
        
        /**
         * Updates the text area counter and shows the tooltip message 
         * according to the maxlen constraint.
         */
        _updateCounter: function()
        {
            var maxlen = this.constraints.maxlen;
            if ( ! maxlen ) {
                // there is no contraints on the max length
                return;
            }
            
            // update the counter
            var len = this._textAreaNode.value.length;
            var left = maxlen - len;
            left = left > 0 ? left : 0; 
            this._messageNode.innerHTML = dojo.string.substitute(this._resources.TEXTAREA_MESSAGE, {len: len, left: left});

            // show/hide the tooltip          
            if ( left === 0 ) {
                this.setStatus({ 
                    state: this.constants.ERROR,
                    message: dojo.string.substitute(this._resources.TEXTAREA_MAX_LIMIT, {maxlen: maxlen})
                });                
            }
            else if ( left > 0 ){
               this.setStatus({ 
                    state: this.constants.DEFAULT,
                    message: ""
                });                
            }      
        }
    }
);

