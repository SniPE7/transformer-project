<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String path = request.getContextPath();
      String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<link href="css/index_concise.css" rel="stylesheet" type="text/css" media="screen" />
<title>黑龙江移动_中国移动通信_用户登录</title>
<meta http-equiv="x-ua-compatible" content="ie=7" />
<script type="text/javascript" language="javascript">
  function trim(str) {
    return str;
  }

  function login_check() {
	  alert("true");
    document.getElementById('vlidate').style.display = 'none';
    if (trim(document.getElementById("loginName")).length == 0) {
      alert("必须输入手机号码!");
      document.getElementById("loginName").focus();
      return false;
    }
    ;
    if (document.getElementById("passwordType").options[document
        .getElementById("passwordType").selectedIndex].value == 1
        && !trim(document.getElementById("password").value).length == 6) {
      alert("服务密码必须是6位数字!");
      document.getElementById("password").focus();
      return false;
    }
    if (document.getElementById("passwordType").options[document
        .getElementById("passwordType").selectedIndex].value == 2
        && trim(document.getElementById("password").value).length < 1) {
      alert("必须输入通行证密码!");
      document.getElementById("password").focus();
      return false;
    }
    if (!trim(document.getElementById("rnum").value).match(
        /^[A-Za-z0-9]{4}$/)) {
      alert("验证码必须是4位字母或数字!");
      document.getElementById("rnum").focus();
      return false;
    }
    document.getElementById("loginButton").disabled = true;
    document.getElementById("loginMyMobileButton").disabled = true;
    alert("true");
    return true;
  }

  function chv() {
    document.getElementById("imgVid").src = "./jsp/check_code_img.jsp?id="
        + Math.random();
  }
</script>
</head>
<body>
  <div id="center">
    <div class="login_top"></div>
    <div class="login_center">
      <form name="fm" action="./service/authen/login" method="post" onSubmit="reutrn login_check()">
        <input type="hidden" name="SAMLRequest" value='<c:out value="${param.SAMLRequest}" escapeXml="false"/>' />
        <input type="hidden" name="RelayState" value='<c:out value="${param.RelayState}" escapeXml="false"/>' />
        <table border="0" align="center">
          <colgroup>
            <col width="90" align="right" />
            <col width="300" align="left" />
          </colgroup>
          <tr>
            <td align="right">手机号码：</td>
            <td><input type="text" id="loginName" class="input_bg" name="User-Name" maxlength="11" value="请输入手机号"
              onfocus="if(this.value=='请输入手机号'|| this.value=='请输入网站ID') this.value='';"
              onkeypress="javascript:if(event.keyCode==13)document.getElementById('password').focus();" /></td>
          </tr>
          <tr>
            <td align="right" nowrap="nowrap"><select name="Password-Type" id="passwordType"><option value="1">服务密码</option>
                <option value="1">网络通行证密码</option>
            </select>：</td>
            <td><input id="password" name="User-Password" type="password" class="rnum_bg" maxlength="6" size="8" /> <a
              href="#" style="text-decoration: underline" target="_blank"><span>忘记密码? 点击此处找回</span> </a>
            </td>
          </tr>
          <tr>
            <td align="right">验证码：</td>
            <td>
              <div style="width: 250px; *width: 300px; text-align: left;">
                <input type="text" name="check_code" id="rnum" class="rnum_bg" maxlength="4" size="8"
                  onkeypress="javascript:if(event.keyCode==13)logincheck();" />
              </div>
              <div
                style="text-align: center; position: absolute; z-index: 1; width: 90px; border: 1px solid #E6E6E6; background-color: #FFFFFF; margin-left: 120px; margin-top: -30px;"
                id="vlidate">
                <img id='imgVid' src='./jsp/check_code_img.jsp'></img>
                <div style="line-height: 23px;">
                  <a href="javascript:chv();" title="击点换一个验证码">看不清,换一个</a>
                </div>
              </div></td>
          </tr>
          <tr>
            <td width="390" colspan="2" align="center"><br /> <input id="loginButton" type="submit" name="loginButton"
              value="登录网上营业厅" class="login_btn" /></td>
          </tr>
        </table>
      </form>
    </div>
    <div class="login_bottom"></div>
  </div>

</body>
</html>
