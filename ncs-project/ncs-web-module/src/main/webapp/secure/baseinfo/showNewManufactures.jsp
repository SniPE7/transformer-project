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

<body  class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


 <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">厂商信息</TD>
          <TD CLASS="pageClose">&nbsp;</TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">厂商信息</TH>

  </TR>
   <TR>
          
          <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">厂商信息</h1>


<form action="<%=request.getContextPath() %>/secure/baseinfo/newManufactures.wss" method="post" id="manu" name="manu"   >     
<%--<form:form method="post" commandName="eventtypeform" >  --%>
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
    </tr></table>    
 
   

        </td>
        </tr>
        </table>
 
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row""  nowrap> 
    
		<div class="sectionHeading" value="详细内容">厂商详细内容</div>
		<div class="sectionContent">
		<table class="loginTable" border="0" bordercolor="#999999" cellpadding="0" cellspacing="1" width="100%">


			

				<tbody>


				<tr class="data-row">
					<td class="tdlabel">厂商名</td>
					<td class="tdcontent">
						
						
							<input name="name" id="formStyle1" value="${model.name}" backgroundcolor='blue' readonly="true">
							<%--<form:input path="major" />--%>
						
					</td>
				</tr>
				
				<tr class="data-row">
					<td class="tdlabel">ObjectID</td>
					<td class="tdcontent">
						
						
							<input name="objID" id="formStyle1" value="${model.objID}" readonly="true">
							<%--<form:input path="major" />--%>
						
					</td>
				</tr>

			

				<tr class="data-row">
					<td class="tdlabel">描述</td>

					<td class="tdcontent" align='left'>
						
						
						
					<%--	<form:textarea path="description" />--%>
						
							<textarea name="description" rows="5" cols="50" id="formStyle2" readonly="true">${model.description }</textarea>
						
					</td>
				</tr>

			
			<tr>
				<td colspan="2" class="tdbutton" >
				</td>
			</tr>
		</tbody></table>

		</div>
		


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