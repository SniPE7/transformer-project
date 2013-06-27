<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.internet2.middleware.shibboleth.idp.authn.LoginHandler"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<base href="<%=basePath%>"/>
<tiles:useAttribute name="title" scope="request" />
<title><spring:message code="${title}" /></title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="keywords" content="用户, 统一身份, 账号, 认证" />
<meta name="description" content="中国石化用户统一身份认证中心" />
<tiles:insertAttribute name="head" />
<script type="text/javascript" src="js/form.js"></script>
</head>
<body>
	<tiles:insertAttribute name="body" />
	<%
    if ("true".equals(request.getAttribute("loginFailed"))) {
      String msgKey = "logon.form.error.default";
      String msgType = "error";
	    if (request.getAttribute(LoginHandler.AUTHENTICATION_INFO_KEY) != null) {
	      msgKey = request.getAttribute(LoginHandler.AUTHENTICATION_INFO_KEY).toString();
	      msgType = "info";
	    } else if (request.getAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY) != null) {
	      msgKey = request.getAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY).toString();
	    }
	    
	    if (request.getAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY) != null) {
	      String arguments = request.getAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY).toString();
	 %>
	 			<script>setMsg('<%=msgType%>', '<spring:message code="<%=msgKey%>" arguments="<%=arguments%>" />');</script>
	 <%
	    } else {
	 %>
	 			<script>setMsg('<%=msgType%>', '<spring:message code="<%=msgKey%>" />');</script>
	 <%
	    }
    }
  %>
</body>
</html>