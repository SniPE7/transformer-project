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
	<form id="mainform" action="<%=request.getContextPath()%>/secure/policyapply/upgradePolices.wss" method="post">
	  <input type="hidden" name="operation" value=""/>
		<div style="background-color: #FFFFFF; margin: 5px; padding: 10px;">
			接收策略模板
			<hr />

			<c:if test="${model.needUpgrade || model.needMigrate }">
				<div class="buttonArea">
					<c:if test="${model.needUpgrade}">
						<input type="button" value="立即升级" onclick="document.forms['mainform'].operation.value='upgrade'; document.forms['mainform'].submit();"/>
					</c:if>
					<c:if test="${model.needMigrate }">
						<input type="button" value="立即迁移" onclick="document.forms['mainform'].operation.value='migrate'; document.forms['mainform'].submit();"/>
					</c:if>
				</div>
			</c:if>
			
      <c:if test="${model.operationMessage != null &&  model.operationMessage != ''}">
        <div id="errmsg" class="validateErrorMsg" style="margin-top: 10px;">${model.operationMessage }</div>
      </c:if>
      <c:if test="${model.message != null &&  model.message != '' && operationMessage != null}">
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

<c:if test="${model.needUpgrade || model.needMigrate }">
      <sql:setDataSource var="myDataSource" dataSource="jdbc/nccDS" />
      <table class="entityview">
        <thead>
          <tr>
            <th colspan="5">策略变更情况</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th width="150"></th>
            <th>设备类策略</th>
            <th>端口类策略</th>
            <th>私有MIB类策略</th>
            <th>策略数量</th>
          </tr>
          <tr>
            <th width="150">更新</th>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and (select to_ptvid from v_policy_tplt_ver_change where to_ppiid=(select ppiid from v_current_released_ppiid) and from_ppiid=(select ppiid from v_branch_current_ppiid) and from_ptvid=pb.ptvid) is not null and pb.category=1
</sql:query>
       <c:forEach var="item" items="${items.rows}"><c:set var="totalUpdate4Device" value="${item.v}"/><c:out value="${item.v}"/></c:forEach>
            </td>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and (select to_ptvid from v_policy_tplt_ver_change where to_ppiid=(select ppiid from v_current_released_ppiid) and from_ppiid=(select ppiid from v_branch_current_ppiid) and from_ptvid=pb.ptvid) is not null and pb.category=4
</sql:query>
       <c:forEach var="item" items="${items.rows}"><c:set var="totalUpdate4Port" value="${item.v}"/><c:out value="${item.v}"/></c:forEach>
            </td>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and (select to_ptvid from v_policy_tplt_ver_change where to_ppiid=(select ppiid from v_current_released_ppiid) and from_ppiid=(select ppiid from v_branch_current_ppiid) and from_ptvid=pb.ptvid) is not null and pb.category=9
</sql:query>
       <c:forEach var="item" items="${items.rows}"><c:set var="totalUpdate4Mib" value="${item.v}"/><c:out value="${item.v}"/></c:forEach>
            </td>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and (select to_ptvid from v_policy_tplt_ver_change where to_ppiid=(select ppiid from v_current_released_ppiid) and from_ppiid=(select ppiid from v_branch_current_ppiid) and from_ptvid=pb.ptvid) is not null
</sql:query>
       <c:forEach var="item" items="${items.rows}"><c:set var="totalUpdate" value="${item.v}"/><c:out value="${item.v}"/></c:forEach>
            </td>
          </tr>
          <tr>
            <th width="150">新增</th>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
  select count(*) as v
  from
   t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid
                             inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
  where 
    ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0)
    and pt.category=1
</sql:query>
            <c:forEach var="item" items="${items.rows}"><c:out value="${item.v - totalUpdate4Device}"/></c:forEach>
            </td>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
  select count(*) as v
  from
   t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid
                             inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
  where 
    ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0)
    and pt.category=4
</sql:query>
            <c:forEach var="item" items="${items.rows}"><c:out value="${item.v - totalUpdate4Port}"/></c:forEach>
            </td>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
  select count(*) as v
  from
   t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid
                             inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
  where 
    ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0)
    and pt.category=9
</sql:query>
            <c:forEach var="item" items="${items.rows}"><c:out value="${item.v - totalUpdate4Mib}"/></c:forEach>
            </td>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
  select count(*) as v
  from
   t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid
                             inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
  where 
    ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0)
</sql:query>
            <c:forEach var="item" items="${items.rows}"><c:out value="${item.v - totalUpdate}"/></c:forEach>
            </td>
          </tr>
          <tr>
            <th width="150">删除</th>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)) and pb.category=1
</sql:query>
            <c:forEach var="item" items="${items.rows}"><c:out value="${item.v - totalUpdate4Device}"/></c:forEach>
            </td>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)) and pb.category=4
</sql:query>
            <c:forEach var="item" items="${items.rows}"><c:out value="${item.v - totalUpdate4Port}"/></c:forEach>
            </td>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)) and pb.category=9
</sql:query>
            <c:forEach var="item" items="${items.rows}"><c:out value="${item.v - totalUpdate4Mib}"/></c:forEach>
            </td>
            <td>
<sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid))
</sql:query>
            <c:forEach var="item" items="${items.rows}"><c:out value="${item.v - totalUpdate}"/></c:forEach>
            </td>
          </tr>
        </tbody>
      </table>
</c:if>
		</div>
	</form>
</body>
</html>