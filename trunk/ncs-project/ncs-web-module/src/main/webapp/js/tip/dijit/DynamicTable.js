dojo.provide("ibm.tivoli.tip.dijit.DynamicTable"); 

dojo.require("dijit._Templated");
dojo.require("ibm.tivoli.tip.dijit.WidgetGroup");
dojo.require("ibm.tivoli.tip.dijit.Images");

/** 
 * This widget implements a table which can dynamically change 
 * the number of rows and can host a configurable number of coloumns.
 * Each cell can be any widget.
 * 
 * For example the widget can be used to easily have a dynamic table of
 * key/value pairs.
 * 
 * The table content can be configured by inheriting from such widget
 * and implementing two methods:
 * 
 *  - getRowHeaders: returns an array of string which are the header to
 *      show in the table header.
 *  - getRowWidgets: returns an array of widgets which will be used to create
 *      a new row.
 * 
 * The arrays returned by the two methods must have the same length.
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.DynamicTable",
	[ibm.tivoli.tip.dijit.WidgetGroup, dijit._Templated],
	{
        /** the tab index */
        tabindex: "0",
	    
	    /** the widget template path */
        templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/DynamicTable.html"),
	    
	    /** the table DOM node */
	    _tableNode: null,
        /** the table header DOM node */
	    _headerNode: null,	 
        /** the table body DOM node */
	    _bodyNode: null,
	    /** a counter used to get a unique row id */
	    _rowId: 0,
	    
        /** the add icon */
        _addIcon: null,
        /** the remove icon */
        _removeIcon: null,
	    
	    /**
	     * Starts-up the widget.
	     */
	    startup: function()
	    {
            // call the superclass's method
            this.inherited("startup", arguments);
            
            // set-up some useful constants
            this._GROUP_PREFIX = this.id + "#group";
            this._ADD_ICON_NAME = this.id + "#add";
            this._REMOVE_ICON_NAME = this.id + "#remove";
            
            // set icon mark-up
            this._addIcon = dojo.string.substitute(
                "<img name='${name}' src='${src}' tabindex='${tabindex}'/>",
                {
                    name: this._ADD_ICON_NAME,
                    src: ibm.tivoli.tip.dijit.Images.get()["TABLE_ADD_ROW_ICON"],
                    tabindex: this.tabindex
                });            
            this._removeIcon = dojo.string.substitute(
                "<img name='${name}' src='${src}' tabindex='${tabindex}'/>",
                {
                    name: this._REMOVE_ICON_NAME,
                    src: ibm.tivoli.tip.dijit.Images.get()["TABLE_REMOVE_ROW_ICON"],
                    tabindex: this.tabindex    
                }); 
            
            //set the header
            this._setHeader();
            // set the bottom row
            this._setBottomRow();
            // set the table width
            this._setTableWidth();
	    },
	    
	    /**
	     * Returns a string array containing the coloumn headers.
	     * Such method is an extension point for inheriting widgets.
	     * If return null or an ampty array the header row will not be
	     * shown.
	     */
	    getRowHeaders: function()
	    {
	        return [];
	    },

	    /**
	     * Returns an array of widgets (or widget declaration) 
	     * to be shown in a row.
	     * Such method is an extension point for inheriting widgets
	     * and is called when the table needs to create a new row.
	     */
	    getRowWidgets: function()
	    {
	        return [];
	    },
	    
	    /**
	     * Sets the table with the given values.
	     * The number of table rows is adapted to the number of 
	     * array passed as value.
	     */
	    setValue: function(value)
	    {
            // ensure the table has the same number of row of value
            var wg = this.getWidgets();
            if ( value.length > wg.length ) {
                // expand the table
                for ( var i = wg.length; i < value.length; i++ ) {
                    this._appendRow();
                }
            }
            else if ( value.length < wg.length ) {
                // reduce the table
                for ( var i = wg.length; i > value.length; i-- ) {
                    this._deleteRow(i-1);
                }
            }
            
            // call the superclass's method
            this.inherited("setValue", arguments);
	    },
	    
	    /**
	     * Sets the table with the given values.
	     * The number of table rows is adapted to the number of 
	     * array passed as value.
	     */
	    appendRows: function(rows)
	    {
	        for ( var i = 0; i < rows.length; i++ ) {
	            this._appendRow(rows[i]);
	        }
	    },
	    
	    /**
	     * Deletes all the row in the table
	     */
	    deleteRows: function()
	    {
            // delete all the row in the table
            var rows = this._bodyNode.rows;
            for ( var i = rows.length - 2; i >= 0 ; i-- ) {
                this._deleteRow(i);
            }
	    },
	    	    
	    /**
         * Enables/disables the widget. 
         */
        setDisabled: function(isDisabled)
        {
            // call the superclass's method
            this.inherited("setDisabled", arguments);
            // hode/show icons accordingly
            this._setIconsStyle("visibility", this.isDisabled() ? "hidden" : "visible");
        },
        
        /**
         * Enables the widget for editing. 
         */
        setEditable: function(isEditable)
        {
            // call the superclass's method
            this.inherited("setEditable", arguments);
            // hode/show icons accordingly
            this._setIconsStyle("visibility", this.isEditable() ? "visible" : "hidden");
        },
        
        /**
	     * Sets the header row.
	     */
	    _setHeader: function()
	    {
	        // get the headers
	        var headers = this.getRowHeaders();
    	    if ( headers && headers.length > 0 ) {
    	        // append the row to the header 
    	        var row = this._headerNode.insertRow(0);
                // set an empty cell 
                var cell = row.insertCell(0);
    	        // populate the row
    	        for ( var i = 0; i < headers.length; i++ ) {
    	            cell = row.insertCell(i+1);
    	            cell.innerHTML = headers[i];
    	        }
	        }
	    },

        /**
	     * Sets the bottom row which is an empty row having only
	     * the button for adding a row.
	     */
	    _setBottomRow: function()
	    {
	        // append the row 
	        var row = this._bodyNode.insertRow(this._bodyNode.rows.length);
            // set the icon
            var cell = row.insertCell(0);
	        cell.innerHTML = this._addIcon;
	    },
	    
	    /**
	     * Resizes the table by setting the width for each cell
	     * to a value which will be enough to contain both the header and
	     * the widget  
	     */
	    _setTableWidth: function()
	    {
            // get the header cell 
            var hw = this._getHeaderCellWidth();
	        // get the body cell width
            var bw = this._computeBodyCellWidth();
            // set the cell widths
            var cells = this._headerNode.rows[0].cells;
            for ( var i = 0; i < hw.length; i++ ) {
                if ( hw[i] < bw[i] ) {
                    cells[i].setAttribute("width", bw[i]);
                }
            }
	    },

        /**
	     * Returns the header cell widths.
	     */
	    _getHeaderCellWidth: function()
	    {
	        var width = [];
	        // get the first header row (just one expected)
	        var cells = this._headerNode.rows[0].cells;
	        // get width
	        for ( var i = 0; i < cells.length; i++ ) {
	            width[i] = cells[i].clientWidth;
	        }
	        return width;
        },
	    
        /**
	     * Computes the width each cell should have in order to 
	     * contain its corresponding widget.
	     * 
	     *     @return an array of widths
	     */
	    _computeBodyCellWidth: function()
	    {
	        var width = [];
	        
	        // create a fake table
	        var table = document.createElement("table");
	        table.setAttribute("style", "visibility:hidden");
	        var row = table.insertRow(table.length);
	        // set the remove icon
	        var cell = row.insertCell(0);
	        cell.innerHTML = this._addIcon;
	        // get the row widgets
	        var widgets = this.getRowWidgets();
	        // populate the row
	        for ( var i = 0; i < widgets.length; i++ ) {
	            cell = row.insertCell(i+1);
	            cell.appendChild(widgets[i].domNode);
	        }

            // insert the fake table in the document body
	        document.body.appendChild(table);
	        
	        // store the width array
	        for ( var i = 0; i < row.cells.length; i++ ) {
                width[i] = row.cells[i].clientWidth;
	        }

	        // remove the fake table
            document.body.removeChild(table);               

	        // return the size
	        return width;
	    },
	    
	    /**
	     * Sets the value to the style property for all the remove/add icons.
	     * 
	     *     @param property: (String) the name of the CSS style property 
	     *         (e.g. "visibility")
	     *     @param value: (String) the value of the CSS style property 
	     *         (e.g. "hidden");   
	     */
        _setIconsStyle: function(property, value)
        {
            // apply to all the remove icons
            var icons = dojo.query("img[name='" + this._REMOVE_ICON_NAME + "']", this._bodyNode);
            dojo.forEach(icons, function(img) {
                    img.style[property] = value;
                });
            // apply to all the add icons 
            icons = dojo.query("img[name='" + this._ADD_ICON_NAME + "']", this._bodyNode);
            dojo.forEach(icons, function(img) {
                    img.style[property] = value;
                });
        },
        	    
	    /**
	     * Returns the row which contains the given node.
	     */
	    _getContainingRow: function(node)
	    {
	        var n = node;
	        while ( n && n.tagName !== "TR" ) {
                n = n.parentNode;
            } 
            return n;
	    },
	    
	    /**
	     * Deletes the row which contains the given node.
	     */
	    _deleteContainingRow: function(node)
	    {
	        var n = this._getContainingRow(node);
            if ( n ) {
                this._deleteRow(n.rowIndex - 1);    
            }
	    },
	    
		/**
	     * Appends a row to the table. If the value parameter is specified 
	     * the row will be set with such value.
	     * 
	     *     @param value: (Array) an optional array of values which will be used
	     *         to set the values of the row being created. 
	     */
	    _appendRow: function(value)
	    {
	        // create the row DOM node
	        var row = document.createElement("tr");
	        // set the unique row id
	        var rowId = this._GROUP_PREFIX + this._rowId++;
	        row.setAttribute("id", rowId);
	        // set the remove icon
	        var cell = document.createElement("td");
	        cell.innerHTML = this._removeIcon;
	        row.appendChild(cell);
	        // get the row widgets
	        var widgets = this.getRowWidgets();
	        // populate the row
	        for ( var i = 0; i < widgets.length; i++ ) {
	            cell = document.createElement("td");
	            cell.appendChild(widgets[i].domNode);
	            row.appendChild(cell);
	        }
            // create a group for the row
            var rowGroup = new ibm.tivoli.tip.dijit.WidgetGroup({id: row.id, ids: []});
            rowGroup.startup();
            // add widgets to the row
            rowGroup.addWidgets(widgets);
            // set the widget value (if any)
            if ( value ) {
                rowGroup.setValue(value);
            }
            // add row group to the main group
            this.addWidgets([rowGroup]);
            // append to the table body
            this._bodyNode.insertBefore(row, this._bodyNode.rows[this._bodyNode.rows.length-1]);
	    },

	    /**
	     * Deletes the row at the given position from the table.
	     */
	    _deleteRow: function(position)
	    {
	        // check the length (remember the last one is reserved for the add button)
	        if ( position < this._bodyNode.rows.length - 1 ) {
	            // destroy widgets in the row
                var row = this._bodyNode.rows[position];
                // get the group widget
                var groupId = row.getAttribute("id");
                var group = this.getWidgetById(groupId);
                // remove the widget group from the main group
                this.removeWidgetsById([groupId]);
                // destroy the row widgets
                group.forEach(function(w) { w.destroy(); });
                // destroy the widget group
                group.destroy();
	            // delete the row
	            this._bodyNode.deleteRow(position); 
	        }
	    },
	    
        /**
         * Handles the onkeyup event.
         */
        _onKeyUp: function(event)
        {
            if ( event.keyCode === dojo.keys.SPACE ) {
                this._onClick(event);   
            }
        },
        
        /**
         * Handles the onclick event.
         * If an icon has been clicked, the corresponding action is executed.
         */
        _onClick: function(event)
        {
            var t = event.target;
            var name = t.getAttribute("name");
            
            // check if the click comes from a remove icon
            if ( name === this._REMOVE_ICON_NAME ) {
                // delete the row
                this._deleteContainingRow(t);
                // stop the event bubbling 
                dojo.stopEvent(event);
                // notify the change 
                this.onValueChanged(this.getValue()); 
                // and that's it
                return;
            }

            // check if the click comes from an add icon
            if ( name === this._ADD_ICON_NAME ) {
                // append the row
                this._appendRow();
                // stop the event bubbling 
                dojo.stopEvent(event);
                // notify the change 
                this.onValueChanged(this.getValue()); 
                // give the focus to the first added widget
                // NOTE: (IE problem) unable to set the focus on the same code 
                // execution run, schedule the code 
                setTimeout(
                    dojo.hitch(this, function() {
                            var widgets = this.getWidgets();
                            var row = widgets[widgets.length-1].getWidgets();
                            row[0].setFocus();
                        }), 
                    1);
                // and that's it
                return;
            }
        }
	}
);

