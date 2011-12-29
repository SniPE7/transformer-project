<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
  String path = request.getContextPath();
      String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" href="css/start/jquery-ui-1.8.14.custom.css" rel="stylesheet" />
<link href="css/index_concise.css" rel="stylesheet" type="text/css" media="screen" />
<link type="text/css" href="media/css/demo_page.css" rel="stylesheet" />
<link type="text/css" href="media/css/demo_table.css" rel="stylesheet" />
<link type="text/css" href="media/css/demo_table_jui.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" language="javascript" src="media/js/jquery.dataTables.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#sessions').dataTable({
			"bProcessing" : true,
			"bServerSide" : true,
			"sAjaxSource" : "./admin/monitor/sessions",
      "aoColumns": [
                     { "mDataProp": "artifactID" },
                     { "mDataProp": "artifactDomain" },
                     { "mDataProp": "createTime" },
                     { "mDataProp": "lastAccessTime" },
                     { "mDataProp": "personDTO" }
                 ],
      "bPaginate": true,
      "sDom": 'R<"H"lfr>t<"F"ip<',
      "bJQueryUI": true,
      "iDisplayLength": 20,
      "sPaginationType": "full_numbers",
      "oLanguage": {                          //汉化  
         "sLengthMenu": "每页显示 _MENU_ 条记录",  
         "sZeroRecords": "没有检索到数据",  
         "sInfo": "当前数据为从第 _START_ 到第 _END_ 条数据；总共有 _TOTAL_ 条记录",  
         "sInfoEmtpy": "没有数据",  
         "sProcessing": "正在加载数据...",  
         "oPaginate": {  
             "sFirst": "首页",  
             "sPrevious": "前页",  
             "sNext": "后页",  
             "sLast": "尾页"  
         }  
       }       
		});
	});
</script>
</head>
<body>
	<table cellpadding="0" cellspacing="0" border="0" class="display sessions_container" id="sessions">
		<thead>
			<tr>
				<th width="20%">Session ID</th>
				<th width="25%">Session Domain</th>
				<th width="25%">Create Time(s)</th>
				<th width="15%">Last Access Time</th>
				<th width="15%">Person DTO</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5" class="dataTables_empty">Loading data from server</td>

			</tr>
		</tbody>
		<tfoot>
			<tr>
        <th>Session ID</th>
        <th>Session Domain</th>
        <th>Create Time(s)</th>
        <th>Last Access Time</th>
        <th>Person DTO</th>
			</tr>
		</tfoot>
	</table>

</body>
</html>