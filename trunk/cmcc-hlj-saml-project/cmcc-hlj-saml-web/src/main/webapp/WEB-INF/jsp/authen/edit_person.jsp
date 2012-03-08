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
</head>
<body>
    <h3>
      <a href="#">欢迎 <c:out value="${sessionScope.username}"/></a>
    </h3>
      <form action="./service/authen/updatePerson">
       MSISDN:<input type="text" name="msisdn" value='<c:out value="${sessionScope.SESSION_PERSON.msisdn}"/>' readonly="readonly"><br/>
        昵称:<input type="text" name="displayName" value='<c:out value="${sessionScope.SESSION_PERSON.nickname}"/>'><br/>
        姓名:<input type="text" name="cn" value='<c:out value="${sessionScope.SESSION_PERSON.commonName}"/>'><br/>
        状态:<input type="text" name="erhljmccstatus" value='<c:out value="${sessionScope.SESSION_PERSON.status}"/>'> 1：开机, 2：单向停机, 3：双向停机, 4：预销户, 5：销户<br/>
        飞信状态:<input type="text" name="erhljmccFetionStatus" value='<c:out value="${sessionScope.SESSION_PERSON.fetionStatus}"/>'> 0: 未开通, 1： 开通<br/>
        139Mail状态:<input type="text" name="erhljmcc139MailStatus" value='<c:out value="${sessionScope.SESSION_PERSON.mail139Status}"/>'> 0: 未开通, 1： 开通<br/>
        用户级别:<input type="text" name="erhljmccuserlevel" value='<c:out value="${sessionScope.SESSION_PERSON.userLevel}"/>'> 钻卡用户(01),金卡用户(02),银卡用户(03),普通用户(04)<br/>
       点数:<input type="text" name="erhljmcccurrentpoint" value='<c:out value="${sessionScope.SESSION_PERSON.currentPoint}"/>'><br/>
       失败次数:<input type="text" name="erhljmccAuthTimes" value='<c:out value="${sessionScope.SESSION_PERSON.erhljmccAuthTimes}"/>'><br/>
       失败阀值:<input type="text" name="erhljmccAuthThreshold" value='<c:out value="${sessionScope.SESSION_PERSON.erhljmccAuthThreshold}"/>'><br/>
       
       <input type="submit"/>
      </form>
</body>
</html>
