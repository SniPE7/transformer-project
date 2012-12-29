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
	function reloadNavi() {
		window.parent.frames[2].location.reload();
	}
</script>
<script language="javascript">
	function ShowWaiting() {
		document.getElementById('doing').style.visibility = 'visible';
	}
	function CloseWaiting() {
		document.getElementById('doing').style.visibility = 'hidden';
	}
</script>
<body onLoad="reloadNavi()" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id='doing' style='visibility: hidden; vZ-INDEX: 12000; LEFT: 0px; WIDTH: 100%; CURSOR: wait; POSITION: absolute; TOP: 0px; HEIGHT: 100%'>
  <table width='100%' height='100%' id="Table1">
    <tr align='center' valign='middle'>
      <td>
        <table id="Table2" border="1" style="background: white; height: 100px; width: 230px;">
          <tr align='center' valign='middle'>
            <td style="font-size: 12px; color: red;"><img src="../../images/icon_progress.gif"> 处理中, 请等待 ...</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
	<form id="mainform" action="<%=request.getContextPath()%>/secure/policyapply/upgradePolices.wss" method="post">
		<input type="hidden" name="operation" value="" />
		<div style="background-color: #FFFFFF; margin: 5px; padding: 10px;">
			接收策略模板
			<hr />
			<c:if test="${model.needUpgrade || model.needMigrate }">
				<div class="buttonArea">
					<c:if test="${model.needUpgrade || model.needMigrate}">
            <input type="button" value="立即升级" onclick="document.forms['mainform'].operation.value='upgrade';this.disabled=true; ShowWaiting(); document.forms['mainform'].submit();" />
					</c:if>
					<c:if test="${model.needMigrate }">
						<input type="hidden" value="立即迁移" onclick="document.forms['mainform'].operation.value='migrate';this.disabled=true; ShowWaiting(); document.forms['mainform'].submit();" />
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
						<td><c:out value="${model.appliedPolicyPublishInfo.version}" />
							<c:if test="${!model.needUpgrade && !model.needMigrate }">
								<font color="blue">&nbsp;[已经使用最新版本]</font>
							</c:if>
							<c:if test="${model.needUpgrade || model.needMigrate }">
								<font color="red">&nbsp;[需要升级]</font>
							</c:if></td>
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

			<c:if test="${model.needUpgrade || model.needMigrate }">
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
							<td><fmt:formatDate value="${model.releasedPolicyPublishInfo.publishTime}" pattern="yyyy-MM-dd HH:mm:ss" type="both" timeStyle="full" /></td>
						</tr>
						<tr>
							<th width="150">备注</th>
							<td><c:out value="${model.releasedPolicyPublishInfo.description}" /></td>
						</tr>
					</tbody>
				</table>
			</c:if>

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
							<td><sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and (select to_ptvid from v_policy_tplt_ver_change where to_ppiid=(select ppiid from v_current_released_ppiid) and from_ppiid=(select ppiid from v_branch_current_ppiid) and from_ptvid=pb.ptvid) is not null and pb.category=1
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:set var="totalUpdate4Device" value="${item.v}" />
									<c:out value="${item.v}" />
								</c:forEach></td>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and (select to_ptvid from v_policy_tplt_ver_change where to_ppiid=(select ppiid from v_current_released_ppiid) and from_ppiid=(select ppiid from v_branch_current_ppiid) and from_ptvid=pb.ptvid) is not null and pb.category=4
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:set var="totalUpdate4Port" value="${item.v}" />
									<c:out value="${item.v}" />
								</c:forEach></td>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and (select to_ptvid from v_policy_tplt_ver_change where to_ppiid=(select ppiid from v_current_released_ppiid) and from_ppiid=(select ppiid from v_branch_current_ppiid) and from_ptvid=pb.ptvid) is not null and pb.category=9
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:set var="totalUpdate4Mib" value="${item.v}" />
									<c:out value="${item.v}" />
								</c:forEach></td>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and (select to_ptvid from v_policy_tplt_ver_change where to_ppiid=(select ppiid from v_current_released_ppiid) and from_ppiid=(select ppiid from v_branch_current_ppiid) and from_ptvid=pb.ptvid) is not null
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:set var="totalUpdate" value="${item.v}" />
									<c:out value="${item.v}" />
								</c:forEach></td>
						</tr>
						<tr>
							<th width="150">新增</th>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
  select count(*) as v
  from
   t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid
                             inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
  where 
    ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0)
    and pt.category=1
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:out value="${item.v - totalUpdate4Device}" />
								</c:forEach></td>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
  select count(*) as v
  from
   t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid
                             inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
  where 
    ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0)
    and pt.category=4
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:out value="${item.v - totalUpdate4Port}" />
								</c:forEach></td>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
  select count(*) as v
  from
   t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid
                             inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
  where 
    ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0)
    and pt.category=9
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:out value="${item.v - totalUpdate4Mib}" />
								</c:forEach></td>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
  select count(*) as v
  from
   t_policy_template_ver ptv inner join t_policy_template pt on pt.ptid=ptv.ptid
                             inner join t_policy_publish_info ppi on ppi.ppiid=ptv.ppiid
  where 
    ptv.ppiid=(select ppiid from v_current_released_ppiid) and ptv.ptvid not in (select ptvid from t_policy_base where ptvid>0)
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:out value="${item.v - totalUpdate}" />
								</c:forEach></td>
						</tr>
						<tr>
							<th width="150">删除</th>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)) and pb.category=1
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:out value="${item.v - totalUpdate4Device}" />
								</c:forEach></td>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)) and pb.category=4
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:out value="${item.v - totalUpdate4Port}" />
								</c:forEach></td>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid)) and pb.category=9
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:out value="${item.v - totalUpdate4Mib}" />
								</c:forEach></td>
							<td><sql:query var="items" dataSource="jdbc/nccDS">
select count(*) as v from t_policy_base pb where ptvid > 0 and ptvid not in (select ptvid from t_policy_template_ver where ppiid=(select ppiid from v_current_released_ppiid))
</sql:query> <c:forEach var="item" items="${items.rows}">
									<c:out value="${item.v - totalUpdate}" />
								</c:forEach></td>
						</tr>
					</tbody>
				</table>
			</c:if>
		</div>
	</form>
</body>
</html>