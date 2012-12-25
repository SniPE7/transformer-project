<%@ include file="/include/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jstl/sql_rt"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
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
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form action="" method="post">
		<div style="background-color: #FFFFFF; margin: 5px; padding: 10px;">
			接收策略模板
			<hr />

			<c:if test="${model.needUpgrade || model.needMigrate }">
				<div class="buttonArea">
					<c:if test="${model.needUpgrade}">
						<input type="button" value="立即升级" />
					</c:if>
					<c:if test="${model.needMigrate }">
						<input type="button" value="立即迁移" />
					</c:if>
				</div>
			</c:if>
			<c:if test="${model.message != null &&  model.message != ''}">
				<div id="errmsg" class="validateErrorMsg" style="margin-top: 10px;">${model.message }</div>
			</c:if>

			<table class="entityview">
				<thead>
					<tr>
						<th colspan="2">当前在用的策略集</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th width="150">版本</th>
						<td><c:out value="${model.appliedPolicyPublishInfo.version}" /></td>
					</tr>
					<tr>
						<th width="150">版本标识</th>
						<td><c:out value="${model.appliedPolicyPublishInfo.versionTag}" /></td>
					</tr>
					<tr>
						<th width="150">备注</th>
						<td><c:out value="${model.appliedPolicyPublishInfo.description}" /></td>
					</tr>
				</tbody>
			</table>

			<table class="entityview">
				<thead>
					<tr>
						<th colspan="2">最新发布的策略集</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th width="150">版本</th>
						<td><c:out value="${model.releasedPolicyPublishInfo.version}" /></td>
					</tr>
					<tr>
						<th width="150">版本标识</th>
						<td><c:out value="${model.releasedPolicyPublishInfo.versionTag}" /></td>
					</tr>
          <tr>
            <th width="150">发布时间</th>
            <td><fmt:formatDate value="${model.appliedPolicyPublishInfo.publishTime}" pattern= "yyyy-MM-dd HH:mm:ss" type="both" timeStyle="full"/></td>
          </tr>
					<tr>
						<th width="150">备注</th>
						<td><c:out value="${model.releasedPolicyPublishInfo.description}" /></td>
					</tr>
				</tbody>
			</table>

		</div>
	</form>
</body>
</html>