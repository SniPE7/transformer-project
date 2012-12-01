<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<title>category test</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR"
	content="Rational® Application Developer™ for WebSphere® Software">
</head>
<body>

<table >
<tr>
<th>ID</th>
<th>Name</th>
<th>Flag</th>
</tr>
<c:forEach items="${model.category}" var="c1" >
<tr>
<td><c:out value="${c1.id}" /> </td>
<td><c:out value="${c1.name}" /> </td>
<td><c:out value="${c1.flag}"/></td>
</tr>
</c:forEach>
</table>
</body>
</html>