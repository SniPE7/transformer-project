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
<script type="text/javascript" src="<%=request.getContextPath() %>/include/table.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/include/table.css" media="all">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<script type="text/javascript">
function add(){
	form1.formAction.value= 'add';

	form1.action = "<%=request.getContextPath() %>/secure/syslog/syslogns.wss";
	this.form1.submit();
}
function deleteform1(){
	//alert("in delete script");
	var v = confirm('确定删除信息?');
	if( ! v ){ return false;	}
	form1.formAction.value= 'delete';

	form1.action = "<%=request.getContextPath() %>/secure/syslog/deletesyslogns.wss";
	this.form1.submit();
}
function checkedAll(allCheckboxName,checkboxName) { 
o = document.getElementsByName(allCheckboxName); 
if (o[0].checked == true) { 
selAllCheckbox(checkboxName); 
} else { 
unselAllCheckbox(checkboxName); 
} 
} 

/*全选*/ 
function selAllCheckbox(checkboxName) { 
o = document.getElementsByName(checkboxName); 
for (i = 0; i < o.length; i++) { 
o[i].checked = true; 
} 
} 
/*取消全选*/ 
function unselAllCheckbox(checkboxName) { 
o = document.getElementsByName(checkboxName); 
for (i = 0; i < o.length; i++) { 
o[i].checked = false; 
} 
}
</script>
</head>
<body>


  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">基础信息</TD>
          <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">基础信息维护</TH>

     
  </TR>

  
  <TBODY ID="wasUniPortlet">
    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">Syslog Event Process_NS 列表</h1>
        <p ></p>

<a name="main"></a>
<form action="" name="form1" method="post">

<input type="hidden" name="formAction" value="${model.formAction}"/>

<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0"  width="100%" summary="Framing Table" CLASS="button-section">
       <tr>
	 
	    <td colspan="2">
								<c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
								</c:if>
       </td>
	</tr>
        <tr valign="top">
        <td class="table-button-section"  nowrap> 
     <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		<input type="button" name="button.new" value="新建"  onclick="add();" class="buttons" id="functions" />
    	</td>
		
        <input type="submit" name="hiddenButton1290018101908118000" value="hiddenButton" style="display:none" class="buttons" id="hiddenButton1290018101908118000"/>
    </tr></table>    

     
        </td>
        </tr>
        </table>
 
        
        
<!-- Data Table -->

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table">

	<TBODY>
		<TR>
			<TD CLASS="layout-manager" id="notabs">
																					

<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS="example table-autosort:2 framing-table">
<thead>
<tr>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" width="80" style="word-break:break-all">编辑/ 删除</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="220" style="word-break:break-all">OrigEvent</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="220" style="word-break:break-all">Mark</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="3%" style="word-break:break-all">Status</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="3%" style="word-break:break-all">AlertGroup</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="3%" style="word-break:break-all">SummaryCN</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:numeric column-head-name" SCOPE="col" width="3%" style="word-break:break-all">Severity1</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="3%" style="word-break:break-all">Manufacturer</th>
</tr>
</thead>

<c:forEach items="${model.pagedtabList.pageList}" var="c1" >
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="eveid">
<c:url value="/secure/syslog/updateToSyslogEventProcessNS.wss" var="markmanuf">
  <c:param name="mark" value="${c1.mark}" />
  <c:param name="manuf" value="${c1.manufacture}" />
</c:url>
<a href="<c:out value='${markmanuf}'/>" ><img border="0" src="../../images/wtable_edit_sorts.gif" width="16" height="16" alt="编辑"></a>

&nbsp;
<a href="<%=request.getContextPath() %>/secure/syslog/deletesyslogns.wss?mark=${c1.mark}&manufacture=${c1.manufacture}" onclick="return confirm('确定删除信息?');">
			<img border="0" src="../../images/delete.gif" width="16" height="16" alt="删除"></a>
</td>
<td VALIGN="top"  class="collection-table-text" width="220" style="word-break:break-all"><c:out
												value='${c1.origevent }' escapeXml="true" /></td>
<td VALIGN="top"  class="collection-table-text" width="220" style="word-break:break-all"><c:out value="${c1.mark }"/></td>
<td VALIGN="top"  class="collection-table-text" headers="" width="20%" style="word-break:break-all"><c:out value="${c1.status}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="" width="3%" style="word-break:break-all"><c:out value="${c1.alertgroup}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="" style="word-break:break-all"><c:out value="${c1.summarycn}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="" width="3%" style="word-break:break-all" ><c:if test="${c1.severity1Null == false}" ><c:out value="${c1.severity1}"/></c:if></td>
<td VALIGN="top"  class="collection-table-text" headers="" width="3%" style="word-break:break-all"><c:out value="${c1.manufacture}"/></td>
</tr>
</c:forEach>

<tr class="table-row">
<c:if test="${model.pagedtabList.firstPage }">[首页]</c:if>


<c:if test="${! model.pagedtabList.firstPage }">
  <a href="<%=request.getContextPath() %>/secure/syslog/syslogEventsProcess_NS.wss?cmd=firstPage">[首页</a>/
  <a href="<%=request.getContextPath() %>/secure/syslog/syslogEventsProcess_NS.wss?cmd=previousPage">上一页]</a>
</c:if>
<c:forEach var="sequence" begin="${model.startpage }" end="${model.endpage }">
   <a href="<%=request.getContextPath() %>/secure/syslog/syslogEventsProcess_NS.wss?page=${sequence - 1}">${sequence }</a>
</c:forEach>
<c:if test="${! model.pagedtabList.lastPage }">
  <a href="<%=request.getContextPath() %>/secure/syslog/syslogEventsProcess_NS.wss?cmd=nextPage">[下一页</a>/
  <a href="<%=request.getContextPath() %>/secure/syslog/syslogEventsProcess_NS.wss?cmd=lastPage">末页]</a>
</c:if>
<c:if test="${model.pagedtabList.lastPage }">[末页]</c:if>&nbsp;&nbsp;
第<c:out value="${model.pagedtabList.page + 1 }"></c:out>页/共<c:out value="${model.pagedtabList.pageCount }" />页
</tr>
</table>

<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">      
               共有记录数： ${model.total}<c:if test="${model.total > 0 }">,显示${model.startRecord}到${model.endRecord }</c:if>         
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>


</TD></TR></TBODY></TABLE>
</form>
</TD></TR></TBODY></TABLE>

</TD></TR></TABLE>

</body>
</html>