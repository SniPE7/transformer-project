<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>

<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/include/table.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/include/table.css" media="all">
<title>baseinfonavi</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<!-- list of menu -->





<script language="javascript">


function deleteDevType(){
	var v = confirm('确认删除选择的设备?');
	if (v){
		devinfo.formAction.value="delete";
		devinfo.action= "<%=request.getContextPath()%>/secure/resourceman/deletedeviceInfo.wss"
		 this.devinfo.submit();
	}
}

function onloaddo(){
	var v ="null";
	if(v=="true" ) alert ("Poll成功!");
	if(v=="false") alert ("Poll失败 !");
}

function toNewPage(){
	devinfo.action = "<%=request.getContextPath() %>/secure/resourceman/toNewPage.wss";
	devinfo.submit();
}
function exportDevInfo(){

    devinfo.action = "<%=request.getContextPath()%>/secure/resourceman/exportDevInfo.wss";
    devinfo.submit();
    
}

function importDevInfo(){

   devinfo.action = "<%=request.getContextPath()%>/secure/resourceman/importDevInfo.jsp";
   devinfo.submit();
  
}

function checkedAll(allCheckboxName,checkboxName) { 
var checked = false;
o = document.getElementsByName(allCheckboxName); 
if (o[0].checked == true) { 
selAllCheckbox(checkboxName); 
checked = true;
} else { 
unselAllCheckbox(checkboxName); 
checked = false;
} 
}
/*全选*/ 
function selAllCheckbox(checkboxName) { 
var o = document.getElementsByName(checkboxName); 
for (i = 0; i < o.length; i++) { 
o[i].checked = true; 
} 
}
/*取消全选*/ 
function unselAllCheckbox(checkboxName) { 
var o = document.getElementsByName(checkboxName); 
for (i = 0; i < o.length; i++) { 
o[i].checked = false; 
} 
}
function checkingAll(obj, checkboxId){
	////var chk = document.getElementById(allCheckboxName);
	var bool = obj.checked;
	var chkbz = document.all[checkboxId];
	//alert("length="+chkbz.length+";bool="+bool);
	try{	
		for( var i=0; i<chkbz.length;i++){
			chkbz[i].checked = bool;
			//alert("i="+i+"; chkbz[i]"+chkbz[i]);
		}
	}
	catch(err){
	
	}

}
// -->
</script>
<% int nodei=0; %>


<body onLoad="onloaddo();" class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">资源管理 
          </TD>
          <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">网络设备</TH>

    
  </TR>
   

  
  <TBODY ID="wasUniPortlet">
    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">设备列表</h1>

<form method="post" action="<%=request.getContextPath() %>/secure/resourceman/deviceInfoOfGid.wss" name="devinfo">
        
<!-- button section --><br>
					<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		<input type="button" name="button.new" value="新建"  onclick="toNewPage()" class="buttons" id="functions"/>
    	</td>
 
        <td>
    		<input type="button" name="button.new" value="导出"  onclick="exportDevInfo()" class="buttons" id="functions"/>
    	</td>
    	
    	<td>
    		<input type="button" name="button.new" value="导入"  onclick="importDevInfo()" class="buttons" id="functions"/>
    	</td>
    	
    </tr></table>    
     <c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
								</c:if>
       
    <input type="hidden" name="gid" value="${model.gid}"/>
    <input type="hidden" name="supid" value="${model.grpnet.supid}"/>
    <input type="hidden" name="level" value="${model.grpnet.level}"/>
     <input type="hidden" name="formAction" value="0"/>  

        </td>
        </tr>
        </table>
 
        
        
<!-- Data Table -->

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table" id="tableList">

	<TBODY>
		<TR>
			<TD CLASS="layout-manager" id="notabs">       


