<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>
<script type="text/javascript" src="../../dojo/dojo/dojo.js"
	djConfig="isDebug: false, parseOnLoad: true"></script>
<style type="text/css">
@import "../../dojo/dojo/resources/dojo.css";

@import "../../dojo/dijit/themes/noir/noir.css";

@import "../../dojo/dijit/themes/dijit.css";
</style>
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<title>Action Result</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR"
	content="Rational® Application Developer™ for WebSphere® Software">
</head>
<body class="noir">
<div>${error} </div>
</body>
</html>