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
function addsnmpevent(){
form1.action = "<%= request.getContextPath()%>/secure/snmp/newsnmpevent.wss";
if(checkNull()){
		this.form1.submit();
	}
}
function updatesnmpevent(){
form1.action = "<%= request.getContextPath()%>/secure/snmp/updateSnmpEventProcess.wss";
if(checkNull()){
		this.form1.submit();
	}
}

function checkNull(){
	if(document.getElementById("mark").value==""){
		alert("MARK不能为空!");
		return false;
	}
	
	if(Trim(document.getElementById("manufacture").value)=="")
	{	alert("MANUFACTURE 不能为空!");
		return false;
	}
	
	if(Trim(document.getElementById("resultlist").value)=="")
	{	alert("RESULTLIST 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("warnmessage").value)=="")
	{	alert("WARNMESSAGE 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("summary").value)=="")
	{	alert("SUMMARY 不能为空!");
		return false;
	}
	
	return true;
}


function   Trim(m){   
  while((m.length>0)&&(m.charAt(0)==' '))   
  m   =   m.substring(1, m.length);   
  while((m.length>0)&&(m.charAt(m.length-1)==' '))   
  m = m.substring(0, m.length-1);   
  return m;   
  }
 
</script>





<body  class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


 <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle"> 基础信息
          </TD>
           <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%"> 基础信息维护</TH>

    
  </TR>
  

  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
       
        <h1 id="title-bread-crumb">
         <c:choose>
           <c:when test="${model.method == 'update' }">更新SNMP Events</c:when>
           <c:otherwise> 新建SNMP Events</c:otherwise>
         </c:choose>
        </h1>
        

        <form action="" method="post" id="newdev1" name="form1">        
       
<!-- button section -->
<br>
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
       	<c:choose >
					                <c:when test="${model.method == 'update'}" >					                
					                	<td>
    		<input type="button" name="button.update" value="保存" class="buttons" onClick="updatesnmpevent()" id="functions"  />
    	</td>
					               </c:when>
					               <c:otherwise >
					               		<td>
    		<input type="button" name="button.new" value="保存" class="buttons" onClick="addsnmpevent()" id="functions" />
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

						<table cellspacing="1" cellpadding="0" border="0" width="100%" class="framing-table">
							<tbody>
								<tr class="table-row">
								 
									<td class="tdlabel" width="30%">MARK</td>
										<c:choose >
					                <c:when test="${model.method == 'update'}" >					                
					                	<td class="tdcontent">
    		<input type="text" name="mark" size="20" value="${model.mark }" readonly="true" maxlength="255"/>
    	</td>
					               </c:when>
					               <c:otherwise >
					               		<td>
    	<input type="text" name="mark" size="20" value="${model.mark }" maxlength="255"/>
    	</td>
					               </c:otherwise>
					 </c:choose>
									
                                    <td>* 必填 *</td>
									<td><input type="hidden" name="mark" value="${model.mark }" /></td>
								</tr>
								<tr class="table-row">
									<td class="tdlabel" width="30%">MANUFACTURE</td>
									<td class="tdcontent">
									<c:if test="${model.method == 'update' }">
                                        <select name="manufacture" style="width:150px">
                                        <option value="">===请选择===</option>
                                        <c:forEach items="${model.manus }" var="m1">
                                                 <option value="${m1.mrname }" <c:if test="${m1.mrname eq model.manufacture}"> selected </c:if>>${m1.mrname }</option>
                                        </c:forEach>
                                        </select>
                                        </c:if>
                                        <c:if test="${model.method == null || model.method == '' }">
                                           <select name="manufacture" style="width:150px">
                                              <c:forEach items="${model.manus }" var="m1">
                                                <option value="${m1.mrname }">${m1.mrname }</option>
                                              </c:forEach>
                                           </select>
                                        </c:if>
                                   </td>
									<td>* 必填 *</td>
								</tr>
								<tr class="table-row">
									<td class="tdlabel" width="30%">RESULTLIST</td>
									<td class="tdcontent"><input type="text" id="resultlist" name="resultlist"  value="${model.resultlist }" size="20" maxlength="255"></td>
									<td>* 必填 *</td>
								</tr>
								<tr class="table-row">
									<td class="tdlabel" width="30%">WARNMESSAGE</td>
									<td class="tdcontent"><input type="text" id="warnmessage" name="warnmessage" value="${model.warnmessage }" size="20" maxlength="255"></td>
									<td>* 必填 *</td>
								</tr>
								<tr class="table-row">
									<td class="tdlabel" width="30%">SUMMARY</td>
									<td class="tdcontent"><input type="text" id="summary" name="summary" size="20" value='${model.summary }'  maxlength="255">
									</td><td>* 必填 *</td>
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