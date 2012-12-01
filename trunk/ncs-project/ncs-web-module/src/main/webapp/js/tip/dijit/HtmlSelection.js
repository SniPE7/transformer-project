dojo.provide("ibm.tivoli.tip.dijit.HtmlSelection"); 

dojo.require("dijit._Templated");
dojo.require("ibm.tivoli.tip.dijit.Widget");

/** 
 * Base class for HTML selection widget (check box and radio button).
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)   
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.HtmlSelection",
    [ibm.tivoli.tip.dijit.Widget, dijit._Templated],
    {
        /** the widget template path */
        templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/HtmlSelection.html"),
        
        /** HTML name attribute */
        name: "",
        /** HTML checked attribute */
        checked: "false",
        
        /** checkbox DOM node */
        _htmlSelectionNode: null,

        /**
         * Sets the initial value.
         * The initial value can be externally provided by setting the 
         * checked property.  
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited("startup", arguments);

            // check the checked attribute
            var value = false;
            if ( this.checked === "" || this.checked === "checked" || this.checked === "true" ) {
                value = true;    
            }
            else {
                value = false;
            }
            
            // set the value
            this.setValue(value);
        },
        
        /**        
         * Returns an object which represent the widget value.
         */
        getValue: function()
        {
            return this._htmlSelectionNode.checked; 
        },
        
        /**        
         * Sets the widget value.
         */
        setValue: function(checked)
        {
            // update the selection
            this._htmlSelectionNode.checked = checked; 
            // need for IE > 6.x
            this._htmlSelectionNode.defaultChecked = checked;
        },
        
        /**
         * Returns true if the widget's value is not set.
         */
        isEmpty: function()
        {
            return ( this.getValue() === false );
        },

        /**
         * Enables/disables the widget. 
         */
        setDisabled: function(isDisabled)
        {
            if ( isDisabled != this.isDisabled() ) {
                // update the enablement status
                this.inherited("setDisabled", arguments);
                // update the widget
                if ( isDisabled === true ) {
                    // the widget must be disabled
                    this._htmlSelectionNode.disabled = true;
                    this._htmlSelectionNode.className = "htmlSelectionDisabled";
                }
                else {
                    // the widget must be enabled
                    this._htmlSelectionNode.disabled = false;
                    this._htmlSelectionNode.className = "htmlSelectionDefault";
                }
            }
        },
        
        /**
         * Handles the onclick event.
         */
        _onClick: function(e)
        {
            if ( this.isEditable() ) {
                // notify the change
                this.onValueChanged(this.getValue());
            }
            else {
                // the control is not editable, prevent the change
                // cancel and stop event propagation
                e.preventDefault();
                e.stopPropagation();                
            }
        }
    }
);

