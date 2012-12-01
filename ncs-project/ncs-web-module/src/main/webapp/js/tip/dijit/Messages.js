/*****************************************************************************
*         Licensed Materials - Property of IBM
*
*         5698WSH
*
*         (C) Copyright IBM Corp. 2006-2007 All Rights Reserved.
*
*         US Government Users Restricted Rights - Use, duplication,
*         or disclosure restricted by GSA ADP Schedule Contract with
*         IBM Corp.
*
******************************************************************************/

dojo.provide("ibm.tivoli.tip.dijit.Messages");

dojo.requireLocalization("ibm.tivoli.tip.dijit", "Messages");
dojo.require("ibm.tivoli.tip.trace.Trace");
dojo.require("dojo.i18n");

// messages cache
ibm.tivoli.tip.dijit.Messages._messages = [];
ibm.tivoli.tip.dijit.Messages._trace = new ibm.tivoli.tip.trace.Trace("ibm.tivoli.tip.dijit.Messages");

ibm.tivoli.tip.dijit.Messages.get = function() 
{
    var trace = ibm.tivoli.tip.dijit.Messages._trace;
    
    if ( ibm.tivoli.tip.dijit.Messages._messages[dojo.locale] ) {
        // the messages has been already loaded
        return ibm.tivoli.tip.dijit.Messages._messages[dojo.locale]; 
    }
    else {
        var messages;
        // messages not yet laoded, load it
        try { 
            messages = dojo.i18n.getLocalization("ibm.tivoli.tip.dijit", "Messages");
        }
        catch ( e ) {
            // the catalog has not been found, try with the english locale
            trace.warn(e.message);
            if ( dojo.locale !== "en" ) {
                trace.info("trying to load the english messages");
                try { 
                    // load the english catalog
                    dojo.requireLocalization("ibm.tivoli.tip.dijit", "Messages", "en");
                    messages = dojo.i18n.getLocalization("ibm.tivoli.tip.dijit", "Messages", "en");
                }
                catch ( ex ) {
                    // the catalog has not been found
                    trace.error(ex.message);
                    trace.error("unable to load the default catalog");
                    // rethrow
                    throw ex;
                }
            }
        }
        
        // set the get message method
        messages.get = 
            function(id, params, showId)
            {    
                var message = "";
                
                // insert the id if required 
                if ( showId ) {
                     message = id + " ";
                }
                
                // get the message from the messages by id                
                if ( this[id] !== undefined ) {
                    // append the message
                    message = message + this[id];
                    // replace parameters if any
                    if ( params ) {
                        for ( var index = 0; index < params.length; index++ ) {
                            var token = "{" + index + "}";
                            message = message.replace(token, params[index]); 
                        }
                    }
                   } 
                else {
                    // message not found
                    ibm.tivoli.tip.dijit.Messages._trace.warn("message for id " + id + " not found");
                  }

                return message;
            };
     
        // store the messages into the cache
        ibm.tivoli.tip.dijit.Messages._messages[dojo.locale] = messages;
        // return the messages
        return messages;
    }  
};
