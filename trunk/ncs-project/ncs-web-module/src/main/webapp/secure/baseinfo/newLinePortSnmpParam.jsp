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
<script language="JavaScript" type="text/javascript">
function saveipnet(){
	ipnet.formAction.value= 'add';

	var btn = document.getElementById("button.update");
	btn.disabled = true;
	if(checkNull()){
	ipnet.action = "<%=request.getContextPath() %>/secure/baseinfo/newLinePortSnmpParam.wss";
	this.ipnet.submit();
	}else{
	ipnet.action = "<%=request.getContextPath() %>/secure/baseinfo/newLinePortSnmpParam.jsp";
	this.ipnet.submit();
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
  if(Trim(document.getElementById("major").value )== ""){
  alert("名称不能为空！");
  return false;
  }
  return true;
  }
</script>



<body onLoad="initAll();" class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


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
        
        <h1 id="title-bread-crumb">新建线路 /端口 SNMP性能指标</h1>


<form action="" method="post" id="ipnet" name="ipnet">        
       
<!-- button section -->
<br>
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap><table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr>
          <td><table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0">
            <tr>
              <td><input type="submit" name="button.update" value="保存" class="buttons" onClick="saveipnet()"  id="functions"/></td>
              
            </tr>
            <tr>
    	<td colspan="2">
    		<c:if test="${model.message != null &&  model.message != ''}">
    			<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
			</c:if>
		</td>
	</tr>
          </table></td>
        </tr>
        </table>
          <input type="hidden" name="gid" value="${model.gid}"/>
    <input type="hidden" name="supid" value="${model.grpnet.supid}"/>
    <input type="hidden" name="level" value="${model.grpnet.level}"/>
     <input type="hidden" name="formAction" value="save"/>  

        </td>
        </tr>
        </table>
 
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row"  nowrap> 
    

		<input name="_name" value="snmpPIRVATE" type="hidden">
		<div class="sectionHeading" value="详细内容">SNMP性能指标</div>
		<div class="sectionContent">
		<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">


			

				<tbody><tr><td  class="table-row"  nowrap>
				<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">
			

				<tr class="table-row">
					<td class="tdlabel">编号:</td>
					<td class="tdcontent">
						
					&nbsp;自动计算
					  <input name="eveid" value="" id="formStyle1" type="hidden">

					
						
						
						
						
					</td>
				</tr>

			

				<tr class="table-row">
					<td class="tdlabel">模式类别</td>
					<td class="tdcontent">
						
					&nbsp;SNMP
					  <input name="modid" value="" id="formStyle1" type="hidden">
					
						
						
						
						
					</td>

				</tr>

				<tr class="table-row">
					<td >公有/私有</td>
					<td >
																								
									
									<fieldset style="width: 400px"><input name="general" value="-1" checked="checked" type="radio">私有&nbsp;<input name="general" value="0" type="radio">公有&nbsp;</fieldset>
									
									
					</td>
				</tr>			

				<tr class="table-row">
					<td class="tdlabel">类型</td>
					<td class="tdcontent">




									<fieldset style="width: 400px">

									<input name="ecode" value="1"  type="radio">
									设备
								
									
								
								<input name="ecode" value="6" checked="checked" type="radio">
								线路/端口
								<input name="ecode" value="9"  type="radio">
								私有Mib Index
									</fieldset>
								
						
						
					</td>
				</tr>

			

				<tr class="table-row">
					<td class="tdlabel">名称</td>
					<td class="tdcontent">
						
						
							&nbsp;<input name="major" id="major" value="${model.major }" maxlength="64"  style="width: 400px">
						
						
						
						
					</td>
				</tr>

			

				<tr class="table-row">
					<td class="tdlabel">描述</td>

					<td class="tdcontent">
						
						
						
						
						
							<textarea name="description" rows="5" cols="50" id="formStyle2" style="width: 400px">${model.description }</textarea>
						
					</td>
				</tr>

			
			<tr class="table-row">
				<td colspan="2" class="tdbutton" >
				</td>
			</tr></table></td></tr>
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


</TD></TR>
</body>
</html>