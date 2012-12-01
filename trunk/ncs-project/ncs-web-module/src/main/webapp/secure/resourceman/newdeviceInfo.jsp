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
<script src="<%=request.getContextPath() %>/js/ajax.js" type="text/javascript" language="JavaScript">
</script>
</head>

<!-- list of menu -->

<script language="JavaScript" type="text/javascript">

function filtery(patterns, list){
 /* backed up dropdown list */
 if (!list.bak){

   list.bak = new Array();
   for (n=0; n<list.length; n++){
     list.bak[list.bak.length] = new Array(list[n].value, list[n].text);
   }
 }
 
 patternArray = patterns.split(" ");

 match = new Array();
 nomatch = new Array();
 for (n=0;n<list.bak.length;n++){

   matchflag = 1;
   for (j=0;j<patternArray.length; j++){
     pattern = patternArray[j];
	 if(list.bak[n][1].toLowerCase().indexOf(pattern.toLowerCase())==-1){
	   matchflag = 0;
	   continue;	  
     }
   }
   if(matchflag==1){
      match[match.length] = new Array(list.bak[n][0], list.bak[n][1]);
   }else{ 
      nomatch[nomatch.length] = new Array(list.bak[n][0], list.bak[n][1]);
   }
 }

 for (n=0; n<match.length; n++){
   list[n].value = match[n][0];
   list[n].text = match[n][1];
 }
 for (n=0; n<nomatch.length; n++){
   list[n+match.length].value = nomatch[n][0];
   list[n+match.length].text = nomatch[n][1];
 }

 /* we make the 1st item selected  */
 list.selectedIndex=0;
}

</script>






<script language="javascript">


function savedeviceinfo(){
	var v = confirm('Are you sure to save the info?');
	if( ! v ){ return false;	}
	newdev.formAction.value= 'save';
	
	
	return true;
}
function cancel(){
	return false;
}

	
function polling(){
	var ip = newdev.devip.value;
	if(ip==''){
		alert('IP address is empty!');
		return;
	}
	var rc = newdev.rcommunity.value;
	if(rc==''){
		alert('RCommunity is empty!');
		return;
	}
	if( ! checksnmp()){
		return;
	}
	var v=confirm('Are you sure to polling this IP '+newdev.devip+'?');
	if(v){
		newdev.action="<%=request.getContextPath()%>/secure/resourceman/pollsnmp.wss";
		newdev.submit();
	}
}
	
function checksnmp(){
	var vs=newdev.snmpversion.options[newdev.snmpversion.selectedIndex].value;
	if(vs!='2' && vs != '1'){
		alert('Only Support SNMP V2 and V1 !');
		return false;
	}
	return true;
}
	
function testsnmp(){
	var ip = newdev.devip.value;
	if(ip==''){
		alert('IP address is empty!');
		return;
	}
	var rc = newdev.rcommunity.value;
	if(rc==''){
		alert('RCommunity is empty!');
		return;
	}
	if( ! checksnmp()){
		return;
	}
	var url ="<%=request.getContextPath()%>/secure/resourceman/snmptest.wss?devip="newdev.devip+"&rcommunity="+newdev.rcommunity+"&snmpversion="+newdev.snmpversion;
	callServer(url, 'updatedo');	
}

function updatedo(response){
	if(response=='1') alert('SNMP Test Successed !!');
	if(response=='0') alert('SNMP Test Failure !');
}

function addnewflashfile(){
  if(!isDigit(newdev.flashtempsize.value)){
    alert('Not a number !');
    return;
  }
  var NewTR=newdev.flashs.insertRow(newdev.flashs.rows.length-1);
  NewTR.className='table-row';
    
  var oTD=NewTR.insertCell(0);
  oTD.innerHTML='<input type="checkbox" id="checkdel">';   
 
  oTD=NewTR.insertCell(1); 
  oTD.innerHTML='<input type="hidden" name="flashfile_name" value="'+newdev.flashtempname.value+'">'+newdev.flashtempname.value;
  
  oTD=NewTR.insertCell(2);
  oTD.innerHTML='<input type="hidden" name="flashfile_size" value="'+newdev.flashtempsize.value+'">'+newdev.flashtempsize.value;
  
  oTD=NewTR.insertCell(3);
  oTD.innerHTML=' ';  
  
  newdev.flashtempname.value="";
  newdev.flashtempsize.value="";
  return true;
}

// -->
</script>
<% int nodei=0; %>


