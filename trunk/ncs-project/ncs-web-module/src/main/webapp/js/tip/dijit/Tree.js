dojo.provide("ibm.tivoli.tip.dijit.Tree");

dojo.require("dijit._Templated");
dojo.require("ibm.tivoli.tip.dijit.Widget");

/**
 * Tree widget.
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)   
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.Tree",
    [ibm.tivoli.tip.dijit.Widget, 
     dijit._Templated],
    {
        /** the widget template path */
        templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/Tree.html"),
        
        /** true if a node must be selected on user click events */
        autoSelect: true,
        /** the tab index */
        tabindex: "0",
        /** true if at least one node contains a Dojo widget */
        hasWidgets: false,
        /** the tree (TreeItem object tree) */
        tree: null,

        /** the tree */
        _tree: null,
        /** current selected node (DOM node) */
        _selected: null,
        /** the node template */
        _nodeTemplate:
            "<div name='${nodeName}' nodeId=${nodeId}>" +
                "<div name='${rowName}'>" +
                    "<table><tbody><tr class='treeRow'>" +
                        "<td class='treeCell'>" +
                            "<img name='${iconName}' expanded='${expanded}' style='margin-left:${marginLeft}px' tabindex='${tabindex}' src='${imgSource}'></img>" +
                        "</td>" +
                        "<td name='${itemName}' class='treeCell'>" +
                            "${item}" +
                        "</td>" +
                    "</tr></tbody></table>" +
                "</div>" +
                "<div class='treeChildren' name='${childrenName}'>" +
                    "${children}" +
                "</div>" +          
            "</div>",
        
        /**
         * Prepares the selection list. 
         * If an array of tree has been specified the list is populated.  
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited("startup", arguments);
            
            // set-up some useful constants
            this._NODE_NAME = this.id + "#node";
            this._ROW_NAME  = this.id + "#row";
            this._ICON_NAME = this.id + "#icon";
            this._CHILDREN_NAME = this.id + "#children";
            this._ITEM_NAME = this.id + "#item";
               
            // valorize the node template with widget constants
            var p = {
                nodeName: this._NODE_NAME,
                rowName: this._ROW_NAME,
                iconName: this._ICON_NAME,
                childrenName: this._CHILDREN_NAME,
                itemName: this._ITEM_NAME,
                tabindex: this.tabindex,
                nodeId: "${nodeId}",
                marginLeft: "${marginLeft}", 
                imgSource: "${imgSource}",
                nesting: "${nesting}", 
                expanded: "${expanded}",
                item: "${item}",
                children: "${children}"
            };
            this._nodeTemplate = dojo.string.substitute(this._nodeTemplate, p);
            
            // populate the tree from the tree parameter
            this.setValue(this.tree);
        },

        /**        
         * Returns the tree (TreeItem object tree).
         */
        getValue: function()
        {
            return this._tree;
        },
        
        /**        
         * Sets the given tree into the tree.
         * 
         *  @param tree
         *      A tree of objects that have the following attributes:
         *          - id: a unique id which identifies the node in the tree
         *          - item: the content of the node to be displayed. It could be
         *                  a string, a HTML fragment, a HTML Dojo declaration.
         *                  In the last case the hasWidgets property must be set 
         *                  to true.
         *          - children: any array of child nodes.  
         */
        setValue: function(tree)
        {
            // destroy enclosing widgets
            if ( this.hasWidgets ) {
                dojo.query("[widgetId]", this.domNode).forEach(
                    function(d) {
                        dijit.byId(d.getAttribute("widgetId")).destroy();
                    }
                );
            }
            
            // store the tree
            this._tree = tree;
            if ( tree === null ) {
                this.domNode.innerHTML = "";
            }
            else {
                this.domNode.innerHTML = this._createTreeNode(tree, 0);
            }
            
            // parse the mark-up if has Dojo widgets 
            if ( this.hasWidgets ) {
                dojo.parser.parse(this.domNode);
            }
        },
        
        /**
         * Returns true if the tree has no tree.
         */
        isEmpty: function()
        {
            return ( this.getValue() === null );
        },
        
        /**
         * Selects/Deselects a node corresponding to the given id.
         * 
         *  @param id:
         *      The node id (the same passed in setValue tree).
         *  @param selected:
         *      true if the node must be selected.
         */
        selectNode: function(id, selected)
        {
            // get the node corresponding to the given id
            var node = dojo.query("div[nodeid='" + id + "']")[0];
            if ( node ) {
                var row = dojo.query("div[name='" + this._ROW_NAME + "']", node)[0];
                this._selectRow(row, selected);
            }
        },
        
        /**
         * Deselects the current selected node (if any).
         */
        unselect: function()
        {
            if ( this._selected ) {
                this._selectRow(this._selected, false);
            }
        },
        
        /**        
         * Returns true if the node is selected.
         * 
         *  @param id:
         *      The node id (the same passed in setValue tree).
         *  @return:
         *      true if the node is selected.

         */
        isNodeSelected: function(id)
        {
            var selected = false;
            var node = dojo.query("div[nodeid='" + id + "']")[0];
            if ( node ) {
                var row = dojo.query("div[name='" + this._ROW_NAME + "']", node)[0];
                selected = this._isRowSelected(row);
            }
            return selected;
        },

        /**
         * This callback is called when a node has been selected/unselected.
         * 
         *  @param id:
         *      The id of the selected node.
         *  @param selected:
         *      true if the node has been selected, false otherwise.    
         */        
        onNodeSelect: null,
                
        /**
         * Handles the onkeyup event.
         * If a space has been pressed on a +/- icon, the node is 
         * expanded/collapsed accordingly.
         */
        _onKeyUp: function(event)
        {
            if ( event.keyCode === dojo.keys.SPACE ) {
                this._onClick(event);   
            }
        },
        
        /**
         * Handles the onclick event.
         * If the +/- icon has been clicked, the node is 
         * expanded/collapsed accordingly.
         */
        _onClick: function(event)
        {
            var t = event.target;
            var node;
            
            // check if the click comes from a +/- icon
            if ( t.getAttribute("name") === this._ICON_NAME ) {
                // look-up the tree node
                node = this._getNode(t);
                if ( node ) {
                    // node found, collapse/expand it
                    this._toggleNode(node, t);
                }
                // stop the event bubbling 
                dojo.stopEvent(event);
                // and that's it
                return;
            }
            
            // handle selection only if enabled
            if ( this.autoSelect ) {
                // check if the click comes from the node area
                node = this._getNode(t);
                if ( node ){
                    var row = dojo.query("div[name='" + this._ROW_NAME + "']", node)[0];
                    this._selectRow(row, ! this._isRowSelected(row));
                    // stop the event bubbling 
                    dojo.stopEvent(event);
                    // and that's it
                    return;
                }
            }
        },
        
        /**
         * Returns the DOM node of the tree node which includes
         * the specified child node.
         * 
         *  @param childNode:
         *      A DOM node which is a descendant of a tree node.
         *  @return 
         *      A tree (DOM) node if found, otherwise null.  
         */
        _getNode: function(childNode)
        {
            while ( childNode ) {
                if ( childNode.getAttribute && 
                     childNode.getAttribute("name") === this._NODE_NAME ) {
                    break;
                } 
                childNode = childNode.parentNode;                
            }
            return childNode;   
        },
         
        /**
         * Creates a tree node mark-up.
         * 
         *  @param node:
         *      A tree node. Such node has two properties:
         *          - item: is the mark-up to be shows for such node.
         *          - children: an optional array of children nodes.
         *  @param nesting:
         *      The current nesting level.
         *  @return
         *      The mark-up string corresponding to the given node
         *      including all the descendants. 
         */
        _createTreeNode: function(node, nesting)
        {
            var s = [];
            
            if ( node ) {
                // get the children mark-up
                var children = [];
                if ( node.children && node.children.length > 0 ) {
                    for ( var i = 0; i < node.children.length; i++ ) {
                        children[children.length] = this._createTreeNode(node.children[i], nesting + 1);                                        
                    }
                }
                 
                // set the image and the tabindex depending on the nesting state 
                var imgSource;
                var tabindex = this.tabindex;
                if ( ! node.children || node.children.length === 0 ) {
                    imgSource = this._images.ICON_TREE_EMPTY;
                    tabindex = "-1";
                }
                else {
                    imgSource = this._images.ICON_EXPANDED;
                }
                
                // prepare the template parameters
                var p = {
                    nodeId: node.id || "",
                    marginLeft: (4 + 16 * nesting), 
                    tabindex: tabindex, 
                    imgSource: imgSource,
                    nesting: nesting, 
                    expanded: true,
                    item: node.item || "",
                    children: children.join("")
                };
                
                // valorize the template
                s[s.length] = dojo.string.substitute(this._nodeTemplate, p);
            }
            
            // that's it
            return s.join("");
        },

        /**        
         * Toogles a node (expand it if collapsed, collapse it if expanded).
         * 
         *  @param node:
         *      The (DOM)node to be toggled.
         *  @param img:
         *      The DOM node of the +/- icon. This is optional,
         *      if not provided the method looks-up it.
         */
        _toggleNode: function(node, img)
        {
            // get the children node
            var children = dojo.query("div:first-child[name='" + this._CHILDREN_NAME + "']", node)[0];
            if ( children && children.hasChildNodes() ) {
                // get the image node (if not provided)
                if ( ! img ) {
                    img = dojo.query("img:first-child[name='" + this._ICON_NAME + "']", node)[0];
                }
                var isExpanded = img.getAttribute("expanded") === "true";
                // update the image node
                img.setAttribute("expanded", isExpanded ? "false" : "true");
                img.src = isExpanded ? this._images.ICON_COLLAPSED : this._images.ICON_EXPANDED;
                // hide/show the children
                children.style.display = isExpanded ? "none" : "block";
            }
        },
        
        /**        
         * Selects/unselects a node.
         * 
         *  @param row:
         *      The row DOM node belonging to the node to be selected/unselected.
         *  @param selected:
         *      true if the node must be selected.
         */
        _selectRow: function(row, selected)
        {
            if ( this._selected ) {
                // another row is selected
                if ( this._selected !== row ) {
                    // it's a different row
                    if ( selected ) {
                        // switch the selection
                        dojo.removeClass(this._selected, "treeCellSelected");
                        dojo.addClass(row, "treeCellSelected");
                        this._selected = row;
                    }
                }
                else {
                    // it's the same row
                    if ( ! selected ) {
                        dojo.removeClass(row, "treeCellSelected");
                        this._selected = null;
                    }
                }
            }
            else {
                // no row selected
                if ( selected ) {
                    dojo.addClass(row, "treeCellSelected");
                    this._selected = row;
                }
            }
            
            // call the callback
            if ( this.onNodeSelect ) {
                this.onNodeSelect(row.parentNode.getAttribute("nodeId"), selected);
            }
        },
        
        /**        
         * Returns true if the row is selected.
         * 
         *  @param row:
         *      The row DOM node belonging to the node to be selected/unselected.
         *  @return :
         *      true if the node is selected.
         */
        _isRowSelected: function(row)
        {
            return ( this._selected === row );
        }
    }
);

