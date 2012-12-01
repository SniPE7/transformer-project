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
    BIND_ERROR_MSG               : "<p><p>Wystąpił błąd przy przetwarzaniu żądania do serwera.<p><p><b>Szczegóły:</b> ${exc}<p><p>${excMsg}",

    BIND_TIMEOUT_ID              : "TIPMSG1001E",   // Do_Not_Translate 
    BIND_TIMEOUT_MSG             : "<p><p>Wykonywanie żądania do serwera trwało zbyt długo. ",

    JS_ERROR_ID                  : "TIPMSG1002E",   // Do_Not_Translate
    JS_ERROR_MSG                 : "<p><p>Wystąpił błąd przy przetwarzaniu odpowiedzi z serwera.<p><p><b>Szczegóły:</b> ${name} [${message}]<p><p><b>Miejsce:</b> wiersz ${lineNumber} w ${fileName}",

    XHR_ERROR_ID                 : "TIPMSG1003E",   // Do_Not_Translate
    XHR_ERROR_MSG                : "<p><p>Wystąpił błąd przy tworzeniu żądania do serwera.<p><p><b>Błąd:</b> ${message}"
 }
)