<body  class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


 <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle"><span class="wpsPortletArea">添加新设备</span></TD>
          <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%"><span class="wpsPortletArea">添加新设备</span></TH>

  </TR>
  

  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">
        添加新设备
        <form action="<%=request.getContextPath() %>/secure/resourceman/savingdeviceInfo.wss" method="post" id="newdev" name="newdev">        
          
  <!-- button section -->
  <table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 

		<td>
    		<input type="submit" name="button.update" value="确认" class="buttons" onClick="savedeviceinfo();"  id="functions"/>
    	</td>
    	<td>
    		<input type="submit" name="button.delete" value="取消" class="buttons" onClick="cancel();"  id="functions"/>
    	</td>
        <td><font color="red"> ${error}</td>
    </tr></table>    
 
    <input type="hidden" name="gid" value="${model.gid}"/>${model.gid}
    <input type="hidden" name="supid" value="${model.grpnet.supid}"/>${model.grpnet.supid}
    <input type="hidden" name="level" value="${model.grpnet.level}"/>${model.grpnet.level}
     <input type="hidden" name="formAction" value="save"/>  
<div class="validation-error" >&nbsp;${model.message }</div>
        </td>
        </tr>
        </table>
 
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row"  nowrap>

						<table border="1">
							<tbody>
								<tr>
									<td>设备名称</td>
									<td><input type="text" name="devname" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>设备别名</td>
									<td><input type="text" name="devalias" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>设备管理IP</td>
									<td><input type="text" name="devip" size="20"></td>
									<td>* 必填 *</td>
								</tr>
								<tr>
									<td>资产编号</td>
									<td><input type="text" name="resnum" size="20"></td>
									<td></td>
								</tr>
								<tr class="table-row">
									<td>用途</td>
									<td><select name="domainid" id="domianid1" style="width: 150px">
										<option value="">--请选择--</option>
										<option value="0" <c:if test="${model.form.domainid==0}">selected="selected"</c:if> >其他</option>
										<option value="1" <c:if test="${model.form.domainid==1}">selected="selected"</c:if> >网点</option>
										<option value="2" <c:if test="${model.form.domainid==2}">selected="selected"</c:if> >ATM</option>
									</select></td>
									<td></td>
								</tr>								
<!--								<tr>-->
<!--									<td>Provider phone</td>-->
<!--									<td><select name="providerphone"><option value="Please select one"></option></select></td>-->
<!--									<td></td>-->
<!--								</tr>-->
								<tr>
									<td>设备类型</td>
									<td><input id="devtype1" type="text" name="devtype" size="20"  readonly="readonly" ></td>
									<td><input  type="button" name="selDeviceType" value="选择类型"
												onclick="openUrl('<%=request.getContextPath() %>/secure/resourceman/chooseDevicetype.wss','_blank',800,600,'yes');" ></td>
								</tr>
								<tr>
									<td>子类型</td>
									<td><input id="subtype1" type="text" name="devsubtype" size="20"  readonly="readonly"></td>
									<td></td>
								</tr>
								<tr>
									<td>设备类型</td>
									<td><input id="model1" type="text" name="model" size="20"  readonly="readonly"></td>
									<td></td>
								</tr>
								<tr>
									<td>制造厂商</td>
									<td><input id="mf1" type="text" name="manufacture" size="20"  readonly="readonly"></td>
									<td></td>
								</tr>
								<tr>
									<td>Object ID</td>
									<td><input id="oid1" type="text" name="objectid" size="20"  readonly="readonly"></td>
									<td><input id="dtid" type="hidden" name="dtid" value=""/> </td>
								</tr>
								<tr>
									<td>SNMP版本</td>
								  <td><select name="snmpversion" id="snmpverion1" >
										<option value="-1">--请选择--</option>
										<option value="1" <c:if test="${model.form.snmpversion==1}">selected="selected"</c:if> >V1</option>
										<option value="2" <c:if test="${model.form.snmpversion==2}">selected="selected"</c:if> >V2</option>
										<option value="3" <c:if test="${model.form.snmpversion==3}">selected="selected"</c:if> >V3</option>
									</select></td>
									<td></td>
								</tr>
								<tr>
									<td>共同体名</td>
									<td><input type="text" name="rcommunity" size="20"></td>
									<td>
									&nbsp;<input type="button" name="testsnmp" value="测试" onClick="testsnmp();">&nbsp;
									&nbsp;<input type="button" name="polling" value="POLL" onClick="polling();">&nbsp;
									</td>
								</tr>
								<tr>
									<td>负责人</td>
									<td><input type="text" name="adminName" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>负责人电话</td>
									<td><input type="text" name="adminPhone" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>设备策略</td>
									<td><input type="text" name="devpolicy" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>时间策略</td>
									<td><input type="text" name="timeframPolicy" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>设备软件版本</td>
									<td><input type="text" name="devSoftwareVer" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>设备序列号</td>
									<td><input type="text" name="devSerialNum" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>设备组</td>
									<td><input type="text" name="devgroup" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>RAM大小</td>
									<td><input type="text" name="ramsize" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>NVRAM大小</td>
									<td><input type="text" name="nvramsize" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>FLASH大小</td>
									<td><input type="text" name="flashSize" size="20"></td>
									<td></td>
								</tr>
								<tr>
									<td>描述</td>
									<td colspan="2"><textarea rows="5" cols="40" name="description"></textarea></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
