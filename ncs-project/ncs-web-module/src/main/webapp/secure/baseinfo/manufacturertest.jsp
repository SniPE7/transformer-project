<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>manufacturers test</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR"
	content="Rational® Application Developer™ for WebSphere® Software">
</head>
<body>

<table border="1">
<tr>
<th>厂商ID</th>
<th>厂商名</th>
<th>ObjectID</th>
<th>描述</th>
</tr>
<c:forEach items="${model.manufacturers}" var="m1" >
<tr>
<td><c:out value="${m1.mrid}" /> </td>
<td><c:out value="${m1.mrname}" /> </td>
<td><c:out value="${m1.objectid}"/></td>
<td><c:out value="${m1.description}"/></td>
</tr>
</c:forEach>
</table>
</body>
</html>