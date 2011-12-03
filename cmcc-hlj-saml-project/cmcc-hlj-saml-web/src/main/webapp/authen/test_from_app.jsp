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
<body>
	<form method="post" action="./service/authen/request">
    RelayState :<input type="text" name="RelayState" value='1234567890' /><br/>
    SALMRequest(Base64) : <textarea name="SAMLRequest" rows="8" cols="80">PHNhbWxwOkF1dGhuUmVxdWVzdA0KICAgIHhtbG5zOnNhbWxwPSJ1cm46b2FzaXM6bmFtZXM6dGM6
U0FNTDoyLjA6cHJvdG9jb2wiDQogICAgeG1sbnM6c2FtbD0idXJuOm9hc2lzOm5hbWVzOnRjOlNB
TUw6Mi4wOmFzc2VydGlvbiINCiAgICBJRD0iaWRfMSINCiAgICBWZXJzaW9uPSIyLjAiDQogICAg
SXNzdWVJbnN0YW50PSIyMDA3LTEyLTA1VDA5OjIxOjU5WiI+DQogICAgPHNhbWw6SXNzdWVyPmh0
dHBzOi8vd3d3LmNoaW5hbW9iaWxlLmNvbS9TU088L3NhbWw6SXNzdWVyPg0KICAgIDxzYW1scDpO
YW1lSURQb2xpY3kNCiAgICAgICAgQWxsb3dDcmVhdGU9InRydWUiDQogICAgICAgIEZvcm1hdD0i
dXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOm5hbWVpZC1mb3JtYXQ6dHJhbnNpZW50Ii8+DQo8
L3NhbWxwOkF1dGhuUmVxdWVzdD4NCg==</textarea><br/>
    Or SAMLRequest(XML) : <textarea name="SAMLRequestXML" rows="8" cols="80"></textarea><br/>
    Login Style: <select name="login_page_style"><option value="cmcc_1">CMCC_1</option><option value="cmcc_2">CMCC_2</option><option value="simple">Simple</option></select><br/>
		<input type="submit"/>
	</form>
</body>
</html>
