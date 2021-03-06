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
<c:if test="${param.saml_debug_mode != 'true'}">
<body onload="document.forms['SAMLResponseForm'].submit();">
</c:if>

<c:if test="${param.saml_debug_mode == 'true'}">
<body>
<c:if test="${SAMLart == null}">
  <h4>您目前没有登录, 点击继续按钮将进入登录界面! </h4>
</c:if>
<c:if test="${SAMLart != null}">
  <h4>登录成功 [${sessionScope.username}], 点击继续按钮将返回应用! </h4>
</c:if>
</c:if>

<c:if test="${SAMLAuthenRequest.samlIssuer != null}">
	<form name="SAMLResponseForm" method="post" action="<c:out value="${SAMLAuthenRequest.samlIssuer}"/>">
    <input type="hidden" name="SAMLart" value='<c:out value="${SAMLart}"/>' />
    <c:if test="${param.RelayState != null}">
    <input type="hidden" name="RelayState" value='<c:out value="${param.RelayState}"/>' />
    </c:if>
    <c:if test="${sessionScope.RELAY_STATE != null}">
    <input type="hidden" name="RELAY_STATE" value='<c:out value="${sessionScope.RelayState}"/>' />
    </c:if>
    <input type="hidden" name="saml_debug_mode" value='<c:out value="${param.saml_debug_mode}"/>' />
<c:if test="${param.saml_debug_mode == 'true'}">
    <input type="submit" value="  继  续  "/>
</c:if>
  </form>
</c:if>
<c:if test="${param.continue != null}">
  <form name="SAMLResponseForm" method="post" action="<c:out value="${param.continue}"/>">
    <input type="hidden" name="SAMLart" value='<c:out value="${SAMLart}"/>' />
    <c:if test="${param.RelayState != null}">
    <input type="hidden" name="RelayState" value='<c:out value="${param.RelayState}"/>' />
    </c:if>
    <c:if test="${sessionScope.RELAY_STATE != null}">
    <input type="hidden" name="RELAY_STATE" value='<c:out value="${sessionScope.RelayState}"/>' />
    <input type="hidden" name="saml_debug_mode" value='<c:out value="${param.saml_debug_mode}"/>' />
    </c:if>
<c:if test="${param.saml_debug_mode == 'true'}">
    <input type="submit" value="  继  续  "/>
</c:if>
  </form>
</c:if>

</body>
</html>
