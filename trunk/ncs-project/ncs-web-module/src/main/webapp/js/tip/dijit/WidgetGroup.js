dojo.provide("ibm.tivoli.tip.dijit.WidgetGroup"); 

dojo.require("dijit._Widget"); 

/**
 * A widget group.
 * 
 * Such widget groups a set of widgets and/or widget groups.
 * The widget acts as a collector for getters, status method and 
 * callbacks. Below some examples:
 * 
 *  - when getValue is called, the getValue is called on all the 
 *    contained widgets and an array of values is returned.
 *  - when the isError is called, the isError is called on all the
 *    contained widgets and true is returned if at least on widget
 *    is in error.
 *  - when a widget changes its value and calls its onValueChanged
 *    callback, the widget group catches such event and calls its
 *    onValueChanged callback.
 * 
 * The widget dispatches values for setters. For example:
 * 
 *  - the setValue accepts an array of values and when called
 *    each array value is set to the corresponing widget.  
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)   
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.WidgetGroup",
	[dijit._Widget],
	{
	    /** the array of widgets ids belonging to the group */
	    widgetIds: null,
	    
	    /** the array of widgets belonging to the group */
        _widgets: null,

        /** connect-handles (a map of connect-handle arrays) */
        _connections: null,
        
        /**
         * Constructor.
         * 
         *      @param params: an object containing the following 
         *          parameters:
         *              - ids: a string array containing the id of 
         *                the widgets to be inserted into the group. 
         */
        constructor: function(params)
        {
            // set-up the connect map
            this._connections = [];
            // get the widgets from the ids
            this.widgetIds = params.ids;
        },
        
        /**
         * Post mixin properties.
         */
        postMixInProperties: function()
        {
            // call the superclass's method
            this.inherited("postMixInProperties", arguments);
            // get the widgets from the ids
            this.setWidgetsById(this.widgetIds);
        },
        
        /**
         * Cleans-up the widget.
         */
	    destroy: function()
	    {
	        // disconnect all the widgets
	        this._disconnectAllWidgets();
            // call the superclass's method
            this.inherited("destroy", arguments);
	    },
	            
        /**
         * Returns true because this is a widget group.
         */
        isGroup: function()
        {
            return true;            
        },
        
        /**
         * Returns the contained widget with the given id.
         * 
         *      @param id: the widget id.
         */
        getWidgetById: function(id)
        {
            for ( var i = 0; i < this._widgets.length; i++ ) {
            	if ( this._widgets[i].id === id ) {
            	    return this._widgets[i];
            	}
            }
            return null;
        },
                
        /**
         * Puts in the group the widgets corresponding to the given ids.
         * 
         *      @param ids: a string array of widget's ids.
         */
        setWidgetsById: function(ids)
        {
            var w = [];
            if ( ids ) {
                for ( var i = 0; i < ids.length; i++ ) {
                	w[i] = dijit.byId(ids[i]);
                }
            }
            this.setWidgets(w);
        },
        
        /**
         * Adds in the group the widgets corresponding to the given ids.
         * 
         *      @param ids: a string array of widget's ids.
         */
        addWidgetsById: function(ids)
        {
            var w = [];
            if ( ids ) {
                for ( var i = 0; i < ids.length; i++ ) {
                	w[i] = dijit.byId(ids[i]);
                }
            }
            this.addWidgets(w);
        },
        
        /**
         * Removes the widgets corresponding to the given ids from the group.
         * 
         *      @param ids: a string array of widget's ids.
         */
        removeWidgetsById: function(ids)
        {
            var w = [];
            if ( ids ) {
                for ( var i = 0; i < ids.length; i++ ) {
                	w[i] = dijit.byId(ids[i]);
                }
            }
            this.removeWidgets(w);
        },

        /**
         * Returns the the widgets in the group.
         */
        getWidgets: function()
        {
            return this._widgets;
        },
        
        /**
         * Puts in the group the widgets.
         * 
         *      @param widgets: a widget array.
         */
        setWidgets: function(widgets)
        {
            // disconnect any previous widget
            this._disconnectAllWidgets();
            // store the widgets 
            this._widgets = widgets;
            // connect new widgets
            this._connectWidgets(this._widgets);
        },

        /**
         * Adds in the group the widgets.
         * 
         *      @param widgets: a widget array.
         */
        addWidgets: function(widgets)
        {
            // add the widgets 
            this._widgets = this._widgets.concat(widgets);
            // connect new widgets
            this._connectWidgets(widgets);
        },

        /**
         * Removes the widgets from the group.
         * 
         *      @param widgets: a widget array.
         */
        removeWidgets: function(widgets)
        {
            // disconnect the widgets
            this._disconnectWidgets(widgets);
            // remove the widgets
            for ( var i = 0; i < widgets.length; i++ ) {
                for ( var j = 0; j < this._widgets.length; j++ ) {
                    if ( widgets[i].id === this._widgets[j].id ) {
                        this._widgets.splice(j, 1);
                        break;
                    }
                }  
            }
        },
        
        /**        
         * Returns an array containing the values for the widgets belonging 
         * to the group in the same order on how the widgets have been set. 
         */
        getValue: function()
        {
            return this._call("getValue", []);
        },
        
        /**        
         * Sets the provided values to the contained widgets.
         * The values must be provided in the same order on how the 
         * widgets have been set. The value array must have the same 
         * length of the number of widgets contained.
         * 
         *      @param value: an array of object.   
         */
        setValue: function(value)
        {
            // set values
            if ( value !== null && value.length === this._widgets.length ) {
                for ( var i = 0; i < this._widgets.length; i++ ) {
                    this._widgets[i].setValue(value[i]);
                } 
            }
        },
        
        /**
         * Returns true if at least on contained widget is empty.
         */
        isEmpty: function()
        {
            return this._testOr("isEmpty");
        },

        /**
         * Enables/disables all the contained widgets. 
         */
        setDisabled: function(isDisabled)
        {
            // update all the contained widgets
            this._call("setDisabled", [isDisabled]);
        },
        
        /**
         * Returns true if at least one containted widget is disabled.
         */
        isDisabled: function()
        {
            return this._testOr("isDisabled");
        },
        
        /**
         * Enables the widget for editing. 
         */
        setEditable: function(isEditable)
        {
            this._call("setEditable", [isEditable]);
        },
        
        /**
         * Returns true if all the contained objects are editable.
         */
        isEditable: function()
        {
            return this._testAnd("isEditable");
        },
        
        /**
         * Sets the status for all the contained widgets.
         * 
         *      @param status: the status is an object with the following 
         *          two properties:
         * 
         *              - state: the state of the widget (e.g. constants.ERROR)
         *              - message: the message related to the current state.
         */
        setStatus: function(status)
        {
            this._call("setStatus", [status]);
        },

        /**
         * Returns an array containing the status for the widgets belonging 
         * to the group in the same order on how the widgets have been set. 
         */
        getStatus: function()
        {
            return this._call("getStatus", []);
        },
        
        /**
         * Returns true if at least one containted widget is in error.
         */
        isInError: function()
        {
            return this._testOr("isInError");  
        },
                
        /**
         * Returns an array of widgets in error.
         */
        getWidgetsInError: function()
        {
            var value = [];
            for ( var i = 0; i < this._widgets.length; i++ ) {
                if ( this._widgets[i].isInError() ) {
                    value[value.length] = this._widgets[i];     
                }
            }
            return value;  
        },
        
        /**
         * Sets required all the contained widgets.
         * 
         *      @param required:
         *          true if the widget's value is required, false otherwise.
         */
        setRequired: function(required)
        {
            this._call("setRequired", [required]);
        },
        
        /**
         * Returns an array of required widgets.
         */
        getRequired: function()
        {
            var value = [];
            for ( var i = 0; i < this._widgets.length; i++ ) {
                if ( this._widgets[i].isRequired() ) {
                    value[value.length] = this._widgets[i];     
                }
            }
            return value;  
        },

        /**
         * Returns true if at least one containted widget is required.
         */
        isRequired: function()
        {
            return this._testOr("isRequired");
        },
        
        /**
         * Executes the given function for all the widgets.
         * The function receives the widget has first parameter.
         * 
         *  @param f: a function to execute on each widget.
         */
        forEach: function(f)
        {
            for ( var i = 0; i < this._widgets.length; i++ ) {
                f.call(null, this._widgets[i]);
            }  
        },
        
        /**
         * Called if a contained widget has changed its value.
         * 
         *  @param value:
         *      the widget's value. 
         */
        onValueChanged: function(value)
        {
        },
        
        /**
         * Called if a contained widget has changed its status.
         * 
         *  @param status:
         *      the widget's status. 
         */
        onStatusChanged: function(status)
        {
        },

        /**
         * Returns true if a contained widget has the focus.
         */
        hasFocus: function()
        {
            return this._testOr("hasFocus");    
        },
        
        /**
         * Returns the widget which has the focus.
         */
        getFocusedWidget: function()
        {
            for ( var i = 0; i < this._widgets.length; i++ ) {
                if ( this._widgets[i].hasFocus() ) {
                    return this._widgets[i];      
                }
            }
            return null;  
        },

        /**
         * Asks the widget to revalidate its value and to set its status accordingly.
         */
        validate: function()
        {
            this._call("validate", []);
        },
        
        /**
         * Called if a contained widget has got the focus.
         */
        onFocus: function()
        {
        },
        
        /**
         * Called if a contained widget grou has lost the focus.
         */
        onBlur: function()
        {
        },
        
        /**
         * Connects the widget's callbacks to the group's callbacks.
         */
        _connectWidgets: function(widgets)
        {
            for ( var i = 0; i < widgets.length; i++ ) {
                var c = this._connections[widgets[i].id];
                if ( c === undefined ) {
                    c = [];
                    this._connections[widgets[i].id] = c;
                }
                c[c.length] = dojo.connect(widgets[i], "onValueChanged", this, "onValueChanged");
                c[c.length] = dojo.connect(widgets[i], "onStatusChanged", this, "onStatusChanged");
                c[c.length] = dojo.connect(widgets[i], "onFocus", this, "onFocus");
                c[c.length] = dojo.connect(widgets[i], "onBlur", this, "onBlur");
            }
        },
        
        /**
         * Disconnects the widget's callbacks for all the widgets.
         */
        _disconnectAllWidgets: function()
        {
     		dojo.forEach(this._connections, function(handles) {
    			dojo.forEach(handles, dojo.disconnect);
    		});
    		this._connections = [];
        },
                
        /**
         * Disconnects the widget's callbacks.
         */
        _disconnectWidgets: function(widgets)
        {
            for ( var i = 0; i < widgets.length; i++ ) {
                var handles = this._connections[widgets[i].id];
                if ( handles ) {
                    dojo.forEach(handles, dojo.disconnect);
                    delete this._connections[widgets[i].id];
                }    
            }
        },
        
        /**
         * Calls the required method on all the contained widgets.
         * 
         *      @param method: (String) the name of the method to call.
         *      @param args: (Array) The arguments to pass to the method.
         *      @return the array of method results.
         */
        _call: function(method, args)
        {
            var value = [];
            for ( var i = 0; i < this._widgets.length; i++ ) {
                value[i] = ((this._widgets[i])[method]).apply(this._widgets[i], args);
            }
            return value;
        },
        
        /**
         * Returns true if at least one method call returns true.
         * 
         *      @param method: (String) the name of the method to call.
         */
        _testOr: function(method)
        {
            for ( var i = 0; i < this._widgets.length; i++ ) {
                if ( ((this._widgets[i])[method]).apply(this._widgets[i]) === true ) {
                    return true;
                }
            }
            return false;            
        },
        
        /**
         * Returns true if all method calls returns true.
         * 
         *      @param method: (String) the name of the method to call.
         */
        _testAnd: function(method)
        {
            for ( var i = 0; i < this._widgets.length; i++ ) {
                if ( ((this._widgets[i])[method]).apply(this._widgets[i]) === false ) {
                    return false;
                }
            }
            return true;
        }        
    }     
);
