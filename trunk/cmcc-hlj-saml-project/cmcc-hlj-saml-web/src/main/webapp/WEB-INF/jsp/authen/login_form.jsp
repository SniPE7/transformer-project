<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>SAMPL Client Login Form</title>
    
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

   <h4>SSO Login Form</h4>
   <hr/>
   
   <form action="./service/authen/login" method="post">
   <input type="hidden" name="SAMLRequest" value='<c:out value="${param.SAMLRequest}" escapeXml="false"/>'/>
   <input type="hidden" name="RelayState" value='<c:out value="${param.RelayState}" escapeXml="false"/>'/>
   MSISDN: <input type="text" name="User-Name" value="13804511234" size="32" maxlength="32"><br/>
   Password-Type: <input type="radio" name="Password-Type" value="1" checked="checked">互联网密码&nbsp;&nbsp;&nbsp;<input type="radio" name="Password-Type" value="2">服务密码<br/>
   Password: <input type="text" name="User-Password" value="1111" size="32" maxlength="32"><br/>
   <input type="submit">
   </form>

  </body>
</html>
