dojo.provide("ibm.tivoli.tip.dijit.LayoutContainer"); 

dojo.require("dijit.layout.LayoutContainer");
dojo.require("dijit.layout.ContentPane");

/**
 * This layout-container enhances the Dojo layout-container
 * by adding the following functionality:
 * 
 *      - if one of the contained content-panes changes its content
 *        by calling the setContent method, the layout-container 
 *        automatically re-layouts.
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.LayoutContainer",
    [dijit.layout.LayoutContainer],
    {
        /**
         * Starts-up the layout-container.
         * The contained content-panes are dynamically patched
         * in order to notify the layout-container about a content
         * change.   
         */
        startup: function() 
        {
            // call the superclass's method
            this.inherited("startup", arguments);

            // iterate on all the children
            var c = this.getChildren();
            for ( var i = 0; i < c.length; i++ ) {
                var child = c[i];
                // if the child has the setContent method (the ContentPane does)
                if ( child.setContent ) {
                    // replace the setContent method with one which calls
                    // the actual setContent method and then calls the layout
                    // method of the parent layout-container
                    child.setContent = dojo.hitch( 
                        {layout: this, child: child}, 
                        function() {
                            // call the actual setContent method
                            dijit.layout.ContentPane.prototype.setContent.call(this.child, arguments[0]);
                            // call the parent layout-container layout method
                            this.layout.layout();
                        }
                    );
                }
            }
        }
        
    }
);

