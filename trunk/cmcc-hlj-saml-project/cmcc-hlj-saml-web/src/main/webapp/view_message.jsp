<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>SAMPL Client Message Viewer</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <c:if test="${responseXML != null}">
      <pre><c:out value="${responseXML}" escapeXml="yes"></c:out></pre><br/>
    </c:if>
    <c:if test="${msisdn != null}">&nbsp;MSISDN: <c:out value="${msisdn}"/><br/></c:if>
    <c:if test="${uniqueIdentifier != null}">SAML ID: <c:out value="${uniqueIdentifier}"/><br/></c:if>
  </body>
</html>
