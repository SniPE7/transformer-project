<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="edu.internet2.middleware.shibboleth.idp.authn.LoginHandler"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="keywords" content="用户, 统一身份, 账号, 认证" />
<meta name="description" content="上海通用汽车统一身份认证中心" />
<tiles:insertAttribute name="head" />
<script type="text/javascript" src="js/form.js"></script>
</head>
<body>
	<object id="badgeTool" style="display:none" classid="clsid:395E6CF3-3084-487D-9606-EDAA8B2C4E3C"></object>
	<object id="clientToolbox"  classid="clsid:A1A99D71-6B0C-4703-A94F-0F1DA8444EF9" style="display:none" ></object>
	<div id="container">
		<div id="header">
			<tiles:insertAttribute name="banner" />
		</div>
		<!-- end of #header -->

		<div id="main" style="clear: both; overflow: hidden;">
			<div id="panel2">
				<div id="sgmbanner">
					   <div class="topleft">
	                       <img src="themes/default/images/login_left_top.gif" /></div>
	                   <div class="topcenter">
	                       <div style="float: left;"><img src="themes/default/images/login_log.gif" /></div>
	                     <c:if test="${not empty param.currentAuthen}">
	                       	<div style="float: right;font-weight: bold;margin-top: 56px;"><spring:message code="${param.currentAuthen}"/></div>
	                      </c:if>
	                   </div>
	                   <div class="topright"> <img src="themes/default/images/login_right_top.gif" /></div>
				</div>
				<!-- <div class="clear"></div> -->
				<div id="sgmcontent">
	                <div class="contentleft"> 
        <div style="margin: 0px auto;text-align:center; "><!--<img src="themes/default/images/login_title1.PNG"/>-->SGM Unified Identity Management Platform<br>
		上海通用汽车统一身份管理平台</div>

	               	<tiles:insertAttribute name="body" />
	                </div>
	                <div class="contentright">
	                    <img src="themes/default/images/login_right.gif" />
	                </div>
				</div>
				<!-- <div class="clear"></div> -->
				<div id="sgmfooter">
	                <div class="footerleft">
	                    <img src="themes/default/images/login_left_bottom.gif" /></div>
	                <div class="footerright">
	                </div>                
				</div>
				<!-- <div class="clear"></div> -->
			</div>
			
			<!-- End of #panel -->

			<!-- <div class="line"></div> -->
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
  
   <script>
		doBodyZoom(screenZoom);
	</script>
</body>
</html>