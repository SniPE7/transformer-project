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
<title>category test</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/include/table.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/include/table.css" media="all">
<% int nodei=0; %>
<script type="text/javascript">
	
	
	function New(){
		//PartnerForm.reset();
		PartnerForm.username.value="";
		PartnerForm.nmsname.value="";
		PartnerForm.nmsid.value="";
		PartnerForm.nmsip.value="";
		PartnerForm.ostype.value="";
		PartnerForm.description.value="";
		//PartnerForm.action = "<%=request.getContextPath() %>/secure/resourceman/servermanagement.wss";
		//this.PartnerForm.submit();
	}
	
	function saveForm(){
	var flag= true;
	   var serverName = document.getElementById("nmsnameId").value;
	   if(!serverName){
	            alert("服务器名称不能为空");
	            flag = false;
	   }
	   var serverIP = document.getElementById("nmsipId").value;
	   var exp=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	   if(!serverIP){
	            alert("服务器IP不能为空");
	            flag = false;
	   }else{
	        var reg = serverIP.match(exp); 
	        if(reg==null){
	        alert("IP地址不合法！"); 
	        flag = false;
	        }
	   }
	   var os_type = document.getElementById("ostypeId").value;
	   if(!os_type){
	            alert("OS 类型不能为空");
	            flag = false;
	   }
	   if(flag){
		PartnerForm.type.value="save";
		PartnerForm.action = "<%=request.getContextPath() %>/secure/resourceman/servermanagementsave.wss";
		this.PartnerForm.submit();
		}
	}
	
	function Del(){
		
		var v = confirm('确定删除信息?');
	    if( ! v ){ return false;	}
	    
	    PartnerForm.type.value="del";
		PartnerForm.action = "<%=request.getContextPath() %>/secure/resourceman/servermanagementdel.wss";
		this.PartnerForm.submit();	
		
	}


function reloadNavi(){


window.parent.frames[2].location.reload();

}
</script>
</head>
<body onLoad="reloadNavi()" class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >
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
      <TH class="wpsPortletTitle" width="100%">网管系统规划</TH>

     
  </TR>
  

   <TBODY ID="wasUniPortlet">

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">网管系统规划</h1>
        <p ></p>
    

		<form name="PartnerForm">
		<a name="main"></a>
		<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		
    	<td>
    		<input value="保存 "  onClick="saveForm()" type="button" class="buttons" id="functions">
    	</td>
    </tr></table>    
  <c:if test="${model.message != null &&  model.message != ''}">
	<div id="errmsg"><fmt:message>${model.message}</fmt:message></div>
</c:if> 
    <input type="hidden" name="definitionName" value="JDBCProvider.collection.buttons.panel"/>
    <input type="hidden" name="buttoncontextType" value="JDBCProvider"/>
    <input type="hidden" name="buttoncontextId" value="All Scopes"/>
    <input type="hidden" name="buttonperspective" value="null"/> 
     <input type="hidden" name="formAction" value="prepare"/>  

        </td>
        </tr>
        </table>
		<input type="hidden" name="type" value="">
		<input type="hidden" name="nmsid" value="${model.svrModMap.nmsid }">
		<input type="hidden" name="modid" value="${model.svrModMap.modid }">
		
		
		<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
		   <tr valign="top">
             <td class="table-row"  nowrap>
                <table cellspacing="1" cellpadding="0" border="0" width="100%" class="framing-table">
                   <tbody>
                      <tr class="table-row">
                        <td colspan="4">详细内容</td>
                      </tr>
                      <tr class="table-row">
					   <td nowrap="nowrap">服务器名称:<font color="#ff0000">*</font></td>
					   <td><input id="nmsnameId" name="nmsname" class="input" type="text" value="${model.nmsname }" maxlength="64"></td>
					   <td nowrap="nowrap">用户名:<font color="#ff0000"></font></td>
					   <td><input name="username" class="input" type="text" value="${model.username }" maxlength="64"></td>
				     </tr>
				<tr class="table-row">
					<td nowrap="nowrap">服务器 IP:<font color="#ff0000">*</font></td>
					<td><input id="nmsipId" name="nmsip" class="input" type="text" value="${model.nmsip }" maxlength="64"></td>
					<!-- c:if test="${model.type }=='new'"  -->
					<td nowrap="nowrap">密码:<font color="#ff0000"></font></td>
					<td><input name="password" class="input" type="password" value="${model.password }" maxlength="64"></td><!-- /c:if -->
				</tr>
				
				<tr class="table-row">
					<td nowrap="nowrap">OS 类型:<font color="#ff0000">*</font></td>
					<td colspan="3">
					<select id="ostypeId" name="ostype" class="select">
					
					<c:if test="${model.ostype == 'unix' }">
					<option value="unix" selected="selected">unix</option>
					<option value="linux">linux</option></c:if>
					<c:if test="${model.ostype == 'linux' }">
					<option value="unix">unix</option>
					<option value="linux" selected="selected">linux</option></c:if>
					<c:if test="${model.ostype != 'unix' and model.ostype != 'linux'}">
					<option value="unix" selected="selected">unix</option>
					<option value="linux">linux</option></c:if>
					</select></td>
				</tr>
				<tr class="table-row">
					<td nowrap="nowrap" valign="top">备注:</td>
					<td colspan="3"><textarea name="description" class="input" >${model.description }</textarea>
					<br></td>
				</tr>
                   </tbody>
                </table>
             
           
		</table>
		
		

<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS="example table-autosort:2 framing-table">
<thead>
<tr>
<th NOWRAP VALIGN="BOTTOM" class="table-sortable:default column-head-name"  SCOPE="col" id="nmsname">服务器名称</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >编辑/ 删除</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" id="username">用户名</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" id="nmsip">服务器 IP</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" id="ostype">OS 类型</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" id="description">备注</th>
</tr>
</thead>
<c:forEach items="${model.serverinfo}" var="ser" >
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="nmsname"><c:out value="${ser.nmsname}" /> </td>
<td><a href="<%=request.getContextPath() %>/secure/resourceman/editServerInfoPre.wss?nmsid=${ser.nmsid}"><img border="0" src="../../images/wtable_edit_sorts.gif" width="16" height="16" alt="编辑"></a>
&nbsp;&nbsp;<a href="<%=request.getContextPath() %>/secure/resourceman/serverManagementdelete.wss?nmsid=${ser.nmsid}" onclick="return confirm('确定删除信息?');"><img border="0" src="../../images/delete.gif" width="16" height="16" alt="删除"></a>
</td>
<td VALIGN="top"  class="collection-table-text" headers="username"><c:out value="${ser.username }"/></td>
<td VALIGN="top"  class="collection-table-text" headers="nmsip"><c:out value="${ser.nmsip }"/></td>
<td VALIGN="top"  class="collection-table-text" headers="ostype"><c:out value="${ser.ostype }"/></td>
<td VALIGN="top"  class="collection-table-text" headers="description"><c:out value="${ser.description }"/></td>
</tr>
</c:forEach>
</table>
<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			
            <TD CLASS="table-totals" VALIGN="baseline">               
            Total:<c:out value="${model.total }"/>
             &nbsp;&nbsp;&nbsp;              
		</TD>
	</TR>

</TABLE>

</form>
</TD></TR></TBODY></TABLE></TD></TR></TABLE>

</body>
</html>

