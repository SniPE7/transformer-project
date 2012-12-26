<%@ include file="/include/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<html>
<head>
<link href='<%=request.getContextPath()%>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath()%>/login.css' rel="styleSheet" type="text/css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/jquery-ui-1.9.2.custom/css/ui-lightness/jquery-ui-1.9.2.custom.min.css" />

<title>baseinfonavi</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<!-- list of menu -->

<script language="JavaScript" type="text/javascript">

function policydetails9(){
	
	///alert("in policyDefinition details**form.action" + form1.action);
	document.getElementsByName("formAction").item(0).value= "bind";
	form1.formAction.value = "bind";
	//alert("1: form1.bind_btn.type: " + form1.bind_btn.type);
	//alert("in policyDefinition.jsp. testing param mode=icmp & mpname=" + form1.mpname.value);
	form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policydetailsPDM.wss";
	this.form1.submit();
}
function policydetails(){
	
	///alert("in policyDefinition details**form.action" + form1.action);
	document.getElementsByName("formAction").item(0).value= "bind";
	form1.formAction.value = "bind";
	//alert("1: form1.bind_btn.type: " + form1.bind_btn.type);
	//alert("in policyDefinition.jsp. testing param mode=icmp & mpname=" + form1.mpname.value);
	form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policydetails.wss?mode=ICMP";
	this.form1.submit();
}
function addform1(){	

//alert("in policyDefinition addform**form.action" + form1.action);
	document.getElementsByName("mpname").item(0).value = "";
	document.getElementsByName("mpid").item(0).value = "";
	document.getElementsByName("description").item(0).value = "";
	//disable "New" button
	document.getElementsByName("button_new").item(0).disabled = true;	
	document.getElementsByName("button_bind").item(0).disabled = true;
}


function search(){
	  if(document.getElementsByName("button_new").item(0).disabled == true){
	    document.getElementsByName("formAction").item(0).value= "add";
	    form1.formAction.value = "add";
	  }else{
	    form1.formAction.value = "save";
	    document.getElementsByName("formAction").item(0).value= "save";
	  }
	  document.getElementsByName("insertOrUpdate").item(0).value="true";
	  document.getElementsByName("searchMode").item(0).value="true";

	  for (var i = 0; i < document.getElementsByName("selected_device_type_list").item(0).options.length; i++)  {
	    document.getElementsByName("selected_device_type_list").item(0).options[i].selected = true;
	  }
	  form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policyDefinition.wss";
	    document.getElementsByName("button_new").item(0).disabled = false;
	  this.form1.submit();
}

function saveform1(){
	if(document.getElementsByName("button_new").item(0).disabled == true){
		document.getElementsByName("formAction").item(0).value= "add";
		form1.formAction.value = "add";
	}else{
		form1.formAction.value = "save";
		document.getElementsByName("formAction").item(0).value= "save";
	}
	
	if(document.getElementsByName("mpname").item(0).value == ""){
		alert("策略名称不能为空！");
		if(form1.formAction.value == "add"){
			document.getElementsByName("mpname").item(0).value = "";
			document.getElementsByName("mpid").item(0).value = "";
			document.getElementsByName("description").item(0).value = "";
			//disable "New" button
			document.getElementsByName("button_new").item(0).disabled = true;	
			document.getElementsByName("button_bind").item(0).disabled = true;
		}
		return false;
	}
	document.getElementsByName("insertOrUpdate").item(0).value="true";
  document.getElementsByName("searchMode").item(0).value="";

	for (var i = 0; i < document.getElementsByName("selected_device_type_list").item(0).options.length; i++)	{
		document.getElementsByName("selected_device_type_list").item(0).options[i].selected = true;
	}
	
	
	form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policyDefinition.wss";
	this.form1.submit();
	document.getElementsByName("button_new").item(0).disabled = false;
}

