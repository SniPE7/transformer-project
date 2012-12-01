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
<title>syslog</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath() %>/js/ajax.js" type="text/javascript" language="JavaScript">
</script>
<script src="${pageContext.request.contextPath}/js/util.js"
	type="text/javascript"></script>
</head>
<script type="text/javascript">
function addsyslog(){
form1.action = "<%= request.getContextPath()%>/secure/syslog/newlinesnotcare.wss";
if(checkNull()){
		this.form1.submit();
	}
}
function updatesyslog(){
form1.action = "<%= request.getContextPath()%>/secure/syslog/updatelinesnotcare.wss";
form1.submit();
}
function   Trim(m){   
  while((m.length>0)&&(m.charAt(0)==' '))   
  m   =   m.substring(1, m.length);   
  while((m.length>0)&&(m.charAt(m.length-1)==' '))   
  m = m.substring(0, m.length-1);   
  return m;   
  }

function checkNull(){
	//alert("HERE");
   // alert(document.getElementById("subCategory").value);
	if(document.getElementById("linesnotcare").value==""){
		alert("LINESNOTCARE不能为空!");
		return false;
	}
	
	
	return true;
}
 
</script>





<body  class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


 <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">Lines Events Not Care信息
        </TD>
          <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">Lines Events Not Care信息</TH>

     
  </TR>
  

  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">添加Lines Events Not Care</h1>

        <form action="" method="post" id="newdev1" name="form1">        
       
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
       	<c:choose >
					                <c:when test="${model.method == 'update'}" >					                
					                	<td>
    		<input type="button" name="button.update" value="确认" class="buttons" onClick="updatesyslog()"  />
    	</td>
					               </c:when>
					               <c:otherwise >
					               		<td>
    		<input type="button" name="button.new" value="确认" class="buttons" onClick="addsyslog()"  />
    	</td>
					               </c:otherwise>
					 </c:choose>
		
    	

        
    </tr></table>    
	 <c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
	</c:if>
    
     <input type="hidden" name="formAction" value="0"/>  

        </td>
        </tr>
        </table>
 
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row"  nowrap>

						<table border="1">
							<tbody>
								<tr>
								 
									<td width="30%">LINESNOTCARE</td>
									<td>
					 <input type="text" id="linesnotcare" name="linesnotcare" size="20" value="${model.linesnotcare }"/>
								</td>
                                    <td>* 必填 *</td>
									<td><input type="hidden" name="mark" value="${model.mark }" /></td>
								</tr>
								
							</tbody>
						</table>



						</td>
        </tr>
        </table>

				


			





</form>

<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
           
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>



</TD></TR></TABLE>


</TD></TR></TABLE>
</body>
</html>