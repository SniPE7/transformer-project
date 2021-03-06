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
<c:if test="${param.saml_debug_mode == 'true'}">
	<body>
		<form name="SAMLRequestForm4Debug" method="post" action="<c:out value="${ssoSAMLAuthRequestURL}"/>">
			RelayState :<input type="text" name="RelayState" value='<c:out value="${RelayState}"/>' /><br /> SALMRequest(Base64)
			:
			<textarea name="SAMLRequest" rows="8" cols="80">
				<c:out value="${SAMLRequest}" />
			</textarea>
			<br /> Or SAMLRequest(XML) :
			<textarea name="SAMLRequestXML" rows="8" cols="80"></textarea>
			<br /> Login Style: <select name="login_page_style">
				<option value="cmcc_1">CMCC_1</option>
				<option value="cmcc_2">CMCC_2</option>
				<option value="cmcc_embed" selected>CMCC_Embed</option>
				<option value="simple">Simple</option>
			</select><br /> Challenge login by FSSO: <input type="checkbox" name="ChallengeLoginByApp" value="true" /><br /> Debug Page Flow: <input type="checkbox" name="saml_debug_mode" value="true" checked="checked" /><br /> <input type="submit" />
		</form>
	</body>
</c:if>
<c:if test="${param.saml_debug_mode != 'true'}">
	<body onload="document.forms['SAMLRequestForm'].submit();">
		<form name="SAMLRequestForm" method="post" action="<c:out value="${ssoSAMLAuthRequestURL}"/>">
			<input type="hidden" name="RelayState" value='<c:out value="${RelayState}"/>' /> <input type="hidden"
				name="SAMLRequest" value='<c:out value="${SAMLRequest}"/>' /> <input type="hidden" name="ChallengeLoginByApp"
				value='true' /> <input type="hidden" name="login_page_style" value='cmcc_embed' /> <input type="hidden"
				name="saml_debug_mode" value='<c:out value="${saml_debug_mode}"/>' />
		</form>
	</body>
</c:if>
</html>