function deleteform1(){
	
	if(document.getElementsByName("ptvid").item(0).value == "" || document.getElementsByName("ptvid").item(0).value == 0){
		alert("没有可以删除的数据！");
		
		document.getElementsByName("button_new").item(0).disabled = true;	
		document.getElementsByName("button_bind").item(0).disabled = true;
		return false;
	}
	
	var vv=confirm("确认删除该条记录?");
   	if(vv){
   		document.getElementsByName("formAction").item(0).value= "delete";
		form1.formAction.value = "delete";
		form1.action = "<%=request.getContextPath()%>/secure/policytemplateapply/policyDefinition.wss";
		this.form1.submit();		
   }
		document.getElementsByName("button_new").item(0).disabled = true;	
		document.getElementsByName("button_bind").item(0).disabled = true;
}
function reloadNavi(){
	window.parent.frames[2].location.reload();
}

function doSelect(srcName, destName) {
	source = document.getElementsByName(srcName).item(0);
  target = document.getElementsByName(destName).item(0);
	while (source.selectedIndex >= 0) {
     target.options[target.options.length] = new Option(source.options[source.selectedIndex].text, source.options[source.selectedIndex].value);
     source.options.remove(source.selectedIndex);
	}
}

function doSelectAll(srcName, destName) {
	  source = document.getElementsByName(srcName).item(0);
    target = document.getElementsByName(destName).item(0);
	  while ( source.options.length > 0) {
	     target.options[target.options.length] = new Option(source.options[0].text, source.options[0].value);
	     source.options.remove(0);
	  }
	}
</script>



