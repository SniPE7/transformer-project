<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.internet2.middleware.shibboleth.idp.authn.LoginHandler"%>
<%@ page import="com.sinopec.siam.am.idp.utils.message.I18NMessageUtils"%>
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
<base href="<%=basePath%>"/>

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
	
  <div id="container">
    <div id="header">
      <tiles:insertAttribute name="banner" />
    </div><!-- end of #header -->
    
    <div id="main" style="clear: both; overflow: hidden;">
			<div id="panel3">
				<div id="sgmbanner2">
					   <div class="topleft">
	                       <img src="themes/default/images/login_left_top.gif" /></div>
	                   <div class="topcenter">
	                       <div style="float: left;"><img src="themes/default/images/login_log.gif" /></div>
	                       <div style="float: left;margin-top:20px;margin-left:20px"><img src="themes/default/images/login_title.png"/></div>
	                     <c:if test="${not empty title}">
	                       	<div style="float: right;font-weight: bold;margin-top: 56px;"><spring:message code="${title}"/></div>
	                      </c:if>
	                   </div>
	                   <div class="topright"> <img src="themes/default/images/login_right_top.gif" /></div>
				</div>
				<!-- <div class="clear"></div> -->
				<div id="sgmcontent2">
	                <div class="contentleft"> 
	                	<tiles:insertAttribute name="body" />
	                </div>
	                <div class="contentright">
	                    <img src="themes/default/images/login_right.gif" />
	                </div>
				</div>
				<!-- <div class="clear"></div> -->
				<div id="sgmfooter2">
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
    </div><!-- end #footer -->
  </div>
    
     <script>
		doBodyZoom(screenZoom);
	</script>
	
</body>
</html>
