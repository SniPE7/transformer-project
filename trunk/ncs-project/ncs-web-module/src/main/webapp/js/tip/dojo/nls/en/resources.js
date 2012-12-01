/****************************************************** {COPYRIGHT-TOP} ***
* Licensed Materials - Property of IBM
* xxxx-xxx
*
* (C) Copyright IBM Corp. 2007
*
* US Government Users Restricted Rights - Use, duplication, or
* disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
********************************************************** {COPYRIGHT-END} **/

(
 {
    // NOTE: the next item is an actual number NOT a string.  Specifies maximum size of the exception message.
    MAX_WIDTH_EXC_MSG            : 500,    // Do_Not_Translate 
    // If an exception message is truncated, the below string will be shown in its place          
    TEXT_CROPPED                 : "...",
    
    BIND_ERROR_ID                : "TIPMSG1000E",   // Do_Not_Translate
    BIND_ERROR_MSG               : "<p><p>An error occurred while processing the request to the server.<p><p><b>Detail:</b> ${exc}<p><p>${excMsg}",

    BIND_TIMEOUT_ID              : "TIPMSG1001E",   // Do_Not_Translate 
    BIND_TIMEOUT_MSG             : "<p><p>A request to the server took too long to complete.",

    JS_ERROR_ID                  : "TIPMSG1002E",   // Do_Not_Translate
    JS_ERROR_MSG                 : "<p><p>An error occurred while processing the response from the server.<p><p><b>Detail:</b> ${name} [${message}]<p><p><b>At:</b> line ${lineNumber} of ${fileName}",

    XHR_ERROR_ID                 : "TIPMSG1003E",   // Do_Not_Translate
    XHR_ERROR_MSG                : "<p><p>An error occurred while making the server request.<p><p><b>Error:</b> ${message}"
 }
)
