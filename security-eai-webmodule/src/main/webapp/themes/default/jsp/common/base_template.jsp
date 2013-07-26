<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="edu.internet2.middleware.shibboleth.idp.authn.LoginHandler"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<base href="<%=basePath%>" />
<tiles:useAttribute name="title" scope="request" />
<title><spring:message code="${title}" /></title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="keywords" content="用户, 统一身份, 账号, 认证" />
<meta name="description" content="上海通用汽车统一身份认证中心" />
<tiles:insertAttribute name="head" />
<script type="text/javascript" src="js/form.js"></script>
</head>
<body>
	<div id="container">
		<div id="header">
			<tiles:insertAttribute name="banner" />
		</div>
		<!-- end of #header -->

		<div id="main">
			<div id="panel">
				<div id="sidebar">
					<tiles:insertAttribute name="info" />
					<h3>统一账号介绍/Introduce Unified Account</h3>
					<div class="desc">
						<div class="text">由上海通用汽车统一身份管理系统产生，统一颁发./By Shanghai General Motors unified identity management system to produce a unified award.</div>
					</div>
					<h3>统一账号管理/Unified Account Management</h3>
					<div class="desc">
						<div class="text">统一账号（SUID）是SGM认证中心颁发的用户登录名，供登录信息系统使用。/Unified account (SUID) is SGM Certification Center user login name for the login information systems.</div>
						<ol>
							<li>忘记了统一账号？/Forgot unified account?：点击/Click“<a
								href="loseuser.do">找回统一账号/Forget unified account</a>”
							</li>
						</ol>
					</div>
					<h3>技术支持热线/Technical Support Hotline</h3>
					<div class="desc">
						<ol>
							<li>电话/Phone：022-58581082</li>
							<li>邮箱/Email：admin@shanghaigm.com</li>
						</ol>
					</div>
				</div>
				<!-- end of #sidebar -->

				<tiles:insertAttribute name="body" />
			</div>
			<!-- End of #panel -->

			<div class="line"></div>
		</div>
		<!-- end #main -->

		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div>
		<!-- end #footer -->
	</div>
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