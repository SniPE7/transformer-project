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
<script type="text/javascript" src="<%=request.getContextPath() %>/include/table.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/include/table.css" media="all">
<script type="text/javascript">
function checkNull(){ 
	if(document.getElementById("formStyle1").value==""){
		alert("厂商名不能为空!");
		return false;
	}
	return true;
}
function updateform1(){
	if(checkNull()){
		form1.formAction.value= 'add';
		form1.action = "<%=request.getContextPath() %>/secure/baseinfo/updateManufactureslist.wss?mrid=${model.mrid }";
		this.form1.submit();
	}
}
function deleteform1(){
  var check = document.getElementById("check").value;
    var v = confirm('确定删除信息?');
	if( ! v ){ return false;	}
   if(v){
   if(check == "yes"){
            alert("该厂商含有设备, 目前无法删除");
            
   }else{
   form1.action="<%=request.getContextPath() %>/secure/baseinfo/deleteManufactureslist.wss?mrid=${model.mrid }";
   form1.submit();
   }
   }
}

</script>
</head>
<body>


  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">基础信息
          </TD>
           <TD CLASS="pageClose"><a href="<%=request.getContextPath() %>/secure/baseinfo/manufecturers.wss"><img border="0" src="../../images/back.gif" width="16" height="16"></a></TD>
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
        
        <h1 id="title-bread-crumb">更新厂商信息</h1>
        <p ></p>
<form name="form1">
<a name="main"></a>
<input type="hidden" name="check" value=${model.messageFlag }>
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		<input type="submit" name="button.update" value="保存" class="buttons" id="functions" onClick="updateform1()"/>
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
     <input type="hidden" name="formAction" value="prepare"/>  

        </td>
        </tr>
        </table>
 <!-- ncc -->
        
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
 
        <tr valign="top">
        <td class="table-row"  nowrap>     
		<input type="hidden" name="mrid" value="${model.mrid }">
		<input name="_pk" value="429" type="hidden">
		<table class="framing-table" border="0" bordercolor="#999999" cellpadding="0" cellspacing="1" width="100%">
				<tbody><tr><td  class="table-row"  nowrap>
				<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">
			 
				<tr class="table-row">
					<td class="tdlabel" width="30%">厂商名:</td>
					<td class="tdcontent"><input type="text" id="formStyle1" name="mrname" size="20" value="${model.mrname }" maxlength="64">&nbsp;
					
					</td>

				</tr>
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">OBJECTID:</td>
					<td class="tdcontent"><input type="text" name="objectid" size="20" value="${model.objectid }" maxlength="255">&nbsp;
					
					</td>

				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">描述:</td>
					<td class="tdcontent"><input type="text" name="description"
									size="20" value="${model.description }" maxlength="400">&nbsp;
					
					</td>
				</tr>
               </table></td></tr>
		</tbody></table>

		


        </td>
        </tr>
        </table>        

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table">

	<TBODY>
		<TR>
			<TD CLASS="layout-manager" id="notabs">

<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
 
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>


</TD></TR></TBODY></TABLE>
</form>	
</TD></TR></TBODY></TABLE></TD></TR></TABLE>

</body>
</html>