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
function addform1(){
	form1.formAction.value= 'add';
	form1.action = "<%=request.getContextPath() %>/secure/baseinfo/updateDevicetypelist.wss";
	if(checkNull()){
	this.form1.submit();
	}
}


function reloadNavi(){


window.parent.frames[2].location.reload();

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
	
	if(Trim(document.getElementById("subCategory").value)=="")
	{	alert("子类型不能为空！");
		return false;
	}
	if(Trim(document.getElementById("model").value)=="")
	{	alert("设备型号不能为空！");
		return false;
	}
	if(Trim(document.getElementById("objectid").value)=="")
	{	alert("objectid 不能为空!");
		return false;
	}
	return true;
}
  
</script>
</head>
<body <c:if test="${model.refresh eq 'true'}">onLoad="reloadNavi()"</c:if> class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">基础信息</TD>
           <TD CLASS="pageClose">
           <a href="<%= request.getContextPath() %>/secure/baseinfo/devicetypelist.wss?mf=${model.mf }&cate=${model.cate }&subcate=${model.subcate }"><img border="0" src="../../images/back.gif" width="16" height="16"></a></TD>
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
        
        <h1 id="title-bread-crumb">修改厂商型号信息</h1>
        <p ></p>
<form name="form1" method="post" action="<%=request.getContextPath() %>/secure/baseinfo/updateDevicetypelist.wss" >
<a name="main"></a>
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

      
        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="3" border="0"><tr> 
		<td>
    		<input type="submit" name="button.new" value="保存" class="buttons" id="functions"/>
    	</td>
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
        

		<input name="_name" value="T_DEVICE_TYPE_INIT" type="hidden">
		<input type="hidden" name="dtid" value="${model.dtid }">
		
		<input name="_pk" value="429" type="hidden">

		<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">


			
                
				<tbody><tr><td  class="table-row"  nowrap>
               <table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">生产厂商:</td>

					<td class="tdcontent">
					<select name="manufacture">
					<c:forEach items="${model.mflist }" var="theMf">   
                    <c:choose >
					                <c:when test="${(theMf.mrid) == (model.mrid) }" >					                
					                	<option value="${theMf.mrid }" selected="selected">${theMf.mrname }</option>
					               </c:when>
					               <c:otherwise >
					               		<option value="${theMf.mrid }" >${theMf.mrname }</option>
					               </c:otherwise>
					 </c:choose>
					 
					</c:forEach>
                    
					</select>
                   
					
					</td>
				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">类型:</td>
					<td class="tdcontent">
					<select name="category">
					<c:forEach items="${model.catelist }" var="theCate">
                       <c:choose >
					                <c:when test="${(theCate.id) == (model.category) }" >					                
					                	<option value="${theCate.id }" selected="selected">${theCate.name }</option>
					               </c:when>
					               <c:otherwise >
					               		<option value="${theCate.id }" >${theCate.name }</option>
					               </c:otherwise>
					              
					 </c:choose>
					 
					</c:forEach>
					</select>&nbsp;
					
					</td>

				</tr>
               
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">子类型:</td>
					<td class="tdcontent">&nbsp;<input type="text" name="subCategory"
									size="20" value="${ model.subCategory}" maxlength="64">&nbsp;
					</td>
				</tr>
               
				<tr class="table-row">
					<td class="tdlabel" width="30%">型号:</td>

					<td class="tdcontent">&nbsp;<input type="text" name="model" size="20" value="${model.model }" maxlength="64">&nbsp;
					</td>
				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">OBJECTID:</td>
					<td class="tdcontent">&nbsp;<input type="text" name="objectid" size="20" value="${model.objectid }" maxlength="255">&nbsp;
					
					</td>

				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">描述:</td>
					<td class="tdcontent">&nbsp;<input type="text" name="description"
									size="20" value="${model.description }">&nbsp;
					</td>
				</tr>
               </table></td></tr>
		</tbody></table>
		
		


<!-- Data Table -->

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