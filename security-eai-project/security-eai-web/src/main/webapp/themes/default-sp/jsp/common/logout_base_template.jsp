<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.internet2.middleware.shibboleth.idp.authn.LoginHandler"%>
<%@ page import="com.sinopec.siam.am.idp.utils.message.I18NMessageUtils"%>
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
<meta name="description" content="用户统一身份认证中心" />
<tiles:insertAttribute name="head" />
</head>
<body>
  <div id="container">
    <div id="header">
      <tiles:insertAttribute name="banner" />
    </div><!-- end of #header -->
    
    <div id="main">
      <div id="panel-logout">
        <div id="sidebar-logout"></div><!-- end of #sidebar -->
        <tiles:insertAttribute name="body" />
      </div><!-- End of #panel -->
      
      <div class="line"></div>
    </div><!-- end #main -->
    
    <div id="footer">
      <tiles:insertAttribute name="footer" />
    </div><!-- end #footer -->
  </div>
</body>
</html>
