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

function restore(){
     var v = confirm('该操作将删除当前数据，并将其覆盖为指定版本号的数据，是否继续？?');
	 if( ! v ){ return false;	}
	 if(v){
	     form1.action = "<%=request.getContextPath() %>/secure/excel/restoresyslog.wss";
	     this.form1.submit();
	}
}

function clearversion(){
  var div = document.getElementById('versionlist');
  div.style.display="block"; 
  form2.deletemulti.disabled = true;
  var vercount = document.getElementById("verCount").value;
   
   if(vercount > 3){
      form2.deletemulti.disabled = false;
   }
}

function deleteversion(){
 var v = confirm('确定要删除该版本吗?');
	 if( ! v ){ return false;	}
	 if(v){
	     form2.action = "<%=request.getContextPath() %>/secure/excel/deletesyslogversion.wss";
	     this.form2.submit();
	}
}

function deleteversionmul(){
    var v = confirm('确定要清理版本吗？');
    if(! v ){return false; }
    if(v){
        form2.action = "<%=request.getContextPath() %>/secure/excel/deletesyslogversionmul.wss";
        this.form2.submit();
    }
}


</script>
</head>
<body   class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

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
        
        <h1 id="title-bread-crumb">恢复以前SYSLOG</h1>
        <p ></p>
<form name="form1" method="post" action="">
<a name="main"></a>

<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        
        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		<input type="button" name="button.new" value="恢复" class="buttons" onClick="restore()" id="functions"/>
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
		<input name="_pk" value="429" type="hidden">

		<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">


			
                
				<tbody><tr><td  class="table-row"  nowrap>
			    <fieldset style="width: 680">
            	<legend>&nbsp;&nbsp;请选择要恢复的SYSLOG版本： </legend>
				<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">
                 
				<tr class="table-row">
					<td class="tdlabel" width="30%">版本:<input name="versionall" type="hidden" value="${fn:length(model.dispver) }" id="verCount"></td>
<%--
					<td class="tdcontent">
					<select name="version">
					<c:forEach items="${model.bksnmp }" var="bksnmp">
					<option value="${bksnmp.bkTime}"><fmt:formatDate value="${bksnmp.bkTime}" type="both"/></option>
					</c:forEach>
					</select>
					</td>--%>
					
					<td class="tdcontent">
					<select name="version">
					<c:forEach items="${model.dispver }" var="dspver">
					<option value="${dspver.ver}"><fmt:formatDate value="${dspver.ver}" type="both"/>&nbsp;${dspver.syslog}&nbsp;${dspver.syslogns}</option>
					</c:forEach>
					</select>
					</td>					
				</tr>
             
               </table>
                </fieldset></td></tr>
		</tbody></table>
		</form>
		<form name="form2" method="post" action="">
		<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        
        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		           <input type="button" name="button.new" value="整理版本" class="buttons" onClick="clearversion()" id="functions"/>
    	            </td>
    </tr></table>    
 <c:if test="${model.Message != null &&  model.Message != ''}">
									<div id="errmsg"><fmt:message>${model.Message }</fmt:message></div>
								</c:if>
   
   
        </td>
        </tr>
        </table>
		
		<div id="versionlist" class="table-row" style="display: none">
		 <table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">


			
                
				<tbody><tr><td  class="table-row"  nowrap>
		  <fieldset style="width: 680">
            	<legend>&nbsp;&nbsp;要整理的SYSLOG版本 </legend>
				<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">
 
				<tr class="table-row">
					<td class="tdlabel" width="30%">版本:</td>
<%--
					<td class="tdcontent">
					<select name="versionsel">
					<c:forEach items="${model.bksnmp }" var="bksnmp">
					<option value="${bksnmp.bkTime}"><fmt:formatDate value="${bksnmp.bkTime}" type="both"/></option>
					</c:forEach>
					</select>
					</td>
					--%>
					
					<td class="tdcontent">
					<select name="versionsel">
					<c:forEach items="${model.dispver }" var="dspver">
					<option value="${dspver.ver}"><fmt:formatDate value="${dspver.ver}" type="both"/>&nbsp;${dspver.syslog}&nbsp;${dspver.syslogns}</option>
					</c:forEach>
					</select>
					</td>	
					
					<td>
    		           <input type="button" name="button.new" value="删除" class="buttons" onClick="deleteversion()" id="functions"/>
    	            </td>
    	            <td>
    		           <input type="button" name="deletemulti" value="只保留最近3个版本" class="buttons" onClick="deleteversionmul()" id="functions" />
    	            </td>
				</tr>
             
               </table>
                </fieldset></td></tr></tbody></table>
		</div>

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