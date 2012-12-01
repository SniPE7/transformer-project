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
<title>New Device OID Group</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<script src="../../js/ajax.js" type="text/javascript"
	language="JavaScript"></script>
<script language="javascript">
var changepobj=null;
function changemfsel(){
	formm.pmpmanu.value = formm.manufselect.value;
	formm.pmppara.value =  formm.performanceparaselect.value ;
}
function changeperfsel(){
	formm.pmpmanu.value = formm.manufselect.value;
	formm.pmppara.value =  formm.performanceparaselect.value ;
}

function changemanufselect(){
  var manuselect=formm.manufselect;
  
  var manuf=manuselect.options[manuselect.selectedIndex].value;
  var ppselect=formm.performanceparaselect;
  
  var tboxLen = ppselect.options.length;
    for(i=0;i<tboxLen;i++)
    {
    	ppselect.options.remove(0);
    }
  if(manuf=='General'){
  
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifDiscards';
    	oOption.value = 'ifDiscards';
    	if('ifDiscards'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifErrors';
    	oOption.value = 'ifErrors';
    	if('ifErrors'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifHCPackets';
    	oOption.value = 'ifHCPackets';
    	if('ifHCPackets'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifPackets';
    	oOption.value = 'ifPackets';
    	if('ifPackets'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifUtilization';
    	oOption.value = 'ifUtilization';
    	if('ifUtilization'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifUtilizationHC';
    	oOption.value = 'ifUtilizationHC';
    	if('ifUtilizationHC'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifUtilizationHighSpeedHC';
    	oOption.value = 'ifUtilizationHighSpeedHC';
    	if('ifUtilizationHighSpeedHC'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'asdf';
    	oOption.value = 'asdf';
    	if('asdf'== '') oOption.selected=true;
	
  }else{
  
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifCRC';
    	oOption.value = 'ifCRC';
    	if('ifCRC'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifCollision';
    	oOption.value = 'ifCollision';
    	if('ifCollision'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifDrops';
    	oOption.value = 'ifDrops';
    	if('ifDrops'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifUtilization_F5_BIGIP';
    	oOption.value = 'ifUtilization_F5_BIGIP';
    	if('ifUtilization_F5_BIGIP'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifUtilization_F5_LB';
    	oOption.value = 'ifUtilization_F5_LB';
    	if('ifUtilization_F5_LB'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifUtils';
    	oOption.value = 'ifUtils';
    	if('ifUtils'== '') oOption.selected=true;
	
    var oOption = document.createElement("OPTION");
		ppselect.options.add(oOption);
		oOption.innerText = 'ifUtilsHC';
    	oOption.value = 'ifUtilsHC';
    	if('ifUtilsHC'== '') oOption.selected=true;
	
  }
  showselected();
}
function showselected(){
  var manuselect=formm.manufselect;
  var manuf=manuselect.options[manuselect.selectedIndex].value;
  
  var ppselect=formm.performanceparaselect;
  var para=ppselect.options[ppselect.selectedIndex].value;
  
  formm.pmppara.value=para;
  formm.pmpmanu.value=manuf;
  
}
function change_TrColor(obj) {
//	if(changepobj!=null)changepobj.runtimeStyle.backgroundColor="";
	if(changepobj=obj)changepobj.runtimeStyle.backgroundColor = "#9cd3ef";
}
function clear_TrColor(obj) {
	if(changepobj!=null)changepobj.runtimeStyle.backgroundColor="";
}

function openUrl(url,name,width,height,scrollbar) {
	window.open(url,name,'titlebar=no,toolbar=no,resizable=yes,width='+width+',height='+height+',scrollbars='+scrollbar);
}

function openModalDialog(url,width,height){
	showModalDialog(url,window,'edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:'+height+'px;dialogWidth:'+width+'px'); 
}
					
function del(){
   var vv=confirm("确认删除该条记录?");
   if(vv){
   document.formModify.action="_delete.jsp";
   document.formModify.submit();
   }
}

function onloaddo(){

 showselected();
 changemanufselect();
   var v="null";
   if(v=="false") alert("保存失败!OID值或序号或OID名称重复!");
   if(v!='null'){
    alert(v);
   }
  reloadparent();

}
function reloadparent(){
  if(window.opener.parent.ory_doc!=null){
  window.opener.parent.ory_doc.location.reload();
  }else{
  window.opener.location.reload();
  }
}
function isDigit(s)
{   
var patrn=/^[0-9]{1,20}$/;   
if (!patrn.exec(s)) return false  
return true  
}   
function showoid(opid,oidname,oidindex,oidvalue,oidname,oidunit,flag){
	formm.opid.value=opid;
  formm.oidname.value=oidname;
  formm.oidindex.value=oidindex;
  formm.oidvalue.value=oidvalue;
  formm.oidunit.value=oidunit;
  formm.showopid.value='update';
  formm.yuanoidname.value=oidname;
  
      if(flag=='0')
   {  formm.flag.checked=false;}
      else
    { formm.flag.checked=true;}
  
  if(oidunit=='BPS'){
     formm.checkbps.checked=true;
  }else{
  formm.checkbps.checked=false;
  }
 
}
function savedetails(){
 if(formm.dto.value == ''){
      alert('添加OID前请先建立OID Group');
      return;
  }
  if(!formm.checkbps.checked&&formm.oidunit.value=='BPS'){
     alert('必须检查单位是否是BPS!');
     return;
  }
  if(formm.checkbps.checked&&formm.oidunit.value!='BPS'){
     alert('单位必须为BPS!');
     return;
  }
   if(formm.oidname.value==''||formm.oidvalue.value==''||formm.oidunit.value==''||formm.oidindex.value==''){
     alert('必填项不能为空!');
     return ;
   }
   if(!checkobject()){
      alert("OID值不合法!不应包含字符");
      return ;
  }
  var oidindex=formm.oidindex.value;
  if(!isDigit(oidindex)&&oidindex!=''){
     alert('序号必须为数字!')
     return ;
  }
  
  if(confirm("确定保存OID?")){
    formm.action="<%=request.getContextPath() %>/secure/baseinfo/savenewlineportoiddetails.wss";
    formm.submit();
    }
}
  function clearfornew(){
	  document.getElementsByName("oidname").item(0).readOnly = false;
     formm.oidname.value='';
     formm.oidindex.value='';
  formm.oidvalue.value='';
  formm.oidunit.value='';
     formm.checkbps.checked=false;
     formm.showopid.value='insert';
  }
    function saveoidgroup(){
      
      if(confirm('确定保存该oidgroup?')){
        formm.action="<%=request.getContextPath() %>/secure/baseinfo/savenewlineportoid.wss";
        formm.submit();
      }

    }
function deleteoiddetails(){
    var ss=document.getElementsByName("oiddetailsid");
   var is=false;
   for(var i=0;i<ss.length;i++){
      if(ss[i].checked) {
      is=true;
      break;
      }
   }
   if(!is) {
    alert('请先选择OID信息!');
   return ;
   }
      if(confirm('确定删除所选OID?')){
         formm.action="<%=request.getContextPath() %>/secure/baseinfo/deletedevoiddetails.wss";
         formm.submit();
       }
    }
   function checkobject()
{
 var exp=formm.oidvalue.value;
    var bRt=true;
  if(exp==null) return true;
  if(exp.indexOf('..')>=0) return false;
  for(var i=0;i<exp.length;i++)
  {
    b = exp.charCodeAt(i);
    if(!((b>=48&&b<=57)||(b==46)))
    {
      bRt=false;
      break;
    }
  }
  return bRt;
}
</script>

<script type="text/javascript">
function addDevOid(){
	devoid.formAction.value= 'add';
	devoid.reset();
	devoid.gname.value="";
	devoid.supid.value ="${model.grpnet.supid}";
	devoid.level.value ="${model.grpnet.level}";
	
	devoid.action = "<%=request.getContextPath() %>/secure/baseinfo/savedevoid.wss";
	this.devoid.submit();
}
</script>

</head>
<body>

 

  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">基础信息</TD>
          <TD CLASS="pageClose"><a href="<%= request.getContextPath() %>/secure/baseinfo/linePortOid.wss"><img border="0" src="../../images/back.gif" width="16" height="16"></a></TD>
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
        
        <h1 id="title-bread-crumb">新建线路/端口 OID Group</h1>
        <p ></p>
<form action="" method="post" name="formm" >
<a name="main"></a>
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
   
    	</td>
		<td>
    	
    	</td>
        <input type="submit" name="hiddenButton1290018101908118000" value="hiddenButton" style="display:none" class="buttons" id="hiddenButton1290018101908118000"/>
    </tr></table>    
    <c:if test="${model.messageg != null &&  model.messageg != ''}">
									<div id="errmsg"><fmt:message>${model.messageg }</fmt:message></div>
    </c:if>
    <input type="hidden" name="definitionName" value="JDBCProvider.collection.buttons.panel"/>
    <input type="hidden" name="buttoncontextType" value="JDBCProvider"/>
    <input type="hidden" name="buttoncontextId" value="All Scopes"/>
    <input type="hidden" name="buttonperspective" value="null"/> 
     <input type="hidden" name="formAction" value="jDBCProviderCollection.do"/>  

        </td>
        </tr>
        </table>
 
        
        


		<input type="hidden" value="" name="dvid">
		<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">
		<tr><td  class="table-row"  nowrap>
		<br/>
		<fieldset>
		<legend>OID Group 新建</legend>
        <table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">
        <tr class="table-row"><td>
		<table  cellspacing="1" cellpadding="3" border="0" width="100%" class="framing-table">

			<tr class="table-row" >
					<td width="20%"  >OID Group 类型</td>

					<td  >&nbsp;&nbsp;线路/端口类</td>
			</tr>
			<tr class="table-row">
					<td width="20%"  class="tdlabel">厂商名</td>
					<td  class="tdcontent"><select name="manufselect" onChange="changemfsel()" id="formStyleblock">

					                <c:forEach items="${model.mflist}" var="theMf" >
					                	<c:choose >
					                <c:when test="${(theMf.mrname) == (model.pmpmanu) }" >					                
					                	<option value="${theMf.mrname }" selected="selected">${theMf.mrname }</option>
					               </c:when>
                                   
				                <c:otherwise >
					               		<option value="${theMf.mrname }" >${theMf.mrname }</option>
					               </c:otherwise>
					                </c:choose>
					                </c:forEach>
					                
					                       </select></td>
			</tr>
			<tr class="table-row">
					<td width="20%"  class="tdlabel">性能监控参数</td>
					<td  class="tdcontent">
					 <select name="performanceparaselect" onChange="changeperfsel()" id="formStyleblock">
					
					                     <c:forEach items="${model.perfparam}" var="p" >
					                              <c:choose >
					                     <c:when test="${(p.major) == (model.pmppara) }" >
					                               <option value="${p.major}" selected="selected" >${p.major}</option>
					                     </c:when>
					                     <c:otherwise>
					                     	<option value="${p.major}">${p.major} </option>
					                     </c:otherwise>
					                     </c:choose>
					                     </c:forEach>
					                       

					                      
	                  </select></td>
			</tr>
			<tr class="table-row" >
					<td width="20%"  class="tdlabel">OIDGroup名称
					  <input type="hidden" name="opid" value="${model.opid}" class="formStyleblock">
					  <input type="hidden" name="dto" value="${model.dto }"></td>
					<td  >&nbsp;
					<input type="text" name="pmppara" readonly value="${model.pmppara }">&nbsp;_&nbsp;
					<input type="text" name="pmpmanu" readonly value="${model.pmpmanu }">&nbsp;_&nbsp;
					<input type="text" name="pmptail" value="${model.pmptail}" class="formStyleblock">&nbsp;</td>
			</tr>
			<tr class="table-row" >

					<td width="20%"  class="tdlabel">&nbsp;</td>
					<td  >&nbsp;<input type="button" value="保存OID Group"  onclick="saveoidgroup()" class="buttons" id="functions" ><input type="hidden" name="oidgroupname" value="${model.dto.oidgroupname }"></td>
			</tr>
			
		</table></td></tr></table>
		</fieldset>
		
		<br/>
		
		<fieldset style="height: 80px">
    	<legend>OID</legend>
	<table  cellspacing="1" cellpadding="3" border="0" width="100%"  VALIGN="top"  class="framing-table">
	<tr class="table-row"><td>
		<table  cellspacing="1" cellpadding="0" border="0" width="100%"  VALIGN="top"  class="collection-table-text">
            <c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
	        </c:if>
			<tr class="table-row" >
					<td width="20%"  class="tdlabel">OID值<input type="hidden" name="showopid" value="insert" class="formStyleblock"></td>
					<td  class="tdcontent"><input type="text" name="oidvalue" value="" class="formStyleblock" maxlength="255">* 必填</td>
			</tr>
			<tr class="table-row" >
					<td width="20%"  class="tdlabel">OID名称<input type="hidden" name="yuanoidname" value="" class="formStyleblock"></td>
					<td  class="tdcontent"><input type="text" name="oidname" value="" class="formStyleblock" maxlength="200">* 必填</td>

			</tr>
			<tr class="table-row" >
					<td width="20%"  class="tdlabel">OID单位</td>
					<td  class="tdcontent"><input type="text" name="oidunit" value="" class="formStyleblock" maxlength="32">* 必填</td>
			</tr>
			<tr class="table-row"  >
					<td width="20%"  class="tdlabel">序号</td>

					<td  class="tdcontent"><input type="text" name="oidindex" value="" class="formStyleblock">* 必填&nbsp;</td>
			</tr>
			<tr class="table-row" >
					<td width="20%"  class="tdlabel">&nbsp;</td>
					<td  class="tdcontent"><input type="checkbox" name="checkbps" value="0">检验单位是否为“BPS”</td>
			</tr>
			<tr class="table-row" >
					<td width="20%"  class="tdlabel">&nbsp;</td>

					<td  class="tdcontent"><input type="checkbox" name="flag" value="0">打勾则OID值加端口Index值</td>
			</tr>
			<tr class="table-row" >
					<td width="20%"  class="tdlabel">&nbsp;</td>
					<td  class="tdcontent"><input type="button" name="new" value="新建" class="buttons" id="functions"  onClick="clearfornew()" >
					<input type="button" value="保存OID" onClick="savedetails()" class="buttons" id="functions" ></td>
			</tr>
		</table></td></tr></table>
		</fieldset>
		
		<br/>
		<fieldset >
    	<legend>OID详细信息</legend>
		<table class="framing-table" border="0" bordercolor="#999999" cellpadding="3" cellspacing="1" width="100%">
		<tr><td class="table-row">
		<table  cellspacing="1" cellpadding="0"
			border="0" width="100%" class="framing-table">
			<tr valign="bottom" >
								<th class="column-head-name">编辑/ 删除</th>
								
								<th class="column-head-name">OID值</th>

								<th class="column-head-name">OID名称</th>
								<th class="column-head-name">OID单位</th>
								<th class="column-head-name">序号</th>
			</tr>
			<c:forEach items="${model.details}" var="theO" >
				<tr class="table-row" >
					
					<td nowrap="nowrap">
					<a href="#" onClick="showoid('${theO.opid}','${theO.oidname}', '${theO.oidindex}','${theO.oidvalue}','${theO.oidname}','${theO.oidunit}','${theO.flag}' );">
                    <img border="0" src="../../images/wtable_edit_sorts.gif" width="16" height="16" alt="编辑"></a>
                    &nbsp;
					<c:url value="/secure/baseinfo/deletelineportoidoid.wss" var="myurl"><c:param name="opid" value="${theO.opid}"/><c:param name="oidname" value="${theO.oidname}"/><c:param name="oidunit" value="${theO.oidunit}"/><c:param name="oidindex" value="${theO.oidindex}"/></c:url><a href="${myurl }" ><img border="0" src="../../images/delete.gif" width="16" height="16" alt="删除"></a> </td>

					<td nowrap="nowrap">${theO.oidvalue }&nbsp;</td>
					<td nowrap="nowrap">${theO.oidname }&nbsp;</td>
					<td nowrap="nowrap">${theO.oidunit }&nbsp;</td>
					<td nowrap="nowrap">${theO.oidindex }&nbsp;</td>
				</tr>
			</c:forEach>
	
			
		</table>
		<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
            <TD CLASS="table-totals" VALIGN="baseline">  
             &nbsp;&nbsp;&nbsp;               
		</TD>
	</TR>

	</TABLE>
		</td></tr></table>
		</fieldset>
	
     
        <br/>
	</td></tr></table>
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