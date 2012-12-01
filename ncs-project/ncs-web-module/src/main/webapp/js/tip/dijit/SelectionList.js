dojo.provide("ibm.tivoli.tip.dijit.SelectionList");

dojo.require("dijit._Templated");
dojo.require("ibm.tivoli.tip.dijit.Widget");

/**
 * The selection list widget.
 * 
 * Such widget implements a selectable list. Caller provides
 * a list of items which could be individually selected
 * and the widget manages the selection functionalities. 
 * 
 * The caller provides the items as a set of object properties.
 * The property's names are the id for the values and the property's
 * values are the list's values. The ids will be used to identify
 * the list's items to work with. Values can be plain text, HTML or
 * widget declarations (in such case hasWidgets must be true).
 * 
 * The widget exposes the following configuration attributes:
 * 
 *      - items: (Object) the items to show in the selection.
 *      - tabindex: (Number) the tabindex to assign to the selection 
 *        boxes.
 *      - multipleSelection: (Boolean) true if a multiple selection 
 *        is allowed, false if a single selection is allowed.
 *      - hasWidgets: (Boolean) true if the list's items contain
 *        Dojo declarations. 
 *      - colorizeSelection: (Boolean) true if items must be colorized
 *        when selected.
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.SelectionList",
    [ibm.tivoli.tip.dijit.Widget, dijit._Templated],
    {
        /** the user's provided items */
        items: null,
        /** the user's provided tabindex (needed to navigate with keyboard) */
        tabindex: 0,
        /** if true allows to choose multiple elements in the list */
        multipleSelection: true,
        /** true if at least one item contains a Dojo widget */
        hasWidgets: false,
        /** true if selected items must be colorized */
        colorizeSelection: true,
        
        /** the widget template path */
        templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/SelectionList.html"),
        
        /** the list node */
        _bodyNode: null,
        /** the table node */
        _tableNode: null,
        /** the items */
        _items: null,
        /** the input node list */
        _inputNodes: null, 
        /** the table cell node list */
        _cellNodes: null, 
        /** the _rowTemplate template */
        _rowTemplate:
            "<tr itemId='${itemId}'>" +
                "<td name='${cellInputName}' width='16px' class='selectionListTableCell'>" +
                    "<input type='${inputType}' name='${inputName}' itemId='${itemId}' tabindex='${tabindex}'/>" +
                "</td>" +                     
                "<td name='${cellName}' itemId='${itemId}' class='selectionListTableCell'>" +
                    "${item}" +
                "</td>" +                
            "</tr>",
        /** the list of key codes to be intercepted */    
        _keys: null,
        
        /**
         * Prepares the selection list. 
         * If an array of items has been specified the list is populated.  
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited("startup", arguments);
            
            /** prepare the code array of keys to be managed (should be a static property ) */            
            this._keys = [];
            this._keys[dojo.keys.SPACE] = true;
            this._keys[dojo.keys.LEFT_ARROW] = true;
            this._keys[dojo.keys.UP_ARROW] = true;
            this._keys[dojo.keys.RIGHT_ARROW] = true;
            this._keys[dojo.keys.DOWN_ARROW] = true;

            // set-up some useful constants
            this._INPUT_NAME = this.id + "#input";
            this._CELL_INPUT_NAME = this.id + "#cellInput";
            this._CELL_NAME = this.id + "#item";
               
            // valorize the item template with widget constants
            var p = {
                inputName: this._INPUT_NAME,
                cellInputName: this._CELL_INPUT_NAME,
                cellName: this._CELL_NAME,
                tabindex: this.tabindex,
                itemId: "${itemId}",
                item: "${item}",
                inputType: (this.multipleSelection) ? "checkbox" : "radio"
            };
            this._rowTemplate = dojo.string.substitute(this._rowTemplate, p);
            
            // populate the list from the item parameter
            this.setValue(this.items || []);
        },

        /**        
         * Returns an array containing the name of the selected 
         * values.
         */
        getValue: function()
        {
            var result = [];
            dojo.forEach( 
                this._inputNodes,
                function(node){
                    if ( node.checked ) { 
                       result[result.length] = node.getAttribute("itemId");
                    }
                });

            return result;
        },
        
        /**        
         * Sets the given items into the list.
         * 
         *  @param items
         *      A maps whose values are the elements shown in the selection
         *      and the names are used to identify the selected elements.
         */
        setValue: function(items)
        {
            // destroy enclosing widgets
            if ( this.hasWidgets ) {
                dojo.query("[widgetId]", this._bodyNode).forEach(
                    function(d) {
                        dijit.byId(d.getAttribute("widgetId")).destroy();
                    }
                );
            }
                        
            // store the items
            this._items = items;
            
            // populate the list
            var s = [];
            s[s.length] = "<table class='selectionListTable' tabindex='-1'>";
            s[s.length] = "<tbody>";
            for ( var i in items ) {
                // prepare the tamplate parameters
                var p = {
                    itemId: i,
                    item: items[i]
                };
                // valorize the template
                s[s.length] = dojo.string.substitute(this._rowTemplate, p);
            }
            s[s.length] = "</tbody>";
            s[s.length] = "</table>";
            this._bodyNode.innerHTML = s.join("");
            
            // set the input node list
            this._inputNodes = dojo.query("input", this._bodyNode);
            // set the cell node list
            this._cellNodes = dojo.query("[name='" + this._CELL_NAME + "']", this._bodyNode);
            // set the table node
            this._tableNode = dojo.query("tbody", this._bodyNode)[0];
            
            // parse the mark-up if has Dojo widgets 
            if ( this.hasWidgets ) {
                dojo.parser.parse(this._bodyNode);
            }
        },
        
        /**
         * Returns true if no selection has been done.
         */
        isEmpty: function()
        {
            return ( this.getValue().length === 0 );
        },
        
        /**
         * Returns the item corresponding to the given name.
         * 
         *  @param itemId:
         *      The id of the item to retrieve.
         *  @return 
         *      null if the item has not been found, or a 
         *      NodeList object containing all the child nodes.         
         */
        getItem: function(itemId)
        {
            var node = this._getNodeById(this._cellNodes, itemId);
            return ( node ? node.childNodes : null );
        },
        
        /**
         * Sets the item corresponding to the given name. If the name
         * is not found the item is not set.
         * 
         *  @param itemId:
         *      The id of the item to retrieve.
         *  @param item:
         *      The item to be set. It could be also a mark-up or a 
         *      Dojo declaration (hasWidgets must be true in order to get
         *      the declaration parsed).         
         */
        setItem: function(itemId, item)
        {
            var node = this._getNodeById(this._cellNodes, itemId);
            if ( node ) {
                // destroy widgets
                if ( this.hasWidgets ) {
                    dojo.query("[widgetId]", node).forEach(
                        function(d) {
                            dijit.byId(d.getAttribute("widgetId")).destroy();
                        }
                    );
                }
                // set the item   
                node.innerHTML = item;
                // parse the mark-up if has Dojo widgets 
                if ( this.hasWidgets ) {
                    dojo.parser.parse(node);
                }
            }
        },
        
        /**
         * Removes the item corresponding to the given name. If the name
         * is not found does nothing.
         * 
         *  @param itemId:
         *      The id of the item to remove.
         */
        removeItem: function(itemId)
        {
            // lookup the row with the given id
            var nodes = dojo.query("tr[itemId=" + itemId + "]", this._bodyNode);
            if ( nodes && nodes.length > 0 ) {
                var tr = nodes[0];
                // destroy widgets
                if ( this.hasWidgets ) {
                    dojo.query("[widgetId]", tr).forEach(
                        function(d) {
                            dijit.byId(d.getAttribute("widgetId")).destroy();
                        }
                    );
                }
                // delete references
                this._removeNodeById(this._inputNodes, itemId);
                this._removeNodeById(this._cellNodes, itemId);
                // delete the row    
                this._tableNode.deleteRow(tr.rowIndex);
            }
        },
        
        /**
         * Appends the item corresponding to the given name. If the name
         * is found the item is not appended.
         * 
         *  @param itemId:
         *      The id of the item to append.
         *  @param item:
         *      The item to be append. It could be also a mark-up or a 
         *      Dojo declaration (hasWidgets must be true in order to get
         *      the declaration parsed).         
         */
        appendItem: function(itemId, item)
        {
            var node = this._getNodeById(this._cellNodes, itemId);
            if ( ! node ) {
                // populate the list
                var e = document.createElement("div");
                e.innerHTML = [
                    "<table><tbody>",
                    dojo.string.substitute(this._rowTemplate, {itemId: itemId, item: item}),
                    "</tbody></table>"].join("");
                
                // append the row to the table
                var rowNode = dojo.query("tr", e)[0];
                this._tableNode.appendChild(rowNode);

                // parse the mark-up if has Dojo widgets
                if ( this.hasWidgets ) {
                    dojo.parser.parse(rowNode);
                }
                
                // set the input node list
                if ( ! this._inputNodes ) {
                    this._inputNodes = new dojo.NodeList();
                }
                this._appendNode(this._inputNodes, dojo.query("input", rowNode)[0]);
                // set the cell node list
                if ( ! this._cellNodes ) {
                    this._cellNodes = new dojo.NodeList();
                }
                this._appendNode(this._cellNodes, dojo.query("[name='" + this._CELL_NAME + "']", rowNode)[0]);
            }
        },
        
        /**
         * Removes all the item currently displayed in the list.
         */
        clear: function()
        {
            this._inputNodes = null;
            this._cellNodes = null;
            // delete all the row in the table
            var rows = this._tableNode.rows;
            for ( var i = rows.length - 1; i >= 0 ; i-- ) {
                this._tableNode.deleteRow(i);
            }
        },
        
        /**
         * Returns the number of elements in the selection list.
         */
        size: function()
        {
            var obj = { counter: 0 };
            if ( this._inputNodes ) {
                this._inputNodes.forEach(
                    function() {
                        this.counter++;
                    },
                    obj
                );
            } 
            return obj.counter;   
        },
        
        /**
         * Selects the pointed item.
         * 
         *  @param itemId 
         *      the id of the item to be selected.
         */
        select: function(itemId)
        {
            this._select({itemId: itemId, selected: true});
        },
        
        /**
         * Unselects the pointed item.
         * 
         *  @param itemId 
         *      the id of the item to be selected.
         */
        unselect: function(itemId)
        {
            this._select({itemId: itemId, selected: false});
        },
        
        /**
         * Selects all the items.
         */
        selectAll: function()
        {
            this._select({all: true, selected: true});
        },
        
        /**
         * Unselects all the items.
         */
        unselectAll: function()
        {
            this._select({all: true, selected: false});
        },
        
        /**
         * A generic select method.
         * 
         *  @param params
         *      An object containing the selection parameters.
         *      Parameters are:
         *          - all [boolean]: if true the selection must be done
         *            on all the items
         *          - selected [boolean]: if true the item(s) must be selected
         *          - item [string]: the id of the item to be selected,
         *            only considered if the all parameter is false.
         */
        _select: function(params)
        {
            // no nodes in place, skip
            if ( ! this._inputNodes ) {
                return;
            }
        
            // select all on single selection unsupported
            if ( params.all && params.selected && ! this.multipleSelection ) {
                return;
            }
            
            if ( params.all ) {
                // the operation must be done on all the items
                
                // select all the input boxes
                this._inputNodes.forEach( 
                    function(node) {
                        node.checked = params.selected;
                    });
                // select all the cells
                if ( this.colorizeSelection ) {
                    this._cellNodes.forEach( 
                        function(node) {
                            dojo.toggleClass(node, "selectionListTableCellSelected", params.selected);
                        });
                }
            }
            else {
                // the operation must be done on a specific item

                // select the input box
                var inputNode = this._getNodeById(this._inputNodes, params.itemId);
                if ( inputNode ) {
                    inputNode.checked = params.selected;
                }
                
                // select the cell
                if ( this.colorizeSelection ) {
                    var nodes = this._cellNodes;
                    // unselect the previous cell in case of single selection
                    if ( ! this.multipleSelection ) {
                        for ( var i = 0; i < nodes.length; i++ ) {
                            dojo.removeClass(nodes[i], "selectionListTableCellSelected");
                        }
                    }
                    // select the current cell
                    var cellNode = this._getNodeById(nodes, params.itemId);
                    if ( cellNode ) {
                        dojo.toggleClass(cellNode, "selectionListTableCellSelected", params.selected);
                    }
                }
            }
        },
        
        /**
         * Returns the node which has the given id.
         * 
         *  @param nodes
         *      the nodes (NodeList) to be searched in.
         *  @param itemId
         *      the id to be searched.   
         */
        _getNodeById: function(nodes, itemId)
        {
            if ( nodes ) {
            itemId = "" + itemId;
                var name;
                for ( name in nodes ) {
                    var node = nodes[name];
                    if ( ! dojo.isFunction(node) && node.getAttribute("itemId") === itemId ) {
                        // the node is not a function and the id has been matched
                        return node;
                    }
                }
            }
            // node not found
            return null;
        },
           
        /**
         * Removes the node which has the given id.
         * 
         *  @param nodes
         *      the nodes (NodeList) to be removed from.
         *  @param itemId
         *      the id to be removed.   
         */
        _removeNodeById: function(nodes, itemId)
        {
            itemId = "" + itemId;
            var name;
            for ( name in nodes ) {
                var node = nodes[name];
                if ( ! dojo.isFunction(node) && node.getAttribute("itemId") === itemId ) {
                    nodes.splice(nodes.indexOf(node), 1);
                    break;
                }
            }
        },
            
        /**
         * Appends the node.
         * 
         *  @param nodes
         *      the nodes (NodeList) to be appended to.
         *  @param node
         *      the id to be appended.   
         */
        _appendNode: function(nodes, node)
        {
            nodes.splice(nodes.length, 0, node);
        },
           
        /**
         * Handles the onkeyup event.
         * If a space has been pressed on a checkbox, the caller 
         * will be notified.
         */
        _onKeyUp: function(event)
        {
            if ( this._keys[event.keyCode] ) {
                this._onClick(event);       
            }
        },
        
        /**
         * Handles the onclick event.
         * If the selection box has been clicked, the node is 
         * selected/unselected accordingly.
         */
        _onClick: function(event)
        {
            var t = event.target;

            // check if the click comes from a checkbox
            var name = t.getAttribute("name");
            if ( name === this._INPUT_NAME || name === this._CELL_INPUT_NAME ) {
                // get the selection control
                var selection;
                if ( name === this._CELL_INPUT_NAME ) {
                    // this a cell 
                    selection = dojo.query("input", t)[0];
                    selection.checked = ! selection.checked; 
                }
                else {
                    // this is already the selection control
                    selection = t;
                }
                // select the item
                this._select({itemId: selection.getAttribute("itemId"), selected: selection.checked});
                // notify the change
                this.onValueChanged(this.getValue());
                // and that's it
                return;
            }
        }
    }
);
