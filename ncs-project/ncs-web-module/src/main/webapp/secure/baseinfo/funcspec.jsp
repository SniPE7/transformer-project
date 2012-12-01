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
<title>performance params</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/include/table.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/include/table.css" media="all">
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
        
        <h1 id="title-bread-crumb">性能参数</h1>
        <p ></p>

<a name="main"></a>
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 

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


<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS="example table-autosort:1 framing-table">
<thead>
<tr>
<th NOWRAP VALIGN="BOTTOM" class="table-sortable:default column-head-name" SCOPE="col" width="5%" >查询</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="15%" >厂商名&nbsp;<input name="filter" size="15" onkeyup="Table.filter(this,this)"></th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="10%">类型</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="10%">子类型</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="10%">型号</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" width="15%">ObjectID</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="table-sortable:default column-head-name" SCOPE="col" >描述</th>
</tr>
</thead>

<c:forEach items="${model.devicelist}" var="c1" >
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="mrname"><input type="hidden" name="mf" value="${model.mf }">
<a href="<%=request.getContextPath() %>/secure/baseinfo/showfuncspec.wss?dtid=${c1.dtid}">
<img border="0"   src="../../images/taskbar_scroll_right.gif"   width="16" height="16"></a>
</td>
<td VALIGN="top"  class="collection-table-text" headers="mrname"><c:out value="${c1.mrName}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="catename"><c:out value="${c1.cateName}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="subcategory"><c:out value="${c1.subCategory}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="model"><c:out value="${c1.model}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="objectid"><c:out value="${c1.objectid}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="description"><c:out value="${c1.description}"/></td>
</tr>
</c:forEach>
</table>
<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
            Total ${fn:length(model.devicelist)}
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>


</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TABLE>

</body>
</html>