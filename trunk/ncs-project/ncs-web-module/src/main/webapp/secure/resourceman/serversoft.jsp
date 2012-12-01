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
		PartnerForm.path.value="";
		PartnerForm.mid.selected="0";
		PartnerForm.desc.value="";
		PartnerForm.nmsid.value="";
		PartnerForm.modid.value="";	
		PartnerForm.type.value="preNew";
		PartnerForm.action = "<%=request.getContextPath() %>/secure/resourceman/editServerSoftPre.wss";
		this.PartnerForm.submit();	
		document.getElementsByName("new").item(0).disabled = true;
		
	}
	
	function saveForm(){
		
		PartnerForm.type.value="save";
		PartnerForm.action = "<%=request.getContextPath() %>/secure/resourceman/editServerSoftPre.wss";
		this.PartnerForm.submit();
	    document.getElementsByName("new").item(0).disabled = false;
	}
	
	

function reloadNavi(){
	var modid_ID = document.getElementById("modidID").value;
	if( modid_ID==null || modid_ID == "" ||modid_ID==0 )
		document.getElementsByName("new").item(0).disabled = true;
	
	var mid_ID = document.getElementById("midId").value;
	if(mid_ID == null || mid_ID==0 || mid_ID == ""){
		document.getElementsByName("save_button").item(0).disabled = true;
	}
	window.parent.frames[2].location.reload();
}
</script>
</head>
<body onLoad="reloadNavi()"  class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  ><TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage"><TR><TD CLASS="pageTitle">资源管理
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
        
        <h1 id="title-bread-crumb">服务器名${model.servInfo.nmsname }</h1>
        <p ></p>
    

		<form name="PartnerForm">
		<a name="main"></a>
		<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		<input name="new" value="新建"  onClick="New()" type="button" class="buttons" id="functions">
    	</td>
    	<td>
    		<input name="save_button" value="保存" class="buttons" onClick="saveForm()" type="button"  id="functions">
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
		<input type="hidden" id="nmsidID" name="nmsid" value="${model.svrModMap.nmsid }">
		<input type="hidden" id="modidID" name="modid" value="${model.svrModMap.modid }">
		<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
		   <tr valign="top">
             <td class="table-row"  nowrap>
                <table cellspacing="1" cellpadding="0" border="0" width="100%" class="framing-table">
                   <tbody>
                      <tr class="table-row">
                        <td colspan="4">详细内容</td>
                      </tr>
                     <tr class="table-row">
			
					   <td nowrap="nowrap" width="30%">服务器名：</td>

					   <td>
					   <input id="sevNameId" name="sevName" class="input" readonly="readonly" value="${model.servInfo.nmsname }" type="text" maxlength="64"> 
					   <input value="${model.servInfo.nmsid }" name="servid" type="hidden"> 
					   <input name="operatetype" value="" type="hidden"></td>
					
				    </tr>
				    <tr class="table-row">
					   <td>安装路径：</td>
					   <td colspan="2"> <input name="path" class="input" type="text" value="${model.svrModMap.path }" maxlength="255"></td>
				    </tr>
				    <tr class="table-row">
					   <td>软件名称： <font color="#ff0000">*</font> </td>
					   <td colspan="2"><select id="midId" name="mid" class="input">
					<c:forEach items="${model.modulelist }" var="ml">
					<option value="${ml.modid}" <c:if test="${ml.modid == model.svrModMap.modid }">selected="selected"</c:if>>${ml.mname}</option>
					</c:forEach>
					</select></td>
				</tr>
				<tr class="table-row">
					<td nowrap="nowrap" valign="top">备注：</td>
					<td colspan="2">
					<textarea name="desc" class="select"><c:out value="${model.svrModMap.description }"/></textarea>
					</td>
				</tr>
                   </tbody>
                </table>
                </td>
                </tr>
                </table>
		
					
<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS="example table-autosort:2 framing-table">
<thead>
<tr>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" id="mname">软件名称</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" width="80">编辑/ 删除</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" id="path">安装路径</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" id="description">描述</th>
</tr>
</thead>

<c:forEach items="${model.servermodule }" var="m1" >

<tr class="table-row">

<td VALIGN="top"  class="collection-table-text" headers="mname" id="moduleNameID" width="20%">&nbsp;&nbsp;
	<c:forEach items="${model.modules}" var="modu">
		<c:if test="${m1.modid == modu.modid }" >
			<c:out value="${modu.mname}" />
		</c:if> 
	</c:forEach>
</td>




<td><a href="<%=request.getContextPath() %>/secure/resourceman/editServerSoftPre.wss?nmsid=${m1.nmsid }&modid=${m1.modid }"><img border="0" src="../../images/wtable_edit_sorts.gif" width="16" height="16" alt="编辑"></a>
&nbsp;&nbsp;<a href="<%=request.getContextPath() %>/secure/resourceman/deleteServerSoft.wss?nmsid=${m1.nmsid }&modid=${m1.modid }" onclick="return confirm('确定删除信息?');"><img border="0" src="../../images/delete.gif" width="16" height="16" alt="删除"></a>
</td>
<td VALIGN="top"  class="collection-table-text" headers="path" width="30%">&nbsp;&nbsp;<c:out value="${m1.path }"/> </td>
<td VALIGN="top"  class="collection-table-text" headers="description" width="35%">&nbsp;&nbsp;<c:out value="${m1.description }"/></td>
</tr>
</c:forEach>
</table>
</form>
</TD></TR></TBODY></TABLE></TD></TR></TABLE>

</body>
</html>

