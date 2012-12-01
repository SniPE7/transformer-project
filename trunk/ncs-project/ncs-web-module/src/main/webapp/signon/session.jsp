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




						<% 
						java.util.Map sessions = com.ibm.ncs.web.HttpSessionList.getSessions();
						out.print(sessions);
						%>
						
	


</body>
</html>
