/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/

 dojo.provide("ibm.tivoli.tip.dijit.Caret"); 

dojo.require("dijit.form.ComboBox");

/**
 * Caret. 
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)   
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.Caret",
	[dijit.form.ComboBoxMixin],
	{
	    /** the DOM node to manage */
	    _node: null,
	    
	    /**
	     * Constructor.
	     */
	    constructor: function(node)
	    {
	        this._node = node;
	    },
	    
        /**
         * Returns the caret position.
         */
        getPosition: function()
		{
			return this._getCaretPos(this._node);
		},
		
		/**
         * Sets the caret position.
         */
		setPosition: function(index)
		{
		    this._setCaretPos(this._node, index);
		}
	}
);
