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
<link href='<%=request.getContextPath()%>/include/displaytag.css' rel="styleSheet" type="text/css">
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script language="JavaScript" type="text/javascript">
</script>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
  <div style="background-color: #FFFFFF; margin: 5px; padding: 10px;">
              各分行策略生效详情 <hr/>
    <table class="entityview">
      <thead>
        <tr>
          <th colspan="2">当前策略集信息</th>
        </tr>
      </thead>
      <tbody>
		    <sql:query var="items" dataSource="jdbc/nccDS">
		    select version, version_tag, description from T_POLICY_PUBLISH_INFO where ppiid=?
		      <sql:param value="${ppiid}"/>
		    </sql:query>
        <c:forEach var="historyInfo" items="${items.rows}">
        <tr>
          <th width="150">版本</th>
          <td><c:out value="${historyInfo.version}"/></td>
        </tr>
        <tr>
          <th>版本标识</th>
          <td><c:out value="${historyInfo.version_tag}"/></td>
        </tr>
        <tr>
          <th>备注</th>
          <td><c:out value="${historyInfo.description}"/></td>
        </tr>
        </c:forEach>
      </tbody>
    </table>

		<sql:query var="results" dataSource="jdbc/nccDS">
select 
 server_id,
 server_code,
 server_name,
 (select count(*) from T_TAKE_EFFECT_HISTORY teh inner join T_SERVER_NODE sn on sn.server_id=teh.server_id where tsn.server_id=sn.server_id and teh.effect_status='S' and teh.ppiid=?) as success_count,
 (select to_char(max(effect_time), 'YYYY-MM-DD HH12:MI:SS') from T_TAKE_EFFECT_HISTORY teh inner join T_SERVER_NODE sn on sn.server_id=teh.server_id where tsn.server_id=sn.server_id and teh.effect_status='S' and teh.ppiid=?) as last_success_timestamp,
 (select max(teh.teid) from T_TAKE_EFFECT_HISTORY teh inner join T_SERVER_NODE sn on sn.server_id=teh.server_id where tsn.server_id=sn.server_id and teh.effect_status='S' and teh.ppiid=?) as teid,
 (select ppiid from (select ppi.ppiid as ppiid, teh.server_id as server_id from T_TAKE_EFFECT_HISTORY teh inner join T_POLICY_PUBLISH_INFO ppi on ppi.ppiid=teh.ppiid where teh.effect_status='S' order by ppi.publish_time asc) where rownum=1 and server_id=tsn.server_id) as last_success_ppiid,
 (select version from (select ppi.version as version, teh.server_id as server_id from T_TAKE_EFFECT_HISTORY teh inner join T_POLICY_PUBLISH_INFO ppi on ppi.ppiid=teh.ppiid where teh.effect_status='S' order by ppi.publish_time asc) where rownum=1 and server_id=tsn.server_id) as last_success_version,
 (select version_tag from (select ppi.version_tag as version_tag, teh.server_id as server_id from T_TAKE_EFFECT_HISTORY teh inner join T_POLICY_PUBLISH_INFO ppi on ppi.ppiid=teh.ppiid where teh.effect_status='S' order by ppi.publish_time asc) where rownum=1 and server_id=tsn.server_id) as last_success_version_tag
FROM 
  T_SERVER_NODE tsn
order by server_code
           <sql:param value="${ppiid}"/>
           <sql:param value="${ppiid}"/>
           <sql:param value="${ppiid}"/>
         </sql:query>
	<display:table id="history" name="${results.rows}" pagesize="50" class="simple">
	  <display:column class="rowNum" title="#" paramId="ppiid" paramProperty="ppiid"><c:out value="${history_rowNum}"/></display:column>
           <display:column property="server_code" sortable="true" title="分行标识" paramId="ppiid" paramProperty="ppiid"/>
           <display:column property="server_name" sortable="true" title="分行名称" paramId="ppiid" paramProperty="ppiid"/>
           <display:column property="last_success_version" sortable="true" title="最新生效的版本" paramId="ppiid" paramProperty="ppiid"/>
           <display:column property="last_success_version_tag" sortable="true" title="最新生效的版本标识" paramId="ppiid" paramProperty="ppiid"/>
           <display:column property="success_count" sortable="true" title="生效成功次数" paramId="ppiid" paramProperty="ppiid"/>
           <display:column>
             <a href="?teid=<c:out value='${history.teid}'/>">下载最新文件</a>
           </display:column>
	</display:table>
	</div>
</body>
</html>