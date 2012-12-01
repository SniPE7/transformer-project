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
function testsnmpfunc(){
	var ip = newdev.devip.value;
	if(ip==''){
		alert('IP地址为空!');
		return;
	}
	var rc = newdev.rcommunity.value;
	if(rc==''){
		alert('RCommunity为空!');
		return;
	}
	if( ! checksnmp()){
		return;
	}
	var url ="<%=request.getContextPath()%>/secure/resourceman/snmptest.wss?devip="+newdev.devip.value+"&rcommunity="+newdev.rcommunity.value+"&snmpversion="+newdev.snmpversion.value;
	
	callServer(url, 'updatedo');	
}

function choosedevice(){
	newdev.action = "<%=request.getContextPath() %>/secure/resourceman/chooseDevicetype.wss?formAction=edit&gid="+newdev.gid.value+"&supid="+newdev.supid.value+"&level="+newdev.level.value;
	//alert("in update device info.jsp newdev.action="+newdev.action);
	newdev.submit();
}

function updatedo(response){
	if(response=='1') alert('SNMP测试成功!');
	if(response=='0') alert('SNMP测试失败!');
}

function isDigit(s){
	var r,re;
	re= /\d*/i;
	r=s.match(re);
	return(r==s)?1:0;
}

function addnewflashfile(){
  if(!isDigit(newdev.flashtempsize.value)){
    alert('请输入数值!');
    return;
  }
  if((newdev.flashtempname.value=='')|( newdev.flashtempsize.value=='')){
    alert('请输入名称,大小数值!');
    return;
  }  
  var NewTR1 = document.getElementById("flashs");
  
  //alert("the table is"+NewTR1);
 // var NewTR=newdev.flashs.insertRow(newdev.flashs.rows.length-1);
 var NewTR=NewTR1.insertRow(NewTR1.rows.length-1);
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


</script>
<script language="JavaScript" type="text/javascript">

function savedeviceinfo(){
	var v = confirm('确定保存?');
	if( ! v ){ return false;	}
	newdev.formAction.value= 'save';
	newdev.action = "<%=request.getContextPath() %>/secure/resourceman/savingdeviceInfo.wss";
	newdev.submit();
	return true;
}
function cancel(){
	return false;
}

	
function pollingfunc(){
	var ip = newdev.devip.value;
	if(ip==''){
		alert('IP为空!');
		return;
	}
	var rc = newdev.rcommunity.value;
	if(rc==''){
		alert('RCommunity为空!');
		return;
	}
	if( ! checksnmp()){
		return;
	}
	var v=confirm('确定polling此IP '+newdev.devip+'?');
	if(v){
		newdev.formAction.value = "edit";
		newdev.action="<%=request.getContextPath()%>/secure/resourceman/pollsnmp.wss?formAction=edit&gid="+newdev.gid.value+"&supid="+newdev.supid.value+"&level="+newdev.level.value;
		newdev.submit();
	}
}
	
function checksnmp(){
	var vs=newdev.snmpversion.options[newdev.snmpversion.selectedIndex].value;
	if(vs!='2' && vs != '1'){
		alert('目前仅支持SNMP V2和V1 !');
		return false;
	}
	return true;
}
function deltablerow(tableid,checkboxid){
   var s=document.getElementsByName(checkboxid);
    for(var i=0;i<s.length;i++){
       if(s[i].checked==true) {
       eval('document.all.'+tableid).deleteRow(i+1);
       deltablerow(tableid,checkboxid);
        }
        }
}
function cancelform(){
newdev.formAction.value = "edit";
		newdev.action="<%=request.getContextPath()%>/secure/resourceman/deviceInfoOfGid.wss?gid=${model.gid}";
		newdev.submit();
}
</script>




<body  class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  ><TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage"><TR><TD CLASS="pageTitle">资源管理
          </TD>
          <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">网络设备</TH>

  </TR>
  

  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">编辑设备详细内容</h1>

        <form action="" method="post" id="newdev1" name="newdev">        
       <input type="hidden" name="update" value="update"/>
<!-- button section --><br>
				<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 

		<td>
    		<input type="button" name="button.update" value="确认"  onClick="savedeviceinfo()"  class="buttons" id="functions"/>
    	</td>
		<td>
    		<input type="button" name="btncancel" value="取消"   onClick="cancelform()"  class="buttons" id="functions"/>
    	</td>
        
    </tr></table>    
  <c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
	</c:if>
    <input type="hidden" name="gid" value="${model.gid}"/>
    <input type="hidden" name="supid" value="${model.grpnet.supid}"/>
    <input type="hidden" name="level" value="${model.grpnet.level}"/>
     <input type="hidden" name="formAction" value="0"/>  

        </td>
        </tr>
        </table>
 
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row"  nowrap>

						<table cellspacing="1" cellpadding="3" border="0" width="100%" class="framing-table">
							<tbody>
								<tr class="table-row">
									<td width="30%">设备名称</td>
									<td><input type="text" name="devname" size="20" value="${model.form.devname }"/></td>
									<td><input type="hidden" name="devid" value="${model.form.devid }" /></td>
								</tr>
								<tr class="table-row">
									<td>设备别名</td>
									<td><input type="text" name="devalias" size="20" value="${model.form.devalias }"></td>
									<td></td>
								</tr>
								<tr class="table-row">
									<td>设备IP</td>
									<td><input type="text" name="devip" id="input.devip" value="${model.form.devip }" size="20"></td>
									<td>* 必填 *</td>
								</tr>
								<tr class="table-row">
									<td>资源编号</td>
									<td><input type="text" name="resnum" value="${model.form.resnum }" size="20"></td>
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
								<tr class="table-row">
									<td>设备类型</td>
									<td><input id="devtype1" type="text" name="devtype" size="20" value="${model.form.devtype }" readonly="readonly" ></td>
									<td>&nbsp;<input  type="button" name="selDeviceType" value="选择类型"
												onclick="choosedevice();" class="buttons" id="buttonStyle1"></td>
								</tr>
								<tr class="table-row">
									<td>子类型</td>
									<td><input id="subtype1" type="text" name="devsubtype" size="20" value="${model.form.devsubtype }" readonly="readonly"></td>
									<td></td>
								</tr>
								<tr class="table-row">
									<td>设备型号</td>
									<td><input id="model1" type="text" name="model" size="20" value="${model.form.model }" readonly="readonly"></td>
									<td></td>
								</tr>
								<tr class="table-row">
									<td>制造厂商</td>
									<td><input id="mf1" type="text" name="manufacture" size="20" value="${model.form.manufacture }" readonly="readonly"></td>
									<td><input type="hidden" name="mrid" value="${model.form.mrid }"/></td>
								</tr>
								<tr class="table-row">
									<td>Object ID</td>
									<td><input id="oid1" type="text" name="objectid" size="20" value="${model.form.objectid}" readonly="readonly"></td>
									<td><input id="dtid" type="hidden" name="dtid" value="${model.form.dtid }"/> </td>
								</tr>
								<tr class="table-row">
									<td>SNMP版本</td>
									<td><select name="snmpversion" id="snmpverion1" style="width: 150px">
										<option value="-1">--请选择--</option>
										<option value="1" <c:if test="${model.form.snmpversion==1}">selected="selected"</c:if> >V1</option>
										<option value="2" <c:if test="${model.form.snmpversion==2}">selected="selected"</c:if> >V2</option>
										<option value="3" <c:if test="${model.form.snmpversion==3}">selected="selected"</c:if> >V3</option>
									</select></td>
									<td></td>
								</tr>
								<tr class="table-row">
									<td>共同体名</td>
									<td><input type="password" name="rcommunity" id="input.rcommunity" value="${model.form.rcommunity }" size="22"></td>
									<td>
									&nbsp;<input type="button" name="testsnmp" value="测试" onClick="testsnmpfunc();" class="buttons" id="buttonStyle1">&nbsp;
								
									&nbsp;<input type="button" name="polling" value="POLL" onClick="pollingfunc();" class="buttons" id="buttonStyle1">&nbsp;
									</td>
								</tr>
								<tr class="table-row">
								<td>私有Index</td>
								<td colspan="2" >
								<fieldset hight="80">								
								<div name="predefmibindex" style="height: 80px;  overflow: auto">
								<c:catch var="ex2">
								<c:forEach items="${model.alldefmibgrp }" var="cp">
								
								<input type="checkbox" name="predefmibindex" value="${cp.mid}|${cp.name}|${cp.indexoid}|${cp.descroid}" <c:if test="${model.pdmid2nameflag[cp.mid][1]==1}" >checked="checked"</c:if> />
								${cp.name} (${cp.indexvar} : ${cp.descrvar})<br/>
								
								</c:forEach>
								</c:catch>
								</div>
								</fieldset>
								</td>
								</tr>
								<tr class="table-row">
									<td>负责人</td>
									<td colspan="2"><input type="text" name="adminName" size="20" value="${model.form.adminName}"></td>
								</tr>
								<tr class="table-row">
									<td>负责人电话</td>
									<td colspan="2"><input type="text" name="adminPhone" size="20" value="${model.form.adminPhone }"></td>
								</tr>
								<!--<tr>
									<td>设备策略</td>
									<td><input type="text" name="devpolicy" size="20" value="${model.form.devpolicy }"></td>
									<td></td>
								</tr>
								<tr>
									<td>时间策略</td>
									<td><input type="text" name="timeframPolicy" size="20" value="${model.form.timeframPolicy }"></td>
									<td></td>
								</tr>-->
								<tr class="table-row" >
									<td>设备软件版本</td>
									<td colspan="2"><input type="text" name="devSoftwareVer" size="20" value="${model.form.devSoftwareVer}"></td>
								</tr>
								<tr class="table-row">
									<td>设备序列号</td>
									<td colspan="2"><input type="text" name="devSerialNum" size="20" value="${model.form.devSerialNum }"></td>
								</tr>
<!--								<tr>-->
<!--									<td>设备组</td>-->
<!--									<td><input type="text" name="devgroup" size="20"></td>-->
<!--									<td></td>-->
<!--								</tr>-->
								<tr class="table-row">
									<td>RAM 大小</td>
									<td colspan="2"><input type="text" name="ramsize" size="20" value="${model.form.ramsize}"></td>
								</tr>
								<tr class="table-row">
									<td>NVRAM 大小</td>
									<td colspan="2"><input type="text" name="nvramsize" size="20" value="${model.form.nvramsize}"></td>
								</tr>
								<tr class="table-row">
									<td>FLASH 大小</td>
									<td colspan="2"><input type="text" name="flashSize" size="20" value="${model.form.flashSize}" ></td>
								</tr>
								<tr class="table-row">
									<td>描述</td>
									<td colspan="2"><textarea rows="5" cols="40" name="description">${model.form.description }</textarea></td>
									
								</tr>
								<tr class="table-row">
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
<br/>



<c:catch>

				<table cellspacing="1" cellpadding="3" border="0" width="100%" class="framing-table">
					<tbody>
					<tr class="table-row">
					  <td colspan="7">
					端口详细内容
					</td></tr>
						<tr class="table-row">
							<td>端口索引</td>
							<td>端口名称</td>
							<td>端口IP</td>
							<td>端口Mac 地址</td>
							<td>&nbsp;</td>
<!--							<td>Policy name</td>-->
<!--							<td>TimeFrame Policy Name</td>-->
<!--							<td></td>-->
						</tr>					
						<c:forEach items="${model.portinfo }" var="if" varStatus="x" begin="0" step="1">
						
						<tr class="table-row">
						    
							<td><input type="text" name="interface_index" value="${if.ifindex }" /></td>
							<td><input type="text" name="interface_name" value="${if.ifdescr }" /></td>
							<td><input type="text" name="interface_ip" value="${if.ifip}" /></td>
							<td><input type="text" name="interface_mac" value="${if.ifmac}" /></td>
							<td><input type="hidden" name="ptid" value="${if.ptid }"/></td>
<!--							<td></td>-->
<!--							<td></td>-->
<!--							<td></td>-->
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<br/>
</c:catch>
<c:catch>

				<table cellspacing="1" cellpadding="3" border="0" width="100%" class="framing-table">
					<tbody>
					<tr class="table-row">
					  <td colspan="3">
					FLASHFILE 详细内容
					</td></tr>
					<tr class="table-row"><td colspan="3">
							<table id="flashs" cellspacing="1" cellpadding="3" border="0" width="100%" class="framing-table" >
								<tbody>
									<tr class="table-row">
										<td>&nbsp;</td>
										<td>名称<input type="text" name="flashtempname" size="20"></td>
										<td>大小<input type="text" name="flashtempsize" size="20"></td>
										<td><input type="button" name="insert" value="插入" onClick="addnewflashfile();" class="buttons" id="buttonStyle1"></td>
									</tr>
						
									
						<c:forEach items="${model.form.flashfile}"	var="ff"  >	
						<tr class="table-row">
							<td><input type="checkbox" name="checkdel"></td>
							<td ><input type="hidden" name="flashfile_name" size="20" value="${ff.name }">${ff.name }</td>
							<td ><input type="hidden" name="flashfile_size" size="20" value="${ff.size }">${ff.size }</td>
							<td>&nbsp;</td>
						</tr>
						</c:forEach>
						<tr class="table-row">
							<td><input type="button" value="删除" onClick="deltablerow('flashs','checkdel')" class="buttons" id="buttonStyle1"></td>							
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tbody></table>
		</td></tr></tbody></table><br/>
		</c:catch>
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