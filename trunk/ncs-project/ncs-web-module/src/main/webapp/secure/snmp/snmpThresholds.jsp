<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>category test</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<script type="text/javascript">
function add(){
	form1.formAction.value= 'add';

	form1.action = "<%=request.getContextPath() %>/secure/snmp/snmpthreshold.jsp";
	this.form1.submit();
}
function deleteform1(){
	//alert("in delete script");
	var v = confirm('确定删除信息?');
	if( ! v ){ return false;	}
	form1.formAction.value= 'delete';

	form1.action = "<%=request.getContextPath() %>/secure/snmp/deletesnmpthreshold.wss";
	this.form1.submit();
}

</script>
</head>
<body>


  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">SNMP Thresholds列表</TD>
          <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">SNMP Thresholds列表</TH>

     
  </TR>
 
  
  <TBODY ID="wasUniPortlet">
    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">SNMP Thresholds</h1>
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
    		<input type="button" name="button.new" value="新建"  onclick="add();"/>
    	</td>
		<td>
    		<input type="button" name="button.delete" value="删除"  onclick="deleteform1();"/>
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
																					

<table BORDER="0" CELLPADDING="3" CELLSPACING="1" WIDTH="100%" SUMMARY="List table" CLASS="framing-table">
<tr>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">选择</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">编辑</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">PERFORMANCE</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">BTIME</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">ETIME</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">THRESHOLD</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">COMPARETYPE</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">SEVERITY1</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">SEVERITY2</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">FILTERFLAG1</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" width="30" style="word-break:break-all">FILTERFLAG2</th>

</tr>

<c:forEach items="${model.tabList}" var="c1" >
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="mark"><input	type="checkbox" name="del" value="${c1.performance}"></td>
<td VALIGN="top"  class="collection-table-text" headers="eveid">
<a href="<%=request.getContextPath() %>/secure/snmp/updateToSnmpThresholdProcess.wss?performance=${c1.performance}">
<img border="0" src="../../images/wtable_edit_sorts.gif" width="16" height="16"></a></td>
<td VALIGN="top"  class="collection-table-text" width="30" style="word-break:break-all"><c:out value="${c1.performance }"/></td>
<td VALIGN="top"  class="collection-table-text" headers="" width="30" style="word-break:break-all"><c:out value="${c1.btime}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="" width="30" style="word-break:break-all"><c:out value="${c1.etime}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="" width="30" style="word-break:break-all"><c:out value="${c1.threshold}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="" width="30" style="word-break:break-all"><c:out value="${c1.compareType}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="" width="30" style="word-break:break-all"><c:out value="${c1.severity1}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="" width="30" style="word-break:break-all"><c:out value="${c1.severity2}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="" width="30" style="word-break:break-all"><c:out value="${c1.filterFlag1}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="" width="30" style="word-break:break-all"><c:out value="${c1.filterFlag2}"/></td>
</tr>
</c:forEach>
</table>

<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
            Total ${fn:length(model.tabList)}
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