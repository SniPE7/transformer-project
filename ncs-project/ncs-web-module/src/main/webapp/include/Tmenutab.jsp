<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/include/include.jsp" %>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>
<style type="text/css">
@import "../dojo/dijit/themes/noir/noir.css";
</style>
<script type="text/javascript" src="../dojo/dojo/dojo.js"
	djConfig="isDebug: false, parseOnLoad: true"></script>
<script type="text/javascript">
dojo.require("dojo.parser");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
</script>







<style type="text/css">
@import "../dojo/dojo/resources/dojo.css";

@import "../dojo/dijit/themes/tundra/tundra.css";

@import "../dojo/dijit/themes/dijit.css";
</style>
<title>banner</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR"
	content="Rational® Application Developer™ for WebSphere® Software">
</head>
<body class="tundra">

<table border="0">
	<tbody>
		<tr >
			<td >
			<a href="#" onclick="tab1c.style.display='block'; tab2c.style.display='none'">TAB1</a> 
			</td><td>
			<a href="#" onclick="tab1c.style.display='none'; tab2c.style.display='block'">TAB2</a>
			</td>
		</tr>
		<tr>
			<td>
			<div id="tab1c">
                <a href="<%=request.getContextPath() %>/secure/baseinfo/naviBaseInfo.jsp" target="navigator">Base INFO</a>
                &nbsp;|&nbsp;
                <a href="<%=request.getContextPath() %>/secure/baseinfo/maintaindb.jsp" target="navigator">Maintain DB</a>
                </div>
                </td><td>
			<div id="tab2c">
				2ndtab  menu
			</div>
			</td>
		</tr>
	</tbody>
</table>
</body>
</html>