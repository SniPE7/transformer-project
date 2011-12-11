<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>SAMPL Client Login Form</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:if test='${sessionScope.SESSION_PERSON.brand == "1"}'>
  <link rel="stylesheet" type="text/css" href="css/css.gotone.css">
</c:if>
<c:if test='${sessionScope.SESSION_PERSON.brand == "2"}'>
  <link rel="stylesheet" type="text/css" href="css/css.easyown.css">
</c:if>
<c:if test='${sessionScope.SESSION_PERSON.brand == "3"}'>
  <link rel="stylesheet" type="text/css" href="css/css.mzone.css">
</c:if>
<script type="text/javascript">
</script>
<style type="text/css">
/*demo page css*/
body {
	font: 80.5% "Trebuchet MS";
	margin: 0px;
	background-color: #ffffff
}

input.text {
	margin-bottom: 12px;
	width: 30%;
	padding: .4em;
}

input {
	display: inheritinline;
}

</style>
</head>
<body>
<div id="login_successfull">
	<div id="brand"><a href="https://hl1.ac.10086.cn/sso/service/authen/logout">退出</a></div>
	<div id="welcome">
	<c:out value="${sessionScope.SESSION_PERSON.commonName}"/> 欢迎使用测试应用!<br>
	(<a href="javascript:void()"><c:out value="${sessionScope.SESSION_PERSON.msisdn}"/> 【黑龙江】</a>) 您好</div>
	<div id="main">
    <hr/>
	  <ul id="MainNav">
<c:if test='${sessionScope.SESSION_PERSON.mail139Status == "1"}'>
	    <li><a href="javascript:void()">139邮件 </a></li>
</c:if>
<c:if test='${sessionScope.SESSION_PERSON.fetionStatus == "1"}'>
	    <li><a href="javascript:void()">飞信</a></li>
</c:if>
	  </ul>
	</div>
	<div id="button" align="center"><a href="javascript:void()">网上营业厅</a></div>
</div>

</body>
</html>
