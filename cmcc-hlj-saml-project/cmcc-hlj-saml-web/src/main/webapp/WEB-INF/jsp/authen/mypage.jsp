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
<link type="text/css" href="css/start/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link href="css/index_concise.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript">
  $(function() {

    // Accordion
    $("#accordion").accordion({
      collapsible : true,
      icons : false,
      autoHeight : false,
    });
  });
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
  <div id="accordion" style="width: 480px; margin: 10px; ">
    <h3>
      <a href="#">欢迎</a>
    </h3>
    <div>
                欢迎, <c:out value="${sessionScope.username}"></c:out> 
    </div>
  </div>
</body>
</html>
