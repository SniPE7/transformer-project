<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<title>baseinfonavi</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<!-- list of menu -->




<script language="javascript">
function saveManu(){
    manu.action = "<%=request.getContextPath() %>/secure/baseinfo/newDeviceType.wss";
	if(checkNull()){
	this.manu.submit();
	}
}

function   Trim(m){   
  while((m.length>0)&&(m.charAt(0)==' '))   
  m   =   m.substring(1, m.length);   
  while((m.length>0)&&(m.charAt(m.length-1)==' '))   
  m = m.substring(0, m.length-1);   
  return m;   
  }
  
  function checkNull(){
  if(Trim(document.getElementById("name").value )== ""){
  alert("设备名称不能为空！");
  return false;
  }
  return true;
  }

	

// -->
</script>


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
        
        <h1 id="title-bread-crumb">新建设备类型</h1>


  
<%--<form:form method="post" commandName="eventtypeform" >  --%>
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

 
        <tr valign="top">
       
        <td class="table-button-section"  nowrap> 
        
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
	
		<td>
    		<input type="button" name="button.update" value="保存" class="buttons" onClick="saveManu()" id="functions"/>
    	</td>
    	
        
    </tr></table>    
    <c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
	</c:if>
   

        </td>
        </tr>
        </table>
  <form method="post" id="manu" name="manu"   > 
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row"  nowrap> 
         

		<div class="sectionHeading" value="详细内容">设备类型</div>
		<div class="sectionContent">
		<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="0" width="100%">
<tr><td class="table-row">
<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">
			

				<tbody>
		
		

				<tr class="table-row">
					<td class="tdlabel">设备类型名称</td>
					<td class="tdcontent">
						
						
							<input id="name" name="name" id="formStyle1" value="${model.name }" maxlength="255">
							<%--<form:input path="major" />--%>
						
					</td>
				</tr>
				
			


			
		
		</tbody></table>

		</td></tr></table></div></td></tr></table>
		</form>
<!-- Data Table -->


<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
           
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>
        </td>
        </tr>
        </table>   

        



 
</TD></TR></TABLE>



</body>
</html>