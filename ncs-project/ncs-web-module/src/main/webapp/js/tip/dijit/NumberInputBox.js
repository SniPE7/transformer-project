dojo.provide("ibm.tivoli.tip.dijit.NumberInputBox"); 

dojo.require("ibm.tivoli.tip.dijit.TextInputBox");

/** 
 * A number input box widget.
 * 
 * Such widget displays a text input box which accepts only a valid 
 * number.
 * The widget accepts the following parameters:
 * 
 *  - value: the initial value the widget must show when created.
 *    An empty string means NaN.
 *  - constraints: an object containing the validation constraints.
 *    The same constraints can be provided as single properties.
 *    A constraint provided as a single property overrides the 
 *    corresponding constraint provided in the constraints object.
 *    The possible constraints are:
 *      
 *      - min: (Number) the minimum value.
 *      - max: (Number) the maximum number.   
 *      - required: (Boolean) if true the value cannot be empty (NaN).
 * 
 *  -  min: (String) the same of constraints.min.
 *  -  max: (String) the same of constraints.max.
 *  -  required: (String) the same of constraints.required.
 * 
 * Example of a declaration:
 * 
 *       <div 
 *          dojoType="ibm.tivoli.tip.dijit.NumberInputBox" 
 *          constraints="{required: true, min:5}"
 *          max="10"/>
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.NumberInputBox",
    [ibm.tivoli.tip.dijit.TextInputBox],
    {
        /** the initial value (an empty string means "not a number")*/
        value: "",
        /** the minimum number */
        min: "",
        /** the maximum number */
        max: "",
        /** the constraints */
        constraints: {},
        
        /**
         * Sets the initial value: this triggers the first validation.
         * The initial value can be externally provided by setting the 
         * value property.  
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited("startup", arguments);

            // set the regular expression (only number accepted)  
            this.constraints.regexp = /^[\-]?[0-9]*$/g;
            // set the regex message for the text
            this.constraints.regexpMessage = this._resources.NUMBER_INVALID_VALUE;
            
            // get parameters
            var decimalNumber = /^[\-]?[0-9]*[,.]?[0-9]+$/g;
            // get the min
            if ( this.min.match(decimalNumber) ) {
                this.constraints.min = parseInt(this.min, 10);
            }
            // get the max
            if ( this.max.match(decimalNumber) ) {
                this.constraints.max = parseInt(this.max, 10);
            }
            // set the validator function
            this.constraints.validator = dojo.hitch(this, this._validator);

            // set the value
            var n;
            if ( this.value.match(decimalNumber) ) {
                // the passed value is a number
                n = parseInt(this.value, 10);
            }
            else {
                n = NaN;
            }
            this.setValue(n);
        },
        
        /**        
         * Returns the value (a number)
         */
        getValue: function()
        {
            // get the text value
            var value = this.inherited("getValue", arguments);
            // convert to a numeric value
            var n = NaN;
            if ( value.match(this.constraints.regexp) ) {
                // the value is a number
                n = parseInt(value, 10);
            }
            return n;
        },
        
        /**        
         * Sets the value.
         * 
         *  @param value:
         *      The value (a number) to be set.
         */
        setValue: function(value)
        {
            if ( isNaN(value) ) {
                value = "";
            }
            else {
                value = "" + value;
            }
            this.inherited("setValue", arguments);
        },
        
        /**
         * Returns true if the widget's value is not set.
         */
        isEmpty: function()
        {
            return isNaN(this.getValue());
        },
        
        /**        
         * Returns the text representation of the value (a String)
         */
        getTextValue: function()
        {
            // return the text value
            return ibm.tivoli.tip.dijit.NumberInputBox.superclass.getValue.call(this);
        },
        
        /**
         * The range validator.
         */
        _validator: function()
        {
            // get the value
            var n = this.getValue();

            // check the max constraint
            if ( this.constraints.max !== undefined && n > this.constraints.max ) {
                // the value is greater than max 
                return { state: this.constants.ERROR, message: dojo.string.substitute(this._resources.NUMBER_MAX_LIMIT, {max: this.constraints.max}) };    
            }
            // check the min constraint
            if ( this.constraints.min !== undefined && n < this.constraints.min ) {
                // the value is lower than min
                return { state: this.constants.ERROR, message: dojo.string.substitute(this._resources.NUMBER_MIN_LIMIT, {min: this.constraints.min}) };    
            }
            // - and -0 not allowed
            var s = this.getTextValue();
            if ( s && ( s === "-" || s.indexOf("-0") === 0 ) ) {
                return { state: this.constants.ERROR, message: this._resources.NUMBER_INVALID_VALUE };
            }
            
            // return no status, to permit the validation to go ahead
            return null;
        }
    }
);

