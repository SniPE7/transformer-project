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
<script type="text/javascript">
function addform1(){
	form1.formAction.value= 'add';
	
	form1.action = "<%=request.getContextPath() %>/secure/baseinfo/newDevicetypelist.wss?mf=${model.mf}&cate=${model.cate}&subcate=${model.subcate}";
	this.form1.submit();
}
function searchform1(){
    form1.action="<%=request.getContextPath() %>/secure/baseinfo/searchDevicetypelist.jsp";
    this.form1.submit();
}
function reloadNavi(){
window.parent.frames[2].location.reload();
}


</script>
</head>
<body <c:if test="${model.refresh eq 'true'}">onLoad="reloadNavi()"</c:if> class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

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
        
        <h1 id="title-bread-crumb">厂商型号列表</h1>
        <p ></p>
<form action="" method="post" name="form1">
<a name="main"></a>

<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
        
        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		<input type="button" name="button.new" value="新建" class="buttons" onClick="addform1();" id="functions"/>
    	</td>
    	<td>
    		<input type="button" name="button.new" value="检索" class="buttons" onClick="searchform1();" id="functions"/>
    	</td>
        <input type="submit" name="hiddenButton1290018101908118000" value="hiddenButton" style="display:none" class="buttons" id="hiddenButton1290018101908118000"/>
    </tr></table>    
    <c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
    </c:if>
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

<!--tr>
<td><input type="text" value="${model.check }"></td></tr-->
<table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" SUMMARY="List table" CLASS="example table-autosort:1 framing-table">


<thead>
<tr>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" width="80">编辑/ 删除</th>
<th NOWRAP VALIGN="BOTTOM" class="table-sortable:default column-head-name" width="12%" SCOPE="col" >生产厂商名&nbsp;</th>
<th NOWRAP VALIGN="BOTTOM" class="table-sortable:default column-head-name" width="12%" SCOPE="col" >类型名&nbsp;</th>
<th NOWRAP VALIGN="BOTTOM" class="table-sortable:default column-head-name" width="12%" SCOPE="col" >子类型名&nbsp;</th>
<th NOWRAP VALIGN="BOTTOM" class="table-sortable:default column-head-name" width="12%" SCOPE="col" >型号名&nbsp;</th>
<th NOWRAP VALIGN="BOTTOM" class="table-sortable:default column-head-name" width="12%" SCOPE="col" >ObjectID名&nbsp;</th>
<th NOWRAP VALIGN="BOTTOM" class="table-sortable:default column-head-name" SCOPE="col" >描述</th>

</tr>
</thead>

<c:forEach items="${model.devicelist}" var="c1" >
<c:if test="${c1.dtid != 0 }">
<tr class="table-row">

<!-- td VALIGN="top"  class="collection-table-text" headers="mrname"><c:url value="/secure/baseinfo/updateToDevicetypelist.wss" var="myurl"><c:param name="dtid" value="${c1.dtid}"/></c:url><a href="${myurl }" >Edit</a></td -->

<td>
<a href="<%=request.getContextPath() %>/secure/baseinfo/updateToDevicetypelist.wss?dtid=${c1.dtid}&mf=${model.mf }&cate=${model.cate }&subcate=${model.subcate }"><img border="0" src="../../images/wtable_edit_sorts.gif" width="16" height="16" alt="编辑"></a>
&nbsp;
<c:choose>
<c:when test="${model.mf != null && model.cate != null && model.subcate != null }">
<a href="<%=request.getContextPath() %>/secure/baseinfo/deleteDevicetypelist.wss?dtid=${c1.dtid }&mf=${model.mf }&cate=${model.cate }&subcate=${model.subcate }" onclick="return confirm('确定删除信息?');">
			<img border="0" src="../../images/delete.gif" width="16" height="16" alt="删除"></a>
</c:when>
<c:when test="${model.mf != null && model.cate != null }">
<a href="<%=request.getContextPath() %>/secure/baseinfo/deleteDevicetypelist.wss?dtid=${c1.dtid }&mf=${model.mf }&cate=${model.cate }" onclick="return confirm('确定删除信息?');">
			<img border="0" src="../../images/delete.gif" width="16" height="16" alt="删除"></a>
</c:when>
<c:otherwise>
<a href="<%=request.getContextPath() %>/secure/baseinfo/deleteDevicetypelist.wss?dtid=${c1.dtid }&mf=${model.mf }" onclick="return confirm('确定删除信息?');">
			<img border="0" src="../../images/delete.gif" width="16" height="16" alt="删除"></a>
</c:otherwise>

</c:choose>	
</td>

<td VALIGN="top"  class="collection-table-text" headers="manufacture"><c:out value="${c1.mrName}" /></td>
<td VALIGN="top"  class="collection-table-text" headers="catename"><c:out value="${c1.cateName}" /></td>
<td VALIGN="top"  class="collection-table-text" headers="subcategory"><c:out value="${c1.subCategory}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="model"><c:out value="${c1.model}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="objectid"><c:out value="${c1.objectid}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="description"><c:out value="${c1.description}"/></td>

</tr>
</c:if>
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


</TD></TR></TBODY></TABLE>
</form>
</TD></TR></TBODY></TABLE></TD></TR></TABLE>

</body>
</html>