<br/>



				<table cellspacing="1" cellpadding="3" border="0" width="100%" class="framing-table">
					<tbody>
					<tr class="table-row">
					  <td colspan="7">端口详细内容</td></tr>
						<tr class="table-row">
							<td>端口Index</td>
							<td>端口名称</td>
							<td>端口IP</td>
							<td>端口Mac地址</td>
<!--							<td>Policy name</td>-->
<!--							<td>TimeFrame Policy Name</td>-->
<!--							<td></td>-->
						</tr>
						<c:catch>
						<c:forEach items="${devicetypeform.interface_index }" var="if" varStatus="x" begin="0" step="1">
						<tr>
							<td><input type="hidden" name="interface_index" value="${devicetypeform.interface_index[x] }" />${devicetypeform.interface_index[x] }</td>
							<td><input type="hidden" name="interface_name" value="${devicetypeform.interface_name[x] }" />${devicetypeform.interface_name[x] }</td>
							<td><input type="hidden" name="interface_ip" value="${devicetypeform.interface_ip[x] }" />${devicetypeform.interface_ip[x] }</td>
							<td><input type="hidden" name="interface_mac" value="${devicetypeform.interface_mac[x] }" />${devicetypeform.interface_mac[x] }</td>
<!--							<td></td>-->
<!--							<td></td>-->
<!--							<td></td>-->
						</tr>
						</c:forEach>
						</c:catch>
					</tbody>
				</table>
<br/>

				<table border="1">
					<tbody>
					<tr class="table-row">
					  <td colspan="3">
					FLASHFILE详细内容
					</td></tr>
					<tr class="table-row"><td colspan="3">
							<table id="flashs" cellspacing="1" cellpadding="3" border="0" width="100%" class="framing-table" >
								<tbody>
									<tr class="table-row">
										<td>名称</td>
										<td><input type="text" name="flashname" size="20"></td>
										<td>大小</td>
										<td><input type="text" name="flashsize" size="20"></td>
										<td><input type="button" name="insert" value="插入" onClick="addnewflashfile();"></td>
									</tr>
								</tbody>
							</table>
							</td></tr>		
						<c:catch>	
						<c:forEach items="${devicetypeform.flashfile_name}"	var="ff" varStatus="y" begin="0" step="1"	>	
						<tr>
							<td><input type="checkbox" name="checkdel"></td>
							<td><input type="hidden" name="flashfile_name" size="20" value="${devicetypeform.flashfile_name[y] }">${devicetypeform.flashfile_name[y] }</td>
							<td><input type="hidden" name="flashfile_size" size="20" value="${devicetypeform.flashfile_size[y] }">${devicetypeform.flashfile_size[y] }</td>
						</tr>
						</c:forEach>
						</c:catch>
						<tr>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table></td></tr></table>
<br/>

<c:catch var="ex1">
<c:forEach items="${model.pdmInfoByMidMap}"  var="pdminfo">
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row"  nowrap>
				<table cellspacing="1" cellpadding="3" border="0" width="100%" class="framing-table">
					<tbody>
					<tr class="table-row">
					  <td colspan="3">
					私有Index ${model.pdmid2nameflag[pdminfo.key][0]} 详细内容
					</td></tr>
						<tr class="table-row">
							<td>私有Index <br/>${model.pdmid2nameflag[pdminfo.key][2]}</td>
							<td>私有Index名称<br/>${model.pdmid2nameflag[pdminfo.key][3]}</td>
							<td>&nbsp;</td>
						</tr>					
						<c:forEach items="${pdminfo.value}" var="idx" varStatus="x" begin="0" step="1">
						
						<tr class="table-row">						    
							<td><input type="text" name="pdmindex" value="${idx.oidindex }" /></td>
							<td><input type="text" name="pdmdescr" value="${idx.oidname }" /></td>
							<td><input type="hidden" name="pdmid" value="${idx.pdmid}"/>
							<input type="hidden" name="mid" value="${idx.mid}"/>						
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table></td></tr></table>
				<br/>
</c:forEach>
</c:catch>



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