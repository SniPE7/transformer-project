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
    BIND_ERROR_MSG               : "<p><p>Hiba történt a kiszolgálóra küldött kérés feldolgozása közben.<p><p><b>Részletek:</b> ${exc}<p><p>${excMsg}",

    BIND_TIMEOUT_ID              : "TIPMSG1001E",   // Do_Not_Translate 
    BIND_TIMEOUT_MSG             : "<p><p>A kiszolgálóra küldött kérés végrehajtása túlságosan sok időt vett igénybe.",

    JS_ERROR_ID                  : "TIPMSG1002E",   // Do_Not_Translate
    JS_ERROR_MSG                 : "<p><p>Hiba történt a kiszolgálóról érkező válasz feldolgozása közben.<p><p><b>Részletek:</b> ${name} [${message}]<p><p><b>Hely:</b> a(z) ${fileName} fájl ${lineNumber}. sora",

    XHR_ERROR_ID                 : "TIPMSG1003E",   // Do_Not_Translate
    XHR_ERROR_MSG                : "<p><p>Hiba történt a kiszolgálókérés összeállítása közben.<p><p><b>Hiba:</b> ${message}"
 }
)

