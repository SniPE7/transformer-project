<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="edu.internet2.middleware.shibboleth.common.session.SessionManager"%>
<%!
private String getSpReturnUrl(String startPageUrl, String lloUrl) {
 	if(startPageUrl != null) {
 	  return startPageUrl;
 	}
 	else if(lloUrl != null) {
 	  return lloUrl.substring(0, lloUrl.indexOf("SSO"));
 	}
 	else {
 	  return "";
 	}
}
%>
<%
  List<Map<String, String>> list = (List<Map<String, String>>) request.getAttribute("spLloUrl");
  String redirectToUrl = (String) request.getAttribute("redirectTo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<script type="text/javascript">
	var lloCount = <%=list == null ? 0 : list.size()%>;

	function processChildComplete() {
		lloCount--;

		if(lloCount <= 0) {
			redirect();
		}
	}
	
	function redirect() {
<%
if(redirectToUrl != null) {
  out.write("window.location='" + redirectToUrl + "'");
}
%>
	}
</script>
</head>
<body>
	<div id="content-logout">
		<div id="Noinfor">
			<div id="GLOAPPCount">
				<spring:message code="logout.view.result" /> ：<%=list == null ? 0 : list.size()%>
			</div>
			<input type="hidden" id="GLObutton" name="GLObutton" value="退出所有应用" />
		</div>
		<div id="logoutcontent">
			<ul id="ultl">
<%
if(list != null) {
	for(int i = 0; i < list.size(); i++) {
	  Map<String, String> lloMap = list.get(i);
	  String startPageUrl = lloMap.get("spStartPageUrl");
	  String lloUrl = lloMap.get("spLocalLogoutUrl");
	  String entityID = lloMap.get("spEntityID");
	  StringBuffer frameCodeBuffer = new StringBuffer();
	  
	  frameCodeBuffer.append("<li>");
	  frameCodeBuffer.append("<a href='").append(getSpReturnUrl(startPageUrl, lloUrl)).append("'>");
	  frameCodeBuffer.append(entityID);
	  frameCodeBuffer.append("</a>");
	  frameCodeBuffer.append("<iframe frameborder='no' border='0' scrolling='no' src='logout_iframe.html?LLOUrl=");
	  frameCodeBuffer.append(lloUrl);
	  frameCodeBuffer.append("&No=").append(i).append("' id='czj").append(i).append("'");
	  frameCodeBuffer.append("></iframe>");
	  frameCodeBuffer.append("</li>");
	  out.write(frameCodeBuffer.toString());
	}
}
%>
			</ul>
		</div>
	</div>
</body>
</html>