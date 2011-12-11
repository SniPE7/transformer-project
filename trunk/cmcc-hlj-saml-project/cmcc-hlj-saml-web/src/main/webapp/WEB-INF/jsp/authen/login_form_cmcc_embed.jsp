<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <base href="<%=basePath%>"></base>
    <title>黑龙江移动统一认证登录</title>

    <style type="text/css">
<!--
body{
  font-family: "宋体";
  font-size: 12px;
  color: #000000;
}
form{
  margin:0px;
}
#unLogin_Area{
  width:200px;
  height:229px;
  background-color:#f0f6ff;
  float:left;
}
#login_top{
  border:0px;
  height:222px;
  background:url(./images/login_bg.jpg);
}
.login_top_title{
  font-weight:bold;
}
.input_text{
  width:82px;
  height:14px;
  border:1px solid #b8b8b8;
  color:#525252;
}
.input_runm{
  width:50px;
}
.login_type{
  width:92px;
}
.link_blue a{
  color:#3381e2;
  text-decoration:none;
  font-size:12px;
  padding-left:10px;
}
.not_reg{
  font-weight:bold;
}
.login_button{
  border:0px;   
  background:url("./images/login_button.jpg") no-repeat;
  width:115px;
  height:24px;
  cursor:hand ; 
}

.login_text{
  color:#3381e2;
  text-decoration:none;
  padding-left:5px;
}
-->
</style>
    <script type="text/JavaScript">
<!--
 
//只允许输入数字
function checknumonly(){
  if( (!window.event.shiftKey && !window.event.shiftLeft
     &&((window.event.keyCode>=96 && window.event.keyCode<=105)
      ||(window.event.keyCode>=48 && window.event.keyCode<=57)))//未按shift键时可用数字及一些控制键
    ||window.event.keyCode==8       //BackTab
    ||window.event.keyCode==9       //Tab
    ||window.event.keyCode==13      //Enter
    ||window.event.keyCode==46      //Delete
    ||window.event.keyCode==36      //Home
    ||window.event.keyCode==35      //End
    ||window.event.keyCode==37
    ||window.event.keyCode==39
    ||(window.event.ctrlKey || window.event.ctrlLeft) //ctrl+C,V,X
     ){
    return true;
  }else{
    return false;
  }
}
 
//判断手机号
function ValidatePhoneNo(phone_no){
  if ((phone_no.indexOf("134")==0)||
    (phone_no.indexOf("135")==0)||
    (phone_no.indexOf("136")==0)||
    (phone_no.indexOf("137")==0)||
    (phone_no.indexOf("138")==0)||
    (phone_no.indexOf("139")==0)||
    (phone_no.indexOf("150")==0)||
        (phone_no.indexOf("157")==0)||
        (phone_no.indexOf("158")==0)||
        (phone_no.indexOf("159")==0)||
        (phone_no.indexOf("151")==0)||
        (phone_no.indexOf("152")==0)||
        (phone_no.indexOf("147")==0)||
        (phone_no.indexOf("187")==0)||
        (phone_no.indexOf("188")==0)){
    return true;
  }
  else{
    return false;
  }
}
 
function isInt(s){
  var len=s.length
  var i
  var j
  var flag=false
  for(i=0;i<len;++i){
    var xxx=s.charAt(i)
    if( !(xxx == "0" || xxx == "1" || xxx == "2"|| xxx == "3"|| xxx == "4"|| xxx == "5"|| xxx == "6"|| xxx == "7"|| xxx == "8"|| xxx == "9") ) 
      return false
  }
  return true
}        
function isPhoneEmpty(userPhone) 
{
  objValue=userPhone.value;
  var iLength=userPhone.length;
  
  if(objValue==""){
    alert('手机号不能为空!');
//    userPhone.value = "";
    userPhone.focus();
    return false;
  }
  if(objValue.length!=11){
    alert('请正确输入11位手机号码!');
//    userPhone.value = "";
    userPhone.focus();
    return false;
  } 
  if(!isInt(objValue)){
    alert('手机号码请输入数字!');
//    userPhone.value = "";
    userPhone.focus();
    return false;
  }
  
  if(!ValidatePhoneNo(objValue)){
    alert('对不起！您不是黑龙江省的移动用户！');
//      userPhone.value = "";
    userPhone.focus();
    return false;
  }                                   
  return true;
}

function logincheck1() {
  if (!isPhoneEmpty(document.getElementById("loginId"))) {
    return false;
  }
  document.getElementById("verify_code").value = document.getElementById("verify_code1").value;
  document.getElementById("verify_code1").value = "";
  document.getElementById("loginbutton").disabled=true;
  document.loginForm.submit();
  return false;
}
function showVeriImage() {
  if (document.getElementById("veriImageDiv").style.display == "none") {
    document.getElementById("veriImage").src = "./check_code_img";
    document.getElementById("veriImageDiv").style.display = "block";
  }
}
function testlogin() {
  document.getElementById("loginId").value = "13613623842";
  document.getElementById("loginPassword").value = "111111";
}

 function logincheck(){
    if(!isPhoneEmpty(document.getElementById("loginId"))) 
  {
  return false;
    }
  var password = trim(document.getElementById("loginPassword").value);
  if(password.length>8 || password.length<6){
    alert("通行证密码必须是6-8位数字");
    document.getElementById("loginPassword").focus();
    return false;
  }
        verify_code1=trim(document.getElementById("verify_code1").value);
        if(verify_code1.length<4){
           alert("请输入验证码!");
    document.getElementById("verify_code1").focus();
    return false;
  }
  
  document.getElementById("verify_code").value = document.getElementById("verify_code1").value;
  document.getElementById("verify_code1").value = "";
  document.getElementById("loginbutton").disabled=true;
  document.loginForm.submit();
  return false;     
}


