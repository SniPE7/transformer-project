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
<c:set var="ppiid" value="${param.ppiid}"/>
<sql:setDataSource var="myDataSource" dataSource="jdbc/nccDS" />
<html>
<head>
<link href='<%=request.getContextPath()%>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath()%>/login.css' rel="styleSheet" type="text/css">
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script language="JavaScript" type="text/javascript">
</script>

<body class="navtree" style="background-color: #FFFFFF;" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form action="" method="post" id="form1" name="form1">
		<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
			<TR>
				<TD CLASS="pageTitle">策略管理</TD>
				<TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
			</TR>
		</TABLE>

		<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
			<TR>
				<TD valign="top">
					<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">
						<TR>
							<TH class="wpsPortletTitle" width="100%">各分行策略生效情况</TH>
						</TR>
						<TR>
							<TD CLASS="wpsPortletArea" COLSPAN="3"><a name="important"></a>
								<sql:query var="results" dataSource="jdbc/nccDS">
select 
 server_id,
 server_code,
 server_name,
 (select count(*) from T_TAKE_EFFECT_HISTORY teh inner join T_SERVER_NODE sn on sn.server_id=teh.server_id where tsn.server_id=sn.server_id and teh.effect_status='S' and teh.ppiid=?) as success_count,
 (select to_char(max(effect_time), 'YYYY-MM-DD HH12:MI:SS') from T_TAKE_EFFECT_HISTORY teh inner join T_SERVER_NODE sn on sn.server_id=teh.server_id where tsn.server_id=sn.server_id and teh.effect_status='S' and teh.ppiid=?) as last_success_timestamp,
 (select ppiid from (select ppi.ppiid as ppiid, teh.server_id as server_id from T_TAKE_EFFECT_HISTORY teh inner join T_POLICY_PUBLISH_INFO ppi on ppi.ppiid=teh.ppiid where teh.effect_status='S' order by ppi.publish_time asc) where rownum=1 and server_id=tsn.server_id) as last_success_ppiid,
 (select version from (select ppi.version as version, teh.server_id as server_id from T_TAKE_EFFECT_HISTORY teh inner join T_POLICY_PUBLISH_INFO ppi on ppi.ppiid=teh.ppiid where teh.effect_status='S' order by ppi.publish_time asc) where rownum=1 and server_id=tsn.server_id) as last_success_ppiid,
 (select version_tag from (select ppi.version_tag as version_tag, teh.server_id as server_id from T_TAKE_EFFECT_HISTORY teh inner join T_POLICY_PUBLISH_INFO ppi on ppi.ppiid=teh.ppiid where teh.effect_status='S' order by ppi.publish_time asc) where rownum=1 and server_id=tsn.server_id) as last_success_ppiid
FROM 
  T_SERVER_NODE tsn
order by server_code
                  <sql:param value="${ppiid}"/>
                  <sql:param value="${ppiid}"/>
                </sql:query>
								<display:table name="${results.rows}" /></TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
</body>
</html>