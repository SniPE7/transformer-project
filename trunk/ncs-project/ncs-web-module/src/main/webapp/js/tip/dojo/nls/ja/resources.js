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
    BIND_ERROR_MSG               : "<p><p>サーバーへの要求を処理中にエラーが発生しました。<p><p><b>詳細:</b> ${exc}<p><p>${excMsg}",

    BIND_TIMEOUT_ID              : "TIPMSG1001E",   // Do_Not_Translate 
    BIND_TIMEOUT_MSG             : "<p><p>サーバーへの要求の完了に時間かかりすぎました。",

    JS_ERROR_ID                  : "TIPMSG1002E",   // Do_Not_Translate
    JS_ERROR_MSG                 : "<p><p>サーバーからの応答の処理中にエラーが発生しました。<p><p><b>詳細:</b> ${name} [${message}]<p><p><b>場所:</b> ${fileName} の ${lineNumber} 行目",

    XHR_ERROR_ID                 : "TIPMSG1003E",   // Do_Not_Translate
    XHR_ERROR_MSG                : "<p><p>サーバー要求の作成中にエラーが発生しました。<p><p><b>エラー:</b> ${message}"
 }
)

