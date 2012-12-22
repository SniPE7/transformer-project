<%@ include file="/include/include.jsp" %>
<%@page	language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<c:choose>
  <c:when test="${model.success}">
<% 
response.setStatus(200);
%>
  </c:when>
  <c:otherwise>
<% 
response.setStatus(201);
%>
  </c:otherwise>
</c:choose>
<pre><c:forEach items="${model.stat}" var="msg"><c:out value="${msg.key}"/>&nbsp;<c:out value="${msg.value}"/><br></c:forEach></pre>
