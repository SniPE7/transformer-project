<%@ include file="/include/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<html>
<head>

<link href='<%=request.getContextPath()%>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath()%>/login.css' rel="styleSheet" type="text/css">
<title>baseinfonavi</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<script type="text/javascript" language="JavaScript">
function changenode(){
  form1.action="<%=request.getContextPath()%>/secure/policyapply/policyApply.wss";
  this.form1.submit();
}
function changedev(selectdev){
  
  form1.action="<%=request.getContextPath()%>/secure/policyapply/policyApply.wss";
  this.form1.submit();
}
</script>

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



<script type="text/javascript" language="javascript">
		function submitForm(buttonId) {
		
			switch(buttonId) {
				case 'test':
					setSelected();
					
				break;
			}
			form1.action="<%=request.getContextPath()%>/secure/policyapply/policyApply.wss";
		}
		
		function ck(aform) {
			if(aform.viewgroup.value=='') {
				alert('请选择查看器组');
				return false;
			}
			if(aform.name.value=='') {
				alert('请填写查看器名称');
				return false;
			}
			if(aform.selecting.options.length <= 1) {
				alert("请选择要查看的表列");
				return false;
			}
			return true;
		}
		

		
		function setSelected() {
			
			for(i=0; i<form1.unselected.options.length; i++) {
				form1.unselected.options[i].selected = true;
			}
			
			for(i=0; i<form1.selecting.options.length; i++) {
				form1.selecting.options[i].selected = true;
			}
			
		}
		
		//option.value is "name@title@width@index"
		
		function add() {
		/*
			len1 = form1.unselected.options.length;
			len2 = form1.selecting.options.length;
			len3 = form1.unselected.options.selectedIndex;
			if(len3 >= 0) {
				form1.selecting.options[len2] = new Option();
				form1.selecting.options[len2].text = form1.unselected.options[len3].text;
				
				tmp_value = form1.unselected.options[len3].value;
			
				form1.selecting.options[len2].value = tmp_value;
				
				form1.unselected.options[len3] = null;
				if(form1.unselected.options.length > len3) 
					form1.unselected.options[len3].selected = true;
				else 
					form1.unselected.options[form1.unselected.options.length-1].selected = true;
			}*/
			len1 = form1.unselected.options.length;
			len2 = form1.selecting.options.length;
			len3 = form1.unselected.options.selectedIndex;
			for (var i=len1-1;i>=0;i--){
			if (form1.unselected.options[i].selected){
			
				form1.selecting.options[len2] = new Option();
				form1.selecting.options[len2].text = form1.unselected.options[i].text;
				
				tmp_value = form1.unselected.options[i].value;
			
				form1.selecting.options[len2].value = tmp_value;
				
				form1.unselected.options[i] = null;
				len2++;
			}	}
		
		}
		
		function addall() {
			len1 = form1.unselected.options.length;
			len2 = form1.selecting.options.length;
			for(i=0; i<len1; i++) {
				form1.selecting.options[len2+i] = new Option();
				form1.selecting.options[len2+i].text = form1.unselected.options[i].text;
				
				tmp_value = form1.unselected.options[i].value;
				
				
				form1.selecting.options[len2+i].value = tmp_value;
			}
			for(i=0; i<len1; i++) {
				form1.unselected.options[0] = null;
			}
		}
		
		function remove() {
			len1 = form1.unselected.options.length;
			len2 = form1.selecting.options.length;
			len3 = form1.selecting.options.selectedIndex;
			for (var a=len2-1;a>=0;a--){
				if (form1.selecting.options[a].selected){
				str = form1.selecting.options[a].text;
				for(i=0; i<len1; i++) {
					if(str < form1.unselected.options[i].text) {
						break;
					}
				}
				form1.unselected.options[len1] = new Option();
				for(j=len1; j>i; j--) {
					form1.unselected.options[j].value = form1.unselected.options[j-1].value;
					form1.unselected.options[j].text  = form1.unselected.options[j-1].text;
				}
				form1.unselected.options[i].text  = form1.selecting.options[a].text;
				
				//赋值,同时设置index为0,也即将Viewfield表中的索引号置为0
				tmp_value = form1.selecting.options[a].value;
				
				form1.unselected.options[i].value =tmp_value;
				
				//在selected中,将所选择条目后面的所有条目的value值中的index值减1
				for(k=a+1; k<len2; k++) {
					tmp_value = form1.selecting.options[k].value;
					
					form1.selecting.options[k].value = tmp_value;
				}
				
				form1.selecting.options[a] = null;
				len1++;
				len2--;
				/*if(form1.selecting.options.length > len3)
					form1.selecting.options[len3].selected = true;
				else 
					form1.selecting.options[form1.selecting.options.length-1].selected = true;*/
			}}
		}
		
		function removeall() {
			while(form1.selecting.options.length >= 0) {
				for(i=0; i<form1.unselected.options.length; i++) {
					if(form1.unselected.options[i].text > form1.selecting.options[0].text)
						break;
				}
				form1.unselected.options[form1.unselected.options.length] = new Option();
				for(j=form1.unselected.options.length-1; j>i; j--) {
					form1.unselected.options[j].value = form1.unselected.options[j-1].value;
					form1.unselected.options[j].text  = form1.unselected.options[j-1].text;
				}
				form1.unselected.options[i].value = form1.selecting.options[0].value;
				form1.unselected.options[i].text  = form1.selecting.options[0].text;
				
				form1.selecting.options[0] = null;
			}
			 
			//将unselected中的所有条目的索引置为0
			for(i=0; i<form1.unselected.options.length; i++) {
				tmp_value = form1.unselected.options[i].value;
				
				form1.unselected.options[i].value = tmp_value;
			}
		}
		
		function toTop() {
			len1 = form1.selecting.options.selectedIndex;
			if(len1 > 0) {
				tmp_text = form1.selecting.options[len1].text;
				tmp_value= form1.selecting.options[len1].value;
				form1.selecting.options[len1] = null;
				form1.selecting.options[form1.selecting.options.length] = new Option();
				for(i=form1.selecting.options.length-1; i>0; i--) {
					form1.selecting.options[i].text = form1.selecting.options[i-1].text;
					form1.selecting.options[i].value= form1.selecting.options[i-1].value;
				}
				form1.selecting.options[1].text = tmp_text;
				form1.selecting.options[1].value= tmp_value;
				form1.selecting.options[1].selected = true;
			}
			
			for(j=1; j<form1.selecting.options.length; j++) {
				tmp_value = form1.selecting.options[j].value;
				
				form1.selecting.options[j].value = tmp_value;
			}
		}
		
		function toBottom() {
			len1 = form1.selecting.options.selectedIndex;
			if(len1>0) {
				tmp_text = form1.selecting.options[len1].text;
				tmp_value= form1.selecting.options[len1].value;
				form1.selecting.options[len1] = null;
				form1.selecting.options[form1.selecting.options.length] = new Option();
				form1.selecting.options[form1.selecting.options.length-1].text = tmp_text;
				form1.selecting.options[form1.selecting.options.length-1].value= tmp_value;
				form1.selecting.options[form1.selecting.options.length-1].selected = true;
			}
			
			for(j=1; j<form1.selecting.options.length; j++) {
				tmp_value = form1.selecting.options[j].value;
				
				form1.selecting.options[j].value = tmp_value;
			}
		}
		
		function toUp() {
			len1 = form1.selecting.options.selectedIndex;
			if(len1 > 1) {
				tmp_text = form1.selecting.options[len1].text;
				
				tmp_value= form1.selecting.options[len1].value;
				
				
				tmp2_value= form1.selecting.options[len1-1].value;
				
				
				form1.selecting.options[len1].text = form1.selecting.options[len1-1].text;
				form1.selecting.options[len1].value = tmp2_value;
				form1.selecting.options[len1-1].text = tmp_text;
				form1.selecting.options[len1-1].value = tmp_value;
				form1.selecting.options[len1-1].selected = true;
			}
		}
		
		function toDown() {
			len1 = form1.selecting.options.selectedIndex;
			if(form1.selecting.options.length > 1) {
				if(len1 < form1.selecting.options.length-1) {
					tmp_text = form1.selecting.options[len1].text;
					
					tmp_value= form1.selecting.options[len1].value;
					
					tmp2_value= form1.selecting.options[len1+1].value;
					
					form1.selecting.options[len1].text = form1.selecting.options[len1+1].text;
					form1.selecting.options[len1].value= tmp2_value;
					form1.selecting.options[len1+1].text = tmp_text;
					form1.selecting.options[len1+1].value= tmp_value;
					form1.selecting.options[len1+1].selected = true;
				}
			}
		}
		
		
		function showit(selectele){
		//alert(selectele.options[selectele.options.selectedIndex].text);
		show.innerText=selectele.options[selectele.options.selectedIndex].text;
		}
		
		
	</script>

