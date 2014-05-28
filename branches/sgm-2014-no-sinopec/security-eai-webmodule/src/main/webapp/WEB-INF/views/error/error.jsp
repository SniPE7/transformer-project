<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ibm.siam.am.idp.AbstractErrorHandler"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div id="content">
	<div class="aui-message error invisible" id="errorDivMsg">
		<!-- shown with class="aui-message error" -->
	</div>
	<div id="acloginpod">
		<div id="acloginpanel" class="loginpanel">
			<fieldset>
				<spring:message code="error.message.content" /><br/><br/>
				<%
				  Throwable error = (Throwable) request.getAttribute(AbstractErrorHandler.ERROR_KEY);
				%>
				<spring:message code="error.message.title" /><p><font size=2px><%=(error != null) ? error.getMessage() : ""%><font></p>
        <c:if test="${empty ERROR_RELOAD_URL}">
        <br>
        <p><form method="get" action="${ERROR_RELOAD_URL}"><input type="submit" value=" 刷新/Reload "></form></p>
        </c:if>
			</fieldset>
		</div>
		<!-- End of #acloginpanel -->
	</div>
	<!-- End of #acloginpod -->
</div>
<!-- End of #content -->