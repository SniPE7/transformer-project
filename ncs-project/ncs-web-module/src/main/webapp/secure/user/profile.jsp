<%@ include file="/include/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jstl/sql_rt"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<sql:setDataSource var="myDataSource" dataSource="jdbc/nccDS" />
<html>
<head>
<link href='<%=request.getContextPath()%>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath()%>/login.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath()%>/include/displaytag.css' rel="styleSheet" type="text/css">
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script language="JavaScript" type="text/javascript">
</script>
<%
String username = ((java.util.Map<String, String>) request.getSession().getAttribute("signOnFlag")).get("username");
request.setAttribute("username", username);
%>
<c:set var="username" value="<%= username %>"></c:set>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="<%=request.getContextPath()%>/secure/user/profile.wss" method="post">
  <div style="background-color: #FFFFFF; margin: 5px; padding: 10px;">
              更改个人信息及口令<hr/>
              
    <c:if test="${definition.message != null &&  definition.message != ''}">
      <div id="errmsg">
        ${definition.message }
      </div>
    </c:if>
              
    <table class="entityview">
      <thead>
        <tr>
          <th colspan="2">${username} 基本信息及口令</th>
        </tr>
      </thead>
      <tbody>
		    <sql:query var="items" dataSource="jdbc/nccDS">
		    select email, fullname from t_user where uname=?
		      <sql:param value="${username}"/>
		    </sql:query>
        <c:forEach var="user" items="${items.rows}">
        <tr>
          <th width="150">登录用户标识</th>
          <td><c:out value="${username}"/></td>
        </tr>
        <tr>
          <th width="150">姓名</th>
          <td><input type="text" name="fullname" value="${user.fullname}"/></td>
        </tr>
        <tr>
          <th>Email</th>
          <td><input type="text" name="email" value="${user.email}"/></td>
        </tr>
        <tr>
          <th>当前口令</th>
          <td><input type="password" name="password" value=""/></td>
        </tr>
        <tr>
          <th>新口令</th>
          <td><input type="password" name="newPassword1" value=""/></td>
        </tr>
        <tr>
          <th>再次输入新口令</th>
          <td><input type="password" name="newPassword2" value=""/></td>
        </tr>
        <tr>
          <td colspan="2"><input type="submit" value=" 保存   "/></td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
	</div>
</form>
</body>
</html>