<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>




<html>
<head>
<title>IBM Tivoli NetCool信息管理系统 - 登陆界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/login.css">
<script type="text/javascript">
	var djConfig =
	{
		locale: "en-us",
		isDebug: false,
		parseOnLoad: false,
		modulePaths:
		{
			"ibm.tivoli": "<%=request.getContextPath()%>/js"
		}
	};
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dojo/dojo.js"></script>
<script type="text/javascript">
	//dojo.requireLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");	
	
	//dojo.addOnLoad(function()
	//{
	//	var _uiStringTable = dojo.i18n.getLocalization("ibm.tivoli.simplesrm.srm.dijit", "uiStringTable");
	//	document.title = _uiStringTable.Title + " - " + _uiStringTable.Login;
	//	dojo.byId('username_lbl').innerHTML = _uiStringTable.Username;
	//	dojo.byId('password_lbl').innerHTML = _uiStringTable.Password;
	//	dojo.byId('login_heading').innerHTML = "Service Automation Manager";	
	//	dojo.byId('login_btn').value = _uiStringTable.Login;
	//	dojo.byId('copyright').innerHTML = _uiStringTable.copyright;
//	});

</script>
</head>
<body >
<table class="main_tbl" cellpadding="0" cellspacing="5" align="center">

	<tr>
		<td align="left"><img src="<%=request.getContextPath()%>/images/tivoli-brandmark.gif" alt="Tivoli" /></td>
		<td align="right"><img src="<%=request.getContextPath()%>/images/ibm-logo-white.gif" alt="IBM" /></td>
	</tr>
	<tr>
		<td class="dialog" colspan="2">

			<form method="POST" action="<%=request.getContextPath() %>/signon/login.wss">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="2">
							<h1 id="login_heading"></h1>
							<div id="errmsg">&nbsp;</div>
						</td>
					<tr>

						<td rowspan="3" valign="top"></td>
						<td class="input_pad"><label id="username_lbl" for="j_username">用户名:</label>
							<br />
							<input size="40" id="user" name="j_username" value="" type="text" /></td>
					</tr>
					<tr>
						<td class="input_pad"><label id="password_lbl" for="password">密码:</label>
							<br />

							<input size="40" id="password" name="j_password" value="" type="password" /></td>
					</tr>
					<tr>
						<td align="right"><input id="login_btn" type="submit" value="登陆" class="btn"/></td>
					</tr>
				</table>
				</form>
	</td>
	</tr>

	<tr>
		<td colspan="2"><span id="copyright">License Material - Property of IBM Corp. &copy; IBM Corporation and other(s) 2009. IBM is a registered trademark of the IBM Corporation in the United States, other countries, or both.</span></td>
	</tr>
</table>

</body>
</html>
