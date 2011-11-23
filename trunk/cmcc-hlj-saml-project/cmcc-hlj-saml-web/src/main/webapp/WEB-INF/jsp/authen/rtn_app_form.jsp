<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>
<body onload="document.forms[1].submit();">
<h4>Return to application ... </h4>
	<form method="post" action="<c:out value="${SAMLAuthenRequest.samlIssuer}" escapeXml="yes"/>">
		<input type="hidden" name="SAMLart" value='<c:out value="${SAMLart}" escapeXml="yes"/>' />
		<input type="hidden" name="RelayState" value='<c:out value="${param.RelayState}" escapeXml="yes"/>' />
	</form>

</body>
</html>
