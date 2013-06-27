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
	<div id="container">
		<div id="header">
			<tiles:insertAttribute name="banner" />
		</div><!-- end of #header -->

		<div id="main">
			<div id="panel">
				<div id="sidebar">
					<tiles:insertAttribute name="info" />
					<h3>统一账号介绍</h3>
          <div class="desc">
          	<div class="text">由中国石化用户统一身份管理系统（简称SIAM）产生，作为中国石化信息系统用户全网统一的账号。申请该账号后，您可以登录所有与SIAM集成的业务系统，而不用再使用其他账号，方便您的日常使用和维护，提高账号安全管理能力。</div>
          </div>
          <h3>统一账号管理</h3>
          <div class="desc">
          	<div class="text">统一账号（SUID）是中国石化信息系统用户统一账号的简称，供中国石化员工登录信息系统使用。</div>
           <ol>
              <li>还没有统一账号？：点击“<a href="https://uid.sinopec.com/ss/view/register.html">创建统一账号</a>”</li>
              <li>忘记了统一账号？：点击“<a href="https://uid.sinopec.com/ss/view/lose/account.html">找回统一账号</a>”</li>
            </ol>
          </div>
          <h3>技术支持热线</h3>
          <div class="desc">
            <ol>
              <li>电话：010-59966450</li>
              <li>邮箱：siam@sinopec.com</li>
            </ol>
          </div>
				</div><!-- end of #sidebar -->

				<tiles:insertAttribute name="body" />
			</div><!-- End of #panel -->

			<div class="line"></div>
		</div><!-- end #main -->

		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div><!-- end #footer -->
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