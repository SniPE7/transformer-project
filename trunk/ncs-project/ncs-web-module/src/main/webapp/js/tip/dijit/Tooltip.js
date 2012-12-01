dojo.provide("ibm.tivoli.tip.dijit.Tooltip"); 

dojo.require("dijit._Widget");
dojo.require("dijit.Tooltip");

/**
 * Tooltip. 
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.Tooltip",
    [dijit._Widget],
    {
        /** the time (in ms) the tooltip is shown after a splash call */
        splashTime: 2000,
        
        /** the node used to attach the tooltip message */
        _tooltipNode: null,
        /** tooltip widget */
        _tooltip: null,
        /** the close timer (set if a close tooltip has been scheduled) */
        _closeTimer: null,
        /** if true the tooltip must be regularly shown */
        _enabled: true,
       
        constructor: function(params)
        {
        	if(undefined !== params && undefined !== params.enabled)
	            this._enabled = params.enabled;
        },
        
        /**
         * Starts-up the widget.
         */
        startup: function()
        {
            this._tooltip = new dijit.Tooltip();
            this._tooltip.startup();
        },
        
        /**
         * Destroys the widget.
         */
        destroy: function()
        {
            // destroy the tooltip
            this.hide();
            this._tooltip.destroy();
            // call the superclass's method
            this.inherited(arguments);
        },
        
        /**
         * Sets the tooltip node.
         */
        setNode: function(tooltipNode)
        {
            this._tooltipNode = tooltipNode;
        },
        
        /**
         * Shows the message tooltip containing the given message.
         */
        show: function(message)
        {
            // do not show if not enabled
            if ( this._enabled === false ) {
                return;
            }
            // close the previous tooltip (if any)
            if ( this.isShown() ) {
               this.hide();
            }
            if ( message !== "" && this._tooltipNode ) {
                // open the new tooltip
                this._tooltip.label = message;
                this._tooltip.open(this._tooltipNode);
            }
        },
        
        /**
         * Opens the message tooltip and hides it after a while.
         */
        splash: function(message)
        {
            // do not show if not enabled
            if ( this._enabled === false ) {
                return;
            }
            this.show(message);
            this._closeTimer = setTimeout(dojo.hitch(this, this.hide), this.splashTime);
        },
        
        /**
         * Hides the message tooltip.
         */
        hide: function()
        {
            if ( this.isShown() ) {
                if ( this._closeTimer ){
                    clearTimeout(this._closeTimer);
                    delete this._closeTimer;
                }
                this._tooltip.close();
            }
        },
        
        /**
         * Returns true if the message tooltip is currently shown.
         */
        isShown: function()
        {
            return ( !! this._tooltip._connectNode );   
        }
    }
);