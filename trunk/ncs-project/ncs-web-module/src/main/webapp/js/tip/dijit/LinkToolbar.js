dojo.provide("ibm.tivoli.tip.dijit.LinkToolbar"); 

dojo.require("dijit._Widget");

/**
 * Links toolbar.
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.LinkToolbar",
	[dijit._Widget, dijit._Templated],
	{
        /** the user's provided links */
        links: [],
        	    
        /** the widget template path */
        templatePath: dojo.moduleUrl("ibm.tivoli.tip.dijit", "templates/LinkToolbar.html"),

	    /** true if the toolbar is shown */
    	_isShown: true,
	    /** the link template */
        _linkTemplate:
            "<span class='linkToolbarItem'>" +
                "<img src='${img}' class='linkToolbarImage'/>" +
                "<a linkId='${linkId}' href='javascript:void(0);' class='linkToolbarLink'>${text}</a>" +
            "</span>", 
            
        /**
         * Starts-up the widget.
         */
        startup: function()
        {
            // call the superclass's method
            this.inherited("startup", arguments);
            // set the links
            this.setLinks(this.links);
        },
        
        /**        
         * Sets the given links into the toolbar.
         * 
         *  @param links
         *      A maps whose values are the links of the toolbar
         *      and the names are used to identify the links.
         */
        setLinks: function(links)
        {
            // populate the toolbar
            var s = [];
            for ( var i in links ) {
                // prepare the tamplate parameters
                if ( links[i].linkId === undefined ) {
                    links[i].linkId = i;
                }
                // valorize the template
                s[s.length] = dojo.string.substitute(this._linkTemplate, links[i]);
            }
            this.domNode.innerHTML = s.join("");
        }, 
               
        /**
         * Shows the toolbar.
         */
        show: function()
        {
    		if ( ! this._isShown ) {
    		    this._isShown = true;
    		}
        },

        /**
         * Hides the toolbar.
         */
        hide: function()
        {
    		if ( this._isShown ) {
    		    this._isShown = false;
    		}
        },
        
        /**
         * Returns true if the toolbar is currently shown.
         */
        isShown: function()
        {
            return this._isShown;   
        },

        /**        
         * Called when a toolbar link has been selected   
         */
        onLinkSelected: function(id)
        {
        },
        
        /**
         * Handles the onkeypress event
         */
        _onKeyPress: function(e)
        {
            this._onClick(e);
        }, 
        
        /**
         * Handles the onclick event
         */
        _onClick: function(e)
        {
            // check if the event comes from the link
            if ( e.target.tagName === "A" ) {
                this.onLinkSelected(e.target.getAttribute("linkId"));
                dojo.stopEvent(e);
            }
        } 
	}
);