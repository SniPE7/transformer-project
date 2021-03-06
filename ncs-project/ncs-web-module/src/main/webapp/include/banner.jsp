<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/include/include.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jstl/sql_rt"%>
<sql:setDataSource var="myDataSource" dataSource="jdbc/nccDS" />
<sql:query var="items" dataSource="jdbc/nccDS">
select db_link from dba_db_links where db_link='NCCADM'
</sql:query>
<c:set var="isBranchNode" value="false"/>
<c:forEach var="item" items="${items.rows}">
  <c:set var="isBranchNode" value="true"/>
</c:forEach>
<fmt:setBundle basename="ncc-configuration" var="commonBundle"/>

<html>
<head>
<script type="text/javascript" src="../dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: true"></script>
<style type="text/css">
@import "../dojo/dojo/resources/dojo.css";
@import "../dojo/dijit/themes/noir/noir.css";
@import "../dojo/dijit/themes/dijit.css";
</style>
<title>banner</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="js/tip/dijit/themes/Tivoli_Tundra.css"></link>
<link rel="stylesheet" type="text/css" href="js/simplesrm/srm/dijit/themes/SimpleSRM_Tundra.css"></link>
<link rel="stylesheet" type="text/css" href="/SimpleSRM/simplesrm.css"></link>
</head>
<body class="tundra" style='background-image: url("../images/ge_login_bkgnd.jpg")'>
	<table border="0" height="35" width="100%" cellpadding="0" cellspacing="0" class="wpsToolBar">
		<tr>
			<td align="left"></td>
			<td align="left" width="85%" nowrap class="ibm-banner-product-name">&nbsp;&nbsp;<span id="headingtext" class="ibm-banner-product-name"> NetCool配置信息管理系统 </span>
			</td>
			<td class="wpsToolBarLink" nowrap style="text-align: center;"><span id="ibm-banner-welcome"> 欢迎 </span>&nbsp;<span id="ibm-banner-user"><%=request.getSession(true).getAttribute("username") %><c:if test="${isBranchNode}">@【<fmt:message key="ncs.node.code" bundle="${commonBundle}"/>分行节点】</c:if><c:if test="${not isBranchNode}">@【数据中心】</c:if>, &nbsp;</span>&nbsp;&nbsp;
			</td>
			<td class="wpsToolBarLink" nowrap style="text-align: center;"><a title='Help' href="<%=request.getContextPath()%>/secure/user/profile.jsp" target="detail"
				style="text-decoration: none;">【设置个人信息及口令】</a></td>
			<td align="center" style="width: 10px; padding-left: 10px; padding-right: 10px;"></td>
			<td class="wpsToolBarLink" nowrap style="text-align: center;"><a title='Logout' href="<%=request.getContextPath()%>/logout.wss" target="_top"
				style="text-decoration: none;">【退出】 </a></td>
			<td align="right" style="padding-left: 10px;"></td>
		</tr>
	</table>

</body>
</html>