<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>SAMPL Client Test Tool</title>
    
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

   <h4>Send Activiate SAML Request</h4>
   <hr/>
   <form action="./service/activiate" method="get" target="SAMLResponse">
   Cookie ID (SAML ID): <input type="text" name="id" value="" size="32" maxlength="32"><br/>
   SAML Server IP/Port: <input type="text" name="hostname" value="${connectionManager.serverName}" size="32">
   <input type="text" name="port" value="${connectionManager.serverPort}" size="6">
   <input type="radio" name="protocol" value="TCP">TCP <input type="radio" name="protocol" value="SSL" checked="checked">SSL <input type="radio" name="protocol" value="TLS">TLS
   <br/>
   <input type="submit">
   </form>

   <br/><br/>
   
   <h4>Send Qurey Attribute SAML Request</h4>
   <hr/>
   <form action="./service/query" method="get" target="SAMLResponse">
   Cookie ID (SAML ID): <input type="text" name="id" value="" size="32" maxlength="32"><br/>
   SAML Server IP/Port: <input type="text" name="hostname" value="${connectionManager.serverName}" size="32"> <input type="text" name="port" value="${connectionManager.serverPort}" size="6">
   <input type="radio" name="protocol" value="TCP">TCP <input type="radio" name="protocol" value="SSL" checked="checked">SSL <input type="radio" name="protocol" value="TLS">TLS<br/>
   <input type="submit">
   </form>

   <br/><br/>
   <h4>Send Password Reset SAML Request</h4>
   <hr/>
   <form action="./service/pwdreset" method="get" target="SAMLResponse">
   UserName: <input type="text" name="userName" value="" size="32" maxlength="32"><br/>
   ServiceCode: <input type="text" name="serviceCode" value="" size="32" maxlength="32"><br/>
   Network Password: <input type="text" name="networkPassword" value="" size="32" maxlength="32"><br/>
   SAML Server IP/Port: <input type="text" name="hostname" value="${connectionManager.serverName}" size="32"> <input type="text" name="port" value="${connectionManager.serverPort}" size="6">
   <input type="radio" name="protocol" value="TCP">TCP <input type="radio" name="protocol" value="SSL" checked="checked">SSL <input type="radio" name="protocol" value="TLS">TLS<br/>
   <input type="submit">
   </form>
   
   <br/><br/>
   <hr/>
   <h4>Send ArtifactResolv SAML Request</h4>
   <hr/>
   <form action="./service/resolv" method="get" target="SAMLResponse">
   Cookie ID (SAML ID): <input type="text" name="id" value="" size="32" maxlength="32"><br/>
   SAML Server IP/Port: <input type="text" name="hostname" value="${connectionManager.serverName}" size="32"> <input type="text" name="port" value="${connectionManager.serverPort}" size="6">
   <input type="radio" name="protocol" value="TCP">TCP <input type="radio" name="protocol" value="SSL" checked="checked">SSL <input type="radio" name="protocol" value="TLS">TLS<br/>
   <input type="submit">
   </form>

   <br/><br/>
   <h4>Send Global Logout SAML Request</h4>
   <hr/>
   <form action="./service/logout" method="get" target="SAMLResponse">
   Cookie ID (SAML ID): <input type="text" name="id" value="" size="32" maxlength="32"><br/>
   SAML Server IP/Port: <input type="text" name="hostname" value="${connectionManager.serverName}" size="32"> <input type="text" name="port" value="${connectionManager.serverPort}" size="6">
   <input type="radio" name="protocol" value="TCP">TCP <input type="radio" name="protocol" value="SSL" checked="checked">SSL <input type="radio" name="protocol" value="TLS">TLS<br/>
   <input type="submit">
   </form>

  </body>
</html>
