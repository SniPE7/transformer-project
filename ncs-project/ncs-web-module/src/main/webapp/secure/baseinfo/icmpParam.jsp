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
function add(){
	form1.formAction.value= 'add';

	form1.action = "<%=request.getContextPath() %>/secure/baseinfo/newIcmpParam.jsp";
	this.form1.submit();
}
function deleteform1(){
	
	var v = confirm('确定删除信息?');
	if( ! v ){return false;	}
	
	
	form1.formAction.value= 'delete';
	form1.action = "<%=request.getContextPath() %>/secure/baseinfo/deleteIcmpParam.wss?eveidcannotbedeleted=${model.eveides }";
	this.form1.submit();
	}
	  
	
	

	


function updateform1(){
	form1.formAction.value= 'update';
	

	form1.action = "<%=request.getContextPath() %>/secure/baseinfo/updateIcmpParam.jsp?eveid=${eveid}&modid=${modid}&ecode=7&major=${major}&description=${description}";
	this.form1.submit();
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
</script>
</head>
<body>


  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">基础信息
          </TD>
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
        
        <h1 id="title-bread-crumb">ICMP性能指标列表</h1>
        <p ></p>

<a name="main"></a>

<form action="<%=request.getContextPath() %>/secure/baseinfo/deviceSnmpParam.wss" method="post" id="form1" name="form1">        
<input type="hidden" name="eveidfromcon" id="eveidfromcon" value="${model.eveides }">
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

       <tr><td colspan="2">
								<c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg">${model.message }</div>
								</c:if>
           </td></tr>
        <tr valign="top">
          
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
        <c:if test="${model.check == 'remainnew' }">
		<td>
    		<input type="submit" name="button.new" value="新建"  class="buttons" onclick="add();" id="functions"/>
    	</td>
    	</c:if>
		
        <input type="submit" name="hiddenButton1290018101908118000" value="hiddenButton" style="display:none" class="buttons" id="hiddenButton1290018101908118000"/>
    </tr></table>    
 
    <input type="hidden" name="definitionName" value="JDBCProvider.collection.buttons.panel"/>
    <input type="hidden" name="buttoncontextType" value="JDBCProvider"/>
    <input type="hidden" name="buttoncontextId" value="All Scopes"/>
    <input type="hidden" name="buttonperspective" value="null"/> 
     <input type="hidden" name="formAction" value="jDBCProviderCollection.do"/>  

        </td>
        </tr>
        </table>
 
        
        
<!-- Data Table -->

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table">

	<TBODY>
		<TR>
			<TD CLASS="layout-manager" id="notabs">


<table BORDER="0" CELLPADDING="3" CELLSPACING="1" WIDTH="100%" SUMMARY="List table" CLASS="framing-table">
<tr>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >编辑/ 删除</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >编号</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >模式类别</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >名称</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >描述</th>
</tr>

<c:forEach items="${model.eventtype}" var="c1" >
<tr class="table-row"  ondbclick="<%=request.getContextPath() %>/secure/baseinfo/updateIcmpParam.jsp?eveid=${c1.eveid}&modid=${c1.modid}&ecode=7&major=${c1.major}&description=${c1.description}" >

<td VALIGN="BOTTOM"  class="collection-table-text" headers="eveid">
<a href="<%=request.getContextPath() %>/secure/baseinfo/updateIcmpParam.jsp?eveid=${c1.eveid}&modid=${c1.modid}&ecode=9&major=${c1.major}&description=${c1.description}">
<img border="0" src="../../images/wtable_edit_sorts.gif" width="16" height="16" alt="编辑"></a>
&nbsp;
<a href="<%=request.getContextPath() %>/secure/baseinfo/deleteIcmpParam.wss?eveid=${c1.eveid}&modid=${c1.modid}&eveidcannotbedeleted=${model.eveides }" onclick="return confirm('确定删除信息?');">
			<img border="0" src="../../images/delete.gif" width="16" height="16" alt="删除"></a>
</td>
<td VALIGN="BOTTOM"  class="collection-table-text" headers="eveid"><c:out value="${c1.eveid}" /> </td>
<td VALIGN="BOTTOM"  class="collection-table-text" headers="catename"><c:out value="${c1.mname}" /> </td>
<td VALIGN="BOTTOM"  class="collection-table-text" headers="dtid"><c:out value="${c1.major}"/></td>
<td VALIGN="BOTTOM"  class="collection-table-text" headers="description"><c:out value="${c1.description}"/></td>
</tr>
</c:forEach>
</table>
<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
            Total ${fn:length(model.eventtype)}
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>


</TD></TR></TBODY></TABLE>
</form>
</TD></TR></TBODY></TABLE></TD></TR></TABLE>

</body>
</html>