<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>SAMPL Client Login Form</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" href="css/start/jquery-ui-1.8.14.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
	function change_check_code() {
		document.getElementById("imgVid").src = "./jsp/check_code_img.jsp?id="
				+ Math.random();
	}

	$(function() {

		// Accordion
		$("#accordion").accordion({
			collapsible : true,
			icons : false,
			autoHeight : false,
		});

		$("#login").button();
		$("#reset").button();
		//$("#Password-Type").combobox();

		$("#reset").click(function() {
			$("#accordion").effect("shake", 219);
		});

		$("#login").click(function() {
			if ($("#UserName").attr("value").length != 11) {
				$("#messageInvalidateUsername").dialog({
					position : [ 200, 200 ],
					height : 120,
					width : 360,
					resizable : false,
					modal : true,
					buttons : {
						"确定" : function() {
							$(this).dialog("close");
							$("#UserName").focus();
						}
					}
				});
				return;
			}
			;
			passType = $("#PasswordType").attr("value");
			if (passType == 1 && $("#UserPassword").attr("value").length < 6) {
				$("#messageInvalidateNetPassword").dialog({
					position : [ 200, 200 ],
					height : 120,
					width : 360,
					resizable : false,
					modal : true,
					buttons : {
						"确定" : function() {
							$(this).dialog("close");
							$("#UserPassword").focus();
						}
					}
				});
				return;
			}
			;
			if (passType == 2 && $("#UserPassword").attr("value").length != 6) {
				$("#messageInvalidateServicePassword").dialog({
					position : [ 200, 200 ],
					height : 120,
					width : 360,
					resizable : false,
					modal : true,
					buttons : {
						"确定" : function() {
							$(this).dialog("close");
							$("#UserPassword").focus();
						}
					}
				});
				return;
			}
			;
			document.LoginForm.submit();
		});

	});
</script>
<style type="text/css">
/*demo page css*/
body {
	font: 68.5% "Trebuchet MS";
	margin: 0px;
	background-color: #222222
}

input.text {
	margin-bottom: 12px;
	width: 30%;
	padding: .4em;
}

input {
	display: inheritinline;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 10px;
}

.loginLabel {
  display:-moz-inline-box;
  display:inline-block;
  width: 90px;
  text-align: right;
}
</style>
</head>
<body>
	<div id="messageInvalidateUsername" style="display: none;" title="信息输入不完整">
		<div>请输入正确的手机号码!</div>
	</div>
	<div id="messageInvalidateNetPassword" style="display: none;" title="信息输入不完整">
		<div>请输入有效的互联网密码, 不少于6个字符!</div>
	</div>
	<div id="messageInvalidateServicePassword" style="display: none;" title="信息输入不完整">
		<div>请输入有效的服务密码, 必须为6位数字!</div>
	</div>

	<div class="demo">

		<div id="accordion" style="width: 480px; margin-left: 200px; margin-top: 40px;">
			<h3>
				<a href="#">登录</a>
			</h3>
			<div>
				<form id="LoginForm" name="LoginForm" action="./service/authen/login" method="post">
					<input type="hidden" name="SAMLRequest" value='<c:out value="${param.SAMLRequest}" escapeXml="false"/>' /> <input
						type="hidden" name="RelayState" value='<c:out value="${param.RelayState}" escapeXml="false"/>' />
					<fieldset>
						<label for="name" class="loginLabel">手机号码 :</label> <input type="text" name="User-Name" id="UserName"
							class="text ui-widget-content ui-corner-all" maxlength="11" /><br />
						<select name="Password-Type"
							id="PasswordType" class="loginLabel"><option value="1">互联网密码</option>
							<option value="2" selected="selected">服务密码</option>
						</select> <input type="password" name="User-Password" id="UserPassword" value=""
							class="text ui-widget-content ui-corner-all" style="width: 100px;"/><br />
					  <label for="name" class="loginLabel">验证码 :</label> <input type="text"
							name="check_code" id="check_code" class="text ui-widget-content ui-corner-all" maxlength="4"  style="width: 60px;"/>
             <img id='imgVid' src='./jsp/check_code_img.jsp'></img> <a href="javascript:change_check_code();" title="击点换一个验证码">看不清,换一个</a>
					</fieldset>
					<div id="login">登录</div>
					<div id="reset">重置</div>
				</form>
			</div>

			<h3>
				<a href="#">注册</a>
			</h3>
			<div>
				<p>Sed non urna. Donec et ante. Phasellus eu ligula. Vestibulum sit amet purus. Vivamus hendrerit, dolor at
					aliquet laoreet, mauris turpis porttitor velit, faucibus interdum tellus libero ac justo. Vivamus non quam. In
					suscipit faucibus urna.</p>
			</div>
			<h3>
				<a href="#">关于</a>
			</h3>
			<div>
				<p>Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis. Phasellus pellentesque purus in
					massa. Aenean in pede. Phasellus ac libero ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis
					lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui.</p>
				<ul>
					<li>List item one</li>
					<li>List item two</li>
					<li>List item three</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- End demo -->
</body>
</html>
