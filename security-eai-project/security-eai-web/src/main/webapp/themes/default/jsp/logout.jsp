<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page
	import="edu.internet2.middleware.shibboleth.common.session.SessionManager"%>
<%@page
	import="edu.internet2.middleware.shibboleth.idp.authn.LoginHandler"%>

<%

request.getSession(true).removeAttribute(LoginHandler.SUBJECT_KEY);
request.getSession(true).removeAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY);
	
%>

<div id="content-logout">
	<div id="Noinfor"></div>
	<div id="logoutcontent">
		<ul id="ultl">
			注销成功！
		</ul>
	</div>
</div>
