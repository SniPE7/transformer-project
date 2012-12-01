<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>




<html>
<head>
<title>IBM Tivoli NetCool信息管理系统 - 登陆界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	var djConfig =
	{
		//locale: "en-us",
		locale: "zh-cn",
		isDebug: false,
		parseOnLoad: false,
		modulePaths:
		{
			"ibm.tivoli": "/js"
		}
	};
</script>
<script type="text/javascript">
	function chkMain(){
		if (top.location.href != self.location.href) {
			eval(top.location.href= self.location.href);
		}
	}
</script>

<link rel="stylesheet" type="text/css" href="./login.css">
</head>
<body onload="chkMain();">
<table class="main_tbl" cellpadding="0" cellspacing="5" align="center">

	<tr>
		<td align="left"></td>
		<td align="right"></td>
	</tr>
	<tr>
		<td class="dialog" colspan="2">

			<form method="POST" action="<%=request.getContextPath() %>/login.wss">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="2">
							<h1 id="login_heading"></h1>
								<c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
								</c:if>
						</td>
					</tr>
					<tr>
						<td rowspan="3" valign="top"></td>
						<td class="input_pad"><label id="username_lbl" for="j_username">用户名:</label>
							<br />
							<input size="40" id="user" name="j_username" value="${model.username}" type="text" /></td>
					</tr>
					<tr>
						<td class="input_pad"><label id="password_lbl" for="password">密码:</label>
							<br />

							<input size="42" id="password" name="j_password" value="${model.password}" type="password" /></td>
					</tr>
					<tr>
						<td align="right"><input id="login_btn" type="submit" value="登陆" class="btn"/></td>
					</tr>
				</table>
				</form>
	</td>
	</tr>
	<tr>
		<td colspan="2"><span id="copyright">&nbsp;</span></td>
	</tr>
</table>
<%--
 <script type="text/javascript">
        //eval(alert(111));
    	if (top.location.href != self.location.href) {
			eval(top.location.href= self.location.href);
		}
 </script>
 --%>
</body>
</html>
