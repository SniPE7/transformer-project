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
<script type="text/javascript">
function addform1(){
	form1.formAction.value= 'add';
	
	for(i=0;i<form1.chkworkday.length; i++){
		form1.chkworkday.checked =false;
	}
	

	form1.action = "<%=request.getContextPath() %>/secure/policyapply/newPolicyPeriodDef.jsp";
	this.form1.submit();
}
function saveform1(){
	form1.formAction.value= 'save';
	

	form1.action = "<%=request.getContextPath() %>/secure/policyapply/savePolicyPeriodDef.wss";
	this.form1.submit();
}

function deleteform1(){
	form1.formAction.value= 'delete';
	

	form1.action = "<%=request.getContextPath() %>/secure/policyapply/deletePolicyPeriodDef.wss";
	this.form1.submit();
}

function reloadNavi(){

<% if (request.getParameter("refresh")!=null){%>
window.parent.frames[2].location.reload();
<% }%>
}
</script>
</head>
<body onLoad="reloadNavi()" class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">时间段策略定义
          </TD>
          <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">时间段策略定义</TH>

  </TR>
 
  
  <TBODY ID="wasUniPortlet">
    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">时间段策略定义</h1>
        <p ></p>
<form action="<%=request.getContextPath() %>/secure/policyapply/savePolicyPeriodDef.wss" method="post" name="form1" >
<a name="main"></a>
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
&nbsp;	<td>
    		<input type="button" name="button.update" value="新建"  onclick="addform1();" class="buttons" id="functions" />
    	</td>
		<td>
    		<input type="button" name="button.update" value="保存"  onclick="saveform1();" class="buttons" id="functions" />
    	</td>    	
		<td>
    		<input type="button" name="button.update" value="删除"  onclick="deleteform1();" class="buttons" id="functions" />
    	</td>      	
    </tr></table>    
    <c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
	 </c:if>
    <input type="hidden" name="defaultflag" value="${model.policyperiod.defaultflag}"/>
    <input type="hidden" name="ppid" value="${model.policyperiod.ppid}"/>
    <input type="hidden" name="buttoncontextId" value="All Scopes"/>
    <input type="hidden" name="buttonperspective" value="null"/> 
    <input type="hidden" name="formAction" value=""/>  

        </td>
        </tr>
        </table>
 
        
        
<!-- Data Table -->

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table">

	<TBODY>
		<TR>
			<TD CLASS="layout-manager" id="notabs">


<table BORDER="0" CELLPADDING="3" CELLSPACING="1" WIDTH="100%" SUMMARY="List table" CLASS="framing-table">
<tr class="table-row"><td>时间段名 </td><td colspan="5"><input type="text" value="${model.policyperiod.ppname}" name="ppname" style="width: 245px"></td></tr>


<!-- d1 -->
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="day"align="right"> 周一</td>

<td VALIGN="top"  class="collection-table-text" ><input type="checkbox" name="chkworkday" <c:if test="${model.policyperiod.workday[0]==1 }">checked="checked"</c:if>  value="0|${model.policyperiod.workday[0]}" /> </td>
<td VALIGN="top"  class="collection-table-text" >开始时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.starttimeFormated[0] }" name="starttime" ></td>
<td VALIGN="top"  class="collection-table-text" >结束时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.endtimeFormated[0] }" name="endtime" ></td>
</tr>

<!-- d1 -->
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="day"align="right">  周二</td>

<td VALIGN="top"  class="collection-table-text" ><input type="checkbox" name="chkworkday" <c:if test="${model.policyperiod.workday[1]==1 }">checked="checked"</c:if>  value="1|${model.policyperiod.workday[1]}" /> </td>
<td VALIGN="top"  class="collection-table-text" >开始时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"   value="${model.policyperiod.starttimeFormated[1] }" name="starttime"  ></td>
<td VALIGN="top"  class="collection-table-text" >结束时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.endtimeFormated[1] }" name="endtime" ></td>
</tr>

<!-- d1 -->
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="day"align="right">  周三</td>

<td VALIGN="top"  class="collection-table-text" ><input type="checkbox" name="chkworkday" <c:if test="${model.policyperiod.workday[2]==1 }">checked="checked"</c:if>  value="2|${model.policyperiod.workday[2]}" /> </td>
<td VALIGN="top"  class="collection-table-text" >开始时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.starttimeFormated[2] }"name="starttime" ></td>
<td VALIGN="top"  class="collection-table-text" >结束时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.endtimeFormated[2] }" name="endtime" ></td>
</tr>

<!-- d1 -->
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="day"align="right">   周四</td>

<td VALIGN="top"  class="collection-table-text" ><input type="checkbox" name="chkworkday" <c:if test="${model.policyperiod.workday[3]==1 }">checked="checked"</c:if>  value="3|${model.policyperiod.workday[3]}"/> </td>
<td VALIGN="top"  class="collection-table-text" >开始时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.starttimeFormated[3] }" name="starttime" ></td>
<td VALIGN="top"  class="collection-table-text" >结束时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.endtimeFormated[3] }" name="endtime" ></td>
</tr>

<!-- d1 -->
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="day"align="right">  周五</td>

<td VALIGN="top"  class="collection-table-text" ><input type="checkbox" name="chkworkday" <c:if test="${model.policyperiod.workday[4]==1 }">checked="checked"</c:if>  value="4|${model.policyperiod.workday[4]}"/> </td>
<td VALIGN="top"  class="collection-table-text" >开始时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.starttimeFormated[4] }" name="starttime" ></td>
<td VALIGN="top"  class="collection-table-text" >结束时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.endtimeFormated[4] }" name="endtime" ></td>
</tr>

<!-- d1 -->
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="day"align="right">  周六</td>

<td VALIGN="top"  class="collection-table-text" ><input type="checkbox" name="chkworkday" <c:if test="${model.policyperiod.workday[5]==1 }">checked="checked"</c:if>  value="5|${model.policyperiod.workday[5]}"/> </td>
<td VALIGN="top"  class="collection-table-text" >开始时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.starttimeFormated[5] }" name="starttime" ></td>
<td VALIGN="top"  class="collection-table-text" >结束时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.endtimeFormated[5] }" name="endtime" ></td>
</tr>
<!-- d1 -->
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="day"align="right">  周日</td>

<td VALIGN="top"  class="collection-table-text" ><input type="checkbox" name="chkworkday" <c:if test="${model.policyperiod.workday[6]==1 }">checked="checked"</c:if>  value="6|${model.policyperiod.workday[6]}"/> </td>
<td VALIGN="top"  class="collection-table-text" >开始时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.starttimeFormated[6] }" name="starttime" ></td>
<td VALIGN="top"  class="collection-table-text" >结束时间</td>
<td VALIGN="top"  class="collection-table-text" ><input type="text"  value="${model.policyperiod.endtimeFormated[6] }" name="endtime" ></td>
</tr>

<%--
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text">启用</td>
<td><input type="checkbox" <c:if test="${model.policyperiod.enabled == 1}" >checked="checked"</c:if>  name="enabled" value="${model.policyperiod.enabled}"></td><td colspan="5"></td></tr>
--%>
<tr class="table-row"><td>备注</td><td colspan="5"><textarea rows="2" cols="20" style="width: 245px"
											name="description">${model.policyperiod.description }</textarea></td></tr>
</table>
<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
            
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>
</TD></TR></TBODY></TABLE>
</form>
</TD></TR></TBODY></TABLE></TD></TR></TABLE>

</body>
</html>