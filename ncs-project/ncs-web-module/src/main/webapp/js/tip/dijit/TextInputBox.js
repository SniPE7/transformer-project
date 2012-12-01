dojo.provide("ibm.tivoli.tip.dijit.TextInputBox"); 

dojo.require("dijit._Templated");
dojo.require("ibm.tivoli.tip.dijit.Widget");
dojo.require("ibm.tivoli.tip.dijit.Caret");

/** 
 * A text input box widget.
 * 
 * Such widget displays a text input box which implements
 * the user-provided validations and adapts the look&feel reflecting
 * to its current status.
 * The widget accepts the following parameters:
 * 
 *  - value: the initial value the widget must show when created.
 *  - constraints: an object containing the validation constraints.
 *    The same constraints can be provided as single properties.
 *    A constraint provided as a single property overrides the 
 *    corresponding constraint provided in the constraints object.
 *    The possible constraints are:
 *      
 *      - minlen: (Number) the minimum number of required characters.
 *      - maxlen: (Number) the maximum number of required characters.   
 *      - regexp: (RegExp) a regular expression (object) which must
 *        be matched.
 *      - regexpMessage: (String) the message to show if the regular 
 *        expression has not been matched.
 *      - required: (Boolean) if true the value cannot be empty ("").
 *      - validator: (Function) an external validator function to be 
 *        invoked. The validator accepts the value has parameters and
 *        returns a status object which will affect the widget status.
 *        If null is returned, the validation goes ahead.  
 * 
 *  -  minLength: (String) the same of constraints.minlen.
 *  -  maxLength: (String) the same of constraints.maxlen.
 *  -  regExp: (String) the same of constraints.regexp.
 *  -  regExpMessage: (String) the same of constraints.regexpMessage.
 *  -  required: (String) the same of constraints.required.
 * 
 * Example of a declaration:
 * 
 *       <div 
 *          dojoType="ibm.tivoli.tip.dijit.TextInputBox" 
 *          constraints="{required: true, minlen:5,regexp: /^[0-9]+$/g}"
 *          maxLength="10"/>
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.TextInputBox",
    [ibm.tivoli.tip.dijit.Widget, dijit._Templated],
    {
        /** the initial value */
        value: "",
        /** the constraints */
        constraints: {},
        /** the minlen */
        minLength: "",
        /** the maxlen */
        maxLength: "",
        /** the regular expression */  
        regExp: "",
        /** the regular expression message */
        regExpMessage: "",
        /** required */
        required: "",
        /** the text box type ("text" or "password") */
        type: "text",
        
        /** the size of the input field */
        size: "16",
        
        /** the field name */
        name: '',
        
        /** the widget template path */
        templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/TextInputBox.html"),
        
        /** input box DOM node */
        _inputNode: null,

        /** the widget value */
        _value: null,
        /** the caret */
        _caret: null,

        /**
         * Widget constructor.
         */
        constructor: function()
        {
            /** the widget's constraints */
            this.constraints = {};

            // set the widget constants
            // default icon url
            this.constants.DEFAULT_ICON = dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/images/transparent.gif");                
            // error icon url
            this.constants.ERROR_ICON = dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/images/error.gif");
        },
        
        /**
         * Post mixin properties.
         * Validates attributes.
         */
        postMixInProperties: function()
        {
            // call the superclass's method
            this.inherited(arguments);
            
            // validate attributes
            if ( this.type !== "text" && this.type !== "password" ) {
                this.type = "text";
            }  
            
            // if a name was provided in the markup, use it.
            // if not, use the id
            if( 0 == this.name.length ) {
            	this.name = this.id;   
            }
        },
        
        /**
         * Sets the initial value: this triggers the first validation.
         * The initial value can be externally provided by setting the 
         * value property.  
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited(arguments);
           
            // get parameters
            // get the required
            if ( this.required !== "" ) {
                this.constraints.required = this.required === "true" ? true : false;
            }
            // get the minlen
            if ( this.minLength !== "" ) {
                this.constraints.minlen = parseInt(this.minLength, 10);
            }
            // get the maxlen
            if ( this.maxLength !== "" ) {
                this.constraints.maxlen = parseInt(this.maxLength, 10);
            }          
            // get the regular expression  
            if ( this.regExp !== "" ) {
                this.constraints.regexp = new RegExp(this.regExp);
                console.debug("TestInputBox.startup: Regular expression source =" +this.constraints.regexp.source);
            }
            // get the regular expression message  
            if ( this.regExpMessage !== "" ) {
                this.constraints.regexpMessage = this.regExpMessage;
            }
            // set the default regexp message if no message has been provided
            if ( ! this.constraints.regexpMessage || this.constraints.regexpMessage === "" ) {
                this.constraints.regexpMessage = this._resources.TEXT_INVALID_CHARS;
            }
            
       
            if( this.constraints.size !== undefined ){
              this.constraints.size = this.size;
              this._inputNode.size = this.size;
            }
            
            // create the caret
            this._caret = new ibm.tivoli.tip.dijit.Caret(this._inputNode);
            
            // get the value
            this.setValue( this.value || "" );
            
            if(this.isRequired()){
            	console.log("required: " + this.id);
            	this.altText.innerHTML = "*";
            }
        },
        
        /**        
         * Returns an object which represent the widget value.
         */
        getValue: function()
        {
            // return the value
            return this._value;
        },
        
        /**        
         * Sets the widget value.
         */
        setValue: function(value)
        {
            // store the internal value
            this._value = value;
            // update the input box
            this._inputNode.value = value;
            // validate
            this._validate();
        },
        
        /**
         * Returns true if the widget's value is not set.
         */
        isEmpty: function()
        {
            return ( this._value === "" );
        },

        /**
         * Enables the widget for editing. 
         */
        setDisabled: function(isDisabled)
        {
            if ( isDisabled != this.isDisabled() ) {
                // update the enablement status
                this.inherited(arguments);
                // update the widget
                this._updateWidget();
            }
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
            // update the status
            this.inherited(arguments);
            // update the widget
            this._updateWidget();
        },
        
        /**
         * Sets the widget required constraint.
         * 
         *  @param required:
         *      true if the value is required, false otherwise.
         */
        setRequired: function(required)
        {
            if ( this.constraints.required != required ) {
                // store the requried constraint
                this.constraints.required = required;
                // trigger the validation
                this._validate();
            }
        },
        
        /**
         * Returns true if the widget's value is required.
         */
        isRequired: function()
        {
            return this.constraints.required;
        },
        
        /**
         * Asks the widget to revalidate its value and to set its status accordingly.
         */
        validate: function() 
        {
        	this._validate();
        },

        /**
         * Gives the focus to the widget.
         */
        setFocus: function()
        {
            this._inputNode.focus();
        },
        
        /**
         * Returns the caret position.
         */
        getCaretPosition: function()
		{
			return this._caret.getPosition();
		},
		
		/**
         * Sets the caret position.
         */
		setCaretPosition: function(index)
		{
		    this._caret.setPosition(index);
		},
		        
        /**
         * Handles the onkeypress event. Prevents container to catch
         * the key pression. 
         */
        _onKeyPress: function(e)
        {
            // let the enter and tab to be propagated
            if ( e.keyCode !== 13 && e.keyCode !== 9 &&
            		e.keyCode!=dojo.keys.F1 && e.keyCode!=dojo.keys.ESCAPE) {
                // stop event propagation
                e.stopPropagation();
            }
        },
        
        /**
         * Handles the onkeyup event. The internal value is updated and
         * the validation is triggered. 
         */
        _onKeyUp: function(e)
        {
            // get the value from the input text box
            this._value = this._inputNode.value;
            // trigger the validation
            this._validate();
            // stop event propagation
            e.stopPropagation();
        },
        
        /**
         * Handles the onchange event.  
         */
        _onChange: function()
        {
            // notify the change
            this.onValueChanged(this.getValue());
        },
         
        /**
         * Handles the onclick event.  
         */        
        _onClick: function(e)
        {
			if ( e.target.tagName === "SPAN" ) {
				this.setCaretPosition(this._inputNode.value.length); 	
			}
        },
        
        /**
         * Validates the current value applying all the user-provided
         * constraints. The status is then updated.
         */
        _validate: function()
        {
            // check if we've some constraints 
            if ( this.constraints !== {} ) {
                // set the widget in error, eventually reset ahead in case of no errors  
                this._isInError = true;
                
                // check if the field is required
                if( this.constraints.required && this._value === "" ) {
                    this.setStatus({ 
                        state: this.constants.REQUIRED, 
                        message: ""  
                    });
                    return;
                }
                                
                // check the min length (if any)
                if ( this.constraints.minlen !== undefined ) {
                    if ( this._value.length < this.constraints.minlen ) {
                        this.setStatus({ 
                            state: this.constants.ERROR, 
                            message: dojo.string.substitute(this._resources.TEXT_MIN_LIMIT, {minlen: this.constraints.minlen})
                        });
                        return;
                    }
                }
                
                // check the max length (if any)
                if ( this.constraints.maxlen !== undefined ) {
                    if ( this._value.length > this.constraints.maxlen ) {
                        this.setStatus({ 
                            state: this.constants.ERROR,
                            message: dojo.string.substitute(this._resources.TEXT_MAX_LIMIT, {maxlen: this.constraints.maxlen})
                        });
                        return;
                    }
                }
                
                // match the regex (if any)
                if ( this.constraints.regexp !== undefined ) {
                    if ( ! this._value.match(this.constraints.regexp) ) {   
                        this.setStatus({ 
                            state: this.constants.ERROR,
                            message: this.constraints.regexpMessage  
                        });
                        return;
                    }
                }
                
                // call the external validator (if any)
                if ( this.constraints.validator !== undefined ) {
                    var status = this.constraints.validator(this._value);
                    if ( status ) {
                        this.setStatus(status);
                        return;
                    }
                }  
            }
                        
            // if we've reached this point, no error is present
            this._isInError = false;
            this.setStatus({ 
                state: this.constants.DEFAULT, 
                message: ""  
            });
        },
        
        /**
         * Updates the widget look&feel depending on the widget' status.
         */
        _updateWidget: function()
        {
            this._inputNode.disabled = this.isDisabled();
            this._inputNode.size = this.size;
  
            if ( this.isDisabled() ) { 
                // the widget is disabled
                this.domNode.className = "tip-tib-disabled";
            }
            else {  
                // the widget is enabled
                var status = this.getStatus();
                
                if( status.state === this.constants.ERROR ) {
                    this.domNode.className = "tip-tib-error";
                }
                else if( this.isRequired() ) {
                    this.domNode.className = "tip-tib-required";                    
                }
                else {
                   this.domNode.className = "tip-tib-normal";      
                }
            }
        }
    }
);

