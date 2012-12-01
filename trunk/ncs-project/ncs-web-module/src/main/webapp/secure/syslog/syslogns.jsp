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


function   Trim(m){   
  while((m.length>0)&&(m.charAt(0)==' '))   
  m   =   m.substring(1, m.length);   
  while((m.length>0)&&(m.charAt(m.length-1)==' '))   
  m = m.substring(0, m.length-1);   
  return m;   
  }
function checkNull(){
	if(document.getElementById("markID").value==""){
		alert("MARK不能为空!");
		return false;
	}
		
	if(document.getElementById("manufactureID").value==""){
		alert("厂商不能为空!");
		return false;
	}
	
	if(Trim(document.getElementById("btimelist1").value)=="" ||Trim(document.getElementById("etimelist1").value)=="" )
	{	alert("监控时间段1不能为空!");
		return false;
	}
	if(Trim(document.getElementById("btimelist2").value)=="" ||Trim(document.getElementById("etimelist2").value)=="" )
	{	alert("监控时间段2不能为空!");
		return false;
	}
	if(Trim(document.getElementById("btimelist3").value)=="" ||Trim(document.getElementById("etimelist3").value)=="" )
	{	alert("监控时间段3不能为空!");
		return false;
	}
	if(Trim(document.getElementById("btimelist4").value)=="" ||Trim(document.getElementById("etimelist4").value)=="" )
	{	alert("监控时间段4不能为空!");
		return false;
	}
	if(Trim(document.getElementById("btimelist5").value)=="" ||Trim(document.getElementById("etimelist5").value)=="" )
	{	alert("监控时间段5不能为空!");
		return false;
	}
	if(Trim(document.getElementById("btimelist6").value)=="" ||Trim(document.getElementById("etimelist6").value)=="" )
	{	alert("监控时间段6不能为空!");
		return false;
	}
	if(Trim(document.getElementById("btimelist7").value)=="" ||Trim(document.getElementById("etimelist7").value)=="" )
	{	alert("监控时间段7不能为空!");
		return false;
	}
	if(Trim(document.getElementById("severity1").value)=="")
	{	alert("SEVERITY1 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("severity2").value)=="")
	{	alert("SEVERITY2 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("notcareflag").value)=="")
	{	alert("NOTCAREFLAG 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("typeID").value)=="")
	{	alert("TYPE 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("eventtypeID").value)=="")
	{	alert("EVENTTYPE 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("subeventtypeID").value)=="")
	{	alert("SUBEVENTTYPE 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("alertgroupID").value)=="")
	{	alert("ALERTGROUP 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("attentionflagID").value)=="")
	{	alert("ATTENTIONFLAG 不能为空!");
		return false;
	}
	if(Trim(document.getElementById("eventsID").value)=="")
	{	alert("EVENTS 不能为空!");
		return false;
	}
	/*if(Trim(document.getElementById("origeventID").value)=="")
	{	alert("ORIGEVENT 不能为空!");
		return false;
	}*/
	
	
	return true;
}

function addsyslog(){
	form1.action = "<%= request.getContextPath()%>/secure/syslog/newsyslogns.wss";
	if(checkNull()){
		this.form1.submit();
	}
}
function updatesyslog(){
	form1.action = "<%= request.getContextPath()%>/secure/syslog/updatesyslogns.wss";
	if(checkNull()){
		if(document.getElementById("markOldID").value != document.getElementById("markID").value 
		|| document.getElementById("manufID").value != document.getElementById("manufactureID").value)
		{
			var v = confirm('主键Mark，Manufacture已更改，若更新失败会导致当前记录丢失，是否继续？');
			if( !v )return false;	
		}
		this.form1.submit();
	}
}

 function checkseverity1(){
	var severity1 = document.getElementById("severity1").value;
	
	var reg=/^[1-7]{1}$/;
	if(severity1 == 100 || severity1.match(reg) ){
		return true;
	}
	
	    alert("请正确填写Severity1,取值范围为1-7或100");
		return false;
	
}
     function checkseverity2(){
     var severity2 = document.getElementById("severity2").value;
     var reg=/^[1-7]{1}$/;
     if(severity2 == 100 || severity2.match(reg)){
	   
		return true;
	}
	 alert("请正确填写Severity2,取值范围为1-7或100");
		return false;
     }

 
</script>





<body  class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


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
      <TH class="wpsPortletTitle" width="100%">基础信息维护</TH>

     
  </TR>
   

  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb"><c:choose>
           <c:when test="${model.method == 'update' }">更新Syslog Event Process_NS</c:when>
           <c:otherwise> 新建Syslog Event Process_NS</c:otherwise>
         </c:choose></h1>

        <form action="" method="post" id="newdev1" name="form1">        
       
<!-- button section --><br>
				<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0">
    <tr> 
       	<c:choose >
					                <c:when test="${model.method == 'update'}" >					                
					                	<td>
    		<input type="button" name="button.update" value="确认" class="buttons" onClick="updatesyslog()" id="functions" />
    	</td>
					               </c:when>
					               <c:otherwise >
					               		<td>
    		<input type="button" name="button.new" value="确认" class="buttons" onClick="addsyslog()"  id="functions"/>
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
								 
									<td>MARK<span style="color: red">*</span>
									<input type="hidden" id="markOldID" name="markOld" value="${model.markOld }">
									<input type="hidden" id="manufID" name="manuf" value="${model.manuf }"></td> 
										<c:choose >
					                <c:when test="${model.method == 'update'}" >					                
					                	<td>
    		<input type="text" id="markID" name="mark" size="20" value="${model.mark }" maxlength="255"/>
    	</td>
					               </c:when>
					               <c:otherwise >
					               		<td>
    	<input type="text" id="markID" name="mark" size="20" value="${model.mark }" maxlength="255"/>
    	</td>
					               </c:otherwise>
					 </c:choose>
									
                                   
								<td>MANUFACTURE<span style="color: red">*</span></td>
									<td>
									
										<c:if test="${model.method == 'update' or model.method=='new'}">
                                        <select id="manufactureID" name="manufacture" class="input">
                                        <option value="">＝＝＝请选择＝＝＝</option>
                                        <c:forEach items="${model.manus }" var="m1">
                                        <c:catch var="ex001">
                                          <c:choose>
                                             <c:when test="${m1.mrname == model.manufacture}">
                                                 <option value="${m1.mrname }" selected="selected">${m1.mrname }</option>
                                             </c:when>
                                             <c:otherwise>
                                                <option value="${m1.mrname }" >${m1.mrname }</option>
                                             </c:otherwise>
                                          </c:choose>
                                          </c:catch>
                                        </c:forEach>
                                        </select>
                                        </c:if>
                                        <c:if test="${model.method == null || model.method == '' }">
                                           <select id="manufactureID" name="manufacture" class="input">
                                              <c:forEach items="${model.manus }" var="m1">
                                                <option value="${m1.mrname }">${m1.mrname }</option>
                                              </c:forEach>
                                           </select>
                                        </c:if>
									</td>
									
									
								</tr>
								
								<c:if test="${model.method == 'update' or model.method=='new'}">
								<tr class="table-row">
									<td>监控时段1<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist1" name="btimelist"  value="${fn:substring(model.btimelist,0,8)}" size="8">--<input type="text" id="etimelist1" name="etimelist"  value="${fn:substring(model.etimelist,0,8)}" size="8"></td>
								
									<td>监控时段2<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist2" name="btimelist"  value="${fn:substring(model.btimelist,9,17)}" size="8">--<input type="text" id="etimelist2" name="etimelist"  value="${fn:substring(model.etimelist,9,17)}" size="8"></td>
								</tr>
								
								<tr class="table-row">
									<td>监控时段3<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist3" name="btimelist"  value="${fn:substring(model.btimelist,18,26)}" size="8">--<input type="text" id="etimelist3" name="etimelist"  value="${fn:substring(model.etimelist,18,26)}" size="8"></td>
								
									<td>监控时段4<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist4" name="btimelist"  value="${fn:substring(model.btimelist,27,35)}" size="8">--<input type="text" id="etimelist4" name="etimelist"  value="${fn:substring(model.etimelist,27,35)}" size="8"></td>
								</tr>
								<tr class="table-row">
									<td>监控时段5<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist5" name="btimelist"  value="${fn:substring(model.btimelist,36,44)}" size="8">--<input type="text" id="etimelist5" name="etimelist"  value="${fn:substring(model.etimelist,36,44)}" size="8"></td>
								
									<td>监控时段6<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist6" name="btimelist"  value="${fn:substring(model.btimelist,45,53)}" size="8">--<input type="text" id="etimelist6" name="etimelist"  value="${fn:substring(model.etimelist,45,53)}" size="8"></td>
								</tr>
								<tr class="table-row">
									<td>监控时段7<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist7" name="btimelist"  value="${fn:substring(model.btimelist,54,62)}" size="8">--<input type="text" id="etimelist7" name="etimelist"  value="${fn:substring(model.etimelist,54,62)}" size="8"></td>
								    <td></td><td></td>
								</tr>
								</c:if>
								<c:if test="${model.method == null || model.method == '' }">
								<tr class="table-row">
									<td>监控时段1<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist1" name="btimelist" value="00:00:00" size="8">--<input type="text" id="etimelist1" name="etimelist"  value="23:59:59" size="8"></td>
								
									<td>监控时段2<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist2" name="btimelist" value="00:00:00" size="8">--<input type="text" id="etimelist2" name="etimelist" value="23:59:59" size="8"></td>
								</tr>
								
								<tr class="table-row">
									<td>监控时段3<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist3" name="btimelist"  value="00:00:00" size="8">--<input type="text" id="etimelist3" name="etimelist"   value="23:59:59"  size="8"></td>
								
									<td>监控时段4<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist4" name="btimelist"   value="00:00:00" size="8">--<input type="text" id="etimelist4" name="etimelist"   value="23:59:59"  size="8"></td>
								</tr>
								<tr class="table-row">
									<td>监控时段5<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist5" name="btimelist"   value="00:00:00"  size="8">--<input type="text" id="etimelist5" name="etimelist"   value="23:59:59"  size="8"></td>
								
									<td>监控时段6<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist6" name="btimelist"   value="00:00:00"  size="8">--<input type="text" id="etimelist6" name="etimelist"   value="23:59:59"  size="8"></td>
								</tr>
								<tr class="table-row">
									<td>监控时段7<span style="color: red">*</span></td>
									<td><input type="text" id="btimelist7" name="btimelist"   value="00:00:00"  size="8">--<input type="text" id="etimelist7" name="etimelist"   value="23:59:59"  size="8"></td>
								    <td></td><td></td>
								</tr>
								</c:if>
								<tr class="table-row" >
									<td>FILTERFLAG1<span style="color: red">*</span></td>
									<td>
									<select id="filterflag1ID" name="filterflag1" class="input">
									  <c:if test="${model.method == 'update' or model.method=='new'}">
										   <option value="0" <c:if test="${model.filterflag1 == 0 }">selected="selected"</c:if>>否</option>
										   <option value="1" <c:if test="${model.filterflag1 == 1 }">selected="selected"</c:if>>是</option>
									   	</c:if>
									   	<c:if test="${model.method == ''|| model.method == null }">
									   		<option value="0" selected="selected">否</option>
				                    	    <option value="1">是</option>
									   	</c:if>
									</select>
									</td>                          
								
									<td>FILTERFLAG2<span style="color: red">*</span></td>
									<td>
									<select id="filterflag2ID"  name="filterflag2" class="input">
										<c:if test="${model.method == 'update' or model.method=='new'}">
										   <option value="0" <c:if test="${model.filterflag2 == 0 }">selected="selected"</c:if>>否</option>
										   <option value="1" <c:if test="${model.filterflag2 == 1 }">selected="selected"</c:if>>是</option>
									   	</c:if>
									   	<c:if test="${model.method == ''|| model.method == null }">
									   		<option value="0" selected="selected">否</option>
				                    	    <option value="1">是</option>
									   	</c:if>
									</select>
									</td>
									
								</tr>
								<tr class="table-row">
									<td class="tdlabel" width="21%">SEVERITY1<br><span style="font-size: 9pt">(填写范围为1-7,100)</span><span style="color: red">*</span></td>
									<td><input id="severity1ID" type="text" name="severity1" size="20" value="${model.severity1 }" onChange="checkseverity1()" ></td>
									
								
									<td class="tdlabel" width="21%">SEVERITY2<br><span style="font-size: 9pt">(填写范围为1-7,100)</span><span style="color: red">*</span></td>
									<td><input  type="text" id="severity2ID" name="severity2" size="20" value="${model.severity2 }" onChange="checkseverity2()" ></td>
									
								</tr>
								<tr class="table-row">
									<td>PORT</td>
									<td><input id="portID" type="text" name="port" size="20" value="${model.port}" maxlength="30"></td>
									
							
									<td>NOTCAREFLAG<span style="color: red">*</span></td>
									<td>
									<select  id="notcareflagID" type="text" name="notcareflag" class="input">
										<c:if test="${model.method == 'update' or model.method=='new'}">			
									   		<option value="0" <c:if test="${model.notcareflag == 0 }">selected="selected"</c:if>>否</option>
									   		<option value="1" <c:if test="${model.notcareflag == 1 }">selected="selected"</c:if>>是</option>
									   	</c:if>
									   	<c:if test="${model.method == ''|| model.method == null }">
									   		<option value="0" selected="selected">否</option>
				                    	    <option value="1">是</option>
									   	</c:if>
									</select>
									</td> 
								</tr>
								<tr class="table-row">
									<td>TYPE<span style="color: red">*</span></td>
									
									 <td>
							            <select name="type"  id="typeID"  class="input">
					                     <c:if test="${model.method == 'update' or model.method=='new'}">					                     
					                          <option value="0"  <c:if test="${model.type == 0 }">selected="selected"</c:if>>默认</option>
				                    	      <option value="1" <c:if test="${model.type == 1 }">selected="selected"</c:if>>故障</option>
				                    	      <option value="2" <c:if test="${model.type == 2}">selected="selected"</c:if>>恢复</option>
				                    	</c:if>				                    	 
				                    	<c:if test="${model.method == ''|| model.method == null }">
				                    	 	<option value="0" selected="selected">默认</option>
				                    	      <option value="1">故障</option>
				                    	      <option value="2">恢复</option>
					                   </c:if>
				               		  </select>
				               		</td>
				               		
							       <td width="20">EVENTTYPE<span style="color: red">*</span></td>
							          <td>
							            <select name="eventtype" id="eventtypeID" class="select">
					                      <c:if test="${model.method == 'update' or model.method=='new'}">
					                     
					                          <option value="1"  <c:if test="${model.eventtype == 1 }">selected="selected"</c:if>>广域网端口事件</option>
				                    	      <option value="2" <c:if test="${model.eventtype == 2 }">selected="selected"</c:if>>局域网端口类事件</option>
				                    	      <option value="3" <c:if test="${model.eventtype == 3 }">selected="selected"</c:if>>网络设备类事件</option>
				                    	      <option value="4" <c:if test="${model.eventtype == 4 }">selected="selected"</c:if>>SNASW事件</option>
				                    	      <option value="5" <c:if test="${model.eventtype == 5 }">selected="selected"</c:if>>路由事件</option>
				                    	      <option value="6" <c:if test="${model.eventtype == 6 }">selected="selected"</c:if>>阀值事件</option>
				                    	      <option value="7" <c:if test="${model.eventtype == 7 }">selected="selected"</c:if>>安全事件</option>
				                    	      <option value="8" <c:if test="${model.eventtype == 8 }">selected="selected"</c:if>>其他类事件</option>
				                    	</c:if>
				                    	<c:if test="${model.method == ''|| model.method == null }">
				                    	 <option value="1" selected="selected">广域网端口事件</option>
				                    	      <option value="2">局域网端口类事件</option>
				                    	      <option value="3"  >网络设备类事件</option>
				                    	      <option value="4">SNASW事件</option>
				                    	      <option value="5"  >路由事件</option>
				                    	      <option value="6" >阀值事件</option>
				                    	      <option value="7" >安全事件</option>
				                    	      <option value="8" >其他类事件</option>
					                   </c:if>
				               	</select>
				               	</td>
									
								</tr>
								<tr class="table-row">
									<td width="20">SUBEVENTTYPE<span style="color: red">*</span></td>
									<td><select name="subeventtype" id="subeventtypeID" class="select" >
									<c:if test="${model.method == 'update' or model.method=='new'}">
					                          <option value="1" <c:if test="${model.subeventtype == 1 }">selected="selected"</c:if>>设备宕</option>
				                    	      <option value="2" <c:if test="${model.subeventtype == 2 }">selected="selected"</c:if>>端口宕</option>
				                    	      <option value="3" <c:if test="${model.subeventtype == 3 }">selected="selected"</c:if>>板卡故障</option>
				                    	      <option value="4" <c:if test="${model.subeventtype == 4 }">selected="selected"</c:if>>电源故障</option>
				                    	      <option value="5" <c:if test="${model.subeventtype == 5 }">selected="selected"</c:if>>风扇故障</option>
				                    	      <option value="6" <c:if test="${model.subeventtype == 6 }">selected="selected"</c:if>>温度超高</option>
				                    	      <option value="7" <c:if test="${model.subeventtype == 7 }">selected="selected"</c:if>>防火墙安session告警</option>
				                    	      <option value="8" <c:if test="${model.subeventtype == 8 }">selected="selected"</c:if>>CPU告警</option>
				                    	      <option value="9" <c:if test="${model.subeventtype == 9 }">selected="selected"</c:if>>内存告警</option>
				                    	      <option value="10" <c:if test="${model.subeventtype == 10 }">selected="selected"</c:if>>其他</option>
				                    	</c:if>
				                    	<c:if test="${model.method == ''||model.method == null }">
				                    	 <option value="1"  selected="selected">设备宕</option>
				                    	      <option value="2">端口宕</option>
				                    	      <option value="3">板卡故障</option>
				                    	      <option value="4" >电源故障</option>
				                    	      <option value="5">风扇故障</option>
				                    	      <option value="6" >温度超高</option>
				                    	      <option value="7" >防火墙安session告警</option>
				                    	      <option value="8" >CPU告警</option>
				                    	      <option value="9">内存告警</option>
				                    	      <option value="10">其他</option>
				                    	 </c:if>
									</select>
									</td>
									
								
									<td>ALERTGROUP<span style="color: red">*</span></td>
									<td><input type="text" id="alertgroupID" name="alertgroup" size="20" value="${model.alertgroup}" maxlength="100"></td>
									
								</tr>
								<tr class="table-row">
									<td>ALERTKEY</td>  
									<td><input type="text" id="alertkeyID" name="alertkey" size="20" value="${model.alertkey }" maxlength="100"></td> 
								
									<td>SUMMARYCN</td>
									<td><input type="text" id="summarycnID" name="summarycn" size="20" value='${model.summarycn}' maxlength="255"></td>
									
								</tr>
								<tr class="table-row">
									<td>PROCESSSUGGEST</td>
									<td><input type="text" id="processsuggestID" name="processsuggest" size="20" value="${model.processsuggest}" maxlength="255"></td>
								
									<td>STATUS</td>
									<td><input type="text" id="statusID" name="status" size="20" value="${model.status}" maxlength="100"></td>
									
								</tr>
                                <tr class="table-row">
									<td>ATTENTIONFLAG<span style="color: red">*</span></td>
									<td>
									<select name="attentionflag" id="attentionflagID" class="input">
										<c:if test="${model.method == 'update' or model.method=='new'}">
									   		<option value="0" <c:if test="${model.attentionflag == 0 }">selected="selected"</c:if>>否</option>
									   		<option value="1" <c:if test="${model.attentionflag == 1 }">selected="selected"</c:if>>是</option>
									   </c:if>
									   	<c:if test="${model.method == ''|| model.method == null }">
									   		<option value="0" selected="selected">否</option>
				                    	    <option value="1">是</option>
									   	</c:if>
									</select>
									</td> 
									<td>EVENTS<span style="color: red">*</span></td>
									<td><input type="text" id="eventsID" name="events" size="20" value="${model.events}" maxlength="255"></td>
									
								</tr>
                                <tr class="table-row">
									<td>ORIGEVENT<span style="color: red"></span></td>
									<td><input type="text" id="origeventID" name="origevent" size="20" value='${model.origevent}' ></td>
								
								    	
									<td>VARLIST</td>
									<td><input type="text" id="varlistID" name="varlist" size="20" value="${model.varlist }" maxlength="255"></td>
									
									
								</tr>
								<tr class="table-row">
									<td height="2"></td>
								  
									
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