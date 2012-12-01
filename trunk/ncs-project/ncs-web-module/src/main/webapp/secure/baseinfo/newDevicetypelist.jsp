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
//String.prototype.replace = function(searchValue,replaceValue){return "";};
function   Trim(m){   
  while((m.length>0)&&(m.charAt(0)==' '))   
  m   =   m.substring(1, m.length);   
  while((m.length>0)&&(m.charAt(m.length-1)==' '))   
  m = m.substring(0, m.length-1);   
  return m;   
  }

function addform1(){
	form1.formAction.value= 'add';
	var mf = document.getElementById("mf").value;
	var cate = document.getElementById("cate").value;
	var subcate = document.getElementById("subcate").value;
	if(mf != ''){
	  if(cate != ''){
	     if(subcate != ''){
	        form1.action = "<%=request.getContextPath() %>/secure/baseinfo/saveDevicetypelist.wss?mf=${model.mf}&cate=${model.cate}&subcate=${model.subcate}";
	     }else{
	        form1.action = "<%=request.getContextPath() %>/secure/baseinfo/saveDevicetypelist.wss?mf=${model.mf}&cate=${model.cate}";
	     }
	  }else{
	     form1.action = "<%=request.getContextPath() %>/secure/baseinfo/saveDevicetypelist.wss?mf=${model.mf}";
	  }
	
	}
	
	if(checkNull()){
		this.form1.submit();
	}

}
function reloadNavi(){
window.parent.frames[2].location.reload();
}

function checkNull(){
	//alert("HERE");
   // alert(document.getElementById("subCategory").value);
	if(document.getElementById("category").value=="-1"){
		alert("设备类型不能为空！");
		return false;
	}
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
<body  <c:if test="${model.refresh eq 'true'}">onLoad="reloadNavi()"</c:if> class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

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
        
        <h1 id="title-bread-crumb">新建厂商型号信息</h1>
        <p ></p>
<form name="form1" method="post" action="">
<a name="main"></a>
<input type="hidden" id="mf" value="${model.mf }">
<input type="hidden" id="cate" value="${model.cate }">
<input type="hidden" id="subcate" value="${model.subcate }">
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        
        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		<input type="button" name="button.new" value="保存" class="buttons" onClick="addform1()" id="functions"/>
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
     <%
     	long nowtime = System.currentTimeMillis();
    
     	request.getSession().setAttribute("sessiontime",Long.toString(nowtime));
     	
      %>
		<input type="hidden" name="sessiontime" value="<%=nowtime %>">
        </td>
        </tr>
        </table>
 <!-- ncc -->
        

		<input name="_name" value="T_DEVICE_TYPE_INIT" type="hidden">
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
					                <c:when test="${(theMf.mrname) == (model.mf) }" >					                
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
					<select name="category" id="category">
					
					<option selected="selected" value="-1">＝＝＝请选择设备类型＝＝＝</option>
					
					<c:forEach items="${model.catelist }" var="theCate">
					<c:if test="${model.cate != null || model.category != null}">
					<c:choose >
					                <c:when test="${(theCate.name) == (model.cate) }" >					                
					                	<option value="${theCate.id }" selected="selected">${theCate.name }</option>
					               </c:when>
					                <c:when test="${(theCate.name) == (model.category) }" >					                
					                	<option value="${theCate.id }" selected="selected">${theCate.name }</option>
					               </c:when>
					               <c:otherwise >
					               		<option value="${theCate.id }" >${theCate.name }</option>
					               </c:otherwise>
					 </c:choose></c:if>
					</c:forEach>
					</select>&nbsp;
					
					</td>

				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">子类型:</td>
					<td class="tdcontent">
									<c:choose >
					                <c:when test="${model.subcate != null && model.subcate != ''}" >					                
					                	&nbsp;<input type="text" name="subCategory" id="subCategory"
									size="20" value="${ model.subcate}" maxlength="64">
					               </c:when>
					               <c:otherwise >
					               		&nbsp;<input type="text" name="subCategory" id="subCategory"
									size="20" value="${ model.subCategory}" maxlength="64">
					               </c:otherwise>
					 </c:choose>
					
					</td>
				</tr>
               
				<tr class="table-row">
					<td class="tdlabel" width="30%">型号:</td>

					<td class="tdcontent">&nbsp;<input type="text" name="model" size="20" value="${model.model }" id="model" maxlength="64">
					</td>
				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">OBJECTID:</td>
					<td class="tdcontent">&nbsp;<input type="text" name="objectid" id="objectid" size="20" value="${model.objectid }" maxlength="255">
					
					</td>

				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">描述:</td>
					<td class="tdcontent">&nbsp;<input type="text" name="description"
									size="20" value="${model.description }" maxlength="400">
					
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