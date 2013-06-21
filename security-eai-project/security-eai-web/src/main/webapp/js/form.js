/**
 * 显示提示信息。
 * 
 * @param type
 * @param txt
 */
function setMsg(type, txt) {
  var div = document.getElementById("infoDivMsg");
  if(type == "error"){
    div.className = "aui-message info invisible";
  }else{
    div.className = "aui-message error invisible";
  }
  var divMsg = document.getElementById(type +"DivMsg");
  divMsg.className = "aui-message " + type;
  var span = document.createElement("span");
  span.className = "aui-icon icon-" + type;
  divMsg.appendChild(span);
  var p = document.createElement("p");
  p.appendChild(document.createTextNode(txt));
  divMsg.appendChild(p);
}