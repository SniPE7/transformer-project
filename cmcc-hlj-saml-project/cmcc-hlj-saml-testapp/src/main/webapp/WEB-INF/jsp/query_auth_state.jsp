<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="false"%>
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
<body onload="document.forms[0].submit();">
	<form method="post" action="<c:out value="${ssoSAMLAuthRequestURL}"/>">
    RelayState :<input type="text" name="RelayState" value='<c:out value="${RelayState}"/>' /><br/>
    SALMRequest(Base64) : <textarea name="SAMLRequest" rows="8" cols="80"><c:out value="${SAMLRequest}"/></textarea><br/>
    Or SAMLRequest(XML) : <textarea name="SAMLRequestXML" rows="8" cols="80"></textarea><br/>
    Login Style: <select name="login_page_style">
                   <option value="cmcc_1">CMCC_1</option>
                   <option value="cmcc_2">CMCC_2</option>
                   <option value="cmcc_embed" selected>CMCC_Embed</option>
                   <option value="simple">Simple</option>
                 </select><br/>
    Challenge login by FSSO: <input type="checkbox" name="ChallengeLoginByApp" value="ture"/><br/>
		<input type="submit"/>
	</form>
</body>
</html>