<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS="example table-autosort:3 framing-table">
<thead>
<tr>
    <th NOWRAP VALIGN="BOTTOM" class="column-head-name" SCOPE="col" width="5%"><input type="checkbox" name="selectall"  id="selectall01" onClick="checkingAll(this,'sel01')"></th>
	<th NOWRAP VALIGN="BOTTOM" class="table-sortable:default column-head-name" SCOPE="col" width="80">编辑/ 删除</th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="5%">资源编码</th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="10%">设备名称&nbsp;<input name="filter" size="15" onkeyup="Table.filter(this,this)"></th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="8%">设备别名</th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="10%">IP&nbsp;<input name="filter" size="15" onkeyup="Table.filter(this,this)"></th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="5%">设备类型</th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="5%">子类型</th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="5%">型号</th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="10%">负责人</th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="8%">负责人电话</th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" >制造厂商</th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="5%">SNMP版本</th>
	<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="5%">用途</th>
</tr>
</thead>        
	<tbody>
		<c:forEach items="${model.deviceinfo}" var="theIp" >
		<tr class="table-row" ondbclick="openUrl('<%=request.getContextPath() %>/secure/resourceman/editdeviceInfo.wss?devid=${theIp.devid}&gid=${model.gid }','popk', 800,600,'yes');" >
			<td VALIGN="middle"  class="collection-table-text" align="center">
            <input type="checkbox" name="sel" id="sel01"  value="${theIp.devid}"/>
            </td>
			<td VALIGN="middle"  class="collection-table-text" headers="rsno">
			<a href="<%=request.getContextPath() %>/secure/resourceman/editdeviceInfo.wss?devid=${theIp.devid}&gid=${model.gid }"  >
			<img border="0" src="../../images/wtable_edit_sorts.gif" width="16" height="16" alt="编辑"></a>
			&nbsp;
			<a href="<%=request.getContextPath() %>/secure/resourceman/deletedeviceInfo.wss?devid=${theIp.devid}&gid=${model.gid }" onclick="return confirm('确定删除信息?');"><img border="0" src="../../images/delete.gif" width="16" height="16" alt="删除"></a></td>
			<td VALIGN="top"  class="collection-table-text" headers="rsno">${theIp.rsno }&nbsp;</td>
			<td VALIGN="top"  class="collection-table-text" headers="sysname">${theIp.sysname}&nbsp;</td>
			<td VALIGN="top"  class="collection-table-text" headers="sysnamealias">${theIp.sysnamealias}&nbsp;</td>
			<td VALIGN="top"  class="collection-table-text" headers="devip">${theIp.devip }&nbsp;</td>
			<td VALIGN="top"  class="collection-table-text" headers="dtid">${model.catemap[model.dtmap[theIp.dtid].category] }&nbsp;</td>
			<td VALIGN="top"  class="collection-table-text" headers="subcategory">${ model.dtmap[theIp.dtid].subcategory }&nbsp;</td>
			<td VALIGN="top"  class="collection-table-text" headers="dtid.model">${model.dtmap[theIp.dtid].model }&nbsp;</td>
<%--			<td VALIGN="top"  class="collection-table-text" headers="servicename">$\{theIp.servicename}</td>--%>
			<td VALIGN="top"  class="collection-table-text" headers="admin">${theIp.admin }&nbsp;</td>
			<td VALIGN="top"  class="collection-table-text" headers="phone">${theIp.phone }&nbsp;</td>
			<td VALIGN="top"  class="collection-table-text" headers="mrname">${model.manufmap[theIp.mrid].mrname}&nbsp;</td>
<%--			<td VALIGN="top"  class="collection-table-text" headers="devgrpname">$\{theIp.devicegroup.name }</td>--%>
			<td VALIGN="top"  class="collection-table-text" headers="snmpver">${theIp.snmpversion }&nbsp;</td>
			<td VALIGN="top"  class="collection-table-text" headers="snmpver">
			<c:if test="${theIp.domainid == 0}">其他</c:if>
			<c:if test="${theIp.domainid == 1}">网点</c:if>
			<c:if test="${theIp.domainid == 2}">ATM</c:if>
			&nbsp;</td>
		</tr>
		</c:forEach>

	</tbody>
</table>



<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
            Total ${fn:length(model.deviceinfo)}  
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>

</TD></TR></TBODY></TABLE>
</form>
</TD></TR></TABLE>

</TD></TR></TABLE>
</body>
</html>