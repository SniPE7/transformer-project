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
function updateform1(){
	form1.formAction.value= 'add';
	form1.action = "<%=request.getContextPath() %>/secure/baseinfo/updatefuncspecpare.wss";
	this.form1.submit();
}

function generateValue(){
	alert("111*****" + document.getElementsByName("oidgrpname").value);
	alert("222*****" + document.form1.oidgroupname.value);
	document.getElementsByName("oidgrpname").value = document.form1.oidgroupname.value;
	
}
  function   Trim(m){   
  while((m.length>0)&&(m.charAt(0)==' '))   
  m   =   m.substring(1, m.length);   
  while((m.length>0)&&(m.charAt(m.length-1)==' '))   
  m = m.substring(0, m.length-1);   
  return m;   
  }
  
  function checkNull(){
  if(Trim(document.getElementById("oidgroupname").value )== ""){
  alert("OID GROUP NAME不能为空！");
  return false;
  }
  return true;
  }
  function addform(){
  
  form1.action = "<%=request.getContextPath() %>/secure/baseinfo/updatefuncspecpare.wss";
  this.form1.submit();
  
  
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
      <TH class="wpsPortletTitle" width="100%">基础信息维护 </TH>

  </TR>
  

  
  <TBODY ID="wasUniPortlet">
    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <p ></p>
<form name="form1" method="post" action="" >
<a name="main"></a>
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		<input type="button" name="button.new" value="保存" class="buttons" onClick="addform();" id="functions" />
    	</td>
        <input type="submit" name="hiddenButton1290018101908118000" value="hiddenButton" style="display:none" class="buttons" id="hiddenButton1290018101908118000"/>
    </tr></table>    
    <c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg">${model.message }</div>
	</c:if>
    <input type="hidden" name="definitionName" value="JDBCProvider.collection.buttons.panel"/>
    <input type="hidden" name="buttoncontextType" value="JDBCProvider"/>
    <input type="hidden" name="buttoncontextId" value="All Scopes"/>
    <input type="hidden" name="buttonperspective" value="null"/> 
     <input type="hidden" name="formAction" value="prepare"/>  
	<input type="hidden" name="dtid" value="<%= request.getParameter("dtid") %>"/>  
    <input type="hidden" name="modid" value="<%= request.getParameter("modid") %>"/>
    <input type="hidden" name="oid" value="<%= request.getParameter("oid") %>"/>
    <input type="hidden" name="mrid" value="<%= request.getParameter("mrid") %>"/>
    <input type="hidden" name="oidgrpname" value="<%= request.getParameter("oidgroupname") %>"/>
        </td>
        </tr>
        </table>
 <!-- ncc -->
        

		<input name="eveid" value="<%= request.getParameter("eveid") %>" type="hidden">
		<input name="major" value="${model.major }" type="hidden">
		
		<table class="button-section" border="0" bordercolor="#999999" cellpadding="3" cellspacing="0" width="100%">   
				<tbody><tr><td class=table-row">
				<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">参数名称:</td>

					<td class="tdcontent">
					&nbsp;<c:out value="${model.major }"></c:out>
					</td>
				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">OID GROUP 名称:</td>
					<td class="tdcontent">
                    <select name="oidgroupname" id="oidgroupname"> 
                    <option value="">===请选择===</option>
					<c:forEach items="${model.toi }" var="theMf">
					<option value="${theMf.oidgroupname}" <c:if test="${ theMf.oidgroupname==model.oidgrpname}"> selected </c:if> >${theMf.oidgroupname}</option>
					</c:forEach>                    
					</select>	
					</td>

				</tr>
               
			
                
				
				<tr class="table-row">
					<td class="tdlabel" width="30%">单位:</td>

					<td class="tdcontent">&nbsp;
                    <c:out value="${model.param.unit }"></c:out>
                    <input type="text" name="unit" size="20" value="<%= request.getParameter("unit") %>" maxlength="32">&nbsp;
					</td>
				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">描述:</td>
					<td class="tdcontent">&nbsp;&nbsp;<input type="text" name="description" size="20" value="<%= request.getParameter("description") %>" maxlength="400">&nbsp;
					
					</td>

				</tr>
              </table></td></tr>
          
               
		</tbody></table>
		
		
<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
           
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>


</form>	
</TD></TR></TBODY></TABLE></TD></TR></TABLE>

</body>
</html>