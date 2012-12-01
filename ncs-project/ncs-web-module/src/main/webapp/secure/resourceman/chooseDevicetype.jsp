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
function choise(obj){
  // alert(obj.rowIndex);
   var cell=obj.cells;
 // alert("all is invid"+window.opener.document.all)
  // alert(cell[0].innerText+cell[1].innerText+cell[2].innerText+cell[3].innerText+cell[4].innerText);
   window.opener.document.all.dtid.value=cell[0].innerText; //dtid
   
   window.opener.document.all.mf.value=cell[1].innerText; //mf
   
   window.opener.document.all.devtype.value=cell[2].innerText; //dev type
   
   window.opener.document.all.subtype.value=cell[3].innerText; //subtype

   window.opener.document.all.model.value=cell[4].innerText; //model
   
   window.opener.document.all.oid.value=cell[5].innerText;  //objectid
   
   window.close();
}


function sub(dt,s2,s3,s4,s5,s6,s7,s8,s9,s10){
	// dt =dtid;
	
	
	cdevice.dtid.value=dt;
	cdevice.mrid.value = s2;
	cdevice.manufacture.value=s3;
	cdevice.devtype.value = s4;
	cdevice.devsubtype.value = s5;
	cdevice.model.value = s6;
	cdevice.objectid.value = s7;
	cdevice.action = "<%=request.getContextPath() %>/secure/resourceman/toEditDeviceInfo.wss?";
	//alert("inChooseDeviceType.jsp:cdevice.action=" + cdevice.action);
	
	cdevice.gid.value = s8;
	cdevice.supid.value = s9;
	cdevice.level.value= s10;
	
	cdevice.submit();
}
</script>
</head>
<body>


  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">资源管理</TD>
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
 
  
  <TBODY ID="wasUniPortlet">
    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">设备类型列表</h1>
        <p ></p>

<a name="main"></a>
<form action="" name="cdevice" method="get">
<input type="hidden" name="devid" value="${ model.form.devid}">
<input type="hidden" name="devname" value="${model.form.devname }">
<input type="hidden" name="devalias" value="${model.form.devalias }">
<input type="hidden" name="devip" value="${model.form.devip }">
<input type="hidden" name="resnum" value="${model.form.resnum }">
<input type="hidden" name="providerphone" value="${model.form.providerphone }">
<input type="hidden" name="devtype" value="${model.form.devtype }">
<input type="hidden" name="devsubtype" value="${model.form.devsubtype }">
<input type="hidden" name="model" value="${model.form.model }">
<input type="hidden" name="manufacture" value="${model.form.manufacture }">
<input type="hidden" name="objectid" value="${model.form.objectid }">
<input type="hidden" name="snmpversion" value="${model.form.snmpversion }">
<input type="hidden" name="rcommunity" value="${model.form.rcommunity }">
<input type="hidden" name="adminName" value="${model.form.adminName}">
<input type="hidden" name="adminPhone" value="${model.form.adminPhone }">
<input type="hidden" name="devpolicy" value="${model.form.devpolicy }">
<input type="hidden" name="timeframPolicy" value="${model.form.timeframPolicy }">
<input type="hidden" name="devSoftwareVer" value="${model.form.devSoftwareVer }">
<input type="hidden" name="devSerialNum" value="${model.form.devSerialNum }">
<input type="hidden" name="devgroup" value="${model.form.devgroup }">
<input type="hidden" name="ramsize" value="${model.form.ramsize }">
<input type="hidden" name="nvramsize" value="${model.form.nvramsize }">
<input type="hidden" name="flashSize" value="${model.form.flashSize }">
<input type="hidden" name="description" value="${model.form.description }">
<input type="hidden" name="dtid" value=""/>
<input type="hidden" name="mrid" value=""/>

<input type="hidden" name="gid" value="${model.gid}"/>
<input type="hidden" name="supid" value="${model.supid}"/>
<input type="hidden" name="level" value="${model.level}"/>

<input type="hidden" name="formAction" value="${model.formAction}"/>



<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    

     
        </td>
        </tr>
        </table>
 
        
        
<!-- Data Table -->

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table">

	<TBODY>
		<TR>
			<TD CLASS="layout-manager" id="notabs">


<table BORDER="0" CELLPADDING="3" CELLSPACING="1" WIDTH="100%" SUMMARY="List table" CLASS="framing-table">
<tr>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >选择</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >厂商ID</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >厂商名</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >类型</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >子类型</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >型号</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >ObjectID</th>
</tr>

<c:forEach items="${model.devicetype}" var="c1" >
<tr class="table-row" ondbclick="choise(this);">
<td VALIGN="middle"  class="collection-table-text" >
<img border="0" src="../../images/wtable_previous.gif" onClick="sub('${c1.dtid}','${c1.mrid}','${c1.mrName}','${c1.cateName}','${c1.subCategory}','${c1.model}','${c1.objectid}','${model.gid}','${model.supid}','${model.level}');"><!--<c:out value="${c1.dtid }"/>--></td>
<td VALIGN="top"  class="collection-table-text" headers="mf"><c:out value="${c1.mrid}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="mrname"><c:out value="${c1.mrName}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="catename"><c:out value="${c1.cateName}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="subcategory"><c:out value="${c1.subCategory}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="model"><c:out value="${c1.model}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="objectid"><c:out value="${c1.objectid}"/></td>
</tr>
</c:forEach>
</table>

<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
            Total ${fn:length(model.devicetype)}
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>


</TD></TR></TBODY></TABLE>
</form>
</TD></TR></TBODY></TABLE>

</TD></TR></TABLE>

</body>
</html>