<%
	int nodei = 0;
%>


<body class="navtree" style="background-color: #FFFFFF;" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<p></p>

	<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
		<TR>
			<TD CLASS="pageTitle">策略管理</TD>
			<TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
		</TR>

	</TABLE>
	<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
		<TR>



			<TD valign="top">

				<TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

					<TR>
						<TH class="wpsPortletTitle" width="100%">策略应用</TH>

						<TH class="wpsPortletTitleControls"></TH>
						<TH class="wpsPortletTitleControls"></TH>
					</TR>



					<TBODY ID="wasUniPortlet">
						<TR>
							<TD CLASS="wpsPortletArea" COLSPAN="3"><a name="important"></a>

								<h1 id="title-bread-crumb">策略应用</h1>
								<h3 id="title-bread-crumb">
									<c:choose>

										<c:when test="${(model.cate=='4')}">
				端口策略：
				</c:when>

										<c:when test="${ (model.cate=='9')}">
				私有MIB策略：
				</c:when>
										<c:when test="${ (model.cate=='16') }">
				时间段策略：
				</c:when>

										<c:otherwise>
				设备策略：		
				</c:otherwise>
									</c:choose>

									<input type="hidden" name="mpname" value="${model.mpname}" />${model.mpname}
								</h3> <br />

								<form id="form1" method="post" action="">
									<!-- node and device info selection field -->


									<table BORDER="0" CELLPADDING="1" CELLSPACING="1" WIDTH="100%" SUMMARY="List table" CLASS="framing-table">

										<tbody>

											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
											<tr>
												<!-- select from node -->
												<td class="column-head-name" width="10%">节点</td>
												<td class="column-head-name"><select name="selectNode" onChange="changenode();" style="width: 98%">
														<option value="0" selected="selected">--请选择组--</option>
														<!-- zero level -->
														<c:forEach items="${model.rootlist}" var="theRoot">
															<c:forEach items="${model.ipnettree[theRoot]}" var="theL0">
																<option value="${model.names[theL0.key].gid}" <c:if test="${model.selectNode==model.names[theL0.key].gid}">selected="selected"</c:if>>
																	${model.names[theL0.key].gname}</option>

																<c:forEach items="${model.ipnettree[theL0.key]}" var="theL1">
																	<c:choose>
																		<c:when test="${fn:length(model.ipnettree[theL0.key]) >1}">

																			<option value="${model.names[theL1.key].gid}" <c:if test="${model.selectNode==model.names[theL1.key].gid}">selected="selected"</c:if>>
																				&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
																				<c:choose>
																					<c:when test="${model.names[theL1.key].unmallocedipsetflag eq 0}">
						${model.names[theL1.key].gname}</c:when>
																					<c:when test="${model.names[theL1.key].unmallocedipsetflag eq 1}">
						未分类</c:when>
																				</c:choose>
																			</option>

																			<!-- L2 more levels  -->
																			<c:forEach items="${model.ipnettree[theL1.key]}" var="theL2">
																				<c:choose>
																					<c:when test="${fn:length(model.ipnettree[theL1.key]) >1}">

																						<option value="${model.names[theL2.key].gid}" <c:if test="${model.selectNode==model.names[theL2.key].gid}">selected="selected"</c:if>>
																							&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
																							<c:choose>
																								<c:when test="${model.names[theL2.key].unmallocedipsetflag eq 0}">
						${model.names[theL2.key].gname}</c:when>
																								<c:when test="${model.names[theL2.key].unmallocedipsetflag eq 1}">
						未分类</c:when>
																							</c:choose>
																						</option>


																						<c:forEach items="${model.ipnettree[theL2.key]}" var="theL3">
																							<c:choose>
																								<c:when test="${fn:length(model.ipnettree[theL2.key]) >1}">

																									<option value="${model.names[theL3.key].gid}" <c:if test="${model.selectNode==model.names[theL3.key].gid}">selected="selected"</c:if>>
																										&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																										<c:choose>
																											<c:when test="${model.names[theL3.key].unmallocedipsetflag eq 0}">
						${model.names[theL3.key].gname}</c:when>
																											<c:when test="${model.names[theL3.key].unmallocedipsetflag eq 1}">
						未分类</c:when>
																										</c:choose>
																									</option>

																									<c:forEach items="${model.ipnettree[theL3.key]}" var="theL4">
																										<c:choose>
																											<c:when test="${fn:length(model.ipnettree[theL3.key]) >1}">

																												<option value="${model.names[theL4.key].gid}" <c:if test="${model.selectNode==model.names[theL4.key].gid}">selected="selected"</c:if>>
																													&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
																													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																													<c:choose>
																														<c:when test="${model.names[theL4.key].unmallocedipsetflag eq 0}">
						${model.names[theL4.key].gname}</c:when>
																														<c:when test="${model.names[theL4.key].unmallocedipsetflag eq 1}">
						未分类</c:when>
																													</c:choose>
																												</option>

																												<c:forEach items="${model.ipnettree[theL4.key]}" var="theL5">
																													<c:choose>
																														<c:when test="${fn:length(model.ipnettree[theL4.key]) >1}">
																															<c:if test="${fn:length(model.ipnettree[theL5.key]) > 0}">

																																<option value="${model.names[theL5.key].gid}" <c:if test="${model.selectNode==model.names[theL5.key].gid}">selected="selected"</c:if>>
																																	&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
																																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																																	<c:choose>
																																		<c:when test="${model.names[theL5.key].unmallocedipsetflag eq 0}">
						${model.names[theL5.key].gname}</c:when>
																																		<c:when test="${model.names[theL5.key].unmallocedipsetflag eq 1}">
						未分类</c:when>
																																	</c:choose>
																																</option>

																															</c:if>
																														</c:when>
																													</c:choose>
																												</c:forEach>
																												<!-- L5 more levels end -->
																											</c:when>
																										</c:choose>
																									</c:forEach>
																									<!-- L4 more levels end -->
																								</c:when>
																							</c:choose>
																						</c:forEach>
																						<!-- L3 more levels end -->
																					</c:when>
																				</c:choose>
																			</c:forEach>
																			<!-- L2 more levels end -->
																		</c:when>
																	</c:choose>
																</c:forEach>


															</c:forEach>

														</c:forEach>

												</select></td>
												<td class="column-head-name" width="10%">&nbsp;<input id="btnselgip" type="submit" name="_target0" value=">>" class="buttons" id="functions">&nbsp;
												</td>

											</tr>
											<tr>
												<td colspan="3" class="column-head-name">&nbsp;</td>
											</tr>
											<!-- select from device of the node -->

											<c:if test="${model.dispdevinfo==true }">
												<tr>
													<td class="column-head-name" valign="top">设备 <br /> <input type="text" id="categorypatterns" name="category_patterns" value="filter"
														style="width: 80px; color: gray" onclick="this.value='';" onKeyPress="return;" onMouseUp="return;" onkeyup="filtery(this.value,selectDevice);"
														onchange="filtery(this.value,selectDevice);"></td>
													<td class="column-head-name"><select name="selectDevice" style="width: 98%" size="5" onchange="changedev(this);">
															<option value="0" selected="selected">--请选择设备--</option>

															<c:forEach items="${model.devicetmp}" var="theIp">
																<option value="${theIp.devid}" <c:if test="${model.selectDevice==theIp.devid }">selected="selected"</c:if> title="${theIp.sysname}(	...${theIp.devid})">
																	${theIp.sysname}</option>
															</c:forEach>


													</select></td>
													<td class="column-head-name">&nbsp;<input id="btnseldevice" type="submit" name="_target1" value=">>" class="buttons" id="functions">&nbsp;
													</td>

												</tr>
											</c:if>
											<tr>
												<td class="column-head-name"><div id="dev" style="color: blue">&nbsp;</div></td>
												<td class="column-head-name" align="center">&nbsp;
													<div id="show" style="color: blue">&nbsp;</div> <input type="hidden" name="cate" value="${model.cate}"> <input type="hidden" name="mpid" value="${model.mpid}">
													<input type="hidden" name="ppid" value="${model.ppid}"> <!-- <input type="hidden" name="selectDevice1" value="${model.selectDevice }">-->

												</td>
												<td class="column-head-name">&nbsp;</td>
											</tr>
										</tbody>
									</table>

									<!-- dual selection field -->

									<c:if test="${model.dispdualselect==true}">
										<table border="0" width="100%" class="framing-table">
											<tbody>
												<!--<tr>
            <td width="40%" class="table-row" align="center">
            	Total Selected ${fn:length(model.portinfo)}
            </td>
            <td width="40%" class="table-row">
            </td>
            <td width="40%" class="table-row">portinfoed
            </td>
        </tr>-->
												<tr>
													<td width="45%" class="table-row" align="center"><select name="unselected" size="18" multiple="multiple" style="height: 245px; width: 98%"
														onChange="showit(this);">

															<c:choose>

																<c:when test="${ (model.cate=='4')}">
																	<c:forEach items="${model.portinfo}" var="theport">
																		<option value="${theport.ptid }">
																			${theport.ifdescr}
																			<c:if test="${model.portpolicyapplied[theport.ptid] != null}">&nbsp;(${model.portpolicyapplied[theport.ptid]})</c:if>
																		</option>
																	</c:forEach>
																</c:when>
																<c:when test="${ (model.cate=='9')}">
																	<c:forEach items="${model.pdminfo}" var="thepdm">
																		<option value="${thepdm.pdmid }">
																			${thepdm.oidindex}=${thepdm.oidname}
																			<c:if test="${model.pdmspolicyapplied[thepdm.pdmid] != null}">&nbsp;(${model.pdmspolicyapplied[thepdm.pdmid]})</c:if>
																		</option>
																	</c:forEach>
																</c:when>

																<c:when test="${ (model.cate=='16') }">
																	<c:forEach items="${model.portinfo}" var="theport">
																		<option value="port_${theport.ptid }">
																			端口 ${theport.ifdescr}
																			<c:if test="${model.portpolicyapplied[theport.ptid ] != null}">&nbsp;(${model.portpolicyapplied[theport.ptid]})</c:if>
																		</option>
																	</c:forEach>
																	<c:forEach items="${model.pdminfo}" var="thepdm">
																		<option value="pdm_${thepdm.pdmid }">
																			私有index ${thepdm.oidindex}=${thepdm.oidname}
																			<c:if test="${model.pdmspolicyapplied[thepdm.pdmid] != null}">&nbsp;(${model.pdmspolicyapplied[thepdm.pdmid]})</c:if>
																		</option>
																	</c:forEach>
																	<c:forEach items="${model.deviceinfo}" var="device0">
																		<option value="device_${device0.devid }">
																			设备 ${device0.devip}_${device0.sysname }
																			<c:if test="${model.devicepolicyapplied[device0.devid] != null}">&nbsp;(${model.devicepolicyapplied[device0.devid]})</c:if>
																		</option>
																	</c:forEach>

																</c:when>


																<c:otherwise>
																	<c:forEach items="${model.deviceinfo}" var="device0">
																		<option value="${device0.devid }">
																			${device0.devip}_${device0.sysname }
																			<c:if test="${model.devicepolicyapplied[device0.devid] != null}">&nbsp;(${model.devicepolicyapplied[device0.devid]})</c:if>
																		</option>
																	</c:forEach>
																</c:otherwise>
															</c:choose>

													</select></td>
													<td width="10%" class="table-row">
														<table border="0" width="100%" cellspacing="5" cellpadding="5">
															<tbody>
																<tr>
																	<td></td>
																</tr>
																<tr>
																	<td class="buttons"><a href="javascript:add();">添加[>]</a></td>
																</tr>
																<tr>
																	<td class="buttons"><a href="javascript:remove();">移除[<]</a></td>
																</tr>
																<tr>
																	<td>&nbsp;</td>
																</tr>
																<tr>
																	<td class="buttons"><a href="javascript:addall();">添加所有[>>]</a></td>
																</tr>
																<tr>
																	<td class="buttons"><a href="javascript:removeall();">移除所有[<<]</a></td>
																</tr>
																<tr>
																	<td></td>
																</tr>
															</tbody>
														</table>
													</td>
													<td width="45%" class="table-row" align="center"><select name="selecting" size="18" multiple="multiple" style="height: 245px; width: 98%">
															<c:choose>

																<c:when test="${ (model.cate=='4') }">
																	<c:forEach items="${model.portinfoed}" var="theport">
																		<option value="${theport.ptid }">${theport.ifdescr}</option>
																	</c:forEach>
																</c:when>
																<c:when test="${ (model.cate=='9')}">
																	<c:forEach items="${model.pdminfoed}" var="thepdm">
																		<option value="${thepdm.pdmid }">${thepdm.oidindex}=${thepdm.oidname}</option>
																	</c:forEach>
																</c:when>


																<c:when test="${ (model.cate=='16') }">
																	<c:forEach items="${model.portinfoed}" var="theport">
																		<option value="port_${theport.ptid }">端口 ${theport.ifdescr}</option>
																	</c:forEach>
																	<c:forEach items="${model.pdminfoed}" var="thepdm">
																		<option value="pdm_${thepdm.pdmid }">私有index ${thepdm.oidindex}=${thepdm.oidname}</option>
																	</c:forEach>
																	<c:forEach items="${model.deviceinfoed}" var="device0">
																		<option value="device_${device0.devid }">设备 ${device0.devip}_${device0.sysname }</option>
																	</c:forEach>

																</c:when>

																<c:otherwise>
																	<c:forEach items="${model.deviceinfoed}" var="device0">
																		<option value="${device0.devid }">${device0.devip}_${device0.sysname }</option>
																	</c:forEach>
																</c:otherwise>
															</c:choose>

													</select></td>

												</tr>
												<tr class="paging-table" align="center">

													<TD CLASS="table-totals" VALIGN="baseline" align="left"><c:choose>
															<c:when test="${ (model.cate=='4')  || (model.cate=='9')}">
                可选端口总数 ${fn:length(model.portinfo)}
				</c:when>
															<c:when test="${ (model.cate=='16') }">
                可选端口总数 ${fn:length(model.portinfo)}<br />
                可选设备总数 ${fn:length(model.deviceinfo)}	
				</c:when>
															<c:otherwise>
                可选设备总数 ${fn:length(model.deviceinfo)}		
				</c:otherwise>
														</c:choose></TD>

													<td></td>

													<TD CLASS="table-totals" VALIGN="baseline" align="left"><c:choose>
															<c:when test="${ (model.cate=='4')  || (model.cate=='9')}">
                已选端口总数 ${fn:length(model.portinfoed)}
				</c:when>

															<c:when test="${ (model.cate=='16') }">
                 已选端口总数 ${fn:length(model.portinfoed)}<br />
                已选设备总数 ${fn:length(model.deviceinfoed)}		
				</c:when>

															<c:otherwise>
                已选设备总数 ${fn:length(model.deviceinfoed)}			
				</c:otherwise>
														</c:choose></td>
												</tr>
												<tr>
													<td colspan="4"></td>
												</tr>
											</tbody>
										</table>



										<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

											<TR>


												<TD CLASS="table-totals" VALIGN="baseline" align="center"><input type="submit" name="ok" onClick="javascript:submitForm('test');" value="  确认  " class="buttons"
													id="functions" /> &nbsp;&nbsp;</TD>
											</TR>

										</TABLE>
									</c:if>
								</form></TD>
						</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
	</TABLE>


</body>
</html>