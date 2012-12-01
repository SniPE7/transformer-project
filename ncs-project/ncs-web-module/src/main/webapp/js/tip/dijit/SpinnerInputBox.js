dojo.provide("ibm.tivoli.tip.dijit.SpinnerInputBox"); 

dojo.require("ibm.tivoli.tip.dijit.Images");
dojo.require("ibm.tivoli.tip.dijit.NumberInputBox");

/** 
 * A number input box widget with spinner arrows.
 * 
 * Such widget shows a number input box with two spinner buttons
 * on the right. The buttons can be used to increment (up-arrow)
 * or decrement (down-arrow) of a delta the current set value.
 * 
 * The widget accepts the following parameters:
 * 
 *  - value: the initial value the widget must show when created.
 *  - required: (String) if true the value cannot be empty (NaN).
 *  - min: (String) the minimum value.
 *  - max: (String) the maximum value.
 *  - smallDelta: (String) the delta used to increment or decrement
 *    the value when the corresponding arrow is pressed. 
 * 
 * Example of a declaration:
 * 
 *      <div  
 *          dojoType="ibm.tivoli.tip.dijit.SpinnerInputBox"
 *          required="true"
 *          smallDelta="5"
 *          value="10"
 *          min="5"
 *          max="100"/>
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.SpinnerInputBox",
    [ibm.tivoli.tip.dijit.Widget, dijit._Templated],
    {
        /** the initial value (an empty string means "not a number") */
        value: "",        
        /** required */
        required: "false",
        /** the tab index */
        tabindex: "0",
        /** the minimum number */
        min: "",
        /** the maximum number */
        max: "",        
        /** the message to show if the regular expression is not matched */
        textRegExpMessage: null,        
        /** adjust the value by this much when spinning using the arrow keys/buttons */
        smallDelta: "1",
        
        /** the widget contains other widget */        
        widgetsInTemplate: true,
        /** the widget template path */
        templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/SpinnerInputBox.html"),

        /** the text node */
        _textNode: null,
        /** the up-arrow button node */
        _upArrowNode: null,
        /** the down-arrow button node */
        _downArrowNode: null,
        /** the up-arrow icon */
        _upArrowIcon: ibm.tivoli.tip.dijit.Images.get()["UP_ARROW_ICON"],
        /** the down-arrow icon */
        _downArrowIcon: ibm.tivoli.tip.dijit.Images.get()["DOWN_ARROW_ICON"],
        
        /** the auto-click slowest delay (milliseconds) */
        _autoClickMaxDelay: 300,
        /** the auto-click fastest delay (milliseconds) */
        _autoClickMinDelay:  25,
        /** the auto-click delay adjustment: auto-click becomes faster with time if this value is > 0, use 0 to disable */
        _autoClickDelayAdjustment: 35,
        /** the auto-click current delay, only valid when auto-clicking */
        _autoClickDelay: 0,
        /** the auto-click current delta, or zero if no auto-clicking is in progress */
        _autoClickDelta: 0,
        /** the auto-click callback */
        _autoClickCallback: null,
        /** the auto-click callback handle (obtained via setTimeout) */
        _autoClickHandle: null,
        /** true if an arrow is currently pushed */
        _isArrowPushed: false,

        
        /**
         * Sets the initial value.  
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited("startup", arguments);
            // get integer form of the small delta
            this.smallDelta = parseInt(this.smallDelta, 10);
            // set the value
            this.setValue(this.value);
            // change the tooltip node
            this._textNode.setTooltipNode(this.domNode);
            // connect the on value changed event
            this._textNode.onValueChanged = dojo.hitch(this, this.onValueChanged);
            // set the auto-click callback
            this._autoClickCallback = dojo.hitch(this, this._autoClick);
            // set the regex message for the text
            this.textRegExpMessage = this._resources.SPINNER_INVALID_VALUE;
       }, 
        
        /**
         * Destroys the widget.
         */
        destroy: function()
        {
            // call the superclass's method
            this.inherited("destroy", arguments);
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
            this._textNode.setValue(parseInt(value, 10));
        },

        /**        
         * Returns the text representation of the value (a String)
         */
        getTextValue: function()
        {
            // return the text value
            return this._textNode.getTextValue();
        },
        
        /**
         * Returns true if the widget's value is not set.
         */
        isEmpty: function()
        {
            return this._textNode.isEmpty();
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
         * Handles the up-arrow pushed event.
         */
        _onPushUpArrow: function(e)
        {
            // handle the event only if the has been just pushed
            if ( this._isArrowPushed === false ) {
                this._onPushArrow(e, 1);
            }
        },
           
        /**
         * Handles the down-arrow pushed event.
         */
        _onPushDownArrow: function(e)
        {
            // handle the event only if the has been just pushed
            if ( this._isArrowPushed === false ) {
                this._onPushArrow(e, -1);
            }
        },
        
        /**
         * Handles a generic-arrow pushed event.
         */
        _onPushArrow: function(e, increment)
        {
            // skip if the pressed key is not the enter 
            if ( e.type === "keypress" && e.charCode !== 32 ) {
                return;
            }
            // the arrow has been pushed
            this._isArrowPushed = true;
            // start the autoclick
            this._startAutoClick(increment);
        },
        
        /**
         * Handles a generic-arrow pushed event.
         */
        _onPullArrow: function(e)
        {
            // the arrow has been pulled
            this._isArrowPushed = false;
            // stop the autoclick
            this._stopAutoClick();
        },     

        /**
         * Starts the auto-click (if a valid number is in place) 
         */
        _startAutoClick: function(increment)
        {
            // store the delta increment
            this._autoClickDelta = increment * this.smallDelta;
            // store the auto-click delay
            this._autoClickDelay = this._autoClickMaxDelay;
            // prepare values
            var value = this._textNode.getValue();
            var min = this._textNode.constraints.min;
            var max = this._textNode.constraints.max;
            
            // correct the value if wrong
            if ( isNaN(value) ) {
                // the value is not a number
                if ( increment === 1 && min !== undefined ) {
                    // up-arrow pushed set to the minimum
                    this._textNode.setValue(min);
                }
                else if ( increment === -1 && max !== undefined ) {
                    // up-arrow pushed set to the maximum
                    this._textNode.setValue(max);
                } 
                else {
                    // no constraint available to use, set to 0
                    this._textNode.setValue(0);
                }
                // schedule the next auto-click callback
                this._autoClickHandle = setTimeout(this._autoClickCallback, this._autoClickDelay);
            }
            else if ( this._isOutOfRange(value) ) {
                // the value is out of range
                if ( min !== undefined && value < min ) {
                    this._textNode.setValue(min);
                }
                else if ( max !== undefined && value > max ) {
                    this._textNode.setValue(max);
                }  
                // schedule the next auto-click callback
                this._autoClickHandle = setTimeout(this._autoClickCallback, this._autoClickDelay);
            }
            else {
                // start the auto-click
                this._autoClick();
            }
        },
        
        /**
         * Stops the auto-click.
         */
        _stopAutoClick: function() 
        {
            // stop the auto-click 
            clearTimeout(this._autoClickHandle);
            this._autoClickHandle = null;
            // notify about the change
            this.onValueChanged(this.getValue());
        },             
        
        /**
         * Simulates a click and schedules the next one. 
         */
        _autoClick: function() 
        {
            // compute the next value
            var value = this._textNode.getValue() + this._autoClickDelta;
            // check constraints 
            if ( this._isOutOfRange(value) ) {
                // the value is out of range, stop auto-click 
                this._stopAutoClick();
            }
            else {
                // set the value
                this._textNode.setValue(value);
                // compute the next auto-click delay
                this._autoClickDelay = Math.max(this._autoClickDelay - this._autoClickDelayAdjustment, this._autoClickMinDelay);
                // schedule the next auto-click callback
                this._autoClickHandle = setTimeout(this._autoClickCallback, this._autoClickDelay);
            }
        },
        
        /**
         * Returns true if the given value is out of range.
         */
        _isOutOfRange: function(value)
        {
            return ( this._textNode.constraints.max !== undefined && value > this._textNode.constraints.max ) ||
                   ( this._textNode.constraints.min !== undefined && value < this._textNode.constraints.min );
        }     
    }        
);