<body <c:if test="${definition.refresh eq 'true'}">onLoad="reloadNavi()"</c:if> class="navtree" style="background-color: #FFFFFF;" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0">
	<form action="" method="post" id="form1" name="form1">
    <input type="hidden" name="ppiid" value="${definition.ppiid}"/>
    <input type="hidden" name="ptvid" value="${definition.ptvid}"/>
    <input type="hidden" name="formAction" value="test" />
    <input type="hidden" name="searchMode" value="" />
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
							<TH class="wpsPortletTitle" width="100%">监控策略定义</TH>
						</TR>
						<TR>
							<TD CLASS="wpsPortletArea" COLSPAN="3"><a name="important"></a>
								<h1 id="title-bread-crumb">策略</h1>
								<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" class="button-section">
									<tr valign="top">
										<td class="table-button-section" nowrap>
											<table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0">
												<tr>
													<c:if test="${definition.cate ==9}">
														<td><input type="submit" name="button_bind" value="策略定制" class="buttons" onClick="policydetails9()" id="functions" /></td>
													</c:if>
													<c:if test="${definition.cate !=9}">
														<td><input type="submit" name="button_bind" value="策略定制" class="buttons" onClick="policydetails()" id="functions" /></td>
													</c:if>
													<td><input type="hidden" name="button_new" value="新建" class="buttons" onClick="addform1()" id="functions" /></td>
													<td><input type="button" name="button_update" value="保存" class="buttons" onClick="saveform1()" id="functions" /></td>
													<td><input type="submit" name="button_delete" value="删除" class="buttons" onClick="deleteform1()" id="functions" /></td>
												</tr>
											</table> 
										</td>
									</tr>
								</table> <!-- button section -->
								<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
									<tr valign="top">
										<td class="table-row" nowrap>
											<table style="display: inline; font-size: 95%;" cellspacing="3" cellpadding="5" border="0">
												<tr>
												</tr>
												<tr>
												<tr>
													<c:if test="${definition.message != null &&  definition.message != '' && param.searchMode != 'true'}">
														<div id="errmsg">
															<fmt:message>${definition.message }</fmt:message>
														</div>
													</c:if>
												</tr>
                        <tr>
                          <td>当前策略模板集</td>
                          <td>&nbsp;
                          <c:if test="${definition.currentPolicyPublishInfo != null}">
                            ${definition.currentPolicyPublishInfo.versionTag} -- [版本: ${definition.currentPolicyPublishInfo.version}]
                            <input type="hidden" name="ppiid" value="${definition.currentPolicyPublishInfo.ppiid}" />
                          </c:if> 
                          </td>
                        </tr>
                        <tr>
                          <td>策略类型</td>
                          <td>&nbsp; 
                          <c:if test="${definition.cate ==9}">私有MIB策略</c:if>
                          <c:if test="${definition.cate ==4}">端口策略</c:if>
                          <c:if test="${definition.cate ==1}">设备策略</c:if>
                          </td>
                        </tr>
												<tr>
												<td>策略名称<font color="red">*</font></td>
												<td>&nbsp; 
												  <c:if test="${definition.ptvid == null}">
												    <input type="text" name="mpname" value="${definition.mpname}" style="width: 245px" />
												  </c:if>
                          <c:if test="${definition.ptvid != null}">
                          ${definition.mpname}
                          <input type="hidden" name="mpname" value="${definition.mpname}" style="width: 245px" />
                          </c:if>
												  <input type="hidden" name="cate" value="${definition.cate}" /> 
												  <input type="hidden" name="ptvid" value="${definition.ptvid}" /> 
												  <input type="hidden" name="delete1" value="false" /> <input
													type="hidden" name="insertOrUpdate" value="false" />
												</td>
												</tr>
                        <tr>
                          <td>适用的设备类型</td>
                          <td>&nbsp;
                            <table>
                              <thead>
                                <tr>
                                  <td colspan="3">
                                    <select name="manufacturer_list" style="font-size: x-small;">
                                      <option value="">所有设备厂商</option>
                                      <c:forEach var="manufacturer" items="${definition.manufacturers}">
                                      <option value="${manufacturer.mrid}" <c:if test="${param.manufacturer_list==manufacturer.mrid}">selected</c:if>>${manufacturer.mrname}</option>
                                      </c:forEach>
                                    </select>
                                    &nbsp;
                                    <input type="text" name="deveiceTypeSearchText" style="width: 120px">
                                    &nbsp;
                                    <input type="button" value="查询" onclick="search();" name="searchButton">
                                  </td>
                                </tr>
                                <tr class="table-row">
                                  <td>&nbsp;待选设备类型:</td>
                                  <td></td>
                                  <td>&nbsp;策略影响的设备类型:</td>
                                </tr>
                              </thead>
                              <tbody>
                                <tr>
                                  <td>
                                    <select multiple="multiple" size="12" name="device_type_list" style="width: 240px; font-size: x-small;">
                                      <c:forEach var="devType" items="${definition.deviceTypes}">
                                      <option value="${devType.dtid}">${devType.mrName} - ${devType.model}</option>
                                      </c:forEach>
                                    </select>
                                  </td>
                                  <td>
                                    <input type="button" value="  >  " onclick="doSelect('device_type_list', 'selected_device_type_list');" style="width: 40px; font-size: x-small;"><br/>
                                    <input type="button" value="  <  " onclick="doSelect('selected_device_type_list', 'device_type_list');" style="width: 40px; font-size: x-small;"><br/>
                                    <input type="button" value=" >>  " onclick="doSelectAll('device_type_list', 'selected_device_type_list');" style="width: 40px; font-size: x-small;"><br/>
                                    <input type="button" value=" <<  " onclick="doSelectAll('selected_device_type_list', 'device_type_list');" style="width: 40px; font-size: x-small;"><br/>
                                  </td>
                                  <td>
                                    <select multiple="multiple" size="12" name="selected_device_type_list" style="width: 240px; font-size: x-small;">
                                      <c:forEach var="devType" items="${definition.selectedDeviceTypes}">
                                      <option value="${devType.dtid}">${devType.mrName} - ${devType.model}</option>
                                      </c:forEach>
                                    </select>
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                          </td>
                        </tr>
												<tr>
													<td>备注</td>
													<td><textarea rows="4" cols="20" name="description" style="width: 245px">${definition.policyTemplateVer.description}</textarea></td>
												</tr>
											</table>


										</td>
									</tr>
								</table> <!-- Data Table -->


								<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

									<TR>

										<TD CLASS="table-totals" VALIGN="baseline">&nbsp;&nbsp;&nbsp;</TD>
									</TR>

								</TABLE></TD>
						</TR>
					</TABLE>


				</TD>
			</TR>
		</TABLE>
	</form>
	<script type="text/javascript">
	if (document.getElementsByName("ptvid").item(0).value == "") {
		document.getElementsByName("button_new").item(0).disabled = true;
		document.getElementsByName("button_bind").item(0).disabled = true;
	}
	</script>
</body>
</html>