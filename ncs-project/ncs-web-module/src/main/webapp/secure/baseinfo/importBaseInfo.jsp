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
<title>index</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR"
	content="Rational® Application Developer™ for WebSphere® Software">
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<script type="text/javascript">

function confirmImport(){
    var v = confirm('确定导入基础信息?');
	if( ! v ){ return false;	}
	if(v){
		form1.action = "<%=request.getContextPath() %>/secure/baseinfo/importBaseInfo.wss";
		this.form1.submit();
		document.getElementById("errmsg").innerHTML = "<img border='0' src='../../images/icon_progress.gif' width='16' height='16' > 开始导入，请稍候....";   
	}
}
</script>
</head>
<body  class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


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
   

  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">基础信息导入</h1>


<form action="" method="post" enctype="multipart/form-data" id="form1" name="form1">        

<!-- button section -->
<br/><table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
	
		<td>
    		<input type="button" name="button.import" value="导入" onClick="confirmImport();" class="buttons" id="functions" />
    	</td>
        
    </tr></table>    
 
        </td>
        </tr>
        </table>

<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row" nowrap> 
        <div id="errmsg" style='margin-left: 2em;'>
           <c:if test="${model.message != null &&  model.message != ''}">
	             <fmt:message>${model.message }</fmt:message>
           </c:if>
           <c:if test="${model.errorLinkStr != null && model.errorLinkStr != ''}">${model.errorLinkStr}</c:if>
    </div>
    <table style="display: inline; font-size: 95%;" cellspacing="3" cellpadding="5" border="0">
    <tr> 
		<td colspan="2">
    		&nbsp;&nbsp;请输入所要导入的EXCEL文件: 
    	</td>
    </tr><tr>
    	<td colspan="2">
 
    		<input type="file" name="type" size="50%" class="buttons" id="functions" >

    	</td>
    	</tr><tr>

    	</tr><tr>
    	
    	<td colspan="2">
<!-- Data Table -->

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table">

	<TBODY>
		<TR>
			<TD CLASS="layout-manager" id="notabs">






</TD></TR></TBODY></TABLE>
    	</td> 
    	
    </tr>
    </table>  


        </td>
        </tr>
        </table>        
        
<!-- Data Table -->


<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
           
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>

</form>
 
</TD></TR></TABLE>


</TD></TR></TABLE>
</body>
</html>