function trim(inputStr) {
  var result = inputStr;
  while (result.substring(0,1) == " ") {
    result = result.substring(1,result.length);
  }
  
  while (result.substring(result.length-1, result.length) == " ") {
    result = result.substring(0, result.length-1);
  }
    
  return result;
}
//-->
</script>
  </head>
  <body>
    <div id="unLogin_Area">
      <div id="login_top">
        <form name="loginForm" action="./service/authen/login" method="post">
          <input type="hidden" name="SAMLRequest" value='<c:out value="${param.SAMLRequest}" escapeXml="false"/>' />
          <input type="hidden" name="RelayState" value='<c:out value="${param.RelayState}" escapeXml="false"/>' />
          <input type="hidden" name="login_page_style" value='<c:out value="${param.login_page_style}"/>'/>
          <input type="hidden" name="continue" value='<c:out value="${param.continue}"/>'/>
          <table width="200" height="85" border="0" align="center"
            cellpadding="0" cellspacing="0">
            <tr>
              <td align="left" colspan="3" height="23" valign="bottom">
              <c:if test="${__ERROR_MESSAGE_SAML_WEB_MODULE__ == null}">
                &nbsp;
                <strong>已经拥有移动通行证</strong>
              </c:if>
              <c:if test="${__ERROR_MESSAGE_SAML_WEB_MODULE__ != null}">
                &nbsp;
                <strong><font color="red"><c:out value="${__ERROR_MESSAGE_SAML_WEB_MODULE__}"/></font></strong>
              </c:if>
              </td>
            </tr>
            <tr>
              <td height="12" align="center" valign="middle" colspan="3">
                <img src="images/line.jpg" width="184" height="1" />
              </td>
            </tr>
            <tr>
              <td width="10" height="25" align="left">
                &nbsp;
              </td>
              <td width="97" align="left">
                <select name="loginType" class="login_type">
                  <option>
                    手机号码
                  </option>
                </select>
              </td>
              <td width="93" align="left">
                <input name="User-Name" id="loginId" type="text"
                  value="<c:out value='${UserName}'/>" default="请输入手机号"
                  class="input_text" size="12" maxlength="11"
                  onfocus="if(this.value=='请输入手机号'|| this.value=='请输入网站ID') this.value='';"
                  onkeydown="return checknumonly()"
                  onkeypress="javascript:if(event.keyCode==13)document.getElementById('loginPassword').focus(); " />
              </td>
            </tr>
            <tr>
              <td height="25" align="left">
                &nbsp;
              </td>
              <td align="left">
                <select name="Password-Type" class="login_type">
                  <option selected="selected">
                    服务密码
                  </option>
                  <option>
                    通行证密码
                  </option>
                </select>
              </td>
              <td align="left">
                <input name="User-Password" type="password" id="loginPassword"
                  class="input_text" size="12" maxlength="8" />
              </td>
            </tr>
          </table>
          <table width="200" height="85" border="0" cellpadding="0"
            cellspacing="0">
            <tr>
              <td width="55" align="right" height="25" >
                <label>
                  验证码:
                </label>
              </td>
              <td width="60" align="left">
                <input type="text" name="verify_code1" id="verify_code1"
                  value="请点击" size="6" maxlength="4"
                  onclick="if(this.value=='请点击') this.value='';showVeriImage();"
                  class="input_runm"
                  onkeypress="javascript:if(event.keyCode==13)logincheck();" />
                <input type="hidden" name="check_code" id="verify_code" />
              </td>
              <td width="85" align="left">
                <div id="veriImageDiv" style="display: none">
                  <img id="veriImage" src="./check_code_img" />
                </div>
              </td>
            </tr>
            <tr>
              <td width="200" height="25" colspan="3">
                <table>
                  <tr>
                    <td width="70">
                      <a href="#" class="login_text">忘记密码？</a>
                    </td>
                    <td width="130">
                      <table>
                        <tr>
                          <td>
                            <c:forEach var="item" items="${requestScope.errInfo }"
                              varStatus="status">
                              <tr>
                                <td style="color: red">
                                  <c:out value="${item}" />
                                </td>
                              </tr>
                            </c:forEach>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td height="25" align="center" colspan="3">
                <input type="button" id="loginbutton" name="loginbutton"
                  class="login_button" value="" onclick="return logincheck();" />
              </td>
            </tr>
            <tr>
              <td height="10" align="center" valign="middle" colspan="3">
                <img src="./images/line.jpg" width="184" height="1" />
              </td>
            </tr>
          </table>
          <table width="200" height="52" border="0" cellpadding="0"
            cellspacing="0">
            <tr>
              <td width="11" height="32">
                &nbsp;
              </td>
              <td width="102" align="left" valign="middle" class="not_reg">
                我还没有通行证
              </td>
              <td width="87" align="left" valign="middle">
                <a href="javascript:testlogin();"><img
                    src="./images/reg_button.jpg" width="61" height="24" border="0" />
                </a>
              </td>
            </tr>
            <tr>
              <td height="20">
                &nbsp;
              </td>
              <td colspan="2" class="link_blue">
                <a href="#">为什么注册移动通行证？</a>
              </td>
            </tr>
          </table>
        </form>
      </div>
      <table width="200" height="7" border="0" align="center"
        cellpadding="0" cellspacing="0">
        <tr>
          <td>
            <img src="./images/bottom.jpg" width="200" height="7" /></td>
        </tr>
      </table>
    </div>
  </body>
</html>

