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
<title>策略定制</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css" media="all">
<script type="text/javascript" src="<%=request.getContextPath() %>/include/table.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/include/table.css" media="all">

<script type="text/javascript" language="javascript">
function exportPlicy(){
	form1.formAction.value= 'export';
	form1.action = "<%=request.getContextPath()%>/secure/policyapply/exportPolicy.wss";

	this.form1.submit();
}
function checkedAll(allCheckboxName,checkboxName) { 
var checked = false;
o = document.getElementById(allCheckboxName); 
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
var o = document.getElementById(checkboxName); 
for (i = 0; i < o.length; i++) { 
o[i].checked = true; 
} 
} 
/*取消全选*/ 
function unselAllCheckbox(checkboxName) { 
var o = document.getElementById(checkboxName); 
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
</script>
</head>
<body >

<% int countChecked = 0; %>

  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">数据库维护</TD>
          <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>
  </TABLE>
  
  <TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  	<TD valign="top">
		<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">
  <TR>
      <TH class="wpsPortletTitle" width="100%">数据库维护</TH>
  </TR>

   
  <TBODY ID="wasUniPortlet">
    <TR>   
  		<TD CLASS="wpsPortletArea" COLSPAN="3" >
    
            <form action="" method="post" name="form1">
                <a name="important"></a> 
                <h1 id="title-bread-crumb">策略导出</h1>
        
                <a name="main"></a>
                <!-- button section -->
                <br>
                <table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

                    <tr valign="top">
                    	<td class="table-button-section" nowrap> 
    
   							<table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0" >
                            	<tr> 
 
                                    <td><input type="button" name="button.export" value="导出Excel" onClick="exportPlicy();" class="buttons" id="functions"/>
                                      &nbsp;
       	 								<input type="submit" name="hiddenButton1290018101908118000" value="hiddenButton" style="display:none" class="buttons" id="hiddenButton1290018101908118000"/>
    							</tr>
    							<tr>
                                	<td>
                                        <c:if test="${model.message != null &&  model.message != ''}">
                                            <div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
                                        </c:if>
                                     </td>
								</tr>
                           </table>    
                          <input type="hidden" name="formAction" value=""/>  
        				</td>
        			</tr>
         
        			<tr><td><input type="hidden" name="mode" value="${model.mode}"/></td></tr>
       			 </table>
<!-- Data Table -->

                <TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table">
                
                    <TBODY>
                        <TR>
                            <TD CLASS="layout-manager" id="notabs">


<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS="example table-autosort:1 framing-table">
<thead>
<tr>
<th NOWRAP VALIGN="BOTTOM" class="column-head-name" SCOPE="col" width="5%"><input type="checkbox" name="selectall" id="selectall01" onClick="checkingAll(this,'sel01')"></th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="10%">策略类型&nbsp;<input name="filter" size="15" onkeyup="Table.filter(this,this)"></th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="20%">策略名称&nbsp;<input name="filter" size="15" onkeyup="Table.filter(this,this)"></th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" >描述</th>
</tr>
</thead>
<% countChecked = 0; %>
<c:forEach items="${model.policybase}" var="c1" >
<tr class="table-row">
<td VALIGN="middle"  class="collection-table-text" align="center">
<input type="checkbox" id="sel01" name="sel" value="${c1.mpid}|${c1.category}"/>
</td>
<td VALIGN="middle"  class="collection-table-text">
	<c:if test="${ (c1.category == 1)}">设备策略</c:if>
    <c:if test="${ (c1.category == 4)}">端口策略</c:if>
    <c:if test="${ (c1.category == 9)}">私有Mib策略</c:if> 
	<c:if test="${ (c1.category == 16)}">时间段策略</c:if>
</td>
<td VALIGN="middle"  class="collection-table-text">
	<c:choose>
	<c:when test="${ (c1.category == 16)}">
        <a 
            href="<%=request.getContextPath() %>/secure/policyapply/policyPeriodDef.wss?cate=${c1.category}&ppid=${c1.mpid}&ppname=${c1.mpname }"
				target="detail" dir="ltr"
				title="c1.mpname }">${c1.mpname}
        </a>
    </c:when>
    <c:otherwise>
        <a 
            href="<%=request.getContextPath() %>/secure/policyapply/policyDefinition.wss?cate=${c1.category}&mpid=${c1.mpid }" 
            target="detail" dir="ltr" title="${c1.mpname}">${c1.mpname}
        </a>
    </c:otherwise>
    </c:choose>
</td>
<td VALIGN="middle"  class="collection-table-text">${c1.description} </td>
</tr>
<% countChecked++; %>
</c:forEach>
</table>


<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function" id="selCountTab">

	<TR>
            <TD CLASS="table-totals" VALIGN="baseline">               
            Total ${fn:length(model.policybase)}
             &nbsp;&nbsp;&nbsp;           
		</TD>
	</TR>

</TABLE>


</TD></TR></TBODY></TABLE>




</form>
</TD></TR></TABLE>

</body>
</html>