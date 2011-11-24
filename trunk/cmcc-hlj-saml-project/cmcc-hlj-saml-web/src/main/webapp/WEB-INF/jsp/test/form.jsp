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

   <h4>Create a SAML ID on specified user.</h4>
   <hr/>
   <form action="/samlsvc/service/create" method="get">
   MSISDN: <input type="text" name="msisdn" value="" size="32" maxlength="32"><br/>
   <input type="submit">
   </form>

   <br/><br/>
   
   <h4>Send Activiate SAML Request</h4>
   <hr/>
   <form action="/samlsvc/service/activiate" method="get">
   Cookie ID (SAML ID): <input type="text" name="id" value="" size="32" maxlength="32"><br/>
   SAML Server IP/Port: <input type="text" name="hostname" value="${activiateClient.serverName}" size="32"> <input type="text" name="port" value="${activiateClient.serverPort}" size="6"><br/>
   <input type="submit">
   </form>

   <br/><br/>
   
   <h4>Send Qurey Attribute SAML Request</h4>
   <hr/>
   <form action="/samlsvc/service/query" method="get">
   Cookie ID (SAML ID): <input type="text" name="id" value="" size="32" maxlength="32"><br/>
   SAML Server IP/Port: <input type="text" name="hostname" value="${queryAttributeClient.serverName}" size="32"> <input type="text" name="port" value="${queryAttributeClient.serverPort}" size="6"><br/>
   <input type="submit">
   </form>

   <br/><br/>
   <h4>Send Global Logout SAML Request</h4>
   <hr/>
   <form action="/samlsvc/service/logout" method="get">
   Cookie ID (SAML ID): <input type="text" name="id" value="" size="32" maxlength="32"><br/>
   SAML Server IP/Port: <input type="text" name="hostname" value="${logoutClient.serverName}" size="32"> <input type="text" name="port" value="${logoutClient.serverPort}" size="6"><br/>
   <input type="submit">
   </form>

   <br/><br/>
   <h4>Send Password Reset SAML Request</h4>
   <hr/>
   <form action="/samlsvc/service/pwdreset" method="get">
   UserName: <input type="text" name="userName" value="" size="32" maxlength="32"><br/>
   ServiceCode: <input type="text" name="serviceCode" value="" size="32" maxlength="32"><br/>
   Network Password: <input type="text" name="networkPassword" value="" size="32" maxlength="32"><br/>
   SAML Server IP/Port: <input type="text" name="hostname" value="${logoutClient.serverName}" size="32"> <input type="text" name="port" value="${logoutClient.serverPort}" size="6"><br/>
   <input type="submit">
   </form>
   
   <hr/>
   <h4>Send ArtifactResolv SAML Request</h4>
   <hr/>
   <form action="/samlsvc/service/resolv" method="get">
   Cookie ID (SAML ID): <input type="text" name="id" value="" size="32" maxlength="32"><br/>
   SAML Server IP/Port: <input type="text" name="hostname" value="${queryAttributeClient.serverName}" size="32"> <input type="text" name="port" value="${queryAttributeClient.serverPort}" size="6"><br/>
   <input type="submit">
   </form>

   <hr/>
   <h4><a href="./authen/test_from_app.jsp">SSO Application Login</a></h4>
   <hr/>

   
  </body>
</html>
